(ns leiningen.scalac
  (:use [leiningen.core.eval :only [eval-in-project]])
  (:require [leiningen.classpath :as classpath]
            [leiningen.core.main :as lein]))

(defn- task-props
  [project]
  (let [{:keys [compile-path scalac-options]} project]
    (merge {:destdir compile-path} scalac-options)))


(def init-form
  '(do
     (import scala.tools.ant.Scalac)
     (import org.apache.tools.ant.types.Path)
     (require '[lancet.core :as lancet])))

(defn- make-scalac-form
  [project classpath]
  `(do
     (.addTaskDefinition lancet/ant-project "scalac" scala.tools.ant.Scalac)
     (lancet/define-ant-task ~'ant-scalac ~'scalac)
     (let [props# ~(task-props project)
           classpath# (Path. lancet/ant-project ~classpath)
           task# (doto 
                   (lancet/instantiate-task lancet/ant-project "scalac" props#)
                   (.setClasspath classpath#)
                   (.setIncludes "**/*.scala"))]
       (doseq [path# ~(into [] (:scala-source-paths project))] 
         (.setSrcdir task# (Path. lancet/ant-project path#)))
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
        scalac-form (make-scalac-form project classpath)]
    (eval-in-project project scalac-form init-form)))

