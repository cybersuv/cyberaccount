package com.suv.flat.model;

public class User {
	private int userid;
	private String user_name;
	private String user_login;
	private String password;
	private int user_role_id;
	private String create_date;
	private int org_id;
	private String org_name;
	private String org_mnemonic;
	
	public User(){
		this.userid=-1;
	}
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_login() {
		return user_login;
	}
	public void setUser_login(String user_login) {
		this.user_login = user_login;
	}
	public int getUser_role_id() {
		return user_role_id;
	}
	public void setUser_role_id(int user_role_id) {
		this.user_role_id = user_role_id;
	}
	@Override
	public String toString() {
		return "User [userid=" + userid + ", user_name=" + user_name
				+ ", user_login=" + user_login + ", password=" + password
				+ ", user_role_id=" + user_role_id + "]";
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public int getOrg_id() {
		return org_id;
	}

	public void setOrg_id(int org_id) {
		this.org_id = org_id;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public String getOrg_mnemonic() {
		return org_mnemonic;
	}

	public void setOrg_mnemonic(String org_mnemonic) {
		this.org_mnemonic = org_mnemonic;
	}
	
	
}
