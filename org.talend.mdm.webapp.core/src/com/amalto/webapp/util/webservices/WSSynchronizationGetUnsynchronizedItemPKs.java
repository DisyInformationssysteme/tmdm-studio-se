// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation ��1.1.2_01������� R40��
// Generated source version: 1.1.2

package com.amalto.webapp.util.webservices;


public class WSSynchronizationGetUnsynchronizedItemPKs {
    protected java.lang.String revisionID;
    protected com.amalto.webapp.util.webservices.WSDataClusterPK wsDataClusterPOJOPK;
    protected java.lang.String conceptName;
    protected java.lang.String instancePattern;
    protected java.lang.String synchronizationPlanName;
    protected int start;
    protected int limit;
    
    public WSSynchronizationGetUnsynchronizedItemPKs() {
    }
    
    public WSSynchronizationGetUnsynchronizedItemPKs(java.lang.String revisionID, com.amalto.webapp.util.webservices.WSDataClusterPK wsDataClusterPOJOPK, java.lang.String conceptName, java.lang.String instancePattern, java.lang.String synchronizationPlanName, int start, int limit) {
        this.revisionID = revisionID;
        this.wsDataClusterPOJOPK = wsDataClusterPOJOPK;
        this.conceptName = conceptName;
        this.instancePattern = instancePattern;
        this.synchronizationPlanName = synchronizationPlanName;
        this.start = start;
        this.limit = limit;
    }
    
    public java.lang.String getRevisionID() {
        return revisionID;
    }
    
    public void setRevisionID(java.lang.String revisionID) {
        this.revisionID = revisionID;
    }
    
    public com.amalto.webapp.util.webservices.WSDataClusterPK getWsDataClusterPOJOPK() {
        return wsDataClusterPOJOPK;
    }
    
    public void setWsDataClusterPOJOPK(com.amalto.webapp.util.webservices.WSDataClusterPK wsDataClusterPOJOPK) {
        this.wsDataClusterPOJOPK = wsDataClusterPOJOPK;
    }
    
    public java.lang.String getConceptName() {
        return conceptName;
    }
    
    public void setConceptName(java.lang.String conceptName) {
        this.conceptName = conceptName;
    }
    
    public java.lang.String getInstancePattern() {
        return instancePattern;
    }
    
    public void setInstancePattern(java.lang.String instancePattern) {
        this.instancePattern = instancePattern;
    }
    
    public java.lang.String getSynchronizationPlanName() {
        return synchronizationPlanName;
    }
    
    public void setSynchronizationPlanName(java.lang.String synchronizationPlanName) {
        this.synchronizationPlanName = synchronizationPlanName;
    }
    
    public int getStart() {
        return start;
    }
    
    public void setStart(int start) {
        this.start = start;
    }
    
    public int getLimit() {
        return limit;
    }
    
    public void setLimit(int limit) {
        this.limit = limit;
    }
}
