package com.drumbeatrepo.infrastructure.http

import cats.effect.IO
import org.http4s.*
import org.http4s.implicits.*
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class HealthRoutesSpec extends AnyFunSuite with Matchers :
    
    test("GET /health returns 200") {
        val request  = Request[IO](Method.GET, uri"/health")
        val response = HealthRoutes.routes.orNotFound.run(request)
        response.map(r => r.status shouldBe Status.Ok)
    }