package org.sbt.loans4b.tools;

import org.sbt.loans4b.tools.generate.FormGenerator;
import org.sbt.loans4b.tools.generate.FormGeneratorTest;
import org.sbt.loans4b.tools.template.FormType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main( String[] args )
    {
        LOG.info( "Hello World!" );
        FormGenerator form = new FormGeneratorTest("template_form.xls");
        try {
            form.prepare();
            form.build();
            form.save(FormType.PDF);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
