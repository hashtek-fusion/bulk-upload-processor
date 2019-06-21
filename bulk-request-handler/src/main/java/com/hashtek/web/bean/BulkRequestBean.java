package com.hashtek.web.bean;

import java.io.Serializable;

public class BulkRequestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orgId;
	private String changeType;
	private String service;
	private String submitterEmail;
	private String templateVersion;
	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getChangeType() {
		return changeType;
	}
	public void setChangeType(String changeType) {
		this.changeType = changeType;
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
	public String getTemplateVersion() {
		return templateVersion;
	}
	public void setTemplateVersion(String templateVersion) {
		this.templateVersion = templateVersion;
	}	
	
}
