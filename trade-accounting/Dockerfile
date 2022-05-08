FROM openjdk:11-jre-slim as runtime

WORKDIR /trade-accounting

ARG version
RUN echo "version=${version}"
COPY --from=accounting-builder:latest build/trade-accounting/target/trade-accounting-${version}.jar  trade-accounting.jar

CMD ["java","-jar","trade-accounting.jar"]