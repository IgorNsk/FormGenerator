package org.sbt.loans4b.tools.blocks;

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
    private boolean startTable = false;
    private boolean endTable = false;

    public Tag(String value) {
        this.value = value.trim();
    }

    public Tag parse() {

        if (!value.contains("#table") && !value.contains("#end_table")) {
            namespace = value.substring(2, value.indexOf("."));
            attr = value.substring(value.indexOf(".") + 1, value.indexOf("}"));
        } else if (value.contains("#table")) {

            startTable = true;

            int fromPos = value.indexOf(":", 0);
            int toPos = value.indexOf(";", fromPos);
            collection = value.substring(++fromPos, toPos);

            fromPos = value.indexOf(":", toPos);
            toPos = value.indexOf(";", fromPos);
            item = value.substring(++fromPos, toPos);

            fromPos = value.indexOf(":", toPos);
            toPos = value.indexOf(")", fromPos);
            index = value.substring(++fromPos, toPos);

            LOG.debug(String.format("parse: collection= %s item= %s index= %s", collection, item, index));
        } else if (value.contains("#end_table")) {
            endTable = true;

        }
        return this;
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

    public boolean isStartTable() {
        return startTable;
    }

    public boolean isEndTable() {
        return endTable;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "value='" + value + '\'' +
                ", namespace='" + namespace + '\'' +
                ", attr='" + attr + '\'' +
                ", collection='" + collection + '\'' +
                ", item='" + item + '\'' +
                ", index='" + index + '\'' +
                ", startTable=" + startTable +
                ", endTable=" + endTable +
                '}';
    }
}
