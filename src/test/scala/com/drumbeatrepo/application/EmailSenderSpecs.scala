package com.drumbeatrepo.application

import cats.effect.testing.scalatest.AsyncIOSpec
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

class EmailSenderSpecs extends AsyncFreeSpec with AsyncIOSpec with Matchers:
  "Email sender" - {
    "should send and succeed" in {
      EmailSender.sendEmail("a@yopmail.com", "super sujet", "super corps").attempt.asserting(_.isRight shouldBe true)
    }

    "should fail for invalid email" in {
      EmailSender.sendEmail("a@yopmailcom", "super sujet", "super corps").attempt.asserting(_.left.map(_.getMessage) shouldEqual Left("Can't throw email"))
    }
  }