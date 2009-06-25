/*
 * Generated by XDoclet - Do not edit!
 */
package com.amalto.core.objects.universe.ejb.local;

/**
 * Local interface for UniverseCtrl.
 * @xdoclet-generated at 25-06-09
 * @copyright The XDoclet Team
 * @author XDoclet
 * @version ${version}
 */
public interface UniverseCtrlLocal
   extends javax.ejb.EJBLocalObject
{
   /**
    * Creates or updates a Universe
    * @throws XtentisException
    */
   public com.amalto.core.objects.universe.ejb.UniversePOJOPK putUniverse( com.amalto.core.objects.universe.ejb.UniversePOJO universe ) throws com.amalto.core.util.XtentisException;

   /**
    * Get Universe
    * @throws XtentisException
    */
   public com.amalto.core.objects.universe.ejb.UniversePOJO getUniverse( com.amalto.core.objects.universe.ejb.UniversePOJOPK pk ) throws com.amalto.core.util.XtentisException;

   /**
    * Get a Universe - no exception is thrown: returns null if not found
    * @throws XtentisException
    */
   public com.amalto.core.objects.universe.ejb.UniversePOJO existsUniverse( com.amalto.core.objects.universe.ejb.UniversePOJOPK pk ) throws com.amalto.core.util.XtentisException;

   /**
    * Remove an item
    * @throws XtentisException
    */
   public com.amalto.core.objects.universe.ejb.UniversePOJOPK removeUniverse( com.amalto.core.objects.universe.ejb.UniversePOJOPK pk ) throws com.amalto.core.util.XtentisException;

   /**
    * Retrieve all Universe PKS
    * @throws XtentisException
    */
   public java.util.Collection getUniversePKs( java.lang.String regex ) throws com.amalto.core.util.XtentisException;

}
