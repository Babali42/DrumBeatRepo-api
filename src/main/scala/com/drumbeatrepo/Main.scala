package com.drumbeatrepo

import cats.effect.{IO, IOApp}
import com.comcast.ip4s.{ipv4, port, Port}
import com.drumbeatrepo.infrastructure.http.HealthRoutes
import org.http4s.ember.server.EmberServerBuilder
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger

object Main extends IOApp.Simple:
  given Logger[IO] = Slf4jLogger.getLogger[IO]
  
  override def run: IO[Unit] =
    val httpPort = sys.env.get("PORT").flatMap(Port.fromString).getOrElse(port"8080")

    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(httpPort)
      .withHttpApp(HealthRoutes.all[IO])
      .build
      .use(_ =>
        IO.println(s"Server started on http://localhost:${httpPort.value}") *> IO.never
      )