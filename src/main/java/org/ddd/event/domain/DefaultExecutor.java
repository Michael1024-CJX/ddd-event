package org.ddd.event.domain;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Michael
 * @date 2021/5/30 19:38
 */
public class DefaultExecutor {
    private static Executor executor = new ThreadPoolExecutor(
            10,
            10,
            6,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(50));


    public static void execute(Runnable command) {
        executor.execute(command);
    }
}
