import scala.swing._
import event._
import java.awt.Color
import java.awt.geom.{Ellipse2D, Rectangle2D}
import java.awt.image.BufferedImage

import java.awt.Font
import javax.imageio.ImageIO
import java.io.File

abstract class Creature{
  def move()
  def health: Int
  def bud: Creature
}

case class Prey(X: Int, Y: Int) extends Creature {
  val random = new scala.util.Random
  var (x , y, health) = (X, Y, 1)

  def move(): Unit = if(health > 0) {
    x = x + -1 + random.nextInt(3)
    y = y + -1 + random.nextInt(3)
    health = health + 1
  } else {}

  def bud: Prey = {
    health = 1
    Prey(x, y)
  }
}

case class Predator(X: Int, Y: Int) extends Creature{
  val random = new scala.util.Random
  var (x , y, health) = (X, Y, 1000)

  def move(): Unit = if(health > 0) {
    x = x + -1 + random.nextInt(3)
    y = y + -1 + random.nextInt(3)
    health = health - 1
  } else {}

//  def bud(prey: Prey): Predator = {
//    health = health + prey.health
//    Predator(prey.x, prey.y)
//  }
  def bud: Predator = {
    Predator(x,y)
  }
}

object Simulation extends App {

  var creatures: Vector[Creature] = Vector(
    Prey(300,300),
    Predator(349,349))

  def creaturesSize: Int = creatures.size

  val panel: Panel = new Panel {

    def clearPanel(graphics: Graphics2D): Unit = {
      graphics.setPaint(Color.white)
      graphics.fill(new Rectangle2D.Double(0, 0, size.width, size.height))
    }

    def drawInformation(graphics: Graphics2D): Unit = {
      graphics.setColor(new Color(0, 128, 0)) // a darker green
      graphics.setFont(new Font("Batang", Font.PLAIN, 20))
      graphics.drawString(s"Number of creatures: $creaturesSize", 30, 30)
    }

    def showObjects(graphics: Graphics2D): Unit = {
      clearPanel(graphics)
      drawInformation(graphics)
      for(obj <- creatures) yield {
        obj match{
          case obj: Prey => {
            graphics.setPaint(Color.green)
            graphics.fill(new Rectangle2D.Double(obj.x - 2, obj.y - 2, 10, 10))
          }
          case obj: Predator => {
            graphics.setPaint(Color.red)
            graphics.fill(new Rectangle2D.Double(obj.x - 2, obj.y - 2, 10, 10))
          }
        }
      }
    }

    override def paint(graphics: Graphics2D): Unit = {
      showObjects(graphics)
    }

    preferredSize = new Dimension(700, 700)

  }

  val window = new MainFrame {
    title = "Simulation"
    contents = panel
    centerOnScreen()
  }






  val system = new javax.swing.Timer(100, Swing.ActionListener(
    action => {
      for (creature <- creatures) yield {
        creature.move()
      }
      lazy val newCreatures = for {
          creature <- creatures
          if creature.health == 100
        } yield {
          creature.bud
        }

      creatures = creatures.filter(_.health > 0) ++ newCreatures

      panel.repaint()
    }
  ))

  window.open()
  system.start()
}

