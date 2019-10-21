package com.invillia.acme.router;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.apache.camel.model.rest.RestBindingMode;

@Component
class OrderRestApi extends RouteBuilder {
	
	@Value("${context.api.version}")
	private String apiVersion;
	
	@Value("${server.context-path}")
	private String contextPath;

    @Override
    public void configure() {

        rest(contextPath.concat("/").concat(apiVersion))
	    	.id("restApi")
			.post("/order")
				.id("create-order")
				.to("direct:create-order")
			.get("/order/status/{status}")
		    	.id("search-order-status")
		    	.param()
		    		.type(RestParamType.path)
		    		.name("status")
		    	.endParam()
		    	.to("direct:search-order-status")
		    .get("/order")
		    	.id("search-order-all")
		    	.to("direct:search-order-all")
		    .get("/order/{id}")
		    	.id("search-order-id")
		    	.param()
		    		.type(RestParamType.path)
		    		.name("id")
	    		.endParam()
		    	.to("direct:search-order-id")	
		    .put("/order")
		    	.id("update-order")
		    	.to("direct:update-order");
    }
}
