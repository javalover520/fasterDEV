package cn.thisfree.autocode.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.metamodel.schema.Column;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.wb.swt.ResourceManager;

import cn.thisfree.autocode.util.AutoCodeDbUtils;
import cn.thisfree.autocode.util.RuleUtils;
import cn.thisfree.autocode.util.StringUtils;
import cn.thisfree.faster.model.Rule;
import swing2swt.layout.BorderLayout;

public class PageThree extends WizardPage {
	private Table columnsTable;

	/**
	 * 列信息
	 */
	List<Column> columnList = new ArrayList<Column>();

	private TableViewer tableViewer;
	
	static String[] columnNames = new String[] {"columnName","columnType","length","columnComment","javaName", "javaType", "isPrimaryKey","isNotNull", "isQuery", "isList", "isEdit", "dictKey","ruleName","ruleDesc","ruleValue"};

	/**
	 * Create the wizard.
	 */
	public PageThree() {
		super("PageThree");
		setTitle("设置字段信息");
		setDescription("设置字段信息");
	}
	
	public TableViewer getTableViewer() {
		return tableViewer;
	}

	public List<Column> getColumnList() {
		return columnList;
	}

	/**
	 * Create contents of the wizard.
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new BorderLayout(3, 0));

		Composite composite = new Composite(container, SWT.BORDER);
		composite.setLayoutData(BorderLayout.EAST);
		composite.setLayout(new GridLayout(1, false));

		ToolBar toolBar = new ToolBar(composite, SWT.FLAT | SWT.RIGHT | SWT.VERTICAL);

		ToolItem tbiUp = new ToolItem(toolBar, SWT.NONE);
		tbiUp.setImage(ResourceManager.getPluginImage("cn.thisfree.autocode", "icons/up.png"));
		tbiUp.addListener(SWT.Selection, new Listener() {  
            public void handleEvent(Event event) {  
            	//columnList
            	int index=tableViewer.getTable().getSelectionIndex();
            	if(index==-1 || index==0){
            		return;
            	}
            	Column cur=columnList.get(index);
            	Column up=columnList.get(index-1);
            	columnList.set(index, up);
            	columnList.set(index-1, cur);
            	tableViewer.refresh();
            	updateTableColor();
            }  
        });   

		ToolItem tbiDown = new ToolItem(toolBar, SWT.NONE);
		tbiDown.setImage(ResourceManager.getPluginImage("cn.thisfree.autocode", "icons/down.png"));
		tbiDown.addListener(SWT.Selection, new Listener() {  
            public void handleEvent(Event event) {  
            	//columnList
            	int index=tableViewer.getTable().getSelectionIndex();
            	if(index==-1 || index==columnList.size()-1){
            		return;
            	}
            	Column cur=columnList.get(index);
            	Column down=columnList.get(index+1);
            	columnList.set(index, down);
            	columnList.set(index+1, cur);
            	tableViewer.refresh();
            	updateTableColor();
            }  
        });   

		Composite composite_1 = new Composite(container, SWT.BORDER);
		composite_1.setLayoutData(BorderLayout.CENTER);
		composite_1.setLayout(new GridLayout(1, false));

		tableViewer = new TableViewer(composite_1, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.setColumnProperties(columnNames);
		columnsTable = tableViewer.getTable();
		columnsTable.setHeaderVisible(true);
		columnsTable.setLinesVisible(true);
		columnsTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,1, 1));

		int i=0;
		TableColumn colName = new TableColumn(columnsTable, SWT.LEFT|SWT.COLOR_RED, i++);
		colName.setText("列名");
		colName.setWidth(120);
		TableColumn colType = new TableColumn(columnsTable, SWT.LEFT, i++);
		colType.setText("列类型");
		colType.setWidth(80);
		TableColumn columnLength = new TableColumn(columnsTable, SWT.LEFT, i++);
		columnLength.setText("长度"); //$NON-NLS-1$
		columnLength.setWidth(60);
		TableColumn colDesc = new TableColumn(columnsTable, SWT.LEFT, i++);
		colDesc.setText("说明"); //$NON-NLS-1$
		colDesc.setWidth(120);
		TableColumn javaName = new TableColumn(columnsTable, SWT.LEFT, i++);
		javaName.setText("属性名"); //$NON-NLS-1$
		javaName.setWidth(120);
		TableColumn javaType = new TableColumn(columnsTable, SWT.LEFT, i++);
		javaType.setText("java类型"); //$NON-NLS-1$
		javaType.setWidth(140);
		TableColumn primaryKey = new TableColumn(columnsTable, SWT.CENTER, i++);
		primaryKey.setText("主键"); //$NON-NLS-1$
		primaryKey.setWidth(70);
		TableColumn isNotNull = new TableColumn(columnsTable, SWT.CENTER, i++);
		isNotNull.setText("是否非空"); //$NON-NLS-1$
		isNotNull.setWidth(70);
		TableColumn showInList = new TableColumn(columnsTable, SWT.CENTER, i++);
		showInList.setText("列表显示"); //$NON-NLS-1$
		showInList.setWidth(70);
		TableColumn asQuery = new TableColumn(columnsTable, SWT.CENTER|SWT.COLOR_BLUE, i++);
		asQuery.setText("查询显示"); //$NON-NLS-1$
		asQuery.setWidth(70);
		TableColumn asModify = new TableColumn(columnsTable, SWT.CENTER, i++);
		asModify.setText("修改显示"); //$NON-NLS-1$
		asModify.setWidth(70);
		TableColumn param = new TableColumn(columnsTable, SWT.CENTER, i++);
		param.setText("参数"); //$NON-NLS-1$
		param.setWidth(50);
		TableColumn ruleName = new TableColumn(columnsTable, SWT.LEFT, i++);
		ruleName.setText("检验规则"); //$NON-NLS-1$
		ruleName.setWidth(90);
		TableColumn ruleDesc = new TableColumn(columnsTable, SWT.LEFT, i++);
		ruleDesc.setText("规则描述"); //$NON-NLS-1$
		ruleDesc.setWidth(150);
		TableColumn rulevalue = new TableColumn(columnsTable, SWT.LEFT, i++);
		rulevalue.setText("规则取值"); //$NON-NLS-1$
		rulevalue.setWidth(150);
		
		CellEditor[] editors = new CellEditor[columnNames.length];
		int j=0;
		editors[j++] = null;//new TextCellEditor(columnsTable, SWT.READ_ONLY);
		editors[j++] = null;//不允许修改
		editors[j++] = null;//不允许修改
		editors[j++] = new TextCellEditor(columnsTable);
		editors[j++] = new TextCellEditor(columnsTable);
		editors[j++] = new TextCellEditor(columnsTable);
		editors[j++] = new CheckboxCellEditor(columnsTable);
		editors[j++] = new CheckboxCellEditor(columnsTable);
		editors[j++] = new CheckboxCellEditor(columnsTable);
		editors[j++] = new CheckboxCellEditor(columnsTable);
		editors[j++] = new CheckboxCellEditor(columnsTable);
		editors[j++] = new TextCellEditor(columnsTable);
		editors[j++] = new ComboBoxCellEditor(columnsTable, RuleUtils.getRuleNames(),SWT.READ_ONLY);
		editors[j++] = new TextCellEditor(columnsTable);
		editors[j++] = new TextCellEditor(columnsTable);
		tableViewer.setCellEditors(editors);

		tableViewer.setLabelProvider(new ColumnLabelProvider());
		tableViewer.setContentProvider(new IStructuredContentProvider(){
			public void dispose() {}
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}
			public Object[] getElements(Object inputElement) {
				return columnList.toArray();
			}
		});
		tableViewer.setCellModifier(new ColumnCellModifier(this,this.columnNames));
		tableViewer.setInput(columnList);
	}

	public void onEnterPage() {
		StartReportWizard wizard = (StartReportWizard) super.getWizard();
		PageOne pageOne = (PageOne) wizard.getPage("PageOne");
		columnList = AutoCodeDbUtils.getColumnListBySql(pageOne.getDs(), wizard.getReportModel().getSql());
		if (columnList.isEmpty()) {
			MessageDialog.openError(super.getShell(), "错误信息", "请确认SQL语句是否正确。");
		}
		tableViewer.refresh();
		updateTableColor();
	}
	
	//修改表格颜色add by huangxiaolong@2015/09/16
	public void updateTableColor(){
		//修改列颜色
		Color gray = Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY);
		for(int row=0;row<columnsTable.getItems().length;row++){
			columnsTable.getItem(row).setForeground(0, gray);
			columnsTable.getItem(row).setForeground(1, gray);
			columnsTable.getItem(row).setForeground(2, gray);
			if(columnList.get(row).getRule().getReadOnly()){
				columnsTable.getItem(row).setForeground(columnsTable.getColumnCount()-1, gray);
			}
		}
		columnsTable.redraw();
	}
	
}

/**
 * LABEL 提供者
 * 
 */
class ColumnLabelProvider extends LabelProvider implements ITableLabelProvider {

