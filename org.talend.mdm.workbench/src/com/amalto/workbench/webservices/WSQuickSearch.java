
package com.amalto.workbench.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Quick searches on the searchable elements of the view
 * 			
 * 
 * <p>Java class for WSQuickSearch complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WSQuickSearch">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="wsDataClusterPK" type="{urn-com-amalto-xtentis-webservice}WSDataClusterPK"/>
 *         &lt;element name="wsViewPK" type="{urn-com-amalto-xtentis-webservice}WSViewPK"/>
 *         &lt;element name="searchedValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="maxItems" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="skip" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="spellTreshold" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="matchAllWords" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="orderBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="direction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WSQuickSearch", propOrder = {
    "wsDataClusterPK",
    "wsViewPK",
    "searchedValue",
    "maxItems",
    "skip",
    "spellTreshold",
    "matchAllWords",
    "orderBy",
    "direction"
})
public class WSQuickSearch {

    @XmlElement(required = true)
    protected WSDataClusterPK wsDataClusterPK;
    @XmlElement(required = true)
    protected WSViewPK wsViewPK;
    @XmlElement(required = true)
    protected String searchedValue;
    protected int maxItems;
    protected int skip;
    protected int spellTreshold;
    protected boolean matchAllWords;
    @XmlElement(nillable = true)
    protected String orderBy;
    @XmlElement(nillable = true)
    protected String direction;

    /**
     * Default no-arg constructor
     * 
     */
    public WSQuickSearch() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public WSQuickSearch(final WSDataClusterPK wsDataClusterPK, final WSViewPK wsViewPK, final String searchedValue, final int maxItems, final int skip, final int spellTreshold, final boolean matchAllWords, final String orderBy, final String direction) {
        this.wsDataClusterPK = wsDataClusterPK;
        this.wsViewPK = wsViewPK;
        this.searchedValue = searchedValue;
        this.maxItems = maxItems;
        this.skip = skip;
        this.spellTreshold = spellTreshold;
        this.matchAllWords = matchAllWords;
        this.orderBy = orderBy;
        this.direction = direction;
    }

    /**
     * Gets the value of the wsDataClusterPK property.
     * 
     * @return
     *     possible object is
     *     {@link WSDataClusterPK }
     *     
     */
    public WSDataClusterPK getWsDataClusterPK() {
        return wsDataClusterPK;
    }

    /**
     * Sets the value of the wsDataClusterPK property.
     * 
     * @param value
     *     allowed object is
     *     {@link WSDataClusterPK }
     *     
     */
    public void setWsDataClusterPK(WSDataClusterPK value) {
        this.wsDataClusterPK = value;
    }

    /**
     * Gets the value of the wsViewPK property.
     * 
     * @return
     *     possible object is
     *     {@link WSViewPK }
     *     
     */
    public WSViewPK getWsViewPK() {
        return wsViewPK;
    }

    /**
     * Sets the value of the wsViewPK property.
     * 
     * @param value
     *     allowed object is
     *     {@link WSViewPK }
     *     
     */
    public void setWsViewPK(WSViewPK value) {
        this.wsViewPK = value;
    }

    /**
     * Gets the value of the searchedValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSearchedValue() {
        return searchedValue;
    }

    /**
     * Sets the value of the searchedValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSearchedValue(String value) {
        this.searchedValue = value;
    }

    /**
     * Gets the value of the maxItems property.
     * 
     */
    public int getMaxItems() {
        return maxItems;
    }

    /**
     * Sets the value of the maxItems property.
     * 
     */
    public void setMaxItems(int value) {
        this.maxItems = value;
    }

    /**
     * Gets the value of the skip property.
     * 
     */
    public int getSkip() {
        return skip;
    }

    /**
     * Sets the value of the skip property.
     * 
     */
    public void setSkip(int value) {
        this.skip = value;
    }

    /**
     * Gets the value of the spellTreshold property.
     * 
     */
    public int getSpellTreshold() {
        return spellTreshold;
    }

    /**
     * Sets the value of the spellTreshold property.
     * 
     */
    public void setSpellTreshold(int value) {
        this.spellTreshold = value;
    }

    /**
     * Gets the value of the matchAllWords property.
     * 
     */
    public boolean isMatchAllWords() {
        return matchAllWords;
    }

    /**
     * Sets the value of the matchAllWords property.
     * 
     */
    public void setMatchAllWords(boolean value) {
        this.matchAllWords = value;
    }

    /**
     * Gets the value of the orderBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * Sets the value of the orderBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderBy(String value) {
        this.orderBy = value;
    }

    /**
     * Gets the value of the direction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Sets the value of the direction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirection(String value) {
        this.direction = value;
    }

}
