# Maestro

A Zero-Trust session orchestrator written in Scala.

## Features

As of now, Maestro includes the following features:

- Access and Refresh tokens generation
- Token validation with cached UUIDs
- Fingerprint and expiration validation
- Token revocation
- Integrated Redis database
- Runnable as a microservice

Other features that makes Maestro
easy to develop and mantain include its
DDD Architecture, aswell as a strict
following of the SOLID principles, which
makes this project highly modular.

## Disclaimer

Maestro does not take care of the entire
user authorization process, but only of the
session management part. This means, Maestro
is supposed to be run as a microservice along
with other authentication API, and should
be called directly from an application's backend.

Maestro also requires the backend to extract a 
fingerprint of the user's device, which is something
away from this project's boundaries and should be 
defined by yourself.

## How to run (Dev)

To run Maestro for development purposes,
follow this steps

### Prerequisites
- SBT 1.9.x or higher
- Docker
- JDK 17 or higher

### Commands

Make sure you've got a Redis database running
on `localhost:6379` (in a docker container, if you want).

Then run

```bash
$ sbt run
```

### How to run (production)

The same prerequisites apply in this 
part.

In this case, it's a good idea to make a
Docker image out of this project, for example
running

```bash
$ sbt docker:publishLocal
```

Then you can have a `docker-compose.yml` like this:

```yaml
services:
  maestro-app:
    # change the image name
    image: my-image/maestro:latest
    ports:
      - "8080:8080"
    depends_on:
      - redis
  redis:
    image: redis:7-alpine
    restart: always
    volumes:
      - redis_data:/data

volumes:
  redis_data:
```

## Future steps

As of now, Maestro assumes that it will be
run continuously without failures. If Maestro
ever falls down, it will generate new keys and the
stored UUIDs will automatically be classified as
incorrect sessions. 

- **TODO**: Automatic public and private key renewal after *n* of days
- **TODO**: `/rotate` endpoint for key rotation, with enhanced security
- **TODO**: Save current keys in Redis Database
- **TODO**: Make `KeystoreManager` check for saved keys upon start
- **TODO**: Make integration and unitary tests