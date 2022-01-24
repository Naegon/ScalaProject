package utils

object Extensions {
  extension (str: String) {
    def isValidDouble: Boolean = str.matches("(^(-*[0-9])+\\.[0-9]+$)")
    
    def noneIfBlank: Option[String] = if (str.isBlank) None else Some(str)
  
  }
}
