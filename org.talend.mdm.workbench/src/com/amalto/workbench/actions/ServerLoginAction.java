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
package com.amalto.workbench.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Event;

import com.amalto.workbench.dialogs.LoginDialog;
import com.amalto.workbench.image.ImageCache;
import com.amalto.workbench.utils.IConstants;
import com.amalto.workbench.utils.Util;
import com.amalto.workbench.views.ServerView;
import com.amalto.workbench.webservices.WSGetUniversePKs;
import com.amalto.workbench.webservices.WSUniversePK;
import com.amalto.workbench.webservices.XtentisPort;

public class ServerLoginAction extends Action implements SelectionListener {

    private static Log log = LogFactory.getLog(ServerLoginAction.class);

    private LoginDialog dialog = null;

    private ServerView view = null;

    private List<WSUniversePK> universes = new ArrayList<WSUniversePK>();;

    public ServerLoginAction(ServerView view) {
        super();
        this.view = view;
        setImageDescriptor(ImageCache.getImage("icons/startserveraction.gif"));
        setText("Login");
        // setToolTipText("Login to an "+IConstants.TALEND+" Server");
        setToolTipText("Add MDM Server Location");

    }

    public void run() {
        try {
            super.run();
            if (Util.IsEnterPrise()) {
                universes = new ArrayList<WSUniversePK>();
                WSUniversePK[] universePKs = null;
                List<XtentisPort> ports = view.getPorts();
                if (ports != null) {
                    for (Iterator iterator = ports.iterator(); iterator.hasNext();) {
                        XtentisPort port = (XtentisPort) iterator.next();
                        universePKs = port.getUniversePKs(new WSGetUniversePKs("*")).getWsUniversePK();

                        if (universePKs != null && universePKs.length > 0)
                            CollectionUtils.addAll(universes, universePKs);
                    }
                }
            }
            dialog = new LoginDialog(this, view.getSite().getShell(), IConstants.TALEND + " Login", universes);
            dialog.setBlockOnOpen(true);
            dialog.open();
        } catch (Exception e) {
            // e.printStackTrace();
            log.error(e.getStackTrace());
        }
    }

    public void runWithEvent(Event event) {
        super.runWithEvent(event);
    }

    /***************************************
     * Selection Listener on OK Button
     * 
     ***************************************/
    public void widgetSelected(SelectionEvent e) {
        int buttonId = ((Integer) e.widget.getData()).intValue();
        if (IDialogConstants.OK_ID != buttonId)
            return;
        String url = dialog.getServer();
        String username = dialog.getUsernameText();
        String password = dialog.getPasswordText();
        String universe = dialog.getUniverse();
        dialog.close();

        String cmp = Util.checkOnVersionCompatibility(url, username, password, universe);
        if (cmp != null) {
            MessageDialog.openError(null, "Error", "The version of mdm studio is not compatible with that of server : \n" + cmp);
            return;
        }

        view.initServerTree(url, username, password, universe);

    }

    public void widgetDefaultSelected(SelectionEvent e) {
    }

}
