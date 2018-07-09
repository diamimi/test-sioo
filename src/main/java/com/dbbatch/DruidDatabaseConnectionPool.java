package com.dbbatch;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 
 */
public final class DruidDatabaseConnectionPool {
	private static final Logger LOG = LoggerFactory.getLogger((DruidDatabaseConnectionPool.class).getSimpleName());
	
	private static DruidDataSource _dds = null;
	private static String confile = "druid.properties";
	private static Properties p = null;
	private static InputStream inputStream = null;

	/**
	 */
	public static void startup() {
		try {
			p = new Properties();
		confile = Class.class.getClass().getResource("/").getPath() + confile;
			File file = new File(confile);  
//			File file = new File("src/main/resources/druid.properties");
            inputStream = new BufferedInputStream(new FileInputStream(file));  
            p.load(inputStream);
            
	        try {
	            _dds = (DruidDataSource) DruidDataSourceFactory.createDataSource(p);
	            LOG.info("DruidPool Inited......");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			throw new DatabaseException(e);
		} finally {
			try {
				if (inputStream != null) {  
				    inputStream.close();  
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 */
	public static void shutdown() {
		_dds.close();
	}

	/**
	 */
	public static Connection getConnection() {
		try {
			if(_dds == null){
				startup();
			}
			return _dds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			throw new DatabaseException(e);
		}
	}
	
	public static void main(String[] args) {
		DruidDatabaseConnectionPool.startup();
	}

}
