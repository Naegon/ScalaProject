import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class ExtensionsSpec extends AnyFlatSpec with Matchers:
  import Extensions.*

  "Given string" should "be a valid doubles" in {
    "12.00".isValidDouble must be(true)
    "-12.00".isValidDouble must be(true)
    "1212354.21354987651".isValidDouble must be(true)
    "12.0".isValidDouble must be(true)
  }

  "String" should "be invalid doubles" in {
    "12.0A".isValidDouble must be(false)
    "*12.00".isValidDouble must be(false)
    "1212354".isValidDouble must be(false)
    "Pomme".isValidDouble must be(false)
    "".isValidDouble must be(false)
  }

  "String 'yes'" should "be Some(true)" in {
    "yes".toOptionalBool must be(Some(true))
  }

  "String 'no'" should "be Some(false)" in {
    "no".toOptionalBool must be(Some(false))
  }

  "invalid bool string" should "be None" in {
    "zradabadjan".toOptionalBool must be(None)
    "".toOptionalBool must be(None)
    "42".toOptionalBool must be(None)
  }

  "Non empty string" should "be valid" in {
    "zradabadjan".noneIfBlank must be(Some("zradabadjan"))
  }

  "Empty string" should "be None" in {
    "".noneIfBlank must be(None)
  }

  "String without parenthesis" should "not change" in {
    "zradabadjan".removeParenthesis() must be("zradabadjan")
    "".removeParenthesis() must be("")
    "Ceci est une string de test".removeParenthesis() must be("Ceci est une string de test")
  }

  "Parenthesis" should "be removed" in {
    "(Enclosing parenthesis)".removeParenthesis() must be("Enclosing parenthesis")
    "()".removeParenthesis() must be("")
    "(12 * (8 - 2) / (51+5)".removeParenthesis() must be("12 * 8 - 2 / 51+5")
  }

  "Fully colored strings" should "be correctly formatted" in {
    "Red".red must be("\u001B[31mRed\u001B[0m")
    "Green".green must be("\u001B[32mGreen\u001B[0m")
    "Blue".blue must be("\u001B[34mBlue\u001B[0m")
    "Yellow".yellow must be("\u001B[33mYellow\u001B[0m")
    "Bold".bold must be("\u001B[1mBold\u001B[0m")
  }

  "Partially colored strings" should "be correctly formatted" in {
    s"${"Red".red} string" must be("\u001B[31mRed\u001B[0m string")
    s"${"Green".green} string" must be("\u001B[32mGreen\u001B[0m string")
    s"${"Blue".blue} string" must be("\u001B[34mBlue\u001B[0m string")
    s"${"Yellow".yellow} string" must be("\u001B[33mYellow\u001B[0m string")
    s"${"Bold".bold} string" must be("\u001B[1mBold\u001B[0m string")
  }

  "String without target" should "not change" in {
    "Pomme".highlight("poire") must be("Pomme")
    "".highlight("Test target") must be("")
  }

  "String with valid target" should "highlight them" in {
    "I like green words".highlight("green") must be("I like \u001B[32mgreen\u001B[0m words")
    "Whole string match".highlight("Whole string match") must be("\u001B[32mWhole string match\u001B[0m")
  }
