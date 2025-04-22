package org.aguiar.leveler.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DatabaseQueue {
  private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

  public DatabaseQueue() {
    new Thread(() -> {
      while (true) {
        try {
          Runnable task = queue.take();
          task.run();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          break;
        }
      }
    }).start();
  }

  public void execute(Runnable task) {
    queue.add(task);
  }
}
