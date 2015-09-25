package cn.thisfree.autocode.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 规则工具类
 * @author xiaolong.huang
 *
 */
public class RuleUtils {
	private static Map<String,Rule> rulesMap=new HashMap<String,Rule>();
	private static String[] ruleNames=null;
	
	static{
		rulesMap.put("none", new Rule("none","","无规则.",true));
		rulesMap.put("required", new Rule("required","","必填项，不允许为空.",true));
		rulesMap.put("engNum", new Rule("engNum","","只允许输入英文字母或数字.",true));
		rulesMap.put("chsEngNum", new Rule("chsEngNum","","只允许汉字、英文字母或数字.",true));
		rulesMap.put("code", new Rule("code","","只允许汉字、英文字母、数字及下划线.",true));
		rulesMap.put("name", new Rule("name","","验证用户名合法不合法(字母开头，允许6-16字节，允许字母数字下划线).",true));
		rulesMap.put("minLength", new Rule("minLength","","指定字符最小长度.",false));
		rulesMap.put("maxLength", new Rule("maxLength","","指定字符最大长度.",false));
		rulesMap.put("contains", new Rule("contains","","必须包含指定的内容.",false));
		rulesMap.put("startsWith", new Rule("startsWith","","以指定的字符开头.",false));
		rulesMap.put("endsWith", new Rule("endsWith","","以指定的字符结束.",false));
		rulesMap.put("longDate", new Rule("longDate","","长日期时间(yyyy-MM-dd hh:mm:ss)格式.",true));
		rulesMap.put("shortDate", new Rule("shortDate","","短日期(yyyy-MM-dd)格式.",true));
		rulesMap.put("date", new Rule("date","","长日期时间(yyyy-MM-dd hh:mm:ss)或短日期(yyyy-MM-dd)格式.",true));
		rulesMap.put("tel", new Rule("tel","","电话号码(中国)格式.",true));
		rulesMap.put("mobile", new Rule("mobile","","移动电话号码(中国)格式.",true));
		rulesMap.put("telOrMobile", new Rule("telOrMobile","","电话号码(中国)或移动电话号码(中国)格式.",true));
		rulesMap.put("fax", new Rule("fax","","传真号码(中国)格式.",true));
		rulesMap.put("zipCode", new Rule("zipCode","","邮政编码(中国)格式.",true));
		rulesMap.put("existChinese", new Rule("existChinese","","必须包含中文汉字.",true));
		rulesMap.put("chinese", new Rule("chinese","","必须是纯中文汉字.",true));
		rulesMap.put("english", new Rule("english","","必须是纯英文字母.",true));
		rulesMap.put("fileName", new Rule("fileName","","必须是合法的文件名(不能包含字符 \\/:*?\"<>|).",true));
		rulesMap.put("ip", new Rule("ip","","必须是正确的 IP地址v4 格式.",true));
		rulesMap.put("url", new Rule("url","","必须是正确的 url 格式.",true));
		rulesMap.put("ipurl", new Rule("ipurl","","必须是正确的 IP地址v4 或 url 格式.",true));
		rulesMap.put("currency", new Rule("currency","","必须是正确的货币金额(阿拉伯数字表示法)格式.",true));
		rulesMap.put("qq", new Rule("qq","","必须是正确 QQ号码格式.",true));
		rulesMap.put("msn", new Rule("msn","","必须是正确 MSN账户名格式.",true));
		rulesMap.put("unNormal", new Rule("unNormal","","输入的内容必须是不包含空格和非法字符.",true));
		rulesMap.put("carNo", new Rule("carNo","","必须是合法的汽车车牌号码格式.",true));
		rulesMap.put("carEngineNo", new Rule("carEngineNo","","输入的内容必须是合法的汽车发动机序列号格式.",true));
		rulesMap.put("idCard", new Rule("idCard","","必须是合法的身份证号码(中国)格式.",true));
		rulesMap.put("integer", new Rule("integer","","必须是合法的整数格式.",true));
		rulesMap.put("integerRange", new Rule("integerRange","[0,100]","必须是合法的整数格式且值介于 {0} 与 {1} 之间.",false));
		rulesMap.put("numeric", new Rule("numeric","","必须是指定类型的数字格式.",true));
		rulesMap.put("numericRange", new Rule("numericRange","[1.0,9.0]","必须是指定类型的数字格式且介于 {0} 与 {1} 之间.",false));
		rulesMap.put("color", new Rule("color","","必须是正确的 颜色(#FFFFFF形式) 格式.",true));
		rulesMap.put("password", new Rule("password","","必须是安全的密码字符(由字符和数字组成，至少 6 位)格式.",true));
		rulesMap.put("equals", new Rule("equals","","输入的字符必须是指定的内容相同.",true));
		
		Set<String> set=rulesMap.keySet();
		ruleNames=new String[set.size()];
		set.toArray(ruleNames);
	}

	public static String[] getRuleNames() {
		return ruleNames;
	}
	
	public static Rule getRuleByName(String name){
		return new Rule(rulesMap.get(name));
	}
	
}
