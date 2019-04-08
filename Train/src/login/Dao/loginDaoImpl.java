package login.Dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import login.Bean.User;
import login.mapping.loginMapper;
import tools.DBTools;

public class loginDaoImpl implements loginDao{
	private SqlSession session;
	private loginMapper mapper;
	

	@Override
	public boolean Register(Map<String, Object> map) {//注册
		return false;
	}
	@Override
	public int CheckuserName(String Name) {	//注册时验证用户是否存在
		int b=0;//0:可以插入，1：存在，2：数据库异常
		ConnectionSQL();
			Map<String, Object> map=new HashMap<>();
			map.put("userName", Name);
			try {
				if(!mapper.CheckuserName(map)){
					b=0;
					System.out.println("用户名:"+Name+"是否可以插入:"+true);
				}
				else{
					b=1;
					System.out.println("用户名："+Name+"是否可以插入:"+false);
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				session.close();
			}finally {
				session.close();
			}
		return b;

	}
	
	@Override
	public int CheckIDCard(String IDCard) {
		int b=0;//0:不存在，1：存在
		ConnectionSQL();
		Map<String, Object> map=new HashMap<>();
		map.put("IDCard", IDCard);
		try {
			if(mapper.CheckIDCard(map)){
				b=1;
				System.out.println("身份证："+IDCard+"是否可以注册"+false);
			}else{
				b=0;
				
				System.out.println("身份证："+IDCard+"是否可以注册"+true);
			}
			/*if(!mapper.CheckIDCard(map)){
				b=0;
				System.out.println("身份证："+IDCard+"是否可以注册"+true);
			}else{
				b=1;
				int i=0;
				i++;
				System.out.println("身份证："+IDCard+"是否可以注册"+false+i);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
		}finally {
			session.close();
		}
		return b;
	}
	
	@Override
	public int InsertUser(Map<String, Object> map) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unused")
		boolean b=false;int i=0;//0：注册成功，1：失败
		ConnectionSQL();
		try {
			b=mapper.InsertUser(map);
			session.commit();
			if(b=true){
				i=0;
			}else{
				i=1;
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return i;
	}
	
	
	public void ConnectionSQL(){
		try {
			session=DBTools.getSession();
			mapper=session.getMapper(loginMapper.class);
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
		}
	}
	@Override
	public Map<String, Object> Check(Map<String, Object> map) {
		Map<String, Object>reMap=new HashMap<>();
		ConnectionSQL();
		try {
			reMap=mapper.CheckUser(map);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return reMap;
	}
}
