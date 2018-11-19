package nosi.webapps.igrp.pages.pesquisarutilizador;

import nosi.core.webapp.Controller;
import nosi.core.webapp.databse.helpers.ResultSet;
import nosi.core.webapp.databse.helpers.QueryInterface;
import java.io.IOException;
import nosi.core.webapp.Core;
import nosi.core.webapp.Response;
/*----#start-code(packages_import)----*/
import nosi.core.webapp.Igrp;
import nosi.webapps.igrp.dao.Application;
import nosi.webapps.igrp.dao.Organization;
import nosi.webapps.igrp.dao.Profile;
import nosi.webapps.igrp.dao.ProfileType;
import nosi.webapps.igrp.dao.User;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

/*----#end-code----*/
		
public class PesquisarUtilizadorController extends Controller {
	public Response actionIndex() throws IOException, IllegalArgumentException, IllegalAccessException{
		PesquisarUtilizador model = new PesquisarUtilizador();
		model.load();
		PesquisarUtilizadorView view = new PesquisarUtilizadorView();
		view.nome.setParam(true);
		view.tb_email.setParam(true);
		view.id.setParam(true);
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		model.loadTable_1(Core.query(null,"SELECT '1' as ativo,'Anim voluptatem labore aperiam accusantium unde vo' as nominho,'1' as range_1,'Sit sit accusantium sit voluptatem stract aliqua iste deserunt elit rem deserunt elit ipsum deserunt' as nome,'Iste officia ipsum natus labore accusantium magna sit lorem sit laudantium unde officia natus mollit' as tb_email,'Deserunt labore accusantium aliqua consectetur des' as perfile,'1' as id "));
		view.aplicacao.setQuery(Core.query(null,"SELECT 'id' as ID,'name' as NAME "));
		view.organica.setQuery(Core.query(null,"SELECT 'id' as ID,'name' as NAME "));
		view.perfil.setQuery(Core.query(null,"SELECT 'id' as ID,'name' as NAME "));
		  ----#gen-example */
		/*----#start-code(index)----*/
		
   		ArrayList<PesquisarUtilizador.Table_1> lista = new ArrayList<>();

      	int idApp = Core.toInt(model.getAplicacao());
		int idOrg = Core.toInt(model.getOrganica());
		int idProf = Core.toInt(model.getPerfil());

		Profile prof = new Profile();
		List<Profile> profiles = null;

		ProfileType pp = new ProfileType().findOne(Core.getCurrentProfile());
		if (pp != null && pp.getCode().equalsIgnoreCase("ADMIN")) {
			profiles = prof.find().andWhere("type", "=", "PROF").andWhere("user.user_name", "=", model.getUsername())
					.andWhere("organization", "=", idOrg != 0 ? idOrg : null)
					.andWhere("profileType", "=", idProf != 0 ? idProf : null)
					.andWhere("profileType.application", "=", idApp != 0 ? idApp : null)
					.andWhere("user.email", "=", model.getEmail()).all();
		} else {
			Application app = Core.getCurrentApp();
			profiles = prof.find().andWhere("type", "=", "PROF").andWhere("user.user_name", "=", model.getUsername())
					.andWhere("organization", "=", idOrg != 0 ? idOrg : null)
					.andWhere("profileType", "=", idProf != 0 ? idProf : null)
					.andWhere("profileType.application", "=", idApp != 0 ? idApp : app.getId())
					.andWhere("user.email", "=", model.getEmail()).all();
		}
		// Preenchendo a tabela
		for (Profile p : profiles) {
			PesquisarUtilizador.Table_1 table1 = new PesquisarUtilizador.Table_1();
			
			int status = p.getUser().getStatus();
			if(status == 0) {
				table1.setAtivo(1);
				table1.setAtivo_check(0);
			}else {
				table1.setAtivo(status);
				table1.setAtivo_check(status);
			}
			
			table1.setTb_email(p.getUser().getEmail());
			table1.setNome(p.getUser().getUser_name());
			table1.setNominho(p.getUser().getName());
			table1.setPerfile(p.getProfileType().getApplication().getName() + "/"
					+ p.getProfileType().getOrganization().getName() + "/" + p.getProfileType().getDescr());
			table1.setId("" + p.getId());
			lista.add(table1);
		}	

		switch (this.getConfig().getAutenticationType()) {
		case "ldap":
			view.btn_adicionar_utilizador.setLink("igrp", "LdapUser", "index");
			break;
		case "db":
		default:
			view.btn_adicionar_utilizador.setLink("igrp", "PesquisarUtilizador", "adicionar_utilizador");
		}

	
		view.aplicacao.setValue(new Application().getListApps());
		view.organica.setValue(new Organization().getListOrganizations(idApp));
		view.perfil.setValue(new ProfileType().getListProfiles(idApp, idOrg));
		
		view.nome.setLabel("Username");
		view.nominho.setLabel("Nome");		
		
		view.table_1.addData(lista);

		view.btn_assiocar_etapa.setVisible(false);
		/*----#end-code----*/
		view.setModel(model);
		return this.renderView(view);	
	}
	
