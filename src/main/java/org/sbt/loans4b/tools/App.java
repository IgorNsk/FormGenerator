package org.sbt.loans4b.tools;

import org.sbt.loans4b.tools.template.FormGenerator;
import org.sbt.loans4b.tools.template.FormGeneratorImpl;
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
        FormGenerator form = new FormGeneratorImpl();
        form.generate("rsv.xls");
    }
}
