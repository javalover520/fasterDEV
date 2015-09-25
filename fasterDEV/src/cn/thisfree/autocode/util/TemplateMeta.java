package cn.thisfree.autocode.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 模板文件元数据
 * 
 * @author xiaolong.huang
 *
 */
public class TemplateMeta {
	public static final String OUT_TYPE_JAVA = "java";
	public static final String OUT_TYPE_JSP = "jsp";
	public static final String OUT_TYPE_XML = "xml";
	private String type = OUT_TYPE_JAVA;
	private String section;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public List<String> getFileList() {
		return fileList;
	}

	private List<String> fileList = new ArrayList<String>();
}
