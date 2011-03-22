This project aims to provide a simple [http://en.wikipedia.org/wiki/Builder_pattern](Builder pattern) and [http://en
.wikipedia.org/wiki/Factory_pattern](Factory pattern) based approach to generating DML (Data Manipulation Language)
SQL for all the popular RDBMS systems. Included builders are SelectBuilder, InsertBuilder, UpdateBuilder, DeleteBuilder.
It is much lighter weight than any of the ORM tools available, and is 100% Java, no other special configuration language or query language to learn.
This code has been in production use since its first incarnation around 2001. Also included is a [http://en.wikipedia
.org/wiki/Facade_pattern](Facade) patterned Database object that wraps a Datasource object. This provides an
execution framework for the results of the Builder objects which includes simplified transaction and batch management as well as the ability to transparently handle sql exceptions in a default manner. The Database object also provides a simple object oriented approach via wrappers around the database metadata.

There are no DDL (Data Definition Language) builders. This library is aimed at creating runtime dynamic sql easy. The DDL for the different databases is so varied that trying to abstract it out to a single generation framework would be counter productive. There are no plans to address DDL any time in the future. 

This is a very active project, come back often as I work on either the code/javadoc or wiki documentation on a daily
basis. Or you can subscribe to notifications using [http://freshmeat.net/projects/sqlck/](freshmeat.net).
