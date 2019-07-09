package com.hashtek.web.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.hashtek.web.entity.BulkRequest;

@Component
public class RequestProcessor implements Processor {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void process(Exchange exchange) throws Exception {		
		BulkRequest bulkRequest = exchange.getIn().getBody(BulkRequest.class);		
		exchange.getOut().setBody(bulkRequest.getRequests());	
		exchange.getOut().setHeader("bulk_request_id", bulkRequest.getBulkRequestId());		
	}

}
