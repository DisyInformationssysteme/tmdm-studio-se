
package com.amalto.workbench.webservices;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WSDataModelArray complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WSDataModelArray">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="wsDataModels" type="{urn-com-amalto-xtentis-webservice}WSDataModel" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WSDataModelArray", propOrder = {
    "wsDataModels"
})
public class WSDataModelArray {

    @XmlElement(required = true)
    protected List<WSDataModel> wsDataModels;

    /**
     * Default no-arg constructor
     * 
     */
    public WSDataModelArray() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public WSDataModelArray(final List<WSDataModel> wsDataModels) {
        this.wsDataModels = wsDataModels;
    }

    /**
     * Gets the value of the wsDataModels property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the wsDataModels property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWsDataModels().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WSDataModel }
     * 
     * 
     */
    public List<WSDataModel> getWsDataModels() {
        if (wsDataModels == null) {
            wsDataModels = new ArrayList<WSDataModel>();
        }
        return this.wsDataModels;
    }

}
