package com.drumbeatrepo.application

import cats.effect.IO
import com.drumbeatrepo.domain.parseEmail

object EmailSender {
  def sendEmail(to: String, subject: String, body: String): IO[Either[String, Unit]] = {
    val value = parseEmail(to, subject, body)
    if(value.isRight)
      IO.pure(Right(()))
    else
      IO.raiseError(Error("Can't throw email"))
  }
}
