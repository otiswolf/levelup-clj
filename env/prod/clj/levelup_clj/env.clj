(ns levelup-clj.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[levelup-clj started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[levelup-clj has shut down successfully]=-"))
   :middleware identity})
