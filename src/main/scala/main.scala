//import UI.UI

object main {
  def main(args: Array[String]): Unit = {
    val rawAirports = Parser.readFromFile("src/main/Ressources/airports.csv").drop(1)
    val airports = Parser.parseToAirport(rawAirports)

    println(airports.flatten.mkString("\n"))

  //UI Launch
  /*
    println("Please select the corresponding number of what you want to do.\n1) Query\n2) Report")
    getInstruction(readLine()) match{
        case 1 => {
            println("You chose Query option.\nPlease enter your country code or country name.")
            println(queryAskCountry(readLine()))
        }
        case 2 => println("Reports")
        case _ => println("Syntax error")
  */
  }

}
