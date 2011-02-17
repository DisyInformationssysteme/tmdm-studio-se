package com.amalto.workbench.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Event;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDIdentityConstraintDefinition;

import com.amalto.workbench.editors.DataModelMainPage;
import com.amalto.workbench.image.EImage;
import com.amalto.workbench.image.ImageCache;
import com.amalto.workbench.utils.Util;

public class XSDEditConceptAction extends UndoAction {

    public XSDEditConceptAction(DataModelMainPage page) {
        super(page);
        setImageDescriptor(ImageCache.getImage(EImage.EDIT_OBJ.getPath()));
        setText("Edit Entity");
        setToolTipText("Edit an Entity");
    }

    public IStatus doAction() {
        try {
            ISelection selection = page.getTreeViewer().getSelection();
            XSDElementDeclaration decl = (XSDElementDeclaration) ((IStructuredSelection) selection).getFirstElement();
            ArrayList<Object> objList = new ArrayList<Object>();
            IStructuredContentProvider provider = (IStructuredContentProvider) page.getTreeViewer().getContentProvider();
            Object[] objs = Util.getAllObject(page.getSite(), objList, provider);
            String oldName = decl.getName();

            InputDialog id = new InputDialog(page.getSite().getShell(), "Edit Entity", "Enter a new Name for the Entity",
                    oldName, new IInputValidator() {

                        public String isValid(String newText) {
                            if ((newText == null) || "".equals(newText))
                                return "The Entity Name cannot be empty";

                            if (Pattern.compile("^\\s+\\w+\\s*").matcher(newText).matches()
                                    || newText.trim().replaceAll("\\s", "").length() != newText.trim().length())
                                return "The name cannot contain the empty characters";

                            EList list = schema.getElementDeclarations();
                            for (Iterator iter = list.iterator(); iter.hasNext();) {
                                XSDElementDeclaration d = (XSDElementDeclaration) iter.next();
                                if (d.getName().equals(newText.trim()))
                                    return "This Entity already exists";
                            }
                            return null;
                        };
                    });

            id.setBlockOnOpen(true);
            int ret = id.open();
            if (ret == Dialog.CANCEL) {
                return Status.CANCEL_STATUS;
            }

            decl.setName(id.getValue().trim());
            decl.updateElement();
            Util.updateReference(decl, objs, id.getValue());

            // change unique key with new name of concept
            EList list = decl.getIdentityConstraintDefinitions();
            XSDIdentityConstraintDefinition toUpdate = null;
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                XSDIdentityConstraintDefinition icd = (XSDIdentityConstraintDefinition) iter.next();
                if (icd.getName().equals(oldName)) {
                    toUpdate = icd;
                    break;
                }
            }
            if (toUpdate != null) {
                toUpdate.setName(id.getValue());
                toUpdate.updateElement();
            }

            page.refresh();
            page.markDirty();
            // page.refreshPage();

        } catch (Exception e) {
            e.printStackTrace();
            MessageDialog.openError(page.getSite().getShell(), "Error",
                    "An error occured trying to edit an Entity: " + e.getLocalizedMessage());
            return Status.CANCEL_STATUS;
        }
        return Status.OK_STATUS;
    }

    public void runWithEvent(Event event) {
        super.runWithEvent(event);
    }
}
