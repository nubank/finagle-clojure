(defproject finagle-clojure/core "1.0.1-SNAPSHOT"
  :description "A light wrapper around Finagle & Twitter Util for Clojure"
  :url "https://github.com/twitter/finagle-clojure"
  :license {:name "Apache License, Version 2.0"
            :url  "https://www.apache.org/licenses/LICENSE-2.0"}
  :scm {:name "git" :url "http://github.com/nubank/finagle-clojure" :dir ".."}
  :plugins [[s3-wagon-private "1.3.1"]
            [lein-midje "3.2"]
            [lein-modules "0.3.11"]]
  :profiles {:dev {:dependencies [[org.clojure/clojure "1.11.2"]
                                  [midje "1.9.9" :exclusions [org.clojure/clojure]]
                                  [criterium "0.4.4"]]}}
  :repositories [["nu-maven" {:url "s3p://nu-maven/releases/"}]]
  :deploy-repositories [["releases" {:url "s3p://nu-maven/releases/" :no-auth true}]]
  ;; the explicit scala/jackson pins below override vulnerable transitive
  ;; versions pulled by finagle 24.2.0 (the last Finagle release ever published);
  ;; jackson artifacts must stay aligned on the same version
  :dependencies [[com.twitter/finagle-core_2.13 "24.2.0"]
                 [org.clojure/algo.monads "0.1.6"]
                 [org.scala-lang/scala-library "2.13.16"]
                 ;; snakeyaml 1.33 clears the <1.32 DoS CVEs without the 2.x API break;
                 ;; CVE-2022-1471 (fixed only in 2.x) remains open until the 2.x migration
                 [org.yaml/snakeyaml "1.33"]
                 [com.fasterxml.jackson.core/jackson-core "2.18.8"]
                 [com.fasterxml.jackson.core/jackson-databind "2.18.8"]
                 [com.fasterxml.jackson.core/jackson-annotations "2.18.8"]
                 [com.fasterxml.jackson.module/jackson-module-scala_2.13 "2.18.8"]])
