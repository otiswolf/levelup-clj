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

(defn update-skill! [{:keys [path-params params]}]
  (let [skill-key (get path-params :skill-key)
        value (get params :value)
        id (get params :id)]
    (case skill-key
      "total-xp" (db/update-skill-xp! {:id id :total_xp value}))
    (response/found "/")))

(defn add-xp! [{:keys [params]}]
  (let [id (get params :id)
        xp (get params :xp)]
    (db/update-skill-xp! {:id id :total_xp xp})
    (response/found "/")))

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
   ["/skill/delete"  {:post delete-skill!}]
   ["/skill/update/:skill-key" {:post update-skill!}]])
