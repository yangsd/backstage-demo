package com.demo.system.bean;

import javax.persistence.*;

/**
 * 系统用户
 * 
 * @author sdyang
 * @date 2016年1月23日 下午5:32:11
 */

public class User {

	private Long pk_user;// 主键

	private String loginid;// 登录帐号

	private String password;// 密码

	private String username;// 用户姓名

	private String email;// 邮箱

	private String salt; // 加密密码的盐

	private Long roleid; // 拥有的角色

	private String rolename;// 角色名称

	private Boolean status = Boolean.FALSE;// 状态：默认启用0

	public User() {
	}

	public User(String loginid, String password) {
		this.loginid = loginid;
		this.password = password;
	}

	public Long getPk_user() {
		return pk_user;
	}

	public void setPk_user(Long pk_user) {
		this.pk_user = pk_user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getCredentialsSalt() {
		return username + salt;
	}

	public Long getRoleid() {
		return roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

}
