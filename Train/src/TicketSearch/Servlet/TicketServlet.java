package TicketSearch.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import TicketSearch.Dao.TicketDao;
import TicketSearch.Dao.TicketDaoImpl;
import net.sf.json.JSONArray;

public class TicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public TicketServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String doString=request.getParameter("doString").toString();
		if(doString.equals("HomepageTicketInfo")){
			System.err.println("开始个人页面已购车票查询");
//			Map<String, Object>map=new HashMap<>();
			List<Map<String, Object>> reList=new ArrayList<>();
			String userName=request.getParameter("userName").toString();
			TicketDao ticketDao=new TicketDaoImpl();
			reList=ticketDao.HomepageTicketInfo(userName);
//			JSONObject json= JSONObject.fromObject(map);
			JSONArray json=JSONArray.fromObject(reList);
			Response(json.toString(), response);
		}else if(doString.equals("Unsubscribe")){
			System.err.println("开始退票");
			String userName=request.getParameter("userName").toString();
			String startstation=request.getParameter("startstation").toString();
			String endstation=request.getParameter("endstation").toString();
			String trainName=request.getParameter("trainName").toString();
			String SeatName=request.getParameter("SeatName").toString();
			Map<String, Object> map=new HashMap<>();
			map.put("userName", userName);
			map.put("startstation", startstation);
			map.put("endstation", endstation);
			map.put("trainName", trainName);
			map.put("SeatName", SeatName);
			TicketDao ticketDao=new TicketDaoImpl();
			boolean b=ticketDao.Unsubscribe(map);
//			JSONObject json=JSONObject.fromObject(b);
			Response(b, response);
		}else if(doString.equals("pdgaiqian")){
			System.err.println("开始判断是否可以改签");
			String trainName=request.getParameter("trainName");
			String userName=request.getParameter("userName");
				Map<String, Object> map=new HashMap<>();
				map.put("trainName", trainName);
				map.put("userName", userName);
			TicketDao TK=new TicketDaoImpl();
			boolean bl=TK.pdgaiqian(map);
			Response(bl, response);
		}else if(doString.equals("gaiqian")){
			System.err.println("开始改签");
			String trianName=request.getParameter("trainName");
			String userName=request.getParameter("userName");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
	public void Response(Object string,HttpServletResponse response) {
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
