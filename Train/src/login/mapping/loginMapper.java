package login.mapping;

import java.util.Map;

import login.Bean.User;

public interface loginMapper {
	public Map<String, Object> CheckUser(Map<String, Object> map) throws Exception;	//��¼
	
	public void Register(Map<String, Object> map) throws Exception;		//ע��
	
	public boolean CheckuserName(Map<String, Object> map) throws Exception;			//ע��ʱ��֤�û����Ƿ����
	
	public boolean CheckIDCard(Map<String, Object> map) throws Exception;			//ע��ʱ��֤���֤�Ƿ����
	
	public boolean InsertUser(Map<String, Object> map) throws Exception;			//ע���û�
	
}
