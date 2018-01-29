
package nosi.webapps.igrp.pages._cons_proc;
/*----#START-PRESERVED-AREA(PACKAGES_IMPORT)----*/
import nosi.core.webapp.Controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nosi.core.webapp.Core;
import nosi.core.webapp.Response;
import nosi.core.webapp.activit.rest.ProcessDefinitionService;
import nosi.core.webapp.activit.rest.TaskServiceQuery;
import nosi.webapps.igrp.dao.Application;
import nosi.webapps.igrp.dao.Organization;
import nosi.core.webapp.Igrp;

/*----#END-PRESERVED-AREA----*/

public class _CONS_PROCController extends Controller {		


	public Response actionIndex() throws IOException, IllegalArgumentException, IllegalAccessException{
		/*----#START-PRESERVED-AREA(INDEX)----*/
		_CONS_PROC model = new _CONS_PROC();
		List<_CONS_PROC.Table_1> data = new ArrayList<>();
		
		if(Igrp.getMethod().equalsIgnoreCase("post")){
			model.load();
			TaskServiceQuery taskS = new TaskServiceQuery();
			if(Core.isNotNull(model.getProc_tp_fk())){
				taskS.addFilter("processDefinitionKey", model.getProc_tp_fk());
			}if(Core.isNotNull(model.getNum())){
				taskS.addFilter("processInstanceId", model.getNum());
			}
			if(Core.isNotNull(model.getUser_fk())){
				taskS.addFilter("taskAssignee", model.getUser_fk());
			}
			if(Core.isNotNull(model.getStatus())) {
				taskS.addFilter("processFinished", model.getStatus());
			}
			for(TaskServiceQuery task:taskS.queryHistoryTask()) {
				_CONS_PROC.Table_1 t = new _CONS_PROC.Table_1();
				t.setNum_processo(task.getProcessInstanceId());
				t.setProcesso(task.getProcessDefinitionId());
				t.setEatapa(Core.isNotNull(task.getName())?task.getName():task.getTaskDefinitionKey());
				t.setDt_inicio_etapa(Core.ToChar(task.getStartTime(), "yyyy-MM-dd'T'HH:mm:ss","yyyy-MM-dd HH:mm:ss"));
				t.setDt_fim_etapa(Core.ToChar(task.getEndTime(), "yyyy-MM-dd'T'HH:mm:ss","yyyy-MM-dd HH:mm:ss"));
				t.setUtilizador(task.getAssignee());
				t.setEstado(this.getStatusTask(task));
				t.setP_id_task(task.getId());
				data.add(t);
			}
		}
		_CONS_PROCView view = new _CONS_PROCView(model);
		
		/*Specify your connection name in first parameter*/
		view.aplicacao.setValue(new Application().getListApps());	
		
		/*Specify your connection name in first parameter*/
		view.organica.setValue(new Organization().getListMyOrganizations());
		
		/*Specify your connection name in first parameter*/
//		view.area_fk.setSqlQuery(null,"SELECT 'id' as ID,'name' as NAME ");
		view.area_fk.setVisible(false);
		
		/*Specify your connection name in first parameter*/
		view.proc_tp_fk.setValue(new ProcessDefinitionService().mapToComboBox());
		
		/*Specify your connection name in first parameter*/
		//view.user_fk.setSqlQuery(null,"SELECT 'id' as ID,'name' as NAME ");
		
		/*Specify your connection name in first parameter*/
		view.status.setValue(this.getStatus());
		view.btn_pesquisar.setLink("index");
		view.table_1.addData(data);
		view.p_id_task.setParam(true);
		return this.renderView(view);
		/*----#END-PRESERVED-AREA----*/
	}


	public Response actionPesquisar() throws IOException, IllegalArgumentException, IllegalAccessException{
		/*----#START-PRESERVED-AREA(PESQUISAR)----*/
		_CONS_PROC model = new _CONS_PROC();
		model.load();
		
		return this.redirect("igrp","_CONS_PROC","index");
		/*----#END-PRESERVED-AREA----*/
	}
	

	public Response actionVer_etapa() throws IOException, IllegalArgumentException, IllegalAccessException{
		/*----#START-PRESERVED-AREA(VER_ETAPA)----*/
		String p_id_task = Igrp.getInstance().getRequest().getParameter("p_id_task");
		return this.redirect("igrp","Detalhes_tarefas","index&target=_blank&taskId="+p_id_task);
		/*----#END-PRESERVED-AREA----*/
	}
	

	public Response actionVer_processo() throws IOException, IllegalArgumentException, IllegalAccessException{
		/*----#START-PRESERVED-AREA(VER_PROCESSO)----*/
		String p_id_task = Igrp.getInstance().getRequest().getParameter("p_id_task");
		return this.redirect("igrp","DetalhesProcesso","index&target=_blank&taskId="+p_id_task);
		/*----#END-PRESERVED-AREA----*/
	}
	
	/*----#START-PRESERVED-AREA(CUSTOM_ACTIONS)----*/
	private Map<String,String> getStatus() {
		Map<String,String> status = new HashMap<String,String>();
		status.put(null, "--- Selecionar Estado ---");
        status.put("false","Ativo");
        status.put("true","Terminado");
		return status;
	}
  	private String getStatusTask(TaskServiceQuery task) {
		if(Core.isNotNull(task.getEndTime()))
			return "Terminado";
		if(Core.isNotNull(task.getAssignee()))
			return "Não Iniciado";
		return "Não Atribuido";
	}

	/*----#END-PRESERVED-AREA----*/
}