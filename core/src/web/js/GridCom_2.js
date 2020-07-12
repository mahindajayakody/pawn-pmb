// Grid component java script version 2
function createGrid(newObj,strObj,gridName, myData, myColumns, recordStatus,cellFormat,Records,initValue,url) {
   	totalRecords = Records;
	initialNumberOfRecords = initValue;
	
	try{
		newObj.setId(gridName);
		    //	define data formats
		newObj.setCellFormat(cellFormat);
	       
	        //	provide cells and headers text
		//newObj.setCellText(myData);
		newObj.setCellData(myData);
		newObj.setHeaderText(myColumns);
	       
	        //	set number of rows/columns
		if (window.showModalDialog) {
			newObj.setRowCount(myData.length - 1);
		} else {
			newObj.setRowCount(myData.length);
		}
		newObj.setColumnCount(myColumns.length);
	        //	enable row selectors
		newObj.setSelectorVisible(true);
		newObj.setSelectorText(function (i) {
			return this.getRowPosition(i) + 1;
		});
	
	        //	set headers width/height
		newObj.setSelectorWidth(40);
		//newObj.setHeaderHeight(20);
	
	        //	set row selection
		newObj.setSelectionMode("single-row");
	        
	    
	        
	    newObj.reset=function(){
   		   temEvnt=newObj.onSelectedRowsChanged;
   		   newObj.onSelectedRowsChanged =function(){};
 		   newObj.setSelectedRows([]);
 		   newObj.clearScrollModel(); 
		   newObj.clearSortModel(); 
		   document.getElementById("searchField_"+gridName).selectedIndex = 0;
	       document.getElementById("searchValue_"+gridName).value= "";
	       document.getElementById("all_"+gridName).checked = false;
	       document.getElementById("first_"+gridName).onclick();
	       newObj.onSelectedRowsChanged =temEvnt; 
		}    
		
		newObj.clearGrid=function (){ 
		    newObj.clearCellModel(); 
		    newObj.clearRowModel(); 
		    newObj.clearScrollModel(); 
		    newObj.clearSelectionModel(); 
		    newObj.clearSortModel(); 
		    newObj.refresh(); 
		}
		
		 newObj.deleteSelectedRow=function(){
		 	temEvn=newObj.onSelectedRowsChanged;
		 	newObj.onSelectedRowsChanged =function(){};
		 	newObj.deleteRow(newObj.getCurrentRow()); 
		    newObj.setSelectedRows([]);
		    newObj.onSelectedRowsChanged =temEvn;
		    
		     if(newObj.getRowCount()==0){
		    	if(document.getElementById("pageId_"+gridName).value==document.getElementById("pageId_"+gridName).length){
		    		document.getElementById("pageId_"+gridName).value=(document.getElementById("pageId_"+gridName).value-1)
		    	}
			    document.getElementById("pageId_"+gridName).onchange()
			}
			
			var range = window.parent.frames['commonSource'].RTrim(document.getElementById("range_"+gridName).value).split("-");
			document.getElementById("totalRecords_"+gridName).value = document.getElementById("totalRecords_"+gridName).value - 1;
			if(range[1] ==1 )
				document.getElementById("range_"+gridName).value = 0 + " - " + 0;
			else
				document.getElementById("range_"+gridName).value =(range[0]) + "- " + (range[1] - 1);
				
			newObj.refresh(); 

		}
		
		newObj.disabled = function() { 
		    if (! newObj._maskEl) { 
		        var maskEl = document.createElement('div'); 
		        document.body.appendChild(maskEl); 
		        maskEl.className = 'aw-grid-mask'; 
		        var gridEl = newObj.element(); 
		        maskEl.style.left = AW.getLeft(gridEl) + 'px'; 
		        maskEl.style.top = AW.getTop(gridEl) + 'px'; 
		        maskEl.style.width =  gridEl.clientWidth + 'px'; 
		        maskEl.style.height =  gridEl.clientHeight + 'px'; 
		        newObj._maskEl = maskEl; 
		        } 
		    newObj._maskEl.style.display = 'block'; 
		    document.getElementById("searchField_"+gridName).disabled = true;
	        document.getElementById("searchValue_"+gridName).disabled = true;
	        document.getElementById("Search_"+gridName).disabled = true;
		}; 
		
		
		newObj.enabled= function() { 
		    if (newObj._maskEl) { 
		        newObj._maskEl.style.display = 'none'; 
		        document.getElementById("searchField_"+gridName).disabled = false;
		        document.getElementById("searchValue_"+gridName).disabled = false;
		        document.getElementById("Search_"+gridName).disabled = false;
		    } 
		} 
		
		newObj.loadingCompleted= function() { 
		    if (newObj._maskLoading) { 
		       newObj._maskLoading.style.display = 'none'; 
	           if (! newObj._maskEl || newObj._maskEl==undefined || newObj._maskEl.style.display != 'block') { 
			        document.getElementById("searchField_"+gridName).disabled = false;
			        document.getElementById("searchValue_"+gridName).disabled = false;
			        document.getElementById("Search_"+gridName).disabled = false;
			    }
		    } 
		} 
		
		newObj.loading = function() { 
		    if (! newObj._maskLoading) { 
		        var maskLoading = document.createElement('div'); 
		        document.body.appendChild(maskLoading); 
		        maskLoading.className = 'aw-grid-loading'; 
		        var gridEl = newObj.element(); 
		        maskLoading.style.left = AW.getLeft(gridEl) + 'px'; 
		        maskLoading.style.top = AW.getTop(gridEl) + 'px'; 
		        maskLoading.style.width =  gridEl.clientWidth + 'px'; 
		        maskLoading.style.height =  gridEl.clientHeight + 'px'; 
		        newObj._maskLoading = maskLoading; 
		        } 
		        newObj.clearCellModel(); 
			    newObj.clearRowModel(); 
			    newObj.clearScrollModel(); 
			    newObj.clearSelectionModel(); 
			    newObj.clearSortModel(); 
			    newObj.refresh(); 
		    	newObj._maskLoading.style.display = 'block'; 
		    	if (! newObj._maskEl || newObj._maskEl==undefined ||  newObj._maskEl.style.display != 'block') { 
			    	document.getElementById("searchField_"+gridName).disabled = true;
			        document.getElementById("searchValue_"+gridName).disabled = true;
			        document.getElementById("Search_"+gridName).disabled = true;
			    }
		} 
	  }
	  catch(err){
		      txt="There was an error on this page.\n\n";
              txt+="Error description: " + err.description + "\n\n";
              txt+="Click OK to continue.\n\n";
              errorUrl=location.pathname +"|"+ err.name +"|"+ err.message+"\n";
              alert(txt);
      }

if (window.showModalDialog) {
	displayRowNum =myData.length - 1
} else {
	displayRowNum=myData.length;
}
if(displayRowNum ==0)
	dispRange1 = 0;
else 
	dispRange1 = 1;
		
		
var gridHead1 = "<fieldset style=\"width:1px;\"><legend>" + window.parent.frames["commonSource"].gridLegend + "</legend>" + "<table  border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" >" + "<tr><td><table cellspacing=\"1\" cellpadding=\"0\"><tbody><tr><td><select class=\"GridColumn\" onchange=\"serverValueCls(this,'"+gridName+"')\" id=\"searchField_" + gridName + "\">";

function gridValueSetSearch() {
	var searchBox = "<option value='0'>" + window.parent.frames["commonSource"].gridSelect + "</option>";
	for (var j = 0; j < myColumns.length; j++) {
		searchBox = searchBox + "<option value=" + (j + 1) + ">" + myColumns[j] + "</option>";
	}
	return searchBox;
}

var gridHead2 = "</select></td><td><input type=\"hidden\" value=\"1\" name=\"recordStatus_" + gridName + "\" id=\"recordStatus_" + gridName + "\" size=1/><input type=\"hidden\" value=\"1\" name=\"additionalParams_" + gridName + "\" id=\"additionalParams_" + gridName + "\" size=1/><input type=\"text\" class=\"GridColumn\" id=\"searchValue_" + gridName + "\"></td><td valign=\"center\"><input type=\"button\" class=\"searchButton\"  id=\"Search_"+ gridName + "\" onclick =\"gridNext("+strObj+",'search','"+url+"',this,"+totalRecords+","+initialNumberOfRecords+","+recordStatus+",'"+gridName+"',"+recordStatus+")\" value=" + window.parent.frames["commonSource"].searchBtn + " \></td><td></td><td></td></tr></tbody></table></td>" + "</tr><tr height=\"2px\"></tr>\t<tr><td colspan='6'>";
var gridFooter1 = "</td> </tr><tr><td><table><tbody> <tr class=\"GridNavigator\"><td  valign=\"middle\" width=\"250px\"> <input   type=\"text\"  class=\"searchTextbox\" size=\"14\"  readonly=\"true\" value=\" " + window.parent.frames["commonSource"].totalRecords + " \"><input   type=\"text\"  class=\"searchTextbox\" size=\"15\" id=\"totalRecords_" + gridName+"\" readonly=\"true\" value=\" ";

if (window.showModalDialog)
	var gridExcel = "<td width=\"90px\"><img src=\"images\\excel_icon.jpg\" onclick=\"ToExcel("+strObj+")\" style=\" cursor: hand;\"></td>";
    else 
		var gridExcel = "<td width=\"90px\">&nbsp;</td>";

var gridFooter2 = " \"></td>" + gridExcel + " <td  width=\"120px\"><nobr> <input   type=\"text\"  class=\"searchTextbox\" size=\"10\"  readonly=\"true\" value=\" " + window.parent.frames["commonSource"].display + " \"><input   type=\"text\"  class=\"searchTextbox\" size=\"15\" id=\"range_" + gridName + "\" readonly=\"true\" value=\"";
var gridFooter3 = "\"></nobr></td> <td align=\"left\"><nobr>" + "<input  type=\"Button\" name=\"First\" class=\"first_d\" id=\"first_" + gridName+ "\"  onclick =\"gridNext("+strObj+",'first','"+url+"',this,"+totalRecords+","+initialNumberOfRecords+","+recordStatus+",'"+gridName+"')\"/>" + "<input  type=\"Button\" name=\"Previous\" class=\"previous_d\"  id=\"previous_" + gridName + "\" onclick =\"gridNext("+strObj+",'previous','"+url+"',this,"+totalRecords+","+initialNumberOfRecords+","+recordStatus+",'"+gridName+"')\"/>" + " <select  name=\"page\" class=\"GridSelect\" onchange=\"gridNext("+strObj+",'page','"+url+"',this,"+totalRecords+","+initialNumberOfRecords+","+recordStatus+",'"+gridName+"')\" id=\"pageId_"+ gridName +"\">";

function numOfPagesCal() {
	calNumOfPages();
	var pages;
	for (var i = 1; i <= numOfPage; i++) {
		pages = pages + "<option value=" + i + ">" + i + "</option>";
	}
	return pages;
}


var gridFooter4 = "</select> <input class=\"next\" type=\"Button\" name=\"Next\" id=\"next_"+gridName +"\"  onclick =\"gridNext("+strObj+",'next','"+url+"',this,"+totalRecords+","+initialNumberOfRecords+","+recordStatus+",'"+gridName+"')\"/>" + "<input class=\"last\" type=\"Button\"  name=\"Last\" id=\"last_" + gridName+"\" onclick =\"gridNext("+strObj+",'last','"+url+"',this,"+totalRecords+","+initialNumberOfRecords+","+recordStatus+",'"+gridName+"')\"/>" + "</nobr> </td><td align=\"right\" valign=\"middle\" style=\"width:100px\">" + "<nobr>" + window.parent.frames["commonSource"].gridAll + "<input class=\"GridNavigator\" type=\"checkbox\"  value=\"all\"  id=\"all_" + gridName +"\"  onclick =\"gridNext("+strObj+",'all','"+url+"',this,"+totalRecords+","+initialNumberOfRecords+","+recordStatus+",'"+gridName+"')\" /></nobr></td></tr></tbody></table>";
var gridFooter5 = "</td></tr></table></fieldset>";
var numOfPage;
function calNumOfPages() {
  numOfPage = Math.ceil(totalRecords / initValue);
}
document.write(gridHead1 + gridValueSetSearch() + gridHead2 + newObj + gridFooter1 + totalRecords + gridFooter2 + dispRange1 + "&nbsp;-&nbsp;" + displayRowNum + gridFooter3 + numOfPagesCal() + gridFooter4 + gridFooter5);
btnDisableInitial(gridName,numOfPage);
document.getElementById("recordStatus_"+gridName).value=recordStatus;

}

