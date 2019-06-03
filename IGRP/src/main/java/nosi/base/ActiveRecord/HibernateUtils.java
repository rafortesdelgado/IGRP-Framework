package nosi.base.ActiveRecord;
/**
 * Emanuel
 * 29 May 2018
 */

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import nosi.core.config.ConfigApp;
import nosi.core.config.ConfigDBIGRP;
import nosi.core.webapp.Core;
import nosi.core.webapp.databse.helpers.DatabaseConfigHelper;
import nosi.core.webapp.security.EncrypDecrypt;
import nosi.webapps.igrp.dao.Config_env;

public class HibernateUtils {

	private static ServiceRegistry registry = null;
	private static final Map<String, SessionFactory> SESSION_FACTORY = new HashMap<>();

	public static SessionFactory getSessionFactory(Config_env config_env) {
		if (config_env != null && config_env.getApplication() != null)
			return getSessionFactory(config_env.getName(), config_env.getApplication().getDad());
		return getSessionFactory(ConfigApp.getInstance().getBaseConnection(), Core.getCurrentDadParam());
	}

	public static SessionFactory getSessionFactory(String connectionName) {
		return getSessionFactory(connectionName, Core.getCurrentDadParam());
	}

	public static SessionFactory getSessionFactory(String connectionName, String dad) {
		String myConnectionName = resolveHibernateFileName(connectionName, dad);
		if (!SESSION_FACTORY.containsKey(myConnectionName)) {
			MetadataSources metadataSources = null;
			try {
				registry = buildConfig(connectionName, myConnectionName, dad);
				metadataSources = new MetadataSources(registry);
				SESSION_FACTORY.put(myConnectionName,
						metadataSources.buildMetadata().getSessionFactoryBuilder().build());
			} catch (Exception ex) {
				SESSION_FACTORY.remove(myConnectionName);
				System.err.println("Initial SessionFactory creation failed." + ex);
				Core.log(ex.getMessage());
				throw new ExceptionInInitializerError(ex);
			} finally {
				if (metadataSources != null) {
					ServiceRegistry metaServiceRegistry = metadataSources.getServiceRegistry();
					if (metaServiceRegistry instanceof BootstrapServiceRegistry) {
						BootstrapServiceRegistryBuilder.destroy(metaServiceRegistry);
					}
				}
			}
		}
		return SESSION_FACTORY.get(myConnectionName);
	}

	@SuppressWarnings("unchecked")
	private static ServiceRegistry buildConfig(String connectionName, String myConnectionName, String dad) {
		StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
		try {
			registryBuilder.configure(myConnectionName + ".cfg.xml");
			registryBuilder.applySettings(getSettings(connectionName, dad,
					(Map<String, String>) registryBuilder.getAggregatedCfgXml().getConfigurationValues()));
		} catch (org.hibernate.internal.util.config.ConfigurationException e) {
			e.printStackTrace();
		}
		return registryBuilder.build();
	}

