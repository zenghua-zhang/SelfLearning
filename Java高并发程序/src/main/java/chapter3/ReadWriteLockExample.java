package chapter3;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {

  private int value;

  public Integer readValue(Lock lock) throws InterruptedException {

    try {
      lock.lock();
      Thread.sleep(1000);
      return value;
    } finally {
      lock.unlock();
    }
  }

  public void writeValue(Lock lock) throws InterruptedException {
    try {
      lock.lock();
      Thread.sleep(1000);
      value = ThreadLocalRandom.current().nextInt();
      System.out.println("write value to " + value);
    } finally {
      lock.unlock();
    }
  }

  public static void main(String[] args) {
    ReadWriteLockExample demo = new ReadWriteLockExample();

    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    Lock writeLock = lock.writeLock();
    Lock readLock = lock.readLock();

    Runnable readRunnable = demo.new ReadRunnable(readLock, demo);
    Runnable writeRunnable = demo.new WriteRunnable(writeLock, demo);

    for (int i = 0; i < 3; i++) {
      new Thread(writeRunnable).start();
    }
    for (int i = 0; i < 20; i++) {
      new Thread(readRunnable).start();
    }
  }


  private class ReadRunnable implements Runnable {

    Lock lock;
    ReadWriteLockExample demo;

    public ReadRunnable(Lock lock, ReadWriteLockExample demo) {
      this.lock = lock;
      this.demo = demo;
    }

    @Override
    public void run() {
      try {
        int value = demo.readValue(lock);
        System.out.println("value is " + value);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private class WriteRunnable implements Runnable {

    Lock lock;
    ReadWriteLockExample demo;

    public WriteRunnable(Lock lock, ReadWriteLockExample demo) {
      this.lock = lock;
      this.demo = demo;
    }

    @Override
    public void run() {
      try {
        demo.writeValue(lock);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }


}
