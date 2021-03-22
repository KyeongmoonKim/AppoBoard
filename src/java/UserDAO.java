package java;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import org.springframework.jdbc.core.*;
import org.springframework.transaction.annotation.*;

public class UserDAO {
	
	private JdbcTemplate jdbcTemplate; 
	public UserDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public void login(String id, String pwd) {
		List<UserVO> results = jdbcTemplate.query(
				"SELECT * FROM MYUSER WHERE USERID = ? ",
				new RowMapper<UserVO>() {
					@Override
					public UserVO mapRow(ResultSet rs, int rowNum) throws SQLException {
						UserVO uvo = new UserVO();
						uvo.setId(rs.getString("USERID"));
						uvo.setPwd(rs.getString("USERPWD"));
						uvo.setName(rs.getString("NAME"));
						return uvo;
					}
				}, id);
		//List<T> query(String sql, Object[] args, RowMapper<T> rowMapper) 같은 타입일경우에거
		//List<T> query(String sql, RowMapper<T> rowMapper, Object... args)
		if(results.size()!=1) throw new LoginFailException("no such ID"); //해당되는 id가 없거나 여러개임.
		UserVO temp = results.get(0); //하나니까 가져옴.
		if(temp.getPwd().compareTo(pwd)!=0) throw new LoginFailException("pwd is wrong!");
	}
	public boolean isDup(String id) {
		List<UserVO> results = jdbcTemplate.query(
				"SELECT * FROM MYUSER WHERE USERID = ? ",
				new RowMapper<UserVO>() {
					@Override
					public UserVO mapRow(ResultSet rs, int rowNum) throws SQLException {
						UserVO uvo = new UserVO();
						uvo.setId(rs.getString("USERID"));
						uvo.setPwd(rs.getString("USERPWD"));
						uvo.setName(rs.getString("NAME"));
						return uvo;
					}
				}, id);
		if(!results.isEmpty()) return true; //아이디 중복
		else return false; //아이디 중복 없음
	}
	
	public void insertUser(UserVO uvo) {
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement("INSERT INTO MYUSER(USERID, USERPWD, NAME) VALUES (?, ?, ?)");
				pstmt.setString(1, uvo.getId());
				pstmt.setString(2, uvo.getPwd());
				pstmt.setString(3, uvo.getName());
				return pstmt;
			}
		});
	}
	public void changePwd(String id, String pwd) {
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement("UPDATE MYUSER SET USERPWD = ? WHERE USERID = ?");
				pstmt.setString(1, pwd);
				pstmt.setString(2, id);
				return pstmt;
			}
		});
	}
	@Transactional
	public void changePwds(String[] ids, String[] pwds, String[] pwdNews) {
		for(int i = 0; i < ids.length; i++) {
			login(ids[i], pwds[i]); //login test;
			changePwd(ids[i], pwdNews[i]);
		}
	}
}
