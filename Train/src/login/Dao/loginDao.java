package login.Dao;

import java.util.Map;

public interface loginDao {
	Map<String, Object> Check(Map<String, Object> map);				//��¼ʱ��֤
	
	boolean Register(Map<String, Object> map);			//ע����Ϣ
	
	int CheckuserName(String Name);					//ע��ʱ��֤�û����Ƿ��Ѵ���
	
	int CheckIDCard(String IDCard);					//ע��ʱ��֤���֤�Ƿ��Ѵ���
	
	int InsertUser(Map<String,Object> map);		//ע��
}
