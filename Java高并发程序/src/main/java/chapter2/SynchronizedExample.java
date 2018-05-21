package chapter2;

public class SynchronizedExample implements Runnable {

  static int i = 0;

  public synchronized static void increase() {
    i++;
  }

  @Override
  public void run() {
    for (int j = 0; j < 10000000; j++) {
      increase();
    }
  }

  public static void main(String[] args) throws InterruptedException {
    SynchronizedExample instance = new SynchronizedExample();
    Thread t1 = new Thread(instance);
    Thread t2 = new Thread(new SynchronizedExample());
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    System.out.println(i);

  }
}
