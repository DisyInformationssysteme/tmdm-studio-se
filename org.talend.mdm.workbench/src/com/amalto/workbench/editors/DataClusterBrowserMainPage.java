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
package com.amalto.workbench.editors;

import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.talend.mdm.commmon.util.core.ICoreConstants;

import com.amalto.workbench.availablemodel.AvailableModelUtil;
import com.amalto.workbench.availablemodel.IAvailableModel;
import com.amalto.workbench.compare.CompareHeadInfo;
import com.amalto.workbench.compare.CompareManager;
import com.amalto.workbench.dialogs.DOMViewDialog;
import com.amalto.workbench.dialogs.DataModelSelectDialog;
import com.amalto.workbench.image.EImage;
import com.amalto.workbench.image.ImageCache;
import com.amalto.workbench.models.IXObjectModelListener;
import com.amalto.workbench.models.TreeObject;
import com.amalto.workbench.models.TreeParent;
import com.amalto.workbench.providers.XObjectBrowserInput;
import com.amalto.workbench.utils.LineItem;
import com.amalto.workbench.utils.Util;
import com.amalto.workbench.webservices.WSConceptRevisionMapMapEntry;
import com.amalto.workbench.webservices.WSCount;
import com.amalto.workbench.webservices.WSDataCluster;
import com.amalto.workbench.webservices.WSDataClusterPK;
import com.amalto.workbench.webservices.WSDataModel;
import com.amalto.workbench.webservices.WSDataModelPK;
import com.amalto.workbench.webservices.WSDeleteItemWithReport;
import com.amalto.workbench.webservices.WSGetConceptsInDataClusterWithRevisions;
import com.amalto.workbench.webservices.WSGetCurrentUniverse;
import com.amalto.workbench.webservices.WSGetDataCluster;
import com.amalto.workbench.webservices.WSGetDataModel;
import com.amalto.workbench.webservices.WSGetItem;
import com.amalto.workbench.webservices.WSGetItemPKsByCriteria;
import com.amalto.workbench.webservices.WSGetItemPKsByFullCriteria;
import com.amalto.workbench.webservices.WSIsItemModifiedByOther;
import com.amalto.workbench.webservices.WSItem;
import com.amalto.workbench.webservices.WSItemPK;
import com.amalto.workbench.webservices.WSItemPKsByCriteriaResponseResults;
import com.amalto.workbench.webservices.WSPutItem;
import com.amalto.workbench.webservices.WSPutItemWithReport;
import com.amalto.workbench.webservices.WSRegexDataModelPKs;
import com.amalto.workbench.webservices.WSRouteItemV2;
import com.amalto.workbench.webservices.WSString;
import com.amalto.workbench.webservices.WSUniverse;
import com.amalto.workbench.webservices.WSUniversePK;
import com.amalto.workbench.webservices.WSUpdateMetadataItem;
import com.amalto.workbench.webservices.WSVersioningGetItemContent;
import com.amalto.workbench.webservices.XtentisPort;
import com.amalto.workbench.widgets.CalendarSelectWidget;
import com.amalto.workbench.widgets.IPagingListener;
import com.amalto.workbench.widgets.PageingToolBar;
import com.amalto.workbench.widgets.WidgetFactory;

public class DataClusterBrowserMainPage extends AMainPage implements IXObjectModelListener, IPagingListener {

    private static final Log log = LogFactory.getLog(DataClusterBrowserMainPage.class);

    protected static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");//$NON-NLS-1$

    protected Button checkFTSearchButton;

    protected Button showTaskIdCB;

    protected Text searchText;

    protected Text fromText;

    protected Text toText;

    protected Combo conceptCombo;

    protected Text keyText;

    protected TableViewer resultsViewer;

    protected ListViewer wcListViewer;

    protected boolean[] ascending = { true, false, false };

    private KeyListener keylistener = new KeyListener() {

        public void keyPressed(KeyEvent e) {
        }

        public void keyReleased(KeyEvent e) {
            if ((e.stateMask == 0) && (e.character == SWT.CR)) {
                DataClusterBrowserMainPage.this.resultsViewer.setInput(getResults(true));
                pageToolBar.getComposite().setVisible(true);
                pageToolBar.getComposite().layout(true);
                pageToolBar.getComposite().getParent().layout(true);
                readjustViewerHeight();
            }
        }// keyReleased
    };

    private PageingToolBar pageToolBar;

    public DataClusterBrowserMainPage(FormEditor editor) {
        super(editor, DataClusterBrowserMainPage.class.getName(), "Data Container Browser "
                + ((XObjectBrowserInput) editor.getEditorInput()).getName());
        // listen to events
        ((XObjectBrowserInput) editor.getEditorInput()).addListener(this);
    }

    @Override
    protected void createFormContent(IManagedForm managedForm) {

        try {
            // sets the title
            managedForm.getForm().setText(this.getTitle());

            // get the toolkit
            FormToolkit toolkit = WidgetFactory.getWidgetFactory();// managedForm.getToolkit();

            // get the body
            Composite composite = managedForm.getForm().getBody();
            // composite.setLayout(new GridLayout(9, false));
            composite.setLayout(new GridLayout());

            // We do not implement IFormPart: we do not care about lifecycle management
            Composite compFirstLine = toolkit.createComposite(composite, SWT.NONE);
            compFirstLine.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
            compFirstLine.setLayout(new GridLayout(9, false));

            // from
            Label fromLabel = toolkit.createLabel(compFirstLine, "From", SWT.NULL);
            fromLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

            CalendarSelectWidget fromCalendar = new CalendarSelectWidget(toolkit, compFirstLine, true);
            fromText = fromCalendar.getText();
            fromText.addKeyListener(new KeyListener() {

                public void keyPressed(KeyEvent e) {
                }

                public void keyReleased(KeyEvent e) {
                    if ((e.stateMask == 0) && (e.character == SWT.CR)) {
                        DataClusterBrowserMainPage.this.resultsViewer.setInput(getResults(true));
                        pageToolBar.getComposite().setVisible(true);
                        pageToolBar.getComposite().layout(true);
                        pageToolBar.getComposite().getParent().layout(true);
                        readjustViewerHeight();
                    }
                }// keyReleased
            }// keyListener
            );
            // to
            Label toLabel = toolkit.createLabel(compFirstLine, "To", SWT.NULL);
            toLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

            CalendarSelectWidget toCalendar = new CalendarSelectWidget(toolkit, compFirstLine, false);
            toText = toCalendar.getText();
            toText.addKeyListener(keylistener);

            Label conceptLabel = toolkit.createLabel(compFirstLine, "Entity", SWT.NULL);
            conceptLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
            conceptCombo = new Combo(compFirstLine, SWT.READ_ONLY | SWT.DROP_DOWN | SWT.MULTI);
            conceptCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
            // ((GridData) conceptCombo.getLayoutData()).widthHint = 180;
            conceptCombo.addKeyListener(keylistener);

            // search
            Button bSearch = toolkit.createButton(compFirstLine, "", SWT.CENTER); //$NON-NLS-1$
            bSearch.setImage(ImageCache.getCreatedImage(EImage.BROWSE.getPath()));
            bSearch.setToolTipText("Search");
            bSearch.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
            bSearch.addListener(SWT.Selection, new Listener() {

                public void handleEvent(Event event) {
                    pageToolBar.reset();
                    doSearch();
                };
            });

            /**********
             * Second Line
             */
            Composite compSecondLine = toolkit.createComposite(composite, SWT.NONE);
            compSecondLine.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
            compSecondLine.setLayout(new GridLayout(9, false));

            Label keyLabel = toolkit.createLabel(compSecondLine, "Keys", SWT.NULL);
            keyLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
            keyText = toolkit.createText(compSecondLine, "", SWT.BORDER);//$NON-NLS-1$
            keyText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
            keyText.addKeyListener(keylistener);

            /***
             * Search Text
             */
            Label descriptionLabel = toolkit.createLabel(compSecondLine, "Keywords", SWT.NULL);
            descriptionLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
            searchText = toolkit.createText(compSecondLine, "", SWT.BORDER);//$NON-NLS-1$
            searchText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
            // searchText.addModifyListener(this);
            searchText.addKeyListener(keylistener);

            checkFTSearchButton = toolkit.createButton(compSecondLine, "Use Full Text Search", SWT.CHECK);
            checkFTSearchButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

            Label fill = toolkit.createLabel(compSecondLine, "", SWT.NULL);//$NON-NLS-1$
            fill.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 8, 1));

