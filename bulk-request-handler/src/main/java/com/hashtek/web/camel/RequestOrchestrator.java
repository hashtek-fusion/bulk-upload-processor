package com.hashtek.web.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class RequestOrchestrator implements Processor {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void process(Exchange exchange) throws Exception {
		// TODO Auto-generated method stub
		// Need to invoke validation & submission e-Bonding endpoints for each request in bulk
	}

}
