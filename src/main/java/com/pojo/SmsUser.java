/*
 * Copyright (C) 2018 Shanghai Sioo soft Co., Ltd
 *
 * All copyrights reserved by Shanghai Sioo.
 * Any copying, transferring or any other usage is prohibited.
 * Or else, Shanghai Sioo possesses the right to require legal 
 * responsibilities from the violator.
 * All third-party contributions are distributed under license by
 * Shanghai Sioo soft Co., Ltd.
 */
package com.pojo;

import java.io.Serializable;

/**
 * @author CQL  2018年7月20日 下午3:42:02
 * @since JDK 1.7	
 */
public class SmsUser implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * UID
	 */
	private int id;
	
	/**
	 * MD5 32位加密密码
	 */
	private String pwd;
	
	/**
	 * 原密码
	 */
	private String dpwd;
	
	/**
	 * 企业名称
	 */
	private String company;
	
	/**
	 * 状态
	 */
	private int stat;
	
	/**
	 * 余额
	 */
	private int sms;
	
	/**
	 * 优先级
	 */
	private int priority;
	
	/**
	 * 用户类型
	 */
	private int userkind;
	
	/**
	 * 企业代码
	 */
	private String username;
	
	/**
	 * 短信接入方式
	 */
	private int submittype;
	
	/**
	 * 行业类别
	 */
	private int hyType;
	
	/**
	 * 用户类型:1为代理,2为终端用户
	 */
	private int usertype;
	
	/**
	 * 是否前台显示报告;0为不显示,1为显示
	 */
	private int isShowRpt;
	
	/**
	 * 是否开通子账号;0为不开通;1为开通
	 */
	private int childFun;
	
	/**
	 * 子账号数量;在childfun为1是起作用
	 */
	private int childNum;
	
	/**
	 * 是否审核;1为不审核;0为审核
	 */
	private int isRelease;
	
	/**
	 * 短信审核数量启始值;在isRelease为0时起作用
	 */
	private int releaseNum;
	
	/**
	 * 内容是否追加'回T退订',1为追加;0为不追加
	 */
	private int replyn;
	
	/**
	 * 重号过滤;0为不过滤;1为10分钟;2为1小时;3为24小时
	 */
	private int repeatFilter;
	
	/**
	 * 重号过滤数量;在repeatFilter大于0时起作用
	 */
	private int repeatNum;
	
	/**
	 * 签名位置;1为前置;2为后置;3为任意
	 */
	private int signPosition;
	
	/**
	 * 签名方式;0为强制签名;1为自定义拓展
	 */
	private int expidSign;
	
	/**
	 * 绑定IP;最大可以绑定10个,用逗号分隔
	 */
	private String proxyIp;
	
	/**
	 * 用户移动通道
	 */
	private int mobile;
	
	/**
	 * 用户联通通道
	 */
	private int unicom;
	
	/**
	 * 用户电信通道
	 */
	private int telecom;
	
	/**
	 * 用户每秒速度限制
	 */
	private int speed;
	
	/**
	 * 是否过滤全量黑名单0为不过滤,1为过滤
	 */
	private int blackAll;
	
	/**
	 * 是否记录重复签名
	 */
	private int repeatSign;
	
	/**
	 * 允许重复签名个数
	 */
	private int repeatSignNum;
	
	/**
	 * 用户线路
	 */
	private int line;
	
	/**
	 * 重号过滤
	 */
	private int repeatFilter2;
	
	/**
	 * 重号过滤次数
	 */
	private int repeatNum2;
	
	/**
	 * 是否是大客户0：小客户，1：大客户
	 */
	private int isBigCustomer;
	
	/**
	 * 是否开通接口状态:0为开通,1为不开通
	 */
	private int apiRpt;

	/**
	 * UID.
	 * @return  the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param   UID  the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * MD532位加密密码.
	 * @return  the pwd
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * @param   MD532位加密密码  the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/**
	 * 原密码.
	 * @return  the dpwd
	 */
	public String getDpwd() {
		return dpwd;
	}

	/**
	 * @param   原密码  the dpwd to set
	 */
	public void setDpwd(String dpwd) {
		this.dpwd = dpwd;
	}

	/**
	 * 企业名称.
	 * @return  the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param   企业名称  the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * 状态.
	 * @return  the stat
	 */
	public int getStat() {
		return stat;
	}

	/**
	 * @param   状态  the stat to set
	 */
	public void setStat(int stat) {
		this.stat = stat;
	}

	/**
	 * 余额.
	 * @return  the sms
	 */
	public int getSms() {
		return sms;
	}

	/**
	 * @param   余额  the sms to set
	 */
	public void setSms(int sms) {
		this.sms = sms;
	}

	/**
	 * 优先级.
	 * @return  the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * @param   优先级  the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * 用户类型.
	 * @return  the userkind
	 */
	public int getUserkind() {
		return userkind;
	}

	/**
	 * @param   用户类型  the userkind to set
	 */
	public void setUserkind(int userkind) {
		this.userkind = userkind;
	}

	/**
	 * 企业代码.
	 * @return  the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param   企业代码  the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 短信接入方式.
	 * @return  the submittype
	 */
	public int getSubmittype() {
		return submittype;
	}

	/**
	 * @param   短信接入方式  the submittype to set
	 */
	public void setSubmittype(int submittype) {
		this.submittype = submittype;
	}

	/**
	 * 行业类别.
	 * @return  the hyType
	 */
	public int getHyType() {
		return hyType;
	}

	/**
	 * @param   行业类别  the hyType to set
	 */
	public void setHyType(int hyType) {
		this.hyType = hyType;
	}

	/**
	 * 用户类型:1为代理2为终端用户.
	 * @return  the usertype
	 */
	public int getUsertype() {
		return usertype;
	}

	/**
	 * @param   用户类型:1为代理2为终端用户  the usertype to set
	 */
	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}

	/**
	 * 是否前台显示报告;0为不显示1为显示.
	 * @return  the isShowRpt
	 */
	public int getIsShowRpt() {
		return isShowRpt;
	}

	/**
	 * @param   是否前台显示报告;0为不显示1为显示  the isShowRpt to set
	 */
	public void setIsShowRpt(int isShowRpt) {
		this.isShowRpt = isShowRpt;
	}

	/**
	 * 是否开通子账号;0为不开通;1为开通.
	 * @return  the childFun
	 */
	public int getChildFun() {
		return childFun;
	}

	/**
	 * @param   是否开通子账号;0为不开通;1为开通  the childFun to set
	 */
	public void setChildFun(int childFun) {
		this.childFun = childFun;
	}

	/**
	 * 子账号数量;在childfun为1是起作用.
	 * @return  the childNum
	 */
	public int getChildNum() {
		return childNum;
	}

	/**
	 * @param   子账号数量;在childfun为1是起作用  the childNum to set
	 */
	public void setChildNum(int childNum) {
		this.childNum = childNum;
	}

	/**
	 * 是否审核;1为不审核;0为审核.
	 * @return  the isRelease
	 */
	public int getIsRelease() {
		return isRelease;
	}

	/**
	 * @param   是否审核;1为不审核;0为审核  the isRelease to set
	 */
	public void setIsRelease(int isRelease) {
		this.isRelease = isRelease;
	}

	/**
	 * 短信审核数量启始值;在isRelease为0时起作用.
	 * @return  the releaseNum
	 */
	public int getReleaseNum() {
		return releaseNum;
	}

	/**
	 * @param   短信审核数量启始值;在isRelease为0时起作用  the releaseNum to set
	 */
	public void setReleaseNum(int releaseNum) {
		this.releaseNum = releaseNum;
	}

	/**
	 * 内容是否追加'回T退订'1为追加;0为不追加.
	 * @return  the replyn
	 */
	public int getReplyn() {
		return replyn;
	}

	/**
	 * @param   内容是否追加'回T退订'1为追加;0为不追加  the replyn to set
	 */
	public void setReplyn(int replyn) {
		this.replyn = replyn;
	}

	/**
	 * 重号过滤;0为不过滤;1为10分钟;2为1小时;3为24小时.
	 * @return  the repeatFilter
	 */
	public int getRepeatFilter() {
		return repeatFilter;
	}

	/**
	 * @param   重号过滤;0为不过滤;1为10分钟;2为1小时;3为24小时  the repeatFilter to set
	 */
	public void setRepeatFilter(int repeatFilter) {
		this.repeatFilter = repeatFilter;
	}

	/**
	 * 重号过滤数量;在repeatFilter大于0时起作用.
	 * @return  the repeatNum
	 */
	public int getRepeatNum() {
		return repeatNum;
	}

	/**
	 * @param   重号过滤数量;在repeatFilter大于0时起作用  the repeatNum to set
	 */
	public void setRepeatNum(int repeatNum) {
		this.repeatNum = repeatNum;
	}

	/**
	 * 签名位置;1为前置;2为后置;3为任意.
	 * @return  the signPosition
	 */
	public int getSignPosition() {
		return signPosition;
	}

	/**
	 * @param   签名位置;1为前置;2为后置;3为任意  the signPosition to set
	 */
	public void setSignPosition(int signPosition) {
		this.signPosition = signPosition;
	}

	/**
	 * 签名方式;0为强制签名;1为自定义拓展.
	 * @return  the expidSign
	 */
	public int getExpidSign() {
		return expidSign;
	}

	/**
	 * @param   签名方式;0为强制签名;1为自定义拓展  the expidSign to set
	 */
	public void setExpidSign(int expidSign) {
		this.expidSign = expidSign;
	}

	/**
	 * 绑定IP;最大可以绑定10个用逗号分隔.
	 * @return  the proxyIp
	 */
	public String getProxyIp() {
		return proxyIp;
	}

	/**
	 * @param   绑定IP;最大可以绑定10个用逗号分隔  the proxyIp to set
	 */
	public void setProxyIp(String proxyIp) {
		this.proxyIp = proxyIp;
	}

	/**
	 * 用户移动通道.
	 * @return  the mobile
	 */
	public int getMobile() {
		return mobile;
	}

	/**
	 * @param   用户移动通道  the mobile to set
	 */
	public void setMobile(int mobile) {
		this.mobile = mobile;
	}

	/**
	 * 用户联通通道.
	 * @return  the unicom
	 */
	public int getUnicom() {
		return unicom;
	}

	/**
	 * @param   用户联通通道  the unicom to set
	 */
	public void setUnicom(int unicom) {
		this.unicom = unicom;
	}

	/**
	 * 用户电信通道.
	 * @return  the telecom
	 */
	public int getTelecom() {
		return telecom;
	}

	/**
	 * @param   用户电信通道  the telecom to set
	 */
	public void setTelecom(int telecom) {
		this.telecom = telecom;
	}

	/**
	 * 用户每秒速度限制.
	 * @return  the speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @param   用户每秒速度限制  the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * 是否过滤全量黑名单0为不过滤1为过滤.
	 * @return  the blackAll
	 */
	public int getBlackAll() {
		return blackAll;
	}

	/**
	 * @param   是否过滤全量黑名单0为不过滤1为过滤  the blackAll to set
	 */
	public void setBlackAll(int blackAll) {
		this.blackAll = blackAll;
	}

	/**
	 * 是否记录重复签名.
	 * @return  the repeatSign
	 */
	public int getRepeatSign() {
		return repeatSign;
	}

	/**
	 * @param   是否记录重复签名  the repeatSign to set
	 */
	public void setRepeatSign(int repeatSign) {
		this.repeatSign = repeatSign;
	}

	/**
	 * 允许重复签名个数.
	 * @return  the repeatSignNum
	 */
	public int getRepeatSignNum() {
		return repeatSignNum;
	}

	/**
	 * @param   允许重复签名个数  the repeatSignNum to set
	 */
	public void setRepeatSignNum(int repeatSignNum) {
		this.repeatSignNum = repeatSignNum;
	}

	/**
	 * 用户线路.
	 * @return  the line
	 */
	public int getLine() {
		return line;
	}

	/**
	 * @param   用户线路  the line to set
	 */
	public void setLine(int line) {
		this.line = line;
	}

	/**
	 * 重号过滤.
	 * @return  the repeatFilter2
	 */
	public int getRepeatFilter2() {
		return repeatFilter2;
	}

	/**
	 * @param   重号过滤  the repeatFilter2 to set
	 */
	public void setRepeatFilter2(int repeatFilter2) {
		this.repeatFilter2 = repeatFilter2;
	}

	/**
	 * 重号过滤次数.
	 * @return  the repeatNum2
	 */
	public int getRepeatNum2() {
		return repeatNum2;
	}

	/**
	 * @param   重号过滤次数  the repeatNum2 to set
	 */
	public void setRepeatNum2(int repeatNum2) {
		this.repeatNum2 = repeatNum2;
	}

	/**
	 * 是否是大客户0：小客户，1：大客户.
	 * @return  the isBigCustomer
	 */
	public int getIsBigCustomer() {
		return isBigCustomer;
	}

	/**
	 * @param   是否是大客户0：小客户，1：大客户  the isBigCustomer to set
	 */
	public void setIsBigCustomer(int isBigCustomer) {
		this.isBigCustomer = isBigCustomer;
	}

	/**
	 * 是否开通接口状态:0为开通1为不开通.
	 * @return  the apiRpt
	 */
	public int getApiRpt() {
		return apiRpt;
	}

	/**
	 * @param   是否开通接口状态:0为开通1为不开通  the apiRpt to set
	 */
	public void setApiRpt(int apiRpt) {
		this.apiRpt = apiRpt;
	}


}
