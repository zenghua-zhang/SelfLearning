package chapter2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemphoreDemo {

  final Semaphore semaphore = new Semaphore(5, true);

  class MyThread implements Runnable {


    MyThread() {
    }

    @Override
    public void run() {
      try {
        semaphore.acquire();
        Thread.sleep(2000);

        System.out.println(System.currentTimeMillis() + " done!");
        semaphore.release();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    SemphoreDemo demo = new SemphoreDemo();
    Runnable task = demo.new MyThread();
    ExecutorService service = Executors.newFixedThreadPool(20);
    for (int i = 0; i < 20; i++) {
      service.submit(task);
    }
    service.shutdown();
  }
}
