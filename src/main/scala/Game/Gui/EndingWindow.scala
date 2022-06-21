package Game.Gui
import Game.Gui.Gui._
import Game._
import scalafx.geometry.Insets
import scalafx.scene.control.Label
import scalafx.scene.layout.VBox

class EndingWindow {
  def createBox(player: Player): VBox ={

    val winnerLabel = new Label(player.name + " won!")
    val restartButton = buttonFactory.createRestartButton()
    restartButton.setOnMouseClicked(_ => {
      start()
    })

    val box = new VBox(10)
    box.setPadding(new Insets(new javafx.geometry.Insets(175, 175, 175, 175)))
    box.getChildren.addAll(winnerLabel, restartButton)
    box
  }
}

object EndingWindow {
  def apply(): EndingWindow = new EndingWindow()
}

