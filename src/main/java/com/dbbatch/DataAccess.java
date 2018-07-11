package com.dbbatch;

import com.pojo.SendingVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 */
public abstract class DataAccess {

	/**
	 */
	private static final Logger LOG = LoggerFactory.getLogger((DataAccess.class).getSimpleName());
	/**
	 */
	private Connection conn;

	/**
	 * @param conn
	 */
	protected DataAccess(Connection conn) {
		this.conn = conn;
	}
	


	protected void batchInsertHistoryContentFor5(List<SendingVo> list){
		PreparedStatement pstmtBefore = null;
		try {
			conn.setAutoCommit(false);
			pstmtBefore = conn.prepareStatement("INSERT INTO smshy.sms_history_content_for5 (pid,senddate,content,uid)VALUE(?,?,?,?)");
			for(SendingVo v : list){
				try {
					pstmtBefore.setInt(1, v.getPid());
					pstmtBefore.setInt(2,Integer.parseInt( (v.getSenddate()+"").substring(0,8)));
					pstmtBefore.setString(3, filterEmoji(v.getContent()));
					pstmtBefore.setInt(4, v.getUid());
					pstmtBefore.addBatch();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					for(SendingVo v1 : list){
					}
				}
			}
			int i = pstmtBefore.executeBatch().length;
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			throw new DataAccessException(e);
		} finally {
			if(pstmtBefore != null) try{pstmtBefore.close();}catch (Exception e2) {}
		}
	}

	protected void batchInser114History(List<SendingVo> list,String table){
		PreparedStatement pstmtBefore = null;
		try {
			conn.setAutoCommit(false);
			pstmtBefore = conn.prepareStatement("INSERT INTO smshy2.sms_user_history_"+table+" (hisid,senddate,mtype,uid,channel,mobile,content,contentNum,stat,rptcode,rpttime)VALUE(?,?,?,?,?,?,?,?,?,?,?)");
			for(SendingVo v : list){
				try {
					pstmtBefore.setLong(1, v.getId());
					pstmtBefore.setLong(2, v.getSenddate());
					pstmtBefore.setInt(3,v.getMtype());
					pstmtBefore.setInt(4,v.getUid());
					pstmtBefore.setInt(5,v.getChannel());
					pstmtBefore.setLong(6,v.getMobile());
					pstmtBefore.setString(7,v.getContent());
					pstmtBefore.setInt(8,v.getContentNum());
					pstmtBefore.setInt(9,v.getStat());
					pstmtBefore.setString(10,"DELIVRD");
					pstmtBefore.setLong(11, v.getRpttime());
					pstmtBefore.addBatch();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					for(SendingVo v1 : list){
					}
				}
			}
			int i = pstmtBefore.executeBatch().length;
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			throw new DataAccessException(e);
		} finally {
			if(pstmtBefore != null) try{pstmtBefore.close();}catch (Exception e2) {}
		}
	}
	

	private static boolean isNotEmojiCharacter(char codePoint){ 
		return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)); 
	} 
	/** 
	* 过滤emoji 或者 其他非文字类型的字符 
	* @param source 
	* @return 
	*/ 
	public static String filterEmoji(String source) { 
		int len = source.length(); 
		StringBuilder buf = new StringBuilder(len); 
		for (int i = 0; i < len; i++) { 
			char codePoint = source.charAt(i); 
			if (isNotEmojiCharacter(codePoint)) { 
				buf.append(codePoint); 
			} else{
				buf.append("");
			}
		} 
		return buf.toString(); 
	}

	public void updateRpt(List<SendingVo> list) {
		PreparedStatement pstmtBefore = null;
		try {
			conn.setAutoCommit(false);
			pstmtBefore = conn.prepareStatement("update smshy2.sms_user_history_180508 set rptcode=?,rpttime=? where hisid=?");
				try {
					for (SendingVo vo : list) {
						pstmtBefore.setString(1, vo.getRptStat());
						pstmtBefore.setLong(2,vo.getRpttime());
						pstmtBefore.setLong(3, vo.getId());
						pstmtBefore.addBatch();
					}

				} catch (Exception e) {
				}
			int i = pstmtBefore.executeBatch().length;
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			throw new DataAccessException(e);
		} finally {
			if(pstmtBefore != null) try{pstmtBefore.close();}catch (Exception e2) {}
		}
	}

	/**
	 * 
	 * 
	 * @param <T>
	 */
	public class Result<T>{
		List<T> list = new ArrayList<T>();
		String idStr = "";
		
		public List<T> getList() {
			return list;
		}
		
		public void setList(List<T> list) {
			this.list = list;
		}
		
		public String getIdStr() {
			return idStr;
		}
		
		public void setIdStr(String idStr) {
			this.idStr = idStr;
		}
		
		public Result() {
			super();
		}
	}

	/**
	 * @param sql
	 */
	private PreparedStatement getPreparedStatement(String sql, Object... params) throws DataAccessException {
		PreparedStatement pstmt = getPreparedStatement(sql);
		setParameters(pstmt, params);
		return pstmt;
	}

	/**
	 * @param sql
	 */
	private PreparedStatement getPreparedStatement(String sql) throws DataAccessException {
		try {
			return conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			throw new DataAccessException(e);
		}
	}

	/**
	 */
	private void setParameters(PreparedStatement pstmt, Object... params) throws DataAccessException {
		try {
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i + 1, params[i]);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			throw new DataAccessException(e);
		}
	}

	/**
	 * 
	 * @param pstmt
	 * @throws DataAccessException
	 */
	private int executeUpdate(PreparedStatement pstmt) throws DataAccessException {
		try {
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			throw new DataAccessException(e);
		}
	}
}