	// Names of images used to represent checkboxes
	public static final String CHECKED_IMAGE = "checked";
	public static final String UNCHECKED_IMAGE = "unchecked";

	private static ImageRegistry imageRegistry = new ImageRegistry();

	static {
		String iconPath = "icons/";
		imageRegistry.put(CHECKED_IMAGE, ResourceManager.getPluginImage("cn.thisfree.autocode", "icons/checked.png"));
		imageRegistry.put(UNCHECKED_IMAGE, ResourceManager.getPluginImage("cn.thisfree.autocode", "icons/unchecked.png"));
	}

	/**
	 * Returns the image with the given key, or <code>null</code> if not found.
	 */
	private Image getImage(boolean isSelected) {
		String key = isSelected ? CHECKED_IMAGE : UNCHECKED_IMAGE;
		return imageRegistry.get(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang
	 * .Object, int)
	 */
	public Image getColumnImage(Object element, int columnIndex) {
		Column col = (Column) element;
		String property=PageThree.columnNames[columnIndex];
		if("isPrimaryKey".equals(property)){//是否主键
			return getImage(col.getIsPrimaryKey());
		}
		
		if("isNotNull".equals(property)){//是否非空
			return getImage(col.getIsNotNull());
		}
		
		if("isList".equals(property)){//是否显示列表
			return getImage("Y".equalsIgnoreCase(col.getIsList()));
		}
		
		if("isQuery".equals(property)){//是否作为条件查询
			return getImage("Y".equalsIgnoreCase(col.getIsQuery()));
		}
		
		if("isEdit".equals(property)){//是否可编辑
			return getImage("Y".equalsIgnoreCase(col.getIsEdit()));
		}
		return super.getImage(element);
	}

	public String getColumnText(Object element, int columnIndex) {
		Column col = (Column) element;
		String property=PageThree.columnNames[columnIndex];
		if("columnName".equals(property)){//是否主键
			return col.getColumnName();
		}
		if("columnType".equals(property)){//是否主键
			return col.getColumnType();
		}
		if("length".equals(property)){//字段长度
			return col.getDatasize()+"";
		}
		
		if("javaName".equals(property)){//是否显示列表
			return col.getJavaName();
		}
		
		if("javaType".equals(property)){//是否显示列表
			return col.getJavaType();
		}
		
		if("columnComment".equals(property)){//是否作为条件查询
			return col.getColumnComment() == null ? "" : col.getColumnComment();
		}
		
		if("dictKey".equals(property)){//是否可编辑
			return col.getDictKey();
		}
		
		if("ruleName".equals(property)){//是否可编辑
			return ColumnRuleUtils.getRule(col.getColumnName()).getRuleName();
		}
		
		if("ruleDesc".equals(property)){//规则描述
			return ColumnRuleUtils.getRule(col.getColumnName()).getRuleRemark();
		}
		
		if("ruleValue".equals(property)){//是否可编辑
			return ColumnRuleUtils.getRule(col.getColumnName()).getRuleValue();
		}
		return "";
	}

}

class ColumnCellModifier implements ICellModifier {
	PageThree pageThree;
	List columnNames;

