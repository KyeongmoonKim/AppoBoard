package user;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import org.springframework.jdbc.core.*;
import org.springframework.transaction.annotation.*;
import user.MyCalendar;
import user.MyPair;
import user.AppointmentVO;

public class AppointmentDAO {
	private JdbcTemplate jdbcTemplate; 
	public AppointmentDAO(DataSource dataSource){
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	//��� ���� ����
	public List<AppointmentVO> dayAppo(String date) {//date�� YYYY-MM-DD��. date��¥�� ��� ���� ��ȸ
		List<AppointmentVO> results = jdbcTemplate.query(
				"SELECT ID, TITLE, EXPLANATION, STARTDATE, ENDDATE, USERID FROM MYAPPOINTMENT WHERE STARTDATE LIKE ? AND ISDELETED=0",
				new RowMapper<AppointmentVO>() {
					@Override
					public AppointmentVO mapRow(ResultSet rs, int rowNum) throws SQLException {
						AppointmentVO avo = new AppointmentVO();
						avo.setId(rs.getInt("ID"));//���̵� ����
						avo.setTitle(rs.getString("TITLE"));
						avo.setExplanation(rs.getString("EXPLANATION"));
						avo.setStartDate(rs.getString("STARTDATE"));
						avo.setEndDate(rs.getString("ENDDATE"));
						avo.setUserId(rs.getString("USERID"));
						return avo;
					}
				}, date);
		return results;
	}
	
	public List<MyPair> getMonthAppo(String YM) {
		List<MyPair> results = jdbcTemplate.query(
				"SELECT DISTINCT(SUBSTR(STARTDATE, 1, 10)) AS MONTHDATE, COUNT(*) AS CNT FROM MYAPPOINTMENT WHERE STARTDATE LIKE ? AND ISDELETED=0 GROUP BY SUBSTR(STARTDATE, 1, 10)",
				new RowMapper<MyPair>() {
					@Override
					public MyPair mapRow(ResultSet rs, int rowNum) throws SQLException {
						MyPair temp = new MyPair();
						temp.key = rs.getString("MONTHDATE");
						temp.value = Integer.toString(rs.getInt("CNT"));
						return temp;
					}
				}, YM+"%");
		return results;
		
	}
	/*public AppointmentVO getAppoWithId(String id) { //Ư�� id�� ���� ��ȸ
		AppointmentVO avo= new AppointmentVO();
		try {
			//db ���� �� ���� �ۼ�
			con = dataFactory.getConnection();
			int key = Integer.parseInt(id); //id�� �⺻ Ű��
			String q = "SELECT * FROM MYAPPOINTMENT WHERE ID=?";
			pstmt = con.prepareStatement(q);
			pstmt.setInt(1, key);
			
			//���� ���� ��� ����
			ResultSet rs = pstmt.executeQuery();
			int chk = 0;
			while(rs.next()) {
				if(rs.getInt("ISDELETED")==1) chk = 1;
				avo.setId(rs.getInt("ID"));//���̵� ����
				avo.setTitle(rs.getString("TITLE"));
				avo.setExplanation(rs.getString("EXPLANATION"));
				avo.setStartDate(rs.getString("STARTDATE"));
				avo.setEndDate(rs.getString("ENDDATE"));
				avo.setUserId(rs.getString("USERID"));
			}
			rs.close();
			pstmt.close();
			con.close();
			if(chk==1) { //������ �� �� �������� ��������. �߰�����ó�� ���߿� �ؾ��ҵ�(���� �������� ������ �����б⸸ ��������)
				System.out.println("getAppoWithId Error : ������ ���� �󼼺��� �õ�");
				return null;
			}
			//���� ����
		}
			catch(Exception e) {
			e.printStackTrace();
		}
		return avo;
	}*/
	
	public void makeAppo(AppointmentVO Avo) { //���� ���, �ܼ� 1�� ¥�� �����̶� transaction ��� ������. 
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement("INSERT INTO MYAPPOINTMENT(ID, TITLE, EXPLANATION, STARTDATE, ENDDATE, ISDELETED, USERID) VALUES (EMP_SEQ.NEXTVAL, ?, ?, ?, ?, 0, ?)");
				pstmt.setString(1,  Avo.getTitle()); //����
				if(Avo.getExplanation().length()==0) pstmt.setString(2,  "No Explanation"); //����
				else pstmt.setString(2,  Avo.getExplanation()); //����
				pstmt.setString(3,  Avo.getStartDate()); //������
				pstmt.setString(4,  Avo.getEndDate()); //������
				pstmt.setString(5,  Avo.getUserId());
				return pstmt;
			}
		});
	}
	public void reviseAppo(AppointmentVO Avo) {
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement("UPDATE MYAPPOINTMENT SET TITLE=?, EXPLANATION=?, STARTDATE=?, ENDDATE=? WHERE ID=?");
				pstmt.setString(1,  Avo.getTitle()); //����
				if(Avo.getExplanation().length()==0) pstmt.setString(2,  "No Explanation"); //����
				else pstmt.setString(2,  Avo.getExplanation()); //����
				pstmt.setString(3,  Avo.getStartDate()); //������
				pstmt.setString(4,  Avo.getEndDate()); //������
				pstmt.setInt(5, Avo.getId());
				return pstmt;
			}
		});
	}
	/*public void deleteAppo(String id) {
		try {
			con = dataFactory.getConnection();
			int key = Integer.parseInt(id);
			
			String q = "UPDATE MYAPPOINTMENT SET ISDELETED=1 WHERE ID=?";
			pstmt = con.prepareStatement(q);
			pstmt.setInt(1,  key); //Ű ����
			
			pstmt.executeUpdate();
			
			pstmt.close();
			con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	} */
}


/*
 TABLE INFORMATION
 ID INT PRIMARY KEY(AUTO INCREMENT),
 TITLE VARCHAR(40) NOT NULL,
 EXPLANATION VARCHAR(255),
 STARTDATE CHAR(16) NOT NULL, "YYYY-MM-DD-HH-mm" 16�ڸ�
 ENDDATE CHAR(16) NOT NULL,
 ISDELETED INT,(�⺻0 ������ 1)
 USERID VARCHAR(20),
 CONSTRAINT FK_ID FOREIGN KEY(USERID) REFERENCES MYUSER(USERID);
 
 ���� ��, INSERT INTO MYAPPOINTMENT VALUES(EMP_SEQ.NEXTVAL, ...);
*/