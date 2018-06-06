package chapter5.pcpattern;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class PCMain {

  public static void main(String[] args) throws InterruptedException {
    BlockingQueue<PCData> queue = new LinkedBlockingQueue<>();
    AtomicInteger integer = new AtomicInteger();
    Producer p1 = new Producer(queue, integer);
    Producer p2 = new Producer(queue, integer);
    Producer p3 = new Producer(queue, integer);

    Consumer c1 = new Consumer(queue);
    Consumer c2 = new Consumer(queue);
    Consumer c3 = new Consumer(queue);

    ExecutorService service = Executors.newCachedThreadPool();

    service.execute(p1);
    service.execute(p2);
    service.execute(p3);

    service.execute(c1);
    service.execute(c2);
    service.execute(c3);

    Thread.sleep(10000);

    p1.stop();
    p2.stop();
    p3.stop();

    Thread.sleep(3000);

    service.shutdownNow();

  }

}
