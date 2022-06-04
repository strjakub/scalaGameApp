import java.lang.Thread.sleep

class Engine() extends Runnable{
  override def run(): Unit = {
    while(true){

      sleep(300)
      Gui.notify_()
    }
  }
}
