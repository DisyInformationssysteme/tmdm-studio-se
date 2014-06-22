
package com.amalto.workbench.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Background Jobs are created and updated by asynchronous methods ending up with ...AsJob
 * 				Use getBackgroundJob to read the progress of the job and read the status.
 * 			
 * 
 * <p>Java class for WSBackgroundJob complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WSBackgroundJob">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="status" type="{urn-com-amalto-xtentis-webservice}BackgroundJobStatusType"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="percentage" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pipeline" type="{urn-com-amalto-xtentis-webservice}WSPipeline" minOccurs="0"/>
 *         &lt;element name="serializedObject" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WSBackgroundJob", propOrder = {
    "id",
    "description",
    "status",
    "message",
    "percentage",
    "timestamp",
    "pipeline",
    "serializedObject"
})
public class WSBackgroundJob {

    @XmlElement(required = true)
    protected String id;
    @XmlElement(required = true, nillable = true)
    protected String description;
    @XmlElement(required = true)
    protected BackgroundJobStatusType status;
    @XmlElement(required = true, nillable = true)
    protected String message;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer percentage;
    @XmlElement(required = true, nillable = true)
    protected String timestamp;
    @XmlElement(nillable = true)
    protected WSPipeline pipeline;
    @XmlElement(nillable = true)
    protected byte[] serializedObject;

    /**
     * Default no-arg constructor
     * 
     */
    public WSBackgroundJob() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public WSBackgroundJob(final String id, final String description, final BackgroundJobStatusType status, final String message, final Integer percentage, final String timestamp, final WSPipeline pipeline, final byte[] serializedObject) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.message = message;
        this.percentage = percentage;
        this.timestamp = timestamp;
        this.pipeline = pipeline;
        this.serializedObject = serializedObject;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link BackgroundJobStatusType }
     *     
     */
    public BackgroundJobStatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link BackgroundJobStatusType }
     *     
     */
    public void setStatus(BackgroundJobStatusType value) {
        this.status = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the percentage property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPercentage() {
        return percentage;
    }

    /**
     * Sets the value of the percentage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPercentage(Integer value) {
        this.percentage = value;
    }

    /**
     * Gets the value of the timestamp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimestamp(String value) {
        this.timestamp = value;
    }

    /**
     * Gets the value of the pipeline property.
     * 
     * @return
     *     possible object is
     *     {@link WSPipeline }
     *     
     */
    public WSPipeline getPipeline() {
        return pipeline;
    }

    /**
     * Sets the value of the pipeline property.
     * 
     * @param value
     *     allowed object is
     *     {@link WSPipeline }
     *     
     */
    public void setPipeline(WSPipeline value) {
        this.pipeline = value;
    }

    /**
     * Gets the value of the serializedObject property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getSerializedObject() {
        return serializedObject;
    }

    /**
     * Sets the value of the serializedObject property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setSerializedObject(byte[] value) {
        this.serializedObject = ((byte[]) value);
    }

}
