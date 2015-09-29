package cn.thisfree.autocode.crud;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.metamodel.schema.Column;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;

import cn.thisfree.autocode.model.TableConfigModel;
import cn.thisfree.autocode.model.TableModel;
import cn.thisfree.autocode.util.FreeMakerUtils;
import cn.thisfree.autocode.util.IniConfigUtils;
import cn.thisfree.autocode.util.StringUtils;
import cn.thisfree.autocode.util.TemplateMeta;
import cn.thisfree.autocode.util.XmlObjectCrud;
import cn.thisfree.autocode.util.XmlObjectUtils;
import freemarker.template.Configuration;

public class StartCurdWizard extends Wizard {
	// 所有全局变量防止在这里

	TableModel tableModel = new TableModel();

	public StartCurdWizard() {
		setWindowTitle("增删查改模块生成");
		setHelpAvailable(false);
		setNeedsProgressMonitor(false);
	}

	@Override
	public void addPages() {
		addPage(new PageOne());
		addPage(new PageTwo());
		addPage(new PageThree());

		// 设置上一次操作的路径
		TableConfigModel fileconfig = (TableConfigModel) XmlObjectUtils.objectXmlDecoder(XmlObjectCrud.getLeastTable());
		if (fileconfig != null) {
			PageTwo pageTwo = (PageTwo) super.getPage("PageTwo");
			TableConfigModel pttable = pageTwo.getTableConfig();
			pttable.setTemplateLocation(fileconfig.getTemplateLocation());
			pttable.setAuthor(fileconfig.getAuthor());
			pttable.setVersion(fileconfig.getVersion());
			pttable.setTopPackage(fileconfig.getTopPackage());
			pttable.setFilePath(fileconfig.getFilePath());
			pttable.setJspLocation(fileconfig.getJspLocation());
			pttable.setFunctionNameCn(fileconfig.getFunctionNameCn());
			pttable.setFunctionNameEn(fileconfig.getFunctionNameEn());
			pttable.setUrlPrefix(fileconfig.getUrlPrefix());
			pttable.setPermissionPrefix(fileconfig.getPermissionPrefix());
		}
	}

	@Override
	public boolean performFinish() {
		PageTwo pageTwo = (PageTwo) super.getPage("PageTwo");
		pageTwo.checkInput();

		TableConfigModel tableconfig = pageTwo.getTableConfig();
		PageThree pageThree = (PageThree) super.getPage("PageThree");
		XmlObjectUtils.objectXmlEncoder(pageThree.getColumnList(), XmlObjectCrud.getLeastColumns());
		XmlObjectUtils.objectXmlEncoder(tableconfig, XmlObjectCrud.getLeastTable());

		Map data = new HashMap();
		data.put("columnList", pageThree.getColumnList());
		data.put("table", tableconfig);
		
		//设置主键
		for(Column col:pageThree.getColumnList()){
			if(col.isPrimaryKey()){
//				tableconfig.setKey(col);
				break;
			}
		}

		String separator = File.separator;
		String basePath = pageTwo.getTableConfig().getFilePath();

		// 生成文件
		// Configuration freemakerCfg =
		// FreeMakerUtils.getFreeMarkerCfg(this.getClass(), "template");
		// modified by huangxiaolong@2015/09/13
		Configuration freemakerCfg = FreeMakerUtils.getFreeMarkerCfg(tableconfig.getTemplateLocation());

		Map<String, Map<String, String>> configMap = null;
		try {
			configMap = IniConfigUtils.getTemplateIni(tableconfig.getTemplateLocation() + "\\config.ini", data);
		} catch (IOException e1) {
			MessageDialog.openError(super.getShell(), "生成失败,请确认config.ini文件是否配置正确", e1 + "");
			return false;
		} catch (ConfigurationException e1) {
			MessageDialog.openError(super.getShell(), "生成失败,请确认config.ini文件是否配置正确", e1 + "");
			return false;
		}

		try {
			String section = null;
			String packageName = null;
			String type = null;
			Map<String, String> map = null;
			String projectDir=null;
			for (Entry<String, Map<String, String>> entry : configMap.entrySet()) {
				section = entry.getKey();
				map = entry.getValue();
				type = map.get("type");
				projectDir=map.get("proj_dir");
				packageName = map.get("packageName");
				// java类
				if (TemplateMeta.OUT_TYPE_JAVA.equalsIgnoreCase(type)) {
					String fileName = map.get("file");
					String newFilename = StringUtils.capitalize(tableconfig.getTableJavaName())
							+ StringUtils.capitalize(fileName.replace("ftl", TemplateMeta.OUT_TYPE_JAVA));
					FreeMakerUtils.generateFile(freemakerCfg, fileName, data,
							projectDir+"\\"+ StringUtils.replace(tableconfig.getTopPackage() + "." + packageName, ".", "\\") + "\\", newFilename, basePath);
				} else if (TemplateMeta.OUT_TYPE_JSP.equalsIgnoreCase(type)) {
					List<String> list=new ArrayList<String>();
					list.add(map.get("list"));
					list.add(map.get("add"));
					list.add(map.get("edit"));
					list.add(map.get("detail"));
					for(String fileName:list){
						String newFilename = tableconfig.getFunctionNameEn()+"_"+fileName.replace("ftl", TemplateMeta.OUT_TYPE_JSP);
						FreeMakerUtils.generateFile(freemakerCfg, fileName, data,
							projectDir+"\\" + StringUtils.replace(tableconfig.getJspLocation(), "/", "\\"), newFilename, basePath);
					}
				} else if (TemplateMeta.OUT_TYPE_XML.equalsIgnoreCase(type)) {
					String fileName = map.get("file");
					String newFilename = fileName.replace("ftl", TemplateMeta.OUT_TYPE_XML);
					FreeMakerUtils.generateFile(freemakerCfg, fileName, data,
							projectDir+"\\" + StringUtils.replace(tableconfig.getTopPackage() + "." + packageName, ".", "\\") + "\\", newFilename,
							basePath);
				}
			}
		} catch (Exception e) {
			MessageDialog.openError(super.getShell(), "生成失败", e + "");
			return false;
		}
		MessageDialog.openInformation(super.getShell(), "代码生成成功", "代码生成成功，生成代码存放路径：" + tableconfig.getFilePath());
		return false;
	}

	public void setTableModel(String name, String comment) {
		tableModel.setName(name);
		tableModel.setComment(comment);
	}

	public TableModel getTableModel() {
		return tableModel;
	}

	@Override
	public boolean canFinish() {
		if ("PageThree".equals(super.getContainer().getCurrentPage().getName())) {
			return true;
		} else {
			return false;
		}
	}

}
