package chapter5.pcpattern;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class Consumer implements Runnable {


  private BlockingQueue<PCData> queue;

  public Consumer(BlockingQueue<PCData> queue) {
    this.queue = queue;
  }


  @Override
  public void run() {
    try {
      while (true) {
        PCData data = queue.take();
        if (null != data) {
          int res = data.getIntData() * data.getIntData();
          System.out.println("result is : " + res);
          Thread.sleep(ThreadLocalRandom.current().nextInt(1000));
        }

      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
