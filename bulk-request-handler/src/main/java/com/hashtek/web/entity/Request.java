package com.hashtek.web.entity;

import com.hashtek.web.constants.ChangeTypeAVPN;

public class Request {	

	private String reasonForFailure;
	private String state;
	private String orderNumber;
	private Object customerRequestedChange;	

	public String getReasonForFailure() {
		return reasonForFailure;
	}
	public void setReasonForFailure(String reasonForFailure) {
		this.reasonForFailure = reasonForFailure;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Object getCustomerRequestedChange() {
		return customerRequestedChange;
	}
	public void setCustomerRequestedChange(Object customerRequestedChange, String changeType) {
		if(changeType.equalsIgnoreCase(ChangeTypeAVPN.CHANGE_PORT_SPEED.toString()))			
			this.customerRequestedChange = (ChangePortSpeed)customerRequestedChange;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}	
	
}
