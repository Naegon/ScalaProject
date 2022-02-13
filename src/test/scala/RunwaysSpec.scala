import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class RunwaysSpec extends AnyFlatSpec with Matchers:
  import Runways._

  "Empty runway" should "be None" in {
    Runways(",,,,,,,,,,,,,,,,,,,") must be(None)
  }

  "Runway with invalid Integers" should "return None" in {
    Runways("incorrect,incorrect,,incorrect,incorrect,,incorrect,incorrect,,,,,,,,,,,,") must be(None)
    Runways("AD2,AD2,,AD2,AD2,,AD2,AD2,,,,,,,,,,,,,") must be(None)
  }

  "Runways with empty integer" should "return None" in {
    Runways(",,\"d\",,,\"d\",,,\"d\",,,,,,,,,,,") must be(None)
  }

  "Runways with empty string" should "return None" in {
    Runways("1,1,\"\",1,1,\"\",1,1,\"\",,,,,,,,,,,") must be(None)
  }

  "Runways with valid string and integers" should "return Runways with its values" in {
    Runways("12,12,\"a\",12,12,\"a\",12,12,\"a\",,,,,,,,,,,") must be(Some(Runways(12,12,"a",12,12,"a",12,12,"a",None,None,None,None,None,None,None,None,None,None,None)))
  }


  "Runways with valid optional integers" should "return Runways with its values in option" in {
    Runways("12,12,\"a\",12,12,\"a\",12,12,\"a\",,,11,11,11,,,,11,11,11") must be(Some(Runways(12,12,"a",12,12,"a",12,12,"a",None,None,Some(11),Some(11),Some(11),None,None,None,Some(11),Some(11),Some(11))))
  }

  "Runways with invalid optional integers" should "return Runways with these values as None" in {
    Runways("12,12,\"a\",12,12,\"a\",12,12,\"a\",,,\"a\",\"a\",\"a\",,,,\"a\",\"a\",\"a\"") must be(Some(Runways(12,12,"a",12,12,"a",12,12,"a",None,None,None,None,None,None,None,None,None,None,None)))
  }

  "Runways with valid optional double" should "return Runways with its values in option" in {
    Runways("12,12,\"a\",12,12,\"a\",12,12,\"a\",22.2,22.2,,,,,22.2,22.2,,,") must be(Some(Runways(12,12,"a",12,12,"a",12,12,"a",Some(22.2),Some(22.2),None,None,None,None,Some(22.2),Some(22.2),None,None,None)))
  }

  "Runways with invalid optional double" should "return Runways with these values as None" in {
    Runways("12,12,\"a\",12,12,\"a\",12,12,\"a\",\"a\",\"a\",,,,,\"a\",\"a\",,,\"") must be(Some(Runways(12,12,"a",12,12,"a",12,12,"a",None,None,None,None,None,None,None,None,None,None,None)))
  }

  "Runways with valid optional string" should "return Runways with its values in option" in {
    Runways("12,12,\"a\",12,12,\"a\",12,12,\"a\",,,,,,\"a\",,,,,") must be(Some(Runways(12,12,"a",12,12,"a",12,12,"a",None,None,None,None,None,Some("a"),None,None,None,None,None)))
  }
