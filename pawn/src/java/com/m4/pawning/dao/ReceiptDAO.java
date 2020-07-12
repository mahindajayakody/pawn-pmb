package com.m4.pawning.dao;

import java.util.List;

import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Receipt;
import com.m4.pawning.dto.ReceiptDTO;

public interface ReceiptDAO {
	ReceiptDTO getReceiptTicketData(UserConfig userConfig,int ticketId, int receiptType );
	String createReceipt(UserConfig userConfig,Receipt receipt);
	List<Receipt> getAllReceiptByTicketId(UserConfig userConfig,int ticketId);
	DataBag getAllReceipt(UserConfig userConfig,List<QueryCriteria> criteriaList);
	void updateReceiptPrint(UserConfig userConfig, String receiptNo );
	void cancelReceipt(UserConfig userConfig, int receiptId);	
}
