package Buy.mapping;

import java.util.Map;

public interface BuyMapper {
	public Map<String, Object> SearchTicketinfo_D (Map<String, Object> map) throws Exception;//��ѯD������λ��Ϣ
	
	public Map<String, Object> SearchTicketinfo_arrive_day_D(Map<String,Object> map) throws Exception;//��ѯD������������;
	
	public Map<String, Object> SearchTicketinfo_Z (Map<String, Object> map) throws Exception;//��ѯZ������λ��Ϣ
	
	public Map<String, Object> SearchTicketinfo_arrive_day_Z(Map<String,Object> map) throws Exception;//��ѯZ������������;
	
	public Map<String, Object> SearchTicketinfo_T (Map<String, Object> map) throws Exception;//��ѯT������λ��Ϣ
	
	public Map<String, Object> SearchTicketinfo_arrive_day_T(Map<String,Object> map) throws Exception;//��ѯT������������;
	
	public Map<String, Object> Searchuserinfo(Map<String, Object> map) throws Exception;//��ѯ�û���Ϣ
	
	public boolean BuyTicket(Map<String, Object> map )throws Exception;	//	��Ʊ
	
	public boolean GQTicket(Map<String, Object> map) throws Exception;	//	��ǩ
	
	public boolean CheckTicket(Map<String, Object> map) throws Exception;	//����û��Ƿ��ѹ���ͬ����
	
	public void delete_Befor_Trian(Map<String, Object> map) throws Exception;	//ɾ��֮ǰ�ĳ���
	
	public int SearchStartStation_NO(Map<String, Object> map) throws Exception; //	��ѯ��ʼվվ��
	
	public int Search_Befor_StartStation_NO(Map<String, Object> map) throws Exception;	//��ѯ��ǩ֮ǰ��������ʼվվ��
	
	public int Search_Befor_EndStation_NO(Map<String, Object> map) throws Exception;	//��ѯ��ǩ֮ǰ�������յ�վվ��
	/**
	 *
	 *���ں����Ʊ��
	 * 
	 * */
	
	public void UpdateSeat_FirstClass(Map<String, Object> map) throws Exception;	//������Ʊ
	
	public void UpdateSeat_SecondClass(Map<String, Object> map) throws Exception;	//������Ʊ
	
	public void UpdateSeat_NoSeat(Map<String, Object> map) throws Exception;	//������Ʊ
	
	public void UpdateSeat_SoftBerth(Map<String, Object> map) throws Exception;	//������Ʊ
	
	public void UpdateSeat_HardBerth(Map<String, Object> map) throws Exception;	//������Ʊ
	
	public void UpdateSeat_HardSeat(Map<String, Object> map) throws Exception;	//������Ʊ
	
	public void UpdateSeat_high_GradeSB(Map<String, Object> map) throws Exception;	//������Ʊ
	
	
	
	/**
	 * 
	 * ��ǩ��������λ
	 * 
	 * */
	public void ADD_UpdateSeat_FirstClass(Map<String, Object> map) throws Exception;	//������Ʊ
	
	public void ADD_UpdateSeat_SecondClass(Map<String, Object> map) throws Exception;	//������Ʊ
	
	public void ADD_UpdateSeat_NoSeat(Map<String, Object> map) throws Exception;	//������Ʊ
	
	public void ADD_UpdateSeat_SoftBerth(Map<String, Object> map) throws Exception;	//������Ʊ
	
	public void ADD_UpdateSeat_HardBerth(Map<String, Object> map) throws Exception;	//������Ʊ
	
	public void ADD_UpdateSeat_HardSeat(Map<String, Object> map) throws Exception;	//������Ʊ
	
	public void ADD_UpdateSeat_high_GradeSB(Map<String, Object> map) throws Exception;	//������Ʊ
}
