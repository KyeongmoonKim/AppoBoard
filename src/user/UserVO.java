package user;

public class UserVO {
	private String userId;
	private String userPwd;
	private String name;
	
	public UserVO() {
		this.userId = null;
		this.userPwd = null;
		this.name = null;
	}
	//매우중요 이거 잭슨쓰려면 getter만들때 엄격하게 ㅁ나들어줘야함. userId프로퍼티면 getUserId로
	public void setId(String a) {
		this.userId = a;
	}
	public String getUserId() {
		return this.userId;
	}
	public void setPwd(String a) {
		this.userPwd = a;
	}
	public String getUserPwd() {
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
