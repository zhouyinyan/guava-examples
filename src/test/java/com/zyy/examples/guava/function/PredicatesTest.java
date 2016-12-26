package com.zyy.examples.guava.function;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;


/**
 * Created by zhouyinyan on 16/12/27.
 */
public class PredicatesTest {

    @Test
    public void test(){

        Predicate<String> predicate = new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return Strings.isNullOrEmpty(input) ? true :
                        input.length() < 5;
            }
        };


        Assert.assertTrue(predicate.apply(null));
        Assert.assertTrue(predicate.apply(""));
        Assert.assertTrue(predicate.apply("asdf"));
        Assert.assertEquals(false, predicate.apply("asdfsdf"));

        Predicate<String> not =  Predicates.not(predicate);
        Assert.assertEquals(true, not.apply("asdfsdf"));

        Predicate<String> predicate2 = new Predicate<String>() {

            @Override
            public boolean apply(String input) {
                return input.contains("zyy");
            }
        };

        Predicate<String> and =  Predicates.and(predicate,predicate2);
        Assert.assertTrue(predicate.apply("zyy"));
        Assert.assertEquals(false, and.apply("zyy4324"));

        Predicate<String> or =  Predicates.or(predicate,predicate2);
        Assert.assertEquals(true, or.apply("zyy4324"));

        Map<String, String> map = Maps.newHashMap();
        map.put("key1","value1");
        map.put("key2","value2");
        map.put("key3","value3");

        Function<String, String> lookup = Functions.forMap(map);
        Assert.assertEquals("value2", lookup.apply("key2"));

        Predicate<String> compose =  Predicates.compose(predicate,lookup);
        Assert.assertEquals(false, compose.apply("key2"));


    }
}
