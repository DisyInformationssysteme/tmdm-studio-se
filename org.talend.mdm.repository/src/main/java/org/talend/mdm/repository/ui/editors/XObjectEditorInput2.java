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
package org.talend.mdm.repository.ui.editors;

import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.mdm.repository.model.mdmproperties.MDMServerObjectItem;
import org.talend.mdm.repository.model.mdmserverobject.MDMServerObject;
import org.talend.mdm.repository.utils.Bean2EObjUtil;

import com.amalto.workbench.models.TreeObject;
import com.amalto.workbench.providers.XObjectEditorInput;

public class XObjectEditorInput2 extends XObjectEditorInput implements IRepositoryViewEditorInput {

    private final IRepositoryViewObject viewObject;

    public XObjectEditorInput2(IRepositoryViewObject viewObject) {
        super(new TreeObject(), null);
        this.viewObject = viewObject;

        init(getInputItem());
    }

    public IRepositoryViewObject getViewObject() {
        return viewObject;
    }

    protected void init(Item item) {
        MDMServerObject serverObject = ((MDMServerObjectItem) item).getMDMServerObject();
        Object treeObject = Bean2EObjUtil.getInstance().wrapEObjWithTreeObject(serverObject);
        setModel(treeObject);
        setName(serverObject.getName());
    }

    public XObjectEditorInput2(IRepositoryViewObject viewObject, Object model, String name) {
        super(model, name);
        this.viewObject = viewObject;
    }

    public String getToolTipText() {
        TreeObject xobject = (TreeObject) getModel();
        return TreeObject.getTypeName(xobject.getType()) + " - "//$NON-NLS-1$
                + xobject.getDisplayName();
    }

    public String getEditorId() {
        return XObjectEditor2.EDITOR_ID;
    }

    public Item getInputItem() {
        if (viewObject != null && viewObject.getProperty() != null)
            return viewObject.getProperty().getItem();
        return null;
    }

    private boolean readOnly;

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }
}
