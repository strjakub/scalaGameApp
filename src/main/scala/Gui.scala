import javafx.scene.input.KeyEvent
import scalafx.application.{JFXApp3, Platform}
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, TextField}
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
  var player1:Player = Player("", 0, -100)
  var player2:Player = Player("", 0, -100)


  def notify_(): Unit = {
    Platform.runLater{
      if(player1.numberOfPawns == 0){
        endGame(player1)
      }
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
          val pawn = board.getPawnAt(Vector(i, j)).getOrElse(Pawn(Vector(-1,-1),Player("", 0, -1), jumped=false))
          val circle = new scalafx.scene.shape.Circle(new javafx.scene.shape.Circle(size/8*i+size/16, size/8*j+size/16, size/16))
          pawn.player.id match{
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
          if(pawn.player.id * teamId > 0 && !someoneJumped) {
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
    val label1 = new Label("First player name:")
    val label2 = new Label("Second player name:")
    val textField1 = new TextField()
    val textField2 = new TextField()
    val firstMode = new Button("single row")
    setButton(firstMode)
    firstMode.setOnMouseClicked(_ => {
      player1 = Player(textField1.getText(), 1, 8)
      player2 = Player(textField2.getText(), -1, 8)
      board.prepare1(player1, player2)
      makeGame()
    })
    val secondMode = new Button("two rows")
    setButton(secondMode)
    secondMode.setOnMouseClicked(_ => {
      player1 = Player(textField1.getText(), 1, 16)
      player2 = Player(textField2.getText(), -1, 16)
      board.prepare2(player1, player2)
      makeGame()
    })
    val thirdMode = new Button("scattered")
    setButton(thirdMode)
    thirdMode.setOnMouseClicked(_ => {
      player1 = Player(textField1.getText(), 1, 12)
      player2 = Player(textField2.getText(), -1, 12)
      board.prepare3(player1, player2)
      makeGame()
    })
    val vBox = new VBox(10)
    vBox.getChildren.addAll(label1, textField1, label2, textField2, firstMode, secondMode, thirdMode)
    vBox.setPadding(new Insets(new javafx.geometry.Insets(150, 175, 150, 175)))
    stage = new JFXApp3.PrimaryStage{
      scene = new Scene(size, size){
        content = vBox
      }
    }
  }

  def endGame(player: Player){
    val winnerLabel = new Label(player.name + " won!")
    val restartButton = new Button("Restart")
    setButton(restartButton)
    restartButton.setOnMouseClicked(_ => {
      start()
    })
    val box = new VBox(10)
    box.getChildren.addAll(winnerLabel, restartButton)
    box.setPadding(new Insets(new javafx.geometry.Insets(150, 175, 150, 175)))
    stage = new JFXApp3.PrimaryStage{
      scene = new Scene(size, size){
        content = box
      }
    }
  }
}


