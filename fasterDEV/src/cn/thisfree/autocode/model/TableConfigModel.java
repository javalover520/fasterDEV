package cn.thisfree.autocode.model;

import cn.thisfree.autocode.util.DateUtil;
import cn.thisfree.autocode.util.StringUtils;

public class TableConfigModel {
	private StringBuilder packagesInclude=new StringBuilder();//add by huangxiaolong@2015/09/14
	String templateLocation;//模板文件路径 add by huangxiaolong@2015/09/13
	String tableName="Default";
	String tableComment="";
	String topPackage="cn.thisfree.module";
	String functionNameEn;
	String functionNameCn;
	String urlPrefix;
	String permissionPrefix;
	String jspLocation;
	String author="huangxiaolong@aliyun.com";
	String version="1.0.0.0";
	String filePath;
	String sql;//执行的SQL语句
	ColumnModel key;//主键字段
	
	private String createDate;
	
	private String namespace;//访问空间
	
	
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getTableJavaName(){
		if(this.tableName==null){
			return "Default";
		}
		return StringUtils.getJavaName(this.tableName);
	}
	
	public String getTableComment() {
		return tableComment;
	}
	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
	public String getTopPackage() {
		return topPackage;
	}
	public void setTopPackage(String topPackage) {
		this.topPackage = topPackage;
	}
	public String getFunctionNameEn() {
		return functionNameEn;
	}
	public void setFunctionNameEn(String functionNameEn) {
		this.functionNameEn = functionNameEn;
	}
	public String getFunctionNameCn() {
		return functionNameCn;
	}
	public void setFunctionNameCn(String functionNameCn) {
		this.functionNameCn = functionNameCn;
	}
	public String getUrlPrefix() {
		return urlPrefix;
	}
	public void setUrlPrefix(String urlPrefix) {
		this.urlPrefix = urlPrefix;
	}
	public String getJspLocation() {
		return jspLocation;
	}
	public void setJspLocation(String jspLocation) {
		this.jspLocation = jspLocation;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getSeqName(){
		int idx = this.tableName.indexOf("_");
		return this.tableName.substring(++idx).toUpperCase();
	}
	public ColumnModel getKey() {
		return key;
	}
	public void setKey(ColumnModel key) {
		this.key = key;
	}	
	
	public String getPermissionPrefix() {
		return permissionPrefix;
	}
	public void setPermissionPrefix(String permissionPrefix) {
		this.permissionPrefix = permissionPrefix;
	}
	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append("tableName=").append(tableName).append("\r\n");
		str.append("sql=").append(sql).append("\r\n");
		str.append("tableComment=").append(tableComment).append("\r\n");
		str.append("topPackage=").append(topPackage).append("\r\n");
		str.append("functionNameEn=").append(functionNameEn).append("\r\n");
		str.append("functionNameCn=").append(functionNameCn).append("\r\n");
		str.append("urlPrefix=").append(urlPrefix).append("\r\n");
		str.append("jspLocation=").append(jspLocation).append("\r\n");
		str.append("author=").append(author).append("\r\n");
		str.append("version=").append(version).append("\r\n");
		str.append("filePath=").append(filePath).append("\r\n");
		return str.toString();
	}
	public String getTemplateLocation() {
		return templateLocation;
	}
	public void setTemplateLocation(String templateLocation) {
		this.templateLocation = templateLocation;
	}
	public StringBuilder getPackagesInclude() {
		return packagesInclude;
	}
	public void setPackagesInclude(StringBuilder packagesInclude) {
		this.packagesInclude = packagesInclude;
	}
	
	public String getCreateDate() {
		return DateUtil.getCurrentDate();
	}
	public String getNamespace() {
		if(topPackage!=null){
			return topPackage.substring(topPackage.lastIndexOf(".")+1);
		}
		return "";
	}
	
}
