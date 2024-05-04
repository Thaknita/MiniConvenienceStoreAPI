./gradlew clean build &&
docker compose up -d --build &&
docker logs mini-mart-api -f
