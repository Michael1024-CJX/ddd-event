package org.ddd.event;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * @author chenjx
 */
public class TimeoutFail {
    private int timeout;
    private TimeUnit timeUnit;

    private final static ScheduledExecutorService SCHEDULED_EXECUTOR = new ScheduledThreadPoolExecutor(5,
            new ThreadFactory() {
                AtomicInteger counter = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable r) {
                    final Thread thread = new Thread(r);
                    thread.setDaemon(true);
                    thread.setName("check-timeout-Thread" + counter.incrementAndGet());
                    return thread;
                }
            });

    public TimeoutFail(int timeout, TimeUnit timeUnit) {
        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }

    public <T> CompletableFuture<T> fastFail(CompletableFuture<T> future) {
        return future.applyToEither(fastFail(), Function.identity());
    }

    private <T> CompletableFuture<T> fastFail() {
        CompletableFuture<T> future = new CompletableFuture<>();
        SCHEDULED_EXECUTOR.schedule(() -> {
                    final TimeoutException ex = new TimeoutException("timeout: " + timeout);
                    return future.completeExceptionally(ex);
                },
                timeout,
                timeUnit);
        return future;
    }
}
