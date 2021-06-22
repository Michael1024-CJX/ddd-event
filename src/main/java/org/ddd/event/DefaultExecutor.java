package org.ddd.event;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Michael
 */
public class DefaultExecutor {
    private static Executor executor = new ThreadPoolExecutor(
            10,
            10,
            6,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(50));

    public static Executor getExecutor() {
        return executor;
    }


    public static void execute(Runnable command) {
        executor.execute(command);
    }
}
