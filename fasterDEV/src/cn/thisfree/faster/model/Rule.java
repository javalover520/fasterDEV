package cn.thisfree.faster.model;

import org.apache.commons.lang3.BooleanUtils;

public class Rule {
	private boolean showAsQuery;// 作为查询条件
	private boolean showAsList;// 作为列表
	private boolean showAsForm;// 作为表单

	private String name;
	private String value;
	private String remark;
	private boolean readOnly = false;
	
	public Rule(Rule rule){
		
	}
	
	public Rule(){
		
	}
	
	public Rule(String name,String value,String remark,boolean readOnly){
		this.name=name;
		this.value=value;
		this.remark=remark;
		this.readOnly=readOnly;
	}

	public boolean getShowAsQuery() {
		return showAsQuery;
	}

	public void setShowAsQuery(boolean showAsQuery) {
		this.showAsQuery = showAsQuery;
	}

	public boolean getShowAsList() {
		return showAsList;
	}

	public void setShowAsList(boolean showAsList) {
		this.showAsList = showAsList;
	}

	public boolean getShowAsForm() {
		return showAsForm;
	}

	public void setShowAsForm(boolean showAsForm) {
		this.showAsForm = showAsForm;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
}
