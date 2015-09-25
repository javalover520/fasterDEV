package cn.thisfree.autocode.model;

/**
 * 报表对象
 * @author xiaolong.huang
 *
 */
public class ReportModel extends AbstractModelObject{
	private String sql;//报表SQL
	private String type="default";//报表类型
	private String name="default";//具体的报表名称
	private String title;//报表标题
	private String subTitle;//子标题
	
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
