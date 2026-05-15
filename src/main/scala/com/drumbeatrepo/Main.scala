package com.drumbeatrepo

import cats.effect.{IO, IOApp}
import cats.implicits.toSemigroupKOps
import com.comcast.ip4s.{ipv4, port}
import com.drumbeatrepo.infrastructure.http.{BeatRoutes, HealthRoutes}
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.Router

object Main extends IOApp.Simple:
  
  override def run: IO[Unit] =
    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(HealthRoutes.all[IO])
      .build
      .use(_ =>
        IO.println("Server started on http://localhost:8080") *> IO.never
      )