package Buy.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Buy.Dao.BuyDao;
import Buy.Dao.BuyDaoImpl;
import net.sf.json.JSONObject;

public class BuyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public BuyServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String doString=request.getParameter("doString");
		Map<String, Object>map=new HashMap<>();
		 if(doString.equals("checkticket")){
				System.err.println("检查是否可以购买点击的车票");
				String userName=request.getParameter("userName");
				String trainName=request.getParameter("trainName");
				map.put("userName", userName);
				map.put("trainName", trainName);
				BuyDao BD=new BuyDaoImpl();
				if(BD.CheckTicket(map)){
					Response(true, response);
				}else{
					Response(false, response);
				}
			}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		String doString=request.getParameter("doString").toString(),str=null;
		Map<String, Object>map=new HashMap<>();
		Map<String, Object>reMap=new HashMap<>();
		if(doString.equals("Buyinfo")){//执行购票信息查询
			System.out.println("车票信息查询");
			String userName=request.getParameter("userName").toString();
			String trainName=request.getParameter("trainName").toString();
			String startstation=request.getParameter("startstation").toString();
			String endstation=request.getParameter("endstation").toString();
			map.put("userName", userName);
			map.put("trainName", trainName);
			map.put("startstation", startstation);
			map.put("endstation", endstation);
			BuyDao BD=new BuyDaoImpl();
			if(trainName.startsWith("D")){
				reMap=BD.SearchTicketinfo_D(map);
				Response(inMap(reMap,trainName,startstation,endstation), response);
			}else if(trainName.startsWith("T")){
				reMap=BD.SearchTicketinfo_T(map);
				Response(inMap(reMap,trainName,startstation,endstation), response);
			}else if(trainName.startsWith("Z")){
				reMap=BD.SearchTicketinfo_Z(map);
				Response(inMap(reMap,trainName,startstation,endstation), response);
			}
		}else if(doString.equals("Buy")){
			System.out.println("购票");
			String userName=request.getParameter("userName").toString();
			String trainName=request.getParameter("trainName").toString();
			String startstation=request.getParameter("startstation").toString();
			String endstation=request.getParameter("endstation").toString();
			String tourist=request.getParameter("tourist").toString();
			String IDCard=request.getParameter("IDCard").toString();
			String phone=request.getParameter("phone").toString();
			String arrive_time=request.getParameter("arrive_time").toString();
			String start_time=request.getParameter("start_time").toString();
			String station_no=request.getParameter("station_no").toString();
			String SeatName=request.getParameter("SeatName").toString();
			map.put("SeatName", SeatName);
			map.put("userName", userName);
			map.put("trainName", trainName);
			map.put("startstation", startstation);
			map.put("endstation", endstation);
			map.put("tourist", tourist);
			map.put("IDCard", IDCard);
			map.put("phone", phone);
			map.put("arrive_time", arrive_time);
			map.put("start_time", start_time);
			map.put("station_no", station_no);
			BuyDao BD=new BuyDaoImpl();
			if(BD.BuyTicket(map)){
				Response(true, response);
			}else{
				Response(false, response);
			}
		}else if(doString.equals("Buy2")){
			System.out.println("改签");
			String userName=request.getParameter("userName").toString();
			String trainName=request.getParameter("trainName").toString();
			String startstation=request.getParameter("startstation").toString();
			String endstation=request.getParameter("endstation").toString();
			String tourist=request.getParameter("tourist").toString();
			String IDCard=request.getParameter("IDCard").toString();
			String phone=request.getParameter("phone").toString();
			String arrive_time=request.getParameter("arrive_time").toString();
			String start_time=request.getParameter("start_time").toString();
			String station_no=request.getParameter("station_no").toString();
			String SeatName=request.getParameter("SeatName").toString();
			String gaiqian=request.getParameter("gaiqian");
			String Befor_trianName=request.getParameter("Befor_trianName");
			map.put("SeatName", SeatName);
			map.put("userName", userName);
			map.put("trainName", trainName);
			map.put("startstation", startstation);
			map.put("endstation", endstation);
			map.put("tourist", tourist);
			map.put("IDCard", IDCard);
			map.put("phone", phone);
			map.put("arrive_time", arrive_time);
			map.put("start_time", start_time);
			map.put("station_no", station_no);
			map.put("gaiqian",gaiqian);
			map.put("Befor_trianName", Befor_trianName);
			BuyDao BD=new BuyDaoImpl();
			if(BD.GQTicket(map)){
				Response(true, response);
			}else{
				Response(false, response);
			}
		}
	}
	
	public String inMap(Map<String, Object> map,String a,String b,String c) {
		Map<String, Object> map2=new HashMap<>();
			map2.putAll(map);
			map2.put("trainName", a);
			map2.put("startstation", b);
			map2.put("endstation", c);
			JSONObject jsonObject=new JSONObject();
			jsonObject=JSONObject.fromObject(map2);
		return jsonObject.toString(); 
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
