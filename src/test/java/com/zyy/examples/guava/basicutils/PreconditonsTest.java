package com.zyy.examples.guava.basicutils;

import org.junit.Test;

import static com.google.common.base.Preconditions.*;

/**
 * Created by zhouyinyan on 16/12/26.
 */
public class PreconditonsTest {

    @Test
    public void testPreconditons(){
        //常规检查
        Object someObj = null;
        if(someObj == null){
//            throw new IllegalArgumentException("someObj must not be null");
        }

//        checkNotNull(someObj, "someObj must not be null");

        PreconditionExample preconditionExample = new PreconditionExample("open");

        preconditionExample.updateCurrentIndexValue(4, 10);
        preconditionExample.doOperation();
    }


    public class PreconditionExample {
        private String label;
        private int[] values = new int[5];
        private int currentIndex;
        public PreconditionExample(String label) {
            //returns value of object if not null
            this.label = checkNotNull(label,"Label can''t be null");
        }
        public void updateCurrentIndexValue(int index, int valueToSet) {
            //Check index valid first
            this.currentIndex = checkElementIndex(index, values.length,
                    "Index out of bounds for values");
            //Validate valueToSet
            checkArgument(valueToSet <= 100,"Value can't be more than 100");
            values[this.currentIndex] = valueToSet;
        }
        public void doOperation(){
            checkState(validateObjectState(),"Can't perform operation");
        }
        private boolean validateObjectState(){
            return this.label.equalsIgnoreCase("open") && values[this.
                    currentIndex]==10;
        }
    }
}
