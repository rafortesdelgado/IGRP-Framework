package nosi.core.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.xml.bind.JAXB;
import org.hibernate.criterion.Restrictions;
import com.google.gson.Gson;
import nosi.core.config.Config;
import nosi.core.config.Connection;
import nosi.core.gui.components.IGRPForm;
import nosi.core.gui.fields.Field;
import nosi.core.gui.fields.HiddenField;
import nosi.core.webapp.activit.rest.CustomVariableIGRP;
import nosi.core.webapp.activit.rest.HistoricTaskService;
import nosi.core.webapp.activit.rest.Rows;
import nosi.core.webapp.activit.rest.TaskVariables;
import nosi.core.webapp.databse.helpers.QueryDelete;
import nosi.core.webapp.databse.helpers.QueryHelper;
import nosi.core.webapp.databse.helpers.QueryInsert;
import nosi.core.webapp.databse.helpers.QuerySelect;
import nosi.core.webapp.databse.helpers.QueryUpdate;
import nosi.core.webapp.helpers.DateHelper;
import nosi.core.webapp.helpers.EncrypDecrypt;
import nosi.core.webapp.helpers.Permission;
import nosi.core.webapp.webservices.biztalk.GenericService_DevProxy;
import nosi.core.webapp.webservices.biztalk.dao.PesquisaBI;
import nosi.core.webapp.webservices.biztalk.dao.PesquisaGeografia;
import nosi.core.webapp.webservices.biztalk.dao.PesquisaHierarquicaCAE;
import nosi.core.webapp.webservices.biztalk.dao.PesquisaNIF;
import nosi.core.webapp.webservices.biztalk.dao.PesquisaNascimento;
import nosi.core.webapp.webservices.biztalk.dao.PesquisaSNIAC;
import nosi.core.webapp.webservices.biztalk.dao.Request;
import nosi.core.webapp.webservices.biztalk.dao.ServiceSerach;
import nosi.core.webapp.webservices.biztalk.message.GenericServiceRequest;
import nosi.core.webapp.webservices.biztalk.message.GenericServiceResponse;
import nosi.core.xml.XMLWritter;
import nosi.webapps.igrp.dao.Application;
import nosi.webapps.igrp.dao.CLob;
import nosi.webapps.igrp.dao.Organization;
import nosi.webapps.igrp.dao.ProfileType;
import nosi.webapps.igrp.dao.Transaction;
import static nosi.core.i18n.Translator.gt;
/**
 * @author: Emanuel Pereira
 * 13 Nov 2017
 */
public final class Core {	// Not inherit 


	
	private Core() {} // Not instantiate  
	
	/**
	 * log.fatal("fatal message");
		log.debug("debug message");
	 * */
	// Add logdbug
	public static void log(String msg) {
		
		Igrp.getInstance().getLog().addMessage(msg);
	}
	
	//Add Message Error
	
	public static void setMessageError(String msg){
		nosi.core.servlet.IgrpServlet.LOGGER.error(gt(msg));
		
		Igrp.getInstance().getFlashMessage().addMessage(FlashMessage.ERROR, gt(msg));
	}	

	public static void setMessageError(){
		nosi.core.servlet.IgrpServlet.LOGGER.error(FlashMessage.MESSAGE_ERROR);
		Igrp.getInstance().getFlashMessage().addMessage(FlashMessage.ERROR,gt( FlashMessage.MESSAGE_ERROR));
	}	

	//Add Message Success
	public static void setMessageSuccess(String msg){
		Igrp.getInstance().getFlashMessage().addMessage(FlashMessage.SUCCESS, gt(msg));	
	}
	
	//Add Message Success
	public static void setMessageSuccess(){
		Igrp.getInstance().getFlashMessage().addMessage(FlashMessage.SUCCESS, gt(FlashMessage.MESSAGE_SUCCESS));
	}
	
	//Add Message Info
	public static void setMessageInfo(String msg){
		nosi.core.servlet.IgrpServlet.LOGGER.info(gt(msg));
		Igrp.getInstance().getFlashMessage().addMessage(FlashMessage.INFO, gt(msg));
	}

