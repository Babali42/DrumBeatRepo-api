package com.drumbeatrepo.domain

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class EmailSpecs extends AnyFunSuite with Matchers :
  
  test("Should not create an email with empty to") {
    parseEmail("", "t", "testBody") shouldBe Left("Email to is invalid")
  }

  test("Should not create an invalid email") {
    parseEmail("a@a", "t", "testBody") shouldBe Left("Email to is invalid")
  }

  test("Should not create an email with empty body") {
    parseEmail("a@test.com", "Super sujet", "") shouldBe Left("Email body is empty")
  }

  test("Should not create an email with empty subject") {
    parseEmail("a@test.com", "", "Super body") shouldBe Left("Email subject is empty")
  }