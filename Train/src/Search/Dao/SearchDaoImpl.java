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
		List<Map<String, Object>> reList=new ArrayList<Map<String,Object>>();//返回的结果List
		
		List<Map<String, Object>> ByStartStation=new ArrayList<>();//定义经过起始站的车次List
		List<Map<String, Object>> ByEndStation=new ArrayList<>();//定义经过终点站的车次List
		List<Map<String, Object>> ByStartEndStation=new ArrayList<>();//定义经过起始站&&终点站的车次List
		
		List<TrainList> reList2= new ArrayList<>();//返回
		Map<String, Object> SeachFinally =new HashMap<>();
		SeachFinally.put("StartStation", map.get("StartStation"));
		ConnectionSQL();
		try {
			reList=mapper.SearchStartStation(map);
			for(int i=0;i<reList.size();i++){
				Map<String, Object> map2=(Map) reList.get(i);
				ByStartStation.add(map2);
				System.out.print(map2.get("key")+"第"+map2.get("value")+"	");//获取经过起始站的车次
			}
			reList.clear();
			System.out.println();
			System.out.println("经过终点站车次");
			reList=mapper.SearchEndStation(map);
			for(int i=0;i<reList.size();i++){
				Map<String, Object> map2=(Map)reList.get(i);
				ByEndStation.add(map2);
				System.out.print(map2.get("key")+"第"+map2.get("value")+"	");//获取经过终点站的车次
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*if(ByStartStation.size()>ByEndStation.size()){//如果经过起始站的车次比经过终点站的车次多则交换,使得双重循环次数减少
			List<Map<String, Object>> cg=new ArrayList<>();
			cg=ByEndStation;
			ByEndStation=ByStartStation;
			ByStartStation=cg;
		}*/
		
		System.out.println();
		for(int i=0;i<ByStartStation.size();i++){
			String ByST=ByStartStation.get(i).get("key").toString();//获取第i个经过起始站的车次
			int ByStartnum=Integer.parseInt(ByStartStation.get(i).get("value").toString());//获取第i个经过起始站的车次的站序(起始车站的站序<终点车站的站序)
			for(int j=0;j<ByEndStation.size();j++){
				String ByET=ByEndStation.get(j).get("key").toString();//获取第j个经过站点站的车次
				int ByEndnum=Integer.parseInt(ByEndStation.get(j).get("value").toString());//获取第j个经过终点站的车次的站序(起始车站的站序<终点车站的站序)
				if(ByST.equals(ByET) && ByStartnum<ByEndnum){	//得到最终车次并加入ByStartEndStation
					ByStartEndStation.add(ByStartStation.get(i));
					break;
				}
			}
		}
		
		
		
		for(int i=0;i<ByStartEndStation.size();i++){				//打印输出最终得到结果
			Map<String, Object> map2=(Map)ByStartEndStation.get(i);
			System.out.println(map2.entrySet());
			SeachFinally.put("TrainName", map2.get("key"));//添加车次去查找
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
				SeachFinally.remove("TrainName");//查到后删除已近查出来的车次
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
