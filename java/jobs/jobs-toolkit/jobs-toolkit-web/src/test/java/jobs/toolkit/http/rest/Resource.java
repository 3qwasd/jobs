package jobs.toolkit.http.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;



public class Resource{
	
	


	@GET
	@Path("test")
	public String datavizResource() throws Exception{		
		return "xxxxxxx";
	}

}
