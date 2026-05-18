package com.drumbeatrepo.domain

//TODO : BPM create value object
case class BeatRequest(label: String, bpm: Int, genre: Option[String], contributor: Option[String])

def parseBeatRequest(label: String, bpm: Int, genre: Option[String], contributor: Option[String]): Either[DomainError, BeatRequest] =
  for {
    label <- parseLabel(label)
    bpm <- parseBPM(bpm)
  } yield BeatRequest(label, bpm, genre, contributor)

def parseLabel(str: String): Either[DomainError, String] = {
  val s = str.trim
  Either.cond(s.nonEmpty, s, EmptyBeatLabel)
}

def parseBPM(value: Int): Either[DomainError, Int] = {
  if(value > 30 && value < 300)
    return Right(value)
  Left(InvalidBPM)
}