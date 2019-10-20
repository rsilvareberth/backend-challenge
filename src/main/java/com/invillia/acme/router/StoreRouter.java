package com.invillia.acme.router;


import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import com.invillia.acme.controller.StoreController;
import com.invillia.acme.model.Store;

@Component
public class StoreRouter extends RouteBuilder {
	
	
	@Override
	public void configure() throws Exception {

	// CREATE STORE
	
		// Create store route POST to JMS
	from("direct:create-store").routeId("direct:create-store")
		//Async processing
		.wireTap("direct:queue-create-store")
		.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
        .setBody(constant(""));
		
		// Create store route JMS
    from("direct:queue-create-store").routeId("direct:queue-create-store")
	    .to("{{activemq.queue.create.store}}")
	    //.log(LoggingLevel.INFO, log, "Message sent to the other queue: ${body}")
	    .end();
    
    	// Create store route JMS to DB
	from("{{activemq.queue.create.store}}").routeId("subscriber_queue.create.store")
		.unmarshal().json(JsonLibrary.Jackson, Store.class)
		.to("jpa:com.invillia.acme.model.Store");
	
	// UPDATE STORE
		// Update store route POST to JMS
	from("direct:update-store").routeId("direct:update-store")
		.wireTap("direct:queue-update-store")
		.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
	    .setBody(constant(""));
	
		// update store route JMS
    from("direct:queue-update-store").routeId("direct:queue-update-store")
	    .to("{{activemq.queue.update.store}}")
	    .end();
    	
    	// update store route JMS to DB
 	from("{{activemq.queue.update.store}}").routeId("subscriber_queue.update.store")
 		.unmarshal().json(JsonLibrary.Jackson, Store.class)
 		.bean("storeController", "updateStore");
 		//.to("jpa:com.invillia.acme.model.Store?usePersist=false&flushOnSend=true&joinTransaction=true");
	
	// RETRIEVE STORE
 	from("direct:search-store-all").routeId("direct:search-store-all")
	 	.bean(StoreController.class, "findAll()")
	 	.marshal().json(JsonLibrary.Jackson);
 	
 	from("direct:search-store-id").routeId("direct:search-store-id")
 		.bean(StoreController.class, "findById(${header.id})")
 		.marshal().json(JsonLibrary.Jackson);

	from("direct:search-store-name").routeId("direct:search-store-name")
		.streamCaching()
		.log(LoggingLevel.INFO,log,"param: ${header.name}")
		.bean(StoreController.class, "findByName(${header.name})")
		.marshal().json(JsonLibrary.Jackson);
	
	from("direct:search-store-address").routeId("direct:search-store-address")
		.streamCaching()
		.bean(StoreController.class, "findByZipAndCity(${header.zip},${header.city})")
		.marshal().json(JsonLibrary.Jackson);
	
	}

}

