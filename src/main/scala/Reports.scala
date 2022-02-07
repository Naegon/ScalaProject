object Reports {
  
  // Report 1: 1O most & 10 less
  def topTen(): Unit = {
    val rawAirports = Parser.readFromFile("src/main/Resources/airports.csv").drop(1)
    val airports = Parser.parseToAirport(rawAirports)
    val rawCountries = Parser.readFromFile("src/main/Resources/countries.csv").drop(1)
    val countries = Parser
      .parseToCountry(rawCountries)
      .map(x => (x._2, x._3))
      .toSeq

    val airportByCountry = airports
      .flatten
      .groupBy(_.isoCountry)
      .view.mapValues(_.length)
      .toSeq
      .sortWith(_._2 > _._2)

    val countriesMap = countries.toMap
    val result = airportByCountry.map(x => (countriesMap.getOrElse(x._1, x._2), x._2))

    val highestAirportNb = result.take(10)
    val lowestAirportNb = result.slice(result.size - 10, result.size)

    println("\n\nUpper:\n"+highestAirportNb.mkString("\n"))
    println("\nLower:\n"+lowestAirportNb.mkString("\n"))
  }

  // Report 2: Type of runways per country
  def runwaysPerCountry(): Unit = {
    val rawCountries = Parser.readFromFile("src/main/Resources/countries.csv").drop(1)
    val countries = Parser
      .parseToCountry(rawCountries)
      .map(x => (x._2, x._3))
      .toMap

    val rawAirports = Parser.readFromFile("src/main/Resources/airports.csv").drop(1)
    val airports = Parser
      .parseToAirport(rawAirports)
      .flatten
      .map(x => (x.isoCountry,x.ident))
      .map(x => (x._2,countries.getOrElse(x._1, x._2)))
      .toMap

    val rawRunways = Parser.readFromFile("src/main/Resources/runways.csv").drop(1)
    val runways = Parser
      .parseToRunways(rawRunways)
      .flatten
      .map(x => (x.airport_ident, x.surface))
      .map(x => (airports.getOrElse(x._1, None), x._2)) // should we find a method to rm lines where it doesn't exist?
      .map {
        case (x, y) if (x != None) && (y != "None") => (x, y) // if None in x or None in y  (there is surface named None in runways.csv)
        case (x, y) => None
      }
      // need problem to remove None, rm previous lines?
      .distinct

    println(runways.mkString("\n"))
  }

  // Report 3: Top latitudes
  def topLatitude(): Unit = {
    val rawRunways = Parser.readFromFile("src/main/Resources/runways.csv").drop(1)
    val runways = Parser
      .parseToRunways(rawRunways)
      .flatten
      .groupBy(_.le_ident)
      .view.mapValues(_.length)
      .toSeq
      .sortWith(_._2 > _._2)

    println("\n10 most common runways latitude: \n%s".format(runways.take(10).mkString("\n")))
  }
}
