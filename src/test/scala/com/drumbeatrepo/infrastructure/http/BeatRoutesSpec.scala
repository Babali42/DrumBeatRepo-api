package com.drumbeatrepo.infrastructure.http

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import org.http4s.*
import org.http4s.implicits.*
import org.http4s.multipart.*
import org.scalatest.funsuite.AsyncFunSuite
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.testing.TestingLogger

class BeatRoutesSpec extends AsyncFunSuite with AsyncIOSpec:

  test("POST /beat should work with label, bpm, genre and contributor") {
    val multipart = Multipart[IO](
      Vector(
        Part.formData[IO]("label", "Gabber"),
        Part.formData[IO]("genre", "Hardcore techno"),
        Part.formData[IO]("bpm",   "170"),
        Part.formData[IO]("contributor", "fakeContributor@fakeCompany.com"),
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
      assert(logs.exists(_.message.contains("fakeContributor@fakeCompany.com")))
  }

  test("POST /beat should work with label, bpm, genre (no contributor)") {
    val multipart = Multipart[IO](
      Vector(
        Part.formData[IO]("label", "Gabber"),
        Part.formData[IO]("genre", "Hardcore techno"),
        Part.formData[IO]("bpm", "170")
      )
    )

    for
      logger <- IO.pure(TestingLogger.impl[IO]())
      given Logger[IO] = logger
      request = Request[IO](Method.POST, uri"/beat")
        .withEntity(multipart)
        .withHeaders(multipart.headers)
      response <- BeatRoutes.all[IO].run(request)
      body <- response.as[String]
      logs <- logger.logged
    yield
      assert(response.status == Status.Ok)
      assert(body.contains("Gabber"))
      assert(body.contains("Hardcore techno"))
      assert(body.contains("170"))
      assert(logs.exists(_.message.contains("processing beat: label=Gabber")))
  }

  test("POST /beat should work with label, bpm (no contributor, no genre)") {
    val multipart = Multipart[IO](
      Vector(
        Part.formData[IO]("label", "Gabber"),
        Part.formData[IO]("bpm", "170")
      )
    )

    for
      logger <- IO.pure(TestingLogger.impl[IO]())
      given Logger[IO] = logger
      request = Request[IO](Method.POST, uri"/beat")
        .withEntity(multipart)
        .withHeaders(multipart.headers)
      response <- BeatRoutes.all[IO].run(request)
      body <- response.as[String]
      logs <- logger.logged
    yield
      assert(response.status == Status.Ok)
      assert(body.contains("Gabber"))
      assert(body.contains("170"))
      assert(logs.exists(_.message.contains("processing beat: label=Gabber")))
  }

  test("POST /beat should fail when fields are missing") {
    for
      logger   <- IO.pure(TestingLogger.impl[IO]())
      given Logger[IO] = logger
      request <- IO.pure(Request[IO](Method.POST, uri"/beat"))
      response <- BeatRoutes.all[IO].run(request)
      body <- response.as[String]
    yield
      assert(response.status == Status.UnprocessableContent)
  }