            showTaskIdCB = toolkit.createButton(compSecondLine, "Show Task ID", SWT.CHECK);
            showTaskIdCB.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

            // pagetoolbar
            pageToolBar = new PageingToolBar(composite);
            pageToolBar.getComposite().setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 9, 1));
            pageToolBar.getComposite().setVisible(false);
            pageToolBar.setPageingListener(this);

            int style = SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.FULL_SELECTION | SWT.HIDE_SELECTION;
            resultsViewer = new TableViewer(composite, style);
            initTable(resultsViewer.getTable());

            resultsViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
            // ((GridData) resultsViewer.getControl().getLayoutData()).heightHint = DEFAULT_VIEWER_HEIGHT;
            resultsViewer.setContentProvider(new ArrayContentProvider());
            resultsViewer.setLabelProvider(new ClusterTableLabelProvider());
            resultsViewer.addDoubleClickListener(new IDoubleClickListener() {

                public void doubleClick(DoubleClickEvent event) {
                    resultsViewer.setSelection(event.getSelection());
                    try {
                        new EditItemAction(DataClusterBrowserMainPage.this.getSite().getShell(), resultsViewer).run();
                    } catch (Exception e) {
                        MessageDialog.openError(
                                DataClusterBrowserMainPage.this.getSite().getShell(),
                                "Error",
                                "Unable to display the element as a tree:\n" + e.getClass().getName() + ": "
                                        + e.getLocalizedMessage());
                    }
                }
            });

            hookKeyboard();

            hookContextMenu();

            managedForm.reflow(true); // nothng will show on the form if not called

            searchText.setFocus();
            // adapt body add mouse/focus listener for child
            // WidgetFactory factory=new WidgetFactory();
            toolkit.adapt(composite);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }// createFormContent

    public void doSearch() {
        if (showTaskIdCB.getSelection()) {
            if (DataClusterBrowserMainPage.this.resultsViewer.getTable().getColumnCount() != 4) {
                final TableColumn column3 = new TableColumn(DataClusterBrowserMainPage.this.resultsViewer.getTable(), SWT.LEFT, 3);
                column3.setText("TaskId");
                column3.setWidth(150);
            }
        } else {
            if (DataClusterBrowserMainPage.this.resultsViewer.getTable().getColumnCount() == 4) {
                DataClusterBrowserMainPage.this.resultsViewer.getTable().getColumn(3).dispose();
            }
        }

        DataClusterBrowserMainPage.this.resultsViewer.setInput(getResults(true));
        pageToolBar.getComposite().setVisible(true);
        pageToolBar.getComposite().layout(true);
        pageToolBar.getComposite().getParent().layout(true);
        readjustViewerHeight();
    }

    /**
     * readjust the viewer height
     */
    private void readjustViewerHeight() {
        resultsViewer.refresh();
    }

    public TableViewer getResultsViewer() {
        return resultsViewer;
    }

    public void setResultsViewer(TableViewer resultsViewer) {
        this.resultsViewer = resultsViewer;
    }

    /**
     * Create the Table
     */
    private void initTable(final Table table) {

        // final Table table = new Table(parent, style);
        GridData gridData = new GridData(GridData.FILL_BOTH);
        gridData.grabExcessVerticalSpace = true;
        gridData.horizontalSpan = 3;
        table.setLayoutData(gridData);

        table.setLinesVisible(true);
        table.setHeaderVisible(true);

        // table.setSortDirection(0);
        // 1st column
        final TableColumn column = new TableColumn(table, SWT.LEFT, 0);
        // table.setSortColumn(column);
        // table.setSortDirection(SWT.UP);
        column.setText("Date");
        column.setWidth(150);
        // column.setImage(getDefaultImage());//============
        column.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
                ascending[0] = !ascending[0];
                DataClusterBrowserMainPage.this.resultsViewer.setSorter(new TableSorter(0, ascending[0]));
            }

            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                ascending[0] = !ascending[0];
                DataClusterBrowserMainPage.this.resultsViewer.setSorter(new TableSorter(0, ascending[0]));
                if (ascending[0]) {
                    table.setSortColumn(column);
                    table.setSortDirection(SWT.DOWN);
                } else {
                    table.setSortColumn(column);
                    table.setSortDirection(SWT.UP);
                }
            }
        });

        // 2nd column
        final TableColumn column1 = new TableColumn(table, SWT.LEFT, 1);
        // table.setSortColumn(column1);
        column1.setText("Entity");
        column1.setWidth(150);
        // Add listener to column so tasks are sorted by description when clicked
        column1.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
                ascending[1] = !ascending[1];
                DataClusterBrowserMainPage.this.resultsViewer.setSorter(new TableSorter(1, ascending[1]));
            }

            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                ascending[1] = !ascending[1];
                DataClusterBrowserMainPage.this.resultsViewer.setSorter(new TableSorter(1, ascending[1]));
                if (ascending[1]) {
                    table.setSortColumn(column1);
                    table.setSortDirection(SWT.DOWN);
                } else {
                    table.setSortColumn(column1);
                    table.setSortDirection(SWT.UP);
                }

            }
        });

        // 3rd column
        final TableColumn column2 = new TableColumn(table, SWT.LEFT, 2);
        // table.setSortColumn(column2);
        column2.setText("Keys");
        column2.setWidth(150);
        // Add listener to column so tasks are sorted by description when clicked
        column2.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
                ascending[2] = !ascending[2];
                DataClusterBrowserMainPage.this.resultsViewer.setSorter(new TableSorter(2, ascending[2]));
            }

            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                ascending[2] = !ascending[2];
                DataClusterBrowserMainPage.this.resultsViewer.setSorter(new TableSorter(2, ascending[2]));
                if (ascending[2]) {
                    table.setSortColumn(column2);
                    table.setSortDirection(SWT.DOWN);
                } else {
                    table.setSortColumn(column2);
                    table.setSortDirection(SWT.UP);
                }

            }
        });

        // return table;
    }

    @Override
    protected void createCharacteristicsContent(FormToolkit toolkit, Composite charComposite) {
        // Everything is implemented in createFormContent
    }

    @Override
    protected void refreshData() {
        try {
            if (conceptCombo.isDisposed())
                return;
            if (getXObject().getEndpointAddress() == null)
                return;
            XtentisPort port = Util.getPort(getXObject());

            WSDataCluster cluster = null;
            if (getXObject().getWsObject() == null) { // then fetch from server
                cluster = port.getDataCluster(new WSGetDataCluster((WSDataClusterPK) getXObject().getWsKey()));
                getXObject().setWsObject(cluster);
            } else { // it has been opened by an editor - use the object there
                cluster = (WSDataCluster) getXObject().getWsObject();
            }

            WSUniverse currentUniverse = port.getCurrentUniverse(new WSGetCurrentUniverse());
            String currentUniverseName = "";//$NON-NLS-1$
            if (currentUniverse != null)
                currentUniverseName = currentUniverse.getName();
            if (currentUniverseName != null && currentUniverseName.equals("[HEAD]"))//$NON-NLS-1$
                currentUniverseName = "";//$NON-NLS-1$

            // add by myli; fix the bug:0013077: if the data is too much, just get the entities from the model instead
            // of from the container.
            String[] concepts;

            String clusterName = URLEncoder.encode(cluster.getName(), "utf-8");//$NON-NLS-1$

            WSString countStr = port.count(new WSCount(new WSDataClusterPK(cluster.getName()), "*", null, 100)); //$NON-NLS-1$
            long count = Long.parseLong(countStr.getValue());

            if (count > 100000) {
                // if (false) {
                // Modified by hbhong,to fix bug 21784|
                TreeParent treeParent = (TreeParent) getAdapter(TreeParent.class);
                DataModelSelectDialog dialog = new DataModelSelectDialog(getSite().getShell(), treeParent);
                // The ending| bug:21784
                dialog.setBlockOnOpen(true);
                dialog.open();
                if (dialog.getReturnCode() == Window.OK) {
                    String xpath = dialog.getXpath();
                    WSDataModel dm = Util.getPort(this.getXObject()).getDataModel(new WSGetDataModel(new WSDataModelPK(xpath)));
                    if (dm == null)
                        return;
                    concepts = new String[Util.getConcepts(Util.getXSDSchema(dm.getXsdSchema())).size()];
                    Util.getConcepts(Util.getXSDSchema(dm.getXsdSchema())).toArray(concepts);
                    TreeObject object = null;
                    TreeObject[] children = this.getXObject().getServerRoot().getChildren();
                    for (int i = 0; i < children.length; i++) {
                        object = children[i];
                        if (object.getType() == TreeObject.DATA_MODEL)
                            break;
                    }
                    String revision = "";//$NON-NLS-1$
                    if (object != null) {
                    	// TMDM-2606: Don't expect data model to contain revision name (CE edition doesn't support revisions).
						if (object.getDisplayName().contains("[") && object.getDisplayName().contains("]")) {
							revision = object.getDisplayName().substring(
									object.getDisplayName().indexOf("[") + 1,//$NON-NLS-1$
									object.getDisplayName().indexOf("]"));//$NON-NLS-1$
						}
                    }
                    
                    for (int i = 0; i < concepts.length; i++) {
                        String concept = concepts[i];
                        if (revision == null || revision.equals("")) { //$NON-NLS-1$
                            revision = "HEAD";//$NON-NLS-1$
                        }
                        concepts[i] = concept + " " + "[" + revision + "]";//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
                    }
                    conceptCombo.removeAll();
                    conceptCombo.add("*");//$NON-NLS-1$
                    for (int i = 0; i < concepts.length; i++)
                        conceptCombo.add(concepts[i]);
                }
            } else {
                WSConceptRevisionMapMapEntry[] wsConceptRevisionMapMapEntries = port.getConceptsInDataClusterWithRevisions(
                        new WSGetConceptsInDataClusterWithRevisions(new WSDataClusterPK(clusterName), new WSUniversePK(
                                currentUniverseName))).getMapEntry();

                concepts = new String[wsConceptRevisionMapMapEntries.length];
                for (int i = 0; i < wsConceptRevisionMapMapEntries.length; i++) {
                    WSConceptRevisionMapMapEntry entry = wsConceptRevisionMapMapEntries[i];
                    String concept = entry.getConcept();
                    String revision = entry.getRevision();
                    if (revision == null || revision.equals("")) { //$NON-NLS-1$
                        revision = "HEAD";//$NON-NLS-1$
                    }
                    concepts[i] = concept + " " + "[" + revision + "]";//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
                }
                conceptCombo.removeAll();
                conceptCombo.add("*");//$NON-NLS-1$
                for (int i = 0; i < concepts.length; i++) {
                    conceptCombo.add(concepts[i]);
                }
            }
            conceptCombo.select(0);
            searchText.setFocus();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            MessageDialog.openError(this.getSite().getShell(), "Error refreshing the page",
                    "Error refreshing the page: " + e.getLocalizedMessage());
        }
    }

    @Override
    protected void commit() {
        try {

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            MessageDialog.openError(this.getSite().getShell(), "Error comtiting the page",
                    "Error comitting the page: " + e.getLocalizedMessage());
        }
    }

    @Override
    protected void createActions() {
    }

    private void hookKeyboard() {
        resultsViewer.getControl().addKeyListener(new KeyListener() {

            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
                if (e.keyCode == SWT.DEL) {
                    new PhysicalDeleteItemsAction(DataClusterBrowserMainPage.this.getSite().getShell(),
                            DataClusterBrowserMainPage.this.resultsViewer).run();
                }
            }
        });
    }

    private void hookContextMenu() {
        MenuManager menuMgr = new MenuManager();
        menuMgr.setRemoveAllWhenShown(true);
        menuMgr.addMenuListener(new IMenuListener() {

            public void menuAboutToShow(IMenuManager manager) {
                // ViewBrowserMainPage.this.fillContextMenu(manager);
                manager.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
                manager.appendToGroup(IWorkbenchActionConstants.MB_ADDITIONS, new EditItemAction(DataClusterBrowserMainPage.this
                        .getSite().getShell(), DataClusterBrowserMainPage.this.resultsViewer));
                manager.appendToGroup(IWorkbenchActionConstants.MB_ADDITIONS, new EditTaskIdAction(
                        DataClusterBrowserMainPage.this.getSite().getShell(), DataClusterBrowserMainPage.this.resultsViewer));
                manager.appendToGroup(IWorkbenchActionConstants.MB_ADDITIONS, new PhysicalDeleteItemsAction(
                        DataClusterBrowserMainPage.this.getSite().getShell(), DataClusterBrowserMainPage.this.resultsViewer));
                manager.appendToGroup(IWorkbenchActionConstants.MB_ADDITIONS, new LogicalDeleteItemsAction(
                        DataClusterBrowserMainPage.this.getSite().getShell(), DataClusterBrowserMainPage.this.resultsViewer));
                manager.appendToGroup(IWorkbenchActionConstants.MB_ADDITIONS, new NewItemAction(DataClusterBrowserMainPage.this
                        .getSite().getShell(), DataClusterBrowserMainPage.this.resultsViewer));
                manager.appendToGroup(IWorkbenchActionConstants.MB_ADDITIONS, new SubmitItemsAction(
                        DataClusterBrowserMainPage.this.getSite().getShell(), DataClusterBrowserMainPage.this.resultsViewer));
                // compare item with each other
                manager.appendToGroup(IWorkbenchActionConstants.MB_ADDITIONS, new CompareItemWithEachOtherAction(getSite()
                        .getShell(), getResultsViewer()));
                // available models
                java.util.List<IAvailableModel> availablemodels = AvailableModelUtil.getAvailableModels();
                for (IAvailableModel model : availablemodels) {
                    model.menuAboutToShow(manager, DataClusterBrowserMainPage.this);
                }

            }
        });
        Menu menu = menuMgr.createContextMenu(resultsViewer.getControl());
        resultsViewer.getControl().setMenu(menu);
        getSite().registerContextMenu(menuMgr, resultsViewer);
    }

    protected void fillContextMenu(IMenuManager manager) {
    }

    protected LineItem[] getResults(boolean showResultInfo) {

        Cursor waitCursor = null;

        try {

            Display display = getEditor().getSite().getPage().getWorkbenchWindow().getWorkbench().getDisplay();
            waitCursor = new Cursor(display, SWT.CURSOR_WAIT);
            this.getSite().getShell().setCursor(waitCursor);

            XtentisPort port = Util.getPort(getXObject());

            long from = -1;
            long to = -1;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");//$NON-NLS-1$
            Pattern pattern = Pattern.compile("^\\d{4}\\d{2}\\d{2} \\d{2}:\\d{2}:\\d{2}$");//$NON-NLS-1$

            if (!"".equals(fromText.getText())) {//$NON-NLS-1$

                String dateTimeText = fromText.getText().trim();
                Matcher matcher = pattern.matcher(dateTimeText);
                if (!matcher.matches()) {
                    MessageDialog.openWarning(this.getSite().getShell(), "Warning", "Time format is illegal! ");
                    return new LineItem[0];
                }

                try {
                    Date d = sdf.parse(fromText.getText());
                    from = d.getTime();
                } catch (ParseException pe) {
                }
            }

            if (!"".equals(toText.getText())) { //$NON-NLS-1$
                String dateTimeText = toText.getText().trim();
                Matcher matcher = pattern.matcher(dateTimeText);
                if (!matcher.matches()) {
                    MessageDialog.openWarning(this.getSite().getShell(), "Warning", "Time format is illegal! ");
                    return new LineItem[0];
                }

                try {
                    Date d = sdf.parse(toText.getText());
                    to = d.getTime();
                } catch (ParseException pe) {
                }
            }

            String concept = conceptCombo.getText();
            if ("*".equals(concept) | "".equals(concept))//$NON-NLS-1$//$NON-NLS-2$
                concept = null;
            if (concept != null) {
                concept = concept.replaceAll("\\[.*\\]", "").trim();//$NON-NLS-1$//$NON-NLS-2$
            }
            String keys = keyText.getText();
            if ("*".equals(keys) | "".equals(keys))//$NON-NLS-1$//$NON-NLS-2$
                keys = null;

            boolean useFTSearch = checkFTSearchButton.getSelection();
            String search = searchText.getText();
            if ("*".equals(search) | "".equals(search))//$NON-NLS-1$//$NON-NLS-2$
                search = null;

            int start = pageToolBar.getStart();
            int limit = pageToolBar.getLimit();
            // see 0015909
            String clusterName = URLEncoder.encode(getXObject().toString(), "utf-8");//$NON-NLS-1$
            WSDataClusterPK clusterPk = new WSDataClusterPK(clusterName);
            // @temp yguo, get item with taskid or get taskid by specify wsitempk.
            WSItemPKsByCriteriaResponseResults[] results = port.getItemPKsByFullCriteria(
                    new WSGetItemPKsByFullCriteria(new WSGetItemPKsByCriteria(clusterPk, concept, search, keys, from, to, start,
                            limit), useFTSearch)).getResults();

            if (showResultInfo && (results.length == 1)) {
                MessageDialog.openInformation(this.getSite().getShell(), "Info", "Sorry, no result. ");
                return new LineItem[0];
            }
            if (results.length == 1)
                return new LineItem[0];
            int totalSize = 0;
            List<LineItem> ress = new ArrayList<LineItem>();
            for (int i = 0; i < results.length; i++) {
                if (i == 0) {
                    totalSize = Integer.parseInt(Util.parse(results[i].getWsItemPK().getConceptName()).getDocumentElement()
                            .getTextContent());
                    continue;
                }
                ress.add(new LineItem(results[i].getDate(), results[i].getWsItemPK().getConceptName(), results[i].getWsItemPK()
                        .getIds(), results[i].getTaskId()));
            }
            pageToolBar.setTotalsize(totalSize);
            pageToolBar.refreshUI();
            return (LineItem[]) ress.toArray(new LineItem[ress.size()]);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if ((e.getLocalizedMessage() != null) && e.getLocalizedMessage().contains("10000"))//$NON-NLS-1$
                MessageDialog.openError(this.getSite().getShell(), "Too Many Results",
                        "More than 10000 results returned by the search. \nPlease narrow your search.");
            else
                MessageDialog.openError(this.getSite().getShell(), "Unable to perform the search", e.getLocalizedMessage());
            return null;
        } finally {
            try {
                this.getSite().getShell().setCursor(null);
                waitCursor.dispose();
            } catch (Exception e) {
            }
        }

    }

    /*********************************
     * IXObjectModelListener interface
     */
    public void handleEvent(int type, TreeObject parent, TreeObject child) {
        refreshData();
    }

    class EditItemAction extends Action {

        protected Shell shell = null;

        protected Viewer viewer;

        public EditItemAction(Shell shell, Viewer viewer) {
            super();
            this.shell = shell;
            this.viewer = viewer;
            setImageDescriptor(ImageCache.getImage("icons/edit_obj.gif"));//$NON-NLS-1$
            setText("Edit Record");
            setToolTipText("View as a DOM Tree or edit the XML source");
        }

        public void run() {
            try {
                super.run();
                final XtentisPort port = Util.getPort(getXObject());
                IStructuredSelection selection = ((IStructuredSelection) viewer.getSelection());
                LineItem li = (LineItem) selection.getFirstElement();
                if (li == null)
                    return;
                final WSItem wsItem = port.getItem(new WSGetItem(new WSItemPK((WSDataClusterPK) getXObject().getWsKey(), li
                        .getConcept().trim(), li.getIds())));
                String xml = wsItem.getContent();

                WSDataModelPK[] dmPKs = port.getDataModelPKs(new WSRegexDataModelPKs("*")).getWsDataModelPKs();//$NON-NLS-1$
                ArrayList<String> dataModels = new ArrayList<String>();
                if (dmPKs != null) {
                    for (int i = 0; i < dmPKs.length; i++) {
                        if (!"XMLSCHEMA---".equals(dmPKs[i].getPk()))//$NON-NLS-1$
                            dataModels.add(dmPKs[i].getPk());
                    }
                }

                final DOMViewDialog d = new DOMViewDialog(DataClusterBrowserMainPage.this.getSite().getShell(), Util.parse(xml),
                        true, dataModels, DOMViewDialog.TREE_VIEWER, wsItem.getDataModelName());
                d.addListener(new Listener() {

                    public void handleEvent(Event event) {
                        if (event.button == DOMViewDialog.BUTTON_SAVE) {
                            // attempt to save
                            try {
                                // check the item is modified by others?
                                boolean isModified = port.isItemModifiedByOther(new WSIsItemModifiedByOther(wsItem)).is_true();
                                if (isModified) {
                                    if (MessageDialog
                                            .openConfirm(
                                                    null,
                                                    "Confirm",
                                                    "This object was also modified by somebody else. If you save now, you will overwrite his or her changes. Are you sure you want to do that?")) {
                                        WSPutItemWithReport item = new WSPutItemWithReport(new WSPutItem(
                                                (WSDataClusterPK) getXObject().getWsKey(), d.getXML(), "".equals(d //$NON-NLS-1$
                                                        .getDataModelName()) ? null : new WSDataModelPK(d.getDataModelName()),
                                                false), "genericUI", d.isTriggerProcess());//$NON-NLS-1$
                                        Util.getPort(getXObject()).putItemWithReport(item);
                                    }
                                } else {
                                    WSPutItemWithReport item = new WSPutItemWithReport(
                                            new WSPutItem((WSDataClusterPK) getXObject().getWsKey(), d.getXML(), "".equals(d //$NON-NLS-1$
                                                    .getDataModelName()) ? null : new WSDataModelPK(d.getDataModelName()), false),
                                            "genericUI", d.isTriggerProcess());//$NON-NLS-1$
                                    Util.getPort(getXObject()).putItemWithReport(item);
                                }
                                // previousDataModel = d.getDataModelName();
                            } catch (Exception e) {
                                MessageDialog.openError(shell, "Error saving the Record",
                                        "An error occured trying save the Record:\n\n " + e.getLocalizedMessage());
                                return;
                            }
                        }// if
                        d.close();
                    }// handleEvent
                });

                d.setBlockOnOpen(true);
                d.open();

            } catch (Exception e) {
                log.error(e.getMessage(), e);
                MessageDialog.openError(shell, "Error",
                        "An error occured trying to view the result as a DOM tree: " + e.getLocalizedMessage());
            }
        }

        @Override
        public void runWithEvent(Event event) {
            super.runWithEvent(event);
        }

    }

    class EditTaskIdAction extends Action {

        protected Shell shell = null;

        protected Viewer viewer;

        public EditTaskIdAction(Shell shell, Viewer viewer) {
            super();
            this.shell = shell;
            this.viewer = viewer;
            setImageDescriptor(ImageCache.getImage("icons/default.gif"));//$NON-NLS-1$
            setText("Edit TaskId");
            setToolTipText("Edit taskId of the record");
        }

        public void run() {
            try {
                super.run();
                final XtentisPort port = Util.getPort(getXObject());
                IStructuredSelection selection = ((IStructuredSelection) viewer.getSelection());
                LineItem li = (LineItem) selection.getFirstElement();

                InputDialog input = new InputDialog(DataClusterBrowserMainPage.this.getSite().getShell(), "Edit TaskId",
                        "Please input the new taskid.", li.getTaskId(), null);
                input.setBlockOnOpen(true);

                if (input.open() == Window.OK) {
                    input.getValue();
                    li.setTaskId(input.getValue());
                    // @temp yguo, save the taskid to db.
                    WSUpdateMetadataItem wsUpdateMetadataItem = new WSUpdateMetadataItem();
                    WSItemPK wsItemPK = new WSItemPK();
                    wsItemPK.setWsDataClusterPK((WSDataClusterPK) getXObject().getWsKey());
                    wsItemPK.setConceptName(li.getConcept());
                    wsItemPK.setIds(li.getIds());
                    wsUpdateMetadataItem.setWsItemPK(wsItemPK);
                    wsUpdateMetadataItem.setTaskId(input.getValue());
                    port.updateItemMetadata(wsUpdateMetadataItem);
                    // @temp yguo, refresh the data
                    viewer.refresh();
                }
            } catch (Exception e) {
                MessageDialog.openError(shell, "Error saving the Record",
                        "An error occured trying save the Record:\n\n " + e.getLocalizedMessage());
                return;
            }
        }

    }

    /***************************************************************
     * Compare item with each other
     ***************************************************************/
    public class CompareItemWithEachOtherAction extends Action {

        protected Shell shell = null;

        protected Viewer viewer;

        public CompareItemWithEachOtherAction(Shell shell, Viewer viewer) {
            super();
            this.shell = shell;
            this.viewer = viewer;
            setImageDescriptor(ImageCache.getImage(EImage.SYNCH.getPath()));
            setText("Compare With Each Other");
            setToolTipText("Compare With Each Other");
        }

        @Override
        public void run() {

            try {
                super.run();

                IStructuredSelection selection = ((IStructuredSelection) viewer.getSelection());
                int selectSize = selection.size();
                if (selectSize != 2) {
                    MessageDialog.openWarning(null, "Warning", "Please select two Records to compare! ");
                    return;
                }

                List<LineItem> liList = selection.toList();

                LineItem leftLineItem = liList.get(0);
                LineItem rightLineItem = liList.get(1);

                // left
                WSItemPK leftWSItemPK = new WSItemPK((WSDataClusterPK) getXObject().getWsKey(), leftLineItem.getConcept().trim(),
                        leftLineItem.getIds());
                WSItem leftWSItem = Util.getPort(getXObject()).getItem(new WSGetItem(leftWSItemPK));
                String leftItemXmlContent = leftWSItem.getContent();

                // right
                WSItemPK rightWSItemPK = new WSItemPK((WSDataClusterPK) getXObject().getWsKey(), rightLineItem.getConcept()
                        .trim(), rightLineItem.getIds());
                WSItem rightWSItem = Util.getPort(getXObject()).getItem(new WSGetItem(rightWSItemPK));
                String rightItemXmlContent = rightWSItem.getContent();

                if (leftItemXmlContent != null && rightItemXmlContent != null) {
                    CompareHeadInfo compareHeadInfo = new CompareHeadInfo(getXObject());
                    compareHeadInfo.setItem(true);
                    compareHeadInfo.setDataModelName(leftWSItem.getDataModelName());
                    CompareManager.getInstance().compareTwoStream(leftItemXmlContent, rightItemXmlContent, true, compareHeadInfo,
                            leftWSItemPK.getConceptName() + "." + Util.joinStrings(leftWSItemPK.getIds(), "."),//$NON-NLS-1$//$NON-NLS-2$
                            rightWSItemPK.getConceptName() + "." + Util.joinStrings(rightWSItemPK.getIds(), "."), true, false);//$NON-NLS-1$//$NON-NLS-2$
                }

            } catch (Exception e) {
                log.error(e.getMessage(), e);
                MessageDialog.openError(shell, "Error",
                        "An error occured trying to compare items with each other: " + e.getLocalizedMessage());
            }
        }
    }

    /***************************************************************
     * Compare item with svn TODO 1.object compare 2. item/object save
     ***************************************************************/
    public class CompareItemWithLatestRevisionAction extends Action {

        protected Shell shell = null;

        protected Viewer viewer;

        public CompareItemWithLatestRevisionAction(Shell shell, Viewer viewer) {
            super();
            this.shell = shell;
            this.viewer = viewer;
            setImageDescriptor(ImageCache.getImage(EImage.SYNCH.getPath()));
            setText("Compare With Latest Revision");
            setToolTipText("Compare With Latest Revision");
        }

        @Override
        public void run() {
            try {
                super.run();

                IStructuredSelection selection = ((IStructuredSelection) viewer.getSelection());
                int selectSize = selection.size();
                if (selectSize == 0) {
                    MessageDialog.openWarning(null, "Warning", "Please select an item at least! ");
                    return;
                } else if (selectSize > 1) {
                    MessageDialog.openWarning(null, "Warning", "This operation can not be supported for muti-Record(s)! ");
                    return;
                }
                LineItem li = (LineItem) selection.getFirstElement();

                WSItem wsItem = Util.getPort(getXObject()).getItem(
                        new WSGetItem(
                                new WSItemPK((WSDataClusterPK) getXObject().getWsKey(), li.getConcept().trim(), li.getIds())));

                String xml = wsItem.getContent();
                WSString svnContent = null;
                try {
                    svnContent = Util.getPort(getXObject()).versioningGetItemContent(
                            new WSVersioningGetItemContent(ICoreConstants.DEFAULT_SVN, new WSItemPK(wsItem.getWsDataClusterPK(),
                                    wsItem.getConceptName(), wsItem.getIds()), "-1"));//$NON-NLS-1$
                } catch (Exception e) {
                    MessageDialog.openWarning(null, "Warning", e.getLocalizedMessage());
                    return;
                }

                String itemcontent = Util.getItemContent(svnContent.getValue());
                if (itemcontent != null) {
                    CompareHeadInfo compareHeadInfo = new CompareHeadInfo(getXObject());
                    compareHeadInfo.setItem(true);
                    compareHeadInfo.setDataModelName(wsItem.getDataModelName());
                    CompareManager.getInstance().compareTwoStream(xml, itemcontent, true, compareHeadInfo, "Local Content",
                            "Lastest Revision", true, false);
                }

            } catch (Exception e) {
                log.error(e.getMessage(), e);
                MessageDialog.openError(shell, "Error",
                        "An error occured trying to compare Record with svn: " + e.getLocalizedMessage());
            }
        }
    }

    /***************************************************************
     * Delete Items Action
     * 
     * @author bgrieder
     * 
     ***************************************************************/

    class LogicalDeleteItemsAction extends Action {

        protected Shell shell = null;

        protected Viewer viewer;

        public LogicalDeleteItemsAction(Shell shell, Viewer viewer) {
            super();
            this.shell = shell;
            this.viewer = viewer;
            setImageDescriptor(ImageCache.getImage("icons/delete_obj.gif"));//$NON-NLS-1$

            IStructuredSelection selection = ((IStructuredSelection) viewer.getSelection());
            if (selection.size() == 1)
                setText("Logically delete the selected Record");
            else
                setText("Logically delete these " + selection.size() + " Records");

            setToolTipText("Logically delete the Selected Record" + (selection.size() > 1 ? "s" : ""));//$NON-NLS-1$//$NON-NLS-2$
        }

        @Override
        public void run() {
            try {
                super.run();

                // retrieve the list of items
                IStructuredSelection selection = ((IStructuredSelection) viewer.getSelection());
                List<LineItem> lineItems = selection.toList();

                if (lineItems.size() == 0)
                    return;

                InputDialog id = new InputDialog(this.shell, "Confirm Deletion", "Are you sure you want to send the selected "
                        + lineItems.size() + " Records to trash?\nSet Part-Path:", "/", new IInputValidator() {

                    public String isValid(String newText) {
                        if ((newText == null) || !newText.matches("^\\/.*$"))//$NON-NLS-1$
                            return "Illegal Part-Path";
                        return null;
                    };
                });

                id.setBlockOnOpen(true);
                int ret = id.open();
                if (ret == Dialog.CANCEL)
                    return;

                // Instantiate the Monitor with actual deletes
                LogicalDeleteItemsWithProgress diwp = new LogicalDeleteItemsWithProgress(getXObject(), lineItems, id.getValue(),
                        this.shell);
                // run
                new ProgressMonitorDialog(this.shell).run(false, // fork
                        true, // cancelable
                        diwp);
                // refresh the search
                DataClusterBrowserMainPage.this.resultsViewer.setInput(getResults(false));

            } catch (Exception e) {
                log.error(e.getMessage(), e);
                MessageDialog.openError(shell, "Error",
                        "An error occured trying to delete the Records: " + e.getLocalizedMessage());
            }
        }

        @Override
        public void runWithEvent(Event event) {
            super.runWithEvent(event);
        }

        // Progress Monitor that implements the actual delete
        class LogicalDeleteItemsWithProgress implements IRunnableWithProgress {

            TreeObject xObject;

            Collection<LineItem> lineItems;

            String partPath;

            Shell parentShell;

            public LogicalDeleteItemsWithProgress(TreeObject object, Collection<LineItem> lineItems, String partPath, Shell shell) {
                super();
                this.xObject = object;
                this.lineItems = lineItems;
                this.partPath = partPath;
                this.parentShell = shell;
            }

            public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                try {
                    monitor.beginTask("Deleting Records", lineItems.size());

                    XtentisPort port = Util.getPort(xObject);

                    int i = 0;
                    for (Iterator<LineItem> iter = lineItems.iterator(); iter.hasNext();) {
                        LineItem lineItem = iter.next();
                        String itemID = ((WSDataClusterPK) xObject.getWsKey()).getPk() + "." + lineItem.getConcept() + "."//$NON-NLS-1$//$NON-NLS-2$
                                + Util.joinStrings(lineItem.getIds(), ".");//$NON-NLS-1$
                        monitor.subTask("Processing Record " + (i++) + ": " + itemID);
                        if (monitor.isCanceled()) {
                            MessageDialog.openWarning(this.parentShell, "User canceled the logically delete",
                                    "The logical-deletes was canceled by the user on Record " + i + "\n"
                                            + "Some Records may have not been logically deleted");
                            return;
                        }
                        WSItemPK itempk = new WSItemPK((WSDataClusterPK) xObject.getWsKey(), lineItem.getConcept(),
                                lineItem.getIds());
                        port.deleteItemWithReport(new WSDeleteItemWithReport(itempk,
                                "genericUI", "LOGIC_DELETE", partPath, getXObject().getUsername(), false, true, false));//$NON-NLS-1$ //$NON-NLS-2$

                        // port.dropItem(new WSDropItem(new WSItemPK((WSDataClusterPK) xObject.getWsKey(),
                        // lineItem.getConcept(),
                        // lineItem.getIds()), partPath));
                        monitor.worked(1);
                    }// for

                    monitor.done();
                } catch (Exception e) {
                    MessageDialog.openError(shell, "Error logically Deleting",
                            "An error occured trying to logically delete the Records:\n\n" + e.getLocalizedMessage());
                }// try

            }// run
        }// class DeleteItemsWithProgress

    }// class DeletItemsAction

    class PhysicalDeleteItemsAction extends Action {

        protected Shell shell = null;

        protected Viewer viewer;

        public PhysicalDeleteItemsAction(Shell shell, Viewer viewer) {
            super();
            this.shell = shell;
            this.viewer = viewer;
            setImageDescriptor(ImageCache.getImage("icons/delete_obj.gif"));//$NON-NLS-1$
            IStructuredSelection selection = ((IStructuredSelection) viewer.getSelection());
            if (selection.size() == 1)
                setText("Physically delete the selected Record");
            else
                setText("Physically delete these " + selection.size() + " Records");

            setToolTipText("Physically delete the selected Record" + (selection.size() > 1 ? "s" : ""));//$NON-NLS-1$//$NON-NLS-2$
        }

        @Override
        public void run() {
            try {
                super.run();

                // retrieve the list of items
                IStructuredSelection selection = ((IStructuredSelection) viewer.getSelection());
                List<LineItem> lineItems = selection.toList();

                if (lineItems.size() == 0)
                    return;

                if (!MessageDialog.openConfirm(this.shell, "Confirm Deletion", "Are you sure you want to delete the selected "
                        + lineItems.size() + " Records?"))
                    return;

                // Instantiate the Monitor with actual deletes
                PhysicalDeleteItemsWithProgress diwp = new PhysicalDeleteItemsWithProgress(getXObject(), lineItems, this.shell);
                // run
                new ProgressMonitorDialog(this.shell).run(false, // fork
                        true, // cancelable
                        diwp);
                // refresh the search
                DataClusterBrowserMainPage.this.resultsViewer.setInput(getResults(false));

            } catch (Exception e) {
                log.error(e.getMessage(), e);
                MessageDialog.openError(shell, "Error",
                        "An error occured trying to delete the Records: " + e.getLocalizedMessage());
            }
        }

        @Override
        public void runWithEvent(Event event) {
            super.runWithEvent(event);
        }

        // Progress Monitor that implements the actual delete
        class PhysicalDeleteItemsWithProgress implements IRunnableWithProgress {

            TreeObject xObject;

            Collection<LineItem> lineItems;

            Shell parentShell;

            public PhysicalDeleteItemsWithProgress(TreeObject object, Collection<LineItem> lineItems, Shell shell) {
                super();
                this.xObject = object;
                this.lineItems = lineItems;
                this.parentShell = shell;
            }

            public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                try {
                    monitor.beginTask("Deleting Records", lineItems.size());

                    XtentisPort port = Util.getPort(getXObject());

                    int i = 0;
                    for (Iterator<LineItem> iter = lineItems.iterator(); iter.hasNext();) {
                        LineItem lineItem = iter.next();
                        String itemID = ((WSDataClusterPK) getXObject().getWsKey()).getPk() + "." + lineItem.getConcept() + "."//$NON-NLS-1$//$NON-NLS-2$
                                + Util.joinStrings(lineItem.getIds(), ".");//$NON-NLS-1$
                        monitor.subTask("Processing Record " + (i++) + ": " + itemID);
                        if (monitor.isCanceled()) {
                            MessageDialog.openWarning(this.parentShell, "User Canceled the delete",
                                    "The deletes werz canceled by the user on Record " + i + "\n"
                                            + "Some Records may have not been deleted");
                            return;
                        }
                        WSItemPK itempk = new WSItemPK((WSDataClusterPK) getXObject().getWsKey(), lineItem.getConcept(),
                                lineItem.getIds());
                        port.deleteItemWithReport(new WSDeleteItemWithReport(itempk,
                                "genericUI", "PHYSICAL_DELETE", null, getXObject().getUsername(), false, true, false));//$NON-NLS-1$ //$NON-NLS-2$
                        monitor.worked(1);
                    }// for

                    monitor.done();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    MessageDialog.openError(shell, "Error Deleting",
                            "An error occured trying to delete the Records:\n\n " + e.getLocalizedMessage());
                }// try

            }// run
        }// class DeleteItemsWithProgress

    }// class DeletItemsAction

    /***************************************************************
     * New Item Action
     * 
     * @author bgrieder
     * 
     ***************************************************************/
    class NewItemAction extends Action {

        protected Shell shell = null;

        protected Viewer viewer;

        public NewItemAction(Shell shell, Viewer viewer) {
            super();
            this.shell = shell;
            this.viewer = viewer;
            setImageDescriptor(ImageCache.getImage("icons/add_obj.gif")); //$NON-NLS-1$
            setText("New Record");
            setToolTipText("Add a new Record");
        }

        @Override
        public void run() {
            try {
                super.run();

                String xml = "<NewItem><NewElement></NewElement></NewItem>"; //$NON-NLS-1$

                WSDataModelPK[] dmPKs = Util.getPort(getXObject()).getDataModelPKs(new WSRegexDataModelPKs("*")) //$NON-NLS-1$
                        .getWsDataModelPKs();
                ArrayList<String> dataModels = new ArrayList<String>();
                if (dmPKs != null) {
                    for (int i = 0; i < dmPKs.length; i++) {
                        if (!"XMLSCHEMA---".equals(dmPKs[i].getPk())) //$NON-NLS-1$
                            dataModels.add(dmPKs[i].getPk());
                    }
                }

                final DOMViewDialog d = new DOMViewDialog(DataClusterBrowserMainPage.this.getSite().getShell(), Util.parse(xml),
                        true, dataModels, DOMViewDialog.SOURCE_VIEWER, null);
                d.addListener(new Listener() {

                    public void handleEvent(Event event) {
                        if (event.button == DOMViewDialog.BUTTON_SAVE) {
                            // attempt to save
                            try {
                                final XtentisPort port = Util.getPort(getXObject());
                                WSPutItemWithReport item = new WSPutItemWithReport(new WSPutItem((WSDataClusterPK) getXObject()
                                        .getWsKey(), d.getXML(), "".equals(d //$NON-NLS-1$
                                        .getDataModelName()) ? null : new WSDataModelPK(d.getDataModelName()), false),
                                        "genericUI", d.isTriggerProcess());//$NON-NLS-1$
                                port.putItemWithReport(item);
                            } catch (Exception e) {
                                MessageDialog.openError(shell, "Error saving the Record",
                                        "An error occured trying save the Record:\n\n " + e.getLocalizedMessage());
                                return;
                            }
                        }// if
                        d.close();
                    }// handleEvent
                });

                d.setBlockOnOpen(true);
                d.open();
                DataClusterBrowserMainPage.this.doSearch();

            } catch (Exception e) {
                log.error(e.getMessage(), e);
                MessageDialog.openError(shell, "Error",
                        "An error occured trying to view the result as a DOM tree: " + e.getLocalizedMessage());
            }
        }

        @Override
        public void runWithEvent(Event event) {
            super.runWithEvent(event);
        }

    }

    /***************************************************************
     * SubmitItems Action
     * 
     * @author bgrieder
     * 
     ***************************************************************/
    class SubmitItemsAction extends Action {

        protected Shell shell = null;

        protected Viewer viewer;

        public SubmitItemsAction(Shell shell, Viewer viewer) {
            super();
            this.shell = shell;
            this.viewer = viewer;
            setImageDescriptor(ImageCache.getImage("icons/execute.gif"));//$NON-NLS-1$
            IStructuredSelection selection = ((IStructuredSelection) viewer.getSelection());
            if (selection.size() == 1)
                setText("Route the selected Record");
            else
                setText("Route these " + selection.size() + " Records");
            setToolTipText("Route the Selected Record" + (selection.size() > 1 ? "s" : ""));
        }

        @Override
        public void run() {
            try {
                super.run();

                // retrieve the list of items
                IStructuredSelection selection = ((IStructuredSelection) viewer.getSelection());
                List<LineItem> lineItems = selection.toList();

                if (lineItems.size() == 0)
                    return;

                if (!MessageDialog.openConfirm(this.shell, "Confirm Deletion", "Are you sure you want to route the selected "
                        + (lineItems.size() > 1 ? lineItems.size() + " " : "") + "Record(s) using the Event Manager?"))
                    return;

                // Instantiate the Monitor with actual deletes
                SubmitItemsWithProgress diwp = new SubmitItemsWithProgress(getXObject(), lineItems, this.shell);
                // run
                new ProgressMonitorDialog(this.shell).run(false, // fork
                        true, // cancelable
                        diwp);

            } catch (Exception e) {
                log.error(e.getMessage(), e);
                MessageDialog.openError(shell, "Error", "An error occured trying route the Records: " + e.getLocalizedMessage());
            }
        }

        @Override
        public void runWithEvent(Event event) {
            super.runWithEvent(event);
        }

        // Progress Monitor that implements the actual delete
        class SubmitItemsWithProgress implements IRunnableWithProgress {

            TreeObject xObject;

            Collection<LineItem> lineItems;

            Shell parentShell;

            public SubmitItemsWithProgress(TreeObject object, Collection<LineItem> lineItems, Shell shell) {
                super();
                this.xObject = object;
                this.lineItems = lineItems;
                this.parentShell = shell;
            }

            public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                monitor.beginTask("Deleting Records", lineItems.size());
                XtentisPort port = null;
                try {
                    port = Util.getPort(getXObject());
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    MessageDialog.openError(shell, "Error Routing",
                            "An error occured trying to route the Records:\n\n " + e.getLocalizedMessage());
                }// try

                int i = 0;
                for (Iterator<LineItem> iter = lineItems.iterator(); iter.hasNext();) {
                    LineItem lineItem = iter.next();
                    String itemID = ((WSDataClusterPK) getXObject().getWsKey()).getPk() + "." + lineItem.getConcept() + "." //$NON-NLS-1$  //$NON-NLS-2$
                            + Util.joinStrings(lineItem.getIds(), ".");
                    monitor.subTask("Processing Record " + (i++) + " - " + itemID);
                    if (monitor.isCanceled()) {
                        MessageDialog.openWarning(this.parentShell, "User Canceled the Routing",
                                "The submissions were canceled by the user on Record " + i + "\n"
                                        + "Some Records may have not been routed");
                        return;
                    }
                    try {
                        port.routeItemV2(new WSRouteItemV2(new WSItemPK((WSDataClusterPK) getXObject().getWsKey(), lineItem
                                .getConcept(), lineItem.getIds())));
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                        MessageDialog.openError(shell, "Error Routing", "An error occured trying to route the item '" + itemID
                                + "':\n\n " + e.getLocalizedMessage());
                    }// try
                    monitor.worked(1);
                }// for

                monitor.done();

            }// run
        }// class DeleteItemsWithProgress

    }// class DeletItemsAction

    /***************************************************************
     * Table Label Provider
     * 
     * @author bgrieder
     * 
     ***************************************************************/
    class ClusterTableLabelProvider implements ITableLabelProvider {

        public Image getColumnImage(Object element, int columnIndex) {
            return null;
        }

        public String getColumnText(Object element, int columnIndex) {
            LineItem li = (LineItem) element;
            switch (columnIndex) {
            case 0:
                return sdf.format(new Date(li.getTime()));
            case 1:
                return li.getConcept();
            case 2:
                return Util.joinStrings(li.getIds(), "."); //$NON-NLS-1$
            case 3:
                return li.getTaskId();
            default:
                return "???????"; //$NON-NLS-1$
            }
        }

        public void addListener(ILabelProviderListener listener) {
        }

        public void dispose() {
        }

        public boolean isLabelProperty(Object element, String property) {
            return false;
        }

        public void removeListener(ILabelProviderListener listener) {
        }

    }

    /***************************************************************
     * Table Sorter
     * 
     * @author bgrieder
     * 
     ***************************************************************/
    class TableSorter extends ViewerSorter {

        int column = 0;

        boolean asc = true;

        public TableSorter(int column, boolean ascending) {
            super();
            this.column = column;
            this.asc = ascending;
        }

        @Override
        public int compare(Viewer viewer, Object e1, Object e2) {
            LineItem li1 = (LineItem) e1;
            LineItem li2 = (LineItem) e2;

            int res = 0;

            switch (column) {
            case 0:
                res = (int) (li1.getTime() - li2.getTime());
                break;
            case 1:
                res = li1.getConcept().compareToIgnoreCase(li2.getConcept());
                break;
            case 2:
                res = Util.joinStrings(li1.getIds(), ".").compareToIgnoreCase(Util.joinStrings(li2.getIds(), ".")); //$NON-NLS-1$
                break;
            default:
                res = 0;
            }
            if (asc)
                return res;
            else
                return -res;
        }

    }

    // Modified by hbhong,to fix bug 21784
    @Override
    public Object getAdapter(Class adapter) {
        if (adapter == TreeParent.class) {
            return Util.getServerTreeParent(getXObject());
        }
        return super.getAdapter(adapter);
    }
    // The ending| bug:21784
}
