(defproject test-project "0.1.0"
            :description "test project with some scala"
            :dependencies [[org.clojure/clojure "1.5.1"]]
            :scala-version "2.10.1"
            :scala-source-paths ["scala" "scala2"]
            :plugins [[com.duramec/lein-scalac "0.1.1"]]
            :prep-tasks ["scalac"]
            :main test-project.core)
