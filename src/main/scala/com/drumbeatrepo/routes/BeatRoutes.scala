package com.drumbeatrepo.routes

import cats.effect.IO
import org.http4s.*
import org.http4s.dsl.io.*

object BeatRoutes:
  val routes: HttpRoutes[IO] = HttpRoutes.of[IO]:
    case req@POST -> Root / "beat" =>
      req.decodeWith(EntityDecoder.multipart[IO], strict = false) { multipart =>
        def field(name: String) =
          multipart.parts.find(_.name == Some(name)).map(_.bodyText.compile.string)

        (field("label"), field("genre"), field("bpm")) match
          case (Some(label), Some(genre), Some(bpm)) =>
            for
              l <- label
              g <- genre
              b <- bpm
              r <- Ok(s"""{"label":"$l","genre":"$g","bpm":"$b"}""")
            yield r
          case _ => BadRequest("missing fields")
      }