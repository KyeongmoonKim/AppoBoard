package user;

import java.sql.Types;
import oracle.jdbc.OracleTypes;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.object.StoredProcedure;
import java.util.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.sql.DataSource;


public class SpDAO{
	 private DataSource ds;
	 public SpDAO(DataSource dataSource) {
	        this.ds = dataSource;
	 }
	 
	 public Map<String, Object> exec(Map<String, Object> params) {
		 System.out.println("SpDAO called!");
		 int count = Integer.parseInt((String)params.get("counts")); //파라미터 개수
		 System.out.println("count : " + Integer.toString(count));
		 Map<String, Object> values = new HashMap<String, Object>(); //파라미터 밸류 저장
		 
		 SimpleJdbcCall jdbcCall = new SimpleJdbcCall(ds).withProcedureName((String)params.get("sqlReq")); //프로시저 호출
		 System.out.println("req : " +(String)params.get("sqlReq"));
		 for(int i = 0; i < count; i++) { //정의한 타입에 따라 키랑 밸류 선언 및 저장
			 String pKey = (String)params.get("key"+Integer.toString(i));
			 System.out.println("pKey : "  + pKey);
			 String pValue = (String)params.get("value"+Integer.toString(i));
			 System.out.println("pValue : "  + pValue);
			 String pType = (String)params.get("type"+Integer.toString(i));
			 System.out.println("pType : "  + pType);
			 if(pType.compareTo("int")==0) {
				 jdbcCall.addDeclaredParameter(new SqlParameter(pKey, Types.INTEGER));
				 values.put(pKey, Integer.getInteger(pValue));
			 } else if(pType.compareTo("string")==0) {
				 jdbcCall.addDeclaredParameter(new SqlParameter(pKey, Types.VARCHAR));
				 values.put(pKey, pValue);
			 } else {
				 System.out.println("not declared type in the spDAO:line28");
				 throw new Error("not declared type in the spDAO:line28");
			 }
		 }
		 System.out.println(values.get("PDATE"));
		 jdbcCall.addDeclaredParameter(new SqlOutParameter("MYCURSOR", OracleTypes.CURSOR, new RowMapper<SpVO>() {//SpVO에 결과 받아 올 것.
			 @Override
			 public SpVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				 SpVO svo = new SpVO();
			     ResultSetMetaData rsmd = rs.getMetaData();
				 int columnCnt = rsmd.getColumnCount(); //컬럼의 수
				 for(int i = 1; i <= columnCnt; i++) { //중요 오라클 컬럼인덱스 번호는 1부터시작
					 System.out.println(rsmd.getColumnName(i)+", " + rs.getString(rsmd.getColumnName(i)));
					 svo.setParam(rsmd.getColumnName(i), rs.getObject(rsmd.getColumnName(i))); //컬럼명에
				 }
				 return svo;
			 }
		 }));
		 Map<String, Object> result = jdbcCall.execute(values);
		 return result;
	 }
}