	private static Map<String, String> getSettings(String connectionName, String dad,
			Map<String, String> configurationValues) {
		if (configurationValues != null) {
			Map<Object, Object> user = configurationValues.entrySet().stream()
					.filter(key -> key.getKey().toString().equals(Environment.USER)
							|| key.getKey().toString().equals(Environment.PASS)
							|| key.getKey().toString().equals(Environment.URL))
					.collect(Collectors.toMap(k -> k, v -> v));
			/**
			 * Check if connection configuration in hibernate file
			 */
			if (user == null || (user != null && user.size()<=0)) {
				DefaultConfigHibernate defaultConfig = DefaultConfigHibernate.getInstance();
				//Strategy used to access the JDBC Metadata	
				configurationValues.put(Environment.HBM2DDL_JDBC_METADATA_EXTRACTOR_STRATEGY,defaultConfig.getAccessStrategyJDBC().orElse("individually"));
				//Thread session
				configurationValues.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, defaultConfig.getCurrentSessionContextClass().orElse("org.hibernate.context.internal.ThreadLocalSessionContext"));
				//Connection provider
				configurationValues.put(Environment.CONNECTION_PROVIDER, defaultConfig.getProvider_class());
				
				ConfigApp configApp = ConfigApp.getInstance();
				//Load default connection database when connection name is hibernate-core-igrp
				if (configApp.getBaseConnection().equalsIgnoreCase(connectionName)) {
					ConfigDBIGRP config = ConfigDBIGRP.getInstance();
					try {
						config.loadIGRPConnectionConfig();
					} catch (Exception e) {
						e.printStackTrace();
					}
					String hibernateDialect = DatabaseConfigHelper.getHibernateDialect(config.getType_db());
					configurationValues.put(Environment.USER, config.getUsername());
					configurationValues.put(Environment.URL, config.getUrlConnection());
					configurationValues.put(Environment.PASS, config.getPassword());
					configurationValues.put(Environment.DRIVER, config.getDriverConnection());
					configurationValues.put(Environment.DIALECT, hibernateDialect);
					return configurationValues;
				}

				Config_env config = getConfigdatabase(connectionName, dad);
        		if (config != null) {	
					configurationValues.put(Environment.USER, config.getUsername());
					configurationValues.put(Environment.URL, config.getUrl_connection());
					configurationValues.put(Environment.PASS, config.getPassword());
					configurationValues.put(Environment.DRIVER, config.getDriver_connection());
					configurationValues.put(Environment.DIALECT, config.getType_db());
					 if(defaultConfig.getUseConnectionPool().compareTo("true")==0) {
			        	if(defaultConfig.getProvider_class().equals("org.hibernate.hikaricp.internal.HikariCPConnectionProvider")) {
					       //hickaricp config	        	        
							//Maximum waiting time for a connection from the pool. 
			        		configurationValues.put("hibernate.hikari.connectionTimeout",defaultConfig.getConnectionTimeout());
							//Maximum time that a connection is allowed to sit ideal in the pool
			        		configurationValues.put("hibernate.hikari.idleTimeout", defaultConfig.getIdleTimeout());	
							//Minimum number of ideal connections in the pool
			        		configurationValues.put("hibernate.hikari.minimumIdle",defaultConfig.getMinimumIdle());
							//Maximum number of actual connection in the pool
			        		configurationValues.put("hibernate.hikari.maximumPoolSize", defaultConfig.getMaximumPoolSize());
							//Maximum lifetime of a connection in the pool
			        		configurationValues.put("hibernate.hikari.maxLifetime", defaultConfig.getMaxLifetime());
							//Detected leak
			        		configurationValues.put("hibernate.hikari.leakDetectionThreshold", defaultConfig.getLeakDetectionThreshold());
			        	}else if(defaultConfig.getProvider_class().equals("org.hibernate.connection.C3P0ConnectionProvider")) {
			        		configurationValues.put(Environment.C3P0_TIMEOUT,defaultConfig.getConnectionTimeout());
			        		configurationValues.put(Environment.C3P0_MIN_SIZE,defaultConfig.getMinimumPoolSize());
			        		configurationValues.put(Environment.C3P0_MAX_SIZE,defaultConfig.getMaximumPoolSize());
			        		configurationValues.put(Environment.C3P0_ACQUIRE_INCREMENT,defaultConfig.getIncrement());
			        	}
			        }	  
				}
			}
		}
		return configurationValues;
	}

	private static Config_env getConfigdatabase(String connectionName, String dad) {
		Config_env config = new Config_env().find().where("name", "=", connectionName)
				.andWhere("application.dad", "=", dad).one();
		if (config != null) {
			config.setUsername(Core.decrypt(config.getUsername(), EncrypDecrypt.SECRET_KEY_ENCRYPT_DB));
			config.setDriver_connection(
					Core.decrypt(config.getDriver_connection(), EncrypDecrypt.SECRET_KEY_ENCRYPT_DB));
			config.setPassword(Core.decrypt(config.getPassword(), EncrypDecrypt.SECRET_KEY_ENCRYPT_DB));
			if (Core.isNotNull(config.getUrl_connection())) {
				config.setUrl_connection(Core.decrypt(config.getUrl_connection(), EncrypDecrypt.SECRET_KEY_ENCRYPT_DB));
			} else {
				config.setUrl_connection(DatabaseConfigHelper.getUrl(
						Core.decrypt(config.getType_db(), EncrypDecrypt.SECRET_KEY_ENCRYPT_DB),
						Core.decrypt(config.getHost(), EncrypDecrypt.SECRET_KEY_ENCRYPT_DB),
						Core.decrypt(config.getPort(), EncrypDecrypt.SECRET_KEY_ENCRYPT_DB),
						Core.decrypt(config.getName_db(), EncrypDecrypt.SECRET_KEY_ENCRYPT_DB)));
			}
			String hibernateDialect = DatabaseConfigHelper
					.getHibernateDialect(Core.decrypt(config.getType_db(), EncrypDecrypt.SECRET_KEY_ENCRYPT_DB));
			config.setType_db(hibernateDialect);
		}
		return config;
	}

	private static String resolveHibernateFileName(String connectionName, String dad) {
		if (!connectionName.equalsIgnoreCase(ConfigApp.getInstance().getBaseConnection())
				&& !connectionName.equalsIgnoreCase(ConfigApp.getInstance().getH2IGRPBaseConnection())) {
			connectionName = connectionName + "." + dad;
		}
		return connectionName;
	}

	public synchronized static void destroy() {
		if (registry != null) {
			StandardServiceRegistryBuilder.destroy(registry);
			registry = null;
		}
	}

	public synchronized static void unregisterAllDrivers() {
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			try {
				DriverManager.deregisterDriver(drivers.nextElement());
			} catch (SQLException e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	public static void closeAllConnection() {
		if (SESSION_FACTORY != null) {
			SESSION_FACTORY.entrySet().stream().forEach(s -> {
				s.getValue().close();
			});
		}
	}

	public static void removeSessionFactory(String connectionName) {
		if (SESSION_FACTORY != null) {
			SESSION_FACTORY.remove(connectionName);
		}
	}

}
