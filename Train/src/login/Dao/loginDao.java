package login.Dao;

import java.util.Map;

public interface loginDao {
	Map<String, Object> Check(Map<String, Object> map);				//登录时验证
	
	boolean Register(Map<String, Object> map);			//注册信息
	
	int CheckuserName(String Name);					//注册时验证用户名是否已存在
	
	int CheckIDCard(String IDCard);					//注册时验证身份证是否已存在
	
	int InsertUser(Map<String,Object> map);		//注册
}
