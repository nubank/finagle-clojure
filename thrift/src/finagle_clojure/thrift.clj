(ns finagle-clojure.thrift
  "Functions for creating Thrift clients & servers from Java classes generated
  from a Thrift service definition using [Scrooge](https://twitter.github.io/scrooge/).
  
  The lein-finagle-clojure plugin can be used to compile Thrift definitions to Java with Scrooge.
  
  See:
  * test/clj/finagle_clojure/thrift_test.clj
  * https://github.com/samn/finagle-clojure-examples/tree/master/dog-breed-info"
  (:require [finagle-clojure.options :as options]
            [finagle-clojure.scala :as scala]
            [clojure.java.io :as io])
  (:import [com.twitter.finagle ListeningServer Service Thrift]
           [javax.net.ssl SSLContext X509TrustManager]
           [java.net InetSocketAddress]
           [java.security.cert X509Certificate]))

(defn- ^:no-doc ^String canonical-class-name
  "Take a class-name, which can be a String, Symbol or Class and returns
  the canonical class name for it (package + class).
  If class-name is a symbol the ns-imports for the current ns are checked.
  If there's no import matching the class-name symbol the symbol is returned
  as a String."
  [class-name]
  (if-let [^Class class (get (ns-imports *ns*) class-name)]
    (.getCanonicalName class)
    (if (class? class-name)
      (.getCanonicalName ^Class class-name)
      (str class-name))))

(defn ^:no-doc finagle-interface
  "Service -> 'package.canonical.Service$ServiceIface"
  [service-class-name]
  (let [canonical-service-class-name (canonical-class-name service-class-name)]
    (if (.endsWith canonical-service-class-name "$ServiceIface")
      (symbol canonical-service-class-name)
      (symbol (str canonical-service-class-name "$ServiceIface")))))

(defmacro service
  "Sugar for implementing a com.twitter.finagle.Service based on the
  interface defined in `qualified-service-class-name`. The appropriate
  Finagle interface for that class will automatically be imported.
  Provide an implementation for it like `proxy` (`this` is an implicit argument).

  The Finagle interface for a Service class generated by Scrooge will wrap the response
  type of a method in Future so it is asynchronous.

  *Arguments*:

    * `qualified-service-class-name`: This class's Finagled interface will automatically be imported.
        e.g. if you pass MyService then MyService$ServiceIface will be imported and used.
    * `body`: the implementation of this service. Methods should be defined without an explicit `this` argument.

  *Returns*:

  A new `Service`."
  [service-class-name & body]
  `(do
     (import ~(finagle-interface service-class-name))
     (proxy [~(finagle-interface service-class-name)] []
       ~@body)))

(defn serve
  "Serve `service` on `addr`. Use this to actually run your Thrift service.
  Note that this will not block while serving.
  If you want to wait on this use [[finagle-clojure.futures/await]].

  *Arguments*:

    * `addr`: The port on which to serve.
    * `service`: The Service that should be served.

  *Returns*:

  A new com.twitter.finagle.ListeningServer."
  [^String addr ^Service service]
  (.serveIface (Thrift/server) addr service))

(defn serve-tls
  "Serve `service` on `addr` over TLS.  Use this to actually run your Thrift Service.
  Note that this will not block while serving.
  If you want to wait on this use [[finagle-clojure.futures/await]].

  *Arguments*:

    * `addr`: The port on which to serve.
    * `service`: The Service that should be served.
    * `opts`: key/value options for the server, includes:
      - `:priv`: (required) fully qualified file name for the private key
                 in PEM format used for running the server
      - `:pub`: (required) fully qualified file name for the public key
                in PEM format used for running the server

  *Arguments*:

    * `addr`: The port on which to serve.
    * `service`: The Service that should be served.
    * `context`: The SSL context that should be used.

  *Returns*:

  A new com.twitter.finagle.ListeningServer."
  ([^String addr ^Service service priv pub]
   (if (not (and (.exists (io/file priv)) (.exists (io/file pub))))
     (throw (IllegalArgumentException. "Could not find public and/or private key."))
     (-> (Thrift/server)
         (.withTransport)
         (.tls pub priv (options/option) (options/option) (options/option))
         (.serveIface addr service))))
  ([^String addr ^Service service ^SSLContext context]
   (-> (Thrift/server)
       (.withTransport)
       (.tls context)
       (.serveIface addr service))))

(defn announce*
  "Announce this server to the configured load balancer.

  *Arguments*:
  * `path`: a String representing the path on the load balancer
  * `server`: a ListeningServer (returned by [serve])
  
  *Returns*:
  
  A Future[Announcement].

  *See*:
  [[announce]], [https://twitter.github.io/finagle/guide/Names.html]"
  [ path ^ListeningServer server]
  (.announce server path))

(defn announce
  "Announce this server to the configured load balancer.

  This functions the same as [[announce*]] but returns the `server` passed in
  so it can be chained together like:

  ````clojure
  (->> service
       (thrift/serve \":9999\")
       (thrift/announce \"zk!localhost!/path/to/nodes\")
       (f/await))
  ````

  *Arguments*:
  * `path`: a String represent the path on the load balancer
  * `server`: a ListeningServer (returned by [serve])
  
  *Returns*:
  
  `server`

  *See*:
  [[announce*]], [https://twitter.github.io/finagle/guide/Names.html]"
  [path ^ListeningServer server]
  (announce* path server)
  server)

(defmacro client
  "Sugar for creating a client for a compiled Thrift service.
  The appropriate Finagle interface for that class will automatically be imported.
  Note that operations on this client will return a Future representing the result of an call
  This is meant to show that this client can make an RPC call and may be expensive to invoke.

  E.g. if a Thrift service definition has a method called `doStuff` you can call it on a client
  like this `(.doStuff client)`. 

  *Arguments*:

    * `addr`: Where to find the Thrift server.
    * `qualified-service-class-name`: This class's Finagled interface will automatically be imported.
        e.g. if you pass MyService then MyService$ServiceIface will be imported and used.

  *Returns*:

  A new client."
  [addr client-iterface-class]
  `(do
     (import ~(finagle-interface client-iterface-class))
     (.newIface (Thrift/client) ~addr ~(finagle-interface client-iterface-class))))

(defn ^:no-doc ssl-context
  "Creates an SSLContext object that uses the provided TrustManagers"
  [trust-mgrs]
  (let [ctx (SSLContext/getInstance "TLS")]
    (.init ctx nil trust-mgrs nil)
    ctx))


(defn insecure-ssl-context
  "Returns a naive SSLContext that provides no certificate verification.

  THIS SHOULD ONLY BE USED FOR TESTING."
  []
  (ssl-context
    (into-array
      (list
        (proxy [X509TrustManager] []
          (getAcceptedIssuers [] (make-array X509Certificate 0))
          (checkClientTrusted [_ _] nil)
          (checkServerTrusted [_ _] nil))))))

(defmacro client-tls
  "Sugar for creating a TLS-enabled client for a compiled Thrift service where the server
  has TLS enabled.  You must use this client for TLS enabled servers.

  The appropriate Finagle interface for that class will automatically be imported.
  Note that operations on this client will return a Future representing the result of an call
  This is meant to show that this client can make an RPC call and may be expensive to invoke.

  E.g. if a Thrift service definition has a method called `doStuff` you can call it on a client
  like this `(.doStuff client)`.

  *Arguments*:

    * `addr`: Where to find the Thrift server.
    * `qualified-service-class-name`: This class's Finagled interface will automatically be imported.
        e.g. if you pass MyService then MyService$ServiceIface will be imported and used.

  *Returns*:

  A new client."
  [addr client-interface-class ssl-ctx]
  `(do
     (import ~(finagle-interface client-interface-class))
     (->
       (Thrift/client)
       (.withTransport)
       (.tls ~ssl-ctx)
       (.newIface ~addr ~(finagle-interface client-interface-class)))))
