import Utils._
import Extensions._
import main.SYSTEM

import scala.util.matching.Regex

object Reports {
  def Report(): Unit = {
    println("||======   Report   ======||".bold)
    println("Please select the report you want to generate\n")
    println("  1) 10 countries with the highest number of airport & with lower number of airports")
    println("  2) Type of runways per country")
    println("  3) Top 10 common runway latitude")

    val input = getUserInput(1 to 3, "Please choose one a number between 1 and 3")

    input match {
      case 1 => topTen()
      case 2 => runwaysPerCountry()
      case 3 => topLatitude()
    }
  }
  
  // Report 1: 1O most & 10 less
  def topTen(): Unit = {
    val countries = SYSTEM
      .countries
      .map(x => (x._2, x._3))
      .toMap

    val airportByCountry = SYSTEM
      .airports
      .groupBy(_.isoCountry)
      .view.mapValues(_.length)
      .toSeq
      .sortWith(_._2 > _._2)

    val result = airportByCountry.map(x => (countries.getOrElse(x._1, x._1), x._2))

    val highestAirportNb = result.take(10)
    val lowestAirportNb = result.slice(result.size - 10, result.size)

    println("\n\nUpper:\n"+highestAirportNb.mkString("\n").removeParenthesis())
    println("\nLower:\n"+lowestAirportNb.mkString("\n").removeParenthesis())
  }

  // Report 2: Type of runways per country
  def runwaysPerCountry(): Unit = {
    val countries = SYSTEM
      .countries
      .map(x => (x.code, x.name))
      .toMap

    val airports = SYSTEM
      .airports
      .map(x => (x.isoCountry,x.ident))
      .map(x => (x._2,countries.getOrElse(x._1, None)))
      .toMap

    val runways = SYSTEM
      .runways
      .map(x => (x.airport_ident, x.surface))

    val result = runways
      .map(x => (airports.getOrElse(x._1, None), x._2)) // should we find a method to rm lines where it doesn't exist?
      .flatMap {
        case (x, y) if (x != None) && (y != "None") => Some(x.toString, y)
        case (x, y) => None
      }
      .distinct
      .map((x, y) => (x, replaceCat(y)))
      .distinct
      .sorted

    val test /*: Map[String, String]*/ = result
      .groupMapReduce(_(0).toString.blue.bold)(_(1).toString)(_ + ", " + _)
      .toSeq.sortWith(_._1 < _._1)

    println(test.mkString("\n").replaceAll(",(?! )", " -> ").removeParenthesis())
  }

  // Report 3: Top latitudes
  def topLatitude(): Unit = {
    val topRunways = SYSTEM
      .runways
      .groupBy(_.le_ident)
      .view.mapValues(_.length)
      .toSeq
      .sortWith(_._2 > _._2)
      .take(10)

    println("\n10 most common runways latitude:\n" +
      "| Latitude     | Occurrences  |\n" +
      "| ------------ | ------------ |\n" +
      topRunways
        .map((x, y) => ("| " + x.toString.padTo(12, ' ')  + " | ", y.toString.padTo(12, ' ') + " |"))
        .mkString("\n")
        .removeParenthesis()
        .replaceAll(",", "")
    )
  }

  def replaceCat(string: String): String = {
    string match {
      case asphalt(_*) => "Asphalt"
      case gravel(_*) => "Gravel"
      case grass(_*) => "Grass"
      case water(_*) => "Water"
      case concrete(_*) => "Concrete"
      case unknown(_*) => "Unknown"
      case unpaved(_*) => "Unpaved"
      case paved(_*) => "Paved"
      case silt(_*) => "Silt"
      case clay(_*) => "Clay"
      case ice(_*) => "Ice"
      case sand(_*) => "Sand"
      case dirt(_*) => "Dirt"
      case graded(_*) => "Graded soil"
      case laterite(_*) => "Laterite"
      case snow(_*) => "Snow"
      case coral(_*) => "Coral"
      case bitume(_*) => "Bitume"
      case macadam(_*) => "Macadam"
      case brick(_*) => "Brick"
      case ground(_*) => "Ground"
      case composite(_*) => "Composite"
      case rock(_*) => "Rock"
      case wood(_*) => "Wood"
      case permanent(_*) => "Permanent"
      case metal(_*) => "Metal"
      case tarmac(_*) => "Tarmac"
      case treated(_*) => "Treated"
      case steel(_*) => "Metal"
      case oil(_*) => "Oil"
      case aluminium(_*) => "Aluminium"
      case volcanic(_*) => "Volcanic ash"
      case rooftop(_*) => "Rooftop"
      case caliche(_*) => "Caliche"
      case mats(_*) => "Mats"
      case seal(_*) => "Sealed"
      case unseal(_*) => "Unsealed"
      case neoprene(_*) => "Neoprene"
      case grain(_*) => "Grain"
      case deck(_*) => "Deck"
      case loam(_*) => "Loam"
      case mud(_*) => "Mud"
      case stone(_*) => "Stone"
      case pad(_*) => "Pad"
      case _ => string.red
    }
  }

}
