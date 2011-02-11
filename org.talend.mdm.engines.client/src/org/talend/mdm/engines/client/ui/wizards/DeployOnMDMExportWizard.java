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
package org.talend.mdm.engines.client.ui.wizards;

import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.wizards.datatransfer.WizardFileSystemResourceExportPage1;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.talend.core.CorePlugin;
import org.talend.core.context.Context;
import org.talend.core.context.RepositoryContext;
import org.talend.core.model.properties.SpagoBiServer;
import org.talend.mdm.engines.client.i18n.Messages;

/**
 * Publish SpagoBI export wizard. <br/>
 * 
 * $Id: DeployOnMDMExportWizard.java 1 2007-04-27 11:30:00 cantoine
 * 
 */
public class DeployOnMDMExportWizard extends Wizard implements IExportWizard {

    private IStructuredSelection selection;

    private WizardFileSystemResourceExportPage1 mainPage;

    private SpagoBiServer mdmServer = null;

    /**
     * Creates a wizard for exporting workspace resources to a zip file.
     */
    public DeployOnMDMExportWizard() {
        AbstractUIPlugin plugin = (AbstractUIPlugin) Platform.getPlugin(PlatformUI.PLUGIN_ID);
        IDialogSettings workbenchSettings = plugin.getDialogSettings();
        IDialogSettings section = workbenchSettings.getSection("DeployOnMDMExportWizard"); //$NON-NLS-1$
        if (section == null) {
            section = workbenchSettings.addNewSection("DeployOnMDMExportWizard"); //$NON-NLS-1$
            section.put(PerlDeployOnMDMExportWizardPage.STORE_SHELL_LAUNCHER_ID, true);
            section.put(PerlDeployOnMDMExportWizardPage.STORE_SYSTEM_ROUTINE_ID, true);
            section.put(PerlDeployOnMDMExportWizardPage.STORE_USER_ROUTINE_ID, true);
            section.put(PerlDeployOnMDMExportWizardPage.STORE_MODEL_ID, true);
            section.put(PerlDeployOnMDMExportWizardPage.STORE_JOB_ID, true);
            section.put(PerlDeployOnMDMExportWizardPage.STORE_CONTEXT_ID, true);

            section.put(JavaDeployOnMDMExportWizardPage.STORE_SHELL_LAUNCHER_ID, true);
            section.put(JavaDeployOnMDMExportWizardPage.STORE_SYSTEM_ROUTINE_ID, true);
            section.put(JavaDeployOnMDMExportWizardPage.STORE_USER_ROUTINE_ID, true);
            section.put(JavaDeployOnMDMExportWizardPage.STORE_MODEL_ID, true);
            section.put(JavaDeployOnMDMExportWizardPage.STORE_JOB_ID, true);
            section.put(JavaDeployOnMDMExportWizardPage.STORE_CONTEXT_ID, true);

        }
        setDialogSettings(section);
    }

    /*
     * (non-Javadoc) Method declared on IWizard.
     */
    public void addPages() {
        super.addPages();

        switch (((RepositoryContext) CorePlugin.getContext().getProperty(Context.REPOSITORY_CONTEXT_KEY)).getProject()
                .getLanguage()) {
        case JAVA:
            mainPage = new JavaDeployOnMDMExportWizardPage(selection, mdmServer);

            break;
        case PERL:
            mainPage = new PerlDeployOnMDMExportWizardPage(selection);
            break;
        }

        addPage(mainPage);
    }

    /*
     * (non-Javadoc) Method declared on IWorkbenchWizard.
     */
    public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
        this.selection = currentSelection;
        List selectedResources = IDE.computeSelectedResources(currentSelection);
        if (!selectedResources.isEmpty()) {
            this.selection = new StructuredSelection(selectedResources);
        }

        setWindowTitle(Messages.getString("DeployOnMDMExportWizard.publishJob")); //$NON-NLS-1$
        setDefaultPageImageDescriptor(IDEWorkbenchPlugin.getIDEImageDescriptor("wizban/exportzip_wiz.png"));//$NON-NLS-1$
        setNeedsProgressMonitor(true);
    }

    /*
     * (non-Javadoc) Method declared on IWizard.
     */
    public boolean performFinish() {
        return mainPage.finish();
    }

    public SpagoBiServer getMdmServer() {
        return this.mdmServer;
    }

    public void setMdmServer(SpagoBiServer mdmServer) {
        this.mdmServer = mdmServer;
    }
}
