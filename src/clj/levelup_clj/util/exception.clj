(ns levelup-clj.util.exception
  (:require
   [clojure.string :as string]))

(defn unique-key-exception? [e]
  (string/starts-with? e "Unique index or primary key violation"))