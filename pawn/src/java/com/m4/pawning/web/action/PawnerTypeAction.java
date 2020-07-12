package com.m4.pawning.web.action;

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
import com.m4.pawning.domain.PawnerType;
import com.m4.pawning.service.PawnerTypeService;


public class PawnerTypeAction extends MasterAction {
	private PawnerTypeService pawnerTypeService;
	private static final Logger logger=Logger.getLogger(PawnerType.class);

	public void setPawnerTypeService(PawnerTypeService pawnerTypeService){
		this.pawnerTypeService=pawnerTypeService;
	}

	public ActionForward create(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		logger.debug("***** Enter In to Create Method ****");
		ActionMessages validateForm=form.validate(mapping, request);
		MessageResources messageResources=getResources(request,"message");

		if (!validateForm.isEmpty()){
			response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm,messageResources,getLocale(request),null).toString());
		}else{
			PawnerType pawnerType=new PawnerType();
			pawnerType.setCode(request.getParameter("code"));
			pawnerType.setDescription(request.getParameter("description"));

			try{
				pawnerTypeService.createPawnerType(SessionUtil.getUserSession(request), pawnerType);
				response.getWriter().write(StrutsFormValidateUtil.getMessageCreateSuccess(messageResources).toString());
			}catch(PawnException pex){
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(pex, messageResources, request.getLocale()).toString());
			}
		}

		logger.debug("**** Leaving Create Method ****");
		return null;

	}

	public ActionForward update(ActionMapping mapping ,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("**** Enter In to Update method ****");
		ActionMessages validateForm = form.validate(mapping, request);
		MessageResources messageResources=getResources(request,"message");

		if (!validateForm.isEmpty()){
			response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm, messageResources, getLocale(request), null).toString());
		}else{
			PawnerType pawnerType=new PawnerType();
			pawnerType.setCode(request.getParameter("code"));
			pawnerType.setDescription(request.getParameter("description"));
			pawnerType.setRecordId(Integer.parseInt(request.getParameter("recordId")));
			pawnerType.setVersion(Integer.parseInt(request.getParameter("version")));

			try{

				pawnerTypeService.modifyPawnerType(SessionUtil.getUserSession(request), pawnerType);
				response.getWriter().write(StrutsFormValidateUtil.getMessageUpdateSuccess(messageResources).toString());
			}catch (PawnException ex){
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
			}
		}
		logger.debug("***** Leaving the Update method ****");
		return null;
	}

	public ActionForward remove(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("**** Enter in to Delete methode ****");
		ActionMessages validateForm=form.validate(mapping, request);
		MessageResources messageResources=getResources(request,"message");

		if (!validateForm.isEmpty()){
			response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm, messageResources, getLocale(request), null).toString());
		}else{
			PawnerType pawnerType=new PawnerType();
			pawnerType.setRecordId(Integer.parseInt(request.getParameter("recordId")));
			pawnerType.setVersion(Integer.parseInt(request.getParameter("version")));

			try{
				pawnerTypeService.deletePawnerType(SessionUtil.getUserSession(request), pawnerType);
				response.getWriter().write(StrutsFormValidateUtil.getMessageDeleteSuccess(messageResources).toString());
			}catch(PawnException ex){
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
			}
		}
		logger.debug("**** Enter in to Delete methode ****");
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

        PawnerType pawnerType = new PawnerType();
		pawnerType.setRecordId(Integer.parseInt(request.getParameter("recordId")));
		pawnerType.setVersion(Integer.parseInt(request.getParameter("version")));

        try{
        	if(authorizeType.equals("Create") ){
        		pawnerTypeService.authorizeCreation(SessionUtil.getUserSession(request), pawnerType, authorize);
        	}else if( authorizeType.equals("Update") ){
        		pawnerTypeService.authorizeUpdation(SessionUtil.getUserSession(request), pawnerType, authorize);
        	}else if( authorizeType.equals("Delete") ){
        		pawnerTypeService.authorizeDeletion(SessionUtil.getUserSession(request), pawnerType, authorize);
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

	public ActionForward getAjaxData(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("**** Enter in to getAjaxData method ****");
		String recordId=request.getParameter("recordId");

		JSONArray mainArray=new JSONArray();

		if(recordId!=null && recordId!=""){
			PawnerType pawnerType=pawnerTypeService.getPawnerTypeById(SessionUtil.getUserSession(request), Integer.parseInt(recordId));
			mainArray.put(pawnerType.getCode());
			mainArray.put(pawnerType.getDescription());
			mainArray.put(pawnerType.getRecordId());
			mainArray.put(pawnerType.getVersion());
		}else{
			List<PawnerType> list=(List<PawnerType>)pawnerTypeService.getAllPawnerType(SessionUtil.getUserSession(request), getQueryCriteriaList(request)).getDataList();
			for (PawnerType type:list){
				JSONArray subArray=new JSONArray();
				subArray.put(type.getCode());
				subArray.put(type.getDescription());
				subArray.put("");
				subArray.put(type.getRecordId());
				subArray.put(type.getVersion());

				mainArray.put(subArray);
			}
		}

		response.getWriter().write(mainArray.toString());
		logger.debug("**** Leaving getAjaxData method ****");
		return null;
	}

	@Override
	public ActionForward getAuthorizeData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONArray mainArray = new JSONArray();

		List<QueryCriteria> criteriaList = getAuthorizeQueryCriteriaList(request);
		criteriaList.add(new QueryCriteria("recordStatus",Oparator.GRATERTHAN,RecordStatusEnum.ACTIVE.getCode()));
		List<PawnerType> list=(List<PawnerType>)pawnerTypeService.getAllPawnerType(SessionUtil.getUserSession(request), criteriaList).getDataList();

		for (PawnerType type:list){
			JSONArray subArray=new JSONArray();
			subArray.put(type.getCode());
			subArray.put(type.getDescription());
			subArray.put(getRecordStatusString(type.getRecordStatus()));
			subArray.put(type.getRecordId());
			subArray.put(type.getVersion());

			mainArray.put(subArray);
		}

		response.getWriter().write(mainArray.toString());
		return null;
	}
}
