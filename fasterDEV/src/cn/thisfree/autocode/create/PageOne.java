package cn.thisfree.autocode.create;

import java.util.ArrayList;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import cn.thisfree.autocode.datasource.DataSourceDailog;
import cn.thisfree.autocode.model.DataSource;
import cn.thisfree.autocode.model.ProjectPropeties;
import cn.thisfree.autocode.model.ProjectUtils;
import cn.thisfree.autocode.util.XmlObjectUtils;

public class PageOne extends WizardPage {
	
	private java.util.List dataSourceList = new ArrayList();
	
	
	private List connList = null;
	
	private DataSource ds;
	private Text nameText;
	private Text outpathText;
	private Combo frameworkCombo;
	private Combo datasourceCombo;
	private Button datasourceBtn;
	
	private ProjectPropeties project;
	
	/**
	 * Create the wizard.
	 */
	public PageOne() {
		super("PageOne");
		setTitle("工程生成向导");
//		setDescription("请选择数据库连接,模板文件位于："+XmlObjectCrud.getFtlPath());
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		project=new ProjectPropeties();
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayoutData(new RowData(571, 319));
		
		Label name = new Label(composite, SWT.NONE);
		name.setBounds(41, 13, 64, 17);
		name.setText("工程名：");
		
		nameText = new Text(composite, SWT.BORDER);
		nameText.setBounds(114, 10, 351, 28);
		nameText.setText("thisfree");
		
		Label outpath = new Label(composite, SWT.NONE|SWT.READ_ONLY);
		outpath.setBounds(41, 73, 64, 17);
		outpath.setText("输出路径：");
		
		outpathText = new Text(composite, SWT.BORDER);
		outpathText.setBounds(114, 70, 351, 28);
		
		Button button = new Button(composite, SWT.NONE);
		button.setBounds(471, 67, 65, 28);
		button.setText("选择");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.OPEN);
				dialog.setFilterPath("");// 设置默认的路径 //$NON-NLS-1$
				dialog.setText("选择代码生成路径");//设置对话框的标题 //$NON-NLS-1$
				dialog.setFilterPath("E:/work/rcp_workspace/"); //$NON-NLS-1$
				dialog.setMessage("请选择相应的文件夹"); //$NON-NLS-1$
				String srcPath = dialog.open();
				if(srcPath!=null){
					outpathText.setText(srcPath);
				}
			}
		});
		
		Label framework = new Label(composite, SWT.NONE);
		framework.setBounds(41, 133, 64, 17);
		framework.setText("框架选择：");
		
		frameworkCombo = new Combo(composite, SWT.DROP_DOWN|SWT.READ_ONLY);
		frameworkCombo.setBounds(114, 130, 351, 25);
		frameworkCombo.setItems(ProjectUtils.getModelList());
		
		Label datasource = new Label(composite, SWT.NONE);
		datasource.setBounds(41, 190, 64, 17);
		datasource.setText("数据源：");
		
		datasourceCombo = new Combo(composite, SWT.DROP_DOWN|SWT.READ_ONLY);
		datasourceCombo.setBounds(114, 187, 351, 25);
		
		datasourceBtn = new Button(composite, SWT.NONE);
		datasourceBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				DataSourceDailog dialog=new DataSourceDailog(getShell());
				dialog.open();
				loadDataSourceList(datasourceCombo);
			}
		});
		datasourceBtn.setBounds(471, 185, 65, 27);
		datasourceBtn.setText("新建");
		
		Label other = new Label(composite, SWT.NONE);
		other.setBounds(41, 234, 64, 17);
		other.setText("其他操作：");
		
		Button initdataBase = new Button(composite, SWT.CHECK);
		initdataBase.setBounds(114, 234, 98, 17);
		initdataBase.setText("初始化数据库");
		
		loadDataSourceList(datasourceCombo);
		bindValues();
	}
	
	private void loadDataSourceList(Combo combo){
		java.util.List tmp = (java.util.List)XmlObjectUtils.objectXmlDecoder(XmlObjectUtils.getDataSourceFileName());
		if(tmp != null){
			dataSourceList = tmp;
		}		
		
		String[] connItems = new String[dataSourceList.size()];
		for (int i = 0; i < dataSourceList.size(); i++) {
			DataSource ds = (DataSource) dataSourceList.get(i);
			connItems[i] = ds.getName();
		}
		
		combo.setItems(connItems);
	}
	
	@Override
	public IWizardPage getNextPage() {
		return super.getNextPage();
	}

	@Override
	public boolean canFlipToNextPage() {
		if (getErrorMessage() != null) return false;
		return false;
	}

	public DataSource getDs() {
		return ds;
	}
	
	private void bindValues() {  
        // The DataBindingContext object will manage the databindings  
        DataBindingContext bindingContext = new DataBindingContext();  
        IObservableValue uiElement;  
        IObservableValue modelElement;  
        // Lets bind it  
        uiElement = SWTObservables.observeText(nameText, SWT.Modify);  
        modelElement = BeansObservables.observeValue(project, "name");  
        // The bindValue method call binds the text element with the model  
        bindingContext.bindValue(uiElement, modelElement, null, null);  
  
        uiElement = SWTObservables.observeText(outpathText, SWT.Modify);  
        modelElement = BeansObservables.observeValue(project, "outpath");  
        // Remember the binding so that we can listen to validator problems  
        // See below for usage  
        bindingContext.bindValue(uiElement, modelElement, null, null);  
  
        uiElement = SWTObservables.observeSelection(frameworkCombo);  
        modelElement = BeansObservables.observeValue(project, "framework");  
        bindingContext.bindValue(uiElement, modelElement, null, null);  
  
        uiElement = SWTObservables.observeSelection(datasourceCombo);  
        modelElement = BeansObservables.observeValue(project, "datasource");  
        bindingContext.bindValue(uiElement, modelElement, null, null);  
        
        uiElement = SWTObservables.observeSelection(datasourceCombo);  
        modelElement = BeansObservables.observeValue(project, "isInitSql");  
        bindingContext.bindValue(uiElement, modelElement, null, null);  
    }

	public ProjectPropeties getProject() {
		return project;
	}

	public void setProject(ProjectPropeties project) {
		this.project = project;
	}  
}
