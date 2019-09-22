import scala.swing._
import event._
import java.awt.Color
import java.awt.geom.{Ellipse2D, Rectangle2D}
import java.awt.image.BufferedImage

import javax.imageio.ImageIO
import java.io.File



class Creature(X: Int, Y: Int, HEALTH: Int, GROUP: String){
  val random = new scala.util.Random

  var (x , y, group) = (X, Y, GROUP)
  var health: Int = HEALTH
  val reproductionThreshold = 100

  def refreshHealth: Unit = health = 1
  def decreaseHealth: Unit = {
    health = health - 1
  }
  def increaseHealth: Unit = {
    health = health + 1
  }

  def move: Unit = group match {
    case "green" => {
      x = x + -1 + random.nextInt(3)
      y = y + -1 + random.nextInt(3)
      increaseHealth
    }
    case "red" => {
      x = x + -1 + random.nextInt(3)
      y = y + -1 + random.nextInt(3)
      decreaseHealth
    }
  }

  def bud: Creature = {
    new Creature(x, y, health,group)
  }
}










object Simulation extends App {

  var creatures: Seq[Creature] = Seq(
    new Creature(300,300,1,"green"),
    new Creature(349,349,100,"red"))

  val panel: Panel = new Panel {

    def clearPanel(graphics: Graphics2D): Unit = {
      graphics.setPaint(Color.white)
      graphics.fill(new Rectangle2D.Double(0, 0, size.width, size.height))
    }

    def showObjects(graphics: Graphics2D): Unit = {
      clearPanel(graphics)

      def color(arg: String): Unit = arg match {
        case "green" => graphics.setPaint(Color.green)
        case "red" => graphics.setPaint(Color.red)
        case _ =>  graphics.setPaint(Color.black)
      }

      for(obj <- creatures) yield {
        obj match{
          case obj: Creature =>
            color(obj.group)
            graphics.fill(new Ellipse2D.Double(obj.x - 2, obj.y - 2, 10, 10))
          case _ => println(s"Something else")
        }
      }
    }

    override def paint(graphics: Graphics2D): Unit = {
      showObjects(graphics)
    }

    preferredSize = new Dimension(1920, 1080)

  }

  val window = new MainFrame {
    title = "Simulation"
    contents = panel
    centerOnScreen()
  }






  val system = new javax.swing.Timer(10, Swing.ActionListener(
    action => {
      for (creature <- creatures) yield {
        creature.move
      }
      val newCreatures = for {
          creature <- creatures
          if creature.health == creature.reproductionThreshold
        } yield {
          creature.refreshHealth
          creature.bud
        }

      creatures = creatures.filter(_.health > 0) ++ newCreatures

      panel.repaint()
    }
  ))

  window.open()
  system.start()
}

