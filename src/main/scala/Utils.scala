import Extensions.red
import scala.annotation.tailrec
import scala.io.StdIn.readLine
import Menu.menu

object Utils {
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
}
