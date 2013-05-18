(ns clojuregex.core
  (:use ring.middleware.resource
        ring.middleware.params
        ring.middleware.content-type
        ring.util.response
        ring.middleware.edn)
  (:require [net.cgrand.moustache :as moustache]
            [ring.adapter.jetty :as jetty]))

(defn- root
  [req]
  (redirect "index.html"))

(defn- regex
  [{params :params}]
  {:status 200
   :headers { "Content-Type" "application/edn" }
   :body (pr-str (re-seq (re-pattern (:regex params)) (:sample params)))})

(def routes
  (moustache/app
   (wrap-edn-params)
   (wrap-resource "public")
   [""] {:get root}
   ["results"] {:post regex}))

(defn start []
  (jetty/run-jetty routes {:port 3000}))
