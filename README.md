#What's this?
Collection of checkers to test servers/services/etc. through Web API packed in a standalone jar. [Check Examples](#examples)

#Requirements
* JDK 8

#How to build it
`gradlew build`

#How to use it
##Run with gradle
1. Build and start: `./gradlew bootRun`
2. Open your browser: [Check Examples](#examples)

##Build and run it
1. Build: `gradlew installDist`
2. Navigate into app dir: `cd build/install/checkthat`
3. Start: `bin/checkthat`
4. Open your browser: [Check Examples](#examples)

#Examples
- Check http(s) endpoint: [http://localhost:8080/checkthat/url/https://github.com](http://localhost:8080/checkthat/url/https://github.com)
- Check open port: [http://localhost:8080/checkthat/url/socket://github.com:80](http://localhost:8080/checkthat/url/socket://github.com:80)
- Check host by ping: [http://localhost:8080/checkthat/server/github.com](http://localhost:8080/checkthat/server/github.com)
- Check open ports (only one port): [http://localhost:8080/checkthat/socket/github.com:80](http://localhost:8080/checkthat/socket/github.com:80)
- Check open ports (multiple ports): [http://localhost:8080/checkthat/socket/github.com:80..82](http://localhost:8080/checkthat/socket/github.com:80..82)

##Specify port
```
./gradlew bootRun -Dport=9000
bin/checkthat --port=9000
```

#Deploy
create dist: `./gradlew installDist`  
run: `./build/install/checkthat/bin/checkthat`

or

create dist: `./gradlew installDist`  
unpack `.tar` or `.zip` from `build/distributions` to your target dir  
run: `bin/checkthat`
