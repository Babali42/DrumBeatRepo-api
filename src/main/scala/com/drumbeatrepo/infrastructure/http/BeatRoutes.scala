package com.drumbeatrepo.infrastructure.http

import cats.effect.Concurrent
import cats.syntax.all.*
import org.http4s.*
import org.http4s.dsl.Http4sDsl
import org.typelevel.log4cats.Logger

object BeatRoutes:

  /*
  def all[F[_]: Concurrent]: HttpApp[F]
  This defines a method that is generic over an effect type F.

  F[_] means F is a type constructor that takes one type parameter — like IO[_], Future[_], etc.
  : Concurrent is a context bound, shorthand for an implicit Concurrent[F] parameter. It means "I need F to have a Concurrent instance", which gives you things like async execution, fibers, and cancellation.
  HttpApp[F] is the return type — essentially a function Request[F] => F[Response[F]].
   */
  def all[F[_] : {Concurrent, Logger}]: HttpApp[F] =
    val dsl = Http4sDsl[F]
    import dsl.*

    val routes = HttpRoutes.of[F]:
      case req@POST -> Root / "beat" =>
        req.decodeWith(EntityDecoder.multipart[F], strict = false) { multipart =>
          val parts = multipart.parts
          val label = parts.find(_.name == Some("label")).map(_.bodyText.compile.string)
          val genre = parts.find(_.name == Some("genre")).map(_.bodyText.compile.string)
          val bpm   = parts.find(_.name == Some("bpm")).map(_.bodyText.compile.string)

          (label, genre, bpm) match
            case (Some(l), Some(g), Some(b)) =>
              for
                labelStr <- l
                genreStr <- g
                bpmStr   <- b
                _ <- Logger[F].info(s"processing beat: label=$labelStr, genre=$genreStr, bpm=$bpmStr")
                r <- Ok(s"""{"label":"$labelStr","genre":"$genreStr","bpm":"$bpmStr"}""")
              yield r
            case _ => BadRequest("missing fields")
        }

    routes.orNotFound
