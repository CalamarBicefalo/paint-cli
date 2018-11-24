# CLI Paint

Paint is a CLI drawing tool for artists who become creative when they have limited resources available. It is also a silly app that was fun to code.

## Getting started

Paint is written in Kotlin.

In order to run Paint you need to have java > 1.8 installed.

If you are in a UNIX based machine execute:

`./run.sh`

If not you will need to build the project and run it yourself, a gradle wrapper is provided
for your convenience:

```
gradlew build
java -jar build/libs/paint.jar
```

## Libraries

The production code does not use any libraries beyond the kotlin Standard Library. For testing there are a few
dependencies:

- The spring boot testing utils: (to get assertJ and JUnit)
- Awaitility: To write async assertions
- Kotlin coroutines to start Paint in a different thread following kotlin idiomatic constructs
- A tiny JUnit library to facilitaty IO testing through rules

## Design decisions / Comments:

- The software has been TDDed from the very first line
- Every commit should add some value and should be 'production ready'. I.e. compilable and runnable on its own
- The canvas uses interface segregation to separate concerns. If it keeps growing it could make sense
  to split drawing and rendering as different classes.
- I tried not to overengineer things e.g. Renderable as an interface returns a String for the first iteration, perhaps
  a second iteration requiring a true renderer could be parameterize, but that is out of the scope.
- I am taking advantage of Kotlin files to avoid creating too many files. As opposed to a more traditional
  java package structured approach.
- Validation of different aspects (e.g. commands syntax, shapes outside of the canvas...) is violating the SRP but for the sake of the exercise it felt good enough.
  If validation got more complicated or it kept growing it could make sense to split that
- There is an E2E user journey exercising the app from the outside, it is worth having a look.
- If the number of drawing operations kept growing perhaps it could make more sense to create an Operator interface
  and one class per operation, taking a Canvas as an argument. This is something I would typically discuss with a colleague while pairing.
- The exercise was good fun, although it took me around 6 hours to get completed properly.
- Most classes implement in a way or another equals / hashCode / toString to facilitate Testing and future PROD logging
- The canvas mutates! A better implementation should use pure functions and an immutable canvas
  but since concurrency is not a concern for the first iteration mutability is acceptable.

Have fun artist!
