package com.posts.Config;

/**
 * Created by mumarm45 on 09/09/2017.
 */
import javax.xml.ws.Endpoint;

import com.posts.ws.HelloPortImpl;
import javafx.application.Application;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class WebServiceConfig {

    @Autowired
    private Bus bus;

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, new HelloPortImpl());
        endpoint.publish("/Hello");
        return endpoint;
    }
    @Bean
    public ServletRegistrationBean cxf() {
        return new ServletRegistrationBean(new CXFServlet(), "/soap/*");
    }

}