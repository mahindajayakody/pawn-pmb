
				function CreateXHR() 
				{
				    var request = false;
				        try {
				            request = new ActiveXObject('Msxml2.XMLHTTP');
				        }
				        catch (err2) {
				            try {
				                request = new ActiveXObject('Microsoft.XMLHTTP');
				            }
				            catch (err3) {
							try {
								request = new XMLHttpRequest();
							}
							catch (err1) 
							{
								request = false;
							}
						}
					}
				    return request;
				}

	
    		
var req;
var validateField = null; 
var myArray = null;
var obj1;
var obj3;
var preID;




function commonSearch(ID,opject1,opject2,opject3,PreviousID) {
  
    myArray = null;
    var url;
    var data;
    obj1=opject1;
    obj3=opject3;
    preID=PreviousID;
    var obj2 = document.getElementById(opject2).value;
    
    data = "ID="+ID+"&obj2="+obj2+"&preID="+preID; 
    
    url = "CommonSearchAction.do"; 
    req = CreateXHR();
    req.onreadystatechange = processRequestValidate;
    req.open("POST", url, true);
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); 
    req.send(data);
}


function processRequestValidate() {
	if (req.readyState == 4) {
	alert("ok")
   		if (req.status == 200) {
       		myArray = eval("("+req.responseText+")");
       		
			  for( var i =0; i < myArray.length ; i++){
			       if ( myArray[i]['Des'] ){
			            document.getElementById(obj3).value = myArray[i]['Des'];
			       }
			       else if( myArray[i]['Id'] ){
			  		    	document.getElementById(obj1).value = myArray[i]['Id'];
				   }
			   }               
   		}else{
   			alert('Internal error occurred. < Server has not responded ! > ');
   		}
	}else{
		//alert('Internal error occurred. < Server has not responded ! > ');
	}
}      		
    		
    		
    		
    		
    		
    		
    		
    		
    		
    		
    		
    		
    		
    		
    		
    		
    		
    		
    		
    		
    		
    		               
    