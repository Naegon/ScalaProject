import scala.io.StdIn.readLine
import utils.Extensions.*

import scala.annotation.tailrec
import Reports.*
import Query._

object Menu {
    @tailrec
    def menu(): Unit = {
        println("||=======   Menu   =======||")
        println("\nWelcome to our application")
        println("Please choose one of the following option:")
        println("  • Query")
        println("  • Report")

        val input = getUserInput(1 to 2, "Please choose 1 (Query) or 2 (Report)")

        input match {
            case 1 => Query()
            case 2 => Report()
        }
        menu()
    }
    
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
    
    



    @tailrec
    def getUserInput(acceptedRange: Range, errorMessage: String): Int = {
        print("\nYour choice: ")
        val input = readLine()
        if (input != "" && (input forall Character.isDigit) && (acceptedRange contains input.toInt)) input.toInt
        else {
            print("Invalid input -> ".red + errorMessage + "\n")
            getUserInput(acceptedRange, errorMessage)
        }
    }



    def Report(): Unit = {
        println("||======   Report   ======||")
        println("Please select the report you want to generate\n")
        println("1) 10 countries with the highest number of airport & with lower number of airports")
        println("2) Type of runways per country")
        println("3) Top 10 common runway latitude")

        val input = getUserInput(1 to 3, "Please choose one a number between 1 and 3")

        input match {
            case 1 => topTen()
            // type of runways (surface) per country
            case 2 => runwaysPerCountry()
            case 3 => topLatitude()
        }
    }
}
