package user;

import java.sql.Types;
import oracle.jdbc.OracleTypes;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.object.StoredProcedure;
import java.util.*;
import javax.sql.DataSource;

public class SpDAO{
	 private DataSource ds;
	 public SpDAO(DataSource dataSource) {
	        this.ds = dataSource;
	 }
	 
	 public Map<String, Object> exec(Map<String, Object> params) {
		 SimpleJdbcCall jdbcCall = new SimpleJdbcCall(ds);
		    Map<String, Object> result =
		        jdbcCall.withProcedureName("PROC_NAME")
		            .declareParameters(
		                    new SqlParameter("param1", Types.VARCHAR),
		                    new SqlParameter("param2", Types.VARCHAR),
		                    new SqlOutParameter("results_cursor", OracleTypes.CURSOR, new SomeRowMapper()))
		            .execute("some string", "some other string");
	 }

}
