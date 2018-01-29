package nosi.core.webapp.activit.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.Part;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.reflect.TypeToken;
import nosi.core.webapp.helpers.FileHelper;
import nosi.core.webapp.webservices.helpers.ResponseConverter;
import nosi.core.webapp.webservices.helpers.ResponseError;
import nosi.core.webapp.webservices.helpers.RestRequest;

/**
 * @author: Emanuel Pereira
 * 27 Sep 2017
 */
public class ProcessDefinitionService extends Activit{

	private Integer version;
	private String nameLike;
	private String key;
	private Boolean suspended;
	private String description;
	private String deploymentId;
	private String deploymentUrl;
	private Boolean graphicalNotationDefined;
	private String resource;
	private String diagramaResource;
	private Boolean startFormDefined;


	public ProcessDefinitionService getProcessDefinition(String id){
		Response response = new RestRequest().get("repository/process-definitions/",id);
		ProcessDefinitionService process = this;
		if(response!=null){
			String contentResp = "";
			InputStream is = (InputStream) response.getEntity();
			try {
				contentResp = FileHelper.convertToString(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(response.getStatus()==200){
				process = (ProcessDefinitionService) ResponseConverter.convertJsonToDao(contentResp,ProcessDefinitionService.class);
			}else{
				this.setError((ResponseError) ResponseConverter.convertJsonToDao(contentResp, ResponseError.class));
			}
		}
		return process;
	}
	
	public String getDiagram(String id){
		String d = null;
		new RestRequest().setAccept_format(MediaType.APPLICATION_OCTET_STREAM);
		Response response = new RestRequest().get("runtime/process-instances/"+id+"/diagram");		
		if(response!=null){
			if(response.getStatus()==200) {
				InputStream finput =(InputStream) response.getEntity();
				try {
					byte[] imageBytes = new byte[finput.available()];
					finput.read(imageBytes, 0, imageBytes.length);
					finput.close();
					return Base64.getEncoder().encodeToString(imageBytes);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return d;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProcessDefinitionService> getProcessDefinitions(){
		List<ProcessDefinitionService> d = new ArrayList<>();
		Response response = new RestRequest().get("repository/process-definitions"+this.getFilter());
		if(response!=null){
			String contentResp = "";
			InputStream is = (InputStream) response.getEntity();
			try {
				contentResp = FileHelper.convertToString(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(Response.Status.OK.getStatusCode() == response.getStatus()){				
				ProcessDefinitionService dep = (ProcessDefinitionService) ResponseConverter.convertJsonToDao(contentResp, this.getClass());
				this.setTotal(dep.getTotal());
				this.setSize(dep.getSize());
				this.setSort(dep.getSort());
				this.setOrder(dep.getOrder());
				this.setStart(dep.getStart());
				d = (List<ProcessDefinitionService>) ResponseConverter.convertJsonToListDao(contentResp,"data", new TypeToken<List<ProcessDefinitionService>>(){}.getType());
			}else{
				this.setError((ResponseError) ResponseConverter.convertJsonToDao(contentResp, ResponseError.class));
			}
		}
		return d;
	}
	
	public List<ProcessDefinitionService> getProcessDefinitionsAtivos(Integer idApp){
		this.setFilter("?suspended=false&latest=true&size=100000000&tenantId="+idApp);
		return this.getProcessDefinitions();
	}
	
	public boolean activateProcessDefinition(String id){
		return this.statusProcessDefinition(id,  "activate");
	}
	public boolean suspendProcessDefinition(String id){
		return this.statusProcessDefinition(id,  "suspend");
	}
	private boolean statusProcessDefinition(String id,String status){
		JSONObject jobj = new JSONObject();
		try {
			jobj.put("action" ,status);
			jobj.put( "includeProcessInstances" , "true");
			jobj.put( "date" , new Date(System.currentTimeMillis()));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Response response = new RestRequest().put("repository/process-definitions",jobj.toString(),id);
		if(response!=null){
			String contentResp = "";
			InputStream is = (InputStream) response.getEntity();
			try {
				contentResp = FileHelper.convertToString(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.setError((ResponseError) ResponseConverter.convertJsonToDao(contentResp, ResponseError.class));
			return response.getStatus()==200;
		}
		return false;
	}
	public ProcessDefinitionService create(){
		ProcessDefinitionService d = this;
		Response response = new RestRequest().post("repository/process-definitions",ResponseConverter.convertDaoToJson(this));
		if(response!=null){
			String contentResp = "";
			InputStream is = (InputStream) response.getEntity();
			try {
				contentResp = FileHelper.convertToString(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(response.getStatus()==201){
				d = (ProcessDefinitionService) ResponseConverter.convertJsonToDao(contentResp, ProcessDefinitionService.class);
			}else{
				d.setError((ResponseError) ResponseConverter.convertJsonToDao(contentResp, ResponseError.class));
			}
		}
		return d;
	}
	

	public ProcessDefinitionService update(){
		ProcessDefinitionService d = this;
		Response response = new RestRequest().put("repository/process-definitions",ResponseConverter.convertDaoToJson(this),this.getId());
		if(response!=null){
			String contentResp = "";
			InputStream is = (InputStream) response.getEntity();
			try {
				contentResp = FileHelper.convertToString(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(response.getStatus()==200){
				d = (ProcessDefinitionService) ResponseConverter.convertJsonToDao(contentResp, ProcessDefinitionService.class);
			}else{
				this.setError((ResponseError) ResponseConverter.convertJsonToDao(contentResp, ResponseError.class));
			}
		}
		return d;
	}
	
	public boolean delete(String id){
		Response response = new RestRequest().delete("repository/process-definitions",id);
		return response!=null && response.getStatus()==204;
	}	

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getNameLike() {
		return nameLike;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}


	public Boolean getSuspended() {
		return suspended;
	}

	public void setSuspended(Boolean suspended) {
		this.suspended = suspended;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public String getDeploymentUrl() {
		return deploymentUrl;
	}

	public void setDeploymentUrl(String deploymentUrl) {
		this.deploymentUrl = deploymentUrl;
	}

	public Boolean getGraphicalNotationDefined() {
		return graphicalNotationDefined;
	}

	public void setGraphicalNotationDefined(Boolean graphicalNotationDefined) {
		this.graphicalNotationDefined = graphicalNotationDefined;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getDiagramaResource() {
		return diagramaResource;
	}

	public void setDiagramaResource(String diagramaResource) {
		this.diagramaResource = diagramaResource;
	}

	public Boolean getStartFormDefined() {
		return startFormDefined;
	}

	public void setStartFormDefined(Boolean startFormDefined) {
		this.startFormDefined = startFormDefined;
	}
	
	

	@Override
	public String toString() {
		return "ProcessDefinitionService [version=" + version + ", nameLike=" + nameLike + ", key=" + key
				+ ", suspended=" + suspended + ", description=" + description + ", deploymentId=" + deploymentId
				+ ", deploymentUrl=" + deploymentUrl + ", graphicalNotationDefined=" + graphicalNotationDefined
				+ ", resource=" + resource + ", diagramaResource=" + diagramaResource + ", startFormDefined="
				+ startFormDefined + "]";
	}

	public Map<String,String> mapToComboBox() {
		List<ProcessDefinitionService> list = this.getProcessDefinitionsAtivos(null);
		Map<String,String> map = new HashMap<>();
		map.put(null, "--- Selecionar Processo ----");
		map.putAll(list.stream().collect(Collectors.toMap(ProcessDefinitionService::getKey, ProcessDefinitionService::getName)));
		return map;
	}

	public boolean addProcessFile(Part file, String processDefinitionId) throws IOException {
		try {
			Response response = new RestRequest().post("runtime/process-instances/"+processDefinitionId+"/variables?name=file_"+processDefinitionId+"_"+file.getName()+"&type=binary&scope=local", file);
			file.delete();
			return response.getStatus() == 201;
		} catch (IOException e) {
			e.printStackTrace();
		}
		file.delete();
		return false;
	}

}