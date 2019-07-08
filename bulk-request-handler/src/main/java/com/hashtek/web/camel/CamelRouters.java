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
	
	@PostConstruct
	public void init() {
		log.info("Camel Routes loaded");
	}

	@Override
	public void configure() throws Exception {
		from("direct:OrchestrateRequest")
			.log("camel route processing started for bulk...")			
			.split(body())
			.parallelProcessing()			
			.log("Camel Processor invoked")
			.process(bulkRequestProcessor)			
			.aggregate(constant(true), requestAggregationStrategy())	
			.completionTimeout(6000L)
			.log(body().toString())
			.log("Bulk request processing completed")
			.end();		
	}
	
	@Bean
	public AggregationStrategy requestAggregationStrategy() {
		return new BulkRequestAggregationStrategy();
	}

}
