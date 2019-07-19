package jobs.toolkit.http.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("test")
public class RestTest {
	
	@GET
	@Path("helloworld")
	public String helloworld(){
		return "Hello World!";
	}
}
