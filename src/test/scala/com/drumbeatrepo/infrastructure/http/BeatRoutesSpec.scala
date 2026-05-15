package com.drumbeatrepo.infrastructure.http

import cats.effect.IO
import org.http4s.*
import org.http4s.implicits.*
import org.http4s.multipart.*
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class BeatRoutesSpec extends AnyFunSuite with Matchers :

  val multipart = Multipart[IO](
    Vector(
      Part.formData[IO]("label", "Gabber"),
      Part.formData[IO]("genre", "Hardcore techno"),
      Part.formData[IO]("bpm",   "170")
    )
  )

  test("POST /beat receives all fields correctly") {
    for
      request  <- IO.pure(Request[IO](Method.POST, uri"/beat")
        .withEntity(multipart)
        .withHeaders(multipart.headers))
      response <- BeatRoutes.all[IO].run(request)
      body     <- response.as[String]
    yield
      response.status shouldBe Status.Ok
      assert(body.contains("Gabber"))
      assert(body.contains("Hardcore techno"))
      assert(body.contains("170"))
  }