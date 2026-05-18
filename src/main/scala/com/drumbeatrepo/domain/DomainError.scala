package com.drumbeatrepo.domain

sealed trait DomainError

//Email
case object InvalidEmail extends DomainError
case object EmptyEmailSubject extends DomainError
case object EmptyEmailBody extends DomainError

//BPM
case object InvalidBPM extends DomainError

//BeatRequest
case object EmptyBeatLabel extends DomainError