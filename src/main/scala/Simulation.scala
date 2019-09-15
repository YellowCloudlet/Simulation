import scala.swing._
import event._
import java.awt.Color
import java.awt.geom.{Ellipse2D, Rectangle2D}
import java.awt.image.BufferedImage

import javax.imageio.ImageIO
import java.io.File

class Creature(x: Int,
               y: Int,
               reproductive: Boolean,
               illnesses: Boolean,
               Group: String){
  val rnd = new scala.util.Random
  var X: Int = x
  var Y: Int = y
  var health = 1000
  val range = 100
  val repFunction: Boolean = reproductive
  val illFunction: Boolean = illnesses
  val group: String = Group

  def generateReadiness: Boolean = {
    -range + rnd.nextInt(range-70) == -range + rnd.nextInt(range-70)
  }

  def generateIllnesses: Boolean = {
    -range + rnd.nextInt(range-67) == -range + rnd.nextInt(range-67)
  }

  def randomMove: Unit = {
    val moveStep_forX = -1 + rnd.nextInt(3)
    val moveStep_forY = -1 + rnd.nextInt(3)
    X = X + moveStep_forX
    Y = Y + moveStep_forY
    health = health - 1
  }

  def takeHealth: Unit = health = health / 2

  def giveBirth: Creature = {
    new Creature(X, Y, true, true, group)
  }
}










object Simulation extends App {

  var creatures: Seq[Creature] = Seq(
    new Creature(349,349,true,true,"yellow"),
    new Creature(349,349,true,true,"blue"),
    new Creature(349,349,true,true,"green"),
    new Creature(349,349,true,true,"pink"),
    new Creature(349,349,true,true,"red"),
    new Creature(349,349,true,true,"orange"))

  val dots = Array.fill(30)(new java.awt.Point(335, 335))

  val panel: Panel = new Panel {
    def showObjects(g: Graphics2D): Unit = {
      g.setPaint(Color.white)
      g.fill(new Rectangle2D.Double(0, 0, size.width, size.height))

      for(obj <- creatures) yield {
        obj match{
          case obj: Creature =>
            g.setPaint(Color.black)
            g.fill(new Ellipse2D.Double(obj.X - 2, obj.Y - 2, 3, 3))
          case _ => println(s"Something else")
        }
      }
    }

    override def paint(g: Graphics2D): Unit = {
      showObjects(g)
    }
    preferredSize = new Dimension(700, 700)
  }

  val window = new MainFrame {
    title = "Simulation"
    contents = panel
    centerOnScreen()
  } //уже не трогаю

  val temp = creatures.filter(_.generateReadiness)

  val system = new javax.swing.Timer(10, Swing.ActionListener(
    action => {
      for (creature <- creatures) yield {
        creature.randomMove
      }
      val newCreatures = for {
          creature <- creatures
          if creature.repFunction
          if creature.generateReadiness
        } yield {
          creature.takeHealth
          creature.giveBirth
        }

      def za(c: Creature): Boolean = {
        if((c.health <= 0) || (c.illFunction && c.generateIllnesses)){false}
        else{true}
      }

      creatures = creatures.filter(za(_)) ++ newCreatures
//      println(creatures.size)
//      if(creatures.size > 10000) creatures = creatures.slice(0,5000)
      panel.repaint()
    }))

  window.open()
  system.start()
}

