package nosi.core.webapp.activit.rest.services;

import org.json.JSONException;
import org.json.JSONObject;

import nosi.core.webapp.activit.rest.entities.Activiti;
import nosi.core.webapp.activit.rest.entities.FormDataService;
import nosi.core.webapp.activit.rest.entities.ProcessDefinitionService;
import nosi.core.webapp.activit.rest.entities.StartProcess;
import nosi.core.webapp.activit.rest.entities.TaskService;
import nosi.core.webapp.webservices.helpers.ResponseConverter;
import nosi.core.webapp.webservices.helpers.ResponseError;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Properties;
import java.util.ArrayList;

/**
 * Emanuel 15 May 2019
 */
public class FormDataServiceRest extends GenericActivitiRest {

	private final List<Properties> propertyFormSubmit;

	public FormDataServiceRest() {
		this.propertyFormSubmit = new ArrayList<>();
	}

	public FormDataService getFormDataByProcessDefinitionId(String processDefinitionId) {
		ProcessDefinitionService process = new ProcessDefinitionService();
		process.setId(processDefinitionId);
		return this.getFormData(process);
	}

	public FormDataService getFormDataByTaskId(String taskId) {
		TaskService task = new TaskService();
		task.setId(taskId);
		return this.getFormData(task);
	}

	private FormDataService getFormData(Activiti activiti) {
		FormDataService d = new FormDataService();
		HttpResponse<String> response = null;
		if (activiti instanceof TaskService) {
			response = this.getRestRequest().getHttpClient("form/form-data?taskId=" +activiti.getId());
		} else if (activiti instanceof ProcessDefinitionService) {
			response = this.getRestRequest().getHttpClient("form/form-data?processDefinitionId=" +activiti.getId());
		}
		if (response != null) {
			String contentResp = response.body();
			if (response.statusCode() == 200) {
				d = ResponseConverter.convertJsonToDao(contentResp, FormDataService.class);
			} else {
				this.setError(ResponseConverter.convertJsonToDao(contentResp, ResponseError.class));
			}
		}
		return d;
	}

	public void addVariable(String name, Object value) {
		Properties p = new Properties();
		p.put("id", name);
		p.put("value", value);
		this.propertyFormSubmit.add(p);
	}

	public StartProcess submitFormByTask(TaskService task) {
		return this.submitForm(task);
	}

	public StartProcess submitFormByProcessDenifition(ProcessDefinitionService processDefinition) {
		return this.submitForm(processDefinition);
	}

	private StartProcess submitForm(Activiti activiti) {
		StartProcess s = new StartProcess();
		JSONObject json = new JSONObject();
		try {
			if (activiti instanceof TaskService) {
				json.put("taskId", activiti.getId());
			} else if (activiti instanceof ProcessDefinitionService) {
				json.put("processDefinitionId", activiti.getId());
				json.put("businessKey", activiti.getBusinessKey_());
			}
			json.put("properties", this.propertyFormSubmit);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		var response = this.getRestRequest().postHttpClient("form/form-data", json.toString());

		if (response != null) {
			String contentResp = response.body();
			if (response.statusCode() == 200 || response.statusCode() == 204) {
				s = ResponseConverter.convertJsonToDao(contentResp, StartProcess.class);
			} else {
				this.setError(ResponseConverter.convertJsonToDao(contentResp, ResponseError.class));
			}
		}
		return s;
	}

}
