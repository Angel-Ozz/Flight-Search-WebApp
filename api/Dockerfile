#recommended image 
FROM gradle:8.4-jdk21 AS build

# generic workdir
WORKDIR /app

#config files necessary for build
COPY gradle gradle
COPY gradlew build.gradle settings.gradle ./

# give wrapper of gradle (downlowads and invoques a version of gradle) ex permissions
RUN chmod +x ./gradlew

#download gradle dependencies without runnin
RUN ./gradlew dependencies --no-daemon

#copy src code
COPY src src

#build spring boot app
RUN ./gradlew bootJar --no-daemon

#Execute parte
FROM openjdk:21-jdk-slim AS runtime

WORKDIR /app

#copy jar generated before
COPY --from=build /app/build/libs/*.jar app.jar


COPY .env /app/.env

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
