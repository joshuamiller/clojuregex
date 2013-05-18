(ns clojureregex.application
  (:require [dommy.core :as dommy]
            [cljs.reader :as reader]
            [goog.net.XhrIo :as xhr])
  (:use-macros
   [dommy.macros :only [sel sel1 node]]))

(defn results-template [results]
  (map #(node [:li %]) results))

(defn get-cljs-result
  [regex sample]
  (re-seq (re-pattern regex) sample))

(defn put-cljs-result
  [results]
  (dommy/replace-contents! (sel1 :#cljs) (results-template results)))

(defn put-clj-result
  [results]
  (let [results (reader/read-string (.getResponseText (.-target results)))]
    (dommy/replace-contents! (sel1 :#clj) (results-template (or results [])))))

(defn get-clj-result
  [regex sample]
  (xhr/send "results" put-clj-result "POST" (pr-str {:regex regex :sample sample}) (js-obj "Content-Type" "application/edn")))

(defn put-result
  []
  (let [regex  (dommy/value (sel1 :#regex))
        sample (dommy/value (sel1 :#sample))]
    (do (put-cljs-result (get-cljs-result regex sample))
        (get-clj-result regex sample))))

(dommy/listen! (sel1 :#submit) :click put-result)


