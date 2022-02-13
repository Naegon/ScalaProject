import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

import scala.reflect.ClassTag

class SystemSpec extends AnyFlatSpec with Matchers:
  import System.getStat
  import Extensions.{red, yellow, green}

  "List of None" should "show error message with zero success rate" in {
    // Given
    val arrayOfNone: Array[Option[String]] = Array(None, None, None, None, None)

    // Then
    getStat(arrayOfNone, "bananas") must be(s"Too much errors while reading bananas -> read 0 bananas out of 5 (0.00%)".red)
  }

  "Nearly empty list" should "show error message with less than 50% success rate" in {
    // Given
    val array: Array[Option[String]] = Array(Some("None"), Some("None"), None, None, None)

    // Then
    getStat(array, "pineapples") must be(s"Too much errors while reading pineapples -> read 2 pineapples out of 5 (40.00%)".red)
  }

  "Nearly full list" should "show warning message with less than 75% success rate" in {
    // Given
    val array: Array[Option[String]] = Array(Some("None"), Some("None"), Some("None"), None, None)

    // Then
    getStat(array, "apricots") must be(s"Successfully read 3 apricots out of 5 (60.00%)\nWarning: Success rate lower than 75%".yellow)
  }

  "Full list" should "show success message with more than 75% success rate" in {
    // Given
    val array: Array[Option[String]] = Array(Some("None"), Some("None"), Some("None"), Some("None"), Some("None"))

    // Then
    getStat(array, "pears") must be(s"Successfully read 5 pears out of 5 (100.00%)".green)
  }
