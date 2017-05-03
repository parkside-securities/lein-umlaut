(ns leiningen.umlaut
  (:require
    [clojure.java.io :as io]
    [clojure.string :as str]
    [umlaut.generators.lacinia :as lacinia]
    [umlaut.generators.dot :as dot]
    [umlaut.generators.graphql :as graphql]
    [umlaut.core :as core]
    [umlaut.utils :as utils]))
(use '[clojure.pprint :only [pprint]])

(defn- join-path [out filename]
  "Join two paths"
  (-> out
    (io/file filename)
    (.getPath)))

(defn- get-umlaut-files [in]
  "Returns a list of .umlaut files from input folder"
  (->> in
       (io/file)
       (file-seq)
       (map #(.getPath %))
       (filter #(str/ends-with? % ".umlaut"))))

(defn- process-dot [ins out]
  (let [umlaut (core/main ins)]
    (utils/save-dotstring-to-image (join-path out "all.png") (dot/gen-all umlaut))
    (reduce (fn [acc [key value]]
              (utils/save-dotstring-to-image (join-path out (str key ".png")) value))
      {} (seq (dot/gen-by-group umlaut)))))

(defn- process-lacinia [ins out]
  (utils/save-map-to-file out (lacinia/gen ins)))

(defn- process-graphql [ins out]
  (utils/save-string-to-file out (graphql/gen ins)))

(defn umlaut
  "Umlaut plugin to interact with umlaut generators.

  Usage:
    - All generators, besides dot, expect a input folder and a output file.
    - dot generator expects an output folder, since it can output several files.

  All files ending with .umlaut inside the input folder will be considered"
  {:help-arglists '([dot graphql lacinia] [input-folder] [output-file-or-path])}
  [project generator & args]
  (when (not= (count args) 2)
    (throw (Exception. "Invalid number of arguments, please run: lein help umlaut")))
  (case generator
    "dot" (process-dot (get-umlaut-files (first args)) (last args))
    "lacinia" (process-lacinia (get-umlaut-files (first args)) (last args))
    "graphql" (process-graphql (get-umlaut-files (first args)) (last args))
    (leiningen.core.main/warn "Invalid generator, please run: lein help umlaut")))
