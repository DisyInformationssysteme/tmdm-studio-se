package com.amalto.workbench.utils;

import java.util.HashMap;
import java.util.Map;

import com.amalto.workbench.models.TreeObject;

/**
 * The XtentisObjects
 * @author aiming
 *
 */
public enum EXtentisObjects {
	DataCluster("Data Container","Data Cluster",TreeObject.DATA_CLUSTER,false),              
	DataMODEL("Data Model","Data Model",TreeObject.DATA_MODEL,true),                
	DataMODELRESOURCE("Data Model","Data Model Resource",TreeObject.DATA_MODEL_RESOURCE,true),                
	DataMODELTYPESRESOURCE("Data Model Type","Data Model Type Resource",TreeObject.DATA_MODEL_TYPES_RESOURCE,true),                
	CUSTOMTYPESRESOURCE("Custom Type","Custom Type Resource",TreeObject.CUSTOM_TYPES_RESOURCE),                
	PICTURESRESOURCE("Pictures","Pictures Resource",TreeObject.PICTURES_RESOURCE),                
	Role("Role","Role",TreeObject.ROLE,true),                      
	RoutingRule("Trigger","Routing Rule",TreeObject.ROUTING_RULE,true),                                 
	StoredProcedure("Stored Procedure","Stored Procedure",TreeObject.STORED_PROCEDURE,true),                  
	Transformer("Process","Transformer V2",TreeObject.TRANSFORMER,true),               
	TransformerPlugin("Process Plugin","Transformer Plugin V2",TreeObject.TRANSFORMER_PLUGIN),                
	View("View","View",TreeObject.VIEW,true),                      
	Menu("Menu","Menu",TreeObject.MENU,true),                      
    SubscriptionEngine("Event Manager","Subscription Engine",TreeObject.SUBSCRIPTION_ENGINE),    
	Universe("Version","Universe",TreeObject.UNIVERSE),                  
	SynchronizationPlan("Synchronization Plan","Synchronization Plan",TreeObject.SYNCHRONIZATIONPLAN,true),      
	ServiceConfiguration("Service Configuration","Service Configuration",TreeObject.SERVICE_CONFIGURATION),
	Workflow("Workflow","Workflow Processes",TreeObject.WORKFLOW),
	JobRegistry("Job Registry","Job Registry",TreeObject.JOB_REGISTRY),
	Resources("Resources","Resources",TreeObject.RESOURCES),
	CustomType("Custom Type","Custom Type",TreeObject.CUSTOM_TYPE);
	
	private String displayName;
	private String name;	
	private int type;
	/**
	 * check this object is need revision
	 */
	private boolean isRevision=false;
	
	public boolean isRevision() {
		return isRevision;
	}

	public void setRevision(boolean isRevision) {
		this.isRevision = isRevision;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	EXtentisObjects(String displayName,String name, int type){
		this.name=name;
		this.displayName=displayName;
		this.type=type;
	}

	EXtentisObjects(String displayName,String name, int type, boolean isRevision){
		this.name=name;
		this.displayName=displayName;
		this.type=type;
		this.isRevision=isRevision;
	}
	//key is the type
	public static Map<String ,EXtentisObjects> getXtentisObjexts(){
		
		Map<String,EXtentisObjects> map=new HashMap<String, EXtentisObjects>();
		for(int i=0; i<values().length; i++){
			map.put(String.valueOf(values()[i].getType()), values()[i]);
		}
		return map;
	}
}
