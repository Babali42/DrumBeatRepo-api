package com.drumbeatrepo.infrastructure.http

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import org.http4s.*
import org.http4s.implicits.*
import org.scalatest.funsuite.AsyncFunSuite
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.testing.TestingLogger

class HealthRoutesSpec extends AsyncFunSuite with AsyncIOSpec:

    test("GET /health returns 200") {
      for
        logger <- IO.pure(TestingLogger.impl[IO]())
        given Logger[IO] = logger
        request = Request[IO](Method.GET, uri"/health")
        response <- HealthRoutes.all[IO].run(request)
        logs <- logger.logged
      yield
        assert(response.status == Status.Ok)
        assert(logs.exists(_.message.contains("Server is alive !")))
    }
