FROM openjdk:jdk-alpine
LABEL maintainer="karina.litwynenko@pollub.edu.pl"

# zainstaluj Mavena
RUN apk add --update maven
ADD .mvn /javaApp.mvn/
ADD mvnw pom.xml /javaApp/
ADD src /javaApp/src/
WORKDIR /javaApp/

# zbuduj projekt
RUN mvn clean install -DskipTests
# wystaw port
EXPOSE 8080
CMD ["java", "-jar", "target/shoutbox-0.0.1-SNAPSHOT.jar"]
