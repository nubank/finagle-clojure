(defproject finagle-clojure/core "0.10.2-SNAPSHOT"
  :description "A light wrapper around Finagle & Twitter Util for Clojure"
  :url "https://github.com/twitter/finagle-clojure"
  :license {:name "Apache License, Version 2.0"
            :url  "https://www.apache.org/licenses/LICENSE-2.0"}
  :scm {:name "git" :url "http://github.com/nubank/finagle-clojure" :dir ".."}
  :plugins [[s3-wagon-private "1.3.1"]
            [lein-midje "3.2"]
            [lein-modules "0.3.11"]]
  :profiles {:dev {:dependencies [[org.clojure/clojure "1.10.0"]
                                  [midje "1.9.9" :exclusions [org.clojure/clojure]]
                                  [criterium "0.4.4"]]}}
  :repositories [["nu-maven" {:url "s3p://nu-maven/releases/"}]]
  :deploy-repositories [["releases" {:url "s3p://nu-maven/releases/" :no-auth true}]]
  :dependencies [[com.twitter/finagle-core_2.11 "20.8.1"]
                 [org.clojure/algo.monads "0.1.6"]])
