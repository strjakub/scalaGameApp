case class Pawn(var position: Vector, player:Player ,var jumped: Boolean, var finished: Boolean=false){
  def checkFinish(xValue:Int): Unit = {
    if((xValue == 8 && player.id == 1) || (xValue == -1 && player.id == -1)){
      finished = true
    }
  }
}
