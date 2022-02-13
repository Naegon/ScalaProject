import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class AirportSpec extends AnyFlatSpec with Matchers:
  import Airport._

  "Empty airport" should "be None" in {
    Airport(",,,,,,,,,,,,,,,,,") must be(None)
  }

  "Airport with invalid doubles" should "return None" in {
    Airport(",,,,invalid,invalid,,,,,,,,,,,,") must be(None)
  }

  "Airport with valid doubles" should "return empty Airport" in {
    Airport(",,,,12.21,42.24,,,,,,,,,,,,") must be(Some(Airport("", "", "", "", 12.21, 42.24, None, "", "", "", "", None, None, None, None, None, None, None)))
  }

  "Airport with set scheduledService" should "return Airport with set scheduleService value" in {
    Airport(",,,,12.21,42.24,,,,,,yes,,,,,,") must be(Some(Airport("", "", "", "", 12.21, 42.24, None, "", "", "", "", Some(true), None, None, None, None, None, None)))
    Airport(",,,,12.21,42.24,,,,,,no,,,,,,") must be(Some(Airport("", "", "", "", 12.21, 42.24, None, "", "", "", "", Some(false), None, None, None, None, None, None)))
  }
