/*-------------------------*/

/*Create Controller*/

package nosi.webapps.igrp.pages.session;
import nosi.core.webapp.Controller;
import nosi.core.webapp.Igrp;
import nosi.core.webapp.RParam;
import nosi.webapps.igrp.dao.Application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class SessionController extends Controller {		

	public void actionIndex(@RParam(rParamName = "dad") String dad) throws IOException{
		Session model = new Session();
		
		if(Igrp.getInstance().getRequest().getMethod().equals("POST")){
			System.out.println("Ok");
		}
		
		SessionView view = new SessionView(model);
		ArrayList<Session.Table_1> data = new ArrayList<>();
		for(Object obj:new nosi.webapps.igrp.dao.Session().getAll()){
			nosi.webapps.igrp.dao.Session s = (nosi.webapps.igrp.dao.Session)obj;
			Session.Table_1 table = new Session().new Table_1();
			table.setAplicacao(""+s.getEnvId());
			table.setData_fim(""+s.getEndTime());
			table.setData_inicio(""+s.getStartTime());
			table.setIp(s.getIpAddress());
			table.setUtilizador(s.getUserName());
			data.add(table);
		}
		view.table_1.addData(data);
		
		HashMap<Integer,String> applications =  new Application().getListApps();
		view.aplicacao.setValue(applications);
		
		HashMap<String, String> status = new HashMap<String,String>();
		status.put("", "--- Escolher estado ---");
		status.put("1", "Ativo");
		status.put("0", "Inativo");
		view.estado.setValue(status);
		
		view.btn_pesquisar.setLink("index&dad=" + dad);
		
		this.renderView(view);
	}

	public void actionPesquisar() throws IOException{
			this.redirect("igrp","Dominio","index");
	}
	
	public void actionVer_logs() throws IOException{
			this.redirect("igrp","Session","index");
	}
	
}
