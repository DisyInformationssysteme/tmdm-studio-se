
package com.amalto.workbench.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Returns the list of available Transformer Plugins in a WSTransformerPluginV2sList 
 * 				that provides their JNDI Name and Description in the requested language
 * 			
 * 
 * <p>Java class for WSGetTransformerPluginV2sList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WSGetTransformerPluginV2sList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="language" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WSGetTransformerPluginV2sList", propOrder = {
    "language"
})
public class WSGetTransformerPluginV2SList {

    @XmlElement(required = true, nillable = true)
    protected String language;

    /**
     * Default no-arg constructor
     * 
     */
    public WSGetTransformerPluginV2SList() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public WSGetTransformerPluginV2SList(final String language) {
        this.language = language;
    }

    /**
     * Gets the value of the language property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguage(String value) {
        this.language = value;
    }

}
