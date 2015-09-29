package cn.thisfree.autocode.model;

import cn.thisfree.autocode.util.Sql2JavaUtils;
import cn.thisfree.autocode.util.StringUtils;

import com.thisfree.faster.model.Rule;

public class ColumnModel extends AbstractModelObject {
	private String columnName;//列名
	private String columnComment;//注释
	private String columnType;//数据库声明的类型
	private int datasize;//数据大小
	private int digits;//小数点位数
	private boolean isPrimaryKey=false;//是否为主键，默认为false
	private boolean isNotNull=false;//是否非空,默认为非空
	private boolean isUnique=false;//是否唯一
	
	private String javaName="";
	private String javaType="";
	
	//控制字段是否显示
	private String isQuery;
	private String isList;
	private String isEdit;
	
	private Rule rule;
	
	
	
	private String dictKey;//数据字典ID	
	
	//设置列对应的java属性
	public void setJAVAinfo(){
		this.javaName = StringUtils.getJavaName(columnName.toLowerCase());
		this.javaType=Sql2JavaUtils.getJavaType(columnType);
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnComment() {
		return columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public int getDatasize() {
		return datasize;
	}

	public void setDatasize(int datasize) {
		this.datasize = datasize;
	}

	public int getDigits() {
		return digits;
	}

	public void setDigits(int digits) {
		this.digits = digits;
	}

	public boolean getIsPrimaryKey() {
		return isPrimaryKey;
	}

	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public boolean getIsNotNull() {
		return isNotNull;
	}

	public void setNotNull(boolean isNotNull) {
		this.isNotNull = isNotNull;
	}

	public boolean getIsUnique() {
		return isUnique;
	}

	public void setUnique(boolean isUnique) {
		this.isUnique = isUnique;
	}

	public String getJavaName() {
		return javaName;
	}

	public void setJavaName(String javaName) {
		this.javaName = javaName;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getIsQuery() {
		return isQuery;
	}

	public void setIsQuery(String isQuery) {
		this.isQuery = isQuery;
	}

	public String getIsList() {
		return isList;
	}

	public void setIsList(String isList) {
		this.isList = isList;
	}

	public String getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}

	public String getDictKey() {
		return dictKey;
	}

	public void setDictKey(String dictKey) {
		this.dictKey = dictKey;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}
	
}
