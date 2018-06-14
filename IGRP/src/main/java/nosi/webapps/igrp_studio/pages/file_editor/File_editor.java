package nosi.webapps.igrp_studio.pages.file_editor;
import nosi.core.config.Config;
import nosi.core.gui.components.IGRPLink;

import nosi.core.webapp.Model;
import nosi.core.webapp.RParam;





public class File_editor extends Model{		
	@RParam(rParamName = "p_json_data")
	private IGRPLink json_data;
	@RParam(rParamName = "p_json_data_desc")
	private String json_data_desc;
	@RParam(rParamName = "p_save_url")
	private IGRPLink save_url;
	@RParam(rParamName = "p_save_url_desc")
	private String save_url_desc;
	
	public IGRPLink setJson_data(String app,String page,String action){
		this.json_data = new IGRPLink(app,page,action);
		return this.json_data;
	}
	public IGRPLink getJson_data(){
		return this.json_data;
	}
	public void setJson_data_desc(String json_data_desc){
		this.json_data_desc = json_data_desc;
	}
	public String getJson_data_desc(){
		return this.json_data_desc;
	}
	public IGRPLink setJson_data(String link){
		this.json_data = new IGRPLink(link);
		return this.json_data;
	}
	
	public IGRPLink setSave_url(String app,String page,String action){
		this.save_url = new IGRPLink(app,page,action);
		return this.save_url;
	}
	public IGRPLink getSave_url(){
		return this.save_url;
	}
	public void setSave_url_desc(String save_url_desc){
		this.save_url_desc = save_url_desc;
	}
	public String getSave_url_desc(){
		return this.save_url_desc;
	}
	public IGRPLink setSave_url(String link){
		this.save_url = new IGRPLink(link);
		return this.save_url;
	}



}