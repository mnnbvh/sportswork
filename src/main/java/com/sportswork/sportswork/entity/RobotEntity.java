package com.sportswork.sportswork.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author junpeng
 * @email 157237022@qq.com
 * @date 2021-06-18 17:33:37
 */
public class RobotEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//机器人编号
	private String robotSn;
	//机器人类型
	private String robotType;
	//所在编号
	private String liveSn;
	//机器人信息
	private String robotInfo;
	//备注
	private String remark;
	//其他
	private String other;
	//在线状态
	private String online;
	private String username;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：机器人编号
	 */
	public void setRobotSn(String robotSn) {
		this.robotSn = robotSn;
	}
	/**
	 * 获取：机器人编号
	 */
	public String getRobotSn() {
		return robotSn;
	}
	/**
	 * 设置：机器人类型
	 */
	public void setRobotType(String robotType) {
		this.robotType = robotType;
	}
	/**
	 * 获取：机器人类型
	 */
	public String getRobotType() {
		return robotType;
	}
	/**
	 * 设置：所在编号
	 */
	public void setLiveSn(String liveSn) {
		this.liveSn = liveSn;
	}
	/**
	 * 获取：所在编号
	 */
	public String getLiveSn() {
		return liveSn;
	}
	/**
	 * 设置：机器人信息
	 */
	public void setRobotInfo(String robotInfo) {
		this.robotInfo = robotInfo;
	}
	/**
	 * 获取：机器人信息
	 */
	public String getRobotInfo() {
		return robotInfo;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：其他
	 */
	public void setOther(String other) {
		this.other = other;
	}
	/**
	 * 获取：其他
	 */
	public String getOther() {
		return other;
	}
	/**
	 * 设置：在线状态
	 */
	public void setOnline(String online) {
		this.online = online;
	}
	/**
	 * 获取：在线状态
	 */
	public String getOnline() {
		return online;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
