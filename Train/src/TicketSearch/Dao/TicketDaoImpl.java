package TicketSearch.Dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import TicketSearch.mapping.TicketMapper;
import login.mapping.loginMapper;
import tools.DBTools;


public class TicketDaoImpl implements TicketDao {
	private SqlSession session;
	private TicketMapper mapper;
	Map<String, Object> reMap=new HashMap<>();
	
	@Override
	public List<Map<String, Object>> HomepageTicketInfo(String string) {
		// TODO Auto-generated method stub
		ConnectionSQL();
		List<Map<String, Object>> reList=new ArrayList<>();
		try {
			reList=mapper.HomepageTicketInfo(string);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reList;
	}
	public void ConnectionSQL(){
		try {
			session=DBTools.getSession();
			mapper=session.getMapper(TicketMapper.class);
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
		}
	}
	@Override
	public boolean Unsubscribe(Map<String, Object> map) {//��Ʊ
		// TODO Auto-generated method stub
		boolean b=false;
		ConnectionSQL();
		try {
			b=mapper.Unsubscribe(map);
			session.commit();
			if(b){
				int start_no=mapper.SearchStartStation_NO(map);
				int end_no=mapper.SearchEndStation_NO(map);
				int l=end_no-start_no;
				map.put("station_no", "");
				if(map.get("trainName").toString().substring(0, 1).equals("D") && map.get("SeatName").toString().equals("FirstClass")){//����D��һ����
					System.out.println("��ʼ����Ʊ��");
					for(int i=start_no;i<=l+start_no;i++){
						map.replace("station_no", i);
						mapper.UpdateSeat_FirstClass(map);
						session.commit();
					}
				}else if (map.get("trainName").toString().substring(0, 1).equals("D") && map.get("SeatName").toString().equals("SecondClass")) {//����D��������
					System.out.println("��ʼ����Ʊ��");
					for(int i=start_no;i<=l+start_no;i++){
						map.replace("station_no", i);
						mapper.UpdateSeat_SecondClass(map);
						session.commit();
					}
				}else if (map.get("SeatName").toString().equals("NoSeat")) {//��������
					System.out.println("��ʼ����Ʊ��");
					for(int i=start_no;i<=l+start_no;i++){
						map.replace("station_no", i);
						mapper.UpdateSeat_NoSeat(map);
						session.commit();
					}
				}else if(map.get("SeatName").toString().equals("SoftBerth")){
					System.out.println("��ʼ����Ʊ��");
					for(int i=start_no;i<=l+start_no;i++){
						map.replace("station_no", i);
						mapper.UpdateSeat_SoftBerth(map);
						session.commit();
					}
				}else if(map.get("SeatName").toString().equals("HardBerth")){
					System.out.println("��ʼ����Ʊ��");
					for(int i=start_no;i<=l+start_no;i++){
						map.replace("station_no", i);
						mapper.UpdateSeat_HardBerth(map);
						session.commit();
					}
				}else if (map.get("SeatName").toString().equals("HardSeat")){
					System.out.println("��ʼ����Ʊ��");
					for(int i=start_no;i<=l+start_no;i++){
						map.replace("station_no", i);
						mapper.UpdateSeat_HardSeat(map);
						session.commit();
					}
				}else if(map.get("trainName").toString().substring(0, 1).equals("Z") && map.get("SeatName").toString().equals("high_GradeSB")){
					System.out.println("��ʼ����Ʊ��");
					for(int i=start_no;i<=l+start_no;i++){
						map.replace("station_no", i);
						mapper.UpdateSeat_high_GradeSB(map);
						session.commit();
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			session.close();
		}
		
		return b;
	}
	@Override
	public boolean pdgaiqian(Map<String, Object> map) {
		// TODO Auto-generated method stub
		boolean bl=false;
		ConnectionSQL();
		
		try {
			String gaiqian=mapper.pdgaiqian(map).get("gaiqian").toString();
			if(gaiqian.equals("0")){
				System.out.println("���Ը�ǩ");
				bl=true;
			}else {
				bl=false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			session.close();
		}
		return bl;
	}
}
