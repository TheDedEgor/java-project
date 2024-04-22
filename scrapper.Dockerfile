FROM openjdk:21-jdk-slim
WORKDIR /app
COPY /scrapper/target/scrapper.jar .
EXPOSE 8080
CMD java -jar /app/scrapper.jar
