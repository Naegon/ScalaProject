import scala.reflect.ClassTag
import Extensions.{green, red, blue, yellow}

final case class System(airports: Array[Airport], runways: Array[Runways], countries: Array[Country])

object System {
  def apply(filePath: String): System = {
    println(s"Reading system from ${filePath.blue} folder")

    val rawAirports = Parser.readFromFile(s"${filePath}airports.csv").drop(1)
    val airports = Parser.parse(rawAirports, Airport)

    printStat(airports, "airports")

    val rawRunways = Parser.readFromFile(s"${filePath}runways.csv").drop(1)
    val runways = Parser.parse(rawRunways, Runways)

    printStat(runways, "runways")

    val rawCountries = Parser.readFromFile(s"${filePath}countries.csv").drop(1)
    val countries = Parser.parse(rawCountries, Country)

    printStat(countries, "countries")

    println("\n\n")

    System(airports.flatten, runways.flatten, countries.flatten)
  }

  def printStat[T:ClassTag](result: Array[Option[T]], value: String): Unit = {
    val correctCount = result.flatten[T].length
    val successRate = correctCount/result.length.toDouble*100

    successRate match {
      case x if x < 50 => println (s"Too much errors while reading $value -> read $correctCount $value out of ${result.length} (${"%.2f".format (successRate)}%)".red)
      case x if (50 to 75).contains(x) => println(s"Successfully read $correctCount $value out of ${result.length} (${"%.2f".format(successRate)}%)\nWarning: Success rate lower than 75%".yellow)
      case _ => println(s"Successfully read $correctCount $value out of ${result.length} (${"%.2f".format(successRate)}%)".green)
    }
  }
}
