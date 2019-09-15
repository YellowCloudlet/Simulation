import scala.swing._
import event._
import java.awt.Color
import java.awt.geom.{Ellipse2D, Rectangle2D}
import java.awt.image.BufferedImage

import javax.imageio.ImageIO
import java.io.File

object Simulation extends App {

//  val img = {
//   val i = new BufferedImage(200,200,BufferedImage.TYPE_INT_ARGB)
//   val g = i.createGraphics()
//    g.setPaint(Color.blue)
//    g.fillRect(0,0,30,30)
//    i
//  }
//
//  for(x <- 1 to 20; y <- 1 to 20) {
//    img.setRGB(x,y,0xffff0000)
//  }

  //val artImage = ImageIO.read(new File("/home/cooper/Simulation/src/main/images/rabbit.png"))
  //val dots = Array.fill(30)(new java.awt.Point(100+util.Random.nextInt(300), 100+util.Random.nextInt(300)))
  val dots = Array.fill(30)(new java.awt.Point(335, 335))

  val panel = new Panel {
    override def paint(g: Graphics2D): Unit = { //единственное место которое напрягает это вот это, принимает обьект и
      //сразу же его рисует при том что функция paint сама по себе нигде не вызывается. Она по 1 обрабатывает обьекты или
      //же все сразу, если все сразу то тогда как она определяет к какому обьекту доступатся и какую функцию на каком
      //обьекте вызывать?
      //g.drawImage(img,335,335,null)
      //g.drawImage(artImage,0,0,null)
      g.setPaint(Color.white)
      g.fill(new Rectangle2D.Double(0,0,size.width,size.height))
      g.setPaint(Color.black)
      for(p <- dots){
        g.fill(new Ellipse2D.Double(p.x-2,p.y-2,5,5))
      }
    }
    preferredSize = new Dimension(700,700)
  }

  val timer = new javax.swing.Timer(1,Swing.ActionListener(
    ection => {
      for (p <- dots) {
        p.x += util.Random.nextInt(3) - 1
        p.y += util.Random.nextInt(3) - 1
      }
      panel.repaint()
    }))

  val frame = new MainFrame {
    title = "Simulation"
    contents = panel
    centerOnScreen()
  }

frame.open()
timer.start()


//  val frame = new MainFrame {
//    title = "Simulation"
//    contents = Button("Test button")(println("Test button was pressed"))
//    size = new Dimension(500, 500)
//    centerOnScreen
//  }
//
//  frame.visible = true
}