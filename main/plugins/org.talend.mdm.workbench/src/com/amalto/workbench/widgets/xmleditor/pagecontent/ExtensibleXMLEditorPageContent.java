package com.amalto.workbench.widgets.xmleditor.pagecontent;

import java.io.IOException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.dom.DOMDocumentFactory;

import com.amalto.workbench.utils.XmlUtil;
import com.sun.xml.bind.StringInputStream;

public abstract class ExtensibleXMLEditorPageContent {

    public ExtensibleXMLEditorPageContent() {
        initToDefault();
    }

    public void init(String xmlExrepssion) {

        StringInputStream inStream = null;

        try {
            inStream = new StringInputStream(xmlExrepssion);
            initByXMLExpression(XmlUtil.parse(inStream));
        } catch (Exception e) {
            initToDefault();
        } finally {

            if (inStream != null)
                try {
                    inStream.close();
                } catch (IOException e) {

                }
        }

    }

    public String toXMLExpression() {

        Document xmlDoc = DOMDocumentFactory.getInstance().createDocument();

        fillXMLDoc(xmlDoc);

        try {
            return XmlUtil.formatPretty(XmlUtil.toXml(xmlDoc), "UTF-8");//$NON-NLS-1$
        } catch (DocumentException e) {
            return "";//$NON-NLS-1$
        }
    }

    protected String getSingleValueByPath(Node parent, String relativePath, String valueForNull) {

        if (parent == null)
            return valueForNull;

        Node node = parent.selectSingleNode("./" + relativePath);//$NON-NLS-1$

        if (node == null)
            return valueForNull;

        return node.getText();
    }

    protected List<?> getXMLNodesByPath(Node parent, String relativePath) {

        return parent.selectNodes("./" + relativePath);//$NON-NLS-1$
    }

    protected String getPathRelativeToRoot(String tagName) {
        return getRootElementName() + "/" + tagName;//$NON-NLS-1$
    }

    protected abstract String getRootElementName();

    protected abstract void fillXMLDoc(Node parent);

    protected abstract void initToDefault();

    protected abstract void initByXMLExpression(Node parent);

}