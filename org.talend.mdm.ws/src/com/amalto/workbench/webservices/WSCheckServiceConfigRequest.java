
package com.amalto.workbench.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WSCheckServiceConfigRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WSCheckServiceConfigRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="jndiName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="conf" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WSCheckServiceConfigRequest", propOrder = {
    "jndiName",
    "conf"
})
public class WSCheckServiceConfigRequest {

    @XmlElement(required = true, nillable = true)
    protected String jndiName;
    @XmlElement(required = true, nillable = true)
    protected String conf;

    /**
     * Default no-arg constructor
     * 
     */
    public WSCheckServiceConfigRequest() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public WSCheckServiceConfigRequest(final String jndiName, final String conf) {
        this.jndiName = jndiName;
        this.conf = conf;
    }

    /**
     * Gets the value of the jndiName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJndiName() {
        return jndiName;
    }

    /**
     * Sets the value of the jndiName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJndiName(String value) {
        this.jndiName = value;
    }

    /**
     * Gets the value of the conf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConf() {
        return conf;
    }

    /**
     * Sets the value of the conf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConf(String value) {
        this.conf = value;
    }

}
