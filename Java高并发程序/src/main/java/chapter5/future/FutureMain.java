package chapter5.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureMain {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    RealData callable = new RealData("abc");

    FutureTask<String> task = new FutureTask<>(callable);

    ExecutorService service = Executors.newSingleThreadExecutor();
    service.submit(task);

    System.out.println("请求完毕");
    try {
      Thread.sleep(2000);

    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println(task.get());

    service.shutdown();

  }

}
