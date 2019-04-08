package Search.mapping;

import java.util.List;
import java.util.Map;

import Search.Bean.TrainList;

public interface SearchMapper {
	public List<Map<String, Object>> SearchStartStation(Map<String, Object> map) throws Exception;	//查询经过起始站的车次
	
	public List<Map<String, Object>> SearchEndStation(Map<String, Object> map) throws Exception;	//查询经过终点站的车次
	
	public List<TrainList> reList(Map<String, Object> map) throws Exception;	//查询出的来结果返回List
	
	public List<Map<String, Object>> GetStartStationName() throws Exception;
	
	public List<Map<String, Object>> GetEndStationName() throws Exception;
}
