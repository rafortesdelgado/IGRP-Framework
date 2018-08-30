package nosi.core.webapp.databse.helpers;

import nosi.core.config.ConfigApp;

/**
 * Emanuel
 * 21 Dec 2017
 */
public class QueryDelete extends CommonFIlter{

	public QueryDelete(String connectionName) {
		super(connectionName);
	}

	public QueryDelete() {		
		this(new ConfigApp().getBaseConnection());
	}

	public QueryHelper delete(String tableName) {
		this.tableName = tableName;
		return this;
	}
	
	public QueryHelper delete(String schemaName,String tableName) {
		this.schemaName = schemaName;
		this.tableName = tableName;
		return this;
	}

}
