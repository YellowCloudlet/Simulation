import scala.swing._
import java.awt.{Color, Font}
import java.awt.geom.{Ellipse2D, Rectangle2D}
import scala.util.Random

import util.control.Breaks._


//abstract class Creature{
//  def move()
//  def health: Int
//  def bud: Creature
//}

case class Creature(xy: (Int, Int), g: String = "none"){
  var coordinates: (Int, Int) = xy
  var group: String = g
  var health: Int = 10000
}



//case class Prey(X: Int, Y: Int) extends Creature {
//  val random = new scala.util.Random
//  var (x , y, health) = (X, Y, 1)
//
//  def move(): Unit = if(health > 0) {
//    x = x + -1 + random.nextInt(3)
//    y = y + -1 + random.nextInt(3)
//    health = health + 1
//  } else {}
//
//  def bud: Prey = {
//    health = 1
//    Prey(x, y)
//  }
//}
//
//case class Predator(X: Int, Y: Int) extends Creature{
//  val random = new scala.util.Random
//  var (x , y, health) = (X, Y, 100)
//
//  def move(): Unit = if(health > 0) {
//    x = x + -1 + random.nextInt(3)
//    y = y + -1 + random.nextInt(3)
//    health = health - 1
//  } else {}
//
//  //  def bud(prey: Prey): Predator = {
//  //    health = health + prey.health
//  //    Predator(prey.x, prey.y)
//  //  }
//  def bud: Predator = {
//    Predator(x,y)
//  }
//}

object Simulation extends App {

//  var creatures: Vector[Creature] = Vector(
//    Prey(300,300),
//    Predator(349,349))

  final val start = 199
  final val end = 499

  var creatures: Vector[Creature] = Vector(
    Creature((start + Random.nextInt((end - start) + 1)  ,start + Random.nextInt((end - start) + 1)),"red"),
    Creature((start + Random.nextInt((end - start) + 1)  ,start + Random.nextInt((end - start) + 1)),"red"),
    Creature((start + Random.nextInt((end - start) + 1)  ,start + Random.nextInt((end - start) + 1)),"green"),
    Creature((start + Random.nextInt((end - start) + 1)  ,start + Random.nextInt((end - start) + 1)),"red"),
    Creature((start + Random.nextInt((end - start) + 1)  ,start + Random.nextInt((end - start) + 1)),"green"),
    Creature((start + Random.nextInt((end - start) + 1)  ,start + Random.nextInt((end - start) + 1)),"red"),
    Creature((start + Random.nextInt((end - start) + 1)  ,start + Random.nextInt((end - start) + 1)),"green"),
    Creature((start + Random.nextInt((end - start) + 1)  ,start + Random.nextInt((end - start) + 1)),"red"),
    Creature((start + Random.nextInt((end - start) + 1)  ,start + Random.nextInt((end - start) + 1)),"red"),
    Creature((start + Random.nextInt((end - start) + 1)  ,start + Random.nextInt((end - start) + 1)),"green"),
    Creature((start + Random.nextInt((end - start) + 1)  ,start + Random.nextInt((end - start) + 1)),"red"),
    Creature((start + Random.nextInt((end - start) + 1)  ,start + Random.nextInt((end - start) + 1)),"green"),
    Creature((start + Random.nextInt((end - start) + 1)  ,start + Random.nextInt((end - start) + 1)),"red"),
    Creature((start + Random.nextInt((end - start) + 1)  ,start + Random.nextInt((end - start) + 1)),"green")) // вектор со всеми обьектами

  var new_creatures: Vector[Creature] = Vector.empty

  var iterations = 0

