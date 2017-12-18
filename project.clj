(defproject lein-umlaut "0.4.2"
  :description "Leiningen plugin for umlaut"
  :url "https://github.com/workco/lein-umlaut"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [umlaut "0.4.1"]]
  :deploy-repositories {"clojars" {:sign-releases false}}
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :eval-in-leiningen true)
