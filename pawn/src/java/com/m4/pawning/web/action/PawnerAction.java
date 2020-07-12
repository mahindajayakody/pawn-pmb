package com.m4.pawning.web.action;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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

import com.m4.core.exception.PawnException;
import com.m4.core.util.MasterAction;
import com.m4.core.util.SessionUtil;
import com.m4.core.util.StrutsFormValidateUtil;
import com.m4.pawning.domain.ArticleModel;
import com.m4.pawning.domain.MapPawner;
import com.m4.pawning.domain.Pawner;
import com.m4.pawning.service.PawnerService;

public class PawnerAction extends MasterAction {
	private static final Logger logger = Logger.getLogger(PawnerAction.class);
	private PawnerService pawnerService;

	public void setPawnerService(PawnerService pawnerService) {
		this.pawnerService = pawnerService;
	}

	public ActionForward create(ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.debug("**** Enter in to create method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		if(!validateForm.isEmpty()){
			JSONArray errorArray = StrutsFormValidateUtil.getMessages(request, validateForm,messageResources,getLocale(request),null);
        	response.getWriter().write(errorArray.toString());
        }else{
        	Pawner pawner = new Pawner();
        	pawner.setCorporateIndividual(request.getParameter("comOrIndiv"));
        	pawner.setTitle(request.getParameter("title"));
        	pawner.setInitials(request.getParameter("initials"));
        	pawner.setInitialsInFull(request.getParameter("initialsInFull"));
        	pawner.setSurName(request.getParameter("surName"));
        	//pawner.setDateOfBirth(StrutsFormValidateUtil.parseDate(request.getParameter("")));
        	//pawner.setNationality(request.getParameter(""));
        	pawner.setMaritalStatus(Integer.parseInt(request.getParameter("maritalStatus")));
        	pawner.setIdOrBrNo(request.getParameter("idOrBrNo"));
        	pawner.setPassportNo(request.getParameter("pasportNo"));
        	pawner.setDrivingLicenseNumber(request.getParameter("drivingLicenseNumber"));
        	pawner.setHomeTelephoneNo(request.getParameter("homeTelephoneNo"));
        	pawner.setOfficeTelephoneNo(request.getParameter("officeTelephoneNo"));
        	pawner.setMobileNo(request.getParameter("mobileNo"));
        	pawner.setFaxNo(request.getParameter("faxNo"));
        	pawner.setEmailAddress(request.getParameter("emailAddress"));
        	pawner.setAddressLine1(request.getParameter("addressLine1"));
        	pawner.setAddressLine2(request.getParameter("addressLine2"));
        	pawner.setAddressLine3(request.getParameter("addressLine3"));
        	pawner.setAddressLine4(request.getParameter("addressLine4"));
        	//pawner.setIntroduceDate(StrutsFormValidateUtil.parseDate(request.getParameter("")));
        	pawner.setPawnerStatus(Integer.parseInt(request.getParameter("clientStatus")));
        	pawner.setSex(Integer.parseInt(request.getParameter("sex")));

        	String pawnerTypes = request.getParameter("clientTypes");
        	Collection<MapPawner> mapPawner = new ArrayList<MapPawner>();
        	if(pawnerTypes!=null && !pawnerTypes.equals("")){
        		String ids[] = pawnerTypes.split("<@>");
        		for(String id:ids){
        			mapPawner.add(new MapPawner(Integer.parseInt(id)));
        		}
        	}

        	pawner.setMapPawner(mapPawner);

        	try {
        		pawnerService.createPawner(SessionUtil.getUserSession(request), pawner);
        		response.getWriter().write(StrutsFormValidateUtil.getMessageCreateSuccess(messageResources).toString());
			} catch (PawnException ex) {
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
			}
        }
		logger.debug("**** Leaving from create method *****");
		return null;
	}

