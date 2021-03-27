package user;

import java.sql.Timestamp;

public class AppointmentVO {
	private int id;
	private String title;
	private String explanation;
	private String startDate;
	private String endDate;
	private String userId;
	
	public AppointmentVO() {
		this.id = -1;
		this.title = null;
		this.explanation = null;
		this.startDate = null;
		this.endDate = null;
		this.userId = null;
	}
	public void setId(int a) {
		this.id = a;
	}
	public int getId() {
		return this.id;
	}
	public void setTitle(String a) {
		this.title = a;
	}
	public String getTitle() {
		return this.title;
	}
	public void setExplanation(String a) {
		this.explanation = a;
	}
	public String getExplanation() {
		return this.explanation;
	}
	public void setStartDate(String a) {
		this.startDate = a;
	}
	public String getStartDate() {
		return this.startDate;
	}
	public void setEndDate(String a) {
		this.endDate = a;
	}
	public String getEndDate() {
		return this.endDate;
	}
	public void setUserId(String a) {
		this.userId = new String(a);
	}
	public String getUserId() {
		return this.userId;
	}
}


/*
 TABLE INFORMATION
 ID INT PRIMARY KEY(AUTO INCREMENT),
 TITLE VARCHAR(40) NOT NULL,
 EXPLANATION VARCHAR(255),
 STARTDATE CHAR(16) NOT NULL, "YYYY-MM-DD-HH-mm" 16자리
 ENDDATE CHAR(16) NOT NULL,
 ISDELETED INT,(기본0 삭제시 1)
 USERID VARCHAR(20),
 CONSTRAINT FK_ID FOREIGN KEY(USERID) REFERENCES MYUSER(USERID);
 
 넣을 때, INSERT INTO MYAPPOINTMENT VALUES(EMP_SEQ.NEXTVAL, ...);
*/
