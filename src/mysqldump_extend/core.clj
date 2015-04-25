(ns mysqldump-extend.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.tools.cli :refer [parse-opts]])
  (:gen-class))

(defn match-insert-into
  [line]
  (.startsWith line "INSERT INTO"))

(defn remove-last-char
  [line]
  (subs line 0 (- (count line) 1)))

(defn extract-values-from-query
  [query]
  (remove-last-char
   (apply str (second (str/split query #"INSERT INTO.*VALUES ")))))

(defn extract-query-header
  [query]
  (if (nil? query)
    ""
    (str/join " " (first (split-at 4 (str/split query #" "))))))

(defn process-values
  [lines]
  (if (empty? lines)
    ""
    (str (str/join "," (map extract-values-from-query lines))
         ";")))

(defn process-queries
  [lines]
  (str (extract-query-header (first lines))
       " "
       (process-values lines)
       "\n"))

(defn extract-lines
  [filename]
  (with-open [rdr (io/reader filename)]
    (doall
     (line-seq rdr))))

(defn split-preamble-postamble
  [lines]
  (split-at
   (inc (.indexOf lines (first (filter #(.contains % "DISABLE KEYS") lines)))) lines))

(defn extract-preamble
  [lines]
  (str/join "\n"
            (first (split-preamble-postamble lines))))

(defn extract-postamble
  [lines]
  (str/join "\n"
            (second (split-preamble-postamble lines))))

(defn extract-non-insert-lines
  [lines]
  (filter #(not (match-insert-into %)) lines))

(defn extract-insert-lines
  [lines]
  (filter match-insert-into lines))

(defn process-script
  [lines]
  (str (extract-preamble (extract-non-insert-lines lines))
       "\n"
       (process-queries (extract-insert-lines lines))
       "\n"
       (extract-postamble (extract-non-insert-lines lines))))

(defn process-script-file
  [filename]
  (let [lines (extract-lines filename)]
    (process-script lines)))

(def cli-options [])

(defn -main [& args]
  (println
   (str/join "\n"
             (map process-script-file
                  ((parse-opts args cli-options) :arguments)))))
