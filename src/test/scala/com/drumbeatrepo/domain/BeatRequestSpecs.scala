package com.drumbeatrepo.domain

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class BeatRequestSpecs extends AnyFunSuite with Matchers :
  test("Should not create an beat request with a too high tempo (high)") {
    parseBeatRequest("Techno from Berlin", 1000, None, None) shouldBe Left(InvalidBPM)
  }

  test("Should not create an beat request with a too high tempo (low)") {
    parseBeatRequest("Techno from Berlin", 10, None, None) shouldBe Left(InvalidBPM)
  }

  test("Should not create an beat request with an empty label") {
    parseBeatRequest("", 100, None, None) shouldBe Left(EmptyBeatLabel)
  }