  val panel: Panel = new Panel {  // панель для рисунка контента

    def drawInformation(graphics: Graphics2D, preySize: Int, predatorSize: Int, i: Int): Unit = {
      graphics.setColor(new Color(0, 128, 0)) // a darker green
      graphics.setFont(new Font("DIALOG", Font.PLAIN, 12))
      graphics.drawString(s"Number of prey: $preySize", 3, 15)
      graphics.drawString(s"Number of predator: $predatorSize", 3, 30)
      graphics.drawString(s"Iteration: $i", 3, 45)
      iterations = iterations + 1
    }

    def drawRectWindow(g: Graphics2D): Unit = {
      g.setPaint(Color.blue)
      g.drawLine(200,200,200,500)
      g.drawLine(200,200,500,200)
      g.drawLine(500,200,500,500)
      g.drawLine(200,500,500,500)
    }

    def clearPanel(g: Graphics2D): Unit = {
      g.setPaint(Color.black)
      g.fill(new Rectangle2D.Double(0, 0, size.width, size.height))
      //g.fill(new Rectangle2D.Double(199, 199, 302, 302))
    }     //просто чистит панель

    def showObjects(g: Graphics2D): Unit = {
      clearPanel(g)         //за черный экран
      for(obj <- creatures) obj.group match {
        case "green" =>
            g.setPaint(Color.green)
            g.fill(new Rectangle2D.Double(obj.coordinates._1 - 2, obj.coordinates._2 - 2, 3, 3))
        case "red"   =>
            g.setPaint(Color.red)
            g.fill(new Rectangle2D.Double(obj.coordinates._1 - 2, obj.coordinates._2 - 2, 3, 3))
        case "yellow" =>
            g.setPaint(Color.yellow)
            g.fill(new Rectangle2D.Double(obj.coordinates._1 - 2, obj.coordinates._2 - 2, 13, 13))
        }
    }

    override def paint(g: Graphics2D): Unit = {
      showObjects(g)
      drawInformation(g, creatures.count(_.group == "green"), creatures.count(_.group == "red"), iterations)
      drawRectWindow(g)
    }

    preferredSize = new Dimension(700, 700)

  }

  val window = new MainFrame {
    title = "высер"
    contents = panel
    centerOnScreen()
  }

  def ramki(c: (Int,Int)): Boolean = {
    //if (c._1 < 330 && c._2 < 330 && c._1 > 270 && c._2 > 270) true
    //if (c._1 < 500 && c._2 < 500 && c._1 > 100 && c._2 > 100) true
    if (c._1 < 500 && c._2 < 500 && c._1 > 200 && c._2 > 200) true
    //if (c._1 < 900 && c._2 < 700 && c._1 > 0 && c._2 > 0) true
    else false
  }

  def present(c: (Int, Int)): Boolean = {
    if (creatures.exists(_.coordinates == c) || new_creatures.exists(_.coordinates == c)) true
    else false
  }

  def foundGroup(c: (Int, Int)): String = {
    creatures.find(_.coordinates == c).getOrElse(new_creatures.find(_.coordinates == c).get).group
  }

  def getPrey(c: (Int, Int)): Creature = {
    creatures.find(_.coordinates == c).getOrElse(new_creatures.find(_.coordinates == c).get)
  }

  final val framerate = 1

