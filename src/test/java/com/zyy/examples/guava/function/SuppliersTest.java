package com.zyy.examples.guava.function;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Strings;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhouyinyan on 16/12/27.
 */
public class SuppliersTest {

    @Test
    public void test() throws InterruptedException {

        String expected = "hello worold!";

        Supplier<String> supplier = new Supplier<String>() {
            @Override
            public String get() {
                return expected;
            }
        };

        Assert.assertEquals(expected, supplier.get());

        Supplier<Predicate<String>> predicateSupplier = new Supplier<Predicate<String>>() {
            @Override
            public Predicate<String> get() {
                return Predicates.and(new Predicate<String>() {
                    @Override
                    public boolean apply(String input) {
                        return !Strings.isNullOrEmpty(input);
                    }
                }, new Predicate<String>() {
                    @Override
                    public boolean apply(String input) {
                        return input.length() > 10;
                    }
                });
            }
        };


        Assert.assertEquals(false, predicateSupplier.get().apply(""));
        Assert.assertEquals(false, predicateSupplier.get().apply("adfasf"));
        Assert.assertEquals(true, predicateSupplier.get().apply("hahasdjfsajdfls"));

        Assert.assertEquals(false, predicateSupplier.get() == predicateSupplier.get());

        Supplier<Predicate<String>> wrapped = Suppliers.memoize(predicateSupplier);
        Assert.assertEquals(true, wrapped.get() == wrapped.get());


        Supplier<Predicate<String>> wrappedExpired = Suppliers.memoizeWithExpiration(predicateSupplier, 5l, TimeUnit.SECONDS);
        Predicate<String> first = wrappedExpired.get();
        Predicate<String> two = wrappedExpired.get();
        Assert.assertEquals(true, first == two);

        TimeUnit.SECONDS.sleep(2);
        Assert.assertEquals(true, first == wrappedExpired.get());

        TimeUnit.SECONDS.sleep(4);
        Assert.assertEquals(false, first == wrappedExpired.get());

    }
}
