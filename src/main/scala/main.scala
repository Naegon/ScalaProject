
import Menu.menu
import Utils.FILEPATH


object main {
  val SYSTEM: System = System.apply(FILEPATH)

  def main(args: Array[String]): Unit = {
    menu()
  }
}
