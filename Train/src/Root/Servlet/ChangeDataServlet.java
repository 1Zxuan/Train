package Root.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Root.Dao.RootDao;
import Root.Dao.RootDaoImpl;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class ChangeDataServlet
 */
public class ChangeDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeDataServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String trainName=request.getParameter("trainName");
		String station_no=request.getParameter("station_no");
		String start_time=request.getParameter("start_time");
		String arrive_time=request.getParameter("arrive_time");
		Map<String, Object> map=new HashMap<>();
		map.put("trainName", trainName);
		map.put("station_no", station_no);
		map.put("start_time", start_time);
		map.put("arrive_time", arrive_time);
		RootDao rootDao=new RootDaoImpl();
		boolean bl=rootDao.ChangeData(map);
		Response(bl, response);
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
