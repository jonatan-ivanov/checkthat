#What's this?
Collection of checkers to test servers/services/etc. through Web API packed in a standalone jar.
Currently HTTP and HTTPS are the supported protocols.

#Requirements
* JDK 8

#How to build it
`gradlew assemble`

#How to use it
##Run through gradle
1. Build and start: `./gradlew run`
2. Open it in your browser: [http://localhost:8080/checkthat/?url=https://github.com](http://localhost:8080/checkthat/?url=https://github.com)

##Build and run it
1. Build: `gradlew installDist`
2. Navigate into app dir: `cd build/install/checkthat`
2. Start: `bin/checkthat`

##Specify port
`bin/checkthat --port=9000`