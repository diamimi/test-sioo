package com.dbbatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 
 */
public class DatabaseTransaction {

	/**
	 */
	private static final Logger LOG = LoggerFactory.getLogger((DatabaseTransaction.class).getSimpleName());

	/**
	 */
	private Connection conn;

	/**
	 */
	public DatabaseTransaction() {
		this(DruidDatabaseConnectionPool.getConnection());
	}

	/**
	 * 
	 * @param isOpenTrans
	 */
	public DatabaseTransaction(boolean isOpenTrans) throws DatabaseException {
		this(DruidDatabaseConnectionPool.getConnection(), isOpenTrans);
	}

	/**
	 * 
	 * @param conn
	 */
	public DatabaseTransaction(Connection conn) {
		this.conn = conn;
	}

	/**
	 */
	public DatabaseTransaction(Connection conn, boolean isOpenTrans) throws DatabaseException {
		this.conn = conn;
		setAutoCommit(!isOpenTrans);
	}

	/**
	 */
	public Connection getConnection() {
		return conn;
	}

	/**
	 */
	private void setAutoCommit(boolean autoCommit) throws DatabaseException {
		try {
			conn.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			throw new DatabaseException(e);
		}
	}

	/**
	 * 
	 * @throws DatabaseException
	 */
	public void begin() throws DatabaseException {
		setAutoCommit(false);
	}

	/**
	 * @throws DatabaseException
	 */
	public boolean isBegin() throws DatabaseException {
		try {
			return !conn.getAutoCommit();
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			throw new DatabaseException(e);
		}
	}

	/**
	 * 
	 * @throws DatabaseException
	 */
	public void commit() throws DatabaseException {
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			throw new DatabaseException(e);
		}
	}

	/**
	 * 
	 * @throws DatabaseException
	 */
	public void rollback() throws DatabaseException {
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			throw new DatabaseException(e);
		}
	}

	/**
	 * 
	 * @throws DatabaseException
	 */
	public void close() throws DatabaseException {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			throw new DatabaseException(e);
		}
	}

	/**
	 * @throws DatabaseException
	 */
	public boolean isClose() throws DatabaseException {
		try {
			return conn.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			throw new DatabaseException(e);
		}
	}

}
