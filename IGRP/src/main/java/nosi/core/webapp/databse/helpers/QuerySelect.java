package nosi.core.webapp.databse.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import nosi.base.ActiveRecord.PersistenceUtils;
import nosi.core.config.Config;
import nosi.core.webapp.Core;
import nosi.webapps.igrp.dao.Config_env;

/**
 * Emanuel
 * 21 Dec 2017
 */
public class QuerySelect extends CommonFIlter{

	private Class<?> className;
	private String table1 = "";
	
	public QuerySelect(String connectionName) {
		super(connectionName);
	}

	public QuerySelect() {				
		this(getMyConnectionName());
			
	}		
	
	private static String getMyConnectionName() {		
		final Config_env firstConnectionNameOfTheApp = new Config_env().find().andWhere("application", "=", Core.getCurrentApp().getId()).one();
		if(firstConnectionNameOfTheApp!=null)
			return firstConnectionNameOfTheApp.getName();
		else return Config.getBaseConnection();
	}
	
	//Validate sql query
	public boolean validateQuery(Config_env config,String sql) {
		try {
			sql = sql.replaceAll(":\\w+", "null");
			Core.log("SQL Query:"+sql);
			EntityManager em = PersistenceUtils.getSessionFactory(config).createEntityManager();
			EntityTransaction t =  em.getTransaction();
			t.begin();
			Query query = em.createNativeQuery(sql,Tuple.class);
			query.getResultList();
			t.commit();
			em.close();
			return true;
		}catch(Exception e) {
			Core.log(e.getMessage());
		}
		return false;
	}
	
	public QueryInterface select(String collumns) {
		if(Core.isNotNull(collumns)) {
			if(!collumns.toUpperCase().contains("SELECT"))
				this.setSql("SELECT "+collumns+" ");
			else
				this.setSql(collumns);
		}
		return this;
	}
	

	public QueryInterface select(String sql,Class<?> className) {
		this.setSql(sql);
		this.className = className;
		return this;
	}
	
	public List<Tuple> getResultList() {		
		try {
			Core.log("SQL Query:"+this.getSql());
			EntityManager em = PersistenceUtils.getSessionFactory(this.getConnectionName()).createEntityManager();
			EntityTransaction t =  em.getTransaction();
			t.begin();
			Query query = em.createNativeQuery(this.getSql(),Tuple.class);	
			for(DatabaseMetadaHelper.Column col:this.getColumnsValue()) {		 
				 if(col.getDefaultValue()!=null) {
					 this.setParameter(query,col.getDefaultValue(),col);					
				 }else {
					 query.setParameter(col.getName(), null);
				 }
			}	
			@SuppressWarnings("unchecked")
			List<Tuple> list = query.getResultList();
			t.commit();
			em.close();
			return list;
		}catch(Exception e) {
			Core.log(e.getMessage());
		}
		return null;
	}

	public <T> List<T> getResultList(Class<T> entity){	
		try {
			Core.log("SQL Query:"+this.getSql());
			EntityManager em = PersistenceUtils.getSessionFactory(this.getConnectionName()).createEntityManager();
			Query query = em.createNativeQuery(this.getSql(),entity);	
			for(DatabaseMetadaHelper.Column col:this.getColumnsValue()) {		 
				 if(col.getDefaultValue()!=null) {
					 this.setParameter(query,col.getDefaultValue(),col);					
				 }else {
					 query.setParameter(col.getName(), null);
				 }
			}		
			@SuppressWarnings("unchecked")
			List<T> list = query.getResultList();
			em.close();
			return list;
		}catch(Exception e) {
			Core.log(e.getMessage());
		}
		return null;
	}
	
	public Object getSigleResult() {
		try {
			Core.log("SQL Query:"+this.getSql());
			EntityManager em = PersistenceUtils.getSessionFactory(this.getConnectionName()).createEntityManager();
			Query query = em.createNativeQuery(this.getSql());
			for(DatabaseMetadaHelper.Column col:this.getColumnsValue()) {		 
				 if(col.getDefaultValue()!=null) {
					 this.setParameter(query,col.getDefaultValue(),col);					
				 }else {
					 query.setParameter(col.getName(), null);
				 }
			}		
			Object list = query.getSingleResult();
			em.close();
			return list;
		}catch(Exception e) {
			Core.log(e.getMessage());
		}
		return null;
	}
	
	public TypedQuery<?> getSingleResult(){
		try {
			Core.log("SQL Query:"+this.getSql());
			EntityManager em = PersistenceUtils.getSessionFactory(this.getConnectionName()).createEntityManager();
			TypedQuery<?> query = em.createQuery(this.getSql(), this.className);
			for(DatabaseMetadaHelper.Column col:this.getColumnsValue()) {		 
				 if(col.getDefaultValue()!=null) {
					 this.setParameter(query,col.getDefaultValue(),col);					
				 }else {
					 query.setParameter(col.getName(), null);
				 }
			}		
			em.close();
			return query;
		}catch(Exception e) {
			Core.log(e.getMessage());
		}
		return null;
	}

	

