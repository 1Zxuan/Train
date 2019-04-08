package Root.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Root.Bean.TrainStation;
import Root.Dao.RootDao;
import Root.Dao.RootDaoImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class RootServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RootServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String doString=request.getParameter("doString");
		RootDao rootDao=new RootDaoImpl();
		if(doString.equals("trainName")){
			List<Map<String, Object>> list=new ArrayList<>();
			list=rootDao.trainName();
			JSONArray jsonObject=JSONArray.fromObject(list);
			Response(jsonObject, response);
		}else if(doString.equals("SearchTrainInfo")){
			String trianName=request.getParameter("trainName");
			List<TrainStation> reList=new ArrayList<>();
			reList=rootDao.trainNameInfo(trianName);
			JSONArray jsonObject=JSONArray.fromObject(reList);
			Response(jsonObject, response);
		}
		//Response("{}", response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
	
	
	public void Response(Object object,HttpServletResponse response) {
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		try {
			PrintWriter out=response.getWriter();
			out.println(object);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