function btnDisableInitial(gridName,numOfPage){
	if( numOfPage <= 1)
		disabledAll(gridName);	
	else{
		disabledLeft(gridName);
		enabledRight(gridName);
	}
}


function gridNext(gridObjName,strValue,gridURL,clickObj,toRec,iniNumRec,recSta,gridName,newRecSta,additionalParams) {
try{
clearAll();
}catch(err){}

if(clickObj.id==undefined){
	document.getElementById("recordStatus_"+gridName).value=newRecSta;
	document.getElementById("searchField_"+gridName).value=0;
	document.getElementById("searchValue_"+gridName).value="";
	//alert(clickObj.id)
}else{
	checkId=clickObj.id
	checkId=checkId.split("_");
	if(checkId.length >=1){
		if(checkId[1]!=gridName){
//				alert(checkId[1])
			document.getElementById("recordStatus_"+gridName).value=newRecSta;
			document.getElementById("searchField_"+gridName).value=0;
			document.getElementById("searchValue_"+gridName).value="";
		}
	}
}
	
	//if(document.getElementById("recordStatus_"+gridName).value!=newRecSta && newRecSta!=undefined){
	//	document.getElementById("recordStatus_"+gridName).value=newRecSta;
	//	document.getElementById("searchField_"+gridName).value=0;
	//	document.getElementById("searchValue_"+gridName).value="";
	//}

	recordStatus=document.getElementById("recordStatus_"+gridName).value;
	

	if(additionalParams!="" && additionalParams!=undefined){
		document.getElementById("additionalParams_"+gridName).value=additionalParams;
	}
	additionalParams=document.getElementById("additionalParams_"+gridName).value;
	
	

	
	var searchField = document.getElementById("searchField_"+gridName).value;
	var searchValue = document.getElementById("searchValue_"+gridName).value;
	
	//enablededAll(gridName)
	nextVal=document.getElementById("pageId_"+gridName).value;
	
	if (strValue == 'all') { 
	    if (clickObj.checked == true) {
			initVal = 0;
			Reclength  = -1;
		}else{
			initVal = 0;
			Reclength  = iniNumRec;
			
		}
	}else if(strValue == 'next') { 
		initVal = (nextVal*iniNumRec);
		Reclength  = iniNumRec;
	}else if (strValue == 'previous') {
		initVal = (initVal-iniNumRec);
		Reclength  = iniNumRec;
	}else if (strValue == 'last') {
		initVal = ((document.getElementById("pageId_"+gridName).length-1)*iniNumRec);
		Reclength  = iniNumRec;
	}else if (strValue == 'first') {
		initVal = 0;
		Reclength  = iniNumRec;
	}else if (strValue == 'page') {	
		if(nextVal==1){
			initVal=0;
		}else{
			initVal = (--nextVal*iniNumRec);
			++nextVal;
		}
		Reclength  = iniNumRec;
	}else if(strValue=='search'){
		 if (document.getElementById("all_"+gridName).checked  == true) {
			initVal = 0;
			Reclength  = -1;
		}else{
			initVal = 0;
			Reclength  = iniNumRec;
			
		}
		if (searchField == "0"){
			alert(window.parent.frames["commonSource"].gridSelectMsg);
			return;
		}
	}
	
	if(searchField == 0)
		url = gridURL + "?gridid="+gridName+"&dispatch=ajaxRequestData" + "&recordStatus=" + recordStatus + "&initVal=" + initVal + "&length=" + Reclength +"&rand="+ Math.random()*9999999+'&param='+additionalParams;
	else
		url = gridURL + "?gridid="+gridName+"&dispatch=ajaxRequestData" + "&recordStatus=" + recordStatus + "&initVal=" + initVal + "&length=" + Reclength +"&searchField=" + searchField + "&searchValue=" + searchValue + "&category=search&rand="+ Math.random()*9999999+'&param='+additionalParams;
         
	disabledAll(gridName);
	// alert(gridObjName)
 	 gridObjName.loading() 
	 req = window.parent.frames['commonSource'].CreateXHR();
	 req.onreadystatechange = function() { 
       if (req.readyState == 4) { 
               if (req.status == 200) { 
                  var totalText = req.responseText;
					var arrayTotaltext = totalText.split("#@%");
					var totalRecordsText = eval("(" + arrayTotaltext[0] + ")");
					if (totalRecordsText["totalRecords"]) {
						document.getElementById("totalRecords_" + gridName).value = totalRecordsText["totalRecords"];
					} else {
						document.getElementById("totalRecords_" + gridName).value="0";
						totalRecords = 0;
					}
					myArray = eval("(" + arrayTotaltext[1] + ")");

					removeAllOptions(document.getElementById("pageId_"+gridName));
					for (var s = 0; s < Math.ceil(totalRecordsText["totalRecords"] / iniNumRec); s++) {
						 var optionObject = new Option(s+1,s+1);
					     document.getElementById("pageId_"+gridName).options[s]=optionObject;	
					}
					if(strValue == 'next') {
					  	document.getElementById("pageId_"+gridName).value =++nextVal;
					}else if (strValue == 'previous') {
						 document.getElementById("pageId_"+gridName).value =--nextVal;
 					}else if (strValue == 'last') {
	 					 document.getElementById("pageId_"+gridName).value =document.getElementById("pageId_"+gridName).length;
 					}else if (strValue == 'first') {
 					 	 document.getElementById("pageId_"+gridName).value =1;
 					}else if (strValue == 'page') {	
	 					 document.getElementById("pageId_"+gridName).value =nextVal;
 					}
 					var pageId = document.getElementById("pageId_"+gridName).value;
 					
 					if(totalRecordsText["totalRecords"]>iniNumRec ){
						
						enabledRight(gridName);
						document.getElementById("pageId_"+gridName).disabled = false;
						enablededAll(gridName);
						if(pageId ==1)
	 					   	disabledLeft(gridName);
	 					else if(pageId == document.getElementById("pageId_"+gridName).length)
	 					 	disabledRight(gridName);
	 				   	/*if (strValue == 'all') {
	    				 	if(clickObj.checked == true){
	    				 		disabledAll(gridName);
	    				 		document.getElementById("all_"+gridName).disabled = false;
	    				 	}
	    				}*/ 
	 					
	 					if((pageId*Reclength)<=totalRecordsText["totalRecords"]){
	 						if(initVal==0)
	 							document.getElementById("range_" + gridName).value =1+ " - " + pageId*Reclength;
	 						else
	 							document.getElementById("range_" + gridName).value =(initVal+1)+ " - " + pageId*Reclength;
	 					}else
	 						if(initVal==0)
	 							document.getElementById("range_" + gridName).value =1+ " - " + totalRecordsText["totalRecords"];
	 						else
	 							document.getElementById("range_" + gridName).value =(initVal+1)+ " - " + totalRecordsText["totalRecords"];
					}
					else{
					 	if(totalRecordsText["totalRecords"] ==0){
					 		document.getElementById("range_" + gridName).value = 0 + " - " + 0 ;
					 	}else					 	
							document.getElementById("range_" + gridName).value = 1 + " - " + totalRecordsText["totalRecords"] ;
					}
					if(Reclength==-1 ){
						document.getElementById("range_" + gridName).value = 1 + " - " + totalRecordsText["totalRecords"] ;
					}
					
					if (document.getElementById("all_"+gridName).checked == true){
   						disabledAll(gridName);
   				 		document.getElementById("all_"+gridName).disabled = false;
	    			}
	    				
 					gridObjName.setSelectorText(function(i){return this.getRowPosition(i)+(initVal+1)});			
					prepareRequestData(myArray,gridObjName);
		        } 
       }
 	} 
	req.open("GET", url, true);
 	req.send(null);
			
}

	
function serverValueCls(myVal,gridName){
	if(myVal.value==0){
		document.getElementById("searchValue_"+gridName).value="";
	}
	document.getElementById("all_"+gridName).checked = false;
}
function prepareRequestData(message,myGrid) {
   	myGrid.setSelectorVisible(true);
	myGrid.clearScrollModel();
	myGrid.clearSelectedModel();
	myGrid.clearSortModel();
	myGrid.clearRowModel();
	myGrid.setRowCount(message.length);
	myGrid.setCellText(message);
	myGrid.refresh();
	window.parent.hidGridloding();
	myGrid.loadingCompleted();
		
}
function enabledLeft(gridName){
	document.getElementById("first_"+gridName).disabled = false;
	document.getElementById("previous_"+gridName).disabled = false;
	document.getElementById("previous_"+gridName).className = "previous";
	document.getElementById("first_"+gridName).className = "first";
}

