
package com.amalto.workbench.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Processes the content of a local (to the data manager)  file 
 * 				after transformation in a Transformer
 * 				and using a DecisionTable for the ouput variables
 * 			
 * 
 * <p>Java class for WSProcessFileUsingTransformer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WSProcessFileUsingTransformer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fileName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contentType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="wsTransformerPK" type="{urn-com-amalto-xtentis-webservice}WSTransformerPK"/>
 *         &lt;element name="wsOutputDecisionTable" type="{urn-com-amalto-xtentis-webservice}WSOutputDecisionTable"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WSProcessFileUsingTransformer", propOrder = {
    "fileName",
    "contentType",
    "wsTransformerPK",
    "wsOutputDecisionTable"
})
public class WSProcessFileUsingTransformer {

    @XmlElement(required = true)
    protected String fileName;
    @XmlElement(required = true, nillable = true)
    protected String contentType;
    @XmlElement(required = true)
    protected WSTransformerPK wsTransformerPK;
    @XmlElement(required = true, nillable = true)
    protected WSOutputDecisionTable wsOutputDecisionTable;

    /**
     * Default no-arg constructor
     * 
     */
    public WSProcessFileUsingTransformer() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public WSProcessFileUsingTransformer(final String fileName, final String contentType, final WSTransformerPK wsTransformerPK, final WSOutputDecisionTable wsOutputDecisionTable) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.wsTransformerPK = wsTransformerPK;
        this.wsOutputDecisionTable = wsOutputDecisionTable;
    }

    /**
     * Gets the value of the fileName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the value of the fileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileName(String value) {
        this.fileName = value;
    }

    /**
     * Gets the value of the contentType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets the value of the contentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContentType(String value) {
        this.contentType = value;
    }

    /**
     * Gets the value of the wsTransformerPK property.
     * 
     * @return
     *     possible object is
     *     {@link WSTransformerPK }
     *     
     */
    public WSTransformerPK getWsTransformerPK() {
        return wsTransformerPK;
    }

    /**
     * Sets the value of the wsTransformerPK property.
     * 
     * @param value
     *     allowed object is
     *     {@link WSTransformerPK }
     *     
     */
    public void setWsTransformerPK(WSTransformerPK value) {
        this.wsTransformerPK = value;
    }

    /**
     * Gets the value of the wsOutputDecisionTable property.
     * 
     * @return
     *     possible object is
     *     {@link WSOutputDecisionTable }
     *     
     */
    public WSOutputDecisionTable getWsOutputDecisionTable() {
        return wsOutputDecisionTable;
    }

    /**
     * Sets the value of the wsOutputDecisionTable property.
     * 
     * @param value
     *     allowed object is
     *     {@link WSOutputDecisionTable }
     *     
     */
    public void setWsOutputDecisionTable(WSOutputDecisionTable value) {
        this.wsOutputDecisionTable = value;
    }

}
