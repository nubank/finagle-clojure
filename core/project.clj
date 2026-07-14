(defproject finagle-clojure/core "1.0.1-SNAPSHOT"
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
  :dependencies [[com.twitter/finagle-core_2.13 "24.2.0"]
                 ;; full jackson 2.18.9 stack (GHSA-5jmj-h7xm-6q6v); Finagle bundles a vulnerable 2.14.x,
                 ;; and jackson-module-scala enforces databind version match, so all four move together
                 [com.fasterxml.jackson.core/jackson-databind "2.18.9"]
                 [com.fasterxml.jackson.core/jackson-core "2.18.9"]
                 [com.fasterxml.jackson.core/jackson-annotations "2.18.9"]
                 [com.fasterxml.jackson.module/jackson-module-scala_2.13 "2.18.9" :exclusions [com.google.guava/guava]]
                 [org.clojure/algo.monads "0.1.6"]])
