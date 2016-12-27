package com.zyy.examples.guava.collections;

import com.google.common.collect.ImmutableListMultimap;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by zhouyinyan on 16/12/27.
 */
public class ImmutableConllectionsTest {

    @Test
    public void testBuilder(){
        ImmutableListMultimap<String, String> immutableListMultimap =
                new ImmutableListMultimap.Builder()
                .put("key1", "value1")
                .putAll("key2", "value21", "value22")
                .putAll("key3", "value31", "value32", "value33")
                .build();

        try {
            immutableListMultimap.clear();
            Assert.fail();
        }catch (UnsupportedOperationException e){

        }

    }
}
