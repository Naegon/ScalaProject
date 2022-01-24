import UI.menu

object main {
  def main(args: Array[String]): Unit = {
//    getAndShowAirports()
//    getAndShowCountries()
//    getAndShowRunways()

    menu()
  }

  def getAndShowAirports(): Unit = {
    val rawAirports = Parser.readFromFile("src/main/Ressources/airports.csv").drop(1)
    val airports = Parser.parseToAirport(rawAirports)

    println(airports.flatten.mkString("\n"))
  }

  def getAndShowCountries(): Unit = {
    val rawCountries = Parser.readFromFile("src/main/Ressources/countries.csv").drop(1)
    val countries = Parser.parseToCountry(rawCountries)
    println(countries.mkString("\n"))
  }

  def getAndShowRunways(): Unit = {
    val rawRunways = Parser.readFromFile("src/main/Ressources/runways.csv").drop(1)
    val runways = Parser.parseToRunways(rawRunways)
    println(runways.flatten.mkString("\n"))
  }
  
}
