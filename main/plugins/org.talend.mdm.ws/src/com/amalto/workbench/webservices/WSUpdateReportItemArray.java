
package com.amalto.workbench.webservices;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WSUpdateReportItemArray complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WSUpdateReportItemArray">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="wsUpdateReportItemPOJO" type="{urn-com-amalto-xtentis-webservice}WSUpdateReportItemPOJO" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WSUpdateReportItemArray", propOrder = {
    "wsUpdateReportItemPOJO"
})
public class WSUpdateReportItemArray {

    @XmlElement(required = true, nillable = true)
    protected List<WSUpdateReportItemPOJO> wsUpdateReportItemPOJO;

    /**
     * Default no-arg constructor
     * 
     */
    public WSUpdateReportItemArray() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public WSUpdateReportItemArray(final List<WSUpdateReportItemPOJO> wsUpdateReportItemPOJO) {
        this.wsUpdateReportItemPOJO = wsUpdateReportItemPOJO;
    }

    /**
     * Gets the value of the wsUpdateReportItemPOJO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the wsUpdateReportItemPOJO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWsUpdateReportItemPOJO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WSUpdateReportItemPOJO }
     * 
     * 
     */
    public List<WSUpdateReportItemPOJO> getWsUpdateReportItemPOJO() {
        if (wsUpdateReportItemPOJO == null) {
            wsUpdateReportItemPOJO = new ArrayList<WSUpdateReportItemPOJO>();
        }
        return this.wsUpdateReportItemPOJO;
    }

}
