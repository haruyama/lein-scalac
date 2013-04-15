# lein-scalac

Compile Scala source from Leiningen.

## Usage

Add `[com.duramec/lein-scalac "0.1.1"]` to `:plugins` project.clj.

Set `:scala-source-paths` in project.clj and place your `.scala`
source files in those directories.

Run `lein scalac` to compile them to `.class` files.

If you want `scalac` to run automatically, add `:prep-tasks ["scalac"]`
to `project.clj`

## License

Copyright © 2013 Aaron Bernard, Tom Wanielista, Phil Hagelberg and Scott Clasen

Distributed under the Eclipse Public License, the same as Clojure.
