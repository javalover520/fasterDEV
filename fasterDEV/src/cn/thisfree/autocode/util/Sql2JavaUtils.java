package cn.thisfree.autocode.util;

import java.util.HashMap;
import java.util.Map;

/**
 * sql转换成java类型
 * @author xiaolong.huang
 *
 */
public class Sql2JavaUtils {
	//key:数据库类型
	//value:java类型
	private static Map<String,String> map=new HashMap<String,String>();
	static{
		map.put("INT", "Integer");
		map.put("INTEGER", "Integer");
		map.put("BIGINT", "Long");
		map.put("LONG", "Long");
		map.put("SMALLINT", "Short");
		map.put("FLOAT", "Float");
		map.put("DOUBLE", "Double");
		map.put("NUMERIC", "java.math.BigDecimal");
		map.put("CHAR","String");
		map.put("VARCHAR", "String");
		map.put("VARCHAR2", "String");
		map.put("TINYINT", "Byte");
		map.put("BIT", "Boolean");
		map.put("DATE", "java.util.Date");
		map.put("Time", "java.util.Date");
		map.put("DATETIME", "java.util.Date");
		map.put("TIMESTAMP", "java.util.DATE");
		map.put("BLOB", "byte[]");
		map.put("VARBINARY", "byte[]");
		map.put("CLOB", "java.sql.Clob");
		map.put("BLOB", "java.sql.Blob");
	}
	
	/**
	 * 获取数据库字段类型对应的java类型
	 * @param dbType
	 * @return
	 */
	public static String getJavaType(String dbType){
		if(dbType==null){
			return "String";
		}
		String type=map.get(dbType.toUpperCase());
		if(type==null){
			return "String";
		}
		return type;
	}
}
