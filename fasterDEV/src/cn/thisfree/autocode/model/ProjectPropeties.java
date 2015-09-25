package cn.thisfree.autocode.model;

/**
 * 工程属性
 * @author xiaolong.huang
 *
 */
public class ProjectPropeties extends AbstractModelObject{
	private String name;
	private String outpath;
	private String framework;
	private String datasource;
	private boolean isInitSql;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOutpath() {
		return outpath;
	}

	public void setOutpath(String outpath) {
		this.outpath = outpath;
	}

	public String getFramework() {
		return framework;
	}

	public void setFramework(String framework) {
		this.framework = framework;
	}

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	public boolean getIsInitSql() {
		return isInitSql;
	}

	public void setInitSql(boolean isInitSql) {
		this.isInitSql = isInitSql;
	}
}
