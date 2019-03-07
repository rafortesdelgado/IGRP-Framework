package nosi.webapps.igrp.pages.transacaoorganica;

import nosi.core.webapp.Controller;
import nosi.core.webapp.databse.helpers.ResultSet;
import nosi.core.webapp.databse.helpers.QueryInterface;
import java.io.IOException;
import nosi.core.webapp.Core;
import nosi.core.webapp.Response;
/*----#start-code(packages_import)----*/
import nosi.webapps.igrp.dao.Organization;
import nosi.webapps.igrp.dao.Profile;
import nosi.webapps.igrp.dao.ProfileType;
import nosi.webapps.igrp.dao.Transaction;
import nosi.webapps.igrp.dao.User;
import java.util.ArrayList;
import java.util.Comparator;
import nosi.core.webapp.helpers.CheckBoxHelper;
import java.util.List;
/*----#end-code----*/
		
public class TransacaoOrganicaController extends Controller {
	public Response actionIndex() throws IOException, IllegalArgumentException, IllegalAccessException{
		TransacaoOrganica model = new TransacaoOrganica();
		model.load();
		TransacaoOrganicaView view = new TransacaoOrganicaView();
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		model.loadTable_1(Core.query(null,"SELECT '1' as transacao,'Mollit magna ipsum accusantium' as nome "));
		  ----#gen-example */
		/*----#start-code(index)----*/
		int id=model.getId();
        String type= model.getType();
	
		if(Core.isInt(id) && Core.isNotNull(type)){		
			
			ArrayList<TransacaoOrganica.Table_1> data = new ArrayList<>();
			List<Transaction> transactions = null;
			User user = null;
			Profile profile = null;
			if(type.equals("org")){
				Organization org = Core.findOrganizationById(id);
				transactions = new Organization().getOrgTransaction(org.getApplication().getId(),org.getId());
				view.btn_gestao_de_transacoes.addParameter("p_aplicacao", org.getApplication().getId());
			}else if(type.equals("perfil")){
				ProfileType p = new ProfileType().findOne(id);
				if(p.getOrganization()!=null)
					transactions = new Organization().getPerfilTransaction(p.getOrganization().getId(),p.getId());
				else
					transactions = new Organization().getPerfilTransaction(1,p.getId());
			} else if(type.equalsIgnoreCase("user")) {
				profile = new Profile().findOne(id);
		      	user = Core.findUserByEmail(Core.getParam("userEmail"));
		      	if(user!=null && profile!=null)
					transactions = new Organization().getOrgTransactionByUser(profile.getOrganization().getId(),user.getId());
			}         
			for(Transaction t:transactions){
				TransacaoOrganica.Table_1 table =new TransacaoOrganica.Table_1();
				table.setTransacao(t.getId());
				table.setNome(t.getDescr()+" ("+t.getCode()+")");
				if(t.isInserted()){
					table.setTransacao_check(t.getId());
				}else{
					table.setTransacao_check(-1);
				}
				data.add(table);
				data.sort(Comparator.comparing(TransacaoOrganica.Table_1::getTransacao_check).reversed());
			}
			
			if(model.getType().equals("user") && user!=null && profile!=null) {
				view.btn_gravar.addParameter("user_id",  user.getId())
				.addParameter("userEmail",  user.getEmail())
				.addParameter("org_id", profile.getOrganization().getId())
				.addParameter("prof_id", profile.getProfileType().getId());
			}
			view.table_1.addData(data);
		}
	
		/*----#end-code----*/
		view.setModel(model);
		return this.renderView(view);	
	}
	
	public Response actionGravar() throws IOException, IllegalArgumentException, IllegalAccessException{
		TransacaoOrganica model = new TransacaoOrganica();
		model.load();
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		 this.addQueryString("p_id","12"); //to send a query string in the URL
		 return this.forward("igrp","Dominio","index", model, this.queryString()); //if submit, loads the values  ----#gen-example */
		/*----#start-code(gravar)----*/
		if(Core.isInt(model.getId()) && Core.isNotNull(model.getType())){
			this.deleteOldTransactions(model);
			this.assocNewsTransactios(model);
		}
		this.addQueryString("userEmail",Core.getParam("userEmail"));	
	 return this.forward("igrp","TransacaoOrganica","index", this.queryString());
		/*----#end-code----*/
			
	}
	
