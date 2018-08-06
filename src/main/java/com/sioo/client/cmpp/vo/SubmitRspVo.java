package com.sioo.client.cmpp.vo;

import java.io.Serializable;

public class SubmitRspVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	/**
	 * 通道编号
	 */
	private int channel;
	
	/**
	 * 历史记录编号
	 */
	private long hisId;
	
	/**
	 * MsgId
	 */
	private Long msgId;
	
	/**
	 * 响应结果
	 */
	private Integer state;
	
	/**
	 * 日期(格式yyMMdd)
	 */
	private int channelDays;
	
	/**
	 * 日期(格式yyMMdd)
	 */
	private int userDays;

	/**
	 * 用户编号
	 */
	private int uid;
	
	/**
	 * 批次编号
	 */
	private long pid;
	
	private String expid;
	
	/**
	 * 手机号码
	 */
	private String mobile;
	
	/**
	 * 是否为标准接口,主要用于上行匹配(1为标准,0为非标准)
	 */
	private int standard;
	
	/**
	 * 描述
	 */
	private String mtstat;

	/**
	 * 历史记录编号
	 * @return hisId 历史记录编号
	 */
	public long getHisId() {
		return hisId;
	}

	/**
	 * 历史记录编号
	 * @param hisId 历史记录编号
	 */
	public void setHisId(long hisId) {
		this.hisId = hisId;
	}

	/**
	 * MsgId
	 * @return msgId MsgId
	 */
	public Long getMsgId() {
		return msgId;
	}

	/**
	 * MsgId
	 * @param msgId MsgId
	 */
	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	/**
	 * 响应结果
	 * @return state 响应结果
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * 响应结果
	 * @param state 响应结果
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * id
	 * @return id id
	 */
	public int getId() {
		return id;
	}

	/**
	 * id
	 * @param id id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 通道编号
	 * @return channel 通道编号
	 */
	public int getChannel() {
		return channel;
	}

	/**
	 * 通道编号
	 * @param channel 通道编号
	 */
	public void setChannel(int channel) {
		this.channel = channel;
	}

	/**
	 * 用户编号
	 * @return uid 用户编号
	 */
	public int getUid() {
		return uid;
	}

	/**
	 * 用户编号
	 * @param uid 用户编号
	 */
	public void setUid(int uid) {
		this.uid = uid;
	}

	/**
	 * 批次编号
	 * @return pid 批次编号
	 */
	public long getPid() {
		return pid;
	}

	/**
	 * expid
	 * @return expid expid
	 */
	public String getExpid() {
		return expid;
	}

	/**
	 * expid
	 * @param expid expid
	 */
	public void setExpid(String expid) {
		this.expid = expid;
	}

	/**
	 * 批次编号
	 * @param pid 批次编号
	 */
	public void setPid(long pid) {
		this.pid = pid;
	}
	/**  
	 * 手机号码  
	 * @return mobile 手机号码  
	 */
	public String getMobile() {
		return mobile;
	}

	/**  
	 * 描述  
	 * @return mtstat 描述  
	 */
	public String getMtstat() {
		return mtstat;
	}

	/**  
	 * 描述  
	 * @return mtstat 描述  
	 */
	public void setMtstat(String mtstat) {
		this.mtstat = mtstat;
	}

	/**  
	 * 手机号码  
	 * @return mobile 手机号码  
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**  
	 * 是否为标准接口主要用于上行匹配(1为标准0为非标准)
	 * @return standard 是否为标准接口主要用于上行匹配(1为标准0为非标准)
	 */
	public int getStandard() {
		return standard;
	}

	/**  
	 * 是否为标准接口主要用于上行匹配(1为标准0为非标准)
	 * @return standard 是否为标准接口主要用于上行匹配(1为标准0为非标准)
	 */
	public void setStandard(int standard) {
		this.standard = standard;
	}

	/**  
	 * 日期(格式yyMMdd)  
	 * @return channelDays 日期(格式yyMMdd)  
	 */
	public int getChannelDays() {
		return channelDays;
	}

	/**  
	 * 日期(格式yyMMdd)  
	 * @return channelDays 日期(格式yyMMdd)  
	 */
	public void setChannelDays(int channelDays) {
		this.channelDays = channelDays;
	}

	/**  
	 * 日期(格式yyMMdd)  
	 * @return userDays 日期(格式yyMMdd)  
	 */
	public int getUserDays() {
		return userDays;
	}

	/**  
	 * 日期(格式yyMMdd)  
	 * @return userDays 日期(格式yyMMdd)  
	 */
	public void setUserDays(int userDays) {
		this.userDays = userDays;
	}

	public SubmitRspVo(int channel, long hisId, String mobile, Long msgId, Integer state, int channelDays, int userDays, int uid, long pid, String expid, int standard, String mtstat) {
		super();
		this.channel = channel;
		this.hisId = hisId;
		this.mobile = mobile;
		this.msgId = msgId;
		this.state = state;
		this.channelDays = channelDays;
		this.userDays = userDays;
		this.uid = uid;
		this.pid = pid;
		this.expid = expid;
		this.standard = standard;
		this.mtstat = mtstat;
	}

	public SubmitRspVo() {
		// TODO Auto-generated constructor stub
	}

}
