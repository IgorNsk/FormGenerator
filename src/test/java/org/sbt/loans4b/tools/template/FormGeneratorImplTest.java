package org.sbt.loans4b.tools.template;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FormGeneratorImplTest {

    FormGenerator form;

    @BeforeEach
    void setUp() {
        form = new FormGeneratorImpl();
    }

    @Test
    void makeForm() {
    }

    @Test
    void generate() {
        form.generate("rsv.xls");
    }
}