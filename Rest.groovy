#!/usr/bin/env groovy

@Grab(group="org.restlet.jee", version="2.0.14", module="org.restlet")
import org.restlet.*
import org.restlet.data.*

class RequestHandler extends Restlet {
	def void handle(Request request, Response response){
		if (request.method == Method.GET){
			response.setEntity("server is running", MediaType.TEXT_PLAIN)
		}
		else if (request.method == Method.POST) {
			handlePost(request, response);
		} 
		else {
			response.setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED)
			response.setAllowedMethods([Method.GET, Method.POST] as Set)
		}
	}
	
	def handlePost(Request request, Response response) {
		Form form = new Form(request.entity)
		def test = form.getValues('test')
		response.setEntity("server test $test", MediaType.TEXT_PLAIN)
	}
}

new Server(Protocol.HTTP, 3000, new RequestHandler()).start()
/* curl teszteléshez: curl --data "test=testvalue&param2=value2" http://localhost:3000 */