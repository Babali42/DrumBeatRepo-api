package com.drumbeatrepo.infrastructure.http

import cats.Applicative.ops.toAllApplicativeOps
import cats.effect.Concurrent
import cats.implicits.toFlatMapOps
import org.http4s.*
import org.http4s.dsl.Http4sDsl

object BeatRoutes:

  /*
  def all[F[_]: Concurrent]: HttpApp[F]
  This defines a method that is generic over an effect type F.

  F[_] means F is a type constructor that takes one type parameter — like IO[_], Future[_], etc.
  : Concurrent is a context bound, shorthand for an implicit Concurrent[F] parameter. It means "I need F to have a Concurrent instance", which gives you things like async execution, fibers, and cancellation.
  HttpApp[F] is the return type — essentially a function Request[F] => F[Response[F]].
   */
  def all[F[_] : Concurrent]: HttpApp[F] =
    val dsl = Http4sDsl[F]
    import dsl.*

    val routes = HttpRoutes.of[F]:
      case req@POST -> Root / "beat" =>
        req.decodeWith(EntityDecoder.multipart[F], strict = false) { multipart =>
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

    routes.orNotFound
