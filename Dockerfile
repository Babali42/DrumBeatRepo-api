FROM sbtscala/scala-sbt:graalvm-community-21.0.2_1.12.8_3.8.2 AS build

WORKDIR /app
COPY project/ project/
COPY build.sbt .
RUN sbt update

COPY src/ src/
RUN sbt stage

FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=build /app/target/universal/stage/ .

ENV PORT=80
EXPOSE 80

CMD ["bin/api"]
