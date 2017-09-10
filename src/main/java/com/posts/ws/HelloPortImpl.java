package com.posts.ws;

/**
 * Created by mumarm45 on 09/09/2017.
 */

@javax.jws.WebService(serviceName = "HelloService", portName = "HelloPort",
        targetNamespace = "http://service.ws.sample/",
        endpointInterface = "com.posts.ws.Hello")
public class HelloPortImpl implements Hello{


    public java.lang.String sayHello(java.lang.String myname) {

        try {
            return "Hello, Welcome to CXF Spring boot " + myname + "!!!";

        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
