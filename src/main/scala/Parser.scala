import scala.io.Source

object Parser {
  def readFromFile(path: String): Array[String] = {
    // Open given file
    val source = Source.fromFile(path)

    // Save file as an Array of strings
    val data = source.getLines.toArray

    source.close
    data
  }

  def parseToAirport(rawData: Array[String]): Array[Option[Airport]] = rawData.map(Airport(_))

}