	public Response actionConvidar() throws IOException, IllegalArgumentException, IllegalAccessException{
		PesquisarUtilizador model = new PesquisarUtilizador();
		model.load();
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		 this.addQueryString("p_id","12"); //to send a query string in the URL
		 this.addQueryString("p_nome",Core.getParam("p_nome"));
		 this.addQueryString("p_tb_email",Core.getParam("p_tb_email"));
		 this.addQueryString("p_id",Core.getParam("p_id"));
		 return this.forward("igrp","NovoUtilizador","index", this.queryString()); //if submit, loads the values  ----#gen-example */
		/*----#start-code(convidar)----*/
   
      // this.addQueryString("p_aplicacao",  model.getAplicacao());
        // this.addQueryString("p_organica",  model.getOrganica());
         //this.addQueryString("p_perfil",  model.getPerfil());
		if (Core.isNotNull(model.getEmail())) {
			User u = new User().find().andWhere("email", "=", model.getEmail()).one();
			if (Core.isNotNull(u)) {
               	this.addQueryString("id", u.getId());
				return this.redirect("igrp", "NovoUtilizador", "index", this.queryString());
			}
		}     	
    
      
		/*----#end-code----*/
		return this.redirect("igrp","NovoUtilizador","index", this.queryString());	
	}
	
	public Response actionAdicionar_utilizador() throws IOException, IllegalArgumentException, IllegalAccessException{
		PesquisarUtilizador model = new PesquisarUtilizador();
		model.load();
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		 this.addQueryString("p_id","12"); //to send a query string in the URL
		 this.addQueryString("p_nome",Core.getParam("p_nome"));
		 this.addQueryString("p_tb_email",Core.getParam("p_tb_email"));
		 this.addQueryString("p_id",Core.getParam("p_id"));
		 return this.forward("igrp","RegistarUtilizador","index", this.queryString()); //if submit, loads the values  ----#gen-example */
		/*----#start-code(adicionar_utilizador)----*/

		/*----#end-code----*/
		return this.redirect("igrp","RegistarUtilizador","index", this.queryString());	
	}
	
	public Response actionPesquisar() throws IOException, IllegalArgumentException, IllegalAccessException{
		PesquisarUtilizador model = new PesquisarUtilizador();
		model.load();
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		 this.addQueryString("p_id","12"); //to send a query string in the URL
		 this.addQueryString("p_nome",Core.getParam("p_nome"));
		 this.addQueryString("p_tb_email",Core.getParam("p_tb_email"));
		 this.addQueryString("p_id",Core.getParam("p_id"));
		 return this.forward("igrp","PesquisarUtilizador","index", this.queryString()); //if submit, loads the values  ----#gen-example */
		/*----#start-code(pesquisar)----*/
	
		if (Igrp.getMethod().equalsIgnoreCase("post")) {		
			return this.forward("igrp", "PesquisarUtilizador", "index");
		}
	
		/*----#end-code----*/
		return this.redirect("igrp","PesquisarUtilizador","index", this.queryString());	
	}
	
	public Response actionEditar() throws IOException, IllegalArgumentException, IllegalAccessException{
		PesquisarUtilizador model = new PesquisarUtilizador();
		model.load();
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		 this.addQueryString("p_id","12"); //to send a query string in the URL
		 this.addQueryString("p_nome",Core.getParam("p_nome"));
		 this.addQueryString("p_tb_email",Core.getParam("p_tb_email"));
		 this.addQueryString("p_id",Core.getParam("p_id"));
		 return this.forward("igrp","RegistarUtilizador","index", this.queryString()); //if submit, loads the values  ----#gen-example */
		/*----#start-code(editar)----*/
		 
      if (Core.isNotNull(Core.getParam("p_tb_email"))) {
			User u = new User().find().andWhere("email", "=", Core.getParam("p_tb_email")).one();
			if (Core.isNotNull(u)) {
              this.addQueryString("p_id", u.getId());
				return this.redirect("igrp", "RegistarUtilizador", "editar", this.queryString());
			}
		}     	
      
		/*----#end-code----*/
		return this.redirect("igrp","RegistarUtilizador","index", this.queryString());	
	}
	
