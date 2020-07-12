<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>www.pawning.lk</title>
		<script type="text/javascript" src="js/ajax.js"></script>
		<link rel="shortcut icon" href="images/home.gif"/>	
		<script type="text/javascript" src="js/common.js"></script>	
	
		<script>
			var popup = '';
			function $(id){return document.getElementById(id);}			
			function logme(){	
				data = "dispatch=logme&company=" + $('company').value + "&userName=" + $('userName').value + "&password=" + $('password').value;
				var mySearchRequest = new ajaxObject('loginProcess.do');
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {				
					if (responseStatus==200) {						
						var message =  eval('(' + responseText + ')');
						if(message['error']){
							document.getElementById('errorTD').innerHTML = message['error'];
						}else if(message['success']){
							document.getElementById('loginDIV').style.display = "none"	
							//document.getElementById('loginOUTDIV').style.display = "";								
							popup = window.open('loginProcess.do?dispatch=setUp','pawn','width='+(window.screen.width-5)+',height='+(window.screen.height-30)+',location=no,scrollbars=auto,resizable=no,top=0,left=0');
							popup.focus();
						}else if(message['warn']){
							alert(message['warn']);
							document.getElementById('loginDIV').style.display = "none"	
							//document.getElementById('loginOUTDIV').style.display = "";								
							popup = window.open('loginProcess.do?dispatch=setUp','pawn','width='+(window.screen.width-5)+',height='+(window.screen.height-30)+',location=no,scrollbars=auto,resizable=no,top=0,left=0');
							popup.focus();
						}	
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request '); 
				    }
				}
				mySearchRequest.update(data,'POST');
				$('company').value  = '';
				$('password').value = '';											
			}
			
			function logOut(){	
				var data = "dispatch=logOut";				
				var mySearchRequest = new ajaxObject('loginProcess.do');
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {				
					if (responseStatus==200) {						
						var message =  eval('(' + responseText + ')');
						if(message['success']){
							document.getElementById('loginDIV').style.display    = "";		
							//document.getElementById('loginOUTDIV').style.display = "none";							
							if(popup!=null){
								popup.close();
							}
						}	
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request'); 
				    }
				}
				mySearchRequest.update(data,'POST');											
			}
			
			function getCompanies(){
				data = "dispatch=getLoginCompanies";
				var mySearchRequest = new ajaxObject('companyService.do');
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {				
					if (responseStatus==200) {						
						var message =  eval('(' + responseText + ')');
						if(message['error']){
							//alert(message['error']);
						}else{									
							for(var i=0;i<message.length;i++) { 					            
				                document.getElementById('company').options[document.getElementById('company').options.length] = new Option(message[i][0],message[i][1]);					             
					        }					
						}	
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request'); 
				    }
				}
				mySearchRequest.update(data,'POST');
			}
		</script>
	</head>
	
	<body onload="getCompanies();" bgcolor="#FFFFBA">
		<table height="575px" cellspacing="0" cellpadding="0" align="center" width="1000px" border="0" id="Table1" style="background-image: url('images/login.png');background-repeat: no-repeat;">
			
		</table>
		<div style="position: absolute;left: 400px;top: 300px" id="loginDIV">			
			<table width="300px">
				<tr>
					<td align="right" width="25%">
						Company
					</td>
					<td>
						<select id="company" onchange="document.getElementById('errorTD').innerHTML='';">
						
						</select>
					</td>
				</tr>
				<tr>
					<td align="right" width="25%">
						User Name
					</td>
					<td>
						<input type="text" id="userName" name="userName" size="15" onfocus="document.getElementById('errorTD').innerHTML='';"/>
					</td>
				</tr>
				<tr>
					<td align="right" width="25%">
						Password
					</td>
					<td>
						<input type="password" id="password" name="password" size="15" onfocus="document.getElementById('errorTD').innerHTML='';"/>
					</td>
				</tr>
				
				<tr>
					<td align="right" width="25%">
						
					</td>
					<td>
						<input type="button" value="Login" onclick="document.getElementById('errorTD').innerHTML='';logme()"/>
					</td>
				</tr>
				
				<tr height="10px"></tr>
				<tr>
					<td align="center" colspan="2" id="errorTD" style="color: red" width="100%">
						
					</td>					
				</tr>
			</table>
		</div>
		
		<div style="position: absolute;left: 400px;top: 300px;display: none;" id="loginOUTDIV">			
			<table width="300px">
				<tr>
					<td width="100%" align="center">
						<input type="button" value="Logout" onclick="logOut()"/>
					</td>
				</tr>
			</table>
		</div>
	</body>
	
	<script>
		var isLogin = '<c:out value='${sessionScope["LOGIN_KEY"].userId}'/>';
		if(isLogin*1>0){
			document.getElementById('loginDIV').style.display = "none"	
			//document.getElementById('loginOUTDIV').style.display = "";		
		}
	</script>
</html>
