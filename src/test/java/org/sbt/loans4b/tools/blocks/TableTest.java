package org.sbt.loans4b.tools.blocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {


    Table table;

    @BeforeEach
    void setUp() {
        table = new Table();
        initTable();
    }

    @Test
    void addColumnTest() {
        table.getColumns().stream().forEach(System.out::println);
        List<String> columns = Arrays.asList("A","B","C");
        assertTrue(columns.equals(table.getColumns()));
    }

    @Test
    void addRowTest() {
        initRows();
        table.getRows().stream().forEach(System.out::println);

    }

    @Test
    void getRowsTest() {
        initRows();
        table.getRows().stream().forEach(showByIndex);
        table.getRows().stream().forEach(showByColumn);
    }


    private Consumer<Row> showByIndex = row -> {
        System.out.println(row.getCell(0).getValue());
    };

    private Consumer<Row> showByColumn = row -> {
        System.out.println(row.getCell("C").getValue());
    };

    private void initTable() {
        table.addColumn("A");
        table.addColumn("B");
        table.addColumn("C");
        table.addColumn("C");
    }

    private void initRows() {
        table.addRow("1", "2", "3");
        table.addRow("21", "22", "23");
        table.addRow(31, 32, 33);
    }

}