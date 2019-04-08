package Root.Dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;

import Root.Bean.TrainStation;
import Root.mapping.RootMapper;
import tools.DBTools;


public class RootDaoImpl implements RootDao{
	private SqlSession session;
	private RootMapper mapper;
	
	public void ConnectionSQL(){
		try {
			session=DBTools.getSession();
			mapper=session.getMapper(RootMapper.class);
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
		}
	}
	
	@Override
	public List<Map<String, Object>> trainName() {
		List<Map<String, Object>> reList=new ArrayList<Map<String,Object>>();//返回的结果List
		ConnectionSQL();
		try {
			reList=mapper.trainNameList();
//			for(int i=0;i<reList.size();i++){
//				Map<String, Object> map2=(Map) reList.get(i);
//				System.out.println(map2.get("TrainName"));
//			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reList;
	}
	
	@Override
	public List<TrainStation> trainNameInfo(String string) {
		List<TrainStation> reList=new ArrayList<>();
		ConnectionSQL();
		try {
			reList=mapper.trainNameInfo(string);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return reList;
	}

	@Override
	public Boolean ChangeData(Map<String, Object> map) {
		// TODO Auto-generated method stub
		boolean bl=false;
		ConnectionSQL();
		try {
			bl=mapper.ChangeData(map);
			session.commit();
			System.out.println(bl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			session.close();
		}
		return bl;
	}
}
