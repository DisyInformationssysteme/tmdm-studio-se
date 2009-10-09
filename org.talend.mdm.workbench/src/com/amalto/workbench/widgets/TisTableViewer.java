package com.amalto.workbench.widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.amalto.workbench.dialogs.XpathSelectDialog;
import com.amalto.workbench.image.EImage;
import com.amalto.workbench.image.ImageCache;
import com.amalto.workbench.models.KeyValue;
import com.amalto.workbench.models.Line;
/**
 * 
 * @author achen
 *
 */
public class TisTableViewer extends ComplexTableViewer{
	protected Line copyLine;
	private Button copyButton;
	private Button pastButton;
	private boolean addMulti;// 'addAll' and 'deleteAll' button will be added if this field is not null
	
	

	public boolean isAddMulti() {
		return addMulti;
	}

	public void setAddMulti(boolean addMulti) {
		this.addMulti = addMulti;
	}

	private XpathSelectDialog xpathDialog;


	public TisTableViewer(List<ComplexTableViewerColumn> columns,
			FormToolkit toolkit, Composite parent) {
		super(columns, toolkit, parent);		
	}
	
	@Override
	protected void createViewer() {
			super.createTable();
			GridData gd=new GridData(SWT.FILL,SWT.FILL,true,true,1,1);
	        table.setLayoutData( gd );
	        gd.heightHint=80;
	       // Up Down Delete button group
	        Composite stepUpDownComposite = toolkit.createComposite(mainComposite,SWT.NONE);
	        stepUpDownComposite.setLayoutData(
	                new GridData(SWT.FILL,SWT.FILL,false,true,1,1)
	        );
	        GridLayout layout=new GridLayout(8,false);
	        layout.marginTop=0;
	        layout.marginBottom=0;
	        layout.marginHeight=0;	        
	        stepUpDownComposite.setLayout(layout);
	        //
	        addButton=toolkit.createButton(stepUpDownComposite,"",SWT.PUSH | SWT.CENTER);
	        addButton.setLayoutData(
	                new GridData(SWT.FILL,SWT.FILL,false,false,1,1)
	        );
	        addButton.setToolTipText("Add");
	        addButton.setImage(ImageCache.getCreatedImage(EImage.ADD_OBJ.getPath()));
	        addButton.addSelectionListener(new SelectionListener() {
	        	public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {};
	        	@SuppressWarnings("unchecked")
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

	        		//check uniqueness by concatenating all the values
	        		List<Line> list=(List<Line>)getViewer().getInput();
	        		//Update the model
	        		Line line =new Line(columns.toArray(new ComplexTableViewerColumn[columns.size()]),getInitValues());
	        		list.add(line);	        		
	        		//update the instances viewer
	        		viewer.setSelection(null);
	        		viewer.refresh();
	        		viewer.getTable().select(viewer.getTable().getItemCount()-1);
	        		markDirty();
	         	};
	        });		
	        //Add Multi
	        if(isAddMulti()){
		        Button addAllButton=toolkit.createButton(stepUpDownComposite,"",SWT.PUSH | SWT.CENTER);
		        addAllButton.setLayoutData(
		                new GridData(SWT.FILL,SWT.FILL,false,false,1,1)
		        );
		        addAllButton.setToolTipText("Add multiple");
		        addAllButton.setImage(ImageCache.getCreatedImage(EImage.ADDMULTI_OBJ.getPath()));
		        addAllButton.addSelectionListener(new SelectionListener() {
		        	public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {};
		        	@SuppressWarnings("unchecked")
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
		        		if(xpathDialog==null){
			        		xpathDialog = new XpathSelectDialog(
			        				table.getShell(),
			        				mainPage.getXObject().getParent(),"Select Multiple XPaths",
			        				mainPage.getSite(),
									true,
									null								
							);
		        		}
		        		xpathDialog.setBlockOnOpen(true);
		        		xpathDialog.open();
		        		
		        		if (xpathDialog.getReturnCode() == Window.OK)  {
		        			String[] xpaths=xpathDialog.getXpath().split("&");
		        			for(String xpath: xpaths){
			        		//check uniqueness by concatenating all the values
			        		List<Line> list=(List<Line>)getViewer().getInput();
			        		//Update the model
			        		Line line =new Line(columns.toArray(new ComplexTableViewerColumn[columns.size()]),getLineValues(xpath,0));
			        		list.add(line);	 
		        			}
			        		//update the instances viewer
			        		viewer.setSelection(null);
			        		viewer.refresh();
			        		viewer.getTable().select(viewer.getTable().getItemCount()-1);
		        			
			        		markDirty();
		        		}
		         	};
		        });			        	
	        }
	        deleteButton = toolkit.createButton(stepUpDownComposite,"",SWT.PUSH | SWT.CENTER);
	        deleteButton.setToolTipText("Delete the selected item");
	        deleteButton.setLayoutData(
	                new GridData(SWT.FILL,SWT.FILL,false,false,1,1)
	        );
	        deleteButton.addSelectionListener(new SelectionListener() {
	        	public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {};
	        	@SuppressWarnings("unchecked")
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
	        		int index =viewer.getTable().getSelectionIndex();
	        		if(index >=0 && index < viewer.getTable().getItemCount() ){
	        			List<Line> items=(List<Line>)viewer.getInput();
	        			items.remove(index);
	        			viewer.refresh();
	        			int pos= index-1;
	        			if(pos>=0) viewer.getTable().select(pos);
	        			markDirty();
	        		}
	        	};
	        });
	        deleteButton.setImage(ImageCache.getCreatedImage(EImage.DELETE_OBJ.getPath()));
	        //delete all
	        Button deleteAllButton = toolkit.createButton(stepUpDownComposite,"",SWT.PUSH | SWT.CENTER);
	        deleteAllButton.setToolTipText("Delete all items");
	        deleteAllButton.setLayoutData(
	                new GridData(SWT.FILL,SWT.FILL,false,false,1,1)
	        );
	        deleteAllButton.addSelectionListener(new SelectionListener() {
	        	public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {};
	        	@SuppressWarnings("unchecked")
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
	        			List<Line> items=(List<Line>)viewer.getInput();
	        			items.clear();
	        			viewer.refresh();
	        			markDirty();	        		
	        	};
	        });
	        deleteAllButton.setImage(ImageCache.getCreatedImage(EImage.PROGRESS_REMALL.getPath()));
	        
	        upButton = toolkit.createButton(stepUpDownComposite,"",SWT.PUSH | SWT.CENTER);
	        upButton.setToolTipText("Move up the selected item");
	        upButton.setImage(ImageCache.getCreatedImage(EImage.PREV_NAV.getPath()));
	        upButton.setLayoutData(
	                new GridData(SWT.FILL,SWT.FILL,false,false,1,1)
	        );
	        upButton.addSelectionListener(new SelectionListener() {
	        	public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {};
	        	@SuppressWarnings("unchecked")
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
	        		int index =viewer.getTable().getSelectionIndex();
	        		if(index >0 && index <viewer.getTable().getItemCount() ){
	        			//commit as we go
	        			if(mainPage!=null){
	        				mainPage.setComitting(true);
	        			}
	        			List<Line> items=(List<Line>)viewer.getInput();
	        			Line line= items.get(index);
	        			items.remove(index);
	        			items.add(index-1,line);
	        			
	        			viewer.refresh();
	        			
	        			if(mainPage!=null){
	        				mainPage.setComitting(false);
	        			}
		            	markDirty();       		
	        		}
	        	};
	        });
	        downButton = toolkit.createButton(stepUpDownComposite,"",SWT.PUSH | SWT.CENTER);
	        downButton.setToolTipText("Move down the selected item");
	        downButton.setImage(ImageCache.getCreatedImage(EImage.NEXT_NAV.getPath()));
	        downButton.setLayoutData(
	                new GridData(SWT.FILL,SWT.FILL,false,false,1,1)
	        );
	        downButton.addSelectionListener(new SelectionListener() {
	        	public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {};
	        	@SuppressWarnings("unchecked")
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
	        		int index =viewer.getTable().getSelectionIndex();
	        		if(index >=0 && index < viewer.getTable().getItemCount()-1 ){
	        			//commit as we go
	        			if(mainPage!=null){
	        				mainPage.setComitting(true);
	        			}
	        			List<Line> items=(List<Line>)viewer.getInput();
	        			Line line= items.get(index);
	        			items.remove(index);
	        			items.add(index+1,line);
	        			//viewer.setInput(items);
	        			viewer.refresh();
	        			//TODO
	        			if(mainPage!=null){
	        				mainPage.setComitting(false);
	        			}
		            	markDirty();       		
	        		}
	        	};
	        });

	        copyButton = toolkit.createButton(stepUpDownComposite,"",SWT.PUSH | SWT.CENTER);
	        copyButton.setToolTipText("Copy the selected item");
	        copyButton.setImage(ImageCache.getCreatedImage(EImage.COPY.getPath()));
	        copyButton.setLayoutData(
	                new GridData(SWT.FILL,SWT.FILL,false,false,1,1)
	        );
	        copyButton.addSelectionListener(new SelectionListener(){

				public void widgetDefaultSelected(SelectionEvent e) {					
					
				}
				public void widgetSelected(SelectionEvent e) {
	        		int index =viewer.getTable().getSelectionIndex();
	        		if(index >=0 && index <=viewer.getTable().getItemCount()-1 ){
	        			List<Line> items=(List<Line>)viewer.getInput();
	        			Line line= items.get(index);
	        			copyLine=line.clone();
	        			pastButton.setEnabled(copyLine!=null);
	        		}					
				}	        	
	        });
	        pastButton = toolkit.createButton(stepUpDownComposite,"",SWT.PUSH | SWT.CENTER);
	        pastButton.setToolTipText("Paste the selected item");
	        pastButton.setImage(ImageCache.getCreatedImage(EImage.PASTE.getPath()));
	        pastButton.setLayoutData(
	                new GridData(SWT.FILL,SWT.FILL,false,false,1,1)
	        );
	        pastButton.setEnabled(false);
	        pastButton.addSelectionListener(new SelectionListener() {
	        	public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {};
	        	@SuppressWarnings("unchecked")
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
	        			if(copyLine==null)return;
	        			if(mainPage!=null){
	        				mainPage.setComitting(true);
	        			}
	        			List<Line> items=(List<Line>)viewer.getInput();
	        			items.add(copyLine);
	        			//viewer.setInput(items);
	        			viewer.refresh();
	        			//TODO
	        			if(mainPage!=null){
	        				mainPage.setComitting(false);
	        			}
		            	markDirty();       			        		
	        	};
	        });
	        // Create the cell editors --> We actually discard those later: not natural for an user
	        CellEditor[] editors = new CellEditor[columns.size()];	        
	        for(int i=0; i< columns.size(); i++){
	        	if(columns.get(i).isText()){        		
	        		editors[i] = new TextCellEditor(table);        		
	        	}
	        	else if(columns.get(i).isCombo())
	        	{
	        		editors[i] = new ComboBoxCellEditor(table, ((ComplexTableViewerColumn)columns.get(i)).getComboValues(), SWT.READ_ONLY);	
	        	}else if(columns.get(i).isXPATH()){
	        		editors[i]= new XpathCellEditor(table);
	        	}	        
	        }
	        viewer.setCellEditors(editors);
	        
	        //set the content provider
	        viewer.setContentProvider(new IStructuredContentProvider() {
	        	public void dispose() {}
	        	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}
	        	@SuppressWarnings("unchecked")
				public Object[] getElements(Object inputElement) {
	        		ArrayList<Line> lines = (ArrayList<Line>)inputElement;
	        		return lines.toArray(new Line[lines.size()]);
	        	}
	        });
	        
	        //set the label provider
	        viewer.setLabelProvider(new ITableLabelProvider() {
	        	public boolean isLabelProperty(Object element, String property) {return false;}
	        	public void dispose() {}
	        	public void addListener(ILabelProviderListener listener) {}
	        	public void removeListener(ILabelProviderListener listener) {}
	        	public String getColumnText(Object element, int columnIndex) {
	        		Line line = (Line) element;
	        		if(columnIndex>=0 && columnIndex<columns.size()){
	        			for(KeyValue keyvalue:line.keyValues){
	        				if(keyvalue.key.equals(columns.get(columnIndex).getName())){
	        					String val = keyvalue.value; 
	        					if (columns.get(columnIndex).isNillable()) {
	        						if (columns.get(columnIndex).getNillValue().equals(val))
	        							val = columns.get(columnIndex).getNillDisplay();
	        					}
	        					return val;
	        				}
	        			}
	        		}
	        		return "";
	        	}
	        	public Image getColumnImage(Object element, int columnIndex) {return null;}
	        });

	        // Set the column properties
	        ArrayList<String> columnNames = new ArrayList<String>();
	        for (ComplexTableViewerColumn column : columns) {
				columnNames.add(column.getName());
			}
	        viewer.setColumnProperties(columnNames.toArray(new String[columnNames.size()]));
	        
	        //set the Cell Modifier
	        viewer.setCellModifier(new ICellModifier() {
	        	public boolean canModify(Object element, String property) {
	        		
	        		return editable;
	        	}

				@SuppressWarnings("unchecked")
				public void modify(Object element, String property, Object value)
	        	{
					if(value instanceof Integer){
						if(Integer.valueOf(value.toString())==-1) return;
					}
					// modify the text  and combo cell value 
					TableItem item = (TableItem) element;
					Line line = (Line) item.getData();
					int columnIndex = Arrays.asList(viewer.getColumnProperties())
							.indexOf(property);
					if(columnIndex>=0 && columnIndex < viewer.getColumnProperties().length ){
						if (isAColumnWithCombo(columnIndex)) {
							String[] attrs = columns.get(columnIndex).getComboValues();
							value = attrs[Integer.parseInt(value.toString())];
						}
						KeyValue kv = line.keyValues.get(columnIndex);
						boolean noChange = kv.value.equals(value.toString());
						kv.value =value.toString();
						viewer.refresh();
						if (!noChange)
						{
							markDirty();
						}
					}
	        	} 
	        	public Object getValue(Object element, String property) {
	        		int columnIndex = Arrays.asList(viewer.getColumnProperties())
							.indexOf(property);
					Line line = (Line) element;
					// add getting value from combo
					if (isAColumnWithCombo(columnIndex)) {
						String value = line.keyValues.get(columnIndex).value;
						String[] attrs = columns.get(columnIndex).getComboValues();
						return Arrays.asList(attrs).indexOf(value);
					}
					for (KeyValue keyvalue : line.keyValues) {
						if (property.equals(keyvalue.key)) {
							if (keyvalue.value.equals("")) {
								return columns.get(columnIndex)
										.getNillDisplay();
							}
							return keyvalue.value;
						}
					}
	        		return null;
	        	}
	        	
	        	private boolean isAColumnWithCombo(int columnIdx)
	        	{
	        		return columns.get(columnIdx).isCombo();
	        	}

	        });

	        	        
	        //display for Delete Key events to delete an instance
	        viewer.getTable().addKeyListener(new KeyListener() {
	        	public void keyPressed(KeyEvent e) {}
	        	@SuppressWarnings("unchecked")
				public void keyReleased(KeyEvent e) {
	        		if ((e.stateMask==0) && (e.character == SWT.DEL) && (viewer.getSelection()!=null)) {
	        			Line line = (Line)((IStructuredSelection)viewer.getSelection()).getFirstElement();
	        			//update the underlying role and refresh the table
	        			//update the underlying model
	        			List<Line> items=(List<Line>)viewer.getInput();
	        			items.remove(line);
	        			//refresh
	        			viewer.refresh();
	        			//mark for update
	        			markDirty();
	        		}
	        	}
	        });			
	}
	
	@Override
	public void create() {
		mainComposite= toolkit.createComposite(parent);
		mainComposite.setLayoutData(
                new GridData(SWT.FILL,SWT.RIGHT,true,true,1,1)
        );		
        GridLayout layout=new GridLayout(1,false);
        layout.marginTop=0;
        layout.marginBottom=0;
        layout.marginHeight=0;
		mainComposite.setLayout(layout);
		
		createViewer();
	}	
	
	protected String[] getLineValues(String fieldValue, int fieldIndex){
		List<String> values=new ArrayList<String>();
		for(int i=0; i<columns.size(); i++){
			ComplexTableViewerColumn column=columns.get(i);
			if(fieldIndex == i){
				values.add(fieldValue);
				continue;
			}
			String text = column.getNillValue();
			if(column.isCombo() && column.getComboValues().length>0){
				text= column.getComboValues()[0];
			}		
			values.add(text);
		}
		return values.toArray(new String[values.size()]);
	}	
}
