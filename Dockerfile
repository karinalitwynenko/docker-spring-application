FROM openjdk:jdk-alpine
LABEL maintainer="karina.litwynenko@pollub.edu.pl"

# zainstaluj Mavena
RUN apt-get install maven
ADD .mvn /javaApp.mvn/
ADD mvnw pom.xml /javaApp/
ADD src /javaApp/src/
WORKDIR /javaApp

# zbuduj projekt
RUN mvn clean install
# wystaw port
EXPOSE 8080
CMD ["java", "-cp", "target/*", "com.klitwynenko.shoutbox.ShoutboxApplication"]