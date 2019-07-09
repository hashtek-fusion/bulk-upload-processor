package com.hashtek.web.camel;

import java.util.ArrayList;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hashtek.web.entity.Request;


public class BulkRequestAggregationStrategy implements AggregationStrategy {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());	

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		 Message newIn = newExchange.getIn();		 
		 String bulkRequestId = newIn.getHeader("bulk_request_id").toString();		
         Object newBody = newIn.getBody(Request.class);
         ArrayList<Request> list = null;
         if (oldExchange == null) {
                 list = new ArrayList<Request>();
                 list.add((Request)newBody);                
                 newIn.setBody(list); 
                 newIn.setHeader("bulk_request_id", bulkRequestId);		
                 return newExchange;
         } else {    	 		
                 Message in = oldExchange.getIn();
                 list = in.getBody(ArrayList.class);                
                 list.add((Request)newBody);
                 in.setHeader("bulk_request_id", bulkRequestId);			
                 return oldExchange;
         }
	}

}
