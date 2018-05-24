
package nosi.webapps.igrp_studio.pages.modulo;

import nosi.core.webapp.Controller;
import nosi.core.webapp.databse.helpers.ResultSet;
import nosi.core.webapp.databse.helpers.QueryInterface;

import static nosi.core.i18n.Translator.gt;

import java.io.IOException;
import nosi.core.webapp.Core;
import nosi.core.webapp.FlashMessage;
import nosi.core.webapp.Igrp;
import nosi.core.webapp.Response;
/*----#start-code(packages_import)----*/
import nosi.webapps.igrp.dao.Application;
import nosi.webapps.igrp.dao.Config_env;
import nosi.core.webapp.helpers.IgrpHelper;
import java.util.List;
/*----#end-code----*/


public class ModuloController extends Controller {		

	public Response actionIndex() throws IOException, IllegalArgumentException, IllegalAccessException{
		
		Modulo model = new Modulo();
		model.load();
		ModuloView view = new ModuloView();
		/*----#gen-example
		  This is an example of how you can implement your code:
		  In a .query(null,... change 'null' to your db connection name added in application builder.
		
		
		view.aplicacao.setQuery(Core.query(null,"SELECT 'id' as ID,'name' as NAME "));
		
		----#gen-example */
		/*----#start-code(index)----*/
		List<Application> l = new Application().findAll();
		view.aplicacao.setValue(IgrpHelper.toMap(l, "id", "name", "-- Selecionar --"));
		/*----#end-code----*/
		view.setModel(model);
		return this.renderView(view);	
	}
	
	public Response actionGravar() throws IOException, IllegalArgumentException, IllegalAccessException{
		
		Modulo model = new Modulo();
		model.load();
		/*----#gen-example
		  This is an example of how you can implement your code:
		  In a .query(null,... change 'null' to your db connection name added in application builder.
		
		 this.addQueryString("p_id","12"); //to send a query string in the URL
		 return this.forward("igrp_studio","Modulo","index", this.queryString()); //if submit, loads the values
		
		----#gen-example */
		/*----#start-code(gravar)----*/
		try {
			nosi.webapps.igrp.dao.Modulo m = new nosi.webapps.igrp.dao.Modulo();
			Application application = new Application();
			application.setId(Core.toInt(model.getAplicacao()));
			m.setApplication(application);
			m.setName(model.getModulo());
			this.addQueryString("p_aplicacao", model.getAplicacao());
			if (new nosi.webapps.igrp.dao.Modulo().find().andWhere("name", "=", model.getModulo()).andWhere("application", "=", Core.toInt(model.getAplicacao())).one() == null) {
				m = m.insert();
				if(m == null) {
					Core.setMessageError();
					return forward("igrp_studio","Modulo","index", this.queryString());
				}
				else
					Core.setMessageSuccess();
			} else {
				Core.setMessageWarning("Nome da conexão já existe");
				return this.forward("igrp_studio","Modulo","index", this.queryString());
			}
         
			
		}catch(Exception e) {
			Core.setMessageError();
			return forward("igrp_studio","Modulo","index", this.queryString());
		}
		/*----#end-code----*/
		return this.redirect("igrp_studio","Modulo","index", this.queryString());	
	}
	
	/*----#start-code(custom_actions)----*/
	
	/*----#end-code----*/
	}