	//Add Message Info With Link
	public static void setMessageInfoLink(String msg,String link){
		nosi.core.servlet.IgrpServlet.LOGGER.info(gt(msg)+"/#RESERVE#/"+link);
		Igrp.getInstance().getFlashMessage().addMessage(FlashMessage.INFO_LINK, gt(msg)+"/#RESERVE#/"+link);		
	}
	
	//Add Message Info With Link
	public static void setMessageInfoLink(String msg,String app,String page,String action){
		nosi.core.servlet.IgrpServlet.LOGGER.info(gt(msg)+"/#RESERVE#/"+new Config().getResolveUrl(app, page, action));
		Igrp.getInstance().getFlashMessage().addMessage(FlashMessage.INFO_LINK, gt(msg)+"/#RESERVE#/"+new Config().getResolveUrl(app, page, action));
	}
		
	//Add Message Warning
	public static void setMessageWarning(String msg){
		nosi.core.servlet.IgrpServlet.LOGGER.warn(gt(msg));
		Igrp.getInstance().getFlashMessage().addMessage(FlashMessage.WARNING, gt(msg));		
	}	
	
	//Get Config Property
	public static String getConfig(String name){
		nosi.webapps.igrp.dao.Config c = new nosi.webapps.igrp.dao.Config().find().andWhere("name", "=", name).one();
		return c!=null?c.getValue():null;
	}
	
	//Get Current User
	public static nosi.webapps.igrp.dao.User getCurrentUser(){
		return (nosi.webapps.igrp.dao.User) Igrp.getInstance().getUser().getIdentity();
	}

	//Find User by ID
	public static nosi.webapps.igrp.dao.User findUserById(Integer id){
		return new nosi.webapps.igrp.dao.User().findOne(id);
	}
	
	//Find User by Username
	public static nosi.webapps.igrp.dao.User findUserByUsername(String userName){
		return new nosi.webapps.igrp.dao.User().find().andWhere("username", "=", userName).one();
	}
	
	//Find User by email
	public static nosi.webapps.igrp.dao.User findUserByEmail(String email){
		return new nosi.webapps.igrp.dao.User().find().andWhere("username", "=", email).one();
	}
	
	//Format date and return to Type String
	public static String convertDate(String date, String formatIn, String outputFormat) {
		return DateHelper.convertDate(date,formatIn,outputFormat);
	}
	
	//Format date and return to Type Date
	public static java.sql.Date formatDate(String data,String inputFormat,String outputFormat){
		return DateHelper.formatDate(data,inputFormat,outputFormat);
	}
	
	//Get Current Datetime (dd-MM-yyyy)
	public static String getCurrentDate(){
		return DateHelper.getCurrentDate("dd-MM-yyyy");
	}

	//Get Current Datetime (User defined output format)
	public static String getCurrentDate(String outputFormat){
		return DateHelper.getCurrentDate(outputFormat);
	}
	
	//Get Current Datetime (dd/MM/yyyy HH:mm:ss)
	public static String getCurrentDataTime() {
		return DateHelper.getCurrentDataTime();
	}
	
	//Get Current Application Dad on Cookie
	public static String getCurrentDad(){
		return Permission.getCurrentEnv();
	}

	//Get Current Organization on Cookie
	public static Integer getCurrentOrganization(){
		return Permission.getCurrentOrganization();
	}

	//Get Current Profile on Cookie
	public static Integer getCurrentProfile(){
		return Permission.getCurrentPerfilId();
	}
	
	//Find Application By ID
	public static Application findApplicationById(Integer id){
		return new Application().findOne(id);
	}
	
	//Find Application By Dad
	public static Application findApplicationByDad(String dad){
		return new Application().find().andWhere("dad", "=", dad).one();
	}
	
	//Find Organization By ID
	public static Organization findOrganizationById(Integer id){
		return new Organization().findOne(id);
	}
	
	//Find Organization By Code
	public static Organization findOrganizationByCode(String code){
		return new Organization().find().andWhere("code", "=", code).one();
	}
	
