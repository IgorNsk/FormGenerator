package org.sbt.loans4b.tools.generate;

import org.sbt.loans4b.tools.blocks.Table;
import org.sbt.loans4b.tools.template.TemplateWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


public class FormGeneratorTest extends FormGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(FormGeneratorTest.class);
    private final Map<String, Object> parametersMap = new HashMap<>();

    public FormGeneratorTest(String templateFileName) {
        super(templateFileName);
    }

    @Override
    public void build() {

        //
        Map<String, Object> mainBlock = new HashMap<>();
        parametersMap.put("main", mainBlock);
        mainBlock.put("A1", "Иванов Петр Сергеевич");
        mainBlock.put("A2", "Петр Сергеевич");
        mainBlock.put("A3", "Сергеевич");
        mainBlock.put("A4", "***");
        //
        Map<String, Object> addBlock = new HashMap<>();
        parametersMap.put("add", addBlock);
        addBlock.put("A1", "КОнь");
        addBlock.put("A2", "Лошадь");
        addBlock.put("A3", "КОшка");
        addBlock.put("A4", "Собака");
        //
        Table penaltyList = new Table("Column1", "Column2", "Column3");
        penaltyList.addRow("c_1", 1, 11);
        penaltyList.addRow("c_2", 2, 22);
        penaltyList.addRow("c_3", 3);
        penaltyList.addRow("c_4", 4);
        penaltyList.addRow("c_5", 5, 5555.555);
        parametersMap.put("penaltyList", penaltyList);
        //
        Table feeList = new Table("Column1", "Column2", "Column3");
        feeList.addRow("f_1", 11, 111);
        feeList.addRow("f_2", 22, 222);
        feeList.addRow("f_3", 33, 123.34);
        feeList.addRow("f_4", 4, 444.44, 44444.4444);
        parametersMap.put("feeList", feeList);


        TemplateWriter templateWriter = new TemplateWriter(templateForm, parametersMap);
        templateWriter.processing();

    }


}
