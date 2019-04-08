package Search.mapping;

import java.util.List;
import java.util.Map;

import Search.Bean.TrainList;

public interface SearchMapper {
	public List<Map<String, Object>> SearchStartStation(Map<String, Object> map) throws Exception;	//��ѯ������ʼվ�ĳ���
	
	public List<Map<String, Object>> SearchEndStation(Map<String, Object> map) throws Exception;	//��ѯ�����յ�վ�ĳ���
	
	public List<TrainList> reList(Map<String, Object> map) throws Exception;	//��ѯ�������������List
	
	public List<Map<String, Object>> GetStartStationName() throws Exception;
	
	public List<Map<String, Object>> GetEndStationName() throws Exception;
}
