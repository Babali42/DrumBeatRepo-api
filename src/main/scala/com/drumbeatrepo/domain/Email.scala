package com.drumbeatrepo.domain

case class Email(to: String, subject: String, body: String)

def parseEmail(
    rawTo: String,
    rawSubject: String,
    rawBody: String
): Either[String, Email] =
  for {
    to <- parseTo(rawTo)
    subject <- parseSubject(rawSubject)
    body <- parseBody(rawBody)
  } yield Email(to, subject, body)

def parseSubject(str: String): Either[String, String] = {
  val s = str.trim
  Either.cond(s.nonEmpty, s, "Email subject is empty")
}

def parseBody(str: String): Either[String, String] = {
  val s = str.trim
  Either.cond(s.nonEmpty, s, "Email body is empty")
}

def parseTo(rawTo: String): Either[String, String] = {
  val s = rawTo.trim
  Either.cond(isValid(s), s, "Email to is invalid")
}

def isValid(email: String): Boolean = {
  val emailRegex =
    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".r

  emailRegex.matches(email)
}
