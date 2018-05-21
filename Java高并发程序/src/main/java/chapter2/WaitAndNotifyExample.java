package chapter2;

import java.util.ArrayList;
import java.util.List;

public class WaitAndNotifyExample {

  private class Number {

    int i;

    private Number(int i) {
      this.i = i;
    }

    public void plus() {
      i++;
    }

    public int getNUmber() {
      return i;
    }
  }

  class PrintThread implements Runnable {

    private int size;

    private int max;

    private Number number;

    private int threadNum;

    public PrintThread(int size, int max, int threadNum, Number number) {
      this.size = size;
      this.max = max;
      this.number = number;
      this.threadNum = threadNum;
    }

    @Override
    public void run() {
      synchronized (number) {
        while (true) {
          if (number.getNUmber() > max) {
            number.notify();
            break;
          }
          if ((number.getNUmber() % size) == threadNum % size) {
            System.out.println("Thread" + threadNum + " print " + number.getNUmber());
            number.plus();
          }
          number.notifyAll();
          try {
            number.wait();
//            System.out.println("Thread" + threadNum + " print received");
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    WaitAndNotifyExample obj = new WaitAndNotifyExample();
    Number number = obj.new Number(1);
    int max = 30;
    int size = 7;
    List<Thread> list = new ArrayList<>(size);
    for (int i = 1; i <= size; i++) {
      Thread thread = new Thread(obj.new PrintThread(size, max, i, number));
      list.add(thread);
      thread.start();
    }

    for (Thread thread : list) {
      thread.join();
    }

    System.out.println("end");


  }


}
