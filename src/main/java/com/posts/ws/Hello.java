package com.posts.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * Created by mumarm45 on 09/09/2017.
 */
@WebService( name = "Hello")
@SOAPBinding(style= SOAPBinding.Style.RPC, use= SOAPBinding.Use.LITERAL)
public interface Hello {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "sayHello",
            targetNamespace = "http://service.ws.sample/",
            className = "java.lang.String")
    @WebMethod(operationName = "sayHello")
    @ResponseWrapper(localName = "sayHelloResponse",
            targetNamespace = "http://service.ws.sample/",
            className = "java.lang.String")
    String sayHello(@WebParam(name = "myname", targetNamespace = "", mode= WebParam.Mode.IN) String myname);
}
