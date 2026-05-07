package com.drumbeatrepo.routes.routes

import cats.effect.IO
import org.http4s.*
import org.http4s.dsl.io.*
import org.http4s.multipart.*

object BeatRoutes:
  val routes: HttpRoutes[IO] = HttpRoutes.of[IO]:
    case req @ POST -> Root / "beat" =>
      req.decodeWith(EntityDecoder.multipart[IO], strict = false) { multipart =>
        Ok()
      }