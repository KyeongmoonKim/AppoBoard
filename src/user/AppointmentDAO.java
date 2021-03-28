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
	//멤버 가져 오기
	public List<AppointmentVO> dayAppo(String date) {//date는 YYYY-MM-DD임. date날짜의 모든 일정 조회
		List<AppointmentVO> results = jdbcTemplate.query(
				"SELECT ID, TITLE, EXPLANATION, STARTDATE, ENDDATE, USERID FROM MYAPPOINTMENT WHERE STARTDATE LIKE ? AND ISDELETED=0",
				new RowMapper<AppointmentVO>() {
					@Override
					public AppointmentVO mapRow(ResultSet rs, int rowNum) throws SQLException {
						AppointmentVO avo = new AppointmentVO();
						avo.setId(rs.getInt("ID"));//아이디 세팅
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
	/*public AppointmentVO getAppoWithId(String id) { //특정 id의 일정 조회
		AppointmentVO avo= new AppointmentVO();
		try {
			//db 연결 및 쿼리 작성
			con = dataFactory.getConnection();
			int key = Integer.parseInt(id); //id가 기본 키임
			String q = "SELECT * FROM MYAPPOINTMENT WHERE ID=?";
			pstmt = con.prepareStatement(q);
			pstmt.setInt(1, key);
			
			//쿼리 수행 결과 저장
			ResultSet rs = pstmt.executeQuery();
			int chk = 0;
			while(rs.next()) {
				if(rs.getInt("ISDELETED")==1) chk = 1;
				avo.setId(rs.getInt("ID"));//아이디 세팅
				avo.setTitle(rs.getString("TITLE"));
				avo.setExplanation(rs.getString("EXPLANATION"));
				avo.setStartDate(rs.getString("STARTDATE"));
				avo.setEndDate(rs.getString("ENDDATE"));
				avo.setUserId(rs.getString("USERID"));
			}
			rs.close();
			pstmt.close();
			con.close();
			if(chk==1) { //삭제된 걸 상세 일정으로 보고있음. 추가에러처리 나중에 해야할듯(에러 페이지로 보내기 에러분기만 만들어놓자)
				System.out.println("getAppoWithId Error : 삭제된 일정 상세보기 시도");
				return null;
			}
			//상태 종료
		}
			catch(Exception e) {
			e.printStackTrace();
		}
		return avo;
	}*/
	
	public void makeAppo(AppointmentVO Avo) { //일정 등록, 단순 1줄 짜리 삽입이라 transaction 명시 안했음. 
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement("INSERT INTO MYAPPOINTMENT(ID, TITLE, EXPLANATION, STARTDATE, ENDDATE, ISDELETED, USERID) VALUES (EMP_SEQ.NEXTVAL, ?, ?, ?, ?, 0, ?)");
				pstmt.setString(1,  Avo.getTitle()); //제목
				if(Avo.getExplanation().length()==0) pstmt.setString(2,  "No Explanation"); //내용
				else pstmt.setString(2,  Avo.getExplanation()); //내용
				pstmt.setString(3,  Avo.getStartDate()); //시작일
				pstmt.setString(4,  Avo.getEndDate()); //종료일
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
				pstmt.setString(1,  Avo.getTitle()); //제목
				if(Avo.getExplanation().length()==0) pstmt.setString(2,  "No Explanation"); //내용
				else pstmt.setString(2,  Avo.getExplanation()); //내용
				pstmt.setString(3,  Avo.getStartDate()); //시작일
				pstmt.setString(4,  Avo.getEndDate()); //종료일
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
			pstmt.setInt(1,  key); //키 설정
			
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
 STARTDATE CHAR(16) NOT NULL, "YYYY-MM-DD-HH-mm" 16자리
 ENDDATE CHAR(16) NOT NULL,
 ISDELETED INT,(기본0 삭제시 1)
 USERID VARCHAR(20),
 CONSTRAINT FK_ID FOREIGN KEY(USERID) REFERENCES MYUSER(USERID);
 
 넣을 때, INSERT INTO MYAPPOINTMENT VALUES(EMP_SEQ.NEXTVAL, ...);
*/