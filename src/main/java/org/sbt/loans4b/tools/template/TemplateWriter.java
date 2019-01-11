package org.sbt.loans4b.tools.template;

import com.aspose.cells.Cell;
import com.aspose.cells.Row;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class TemplateWriter {

    private static final Logger LOG = LoggerFactory.getLogger(TemplateWriter.class);

    private final Workbook workbook;
    private final Map<String, Object> map;

    private int currentSheet;
    private int currentRow;
    private int currenColumn;
    private int maxRow;

    private String currentTable = "";

    public TemplateWriter(Workbook workbook, Map<String, Object> map) {
        this.workbook = workbook;
        this.map = map;
    }

    //

    public void processingWorksheets() {
        workbook.getWorksheets()
                .forEach(fillWorksheet);
    }

    /**
     *
     */
    private Consumer<Worksheet> fillWorksheet = worksheet -> {
        LOG.debug(worksheet.getName());
        currentSheet = worksheet.getIndex();
        maxRow = worksheet.getCells().getMaxDataRow();
        while (currentRow <= maxRow) {
            processingRow(worksheet.getCells().getRows().getRowByIndex(currentRow));
            currentRow++;
        }
    };

    private void processingRow(Row row) {

        Optional<Cell> lastDataCell = Optional.ofNullable(row.getLastDataCell());
        int lastDataCellIdx = lastDataCell.isPresent() ? lastDataCell.get().getColumn() : -1;
        if(lastDataCellIdx != -1){
            LOG.debug("rowIdx = " + row.getFirstCell().getName());
        }
        for (int i = 0; i <= lastDataCellIdx; i++) {
            Optional<Cell> cell = Optional.ofNullable(row.getCellOrNull(i));
            if (cell.isPresent()) {
                processingCell(cell.get());
            }
        }

    }

    private void processingCell(Cell cell) {
        //LOG.debug(cell.toString());
        currenColumn = cell.getColumn();
        Optional<Object> value = Optional.ofNullable(cell.getValue());
        if (value.isPresent() && value.toString().contains("%{#")) {
            LOG.debug("====================================");
            Tag tag = new Tag(cell.getStringValue());
            tag.parse();

            if(tag.isStartForeach()) {
                currentTable = tag.getCollection();
            } else if(tag.isEndForteach()) {
                currentTable = "";
            }

            currentRow++;
            Row row = workbook.getWorksheets().get(currentSheet).getCells().getRows().getRowByIndex(currentRow);
            processingRow(row);

        } else if (value.isPresent() && value.toString().contains("%{")) {
            Tag tag = new Tag(cell.getStringValue());
            tag.parse();

            LOG.debug(map.toString());

            Optional<String> namespace = Optional.ofNullable(tag.getNamespace());
            if(!"".equals(currentTable)) {
                namespace = Optional.of(currentTable);
            }

            Map<String, Object> attributes = (Map<String, Object>) map.get(namespace.orElse(""));

            if (attributes != null) {
                LOG.debug(attributes.toString());

                String newValue = (String) attributes.get(tag.getAttr());
                LOG.debug("newValue: " + newValue);
                cell.setValue(newValue);
            }
        }

    }


}
