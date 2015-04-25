(defproject mysqldump-extend "0.1.0-SNAPSHOT"
  :description "turns your version control friendly mysqldumps into an extended-insert style script"
  :url "http://github.com/it0a"
  :license {:name "MIT License"}
  :main mysqldump-extend.core
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/tools.cli "0.3.1"]]
  :plugins [[lein-bin "0.3.4"]]
  :bin {:name "mysqldump-extend"})
