package com.zyy.examples.guava.collections;

import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

/**
 * Created by zhouyinyan on 16/12/27.
 */
public class SetsTest {

    Set<String> s1;
    Set<String> s2;

    @Before
    public void setup(){
        s1 = Sets.newHashSet("1","2","3");
        s2 = Sets.newHashSet("2","3","4");
    }

    @Test
    public void testDifference(){
        Sets.SetView<String> setView = Sets.difference(s1, s2);


        Assert.assertTrue(setView.contains("1"));
        Assert.assertEquals(false, setView.contains("2"));
        Assert.assertEquals(false, setView.contains("4"));

        setView = Sets.difference(s2, s1);
        Assert.assertTrue(setView.contains("4"));

    }

    @Test
    public void testSymmetricDifference(){
        Sets.SetView<String> setView = Sets.symmetricDifference(s1, s2);

        Assert.assertEquals(2, setView.size());
        Assert.assertTrue(setView.contains("1"));
        Assert.assertTrue(setView.contains("4"));
    }

    @Test
    public void testIntersection(){
        Sets.SetView<String> setView = Sets.intersection(s1, s2);

        Assert.assertEquals(2, setView.size());
        Assert.assertTrue(setView.contains("2"));
        Assert.assertTrue(setView.contains("3"));
    }

    @Test
    public void testUnion(){
        Sets.SetView<String> setView = Sets.union(s1, s2);

        Assert.assertEquals(4, setView.size());
        Assert.assertTrue(setView.contains("1"));
        Assert.assertTrue(setView.contains("3"));
    }

}
