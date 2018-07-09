package com.dbbatch;

import java.sql.Connection;

public abstract class BaseService {

	private DatabaseTransaction trans;

	protected BaseService(DatabaseTransaction trans) {
		this.trans = trans;
	}

	protected BaseService() {
	}

	protected DatabaseTransaction getTransaction() {
		return trans;
	}

	protected Connection getConnection() {
		return trans.getConnection();
	}
}