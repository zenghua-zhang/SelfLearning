package chapter3;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class CountDownLatchDemo {

  class MainThread implements Runnable {

    CountDownLatch countDownLatch;

    public MainThread(CountDownLatch countDownLatch) {
      this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
      try {
        System.out.println("Main Thread waiting!!");
        countDownLatch.await();
        System.out.println("Main Thread finished!!");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    }
  }

  class SubThread implements Runnable {

    CountDownLatch countDownLatch;

    public SubThread(CountDownLatch countDownLatch) {
      this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
      try {
        System.out.println(Thread.currentThread().getName() + " start !");
        Thread.sleep(ThreadLocalRandom.current().nextInt(10) * 500);
        System.out.println(Thread.currentThread().getName() + " finished");
        countDownLatch.countDown();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

  }

  public static void main(String[] args) {
    CountDownLatchDemo demo = new CountDownLatchDemo();
    CountDownLatch latch = new CountDownLatch(10);
    Thread main = new Thread(demo.new MainThread(latch));
    main.start();

    ExecutorService service = Executors.newFixedThreadPool(6);
    for (int i = 0; i < 10; i++) {
      service.submit(demo.new SubThread(latch));
    }
    service.shutdown();

//    for (int i = 0; i < 10; i++) {
//      new Thread(demo.new SubThread(latch)).start();
//
//    }
  }

}
