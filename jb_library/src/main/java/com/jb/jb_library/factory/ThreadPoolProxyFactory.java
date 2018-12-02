package com.jb.jb_library.factory;

public class ThreadPoolProxyFactory {
    static ThreadPoolProxy mNormalThreadPoolProxy;
    static ThreadPoolProxy mDownloadThreadPoolProxy;


    public static ThreadPoolProxy createNormalThreadPoolProxy() {
        if (mNormalThreadPoolProxy == null) {
            synchronized (ThreadPoolProxyFactory.class) {
                if (mNormalThreadPoolProxy == null) {
                    mNormalThreadPoolProxy = new ThreadPoolProxy(5,5,3000);
                }
            }
        }
        return mNormalThreadPoolProxy;
    }

    public static ThreadPoolProxy createDownloadThreadPoolProxy(){
        if(mDownloadThreadPoolProxy == null){
            synchronized (ThreadPoolProxyFactory.class){
                if (mDownloadThreadPoolProxy == null){
                    mDownloadThreadPoolProxy = new ThreadPoolProxy(3,3,2000);
                }
            }
        }
        return mDownloadThreadPoolProxy;
    }

    public static ThreadPoolProxy createCommonThreadPoolProxy(){
        if(mDownloadThreadPoolProxy == null){
            synchronized (ThreadPoolProxyFactory.class){
                if (mDownloadThreadPoolProxy == null){
                    mDownloadThreadPoolProxy = new ThreadPoolProxy(1,1,2000);
                }
            }
        }
        return mDownloadThreadPoolProxy;
    }
}
