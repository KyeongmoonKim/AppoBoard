package user;
import java.util.*;

public class SpVO {
	private Map<String, Object> map;
	public SpVO() {
		map = new HashMap<String, Object>();
	}

	public void setParam(String key, Object value) {
		map.put(key, value);
	}

	public Object getParam(String key) {
		Object ret = map.get(key);
		if(ret == null) throw new Error("SpAppoVO getParam : key doesn't exist!");
		else return ret;
	}
}
