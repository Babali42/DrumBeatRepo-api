package com.drumbeatrepo.domain

case class Email(to: String, subject: String, body: String)

def parseEmail(
    rawTo: String,
    rawSubject: String,
    rawBody: String
): Either[DomainError, Email] =
  for {
    to <- parseTo(rawTo)
    subject <- parseSubject(rawSubject)
    body <- parseBody(rawBody)
  } yield Email(to, subject, body)

def parseSubject(str: String): Either[DomainError, String] = {
  val s = str.trim
  Either.cond(s.nonEmpty, s, EmptyEmailSubject)
}

def parseBody(str: String): Either[DomainError, String] = {
  val s = str.trim
  Either.cond(s.nonEmpty, s, EmptyEmailBody)
}

def parseTo(rawTo: String): Either[DomainError, String] = {
  val s = rawTo.trim
  Either.cond(isValid(s), s, InvalidEmail)
}

def isValid(email: String): Boolean = {
  val emailRegex =
    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".r

  emailRegex.matches(email)
}
