/*-------------------------*/

/*Create Controller*/

package nosi.webapps.igrp.pages.mapaprocesso;


/*----#START-PRESERVED-AREA(PACKAGES_IMPORT)----*/
import static nosi.core.i18n.Translator.gt;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import nosi.core.gui.components.IGRPMenu;
import nosi.core.webapp.Controller;
import nosi.core.webapp.Core;
import nosi.core.webapp.Igrp;
import nosi.core.webapp.Response;
import nosi.core.webapp.activit.rest.FormDataService;
import nosi.core.webapp.activit.rest.ProcessDefinitionService;
import nosi.core.webapp.activit.rest.TaskService;
import nosi.core.webapp.helpers.FileHelper;
import nosi.core.webapp.helpers.Permission;
import nosi.core.xml.XMLExtractComponent;
import nosi.core.xml.XMLWritter;
import nosi.webapps.igrp.dao.Action;
import nosi.webapps.igrp.dao.Application;
/*----#END-PRESERVED-AREA----*/

public class MapaProcessoController extends Controller{		


	public Response actionIndex() throws IOException{
		/*----#START-PRESERVED-AREA(INDEX)----*/
		MapaProcesso model = new MapaProcesso();
		MapaProcessoView view = new MapaProcessoView(model);
		List<IGRPMenu> listMenus = new ArrayList<>();
		IGRPMenu menus = new IGRPMenu(gt("Lista de Processos"),"webapps?r=");
		IGRPMenu.Menu menu = new IGRPMenu.Menu(gt("Processos Ativos"));
		Application app = new Application().find().andWhere("dad", "=",new Permission().getCurrentEnv()).one();
		for(ProcessDefinitionService process:new ProcessDefinitionService().getProcessDefinitionsAtivos(app.getId())){
			IGRPMenu.SubMenu submenu = new IGRPMenu.SubMenu(process.getName(), this.getConfig().getResolveUrl("igrp","MapaProcesso","openProcess")+"&p_processId="+process.getId(), process.getId(),process.getSuspended(), "LEFT_MENU");
			menu.addSubMenu(submenu);
		}
		menus.addMenu(menu);
		listMenus.add(menus);
		view.menu.setListMenu(listMenus);
		return this.renderView(view);
		/*----#END-PRESERVED-AREA----*/
	}

	/*----#START-PRESERVED-AREA(CUSTOM_ACTIONS)----*/
	public Response actionOpenProcess() throws IOException{
		XMLExtractComponent comp = new XMLExtractComponent();
		String p_processId = Igrp.getInstance().getRequest().getParameter("p_processId");
		String taskId = Igrp.getInstance().getRequest().getParameter("taskId");
		String withButton = Igrp.getInstance().getRequest().getParameter("withButton");
		FormDataService formData = null;
		String title = "";
		String idApp = "-1";
		if(p_processId!=null){
			ProcessDefinitionService process = new ProcessDefinitionService().getProcessDefinition(p_processId);
			title = process!=null?process.getName():"";
			formData = new FormDataService().getFormDataByProcessDefinitionId(p_processId);
			idApp = process.getTenantId();
		}
		if(taskId!=null){
			TaskService task = new TaskService().getTask(taskId);
			title = task!=null?Core.isNotNull(task.getDescription())?task.getDescription():task.getName()+" - Nº "+task.getId():"";
			formData = new FormDataService().getFormDataByTaskId(taskId);
			idApp = task.getTenantId();
		}
		if(formData != null) {
			if(Core.isNotNull(formData.getFormKey())) {
				Action action = new Action().find().andWhere("application", "=",Core.toInt(idApp)).andWhere("page", "=",formData.getFormKey()).one();
				Response resp = this.call(action.getApplication().getDad(), action.getPage(),"index");
				String content = comp.removeXMLButton(resp.getContent());
				XMLWritter xml = new XMLWritter("rows", this.getConfig().getResolveUrl("igrp","mapa-processo","get-xsl").replaceAll("&", "&amp;")+"&amp;page="+formData.getFormKey()+"&amp;app="+idApp, "utf-8");
				xml.addXml(this.getConfig().getHeader(null));
				xml.startElement("content");
				xml.writeAttribute("type", "");
				xml.setElement("title", title);
				xml.addXml(comp.generateButtonProcess(p_processId, taskId).toString());
				xml.addXml(comp.addFormlistFile().toString());
				xml.addXml(content);
				xml.endElement();
				return this.renderView(xml.toString());	
			}
			String content = comp.transformToXmlWorkFlow(title,formData,(Core.isNotNull(withButton) && withButton.equals("false"))?false:true);
			return this.renderView(content);
		}
		return null;
	}
	
	public Response actionGetXsl() throws IOException{
		String page = Core.getParam("page");
		String app = Core.getParam("app");
		if(Core.isNotNull(page) && Core.isNotNull(app)){
			XMLExtractComponent comp = new XMLExtractComponent();
			Action ac = new Action().find().andWhere("page", "=",page).andWhere("application", "=",Core.toInt(app)).one();
			String path_xsl = this.getConfig().getBaseServerPahtXsl(ac);
			String content = FileHelper.readFile(path_xsl, ac.getPage()+".xsl");
			content = comp.addButtonXsl(content);
			this.format = Response.FORMAT_XSL;			
			return this.renderView(content.replaceAll("../../../",this.getConfig().getLinkImg()+"/"));
		}
		return this.redirect("igrp", "ErrorPage", "exception");
	}
	/*----#END-PRESERVED-AREA----*/
}
