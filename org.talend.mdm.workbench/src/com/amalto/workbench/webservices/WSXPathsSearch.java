// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation ��1.1.2_01������� R40��
// Generated source version: 1.1.2

package com.amalto.workbench.webservices;


public class WSXPathsSearch {
    protected com.amalto.workbench.webservices.WSDataClusterPK wsDataClusterPK;
    protected java.lang.String pivotPath;
    protected com.amalto.workbench.webservices.WSStringArray viewablePaths;
    protected com.amalto.workbench.webservices.WSWhereItem whereItem;
    protected int spellTreshold;
    protected int skip;
    protected int maxItems;
    protected java.lang.String orderBy;
    protected java.lang.String direction;
    
    public WSXPathsSearch() {
    }
    
    public WSXPathsSearch(com.amalto.workbench.webservices.WSDataClusterPK wsDataClusterPK, java.lang.String pivotPath, com.amalto.workbench.webservices.WSStringArray viewablePaths, com.amalto.workbench.webservices.WSWhereItem whereItem, int spellTreshold, int skip, int maxItems, java.lang.String orderBy, java.lang.String direction) {
        this.wsDataClusterPK = wsDataClusterPK;
        this.pivotPath = pivotPath;
        this.viewablePaths = viewablePaths;
        this.whereItem = whereItem;
        this.spellTreshold = spellTreshold;
        this.skip = skip;
        this.maxItems = maxItems;
        this.orderBy = orderBy;
        this.direction = direction;
    }
    
    public com.amalto.workbench.webservices.WSDataClusterPK getWsDataClusterPK() {
        return wsDataClusterPK;
    }
    
    public void setWsDataClusterPK(com.amalto.workbench.webservices.WSDataClusterPK wsDataClusterPK) {
        this.wsDataClusterPK = wsDataClusterPK;
    }
    
    public java.lang.String getPivotPath() {
        return pivotPath;
    }
    
    public void setPivotPath(java.lang.String pivotPath) {
        this.pivotPath = pivotPath;
    }
    
    public com.amalto.workbench.webservices.WSStringArray getViewablePaths() {
        return viewablePaths;
    }
    
    public void setViewablePaths(com.amalto.workbench.webservices.WSStringArray viewablePaths) {
        this.viewablePaths = viewablePaths;
    }
    
    public com.amalto.workbench.webservices.WSWhereItem getWhereItem() {
        return whereItem;
    }
    
    public void setWhereItem(com.amalto.workbench.webservices.WSWhereItem whereItem) {
        this.whereItem = whereItem;
    }
    
    public int getSpellTreshold() {
        return spellTreshold;
    }
    
    public void setSpellTreshold(int spellTreshold) {
        this.spellTreshold = spellTreshold;
    }
    
    public int getSkip() {
        return skip;
    }
    
    public void setSkip(int skip) {
        this.skip = skip;
    }
    
    public int getMaxItems() {
        return maxItems;
    }
    
    public void setMaxItems(int maxItems) {
        this.maxItems = maxItems;
    }
    
    public java.lang.String getOrderBy() {
        return orderBy;
    }
    
    public void setOrderBy(java.lang.String orderBy) {
        this.orderBy = orderBy;
    }
    
    public java.lang.String getDirection() {
        return direction;
    }
    
    public void setDirection(java.lang.String direction) {
        this.direction = direction;
    }
}
