final case class System(airports: Array[Option[Airport]], runways: Array[Option[Runways]], countries: Array[Country])

object System {
  def apply(filePath: String): System = {
    val rawAirports = Parser.readFromFile(s"${filePath}airports.csv").drop(1)
    val airports = Parser.parseToAirport(rawAirports)

    val rawRunways = Parser.readFromFile(s"${filePath}runways.csv").drop(1)
    val runways = Parser.parseToRunways(rawRunways)

    val rawCountries = Parser.readFromFile(s"${filePath}countries.csv").drop(1)
    val countries = Parser.parseToCountry(rawCountries)

    System(airports, runways, countries)
  }
}
