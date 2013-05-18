(defproject clojuregex "0.1.0-SNAPSHOT"
  :min-lein-version "2.0.0"
  :description "Online Clojure[Script] Regex Playground"
  :url "http://clojuregex.org/"
  :license {:name "The MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :plugins [[lein-cljsbuild "0.3.2"]
            [lein-ring "0.8.2"]]
  :ring { :handler clojuregex.core/routes }
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [net.cgrand/moustache "1.1.0"]
                 [ring "1.0.3"]
                 [prismatic/dommy "0.1.1"]
                 [fogus/ring-edn "0.1.0"]]
  :source-paths ["src/clj"]
  :cljsbuild {:builds
              [{:source-paths ["src/cljs"]
                :compiler
                {:output-to "resources/public/clojuregex.js"
                 :optimizations :advanced}}]})
