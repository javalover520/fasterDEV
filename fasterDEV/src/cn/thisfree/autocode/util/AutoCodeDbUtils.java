package cn.thisfree.autocode.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import cn.thisfree.autocode.model.ColumnModel;
import cn.thisfree.autocode.model.DataSource;
import cn.thisfree.autocode.model.TableModel;

public class AutoCodeDbUtils {
	/**
	 * 数据库连接 MySql,SqlServer,Oracle driver-驱动名,url-连接地址及数据库,user-用户名,pwd-密码
	 * */
	public static Connection getConntion(DataSource ds) {
		try {
			Class.forName(ds.getDriver()).newInstance();
			Properties prop = new Properties();
			prop.put("user", ds.getUser());
			prop.put("password", ds.getPassword()+"");
			// !!! Oracle 如果想要获取元数据 REMARKS 信息,需要加此参数  
			prop.put("remarksReporting","true");  
            // !!! MySQL 标志位, 获取TABLE元数据 REMARKS 信息  
			prop.put("useInformationSchema","true");  
			Connection conn = DriverManager.getConnection(ds.getUrl(), prop);
			return conn;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<TableModel> getTableList(DataSource ds) {
		List<TableModel> list = new ArrayList<TableModel>();
		
		Connection conn = null;
		ResultSet rs=null;
		try {
			conn = getConntion(ds);
			DatabaseMetaData metaData = conn.getMetaData();  
	        rs = metaData.getTables(null, ds.getDbName(), null, new String[]{"TABLE","VIEW"});  
	        while(rs.next()){  
	        	TableModel table = new TableModel();
	            String tableName = rs.getString("TABLE_NAME");  
	            String comment=rs.getString("REMARKS");
	            String type=rs.getString("TABLE_TYPE");
	            table.setName(tableName);
	            table.setComment(comment);
	            table.setType(type);
	            list.add(table);
	        }  
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return list;
	}

	
	public static List<ColumnModel> getColumnList(DataSource ds,String tableName){
		List<ColumnModel> list = new ArrayList<ColumnModel>();
		Map<String,ColumnModel> ref=new HashMap<String,ColumnModel>();//引用
		Connection conn = null;
		conn = getConntion(ds);
		DatabaseMetaData metaData=null;
		ResultSet rs=null;
		try {
			metaData = conn.getMetaData();
			
			//获取列基本信息
			rs = metaData.getColumns(null, ds.getDbName(), tableName, null);  
	        while(rs.next()){  
	        	String name=rs.getString("COLUMN_NAME");//名称
	        	String type=rs.getString("TYPE_NAME");//类型
	        	String remarks=rs.getString("REMARKS");//备注
	        	int dataSize=rs.getInt("COLUMN_SIZE");//长度
	        	int digits=rs.getInt("DECIMAL_DIGITS");//小数长度
	        	int nullable=rs.getInt("NULLABLE");//是否为空
	        	
	        	ColumnModel col=new ColumnModel();
	        	col.setColumnName(name);
	        	col.setColumnComment(remarks);
	        	col.setColumnType(type);
	        	col.setDatasize(dataSize);
	        	col.setDigits(digits);
	        	col.setJAVAinfo();
	        	if(nullable==0){
	        		col.setNotNull(true);
	        		col.setRule(RuleUtils.getRuleByName("required"));
	        	}else{
	        		col.setNotNull(false);
	        		col.setRule(RuleUtils.getRuleByName("none"));
	        	}
	        	col.setNotNull(nullable==1?false:true);
	        	
	        	col.setIsEdit("Y");
	        	col.setIsList("Y");
	        	col.setIsQuery("Y");
	            list.add(col);
	            ref.put(name, col);
	        }  
	        //获取主键信息
	        rs=metaData.getPrimaryKeys(ds.getDbName(), ds.getDbName(), tableName);
	        while(rs.next()){  
	        	String columnName = rs.getString("COLUMN_NAME");
	        	ColumnModel col=ref.get(columnName);
	        	col.setPrimaryKey(true);;//设置主键
	        	col.setIsEdit("N");
	        	col.setIsList("N");
	        	col.setIsQuery("N");
	        }
	        
	        rs=metaData.getIndexInfo(null, ds.getDbName(), tableName, true, false);  
	        while(rs.next()){  
	        	String columnName = rs.getString("COLUMN_NAME");
	        	boolean nonUnique = rs.getBoolean("NON_UNIQUE");
	        	ref.get(columnName).setUnique(!nonUnique);
//	        	System.out.println("TABLE_CAT" + "===" + rs.getString("TABLE_CAT"));  
//	            System.out.println("TABLE_SCHEM" + "===" + rs.getString("TABLE_SCHEM"));  
//	            System.out.println("TABLE_NAME" + "===" + rs.getString("TABLE_NAME"));  
//	            System.out.println("NON_UNIQUE" + "===" + rs.getString("NON_UNIQUE"));  
//	            System.out.println("INDEX_QUALIFIER" + "===" + rs.getString("INDEX_QUALIFIER"));  
//	            System.out.println("INDEX_NAME" + "===" + rs.getString("INDEX_NAME"));  
//	            System.out.println("TYPE" + "===" + rs.getString("TYPE"));  
//	            System.out.println("ORDINAL_POSITION" + "===" + rs.getString("ORDINAL_POSITION"));  
//	            System.out.println("COLUMN_NAME" + "===" + rs.getString("COLUMN_NAME"));  
//	            System.out.println("ASC_OR_DESC" + "===" + rs.getString("ASC_OR_DESC"));  
//	            System.out.println("CARDINALITY" + "===" + rs.getString("CARDINALITY"));  
//	            System.out.println("PAGES" + "===" + rs.getString("PAGES"));  
//	            System.out.println("FILTER_CONDITION" + "===" + rs.getString("FILTER_CONDITION"));  
	        }
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return list;
	}
	
	
	public static List<ColumnModel> getColumnListBySql(DataSource ds,final String sql){
		//SQL增加条件过滤，只选择一条记录		
		Connection conn = null;
		
		List<ColumnModel> list = new ArrayList<ColumnModel>();
		
		String newsql = sql;
		
		if ("ORACLE".equals(ds.getDataBaseType())) {
			
		}else if("MYSQL".equals(ds.getDataBaseType())){
			newsql += " limit 0,1"; 
		}

		try {
			conn = getConntion(ds);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			
			//spring dao 里面有具体的实现，回头翻阅下spring jdbc 代码
			List<ColumnModel> colList = new ArrayList();
			int columnCount = rsmd.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				ColumnModel col=new ColumnModel();
	        	col.setColumnName(rsmd.getColumnName(i));
	        	col.setColumnComment("无");
	        	col.setColumnType(rsmd.getColumnTypeName(i));
	        	col.setDatasize(rsmd.getColumnDisplaySize(i));
	        	col.setDigits(rsmd.getScale(i));
	        	col.setNotNull(rsmd.isNullable(i)==0?true:false);
	        	col.setRule(RuleUtils.getRuleByName("none"));
	        	
	        	col.setIsEdit("N");
	        	col.setIsList("N");
	        	col.setIsQuery("N");
	        	col.setJAVAinfo();
	            list.add(col);
			}
			return list;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return null;
	}
}
