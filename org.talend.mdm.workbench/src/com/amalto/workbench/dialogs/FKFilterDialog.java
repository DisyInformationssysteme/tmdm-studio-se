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
package com.amalto.workbench.dialogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.amalto.workbench.editors.DataModelMainPage;
import com.amalto.workbench.models.Line;
import com.amalto.workbench.models.TreeParent;
import com.amalto.workbench.utils.IConstants;
import com.amalto.workbench.widgets.ComplexTableViewerColumn;
import com.amalto.workbench.widgets.ICellEditor;
import com.amalto.workbench.widgets.TisTableViewer;
import com.amalto.workbench.widgets.WidgetFactory;

public class FKFilterDialog extends Dialog {

    private static final String CUSTOM_FILTERS_PREFIX = "$CFFP:";//$NON-NLS-1$

    String title;

    private TisTableViewer viewer;

    DataModelMainPage page;

    String filter;

    private ComplexTableViewerColumn[] columns;

    String conceptName;

    Text customFiltersText;

    public FKFilterDialog(Shell parentShell, String title, String filter, DataModelMainPage page, String conceptName) {
        super(parentShell);
        this.filter = filter;
        this.page = page;
        this.title = title;
        this.conceptName = conceptName;
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        parent.getShell().setText(this.title);
        Composite composite = (Composite) super.createDialogArea(parent);
        composite.setLayout(new GridLayout(2, false));

        columns = new ComplexTableViewerColumn[] {
                new ComplexTableViewerColumn("XPath", false, "newXPath", "newXPath", "", ComplexTableViewerColumn.XPATH_STYLE,//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
                        new String[] {}, 0),
                new ComplexTableViewerColumn("Operator", false, "", "", "", ComplexTableViewerColumn.COMBO_STYLE,//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
                        IConstants.VIEW_CONDITION_OPERATORS, 0),
                new ComplexTableViewerColumn("Value", false, "", "", "", ComplexTableViewerColumn.XPATH_STYLE, new String[] {}, 0),//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
                new ComplexTableViewerColumn("Predicate", true, "", "", "", ComplexTableViewerColumn.COMBO_STYLE,//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
                        IConstants.PREDICATES, 0), };
        columns[0].setColumnWidth(200);
        columns[1].setColumnWidth(140);
        columns[2].setColumnWidth(200);
        columns[3].setColumnWidth(140);
        viewer = getNewTisTableViewer(Arrays.asList(columns), WidgetFactory.getWidgetFactory(), composite);
        viewer.setXpath(true);
        // viewer.setMainPage(page);//TODO
        // viewer.setConceptName(conceptName);
        // viewer.setContext(true);

        // Modified by hbhong,to fix bug 21784
        TreeParent treeParent = (TreeParent) page.getAdapter(TreeParent.class);
        viewer.setTreeParent(treeParent);
        // The ending| bug:21784
        viewer.create();
        viewer.setHeight(140);
        viewer.setWidth(680);
        viewer.getMainComposite().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 3));

        // the text box of the custom filters
        Group customFiltersGroup = new Group(composite, SWT.NONE);
        customFiltersGroup.setVisible(true);
        customFiltersGroup.setText("Custom filters");
        customFiltersGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
        customFiltersGroup.setLayout(new GridLayout(1, false));

        customFiltersText = new Text(customFiltersGroup, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
        customFiltersText.setEditable(true);
        GridData customFiltersTextGridData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        customFiltersTextGridData.heightHint = 50;
        customFiltersText.setLayoutData(customFiltersTextGridData);

        parent.getShell().addDisposeListener(new DisposeListener() {

            public void widgetDisposed(DisposeEvent e) {
                XpathSelectDialog.setContext(null);
            }
        });

        // init data
        parseRules();

        return composite;
    }

    protected TisTableViewer getNewTisTableViewer(List<ComplexTableViewerColumn> columns, FormToolkit toolkit, Composite parent) {
        return new TisTableViewer(columns, toolkit, parent);
    }

    @Override
    protected boolean isResizable() {
        return true;
    }

    private void parseRules() {

        List<Line> lines = new ArrayList<Line>();
        if (filter != null && filter.length() > 0) {
            if (filter.startsWith(CUSTOM_FILTERS_PREFIX)) {
                filter = StringEscapeUtils.unescapeXml(filter);
                customFiltersText.setText(filter.substring(6));
            } else {
                lines = buildLine(filter);
            }
        }
        viewer.getViewer().setInput(lines);
    }

    @Override
    protected void okPressed() {

        TableItem[] items = viewer.getViewer().getTable().getItems();
        if (items.length > 0 && customFiltersText.getText() != null && customFiltersText.getText().trim().length() > 0) {
            if (!MessageDialog.openConfirm(null, "Confirm",
                    "Customized filter will override your already configured information, are you sure?"))
                return;
        }

        XpathSelectDialog.setContext(null);
        deactiveAllCellEditors();
        resetFilter();
        super.okPressed();
    }

    private void deactiveAllCellEditors() {
        CellEditor[] editors = viewer.getViewer().getCellEditors();
        for (CellEditor editor : editors) {
            if (editor instanceof ICellEditor) {
                ((ICellEditor) editor).deactive();
            }
        }
    }

    @Override
    protected void cancelPressed() {
        super.cancelPressed();
        XpathSelectDialog.setContext(null);
    }

    private String resetFilter() {

        if (customFiltersText.getText() != null && customFiltersText.getText().trim().length() > 0) {
            filter = CUSTOM_FILTERS_PREFIX + customFiltersText.getText().trim();
            filter = StringEscapeUtils.escapeXml(filter);
            return filter;
        }

        TableItem[] items = viewer.getViewer().getTable().getItems();
        StringBuffer sb = new StringBuffer();
        if (items.length > 0) {
            for (TableItem item : items) {
                Line line = (Line) item.getData();

                String xpath = line.keyValues.get(0).value;
                String operator = line.keyValues.get(1).value;
                String value = line.keyValues.get(2).value;
                value = normalizeValue(value);
                String predicate = line.keyValues.get(3).value;
                sb.append(xpath + "$$" + operator + "$$" + value + "$$" + predicate + "#");//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
            }
            // rules.add(sb.toString());
        }

        return filter = sb.toString();
    }

    private String normalizeValue(String value) {
        if (value != null && value.trim().length() > 0) {
            value = value.replaceAll("\"", "&quot;");//$NON-NLS-1$//$NON-NLS-2$
            value = value.replaceAll("'", "&quot;");//$NON-NLS-1$//$NON-NLS-2$
        }
        return value;
    }

    public String getFilter() {
        return filter;
    }

    public List<Line> buildLine(String criteria) {
        List<Line> lines = new ArrayList<Line>();
        if (criteria != null) {
            String[] criterias = criteria.split("#");//$NON-NLS-1$
            for (String cria : criterias) {
                String[] values = cria.split("\\$\\$");//$NON-NLS-1$
                List<String> list = new ArrayList<String>();
                list.addAll(Arrays.asList(values));
                int num = 4 - list.size();
                for (int i = 0; i < num; i++) {
                    list.add("");//$NON-NLS-1$
                }
                // filter value
                if (list.get(2) != null && list.get(2).length() > 0) {
                    String value = list.get(2);
                    value = value.replaceAll("&quot;", "\"");//$NON-NLS-1$//$NON-NLS-2$
                    list.set(2, value);
                }
                Line line = new Line(columns, list.toArray(new String[list.size()]));
                lines.add(line);
            }
        }
        return lines;
    }

}
