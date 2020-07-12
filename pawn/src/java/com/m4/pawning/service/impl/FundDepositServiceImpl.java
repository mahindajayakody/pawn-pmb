package com.m4.pawning.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableServiceImpl;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.FundDepositDAO;
import com.m4.pawning.dao.FundRequestDAO;
import com.m4.pawning.domain.FundDeposit;
import com.m4.pawning.domain.FundRequest;
import com.m4.pawning.service.FundDepositService;
import com.m4.pawning.service.FundRequestService;
import com.m4.pawning.util.SendMail;

public class FundDepositServiceImpl extends AuthorizableServiceImpl implements FundDepositService {
	private static final Logger logger=Logger.getLogger(FundRequest.class);
	private static final String EMAIL_BODY = "Do not reply. This is a system generated mail";
	

	private FundDepositDAO fundDepositDAO;
	private SendMail sendMail = new SendMail();
	
	
	public void setFundDepositDAO(FundDepositDAO fundDepositDAO) {
		this.fundDepositDAO = fundDepositDAO;
	}

	public void approveFundDeposit(UserConfig userConfig,
			FundDeposit fundDeposit) throws PawnException {
		logger.debug("***** Enter in to approveFundDeposit method ****");
		final String emailAddess = fundDeposit.getMail();
		final String subject = "Fund Deposite Has Been Approved for : ";
		fundDepositDAO.approveFundDeposit(userConfig, fundDeposit);
		
		if (emailAddess != null){
			try {
				sendMail.sendMessage(emailAddess, subject + fundDeposit.getDepositNo(), EMAIL_BODY, null);	
			} catch (Exception e) {
				logger.debug(e.getStackTrace());
				throw new PawnException("error.sendemail");
			}
		}
		logger.debug("***** Leaving approveFundDeposit method ****");
	}

	public void createFundDeposit(UserConfig userConfig, FundDeposit fundDeposit)
			throws PawnException {
		final String subject = "Fund Deposit Approval Needed for : ";
		final String emailAddess = userConfig.getBrachEmail();
		fundDepositDAO.createFundDeposit(userConfig, fundDeposit);
		
		
		try {
			sendMail.sendMessage(emailAddess, subject + fundDeposit.getDepositNo(), EMAIL_BODY, null);	
		} catch (Exception e) {
			throw new PawnException("error.sendemail");
		}
		
	}

	public DataBag getAllFundDeposit(UserConfig userConfig,
			List<QueryCriteria> criteriaList) throws PawnException {
		return fundDepositDAO.getAllFundDeposit(userConfig, criteriaList);
	}

	public FundDeposit getFundDepositById(UserConfig userConfig, int recordId)
			throws PawnException {
		return fundDepositDAO.getFundDepositById(userConfig, recordId);
	}

	public FundDeposit getFundDepositByDepositNo(UserConfig userConfig,
			String depositNo) throws PawnException {
		return fundDepositDAO.getFundDepositByDepositNo(userConfig, depositNo);
	}
	public List<Object[]> getTiketsForTheDay(UserConfig userConfig) 
	{
		return fundDepositDAO.getTiketsForTheDay(userConfig);
	}
	public List<Object[]> getReceiptsForTheDay(UserConfig userConfig){
		return fundDepositDAO.getReceiptsForTheDay(userConfig);
	}
	public List<Object[]> getTiketAllForTheDay(UserConfig userConfig){
		return fundDepositDAO.getTiketAllForTheDay(userConfig);
	}
	public List<Object[]> getReceiptAllForTheDay(UserConfig userConfig){
		return fundDepositDAO.getReceiptAllForTheDay(userConfig);
	}
	public List<Object[]> getFundRequestAllForTheDay(UserConfig userConfig){
		return fundDepositDAO.getFundRequestAllForTheDay(userConfig);
	}
	public List<Object[]> getFundDepositAllForTheDay(UserConfig userConfig){
		return fundDepositDAO.getFundDepositAllForTheDay(userConfig);
	}
}
