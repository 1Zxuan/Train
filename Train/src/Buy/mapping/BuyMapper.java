package Buy.mapping;

import java.util.Map;

public interface BuyMapper {
	public Map<String, Object> SearchTicketinfo_D (Map<String, Object> map) throws Exception;//查询D车辆座位信息
	
	public Map<String, Object> SearchTicketinfo_arrive_day_D(Map<String,Object> map) throws Exception;//查询D车辆到达日期;
	
	public Map<String, Object> SearchTicketinfo_Z (Map<String, Object> map) throws Exception;//查询Z车辆座位信息
	
	public Map<String, Object> SearchTicketinfo_arrive_day_Z(Map<String,Object> map) throws Exception;//查询Z车辆到达日期;
	
	public Map<String, Object> SearchTicketinfo_T (Map<String, Object> map) throws Exception;//查询T车辆座位信息
	
	public Map<String, Object> SearchTicketinfo_arrive_day_T(Map<String,Object> map) throws Exception;//查询T车辆到达日期;
	
	public Map<String, Object> Searchuserinfo(Map<String, Object> map) throws Exception;//查询用户信息
	
	public boolean BuyTicket(Map<String, Object> map )throws Exception;	//	购票
	
	public boolean GQTicket(Map<String, Object> map) throws Exception;	//	改签
	
	public boolean CheckTicket(Map<String, Object> map) throws Exception;	//检查用户是否已购买同辆车
	
	public void delete_Befor_Trian(Map<String, Object> map) throws Exception;	//删除之前的车辆
	
	public int SearchStartStation_NO(Map<String, Object> map) throws Exception; //	查询起始站站序
	
	public int Search_Befor_StartStation_NO(Map<String, Object> map) throws Exception;	//查询改签之前车辆的起始站站序
	
	public int Search_Befor_EndStation_NO(Map<String, Object> map) throws Exception;	//查询改签之前车辆的终点站站序
	/**
	 *
	 *改期后减少票数
	 * 
	 * */
	
	public void UpdateSeat_FirstClass(Map<String, Object> map) throws Exception;	//更改余票
	
	public void UpdateSeat_SecondClass(Map<String, Object> map) throws Exception;	//更改余票
	
	public void UpdateSeat_NoSeat(Map<String, Object> map) throws Exception;	//更改余票
	
	public void UpdateSeat_SoftBerth(Map<String, Object> map) throws Exception;	//更改余票
	
	public void UpdateSeat_HardBerth(Map<String, Object> map) throws Exception;	//更改余票
	
	public void UpdateSeat_HardSeat(Map<String, Object> map) throws Exception;	//更改余票
	
	public void UpdateSeat_high_GradeSB(Map<String, Object> map) throws Exception;	//更改余票
	
	
	
	/**
	 * 
	 * 改签后增加座位
	 * 
	 * */
	public void ADD_UpdateSeat_FirstClass(Map<String, Object> map) throws Exception;	//更改余票
	
	public void ADD_UpdateSeat_SecondClass(Map<String, Object> map) throws Exception;	//更改余票
	
	public void ADD_UpdateSeat_NoSeat(Map<String, Object> map) throws Exception;	//更改余票
	
	public void ADD_UpdateSeat_SoftBerth(Map<String, Object> map) throws Exception;	//更改余票
	
	public void ADD_UpdateSeat_HardBerth(Map<String, Object> map) throws Exception;	//更改余票
	
	public void ADD_UpdateSeat_HardSeat(Map<String, Object> map) throws Exception;	//更改余票
	
	public void ADD_UpdateSeat_high_GradeSB(Map<String, Object> map) throws Exception;	//更改余票
}
