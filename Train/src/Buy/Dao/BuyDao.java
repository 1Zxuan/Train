package Buy.Dao;

import java.util.Map;

public interface BuyDao {
	
	Map<String, Object> SearchTicketinfo_D(Map<String, Object> map);	//查询车辆座位信息D车
	
	Map<String, Object> SearchTicketinfo_Z(Map<String, Object> map);	//查询车辆座位信息Z车
	
	Map<String, Object> SearchTicketinfo_T(Map<String, Object> map);	//查询车辆座位信息T车
	
	boolean BuyTicket(Map<String, Object> map);		//购买车票
	
	boolean GQTicket(Map<String, Object> map);		//改签
	
	boolean CheckTicket(Map<String, Object> map);	//检查用户是否已购买同辆车
}
