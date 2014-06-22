
package com.amalto.workbench.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WSDeleteSynchronizationItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WSDeleteSynchronizationItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="wsSynchronizationItemPK" type="{urn-com-amalto-xtentis-webservice}WSSynchronizationItemPK"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WSDeleteSynchronizationItem", propOrder = {
    "wsSynchronizationItemPK"
})
public class WSDeleteSynchronizationItem {

    @XmlElement(required = true)
    protected WSSynchronizationItemPK wsSynchronizationItemPK;

    /**
     * Default no-arg constructor
     * 
     */
    public WSDeleteSynchronizationItem() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public WSDeleteSynchronizationItem(final WSSynchronizationItemPK wsSynchronizationItemPK) {
        this.wsSynchronizationItemPK = wsSynchronizationItemPK;
    }

    /**
     * Gets the value of the wsSynchronizationItemPK property.
     * 
     * @return
     *     possible object is
     *     {@link WSSynchronizationItemPK }
     *     
     */
    public WSSynchronizationItemPK getWsSynchronizationItemPK() {
        return wsSynchronizationItemPK;
    }

    /**
     * Sets the value of the wsSynchronizationItemPK property.
     * 
     * @param value
     *     allowed object is
     *     {@link WSSynchronizationItemPK }
     *     
     */
    public void setWsSynchronizationItemPK(WSSynchronizationItemPK value) {
        this.wsSynchronizationItemPK = value;
    }

}
