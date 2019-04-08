package Buy.Dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import Buy.mapping.BuyMapper;
import tools.DBTools;

public class BuyDaoImpl implements BuyDao{
	private SqlSession session;
	private BuyMapper mapper;
	Map<String, Object>reMap=new HashMap<>();	//车辆信息
	Map<String, Object>reMap2=new HashMap<>();	//车辆历时
	Map<String, Object>userMap=new HashMap<>();	//用户类型
	Map<String, Object>reMap3=new HashMap<String,Object>();	//用于返回的Map
	boolean in=false;
	public void ConnectionSQL(){
		try {
			session=DBTools.getSession();
			mapper=session.getMapper(BuyMapper.class);
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
		}
	}
	@Override
	public Map<String, Object> SearchTicketinfo_D(Map<String, Object> map) {
		// TODO Auto-generated method stub
		ConnectionSQL();
		 try {
			reMap=mapper.SearchTicketinfo_D(map);//获取车票信息
			reMap2=mapper.SearchTicketinfo_arrive_day_D(map);
			reMap3=Station_no(reMap, reMap2);
			userMap=mapper.Searchuserinfo(map);	//获取用户类型
			reMap3.putAll(userMap);
			reMap.clear();
			reMap2.clear();
			userMap.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return setDuration(reMap3);
	}
	@Override
	public Map<String, Object> SearchTicketinfo_Z(Map<String, Object> map) {
		ConnectionSQL();
		try {
			reMap=mapper.SearchTicketinfo_Z(map);
			reMap2=mapper.SearchTicketinfo_arrive_day_Z(map);
			reMap3=Station_no(reMap, reMap2);
			userMap=mapper.Searchuserinfo(map);
			reMap3.putAll(userMap);
			reMap.clear();
			reMap2.clear();
			userMap.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return setDuration(reMap3);
	}
	@Override
	public Map<String, Object> SearchTicketinfo_T(Map<String, Object> map) {
		ConnectionSQL();
		try {
			reMap=mapper.SearchTicketinfo_T(map);					//开始站序号
			reMap2=mapper.SearchTicketinfo_arrive_day_T(map);		//到达站站序号
			reMap3=Station_no(reMap, reMap2);
			userMap=mapper.Searchuserinfo(map);	
			reMap3.putAll(userMap);
			reMap.clear();
			reMap2.clear();
			userMap.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return setDuration(reMap3);
	}
	
	public Map<String, Object> Station_no(Map<String, Object> map,Map<String, Object> map2) {
		Map<String, Object> map3=new HashMap<>();
		String ss=map.get("station_no").toString(),es=map2.get("station_no").toString();
		int counts=Integer.parseInt(es)-Integer.parseInt(ss);
		map3.put("station_no", String.valueOf(counts));
		map.remove("station_no");map2.remove("station_no");
		map3.putAll(map);
		map3.putAll(map2);
		return map3;
	}
	
	public Map<String, Object> setDuration (Map<String, Object> map){//	计算历时
		Map<String, Object> map2=new HashMap<>();
		map2.putAll(map);
		String starttime,arrivetime,arrive_day_diff;
		starttime=map.get("start_time").toString();
		arrivetime=map.get("arrive_time").toString();
		DateFormat df = new SimpleDateFormat("HH:mm");
		int rt,tm;
		String temp;
		if(map.get("arrive_day_diff").equals("0")){
			try {
				Date star,arr;
				star = df.parse(starttime);
				arr=df.parse(arrivetime);
				@SuppressWarnings("deprecation")
//				int starm=star.getHours()*60+star.getMinutes();
//				int arrm=arr.getMinutes()+arr.getHours()*60;
				int hhstarm=arr.getHours()-star.getHours()-1;
				int jm=hhstarm*60+(60-star.getMinutes())+arr.getMinutes();
				rt=jm/60;
				tm=(jm-(rt*60));
				temp=rt+":"+tm;
				map2.replace("arrive_day_diff", temp);
				System.out.println(map2.get("arrive_day_diff"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else if(map.get("arrive_day_diff").equals("1")){
			Date star,arr;
			try {
				star = df.parse(starttime);
				arr=df.parse(arrivetime);
				System.out.println(star.getMinutes());
				int starm=((23-star.getHours())*60)+(60-star.getMinutes());
				int arrm=arr.getHours()*60+arr.getMinutes();
				int jm=starm+arrm;
				rt=jm/60;
				System.err.println(jm);
				tm=(jm-(rt*60));
				temp=rt+":"+tm;
				map2.replace("arrive_day_diff", temp);
				System.out.println(map2.get("arrive_day_diff"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return map2;
	}
	@Override
	public boolean BuyTicket(Map<String, Object> map) {
		ConnectionSQL();
		try {
			in=mapper.BuyTicket(map);
			session.commit();
			int start_no=mapper.SearchStartStation_NO(map);
			System.out.println("**************"+start_no);
			int l=Integer.parseInt(map.get("station_no").toString());//总共的站数
			System.out.println(map.get("trainName").toString().substring(0, 1));
			if(map.get("trainName").toString().substring(0, 1).equals("D") && map.get("SeatName").toString().equals("FirstClass")){//更新D车一等座
				System.out.println("开始更新票数");
				for(int i=start_no;i<=l+start_no;i++){
					map.replace("station_no", i);
					mapper.UpdateSeat_FirstClass(map);
					session.commit();
				}
			}else if (map.get("trainName").toString().substring(0, 1).equals("D") && map.get("SeatName").toString().equals("SecondClass")) {//更新D车二等座
				System.out.println("开始更新票数");
				for(int i=start_no;i<=l+start_no;i++){
					map.replace("station_no", i);
					mapper.UpdateSeat_SecondClass(map);
					session.commit();
				}
			}else if (map.get("SeatName").toString().equals("NoSeat")) {//更新无座
				System.out.println("开始更新票数");
				for(int i=start_no;i<=l+start_no;i++){
					map.replace("station_no", i);
					mapper.UpdateSeat_NoSeat(map);
					session.commit();
				}
			}else if(map.get("SeatName").toString().equals("SoftBerth")){
				System.out.println("开始更新票数");
				for(int i=start_no;i<=l+start_no;i++){
					map.replace("station_no", i);
					mapper.UpdateSeat_SoftBerth(map);
					session.commit();
				}
			}else if(map.get("SeatName").toString().equals("HardBerth")){
				System.out.println("开始更新票数");
				for(int i=start_no;i<=l+start_no;i++){
					map.replace("station_no", i);
					mapper.UpdateSeat_HardBerth(map);
					session.commit();
				}
			}else if (map.get("SeatName").toString().equals("HardSeat")){
				System.out.println("开始更新票数");
				for(int i=start_no;i<=l+start_no;i++){
					map.replace("station_no", i);
					mapper.UpdateSeat_HardSeat(map);
					session.commit();
				}
			}else if(map.get("trainName").toString().substring(0, 1).equals("Z") && map.get("SeatName").toString().equals("high_GradeSB")){
				System.out.println("开始更新票数");
				for(int i=start_no;i<=l+start_no;i++){
					map.replace("station_no", i);
					mapper.UpdateSeat_high_GradeSB(map);
					session.commit();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return in;
	}
	@Override
	public boolean GQTicket(Map<String, Object> map) {
		// TODO Auto-generated method stub
		ConnectionSQL();
		try {
			in=mapper.GQTicket(map);
			session.commit();
			
			mapper.delete_Befor_Trian(map);
			session.commit();
			
			int start_no=mapper.SearchStartStation_NO(map);
			int l=Integer.parseInt(map.get("station_no").toString());//总共的站数
			System.out.println(map.get("trainName").toString().substring(0, 1));
			if(map.get("trainName").toString().substring(0, 1).equals("D") && map.get("SeatName").toString().equals("FirstClass")){//更新D车一等座
				System.out.println("开始更新票数");
				for(int i=start_no;i<=l+start_no;i++){
					map.replace("station_no", i);
					mapper.UpdateSeat_FirstClass(map);
					session.commit();
				}
			}else if (map.get("trainName").toString().substring(0, 1).equals("D") && map.get("SeatName").toString().equals("SecondClass")) {//更新D车二等座
				System.out.println("开始更新票数");
				for(int i=start_no;i<=l+start_no;i++){
					map.replace("station_no", i);
					mapper.UpdateSeat_SecondClass(map);
					session.commit();
				}
			}else if (map.get("SeatName").toString().equals("NoSeat")) {//更新无座
				System.out.println("开始更新票数");
				for(int i=start_no;i<=l+start_no;i++){
					map.replace("station_no", i);
					mapper.UpdateSeat_NoSeat(map);
					session.commit();
				}
			}else if(map.get("SeatName").toString().equals("SoftBerth")){
				System.out.println("开始更新票数");
				for(int i=start_no;i<=l+start_no;i++){
					map.replace("station_no", i);
					mapper.UpdateSeat_SoftBerth(map);
					session.commit();
				}
			}else if(map.get("SeatName").toString().equals("HardBerth")){
				System.out.println("开始更新票数");
				for(int i=start_no;i<=l+start_no;i++){
					map.replace("station_no", i);
					mapper.UpdateSeat_HardBerth(map);
					session.commit();
				}
			}else if (map.get("SeatName").toString().equals("HardSeat")){
				System.out.println("开始更新票数");
				for(int i=start_no;i<=l+start_no;i++){
					map.replace("station_no", i);
					mapper.UpdateSeat_HardSeat(map);
					session.commit();
				}
			}else if(map.get("trainName").toString().substring(0, 1).equals("Z") && map.get("SeatName").toString().equals("high_GradeSB")){
				System.out.println("开始更新票数");
				for(int i=start_no;i<=l+start_no;i++){
					map.replace("station_no", i);
					mapper.UpdateSeat_high_GradeSB(map);
					session.commit();
				}
			}
			
			
			int strat_no=mapper.Search_Befor_StartStation_NO(map);
			
			int end_start_no=mapper.Search_Befor_EndStation_NO(map);
			if(map.get("Befor_trianName").toString().substring(0, 1).equals("D") && map.get("SeatName").toString().equals("FirstClass")){//更新D车一等座
				System.out.println("开始更新票数");
				for(int i=start_no;i<end_start_no;i++){
					map.replace("station_no", i);
					mapper.ADD_UpdateSeat_FirstClass(map);
					session.commit();
				}
			}else if (map.get("Befor_trianName").toString().substring(0, 1).equals("D") && map.get("SeatName").toString().equals("SecondClass")) {//更新D车二等座
				System.out.println("开始更新票数");
				for(int i=start_no;i<end_start_no;i++){
					map.replace("station_no", i);
					mapper.ADD_UpdateSeat_SecondClass(map);
					session.commit();
				}
			}else if (map.get("SeatName").toString().equals("NoSeat")) {//更新无座
				System.out.println("开始更新票数");
				for(int i=start_no;i<end_start_no;i++){
					map.replace("station_no", i);
					mapper.ADD_UpdateSeat_NoSeat(map);
					session.commit();
				}
			}else if(map.get("SeatName").toString().equals("SoftBerth")){
				System.out.println("开始更新票数");
				for(int i=start_no;i<end_start_no;i++){
					map.replace("station_no", i);
					mapper.ADD_UpdateSeat_SoftBerth(map);
					session.commit();
				}
			}else if(map.get("SeatName").toString().equals("HardBerth")){
				System.out.println("开始更新票数");
				for(int i=start_no;i<end_start_no;i++){
					map.replace("station_no", i);
					mapper.ADD_UpdateSeat_HardBerth(map);
					session.commit();
				}
			}else if (map.get("SeatName").toString().equals("HardSeat")){
				System.out.println("开始更新票数");
				for(int i=start_no;i<end_start_no;i++){
					map.replace("station_no", i);
					mapper.ADD_UpdateSeat_HardSeat(map);
					session.commit();
				}
			}else if(map.get("Befor_trianName").toString().substring(0, 1).equals("Z") && map.get("SeatName").toString().equals("high_GradeSB")){
				System.out.println("开始更新票数");
				for(int i=start_no;i<end_start_no;i++){
					map.replace("station_no", i);
					mapper.ADD_UpdateSeat_high_GradeSB(map);
					session.commit();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		
		return in;
	}
	@Override
	public boolean CheckTicket(Map<String, Object> map) {
		// TODO Auto-generated method stub
		ConnectionSQL();
		try {
			in=!mapper.CheckTicket(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			session.close();
		}
		return in;
	}
}	
