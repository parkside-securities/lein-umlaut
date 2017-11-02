(ns leiningen.umlaut
  (:require
    [clojure.java.io :as io]
    [clojure.string :as str]
    [umlaut.generators.lacinia :as lacinia]
    [umlaut.generators.dot :as dot]
    [umlaut.generators.spec :as spec]
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
    (utils/save-string-to-file (join-path out "all.dot") (dot/gen-all umlaut))
    (reduce (fn [acc [key value]]
              (utils/save-dotstring-to-image (join-path out (str key ".png")) value)
              (utils/save-string-to-file (join-path out (str key ".dot")) value))
      {} (seq (dot/gen-by-group umlaut)))))

(defn- process-spec [ins args]
  (let [out (first args)
        spec-package (second args)
        custom-validators-filepath (nth args 2)
        id-namespace (nth args 3)
        specs (spec/gen spec-package custom-validators-filepath id-namespace ins)]
    (doseq [[k v] specs]
      (let [filename (clojure.string/replace k #"-" "_")]
        (utils/save-string-to-file (join-path out (str filename ".clj")) v)))))

(defn- process-lacinia [ins out]
  (utils/save-map-to-file out (lacinia/gen ins)))

(defn- process-graphql [ins out]
  (utils/save-string-to-file out (graphql/gen ins)))

(defn ^:no-project-needed umlaut
  "Umlaut plugin to interact with umlaut generators.

  Usage:
    - lein umlaut dot [umlaut-files-folder] [output-folder]
    - lein umlaut graphql [umlaut-files-folder] [output-file]
    - lein umlaut lacinia [umlaut-files-folder] [output-file]
    - lein umlaut spec [umlaut-files-folder] [output-folder] [spec-package] [custom-validators-filepath] [id-namespace]

  All files ending with .umlaut inside the input folder will be considered"
  [project generator & args]
  (when (and (not= (count args) 2) (not= (count args) 5))
    (throw (Exception. "Invalid number of arguments, please run: lein help umlaut")))
  (case generator
    "dot" (process-dot (get-umlaut-files (first args)) (last args))
    "lacinia" (process-lacinia (get-umlaut-files (first args)) (last args))
    "graphql" (process-graphql (get-umlaut-files (first args)) (last args))
    "spec" (process-spec (get-umlaut-files (first args)) (rest args))
    (leiningen.core.main/warn "Invalid generator, please run: lein help umlaut")))
