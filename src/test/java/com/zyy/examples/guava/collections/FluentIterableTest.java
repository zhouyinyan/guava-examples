package com.zyy.examples.guava.collections;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by zhouyinyan on 16/12/27.
 */
public class FluentIterableTest {


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
    public void testFilter(){
        FluentIterable<Person> iterable = FluentIterable.from(personList)
                .filter(new Predicate<Person>() {
                    @Override
                    public boolean apply(Person input) {
                        return input.getAge() > 31;
                    }
                });

        Assert.assertTrue(Iterables.contains(iterable, person2));
        Assert.assertTrue(Iterables.contains(iterable, person4));
        Assert.assertEquals(false, Iterables.contains(iterable, person1));
        Assert.assertEquals(false, Iterables.contains(iterable, person3));

    }

    @Test
    public void testTransform(){
        String expected = "Wilma#30#F";

        List<String> result = FluentIterable.from(personList)
                .transform(new Function<Person, String>() {
                    @Override
                    public String apply(Person input) {
                        return Joiner.on("#").join(input.firstName, input.getAge(), input.getGender());
                    }
                }).toList();

        Assert.assertEquals(expected, result.get(0));

    }


    @Test
    public void testLists(){

        List<List<Person>> partitions = Lists.partition(personList, 3);

        Assert.assertEquals(2, partitions.size());
        Assert.assertEquals(3, partitions.get(0).size());
        Assert.assertEquals(1, partitions.get(1).size());

        Assert.assertEquals(person1, partitions.get(0).get(0));

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
