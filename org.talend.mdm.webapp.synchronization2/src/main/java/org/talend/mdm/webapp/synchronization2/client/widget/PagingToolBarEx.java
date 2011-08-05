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
package org.talend.mdm.webapp.synchronization2.client.widget;

import com.extjs.gxt.ui.client.core.El;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.extjs.gxt.ui.client.widget.toolbar.LabelToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Element;


public class PagingToolBarEx extends PagingToolBar {
    El inputEl;
    public PagingToolBarEx(int pageSize) {
        super(pageSize);
        LabelToolItem sizeLabel = new LabelToolItem("new Line size"); //$NON-NLS-1$
        
        final NumberField sizeField = new NumberField(){
            protected void onRender(Element target, int index) {
                super.onRender(target, index);
                inputEl = this.input;
            }
        };
        sizeField.setWidth(30);
        sizeField.setValue(pageSize);
        sizeField.setValidator(validator);
        sizeField.addListener(Events.Change, new Listener<BaseEvent>() {

            public void handleEvent(BaseEvent be) {
                if (sizeField.isValid() && sizeField.getValue() != null){
                    setPageSize((int)Double.parseDouble(sizeField.getValue()+""));//$NON-NLS-1$
                    first();
                }
            }
        });
        sizeField.addListener(Events.KeyDown, new Listener<FieldEvent>() {

            public void handleEvent(FieldEvent fe) {
                if (fe.getKeyCode() == KeyCodes.KEY_ENTER) {
                    blur(inputEl.dom);
                }
            }
        });
        sizeField.setValue(pageSize);
        this.insert(new SeparatorToolItem(), this.getItemCount() - 2);
        this.insert(sizeLabel, this.getItemCount() - 2);
        this.insert(sizeField, this.getItemCount() - 2);
    }
    
    private native void blur(Element el)/*-{
        el.blur();
    }-*/;
    
    Validator validator = new Validator() {
        
        public String validate(Field<?> field, String value) {
            String valueStr = value == null? "": value.toString();//$NON-NLS-1$
            boolean success = true;
            try{
                int num = Integer.parseInt(valueStr);
                if (num <= 0) {
                    success = false;
                }
            } catch (NumberFormatException e){
                success = false;
            }
            if (!success){
                return "HAHAHA"; //$NON-NLS-1$
            }
            return null;
        }
    };
}
