package com.drumbeatrepo.infrastructure.http

import cats.effect.IO
import cats.effect.std.Env
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

    test("GET /health should log email env var") {
      val testEnv = Map("EMAIL" -> "terminator@T800.com")

      val envInstance: Env[IO] = new Env[IO]:
        def get(name: String): IO[Option[String]] = IO.pure(testEnv.get(name))
        def entries: IO[Map[String, String]] = IO.pure(testEnv)

      for
        logger <- IO.pure(TestingLogger.impl[IO]())
        given Logger[IO] = logger
        given Env[IO] = envInstance
        request = Request[IO](Method.GET, uri"/health")
        response <- HealthRoutes.all[IO].run(request)
        logs <- logger.logged
      yield
        assert(response.status == Status.Ok)
        assert(logs.exists(_.message.contains("terminator@T800.com")))
    }
