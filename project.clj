(defproject lein-umlaut "0.5.3"
  :description "Leiningen plugin for umlaut"
  :url "https://github.com/workco/lein-umlaut"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [umlaut "0.5.3"]]
  :deploy-repositories {"clojars" {:sign-releases false}}
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :eval-in-leiningen true)
