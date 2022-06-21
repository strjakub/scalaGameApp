import java.lang.Thread.sleep

class Engine private extends Runnable{
  var moveMade = false
  var player1: Player = _
  var player2: Player = _

  def setPlayers(name1: String, name2: String, noPawns: Int): Unit = {
    player1 = Player(name1, -1, noPawns)
    player2 = Player(name2, 1, noPawns)
  }

  def move(): Unit = {
    moveMade = true
  }

  override def run(): Unit = {
    while(true){
      while(!moveMade)
        sleep(100)
      moveMade = false
      Gui.notify_()
      if(player1.numberOfPawns == 0)
        Gui.notify_endgame(player1)
      if(player2.numberOfPawns == 0)
        Gui.notify_endgame(player2)
    }
  }
}

object Engine {
  def apply() = new Engine
}
