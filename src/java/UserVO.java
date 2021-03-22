package java;

public class UserVO {
	private String userId;
	private String userPwd;
	private String name;
	
	public UserVO() {
		this.userId = null;
		this.userPwd = null;
		this.name = null;
	}
	
	public void setId(String a) {
		this.userId = a;
	}
	public String getId() {
		return this.userId;
	}
	public void setPwd(String a) {
		this.userPwd = a;
	}
	public String getPwd() {
		return this.userPwd;
	}
	public void setName(String a) {
		this.name = a;
	}
	public String getName() {
		return this.name;
	}
}



/*
 USERID VARCHAR(20) PRIMARY KEY,
 USERPWD VARCHAR(20) NOT NULL,
 NAME VARCHAR(20) NOT NULL
 */
