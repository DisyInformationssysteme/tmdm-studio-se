// ============================================================================
//
// Copyright (C) 2006-2011 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.mdm.webapp.synchronization2.server.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.amalto.webapp.util.webservices.WSDataClusterPK;
import com.amalto.webapp.util.webservices.WSItemPK;
import com.amalto.webapp.util.webservices.WSSynchronizationItem;
import com.amalto.webapp.util.webservices.WSSynchronizationItemRemoteInstances;
import com.amalto.webapp.util.webservices.WSSynchronizationItemStatus;

public class SynchronizationItem {

    private static final Logger LOGGER = Logger.getLogger(SynchronizationItem.class);

    /** The synchronization was started but is not resolved yet */
    public final static String STATUS_PENDING = "PENDDING"; //$NON-NLS-1$

    /** The synchronization failed - manual resolution required (for items only) */
    public final static String STATUS_MANUAL = "MANUAL"; //$NON-NLS-1$

    /** The synchronization was resolved but not yet executed */
    public final static String STATUS_RESOLVED = "RESOLVED"; //$NON-NLS-1$

    /** The synchronization was executed */
    public final static String STATUS_EXECUTED = "EXECUTED"; //$NON-NLS-1$

    private ItemPK itemPOJOPK = null;

    private String localRevisionID = null;

    private String lastRunPlan = null;

    private String status;

    private String resolvedProjection = null;

    private HashMap<String, SynchronizationItemRemoteInstance> remoteIntances = new HashMap<String, SynchronizationItemRemoteInstance>();

    private TreeNode node;

