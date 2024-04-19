package com.xiaopeng.xmart.cargallery;

import android.content.Context;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.engine.executor.GlideExecutor;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.xiaopeng.xmart.cargallery.helper.CarTypeHelper;
/* loaded from: classes21.dex */
public class MyAppGlideModule extends AppGlideModule {
    private static final int DISK_CACHE_SIZE = 134217728;
    private static final int MEMORY_CACHE_SIZE = 67108864;

    @Override // com.bumptech.glide.module.AppGlideModule, com.bumptech.glide.module.AppliesOptions
    public void applyOptions(Context context, GlideBuilder builder) {
        int threadCount;
        super.applyOptions(context, builder);
        builder.setDefaultRequestOptions(RequestOptions.formatOf(DecodeFormat.PREFER_RGB_565));
        builder.setMemoryCache(new LruResourceCache(67108864L));
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).setMaxSizeMultiplier(0.5f).build();
        builder.setBitmapPool(new LruBitmapPool(calculator.getBitmapPoolSize()));
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, 134217728L));
        if (CarTypeHelper.isXMartQ5()) {
            threadCount = 1;
        } else {
            threadCount = Math.min(2, GlideExecutor.calculateBestThreadCount());
        }
        builder.setSourceExecutor(GlideExecutor.newSourceExecutor(threadCount, "GlideSource", GlideExecutor.UncaughtThrowableStrategy.DEFAULT));
    }
}
