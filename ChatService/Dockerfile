# 1. официальный образ с JDK
FROM openjdk:17-jdk-alpine

# 2. Указываем рабочую директорию
WORKDIR /app

# 3. Копируем jar-файл (собран заранее)
COPY target/*.jar app.jar

# 4. Открываем порт (необязательно)
EXPOSE 8080

# 5. Запуск
ENTRYPOINT ["java", "-jar", "app.jar"]
