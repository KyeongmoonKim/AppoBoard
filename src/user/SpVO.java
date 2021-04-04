package user;
import java.util.*;

public class SpVO {
	private Map<String, Object> param;
	public SpVO() {
		param = new HashMap<String, Object>();
	}

	public void setParam(String key, Object value) {
		param.put(key, value);
	}

	public Object getParam(String key) {
		Object ret = param.get(key);
		if(ret == null) throw new Error("SpAppoVO getParam : key doesn't exist!");
		else return ret;
	}
	
	public Map<String, Object> retParam() {
		return param;
	}
}
