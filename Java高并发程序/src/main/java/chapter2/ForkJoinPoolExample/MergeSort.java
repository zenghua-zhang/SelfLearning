package chapter2.ForkJoinPoolExample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class MergeSort {

  private static class MergeSortTask extends RecursiveTask<List<Integer>> {

    private List<Integer> list;

    public MergeSortTask(List<Integer> list) {
      this.list = list;
    }

    @Override
    protected List<Integer> compute() {
      if (list.size() <= 1) {
        return list;
      }

      if (list.size() == 2) {
        int i1 = list.get(0);
        int i2 = list.get(1);

        if (i1 > i2) {
          list.set(0, i2);
          list.set(1, i1);
        }
        return list;
      }

      int mid = list.size() / 2;
      List<Integer> left = new ArrayList<>();
      List<Integer> right = new ArrayList<>();

      for (int i = 0; i < mid; i++) {
        left.add(list.get(i));
      }
      for (int i = mid; i < list.size(); i++) {
        right.add(list.get(i));
      }
      MergeSortTask leftTask = new MergeSortTask(left);
      MergeSortTask rightTask = new MergeSortTask(right);

      leftTask.fork();
      rightTask.fork();

      try {
        List<Integer> leftResult = leftTask.get();
        List<Integer> rightResult = rightTask.get();

        List<Integer> result = new ArrayList<>();
        int l = 0, r = 0;

        while (l < leftResult.size() && r < rightResult.size()) {
          int lr = leftResult.get(l);
          int rr = rightResult.get(r);

          if (lr < rr) {
            result.add(lr);
            l++;
          } else {
            result.add(rr);
            r++;
          }
        }

        for (int i = l; i < leftResult.size(); i++) {
          result.add(leftResult.get(i));
        }
        for (int i = r; i < rightResult.size(); i++) {
          result.add(rightResult.get(i));
        }

        return result;

      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ExecutionException e) {
        e.printStackTrace();
      }

      return null;
    }
  }

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ForkJoinPool pool = ForkJoinPool.commonPool();

    List<Integer> list = Arrays.asList(5,4,3,2,0,98,65,43,34);

    MergeSortTask task = new MergeSortTask(list);

    pool.submit(task);

    System.out.println(task.get());

  }


}
