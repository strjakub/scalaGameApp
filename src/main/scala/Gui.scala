import javafx.scene.input.KeyEvent
import scalafx.application.{JFXApp3, Platform}
import scalafx.scene.Scene
import scalafx.scene.layout.GridPane
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.{Black, Blue, Red, White, Yellow}
import scalafx.scene.shape.Rectangle

object Gui extends JFXApp3{
  val engine = new Engine()
  var board: Board = new Board(engine)
  board.prepare()
  var vector: Vector = Vector(-1,-1)
  var grid: GridPane = new GridPane()
  var size: Int = 500
  var teamId: Int = -1

  def notify_(): Unit = {
    Platform.runLater{
      if(teamId == 3)
        teamId = -5
      teamId = teamId + 2
      grid = paint()
    }
  }

  def rect(xr: Int, yr: Int, color: Color): Rectangle = new Rectangle {
    x = xr
    y = yr
    width = size/8
    height = size/8
    fill = color
  }

  def paint(): GridPane = {
    for{i <- 0 until 8}{
      for{j <- 0 until 8}{
        if(i == vector.x && j ==vector.y)
          grid.add(rect(size/8*i, size/8*j, Red), j, i)
        else if(i%2 == j%2)
          grid.add(rect(size/8*i, size/8*j, Black), j, i)
        else
          grid.add(rect(size/8*i, size/8*j, White), j, i)
        if(board.getPawnAt(Vector(i, j)).isDefined) {
          val pawn = board.getPawnAt(Vector(i, j)).getOrElse(Pawn(Vector(-1,-1),0))
          val circle = new scalafx.scene.shape.Circle(new javafx.scene.shape.Circle(size/8*i+size/16, size/8*j+size/16, size/16))
          pawn.id match{
            case 1 => circle.setFill(Blue)
            case -1 => circle.setFill(Yellow)
          }
          if(pawn.id * teamId > 0) {
            circle.setOnMouseClicked(_ => {
              vector = Vector(i, j)
              grid = paint()
            })
          }
          grid.add(circle, j, i)
        }
      }
    }
    grid
  }

  override def start(): Unit = {
    size = size - size%8
    val thread = new Thread(engine)
    thread.start()
    for{i <- 0 until 8}{
      grid.addRow(i)
      grid.addColumn(i)
    }
    grid = paint()
    stage = new JFXApp3.PrimaryStage{
      scene = new Scene(size, size){
        content = grid
        onKeyPressed = (ke: KeyEvent) => {
          board.makePawnMove(vector, ke.getCode)
          vector = Vector(-1, -1)
        }
      }
    }
  }
}
