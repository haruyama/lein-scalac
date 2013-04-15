(ns leiningen.scalac
  (:require [leiningen.classpath :as classpath]
            [leiningen.core.main :as lein]
            [leiningen.core.eval :as eval]))

(defn- task-props
  [project]
  (merge {:destdir (:compile-path project)}
         (:scalac-options project)))

(defn- scalac-form
  [project classpath]
  `(do
     (import scala.tools.ant.Scalac)
     (import org.apache.tools.ant.types.Path)
     (.addTaskDefinition lancet/ant-project "scalac" scala.tools.ant.Scalac)
     (lancet/define-ant-task ~'ant-scalac ~'scalac)
     (let [props# ~(task-props project)
           classpath# (Path. lancet/ant-project ~classpath)
           task# (doto (lancet/instantiate-task lancet/ant-project "scalac" props#)
                   (.setClasspath classpath#)
                   (.setIncludes "**/*.scala"))]
       (doseq [p# ~(into [] (:scala-source-paths project))] 
         (.setSrcdir task# (Path. lancet/ant-project p#)))
       (lancet/mkdir {:dir ~(:compile-path project)})
       (.execute task#))))

(defn scalac
  "Compile Scala source in :scala-source-paths to :compile-path.

  Set :scalac-options in project.clj to pass options to the Scala compiler.
  See http://www.scala-lang.org/node/98 for details."
  [project]
  (if (not (:scala-version project))
    (lein/abort "lein scalac: You must specify a :scala-version key in your project.clj"))
  (if (not (:scala-source-paths project))
    (lein/abort "lein scalac: You must specify :scala-source-paths [] in your project.clj"))
  (let [classpath (classpath/get-classpath-string project)
        eval-form (scalac-form project classpath)
        eval-ns '(require '[lancet.core :as lancet])]
    ; last argument addresses http://technomancy.us/143
    (eval/eval-in-project project eval-form eval-ns)))

