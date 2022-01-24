final case class Country (
                         id: String,
                         code: String,
                         name: String,
                         continent: String,
                         wikipediaLink: String,
                         keywords: String
                         ) {

  override def toString: String = {
    s"\n$id - [$code] $name ($continent)".appendedAll(
        if (!wikipediaLink.isBlank) s"\n    ↳ Wikipedia: $wikipediaLink" else "")
  }
}



object Country {
  def apply(input: String): Country = {
    val values = input.replaceAll("[\"]", "")
      .split(",", -1)

    Country(values(0), values(1), values(2), values(3), values(4), values(5))
  }

}
