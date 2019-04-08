package Search.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Search.Bean.TrainList;
import Search.Dao.SearchDao;
import Search.Dao.SearchDaoImpl;
import jdk.nashorn.api.scripting.JSObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.processors.JsonBeanProcessor;

public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String doString=request.getParameter("doString").toString(),str = null;
		if(doString.equals("Search")){
			System.out.println("发起查询车票");
			List<TrainList> reList=new ArrayList<>();
			String StartStation=request.getParameter("StartStation");
			System.out.println(StartStation);
			String EndStation=request.getParameter("EndStation");
			System.out.println(EndStation);
			String StartDate=request.getParameter("StartDate");
			Map<String, Object> map= new HashMap<>();
			map.put("StartStation", StartStation);
			map.put("EndStation", EndStation);
			map.put("StartDate", StartDate);
			SearchDao SD=new SearchDaoImpl();
			reList=SD.Search(map);
			new JSONArray();
			JSONArray jsonObject=JSONArray.fromObject(reList);
			for(int i=0;i<jsonObject.length();i++){								//添加到达站名称
				jsonObject.getJSONObject(i).put("endStation", EndStation);
			}
			str=jsonObject.toString();
			System.out.println(jsonObject);
			Response(str,response);
		}else if(doString.equals("GetStartStation")) {
			List<Map<String, Object>> reList=new ArrayList<>();
			SearchDao SD=new SearchDaoImpl();
			reList=SD.GetStartStationName();
			JSONArray jsonArray=new JSONArray().fromObject(reList);
			Response(jsonArray.toString(), response);
		}else if(doString.equals("EndStation")){
//			String startstation=request.getParameter("startstation");
			List<Map<String, Object>> reList=new ArrayList<>();
			SearchDao SD=new SearchDaoImpl();
			reList=SD.GetEndStationName();
			JSONArray jsonArray=new JSONArray().fromObject(reList);
			Response(jsonArray.toString(), response);
		}
		
//		Response(str, response);
		/*PrintWriter out= response.getWriter();
		out.println(str);
		out.flush();
		out.close();*/
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public void Response(String string,HttpServletResponse response) {
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		try {
			PrintWriter out=response.getWriter();
			out.println(string);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
