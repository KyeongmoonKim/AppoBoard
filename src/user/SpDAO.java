package user;

import java.sql.Types;
import oracle.jdbc.OracleTypes;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.object.StoredProcedure;
import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;


public class SpDAO{
	 private DataSource ds;
	 public SpDAO(DataSource dataSource) {
	        this.ds = dataSource;
	 }
	 
	 public Map<String, Object> exec(Map<String, Object> params) {
		 int count = Integer.parseInt((String)params.get("counts")); //파라미터 개수
		 Map<String, Object> values = new HashMap<String, Object>(); //파라미터 밸류 저장
		 
		 SimpleJdbcCall jdbcCall = new SimpleJdbcCall(ds).withProcedureName((String)params.get("sqlReq")); //프로시저 호출
		 
		 for(int i = 0; i < count; i++) { //타입에 따라 넣을거
			 String pKey = (String)params.get((String)params.get("key"+Integer.toString(i)));
			 String pValue = (String)params.get((String)params.get("value"+Integer.toString(i)));
			 String pType = (String)params.get((String)params.get("type"+Integer.toString(i)));
			 if(pType.compareTo("int")==0) {
				 jdbcCall.addDeclaredParameter(new SqlParameter(pKey, Types.INTEGER));
				 values.put(pKey, Integer.getInteger(pValue));
			 } else if(pType.compareTo("int")==0) {
				 jdbcCall.addDeclaredParameter(new SqlParameter(pKey, Types.VARCHAR));
				 values.put(pKey, pValue);
			 } else {
				 System.out.println("not declared type in the spDAO:line28");
				 throw new Error("not declared type in the spDAO:line28");
			 }
		 }
		 
		 jdbcCall.addDeclaredParameter("results_cursor", OracleTypes.CURSOR, new RowMapper<SpVO>() {
			 @Override
			 public SpVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				 SpVO svo = new SpVO();
				 for(int i = 0; i < count; i++) {
					 String pKey = (String)params.get((String)params.get("key"+Integer.toString(i)));
					 String pType = (String)params.get((String)params.get("type"+Integer.toString(i)));
					 svo.setParam(pKey, value);
				 }
			 }
		 });
		 Map<String, Object> result = jdbcCall.execute(values);
		    /*Map<String, Object> result =
		        jdbcCall.withProcedureName((String)params.get("sqlReq"))
		            .declareParameters(
		            		for(int i = 0; i < n; i++) {
		                    new SqlParameter("param1", Types.VARCHAR),
		            		}
		                    new SqlOutParameter("results_cursor", OracleTypes.CURSOR, new SomeRowMapper()))
		            .execute("some string", "some other string");*/
	 }

}
