package org.sbt.loans4b.tools.blocks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private static final Logger LOG = LoggerFactory.getLogger(Table.class);

    private List<String> columns = new ArrayList<>();
    private List<Row> rows = new ArrayList<>();
    private int selectRow = 0;


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
        try {
            for (Object value : values) {
                row.addCell(columns.get(rowIndex++), value);
            }
            rows.add(row);

            return true;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return false;
        }
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
