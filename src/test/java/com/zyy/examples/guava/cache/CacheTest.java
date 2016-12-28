package com.zyy.examples.guava.cache;

import com.google.common.base.Ticker;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

/**
 * Created by zhouyinyan on 16/12/28.
 */
public class CacheTest {

    @Test
    public void testCache() throws ExecutionException, InterruptedException {
        Cache<String,String> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(5l, TimeUnit.SECONDS)
                .maximumSize(100)
                .ticker(Ticker.systemTicker())
                .build();

        Assert.assertNull(cache.getIfPresent("key1"));

        String expected = "value1";
        Callable<String> callable = new LoadValueCallbale();

        String value =  cache.get("key1", callable); //load from origin
        Assert.assertEquals(expected, value);

        value =  cache.get("key1", callable); //load from cache
        Assert.assertEquals(expected, value);

        cache.invalidate("key1");
        value =  cache.get("key1", callable);  // load from origin
        Assert.assertEquals(expected, value);

        TimeUnit.SECONDS.sleep(5l);          //key expired
        value =  cache.get("key1", callable);  // load from origin
        Assert.assertEquals(expected, value);

    }

    class LoadValueCallbale implements Callable<String>{
        @Override
        public String call() throws Exception {
            out.println("load value ...");
            return "value1";
        }
    }

}