	public ActionForward update(ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.debug("**** Enter in to update method *****");
		ActionMessages validateForm = form.validate(mapping,request);
		MessageResources messageResources = getResources(request,"message");

		if(!validateForm.isEmpty()){
			JSONArray errorArray = StrutsFormValidateUtil.getMessages(request, validateForm,messageResources,getLocale(request),null);
        	response.getWriter().write(errorArray.toString());
        }else{
        	Pawner pawner = new Pawner();
        	pawner.setCorporateIndividual(request.getParameter("comOrIndiv"));
        	pawner.setTitle(request.getParameter("title"));
        	pawner.setInitials(request.getParameter("initials"));
        	pawner.setInitialsInFull(request.getParameter("initialsInFull"));
        	pawner.setSurName(request.getParameter("surName"));
        	//pawner.setDateOfBirth(StrutsFormValidateUtil.parseDate(request.getParameter("")));
        	//pawner.setNationality(request.getParameter(""));
        	pawner.setMaritalStatus(Integer.parseInt(request.getParameter("maritalStatus")));
        	pawner.setIdOrBrNo(request.getParameter("idOrBrNo"));
        	pawner.setPassportNo(request.getParameter("pasportNo"));
        	pawner.setDrivingLicenseNumber(request.getParameter("drivingLicenseNumber"));
        	pawner.setHomeTelephoneNo(request.getParameter("homeTelephoneNo"));
        	pawner.setOfficeTelephoneNo(request.getParameter("officeTelephoneNo"));
        	pawner.setMobileNo(request.getParameter("mobileNo"));
        	pawner.setFaxNo(request.getParameter("faxNo"));
        	pawner.setEmailAddress(request.getParameter("emailAddress"));
        	pawner.setAddressLine1(request.getParameter("addressLine1"));
        	pawner.setAddressLine2(request.getParameter("addressLine2"));
        	pawner.setAddressLine3(request.getParameter("addressLine3"));
        	pawner.setAddressLine4(request.getParameter("addressLine4"));
        	//pawner.setIntroduceDate(StrutsFormValidateUtil.parseDate(request.getParameter("")));
        	pawner.setPawnerStatus(Integer.parseInt(request.getParameter("clientStatus")));
        	pawner.setSex(Integer.parseInt(request.getParameter("sex")));
        	pawner.setRecordId(Integer.parseInt(request.getParameter("recordId")));
        	pawner.setVersion(Integer.parseInt(request.getParameter("version")));
        	pawner.setPawnerCode(request.getParameter("pawnerCode"));

        	String pawnerTypes = request.getParameter("clientTypes");
        	Collection<MapPawner> mapPawner = new ArrayList<MapPawner>();
        	if(pawnerTypes!=null && !pawnerTypes.equals("")){
        		String ids[] = pawnerTypes.split("<@>");
        		for(String id:ids){
        			mapPawner.add(new MapPawner(Integer.parseInt(id)));
        		}
        	}

        	pawner.setMapPawner(mapPawner);

        	try {
        		pawnerService.updatePawner(SessionUtil.getUserSession(request), pawner);
        		response.getWriter().write(StrutsFormValidateUtil.getMessageUpdateSuccess(messageResources).toString());
			} catch (PawnException ex) {
				response.getWriter().write(StrutsFormValidateUtil.getErrorMessage(ex, messageResources, request.getLocale()).toString());
			}
        }
		logger.debug("**** Leaving from update method *****");
		return null;
	}

