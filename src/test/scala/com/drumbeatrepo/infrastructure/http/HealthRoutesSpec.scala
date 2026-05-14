package com.drumbeatrepo.infrastructure.http

import cats.effect.IO
import munit.CatsEffectSuite
import org.http4s.*
import org.http4s.implicits.*

class HealthRoutesSpec extends CatsEffectSuite {

    test("GET /health returns 200") {
        val request  = Request[IO](Method.GET, uri"/health")
        val response = HealthRoutes.routes.orNotFound.run(request)
        response.map(r => assertEquals(r.status, Status.Ok))
    }
}