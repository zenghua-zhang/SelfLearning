package chapter3;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class CyclicBarrierExample {

  static class MainClass implements Runnable {


    @Override
    public void run() {
      System.out.println("Main Thread received message.");
    }
  }

  static class SubClass implements Runnable {

    CyclicBarrier cyclicBarrier;

    public SubClass(CyclicBarrier cyclicBarrier) {
      this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
      try {
        String name = Thread.currentThread().getName();
        System.out.println(name + " start");
        Thread.sleep(ThreadLocalRandom.current().nextInt(10) * 500);
        System.out.println(name + " finish phase 1");
        cyclicBarrier.await();
        Thread.sleep(ThreadLocalRandom.current().nextInt(10) * 500);
        System.out.println(name + " finish phase 2");
        cyclicBarrier.await();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (BrokenBarrierException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    CyclicBarrier cyclicBarrier = new CyclicBarrier(10, new MainClass());
    CyclicBarrierExample demo = new CyclicBarrierExample();

//    for (int i = 0; i < 10; i++) {
//      new Thread(new SubClass(cyclicBarrier)).start();
//    }

    Runnable main = new SubClass(cyclicBarrier);
    ExecutorService service = Executors.newFixedThreadPool(10);
    for (int i = 0; i < 10; i++) {
      service.submit(main);
    }
    service.shutdown();

  }


}
