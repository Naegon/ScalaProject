# ScalaProject
Scala project in Efrei Paris' fifth year

# CORE SUBJECT
_For every one to reach 10_

## C.1
For this project you'll write a program parsing 3 CSV files. The files contain data for countries, airports and runway information.
You will create a sbt scala project with unit tests.
Feel free to use any Scala library/framework as to write your test.

You must not use «var», «for», «return», «throw» or «try/catch» keywords (unless you're writing an optional part, in that case it may be allowed if you ask first).

If you do not choose the database optional task, you're allowed to use mutable collections instead.
The sole purpose of the mutable collections must be to replace a database.

### C.2.1
Your program will be a command line program that will ask the user for two options:
- Query
- Reports

### C.2.2
Query Option will ask the user for the country name or code and display the airports & runways at each airport.
The input can be country code or country name.

### C.2.3
Choosing Reports will display the following (possibly through a menu):
- 10 countries with the highest number of airports (with count) and countries  with lowest number of airports.
- Type of runways (as indicated in "surface" column) per country
- The top 10 most common runway latitude (indicated in "le_ident" column)

If you get the job done (6pt) with a clean code (4pt), correct test coverage (4pt) you'll have up to 14.

# OPTIONAL PART
_For those who want more_

To reach 20 or more (if its allowed) chose among those options.

0. Use scala 3 _(1pt)_
1. In 2.2 make the name matching partial/fuzzy. e.g. entering zimb will result in Zimbabwe _(2pt)_
2. Use database (in memory for the exercise like h2, sqlite) _(4pt)_
3. Do a GUI _(6pt)_
4. Provide a rest API _(4pt)_
5. O.4 with a scalajs (possibly scalajs-react) frontend, in that case you only have to write Query (no report) from the CORE question. _(4pt + 4pt of O.4)_


For optional part 2 to 4 you're allowed to use scala libraries. But every group should use different libraries.
Possible libraries for 
- 2 -> Anorm, slick, squeryl, reactive-mongo, Casbah, elastic4s, Quil, doobie, Scalikejdbc, sdbc, sorm, mongo-scala-driver
- 3 -> Finch, http4s, Akka Http, Spray, Play (finatra and scalatra are forbidden)
