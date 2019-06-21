package com.hashtek.web.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "BulkRequests")
public class BulkRequest {	

	@Id
	private String bulkRequestId;	
	private String orgId;
	private String service;
	private String submitterEmail;
	private String changeType;
	private Date submittedDateTime;
	private String state;
	private List<Request> requests;
	
	public String getBulkRequestId() {
		return bulkRequestId;
	}
	public void setBulkRequestId(String bulkRequestId) {
		this.bulkRequestId = bulkRequestId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getSubmitterEmail() {
		return submitterEmail;
	}
	public void setSubmitterEmail(String submitterEmail) {
		this.submitterEmail = submitterEmail;
	}
	public String getChangeType() {
		return changeType;
	}
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	public Date getSubmittedDateTime() {
		return submittedDateTime;
	}
	public void setSubmittedDateTime(Date submittedDateTime) {
		this.submittedDateTime = submittedDateTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<Request> getRequests() {
		return requests;
	}
	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}

	
}
