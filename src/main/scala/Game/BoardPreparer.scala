package Game

class BoardPreparer(board: Board) {

  def prepareOneRow(player1: Player, player2: Player): Unit = {
    for(i <- 0  to 7){
      val pawn1 = Pawn(Vector(7, i), player1, jumped=false)
      val pawn2 = Pawn(Vector(0, i), player2, jumped=false)
      board.board.put(pawn1.position, pawn1)
      board.board.put(pawn2.position, pawn2)
    }
  }

  def prepareTwoRows(player1: Player, player2: Player): Unit = {
    prepareOneRow(player1, player2)
    for(i <- 0  to 7){
      val pawn1 = Pawn(Vector(6, i), player1, jumped=false)
      val pawn2 = Pawn(Vector(1, i), player2, jumped=false)
      board.board.put(pawn1.position, pawn1)
      board.board.put(pawn2.position, pawn2)
    }
  }

  def prepareScatteredRows(player1: Player, player2: Player): Unit = {
    for(i <- 0  to 7){
      for(j <- 0 to 7){
        if(j > 4 && j%2 == i%2) {
          val pawn1 = Pawn(Vector(j, i), player1, jumped = false)
          board.board.put(pawn1.position, pawn1)
        }
        else if(j < 3 && j%2 == i%2) {
          val pawn1 = Pawn(Vector(j, i), player2, jumped = false)
          board.board.put(pawn1.position, pawn1)
        }
      }
    }
  }

  def prepare1v1(player1: Player, player2: Player): Unit = {
    val pawn1 = Pawn(Vector(0, 3), player2, jumped = false)
    board.board.put(pawn1.position, pawn1)
    val pawn2 = Pawn(Vector(7, 4), player1, jumped = false)
    board.board.put(pawn2.position, pawn2)
  }
}

object BoardPreparer {
  def apply(board: Board): BoardPreparer = new BoardPreparer(board)
}

