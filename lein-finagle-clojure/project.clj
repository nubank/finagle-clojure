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
  :dependencies [[com.twitter/scrooge-generator_2.13 "24.2.0"]
                 [com.twitter/scrooge-linter_2.13 "24.2.0"]
                 ;; full jackson 2.18.9 stack (GHSA-5jmj-h7xm-6q6v); scrooge bundles a vulnerable 2.14.x,
                 ;; and jackson-module-scala enforces databind version match, so all four move together
                 [com.fasterxml.jackson.core/jackson-databind "2.18.9"]
                 [com.fasterxml.jackson.core/jackson-core "2.18.9"]
                 [com.fasterxml.jackson.core/jackson-annotations "2.18.9"]
                 [com.fasterxml.jackson.module/jackson-module-scala_2.13 "2.18.9" :exclusions [com.google.guava/guava org.scala-lang.modules/scala-collection-compat_3]]]
  :eval-in-leiningen true)
