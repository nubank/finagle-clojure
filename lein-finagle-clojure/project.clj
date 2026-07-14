(defproject lein-finagle-clojure "1.0.1-SNAPSHOT"
  :description "A lein plugin for working with finagle-clojure"
  :url "https://github.com/twitter/finagle-clojure"
  :license {:name "Apache License, Version 2.0"
            :url "https://www.apache.org/licenses/LICENSE-2.0"}
  :scm {:name "git" :url "https://github.com/finagle/finagle-clojure"}
  :min-lein-version "2.0.0"
  :plugins [[s3-wagon-private "1.3.1"]
            [lein-modules "0.3.11"]]
  :repositories [["nu-maven" {:url "s3p://nu-maven/releases/"}]
                 ["sonatype" "https://oss.sonatype.org/content/groups/public/"]
                 ["twitter" {:url "https://maven.twttr.com/" :checksum :warn}]]
  :deploy-repositories [["releases" {:url "s3p://nu-maven/releases/" :no-auth true}]]
  ;; the pins below override vulnerable transitives of scrooge 24.2.0
  ;; (the last scrooge release ever published); libthrift is capped at
  ;; 0.12.0 for the same scrooge codegen compatibility reason documented
  ;; in thrift/project.clj
  :dependencies [[com.twitter/scrooge-generator_2.13 "24.2.0"]
                 [com.twitter/scrooge-linter_2.13 "24.2.0"]
                 [org.apache.thrift/libthrift "0.12.0"]
                 [org.scala-lang/scala-library "2.13.16"]
                 [org.codehaus.plexus/plexus-utils "3.6.1"]
                 [com.google.guava/guava "32.0.1-jre"]
                 [org.apache.httpcomponents/httpclient "4.5.14"]
                 [com.fasterxml.jackson.core/jackson-core "2.18.9"]
                 [com.fasterxml.jackson.core/jackson-databind "2.18.9"]
                 [com.fasterxml.jackson.core/jackson-annotations "2.18.9"]
                 [com.fasterxml.jackson.module/jackson-module-scala_2.13 "2.18.9"]]
  :eval-in-leiningen true)
