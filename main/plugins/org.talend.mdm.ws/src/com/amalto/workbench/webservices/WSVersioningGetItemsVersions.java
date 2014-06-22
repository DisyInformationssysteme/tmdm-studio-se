
package com.amalto.workbench.webservices;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Get tagging versions for one or more Items instances
 * 			
 * 
 * <p>Java class for WSVersioningGetItemsVersions complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WSVersioningGetItemsVersions">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="versioningSystemName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="wsItemPKs" type="{urn-com-amalto-xtentis-webservice}WSItemPK" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WSVersioningGetItemsVersions", propOrder = {
    "versioningSystemName",
    "wsItemPKs"
})
public class WSVersioningGetItemsVersions {

    @XmlElement(required = true, nillable = true)
    protected String versioningSystemName;
    @XmlElement(required = true)
    protected List<WSItemPK> wsItemPKs;

    /**
     * Default no-arg constructor
     * 
     */
    public WSVersioningGetItemsVersions() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public WSVersioningGetItemsVersions(final String versioningSystemName, final List<WSItemPK> wsItemPKs) {
        this.versioningSystemName = versioningSystemName;
        this.wsItemPKs = wsItemPKs;
    }

    /**
     * Gets the value of the versioningSystemName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersioningSystemName() {
        return versioningSystemName;
    }

    /**
     * Sets the value of the versioningSystemName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersioningSystemName(String value) {
        this.versioningSystemName = value;
    }

    /**
     * Gets the value of the wsItemPKs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the wsItemPKs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWsItemPKs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WSItemPK }
     * 
     * 
     */
    public List<WSItemPK> getWsItemPKs() {
        if (wsItemPKs == null) {
            wsItemPKs = new ArrayList<WSItemPK>();
        }
        return this.wsItemPKs;
    }

}
