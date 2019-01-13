package org.sbt.loans4b.tools.blocks;

import java.util.ArrayList;
import java.util.List;

public class Row {
    private Table parent;
    private List<String> columns;
    private List<Cell> cells = new ArrayList<>();

    public Row(List<String> columns) {
        this.columns = columns;
    }

    public boolean addCell(String column, Object value){
        if(!columns.contains(column)){
            return false;
        }

        int idx = columns.indexOf(column);
        Cell cell = new Cell(value);
        cells.add(idx, cell);

        return true;
    }


    public Cell getCell(int index){
        return cells.get(index);
    }

    public Cell getCell(String column){
        return cells.get(columns.indexOf(column));
    }

    @Override
    public String toString() {
        return "Row{" +
                "columns=" + columns +
                ", cells=" + cells +
                '}';
    }
}
