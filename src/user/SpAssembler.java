package user;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.object.StoredProcedure;
import java.util.*;
import javax.sql.DataSource;

public class SpAssembler{
	private DataSource ds;
	private Map<String, Object> map;
	public SpAssembler(DataSource dataSource) {
		this.ds = dataSource;
		this.map = null;
	}
	
	public void setParams(Map<String, Object> m) {
		this.map = m;
	}
}
