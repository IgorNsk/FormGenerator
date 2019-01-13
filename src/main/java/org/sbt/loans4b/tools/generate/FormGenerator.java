package org.sbt.loans4b.tools.generate;

import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.SaveOptions;
import com.aspose.cells.Workbook;
import com.aspose.cells.XlsSaveOptions;
import org.sbt.loans4b.tools.template.FormType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

public abstract class FormGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(FormGenerator.class);

    private String templateFileName;
    protected Workbook templateForm;

    public FormGenerator(String templateFileName) {
        this.templateFileName = templateFileName;
    }

    public void save(FormType formType) throws Exception{
        //

        SaveOptions saveOptions;
        String fileNameExt;

        switch (formType) {
            case XLS:
                saveOptions = new XlsSaveOptions();
                fileNameExt = "xls";
                break;
            case PDF:
                saveOptions = new PdfSaveOptions();
                fileNameExt = "pdf";
                break;
            default:
                saveOptions = new PdfSaveOptions();
                fileNameExt = "pdf";
                break;
        }
        String formFileName = generateFormName(fileNameExt);
        try (OutputStream out = new FileOutputStream(formFileName)) {
            templateForm.save(out, saveOptions);
        }

    }

    public void prepare() throws Exception {
        try (InputStream inputStream = FormGeneratorTest.class.getClassLoader().getResourceAsStream(templateFileName)) {
            templateForm = new Workbook(inputStream);
        }
    }



    public String generateFormName(String extName) {
        Long ms = new Date().getTime();
        return "out_" + templateFileName.substring(0,templateFileName.indexOf(".")) + "_"+ms.toString()+ "." + extName;
    }


    public abstract void build();

    //


    public void setTemplateFileName(String templateFileName) {
        this.templateFileName = templateFileName;
    }
}