	@Override
	public QueryInterface having(String name, String operator, String value) {		 
		if(Core.isNotNull(value)) {
			this.filterWhere(" HAVING "+name+" "+operator+":"+name+" ").addString(name, value);
		}
		return this;
	}

	@Override
	public QueryInterface having(String name, String operator, Integer value) {		 
		if(Core.isNotNull(value)) {
			this.filterWhere(" HAVING "+name+" "+operator+":"+name+" ").addInt(name, value);
		}
		return this;
	}

	@Override
	public QueryInterface having(String name, String operator, Double value) {		 
		if(Core.isNotNull(value)) {
			this.filterWhere(" HAVING "+name+" "+operator+":"+name+" ").addDouble(name, value);
		}
		return this;
	}

	@Override
	public QueryInterface having(String name, String operator, Float value) {		 
		if(Core.isNotNull(value)) {
			this.filterWhere(" HAVING "+name+" "+operator+":"+name+" ").addFloat(name, value);
		}
		return this;
	}

	@Override
	public QueryInterface orderBy(String[]... orderByNames) {
		if(orderByNames!=null) {
			String c = " ORDER BY ";
    		int i=1;
    		for(String[] names:orderByNames) {
    			String order = names[names.length-1];
    			if(!order.equalsIgnoreCase(ORDERBY.ASC) && !order.equalsIgnoreCase(ORDERBY.DESC))
    				order = ORDERBY.ASC;
    			String[] newNames = Arrays.copyOf(names, names.length-1>=1?names.length-1:names.length);
    			c+= (Arrays.toString(newNames).replaceAll("\\[", "").replaceAll("\\]", "")+" "+order+(i==orderByNames.length?" ":", "));
    			i++;
    		}
    		this.filterWhere(c);
    	}
		return this;
	}

	@Override
	public QueryInterface groupBy(String... groupByNames) {
		if(groupByNames!=null) {
			this.filterWhere(" GROUP BY "+Arrays.toString(groupByNames).replaceAll("\\[", "").replaceAll("\\]", "")+" ");
		}
		return this;
	}

	

	@Override
	public QueryInterface innerJoin(String table2, String key1, String key2) {
		if(Core.isNotNull(table2) && Core.isNotNull(key1) && Core.isNotNull(key2)) {
			this.filterWhere(" INNER JOIN "+table2+" ON "+this.getAlias(this.table1)+"."+key1+" = "+this.getAlias(table2)+"."+key2+" ");
		}
		return this;
	}
	
	@Override
	public QueryInterface leftJoin(String table2, String key1, String key2) {
		if(Core.isNotNull(table2) && Core.isNotNull(key1) && Core.isNotNull(key2)) {
			this.filterWhere(" LEFT JOIN "+table2+" ON "+this.getAlias(this.table1)+"."+key1+" = "+this.getAlias(table2)+"."+key2+" ");
		}
		return this;
	}

	@Override
	public QueryInterface rightJoin(String table2, String key1, String key2) {
		if(Core.isNotNull(table2) && Core.isNotNull(key1) && Core.isNotNull(key2)) {
			this.filterWhere(" RIGHT JOIN "+table2+" ON "+this.getAlias(this.table1)+"."+key1+" = "+this.getAlias(table2)+"."+key2+" ");
		}
		return this;
	}

	@Override
	public QueryInterface outerJoin(String table2, String key1, String key2) {
		if(Core.isNotNull(table2) && Core.isNotNull(key1) && Core.isNotNull(key2)) {
			this.filterWhere(" FULL OUTER JOIN "+table2+" ON "+this.getAlias(this.table1)+"."+key1+" = "+this.getAlias(table2)+"."+key2+" ");
		}
		return this;
	}

	@Override
	public QueryInterface selfJoin(String table2, String key1, String key2) {
		if(Core.isNotNull(table2) && Core.isNotNull(key1) && Core.isNotNull(key2)) {
			this.filterWhere(" AND "+table2+" ON "+this.getAlias(this.table1)+"."+key1+" = "+this.getAlias(table2)+"."+key2+" ");
		}
		return this;
	}

	@Override
	public QueryInterface union() {
		this.filterWhere(" UNION ");
		return this;
	}

	@Override
	public QueryInterface unionAll() {
		this.filterWhere(" UNION ALL ");
		return this;
	}

	
	@Override
	public QueryInterface from(String tables) {
		if(Core.isNotNull(tables)) {
			this.setSql(" FROM "+tables);
			this.table1 = tables;
		}
		return this;
	}
		
	private String getAlias(String tableName) {
		String[] split = tableName.split(" ");
		return split[split.length-1];
	}
	
	public ResultSet executeQuery(Config_env env) {
		ResultSet r = new ResultSet();
		Connection conn =nosi.core.config.Connection.getConnection(env);	
		if(conn!=null) {
			PreparedStatement ps = null;
			try {
				ps = conn.prepareStatement(this.getSql());
				ps.execute();
				r.setSql(this.getSql());
				Core.log("SQL:"+this.getSql());
			} catch (SQLException e) {
				r.setError(e.getMessage());
				Core.log(e.getMessage());
			}
			try {
				conn.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					if(ps!=null)
						ps.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(conn!=null) {
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}		
		}
		return r;
	}
}
