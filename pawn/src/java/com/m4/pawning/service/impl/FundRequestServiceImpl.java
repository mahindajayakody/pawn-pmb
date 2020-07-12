package com.m4.pawning.service.impl;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.log4j.Logger;

import com.m4.core.exception.PawnException;
import com.m4.core.util.AuthorizableServiceImpl;
import com.m4.core.util.DataBag;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.StrutsFormValidateUtil;
import com.m4.core.util.UserConfig;
import com.m4.pawning.dao.BranchDAO;
import com.m4.pawning.dao.FundRequestDAO;
import com.m4.pawning.domain.Branch;
import com.m4.pawning.domain.FundRequest;
import com.m4.pawning.service.FundRequestService;
import com.m4.pawning.util.SendMail;

public class FundRequestServiceImpl extends AuthorizableServiceImpl implements
		FundRequestService {

	private static final Logger logger = Logger.getLogger(FundRequest.class);
	private static final String EMAIL_BODY = "Do not reply. This is a system generated mail";

	private FundRequestDAO fundRequestDAO;
	private BranchDAO branchDAO;
	private SendMail sendMail = new SendMail();
	static DecimalFormat points2decimalFormat = new DecimalFormat();
	static {
		points2decimalFormat.setMinimumFractionDigits(2);
		points2decimalFormat.setMaximumFractionDigits(2);
		points2decimalFormat.setGroupingSize(3);
	}

	public void setFundRequestDAO(FundRequestDAO fundRequestDAO) {
		this.fundRequestDAO = fundRequestDAO;
	}

	public void setBranchDAO(BranchDAO branchDAO) {
		this.branchDAO = branchDAO;
	}

	public void createFundRequest(UserConfig userConfig, FundRequest fundRequest)throws PawnException {
		fundRequestDAO.createFundRequest(userConfig, fundRequest);
		final String emailAddess = userConfig.getBrachEmail();
		final String subject = "Fund Request Approval Needed for : ";
		final StringBuilder body = new StringBuilder();

		logger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ emailAddess " + emailAddess);
		logger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ subject	 " + subject + fundRequest.getRequestNo());

		Branch branch = branchDAO.getBranchById(userConfig, userConfig.getBranchId());
		body.append("Requesting Date          :- ");
		body.append(StrutsFormValidateUtil.parseString(userConfig.getLoginDate()));
		body.append("\n");
		body.append("Requesting Branch        :- ");
		body.append(userConfig.getBranchName());
		body.append("\n");
		body.append("Requesting Officer       :- ");
		body.append(userConfig.getLoginName());
		body.append("\n");
		body.append("Authorized Fund Limit    :- ");
		body.append(points2decimalFormat.format(branch.getFundLimit()));
		body.append("\n");
		body.append("Balance Amount Available :- ");
		body.append(points2decimalFormat.format(branch.getFundAvailable()));
		body.append("\n");
		body.append("Requesting Amount        :- ");
		body.append(points2decimalFormat.format(fundRequest.getRequestAmount()));
		body.append("\n\n");
		body.append(EMAIL_BODY);

		try {
			sendMail.sendMessage(emailAddess, subject + fundRequest.getRequestNo(), body.toString(), null);
		} catch (Exception e) {
			logger.error(e);
			throw new PawnException("error.sendemail");
		}
	}

	public void approveFundRequest(UserConfig userConfig, FundRequest fundRequest) throws PawnException {
		logger.debug("***** Enter in to approveFundRequest method ****");
		// final String emailAddess = "nuwankaman2005@gmail.com";
		final String emailAddess = fundRequest.getMail();
		final String subject = "Fund Request Approved for : ";

		fundRequestDAO.approveFundRequest(userConfig, fundRequest);

		if (emailAddess != null) {
			try {
				sendMail.sendMessage(emailAddess, subject
						+ fundRequest.getRequestNo(), EMAIL_BODY, null);
			} catch (Exception e) {
				logger.debug(e.getStackTrace());
				throw new PawnException("error.sendemail");
			}
		}
		logger.debug("***** Leaving approveFundRequest method ****");
	}

	public DataBag getAllFundRequest(UserConfig userConfig,
			List<QueryCriteria> criteriaList) throws PawnException {
		return fundRequestDAO.getAllFundRequest(userConfig, criteriaList);
	}

	public FundRequest getFundRequestById(UserConfig userConfig, int recordId)
			throws PawnException {
		return fundRequestDAO.getFundRequestById(userConfig, recordId);
	}

	public FundRequest getFundRequestByRequestNo(UserConfig userConfig,
			String requestNo) throws PawnException {
		return fundRequestDAO.getFundRequestByRequestNo(userConfig, requestNo);
	}

	public void updateFundRequest(UserConfig userConfig, FundRequest fundRequest)
			throws PawnException {
		fundRequestDAO.updateFundRequest(userConfig, fundRequest);
	}
}
