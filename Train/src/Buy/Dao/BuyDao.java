package Buy.Dao;

import java.util.Map;

public interface BuyDao {
	
	Map<String, Object> SearchTicketinfo_D(Map<String, Object> map);	//��ѯ������λ��ϢD��
	
	Map<String, Object> SearchTicketinfo_Z(Map<String, Object> map);	//��ѯ������λ��ϢZ��
	
	Map<String, Object> SearchTicketinfo_T(Map<String, Object> map);	//��ѯ������λ��ϢT��
	
	boolean BuyTicket(Map<String, Object> map);		//����Ʊ
	
	boolean GQTicket(Map<String, Object> map);		//��ǩ
	
	boolean CheckTicket(Map<String, Object> map);	//����û��Ƿ��ѹ���ͬ����
}
