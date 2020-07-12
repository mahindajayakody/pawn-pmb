document.onkeypress = function(e){	
	if (e.target.nodeName.toUpperCase() != 'INPUT'){
		//alert(e.keyCode);
		return (e.keyCode != 8 || e.keyCode !=116);
	}	
}	

function CreateXHR() {
	var request = false;
    try {
    	request = new ActiveXObject('Msxml2.XMLHTTP');
	}catch (err2) {
    	try {
			request = new ActiveXObject('Microsoft.XMLHTTP');
      	}catch (err3) {
			try {
				request = new XMLHttpRequest();
			}catch (err1){
				request = false;
			}
		}
	}
    return request;
}
function clearScreen(id){
	window.parent.frames['mainbody'].document.getElementById(id).innerHTML = "";              	
}
function upperCase(id){
	window.parent.frames['mainbody'].document.getElementById(id).value = window.parent.frames['mainbody'].document.getElementById(id).value.toUpperCase();	
}
function ajaxObject(url, callbackFunction) {
	var that=this;      
  	this.updating = false;
  	this.abort = function() {
   		if (that.updating) {
      		that.updating=false;
      		that.AJAX.abort();
      		that.AJAX=null;
    	}
  	}
  	
	this.update = function(passData,postMethod) { 
    	if (that.updating) { 
    		return false;
    	}
    	that.AJAX = null;                          
   		if (window.XMLHttpRequest) {              
     			that.AJAX=new XMLHttpRequest();              
   		} else {                                  
     			that.AJAX=new ActiveXObject("Microsoft.XMLHTTP");
   		}                                             
   		if (that.AJAX==null) {                             
     			return false;                               
   		} else {
     		that.AJAX.onreadystatechange = function() {  
	       		if (that.AJAX.readyState==4) {             
         			that.updating=false;                
         			that.callback(that.AJAX.responseText,that.AJAX.status,that.AJAX.responseXML);        
         			that.AJAX=null;                                         
    	   		}                                                      
     		}
	   		that.updating = new Date();                              
			if (/post/i.test(postMethod)) {
	       		var uri=urlCall+'?'+that.updating.getTime();
		        that.AJAX.open("POST", uri, true);
	       		that.AJAX.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		        that.AJAX.setRequestHeader("Content-Length", passData.length);
		        that.AJAX.send(passData);
			} else {
				var uri=urlCall+'?'+passData+'&timestamp='+(that.updating.getTime()); 
			    that.AJAX.open("GET", uri, true);                             
		        that.AJAX.send(null);                                         
			}              
			return true;                                             
		}                                                                           
  	}
	var urlCall = url;        
  	this.callback = callbackFunction || function () { };
}

function commonSearch(action,field,code,description,dispatch,errordiv,func,addParm,errorFuntion) {
	if (document.getElementById(code).value!=''){ //if input is blank, igone the ajax call	
		if(addParm!=undefined && addParm!='')
			postData="dispatch="+dispatch+"&code="+escape(document.getElementById(code).value)+addParm;
		else 
			postData="dispatch="+dispatch+"&code="+escape(document.getElementById(code).value);
		var mySearchRequest = new ajaxObject(action);
		mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
			if (responseStatus==200) {
				var message =  eval('(' + responseText + ')');
				document.getElementById(errordiv).innerHTML = message.error;
				document.getElementById(field).value		= message.id;			
				document.getElementById(description).value  = message.description;
				if(message.error=='' && func!=undefined){
					func();
				}else if(errorFuntion!=undefined && errorFuntion!=null && message.error!=''){
					errorFuntion();
				}
			}else {
	    	    alert(responseStatus + ' -- Error Processing Request'); 
		    }
		}
		mySearchRequest.update(postData,'POST');
	}else{
		document.getElementById(description).value  = '';
		document.getElementById(field).value  = '0';
		if(errorFuntion!=undefined && errorFuntion!=null){
			errorFuntion();
		}
	}
}		