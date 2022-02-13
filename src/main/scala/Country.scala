import Extensions.noneIfBlank

final case class Country (
                         id: String,
                         code: String,
                         name: String,
                         continent: String,
                         wikipediaLink: Option[String],
                         keywords: Option[String]
                         ) {

  override def toString: String = {
    s"\n$id - [$code] $name ($continent)"
      .appendedAll(if (wikipediaLink.isDefined) s"\n    ↳ Wikipedia: ${wikipediaLink.get}" else "")
      .appendedAll(if (keywords.isDefined) s"\n    ↳ Keywords: ${keywords.get}" else "")
  }
}

object Country {
  def apply(input: String): Option[Country] = {
    val values = input.replaceAll("[\"]", "")
      .split(",", -1)

    if (values(0).equals("") || values(1).equals("") || values(2).equals("") || values(3).equals("")) None
    else Some(Country(values(0), values(1), values(2), values(3), values(4).noneIfBlank, values(5).noneIfBlank))
  }

}
