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
package com.amalto.workbench.export;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.amalto.workbench.models.TreeObject;
import com.amalto.workbench.utils.IConstants;
import com.amalto.workbench.utils.Util;
import com.amalto.workbench.widgets.FileSelectWidget;
import com.amalto.workbench.widgets.LabelCombo;
import com.amalto.workbench.widgets.WidgetFactory;

/**
 * @deprecated
 * @author achen
 *
 */
public class ExportItemsDialog extends Dialog{
	FormToolkit toolkit=WidgetFactory.getWidgetFactory();
	private LabelCombo comboDataCluster;
	TreeObject xObject;
	private Map<String , String> xpathMap = new HashMap<String, String>();
	private FileSelectWidget fw;
	private String server;
	String dataCluster;
	String filename;
	public ExportItemsDialog(Shell parentShell,TreeObject xObject) {
		super(parentShell);
		this.xObject=xObject;
	}
	@Override
	protected Control createDialogArea(Composite parent) {
		parent.getShell().setText("Data Container Export");
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.setBackground(composite.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		GridLayout layout = (GridLayout)composite.getLayout();
		layout.numColumns = 1;
        
		Matcher matcher;
		final String dataCluster = "Data Container";
		final String all = "ALL";
		String xobjectName = "";
		xobjectName = xObject.getDisplayName();
		if (xobjectName.equals(dataCluster))
			xobjectName = all;

		comboDataCluster=new LabelCombo(toolkit,composite,dataCluster,SWT.BORDER,2);
		comboDataCluster.getCombo().setEditable(false);
		//List<String> dcs=Util.getCachedXObjectsNameSet(this.xObject, TreeObject.DATA_CLUSTER);
		List<String> dcs=Util.getChildren(this.xObject.getServerRoot(), TreeObject.DATA_CLUSTER);
		dcs.add(0,all);
		for (String cluster : dcs) {
			xpathMap.put(cluster, cluster);
		}
        for (TreeObject treeObj: xObject.getServerRoot().getChildren())
        {
        	if (treeObj.getDisplayName().startsWith(dataCluster))
        		continue;
        	String revision, xobject = "";
        	String xpath = "";

            matcher = filter(treeObj.getDisplayName());
        	if (matcher.matches()) {
				xobject = matcher.group(1).replace(" ", "");
				revision = matcher.group(3);
				if(xobject.equalsIgnoreCase("Process")){
					xobject="TransformerV2";
				}
				if (revision.equals(IConstants.HEAD)) {
					xpath = "amaltoOBJECTS" + xobject;
				} else {
					xpath = "R-" + revision + "/" + "amaltoOBJECTS" + xobject;
				}
			} else if (treeObj.getDisplayName().equals("Version")) {
				xobject = treeObj.getDisplayName();
				xpath = "amaltoOBJECTS" + treeObj.getDisplayName();
			}
        	
        	if (!xpath.equals("")) {
        		xpathMap.put(treeObj.getDisplayName(), xpath);
				dcs.add(treeObj.getDisplayName());
			}
        }
		comboDataCluster.getCombo().setItems(dcs.toArray(new String[dcs.size()]));
		int sel = Arrays.asList(comboDataCluster.getCombo().getItems()).indexOf(xobjectName);
		comboDataCluster.getCombo().select(sel);
		
		//file
		 
		fw=new FileSelectWidget(composite,"Target      ",new String[]{"*.*","*.zip"}, comboDataCluster.getCombo().getText(),false);
		comboDataCluster.getCombo().addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {				
				
			}

			public void widgetSelected(SelectionEvent e) {
				if(comboDataCluster.getCombo().getText().length()>0){
					fw.getText().setText(comboDataCluster.getCombo().getText());
					fw.setFilename(comboDataCluster.getCombo().getText());
				}
			}			
		});
		return composite;
	}
	
	private Matcher filter(String name) {
		Pattern bracket = Pattern.compile("(.*?)(\\s*)\\[(\\w+)\\]");
		Matcher matcher = bracket.matcher(name);
		return matcher;
	}
	
	@Override
	protected void okPressed() {
		///db/CONF -d c:/CONF.zip";
		if(comboDataCluster.getCombo().getText().trim().length()==0){
			MessageDialog.openError(null, "Error", "Data Container should not be null!");
			comboDataCluster.getCombo().setFocus();
			return;
		}
		if(fw.getText().getText().trim().length()==0 ){
			MessageDialog.openError(null, "Error", "Target should not be null!");
			fw.getText().setFocus();
			return;
		}
		String url=xObject.getServerRoot().getEndpointAddress();
		server=null;
		try {
			 server= new URL(url).getHost();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		dataCluster=comboDataCluster.getCombo().getText();
		dataCluster = xpathMap.get(dataCluster);
		filename=fw.getText().getText().trim();
		Job job=new Job("Export Data Containers : " + dataCluster+" ..."){
			@Override
			public IStatus run(IProgressMonitor monitor) {	
				try{					
					monitor.beginTask("Export Data Containers : " + dataCluster+" ...", IProgressMonitor.UNKNOWN);
					//Util.exportDataCluster(xObject, dataCluster, filename,server, monitor);
					monitor.done();
					return Status.OK_STATUS;
				}catch(Exception e){
					e.printStackTrace();
					return Status.CANCEL_STATUS;
				}
			}			
		};
		job.setPriority(Job.INTERACTIVE);
		job.schedule();
		
		super.okPressed();
	}
	
}
