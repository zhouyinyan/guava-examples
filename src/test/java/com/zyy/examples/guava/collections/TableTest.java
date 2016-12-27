package com.zyy.examples.guava.collections;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

/**
 * Created by zhouyinyan on 16/12/27.
 */
public class TableTest {

    @Test
    public void testTableOperations(){

        Table<Integer,Integer,String> table = HashBasedTable.create();

        table.put(1,1,"Rook");
        table.put(1,2,"Knight");
        table.put(1,3,"Bishop");


        Assert.assertEquals("Rook", table.get(1,1));

        Assert.assertTrue(table.contains(1,1));
        Assert.assertTrue(table.containsRow(1));
        Assert.assertTrue(table.containsColumn(1));

        Map<Integer, String> row = table.row(1);
        Assert.assertEquals(3, row.size());
        Assert.assertEquals("Knight", row.get(2));

        Map<Integer, String> column = table.column(1);
        Assert.assertEquals(1, column.size());
        Assert.assertEquals("Rook", row.get(1));


        Assert.assertNull(table.get(2,1));

        Set<Table.Cell<Integer, Integer, String>> cells =  table.cellSet();
        Assert.assertEquals(3, cells.size());

        table.remove(1, 3);
        Assert.assertEquals(2, table.size());

    }
}