	public Response actionGestao_de_transacoes() throws IOException, IllegalArgumentException, IllegalAccessException{
		TransacaoOrganica model = new TransacaoOrganica();
		model.load();
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		 this.addQueryString("p_id","12"); //to send a query string in the URL
		 return this.forward("igrp","Transaccao","index", model, this.queryString()); //if submit, loads the values  ----#gen-example */
		/*----#start-code(gestao_de_transacoes)----*/
		 this.addQueryString("p_aplicacao",Core.getParam("p_aplicacao"));
		
		/*----#end-code----*/
		return this.redirect("igrp","Transaccao","index", this.queryString());	
	}
	
/*----#start-code(custom_actions)----*/

	private User userAdmin = new User().getUserAdmin();
	private ProfileType profAdmin = new ProfileType().getProfileAdmin();
	
	private void deleteOldTransactions(TransacaoOrganica model) {
		Organization organization = new Organization();
		Profile profD = new Profile();
		List<ProfileType> list = null;
		if(model.getType().equals("org")){
			organization = new Organization().findOne(model.getId());
			profD.setOrganization(organization);
			profD.setType("TRANS");
			profD.setProfileType(profAdmin);
			profD.setUser(userAdmin);
			profD.deleteAllProfile();
			list = new ProfileType().find().andWhere("organization.id", "=", organization.getId()).all();
			if (list != null && list.size() > 0) {
				list.sort((o1, o2) -> {
					if (o1.getId() > o2.getId())
						return 1;
					else if (o1.getId() < o2.getId())
						return -1;
					return 0;
				});
				ProfileType pAux = list.get(0);
				Profile pAux2 = new Profile();
				pAux2.setOrganization(organization);
				pAux2.setType("TRANS");
				pAux2.setUser(userAdmin);
				pAux2.setProfileType(pAux);
				pAux2.deleteAllProfile();
			}
		}else if(model.getType().equals("perfil")){
			ProfileType pt = new ProfileType().findOne(model.getId());
			profD.setOrganization(pt.getOrganization());
			profD.setType("TRANS");
			profD.setUser(userAdmin);
			profD.setProfileType(new ProfileType().findOne(model.getId()));
			profD.deleteAllProfile();
		}else if (model.getType().equals("user")) {
			profD.setOrganization(new Organization().findOne(Core.getParamInt("org_id")));
			profD.setType("TRANS_USER");
			profD.setUser(new User().findOne(Core.getParamInt("user_id")));
			profD.setProfileType(new ProfileType().findOne(Core.getParamInt("prof_id")));
			profD.deleteAllProfile();
		}
	}
	
	
	

	private void assocNewsTransactios(TransacaoOrganica model) {
			
		CheckBoxHelper cb = Core.extractCheckBox(Core.getParamArray("p_transacao"), Core.getParamArray("p_transacao_check"));
		if(cb.getChekedIds().size()>0){
			List<ProfileType> list = null;
            boolean sucess=true;
			for(String x:cb.getChekedIds()){
				Profile prof = new Profile();
				prof.setUser(userAdmin);
				prof.setType("TRANS");
				prof.setType_fk(Integer.parseInt(x.toString()));
				if(model.getType().equals("org")){
					Organization auxOrg = new Organization().findOne(model.getId());
					prof.setOrganization(auxOrg);
					prof.setProfileType(profAdmin);
					list = new ProfileType().find().andWhere("organization.id", "=", auxOrg.getId()).all();
					if (list != null && list.size() > 0) {
						list.sort((o1, o2) -> {
							if (o1.getId() > o2.getId())
								return 1;
							else if (o1.getId() < o2.getId())
								return -1;
							return 0;
						});
						ProfileType pAux = list.get(0);
						Profile pAux2 = new Profile();
						pAux2.setUser(userAdmin);
						pAux2.setType("TRANS");		
						pAux2.setType_fk(Integer.parseInt(x.toString()));
						pAux2.setOrganization(auxOrg);
						pAux2.setProfileType(pAux);
						pAux2.insert();
					}
				}else if(model.getType().equals("perfil")){
					ProfileType p = new ProfileType().findOne(model.getId());
					prof.setOrganization(p.getOrganization());
					prof.setProfileType(new ProfileType().findOne(model.getId()));
				}else if (model.getType().equals("user")) {
					prof.setType("TRANS_USER");
					prof.setOrganization(new Organization().findOne(Core.getParamInt("org_id")));
					prof.setUser(new User().findOne(Core.getParamInt("user_id")));
					prof.setProfileType(new ProfileType().findOne(Core.getParamInt("prof_id")));
				}
				prof = prof.insert();
              if(Core.isNull(prof)){
                sucess=false;
              }
			}
          if(sucess){
            Core.setMessageSuccess();
          }else
            Core.setMessageError();
          
		}		
   			
	}
	/*----#end-code----*/
}
