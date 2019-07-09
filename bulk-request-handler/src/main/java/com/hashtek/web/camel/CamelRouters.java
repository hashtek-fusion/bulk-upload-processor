package com.hashtek.web.camel;

import javax.annotation.PostConstruct;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelRouters extends RouteBuilder {
	
	@Autowired
	RequestProcessor bulkRequestProcessor;
	
	@Autowired
	RequestOrchestrator requestOrchestrator;
	
	@Autowired
	BulkRequestStatus bulkRequestStatusProcessor;
	
	@PostConstruct
	public void init() {
		log.info("Camel Routes loaded");
	}

	@Override
	public void configure() throws Exception {
		from("direct:OrchestrateRequest")
			.log("camel route processing started for bulk...")	
			.process(bulkRequestProcessor)
			.split(body())
			.parallelProcessing()			
			.log("Camel Processor Request Orchestration Started")			
			.process(requestOrchestrator)
			.log("Camel Processor Request Orchestration Completed")			
			.aggregate(constant(true), requestAggregationStrategy())	
			.completionTimeout(3000L)			
			.log("Bulk request processing completed")
			.to("direct:BulkRequestStatus")
			.end();	
		
		from("direct:BulkRequestStatus")
			.log("Status update process started")
			.process(bulkRequestStatusProcessor)
			.log("Bulk Request Status update completed")
			.end();
			
			
	}
	
	@Bean
	public AggregationStrategy requestAggregationStrategy() {
		return new BulkRequestAggregationStrategy();
	}

}
