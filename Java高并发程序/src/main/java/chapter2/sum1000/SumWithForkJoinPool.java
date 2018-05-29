package chapter2.sum1000;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SumWithForkJoinPool {

  private static class SumTask extends RecursiveTask<Long> {

    private int from;

    private int to;

    public SumTask(int from, int to) {
      this.from = from;
      this.to = to;
    }

    @Override
    protected Long compute() {
      if ((to - from) < 10) {
        long total = 0;

        for (int i = from; i <= to; i++) {
          total += i;
        }
        System.out.println("calculate from " + from + " to " + to + ", result is " + total);
        return total;

      } else {
        int mid = (to + from) / 2;
        SumTask left = new SumTask(from, mid);
        SumTask right = new SumTask(mid + 1, to);

        left.fork();
        right.fork();
        return  left.join() + right.join();
      }

    }
  }

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ForkJoinPool pool = ForkJoinPool.commonPool();
    SumTask task = new SumTask(0, 1000);
    pool.execute(task);
    System.out.println(task.get());
  }

}
