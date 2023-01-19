FROM openjdk:8-alpine

COPY target/uberjar/levelup-clj.jar /levelup-clj/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/levelup-clj/app.jar"]
