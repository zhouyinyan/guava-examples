package com.zyy.examples.guava.collections;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhouyinyan on 16/12/27.
 */
public class MapsTest {
    
    Person person1;
    Person person2;
    Person person3;
    Person person4;
    List<Person> personList;
    Set<Person> personSet;

    @Before
    public void setup(){
        person1 = new Person("Wilma", "Flintstone", 30, "F");
        person2 = new Person("Fred", "Flintstone", 32, "M");
        person3 = new Person("Betty", "Rubble", 31, "F");
        person4 = new Person("Barney", "Rubble", 33, "M");
        personList = Lists.newArrayList(person1, person2, person3, person4);

        personSet = Sets.newHashSet(person1, person2, person3, person4);
    }


    @Test
    public void testUniqueIndex(){

        Map<String, Person> map = Maps.uniqueIndex(personList.iterator(), new Function<Person, String>() {
            @Override
            public String apply(Person input) {
                return Joiner.on("#").join(input.getFirstName(), input.getAge(), input.getGender());
            }
        });

        Assert.assertEquals(4, map.size());

        System.out.println(map);

    }

    @Test
    public void testAsOrToMap(){
        Map<Person, String> map = Maps.asMap(personSet, new Function<Person, String>() {
            @Override
            public String apply(Person input) {
                return Joiner.on("#").join(input.getFirstName(), input.getAge(), input.getGender());
            }
        });

        Assert.assertEquals(4, map.size());

        map = Maps.toMap(personSet, new Function<Person, String>() {
            @Override
            public String apply(Person input) {
                return Joiner.on("#").join(input.getFirstName(), input.getAge(), input.getGender());
            }
        });

        Assert.assertEquals(4, map.size());

        //不可变集合，抛出异常
        //map.clear();

    }


    @Test
    public void testTransform(){


        Map<String, Person> map = Maps.uniqueIndex(personList.iterator(), new Function<Person, String>() {
            @Override
            public String apply(Person input) {
                return Joiner.on("#").join(input.getFirstName(), input.getAge(), input.getGender());
            }
        });

        map = Maps.transformEntries(map, new Maps.EntryTransformer<String, Person, Person>() {
            @Override
            public Person transformEntry(String key, Person value) {
                value.setDesc(key);
                return value;
            }
        });

        Assert.assertNotNull(map.entrySet().iterator().next().getValue().getDesc());

        map = Maps.uniqueIndex(personList.iterator(), new Function<Person, String>() {
            @Override
            public String apply(Person input) {
                return Joiner.on("#").join(input.getFirstName(), input.getAge(), input.getGender());
            }
        });

        Map<String, String> transMap = Maps.transformValues(map, new Function<Person, String>() {
            @Override
            public String apply(Person input) {
                return Joiner.on("#").join(input.getFirstName(), input.getLastName(), input.getAge(), input.getGender());
            }
        });

        String expected = "Wilma#Flintstone#30#F";
        Assert.assertEquals(expected, transMap.get("Wilma#30#F"));


    }

    @Test
    public void testArrayListMultiMap(){

        Multimap<String, String> multiMap = ArrayListMultimap.create();
        multiMap.put("Foo","1");
        multiMap.put("Foo","2");
        multiMap.put("Foo","3");

        List<String> expected = Lists.newArrayList("1","2","3");

        Assert.assertEquals(expected, multiMap.get("Foo"));

//        multiMap.clear();

        multiMap.put("Bar","1");
        multiMap.put("Bar","2");
        multiMap.put("Bar","3");
        multiMap.put("Bar","3");
        multiMap.put("Bar","3");
        expected = Lists.newArrayList("1","2","3","3","3");
        Assert.assertEquals(expected, multiMap.get("Bar"));


        Assert.assertEquals(8, multiMap.size());
        Assert.assertEquals(8, multiMap.values().size());


    }

    @Test
    public void testHashMultiMap(){
        //key－value对唯一
        Multimap<String, String> multiMap = HashMultimap.create();
        multiMap.put("Bar","1");
        multiMap.put("Bar","2");
        multiMap.put("Bar","3");
        multiMap.put("Bar","3");
        multiMap.put("Bar","3");

        Assert.assertEquals(3, multiMap.size());
    }


    @Test
    public void testBiMap(){
        BiMap<String, String> bimap = HashBiMap.create();
        bimap.put("1", "hello");
        //bimap.put("2", "hello"); //throw IllegalArgumentException
        bimap.forcePut("2", "hello");

        Assert.assertFalse(bimap.containsKey("1"));
        Assert.assertTrue(bimap.containsKey("2"));

        BiMap<String, String> inverseMap = bimap.inverse();
        Assert.assertEquals("2", inverseMap.get("hello"));

    }



    class Person{
        private String firstName;

        private String lastName;

        private int age;

        private String gender;

        private String desc;

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

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this)
                    .add("firstName", firstName)
                    .add("lastName", lastName)
                    .add("age", age)
                    .add("gender", gender)
                    .add("desc", desc)
                    .toString();
        }
    }
}
