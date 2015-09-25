package cn.thisfree.autocode.model;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.SubnodeConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.eclipse.core.runtime.FileLocator;

/**
 * 读取配置文件
 * 
 * @author Administrator
 *
 */
public class ProjectUtils {
	
	private static List<ProjectModel> models=new ArrayList<ProjectModel>();
	private static Map<String,ProjectModel> map=new HashMap<String,ProjectModel>();
	
	static{
		try {
			models.addAll(getModels());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 从配置文件读取包含的工程信息
	 * @return
	 * @throws IOException
	 * @throws ConfigurationException
	 */
	public static List<ProjectModel> getModels() throws IOException, ConfigurationException{
		List<ProjectModel> list=new ArrayList<ProjectModel>();
		String path=FileLocator.toFileURL(ProjectUtils.class.getResource("/project.ini")).getPath();
		if(path.startsWith("/")){
			path=path.substring(1);
		}
		INIConfiguration ini = new INIConfiguration();
		ini.read(new FileReader(new File(path)));
		// 列出所有Sections
		Iterator sectionsItr = ini.getSections().iterator();
		// 遍历section
		while (sectionsItr.hasNext()) {
			String section = sectionsItr.next() + "";
			SubnodeConfiguration sub = ini.getSection(section);
			ProjectModel model=new ProjectModel();
			model.setDesc(sub.getString("desc"));
			model.setDirectory(sub.getString("directory"));
			model.setProjectFile(sub.getString("projectFile"));
			model.setTemplateFile(sub.getString("templateFile"));
			list.add(model);
			map.put(section, model);
		}
		return list;
	}
	
	/**
	 * 获取选择的工程对象
	 * @param key
	 * @return
	 */
	public static ProjectModel getProjectModelBysection(String key){
		return map.get(key);
	}
	
	public static String[] getModelList(){
		String[] names=new String[models.size()];
		for(int i=0;i<models.size();i++){
			names[i]=models.get(i).getDesc();
		}
		
		return names;
	}
}
