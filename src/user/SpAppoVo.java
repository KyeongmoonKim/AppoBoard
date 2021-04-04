package user;
import java.util.*;

public class SpAppoVo implements ISpVO{
	private Map<String, Object> map;
	public SpAppoVo() {
		map = new HashMap<String, Object>();
	}
	@Override
	public void setParam(String key, Object value) {
		map.put(key, value);
	}
	@Override
	public Object getParam(String key) {
		Object ret = map.get(key);
		if(ret == null) throw new Error("SpAppoVO getParam : key doesn't exist!");
		else return ret;
	}
}
