package com.zyy.examples.guava.basicutils;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouyinyan on 16/12/26.
 */
public class SplitterTest {

    //String.spilt() 默认行为会截断末尾的空串
    @Test
    public void testStringSplit(){

        String[] expectedArray = new String[]{"Monday", "Tuesday", "","Thursday", "Friday"};

        String testString = "Monday,Tuesday,,Thursday,Friday,,";
        //parts is [Monday, Tuesday, , Thursday,Friday]
        String[] parts = testString.split(",");

        Assert.assertArrayEquals(expectedArray, parts);
    }

    @Test
    public void testSplitter(){
        List<String> expectedList = Lists.asList("Monday", new String[]{"Tuesday", "","Thursday", "Friday","",""});

        String testString = "Monday,Tuesday,,Thursday,Friday,,";
        Iterable<String> parts = Splitter.on(",").split(testString);

        List<String> splitList = Lists.newArrayList();
        parts.forEach(s -> splitList.add(s));

        System.out.print(parts);

        Assert.assertEquals(expectedList.size(), splitList.size());
        Assert.assertEquals(expectedList.get(2), splitList.get(2));


        List<String> expectedList2 = Arrays.asList("Monday", "Tuesday","Thursday", "Friday");



        Iterable<String> parts2 = Splitter.on(",").trimResults().split(testString);

        //去除头尾的空串貌似不得行呢 why
        System.out.print(Splitter.on("|").trimResults().split("1|2|3|||"));

        List<String> splitList2 = Lists.newArrayList();
        parts2.forEach(s -> splitList2.add(s));

        Assert.assertEquals(expectedList2.size(), splitList2.size());
        Assert.assertEquals(expectedList2.get(2), splitList2.get(2));

    }


    @Test
    public void testMapSplitter() {
        String startString = "Washington D.C=Redskins#New York City=Giants#Philadelphia=Eagles#Dallas=Cowboys";
        Map<String,String> testMap = Maps.newLinkedHashMap();
        testMap.put("Washington D.C","Redskins");
        testMap.put("New York City","Giants");
        testMap.put("Philadelphia","Eagles");
        testMap.put("Dallas","Cowboys");
        Splitter.MapSplitter mapSplitter =
                Splitter.on("#").withKeyValueSeparator("=");
        Map<String,String> splitMap =
                mapSplitter.split(startString);

        Assert.assertEquals(testMap, splitMap);
    }

}
