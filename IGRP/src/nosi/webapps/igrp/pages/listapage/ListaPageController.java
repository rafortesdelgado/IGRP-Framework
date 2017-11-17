/*-------------------------*/

/*Create Controller*/


package nosi.webapps.igrp.pages.listapage;
/*---- Import your packages here... ----*/

import nosi.core.config.Config;
import nosi.core.webapp.Controller;
import nosi.core.webapp.FlashMessage;
import nosi.core.webapp.Igrp;
import nosi.core.webapp.Response;
import nosi.core.webapp.helpers.DateHelper;
import nosi.core.webapp.helpers.FileHelper;
import nosi.core.webapp.helpers.ImportExportApp;
import nosi.core.webapp.helpers.JarUnJarFile;
import nosi.webapps.igrp.dao.Action;
import nosi.webapps.igrp.dao.Application;
import nosi.webapps.igrp.dao.ImportExportDAO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*---- End ----*/
public class ListaPageController extends Controller {		

	public Response actionIndex() throws IOException, IllegalArgumentException, IllegalAccessException{
		ListaPage model = new ListaPage();
		ArrayList<ListaPage.Table_1> lista = new ArrayList<>();
		Action a = new Action();
		if(Igrp.getInstance().getRequest().getMethod().toUpperCase().equals("POST")){
			model.load();		
			Application app = new Application();
			app.setId(model.getEnv_fk());
			a.setPage(model.getPage());
			a.setPage_descr(model.getPage_descr());
		}	
		List<Action> actions = a.find()
				  .andWhere("application", "=", model.getEnv_fk()!=0?model.getEnv_fk():null)
				  .andWhere("page", "like", model.getPage())
				  .andWhere("page_descr", "like", model.getPage_descr())
				  .all();
		for(Action ac:actions){
			ListaPage.Table_1 table1 = new ListaPage().new Table_1();
			table1.setId(ac.getId());
			table1.setNome_page(ac.getPage());
			table1.setDescricao_page(ac.getPage_descr());
			table1.setVersao_page(ac.getVersion());
			if(ac.getStatus()==1){
				table1.setStatus_page_check(ac.getStatus());
				table1.setStatus_page(ac.getStatus());
			}
			lista.add(table1);
		}
		
		ListaPageView view = new ListaPageView(model);
		view.id.setParam(true);
		view.env_fk.setLabel("Aplica��o");
		view.env_fk.setValue(new Application().getListApps());
		view.table_1.addData(lista);
		
		return this.renderView(view);
	}

	public Response actionExport() throws IOException{
		/*---- Insert your code here... ----*/	
		String id = Igrp.getInstance().getRequest().getParameter("id");
		if(id!=null && !id.equals("")){
			Action page = new Action().findOne(Integer.parseInt(id));
			ImportExportApp iea = new ImportExportApp();
			if(iea.validateExportPage(page)){
				iea.ExportPage(page);
				String pathJar = Config.getPathExport()+page.getApplication().getDad().toLowerCase()+File.separator+page.getPage()+".page.jar";
				FileHelper.createDiretory(Config.getPathExport()+page.getApplication().getDad().toLowerCase());
				JarUnJarFile.saveJarFiles(pathJar, iea.getFilesPageClasses(),9);			
				
				//insert data on import/export table
				ImportExportDAO ie_dao = new ImportExportDAO(page.getPage(), Config.getUserName(), DateHelper.getCurrentDataTime(), "Export");
				ie_dao = ie_dao.insert();
				
				return this.sendFile(new File(pathJar), page.getPage()+".page", "application/jar", true);
			}
			Igrp.getInstance().getFlashMessage().addMessage(FlashMessage.WARNING,FlashMessage.WARNING_EXPORT_PAGE);
		}else {
			Igrp.getInstance().getFlashMessage().addMessage(FlashMessage.ERROR,FlashMessage.MESSAGE_ERROR);
		}
		return this.redirect("igrp","ListaPage","index");
			/*---- End ----*/
	}
}
