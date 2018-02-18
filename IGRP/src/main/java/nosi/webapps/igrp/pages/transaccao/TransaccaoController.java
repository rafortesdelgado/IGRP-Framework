
package nosi.webapps.igrp.pages.transaccao;
/*----#START-PRESERVED-AREA(PACKAGES_IMPORT)----*/
import nosi.core.webapp.Controller;
import nosi.core.webapp.FlashMessage;
import nosi.core.webapp.Igrp;
import nosi.core.webapp.Response;
import nosi.core.webapp.Core;
import nosi.webapps.igrp.dao.Application;
import nosi.webapps.igrp.dao.Transaction;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONException;
import nosi.core.webapp.Core;
import org.json.JSONObject;
import com.google.gson.Gson;
import java.util.List;
import static nosi.core.i18n.Translator.gt;
/*----#END-PRESERVED-AREA----*/

public class TransaccaoController extends Controller {		


	public Response actionIndex() throws IOException, IllegalArgumentException, IllegalAccessException{
		/*----#START-PRESERVED-AREA(INDEX)----*/
		Transaccao model = new Transaccao();		
		ArrayList<Transaccao.Table_1> table_1 = new ArrayList<>();
		Transaction trans = new Transaction();
		if(Igrp.getMethod().equalsIgnoreCase("post")){
			model.load();	
		}
				
			
	
		List<Transaction> list =trans.find()
				.andWhere("application", "=", Core.isNotNull(model.getAplicacao())?Integer.parseInt(model.getAplicacao()):null)
				.andWhere("code", "=", Core.isNotNull(model.getFiltro_codigo())?model.getFiltro_codigo():null)
				.all();
		for(Transaction t:list){
			Transaccao.Table_1 table = new Transaccao.Table_1();
			table.setCodigo(t.getCode());
			table.setDescricao(t.getDescr());
			int check = t.getStatus() == 1 ? t.getStatus() : -1;
            table.setStatus(t.getStatus());
          	table.setStatus_check(check);
			table_1.add(table);
		}		
		
		TransaccaoView view = new TransaccaoView(model);
		view.aplicacao.setValue(new Application().getListApps());
//		view.organica.setValue(new Organization().getListMyOrganizations());
		view.table_1.addData(table_1);
		view.codigo.setParam(true);
		return this.renderView(view);
		/*----#END-PRESERVED-AREA----*/
	}


	public Response actionEditar() throws IOException{
		/*----#START-PRESERVED-AREA(EDITAR)----*/
    String codigo = Igrp.getInstance().getRequest().getParameter("codigo");
      if(codigo!=null && !codigo.equals(""))
			return this.redirect("igrp", "EditarTransacao", "index&codigo="+codigo);
		else
			return this.redirect("igrp", "error-page", "permission");
		/*----#END-PRESERVED-AREA----*/
	}
	

	public Response actionEliminar() throws IOException{
		/*----#START-PRESERVED-AREA(ELIMINAR)----*/
		String code = Igrp.getInstance().getRequest().getParameter("codigo");
		Transaction t = new Transaction();
		t = t.find().andWhere("code", "=", code).one();
		if(t.delete(t.getId()))
			Core.setMessageSuccess();
		else
			Core.setMessageError();
		return this.redirect("igrp","Transaccao","index");
		/*----#END-PRESERVED-AREA----*/
	}
	
	/*----#START-PRESERVED-AREA(CUSTOM_ACTIONS)----*/
	public Response actionChangeStatus() throws IOException, IllegalArgumentException, IllegalAccessException, JSONException {

		this.format = Response.FORMAT_JSON;		
		String code = Igrp.getInstance().getRequest().getParameter("p_code");
		String status = Igrp.getInstance().getRequest().getParameter("p_status");
		
		boolean response = false;

		if (Core.isNotNull(code)) {
			Transaction t = new Transaction();
			t.setCode(code);
			t = t.find().andWhere("code", "=", code).one();
			if(t!=null){
				t.setStatus(Integer.parseInt(status));
				if (t.update() != null)
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
