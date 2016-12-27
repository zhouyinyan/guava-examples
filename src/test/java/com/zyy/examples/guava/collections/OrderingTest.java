package com.zyy.examples.guava.collections;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by zhouyinyan on 16/12/27.
 */
public class OrderingTest {

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
    public void testOrdering(){
        Ordering<Person> ordering = Ordering.from(new AgeComparator());


        Collections.sort(personList,ordering);
        Assert.assertEquals(person1, personList.get(0));

        ordering = Ordering.from(new AgeComparator()).reverse().nullsFirst();

        Collections.sort(personList,ordering);
//        Assert.assertNull( personList.get(0));
        Assert.assertEquals(person4, personList.get(0));

        ordering = Ordering.from(new AgeComparator()).compound(new NameComparator()).nullsFirst();
        Collections.sort(personList,ordering);
        Assert.assertEquals(person1, personList.get(0));

        List<Person> topPersons = ordering.greatestOf(personList, 2);
        Assert.assertEquals(2, topPersons.size());
        Assert.assertEquals(person4, topPersons.get(0));
        Assert.assertEquals(person2, topPersons.get(1));

        Assert.assertEquals(person4, ordering.max(personList));

    }

    class AgeComparator implements Comparator<Person> {

        @Override
        public int compare(Person o1, Person o2) {
            Preconditions.checkNotNull(o1);
            Preconditions.checkNotNull(o2);
            return Ints.compare(o1.getAge(), o2.getAge());
        }
    }

    class NameComparator implements Comparator<Person> {

        @Override
        public int compare(Person o1, Person o2) {
            Preconditions.checkNotNull(o1);
            Preconditions.checkNotNull(o2);
            return Ordering.natural().compare(o1.getFirstName(), o2.getFirstName());
        }
    }

    class Person {
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