function disabledLeft(gridName){
	document.getElementById("first_"+gridName).disabled = true;
	document.getElementById("previous_"+gridName).disabled = true;
	document.getElementById("first_"+gridName).className = "first_d";
	document.getElementById("previous_"+gridName).className = "previous_d";
}
function disabledRight(gridId){
	document.getElementById("last_"+gridId).disabled = true;
	document.getElementById("next_"+gridId).disabled = true;
	document.getElementById("next_"+gridId).className = "next_d";
	document.getElementById("last_"+gridId).className = "last_d";
}
function disabledAll(gridName) {
	document.getElementById("first_"+gridName).disabled = true;
	document.getElementById("previous_"+gridName).disabled = true;
	document.getElementById("next_"+gridName).disabled = true;
	document.getElementById("last_"+gridName).disabled = true;
	document.getElementById("pageId_"+gridName).disabled = true;
	document.getElementById("all_"+gridName).disabled = true;
	document.getElementById("previous_"+gridName).className = "previous_d";
	document.getElementById("first_"+gridName).className = "first_d";
	document.getElementById("next_"+gridName).className = "next_d";
	document.getElementById("last_"+gridName).className = "last_d";
}

function enabledRight(gridName){
    document.getElementById("next_"+gridName).disabled = false;
	document.getElementById("last_"+gridName).disabled = false;
	document.getElementById("next_"+gridName).className = "next";
	document.getElementById("last_"+gridName).className = "last";
	
}

