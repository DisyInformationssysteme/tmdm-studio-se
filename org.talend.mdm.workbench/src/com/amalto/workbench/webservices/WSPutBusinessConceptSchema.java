
package com.amalto.workbench.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WSPutBusinessConceptSchema complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WSPutBusinessConceptSchema">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="wsDataModelPK" type="{urn-com-amalto-xtentis-webservice}WSDataModelPK"/>
 *         &lt;element name="businessConceptSchema" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WSPutBusinessConceptSchema", propOrder = {
    "wsDataModelPK",
    "businessConceptSchema"
})
public class WSPutBusinessConceptSchema {

    @XmlElement(required = true)
    protected WSDataModelPK wsDataModelPK;
    @XmlElement(required = true)
    protected String businessConceptSchema;

    /**
     * Default no-arg constructor
     * 
     */
    public WSPutBusinessConceptSchema() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public WSPutBusinessConceptSchema(final WSDataModelPK wsDataModelPK, final String businessConceptSchema) {
        this.wsDataModelPK = wsDataModelPK;
        this.businessConceptSchema = businessConceptSchema;
    }

    /**
     * Gets the value of the wsDataModelPK property.
     * 
     * @return
     *     possible object is
     *     {@link WSDataModelPK }
     *     
     */
    public WSDataModelPK getWsDataModelPK() {
        return wsDataModelPK;
    }

    /**
     * Sets the value of the wsDataModelPK property.
     * 
     * @param value
     *     allowed object is
     *     {@link WSDataModelPK }
     *     
     */
    public void setWsDataModelPK(WSDataModelPK value) {
        this.wsDataModelPK = value;
    }

    /**
     * Gets the value of the businessConceptSchema property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessConceptSchema() {
        return businessConceptSchema;
    }

    /**
     * Sets the value of the businessConceptSchema property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessConceptSchema(String value) {
        this.businessConceptSchema = value;
    }

}
