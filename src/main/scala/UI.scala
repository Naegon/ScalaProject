object UI {
    def getInstruction(instruction: String): Int = instruction match {
        //case x if (x.length == 0) => "Requires 1 argument"
        case "1" => 1
        case "2" => 2
        case _ => 0
    }

    def queryAskCountry(country: String): String= country match {
        case x if (x.length <= 1) => "Not enough characters"
        case x if (x.exists{_.isDigit})  => "Digits are not allowed" 
        case x if (x.length == 2) => {
            val code = country.toUpperCase()
            s"Search in Code item: $code"
        }

        case _ => {
            val name = country.toLowerCase() //do same in database
            s"Search in Names item: $name"
        }
    }
}