#What's this?
Collection of checkers to test servers/services/etc. through Web API packed in a standalone jar.
Currently HTTP and HTTPS are the supported protocols.

#Requirements
* JDK 8

#How to build it
`gradlew assemble`

#How to use it
##Run with gradle
1. Build and start: `./gradlew run`
2. In your browser: [http://localhost:8080/checkthat?url=https://github.com](http://localhost:8080/checkthat?url=https://github.com) [http://localhost:8080/checkthat?server=github.com](http://localhost:8080/checkthat?server=github.com)

##Build and run it
1. Build: `gradlew installDist`
2. Navigate into app dir: `cd build/install/checkthat`
3. Start: `bin/checkthat`
4. In your browser: [http://localhost:8080/checkthat?url=https://github.com](http://localhost:8080/checkthat?url=https://github.com) [http://localhost:8080/checkthat?server=github.com](http://localhost:8080/checkthat?server=github.com)
##Specify port
`bin/checkthat --port=9000`
