import Utils.getUserInput
import Menu.menu
import Extensions.*

import scala.io.StdIn.readLine

object Query {

  def Query(): Unit = {
    println("\n\n\n||=======   Query   ======||\n")
    println("Please enter the country code or the country name you want to search\n")
    print("Your input: ")
    val input = readLine()

    val rawCountries = Parser.readFromFile("src/main/Resources/countries.csv").drop(1)
    val countries = Parser.parseToCountry(rawCountries)

    val result = countries.filter(country => (country.name.toLowerCase() contains input.toLowerCase()) || (country.code.toLowerCase() contains input.toLowerCase()))

    result.length match {
      case 0 => println(s"No result found for \"$input\"")
      case 1 => show(result(0))
      case matches => select(result, input)
    }

    println("\n\n")
  }

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
