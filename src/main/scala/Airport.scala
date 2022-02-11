import Extensions._

final case class Airport(
                   id: String,
                   ident: String,
                   airportType: String,
                   name: String,
                   latitude_deg: Double,
                   longitude_deg: Double,
                   elevation_ft: Option[Int],
                   continent: String,
                   isoCountry: String,
                   isoRegion: String,
                   municipality: String,
                   scheduledService: Option[Boolean],
                   gpsCode: Option[String],
                   iataCode: Option[String],
                   localCode: Option[String],
                   homeLink: Option[String],
                   wikipediaLink: Option[String],
                   keywords: Option[String]
                   ) {

  override def toString: String = {
    s"\n$id - Airport: $name [$ident]" +
      s"\n    | Type: $airportType" +
      s"\n    | Coordinate: $latitude_deg • $longitude_deg"
        .appendedAll(if(elevation_ft.isDefined) s" • ${elevation_ft.get}ft." else "") +
      s"\n    | Localisation: $continent - $municipality ($isoCountry, $isoRegion)"
        .appendedAll(if (wikipediaLink.isDefined) s"\n    | Wikipedia: ${wikipediaLink.get}" else "")
  }
}

object Airport {
  def apply(input: String): Option[Airport] = {
    val values = input.replaceAll("[\"]", "")
      .split(",", -1)

    if (!values(4).isValidDouble || !values(5).isValidDouble) None
    else Some(Airport(
      values(0),
      values(1),
      values(2),
      values(3),
      values(4).toDouble,
      values(5).toDouble,
      values(6).toIntOption,
      values(7),
      values(8),
      values(9),
      values(10),
      values(11).toOptionalBool,
      values(12).noneIfBlank,
      values(13).noneIfBlank,
      values(14).noneIfBlank,
      values(15).noneIfBlank,
      values(16).noneIfBlank,
      values(17).noneIfBlank
    ))
  }

}
