package Root.mapping;

import java.util.List;
import java.util.Map;

import Root.Bean.TrainStation;

public interface RootMapper {
	public List<Map<String, Object>> trainNameList() throws Exception;
	
	public List<TrainStation> trainNameInfo(String trainName) throws Exception;
	
	public boolean ChangeData(Map<String, Object> map) throws Exception;
}
