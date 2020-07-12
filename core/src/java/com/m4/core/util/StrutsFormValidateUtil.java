package com.m4.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.m4.core.exception.PawnException;

public class StrutsFormValidateUtil {

    public static JSONArray getMessages(HttpServletRequest request,ActionMessages actionMessages,MessageResources resources,Locale userLocale,String property){
        StringBuilder messageText = new StringBuilder();
        String localProperty = null;
        Iterator reports = null;
        ActionMessage report = null;

    	JSONObject message 	= null;
        JSONArray messages	= new JSONArray();

        if (property==null){//Messages for all properties
        	Iterator properties = actionMessages.properties();
        	while(properties.hasNext()){//Iterate for all properties
        		localProperty = (String)properties.next();
        		reports = actionMessages.get(localProperty);
        		while (reports.hasNext()) {//Iterate for all messages of property
                	report = (ActionMessage) reports.next();
                	messageText.append(getMessageText(report, resources, userLocale));//Get text message
        		}
        		message = new JSONObject();
        		try{
        			message.put(localProperty,messageText);
        			messageText = new StringBuilder();
        		}catch(JSONException jex){}
        		messages.put(message);
        	}
        }else{//Messages for given properties
        	reports = actionMessages.get(property);
        	while (reports.hasNext()) {
            	report = (ActionMessage) reports.next();
            	messageText.append(getMessageText(report, resources, userLocale));//Get text message
        	}
    		message = new JSONObject();
    		try{
    			message.put(property,messageText);
    		}catch(JSONException jex){}
    		messages.put(message);
        }
        return messages;

    }
    public static JSONObject getErrorMessage(PawnException ex,MessageResources messageResources,Locale userLocale){
    	JSONObject errorObject = new JSONObject();
    	try{
    		errorObject.put("error",messageResources.getMessage(userLocale,ex.getExceptionCode())==null?ex.getExceptionCode():messageResources.getMessage(userLocale,ex.getExceptionCode()));
    	}catch(JSONException jex){}

    	return errorObject;
    }

    public static String getAJAXErrorMessage(PawnException ex,MessageResources messageResources,Locale userLocale){
    	JSONObject message = new JSONObject();
    	try{
    		message.put("error",messageResources.getMessage(userLocale,ex.getExceptionCode())==null?ex.getExceptionCode():messageResources.getMessage(userLocale,ex.getExceptionCode()));
        	message.put("id", "");
        	message.put("description", "");
    	}catch(JSONException jex){

    	}
    	return message.toString();
    }
    public static String getAJAXReferenceData(int id,String description){
    	JSONObject message	= new JSONObject();
        try{
        	message.put("error","");
        	message.put("id", id);
        	message.put("description", description);
        }catch (JSONException e) {

		}
        return message.toString();
    }


    public static JSONObject getMessageCreateSuccess(MessageResources messageResources){
    	JSONObject messageObject = new JSONObject();
    	try{
    		messageObject.put("success",messageResources.getMessage("msg.createsuccess"));
    	}catch(JSONException jex){}

    	return messageObject;
    }

    public static JSONObject getMessageUpdateSuccess(MessageResources messageResources){
    	JSONObject messageObject = new JSONObject();
    	try{
    		messageObject.put("success",messageResources.getMessage("msg.updatesuccess"));
    	}catch(JSONException jex){}

    	return messageObject;
    }

    public static JSONObject getMessageDeleteSuccess(MessageResources messageResources){
    	JSONObject messageObject = new JSONObject();
    	try{
    		messageObject.put("success",messageResources.getMessage("msg.deletesuccess"));
    	}catch(JSONException jex){}

    	return messageObject;
    }

    public static JSONObject getMessageNotFound(MessageResources messageResources){
    	JSONObject messageObject = new JSONObject();
    	try{
    		messageObject.put("error",messageResources.getMessage("errors.notfound"));
    	}catch(JSONException jex){}

    	return messageObject;
    }

    private static String getMessageText(ActionMessage message,MessageResources resources,Locale userLocale){
    	String messageText=null;
    	if (message.isResource()) {
    		if (message.getValues()==null){
    			messageText = resources.getMessage(userLocale, message.getKey()); //Message
            }else{
            	messageText = resources.getMessage(userLocale, message.getKey(),message.getValues()); //Message with values
            }
    	}else{
    		messageText = message.getKey(); //Message is not found in the resource file
        }
        return messageText;
    }

    public static JSONObject getMessageAuthorizeSuccess(MessageResources messageResources){
    	JSONObject messageObject = new JSONObject();
    	try{
    		messageObject.put("authorizeSuccess",messageResources.getMessage("msg.authorized"));
    	}catch(JSONException jex){}

    	return messageObject;
    }

    public static JSONObject getMessageRejectSuccess(MessageResources messageResources){
    	JSONObject messageObject = new JSONObject();
    	try{
    		messageObject.put("authorizeSuccess",messageResources.getMessage("msg.reject"));
    	}catch(JSONException jex){}

    	return messageObject;
    }
    
    public static JSONObject getMessageCancelSuccess(MessageResources messageResources){
    	JSONObject messageObject = new JSONObject();
    	try{
    		messageObject.put("success",messageResources.getMessage("msg.cancel"));
    	}catch(JSONException jex){}

    	return messageObject;
    }

    public static Date parseDate(String date) {
		try {
			SimpleDateFormat simpdate=new SimpleDateFormat("dd/MM/yyyy");
			return simpdate.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String parseString(Date date) {
		try {
			SimpleDateFormat simpdate=new SimpleDateFormat("dd/MM/yyyy");
			return simpdate.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    public static JSONObject getMessageApproveSuccess(MessageResources messageResources){
    	JSONObject messageObject = new JSONObject();
    	try{
    		messageObject.put("approveSuccess",messageResources.getMessage("msg.approvesuccess"));
    	}catch(JSONException jex){}

    	return messageObject;
    }
	
}
