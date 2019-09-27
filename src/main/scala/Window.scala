import System.panel
import scala.swing.MainFrame

object Window {
  lazy val window: MainFrame = new MainFrame {
    title = "Simulation"
    contents = panel
    centerOnScreen()
  }
}