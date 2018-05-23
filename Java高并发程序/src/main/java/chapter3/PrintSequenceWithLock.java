package chapter3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程用ReentrantLock依次打印数字
 */
public class PrintSequenceWithLock {

  private static int i = 1;

  class LockExample implements Runnable {

    private int threadNum;

    private int max;

    private ReentrantLock lock;

    private int threadSize;

    public LockExample(int threadNum, int max, ReentrantLock lock, int threadSize) {
      this.threadNum = threadNum;
      this.max = max;
      this.lock = lock;
      this.threadSize = threadSize;
    }


    @Override
    public void run() {
      try {
        while (true) {
          lock.lock();
          //System.out.println("Thread " + threadNum + " got lock");

          if (i > max) {
            break;
          }
          if (i % threadSize == threadNum % threadSize) {
            System.out.println(i++);
          }
          lock.unlock();
        }
      } finally {
        lock.unlock();
      }
    }
  }

  public static void main(String[] args) {
    PrintSequenceWithLock example = new PrintSequenceWithLock();

    int max = 20;
    int threadSize = 5;

    List<Thread> list = new ArrayList<>(threadSize);

    ReentrantLock lock = new ReentrantLock(true);
    for (int j = 1; j <= threadSize; j++) {
      list.add(new Thread(example.new LockExample(j, max, lock, threadSize)));
    }

    for (Thread thread : list) {
      thread.start();

    }
  }


}
