package com.hashtek.web.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import com.hashtek.web.entity.BulkRequest;

public interface BulkRequestRepository extends MongoRepository<BulkRequest, String> {

	public BulkRequest findByBulkRequestId(String bulkRequestId);
	public List<BulkRequest> findByOrgId(String orgId);
}
