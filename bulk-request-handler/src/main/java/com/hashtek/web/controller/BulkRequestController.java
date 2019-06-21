package com.hashtek.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import com.hashtek.web.bean.BulkRequestBean;
import com.hashtek.web.component.BulkUploadComponent;
import com.hashtek.web.entity.BulkRequest;
import com.hashtek.web.repository.BulkRequestRepository;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class BulkRequestController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BulkUploadComponent uploadComponent;
	
	@Autowired
	private BulkRequestRepository repository; 
	
	@GetMapping("/")	
	public RedirectView renderUploadForm() {
		return new RedirectView("index.html");
	}
	
	@GetMapping("/bulk-requests/{orgId}")
	public List<BulkRequest> getBulkRequests(@PathVariable String orgId) {
		List<BulkRequest> bulkRequests= this.repository.findByOrgId(orgId);
		return bulkRequests;
		
	}
	
	@GetMapping("/bulk-requests/{orgId}/request/{requestId}")
	public Optional<BulkRequest> getBulkRequestById(@PathVariable String orgId, @PathVariable String requestId) {
		//TODO:: Need to compare the OrgID of bulk request with logged in user orgid from profile 
		Optional<BulkRequest> request = this.repository.findById(requestId);
		return request;
	}
	
	@PostMapping("/bulk-requests")
	public RedirectView createBulkRequest(@RequestParam("file") MultipartFile file, @ModelAttribute("bulkForm") BulkRequestBean formData , RedirectAttributes redirectAttribs ) {		
		redirectAttribs.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
		logger.info(file.getOriginalFilename());
		logger.info("OrgId::" + formData.getOrgId());
		try {
			Map data = this.uploadComponent.parseExcelFile(file.getInputStream());
			BulkRequest bulkRequest = this.uploadComponent.generateDataToPersist(data, formData);
			this.repository.save(bulkRequest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new RedirectView("/", true);
	}
	
	@GetMapping("/error")
	public String handleError() {
		return "OOPS Something Wentwrong!!!";
	}

}
