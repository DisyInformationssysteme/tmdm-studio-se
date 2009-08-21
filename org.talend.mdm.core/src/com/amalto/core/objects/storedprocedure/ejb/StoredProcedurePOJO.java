package com.amalto.core.objects.storedprocedure.ejb;

import java.util.Collection;

import javax.ejb.EJBException;
import javax.naming.InitialContext;

import com.amalto.core.ejb.ObjectPOJO;
import com.amalto.core.ejb.ObjectPOJOPK;
import com.amalto.core.ejb.local.XmlServerSLWrapperLocal;
import com.amalto.core.ejb.local.XmlServerSLWrapperLocalHome;
import com.amalto.core.objects.datacluster.ejb.DataClusterPOJOPK;
import com.amalto.core.util.XtentisException;


/**
 * @author Bruno Grieder
 * 
 */
public class StoredProcedurePOJO extends ObjectPOJO{
   
    private String name;
    private String description;
    private String procedure;
        
    /**
     * 
     */
    public StoredProcedurePOJO() {
        super();
    }
    
	public StoredProcedurePOJO(String name, String query) {
		super();
		this.name = name;
		this.procedure = query;
	}
	

	/**
	 * @return Returns the Name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	/**
	 * @return Returns the Description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	/**
	 * @return Returns the Procedure.
	 */
	public String getProcedure() {
		return procedure;
	}

	/**
	 * @param procedure The procedure to set.
	 */
	public void setProcedure(String procedure) {
		this.procedure = procedure;
	}

        
    public Collection<String> execute(String revisionID, DataClusterPOJOPK dataClusterPOJOPK, String[] parameters) throws XtentisException {
        
    	if (getProcedure()==null) return null;
    	
        try {
	    	
            //get the xml server wrapper
            XmlServerSLWrapperLocal server = null;
			try {
				server  =  ((XmlServerSLWrapperLocalHome)new InitialContext().lookup(XmlServerSLWrapperLocalHome.JNDI_NAME)).create();
			} catch (Exception e) {
				String err = "Error Executing Stored Procedure '"+getName()+"': unable to access the XML Server wrapper";
				org.apache.log4j.Logger.getLogger(this.getClass()).error(err,e);
				throw new XtentisException(err);
			}
        	
        	//execute
        	return server.runQuery(revisionID, dataClusterPOJOPK.getUniqueId(), getProcedure(), parameters);
 
	    } catch (Exception e) {
    	    String err = "Unable to execute the Stored Procedure "+getPK().getUniqueId()
    	    		+": "+e.getClass().getName()+": "+e.getLocalizedMessage();
    	    org.apache.log4j.Logger.getLogger(this.getClass()).error(err);
    	    throw new EJBException(err);
	    } 

    }
   
    
    
	@Override
	public ObjectPOJOPK getPK() {
		if (getName()==null) return null;
		return new ObjectPOJOPK(new String[] {name});
	}

}
