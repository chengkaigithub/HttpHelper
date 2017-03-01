package http;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by chengkai on 2017/3/1.
 */

public class ThreadManager {

    private static ThreadManager instance = new ThreadManager();

    private ThreadPoolExecutor threadPoolExecutor;

    private LinkedBlockingQueue<FutureTask<?>> blockingQueue = new LinkedBlockingQueue<>();

    private RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            try {
                blockingQueue.put(new FutureTask<Object>(runnable, null));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public static ThreadManager getInstance() {
        return instance;
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            for (;;) {
                FutureTask futureTask = null;
                try {
                    Log.i("=======", "等待队列     " + blockingQueue.size());
                    futureTask = blockingQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (futureTask != null) {
                    threadPoolExecutor.execute(futureTask);
                }
                Log.i("=======", "线程池大小     " + threadPoolExecutor.getPoolSize());
            }
        }
    };

    private ThreadManager() {
        threadPoolExecutor = new ThreadPoolExecutor(
                4, 10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4), rejectedExecutionHandler);
        threadPoolExecutor.execute(mRunnable);
    }

    public <T> void execute(FutureTask<T> futureTask) throws InterruptedException {
        blockingQueue.put(futureTask);
    }
}
