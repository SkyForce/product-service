FROM eclipse-temurin:17-jre-focal

WORKDIR /product-service
ARG JAR_PATH
COPY ${JAR_PATH} product-service.jar

ENTRYPOINT ["java", "-jar", "/product-service/product-service.jar"]