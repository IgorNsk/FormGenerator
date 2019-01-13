package org.sbt.loans4b.tools.template;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sbt.loans4b.tools.generate.FormGenerator;
import org.sbt.loans4b.tools.generate.FormGeneratorTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class FormGeneratorTestTest {

    FormGenerator form;

    @BeforeEach
    void setUp() {
        form = new FormGeneratorTest("template_form.xls");
    }

    @Test
    void makeFormTest() {
    }

    @Test
    void generateFormNameTest(){
        //FormGenerator fg = mock(FormGeneratorTest.class);
        form.setTemplateFileName("template_form.xls");
        assertAll( "",
                () -> assertTrue(form.generateFormName("ext").startsWith("out_template_form")),
                () -> assertTrue(form.generateFormName("ext").contains(".ext"))
        );

    }

    @Test
    void generateTest() {
        try {
            form.prepare();
            form.build();
            form.save(FormType.XLS);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}