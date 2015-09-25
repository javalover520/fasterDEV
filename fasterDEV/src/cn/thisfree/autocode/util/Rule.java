package cn.thisfree.autocode.util;
/**
 * 校验规则基本信息
 * @author xiaolong.huang
 *
 */
public class Rule {
	private String name;//名称
	private String value;//值
	private String remark;//描述
	private boolean isReadOnly=true;
	
	public Rule(){
		
	}
	
	public Rule(Rule rule){
		this.name=rule.getName();
		this.value=rule.getValue();
		this.remark=rule.getRemark();
		this.isReadOnly=rule.isReadOnly();
	}
	
	public Rule(String name, String value,String remark,boolean isReadOnly) {
		super();
		this.name = name;
		this.value = value;
		this.remark = remark;
		this.isReadOnly=isReadOnly;
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

	public boolean isReadOnly() {
		return isReadOnly;
	}

	public void setReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
