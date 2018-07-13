package com.pojo;

import java.io.Serializable;

public class MoClass implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8231671339200562757L;
	private int id;
	private String time;
	private int uid;
	private int channel;
	private String mobile;
	private String content;
	private String expid;
	private String src;
	private int stantard;


	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getExpid() {
		return expid;
	}

	public void setExpid(String expid) {
		this.expid = expid;
	}

	/**  
	 * stantard  
	 * @return stantard stantard  
	 */
	public int getStantard() {
		return stantard;
	}

	/**  
	 * stantard  
	 * @return stantard stantard  
	 */
	public void setStantard(int stantard) {
		this.stantard = stantard;
	}

	public MoClass(String time, int channel, String mobile, String content,
                   String expid, String src, int stantard) {
		super();
		this.time = time;
		this.channel = channel;
		this.mobile = mobile;
		this.content = content;
		this.expid = expid;
		this.src = src;
		this.stantard = stantard;
	}

	public MoClass() {
		super();
		// TODO Auto-generated constructor stub
	}
}
