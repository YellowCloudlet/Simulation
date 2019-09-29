import java.awt.{Color, Font}
import java.awt.geom.Rectangle2D
import javax.swing.Timer
import scala.swing.{Dimension, Graphics2D, Panel, Swing}

//need to be refactored to Renderer
object System {

  var creatures: Vector[Creature] = Vector(Prey(scala.util.Random.nextInt(700),scala.util.Random.nextInt(700)),
                                           Prey(scala.util.Random.nextInt(700),scala.util.Random.nextInt(700)),
                                           Prey(scala.util.Random.nextInt(700),scala.util.Random.nextInt(700)),
                                           Predator(349,349))
  var iterations: Int = 0

  lazy val panel: Panel = new Panel {

    def clearPanel(graphics: Graphics2D): Unit = {
      graphics.setPaint(Color.black)
      graphics.fill(new Rectangle2D.Double(0, 0, size.width, size.height))
    }

    def drawInformation(graphics: Graphics2D, cSize: Int, i: Int): Unit = {
      val rb = new java.awt.Robot()
      val aPixelColor = rb.getPixelColor(349, 349)
      graphics.setColor(new Color(0, 128, 0)) // a darker green
      graphics.setFont(new Font("DIALOG", Font.PLAIN, 12))
      graphics.drawString(s"Number of creatures: $cSize", 3, 15)
      graphics.drawString(s"Iteration: $i", 3, 30)
      graphics.drawString(s"color: $aPixelColor", 3, 45)
      iterations = iterations + 1
    }

    def showObjects(graphics: Graphics2D): Unit = {
      //clearPanel(graphics)
      drawInformation(graphics, creatures.size, iterations)
      for(obj <- creatures) yield {
        obj match{
          case obj: Prey =>
            graphics.setPaint(new Color(obj.health,252,0))
            graphics.fill(new Rectangle2D.Double(obj.x - 2, obj.y - 2, 1, 1))
          case obj: Predator =>
            graphics.setPaint(new Color(scala.math.abs(255-obj.health)/3,0,0))
            graphics.fill(new Rectangle2D.Double(obj.x - 2, obj.y - 2, 1, 1))
        }
      }
    }

    override def paint(graphics: Graphics2D): Unit = {
      showObjects(graphics)
    }

    preferredSize = new Dimension(700, 700)
  }

  lazy val system: Timer = new javax.swing.Timer(10, Swing.ActionListener(
    action => {
      for (creature <- creatures) yield creature.move()
      lazy val newCreatures = for {creature <- creatures; if creature.health == 100
      } yield creature.bud
      creatures = creatures.filter(_.health > 0) ++ newCreatures
      panel.repaint()
    }
  ))

}