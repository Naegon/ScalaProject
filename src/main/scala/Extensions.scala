import java.util.regex.Pattern

object Extensions {
  extension (str: String) {
    def isValidDouble: Boolean = str.matches("(^(-*[0-9])+\\.[0-9]+$)")
    
    def toOptionalBool: Option[Boolean] = {
      str match {
        case "yes" => Some(true)
        case "non" => Some(false)
        case _ => None
      }
    }

    def noneIfBlank: Option[String] = if (str.isBlank) None else Some(str)
    
    def removeParenthesis: String = str.replaceAll("[()]", "")
    
    def red: String = Console.RED + str + Console.RESET
    def green: String = Console.GREEN + str + Console.RESET
    def blue: String = Console.BLUE + str + Console.RESET
    def yellow: String = Console.YELLOW + str + Console.RESET
    def bold: String = Console.BOLD + str + Console.RESET

    def highlight(input: String): String = str.replaceAll("(?i)" + Pattern.quote(input), "$0".green)
  }

}
