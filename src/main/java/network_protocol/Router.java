package network_protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class Router {
	
	private final Logger LOGGER = Logger.getLogger(Router.class);
	private Integer id;
	private String name;
	private ArrayList<Double> lsp;
	private ArrayList<Router> routerList = new ArrayList<Router>();
	
	private Map<String,Object> routingTable = new HashMap<String, Object>();
	
	private Map<String, Object> shortestPath = new HashMap<String, Object>(); 
	
	
	
	public void addLSP(ArrayList<Double> lsp) {
		this.lsp = lsp;
		routerList.add(this);
	}
	
	public void addRouter(Router router) {
		routerList.add(router);
	}
	
	public void floodLSP(){
		int i =1;
		for(Double dis : lsp) {
			if (dis != 0 && dis != -1) {
				if(!SystemContext.ROUTERS.get(i).isRouterExist(this)) {
					LOGGER.debug("flood router["+this.getName()+"] to ["+SystemContext.ROUTERS.get(i).getName()+"]" );
					SystemContext.ROUTERS.get(i).addRouter(this);
					SystemContext.ROUTERS.get(i).floodLSP();
				}
			}
			i++;
		}
	}
	
	public void calculateRouteTable() {
		
	}
	
	
	
	public boolean isRouterExist(Router router) {
		return routerList.contains(router);
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
