package com.zyy.examples.guava.function;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import jdk.nashorn.internal.scripts.JO;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by zhouyinyan on 16/12/27.
 */
public class FunctionsTest {

    @Test
    public void test(){

        Function<Date,String> function = new Function<Date, String>() {
            @Override
            public String apply(Date input) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
                return dateFormat.format(input);
            }
        };

        Map<String, String> map = Maps.newHashMap();
        map.put("key1","value1");
        map.put("key2","value2");
        map.put("key3","value3");

        Function<String, String> lookup = Functions.forMap(map);
        Assert.assertEquals("value2", lookup.apply("key2"));

        Function<String, String> lookup2 = Functions.forMap(map,"value");
        Assert.assertEquals("value", lookup2.apply("key"));

        Function<String,String> appendFunction = new Function<String, String>() {
            @Override
            public String apply(String input) {
                return Strings.padEnd(input,10,'#');
            }
        };

        Function<String, String> compose =
                Functions.compose(appendFunction, Functions.forMap(map) );

        Assert.assertEquals("value1####", compose.apply("key1"));

    }

    class DateFormatFunction implements Function<Date, String> {

        @Override
        public String apply(Date input) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
            return dateFormat.format(input);
        }
    }

}
