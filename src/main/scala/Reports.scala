import Utils.getUserInput
import main.SYSTEM

object Reports {
  def Report(): Unit = {
    println("||======   Report   ======||")
    println("Please select the report you want to generate\n")
    println("1) 10 countries with the highest number of airport & with lower number of airports")
    println("2) Type of runways per country")
    println("3) Top 10 common runway latitude")

    val input = getUserInput(1 to 3, "Please choose one a number between 1 and 3")

    input match {
      case 1 => topTen()
      case 2 => runwaysPerCountry()
      case 3 => topLatitude()
    }
  }
  
  // Report 1: 1O most & 10 less
  def topTen(): Unit = {
    val countries = SYSTEM.countries
      .map(x => (x._2, x._3))
      .toSeq

    val airportByCountry = SYSTEM.airports
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
      .flatMap {
        case (x, y) if (x != None) && (y != "None") => Some(x.toString, y)
        case (x, y) => None
      }
      .distinct
      .sorted

    val test /*: Map[String, String]*/ = runways
      .groupMapReduce(_(0).toString)(_(1).toString)(_ + ", " + _)

    println(test.mkString("\n"))
  }

  // Report 3: Top latitudes
  def topLatitude(): Unit = {
    val runways = SYSTEM.runways
      .flatten
      .groupBy(_.le_ident)
      .view.mapValues(_.length)
      .toSeq
      .sortWith(_._2 > _._2)

    println("\n10 most common runways latitude: \n%s".format(runways.take(10).mkString("\n")))
  }

}
