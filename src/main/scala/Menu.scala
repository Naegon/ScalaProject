import scala.io.StdIn.readLine
import Extensions.*

import scala.annotation.tailrec
import Reports._
import Query._
import Utils._

object Menu {
    @tailrec
    def menu(): Unit = {
        println("||=======   Menu   =======||\n")
        println("Welcome to our application")
        println("Please choose one of the following option:")
        println("  1) Query")
        println("  2) Report")

        val input = getUserInput(1 to 2, "Please choose 1 (Query) or 2 (Report)")

        input match {
            case 1 => Query()
            case 2 => Report()
        }
        if continue then menu()
    }
}
