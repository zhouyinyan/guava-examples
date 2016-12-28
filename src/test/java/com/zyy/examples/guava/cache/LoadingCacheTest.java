package com.zyy.examples.guava.cache;

import com.google.common.base.Ticker;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalListeners;
import com.google.common.cache.RemovalNotification;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

/**
 * Created by zhouyinyan on 16/12/28.
 */
public class LoadingCacheTest {

    @Test
    public void testLoadingCache() throws ExecutionException, InterruptedException {
        LoadingCache loadingCache = CacheBuilder.newBuilder()
                .expireAfterWrite(5l, TimeUnit.SECONDS)
                .concurrencyLevel(2)
                .softValues()
                .ticker(Ticker.systemTicker())
                .recordStats()
//                .removalListener(new RemovalListener<String, String>() {
//                    @Override
//                    public void onRemoval(RemovalNotification<String, String> notification) {
//                        out.println("remove event:"+notification);
//                    }
//                })

                .removalListener(RemovalListeners.asynchronous(new RemovalListener<String, String>() {
                    @Override
                    public void onRemoval(RemovalNotification<String, String> notification) {
                        out.println("thread:"+Thread.currentThread().getName()+"remove event:"+notification);
                    }
                }, Executors.newSingleThreadExecutor()))

                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        out.println("load value ...");
                        return "value"+key;
                    }
                });


        String expected = "value1";
        Assert.assertNull(loadingCache.getIfPresent("1"));

        Assert.assertEquals(expected, loadingCache.get("1")); // load from origin

        Assert.assertEquals(expected, loadingCache.get("1")); // load from cache

        loadingCache.invalidateAll();
        Assert.assertEquals(expected, loadingCache.get("1")); // load from origin

        loadingCache.refresh("1"); //when  refresh ,load value from origin
        Assert.assertEquals(expected, loadingCache.get("1")); // load from cache

        TimeUnit.SECONDS.sleep(5l);          //key expired
        Assert.assertEquals(expected, loadingCache.get("1")); // load from cache


        //stat infos
        CacheStats stats = loadingCache.stats();
        Assert.assertEquals(2, stats.hitCount());
        Assert.assertEquals(4, stats.missCount());
        Assert.assertEquals(4, stats.loadCount());
        Assert.assertEquals(4, stats.loadSuccessCount());
//        out.println(stats);


    }


}
