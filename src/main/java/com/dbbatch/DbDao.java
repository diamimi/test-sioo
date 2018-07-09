package com.dbbatch;

import org.apache.log4j.Logger;

import java.sql.Connection;

public class DbDao extends DataAccess {
	Logger logger = Logger.getLogger(getClass());
	public DbDao(Connection conn) {
		super(conn);
	}


}
