package chapter3.sum1000;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SumWithExecutorService {


  private static class SumTask implements Callable<Long> {

    private int from;

    private int to;

    public SumTask(int from, int to) {
      this.from = from;
      this.to = to;
    }

    @Override
    public Long call() throws Exception {
      long total = 0;
      for (int i = from; i <= to; i++) {
        total += i;
      }
      System.out.println("caculate from" + from + " to " + to + ", result is " + total);

      return total;
    }
  }

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    int parallism = Runtime.getRuntime().availableProcessors();
    ExecutorService pool = Executors.newFixedThreadPool(parallism);

    List<Future<Long>> list = new ArrayList<>();
    int target = 1000;
    int part = target / parallism;
    for (int i = 0; i < parallism; i++) {
      int from = i * part + 1;
      int to = (i == parallism - 1) ? target : (i + 1) * part;
      list.add(pool.submit(new SumTask(from, to)));
    }

    long total = 0;
    for (Future<Long> result : list) {
      total += result.get();
    }

    System.out.println(total);

    pool.shutdown();

  }


}
