(ns lein-scalac.plugin)

(defn middleware [project]
  (let [scala-version (:scala-version project)]
    (update-in project [:dependencies] concat
               [['lancet "1.0.1"]
                ['org.scala-lang/scala-compiler scala-version]
                ['org.scala-lang/scala-library scala-version]])))

