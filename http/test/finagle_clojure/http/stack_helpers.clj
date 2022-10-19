(ns finagle-clojure.http.stack-helpers
  (:import [com.twitter.finagle Http$Server Stack$Parameterized]
           [scala.jdk.javaapi CollectionConverters])
  (:require [finagle-clojure.scala :as scala]))

(defn- params [^Stack$Parameterized stackable]
  (map scala/tuple->vec (CollectionConverters/asJava (.params stackable))))

(defn extract-param [^Stack$Parameterized stackable ^Class cls]
  (->> stackable
       (params)
       (flatten)
       (filter #(instance? cls %))
       (first)))
