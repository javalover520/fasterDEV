package cn.thisfree.autocode.model;

/**
 * 生成代码工程
 * 
 * @author xiaolong.huang
 * @date 2015/09/21
 */
public class ProjectModel {
	private String desc;
	private String directory;
	private String projectFile;
	private String templateFile;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getProjectFile() {
		return projectFile;
	}

	public void setProjectFile(String projectFile) {
		this.projectFile = projectFile;
	}

	public String getTemplateFile() {
		return templateFile;
	}

	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}
}
