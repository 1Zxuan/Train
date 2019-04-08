package Search.Dao;

import java.util.List;
import java.util.Map;

import Search.Bean.TrainList;

public interface SearchDao {
	List<TrainList> Search(Map<String, Object> map);	//查询  A to B
	
	List<Map<String, Object>> GetStartStationName();	//查询所有车站名
	
	List<Map<String, Object>> GetEndStationName();	//查询所有车站名
}