	//Find Profile By ID
	public static ProfileType findProfileById(Integer id){
		return new ProfileType().findOne(id);
	}
	
	//Find Profile By Code
	public static ProfileType findProfileByCode(String code){
		return new ProfileType().find().andWhere("code", "=", code).one();
	}
	
	//Check permition transaction for current user
	public static boolean checkUserTransaction(String transaction){
		return new Transaction().getPermission(transaction);
	}
	
	public static Response getLinkReport(String code_report,Report rep){
		return new Report().invokeReport(code_report, rep);
	}
	
	public static GenericServiceResponse getBizTalkClient(String clientId,String transaction,String service,String args){
		GenericService_DevProxy proxy = new GenericService_DevProxy(); 
		GenericServiceRequest part = new GenericServiceRequest(clientId, transaction, service, args);
		try {
			return proxy.getGenericService_Dev().genericRequest(part);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static GenericServiceResponse getBizTalkClientService(ServiceSerach service){
		String args = new Request().prepare(service,"xml");
		System.out.println("args: "+args);
		return getBizTalkClient(service.getClientID(), service.getTransactionID(), service.getServiceID(), args);			
	}
	
	private static ServiceSerach processRequestBiztalkClientService(GenericServiceResponse response,ServiceSerach service){
		if(response.getStatus().equals("true")){
			String xml = response.getResult();
			StringReader input = new StringReader(xml);
			nosi.core.webapp.webservices.biztalk.dao.Response r = JAXB.unmarshal(input,nosi.core.webapp.webservices.biztalk.dao.Response.class);
			if(r.getRow()!=null){
				if(r.getRow().isStatus()){
					xml = xml.replaceAll("lista_nifs", "lista");
					xml = xml.substring(xml.indexOf("<lista>"), xml.indexOf("</lista>")+"</lista>".length());
					input = new StringReader(xml);
					service = JAXB.unmarshal(input, service.getClass());
					return service;
				}
			}
		}
		return null;
	}
	
	//Pesquia SNIAC via Biztalk
	public static PesquisaSNIAC getBizTalkPesquisaSNIAC(PesquisaSNIAC pesquisa){
		return (PesquisaSNIAC) processRequestBiztalkClientService(getBizTalkClientService(pesquisa),pesquisa);
	}
	
	public static PesquisaSNIAC getBizTalkPesquisaSNIAC(Integer num_idnt_civil_pes, String num_registo_pes, String nome_pes, String data_nasc_pes,
			Integer id_tp_doc_pes){
		return getBizTalkPesquisaSNIAC(new PesquisaSNIAC(num_idnt_civil_pes, num_registo_pes, nome_pes, data_nasc_pes, id_tp_doc_pes));
	}	

	//Pesquia BI via Biztalk
	public static PesquisaBI getBizTalkPesquisaBI(PesquisaBI pesquisa){
		return (PesquisaBI) processRequestBiztalkClientService(getBizTalkClientService(pesquisa),pesquisa);
	}
	
	public static PesquisaBI getBizTalkPesquisaBI(Integer bi, String nome){
		return getBizTalkPesquisaBI(new PesquisaBI(bi, nome));
	}	

	//Pesquia NIF via Biztalk
	public static PesquisaNIF getBizTalkPesquisaNIF(PesquisaNIF pesquisa){
		return (PesquisaNIF) processRequestBiztalkClientService(getBizTalkClientService(pesquisa),pesquisa);
	}
	
	public static PesquisaNIF getBizTalkPesquisaNIF(Integer numero, String nome){
		return getBizTalkPesquisaNIF(new PesquisaNIF(numero, nome));
	}
	
	//Pesquia Nascimento via Biztalk
	public static PesquisaNascimento getBizTalkPesquisaNascimento(PesquisaNascimento pesquisa){
		return (PesquisaNascimento) processRequestBiztalkClientService(getBizTalkClientService(pesquisa),pesquisa);
	}
	
	public static PesquisaNascimento getBizTalkPesquisaNascimento(String nome, Integer numero_registo, String data_nascimento){
		return getBizTalkPesquisaNascimento(new PesquisaNascimento(nome, numero_registo, data_nascimento));
	}	

	//Pesquia Hierarquia CAE via Biztalk
	public static PesquisaHierarquicaCAE getBizTalkPesquisaHierarquiaCAE(PesquisaHierarquicaCAE pesquisa){
		return (PesquisaHierarquicaCAE) processRequestBiztalkClientService(getBizTalkClientService(pesquisa),pesquisa);
	}
	
	public static PesquisaHierarquicaCAE getBizTalkPesquisaHierarquiaCAE(String id, String codigo, String crpcae_id, String self_id){
		return getBizTalkPesquisaHierarquiaCAE(new PesquisaHierarquicaCAE(id, codigo, crpcae_id, self_id));
	}
	
	//Pesquia Geografia via Biztalk
	public static PesquisaGeografia getBizTalkPesquisaGeografia(PesquisaGeografia pesquisa){
		return (PesquisaGeografia) processRequestBiztalkClientService(getBizTalkClientService(pesquisa),pesquisa);
	}
	
	public static PesquisaGeografia getBizTalkPesquisaGeografia(String id, String zona, String freguesia, String concelho, String ilha, String pais,
			String nivel_detalhe, String tp_geog_cd, String codigo_ine, String codigo, String self_id){
		return getBizTalkPesquisaGeografia(new PesquisaGeografia(id, zona, freguesia, concelho, ilha, pais, nivel_detalhe, tp_geog_cd, codigo_ine, codigo, self_id));
	}
	
	public static String encrypt(String content){
		return EncrypDecrypt.encrypt(content);
	}
	
	public static String decrypt(String content){
		return EncrypDecrypt.decrypt(content);
	}
	
	public static boolean isNotNull(Object value) {
		return value!=null && !value.equals("");
	}
	
	public static boolean isNull(Object value) {
		return value==null || value.equals("");
	}	
	
	public static boolean isInt(Object value) {
		if(isNotNull(value)) {
			try {
				Integer.parseInt(value.toString());
				return true;
			}catch(NumberFormatException e) {
				return false;
			}
		}
		return false;
	}
	
	public static boolean isDouble(Object value) {
		if(isNotNull(value)) {
			try {
				Double.parseDouble(value.toString());
				return true;
			}catch(NumberFormatException e) {
				return false;
			}
		}
		return false;
	}
	
	public static boolean isFloat(Object value) {
		if(isNotNull(value)) {
			try {
				Float.parseFloat(value.toString());
				return true;
			}catch(NumberFormatException e) {
				return false;
			}
		}
		return false;
	}
	
	public static boolean isLong(Object value) {
		if(isNotNull(value)) {
			try {
				Long.parseLong(value.toString());
				return true;
			}catch(NumberFormatException e) {
				return false;
			}
		}
		return false;
	}
	
	public static boolean isShort(Object value) {
		if(isNotNull(value)) {
			try {
				Short.parseShort(value.toString());
				return true;
			}catch(NumberFormatException e) {
				return false;
			}
		}
		return false;
	}
	public static Integer toInt(String value) {
		if(Core.isInt(value))
			return Integer.parseInt(value);
		return 0;
	}

	public static Long toLong(String value) {
		if(Core.isLong(value))
			return Long.parseLong(value);
		return (long) 0;
	}
	
	public static Double toDouble(String value) {
		if(Core.isDouble(value))
			return Double.parseDouble(value);
		return 0.0;
	}

	public static Float toFloat(String value) {
		if(Core.isFloat(value))
			return Float.parseFloat(value);
		return (float) 0;
	}

	public static Short toShort(String value) {
		if(Core.isShort(value))
			return Short.parseShort(value);
		return 0;
	}
	
	public static QueryHelper insert(String connectionName,String tableName) {
		return new QueryInsert(connectionName).insert(tableName);
	}
	
	public static QueryHelper insert(String connectionName,String schemaName,String tableName) {
		return new QueryInsert(connectionName).insert(schemaName,tableName);
	}
	
	public static QueryHelper update(String connectionName,String tableName) {
		return new QueryUpdate(connectionName).update(tableName);
	}
	
	public static QueryHelper update(String connectionName,String schemaName,String tableName) {
		return new QueryUpdate(connectionName).update(schemaName,tableName);
	}
	
	public static QueryHelper delete(String connectionName,String tableName) {
		return new QueryDelete(connectionName).delete(tableName);
	}
	
	public static QueryHelper delete(String connectionName,String schemaName,String tableName) {
		return new QueryDelete(connectionName).delete(schemaName,tableName);
	}
	
	public static QueryHelper query(String connectionName,String sql) {
		return new QuerySelect(connectionName).select(sql);
	}
	public static QueryHelper query(String connectionName,String sql,Class<?> className) {
		return new QuerySelect(connectionName).select(sql,className);
	}
	public static QueryHelper query(String sql) {
		return new QuerySelect().select(sql);
	}
	
	public static java.sql.Date ToDate(String date,String formatIn){
		return DateHelper.convertStringToDate(date, formatIn);
	}
	
	public static java.sql.Date ToDate(String date,String formatIn,String formatOut){
		return DateHelper.formatDate(date, formatIn,formatOut);
	}
	
	public static String ToChar(java.sql.Date date,String formatIn) {
		return DateHelper.convertDateToString(date, formatIn);
	}

	public static String ToChar(String date, String formatIn, String formatOut) {
		return DateHelper.convertDate(date, formatIn, formatOut);
	}
	
	public static String ToChar(String date,String formatOut) {
		return DateHelper.convertDate(date, "yyyy-MM-dd", formatOut);
	}
	
	public static class Restriction extends Restrictions{
		
	}

	public static void addHiddenField(String name,Object value) {
		Field f = new HiddenField(name, value!=null?value.toString():"");
		f.setValue(value);
		IGRPForm.hiddenFields.add(f);
	}

	public static String getXMLParams() {
		Map<String,String[]> params = Igrp.getInstance().getRequest().getParameterMap();
		XMLWritter xml = new XMLWritter();
		xml.startElement("rows");
		params.entrySet().stream()
						 .filter(p->!p.getKey().equalsIgnoreCase("r"))
						 .filter(p->!p.getKey().equalsIgnoreCase("prm_app"))
						 .filter(p->!p.getKey().equalsIgnoreCase("prm_page"))
						 .forEach(
								p->{									
									for(String v:p.getValue()) {
										xml.setElement(p.getKey(), v);
									}
								}
						  );
		xml.endElement();
		return xml.toString();
	}
	
	public static String getJsonParams() {
		Map<String,String[]> params = Igrp.getInstance().getRequest().getParameterMap();
		CustomVariableIGRP customV = new CustomVariableIGRP();
		Gson gson = new Gson();		
		params.entrySet().stream()
						 .filter(p->!p.getKey().equalsIgnoreCase("r"))
						 .filter(p->!p.getKey().equalsIgnoreCase("prm_app"))
						 .filter(p->!p.getKey().equalsIgnoreCase("prm_page"))
						 .forEach(
								p->{
									Rows row = new Rows();
									row.setName(p.getKey());
									row.setValue(p.getValue());
									customV.add(row);
								}
						  );
		String json = gson.toJson(customV);
		return json;
	}
	public static Map<String,String[]> getParameters() {
		return Igrp.getInstance().getRequest().getParameterMap();
	}
	
	public static void setParam(String name,Object value) {
		Igrp.getInstance().getRequest().setAttribute(name, value);
	}
	
	public static void setParam(String name,Object[] value) {
		Igrp.getInstance().getRequest().setAttribute(name, value);
	}
	
	public static String getParam(String name) {
		return (String) Igrp.getInstance().getRequest().getAttribute(name);
	}

	public static String[] getParamArray(String name) {
		return (String[]) Igrp.getInstance().getRequest().getAttribute(name);
	}
	
	public static String getTaskVariable(String taskDefinitionKey,String variableName) {
		String taskId = Core.getParam("taskId");
		List<HistoricTaskService> task = new HistoricTaskService().getHistory(taskId);
		if(task!=null && task.size() > 0) {
			List<HistoricTaskService> task1 = new HistoricTaskService().getHistory(taskDefinitionKey,task.get(0).getExecutionId());
			if(task1!=null && task1.size() > 0) {
				List<TaskVariables> vars = task1.get(0).getVariables();
				List<TaskVariables> var = vars.stream().filter(v->v.getName().equalsIgnoreCase(taskDefinitionKey+"_"+variableName)).collect(Collectors.toList());
				return (var!=null && var.size() > 0)?(String) var.get(0).getValue():"";
			}
		}
		return "";
	}
	
	
	public static String getPinkColor() {
		return "1";
	}

	public static String getAmberColor() {
		return "2";
	}
	
	public static String getYellowColor() {
		return "3";
	}

	public static String getGreenColor() {
		return "4";
	}
	
	public static String getBlueGreyColor() {
		return "5";
	}

	public static String getPurpleColor() {
		return "6";
	}
	
	public static String getBlueColor() {
		return "7";
	}

	public static String getBrownColor() {
		return "8";
	}
	
	public static String getDeepPurpleColor() {
		return "9";
	}

	//Receive multiple params and get one of these params that's not null
	public static String getSwitchNotNullValue(String ...strings) {
		if(strings.length > 1) {
			if(Core.isNotNull(strings[0]))
				return strings[0];
			String[] newStrings = new String[strings.length-1];
			System.arraycopy(strings, 1, newStrings,0, newStrings.length);
			return getSwitchNotNullValue(newStrings);
		}else if(strings.length==1) {
			if(Core.isNotNull(strings[0]))
				return strings[0];
		}
		return "";
	}
	
	/** Insert a file to the Igrp core DataBase and return an Id ... **/
	
	public static int saveFile(File file, String name, String mime_type) {
		String igrpCoreConnection = Config.getBaseConnection();
		java.sql.Connection conn = Connection.getConnection(igrpCoreConnection);
		int lastInsertedId = 0;
		if(conn != null) {
			name = (name == null || name.trim().isEmpty() ? file.getName() : name);
			FileNameMap fileNameMap = URLConnection.getFileNameMap();
			mime_type = (mime_type == null || mime_type.trim().isEmpty() ? fileNameMap.getContentTypeFor(file.getPath()) : mime_type);
			String sysdate = LocalDate.parse(LocalDate.now().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString();
			String standardSql = "insert into tbl_clob(c_lob_content, dt_created, mime_type, name) values(?, ?, ?, ?)";
			try {
				java.sql.PreparedStatement ps = conn.prepareStatement(standardSql, java.sql.PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setBinaryStream(1, new FileInputStream(file));
				ps.setString(2, sysdate);
				ps.setString(3, mime_type);
				ps.setString(4, name);
				if(ps.executeUpdate() > 0) {			
					try (java.sql.ResultSet rs = ps.getGeneratedKeys()) {
				        if (rs.next()) {
				        	lastInsertedId = rs.getInt(1);
				        }
					}
					ps.close();
				}
				conn.commit();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return lastInsertedId;
	}
	
	public static int saveFile(File file) {
		return saveFile(file, null, null);
	}
	
	public static CLob getFile(int fileId) {
		CLob cLob = null;
		java.sql.Connection conn = null;
		
		try {
			String igrpCoreConnection = Config.getBaseConnection();
			conn = Connection.getConnection(igrpCoreConnection);
			String sql = "select * from tbl_clob where id = ?";
			java.sql.PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, fileId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				cLob = new CLob();
				cLob.setC_lob_content(rs.getBytes("c_lob_content"));
				cLob.setDt_created(rs.getString("dt_created"));
				cLob.setName(rs.getString("name"));
				cLob.setMime_type(rs.getString("mime_type"));
				cLob.setId(rs.getInt("id"));
			}
			rs.close();
		}catch(java.sql.SQLException e) {
			e.printStackTrace();
			cLob = null;
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return cLob;
	}
	
	/** **/
}
