package TicketSearch.Dao;

import java.util.List;
import java.util.Map;

public interface TicketDao {
	List<Map<String, Object>> HomepageTicketInfo (String string);
	
	boolean Unsubscribe(Map<String, Object> map);
	
	boolean pdgaiqian(Map<String, Object> map);
}