function enablededAll(gridName) {
	document.getElementById("first_"+gridName).disabled = false;
	document.getElementById("previous_"+gridName).disabled = false;
	document.getElementById("next_"+gridName).disabled = false;
	document.getElementById("last_"+gridName).disabled = false;
	document.getElementById("pageId_"+gridName).disabled = false;
	document.getElementById("all_"+gridName).disabled = false;
	document.getElementById("previous_"+gridName).className = "previous";
	document.getElementById("first_"+gridName).className = "first";
	document.getElementById("next_"+gridName).className = "next";
	document.getElementById("last_"+gridName).className = "last";
}

function removeAllOptions(selectbox){
    var i;
    for(i=selectbox.options.length-1;i>=0;i--){
        selectbox.remove(i);
    }
}

function ToExcel(strObj) {
	try {
		if (window.ActiveXObject) {
			var xlApp = new ActiveXObject("Excel.Application");
			var xlBook = xlApp.Workbooks.Add();
			xlBook.worksheets("Sheet1").activate;
			var XlSheet = xlBook.activeSheet;
			xlApp.visible = true;
			var xlRow = 1;
			var xlCol = 1;
			for (var C = 0; C < strObj.getColumnCount(); C++) {  
     // alert(obj.getHeaderText(C))
        //XlSheet.cells(xlRow, xlCol).value =strObj.getHeaderText(obj.getColumnIndices()[C]); 
				XlSheet.cells(xlRow, xlCol).value = strObj.getHeaderText(C);
				XlSheet.cells(xlRow, xlCol).Font.Bold = true;
				XlSheet.cells(xlRow, xlCol).Font.Size = 11;
				xlCol++;
			}
			for (var R = 0; R < strObj.getRowCount(); R++) {
				xlRow++;
				xlCol = 1;
				for (var C = 0; C <strObj.getColumnCount(); C++) {
					XlSheet.cells(xlRow, xlCol).value =strObj.getCellText(C, R);
					xlCol++;
				}
			}
			XlSheet.columns.autofit;
		}
	}
	catch (err) {
		alert("Pleace setup configaration for excel");
	}
}

//set total record after delete,Authorize,update
var gridObj;
var gridName;
function setTotalRecordsAfterAction(my) {
	try{
		var objName = document.getElementById('gridName').value;
		gridObj.deleteRow(gridObj.getCurrentRow()); 
	    gridObj.setSelectedRows([]);
		var range = document.getElementById("range_"+objName).value.split("-");
		document.getElementById("totalRecords_"+objName).value = document.getElementById("totalRecords_"+objName).value - 1;
		if(range[1] ==1 )
			document.getElementById("range_"+objName).value = 0 + " - " + 0;
		else
			document.getElementById("range_"+objName).value =(range[0]) + " - " + (range[1] - 1);
			
    }
    catch(ee)
    {
    	alert(ee);
    }
}

//document.onkeydown = checkKeycode
function checkKeycode(e) {
var keycode;
var strObject;
if (window.event) {
keycode = window.event.keyCode;
strObject=window.event.srcElement.tagName;
}else if (e){
  keycode = e.which;
  strObject=e.target.tagName
}

if((strObject!="INPUT" || strObject!="TEXTAREA") && keycode==8){
return false;
}
}
 