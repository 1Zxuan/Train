package TicketSearch.mapping;

import java.util.List;
import java.util.Map;

public interface TicketMapper {
	List<Map<String, Object>> HomepageTicketInfo(String string) throws Exception;
	
	boolean Unsubscribe(Map<String, Object> map) throws Exception;
	
	public Map<String, Object> pdgaiqian(Map<String, Object> map) throws Exception;
	
	public int SearchStartStation_NO(Map<String, Object> map) throws Exception; //	��ѯ��ʼվվ��
	
	public int SearchEndStation_NO(Map<String, Object> map) throws Exception; //	��ѯ��ʼվվ��
	
	public void UpdateSeat_FirstClass(Map<String, Object> map) throws Exception;	//������Ʊ
	
	public void UpdateSeat_SecondClass(Map<String, Object> map) throws Exception;	//������Ʊ
	
	public void UpdateSeat_NoSeat(Map<String, Object> map) throws Exception;	//������Ʊ
	
	public void UpdateSeat_SoftBerth(Map<String, Object> map) throws Exception;	//������Ʊ
	
	public void UpdateSeat_HardBerth(Map<String, Object> map) throws Exception;	//������Ʊ
	
	public void UpdateSeat_HardSeat(Map<String, Object> map) throws Exception;	//������Ʊ
	
	public void UpdateSeat_high_GradeSB(Map<String, Object> map) throws Exception;	//������Ʊ
}
