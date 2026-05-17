package com.drumbeatrepo.infrastructure.http

import cats.Applicative.ops.toAllApplicativeOps
import cats.FlatMap.nonInheritedOps.toFlatMapOps
import cats.effect.Concurrent
import cats.effect.std.Env
import org.http4s.*
import org.http4s.dsl.Http4sDsl
import org.typelevel.log4cats.Logger

object HealthRoutes:
  def all[F[_] : {Concurrent, Logger, Env}]: HttpApp[F] =
    val dsl = Http4sDsl[F]
    import dsl.*

    val routes: HttpRoutes[F] = HttpRoutes.of[F]:
      case GET -> Root / "health" => {
        for {
          _ <- Logger[F].info(s"Server is alive !")
          email <- Env[F].get("EMAIL")
          _ <- Logger[F].info(s"The email is [${email}]")
          r <- Ok("ok")
        } yield r
      }

    routes.orNotFound
