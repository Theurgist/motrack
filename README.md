# motrack 
master: [![Build Status](https://travis-ci.org/Theurgist/motrack.svg?branch=master)](https://travis-ci.org/Theurgist/motrack)
develop: [![Build Status](https://travis-ci.org/Theurgist/motrack.svg?branch=develop)](https://travis-ci.org/Theurgist/motrack)

## Application for budget retrospection
This is a pet-project for fun and scala digging, 
it is not usable as a real tool ATM.

Here is an approximate list of applied technologies 
(some of them are possibly horribly misused):
* Core
    * akka (http, actor, FSM)
    * cats-effect
    * circe
* Persistence
    * quill
    * h2 (postgresql soon)
    * flyway
* UI
    * ScalaFx
    
#### Build
`sbt clean assembly` or `bin/build.sh` (need installed sbt)

#### Execute
After assembling project it's sufficient to:
* run `bin/motrack-server.sh` for server
* run `bin/motrack-client.sh` for client