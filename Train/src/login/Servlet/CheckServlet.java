package login.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.xpath.internal.axes.HasPositionalPredChecker;

import login.Dao.loginDao;
import login.Dao.loginDaoImpl;
import net.sf.json.JSONObject;



public class CheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public CheckServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String doString=request.getParameter("doString").toString();
	System.out.println(doString);
	String retString;
	String str = null;
	if(doString.equals("CheckuserName")){
		String name=request.getParameter("name").toString();
		if(CheckuserName(name)==0){	//检测用户名是否存在. True:可以注册，False:用户名已存在
			retString="True";
			JSONObject json=new JSONObject();
			json.put("msg", retString);
			str=json.toString();
		}else if(CheckuserName(name)==1){
			retString="False";
			JSONObject json=new JSONObject();
			json.put("msg", retString);
			str=json.toString();
		}/*else if(CheckuserName(name)==2){
			System.out.println("er");
			retString="数据库操作异常";
			JSONObject json=new JSONObject();
			json.put("msg", retString);
			str=json.toString();
		}*/
	}
	if(doString.equals("CheckIDCard")){
		System.out.println("检测身份证是否存在");
		String IDCard=request.getParameter("IDCard").toString();
		int CheckIDCard=CheckIDCard(IDCard);
		if(CheckIDCard==0){//检测身份证是否存在.0：不存在,1:存在
			retString="True";
			JSONObject json=new JSONObject();
			json.put("msg", retString);
			str=json.toString();
		}
		if(CheckIDCard==1){
			retString="False";
			JSONObject json=new JSONObject();
			json.put("msg", retString);
			str=json.toString();
		}
	}
	 if(doString.equals("Insert")){
		System.out.println("执行插入");
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		String IDCard=request.getParameter("IDCard");
		String Name=request.getParameter("Name");
		String Email=request.getParameter("Email");
		String PhoneNum=request.getParameter("PhoneNum");
		String Type=request.getParameter("Type");
		System.out.println("用户名"+userName+"密码"+password+"身份证"+IDCard+"姓名"+Name+"电子邮箱"+Email+"号码"+PhoneNum+"类型"+Type);
		Map<String, Object> map= new HashMap<>();
		Map<String, Object> remap= new HashMap<>();
		map.put("userName", userName);
		map.put("password", password);
		map.put("IDCard", IDCard);
		map.put("Name", Name);
		map.put("Email", Email);
		map.put("PhoneNum", PhoneNum);
		map.put("Type", Type);
		if(InsertUser(map)){
			retString="True";
			JSONObject json=new JSONObject();
			str=json.put("msg", retString).toString();
		}else {
			retString="False";
			JSONObject json=new JSONObject();
			str=json.put("msg",retString).toString();
		}
	} if(doString.equals("Login")){
		System.out.println("登录");
		String userName=request.getParameter("user").toString();//用户名
		String password=request.getParameter("password").toString();//md5加密的密码
		Map<String, Object> map=new HashMap<>();
		map.put("userName", userName);
		map.put("password", password);
		loginDao loginDao=new loginDaoImpl();
		Map<String, Object> remap=new HashMap<>();
		remap=loginDao.Check(map);
		if(remap!=null){
			JSONObject json=JSONObject.fromObject(remap);
			str=json.toString();
		}else{
			JSONObject json=JSONObject.fromObject(remap);
			str=json.toString();
		}
	}
	response.setContentType("application/json;charset=utf-8");
	response.setCharacterEncoding("utf-8");
	PrintWriter out =response.getWriter();
	out.println(str);
	out.flush();
	out.close();

	}

	public boolean InsertUser(Map<String, Object> map) {
		// TODO Auto-generated method stub
		boolean b=false;
		loginDao loginDao=new loginDaoImpl();
		if(loginDao.InsertUser(map)==0)
			b=true;
		else
			b=false;
		return b;
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	public int CheckuserName(String Name) {
		int b=0;
		loginDao loginDao=new loginDaoImpl();
		b=loginDao.CheckuserName(Name);
		return b;
	}
	public int CheckIDCard (String IDCard) {
		int b=0;
		loginDao loginDao =new loginDaoImpl();
		b=loginDao.CheckIDCard(IDCard);
		return b;
	}
}
