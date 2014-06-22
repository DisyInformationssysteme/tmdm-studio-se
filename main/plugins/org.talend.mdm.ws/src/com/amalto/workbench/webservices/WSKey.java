
package com.amalto.workbench.webservices;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				In simple terms: each key field is found using the path selectorpath/fieldpath within the element
 * 			
 * 
 * <p>Java class for WSKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WSKey">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="selectorpath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fieldpath" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WSKey", propOrder = {
    "selectorpath",
    "fieldpath"
})
public class WSKey {

    @XmlElement(required = true)
    protected String selectorpath;
    @XmlElement(required = true)
    protected List<String> fieldpath;

    /**
     * Default no-arg constructor
     * 
     */
    public WSKey() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public WSKey(final String selectorpath, final List<String> fieldpath) {
        this.selectorpath = selectorpath;
        this.fieldpath = fieldpath;
    }

    /**
     * Gets the value of the selectorpath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelectorpath() {
        return selectorpath;
    }

    /**
     * Sets the value of the selectorpath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelectorpath(String value) {
        this.selectorpath = value;
    }

    /**
     * Gets the value of the fieldpath property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fieldpath property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFieldpath().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFieldpath() {
        if (fieldpath == null) {
            fieldpath = new ArrayList<String>();
        }
        return this.fieldpath;
    }

}
