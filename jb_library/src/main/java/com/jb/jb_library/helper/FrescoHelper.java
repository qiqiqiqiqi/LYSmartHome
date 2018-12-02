package com.jb.jb_library.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.facebook.cache.common.CacheKey;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.cache.CountingMemoryCache;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.cache.ImageCacheStatsTracker;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.jb.jb_library.util.LogUtil;

import java.io.File;

/**
 * @创建者： zhangbo
 * @创建时间： 2016/10/8 8:58
 * @描述： ${TODO} Fresco加载图片帮助类
 */

public class FrescoHelper {

    private static final String PHOTO_FRESCO = "fresco_cache";

    /**
     * 加载网络图片，不对图片进行压缩，加载失败，不可点击再次加载
     *
     * @param url
     * @param draweeView
     */
    public static void loadUrl(String url,SimpleDraweeView draweeView){
        draweeView.setImageURI(Uri.parse(url));
    }

    /**
     * 加载网络图片，不对图片进行压缩
     *
     * @param url
     * @param draweeView
     * @param isRetryLoading 加载失败是否可重试
     */
    public static void loadUrl(String url,SimpleDraweeView draweeView,boolean isRetryLoading){
       load(draweeView,Uri.parse(url),0,0,isRetryLoading);
    }

    /**
     * 压缩指定的图片的大小,加载失败可重试再次加载，默认四次，四次后显示网络加载失败的图片
     *
     * @param url
     * @param draweeView
     * @param width
     * @param height
     */
    public static void loadUrl(String url, SimpleDraweeView draweeView, int width, int height){
        load(draweeView,Uri.parse(url),width,height,false);
    }

    /**
     * 压缩指定的图片的大小
     *
     * @param url
     * @param draweeView
     * @param width 指定压缩目标宽度
     * @param height 指定压缩目标高度
     * @param isRetryLoading 加载失败是否可重试
     */
    public static void loadUrl(String url, SimpleDraweeView draweeView, int width, int height,boolean isRetryLoading){
        load(draweeView,Uri.parse(url),width,height,isRetryLoading);

    }


    public static void load(final SimpleDraweeView draweeView, Uri uri, int width, int height,boolean isRetryLoading) {
        LogUtil.e("uri::"+uri);
        ImageRequestBuilder builder = ImageRequestBuilder.newBuilderWithSource(uri);
        if(width > 0 && height > 0){
            builder.setResizeOptions(new ResizeOptions(width, height));
        }
        ImageRequest request = builder.setProgressiveRenderingEnabled(true).build(); //支持图片渐进式加载
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setTapToRetryEnabled(isRetryLoading)
                .setOldController(draweeView.getController())
                .build();
        draweeView.setController(controller);
    }


    /**
     * 初始化操作，建议在子线程中进行
     *
     * @param context
     * @param cacheSizeInM  磁盘缓存的大小，以M为单位
     */
    public static void init(final Context context, int cacheSizeInM){


        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder()
                .setMaxCacheSize(cacheSizeInM*1024*1024)
                .setBaseDirectoryName(PHOTO_FRESCO)
                .setBaseDirectoryPathSupplier(new Supplier<File>() {
                    @Override
                    public File get() {
                        return context.getCacheDir();
                    }
                })
                .build();

        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context)
                .setMainDiskCacheConfig(diskCacheConfig)
                .setImageCacheStatsTracker(new MyImageCacheStatsTracker())
                .setDownsampleEnabled(true)//Downsampling，它处理图片的速度比常规的裁剪更快，
                // 并且同时支持PNG，JPG以及WEP格式的图片，非常强大,与ResizeOptions配合使用
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .build();
        Fresco.initialize(context, config);
    }


    /**
     *判断图片是否有缓存
     *
     * @param url
     * @return 该url对应的图片是否已经缓存到本地
     */
    public static boolean isCached(String url) {
        ImageRequest imageRequest = ImageRequest.fromUri(url);
        CacheKey cacheKey = DefaultCacheKeyFactory.getInstance()
                .getEncodedCacheKey(imageRequest);
        return ImagePipelineFactory.getInstance().getMainDiskStorageCache().hasKey(cacheKey);
    }

    /**
     * 清除磁盘缓存
     */
    public static void clearDiskCache(){
        Fresco.getImagePipeline().clearDiskCaches();
    }


    /**
     * 清除单张图片的磁盘缓存
     * @param url
     */
    public static void clearCacheByUrl(String url){
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        Uri uri = Uri.parse(url);
        // imagePipeline.evictFromMemoryCache(uri);
        imagePipeline.evictFromDiskCache(uri);
        //imagePipeline.evictFromCache(uri);//这个包含了从内存移除和从硬盘移除
    }

    public static class MyImageCacheStatsTracker implements ImageCacheStatsTracker {

        @Override
        public void onBitmapCachePut() {

        }

        @Override
        public void onBitmapCacheHit() {

        }

        @Override
        public void onBitmapCacheMiss() {

        }

        @Override
        public void onMemoryCachePut() {

        }

        @Override
        public void onMemoryCacheHit() {

        }

        @Override
        public void onMemoryCacheMiss() {

        }

        @Override
        public void onStagingAreaHit() {

        }

        @Override
        public void onStagingAreaMiss() {

        }

        @Override
        public void onDiskCacheHit() {

        }

        @Override
        public void onDiskCacheMiss() {

        }

        @Override
        public void onDiskCacheGetFail() {

        }

        @Override
        public void registerBitmapMemoryCache(CountingMemoryCache<?, ?> bitmapMemoryCache) {

        }

        @Override
        public void registerEncodedMemoryCache(CountingMemoryCache<?, ?> encodedMemoryCache) {

        }
    }

}
