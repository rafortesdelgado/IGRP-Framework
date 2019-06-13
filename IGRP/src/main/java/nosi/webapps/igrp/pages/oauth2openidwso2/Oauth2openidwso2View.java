package nosi.webapps.igrp.pages.oauth2openidwso2;

import nosi.core.webapp.Model;
import nosi.core.webapp.View;
import nosi.core.gui.components.*;
import nosi.core.gui.fields.*;
import static nosi.core.i18n.Translator.gt;
import nosi.core.config.Config;

public class Oauth2openidwso2View extends View {

	public Field sectionheader_1_text;
	public IGRPForm box_1;
	public IGRPForm sectionheader_1;

	public IGRPToolsBar toolsbar_1;
	public IGRPToolsBar toolsbar_2;
	public IGRPButton btn_sign_in_;
	public IGRPButton btn_logout;

	public Oauth2openidwso2View(){

		this.setPageTitle("OAuth2OpenIdWso2");
			
		box_1 = new IGRPForm("box_1","");

		sectionheader_1 = new IGRPForm("sectionheader_1","");

		sectionheader_1_text = new TextField(model,"sectionheader_1_text");
		sectionheader_1_text.setLabel(gt(""));
		sectionheader_1_text.setValue(gt("<p style='text-align: center;'>igrp<strong>web</strong>&nbsp;<strong>OpenID</strong></p>"));
		sectionheader_1_text.propertie().add("type","text").add("name","p_sectionheader_1_text").add("maxlength","4000");
		

		toolsbar_1 = new IGRPToolsBar("toolsbar_1");
		toolsbar_2 = new IGRPToolsBar("toolsbar_2");

		btn_sign_in_ = new IGRPButton("Login","igrp","Oauth2openidwso2","sign_in_","submit","primary|fa-sign-in","","");
		btn_sign_in_.propertie.add("type","specific").add("rel","sign_in_");

		btn_logout = new IGRPButton("Logout","igrp","Oauth2openidwso2","logout","_blank","primary|fa-sign-out","","");
		btn_logout.propertie.add("type","specific").add("rel","logout");

		
	}
		
	@Override
	public void render(){
		

		sectionheader_1.addField(sectionheader_1_text);



		toolsbar_1.addButton(btn_sign_in_);
		toolsbar_2.addButton(btn_logout);
		this.addToPage(box_1);
		this.addToPage(sectionheader_1);
		this.addToPage(toolsbar_1);
		this.addToPage(toolsbar_2);
	}
		
	@Override
	public void setModel(Model model) {
			

		}
}
