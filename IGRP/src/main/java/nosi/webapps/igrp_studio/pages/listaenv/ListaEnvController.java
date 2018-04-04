
package nosi.webapps.igrp_studio.pages.listaenv;

/*----#START-PRESERVED-AREA(PACKAGES_IMPORT)----*/
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import nosi.core.webapp.Core;
import java.util.Map;
import nosi.core.webapp.Controller;
import nosi.core.webapp.FlashMessage;
import nosi.core.webapp.Igrp;
import nosi.core.webapp.Response;
import nosi.core.webapp.export.app.ExportAppJava;
import nosi.core.webapp.helpers.DateHelper;
import nosi.core.webapp.helpers.FileHelper;
import nosi.core.webapp.helpers.ImportExportApp;
import nosi.core.webapp.helpers.JarUnJarFile;
import nosi.core.webapp.helpers.Permission;
import nosi.webapps.igrp.dao.Action;
import nosi.webapps.igrp.dao.Application;
import nosi.webapps.igrp.dao.Config_env;
import nosi.webapps.igrp.dao.ImportExportDAO;
import nosi.webapps.igrp.dao.User;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
/*----#END-PRESERVED-AREA----*/

public class ListaEnvController extends Controller {

	public Response actionIndex() throws IOException, IllegalArgumentException, IllegalAccessException {
		/*----#START-PRESERVED-AREA(INDEX)----*/
		ListaEnv model = new ListaEnv();
		ArrayList<ListaEnv.Table_1> lista = new ArrayList<>();
		Application app = new Application();
		if (Igrp.getInstance().getRequest().getMethod().toUpperCase().equals("POST")) {
			model.load();
			// app.setDad(model.getDad());
			// app.setName(model.getNome());
		}
		List<Application> apps = new ArrayList<>();
		User user = (User) Igrp.getInstance().getUser().getIdentity();
		String dad = new Permission().getCurrentEnv();
		if ("igrp".equalsIgnoreCase(dad)) {
			apps = app.find()
					// .andWhere("dad", "like", app.getDad())
					// .andWhere("name", "like", app.getName())
					.all();
		} else {
			apps = app.getListMyApp(user.getId());
		}

		for (Application a : apps) {
			if (!a.getDad().toLowerCase().equals("igrp")) {
				ListaEnv.Table_1 table = new ListaEnv.Table_1();
				table.setDad(a.getDad());
				table.setName("" + a.getName());
				table.setStatus(a.getStatus());
				if (a.getStatus() == 1) {
					table.setStatus_check(a.getStatus());
				} else
					table.setStatus_check(-1);
				table.setP_id("" + a.getId());
				table.setT_page_builder("igrp_studio", "ListaPage", "index&app=" + a.getId());
				table.setT_page_builder_desc("Page builder");
				lista.add(table);
			}
		}
		model.setTable_1(lista);

		ListaEnvView view = new ListaEnvView(model);
		view.table_1.addData(model.getTable_1());
		view.p_id.setParam(true);
		view.btn_eliminar.setVisible(false);
		return this.renderView(view);
		/*----#END-PRESERVED-AREA----*/
	}

	public Response actionImportar() throws IOException, IllegalArgumentException, IllegalAccessException {
		/*----#START-PRESERVED-AREA(IMPORTAR)----*/
		return this.redirect("igrp_studio", "ImportArquivo", "index&target=_blank");
		/*----#END-PRESERVED-AREA----*/
	}

	public Response actionNovo() throws IOException, IllegalArgumentException, IllegalAccessException {
		/*----#START-PRESERVED-AREA(NOVO)----*/
		return this.redirect("igrp_studio", "Env", "index&target=_blank");
		/*----#END-PRESERVED-AREA----*/
	}

	public Response actionEditar() throws IOException {
		/*----#START-PRESERVED-AREA(EDITAR)----*/
		String p_id = Igrp.getInstance().getRequest().getParameter("p_id");
		if (p_id != null && !p_id.equals("")) {
			return this.forward("igrp_studio", "Env", "editar&target=_blank&id=" + p_id);
		}
		return this.redirect("igrp_studio", "lista-env", "index&target=_blank");
		/*----#END-PRESERVED-AREA----*/
	}

	public Response actionEliminar() throws IOException {
		/*----#START-PRESERVED-AREA(ELIMINAR)----*/
		String id = Igrp.getInstance().getRequest().getParameter("p_id");
		Application app = new Application();
		if (app.delete(Integer.parseInt(id)))
			Igrp.getInstance().getFlashMessage().addMessage(FlashMessage.SUCCESS, FlashMessage.MESSAGE_SUCCESS);
		else
			Igrp.getInstance().getFlashMessage().addMessage(FlashMessage.ERROR, FlashMessage.MESSAGE_ERROR);
		return this.redirect("igrp_studio", "lista-env", "index");
		/*----#END-PRESERVED-AREA----*/
	}

