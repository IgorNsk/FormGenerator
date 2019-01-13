package org.sbt.loans4b.tools.template;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sbt.loans4b.tools.generate.FormGenerator;
import org.sbt.loans4b.tools.generate.FormGeneratorTest;

class FormGeneratorTestTest {

    FormGenerator form;

    @BeforeEach
    void setUp() {
        form = new FormGeneratorTest();
    }

    @Test
    void makeFormTest() {
    }

    @Test
    void generateTest() {
        try {
            form.prepare("template_form.xls");
            form.build();
            form.save(FormType.XLS);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}