	public Response actionAssiocar_menu() throws IOException, IllegalArgumentException, IllegalAccessException{
		PesquisarUtilizador model = new PesquisarUtilizador();
		model.load();
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		 this.addQueryString("p_id","12"); //to send a query string in the URL
		 this.addQueryString("p_nome",Core.getParam("p_nome"));
		 this.addQueryString("p_tb_email",Core.getParam("p_tb_email"));
		 this.addQueryString("p_id",Core.getParam("p_id"));
		 return this.forward("igrp","PesquisarUtilizador","index", this.queryString()); //if submit, loads the values  ----#gen-example */
		/*----#start-code(assiocar_menu)----*/
		this.addQueryString("p_id",Core.getParamInt("p_id"))
			.addQueryString("userEmail", Core.getParam("p_tb_email"))
			.addQueryString("p_type", "user")
			.addQueryString("env_fk",model.getAplicacao());  ;
	   	return this.redirect("igrp","MenuOrganica","index", this.queryString());
		
		/*----#end-code----*/
			
	}
	
	public Response actionAssiocar_transacao() throws IOException, IllegalArgumentException, IllegalAccessException{
		PesquisarUtilizador model = new PesquisarUtilizador();
		model.load();
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		 this.addQueryString("p_id","12"); //to send a query string in the URL
		 this.addQueryString("p_nome",Core.getParam("p_nome"));
		 this.addQueryString("p_tb_email",Core.getParam("p_tb_email"));
		 this.addQueryString("p_id",Core.getParam("p_id"));
		 return this.forward("igrp","PesquisarUtilizador","index", this.queryString()); //if submit, loads the values  ----#gen-example */
		/*----#start-code(assiocar_transacao)----*/
		this.addQueryString("p_id",Core.getParamInt("p_id"))
			.addQueryString("userEmail", Core.getParam("p_tb_email"))
			.addQueryString("p_type", "user");		
      	return this.redirect("igrp","TransacaoOrganica","index", this.queryString());
		/*----#end-code----*/
			
	}
	
	public Response actionAssiocar_etapa() throws IOException, IllegalArgumentException, IllegalAccessException{
		PesquisarUtilizador model = new PesquisarUtilizador();
		model.load();
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		 this.addQueryString("p_id","12"); //to send a query string in the URL
		 this.addQueryString("p_nome",Core.getParam("p_nome"));
		 this.addQueryString("p_tb_email",Core.getParam("p_tb_email"));
		 this.addQueryString("p_id",Core.getParam("p_id"));
		 return this.forward("igrp","PesquisarUtilizador","index", this.queryString()); //if submit, loads the values  ----#gen-example */
		/*----#start-code(assiocar_etapa)----*/
		Profile p = new Profile().findOne(Core.getParamInt("p_id"));
      	if(p!=null)
	          this.addQueryString("p_id",p.getProfileType().getId());
		this.addQueryString("type", "user").addQueryString("userEmail",Core.getParam("p_tb_email"));
      	return this.redirect("igrp","Etapaaccess","index", this.queryString());
		/*----#end-code----*/
			
	}
	
	public Response actionConvidar_user() throws IOException, IllegalArgumentException, IllegalAccessException{
		PesquisarUtilizador model = new PesquisarUtilizador();
		model.load();
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		 this.addQueryString("p_id","12"); //to send a query string in the URL
		 this.addQueryString("p_nome",Core.getParam("p_nome"));
		 this.addQueryString("p_tb_email",Core.getParam("p_tb_email"));
		 this.addQueryString("p_id",Core.getParam("p_id"));
		 return this.forward("igrp","NovoUtilizador","index", this.queryString()); //if submit, loads the values  ----#gen-example */
		/*----#start-code(convidar_user)----*/
		Integer id = Core.getParamInt("p_id");		
		Profile p = new Profile().findOne(id);
	 	if(p!=null)
	          this.addQueryString("p_id",p.getProfileType().getId());
  		User u = new User().find().andWhere("email", "=", Core.getParam("tb_email")).one();
			if (u != null) 
			  this.addQueryString("id",u.getId());
   
		return this.forward("igrp","NovoUtilizador","index", this.queryString()); 
		/*----#end-code----*/
			
	}
	
