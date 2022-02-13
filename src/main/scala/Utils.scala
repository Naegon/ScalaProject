import Extensions.red

import scala.annotation.tailrec
import scala.io.StdIn.readLine
import Menu.menu

import scala.util.matching.Regex

object Utils {
  val FILEPATH = "src/main/Resources/"
  
  @tailrec
  def getUserInput(acceptedRange: Range, errorMessage: String): Int = {
    print("\nYour choice: ")
    val input = readLine()
    if (input != "" && (input forall Character.isDigit) && (acceptedRange contains input.toInt)) input.toInt
    else {
      print("Invalid input -> ".red + errorMessage + "\n")
      getUserInput(acceptedRange, errorMessage)
    }
  }

  def continue: Boolean = {
    print("\n\n")
    print("Would you like to go back to the menu (1) or end the program (2)")
    val choice = getUserInput(1 to 2, "Select 1 get back to the menu or 2 to quit")

    print("\n\n")

    choice == 1
  }

  val asphalt: Regex =    "(?ix).*asp.*".r
  val gravel: Regex =     "(?ix)(.*grav.* | .*grv.* | .*gvl.*)".r
  val grass: Regex =      "(?ix)(.*grs.* | ^gras.* | ^turf.* | sod)".r
  val water: Regex =      "(?i).*wat.*".r
  val concrete: Regex =   "(?ix)(.*con.* | .*concrete.*)".r
  val unknown: Regex =    "(?ix)(^unk.* | ^u$)".r
  val unpaved: Regex =    "(?i).*unpaved.*".r
  val paved: Regex =      "(?ix)(.*paved.* | .*pav.*)".r
  val silt: Regex =       "(?i).*silt.*".r
  val clay: Regex =       "(?i).*cla.*".r
  val ice: Regex =        "(?i).*ice.*".r
  val sand: Regex =       "(?i).*san.*".r
  val dirt: Regex =       "(?i).*dirt.*".r
  val graded: Regex =     "(?ix)(gre | .*earth.* | .*graded.* | .*soil.* | .*hard.*)".r
  val laterite: Regex =   "(?i)lat".r
  val snow: Regex =       "(?i).*sno.*".r
  val coral: Regex =      "(?ix)(^cor.* | .*coral.*)".r
  val bitume: Regex =     "(?i).*bit.*".r
  val macadam: Regex =    "(?ix)(pem | mac)".r
  val brick: Regex =      "(?i)^bri.*".r
  val ground: Regex =     "(?i).*ground.*".r
  val composite: Regex =  "(?ix)(.*com.* | (?i)cop)".r
  val rock: Regex =       "(?i).*rock.*".r
  val wood: Regex =       "(?i).*wood.*".r
  val permanent: Regex =  "(?ix)per|(?i)pam".r
  val metal: Regex =      "(?ix)met.*|(?i)mtal".r
  val tarmac: Regex =     "(?i).*tar.*".r
  val treated: Regex =    "(?ix)treated.*|(?i)trtd.*".r
  val steel: Regex =      "(?i).*steel.*".r
  val oil: Regex =        "(?i)oil.*".r
  val aluminium: Regex =  "(?i)alum.*".r
  val volcanic: Regex =   "(?i)volcanic.*".r
  val rooftop: Regex =    "(?i)roof.*".r
  val caliche: Regex =    "(?i)caliche".r
  val mats: Regex =       "(?i)mats.*".r
  val seal: Regex =       "(?i)sealed".r
  val unseal: Regex =     "(?i)unsealed".r
  val neoprene: Regex =   "(?i)neoprene".r
  val grain: Regex =      "(?i)grain".r
  val deck: Regex =       "(?i)deck".r
  val loam: Regex =       "(?i).*loam.*".r
  val mud: Regex =        "(?i).*mud.*".r
  val stone: Regex =      "(?i).*stone.*".r
  val pad: Regex =        "(?i).*pad.*".r
}
