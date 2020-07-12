<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<link rel="StyleSheet" type="text/css" href="css/commonCSS.css"></link>
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript" src="js/ajax.js"></script>
		
		<script>
			var ref = window.parent.frames['mainbody'];
			var action = 1;		
			var clicked ='1';
		    myUrl = ref.url	
			
			function prosessForm(){
				
		    	var processButton = ''	
		    	processButton = $('Check');
		    	processButton.disabled=true;
				processButton.id = 'Processing';
				processButton.value = '<bean:message bundle="button" key="button.processsing"/>';				
			}
			
			function resetProcessBtn(){
				processButton = $('Processing');
		       	processButton.disabled=false;		       	
		    	processButton.id = 'Check';
		    	processButton.value = '<bean:message bundle="button" key="button.submit"/>' ; 		    	
		    }
		    
		    // reset authorize button 	
		    function resetAuthorizeBtn(){
		       	processButton.disabled=false;
		        processButton.id = 'AddNew';
			    processButton.value = '<bean:message bundle="button" key="button.authorize"/>' ;
		    }
		     //reset Reject Button
		    function resetRejectBtn(){
		    	processButton.disabled=false;
		        processButton.id = 'Exit';
		  		processButton.value = '<bean:message bundle="button" key="button.reject"/>' ;
		     	
		    }
		    
		    function getCheckedValue(radioObj) {
		    	if(!radioObj)
			    	return "";
		        var radioLength = radioObj.length;
		        if(radioLength == undefined)
			    	if(radioObj.checked)
				    	return radioObj.value;
			        else
				        return "";
		        for(var i = 0; i < radioLength; i++) {
			    	if(radioObj[i].checked) {
				        return radioObj[i].value;
			        }
		        }
		        return "";
	    	}
	    	
	    	function getRadioBtnValue(){
	    		return getCheckedValue(document.forms[0].elements['toolBtn']);
	    	}
		    
		    function submitForm(btnObj){	
		    	radioValue = getCheckedValue(document.forms[0].elements['toolBtn']);
		    	clickedBtn = btnObj.value;
		    	ref.$('screenCont').className = 'disableAll';
		    	
		    	if(confirmCommonSubmit(radioValue,btnObj)){	
		    		prosessForm();			    	
			    	submitData(radioValue,clickedBtn);
			    	resetProcessBtn();				    	
			    }	
			    ref.$('screenCont').className = 'enableAll';
		    }
	     	
	     	function RTrim( value ) {
				var re = /((\s*\S+)*)\s*/;
				return value.replace(re, "$1");			
			}
	     	
	     	function submitData(action,clickedBtn){	
	     		    		
	     		//if(confirmCommonSubmit()){
	     			var booleanVal = RTrim(clickedBtn)=='Authorize'?'true':'false';	     			
					var data = '';
					
					switch(action){
						case "1":
								data = "dispatch=create" + ref.getCreateData();
								break;
						case "2":
								data = "dispatch=update" + ref.getChangedData();
								break;
						case "3":
								data = "dispatch=remove" + ref.getChangedData();
								break;	
						case "4":
								data = "dispatch=authorize" + ref.getAuthorizeData() + "&authorizeValue=" + booleanVal;
								break;	
						case "5":								
								data = "dispatch=approve" + ref.getApproveData() + "&authorizeValue=" + booleanVal;								
								break;																			
					}
					
					var mySearchRequest = new ajaxObject(myUrl);
					mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
						if (responseStatus==200) {												
							var message =  eval('(' + responseText + ')');
							
							if(message['error']){
				       			alert(message['error']);
				       		}else if(message['success']){			       			
				       			alert(message['success']);
				       			ref.clearAll();				   
				       			ref.getGridData();    						       									
				       		}else if(message['authorizeSuccess']){			       			
				       			alert(message['authorizeSuccess']);
				       			ref.clearAll();				   
				       			ref.getAuthorizeGridData();
				       		}else if(message['approveSuccess']){			       			
				       			alert(message['approveSuccess']);
				       			ref.clearAll();				   
				       			ref.getApproveGridData();
				       		}else{				       			
				       			ref.showValidationErrors(message);
				       		}
				       		
						}else {
				    	    alert(responseStatus + ' -- Error Processing Request'); 
					    }
					}
					mySearchRequest.update(data,'POST');
				//}	
			}
	     	
     	    function confirmCommonSubmit(radioValue,btnObj){
     	    	var agree;
     	    	if (radioValue==1)
     	    		agree=confirm('<bean:message bundle="message" key="msg.createconfirm"/>');
   	    		if (radioValue==2)
   	    			agree=confirm('<bean:message bundle="message" key="msg.updateconfirm"/>');
   	    		if (radioValue==3)
   	    			agree = confirm('<bean:message bundle="message" key="msg.deleteconfirm"/>');
   	    		if(radioValue==4 || radioValue==5){
	   	    		if (radioValue==4){
	   	    			agree=confirm('<bean:message bundle="message" key="msg.authorizeconfirm"/>');
	   	    		}else if (radioValue==5){
	   	    			agree=confirm('<bean:message bundle="message" key="msg.approveconfirm"/>');	
		        	}else{
		        		agree=confirm('<bean:message bundle="message" key="msg.rejectconfirm"/>');	 
		        	}
		        }              
			       
				if (agree)
				    return true ;
				else
					return false ;
			    //}
			    /*catch(err){
				      txt="There was an error on this page.\n\n";
		              txt+="Error description: " + err.description + "\n\n";
		              txt+="Click OK to continue.\n\n";
		              errorUrl=location.pathname +"|"+ err.name +"|"+ err.message+"\n";
		              alert(txt);
				}*/
		    }
	     	
	     	function tdChecked(radioObj){	     		
	    		var radioBtnValue = radioObj.value;
	    	
	    	 	if(radioObj.checked == false) 
	    	 		radioObj.checked = true;
	    	 	if(radioBtnValue == 1 || radioBtnValue == 2 || radioBtnValue == 3){	
	    	 		document.getElementById('buttonDiv').innerHTML="<input type='button' value='<bean:message bundle="button" key="button.submit"/> ' class='buttonNormal' id='Check' onclick='submitForm(this,toolBtn);'/>";	   	    	
		   	    	if( radioBtnValue == 1 && clicked !='1' ){
		   	    		open(myUrl+"?dispatch=createSetup", "mainbody");
		   	    		clicked ='1';			   	    		
			   	    }else if( radioBtnValue == 2 && clicked !='2'){
			   	    	open(myUrl+"?dispatch=updateSetup", "mainbody");
			   	    	clicked ='2';				   	    	
			   	    }else if( radioBtnValue == 3 && clicked !='3'){
			   	    	open(myUrl+"?dispatch=deleteSetup", "mainbody");
			   	    	clicked = '3';				   	    	
			   	    }	   	    
	    	 	}else if(radioBtnValue == 4 && clicked !='4'){	 
	    	 		document.getElementById('buttonDiv').innerHTML="<nobr><input type='button' value='<bean:message bundle="button" key="button.authorize"/> ' class='buttonNormal' id='Check' onclick='submitForm(this);'/> <input type='button' value='<bean:message bundle="button" key="button.reject"/> ' class='buttonNormal' id='Reject' onclick='submitForm(this);'/></nobr>";
	    	 		open(myUrl+"?dispatch=authorizeSetup", "mainbody");
		    	 	clicked = '4';	    		 	   	 	
	    	 	}else if(radioBtnValue == 5 && clicked !='5'){
	    	 		document.getElementById('buttonDiv').innerHTML="<input type='button' value='<bean:message bundle="button" key="button.approve"/> ' class='buttonNormal' id='Check' onclick='submitForm(this);'/>";
	    	 		//document.getElementById('buttonDiv').innerHTML="<nobr><input type='button' value='<bean:message bundle="button" key="button.approve"/> ' class='buttonNormal' id='Check' onclick='submitForm(this);'/> <input type='button' value='<bean:message bundle="button" key="button.reject"/> ' class='buttonNormal' id='Reject' onclick='submitForm(this);'/></nobr>";
	    			open(myUrl+"?dispatch=approveSetup", "mainbody");
	    			clicked = '5';	    			
	    	 	}
	     	}
		</script>
	</head>

	<body>
		<form action="toolBar.jsp">
			<table class="toolTable">
				<tr>						
					<c:set var="eventsChk" value="1"/>
		  			<c:set var="setClick" value="0"/>
					<c:set var="checked" value="checked='checked'"/>  			
					<c:forTokens delims=":" items="${param.evn}" var="event">
						<c:if test="${event=='1'}">
		        		  	<td width="5px"   class="cusorHand" onclick="tdChecked(document.getElementById('add'))">
				        		<input type="radio" value="1" ${checked}  id="add" name="toolBtn"/>
				           	</td>
							<td width="10px" onclick="tdChecked(document.getElementById('add'))" class="cusorHand">
							  <bean:message bundle="button" key="button.add"/>
							</td>
							<c:set var="setClick" value="1"/>	
							<c:set var="eventsChk" value="1"/>					
						</c:if>
						<c:if test="${event=='2'}">					
			        		<td width="5px"   class="cusorHand" onclick="tdChecked(document.getElementById('modify'))">
				        		<input type="radio" value="2" <c:if test="${setClick =='0'}"> ${checked} </c:if> name="toolBtn" id="modify"/> 
				         	</td>
							<td width="10px" onclick="tdChecked(document.getElementById('modify'))" class="cusorHand">
								<bean:message bundle="button" key="button.modify"/>
							</td>
							<c:set var="setClick" value="1"/>
							<c:set var="eventsChk" value="1"/>
						</c:if>
						<c:if test="${event=='3'}">
			        		<td width="5px" class="cusorHand" onclick="tdChecked(document.getElementById('delete'))">
				        		<input type="radio" value="3" <c:if test="${setClick =='0'}"> ${checked} </c:if> name="toolBtn" id="delete" /> 
				         	</td>
							<td width="10px" onclick="tdChecked(document.getElementById('delete'))" class="cusorHand">
								 <bean:message bundle="button" key="button.delete"/> 
							</td>
							<c:set var="setClick" value="1"/>
							<c:set var="eventsChk" value="1"/>
						</c:if>
						
						<c:if test="${event=='4'}">					
				        	<td width="5px"  class="cusorHand" onclick="tdChecked(document.getElementById('authorize'))">
				         		<input type="radio" value="4"  <c:if test="${setClick =='0'}"> ${checked} </c:if> name="toolBtn" id="authorize" />
				         	</td>
							<td width="10px" onclick="tdChecked(document.getElementById('authorize'))" class="cusorHand">
								<bean:message bundle="button" key="button.authorize"/>
							</td>
							<c:set var="eventsChk" value="2"/>
							<c:set var="setClick" value="1"/>
						</c:if>				
						
						<c:if test="${event=='5'}">					
				        	<td width="5px"   class="cusorHand" onclick="tdChecked(document.getElementById('approve'))">
			        			<input type="radio" value="5" <c:if test="${setClick =='0'}"> ${checked} </c:if> name="toolBtn" id="approve"/>
				           	</td>
							<td width="10px" onclick="tdChecked(document.getElementById('approve'))" class="cusorHand">
							  	<bean:message bundle="button" key="button.approve"/>
							</td>
						</c:if>	
					</c:forTokens>
																	
					<td width="*" align="right">
						<table width="100%">
							<tr>
								<td align="right">	
									<div id="buttonDiv">				
										<c:set var="buttonSubmit" value="1"/>
										<c:set var="buttonAuthorize" value="1"/>
										<c:forTokens delims=":" items="${param.evn}" var="event">
											<c:if test="${(event=='1'|| event=='2'||event=='3') && buttonSubmit=='1'}">					
												<input type="button" value="<bean:message bundle="button" key="button.submit"/>" id="Check" class="buttonNormal" onclick="submitForm(this,toolBtn);"/>
												<c:set var="buttonSubmit" value="2"/>						
											</c:if>
											<c:if test="${event=='4' && buttonAuthorize=='1' && buttonSubmit=='1'}">					
												<nobr>
													<input type='button' value='<bean:message bundle="button" key="button.authorize"/> ' class='buttonNormal' id='Authorize' onclick='submitForm(this,toolBtn);'/> 
													<input type='button' value='<bean:message bundle="button" key="button.reject"/> ' class='buttonNormal' id='Reject' onclick='submitForm(this,toolBtn);'/>
												</nobr>	
												<c:set var="buttonAuthorize" value="2"/>							
											</c:if>		
											<c:if test="${event=='5' && buttonAuthorize=='1' && buttonSubmit=='1'}">					
												<nobr>
													<input type='button' value='<bean:message bundle="button" key="button.approve"/> ' class='buttonNormal' id='Approve' onclick='submitForm(this,toolBtn);'/> 
													<input type='button' value='<bean:message bundle="button" key="button.reject"/> ' class='buttonNormal' id='Reject' onclick='submitForm(this,toolBtn);'/>
												</nobr>	
												<c:set var="buttonAuthorize" value="2"/>							
											</c:if>					
										</c:forTokens>
									</div>	
								</td>
								<td width="20px">
									<input type="button" value="<bean:message bundle="button" key="button.clear"/>" id="Clear" class="buttonNormal" onclick="ref.clearAll();"/>	
						      	</td>
							</tr>	
						</table>												
					</td>
				</tr>
			</table>
		</form>	
	</body>
</html>
