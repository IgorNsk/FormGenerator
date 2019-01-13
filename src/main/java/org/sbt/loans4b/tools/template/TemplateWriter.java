package org.sbt.loans4b.tools.template;

import com.aspose.cells.*;
import org.sbt.loans4b.tools.blocks.Table;
import org.sbt.loans4b.tools.blocks.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class TemplateWriter {

    private static final Logger LOG = LoggerFactory.getLogger(TemplateWriter.class);

    private final Workbook workbook;
    private final Map<String, Object> map;

    private int currentSheet;
    private int maxRowIdx;
    private int currentRowIdx;

    private int currenColumn;

    private String currentTableName = "";
    private int rowTableIdx = 0;

    public TemplateWriter(Workbook workbook, Map<String, Object> map) {
        this.workbook = workbook;
        this.map = map;
    }

    //

    public void processing() {
        try {
            workbook.getWorksheets()
                    .forEach(processingWorksheet);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }


    /**
     *
     */
    private final Consumer<Worksheet> processingWorksheet = worksheet -> {
        LOG.debug(worksheet.getName());
        currentSheet = worksheet.getIndex();
        RowCollection rows = worksheet.getCells().getRows();
        maxRowIdx = worksheet.getCells().getMaxDataRow();
        LOG.debug("maxRowIdx= " + maxRowIdx);
        currentRowIdx = 0;
        while (currentRowIdx <= maxRowIdx) {
            try {
                processingRow(rows.get(currentRowIdx));
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(),e.getCause());
            }
            currentRowIdx++;
        }
    };


    private void processingRow(Row row) throws Exception{
        LOG.debug(String.format("currentRowIdx = %s RowIdx = %s ~~~~~~~~~~~~~~~~~~~~~~~~~~", currentRowIdx, row.getIndex()));
        if (!currentTableName.isEmpty()) {
            LOG.debug(String.format("tabelName= %s tableRow= %s", currentTableName, rowTableIdx));
        }

        Optional<Cell> lastDataCell = Optional.ofNullable(row.getLastDataCell());
        int lastDataCellIdx = lastDataCell.map(c -> c.getColumn()).orElse(-1);

        for (int i = 0; i <= lastDataCellIdx; i++) {
            Optional<Cell> cell = Optional.ofNullable(row.getCellOrNull(i));
            //cell.map(c -> processingCell(c));
            if (cell.isPresent()) {
                if (!processingCell(cell.get())) {
                    break;
                }
            }
        }

        if (!currentTableName.isEmpty()) {
            rowTableIdx++;
        }

        LOG.debug(String.format("currentRowIdx = %s RowIdx = %s =========================", currentRowIdx, row.getIndex()));
    }


    private boolean processingCell(Cell cell) throws Exception{

        boolean done = true;

        currenColumn = cell.getColumn();
        Optional<Object> value = Optional.ofNullable(cell.getValue());

        if (value.orElse("").toString().contains("%{")) {
            Tag tag = value.map(v -> new Tag(v.toString()).parse()).get();

            if (tag.isStartTable()) {
                currentTableName = tag.getCollection();
                rowTableIdx = -1;
                insertRows();
                deleteRow(currentRowIdx--);
                done = false;

            } else if (tag.isEndTable()) {
                currentTableName = "";
                deleteRow(currentRowIdx);
                done = false;

            } else {
                Optional<String> namespace = Optional.ofNullable(tag.getNamespace());
                if (!currentTableName.isEmpty()) {
                    namespace = Optional.of(currentTableName);
                    Table table = (Table) map.get(namespace.orElse(""));

                    Object newValue;
                    try {
                        newValue = table.getRows().get(rowTableIdx).getCell(tag.getAttr()).getValue();
                    } catch (IndexOutOfBoundsException e) {
                        LOG.error("Не инициализировано значение формы");
                        newValue = "НЕ ОПРЕДЕЛЕНО";
                        Style style = cell.getStyle();
                        Font font = style.getFont();
                        font.setColor(Color.getRed());
                        cell.setStyle(style);
                    }

                    LOG.debug("newValue: " + newValue);
                    cell.setValue(newValue);

                } else {

                    Map<String, Object> valuesMap = (Map<String, Object>) map.get(namespace.orElse(""));

                    if (valuesMap != null) {
                        LOG.debug(valuesMap.toString());

                        Object newValue = valuesMap.get(tag.getAttr());
                        LOG.debug("newValue: " + newValue);
                        if (newValue != null) {
                            cell.setValue(newValue);
                        }
                    }
                }

            }


        }


        return done;

    }

    private void insertRows() throws Exception {
        LOG.debug("+++++++++++++++++++++++++++++++++++++++++");

        Table table = (Table) map.get(currentTableName);
        int rowCount = table.getRowCount();

        Cells cells = workbook.getWorksheets().get(currentSheet).getCells();
        workbook.getWorksheets().get(currentSheet).getCells().insertRows(currentRowIdx + 2, rowCount - 1);

        for (int i = 0; i < rowCount - 1; i++) {
            workbook.getWorksheets().get(currentSheet).getCells().copyRow(cells, currentRowIdx + 1, currentRowIdx + 2 + i);
        }
        //workbook.getWorksheets().get(currentSheet).getCells().copyRows(cells, currentRowIdx + 1, currentRowIdx + 2, 3);
        maxRowIdx += rowCount - 1;

    }

    private void deleteRow(int deleteIdx) {
        workbook.getWorksheets().get(currentSheet).getCells().deleteRows(deleteIdx, 1, true);
        maxRowIdx -= 1;
        LOG.debug("------------------------------------------" + deleteIdx);
    }


}
