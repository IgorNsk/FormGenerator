package org.sbt.loans4b.tools.blocks;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private List<String> columns = new ArrayList<>();
    private List<Row> rows = new ArrayList<>();


    public Table(String... columns) {
        for (String column : columns) {
            addColumn(column);
        }
    }

    public Table(List<String> columns){
        this.columns = columns;
    }


    public boolean addColumn(String name) {
        if (columns.contains(name)) {
            return false;
        }
        if (rows.size() != 0) {
            return false;
        }
        columns.add(name);
        return true;
    }

    public boolean addColumns(String... columns) {
        for (String column : columns) {
            addColumn(column);
        }
        return true;
    }

    public boolean addRow(Object... values) {
        Row row = new Row(columns);
        int rowIndex = 0;
        for (Object value : values) {
            row.addCell(columns.get(rowIndex++), value);
        }
        rows.add(row);

        return true;
    }

    public List<String> getColumns() {
        return columns;
    }

    public List<Row> getRows() {
        return rows;
    }

    public int getRowCount() {
        return rows.size();
    }

}
