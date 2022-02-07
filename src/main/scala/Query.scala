import Menu.{getUserInput, menu}
import utils.Extensions.*


object Query {

  def select(matches: Array[Country], searched: String): Unit = {
    println(s"Found ${matches.length} matches:")
    matches.zipWithIndex.foreach((country, index) => println(s"    ${index + 1}) [${country.code.highlight(searched)}] ${country.name.highlight(searched)}"))

    println(s"\nPlease select one of the matched country with keys 1 to ${matches.length} or return with 0")

    val input = getUserInput(0 to matches.length, s" select one of the matched country with keys 1 to ${matches.length} or return with 0")

    if (input == 0) menu()
    else show(matches(input - 1))
  }
  
  
  def show(country: Country): Unit = {
    val rawAirports = Parser.readFromFile("src/main/Resources/airports.csv").drop(1)
    val airports = Parser.parseToAirport(rawAirports)

    val matchingAirports = airports.flatten.filter(_.isoCountry.contentEquals(country.code))
    println(s"Found ${matchingAirports.length} airports in ${country.name}")
    print(matchingAirports.mkString("\n"))
  }
  

}
