(ns finagle-clojure.builder.server
  "Functions for creating and altering `com.twitter.finagle.Server` objects independent
  of any particular codec. Generally speaking codec-specific server functions
  should be preferred, but these are included for comptability with older systems
  configured at the server level."
  (:require [finagle-clojure.scala :as scala])
  (:import (com.twitter.finagle.server StackServer$)
           (com.twitter.finagle Service ListeningServer)
           (com.twitter.finagle.netty4 Netty4Listener Netty4Listener$)
           (java.net InetSocketAddress)
           (io.netty.channel ChannelPipeline)
           (io.netty.handler.codec.string StringEncoder StringDecoder)
           (io.netty.handler.codec DelimiterBasedFrameDecoder Delimiters)
           (io.netty.util CharsetUtil)
           (scala.reflect Manifest)
           (com.twitter.util Future)
           (com.twitter.finagle.dispatch SerialServerDispatcher)))

(defn- string-pipeline-builder
  [^ChannelPipeline p]
  (doto p
    (.addLast "line" (new DelimiterBasedFrameDecoder 100 (Delimiters/lineDelimiter)))
    (.addLast "stringDecoder" (new StringDecoder (CharsetUtil/UTF_8)))
    (.addLast "stringEncoder" (new StringEncoder (CharsetUtil/UTF_8)))))

(defn- ^Netty4Listener netty4listener
  []
  (.apply Netty4Listener$/MODULE$
          (scala/Function [p] (string-pipeline-builder p))
          (.defaultParams StackServer$/MODULE$)
          (Manifest/Any)
          (Manifest/Any)))

(defn- dispatcher-builder
  [^Service service]
  (scala/Function [t] (SerialServerDispatcher. t service)))

(defn- address
  [p]
  (InetSocketAddress. (int p)))

(defn ^ListeningServer server
  [^Service service port]
  (let [server (.listen (netty4listener) (address port) (dispatcher-builder service))]
    server))

(defn ^Future close!
  "Stops the given Server.

  *Arguments*:

    * `server`: an instance of [[com.twitter.finagle.builder.Server]]

  *Returns*:

    a Future that closes when the server stops"
  [^ListeningServer server]
  (.close server))
