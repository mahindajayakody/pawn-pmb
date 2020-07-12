package com.m4.pawning.web.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
import com.m4.core.util.SessionUtil;
import com.m4.core.util.StrutsFormValidateUtil;
import com.m4.pawning.domain.ProgramAccess;
import com.m4.pawning.domain.SystemProgram;
import com.m4.pawning.domain.UserGroup;
import com.m4.pawning.dto.ProgramAccessDTO;
import com.m4.pawning.service.ProgramAccessService;
import com.m4.pawning.service.UserGroupService;

public class ProgramAccessAction extends MasterAction {
	private static final Logger logger = Logger.getLogger(ProgramAccessAction.class);

	private ProgramAccessService programAccessService;
	private UserGroupService userGroupService;

	public void setProgramAccessService(ProgramAccessService programAccessService) {
		this.programAccessService = programAccessService;
	}
	public void setUserGroupService(UserGroupService userGroupService) {
		this.userGroupService = userGroupService;
	}

	public ActionForward createOrUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("*** Enter in to createOrUpdate method ***");
		ActionMessages validateForm = form.validate(mapping,request);
        MessageResources messageResources = getResources(request,"message");
        List<ProgramAccess> accessList = null;
        String data = null;

        if(!validateForm.isEmpty()){
        	response.getWriter().write(StrutsFormValidateUtil.getMessages(request, validateForm,messageResources,getLocale(request),null).toString());
        }else{
        	int userGroupId = Integer.parseInt(request.getParameter("userGroupId"));
        	data = request.getParameter("data");
        	String[] rows = data.split("<row>");
        	if(rows.length>0){
        		accessList = new ArrayList<ProgramAccess>();
        		for(String row:rows){
	        		if(!row.equals("")){
	        			String[] column = row.split("<@>");
	        			ProgramAccess programAccess = new ProgramAccess();
	        			programAccess.setSystemProgram(new SystemProgram(Integer.parseInt(column[0])));
	        			if(!column[1].equals("0"))programAccess.setProgramAccessId(Integer.parseInt(column[1]));
	        			programAccess.setAccess(column[2]);
	        			if(!column[3].equals("-1"))programAccess.setVersion(Integer.parseInt(column[3]));
	        			programAccess.setUserGroupId(userGroupId);
	        			accessList.add(programAccess);
	        		}
        		}
        	}
        	try {
        		programAccessService.createOrUpdateOfficerAccess(SessionUtil.getUserSession(request),accessList);
        		response.getWriter().write(StrutsFormValidateUtil.getMessageUpdateSuccess(messageResources).toString());
			} catch (PawnException ex) {
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
			}
        }
        logger.info("*** Leave from createOrUpdate method ***");
		return null;
	}

	public ActionForward getTreeData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("*** Enter in to getTreeData method ***");
		ActionMessages validateForm = form.validate(mapping,request);
        MessageResources messageResources = getResources(request,"message");

        int userGroupId = Integer.parseInt(request.getParameter("userGroupId"));
        Collection<ProgramAccessDTO> collection = null;

        try {
        	collection = programAccessService.getAllProgramsWithAccessByGroupId(userGroupId);
		} catch (PawnException ex) {
			logger.error("*** Error occcur while getting records from officerAccess ***");
		}

		JSONArray mainArray = new JSONArray();
		Iterator<ProgramAccessDTO> it = collection.iterator();
		while (it.hasNext()) {
			ProgramAccessDTO element = (ProgramAccessDTO) it.next();
			JSONArray objArray = new JSONArray();
			objArray.put(element.getSystemProgramId());
			objArray.put(element.getParentId());
			objArray.put(element.getDescription());
			objArray.put(element.getProgramAccessId());
			objArray.put(element.getAccess()!=null?element.getAccess():"0");
			objArray.put(element.getVersion());
			objArray.put(element.getDefAccess());
			mainArray.put(objArray);
		}
		response.getWriter().write(mainArray.toString());
		logger.info("*** Leave from getTreeData method ***");
		return null;
	}

	public ActionForward getUserGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	MessageResources messageResources = getResources(request,"message");
    	UserGroup userGroup = null;
 		String code = null;

 		if (request.getParameter("code")!=null)
 			code =  request.getParameter("code");

 		try {
 			userGroup = userGroupService.getUserGroupByCode(SessionUtil.getUserSession(request), code);
 		}catch(PawnException ex){
			response.getWriter().write(StrutsFormValidateUtil.getAJAXErrorMessage(ex, messageResources, request.getLocale()));
			return null;
 		}
 		response.getWriter().write(StrutsFormValidateUtil.getAJAXReferenceData(userGroup.getUserGroupId(),userGroup.getDescription()));
 		return null;
	}

	@Override
	protected ActionForward getAuthorizeData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return null;
	}
}
