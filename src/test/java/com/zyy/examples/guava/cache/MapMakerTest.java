package com.zyy.examples.guava.cache;

import com.google.common.collect.MapMaker;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ConcurrentMap;

/**
 * Created by zhouyinyan on 16/12/28.
 */
public class MapMakerTest {

    @Test
    public void testMapMaker(){

        //just init ConcurrentMap with some paramter
        ConcurrentMap<String, String> map =
                new MapMaker().concurrencyLevel(1)
                .softValues()
                .makeMap();

        map.put("key1", "value1");

        Assert.assertEquals("value1", map.get("key1"));
    }
}
