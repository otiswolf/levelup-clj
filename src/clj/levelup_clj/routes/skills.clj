(ns levelup-clj.routes.skills
  (:require
   [levelup-clj.layout :as layout]
   [levelup-clj.db.core :as db]
   [levelup-clj.util.exception :as ex]
   [levelup-clj.middleware :as middleware]
   [camel-snake-kebab.core :as csk]
   [ring.util.response]
   [ring.util.http-response :as response]
   [struct.core :as st]))

(defn get-skill [id] 
   (-> (db/get-skill {:id id})
       (update-keys csk/->kebab-case)))

(defn edit-skill! [{:keys [params]}]
  (let [skill-name (get params :skill-name)
        skill-level (get params :skill-level)
        total-xp (get params :total-xp)
        id (get params :id)]
    (try
      (db/update-skill! {:id id :skill-name skill-name :skill-level skill-level :total-xp total-xp})
      (response/found (str "/skill?id=" id))
      (catch Exception e (cond
                           (ex/unique-key-exception? (.getMessage e)) 
                           (-> (response/found (str "/skill/edit?id=" id)) 
                               (assoc :flash (assoc params :errors {:skill-name "Skill name must be unique."})))
                           :else                     
                           (-> (response/found (str "/skill/edit?id=" id))
                               (assoc :flash (assoc params :errors {:general (.getMessage e)}))))))))

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

(defn edit-skill-page [{:keys [query-params flash] :as request}]
  (let [id (get query-params "id")]
    (layout/render
     request
     "edit-skill.html"
     {:skill (get-skill id)
      :errors (:errors flash)})))

(defn skills-routes []
  [""
   {:middleware      [middleware/wrap-csrf
                      middleware/wrap-formats]}
   ["/skill"         {:get  skill-page}]
   ["/skill/delete"  {:post delete-skill!}]
   ["/skill/edit"    {:get  edit-skill-page
                      :post edit-skill!}]])
