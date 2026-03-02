# ==========================================
# Этап 1: Сборка (Maven 4.0 + Java 25)
# ==========================================
# Используем базовый образ с Java 25
FROM eclipse-temurin:25-jdk AS builder

# Устанавливаем Maven 4.0 (скачиваем напрямую, если нет готового образа)
ARG MAVEN_VERSION=4.0.0-beta-3
RUN apt-get update && apt-get install -y curl tar \
    && curl -fsSL https://archive.apache.org/dist/maven/maven-4/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz | tar xz -C /opt \
    && ln -s /opt/apache-maven-${MAVEN_VERSION}/bin/mvn /usr/bin/mvn

WORKDIR /app

# Копируем настройки проекта
COPY pom.xml .
# В Maven 4 команда остается прежней для загрузки зависимостей
RUN mvn dependency:go-offline

# Сборка приложения
COPY src ./src
RUN mvn clean package -DskipTests

# ==========================================
# Этап 2: Финальный образ (Tomcat 11 + Java 25)
# ==========================================
FROM tomcat:11.0-jdk25

# Стандартная "очистка" и установка
RUN rm -rf /usr/local/tomcat/webapps/*

# Копируем результат сборки.
# В Maven 4 путь к таргету остается стандартным: target/*.war
COPY --from=builder /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]