	public Response actionEliminar() throws IOException, IllegalArgumentException, IllegalAccessException{
		PesquisarUtilizador model = new PesquisarUtilizador();
		model.load();
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		 this.addQueryString("p_id","12"); //to send a query string in the URL
		 this.addQueryString("p_nome",Core.getParam("p_nome"));
		 this.addQueryString("p_tb_email",Core.getParam("p_tb_email"));
		 this.addQueryString("p_id",Core.getParam("p_id"));
		 return this.forward("igrp","PesquisarUtilizador","index", this.queryString()); //if submit, loads the values  ----#gen-example */
		/*----#start-code(eliminar)----*/
		String id = Core.getParam("p_id");
		if (id != null) {
			Profile p = new Profile().findOne(Integer.parseInt(id));
			p.setType("INATIVE_" + p.getType());
			p = p.update();
			if (p != null) {
				Core.setMessageSuccess();
				return this.redirect("igrp", "PesquisarUtilizador", "index");
			}
		}
      
		Core.setMessageError();     
		/*----#end-code----*/
		return this.redirect("igrp","PesquisarUtilizador","index", this.queryString());	
	}
	
/*----#start-code(custom_actions)----*/
	public Response actionListaGeral() throws IOException, IllegalArgumentException, IllegalAccessException {
		PesquisarUtilizador model = new PesquisarUtilizador();
		if (Igrp.getInstance().getRequest().getMethod().toUpperCase().equals("POST")) {
			model.load();
		}
		ArrayList<PesquisarUtilizador.Table_1> lista = new ArrayList<>();
		// condicao para pesquisar com filtros
		List<User> users = new ArrayList<>();

		ProfileType pp = new ProfileType().findOne(Core.getCurrentProfile());
		if (pp != null && pp.getCode().equalsIgnoreCase("ADMIN")) {
			users = new User().find().andWhere("email", "=", model.getEmail())
					.andWhere("user_name", "=", model.getUsername()).all();
		}
		// Preenchendo a tabela
		for (User p : users) {
			PesquisarUtilizador.Table_1 table1 = new PesquisarUtilizador.Table_1();
			table1.setTb_email(p.getEmail());
			table1.setNome(p.getUser_name());
			table1.setNominho(p.getName());
			table1.setPerfile(p.getStatus() == 1 ? "ATIVO" : "INATIVO");
			table1.setId("" + p.getId());
			lista.add(table1);
		}
		// Alimentando o selectorOption (Aplicacao, organica, e menuPrincipal)
		PesquisarUtilizadorView view = new PesquisarUtilizadorView();
		view.aplicacao.setVisible(false);
		view.perfil.setVisible(false);
		view.organica.setVisible(false);
		view.id.setParam(true);
		view.email.setParam(true);
		view.btn_adicionar_utilizador.setVisible(false);

		view.btn_eliminar.setLink("eliminarUser");
		view.btn_eliminar.setTitle("Alterar Estado");
		view.btn_eliminar.setImg("");

		switch (this.getConfig().getAutenticationType()) {
		case "ldap":
			view.btn_editar.setLink("igrp", "ldap-user", "index_");
			view.btn_editar.setTarget("_self");
			break;
		case "db":
		default: {
			view.btn_editar.setLink("editarUser");
			view.btn_editar.setTarget("submit");
		}
		}

		view.btn_convidar_user.setTarget("submit");
		view.perfil.setLabel("Estado");
		view.nome.setLabel("Username");
		view.nominho.setLabel("Nome");
		view.btn_pesquisar.setLink("listaGeral");
		view.table_1.addData(lista);
		view.table_1.setTitle("Lista Geral de Utilizadores");
     view.setModel(model);
		return this.renderView(view);
	}

	public Response actionEditarUser() throws IOException {

		if (Igrp.getMethod().equalsIgnoreCase("post")) {
			String id = Igrp.getInstance().getRequest().getParameter("p_id");
			if (id != null) {
				User p = new User().findOne(Integer.parseInt(id));
				if (p != null) {
					return this.redirect("igrp", "RegistarUtilizador", "editar", "p_id=" + p.getId());
				}
			}
		}
		return this.redirectError();

	}

	public Response actionEliminarUser() throws IOException {

		String id = Igrp.getInstance().getRequest().getParameter("p_id");
		if (id != null) {
			User p = new User().findOne(Integer.parseInt(id));
			if (p.getStatus() == 1)
				p.setStatus(0);
			else
				p.setStatus(1);
			p = p.update();
			if (p != null) {
				Core.setMessageSuccess();
				return this.redirect("igrp", "PesquisarUtilizador", "lista_geral");
			}
		}
		return this.redirectError();

	}
  
    public Response actionChangeStatus(){
      this.format = Response.FORMAT_JSON;
      String id = Core.getParam("p_id");
      String status = Core.getParam("p_status_page");
      boolean response = false;
      try {
          if(id != null) {
              User u = new User().find().andWhere("user_name", "=", id).one();
              if(u != null) {
                  u.setStatus(Core.toInt(status));
                  u = u.update();
                  if(u != null)
                      response = true;
              }
          }
      }catch(Exception e) {

      }

      JSONObject json = new JSONObject();
      json.put("status", response);     

      return this.renderView(json.toString());
    }	
	/*----#end-code----*/
}
