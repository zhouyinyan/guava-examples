package com.zyy.examples.guava.basicutils;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by zhouyinyan on 16/12/26.
 */
public class StringsTest {

    @Test
    public void testCharsets(){
        String s = "alsdjadfadsf中文";
        byte[] bytes = s.getBytes(Charsets.UTF_8);

        Assert.assertEquals(s, new String(bytes, Charsets.UTF_8));
    }


    @Test
    public void testFill(){
        String expectedString = "fooxxx";

        //常规的字符串对齐
        StringBuilder builder = new StringBuilder("foo");
        char c = 'x';
        for(int i=0; i<3; i++){
            builder.append(c);
        }
        String buildString =  builder.toString();

        Assert.assertEquals(expectedString, buildString);


        //使用strings对齐
        Assert.assertEquals(expectedString, Strings.padEnd("foo",6,c));


        expectedString = "xxxfoo";
        Assert.assertEquals(expectedString, Strings.padStart("foo",6,c));

    }


    @Test
    public void testCharMatcher(){

        //多行文本
        String muiltiline = "one\ntwo\nthree";
        String expected = "one two three";
        String replaced = CharMatcher.BREAKING_WHITESPACE.replaceFrom(muiltiline, " ");

        Assert.assertEquals(expected, replaced);

        String tabsAndSpaces = "String  with      spaces     and tabs";
        expected = "String with spaces and tabs";

        Assert.assertEquals(expected, CharMatcher.WHITESPACE.collapseFrom(tabsAndSpaces, ' '));

        tabsAndSpaces = "       String  with      spaces     and tabs    ";
        Assert.assertEquals(expected, CharMatcher.WHITESPACE.trimAndCollapseFrom(tabsAndSpaces, ' '));

        String lettersAndNumbers = "foo 989yxb ar234";
        expected = "989234";
        Assert.assertEquals(expected, CharMatcher.JAVA_DIGIT.retainFrom(lettersAndNumbers));

        expected = " 989 234";
        CharMatcher matcher = CharMatcher.JAVA_DIGIT.or(CharMatcher.WHITESPACE);
        Assert.assertEquals(expected, matcher.retainFrom(lettersAndNumbers));

    }
}
