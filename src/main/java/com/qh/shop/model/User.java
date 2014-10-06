package com.qh.shop.model;

import java.util.List;

public class User {
	private int id;
	@ValidateForm(type=ValidateType.NotNull,errorMsg="�û�������Ϊ��")
	private String username;
	@ValidateForm(type=ValidateType.Length,value="6",errorMsg="�û����벻������6λ")
	private String password;
	@ValidateForm(type=ValidateType.NotNull,errorMsg="�û��ǳƲ���Ϊ��")
	private String nickname;
	private int type;
    private List<Address> address;
	
	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password="
				+ password + ", nickname=" + nickname + ", type=" + type
				+ ", address=" + address + "]";
	}
    
}
