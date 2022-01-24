import utils.Extensions._

final case class Runways(
                   id: Int,
                   airport_ref: Int,
                   airport_ident: String,
                   length_ft: Int,
                   width_ft: Int,
                   surface: String,
                   lighted: Int,
                   closed: Int,
                   le_ident: Option[String],
                   le_latitude_deg: Option[Double],
                   le_longitude_deg: Option[Double],
                   le_elevation_ft: Option[Int],
                   le_heading_degT: Option[Int],
                   le_displaced_threshold_ft: Option[Int],
                   he_ident: Option[String],
                   he_latitude_deg: Option[Double],
                   he_longitude_deg: Option[Double],
                   he_elevation_ft: Option[Int],
                   he_heading_degT: Option[Int],
                   he_displaced_threshold_ft: Option[Int]
                   ) {

  override def toString: String = {
    s"\n$id - Runways: $airport_ref [$airport_ident]" +
    s"\n    | Dims: $length_ft - width_ft" +
    s"\n    | Type: $surface" +
    s"\n    | Coordinate: $le_latitude_deg • $le_longitude_deg • $le_elevation_ft ft" +
    s"\n    | Lighted: $lighted" +
    s"\n    | Closed: $closed"
  }
}

object Runways {
  def apply(input: String): Option[Runways] = {
    val values = input.replaceAll("[\"]", "")
      .split(",", -1)

    if (!(values(0) forall Character.isDigit)
        || !(values(1) forall Character.isDigit)
        || values(2).equals("")
        || !(values(3) forall Character.isDigit) || values(3).isEmpty
        || !(values(4) forall Character.isDigit) || values(4).isEmpty
        || values(5).equals("")
        || !(values(6) forall Character.isDigit)
        || !(values(7) forall Character.isDigit)
        || values(8).equals("")
/*        || !(values(11) forall Character.isDigit)
        || !(values(12) forall Character.isDigit)
        || !(values(13) forall Character.isDigit)
        || !(values(17) forall Character.isDigit)
        || !(values(18) forall Character.isDigit)
        || !(values(19) forall Character.isDigit)*/
    ) None
    
    else Some(Runways(
      values(0).toInt,
      values(1).toInt,
      values(2),
      values(3).toInt,
      values(4).toInt,
      values(5),
      values(6).toInt,
      values(7).toInt,
      values(8).noneIfBlank,
      values(9).toDoubleOption,
      values(10).toDoubleOption,
      values(11).toIntOption,
      values(12).toIntOption,
      values(13).toIntOption,
      values(14).noneIfBlank,
      values(15).toDoubleOption,
      values(16).toDoubleOption,
      values(17).toIntOption,
      values(18).toIntOption,
      values(19).toIntOption
    ))
  }

}
