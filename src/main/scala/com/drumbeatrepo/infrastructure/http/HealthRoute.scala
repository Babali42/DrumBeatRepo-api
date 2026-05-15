package com.drumbeatrepo.infrastructure.http

import cats.effect.Concurrent
import org.http4s.*
import org.http4s.dsl.Http4sDsl

object HealthRoutes:
  def all[F[_] : Concurrent]: HttpApp[F] =
    val dsl = Http4sDsl[F]
    import dsl.*

    val routes: HttpRoutes[F] = HttpRoutes.of[F]:
      case GET -> Root / "health" => Ok("ok")

    routes.orNotFound