	public ColumnCellModifier(PageThree pageThree, String[] columnNames) {
		this.pageThree = pageThree;
		this.columnNames = Arrays.asList(columnNames);
	}

	public boolean canModify(Object element, String property) {
		Column col = (Column) element;
		if("ruleValue".equals(property)){
			String name=col.getColumnName();
			Rule rule=ColumnRuleUtils.getRule(name);
			if(rule.getReadOnly()){
				return false;
			}
		}
		return true;
	}
	
	 private int getRuleIndex(String name){
         for(int i=0;i<RuleUtils.getRuleNames().length;i++){
             if(RuleUtils.getRuleNames()[i].equals(name)){
                 return i;
             }
         }
         return -1;
     }

	public Object getValue(Object element, String property) {
		Column col = (Column) element;
		
		if("columnName".equals(property)){//列名
			return col.getColumnName();
		}
		
		if("columnType".equals(property)){//列名
			return col.getColumnType();
		}
		
		if("length".equals(property)){//字段长度
			return col.getDatasize()+"";
		}
		
		if("columnComment".equals(property)){//列注释
			return col.getColumnComment();
		}
		
		if("javaName".equals(property)){//Java名
			return col.getJavaName();
		}
		
		if("javaType".equals(property)){//Java类型
			return col.getJavaType();
		}
		
		if("isPrimaryKey".equals(property)){//是否主键
			return col.getIsPrimaryKey();
		}
		
		if("isNotNull".equals(property)){//是否主键
			return col.getIsNotNull();
		}
		
		if("isList".equals(property)){//是否显示列表
			return !"Y".equals(col.getIsList());
		}
		
		if("isQuery".equals(property)){//是否作为条件查询
			return !"Y".equals(col.getIsQuery());
		}
		
		if("isEdit".equals(property)){//是否可编辑
			return !"Y".equals(col.getIsEdit());
		}
		
		if("dictKey".equals(property)){//数据字典
			return col.getDictKey()==null?"":col.getDictKey();
		}
		if("ruleName".equals(property)){//规则名
			String name=col.getColumnName();
			return getRuleIndex(ColumnRuleUtils.getRule(name).getRuleName());
		}
		
		if("ruleValue".equals(property)){//规则值
			String name=col.getColumnName();
			Rule rule=ColumnRuleUtils.getRule(name);
			return rule.getRuleValue()==null?"":rule.getRuleValue();
		}
		
		if("ruleDesc".equals(property)){//规则描述
			String name=col.getColumnName();
			Rule rule=ColumnRuleUtils.getRule(name);
			return rule.getRuleRemark()==null?"":rule.getRuleRemark();
		}

		return "";
	}

