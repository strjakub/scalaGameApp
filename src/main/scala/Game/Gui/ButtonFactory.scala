package Game.Gui
import Game.Gui.GameType._
import scalafx.scene.control.Button
import scalafx.scene.text.Font

class ButtonFactory() {

  def createModeButton(gameType: GameType): Button ={
    var button: Button = new Button()
    gameType match {
      case OneRow => button = new Button("Single row")
      case TwoRows => button = new Button("Two rows")
      case Scattered => button = new Button("Scattered")
      case OneVOne => button = new Button("1 v 1")
    }
    setButtonSize(button)
    button
  }

  def createRestartButton(): Button = {
    val button:Button = new Button("Restart")
    setButtonSize(button)
    button
  }

  def setButtonSize(button: Button): Unit = {
    button.setMinWidth(150)
    button.setMaxWidth(150)
    button.setMinHeight(50)
    button.setMaxHeight(50)
    button.setFont(new Font(20))
  }
}

object ButtonFactory {
  def apply(): ButtonFactory = new ButtonFactory()
}
