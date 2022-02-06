import scala.io.StdIn.readLine
import utils.Extensions._

object UI {
    def getInstruction(instruction: String): Int = instruction match {
        //case x if (x.length == 0) => "Requires 1 argument"
        case "1" => 1
        case "2" => 2
        case _ => 0
    }

    def queryAskCountry(country: String): String= country match {
        case x if x.length <= 1 => "Not enough characters"
        case x if x.exists{_.isDigit}  => "Digits are not allowed"
        case x if x.length == 2 =>
            val code = country.toUpperCase()
            s"Search in Code item: $code"

        case _ =>
            val name = country.toLowerCase() //do same in database
            s"Search in Names item: $name"
    }

    def reportAskNumber(report: String): String= report match{
        case "1" => "Highest"
        case "2" => "Lowest"
        case "3" => "Type"
        case "4" => "Latitude"
        case _ => "Error"
    }

    val validInputQuery = List("Q", "Query", "1")
    val validInputReport = List("R", "Report", "2")

    def menu(): Unit = {
        println("||=======   Menu   =======||")
        println("\nWelcome to our application")
        println("Please choose one of the following option:")
        println("  • Query")
        println("  • Report")

        print("\nYour choice: ")
        var input: String = readLine()

        while (!validInputQuery.contains(input) && !validInputReport.contains(input)) {
            println("Invalid input ->".red + " please choose one of the following: " + validInputQuery.concat(validInputReport).mkString(", "))
            print("\nYour choice: ")
            input = readLine()
        }

        input match {
            case x if validInputQuery contains x => Query()
            case x if validInputReport contains x => Report()
        }
    }

    def Query(): Unit = {
        println("\n\n\n||=======   Query   ======||\n")
        println("Please enter the country code or the country name you want to search\n")
        print("Your input: ")
        val input = readLine()

        val rawCountries = Parser.readFromFile("src/main/Resources/countries.csv").drop(1)
        val countries = Parser.parseToCountry(rawCountries)

        val result = countries.filter(_.name.toLowerCase() contains input.toLowerCase())

        result.length match {
            case 0 => println(s"No result found for \"$input\"")
            case 1 => show(result(0))
            case matches => select(result, input)
        }
    }

    def select(matches: Array[Country], searched: String): Unit = {
        println(s"Found ${matches.length} matches:")
        matches.zipWithIndex.foreach((country, index) => println(s"    ${index + 1}) [${country.code.highlight(searched)}] ${country.name.highlight(searched)}"))

        println(s"\nPlease select one of the matched country with keys 1 to ${matches.length} or return with 0")
        print("Your choice: ")

        var input = readLine()
        while (!(input forall Character.isDigit) || !(0 to matches.length contains input.toInt)) {
            println("Invalid input ->".red + s" select one of the matched country with keys 1 to ${matches.length} or return with 0")
            print("\nYour choice: ")
            input = readLine()
        }

        if (input.toInt == 0) menu()
        else show(matches(input.toInt -1))
    }


//    def selectReport() {}

    def show(country: Country): Unit = {
        val rawAirports = Parser.readFromFile("src/main/Resources/airports.csv").drop(1)
        val airports = Parser.parseToAirport(rawAirports)

        print(airports.flatten.filter(_.isoCountry.contentEquals(country.code)).mkString("\n"))
    }

    def Report(): Unit = {
        println("||======   Report   ======||")
        println("Please select the report you want to generate\n")
        println("1) 10 countries with the highest number of airport & with lower number of airports")
        println("2) Type of runways per country")
        println("3) Top 10 common runway latitude")
        print("Your choice: ")
        val validInput = List("1","2","3")

        var input = readLine()
        while (!(input forall Character.isDigit) && !validInput.contains(input)) {
            println("Invalid input ->".red + " please choose one of the following: " + validInput.mkString(", "))
            print("\nYour choice: ")
            input = readLine()
        }

        input match {
            case "1" =>
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
                  .mapValues(_.length)
                  .toSeq
                  .sortWith(_._2 > _._2)

                val countriesMap = countries.toMap
                val result = airportByCountry.map(x => (countriesMap.getOrElse(x._1, x._2), x._2))

                val highestAirportNb = result.take(10)
                val lowestAirportNb = result.slice(result.size - 10, result.size)

                println("\n\nUpper:\n"+highestAirportNb.mkString("\n"))
                println("\nLower:\n"+lowestAirportNb.mkString("\n"))

// type of runways (surface) per country
            case "2" =>
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
                  .map(x => (airports.getOrElse(x._1, None),x._2))  // should we find a method to rm lines where it doesn't exist?
                  .map(_ match {
                      case (x,y) if (x != None) && (y != "None") => ((x),(y))   // if None in x or None in y  (there is surface named None in runways.csv)
                      case (x,y) => None
                  })
                  // need problem to remove None, rm previous lines?
                  .distinct



                println(runways.mkString("\n"))

            case "3" =>
                val rawRunways = Parser.readFromFile("src/main/Resources/runways.csv").drop(1)
                val runways = Parser
                  .parseToRunways(rawRunways)
                  .flatten
                  .groupBy(_.le_ident)
                  .mapValues(_.length)
                  .toSeq
                  .sortWith(_._2 > _._2)

                println("\n10 most common runways latitude: \n%s".format(runways.take(10).mkString("\n")))
        }
    }

}
