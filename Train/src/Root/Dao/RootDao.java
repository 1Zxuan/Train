package Root.Dao;

import java.util.List;
import java.util.Map;

import Root.Bean.TrainStation;

public interface RootDao {
	List<Map<String, Object>> trainName();
	
	List<TrainStation> trainNameInfo(String string);
	
	Boolean ChangeData(Map<String, Object> map);
}
