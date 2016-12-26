package com.zyy.examples.guava.basicutils;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by zhouyinyan on 16/12/26.
 */
public class JoinerTest {

    @Test
    public void testBuildString(){
        String expectedString = "a-b-c-d";
        List<String> stringList = Lists.newArrayList();
        stringList.add("a");
        stringList.add("b");
        stringList.add("c");
        stringList.add("d");

        Assert.assertEquals(expectedString, buildString(stringList,"-"));
    }


    //典型的连接样板代码
    public String buildString(List<String> stringList, String delimiter){
        StringBuilder builder = new StringBuilder();
        for (String s : stringList) {
            if(s !=null){
                builder.append(s).append(delimiter);
            } }
        builder.setLength(builder.length() - delimiter.length());
        return builder.toString();
    }


    //使用joiner，可以非常简单
    @Test
    public void testJoinString(){
        String expectedString = "a-b-c-d";
        String expectedString2 = "a-b-c-d-null";

        List<String> stringList = Lists.newArrayList();
        stringList.add("a");
        stringList.add("b");
        stringList.add("c");
        stringList.add("d");
        stringList.add(null);

        String joinString = Joiner.on("-").skipNulls().join(stringList);
        Assert.assertEquals(expectedString, joinString);

        String joinString2 = Joiner.on("-").useForNull("null").join(stringList);
        Assert.assertEquals(expectedString2, joinString2);
    }

    @Test
    public void testMapJoiner(){

        String expectedString = "Washington D.C=Redskins#New York City=Giants#Philadelphia=Eagles#Dallas=Cowboys";

        Map<String,String> testMap = Maps.newLinkedHashMap();
        testMap.put("Washington D.C","Redskins");
        testMap.put("New York City","Giants");
        testMap.put("Philadelphia","Eagles");
        testMap.put("Dallas","Cowboys");

        String joinString = Joiner.on("#").withKeyValueSeparator("=").join(testMap);
        Assert.assertEquals(expectedString, joinString);

    }

}
