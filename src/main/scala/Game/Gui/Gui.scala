package Game.Gui

import Game._
import javafx.scene.input.KeyEvent
import scalafx.application.{JFXApp3, Platform}
import scalafx.scene.Scene
import scalafx.scene.layout.{GridPane, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.{Black, Blue, Green, Orange, Red, White}
import scalafx.scene.shape.Rectangle

object Gui extends JFXApp3 {
  val engine: Engine = Engine()
  var board: Board = Board(engine)
  var vector: Vector = Vector(-1, -1)
  var grid: GridPane = new GridPane()
  var size: Int = 500
  var teamId: Int = -1
  var someoneJumped: Boolean = false
  val buttonFactory: ButtonFactory = ButtonFactory()
  val boardPreparer: BoardPreparer = BoardPreparer(board)

  def notify_(): Unit = {
    Platform.runLater {
      if (teamId == 1)
        teamId = -3
      teamId = teamId + 2
      grid = paint()
    }
  }

  def notify_nothing(): Unit = {
    Platform.runLater {
      grid = paint()
    }
  }

  def notify_endgame(player: Player): Unit = {
    Platform.runLater {
      setStageBox(EndingWindow().createBox(player))
    }
  }

  def paint(): GridPane = {
    for {i <- 0 until 8} {
      for {j <- 0 until 8} {
        if (i == vector.x && j == vector.y)
          grid.add(rect(size / 8 * i, size / 8 * j, Red), j, i)
        else if (i % 2 == j % 2)
          grid.add(rect(size / 8 * i, size / 8 * j, Black), j, i)
        else
          grid.add(rect(size / 8 * i, size / 8 * j, White), j, i)
        if (board.getPawnAt(Vector(i, j)).isDefined) {
          val pawn = board.getPawnAt(Vector(i, j)).getOrElse(Pawn(Vector(-1, -1), Player("", 0, -1), jumped = false))
          val circle = new scalafx.scene.shape.Circle(new javafx.scene.shape.Circle(size / 8 * i + size / 16, size / 8 * j + size / 16, size / 16))
          pawn.player.id match {
            case 1 => circle.setFill(Blue)
            case -1 => circle.setFill(Orange)
          }
          if (pawn.jumped) {
            circle.setFill(Green)
            someoneJumped = true
            circle.setOnMouseClicked(_ => {
              vector = Vector(i, j)
              grid = paint()
            })
          }
          if (pawn.player.id * teamId > 0 && !someoneJumped) {
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
    stage = new JFXApp3.PrimaryStage {
      scene = new Scene(size, size) {
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


  override def start(): Unit = {
    size = size - size % 8

    val thread = new Thread(engine)
    thread.start()

    prepareGrid()
    setStageBox(StartingWindow().createBox())
  }


  def setStageBox(box: VBox): Unit = {
    stage = new JFXApp3.PrimaryStage {
      scene = new Scene(size, size) {
        content = box
      }
    }
  }

  def prepareGrid(): Unit = {
    for {i <- 0 until 8} {
      grid.addRow(i)
      grid.addColumn(i)
    }
  }

  def rect(xr: Int, yr: Int, color: Color): Rectangle = new Rectangle {
    x = xr
    y = yr
    width = size / 8
    height = size / 8
    fill = color
  }
}
