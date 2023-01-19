(ns levelup-clj.routes.skills
  (:require
   [levelup-clj.layout :as layout]
   [levelup-clj.db.core :as db]
   [clojure.java.io :as io]
   [levelup-clj.middleware :as middleware]
   [ring.util.response]
   [ring.util.http-response :as response]
   [struct.core :as st]))

(defn get-skill [id] 
   (db/get-skill {:id id}))

(defn delete-skill! [{:keys [params]}]
  (let [id (get params :id)]
    (db/delete-skill! {:id id})
    (response/found "/")))

(defn skill-page [{:keys [query-params] :as request}]
  (let [id (get query-params "id")]
    (layout/render
     request
     "skill.html"
     {:skill (get-skill id)})))

(defn skills-routes []
  [""
   {:middleware      [middleware/wrap-csrf
                      middleware/wrap-formats]}
   ["/skill"         {:get skill-page}]
   ["/skill/delete"  {:post delete-skill!}]])
