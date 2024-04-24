./gradlew clean build &&
docker rmi minimartapi:1.0 &&
docker compose up -d --build &&
docker logs mini-mart-api -f