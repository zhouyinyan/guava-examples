package com.zyy.examples.guava.collections;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.io.Flushables;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by zhouyinyan on 16/12/27.
 */
public class RangeTest {
    
    
    Person person1;
    Person person2;
    Person person3;
    Person person4;
    List<Person> personList;

    @Before
    public void setup(){
        person1 = new Person("Wilma", "Flintstone", 30, "F");
        person2 = new Person("Fred", "Flintstone", 32, "M");
        person3 = new Person("Betty", "Rubble", 31, "F");
        person4 = new Person("Barney", "Rubble", 33, "M");
        personList = Lists.newArrayList(person1, person2, person3, person4);
    }

    @Test
    public void testRange(){
        Range<Integer> integerRange = Range.closed(1, 10);

        Assert.assertTrue(integerRange.contains(1));
        Assert.assertTrue(integerRange.contains(10));

        integerRange = Range.greaterThan(2);
        Assert.assertFalse(integerRange.contains(1));
        Assert.assertTrue(integerRange.contains(10));


        integerRange = Range.open(1, 10);
        Assert.assertFalse(integerRange.contains(1));
        Assert.assertTrue(integerRange.contains(2));
        Assert.assertFalse(integerRange.contains(10));

    }

    @Test
    public void filterPerson(){
        List<Person> filterdPerson = FluentIterable.from(personList)
                .filter(Predicates.compose(Range.closed(31, 32), new Function<Person, Integer>() {
                    @Override
                    public Integer apply(Person input) {
                        return input.getAge();
                    }
                })).toList();


        Assert.assertEquals(2, filterdPerson.size());
    }


    class Person{
        private String firstName;

        private String lastName;

        private int age;

        private String gender;

        public Person(String firstName, String lastName, int age, String gender) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
            this.gender = gender;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }

}
