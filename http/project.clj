(defproject finagle-clojure/http "1.0.1-SNAPSHOT"
  :description "A light wrapper around Finagle HTTP for Clojure"
  :url "https://github.com/twitter/finagle-clojure"
  :license {:name "Apache License, Version 2.0"
            :url  "https://www.apache.org/licenses/LICENSE-2.0"}
  :scm {:name "git" :url "https://github.com/nubank/finagle-clojure"}
  :plugins [[s3-wagon-private "1.3.1"]
            [lein-midje "3.2"]
            [lein-modules "0.3.11"]]
  :profiles {:dev {:dependencies [[org.clojure/clojure "1.11.2"]
                                  [midje "1.9.9" :exclusions [org.clojure/clojure]]]}}
  :repositories [["nu-maven" {:url "s3p://nu-maven/releases/"}]]
  :deploy-repositories [["releases" {:url "s3p://nu-maven/releases/" :no-auth true}]]
  ;; the netty pins below override the vulnerable 4.1.100.Final pulled by
  ;; finagle 24.2.0 (the last Finagle release ever published); all netty
  ;; artifacts must stay aligned on the same version, including the
  ;; classified native-epoll jars (conflict resolution is per classifier)
  :dependencies [[finagle-clojure/core "1.0.1-SNAPSHOT"]
                 [com.twitter/finagle-http_2.13 "24.2.0"]
                 [com.twitter/finagle-stats_2.13 "24.2.0"]
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
                 [io.netty/netty-codec-http2 "4.1.135.Final"]
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
