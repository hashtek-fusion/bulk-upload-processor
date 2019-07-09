package com.hashtek.web.camel;

import java.util.Calendar;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.hashtek.web.constants.RequestState;
import com.hashtek.web.entity.Request;

@Component
public class RequestOrchestrator implements Processor {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	

	@Override
	public void process(Exchange exchange) throws Exception {
		// TODO Auto-generated method stub
		// Need to invoke validation & submission e-Bonding endpoints for each request in bulk
		Request request = exchange.getIn().getBody(Request.class);	
		//TODO:: Invoke e-Bonding REST end point to validate the individual request			
	    java.util.Random random = new java.util.Random();
	    int IS_VALIDATION_SUCCESS = random.nextInt(2) + 1;
		if(IS_VALIDATION_SUCCESS % 2 == 0) {// TODO:: Validation Succeeds
			//TODO:: Invoke e-Bonding REST end point to submit the individual request
			java.util.Random random1 = new java.util.Random();
		    int IS_SUBMIT_SUCCESS = random1.nextInt(2) + 1;
			if(IS_SUBMIT_SUCCESS % 2 == 0) {//TODO:: Submit Succeeds
				request.setState(RequestState.SUBMITTED.toString());
				request.setOrderNumber("B" + Calendar.getInstance().getTimeInMillis());//TODO:: Set Order number returned from downstream
			}else {
				request.setState(RequestState.SUBMIT_FAILED.toString());
				request.setReasonForFailure("SYSTEM_DOWMN OR API_NOT_REACHABLE");
			}
		}else {//Validation Failed
			request.setState(RequestState.VALIDATION_FAILED.toString());
			request.setReasonForFailure("INVALID_REQUEST");//TODO:: Validation Failure reason should go here
		}
		
		Object bulkRequestId = exchange.getIn().getHeader("bulk_request_id");		
		exchange.getOut().setHeader("bulk_request_id", bulkRequestId);
		exchange.getOut().setBody(request);		
	}

}
