package cn.thisfree.autocode.report;

import java.util.ArrayList;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import cn.thisfree.autocode.model.ColumnModel;
import cn.thisfree.autocode.model.DataSource;
import cn.thisfree.autocode.model.ReportUtils;
import cn.thisfree.autocode.util.AutoCodeDbUtils;
import cn.thisfree.autocode.util.SQLFormatter;
import cn.thisfree.autocode.util.StringUtils;
import cn.thisfree.autocode.util.XmlObjectUtils;

import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.wb.swt.ResourceManager;

public class PageOne extends WizardPage {
	private DataBindingContext m_bindingContext;
	
	private java.util.List dataSourceList = new ArrayList();
	
	private List connList = null;
	
	private DataSource ds;
	
	private Text txtSql;
	private Text reportTitle;
	private Combo reportType;
	private Combo reportName;
	private Label previewLabel;
	private Combo dataSourceCombo;
	
	private void updateButtons(){
		getWizard().getContainer().updateButtons();
	}
	
	/**
	 * Create the wizard.
	 */
	public PageOne() {
		super("PageOne");
		setTitle("根据SQL生成报表");
		setDescription("选择数据源，输入可以运行的SQL语句");
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(final Composite parent) {
		final StartReportWizard wizard = (StartReportWizard)super.getWizard();
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new GridLayout(1, false));
		
//		SashForm sashForm = new SashForm(container, SWT.NONE);
		
		Composite composite = new Composite(container, SWT.BORDER);
		GridData gd_composite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite.widthHint = 715;
		gd_composite.heightHint = 58;
		composite.setLayoutData(gd_composite);
		composite.setLayout(new FormLayout());
		
		Label label = new Label(composite, SWT.NONE);
		FormData fd_label = new FormData();
		label.setLayoutData(fd_label);
		label.setText("数据源：");
		
		dataSourceCombo = new Combo(composite, SWT.NONE);
		fd_label.top = new FormAttachment(dataSourceCombo, 3, SWT.TOP);
		fd_label.right = new FormAttachment(dataSourceCombo, -6);
		FormData fd_combo = new FormData();
		fd_combo.top = new FormAttachment(0);
		fd_combo.right = new FormAttachment(100, -10);
		fd_combo.left = new FormAttachment(0, 383);
		dataSourceCombo.setLayoutData(fd_combo);
		dataSourceCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//更新表列表
				ds = (DataSource)dataSourceList.get(dataSourceCombo.getSelectionIndex());
				updateButtons();
			}
		});
		
		Label label_1 = new Label(composite, SWT.NONE);
		FormData fd_label_1 = new FormData();
		fd_label_1.top = new FormAttachment(0, 3);
		fd_label_1.left = new FormAttachment(0, 10);
		label_1.setLayoutData(fd_label_1);
		label_1.setText("报表显示名称：");
		
		reportTitle = new Text(composite, SWT.BORDER);
		fd_label.left = new FormAttachment(0, 334);
		FormData fd_text = new FormData();
		fd_text.right = new FormAttachment(label, -16);
		fd_text.left = new FormAttachment(label_1, 6);
		fd_text.top = new FormAttachment(0);
		reportTitle.setLayoutData(fd_text);
		
		Label label_2 = new Label(composite, SWT.NONE);
		FormData fd_label_2 = new FormData();
		fd_label_2.top = new FormAttachment(label_1, 8);
		fd_label_2.left = new FormAttachment(label_1, -60);
		fd_label_2.right = new FormAttachment(label_1, 0, SWT.RIGHT);
		label_2.setLayoutData(fd_label_2);
		label_2.setText("报表类型：");
		
		reportType = new Combo(composite, SWT.NONE);
		FormData fd_combo_1 = new FormData();
		fd_combo_1.left = new FormAttachment(reportTitle, 0, SWT.LEFT);
		fd_combo_1.top = new FormAttachment(label_1, 5);
		reportType.setLayoutData(fd_combo_1);
		
		Label label_3 = new Label(composite, SWT.NONE);
		FormData fd_label_3 = new FormData();
		fd_label_3.left = new FormAttachment(reportType);
		fd_label_3.bottom = new FormAttachment(100, -10);
		label_3.setLayoutData(fd_label_3);
		
		reportName = new Combo(composite, SWT.NONE);
		fd_combo_1.right = new FormAttachment(reportName, -6);
		FormData fd_combo_2 = new FormData();
		fd_combo_2.top = new FormAttachment(reportTitle, 3);
		fd_combo_2.right = new FormAttachment(100, -188);
		fd_combo_2.left = new FormAttachment(0, 212);
		reportName.setLayoutData(fd_combo_2);
		
		Composite composite_1 = new Composite(container, SWT.BORDER);
		GridData gd_composite_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite_1.heightHint = 145;
		gd_composite_1.widthHint = 714;
		composite_1.setLayoutData(gd_composite_1);
		composite_1.setLayout(new FormLayout());
		
		Label lblNewLabel_1 = new Label(composite_1, SWT.HORIZONTAL);
		FormData fd_lblNewLabel_1 = new FormData();
		fd_lblNewLabel_1.right = new FormAttachment(0, 478);
		fd_lblNewLabel_1.top = new FormAttachment(0, 5);
		fd_lblNewLabel_1.left = new FormAttachment(0, 5);
		lblNewLabel_1.setLayoutData(fd_lblNewLabel_1);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.BOLD));
		lblNewLabel_1.setText("输入SQL语句");
		
		txtSql = new Text(composite_1, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		FormData fd_txtSql = new FormData();
		fd_txtSql.bottom = new FormAttachment(0, 102);
		fd_txtSql.right = new FormAttachment(0, 714);
		fd_txtSql.top = new FormAttachment(0, 27);
		fd_txtSql.left = new FormAttachment(0, 5);
		txtSql.setLayoutData(fd_txtSql);
		txtSql.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateButtons();
			}
		});
		
		Button btnsql = new Button(composite_1, SWT.NONE);
		FormData fd_btnsql = new FormData();
		fd_btnsql.top = new FormAttachment(0, 107);
		fd_btnsql.left = new FormAttachment(0, 5);
		btnsql.setLayoutData(fd_btnsql);
		btnsql.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//格式化SQL
				String newsql = SQLFormatter.formatSql(txtSql.getText());
				txtSql.setText(newsql);
			}
		});
		btnsql.setText("格式化SQL");	
		
		Button checkSQL = new Button(composite_1, SWT.NONE);
		FormData fd_checkSQL = new FormData();
		fd_checkSQL.top = new FormAttachment(btnsql, 0, SWT.TOP);
		fd_checkSQL.left = new FormAttachment(btnsql, 6);
		checkSQL.setLayoutData(fd_checkSQL);
		checkSQL.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(getDs()==null){
					MessageDialog.openInformation(parent.getShell(), "错误信息", "请选择左侧数据源.");
					return;
				}
				java.util.List<ColumnModel> list = AutoCodeDbUtils.getColumnListBySql(getDs(), txtSql.getText());
				if(list==null || list.isEmpty()){
					MessageDialog.openError(parent.getShell(), "错误信息", "SQL校验失败，请确认语句是否正确。");
				}else{
					MessageDialog.openInformation(parent.getShell(), "成功", "SQL校验成功.");
				}
			}
		});
		checkSQL.setText("SQL语句校验");
		
		Composite preview = new Composite(container, SWT.NONE);
		
		previewLabel = new Label(preview, SWT.NONE);
		previewLabel.setImage(ResourceManager.getPluginImage("cn.thisfree.autocode", "icons/report.png"));
		previewLabel.setAlignment(SWT.CENTER);
		previewLabel.setBounds(0, 10, 715, 305);
		previewLabel.setText("报表预览");
		new Label(container, SWT.NONE);
		
		java.util.List tmp = (java.util.List)XmlObjectUtils.objectXmlDecoder(XmlObjectUtils.getDataSourceFileName());
		if(tmp != null){
			dataSourceList = tmp;
		}		
		
		String[] connItems = new String[dataSourceList.size()];
		for (int i = 0; i < dataSourceList.size(); i++) {
			DataSource ds = (DataSource) dataSourceList.get(i);
			connItems[i] = ds.getName();
		}
		//sashForm.setWeights(new int[] {206, 427});
		container.setSize(206, 800);
		loadDataSourceList(dataSourceCombo);
		m_bindingContext = initDataBindings();
		
		reportType.setItems(ReportUtils.getTypes());
		reportType.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent arg0) {
				String type=reportType.getText();
				wizard.getReportModel().setType(type);
				if(type!=null){
					reportName.setItems(ReportUtils.getNamesByType(type));
				}
			}
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		reportName.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent arg0) {
				String name=reportName.getText();
				wizard.getReportModel().setName(name);
				previewLabel.setImage(ResourceManager.getPluginImage("cn.thisfree.autocode", "image/"+name+".png"));
			}
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
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
		//设置表信息
		StartReportWizard wizard = (StartReportWizard)super.getWizard();
		//设置列信息
		PageThree pageThreeBAk = (PageThree)wizard.getPage("PageThree");
		pageThreeBAk.onEnterPage();
		return super.getNextPage();
	}

	@Override
	public boolean canFlipToNextPage() {
		if(ds!=null&&StringUtils.isNotBlank(txtSql.getText())){
			return true;
		}
		return false;
	}

	public DataSource getDs() {
		return ds;
	}
	
	protected DataBindingContext initDataBindings() {
		StartReportWizard wizard = (StartReportWizard)super.getWizard();
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue txtSqlObserveTextObserveWidget = SWTObservables.observeText(txtSql, SWT.Modify);
		IObservableValue sqlEmptyObserveValue = PojoObservables.observeValue(wizard.getReportModel(), "sql");
		bindingContext.bindValue(txtSqlObserveTextObserveWidget, sqlEmptyObserveValue, null, null);
		
		IObservableValue titleObserveTextObserveWidget = SWTObservables.observeText(reportTitle, SWT.Modify);
		IObservableValue titleEmptyObserveValue = PojoObservables.observeValue(wizard.getReportModel(), "title");
		bindingContext.bindValue(titleObserveTextObserveWidget, titleEmptyObserveValue, null, null);
		
//		IObservableValue typeObserveTextObserveWidget = SWTObservables.observeText(reportType.gett, SWT.Modify);
//		IObservableValue typeEmptyObserveValue = PojoObservables.observeValue(wizard.getReportModel(), "type");
//		bindingContext.bindValue(typeObserveTextObserveWidget, typeEmptyObserveValue, null, null);
//		
//		IObservableValue nameObserveTextObserveWidget = SWTObservables.observeText(reportName, SWT.Modify);
//		IObservableValue nameEmptyObserveValue = PojoObservables.observeValue(wizard.getReportModel(), "name");
//		bindingContext.bindValue(nameObserveTextObserveWidget, nameEmptyObserveValue, null, null);
		//
		return bindingContext;
	}
}
