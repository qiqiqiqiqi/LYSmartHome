package com.jb.jb_library.factory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @创建者 zhangbo
 * @创建时间 2016/5/3 15:41
 * @描述 ${TODO} 线程池代理类
 * @更新描述 ${TODO}
 */

public class ThreadPoolProxy {

    private int  mCorePoolSize;
    private int  mMaximumPoolSize;
    private long mKeepAliveTime;
    private ThreadPoolExecutor mPoolExecutor;

    public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        mCorePoolSize = corePoolSize;
        mMaximumPoolSize = maximumPoolSize;
        mKeepAliveTime = keepAliveTime;
    }

    /**
     * 提交任务
     * @return futrue是的到返回的结果里面包含了get和cancle的方法,
     * get是阻塞的方法,会阻塞任务完成之后的结果,抛出一个异常
     */
    public Future<?> submit(Runnable task) {
        initThreadPoolExecutor();
        return mPoolExecutor.submit(task);
    }

    public void executor(Runnable task) {
        initThreadPoolExecutor();
       mPoolExecutor.submit(task);
    }

    public void remove(Runnable task) {
        initThreadPoolExecutor();
       mPoolExecutor.submit(task);
    }

    private void initThreadPoolExecutor() {

        if(mPoolExecutor == null||mPoolExecutor.isShutdown()||mPoolExecutor.isTerminated()){
            synchronized (ThreadPoolProxy.class){
                if(mPoolExecutor == null||mPoolExecutor.isShutdown()||mPoolExecutor.isTerminated()){
                    TimeUnit unit = TimeUnit.MILLISECONDS;
                    BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>();
                    ThreadFactory threadFactory = Executors.defaultThreadFactory();
                    RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardOldestPolicy();
                    mPoolExecutor = new ThreadPoolExecutor(
                            mCorePoolSize,
                            mMaximumPoolSize,
                            mKeepAliveTime,
                            unit,
                            workQueue,
                            threadFactory,
                            handler
                    );
                }
            }
        }

    }
}
