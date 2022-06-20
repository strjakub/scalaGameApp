import javafx.scene.input.KeyEvent
import scalafx.application.{JFXApp3, Platform}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.{GridPane, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.{Black, Blue, Green, Red, White, Yellow}
import scalafx.scene.shape.Rectangle
import scalafx.scene.text.Font

object Gui extends JFXApp3{
  val engine = new Engine()
  var board: Board = new Board(engine)
  var vector: Vector = Vector(-1,-1)
  var grid: GridPane = new GridPane()
  var size: Int = 500
  var teamId: Int = -1
  var someoneJumped: Boolean = false

  def notify_(): Unit = {
    Platform.runLater{
      if(teamId == 1)
        teamId = -3
      teamId = teamId + 2
      grid = paint()
    }
  }

  def notify_nothing(): Unit = {
    Platform.runLater{
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
          val pawn = board.getPawnAt(Vector(i, j)).getOrElse(Pawn(Vector(-1,-1),0, jumped=false))
          val circle = new scalafx.scene.shape.Circle(new javafx.scene.shape.Circle(size/8*i+size/16, size/8*j+size/16, size/16))
          pawn.id match{
            case 1 => circle.setFill(Blue)
            case -1 => circle.setFill(Yellow)
          }
          if(pawn.jumped) {
            circle.setFill(Green)
            someoneJumped = true
            circle.setOnMouseClicked(_ => {
            vector = Vector(i, j)
            grid = paint()})
          }
          if(pawn.id * teamId > 0 && !someoneJumped) {
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

  def makeGame(): Unit = {
    stage = new JFXApp3.PrimaryStage{
      scene = new Scene(size, size){
        paint()
        content = grid
        onKeyPressed = (ke: KeyEvent) => {
          board.makePawnMove(vector, ke.getCode)
          someoneJumped = false
          vector = Vector(-1, -1)
        }
      }
    }
  }

  def setButton(button: Button): Unit = {
    button.setMinWidth(150)
    button.setMaxWidth(150)
    button.setMinHeight(50)
    button.setMaxHeight(50)
    button.setFont(new Font(20))
  }

  override def start(): Unit = {
    size = size - size%8
    val thread = new Thread(engine)
    thread.start()
    for{i <- 0 until 8}{
      grid.addRow(i)
      grid.addColumn(i)
    }
    val firstMode = new Button("single row")
    setButton(firstMode)
    firstMode.setOnMouseClicked(_ => {
      board.prepare1()
      makeGame()
    })
    val secondMode = new Button("two rows")
    setButton(secondMode)
    secondMode.setOnMouseClicked(_ => {
      board.prepare2()
      makeGame()
    })
    val thirdMode = new Button("scattered")
    setButton(thirdMode)
    thirdMode.setOnMouseClicked(_ => {
      board.prepare3()
      makeGame()
    })
    val vBox = new VBox(10)
    vBox.getChildren.addAll(firstMode, secondMode, thirdMode)
    vBox.setPadding(new Insets(new javafx.geometry.Insets(150, 175, 150, 175)))
    stage = new JFXApp3.PrimaryStage{
      scene = new Scene(size, size){
        content = vBox
      }
    }
  }
}
