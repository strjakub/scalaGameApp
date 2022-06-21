package Game.Gui

import Game.Gui.GameType._
import Game.Gui.Gui.{boardPreparer, buttonFactory, engine}
import scalafx.geometry.Insets
import scalafx.scene.control.{Label, TextField}
import scalafx.scene.layout.VBox

class StartingWindow {
  def createBox(): VBox ={
    val label1 = new Label("First player name:")
    val label2 = new Label("Second player name:")

    val textField1 = new TextField()
    val textField2 = new TextField()

    val firstMode = buttonFactory.createModeButton(OneRow)
    val secondMode = buttonFactory.createModeButton(TwoRows)
    val thirdMode = buttonFactory.createModeButton(ScatteredRows)

    firstMode.setOnMouseClicked(_ => {
      engine.setPlayers(textField1.getText(), textField2.getText(), 8)
      boardPreparer.prepareOneRow(engine.player1, engine.player2)
      Gui.makeGame()
    })
    secondMode.setOnMouseClicked(_ => {
      engine.setPlayers(textField1.getText(), textField2.getText(), 16)
      boardPreparer.prepareTwoRows(engine.player1, engine.player2)
      Gui.makeGame()
    })
    thirdMode.setOnMouseClicked(_ => {
      engine.setPlayers(textField1.getText(), textField2.getText(), 12)
      boardPreparer.prepareScatteredRows(engine.player1, engine.player2)
      Gui.makeGame()
    })

    val vBox = new VBox(10)
    vBox.getChildren.addAll(label1, textField1, label2, textField2, firstMode, secondMode, thirdMode)
    vBox.setPadding(new Insets(new javafx.geometry.Insets(100, 175, 100, 175)))
    vBox
  }
}

object StartingWindow {
  def apply(): StartingWindow = new StartingWindow()
}
