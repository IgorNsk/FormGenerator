package org.sbt.loans4b.tools.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tag {

    private static final Logger LOG = LoggerFactory.getLogger(Tag.class);

    private String value;
    private String namespace;
    private String attr;

    public Tag(String value) {
        this.value = value.trim().toLowerCase();
    }

    public void parse() {
        LOG.debug("value.trim(): " + value);
        if(!value.contains("{#")) {
            namespace = value.substring(2, value.indexOf("."));
            attr = value.substring(value.indexOf(".") + 1, value.indexOf("}"));
            LOG.debug("namespace.attr: " + namespace + " " + attr);
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }
}
