object main {
  def main(args: Array[String]): Unit = {
    val rawAirports = Parser.readFromFile("/Users/guillaumehamel/Documents/Cours/Scala/ScalaProject/src/main/Ressources/airports.csv").drop(1)
    val airports = Parser.parseToAirport(rawAirports)

    println(airports.mkString("Array(", ", ", ")"))
  }

}