	public Response actionConfigurar_base_dados() throws IOException, IllegalArgumentException, IllegalAccessException {
		/*----#START-PRESERVED-AREA(CONFIGURAR_BASE_DADOS)----*/
		String id = Igrp.getInstance().getRequest().getParameter("p_id");
		if (id != null) {
			return this.redirect("igrp", "ConfigDatabase", "index&target=_blank&p_aplicacao=" + id);
		}
		return this.forward("igrp_studio", "ListaEnv", "index");
		/*----#END-PRESERVED-AREA----*/
	}

	public Response actionExportar() throws IOException, IllegalArgumentException, IllegalAccessException {
		/*----#START-PRESERVED-AREA(EXPORTAR)----*/
		String id = Igrp.getInstance().getRequest().getParameter("p_id");
		if (id != null && !id.equals("")) {
			Application app = new Application().findOne(id);
			if (app != null) {
				// Insert data on Export/Import
				ImportExportDAO ie_dao = new ImportExportDAO(app.getName(), this.getConfig().getUserName(),
						DateHelper.getCurrentDataTime(), "Export");
				ie_dao = ie_dao.insert();
				return this.exportApp(app);
			} else {
				Igrp.getInstance().getFlashMessage().addMessage(FlashMessage.ERROR, FlashMessage.ERROR);
			}
		}
		return this.redirect("igrp_studio", "ListaEnv", "index");
		/*----#END-PRESERVED-AREA----*/
	}

	/*----#START-PRESERVED-AREA(CUSTOM_ACTIONS)----*/
	private Response exportApp(Application app) {
//		
//		for (Action a : new Action().find().andWhere("application", "=", app.getId()).all()) {
//			iea.putFilesPageConfig(a);
//		}
//
//		Map<String, String> files = iea.getFilesPageClasses();
//
//		for (Config_env configDb : new Config_env().find().andWhere("application", "=", app.getId()).all()) {
//			files.put("configHibernate/" + configDb.getName(),
//					this.getConfig().getBasePathClass() + configDb.getName() + ".cfg.xml");
//		}
//
//		if (iea.getFilesDaoClasses() != null)
//			files.putAll(iea.getFilesDaoClasses());
//
//		String pathConfigApp = this.getConfig().getPathExport() + "ConfigApp" + File.separator + app.getDad().toLowerCase();
//		try {
//			FileHelper.save(pathConfigApp, "Config" + app.getDad() + ".xml",
//					ImportExportApp.genereteXMLApplication(app));
//			FileHelper.save(pathConfigApp, "Config" + app.getDad() + "DB.xml",
//					ImportExportApp.generateXMLConfigDB(app));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		files.put("configDBApp/" + app.getDad().toLowerCase() + 
//				"/" + app.getDad().toLowerCase() + ".xml",
//				pathConfigApp + File.separator + "Config" + 
//				app.getDad().toLowerCase() + "DB.xml");
//		
//		files.put("configApp/" + app.getDad().toLowerCase() + "/" + app.getDad().toLowerCase() + ".xml",
//				pathConfigApp + File.separator + "Config" + app.getDad().toLowerCase() + ".xml");
//		
//		String pathJar = this.getConfig().getPathExport() + app.getDad().toLowerCase() + File.separator
//				+ app.getDad().toLowerCase() + ".app.jar";
//		
//		FileHelper.createDiretory(this.getConfig().getPathExport() + app.getDad().toLowerCase());
//		
//		/*String aux = this.getConfig().getPathExport() + app.getDad().toLowerCase(); 
//		aux = aux.replace(File.separator + app.getDad().toLowerCase(), ""); 
//		new File(aux).deleteOnExit();*/
//		
//		JarUnJarFile.saveJarFiles(pathJar, files, 9);
//		
//	//	System.out.println(pathJar); 
//		
//		return this.sendFile(new File(pathJar), app.getDad().toLowerCase() + ".app.jar", "application/jar", true);
		return this.xSend(new ExportAppJava(app.getId()).export(),app.getDad().toLowerCase() + ".app.jar", "application/jar", true);
	}

	public Response actionChangeStatus()
			throws IOException, IllegalArgumentException, IllegalAccessException, JSONException {

		this.format = Response.FORMAT_JSON;
		String idAplicacao = Igrp.getInstance().getRequest().getParameter("p_id");
		String status = Igrp.getInstance().getRequest().getParameter("p_status");
		boolean response = false;

		if (Core.isNotNull(idAplicacao)) {
			Application aplica_db = new Application().findOne(Integer.parseInt(idAplicacao));
			if (aplica_db != null) {
				aplica_db.setStatus(Integer.parseInt(status));
				if (aplica_db.update() != null)
					response = true;
			}
		}

		JSONObject json = new JSONObject();
		json.put("status", response);
		Gson res = new Gson();
		res.toJson(json);
		return this.renderView(json.toString());
	}
	/*----#END-PRESERVED-AREA----*/
}
