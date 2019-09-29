case class Predator(X: Int, Y: Int) extends Creature{
  val random = new scala.util.Random
  var (x , y, health) = (X, Y, 1000)

  def move(): Unit = if(health > 0) {
    x = x + -1 + random.nextInt(3)
    y = y + -1 + random.nextInt(3)
    health = health - 1
  } else {}

  def bud: Predator = {
    Predator(x,y)
  }
}
