(ns finagle-clojure.builder.server-test
  (:import (com.twitter.finagle ListeningServer))
  (:require [midje.sweet :refer :all]
            [finagle-clojure.builder.server :as server]
            [finagle-clojure.service :as service]
            [finagle-clojure.futures :as f]
            [finagle-clojure.scala :as scala]))

(def empty-service
  (service/mk [req]
    (f/value nil)))

(facts "We have a ListeningServer"
       (let [server (server/server empty-service 3050)]
         (ancestors (class server)) => (contains ListeningServer)
         (f/await (server/close! server)) => scala/unit))
