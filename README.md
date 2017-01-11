Log4mongo-java
================

[Source code on GitHub](https://github.com/hhaa12321/log4mongo-java)

# Description
In order to support Mongo 3.0+. I fork this library from [log4mongo/log4mongo-java](https://github.com/log4mongo/log4mongo-java).

This library provides Log4J Appenders [1] that write log events to the
MongoDB document oriented database [2].

* MongoDbAppender - Stores a BSONified version of the Log4J LoggingEvent
* ExtendedMongoDbAppender - Extends MongoDbAppender by allowing you to add top level elements
* MongoDbPatternLayoutAppender - Uses standard Log4J pattern layout, parser
    and converter classes to store a log message as a custom-formatted document

# Author
* Bourne Wang (jeffking838@gmail.com)

# Old Authors
* Peter Monks (pmonks@gmail.com)
* Robert Stewart (robert@wombatnation.com)

# Old Contributors
* Jozef Sevcik (sevcik@styxys.com)
* Zach Bailey (znbailey@gmail.com)
* Gabriel Eisbruch (gabrieleisbruch@gmail.com)
* cskinfill
* Mick Knutson
* Jay Patel

# Pre-requisites
* JDK 1.5+
* MongoDB Server v3.0+ (tested with 3.4.1)
* MongoDB Java Driver v3.0+ (tested with 3.2.2)
* Log4J 1.2+ (tested with 1.2.16 - note: tests won't work on earlier versions due to Log4J API changes)
* Privateer (used only in unit tests - a copy is in the lib dir, in case you can't get it
from the central Maven repo)
