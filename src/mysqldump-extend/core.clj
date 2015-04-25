(ns mysqldump-extend.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn match-insert-into
  [line]
  (.startsWith line "INSERT INTO"))

(defn remove-last-char
  [line]
  (subs line 0 (- (count line) 1)))

(defn extract-values-from-query
  [query]
  (remove-last-char
   (apply str (second (split-at 4 (str/split query #" "))))))

(defn extract-query-header
  [query]
  (str/join " " (first (split-at 4 (str/split query #" ")))))

(defn process-values
  [lines]
  (str (str/join "," (map extract-values-from-query lines))
       ";"))

(defn process-queries
  [lines]
  (str (extract-query-header (first insertIntoLines))
    " "
    (process-values insertIntoLines)
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
  [filename]
  (let [lines (extract-lines filename)]
    (str (extract-preamble (extract-non-insert-lines lines))
         "\n"
         (process-queries (extract-insert-lines lines))
         "\n"
         (extract-postamble (extract-non-insert-lines lines)))))

(spit "/home/it0a/sample-extended.txt" (process-script "/home/it0a/sample.txt"))
