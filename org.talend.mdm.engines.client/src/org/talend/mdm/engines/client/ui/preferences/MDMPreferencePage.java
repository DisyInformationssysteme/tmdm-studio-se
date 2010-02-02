// ============================================================================
//
// Copyright (C) 2006-2009 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.mdm.engines.client.ui.preferences;

import org.apache.log4j.Logger;
import org.eclipse.gmf.runtime.common.ui.preferences.CheckBoxFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.model.properties.SpagoBiServer;
import org.talend.mdm.engines.client.Activator;
import org.talend.mdm.engines.client.i18n.Messages;

/**
 * Preference for the SpagoBiServer values.
 * 
 * $Id: SpagoBiPreferencePage.java 2738 2007-04-26 13:12:27Z cantoine $
 * 
 */
public class MDMPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

    protected static Logger log = Logger.getLogger(MDMPreferencePage.class);

    protected CheckBoxFieldEditor spagoBiCheckButton;

    protected MDMServerEditor editor;

    private Composite parent;

    /**
     * SpagoBiPreferencePage.
     * 
     * $Id: SpagoBiPreferencePage.java 2738 2007-04-26 13:12:27Z cantoine $
     * 
     */

    public MDMPreferencePage() {
        super(FLAT);
        setPreferenceStore(Activator.getDefault().getPreferenceStore());
    }

    /*
     * @Override protected IPreferenceStore doGetPreferenceStore() { // SpagoPreferenceStore preferenceStore = new
     * SpagoPreferenceStore(ProxyRepositoryFactory.getInstance()); // try { // preferenceStore.load(); // } catch
     * (PersistenceException e) { // String detailError = e.getMessage(); // new ErrorDialogWidthDetailArea(new Shell(),
     * RepositoryPlugin.PLUGIN_ID, Messages // .getString("CommonWizard.persistenceException"), detailError);
     * //$NON-NLS-1$ // log.error(Messages.getString("CommonWizard.persistenceException") + "\n" + detailError);
     * //$NON-NLS-1$ // //$NON-NLS-2$ // } // return preferenceStore; return
     * Activator.getDefault().getPreferenceStore(); }
     */

    @Override
    protected void createFieldEditors() {
        parent = getFieldEditorParent();

        spagoBiCheckButton = new CheckBoxFieldEditor(MDMPreferenceInitializer.SPAGO_STATUS, Messages
                .getString("SpagoBiPreferencePage.spagoBiCheckButton"), parent); //$NON-NLS-1$
        editor = new MDMServerEditor(SpagoBiServer.SPAGOBI_SERVER, Messages
                .getString("SpagoBiPreferencePage.technicalStatusLabel"), parent); //$NON-NLS-1$
        addField(editor);

        updateEnableStateFromPreferences();

        SelectionListener listener = new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {
            }

            public void widgetSelected(SelectionEvent e) {
                IPreferenceStore preferenceStore = getPreferenceStore();
                preferenceStore.setValue(MDMPreferenceInitializer.SPAGO_STATUS, ((Button) e.getSource()).getSelection());
                updateEnableStateFromDisplay();
            }
        };
        spagoBiCheckButton.getCheckbox().addSelectionListener(listener);
    }

    private void updateEnableStateFromDisplay() {
        boolean spago = spagoBiCheckButton.getBooleanValue();
        editor.setEnabled(spago, parent);
    }

    private void updateEnableStateFromPreferences() {
        IPreferenceStore preferenceStore = getPreferenceStore();
        boolean spago = preferenceStore.getBoolean(MDMPreferenceInitializer.SPAGO_STATUS);
        spagoBiCheckButton.getCheckbox().setSelection(spago);
        editor.setEnabled(spago, parent);
    }

    @Override
    protected void initialize() {
        try {
            super.initialize();
        } catch (RuntimeException e) {
            // e.printStackTrace();
            ExceptionHandler.process(e);
            setErrorMessage(e.getMessage());
            log.error(e);
        }
    }

    @Override
    protected void checkState() {
        if (getErrorMessage() == null) {
            super.checkState();
        } else {
            setValid(false);
        }
    }

    public void init(IWorkbench workbench) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.preference.FieldEditorPreferencePage#performDefaults()
     */
    @Override
    protected void performDefaults() {
        super.performDefaults();
        updateEnableStateFromDisplay();
    }

}