  val hueta = new javax.swing.Timer(framerate, Swing.ActionListener(
    action => {
      for (creature <- creatures) yield {
        creature.group match {
          case "green" => {
            val x_T_y = (creature.coordinates._1 + -1 + Random.nextInt(3), creature.coordinates._2 + -1 + Random.nextInt(3))
            val shansStatZlim = Random.nextInt(1000)
            if (!present(x_T_y) && ramki(x_T_y)) {  //если клетка свободна и не выходит за рамки то можно двигататся
              if (creature.health >= 10400) {       //если можно ещё и родить //поменять координаты, дать потомство и обновить хитпоинты
                creature.health = 10000                                                            // обновляем хитпоинты
                new_creatures = new_creatures ++ Vector(Creature(creature.coordinates,"green")) //сначала даем потомство на старых координатах
                creature.coordinates = x_T_y                                                      //двигаемся на новые координаты
              }
              else { //если родить нельзя
                creature.coordinates = x_T_y      //двинемся на новые координаты
                creature.health = creature.health + 1     //увеличим хитпоинты
              }
            }
            else if (present(x_T_y) && ramki(x_T_y)) {  //если клетка занята то стоим и при этом можем стать злым
              if(shansStatZlim > 995){
                creature.group = "red"
                creature.coordinates = creature.coordinates
                creature.health = creature.health - 1
              }
              else{
                creature.coordinates = creature.coordinates
                creature.health = creature.health + 1
              }

            }
            else {creature.health = 0}  // в другом случае выводим эмоции на екран
          }
          case "red" => {
            val x_T_y = (creature.coordinates._1 + -1 + Random.nextInt(3), creature.coordinates._2 + -1 + Random.nextInt(3))
            val shansStatvseyadnim = Random.nextInt(100)
            if (!present(x_T_y) && ramki(x_T_y)) {  //если клетка свободна и не выходит за рамки то можно двигататся
                creature.coordinates = x_T_y      //двинемся на новые координаты
                if(creature.health < 50 && shansStatvseyadnim > 75){ // если можно двинутся то есть шанс стать монахом
                  creature.group = "yellow"
                }
                else{creature.health = creature.health - 4}  //уменьшим хитпоинты

            }
            else if (present(x_T_y) && ramki(x_T_y)) {  //если клетка занята то проверим кем, если занята грином то множимся, если редом то просто стоим
              if(foundGroup(x_T_y) == "green") {             //если нашли зеленый то множимся, бустим хитпоинты и стоим
                creature.coordinates = creature.coordinates //постояли
                lazy val gertva = getPrey(x_T_y)          //нашли жертву
                creature.health = creature.health + gertva.health // спиздили хитпоинты
                gertva.group = "red"                  // размножились
                creature.health = creature.health - 1
              }
              else if(foundGroup(x_T_y) == "red") {                               //если нашли красный то стоим и уменьшяем хитпоинты
                creature.coordinates = creature.coordinates
                creature.health = creature.health - 100
              }
              else if(foundGroup(x_T_y) == "yellow"){
                creature.coordinates = creature.coordinates
                creature.health = creature.health + 100
              }
              else{}
            }
            else {creature.health = 0}  // в другом случае выводим эмоции на екран
          }
          case "yellow" => {
            val x_T_y = (creature.coordinates._1 + -1 + Random.nextInt(3), creature.coordinates._2 + -1 + Random.nextInt(3))
            val shansStatGreen = Random.nextInt(100)
            val shansStatRed = Random.nextInt(100)
            if (!present(x_T_y) && ramki(x_T_y)) {  //если клетка свободна и не выходит за рамки то можно двигататся
              if(shansStatGreen > 95){
                creature.group = "green"
                creature.coordinates = x_T_y
              }
              else if(shansStatRed > 99){
                creature.group = "red"
                creature.coordinates = x_T_y
              }
              else{creature.coordinates = x_T_y}
            }
            else if (present(x_T_y) && ramki(x_T_y)) {  //если клетка занята то проверим кем, если занята грином то множимся, если редом то просто стоим
              if(foundGroup(x_T_y) == "green") {             //если нашли зеленый то множимся, бустим хитпоинты и стоим
                creature.health = creature.health + 1
              }
              else if(foundGroup(x_T_y) == "red") {                               //если нашли красный то стоим и уменьшяем хитпоинты
                creature.health = creature.health - 1
              }
              else if(foundGroup(x_T_y) == "yellow") {
                creature.health = creature.health
              }
              else{}
            }
            else {creature.health = 0}  // в другом случае выводим эмоции на екран
          }
        }
      }


      creatures = creatures.filter(_.health > 0)
      creatures = creatures ++ new_creatures


      //test zone//
//      lazy val temp = creatures.filter(_.coordinates == (300,300))
//      println(temp.size)
//      lazy val predators = creatures.filter(_.group == "red")
//      println(predators.size)
      //println(creatures.size)
      //println(creatures(1).health)
      new_creatures = Vector.empty
      panel.repaint()
    }
  ))

  window.open()
  hueta.start()
}