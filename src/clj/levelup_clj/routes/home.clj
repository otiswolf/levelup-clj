(ns levelup-clj.routes.home
  (:require
   [levelup-clj.layout :as layout]
   [levelup-clj.db.core :as db]
   [levelup-clj.util.exception :as ex]
   [clojure.java.io :as io] 
   [levelup-clj.middleware :as middleware]
   [ring.util.response]
   [ring.util.http-response :as response]
   [struct.core :as st]))

(def skill-schema
  [[:skill_name
    st/required
    st/string]])

(defn validate-skill [params]
  (first (st/validate params skill-schema)))

(defn create-skill! [{:keys [params] :as p}]
  (if-let [errors (validate-skill params)]
    (-> (response/found "/")
        (assoc :flash (assoc params :errors errors)))
    (try
      (db/create-skill!
       (merge params {:timestamp (java.util.Date.)
                      :skill_level 0
                      :total_xp 0}))
      (response/found "/")
      (catch Exception e (cond
                           (ex/unique-key-exception? (.getMessage e)) 
                           (-> (response/found "/") 
                               (assoc :flash (assoc params :errors {:skill_name "Skill name must be unique."})))
                           :else                     
                           (-> (response/found "/")
                               (assoc :flash (assoc params :errors {:skill_name (.getMessage e)}))))))))

(defn home-page [{:keys [flash] :as request}]
  (layout/render 
   request 
   "home.html" 
   (merge {:skills (db/get-skills)}
          (select-keys flash [:name :skill :errors]))))

(defn about-page [request]
  (layout/render request "about.html"))

(defn home-routes []
  [ "" 
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats
                 ]}
   ["/" {:get  home-page
         :post create-skill!}]
   ["/about" {:get about-page}]])

