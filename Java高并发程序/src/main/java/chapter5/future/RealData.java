package chapter5.future;

import java.util.concurrent.Callable;

public class RealData implements Callable<String> {

  private final String para;

  public RealData(String para) {
    this.para = para;
  }

  @Override
  public String call() throws Exception {

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 10; i++) {
      sb.append(para);
      Thread.sleep(500);
    }
    return sb.toString();
  }

}
