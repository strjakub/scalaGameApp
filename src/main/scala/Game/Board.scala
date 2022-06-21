package Game

import javafx.scene.input.KeyCode

import scala.collection.mutable

class Board private (engine: Engine) {
  var board:mutable.HashMap[Vector, Pawn] = new mutable.HashMap()

  def getPawnAt(vector: Vector): Option[Pawn] ={
    board.get(vector)
  }

  def makePawnMove(vector: Vector, command: KeyCode): Unit =  {
    val pawn : Pawn = board.remove(vector) match {
      case Some(value: Pawn) => value
      case _ => Pawn(Vector(-1,-1), Player("", 0, -1), jumped=false)
    }
    val memoryX = pawn.position.x
    val memoryY = pawn.position.y

    command match {
      case KeyCode.UP => checkJumps(pawn, 'x', -1)
      case KeyCode.DOWN => checkJumps(pawn, 'x', 1)
      case KeyCode.RIGHT => checkJumps(pawn, 'y', 1)
      case KeyCode.LEFT => checkJumps(pawn, 'y', -1)
      case _ =>
    }

    if((memoryX != pawn.position.x || memoryY != pawn.position.y) && pawn.player.id != 0) {
      engine.move()
    }else{
      Gui.Gui.notify_nothing()
    }
    if (!pawn.finished){
      board.put(pawn.position, pawn)
    }
    else {
      pawn.player.numberOfPawns -= 1
      engine.move()
    }
  }


  def outOfMapValidationMove(pawn: Pawn, axis: Char, value: Int): Boolean ={
    axis match{
      case 'x' =>
        if (7 >= pawn.position.x + value && pawn.position.x + value >= 0){
          pawn.position.x += value
          pawn.jumped = false
          return true
        }
        pawn.checkFinish(pawn.position.x+value)
      case 'y' =>
        if (7 >= pawn.position.y + value && pawn.position.y + value >= 0){
          pawn.position.y += value
          pawn.jumped = false
          return true
      }
    }
    false
  }

  def checkJumps(pawn: Pawn, axis: Char, value: Int): Unit ={
    val oldVector = Vector(pawn.position.x, pawn.position.y)
    var jumped = false
    val alreadyHasExtraTurn = pawn.jumped
    outOfMapValidationMove(pawn, axis, value)
    if (getPawnAt(pawn.position).isDefined){
      if(outOfMapValidationMove(pawn, axis, value)) {
        Gui.Gui.teamId -= 2
        jumped = true
        pawn.jumped = true
      }
    }
    if (getPawnAt(pawn.position).isDefined){
      pawn.position = oldVector
      if (jumped) {
        Gui.Gui.teamId += 2
        pawn.jumped = alreadyHasExtraTurn
      }
    }
  }
}

object Board {
  def apply(engine: Engine): Board = new Board(engine)
}
