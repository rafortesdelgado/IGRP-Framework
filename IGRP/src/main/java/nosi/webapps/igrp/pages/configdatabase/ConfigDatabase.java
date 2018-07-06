package nosi.webapps.igrp.pages.configdatabase;

import nosi.core.gui.components.IGRPTable;
import nosi.core.webapp.Model;
import nosi.core.webapp.RParam;
import nosi.core.webapp.databse.helpers.BaseQueryInterface;



import java.util.ArrayList;
import java.util.List;

public class ConfigDatabase extends Model{		
	@RParam(rParamName = "p_sectionheader_1_text")
	private String sectionheader_1_text;
	@RParam(rParamName = "p_aplicacao")
	private String aplicacao;
	@RParam(rParamName = "p_tipo_base_dados")
	private String tipo_base_dados;
	@RParam(rParamName = "p_nome_de_conexao")
	private String nome_de_conexao;
	@RParam(rParamName = "p_config")
	private String config;
	@RParam(rParamName = "p_hostname")
	private String hostname;
	@RParam(rParamName = "p_port")
	private int port;
	@RParam(rParamName = "p_nome_de_bade_dados")
	private String nome_de_bade_dados;
	@RParam(rParamName = "p_credenciais")
	private String credenciais;
	@RParam(rParamName = "p_username")
	private String username;
	@RParam(rParamName = "p_password")
	private String password;
	@RParam(rParamName = "p_paragraph_1_text")
	private String paragraph_1_text;
	
	private List<Table_1> table_1 = new ArrayList<>();	
	public void setTable_1(List<Table_1> table_1){
		this.table_1 = table_1;
	}
	public List<Table_1> getTable_1(){
		return this.table_1;
	}
	@RParam(rParamName = "p_table_1_id")
	private String[] p_table_1_id;
	@RParam(rParamName = "p_table_1_del")
	private String[] p_table_1_del;
	
	public void setP_table_1_id(String[] p_table_1_id){
		this.p_table_1_id = p_table_1_id;
	}
	public String[] getP_table_1_id(){
		return this.p_table_1_id;
	}
	
	public void setP_table_1_del(String[] p_table_1_del){
		this.p_table_1_del = p_table_1_del;
	}
	public String[] getP_table_1_del(){
		return this.p_table_1_del;
	}
	
	public void setSectionheader_1_text(String sectionheader_1_text){
		this.sectionheader_1_text = sectionheader_1_text;
	}
	public String getSectionheader_1_text(){
		return this.sectionheader_1_text;
	}
	
	public void setAplicacao(String aplicacao){
		this.aplicacao = aplicacao;
	}
	public String getAplicacao(){
		return this.aplicacao;
	}
	
	public void setTipo_base_dados(String tipo_base_dados){
		this.tipo_base_dados = tipo_base_dados;
	}
	public String getTipo_base_dados(){
		return this.tipo_base_dados;
	}
	
	public void setNome_de_conexao(String nome_de_conexao){
		this.nome_de_conexao = nome_de_conexao;
	}
	public String getNome_de_conexao(){
		return this.nome_de_conexao;
	}
	
	public void setConfig(String config){
		this.config = config;
	}
	public String getConfig(){
		return this.config;
	}
	
	public void setHostname(String hostname){
		this.hostname = hostname;
	}
	public String getHostname(){
		return this.hostname;
	}
	
	public void setPort(int port){
		this.port = port;
	}
	public int getPort(){
		return this.port;
	}
	
	public void setNome_de_bade_dados(String nome_de_bade_dados){
		this.nome_de_bade_dados = nome_de_bade_dados;
	}
	public String getNome_de_bade_dados(){
		return this.nome_de_bade_dados;
	}
	
	public void setCredenciais(String credenciais){
		this.credenciais = credenciais;
	}
	public String getCredenciais(){
		return this.credenciais;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	public String getUsername(){
		return this.username;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	public String getPassword(){
		return this.password;
	}
	
	public void setParagraph_1_text(String paragraph_1_text){
		this.paragraph_1_text = paragraph_1_text;
	}
	public String getParagraph_1_text(){
		return this.paragraph_1_text;
	}


	public static class Table_1 extends IGRPTable.Table{
		private String nome_de_conexao_tabela;
		private String hostname_tabela;
		private int porta_tabela;
		private String nome_base_de_dados_tabela;
		private String user_name_tabela;
		private String tipo_de_base_de_dados_tabela;
		private String id;
		public void setNome_de_conexao_tabela(String nome_de_conexao_tabela){
			this.nome_de_conexao_tabela = nome_de_conexao_tabela;
		}
		public String getNome_de_conexao_tabela(){
			return this.nome_de_conexao_tabela;
		}

		public void setHostname_tabela(String hostname_tabela){
			this.hostname_tabela = hostname_tabela;
		}
		public String getHostname_tabela(){
			return this.hostname_tabela;
		}

		public void setPorta_tabela(int porta_tabela){
			this.porta_tabela = porta_tabela;
		}
		public int getPorta_tabela(){
			return this.porta_tabela;
		}

		public void setNome_base_de_dados_tabela(String nome_base_de_dados_tabela){
			this.nome_base_de_dados_tabela = nome_base_de_dados_tabela;
		}
		public String getNome_base_de_dados_tabela(){
			return this.nome_base_de_dados_tabela;
		}

		public void setUser_name_tabela(String user_name_tabela){
			this.user_name_tabela = user_name_tabela;
		}
		public String getUser_name_tabela(){
			return this.user_name_tabela;
		}

		public void setTipo_de_base_de_dados_tabela(String tipo_de_base_de_dados_tabela){
			this.tipo_de_base_de_dados_tabela = tipo_de_base_de_dados_tabela;
		}
		public String getTipo_de_base_de_dados_tabela(){
			return this.tipo_de_base_de_dados_tabela;
		}

		public void setId(String id){
			this.id = id;
		}
		public String getId(){
			return this.id;
		}

	}

	public void loadTable_1(BaseQueryInterface query) {
		this.setTable_1(this.loadTable(query,Table_1.class));
	}

}
