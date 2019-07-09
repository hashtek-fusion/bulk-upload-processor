package com.hashtek.web.camel;

import java.util.List;
import java.util.Optional;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hashtek.web.constants.BulkRequestState;
import com.hashtek.web.constants.RequestState;
import com.hashtek.web.entity.BulkRequest;
import com.hashtek.web.entity.Request;
import com.hashtek.web.repository.BulkRequestRepository;

@Component
public class BulkRequestStatus implements Processor {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BulkRequestRepository repository; 

	@Override
	public void process(Exchange exchange) throws Exception {		
		 String bulkRequestId = exchange.getIn().getHeader("bulk_request_id").toString();	
		 List<Request> requests = exchange.getIn().getBody(List.class);
		 BulkRequest bulkRequest = updateBulkRequestStatus(bulkRequestId, requests);
		 exchange.getOut().setBody(bulkRequest);
	}
	
	private BulkRequest updateBulkRequestStatus(String bulkRequestId, List<Request> requests) {
		Optional<BulkRequest> bulkRequest = this.repository.findById(bulkRequestId);
		if(bulkRequest.isPresent()) {
			bulkRequest.get().setRequests(requests);
			if(verifyRequestSubmitStatus(requests))
				bulkRequest.get().setState(BulkRequestState.SUBMITTED.toString());
			else
				bulkRequest.get().setState(BulkRequestState.PARTIALLY_SUBMITTED.toString());
		}
		logger.info("Individual requests processed:::" + bulkRequest.get().getRequests().size());
		return this.repository.save(bulkRequest.get());
	}
	
	private boolean verifyRequestSubmitStatus(List<Request> requests) {
		boolean status = true;
		for(Request req: requests) {
			if(!req.getState().toString().equals(RequestState.SUBMITTED.toString())) {
				status = false;
				break;
			}
		}
		return status;
	}

}
