package com.invillia.acme.router;


import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import com.invillia.acme.controller.OrderController;
import com.invillia.acme.model.Order;

@Component
public class OrderRouter extends RouteBuilder {
	
	
	@Override
	public void configure() throws Exception {

	// CREATE order
	
		// Create order route POST to JMS
	from("direct:create-order").routeId("direct:create-order")
		//Async processing
		.wireTap("direct:queue-create-order")
		.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
        .setBody(constant(""));
		
		// Create order route JMS
    from("direct:queue-create-order").routeId("direct:queue-create-order")
	    .to("{{activemq.queue.create.order}}")
	    .end();
    
    	// Create order route JMS to DB
	from("{{activemq.queue.create.order}}").routeId("subscriber_queue.create.order")
		.unmarshal().json(JsonLibrary.Jackson, Order.class)
		.to("jpa:com.invillia.acme.model.Order");
	
//	// UPDATE STORE
		// Update order route POST to JMS
	from("direct:update-order").routeId("direct:update-order")
		.wireTap("direct:queue-update-order")
		.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
	    .setBody(constant(""));
	
		// update order route JMS
    from("direct:queue-update-order").routeId("direct:queue-update-order")
	    .to("{{activemq.queue.update.order}}")
	    .end();
    	
    	// update order route JMS to DB
 	from("{{activemq.queue.update.order}}").routeId("subscriber_queue.update.order")
 		.unmarshal().json(JsonLibrary.Jackson, Order.class)
 		.bean("orderController", "updateOrder");
	
	// RETRIEVE STORE
 	from("direct:search-order-all").routeId("direct:search-order-all")
	 	.bean(OrderController.class, "findAll()")
	 	.marshal().json(JsonLibrary.Jackson);
 	
 	from("direct:search-order-id").routeId("direct:search-order-id")
 		.bean(OrderController.class, "findById(${header.id})")
 		.marshal().json(JsonLibrary.Jackson);

	from("direct:search-order-status").routeId("direct:search-order-status")
		.streamCaching()
		.bean(OrderController.class, "findByStatus(${header.status})")
		.marshal().json(JsonLibrary.Jackson);
	}

}

