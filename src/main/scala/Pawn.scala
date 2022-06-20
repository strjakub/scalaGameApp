case class Pawn(var position:Vector, id:Int,var jumped:Boolean, var finished:Boolean=false){
  def checkFinish(xValue:Int): Unit ={
    if((xValue == 8 && id == 1) || (xValue == -1 && id == -1)){
      finished = true
    }
  }
}


