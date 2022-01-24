import utils.Extensions._

final case class Airport(
                   id: String,
                   ident: String,
                   airportType: String,
                   name: String,
                   latitude_deg: Double,
                   longitude_deg: Double,
                   elevation_ft: Int,
                   continent: String,
                   isoCountry: String,
                   isoRegion: String,
                   municipality: String,
                   scheduledService: Boolean,
                   gpsCode: String,
                   iataCode: String,
                   localCode: String,
                   homeLink: String,
                   wikipediaLink: String,
                   keywords: String
                   ) {

  override def toString: String = {
    s"\n$id - Airport: $name [$ident]" +
      s"\n    | Type: $airportType" +
      s"\n    | Coordinate: $latitude_deg • $longitude_deg • ${elevation_ft}ft." +
      s"\n    | Localisation: $continent - $municipality ($isoCountry, $isoRegion)".appendedAll(
      if (!wikipediaLink.isBlank) s"\n    | Wikipedia: $wikipediaLink" else "")
  }
}

object Airport {
  def apply(input: String): Option[Airport] = {
    val values = input.replaceAll("[\"]", "")
      .split(",", -1)

    if (!values(4).isValidDouble
        || !values(5).isValidDouble
        || !(values(6) forall Character.isDigit)
        || values(4).equals("")
        || values(5).equals("")
        || values(6).equals("")
    ) None
    else Some(Airport(
      values(0),
      values(1),
      values(2),
      values(3),
      values(4).toDouble,
      values(5).toDouble,
      values(6).toInt,
      values(7),
      values(8),
      values(9),
      values(10),
      values(11) == "yes",
      values(12),
      values(13),
      values(14),
      values(15),
      values(16),
      values(17)
    ))
  }

}
