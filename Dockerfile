FROM eclipse-temurin:17 as build

WORKDIR /app
COPY . .

RUN <<EOF
./gradlew bootJar
mv build/libs/*.jar app.jar
EOF

# 여기부터 새로운 Stage
FROM eclipse-temurin:17-jre

WORKDIR /app
# build stage에 만들었던 app.jar를 복사해온다.
COPY --from=build /app/app.jar .

CMD ["java", "-jar", "app.jar"]
EXPOSE 8080