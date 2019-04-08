package Search.Dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;

import Search.Bean.TrainList;
import Search.mapping.SearchMapper;
import tools.DBTools;

public class SearchDaoImpl implements SearchDao {

	private SqlSession session;
	private SearchMapper mapper;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<TrainList> Search(Map<String,Object> map) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> reList=new ArrayList<Map<String,Object>>();//���صĽ��List
		
		List<Map<String, Object>> ByStartStation=new ArrayList<>();//���徭����ʼվ�ĳ���List
		List<Map<String, Object>> ByEndStation=new ArrayList<>();//���徭���յ�վ�ĳ���List
		List<Map<String, Object>> ByStartEndStation=new ArrayList<>();//���徭����ʼվ&&�յ�վ�ĳ���List
		
		List<TrainList> reList2= new ArrayList<>();//����
		Map<String, Object> SeachFinally =new HashMap<>();
		SeachFinally.put("StartStation", map.get("StartStation"));
		ConnectionSQL();
		try {
			reList=mapper.SearchStartStation(map);
			for(int i=0;i<reList.size();i++){
				Map<String, Object> map2=(Map) reList.get(i);
				ByStartStation.add(map2);
				System.out.print(map2.get("key")+"��"+map2.get("value")+"	");//��ȡ������ʼվ�ĳ���
			}
			reList.clear();
			System.out.println();
			System.out.println("�����յ�վ����");
			reList=mapper.SearchEndStation(map);
			for(int i=0;i<reList.size();i++){
				Map<String, Object> map2=(Map)reList.get(i);
				ByEndStation.add(map2);
				System.out.print(map2.get("key")+"��"+map2.get("value")+"	");//��ȡ�����յ�վ�ĳ���
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*if(ByStartStation.size()>ByEndStation.size()){//���������ʼվ�ĳ��αȾ����յ�վ�ĳ��ζ��򽻻�,ʹ��˫��ѭ����������
			List<Map<String, Object>> cg=new ArrayList<>();
			cg=ByEndStation;
			ByEndStation=ByStartStation;
			ByStartStation=cg;
		}*/
		
		System.out.println();
		for(int i=0;i<ByStartStation.size();i++){
			String ByST=ByStartStation.get(i).get("key").toString();//��ȡ��i��������ʼվ�ĳ���
			int ByStartnum=Integer.parseInt(ByStartStation.get(i).get("value").toString());//��ȡ��i��������ʼվ�ĳ��ε�վ��(��ʼ��վ��վ��<�յ㳵վ��վ��)
			for(int j=0;j<ByEndStation.size();j++){
				String ByET=ByEndStation.get(j).get("key").toString();//��ȡ��j������վ��վ�ĳ���
				int ByEndnum=Integer.parseInt(ByEndStation.get(j).get("value").toString());//��ȡ��j�������յ�վ�ĳ��ε�վ��(��ʼ��վ��վ��<�յ㳵վ��վ��)
				if(ByST.equals(ByET) && ByStartnum<ByEndnum){	//�õ����ճ��β�����ByStartEndStation
					ByStartEndStation.add(ByStartStation.get(i));
					break;
				}
			}
		}
		
		
		
		for(int i=0;i<ByStartEndStation.size();i++){				//��ӡ������յõ����
			Map<String, Object> map2=(Map)ByStartEndStation.get(i);
			System.out.println(map2.entrySet());
			SeachFinally.put("TrainName", map2.get("key"));//��ӳ���ȥ����
			{
				
				try {
					reList2.addAll(mapper.reList(SeachFinally));
//					reList2= mapper.reList(SeachFinally);
//					for (Iterator iterator = reList2.iterator(); iterator.hasNext();) {
//						TrainList trainList = (TrainList) iterator.next();
//						System.out.println(trainList.toString());
//					}
					System.out.println(reList2.get(0));
				} catch (Exception e) {
					e.printStackTrace();
					session.close();
				}
				SeachFinally.remove("TrainName");//�鵽��ɾ���ѽ�������ĳ���
			}
		}
		session.close();
		return reList2;
	}

	
	public void ConnectionSQL(){
		try {
			session=DBTools.getSession();
			mapper=session.getMapper(SearchMapper.class);
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
		}
	}


	@Override
	public List<Map<String, Object>> GetStartStationName() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> reList=new ArrayList<>();
		ConnectionSQL();
		try {
			reList=mapper.GetStartStationName();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			session.close();
		}
		return reList;
	}


	@Override
	public List<Map<String, Object>> GetEndStationName() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> reList=new ArrayList<>();
		ConnectionSQL();
		try {
			reList=mapper.GetEndStationName();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			session.close();
		}
		return reList;
	}
	
}
