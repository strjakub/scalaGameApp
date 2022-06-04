import javafx.scene.input.KeyCode

import scala.collection.mutable


class Board {
  var board:mutable.HashMap[Vector, Pawn] = new mutable.HashMap()

  def prepare(): Unit = {
    for(i <- 0  to 7){
      val pawn1 = Pawn(Vector(0, i), 1)
      val pawn2 = Pawn(Vector(7, i), 2)
      board.put(pawn1.position, pawn1)
      board.put(pawn2.position, pawn2)
    }
  }

  def getPawnAt(vector: Vector): Option[Pawn] ={
    board.get(vector)
  }

  def makePawnMove(vector: Vector, command: KeyCode): Unit ={
    val pawn : Pawn = board.remove(vector) match {
      case Some(value: Pawn) => value
      case _ => Pawn(Vector(-1,-1), 0)
    }
    command match {
      case KeyCode.UP => checkJumps(pawn, 'x', -1)
      case KeyCode.DOWN => checkJumps(pawn, 'x', 1)
      case KeyCode.RIGHT => checkJumps(pawn, 'y', 1)
      case KeyCode.LEFT => checkJumps(pawn, 'y', -1)
      case _ =>
    }
    board.put(pawn.position, pawn)
  }


  def outOfMapValidationMove(pawn: Pawn, axis: Char, value: Int): Unit ={
    axis match{
      case 'x' => if (7 >= pawn.position.x + value && pawn.position.x + value >= 0){
        pawn.position.x += value
      }
      case 'y' => if (7 >= pawn.position.y + value && pawn.position.y + value >= 0){
        pawn.position.y += value
      }
    }
  }

  def checkJumps(pawn: Pawn, axis: Char, value: Int): Unit ={
    val oldVector = Vector(pawn.position.x, pawn.position.y)
    outOfMapValidationMove(pawn, axis, value)
    if (getPawnAt(pawn.position).isDefined){
      outOfMapValidationMove(pawn, axis, value)
    }
    if (getPawnAt(pawn.position).isDefined){
      pawn.position = oldVector
    }
  }
}