    public TreeNode[] getRemoteNodes() {
        List<TreeNode> nodes = new ArrayList<TreeNode>();
        for (SynchronizationItemRemoteInstance instance : remoteIntances.values()) {
            TreeNode n = new TreeNode();
            try {
                n = n.deserialize(instance.getXml());
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
            nodes.add(n);
        }
        return nodes.toArray(new TreeNode[nodes.size()]);
    }

    public TreeNode getNode() {
        return this.node;
    }

    public void setNode(TreeNode node) {
        this.node = node;
    }

    public String getItemPK() {
        return itemPOJOPK.getUniqueID();
    }

    public String[] getRemoteItemNames() {
        return remoteIntances.keySet().toArray(new String[remoteIntances.keySet().size()]);
    }

    /** For marshaling */
    public SynchronizationItem() {
    }

    public SynchronizationItem(ItemPK itemPOJOPK, String localRevisionID, String lastRunPlan, String status,
            String resolvedProjection, HashMap<String, SynchronizationItemRemoteInstance> remoteIntances) {
        super();
        this.itemPOJOPK = itemPOJOPK;
        this.localRevisionID = localRevisionID == null ? "" : localRevisionID; //$NON-NLS-1$
        this.lastRunPlan = lastRunPlan;
        this.status = status;
        this.resolvedProjection = resolvedProjection;
        this.remoteIntances = remoteIntances;
    }

    public String getLocalRevisionID() {
        return localRevisionID == null ? "" : localRevisionID; //$NON-NLS-1$
    }

    public void setLocalRevisionID(String localRevisionID) {
        this.localRevisionID = (localRevisionID == null ? "" : localRevisionID); //$NON-NLS-1$
    }

    public ItemPK getItemPOJOPK() {
        return itemPOJOPK;
    }

    public void setItemPOJOPK(ItemPK itemPOJOPK) {
        this.itemPOJOPK = itemPOJOPK;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HashMap<String, SynchronizationItemRemoteInstance> getRemoteIntances() {
        return remoteIntances;
    }

    public void setRemoteIntances(HashMap<String, SynchronizationItemRemoteInstance> remoteIntances) {
        this.remoteIntances = remoteIntances;
    }

    public String getLastRunPlan() {
        return lastRunPlan;
    }

    public void setLastRunPlan(String lastRunPlan) {
        this.lastRunPlan = lastRunPlan;
    }

    public String getResolvedProjection() {
        return resolvedProjection;
    }

    public void setResolvedProjection(String resolvedProjection) {
        this.resolvedProjection = resolvedProjection;
    }

    public WSSynchronizationItem POJO2WS(SynchronizationItem synchronizationItemPOJO) throws Exception {
        WSSynchronizationItem ws = new WSSynchronizationItem();
        ws.setLastRunPlan(synchronizationItemPOJO.getLastRunPlan());
        ws.setLocalRevisionID(synchronizationItemPOJO.getLocalRevisionID());
        ws.setResolvedProjection(synchronizationItemPOJO.getResolvedProjection());
        ws.setWsItemPK(POJO2WS(synchronizationItemPOJO.getItemPOJOPK()));

        if (synchronizationItemPOJO.getStatus().equals(SynchronizationItem.STATUS_EXECUTED)) {
            ws.setStatus(WSSynchronizationItemStatus.EXECUTED);
        }

        if (synchronizationItemPOJO.getStatus().equals(SynchronizationItem.STATUS_MANUAL)) {
            ws.setStatus(WSSynchronizationItemStatus.MANUAL);
        }

        if (synchronizationItemPOJO.getStatus().equals(SynchronizationItem.STATUS_PENDING)) {
            ws.setStatus(WSSynchronizationItemStatus.PENDING);
        }

        if (synchronizationItemPOJO.getStatus().equals(SynchronizationItem.STATUS_RESOLVED)) {
            ws.setStatus(WSSynchronizationItemStatus.RESOLVED);
        }

        // remote instances
        ArrayList<WSSynchronizationItemRemoteInstances> wsInstances = new ArrayList<WSSynchronizationItemRemoteInstances>();
        for (SynchronizationItemRemoteInstance instance : synchronizationItemPOJO.getRemoteIntances().values()) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(instance.getLastLocalSynchronizationTime());
            WSSynchronizationItemRemoteInstances wsInstance = new WSSynchronizationItemRemoteInstances(
                    instance.getRemoteSystemName(), instance.getRevisionID(), instance.getXml(), cal);
            wsInstances.add(wsInstance);
        }
        ws.setRemoteInstances(wsInstances.toArray(new WSSynchronizationItemRemoteInstances[wsInstances.size()]));

        return ws;
    }

    public SynchronizationItem WS2POJO(WSSynchronizationItem wsSynchronizationItem) throws Exception {
        SynchronizationItem pojo = new SynchronizationItem();
        pojo.setItemPOJOPK(WS2POJO(wsSynchronizationItem.getWsItemPK()));
        pojo.setLastRunPlan(wsSynchronizationItem.getLastRunPlan());
        pojo.setLocalRevisionID(wsSynchronizationItem.getLocalRevisionID());
        pojo.setResolvedProjection(wsSynchronizationItem.getResolvedProjection());

        if (WSSynchronizationItemStatus.EXECUTED.equals(wsSynchronizationItem.getStatus())) {
            pojo.setStatus(SynchronizationItem.STATUS_EXECUTED);
        } else if (WSSynchronizationItemStatus.MANUAL.equals(wsSynchronizationItem.getStatus())) {
            pojo.setStatus(SynchronizationItem.STATUS_MANUAL);
        } else if (WSSynchronizationItemStatus.PENDING.equals(wsSynchronizationItem.getStatus())) {
            pojo.setStatus(SynchronizationItem.STATUS_PENDING);
        } else if (WSSynchronizationItemStatus.RESOLVED.equals(wsSynchronizationItem.getStatus())) {
            pojo.setStatus(SynchronizationItem.STATUS_RESOLVED);
        }

        HashMap<String, SynchronizationItemRemoteInstance> instances = new HashMap<String, SynchronizationItemRemoteInstance>();
        WSSynchronizationItemRemoteInstances[] wsInstances = wsSynchronizationItem.getRemoteInstances();
        if (wsInstances != null) {
            for (int i = 0; i < wsInstances.length; i++) {
                SynchronizationItemRemoteInstance instance = new SynchronizationItemRemoteInstance(
                        wsInstances[i].getRemoteSystemName(), wsInstances[i].getRemoteRevisionID(), wsInstances[i].getXml(),
                        wsInstances[i].getLastLocalSynchronizationTime().getTimeInMillis());
                instances.put(instance.getKey(), instance);
            }
        }
        pojo.setRemoteIntances(instances);
        return pojo;
    }

    private WSItemPK POJO2WS(ItemPK itemPK) throws Exception {
        return new WSItemPK(new WSDataClusterPK(itemPK.getDataClusterPOJOPK()), itemPK.getConceptName(), itemPK.getIds());
    }

    private ItemPK WS2POJO(WSItemPK wsItemPK) throws Exception {
        return new ItemPK(wsItemPK.getWsDataClusterPK().getPk(), wsItemPK.getConceptName(), wsItemPK.getIds());
    }

}