	public void modify(Object element, String property, Object value) {
		TableItem item = (TableItem) element;
		Column col = (Column)item.getData();
		
		if("columnComment".equals(property)){//列注释
			col.setColumnComment(StringUtils.trim((String)value));
		}
		
		if("javaName".equals(property)){//Java名
			col.setJavaName(StringUtils.trim((String)value));
		}
		
		if("javaType".equals(property)){//Java类型
			col.setJavaType(StringUtils.trim((String)value));
		}
		
		if("isPrimaryKey".equals(property)){//是否主键
			col.setPrimaryKey(((Boolean) value).booleanValue());
		}
		if("isNotNull".equals(property)){//是否主键
			col.setNotNull(((Boolean) value).booleanValue());
		}
		
		if("isList".equals(property)){//是否显示列表
			col.setIsList(((Boolean) value).booleanValue()?"N":"Y");
		}
		
		if("isQuery".equals(property)){//是否作为条件查询
			col.setIsQuery(((Boolean) value).booleanValue()?"N":"Y");
		}
		
		if("isEdit".equals(property)){//是否可编辑
			col.setIsEdit(((Boolean) value).booleanValue()?"N":"Y");
		}
		
		if("dictKey".equals(property)){//数据字典
			col.setDictKey(StringUtils.trim((String)value));
		}
		if("ruleName".equals(property)){//规则名
			Integer comboIndex = (Integer)value;
            if(comboIndex.intValue() == -1){
                return ;
            }
            Rule rule=new Rule(RuleUtils.getRuleByName(RuleUtils.getRuleNames()[comboIndex]));
            //col.setRule(rule);
            ColumnRuleUtils.setRule(col.getColumnName(), rule);
            
            int row=item.getParent().indexOf(item);
            Color gray = Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY);
            Color black = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
            if(rule.getReadOnly()){
            	item.getParent().getItem(row).setForeground(item.getParent().getColumnCount()-1, gray);
            }else{
            	item.getParent().getItem(row).setForeground(item.getParent().getColumnCount()-1, black);
            }
		}
		
		if("ruleDesc".equals(property)){//规则值
			String name=col.getColumnName();
			ColumnRuleUtils.getRule(name).setRuleRemark(StringUtils.trim(value+""));
		}
		if("ruleValue".equals(property)){//规则值
			String name=col.getColumnName();
			ColumnRuleUtils.getRule(name).setRuleValue(StringUtils.trim(value+""));
		}

		this.pageThree.getTableViewer().update(col, null);
	}

}