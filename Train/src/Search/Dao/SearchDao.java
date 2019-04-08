package Search.Dao;

import java.util.List;
import java.util.Map;

import Search.Bean.TrainList;

public interface SearchDao {
	List<TrainList> Search(Map<String, Object> map);	//��ѯ  A to B
	
	List<Map<String, Object>> GetStartStationName();	//��ѯ���г�վ��
	
	List<Map<String, Object>> GetEndStationName();	//��ѯ���г�վ��
}
