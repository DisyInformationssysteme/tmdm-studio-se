package com.amalto.workbench.widgets.composites;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.amalto.workbench.dialogs.datamodel.SelectXPathDialog;
import com.amalto.workbench.image.EImage;
import com.amalto.workbench.image.ImageCache;
import com.amalto.workbench.models.infoextractor.IAllDataModelHolder;

public class SimpleXPathComposite extends Composite {

    private Text txtXPath;

    private Button btnSelectXPath;

    private Label lblTitle;

    private String defaultDataModelForSelect = "";

    private IAllDataModelHolder allDataModelHolder;

    public static final String DEFAULTTITLE = "Enter an xPath for the Foreign Key - Leave BLANK to delete the Foreign Key";

    public SimpleXPathComposite(Composite parent, int style, String title, IAllDataModelHolder allDataModelHolder,
            String defaultDataModelForSelect) {
        super(parent, style);

        this.defaultDataModelForSelect = defaultDataModelForSelect;
        this.allDataModelHolder = allDataModelHolder;

        final GridLayout gridLayout = new GridLayout();
        setLayout(gridLayout);

        if (title != null && !"".trim().equals(title)) {
            lblTitle = new Label(this, SWT.NONE);
            final GridData gd_lblTitle = new GridData(SWT.FILL, SWT.CENTER, true, false);
            lblTitle.setLayoutData(gd_lblTitle);
            lblTitle.setText(title);
        }

        final Composite composite = new Composite(this, SWT.NONE);
        composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        final GridLayout gridLayout_1 = new GridLayout();
        gridLayout_1.numColumns = 2;
        composite.setLayout(gridLayout_1);

        txtXPath = new Text(composite, SWT.BORDER);
        final GridData gd_txtXPath = new GridData(SWT.FILL, SWT.CENTER, true, false);
        txtXPath.setLayoutData(gd_txtXPath);

        btnSelectXPath = new Button(composite, SWT.NONE);
        btnSelectXPath.setImage(ImageCache.getCreatedImage(EImage.DOTS_BUTTON.getPath()));

        initUIListeners();
    }

    public String getXPath() {
        return txtXPath.getText().trim().replaceAll("'|\"", "");
    }

    public void setXPath(String xpath) {
        txtXPath.setText(xpath);
    }

    public void setDefaultDataModelForSelect(String defaultDataModelForSelect) {
        this.defaultDataModelForSelect = defaultDataModelForSelect;
    }

    public String getDefaultDataModelForSelect() {
        return defaultDataModelForSelect;
    }

    private void initUIListeners() {

        btnSelectXPath.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {

                SelectXPathDialog dialog = new SelectXPathDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                        allDataModelHolder, defaultDataModelForSelect);

                if (dialog.open() != Window.OK)
                    return;

                txtXPath.setText(dialog.getSelectedXPath());
            }
        });
    }

}
