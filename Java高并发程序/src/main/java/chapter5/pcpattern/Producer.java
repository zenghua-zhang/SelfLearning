package chapter5.pcpattern;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable {

  private volatile boolean isRunning = true;

  private BlockingQueue<PCData> queue;

  private AtomicInteger count ;

  public Producer(BlockingQueue<PCData> queue, AtomicInteger count) {
    this.queue = queue;
    this.count = count;
  }

  @Override
  public void run() {
    try {
      while (isRunning) {
        Thread.sleep(ThreadLocalRandom.current().nextInt(1000));
        PCData data = new PCData(count.incrementAndGet());
        System.out.println(data + " is put into queue");
        if (!queue.offer(data, 2, TimeUnit.SECONDS)) {
          System.err.println("Putting data error " + data);
        }

      }

    } catch (InterruptedException e) {

    }

  }

  public void stop() {
    isRunning = false;
  }
}
