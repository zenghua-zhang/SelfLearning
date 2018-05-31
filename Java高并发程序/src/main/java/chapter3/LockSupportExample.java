package chapter3;

import java.util.concurrent.locks.LockSupport;

public class LockSupportExample {

  static class MyThread implements Runnable {


    @Override
    public void run() {
      System.out.println("MyThread waiting!");
      LockSupport.park();
      System.out.println("MyThread finish!");
    }
  }

  public static void main(String[] args) {
    Thread thread = new Thread(new MyThread());
    thread.start();
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    LockSupport.unpark(thread);
  }



}
