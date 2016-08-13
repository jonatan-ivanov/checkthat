FROM openjdk:8

COPY build/libs/*.jar /opt/checkthat/
WORKDIR /opt/checkthat/
CMD java -jar *.jar
