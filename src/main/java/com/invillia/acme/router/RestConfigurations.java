package com.invillia.acme.router;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class RestConfigurations  extends RouteBuilder {
	
	@Value("${context.api.version}")
	private String apiVersion;
	
	@Value("${server.context-path}")
	private String contextPath;

	@Override
	public void configure() throws Exception {
	    restConfiguration()
		.component("servlet")
		.dataFormatProperty("prettyPrint", "true")
	    .contextPath(contextPath.concat("/").concat(apiVersion))
	    .apiContextPath("/api-doc")
	        .apiProperty("api.title", "ACME REST API")
	        .apiProperty("api.version", apiVersion)
	        .apiProperty("cors", "true")
	        .apiContextRouteId("swagger")
	        .port("{{server.port}}")
	    	.bindingMode(RestBindingMode.off);
		
	}

}