	public ActionForward getAjaxData(ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.debug("**** Enter in to getAjaxData method ****");
		String recordId=request.getParameter("recordId");

		JSONArray mainArray=new JSONArray();
		if(recordId!=null && recordId!="" && !recordId.equals("0")){
			Pawner pawner = pawnerService.getPawnerById(SessionUtil.getUserSession(request), Integer.parseInt(recordId));
			mainArray.put(pawner.getPawnerCode());
			mainArray.put(pawner.getPawnerName());
			mainArray.put(pawner.getIdOrBrNo());
			mainArray.put(pawner.getCorporateIndividual());
			mainArray.put(pawner.getTitle());
			mainArray.put(pawner.getInitials());
			mainArray.put(pawner.getSurName());
			mainArray.put(pawner.getInitialsInFull());
			mainArray.put(pawner.getMaritalStatus());
			mainArray.put(pawner.getSex());
			mainArray.put(pawner.getAddressLine1());
			mainArray.put(pawner.getAddressLine2());
			mainArray.put(pawner.getAddressLine3());
			mainArray.put(pawner.getAddressLine4());
			mainArray.put(pawner.getPassportNo());
			mainArray.put(pawner.getDrivingLicenseNumber());
			mainArray.put(pawner.getHomeTelephoneNo());
			mainArray.put(pawner.getMobileNo());
			mainArray.put(pawner.getOfficeTelephoneNo());
			mainArray.put(pawner.getFaxNo());
			mainArray.put(pawner.getEmailAddress());
			mainArray.put(pawner.getPawnerStatus());
			mainArray.put(pawner.getRecordId());
			mainArray.put(pawner.getVersion());

			JSONArray array = new JSONArray();
			for(MapPawner mapPawner:pawner.getMapPawner()){
				array.put(mapPawner.getPawnerType().getPawnerTypeId());
				array.put(mapPawner.getPawnerType().getDescription());
			}

			mainArray.put(array);
		}else{
			List<Pawner> pawnerList = (List<Pawner>)pawnerService.getAllPawner(SessionUtil.getUserSession(request), getQueryCriteriaListWithoutNumbers(request)).getDataList();
			for(Pawner pawner:pawnerList){
				JSONArray subArray=new JSONArray();
				subArray.put(pawner.getPawnerCode());
				subArray.put(pawner.getPawnerName());
				subArray.put(pawner.getIdOrBrNo());
				subArray.put(pawner.getCorporateIndividual());
				subArray.put(pawner.getTitle());
				subArray.put(pawner.getInitials());
				subArray.put(pawner.getSurName());
				subArray.put(pawner.getInitialsInFull());
				subArray.put(pawner.getMaritalStatus());
				subArray.put(pawner.getSex());
				subArray.put(pawner.getAddressLine1());
				subArray.put(pawner.getAddressLine2());
				subArray.put(pawner.getAddressLine3());
				subArray.put(pawner.getAddressLine4());
				subArray.put(pawner.getPassportNo());
				subArray.put(pawner.getDrivingLicenseNumber());
				subArray.put(pawner.getHomeTelephoneNo());
				subArray.put(pawner.getMobileNo());
				subArray.put(pawner.getOfficeTelephoneNo());
				subArray.put(pawner.getFaxNo());
				subArray.put(pawner.getEmailAddress());
				subArray.put(pawner.getPawnerStatus());
				subArray.put(pawner.getRecordId());
				subArray.put(pawner.getVersion());
				mainArray.put(subArray);
			}
		}

		response.getWriter().write(mainArray.toString());
		logger.debug("**** Leaving from getAjaxData method ****");
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

	public ActionForward getPawnerByNICOrBR(ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		MessageResources messageResources = getResources(request,"message");
    	Pawner pawner = null;
 		String code   = request.getParameter("code");
 		
 		try {
 			pawner = (Pawner)pawnerService.getPawnerByIdOrBr(SessionUtil.getUserSession(request),code);
 		}catch(PawnException ex){
			response.getWriter().write(StrutsFormValidateUtil.getAJAXErrorMessage(ex, messageResources, request.getLocale()));
			return null;
 		}
 		response.getWriter().write(StrutsFormValidateUtil.getAJAXReferenceData(pawner.getRecordId(),code));
		return null;
	}

	public ActionForward getPawnerSerchData(ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.debug("***** Enter in to getAjaxData method ****");
		String nicOrBrn = request.getParameter("nicOrBrn");
		String surname  = request.getParameter("surname");
		String town     = request.getParameter("town");
		String userType = request.getParameter("userType");

		StringBuilder sql = new StringBuilder();
//		String sql = "";

		if(nicOrBrn!=null && nicOrBrn!="")
			sql.append(" AND map.pawner.idOrBrNo LIKE '" + nicOrBrn + "%' ");
		if(surname!=null && surname!="")
			sql.append(" AND map.pawner.surName LIKE '" + surname + "%' ");
		if(town!=null && town!="")
			sql.append(" AND map.pawner.addressLine4 LIKE '" + town + "%' ");
		if(userType!=null && userType!="")
			sql.append(" AND map.pawnerType.code='" + userType + "' ");

		JSONArray mainArray=new JSONArray();
		List<Pawner> pawnerList = pawnerService.getAllPawnerBySQL(SessionUtil.getUserSession(request),sql.toString());
		for(Pawner pawner:pawnerList){
			JSONArray subArray=new JSONArray();
			subArray.put(pawner.getPawnerCode());
			subArray.put(pawner.getPawnerName());
			subArray.put(pawner.getIdOrBrNo());
			subArray.put(pawner.getCorporateIndividual());
			subArray.put(pawner.getTitle());
			subArray.put(pawner.getInitials());
			subArray.put(pawner.getSurName());
			subArray.put(pawner.getInitialsInFull());
			subArray.put(pawner.getMaritalStatus());
			subArray.put(pawner.getSex());
			subArray.put(pawner.getAddressLine1());
			subArray.put(pawner.getAddressLine2());
			subArray.put(pawner.getAddressLine3());
			subArray.put(pawner.getAddressLine4());
			subArray.put(pawner.getPassportNo());
			subArray.put(pawner.getDrivingLicenseNumber());
			subArray.put(pawner.getHomeTelephoneNo());
			subArray.put(pawner.getMobileNo());
			subArray.put(pawner.getOfficeTelephoneNo());
			subArray.put(pawner.getFaxNo());
			subArray.put(pawner.getEmailAddress());
			subArray.put(pawner.getPawnerStatus());
			subArray.put(pawner.getRecordId());
			subArray.put(pawner.getVersion());
			mainArray.put(subArray);
		}

		response.getWriter().write(mainArray.toString());
		logger.debug("**** Leaving from getAjaxData method ****");
		return null;
	}

	@Override
	protected ActionForward getAuthorizeData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return null;
	}
}
