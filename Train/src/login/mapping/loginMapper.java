package login.mapping;

import java.util.Map;

import login.Bean.User;

public interface loginMapper {
	public Map<String, Object> CheckUser(Map<String, Object> map) throws Exception;	//登录
	
	public void Register(Map<String, Object> map) throws Exception;		//注册
	
	public boolean CheckuserName(Map<String, Object> map) throws Exception;			//注册时验证用户名是否存在
	
	public boolean CheckIDCard(Map<String, Object> map) throws Exception;			//注册时验证身份证是否存在
	
	public boolean InsertUser(Map<String, Object> map) throws Exception;			//注册用户
	
}
