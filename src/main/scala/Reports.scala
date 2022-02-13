import Utils.getUserInput
import Extensions.{ bold, blue }
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
    val countries = SYSTEM
      .countries
      .map(x => (x._2, x._3))
      .toMap

    val airportByCountry = SYSTEM
      .airports
      .flatten
      .groupBy(_.isoCountry)
      .view.mapValues(_.length)
      .toSeq
      .sortWith(_._2 > _._2)

    val result = airportByCountry.map(x => (countries.getOrElse(x._1, x._1), x._2))

    val highestAirportNb = result.take(10)
    val lowestAirportNb = result.slice(result.size - 10, result.size)

    println("\n\nUpper:\n"+highestAirportNb.toMap.mkString("\n"))
    println("\nLower:\n"+lowestAirportNb.toMap.mkString("\n"))
  }

  // Report 2: Type of runways per country
  def runwaysPerCountry(): Unit = {
    val countries = SYSTEM
      .countries
      .map(x => (x.code, x.name))
      .toMap

    val airports = SYSTEM
      .airports
      .flatten
      .map(x => (x.isoCountry,x.ident))
      .map(x => (x._2,countries.getOrElse(x._1, None)))
      .toMap


    val runways = SYSTEM
      .runways
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
        case (x,y) if "(?i).*asp.*".r.matches(y) => (x,"Asphalt")
        case (x,y) if "(?i).*grav.*".r.matches(y)
          || "(?i).*grv.*".r.matches(y)
          || "(?i).*gvl.*".r.matches(y)
        => (x,"Gravel")
        case (x,y) if "(?i).*grs.*".r.matches(y)
          || "(?i)^gras.*".r.matches(y)
          || "(?i)^turf.*".r.matches(y)
          || "(?i)sod".r.matches(y)
        => (x,"Grass")
        case (x,y) if "(?i)^wat.*".r.matches(y) => (x,"Water")
        case (x,y) if "(?i).*con.*".r.matches(y)
          || "(?i).*concrete.*".r.matches(y)
        => (x,"Concrete")
        case (x,y) if "(?i)^unk.*".r.matches(y)
          || "(?i)^u$".r.matches(y)
        => (x,"Unknown")
        case (x,y) if "(?i)^unpaved.*".r.matches(y) => (x,"Unpaved")
        case (x,y) if "(?i)^paved".r.matches(y)
          || "(?i)^pav.*".r.matches(y)
        => (x,"Paved")
        case (x,y) if "(?i).*silt.*".r.matches(y) => (x,"Silt")
        case (x,y) if "(?i).*cla.*".r.matches(y) => (x,"Clay")
        case (x,y) if "(?i).*ice.*".r.matches(y) => (x,"Ice")
        case (x,y) if "(?i).*san.*".r.matches(y) => (x,"Sand")
        case (x,y) if "(?i).*dirt.*".r.matches(y) => (x,"Dirt")
        case (x,y) if "(?i)gre".r.matches(y)
          || "(?i).*earth.*".r.matches(y)
          || "(?i).*graded.*".r.matches(y)
          || "(?i).*soil.*".r.matches(y)
        => (x,"Graded soil")
        case (x,y) if "(?i)lat".r.matches(y) => (x,"Laterite")
        case (x,y) if "(?i)sno".r.matches(y) => (x,"Snow")
        case (x,y) if "(?i).*coral.*".r.matches(y)
          || "(?i)^cor.*".r.matches(y)
        => (x,"Coral")
        case (x,y) if "(?i).*bit.*".r.matches(y) => (x,"Bitume")
        case (x,y) if "(?i)pem".r.matches(y)
          || "(?i)mac".r.matches(y)
        => (x,"Macadam")
        case (x,y) if "(?i)^bri.*".r.matches(y) => (x,"Brick")
        case (x,y) if "(?i).*ground.*".r.matches(y) => (x,"Ground")
        case (x,y) if "(?i).*com.*".r.matches(y)
          || "(?i)cop".r.matches(y)
        => (x,"Composite")
        case (x,y) if "(?i).*snow.*".r.matches(y) => (x,"Snow")
        case (x,y) if "(?i).*sand.*".r.matches(y) => (x,"sand")
        case (x,y) if "(?i).*rock.*".r.matches(y) => (x,"Rock")
        case (x,y) if "(?i).*wood.*".r.matches(y) => (x,"Wood")
        case (x,y) if "(?i)per".r.matches(y)
          || "(?i)pam".r.matches(y)
        => (x,"Permanent")
        case (x,y) if "(?i)met.*".r.matches(y) ||
          "(?i)mtal".r.matches(y)
        => (x,"Metal")
        case (x,y) if "(?i).*tar.*".r.matches(y) => (x,"Tarmac")
        case (x,y) if "(?i)treated.*".r.matches(y) ||
          "(?i)trtd.*".r.matches(y)
        => (x,"Metal")
        case (x,y) if "(?i).*steel.*".r.matches(y) => (x,"Metal")
        case (x,y) if "(?i)oil.*".r.matches(y) => (x,"Oil")
        case (x,y) if "(?i)alum.*".r.matches(y) => (x,"Aluminium")
        case (x,y) if "(?i)volcanic.*".r.matches(y) => (x,"Volcanic ash")
        case (x,y) if "(?i)roof.*".r.matches(y) => (x,"Rooftop")
        case (x,y) if "(?i)caliche".r.matches(y) => (x,"Caliche")
        case (x,y) if "(?i)mats.*".r.matches(y) => (x,"Mats")
        case (x,y) if "(?i)sealed".r.matches(y) => (x,"Sealed")
        case (x,y) if "(?i)unsealed".r.matches(y) => (x,"Unsealed")
        case (x,y) if "(?i)neoprene".r.matches(y) => (x,"Neoprene")
        case (x,y) if "(?i)grain".r.matches(y) => (x,"Grain")
        case (x,y) if "(?i)deck".r.matches(y) => (x,"Deck")
        case (x,y) if "(?i).*loam.*".r.matches(y) => (x,"Loam")
        case (x,y) if "(?i).*mud.*".r.matches(y) => (x,"Mud")
        case (x,y) if "(?i).*lime.*".r.matches(y) => (x,"Limestone")
        case (x,y) if "(?i).*pad.*".r.matches(y) => (x,"Pad")
        case (x,y) => (x,y)
      }
      .distinct
      .sorted

    val test /*: Map[String, String]*/ = result
      .groupMapReduce(_(0).toString.blue.bold)(_(1).toString)(_ + ", " + _)
      .toSeq.sortWith(_._1 < _._1)

    println(test.mkString("\n").replaceAll(",(?! )", " -> ").replaceAll("[()]", ""))

    val tmp = result
      .map(_._2)
      .distinct
      .sorted

//    println("\n\n"+tmp.mkString(", ")+"\n"+tmp.length)
  }

  // Report 3: Top latitudes
  def topLatitude(): Unit = {
    val runways = SYSTEM
      .runways
      .flatten
      .groupBy(_.le_ident)
      .view.mapValues(_.length)
      .toSeq
      .sortWith(_._2 > _._2)

    println(("\n10 most common runways latitude:\n" +
      "\tLatitude: \tOccurrences\n" +
      "%s").format(runways.take(10).mkString("\n")
      .replace(",", "\t:\t   "))
      .replace("(","\t   ")
      .replace(")",""))

  }




}
