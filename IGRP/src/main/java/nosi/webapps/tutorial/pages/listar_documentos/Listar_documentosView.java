package nosi.webapps.tutorial.pages.listar_documentos;

import nosi.core.webapp.Model;
import nosi.core.webapp.View;
import nosi.core.gui.components.*;
import nosi.core.gui.fields.*;
import static nosi.core.i18n.Translator.gt;
import nosi.core.webapp.Core;

public class Listar_documentosView extends View {

	public Field treemenu_1_link;
	public Field treemenu_1_link_desc;
	public Field treemenu_1_tmid;
	public Field treemenu_1_parent;
	public Field treemenu_1_icon;
	public Field treemenu_1_child;
	public Field treemenu_1_active;
	public Field sectionheader_1_text;
	public Field sectionheader_2_text;
	public Field form_1_plaintext_1;
	public Field id_rel;
	public Field atualizado_em;
	public IGRPTreeMenu treemenu_1;
	public IGRPSectionHeader sectionheader_1;
	public IGRPBox box_controll;
	public IGRPSectionHeader sectionheader_2;
	public IGRPForm form_1;
	public IGRPView view_1;

	public IGRPVerticalMenu documentos_relacionados;
	public IGRPButton btn_documentos_relacionados;

	public Listar_documentosView(){

		this.setPageTitle("Listar Documentos");
			
		treemenu_1 = new IGRPTreeMenu("treemenu_1","");

		sectionheader_1 = new IGRPSectionHeader("sectionheader_1","");

		box_controll = new IGRPBox("box_controll","");

		sectionheader_2 = new IGRPSectionHeader("sectionheader_2","");

		form_1 = new IGRPForm("form_1","");

		view_1 = new IGRPView("view_1","");

		treemenu_1_link = new LinkField(model,"treemenu_1_link");
		treemenu_1_link.setLabel(gt("Link"));
		treemenu_1_link_desc = new LinkField(model,"treemenu_1_link_desc");
		treemenu_1_link_desc.setLabel(gt("Link"));
		treemenu_1_link.propertie().add("type","link").add("maxlength","4000").add("target","_self").add("desc","true");
		
		treemenu_1_tmid = new TextField(model,"treemenu_1_tmid");
		treemenu_1_tmid.setLabel(gt("ID"));
		treemenu_1_tmid.propertie().add("type","text");
		
		treemenu_1_parent = new NumberField(model,"treemenu_1_parent");
		treemenu_1_parent.setLabel(gt("Parent ID"));
		treemenu_1_parent.propertie().add("type","number").add("java-type","Integer");
		
		treemenu_1_icon = new TextField(model,"treemenu_1_icon");
		treemenu_1_icon.setLabel(gt("Icon"));
		treemenu_1_icon.propertie().add("type","text");
		
		treemenu_1_child = new TextField(model,"treemenu_1_child");
		treemenu_1_child.setLabel(gt("Has child value(0/X)"));
		treemenu_1_child.propertie().add("type","text");
		
		treemenu_1_active = new TextField(model,"treemenu_1_active");
		treemenu_1_active.setLabel(gt("Is Active value(true/false)"));
		treemenu_1_active.propertie().add("type","text");
		
		sectionheader_1_text = new TextField(model,"sectionheader_1_text");
		sectionheader_1_text.setLabel(gt(""));
		sectionheader_1_text.setValue(gt("Sectionheader"));
		sectionheader_1_text.propertie().add("type","text").add("name","p_sectionheader_1_text").add("maxlength","4000");
		
		sectionheader_2_text = new TextField(model,"sectionheader_2_text");
		sectionheader_2_text.setLabel(gt(""));
		sectionheader_2_text.setValue(gt("<p><span style='font-size:12px;'>Documentos Relacionados</span></p>"));
		sectionheader_2_text.propertie().add("type","text").add("name","p_sectionheader_2_text").add("maxlength","4000");
		
		form_1_plaintext_1 = new PlainTextField(model,"form_1_plaintext_1");
		form_1_plaintext_1.setLabel(gt("Plaintext"));
		form_1_plaintext_1.propertie().add("name","p_form_1_plaintext_1").add("type","plaintext").add("clear","false").add("disable_output_escaping","true").add("html_class","ff-fix").add("maxlength","4000");
		
		id_rel = new HiddenField(model,"id_rel");
		id_rel.setLabel(gt(""));
		id_rel.propertie().add("name","p_id_rel").add("type","hidden").add("maxlength","250").add("java-type","").add("tag","id_rel");
		
		atualizado_em = new DateField(model,"atualizado_em");
		atualizado_em.setLabel(gt("Atualizado em:"));
		atualizado_em.propertie().add("name","p_atualizado_em").add("type","date").add("range","false").add("maxlength","250").add("class","info").add("img","fa-clock-o").add("showlabel","true");
		

		documentos_relacionados = new IGRPVerticalMenu("documentos_relacionados");

		btn_documentos_relacionados = new IGRPButton("","tutorial","Listar_documentos","documentos_relacionados","void","link|fa-angle-right","","");
		btn_documentos_relacionados.propertie.add("type","specific").add("rel","documentos_relacionados").add("refresh_components","");

		
	}
		
	@Override
	public void render(){
		
		treemenu_1.addField(treemenu_1_link);
		treemenu_1.addField(treemenu_1_link_desc);
		treemenu_1.addField(treemenu_1_tmid);
		treemenu_1.addField(treemenu_1_parent);
		treemenu_1.addField(treemenu_1_icon);
		treemenu_1.addField(treemenu_1_child);
		treemenu_1.addField(treemenu_1_active);

		sectionheader_1.addField(sectionheader_1_text);


		sectionheader_2.addField(sectionheader_2_text);

		form_1.addField(form_1_plaintext_1);
		form_1.addField(id_rel);

		view_1.addField(atualizado_em);


		documentos_relacionados.addButton(btn_documentos_relacionados);
		this.addToPage(treemenu_1);
		this.addToPage(sectionheader_1);
		this.addToPage(box_controll);
		this.addToPage(sectionheader_2);
		this.addToPage(form_1);
		this.addToPage(view_1);
		this.addToPage(documentos_relacionados);
	}
		
	@Override
	public void setModel(Model model) {
		
		treemenu_1_link.setValue(model);
		treemenu_1_link_desc.setValue(model);
		treemenu_1_tmid.setValue(model);
		treemenu_1_parent.setValue(model);
		treemenu_1_icon.setValue(model);
		treemenu_1_child.setValue(model);
		treemenu_1_active.setValue(model);
		form_1_plaintext_1.setValue(model);
		id_rel.setValue(model);
		atualizado_em.setValue(model);	

		treemenu_1.loadModel(((Listar_documentos) model).getTreemenu_1());
		}
}