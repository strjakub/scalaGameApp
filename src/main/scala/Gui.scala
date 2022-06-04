import javafx.scene.input.{KeyCode, KeyEvent}
import scalafx.application.{JFXApp3, Platform}
import scalafx.scene.Scene
import scalafx.scene.layout.GridPane
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.{Black, Blue, Red, White, Yellow}
import scalafx.scene.shape.Rectangle

object Gui extends JFXApp3{
  var tab: Array[Array[Int]] = Array(Array(1,1,1,1,1,1,1,1), Array(1,1,1,1,1,1,1,1), Array(0,0,0,0,0,0,0,0), Array(0,0,0,0,0,0,0,0),
    Array(0,0,0,0,0,0,0,0), Array(0,0,0,0,0,0,0,0), Array(2,2,2,2,2,2,2,2), Array(2,2,2,2,2,2,2,2))
  var vector: Vector = Vector(-1,-1)
  var grid: GridPane = new GridPane()
  var size: Int = 500
  var teamId: Int = 1

  def notify_(): Unit = {
    Platform.runLater{
      grid = paint(grid)
    }
  }

  def rect(xr: Int, yr: Int, color: Color): Rectangle = new Rectangle {
    x = xr
    y = yr
    width = size/8
    height = size/8
    fill = color
  }

  def paint(grid: GridPane): GridPane = {
    for{i <- 0 until 8}{
      for{j <- 0 until 8}{
        if(i == vector.x && j ==vector.y)
          grid.add(rect(size/8*i, size/8*j, Red), j, i)
        else if(i%2 == j%2)
          grid.add(rect(size/8*i, size/8*j, Black), j, i)
        else
          grid.add(rect(size/8*i, size/8*j, White), j, i)
        if(tab(i)(j) == 1) {
          val circle = new scalafx.scene.shape.Circle(new javafx.scene.shape.Circle(size/8*i+size/16, size/8*j+size/16, size/16, Blue))
          circle.setOnMouseClicked(event => {
            vector = Vector(i, j)
          })
          grid.add(circle, j, i)
        }
        if(tab(i)(j) == 2) {
          val circle = new scalafx.scene.shape.Circle(new javafx.scene.shape.Circle(size/8*i+size/16, size/8*j+size/16, size/16, Yellow))
          circle.setOnMouseClicked(event => {
            vector = Vector(i, j)
          })
          grid.add(circle, j, i)
        }
      }
    }
    grid
  }

  override def start(): Unit = {
    size = size - size%8
    val engine = new Engine()
    val thread = new Thread(engine)
    thread.start()
    for{i <- 0 until 8}{
      grid.addRow(i)
      grid.addColumn(i)
    }
    grid = paint(grid)
    stage = new JFXApp3.PrimaryStage{
      scene = new Scene(size, size){
        content = grid
        onKeyPressed = (ke: KeyEvent) => {
          ke.getCode match {
            case KeyCode.UP => //tab.pawnAt(vector).move(UP)
            case KeyCode.DOWN => //tab.pawnAt(vector).move(DOWN)
            case KeyCode.RIGHT => //tab.pawnAt(vector).move(RIGHT)
            case KeyCode.LEFT => //tab.pawnAt(vector).move(LEFT)
            case _ =>
          }
          vector = Vector(-1, -1)
        }
      }
    }
  }
}
