// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation ��1.1.2_01������� R40��
// Generated source version: 1.1.2

package com.amalto.workbench.webservices;


public class WSTypedContent {
    protected java.lang.String url;
    protected com.amalto.workbench.webservices.WSByteArray wsBytes;
    protected java.lang.String contentType;
    
    public WSTypedContent() {
    }
    
    public WSTypedContent(java.lang.String url, com.amalto.workbench.webservices.WSByteArray wsBytes, java.lang.String contentType) {
        this.url = url;
        this.wsBytes = wsBytes;
        this.contentType = contentType;
    }
    
    public java.lang.String getUrl() {
        return url;
    }
    
    public void setUrl(java.lang.String url) {
        this.url = url;
    }
    
    public com.amalto.workbench.webservices.WSByteArray getWsBytes() {
        return wsBytes;
    }
    
    public void setWsBytes(com.amalto.workbench.webservices.WSByteArray wsBytes) {
        this.wsBytes = wsBytes;
    }
    
    public java.lang.String getContentType() {
        return contentType;
    }
    
    public void setContentType(java.lang.String contentType) {
        this.contentType = contentType;
    }
}
