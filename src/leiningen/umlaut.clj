(ns leiningen.umlaut
  (:require
    [umlaut.generators.lacinia :as lacinia]
    [umlaut.generators.dot :as dot]
    [umlaut.generators.graphql :as graphql]
    [umlaut.utils :as utils]))
(use '[clojure.pprint :only [pprint]])

(defn- process-dot [ins out]
  (utils/save-dotstring-to-image out (dot/gen ins)))

(defn- process-lacinia [ins out]
  (utils/save-map-to-file out (lacinia/gen ins)))

(defn- process-graphql [ins out]
  (utils/save-string-to-file out (graphql/gen ins)))

(defn umlaut
  "Manage a umlaut-based application"
  {:help-arglists '([dot graphql lacinia] [input-files] [output-file-or-path])}
  [project generator & args]
  (case generator
    "dot" (process-dot (drop-last args) (last args))
    "lacinia" (process-lacinia (drop-last args) (last args))
    "graphql" (process-graphql (drop-last args) (last args))
    (leiningen.core.main/warn "Invalid generator, please run: lein help umlaut")))
