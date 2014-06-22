// ============================================================================
//
// Copyright (C) 2006-2010 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.mdm.repository.ui.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.mdm.repository.core.AbstractRepositoryAction;
import org.talend.mdm.repository.core.service.ContainerCacheService;
import org.talend.mdm.repository.i18n.Messages;
import org.talend.mdm.repository.model.mdmproperties.ContainerItem;
import org.talend.mdm.repository.utils.RepositoryResourceUtil;

import com.amalto.workbench.image.EImage;
import com.amalto.workbench.image.ImageCache;

/**
 * DOC hbhong class global comment. Detailled comment
 */
public abstract class AbstractSimpleAddAction extends AbstractRepositoryAction {

    private static Logger log = Logger.getLogger(AbstractSimpleAddAction.class);

    /**
     * DOC hbhong AddMenu constructor comment.
     * 
     * @param text
     */
    public AbstractSimpleAddAction() {
        super("New"); //$NON-NLS-1$
        setImageDescriptor(ImageCache.getImage(EImage.ADD_OBJ.getPath()));
    }

    protected ContainerItem parentItem;

    protected Object selectObj;

    protected abstract String getDialogTitle();

    protected void doRun() {
        parentItem = null;
        selectObj = getSelectedObject().get(0);
        if (selectObj instanceof IRepositoryViewObject) {
            Item pItem = ((IRepositoryViewObject) selectObj).getProperty().getItem();
            if (pItem instanceof ContainerItem) {
                parentItem = (ContainerItem) pItem;
            }
        }

        InputDialog dlg = new InputDialog(getShell(), getDialogTitle(), Messages.Common_inputName, null, new IInputValidator() {

            public String isValid(String newText) {
                if (newText == null || newText.trim().length() == 0)
                    return Messages.Common_nameCanNotBeEmpty;
                if (!Pattern.matches("\\w*(#|-|\\.|\\w*)+\\w+", newText)) {//$NON-NLS-1$
                    return Messages.Common_nameInvalid;
                }
                if (RepositoryResourceUtil.isExistByName(parentItem.getRepObjType(), newText.trim())) {
                    return Messages.Common_nameIsUsed;
                }
                return null;
            };
        });
        dlg.setBlockOnOpen(true);
        if (dlg.open() == Window.CANCEL)
            return;
        String key = dlg.getValue();
        if (key != null) {
            Item item = createServerObject(key);
            commonViewer.refresh(selectObj);
            commonViewer.expandToLevel(selectObj, 1);
            if (runOpenActionAfterCreation(item)) {
                openEditor(item);
            }
        }
    }

    protected boolean runOpenActionAfterCreation(Item item) {
        return true;
    }

    protected void openEditor(Item item) {
        if (item == null)
            return;
        IRepositoryViewObject viewObject = ContainerCacheService.get(item.getProperty());
        OpenObjectAction action = new OpenObjectAction();
        List<Object> selObjects = new ArrayList<Object>();
        selObjects.add(viewObject);
        action.setSelObjects(selObjects);
        action.run();
    }

    protected abstract Item createServerObject(String key);

    public String getGroupName() {
        return GROUP_EDIT;
    }

}