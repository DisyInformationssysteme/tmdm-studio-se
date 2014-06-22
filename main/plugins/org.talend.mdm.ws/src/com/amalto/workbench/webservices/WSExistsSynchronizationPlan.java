
package com.amalto.workbench.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WSExistsSynchronizationPlan complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WSExistsSynchronizationPlan">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="wsSynchronizationPlanPK" type="{urn-com-amalto-xtentis-webservice}WSSynchronizationPlanPK"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WSExistsSynchronizationPlan", propOrder = {
    "wsSynchronizationPlanPK"
})
public class WSExistsSynchronizationPlan {

    @XmlElement(required = true)
    protected WSSynchronizationPlanPK wsSynchronizationPlanPK;

    /**
     * Default no-arg constructor
     * 
     */
    public WSExistsSynchronizationPlan() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public WSExistsSynchronizationPlan(final WSSynchronizationPlanPK wsSynchronizationPlanPK) {
        this.wsSynchronizationPlanPK = wsSynchronizationPlanPK;
    }

    /**
     * Gets the value of the wsSynchronizationPlanPK property.
     * 
     * @return
     *     possible object is
     *     {@link WSSynchronizationPlanPK }
     *     
     */
    public WSSynchronizationPlanPK getWsSynchronizationPlanPK() {
        return wsSynchronizationPlanPK;
    }

    /**
     * Sets the value of the wsSynchronizationPlanPK property.
     * 
     * @param value
     *     allowed object is
     *     {@link WSSynchronizationPlanPK }
     *     
     */
    public void setWsSynchronizationPlanPK(WSSynchronizationPlanPK value) {
        this.wsSynchronizationPlanPK = value;
    }

}
