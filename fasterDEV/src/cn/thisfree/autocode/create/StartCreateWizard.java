package cn.thisfree.autocode.create;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;

import cn.thisfree.autocode.model.ProjectModel;
import cn.thisfree.autocode.model.ProjectPropeties;
import cn.thisfree.autocode.model.ProjectUtils;
import cn.thisfree.autocode.util.FileUtils;

/**
 * 初始化环境
 * 
 * @author xiaolong.huang
 * @date 2015/09/15
 */
public class StartCreateWizard extends Wizard {
	// 所有全局变量防止在这里

	ProjectModel projectModel = new ProjectModel();

	public StartCreateWizard() {
		setWindowTitle("生成代码工程");
		setHelpAvailable(false);
		setNeedsProgressMonitor(false);
	}

	@Override
	public void addPages() {
		addPage(new PageOne());
	}

	@Override
	public boolean performFinish() {
		PageOne pageOne = (PageOne) super.getPage("PageOne");
		ProjectPropeties project = pageOne.getProject();
		ProjectModel mode = ProjectUtils.getProjectModelBysection(project.getFramework());
		String path = mode.getDirectory() + "/" + mode.getProjectFile();
		InputStream in = StartCreateWizard.class.getResourceAsStream("/project/" + path);
		try {
			FileUtils.copy(in, new FileOutputStream(project.getOutpath()+"/"+project.getName()+".zip"));
		} catch (Exception e) {
			MessageDialog.openError(super.getShell(), "提示信息", "工程生成失败，失败原因：" + e.getCause());
			return false;
		}
		
		try {
			//解压文件
			FileUtils.upZip(project.getOutpath()+"/"+project.getName()+".zip", project.getOutpath());
		} catch (Exception e) {
			MessageDialog.openError(super.getShell(), "提示信息", "解压工程文件失败，失败原因：" + e.getCause());
			return false;
		}
		
		MessageDialog.openInformation(super.getShell(), "提示信息", "工程生成成功,工程文件目录路径为：" + project.getOutpath());
		return false;
	}

	@Override
	public boolean canFinish() {
		return true;
	}

}
