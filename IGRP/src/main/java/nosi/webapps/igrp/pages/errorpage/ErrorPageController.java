
package nosi.webapps.igrp.pages.errorpage;
import static nosi.core.i18n.Translator.gt;

import java.io.IOException;

import javax.servlet.RequestDispatcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*----#START-PRESERVED-AREA(PACKAGES_IMPORT)----*/
import nosi.core.webapp.Controller;
import nosi.core.webapp.Igrp;
import nosi.core.webapp.Response;
/*----#END-PRESERVED-AREA----*/

public class ErrorPageController extends Controller {		


	public Response actionIndex() throws IOException, IllegalArgumentException, IllegalAccessException{
		/*----#START-PRESERVED-AREA(INDEX)----*/
		ErrorPage model = new ErrorPage();
		if(Igrp.getMethod().equalsIgnoreCase("post")){
			model.load();
		}
		ErrorPageView view = new ErrorPageView(model);
		return this.renderView(view);
		/*----#END-PRESERVED-AREA----*/
	}

	/*----#START-PRESERVED-AREA(CUSTOM_ACTIONS)----*/
	private Logger logger = LogManager.getLogger(ErrorPageController.class);
	public Response actionException() throws IOException{
		if(Igrp.getInstance().getUser().isAuthenticated()){
			
			Exception e = (Exception)Igrp.getInstance().getRequest().getAttribute(RequestDispatcher.ERROR_EXCEPTION);
			//e.printStackTrace();
			
			String errorMsg = Igrp.getInstance().getRequest().getAttribute("javax.servlet.error.message") + ""; 
			
			logger.error(errorMsg);
			
			Igrp.getInstance().getFlashMessage().addMessage("error", errorMsg );
			Igrp.getInstance().getFlashMessage().addMessage("info", gt("Por favor contactar o serviço de HELPDESK para mais informações.(helpdesk@nosi.cv - Tel:2607973)"));
			ErrorPage model = new ErrorPage();
			ErrorPageView view = new ErrorPageView(model);
			return this.renderView(view);
		}
		else
			return this.redirect("igrp", "login", "login");
	}
	
	
	/*----#END-PRESERVED-AREA----*/
}