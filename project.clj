(defproject lein-umlaut "0.2.0"
  :description "CLI for umlaut"
  :url "https://github.com/workco/lein-umlaut"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clojure-future-spec "1.9.0-alpha14"]
                 [umlaut "0.2.0"]]
  :deploy-repositories {"clojars" {:sign-releases false}}
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :eval-in-leiningen true)
