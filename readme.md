# DrumBeatRepo API (WIP)

## An API to submit track to DrumBeatRepo

Backend API for [DrumBeatRepo](https://github.com/Babali42/DrumBeatRepo).

## Why this api ?

www.drumBeatRepo.com aims to be a **beat library**.
Then it need to be something people can contribute to.

But I did not get any PR to contribute to the beat database for now.
Beat comes from me or friends of mine.

I think it's too hard for musician to git clone and tutti quanti.
Musician likes to do music isn't it ?

My idea here is to create an api which will be connected to a beat form (non existing yet) on www.drumbeatrepo.com
The api will transform it and prepare it for me and send it by email in a `.zip`.

## Why scala ?

I like this language !
As I am a begginer in scala every feedback is welcome.
Especially those about security.

## What is a beat ?

It's mainly a litlle musical pattern which can be represented as a `.mid` file or as a `.json` file.
It can contains samples like `.mp3` or `.wav`.

## Stack

- Scala 3 / Http4s / Cats Effect

## Run locally

```bash
sbt run
```

## Deploy (WIP)

Hosted on Render. Deploys automatically on push to `main`.
