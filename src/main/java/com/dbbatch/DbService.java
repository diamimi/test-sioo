package com.dbbatch;


import com.pojo.SendingVo;

import java.util.List;

/**
 * 
 */
public class DbService extends BaseService {
	public DbService() {
		super();
	}

	public DbService(DatabaseTransaction trans) {
		super(trans);
	}
	
	public void batchInsertHistoryContentFor5(List<SendingVo> list){
		DbDao dao = new DbDao(getConnection());
		dao.batchInsertHistoryContentFor5(list);
	}

	public void updateRpt(List<SendingVo> list) {
		DbDao dao = new DbDao(getConnection());
		dao.updateRpt(list);
	}
}