(defproject test-project "0.1.0"
            :description "test project with some scala"
            :dependencies [[org.clojure/clojure "1.5.1"]
                           [org.scala-lang/scala-library "2.9.1"]]
            :scala-source-path "scala"
            :plugins [[com.duramec/lein-scalac "0.1.1"]]
            :prep-tasks ["scalac"]
            :main test-project.core)
