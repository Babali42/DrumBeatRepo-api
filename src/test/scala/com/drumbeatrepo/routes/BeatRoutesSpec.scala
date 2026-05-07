package com.drumbeatrepo.routes

import cats.effect.IO
import com.drumbeatrepo.routes.routes.BeatRoutes
import munit.CatsEffectSuite
import org.http4s.*
import org.http4s.implicits.*
import org.http4s.multipart.*

class BeatRoutesSpec extends CatsEffectSuite:

  test("POST /issue with multipart form returns 200") {
    val multipart = Multipart[IO](
      Vector(
        Part.formData[IO]("title", "My Beat"),
        Part.formData[IO]("genre", "Funk"),
        Part.formData[IO]("bpm",   "120")
      )
    )

    val request = Request[IO](Method.POST, uri"/beat")
      .withEntity(multipart)
      .withHeaders(multipart.headers)

    val response = BeatRoutes.routes.orNotFound.run(request)

    response.map(r => assertEquals(r.status, Status.Ok))
  }