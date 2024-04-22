FROM openjdk:21-jdk-slim
WORKDIR /app
COPY /bot/target/bot.jar .
EXPOSE 8090
CMD java -jar /app/bot.jar
