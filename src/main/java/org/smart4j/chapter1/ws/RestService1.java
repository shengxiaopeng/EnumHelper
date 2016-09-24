package org.smart4j.chapter1.ws;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * ${DESCRIPTION}
 *
 * @author sxp
 * @create 2016/9/17.
 */

@WebService
@Path("/service")
public class RestService1 {

    @GET
    @Path("/hello")
    public String hello(){  //http://localhost:8080/services/service/hello

        return "hello ws";

    }


}
