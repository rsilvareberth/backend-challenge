package com.invillia.acme.router;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.apache.camel.model.rest.RestBindingMode;

@Component
class StoreRestApi extends RouteBuilder {
	
	@Value("${context.api.version}")
	private String apiVersion;
	
	@Value("${server.context-path}")
	private String contextPath;

    @Override
    public void configure() {

        rest(contextPath.concat("/").concat(apiVersion))
	    	.id("restApi")
			.post("/store")
				.id("create-store")
				.to("direct:create-store")
			.get("/store/name/{name}")
		    	.id("search-store-name")
		    	.param()
		    		.type(RestParamType.path)
		    		.name("name")
		    	.endParam()
		    	.to("direct:search-store-name")
		    .get("/store")
		    	.id("search-store-all")
		    	.to("direct:search-store-all")
		    .get("/store/{id}")
		    	.id("search-store-id")
		    	.param()
		    		.type(RestParamType.path)
		    		.name("id")
	    		.endParam()
		    	.to("direct:search-store-id")	
		    .get("/store/zip/{zip}/city/{city}")
		    	.id("search-store-address")
		    	.param()
		    		.type(RestParamType.path)
		    		.name("zip")
		    	.endParam()
		    	.param()
		    		.type(RestParamType.path)
		    		.name("city")
		    	.endParam()
		    	.to("direct:search-store-address")
		    .put("/store")
		    	.id("update-store")
		    	.to("direct:update-store");
    }
}
