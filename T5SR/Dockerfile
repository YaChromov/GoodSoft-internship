
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app


ARG MAVEN_VERSION=4.0.0-beta-3
RUN apt-get update && apt-get install -y curl tar \
    && curl -fsSL https://archive.apache.org/dist/maven/maven-4/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz | tar xz -C /opt \
    && ln -s /opt/apache-maven-${MAVEN_VERSION}/bin/mvn /usr/bin/mvn


COPY pom.xml .
RUN mvn dependency:go-offline


COPY src ./src
RUN mvn clean package -DskipTests


FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.war app.war

EXPOSE 8080


ENTRYPOINT ["java", "-jar", "app.war"]