function formatNumber(num,prefix){
	prefix = prefix || '';   num += '';
	var splitStr = num.split('.');
	var splitLeft = splitStr[0];   
	var splitRight = splitStr.length > 1 ? '.' + splitStr[1] : '';   
	var regx = /(\d+)(\d{3})/;   
	while (regx.test(splitLeft)) {      
		splitLeft = splitLeft.replace(regx, '$1' + ',' + '$2');   
	}   
	return prefix + splitLeft + splitRight;
}

function unformatNumber(num) {
  	return num.replace(/([^0-9\.\-])/g,'');
}

function $(id){
	return document.getElementById(id);
}

function clearDivision(id){
	$(id).innerHTML = "";
}

function upperCase(id){
	$(id).value = $(id).value.toUpperCase();	
}

function CurrentPage(strUrl){
    strUrl=strUrl.split("/");
    strUrl=strUrl[strUrl.length-1];
    return strUrl;
}

function setLinkToIFrame(srcStr){
	$('bodyframe').src = srcStr;
	$('productTr').style.display    = 'none';
	$('bodyiframeTr').style.display = '';
	$('pages').innerHTML 			= '';
	$('categoryPathTd').innerHTML   = '';
}

function confirmCommonSubmit(btnName,value){
   	try{
       var agree;            
       if (value==1)
           agree=confirm('Confirm to create the record ?');                    
       if (value==2)
           agree=confirm('Confirm to update the record ?');
       if (value==3)
           agree=confirm('Confirm to delete the record ?');
       if (value==5)
           agree=confirm('Confirm to upload the image ?');
           
       if (agree)
           return true ;
       else
           return false ;
    }
    catch(err){
	      txt="There was an error on this page.\n\n";
             txt+="Error description: " + err.description + "\n\n";
             txt+="Click OK to continue.\n\n";
             errorUrl=location.pathname +"|"+ err.name +"|"+ err.message+"\n";
             alert(txt);
	}
}


var rightmessage="Right-mouse click is not allowed";
function click(e){		
    if (document.all){    	
        if (event.button==2||event.button==3){
            //alert(rightmessage);
            return false;
        }
    }else{
        if (e.button==2||e.button==3){
            e.preventDefault();
            e.stopPropagation();
            //alert(rightmessage);
            return false;
        }
    }
    
    
    if(e.keyCode==116){    	
	   	// Standard DOM (Mozilla):	
	   	if (e.preventDefault) e.preventDefault();	
	   	//IE (exclude Opera with !event.preventDefault):	
	   	/*if (document.all && window.event && !event.preventDefault) {
			event.cancelBubble = true;	
	     	event.returnValue = false;	
	     	event.keyCode = 0;	
	   	}*/	
	   	return false;
	}
}

//if (document.all){ // for IE
//    document.onmousedown=click;    
//}else{ // for other browsers
    document.onclick=click;
    document.ondblclick=click;
//}
document.onkeydown=click;
window.history.forward(1);     