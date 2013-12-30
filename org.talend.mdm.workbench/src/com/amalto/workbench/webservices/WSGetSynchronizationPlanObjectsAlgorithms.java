
package com.amalto.workbench.webservices;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WSGetSynchronizationPlanObjectsAlgorithms complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WSGetSynchronizationPlanObjectsAlgorithms">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="algorithm" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WSGetSynchronizationPlanObjectsAlgorithms", propOrder = {
    "algorithm"
})
public class WSGetSynchronizationPlanObjectsAlgorithms {

    protected List<String> algorithm;

    /**
     * Default no-arg constructor
     * 
     */
    public WSGetSynchronizationPlanObjectsAlgorithms() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public WSGetSynchronizationPlanObjectsAlgorithms(final List<String> algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * Gets the value of the algorithm property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the algorithm property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAlgorithm().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAlgorithm() {
        if (algorithm == null) {
            algorithm = new ArrayList<String>();
        }
        return this.algorithm;
    }

}
