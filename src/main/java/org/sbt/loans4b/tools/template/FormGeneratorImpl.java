package org.sbt.loans4b.tools.template;

import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.SaveOptions;
import org.sbt.loans4b.tools.blocks.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.aspose.cells.Workbook;

public class FormGeneratorImpl implements FormGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(FormGeneratorImpl.class);

    @Override
    public void makeForm() {
        //

    }

    @Override
    public void generate(String templateFileName) {
        LOG.debug("call");
        Workbook workbook = null;

        try (InputStream inputStream = FormGeneratorImpl.class.getClassLoader().getResourceAsStream(templateFileName)) {
            workbook = new Workbook(inputStream);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

        Map<String, Object> namespace = new HashMap<>();
        //
        Map<String, Object> main = new HashMap<>();
        namespace.put("main", main);
        main.put("auditor_name", "Иванов Петр Сергеевич");
        //
        Table penaltyList = new Table("TITLE");
        penaltyList.addRow("title_1");
        penaltyList.addRow("title_2");
        penaltyList.addRow("title_3");
        namespace.put("penaltyList", penaltyList);



        TemplateWriter templateWriter = new TemplateWriter(workbook, namespace);
        templateWriter.processingWorksheets();

        if (workbook != null) {
            SaveOptions saveOptions = new PdfSaveOptions();
            try (OutputStream out = new FileOutputStream(generateOutFormName(templateFileName, ".pdf"))) {
                workbook.save(out, saveOptions);
            } catch (Exception e) {
                LOG.error(e.getMessage());
            }
        }

    }

    private String generateOutFormName(String templateName, String extName) {
        return "out_" + templateName + extName;
    }
}
