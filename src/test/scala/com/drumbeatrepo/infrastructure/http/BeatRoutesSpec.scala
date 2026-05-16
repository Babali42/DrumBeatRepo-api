package com.drumbeatrepo.infrastructure.http

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import org.http4s.*
import org.http4s.implicits.*
import org.http4s.multipart.*
import org.scalatest.funsuite.AsyncFunSuite
import org.typelevel.log4cats.testing.TestingLogger
import org.typelevel.log4cats.Logger

class BeatRoutesSpec extends AsyncFunSuite with AsyncIOSpec:

  test("POST /beat receives all fields correctly") {
    val multipart = Multipart[IO](
      Vector(
        Part.formData[IO]("label", "Gabber"),
        Part.formData[IO]("genre", "Hardcore techno"),
        Part.formData[IO]("bpm",   "170")
      )
    )

    for
      logger   <- IO.pure(TestingLogger.impl[IO]())
      given Logger[IO] = logger
      request  = Request[IO](Method.POST, uri"/beat")
        .withEntity(multipart)
        .withHeaders(multipart.headers)
      response <- BeatRoutes.all[IO].run(request)
      body     <- response.as[String]
      logs     <- logger.logged
    yield
      assert(response.status == Status.Ok)
      assert(body.contains("Gabber"))
      assert(body.contains("Hardcore techno"))
      assert(body.contains("170"))
      assert(logs.exists(_.message.contains("processing beat: label=Gabber")))
  }
