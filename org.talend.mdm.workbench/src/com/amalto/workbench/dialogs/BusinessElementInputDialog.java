// ============================================================================
//
// Copyright (C) 2006-2012 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package com.amalto.workbench.dialogs;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.amalto.workbench.utils.Util;

public class BusinessElementInputDialog extends Dialog {

    private Text elementNameText = null;

    private Combo refCombo = null;

    private Text minOccursText = null;

    private Text maxOccursText = null;

    private Collection<String> elementDeclarations = null;

    protected Button checkBox;

    private String elementName = "";//$NON-NLS-1$

    private String refName = "";//$NON-NLS-1$

    private int minOccurs = 0;

    private int maxOccurs = 1;

    private boolean isNew = false;

    private SelectionListener caller = null;

    private String title = "";//$NON-NLS-1$

    // fix 0010248
    private boolean inherit = true;

    public boolean isInherit() {
        return inherit;
    }

    public void setInherit(boolean inherit) {
        this.inherit = inherit;
    }

    /**
     * @param parentShell
     */
    public BusinessElementInputDialog(SelectionListener caller, Shell parentShell, String title, boolean isNew) {
        this(caller, parentShell, title, "", "", new ArrayList<String>(), 0, 1, isNew);//$NON-NLS-1$//$NON-NLS-2$
    }

    /**
     * @param parentShell
     */
    public BusinessElementInputDialog(SelectionListener caller, Shell parentShell, String title, String elementName,
            String refName, Collection<String> decls, int minOccurs, int maxOccurs, boolean isNew) {
        super(parentShell);
        this.caller = caller;
        this.title = title;
        this.elementName = elementName;
        this.refName = refName;
        this.elementDeclarations = decls;
        this.minOccurs = minOccurs;
        this.maxOccurs = maxOccurs;
        this.isNew = isNew;
    }

    protected Control createDialogArea(Composite parent) {
        // Should not really be here but well,....
        parent.getShell().setText(this.title);

        Composite composite = (Composite) super.createDialogArea(parent);

        GridLayout layout = (GridLayout) composite.getLayout();
        layout.numColumns = 2;
        // layout.verticalSpacing = 10;

        Label nameLabel = new Label(composite, SWT.NONE);
        nameLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
        nameLabel.setText("Business Element Name");

        elementNameText = new Text(composite, SWT.BORDER);
        elementNameText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        elementNameText.setText(getElementName() == null ? "" : getElementName());//$NON-NLS-1$
        ((GridData) elementNameText.getLayoutData()).widthHint = 200;
        // elementNameText.setSize(100, 22);

        Label refLabel = new Label(composite, SWT.NONE);
        refLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
        refLabel.setText("Reference Name");

        refCombo = new Combo(composite, SWT.DROP_DOWN | SWT.SIMPLE | SWT.READ_ONLY);
        refCombo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        refCombo.setItems(elementDeclarations.toArray(new String[elementDeclarations.size()]));
        refCombo.setText(getRefName() == null ? "" : getRefName());//$NON-NLS-1$
        ((GridData) refCombo.getLayoutData()).widthHint = 200;

        if (refCombo.getText().length() > 0) {
            elementNameText.setText("");//$NON-NLS-1$
            elementNameText.setEditable(false);
        } else {
            elementNameText.setEditable(true);
            elementNameText.setText(getElementName() == null ? "" : getElementName());//$NON-NLS-1$
        }

        refCombo.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {

            }

            public void widgetSelected(SelectionEvent e) {
                if (refCombo.getText().length() > 0) {
                    elementNameText.setText("");//$NON-NLS-1$
                    elementNameText.setEditable(false);
                } else {
                    elementNameText.setEditable(true);
                    elementNameText.setText(getElementName() == null ? "" : getElementName());//$NON-NLS-1$
                }

            }
        });

        Label minLabel = new Label(composite, SWT.NONE);
        minLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
        minLabel.setText("Minimum Occurence");

        minOccursText = new Text(composite, SWT.NONE);
        minOccursText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        minOccursText.setDoubleClickEnabled(false);
        minOccursText.setText("" + getMinOccurs());//$NON-NLS-1$

        Label maxLabel = new Label(composite, SWT.NONE);
        maxLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
        maxLabel.setText("Maximum Occurence");

        maxOccursText = new Text(composite, SWT.NONE);
        maxOccursText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        maxOccursText.setText(getMaxOccurs() == -1 ? "" : "" + getMaxOccurs());//$NON-NLS-1$//$NON-NLS-2$

        if (isNew && Util.IsEnterPrise()) {
            checkBox = new Button(composite, SWT.CHECK);
            checkBox.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true, 2, 1));
            checkBox.addSelectionListener(new SelectionListener() {

                public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
                };

                public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                    inherit = checkBox.getSelection();
                };
            });

            checkBox.setSelection(inherit);
            checkBox.setText(" Inherit the security annotations");
        }

        return composite;
    }

    protected void createButtonsForButtonBar(Composite parent) {
        super.createButtonsForButtonBar(parent);
        getButton(IDialogConstants.OK_ID).addSelectionListener(this.caller);
        /*
         * createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true); createButton(parent,
         * IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
         */
    }

    protected void okPressed() {
        elementName = elementNameText.getText().trim();
        refName = refCombo.getText();
        if (((elementName == null) || ("".equals(elementName))) && ((refName == null) || "".equals(refName))) {//$NON-NLS-1$//$NON-NLS-2$
            MessageDialog.openError(this.getShell(), "Error",
                    "The Business Element Name cannot be empty if the reference is empty");
            setReturnCode(-1);
            elementNameText.setFocus();
            return;
        }

        if (elementName.replaceAll("\\s", "").length() != elementName.length()) {//$NON-NLS-1$//$NON-NLS-2$
            MessageDialog.openError(this.getShell(), "Error", "The Business Element Name cannot contain the empty characters");
            setReturnCode(-1);
            elementNameText.setFocus();
            return;
        }

        if ("".equals(minOccursText.getText()) && "".equals(maxOccursText.getText())) {//$NON-NLS-1$//$NON-NLS-2$
            minOccurs = 1;
            maxOccurs = 1;
            return;
        }
        try {
            minOccurs = Integer.parseInt(minOccursText.getText());
        } catch (Exception e1) {
            MessageDialog.openError(this.getShell(), "Error", "The Minimum Occurence must be greater or equal to Zero");
            setReturnCode(-1);
            minOccursText.setFocus();
            return;
        }
        if (minOccurs < 0) {
            MessageDialog.openError(this.getShell(), "Error", "The Minimum Occurence must be greater or equal to Zero");
            setReturnCode(-1);
            minOccursText.setFocus();
            return;
        }

        if ("".equals(maxOccursText.getText())) {//$NON-NLS-1$
            maxOccurs = -1;
        } else {
            try {
                maxOccurs = Integer.parseInt(maxOccursText.getText());
            } catch (Exception e2) {
                MessageDialog.openError(this.getShell(), "Error", "The Maximum Occurence must be a Number or Blank (unbounded).");
                setReturnCode(-1);
                maxOccursText.setFocus();
                return;
            }
            if ((maxOccurs < minOccurs) || (maxOccurs <= 0))
                maxOccurs = -1;
        }

        setReturnCode(OK);
        // no close let Action Handler handle it
    }

    public String getElementName() {
        return elementName;
    }

    public int getMaxOccurs() {
        return maxOccurs;
    }

    public int getMinOccurs() {
        return minOccurs;
    }

    public String getRefName() {
        return refName;
    }

}
