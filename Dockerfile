FROM openjdk:17-alpine
RUN mkdir -p minimartapi
COPY ./build/libs/*.jar /minimartapi/minimartapi.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=stage","/minimartapi/minimartapi.jar"]

