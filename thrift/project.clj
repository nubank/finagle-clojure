(defproject finagle-clojure/thrift "1.0.1-SNAPSHOT"
  :description "A light wrapper around finagle-thrift for Clojure"
  :url "https://github.com/twitter/finagle-clojure"
  :license {:name "Apache License, Version 2.0"
            :url "https://www.apache.org/licenses/LICENSE-2.0"}
  :scm {:name "git" :url "https://github.com/nubank/finagle-clojure"}
  :plugins [[s3-wagon-private "1.3.1"]
            [lein-midje "3.2.1"]
            [lein-modules "0.3.11"]]
  :profiles {:dev   {:dependencies   [[org.clojure/clojure "1.10.0"]
                                      [midje "1.9.9" :exclusions [org.clojure/clojure]]
                                      [br.com.nubank/tls-extensions "7.2.0"]
                                      ;; tls-extensions uses javax.xml.bind, gone from the JDK since Java 11
                                      [javax.xml.bind/jaxb-api "2.3.1"]]
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
  :dependencies [[finagle-clojure/core "1.0.1-SNAPSHOT"]
                 [com.twitter/finagle-thrift_2.13 "24.2.0"]
                 ;; scrooge 24.2.0 generates `boolean TProcessor.process`; libthrift 0.13+
                 ;; changed it to void, so 0.12.0 is the newest compatible version
                 [org.apache.thrift/libthrift "0.12.0"]
                 ;; full jackson 2.18.9 stack (GHSA-5jmj-h7xm-6q6v); Finagle bundles a vulnerable 2.14.x,
                 ;; and jackson-module-scala enforces databind version match, so all four move together
                 [com.fasterxml.jackson.core/jackson-databind "2.18.9"]
                 [com.fasterxml.jackson.core/jackson-core "2.18.9"]
                 [com.fasterxml.jackson.core/jackson-annotations "2.18.9"]
                 [com.fasterxml.jackson.module/jackson-module-scala_2.13 "2.18.9" :exclusions [com.google.guava/guava]]
                 [org.apache.tomcat/tomcat-jni "8.5.100"]])
