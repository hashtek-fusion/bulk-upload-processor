package com.hashtek.web.component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import com.hashtek.web.bean.BulkRequestBean;
import com.hashtek.web.entity.BulkRequest;
import com.hashtek.web.entity.ChangePortSpeed;
import com.hashtek.web.entity.Request;
import com.hashtek.web.enums.BulkRequestState;
import com.hashtek.web.enums.ChangeTypeAVPN;
import com.hashtek.web.enums.RequestState;

@Component
public class BulkUploadComponent {

	public Map<Integer, List<String>> parseExcelFile(InputStream file) throws IOException {
		Workbook workbook =  new XSSFWorkbook(file);//Retrieve workbook
		Sheet sheet = workbook.getSheetAt(0);//Retrieve first sheet
		Map<Integer, List<String>> data = new HashMap<>();
		int i=0;
		for(Row row : sheet) {
			data.put(i, new ArrayList<String>());
			for(Cell cell : row) {
				switch(cell.getCellTypeEnum()) {
					case STRING:
						data.get(i).add(cell.getStringCellValue());
						break;
					case NUMERIC:
						data.get(i).add(cell.getNumericCellValue()+"");
						break;						
					default:
						data.get(i).add(cell.getStringCellValue());
				}				
			}
			i++;
		}//Iterating row ends
		return data;
	}
	
	public BulkRequest generateDataToPersist(Map<Integer, List<String>> excelData, BulkRequestBean formData) {
		Iterator<Map.Entry<Integer, List<String>>> itr= excelData.entrySet().iterator();
		BulkRequest bulkRequest = new BulkRequest();
		bulkRequest.setChangeType(formData.getChangeType());
		bulkRequest.setService(formData.getService());
		bulkRequest.setOrgId(formData.getOrgId());
		bulkRequest.setState(BulkRequestState.ACCEPTED.toString());
		bulkRequest.setSubmitterEmail(formData.getSubmitterEmail());
		bulkRequest.setSubmittedDateTime(new Date());		
		List<Request> requestList = new ArrayList<Request>();
		bulkRequest.setRequests(requestList);
		while(itr.hasNext()) {
			Request request = new Request();
			Map.Entry<Integer, List<String>> row = itr.next();			
			if(row.getKey()!=0) {//No need to consider the header row from excel
				List<String> columns = row.getValue();				
				request.setState(RequestState.ACCEPTED.toString());
				if(formData.getChangeType().equalsIgnoreCase(ChangeTypeAVPN.CHANGE_PORT_SPEED.toString())) {					
					request.setCustomerRequestedChange(constructPortSpeedRequest(columns), ChangeTypeAVPN.CHANGE_PORT_SPEED.toString());					
				}
				requestList.add(request);				
			}
			
		}//Iteration all requests ends
		return bulkRequest;
	}
	
	private ChangePortSpeed constructPortSpeedRequest(List<String> columns) {//For each MACD need to construct a new method
		ChangePortSpeed changeRequest = new ChangePortSpeed();
		changeRequest.setCircuitId(columns.get(0));
		changeRequest.setPortSpeed(columns.get(1));
		changeRequest.setContactType(columns.get(2));
		changeRequest.setFirstName(columns.get(3));
		changeRequest.setLastName(columns.get(4));
		changeRequest.setEmail(columns.get(5));
		changeRequest.setPhone(columns.get(6));
		return changeRequest;
	}
}
