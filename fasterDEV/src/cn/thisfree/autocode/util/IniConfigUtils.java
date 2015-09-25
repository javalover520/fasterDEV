package cn.thisfree.autocode.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.SubnodeConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;

/**
 * 读取魔板配置文件元文件
 * 
 * @author xiaolong.huang
 *
 */
public class IniConfigUtils {
	/**
	 * 加载配置文件元数据
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @throws ConfigurationException
	 */
	public static Map<String, Map<String,String>> getTemplateIni(String filePath,Map data) throws IOException, ConfigurationException {
		Map<String, Map<String,String>> templateMap = new TreeMap<String, Map<String,String>>();
		INIConfiguration ini = new INIConfiguration();
		ini.read(new FileReader(new File(filePath)));
		// 列出所有Sections
		Iterator sectionsItr = ini.getSections().iterator();
		// 遍历section
		while (sectionsItr.hasNext()) {
			String section = sectionsItr.next() + "";
			SubnodeConfiguration sub = ini.getSection(section);
			Iterator<String> keyItr = sub.getKeys();
			
			Map<String,String> sectionMap=new HashMap<String,String>();
			while (keyItr.hasNext()) {
				String key = keyItr.next();
				sectionMap.put(key + ".name", key);
				sectionMap.put(key, sub.getString(key));
			}
			templateMap.put(section, sectionMap);
			data.put(section,sectionMap);
		}

		return templateMap;
	}
}
