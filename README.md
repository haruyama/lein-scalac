# lein-scalac

Compile Scala source from Leiningen.

## Usage

Add `[lein-scalac "0.1.1"]` to `:plugins` project.clj.

Set `:scala-source-path` in project.clj, usually to "src/scala", and
place your `.scala` source files in there.

Run `lein scalac` to compile them to `.class` files.

If you want `scalac` to run automatically, add `:prep-tasks ["scalac"]`
to `project.clj`

If you need runtime features of Scala you'll have to declare a
dependency on `scala-library` like so:

`:dependencies [org.scala-lang/scala-library "2.9.1"]`

## License

Copyright © 2013 Aaron Bernard, Phil Hagelberg and Scott Clasen

Distributed under the Eclipse Public License, the same as Clojure.
