import java.lang.Thread.sleep

class Engine() extends Runnable{
  var moveMade = false

  def move():Unit = {
    moveMade = true
  }

  override def run(): Unit = {
    while(true){
      while(!moveMade)
        sleep(100)
      moveMade = false
      Gui.notify_()
    }
  }
}
