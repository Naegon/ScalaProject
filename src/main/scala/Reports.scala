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
      .toMap

    val airportByCountry = SYSTEM.airports
      .flatten
      .groupBy(_.isoCountry)
      .view.mapValues(_.length)
      .toSeq
      .sortWith(_._2 > _._2)

    val result = airportByCountry.map(x => (countries.getOrElse(x._1, x._2), x._2))

    val highestAirportNb = result.take(10)
    val lowestAirportNb = result.slice(result.size - 10, result.size)

    println("\n\nUpper:\n"+highestAirportNb.mkString("\n"))
    println("\nLower:\n"+lowestAirportNb.mkString("\n"))
  }

  // Report 2: Type of runways per country
  def runwaysPerCountry(): Unit = {
    val countries = SYSTEM.countries
      .map(x => (x.code, x.name))
      .toMap

    val airports = SYSTEM.airports
      .flatten
      .map(x => (x.isoCountry,x.ident))
      .map(x => (x._2,countries.getOrElse(x._1, None)))
      .toMap


    val runways = SYSTEM.runways
      .flatten
      .map(x => (x.airport_ident, x.surface))


    val result = runways
      .map(x => (airports.getOrElse(x._1, None), x._2)) // should we find a method to rm lines where it doesn't exist?
      .flatMap {
        case (x, y) if (x != None) && (y != "None") => Some(x.toString, y)
        case (x, y) => None
      }
      .distinct
      .map {
        case (x,y) if ".*asp.*".r.matches(y.toLowerCase()) => (x,"Asphalt")
        case (x,y) if ".*grav.*".r.matches(y.toLowerCase())
          || ".*grv.*".r.matches(y.toLowerCase())
          || ".*gvl.*".r.matches(y.toLowerCase())
        => (x,"Gravel")
        case (x,y) if ".*grs.*".r.matches(y.toLowerCase())
          || "^gras.*".r.matches(y.toLowerCase())
          || "^turf.*".r.matches(y.toLowerCase())
          || "sod".r.matches(y.toLowerCase())
        => (x,"Grass")
        case (x,y) if "^wat.*".r.matches(y.toLowerCase()) => (x,"Water")
        case (x,y) if ".*con.*".r.matches(y.toLowerCase())
          || ".*concrete.*".r.matches(y.toLowerCase())
        => (x,"Concrete")
        case (x,y) if "^unk.*".r.matches(y.toLowerCase())
          || "^u$".r.matches(y.toLowerCase())
        => (x,"Unknown")
        case (x,y) if "^unpaved.*".r.matches(y.toLowerCase()) => (x,"Unpaved")
        case (x,y) if "^paved".r.matches(y.toLowerCase())
          || "^pav.*".r.matches(y.toLowerCase())
        => (x,"Paved")
        case (x,y) if ".*silt.*".r.matches(y.toLowerCase()) => (x,"Silt")
        case (x,y) if ".*cla.*".r.matches(y.toLowerCase()) => (x,"Clay")
        case (x,y) if ".*ice.*".r.matches(y.toLowerCase()) => (x,"Ice")
        case (x,y) if ".*san.*".r.matches(y.toLowerCase()) => (x,"Sand")
        case (x,y) if ".*dirt.*".r.matches(y.toLowerCase()) => (x,"Dirt")
        case (x,y) if "gre".r.matches(y.toLowerCase())
          || ".*earth.*".r.matches(y.toLowerCase())
          || ".*graded.*".r.matches(y.toLowerCase())
          || ".*soil.*".r.matches(y.toLowerCase())
        => (x,"Graded soil")
        case (x,y) if "lat".r.matches(y.toLowerCase()) => (x,"Laterite")
        case (x,y) if "sno".r.matches(y.toLowerCase()) => (x,"Snow")
        case (x,y) if ".*coral.*".r.matches(y.toLowerCase())
          || "^cor.*".r.matches(y.toLowerCase())
        => (x,"Coral")
        case (x,y) if ".*bit.*".r.matches(y.toLowerCase()) => (x,"Bitume")
        case (x,y) if "pem".r.matches(y.toLowerCase())
          || "mac".r.matches(y.toLowerCase())
        => (x,"Macadam")
        case (x,y) if "^bri.*".r.matches(y.toLowerCase()) => (x,"Brick")
        case (x,y) if ".*ground.*".r.matches(y.toLowerCase()) => (x,"Ground")
        case (x,y) if ".*com.*".r.matches(y.toLowerCase())
          || "cop".r.matches(y.toLowerCase())
        => (x,"Composite")
        case (x,y) if ".*snow.*".r.matches(y.toLowerCase()) => (x,"Snow")
        case (x,y) if ".*sand.*".r.matches(y.toLowerCase()) => (x,"sand")
        case (x,y) if ".*rock.*".r.matches(y.toLowerCase()) => (x,"Rock")
        case (x,y) if ".*wood.*".r.matches(y.toLowerCase()) => (x,"Wood")
        case (x,y) if "per".r.matches(y.toLowerCase())
          || "pam".r.matches(y.toLowerCase())
        => (x,"Permanent")
        case (x,y) if "met.*".r.matches(y.toLowerCase()) ||
          "mtal".r.matches(y.toLowerCase())
        => (x,"Metal")
        case (x,y) if ".*tar.*".r.matches(y.toLowerCase()) => (x,"Tarmac")
        case (x,y) if "treated.*".r.matches(y.toLowerCase()) ||
          "trtd.*".r.matches(y.toLowerCase())
        => (x,"Metal")
        case (x,y) if ".*steel.*".r.matches(y.toLowerCase()) => (x,"Metal")
        case (x,y) if "oil.*".r.matches(y.toLowerCase()) => (x,"Oil")
        case (x,y) if "alum.*".r.matches(y.toLowerCase()) => (x,"Aluminium")
        case (x,y) if "volcanic.*".r.matches(y.toLowerCase()) => (x,"Volcanic ash")
        case (x,y) if "roof.*".r.matches(y.toLowerCase()) => (x,"Rooftop")
        case (x,y) if "caliche".r.matches(y.toLowerCase()) => (x,"Caliche")
        case (x,y) if "mats.*".r.matches(y.toLowerCase()) => (x,"Mats")
        case (x,y) if "sealed".r.matches(y.toLowerCase()) => (x,"Sealed")
        case (x,y) if "unsealed".r.matches(y.toLowerCase()) => (x,"Unsealed")
        case (x,y) if "neoprene".r.matches(y.toLowerCase()) => (x,"Neoprene")
        case (x,y) if "grain".r.matches(y.toLowerCase()) => (x,"Grain")
        case (x,y) if "deck".r.matches(y.toLowerCase()) => (x,"Deck")
        case (x,y) if ".*loam.*".r.matches(y.toLowerCase()) => (x,"Loam")
        case (x,y) if ".*mud.*".r.matches(y.toLowerCase()) => (x,"Mud")
        case (x,y) if ".*lime.*".r.matches(y.toLowerCase()) => (x,"Limestone")
        case (x,y) if ".*pad.*".r.matches(y.toLowerCase()) => (x,"Pad")
        case (x,y) => (x,y)
      }
      .distinct
      .sorted

    val test /*: Map[String, String]*/ = result
      .groupMapReduce(_(0).toString)(_(1).toString)(_ + ", " + _)

    println(test.mkString("\n"))

 /*   val tmp = result.map(_._2)
      .distinct
      .sorted

    println("\n\n"+tmp.mkString(", ")+"\n"+tmp.length)*/
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
