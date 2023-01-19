(ns levelup-clj.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [levelup-clj.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[levelup-clj started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[levelup-clj has shut down successfully]=-"))
   :middleware wrap-dev})
