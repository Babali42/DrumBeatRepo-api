package com.drumbeatrepo.domain

sealed trait DomainError

case object InvalidEmail extends DomainError
case object EmptyEmailSubject extends DomainError
case object EmptyEmailBody extends DomainError