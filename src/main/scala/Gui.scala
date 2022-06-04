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

  def notify_(): Unit = {
    Platform.runLater{
      grid = paint(grid)
      print("x")
    }
  }

  def rect(xr: Int, yr: Int, color: Color): Rectangle = new Rectangle {
    x = xr
    y = yr
    width = 50
    height = 50
    fill = color
  }

  def paint(grid: GridPane): GridPane = {
    for{i <- 0 until 8}{
      for{j <- 0 until 8}{
        if(i == vector.x && j ==vector.y)
          grid.add(rect(50*i, 50*j, Red), j, i)
        else if(i%2 == j%2)
          grid.add(rect(50*i, 50*j, Black), j, i)
        else
          grid.add(rect(50*i, 50*j, White), j, i)
        if(tab(i)(j) == 1) {
          val circle = new scalafx.scene.shape.Circle(new javafx.scene.shape.Circle(50.0*i+25.0, 50.0*j+25.0, 25.0, Blue))
          circle.setOnMouseClicked(event => {
            vector = Vector(i, j)
            println(vector.x + " " + vector.y)
          })
          grid.add(circle, j, i)
        }
        if(tab(i)(j) == 2) {
          val circle = new scalafx.scene.shape.Circle(new javafx.scene.shape.Circle(50.0*i+25.0, 50.0*j+25.0, 25.0, Yellow))
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
    val engine = new Engine()
    val thread = new Thread(engine)
    thread.start()
    for{i <- 0 until 8}{
      grid.addRow(i)
      grid.addColumn(i)
    }
    grid = paint(grid)

    stage = new JFXApp3.PrimaryStage{
      scene = new Scene(400, 400){
        content = grid
      }
    }
  }
}
