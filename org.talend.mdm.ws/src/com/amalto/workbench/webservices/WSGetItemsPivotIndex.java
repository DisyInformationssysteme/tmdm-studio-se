
package com.amalto.workbench.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Get items hierarchical tree according to pivots
 * 			
 * 
 * <p>Java class for WSGetItemsPivotIndex complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WSGetItemsPivotIndex">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="clusterName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mainPivotName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pivotWithKeys" type="{urn-com-amalto-xtentis-webservice}WSLinkedHashMap"/>
 *         &lt;element name="indexPaths" type="{urn-com-amalto-xtentis-webservice}WSStringArray"/>
 *         &lt;element name="whereItem" type="{urn-com-amalto-xtentis-webservice}WSWhereItem" minOccurs="0"/>
 *         &lt;element name="pivotDirections" type="{urn-com-amalto-xtentis-webservice}WSStringArray" minOccurs="0"/>
 *         &lt;element name="indexDirections" type="{urn-com-amalto-xtentis-webservice}WSStringArray" minOccurs="0"/>
 *         &lt;element name="start" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="limit" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WSGetItemsPivotIndex", propOrder = {
    "clusterName",
    "mainPivotName",
    "pivotWithKeys",
    "indexPaths",
    "whereItem",
    "pivotDirections",
    "indexDirections",
    "start",
    "limit"
})
public class WSGetItemsPivotIndex {

    @XmlElement(required = true)
    protected String clusterName;
    @XmlElement(required = true)
    protected String mainPivotName;
    @XmlElement(required = true)
    protected WSLinkedHashMap pivotWithKeys;
    @XmlElement(required = true)
    protected WSStringArray indexPaths;
    @XmlElement(nillable = true)
    protected WSWhereItem whereItem;
    @XmlElement(nillable = true)
    protected WSStringArray pivotDirections;
    @XmlElement(nillable = true)
    protected WSStringArray indexDirections;
    protected int start;
    protected int limit;

    /**
     * Default no-arg constructor
     * 
     */
    public WSGetItemsPivotIndex() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public WSGetItemsPivotIndex(final String clusterName, final String mainPivotName, final WSLinkedHashMap pivotWithKeys, final WSStringArray indexPaths, final WSWhereItem whereItem, final WSStringArray pivotDirections, final WSStringArray indexDirections, final int start, final int limit) {
        this.clusterName = clusterName;
        this.mainPivotName = mainPivotName;
        this.pivotWithKeys = pivotWithKeys;
        this.indexPaths = indexPaths;
        this.whereItem = whereItem;
        this.pivotDirections = pivotDirections;
        this.indexDirections = indexDirections;
        this.start = start;
        this.limit = limit;
    }

    /**
     * Gets the value of the clusterName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClusterName() {
        return clusterName;
    }

    /**
     * Sets the value of the clusterName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClusterName(String value) {
        this.clusterName = value;
    }

    /**
     * Gets the value of the mainPivotName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMainPivotName() {
        return mainPivotName;
    }

    /**
     * Sets the value of the mainPivotName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMainPivotName(String value) {
        this.mainPivotName = value;
    }

    /**
     * Gets the value of the pivotWithKeys property.
     * 
     * @return
     *     possible object is
     *     {@link WSLinkedHashMap }
     *     
     */
    public WSLinkedHashMap getPivotWithKeys() {
        return pivotWithKeys;
    }

    /**
     * Sets the value of the pivotWithKeys property.
     * 
     * @param value
     *     allowed object is
     *     {@link WSLinkedHashMap }
     *     
     */
    public void setPivotWithKeys(WSLinkedHashMap value) {
        this.pivotWithKeys = value;
    }

    /**
     * Gets the value of the indexPaths property.
     * 
     * @return
     *     possible object is
     *     {@link WSStringArray }
     *     
     */
    public WSStringArray getIndexPaths() {
        return indexPaths;
    }

    /**
     * Sets the value of the indexPaths property.
     * 
     * @param value
     *     allowed object is
     *     {@link WSStringArray }
     *     
     */
    public void setIndexPaths(WSStringArray value) {
        this.indexPaths = value;
    }

    /**
     * Gets the value of the whereItem property.
     * 
     * @return
     *     possible object is
     *     {@link WSWhereItem }
     *     
     */
    public WSWhereItem getWhereItem() {
        return whereItem;
    }

    /**
     * Sets the value of the whereItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link WSWhereItem }
     *     
     */
    public void setWhereItem(WSWhereItem value) {
        this.whereItem = value;
    }

    /**
     * Gets the value of the pivotDirections property.
     * 
     * @return
     *     possible object is
     *     {@link WSStringArray }
     *     
     */
    public WSStringArray getPivotDirections() {
        return pivotDirections;
    }

    /**
     * Sets the value of the pivotDirections property.
     * 
     * @param value
     *     allowed object is
     *     {@link WSStringArray }
     *     
     */
    public void setPivotDirections(WSStringArray value) {
        this.pivotDirections = value;
    }

    /**
     * Gets the value of the indexDirections property.
     * 
     * @return
     *     possible object is
     *     {@link WSStringArray }
     *     
     */
    public WSStringArray getIndexDirections() {
        return indexDirections;
    }

    /**
     * Sets the value of the indexDirections property.
     * 
     * @param value
     *     allowed object is
     *     {@link WSStringArray }
     *     
     */
    public void setIndexDirections(WSStringArray value) {
        this.indexDirections = value;
    }

    /**
     * Gets the value of the start property.
     * 
     */
    public int getStart() {
        return start;
    }

    /**
     * Sets the value of the start property.
     * 
     */
    public void setStart(int value) {
        this.start = value;
    }

    /**
     * Gets the value of the limit property.
     * 
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Sets the value of the limit property.
     * 
     */
    public void setLimit(int value) {
        this.limit = value;
    }

}
