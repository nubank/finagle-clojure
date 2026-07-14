(defproject finagle-clojure/thrift "1.0.1-SNAPSHOT"
  :description "A light wrapper around finagle-thrift for Clojure"
  :url "https://github.com/twitter/finagle-clojure"
  :license {:name "Apache License, Version 2.0"
            :url "https://www.apache.org/licenses/LICENSE-2.0"}
  :scm {:name "git" :url "https://github.com/nubank/finagle-clojure"}
  :plugins [[s3-wagon-private "1.3.1"]
            [lein-midje "3.2.1"]
            [lein-modules "0.3.11"]]
  :profiles {:dev   {:dependencies   [[org.clojure/clojure "1.11.2"]
                                      [midje "1.9.9" :exclusions [org.clojure/clojure]]
                                      [br.com.nubank/tls-extensions "7.2.0"]
                                      ;; tls-extensions uses javax.xml.bind, gone from the JDK since Java 11
                                      [javax.xml.bind/jaxb-api "2.3.1"]
                                      ;; CVE pins for vulnerable transitives of tls-extensions;
                                      ;; aws-java-sdk-core is pinned alongside s3 so the two stay aligned
                                      [com.google.code.gson/gson "2.10.1"]
                                      [com.google.guava/guava "32.0.1-jre"]
                                      [com.amazonaws/aws-java-sdk-s3 "1.12.797"]
                                      [com.amazonaws/aws-java-sdk-core "1.12.797"]]
                     :resource-paths ["test/resources"]
                     :test-paths     ["test/clj/"]}
             :midje {:plugins [[lein-finagle-clojure "1.0.0"]]}}
  :finagle-clojure {:thrift-source-path "test/resources" :thrift-output-path "test/java"}
  :java-source-paths ["test/java"]
  :jar-exclusions [#"test"]
  ;; TODO there's no checksum for libthrift-0.5.0.pom, set checksum to warn for now
  :repositories [["nu-maven" {:url "s3p://nu-maven/releases/"}]
                 ["twitter" {:url "https://maven.twttr.com/" :checksum :warn}]]
  :deploy-repositories [["releases" {:url "s3p://nu-maven/releases/" :no-auth true}]]
  ;; the dependency on finagle-clojure/core is required for tests
  ;; but also to require fewer dependencies in projects that use thrift.
  ;; this is akin to Finagle itself, where depending on finagle-thrift
  ;; pulls in finagle-core as well.
  ;; the netty pins below override the vulnerable 4.1.100.Final pulled by
  ;; finagle 24.2.0 (the last Finagle release ever published); all netty
  ;; artifacts must stay aligned on the same version, including the
  ;; classified native-epoll jars (conflict resolution is per classifier)
  :dependencies [[finagle-clojure/core "1.0.1-SNAPSHOT"]
                 [com.twitter/finagle-thrift_2.13 "24.2.0"]
                 ;; scrooge 24.2.0 generates `boolean TProcessor.process`; libthrift 0.13+
                 ;; changed it to void, so 0.12.0 is the newest compatible version
                 [org.apache.thrift/libthrift "0.12.0"]
                 ;; httpclient is a vulnerable transitive of libthrift
                 [org.apache.httpcomponents/httpclient "4.5.14"]
                 [org.apache.tomcat/tomcat-jni "8.5.100"]
                 [org.scala-lang/scala-library "2.13.16"]
                 ;; snakeyaml 2.x clears CVE-2022-1471 (RCE); util-security is exercised
                 ;; against it by the midje suites
                 [org.yaml/snakeyaml "2.4"]
                 [com.fasterxml.jackson.core/jackson-core "2.18.9"]
                 [com.fasterxml.jackson.core/jackson-databind "2.18.9"]
                 [com.fasterxml.jackson.core/jackson-annotations "2.18.9"]
                 [com.fasterxml.jackson.module/jackson-module-scala_2.13 "2.18.9"]
                 [io.netty/netty-buffer "4.1.135.Final"]
                 [io.netty/netty-codec "4.1.135.Final"]
                 [io.netty/netty-codec-dns "4.1.135.Final"]
                 [io.netty/netty-codec-http "4.1.135.Final"]
                 [io.netty/netty-codec-socks "4.1.135.Final"]
                 [io.netty/netty-common "4.1.135.Final"]
                 [io.netty/netty-handler "4.1.135.Final"]
                 [io.netty/netty-handler-proxy "4.1.135.Final"]
                 [io.netty/netty-resolver "4.1.135.Final"]
                 [io.netty/netty-resolver-dns "4.1.135.Final"]
                 [io.netty/netty-transport "4.1.135.Final"]
                 [io.netty/netty-transport-classes-epoll "4.1.135.Final"]
                 [io.netty/netty-transport-native-unix-common "4.1.135.Final"]
                 [io.netty/netty-transport-native-epoll "4.1.135.Final"]
                 [io.netty/netty-transport-native-epoll "4.1.135.Final" :classifier "linux-x86_64"]
                 [io.netty/netty-transport-native-epoll "4.1.135.Final" :classifier "linux-aarch_64"]])
