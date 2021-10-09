[![Build Status](https://github.com/jonatan-ivanov/checkthat/actions/workflows/gradle.yml/badge.svg)](https://github.com/jonatan-ivanov/checkthat/actions)

# What's this?
Collection of checkers to test servers/services/etc. through Web API packed in a standalone jar. [Check Examples](#online-demo)

# Requirements
* JDK 8

# How to build it
`./gradlew build`

# How to use it
## Run with Gradle
1. Build and start: `./gradlew bootRun`
2. Open your browser: [Check Examples](#local-demo)

## Build and run it
1. Build: `./gradlew assemble`
2. Start: `java -jar build/libs/*.jar`
3. Open your browser: [Check Examples](#local-demo)

# Examples
## Online demo
- Check http(s) endpoint: [https://checkthat.herokuapp.com/checkthat/url/https://github.com](https://checkthat.herokuapp.com/checkthat/url/https://github.com)
- Check open port: [https://checkthat.herokuapp.com/checkthat/url/socket://github.com:80](https://checkthat.herokuapp.com/checkthat/url/socket://github.com:80)
- Check host by ping: [https://checkthat.herokuapp.com/checkthat/server/github.com](https://checkthat.herokuapp.com/checkthat/server/github.com)
- Check open ports (only one port): [https://checkthat.herokuapp.com/checkthat/socket/github.com:80](https://checkthat.herokuapp.com/checkthat/socket/github.com:80)
- Check open ports (multiple ports): [https://checkthat.herokuapp.com/checkthat/sockets/github.com:80..82](https://checkthat.herokuapp.com/checkthat/sockets/github.com:80..82)

## Local demo
- Check http(s) endpoint: [http://localhost:8080/checkthat/url/https://github.com](http://localhost:8080/checkthat/url/https://github.com)
- Check open port: [http://localhost:8080/checkthat/url/socket://github.com:80](http://localhost:8080/checkthat/url/socket://github.com:80)
- Check host by ping: [http://localhost:8080/checkthat/server/github.com](http://localhost:8080/checkthat/server/github.com)
- Check open ports (only one port): [http://localhost:8080/checkthat/socket/github.com:80](http://localhost:8080/checkthat/socket/github.com:80)
- Check open ports (multiple ports): [http://localhost:8080/checkthat/sockets/github.com:80..82](http://localhost:8080/checkthat/sockets/github.com:80..82)

## Specify port
```
./gradlew bootRun -Dport=9000
java -Dport=9000 -jar build/libs/*.jar
```

# Docker
- Build image: `./gradlew bootBuildImage`
- Build image and run: `./gradlew bootDockerRun`
