package utils

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class ExtensionsSpec extends AnyFlatSpec with Matchers:
  import Extensions._

  "String" should "be a valid doubles" in {
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
