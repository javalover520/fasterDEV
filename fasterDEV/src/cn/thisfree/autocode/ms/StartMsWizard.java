package cn.thisfree.autocode.ms;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;

import cn.thisfree.autocode.model.TableConfigModel;
import cn.thisfree.autocode.model.TableModel;
import cn.thisfree.autocode.util.FreeMakerUtils;
import cn.thisfree.autocode.util.StringUtils;
import cn.thisfree.autocode.util.XmlObjectCrud;
import cn.thisfree.autocode.util.XmlObjectUtils;
import freemarker.template.Configuration;

/**
 * 主从表代码生成
 */
public class StartMsWizard extends Wizard {
	//所有全局变量防止在这里
	
	TableModel tableModel = new TableModel();

	public StartMsWizard() {
		setWindowTitle("树状机构生成");
		setHelpAvailable(false);
		setNeedsProgressMonitor(false);
	}

	@Override
	public void addPages() {
		addPage(new PageOne());
		addPage(new PageTwo());
		addPage(new PageThree());
		
		//设置上一次操作的路径
		TableConfigModel fileconfig = (TableConfigModel)XmlObjectUtils.objectXmlDecoder(XmlObjectCrud.getLeastTable());
		if(fileconfig!=null){
			PageTwo pageTwo = (PageTwo)super.getPage("PageTwo");
			TableConfigModel pttable = pageTwo.getTableConfig();
			pttable.setAuthor(fileconfig.getAuthor());
			pttable.setVersion(fileconfig.getVersion());
			pttable.setTopPackage(fileconfig.getTopPackage());
			pttable.setFilePath(fileconfig.getFilePath());
			pttable.setJspLocation(fileconfig.getJspLocation());
			pttable.setFunctionNameCn(fileconfig.getFunctionNameCn());
			pttable.setFunctionNameEn(fileconfig.getFunctionNameEn());
			pttable.setUrlPrefix(fileconfig.getUrlPrefix());
		}
	}

	@Override
	public boolean performFinish() {
		PageTwo pageTwo = (PageTwo)super.getPage("PageTwo");
		pageTwo.checkInput();
		
		TableConfigModel tableconfig = pageTwo.getTableConfig();
		
		PageThree pageThree = (PageThree)super.getPage("PageThree");
		XmlObjectUtils.objectXmlEncoder(pageThree.getColumnList(), XmlObjectCrud.getLeastColumns());		
		XmlObjectUtils.objectXmlEncoder(tableconfig,XmlObjectCrud.getLeastTable());
		
		Map data = new HashMap();
		data.put("columnList", pageThree.getColumnList());
		data.put("table", tableconfig);
		
		
		String separator = File.separator;
		String basePath = pageTwo.getTableConfig().getFilePath();
		
		//生成文件
		//Configuration freemakerCfg = FreeMakerUtils.getFreeMarkerCfg(this.getClass(), "template");
		
		Configuration freemakerCfg = FreeMakerUtils.getFreeMarkerCfg(XmlObjectCrud.getFtlPath());		
		try {
			FreeMakerUtils.generateFile(freemakerCfg, "entity.ftl", data, "src\\main\\java\\"+StringUtils.replace(tableconfig.getTopPackage(), ".", "\\") + "\\entity\\", StringUtils.capitalize(tableconfig.getTableJavaName()) +".java", basePath);
		} catch (Exception e) {
			MessageDialog.openError(super.getShell(), "生成失败", e+"");
			return false;
		}
				
		MessageDialog.openInformation(super.getShell(), "代码生成成功", "代码生成成功，生成代码存放路径："+tableconfig.getFilePath());
		return true;
	}
	
	
	
	public void setTableModel(String name,String comment){
		tableModel.setName(name);
		tableModel.setComment(comment);
	}

	public TableModel getTableModel() {
		return tableModel;
	}

	@Override
	public boolean canFinish() {
		if("PageThree".equals(super.getContainer().getCurrentPage().getName())){
			return true;
		}else{
			return false;
		}
	}
	
}
