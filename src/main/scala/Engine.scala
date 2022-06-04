import java.lang.Thread.sleep

class Engine() extends Runnable{
  override def run(): Unit = {
    while(true){
      sleep(700)
      Gui.notify_()
    }
  }
}
