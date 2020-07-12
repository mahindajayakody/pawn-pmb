package com.m4.pawning.service;

import java.util.List;

import com.m4.core.exception.PawnException;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.domain.Receipt;
import com.m4.pawning.dto.ReceiptDTO;

public interface ReceiptService {
	ReceiptDTO getReceiptTicketData(UserConfig userConfig,int ticketId, int receiptType )throws PawnException;
	String createReceipt(UserConfig userConfig,Receipt receipt)throws PawnException;
	List<Receipt> getAllReceiptByTicketId(UserConfig userConfig,int ticketId)throws PawnException;
	DataBag getAllReceipt(UserConfig userConfig,List<QueryCriteria> criteriaList) throws PawnException;
	void updateReceiptPrint(UserConfig userConfig, String receiptNo )throws PawnException;
	void cancelReceipt(UserConfig userConfig, int receiptId)throws PawnException;
}
