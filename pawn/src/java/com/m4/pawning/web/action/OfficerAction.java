package com.m4.pawning.web.action;

import java.util.ArrayList;
import java.util.Collection;
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

import com.m4.core.bean.AccessBranch;
import com.m4.core.exception.PawnException;
import com.m4.core.util.MasterAction;
import com.m4.core.util.PasswordService;
import com.m4.core.util.QueryCriteria;
import com.m4.core.util.RecordStatusEnum;
import com.m4.core.util.SessionUtil;
import com.m4.core.util.StrutsFormValidateUtil;
import com.m4.core.util.QueryCriteria.Oparator;
import com.m4.pawning.domain.Branch;
import com.m4.pawning.domain.Officer;
import com.m4.pawning.domain.Pawner;
import com.m4.pawning.domain.UserGroup;
import com.m4.pawning.service.BranchService;
import com.m4.pawning.service.OfficerService;
import com.m4.pawning.service.PawnerService;
import com.m4.pawning.service.UserGroupService;

public class OfficerAction extends MasterAction {
	private static final Logger logger = Logger.getLogger(OfficerAction.class);
	private BranchService branchService;
	private UserGroupService userGroupService;
	private OfficerService officerService;
	private PawnerService pawnerService;

	public void setOfficerService(OfficerService officerService) {
		this.officerService = officerService;
	}
	public void setBranchService(BranchService branchService) {
		this.branchService = branchService;
	}
	public void setUserGroupService(UserGroupService userGroupService) {
		this.userGroupService = userGroupService;
	}
	public void setPawnerService(PawnerService pawnerService) {
		this.pawnerService = pawnerService;
	}

	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to create method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		if(!validateForm.isEmpty()){
        	response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm,messageResources,getLocale(request),null).toString());
        }else{
        	Officer officer = new Officer();
        	officer.setBranch(new Branch(Integer.parseInt(request.getParameter("branchId"))));
        	officer.setPawner(new Pawner(Integer.parseInt(request.getParameter("pawnerId"))));
        	officer.setUserGroup(new UserGroup(Integer.parseInt(request.getParameter("userGroupId"))));
        	officer.setUserName(request.getParameter("userName"));
        	//officer.setPassword(PasswordService.getInstance().encrypt(request.getParameter("password")));
        	officer.setPassword(request.getParameter("password"));
        	officer.setIsActive(Integer.parseInt(request.getParameter("isActive")));
        	
        	String accessBranch = request.getParameter("accessBranch");
        	String[] branchArr = accessBranch.split("<#>");
        	
        	Collection<AccessBranch> accBranchCollection = new ArrayList<AccessBranch>();
        	for(String acc : branchArr){
        		String[] idName = acc.split("<@>");
        		AccessBranch branch = new AccessBranch();
        		branch.setBranchId(Integer.parseInt(idName[0]));
        		branch.setBranchName(idName[1]);
        		
        		accBranchCollection.add(branch);
        	}

        	officer.setAccessBranchCollection(accBranchCollection);
        	
        	try {
        		officerService.createOfficer(SessionUtil.getUserSession(request), officer);
        		response.getWriter().write(StrutsFormValidateUtil.getMessageCreateSuccess(messageResources).toString());
			} catch (PawnException ex) {
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
			}
        }

		logger.debug("**** Leaving from create method *****");
		return null;
	}

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to update method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		if(!validateForm.isEmpty()){
        	response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm,messageResources,getLocale(request),null).toString());
        }else{
        	int officerId   = Integer.parseInt(request.getParameter("recordId"));
        	String password = request.getParameter("password"); 
        	String changePassword = request.getParameter("change");
        	Officer oldOfficer = officerService.getOfficerById(SessionUtil.getUserSession(request), officerId);
        	Officer officer = new Officer();
        	
        	if (!changePassword.equals("Yes"))
        	{
	        	officer.setBranch(new Branch(Integer.parseInt(request.getParameter("branchId"))));
	        	officer.setPawner(new Pawner(Integer.parseInt(request.getParameter("pawnerId"))));
	        	officer.setUserGroup(new UserGroup(Integer.parseInt(request.getParameter("userGroupId"))));
	        	officer.setUserName(request.getParameter("userName"));
	        	officer.setPassword(oldOfficer.getPassword());
	        	officer.setVersion(Integer.parseInt(request.getParameter("version")));
	        	officer.setOfficerId(officerId);
	        	officer.setIsActive(Integer.parseInt(request.getParameter("isActive")));
	        	String accessBranch = request.getParameter("accessBranch");
	        	officer.setPasswordValidPeriod(oldOfficer.getPasswordValidPeriod());
	        	officer.setPasswordRepatTime(oldOfficer.getPasswordRepatTime());
	        	String[] branchArr = accessBranch.split("<#>");
	        	
	        	Collection<AccessBranch> accBranchCollection = new ArrayList<AccessBranch>();
	        	for(String acc : branchArr){
	        		String[] idName = acc.split("<@>");
	        		AccessBranch branch = new AccessBranch();
	        		branch.setBranchId(Integer.parseInt(idName[0]));
	        		branch.setBranchName(idName[1]);
	        		
	        		accBranchCollection.add(branch);
	        	}
	
	        	officer.setAccessBranchCollection(accBranchCollection);
	        	try {
	        		officerService.updateOfficer(SessionUtil.getUserSession(request), officer);
	        		response.getWriter().write(StrutsFormValidateUtil.getMessageUpdateSuccess(messageResources).toString());
				} catch (PawnException ex) {
					response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
				}
        	}else{
        		officer.setRecordId(Integer.parseInt(request.getParameter("recordId")));
        		officer.setPawner(new Pawner(Integer.parseInt(request.getParameter("pawnerId"))));
        		officer.setUserName(request.getParameter("userName"));
        		officer.setPassword(password);
        		try {
            		officerService.updateOfficer(SessionUtil.getUserSession(request), officer);					
	        		response.getWriter().write(StrutsFormValidateUtil.getMessageUpdateSuccess(messageResources).toString());            		
				} catch (PawnException ex) {
					response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
				}

        	}
        	
        }

		logger.debug("**** Leaving from update method *****");
		return null;
	}

	public ActionForward authorize(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("**** Enter in to remove method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		String recordId = request.getParameter("recordId");
        String version  = request.getParameter("version");
        String authorizeType   = request.getParameter("authorizeMode");
        boolean authorize      = Boolean.parseBoolean(request.getParameter("authorizeValue"));

        if((recordId==null)||(version==null)||(recordId.equals(""))||(version.equals(""))){
        	response.getWriter().write(StrutsFormValidateUtil.getMessageNotFound(messageResources).toString());
        	return null;
        }

        Officer officer = new Officer();

        try{
        	if(authorizeType.equals("Create") ){
        		officerService.authorizeCreation(SessionUtil.getUserSession(request), officer, authorize);
        	}else if( authorizeType.equals("Update") ){
        		officerService.authorizeUpdation(SessionUtil.getUserSession(request), officer, authorize);
        	}else if( authorizeType.equals("Delete") ){
        		officerService.authorizeDeletion(SessionUtil.getUserSession(request), officer, authorize);
	    	}
        }catch(PawnException ex){
	    	logger.error("exception in authorization" + ex.getExceptionCode());
	    	response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, getLocale(request)).toString());
	    	return null;
        }

        if(authorize==true){
        	response.getWriter().write(StrutsFormValidateUtil.getMessageAuthorizeSuccess(messageResources).toString());
        }else{
        	response.getWriter().write(StrutsFormValidateUtil.getMessageRejectSuccess(messageResources).toString());
        }

		return null;
	}

	public ActionForward getAjaxData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("**** Enter in to getGridData method *****");
		String recordId = request.getParameter("recordId");

		JSONArray mainArray = new JSONArray();

		if(recordId!=null && recordId!=""){
			Officer officer = officerService.getOfficerById(SessionUtil.getUserSession(request), Integer.parseInt(recordId));
			mainArray.put(officer.getUserName());
			mainArray.put(officer.getBranch().getBarnchName());
			mainArray.put(officer.getUserGroup().getDescription());
			mainArray.put(officer.getPawner().getIdOrBrNo());
			mainArray.put(officer.getBranch().getBranchId());
			mainArray.put(officer.getBranch().getCode());
			mainArray.put(officer.getPawner().getPawnerCode());
			mainArray.put(officer.getPawner().getPawnerName());
			mainArray.put(officer.getPawner().getPawnerId());
			mainArray.put(officer.getUserGroup().getUserGroupId());
			mainArray.put(officer.getUserGroup().getCode());
			mainArray.put(officer.getPassword());
			mainArray.put(officer.getRecordId());
			mainArray.put(officer.getVersion());
//			mainArray.put(officer.getIsActive());
			
			JSONArray array = new JSONArray();
			for(AccessBranch accessBranch : officer.getAccessBranchCollection()){
				JSONArray accArray = new JSONArray();
				accArray.put(accessBranch.getBranchId());
				accArray.put(accessBranch.getBranchName());
				
				array.put(accArray);
			}
			
			mainArray.put(array);
		}else{
			List<Officer> list = officerService.getAllOfficer(SessionUtil.getUserSession(request), getQueryCriteriaList(request)).getDataList();
			for(Officer officer:list){
				JSONArray array = new JSONArray();
				array.put(officer.getUserName());
				array.put(officer.getBranch().getBarnchName());
				array.put(officer.getUserGroup().getDescription());
				array.put(officer.getPawner().getIdOrBrNo());
				array.put(officer.getBranch().getBranchId());
				array.put(officer.getBranch().getCode());
				array.put(officer.getPawner().getPawnerCode());
				array.put(officer.getPawner().getPawnerName());
				array.put(officer.getPawner().getPawnerId());
				array.put(officer.getUserGroup().getUserGroupId());
				array.put(officer.getUserGroup().getCode());
				array.put(officer.getPassword());
				array.put(officer.getRecordId());
				array.put(officer.getVersion());
//				array.put(officer.getIsActive());
				
				JSONArray brArray = new JSONArray();
				for(AccessBranch accessBranch : officer.getAccessBranchCollection()){
					JSONArray accArray = new JSONArray();
					accArray.put(accessBranch.getBranchId());
					accArray.put(accessBranch.getBranchName());
					
					brArray.put(accArray);
				}
				
				array.put(brArray);
				mainArray.put(array);
			}
		}

		response.getWriter().write(mainArray.toString());
		logger.debug("**** Leaving from getGridData method *****");
		return null;
	}

	public ActionForward getBranch(ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		MessageResources messageResources = getResources(request,"message");
    	Branch branch = null;
 		String code   = request.getParameter("code");

 		try {
 			branch = branchService.getBranchByCode(SessionUtil.getUserSession(request), code);
 		}catch(PawnException ex){
			response.getWriter().write(StrutsFormValidateUtil.getAJAXErrorMessage(ex, messageResources, request.getLocale()));
			return null;
 		}
 		response.getWriter().write(StrutsFormValidateUtil.getAJAXReferenceData(branch.getRecordId(),branch.getBarnchName()));
		return null;
	}

	public ActionForward getUserGroup(ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		MessageResources messageResources = getResources(request,"message");
    	UserGroup userGroup = null;
 		String code   = request.getParameter("code");

 		try {
 			userGroup = userGroupService.getUserGroupByCode(SessionUtil.getUserSession(request), code);
 		}catch(PawnException ex){
			response.getWriter().write(StrutsFormValidateUtil.getAJAXErrorMessage(ex, messageResources, request.getLocale()));
			return null;
 		}
 		response.getWriter().write(StrutsFormValidateUtil.getAJAXReferenceData(userGroup.getRecordId(),userGroup.getDescription()));
		return null;
	}

	public ActionForward getPawner(ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		MessageResources messageResources = getResources(request,"message");
    	Pawner pawner = null;
 		String code   = request.getParameter("code");

 		try {
 			pawner = (Pawner)pawnerService.getPawnerById(SessionUtil.getUserSession(request),Integer.parseInt(code));
 		}catch(PawnException ex){
			response.getWriter().write(StrutsFormValidateUtil.getAJAXErrorMessage(ex, messageResources, request.getLocale()));
			return null;
 		}
 		response.getWriter().write(StrutsFormValidateUtil.getAJAXReferenceData(pawner.getRecordId(),pawner.getPawnerName()));
		return null;
	}

	private String getAddress(Branch branch){
		StringBuilder builder = new StringBuilder();
		builder.append(branch.getAddressline1());
		builder.append(", ");
		builder.append(branch.getAddressline2());
		builder.append(branch.getAddressline3()!=null?" ":".");
		builder.append(branch.getAddressline3()!=null?branch.getAddressline3():"");
		builder.append(branch.getAddressline3()!=null && branch.getAddressline4()!=null?" ":"");
		builder.append(branch.getAddressline3()!=null && branch.getAddressline4()!=null?branch.getAddressline4():"");
		return builder.toString();
	}

	@Override
	public ActionForward getAuthorizeData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONArray mainArray = new JSONArray();

		List<QueryCriteria> criteriaList = getAuthorizeQueryCriteriaList(request);
		criteriaList.add(new QueryCriteria("recordStatus",Oparator.GRATERTHAN,RecordStatusEnum.ACTIVE.getCode()));
		List<Officer> list = officerService.getAllOfficer(SessionUtil.getUserSession(request), criteriaList).getDataList();

		for(Officer officer:list){
			JSONArray array = new JSONArray();

			array.put(officer.getUserName());
			array.put(officer.getBranch().getCode());
			array.put(officer.getUserGroup().getCode());
			array.put(getRecordStatusString(officer.getRecordStatus()));
			array.put(officer.getRecordId());
			array.put(officer.getVersion());
			mainArray.put(array);
		}

		response.getWriter().write(mainArray.toString());
		return null;
	}
}
