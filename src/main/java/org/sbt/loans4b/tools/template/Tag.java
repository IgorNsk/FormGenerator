package org.sbt.loans4b.tools.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tag {

    private static final Logger LOG = LoggerFactory.getLogger(Tag.class);

    private String value;
    private String namespace;
    private String attr;
    private String collection;
    private String item;
    private String index;
    private boolean startForeach = false;
    private boolean endForteach = false;

    public Tag(String value) {
        this.value = value.trim().toLowerCase();
    }

    public void parse() {
        LOG.debug("value.trim(): " + value);
        if (!value.contains("#foreach") && !value.contains("#end_foreach")) {
            namespace = value.substring(2, value.indexOf("."));
            attr = value.substring(value.indexOf(".") + 1, value.indexOf("}"));
            LOG.debug("namespace.attr: " + namespace + " " + attr);
        } else if (value.contains("#foreach")) {

            startForeach = true;

            int fromPos = value.indexOf(":", 0);
            int toPos = value.indexOf(";", fromPos);
            collection = value.substring(++fromPos, toPos);

            fromPos = value.indexOf(":", toPos);
            toPos = value.indexOf(";", fromPos);
            item = value.substring(++fromPos, toPos);

            fromPos = value.indexOf(":", toPos);
            toPos = value.indexOf(")", fromPos);
            index = value.substring(++fromPos, toPos);

            LOG.debug(String.format("parse: %s %s %s", collection, item, index));
        } else if (value.contains("#end_foreach")) {
            endForteach = true;

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

    public String getCollection() {
        return collection;
    }

    public String getItem() {
        return item;
    }

    public String getIndex() {
        return index;
    }

    public boolean isStartForeach() {
        return startForeach;
    }

    public boolean isEndForteach() {
        return endForteach;
    }
}
