package com.m4.pawning.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.json.JSONArray;

import com.m4.core.exception.PawnException;
import com.m4.core.util.MasterAction;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.RecordStatusEnum;
import com.m4.core.util.SessionUtil;
import com.m4.core.util.StrutsFormValidateUtil;
import com.m4.core.util.QueryCriteria.Oparator;
import com.m4.pawning.domain.Article;
import com.m4.pawning.domain.ArticleModel;
import com.m4.pawning.domain.InterestRates;
import com.m4.pawning.domain.Company;
import com.m4.pawning.domain.InterestSlab;
import com.m4.pawning.domain.Product;
import com.m4.pawning.service.InterestRatesService;
import com.m4.pawning.service.ProductService;


public class InterestRatesAction extends MasterAction {
	private static final Logger logger = Logger.getLogger(InterestRatesAction.class);
	private InterestRatesService interestRatesService;
	private ProductService productService;
	public void setInterestRatesService(InterestRatesService interestRatesService) {
		this.interestRatesService = interestRatesService;
	}
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public ActionForward create(ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.debug("**** Enter in to create method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		if(!validateForm.isEmpty()){
        	response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm,messageResources,getLocale(request),null).toString());
        }else{
        	InterestRates interestRates = new InterestRates();
        	interestRates.setCode(request.getParameter("code"));
        	interestRates.setDescription(request.getParameter("description"));
        	interestRates.setProductId(Integer.parseInt(request.getParameter("productId")));
        	interestRates.setIsActive(Integer.parseInt(request.getParameter("isActive")));

        	List<InterestSlab> slabs = new ArrayList<InterestSlab>();
        	String strSlabs = request.getParameter("slabs");

        	if(strSlabs!=null && !strSlabs.equals("")){
        		String rows[] = strSlabs.split("<next>");
        		for(String row:rows){
        			String[] columns = row.split("<@>");
        			InterestSlab interestSlab = new InterestSlab();
        			interestSlab.setProductId(interestRates.getProductId());
        			interestSlab.setSlabNo(Integer.parseInt(columns[0]));
        			interestSlab.setNoOfDaysFrom(Integer.parseInt(columns[1]));
        			interestSlab.setNoOfDaysTo(Integer.parseInt(columns[2]));
        			interestSlab.setRate(Double.parseDouble(columns[3]));
        			slabs.add(interestSlab);
        		}
        	}

        	try {
        		interestRatesService.createInterestRates(SessionUtil.getUserSession(request), interestRates,slabs);
        		response.getWriter().write(StrutsFormValidateUtil.getMessageCreateSuccess(messageResources).toString());
			} catch (PawnException ex) {
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
			}
        }

		logger.debug("**** Leaving from create method *****");

		return null;

	}

	public ActionForward update (ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.debug("**** Enter in to update method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		if(!validateForm.isEmpty()){
        	response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm,messageResources,getLocale(request),null).toString());
        }else{
        	InterestRates interestRates = new InterestRates();
        	interestRates.setCode(request.getParameter("code"));
        	interestRates.setDescription(request.getParameter("description"));
        	interestRates.setProductId(Integer.parseInt(request.getParameter("productId")));
        	interestRates.setIsActive(Integer.parseInt(request.getParameter("isActive")));
        	interestRates.setRecordId(Integer.parseInt(request.getParameter("recordId")));
        	interestRates.setVersion(Integer.parseInt(request.getParameter("version")));

        	List<InterestSlab> slabs = new ArrayList<InterestSlab>();
        	String strSlabs = request.getParameter("slabs");

        	if(strSlabs!=null && !strSlabs.equals("")){
        		String rows[] = strSlabs.split("<next>");
        		for(String row:rows){
        			String[] columns = row.split("<@>");
        			InterestSlab interestSlab = new InterestSlab();
        			interestSlab.setProductId(interestRates.getProductId());
        			interestSlab.setSlabNo(Integer.parseInt(columns[0]));
        			interestSlab.setNoOfDaysFrom(Integer.parseInt(columns[1]));
        			interestSlab.setNoOfDaysTo(Integer.parseInt(columns[2]));
        			interestSlab.setRate(Double.parseDouble(columns[3]));
        			slabs.add(interestSlab);
        		}
        	}
        	try {
        		interestRatesService.updateInterestRates(SessionUtil.getUserSession(request), interestRates,slabs);
        		response.getWriter().write(StrutsFormValidateUtil.getMessageUpdateSuccess(messageResources).toString());
			} catch (PawnException ex) {
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
			}
        }

		logger.debug("**** Leaving from update method *****");
		return null;

	}


	public ActionForward getAjaxData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to getGridData method *****");
		String recordId = request.getParameter("recordId");

		JSONArray mainArray = new JSONArray();
		if(recordId!=null && recordId!="" && recordId!="0"){
			InterestRates interestRates = interestRatesService.getInterestRateById(SessionUtil.getUserSession(request), Integer.parseInt(recordId));
			Product product = productService.getProductById(SessionUtil.getUserSession(request), interestRates.getProductId());
			mainArray.put(interestRates.getCode());
			mainArray.put(interestRates.getDescription());
			mainArray.put(product.getProductId());
			mainArray.put(product.getCode());
			mainArray.put(product.getDescription());
			mainArray.put(interestRates.getRecordId());
			mainArray.put(interestRates.getVersion());
			mainArray.put(interestRates.getIsActive());

		}else{
			List<InterestRates> list = (List<InterestRates>)interestRatesService.getAllInterestRates(SessionUtil.getUserSession(request), getQueryCriteriaList(request)).getDataList();
			for(InterestRates rates:list){
				JSONArray subArray = new JSONArray();
//				Product product = productService.getProductById(SessionUtil.getUserSession(request), rates.getProductId());
				subArray.put(rates.getCode());
				subArray.put(rates.getDescription());
//				subArray.put(product.getProductId());
//				subArray.put(product.getCode());
//				subArray.put(product.getDescription());
				subArray.put(rates.getRecordId());
				subArray.put(rates.getVersion());
				subArray.put(rates.getIsActive());

				mainArray.put(subArray);
			}
		}

		response.getWriter().write(mainArray.toString());
		logger.debug("**** Leaving from getGridData method *****");
		return null;
	}

	public ActionForward getProduct(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	MessageResources messageResources = getResources(request,"message");
    	Product product = null;
 		String code = null;

 		if (request.getParameter("code")!=null)
 			code =  request.getParameter("code");

 		try {
 			product = productService.getProductByCode(SessionUtil.getUserSession(request), code);
 		}catch(PawnException ex){
			response.getWriter().write(StrutsFormValidateUtil.getAJAXErrorMessage(ex, messageResources, request.getLocale()));
			return null;
 		}
 		response.getWriter().write(StrutsFormValidateUtil.getAJAXReferenceData(product.getRecordId(),product.getDescription()));
 		return null;
	}

	public ActionForward getSlabs(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MessageResources messageResources = getResources(request,"message");
		//String interestRateId = request.getParameter("interestId");

		List<InterestSlab> slabs = interestRatesService.getAllInterestSlabs(SessionUtil.getUserSession(request), getQueryCriteriaList(request)).getDataList();

		JSONArray mainArray = new JSONArray();
		for(InterestSlab slab:slabs){
			JSONArray array = new JSONArray();
			array.put(slab.getSlabNo());
			array.put(slab.getNoOfDaysFrom());
			array.put(slab.getNoOfDaysTo());
			array.put(slab.getRate());
			mainArray.put(array);
		}

		response.getWriter().write(mainArray.toString());
		return null;
	}

	@Override
	public ActionForward getAuthorizeData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONArray mainArray = new JSONArray();

		List<QueryCriteria> criteriaList = getAuthorizeQueryCriteriaList(request);
		criteriaList.add(new QueryCriteria("recordStatus",Oparator.GRATERTHAN,RecordStatusEnum.ACTIVE.getCode()));
		List<InterestRates> list = (List<InterestRates>)interestRatesService.getAllInterestRates(SessionUtil.getUserSession(request), getQueryCriteriaList(request)).getDataList();

		for(InterestRates rates:list){
			JSONArray subArray = new JSONArray();
			subArray.put(rates.getCode());
			subArray.put(rates.getDescription());
			subArray.put(getRecordStatusString(rates.getRecordStatus()));
		}
		
		response.getWriter().write(mainArray.toString());
		return null;
	}
}
