import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class CountrySpec extends AnyFlatSpec with Matchers:
  import Country._

  "Empty Country" should "be None" in {
    Country(",,,,,") must be(None)
  }

  "Country with empty Strings" should "return None" in {
    Country("\"\",\"\",\"\",\"\",,") must be(None)
  }

  "Country with valid Strings" should "return Country with these values" in {
    Country("\"a\",\"a\",\"a\",\"a\",,") must be(Some(Country("a","a","a","a",None,None)))
  }

  "Country with valid optional Strings" should "return Country with these values" in {
    Country("\"a\",\"a\",\"a\",\"a\",\"a\",\"a\"") must be(Some(Country("a","a","a","a",Some("a"),Some("a"))))
  }
