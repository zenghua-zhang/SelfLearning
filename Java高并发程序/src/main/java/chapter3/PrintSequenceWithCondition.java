package chapter3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程用ReentrantLock依次打印数字
 */
public class PrintSequenceWithCondition {

  private static int i = 1;

  class LockExample implements Runnable {

    private int threadNum;

    private int max;

    private ReentrantLock lock;

    private int threadSize;

    private Condition condition;

    public LockExample(int threadNum, int max, ReentrantLock lock, int threadSize, Condition condition) {
      this.threadNum = threadNum + 1;
      this.max = max;
      this.lock = lock;
      this.threadSize = threadSize;
      this.condition = condition;
    }


    @Override
    public void run() {
      try {
        lock.lock();
        while (true) {
          System.out.println("Thread " + threadNum + " got lock");

          if (i > max) {
            break;
          }
          if (i % threadSize == threadNum % threadSize) {
            System.out.println(i++);
            condition.signalAll();
          }
          try {
            condition.await();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      } finally {
        System.out.println("Thread " + threadNum + " unlock");
        condition.signalAll();
        lock.unlock();
      }
    }
  }

  public static void main(String[] args) {
    PrintSequenceWithCondition example = new PrintSequenceWithCondition();

    int max = 20;
    int threadSize = 5;

    List<Thread> list = new ArrayList<>(threadSize);

    ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    for (int j = 1; j <= threadSize; j++) {
      list.add(new Thread(example.new LockExample(j, max, lock, threadSize, condition)));
    }

    for (Thread thread : list) {
      thread.start();

    }
  }


}
