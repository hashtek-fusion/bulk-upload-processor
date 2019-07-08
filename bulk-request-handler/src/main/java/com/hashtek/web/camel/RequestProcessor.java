package com.hashtek.web.camel;

import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.hashtek.web.entity.ChangePortSpeed;
import com.hashtek.web.entity.Request;

@Component
public class RequestProcessor implements Processor {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void process(Exchange exchange) throws Exception {		
		Request request = exchange.getIn().getBody(Request.class);
		//logger.info("State ::" + request.getState());
		//logger.info("Order Number::" + request.getOrderNumber());
		request.setState("Processed");
		request.setOrderNumber("B" + new Date().getSeconds());
		exchange.getOut().setBody(request);		
	}

}
