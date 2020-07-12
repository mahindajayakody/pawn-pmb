// Grid component java script
var totalRecords;
var initialNumberOfRecords;
var req;
var target;
var isIE;
var initVal = 0;
var length;
var numOfPage;
var finalValue; //range final value
var btn; //to assign value for user event
var pageNumber=1; //page number
var myArray = null; // To prepare ajax request data
var recordNumber = 0;
var category; //to assign a request criteria
var gridURL;
var authorizeType;
var firstTime ='1';


var gridHead1 = "<fieldset style=\"width:1px;\"><legend>" + window.parent.frames["commonSource"].gridLegend + "</legend>" + "<table  border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" >" + "<tr><td><table cellspacing=\"1\" cellpadding=\"0\"><tbody><tr><td><select class=\"GridColumn\" id=\"serachField\">";

function gridValueSetSearch() {
	var searchBox = "<option value='0'>" + window.parent.frames["commonSource"].gridSelect + "</option>";
	for (var j = 0; j < myColumns.length; j++) {
		searchBox = searchBox + "<option value=" + (j + 1) + ">" + myColumns[j] + "</option>";
	}
	return searchBox;
}

var gridHead2 = "</select></td><td><input type=\"text\" class=\"GridColumn\" id=\"searchValue\"></td><td valign=\"center\"><input type=\"button\" class=\"buttonNormal\"  id=\"Search\" onclick =\"pleaceSelect();\" value=" + window.parent.frames["commonSource"].searchBtn + " \></td><td></td><td></td></tr></tbody></table></td>" + "</tr><tr height=\"2px\"></tr>\t<tr><td colspan='6'>";
var gridFooter1 = "</td> </tr><tr><td><table><tbody> <tr class=\"GridNavigator\"><td  valign=\"middle\" width=\"250px\"> <input   type=\"text\"  class=\"searchTextbox\" size=\"14\"  readonly=\"true\" value=\" " + window.parent.frames["commonSource"].totalRecords + " \"><input   type=\"text\"  class=\"searchTextbox\" size=\"15\" id=\"totalRecords\" readonly=\"true\" value=\" ";

if (window.showModalDialog)
	var gridExcel = "<td width=\"90px\"><img src=\"images\\excel_icon.jpg\" onclick=\"ToExcel()\" style=\" cursor: hand;\"></td>";
    else 
		var gridExcel = "<td width=\"90px\">&nbsp;</td>";

var gridFooter2 = " \"></td>" + gridExcel + " <td  width=\"120px\"><nobr> <input   type=\"text\"  class=\"searchTextbox\" size=\"10\"  readonly=\"true\" value=\" " + window.parent.frames["commonSource"].display + " \"><input   type=\"text\"  class=\"searchTextbox\" size=\"15\" id=\"range\" readonly=\"true\" value=\"";
var gridFooter3 = "\"></nobr></td> <td align=\"left\"><nobr>" + "<input  type=\"Button\" name=\"First\" class=\"first_d\" id=\"first\"  onclick=\"gridNext('btnFirst')\"/>" + "<input  type=\"Button\" name=\"Previous\" class=\"previous_d\"  id=\"previous\" onclick=\"gridNext('btnPrevious')\"/>" + "<select  name=\"page\" class=\"GridSelect\" onchange=\"gridNext(this.value)\" id=\"pageId\">";

function numOfPagesCal() {
	calNumOfPages();
	var pages;
	for (var i = 1; i <= numOfPage; i++) {
		pages = pages + "<option value=" + i + ">" + i + "</option>";
	}
	return pages;
}

var gridFooter4 = "</select> <input class=\"next\" type=\"Button\" name=\"Next\" id=\"next\"  onclick=\"gridNext('btnNext')\"/>" + "<input class=\"last\" type=\"Button\"  name=\"Last\" id=\"last\" onclick=\"gridNext('btnLast')\"/>" + "</nobr> </td><td align=\"right\" valign=\"middle\" style=\"width:100px\">" + "<nobr>" + window.parent.frames["commonSource"].gridAll + "<input class=\"GridNavigator\" type=\"checkbox\"  value=\"on\"  id=\"on\"  onclick = \"gridNext(this.value)\" /></nobr></td></tr></tbody></table>";
var gridFooter5 = "</td></tr></table></fieldset>";

function completedGrid() {
	return (gridHead1 + gridValueSetSearch() + gridHead2 + obj + gridFooter1 + totalRecords + gridFooter2 + 1 + "&nbsp;-&nbsp;" + initialNumberOfRecords + gridFooter3 + numOfPagesCal() + gridFooter4 + gridFooter5);
}

function pleaceSelect() {
	var searchField = document.getElementById("serachField").value;
	if (searchField == "0")
		alert(window.parent.frames["commonSource"].gridSelectMsg);
    else 
		gridNext("search");
	
}

function setGridValueAsBrowser() {
	if (window.showModalDialog)
		initialNumberOfRecords = initialNumberOfRecords - 1;
	else 
		initialNumberOfRecords = initialNumberOfRecords;
	
}
function calNumOfPages() {
	numOfPage = Math.ceil(totalRecords / initClientValue);
}

// tihs is to change the button class to enable / desable
var firstButton;

function first(frm) {
	addButton.className = "cAddNew_d";
}

function btnDisabled() {
	if (totalRecords >= initialNumberOfRecords && initClientValue >= totalRecords)
		disabledAll();
	else {
		document.getElementById("previous").disabled = true;
		document.getElementById("first").disabled = true;
		document.getElementById("previous").className = "previous_d";
		document.getElementById("first").className = "first_d";
	}
}

function disabledAll() {
	document.getElementById("first").disabled = true;
	document.getElementById("previous").disabled = true;
	document.getElementById("next").disabled = true;
	document.getElementById("last").disabled = true;
	document.getElementById("pageId").disabled = true;
	document.getElementById("on").disabled = true;
	document.getElementById("previous").className = "previous_d";
	document.getElementById("first").className = "first_d";
	document.getElementById("next").className = "next_d";
	document.getElementById("last").className = "last_d";
}

function enablededAll() {
	document.getElementById("first").disabled = false;
	document.getElementById("previous").disabled = false;
	document.getElementById("next").disabled = false;
	document.getElementById("last").disabled = false;
	document.getElementById("pageId").disabled = false;
	document.getElementById("on").disabled = false;
	document.getElementById("previous").className = "previous";
	document.getElementById("first").className = "first";
	document.getElementById("next").className = "next";
	document.getElementById("last").className = "last";
}

//set total record after delete,Authorize,update
function setTotalRecordsAfterAction() {
	obj.deleteRow(obj.getCurrentRow()); 
    obj.setSelectedRows([]);
	var range = document.getElementById("range").value.split("-");
	document.getElementById("totalRecords").value = document.getElementById("totalRecords").value - 1;
	document.getElementById("range").value =(range[0]) + "- " + (range[1] - 1);
}

function gridNext(btnEvent) {
	try{  
		var radioBtnValue = window.parent.frames["footer"].radioBtnValue;
		recordNumber = 1;
		if (radioBtnValue == 4) {
			authorizeType = document.getElementById("clickTab").value;
			if (authorizeType == "l1") {
				authorizeType = "create";
				recordNumber = 0;
				length = initClientValue;
			} 
			else if(authorizeType == "l2") {
				authorizeType = "update";
				recordNumber = 3;
			    length = initClientValue;
			} 
			else if (authorizeType == "l3") {
				authorizeType = "delete";
				recordNumber = 2;
			    length = initClientValue;
			}
		}
		calNumOfPages();
		disabledAll();
		btn = btnEvent;
		myArray = null;
		if (totalRecords < initClientValue) {
			disabledAll();
			initVal = 0;
		} 
		else{
			if (btnEvent > 0 && btnEvent <= numOfPage) {
				initVal = (btnEvent - 1) * initClientValue;
				finalValue = initVal + initClientValue;
				pageNumber = btnEvent;
				//alert(initVal +"final"+ finalValue);
				if(authorizeType == "create")
					recordNumber = 0;					
				else if(authorizeType == "update")
					recordNumber = 3;					
				else if(authorizeType == "delete")
					recordNumber = 2;
			   if (category == "search")
					btnEvent = "search";
			   firstTime = '0';
								
			} 
			else if (btnEvent == "on") { // checked all checked box
				if (document.getElementById("on").checked == true) {
					obj.setVirtualMode(true);
					initVal = 0;
					length = totalRecords;
					
				}
				else {  //assing page size after checked button
					initVal = 0;
					length = initClientValue;
					finalValue = length;
					pageNumber = 1;
									
				}
				if (category == "search")
					btnEvent = "search";
			} 
			else if (btnEvent == "btnFirst") {
					initVal = 0;
					finalValue = initClientValue;
					pageNumber = 1;
					if (category == "search") {
						btnEvent = "search";
					}
			} 
			else if (btnEvent == "btnPrevious") {
				initVal = initVal - initClientValue;
				var pageId1 = document.getElementById("pageId").value;
				pageNumber = pageId1 - 1;
				finalValue = initVal + initClientValue;
				if (category == "search")
					btnEvent = "search";
		   }
		   else if (btnEvent == "btnLast") {
		   		initVal = ((numOfPage * initClientValue) - initClientValue);
				finalValue = initVal + initClientValue;
				pageNumber = numOfPage;
				if (category == "search")
					btnEvent = "search";
		   }
		   else if (btnEvent == "btnNext") {
				initVal = initVal + initClientValue;
				var pageId = document.getElementById("pageId").value;
				pageNumber = (Math.floor(pageId) + 1);
				finalValue = initVal + initClientValue;
				if (category == "search")
					btnEvent = "search";
		   }
		   
		   else if(btnEvent == "authorizeUpdate") {
		        document.getElementById("on").checked =false;
		  		recordNumber = 3;
				initVal = 0;
				pageNumber = 1; 
				length = initClientValue;
		  } 
		  else if(btnEvent == "authorizeCreate") {
		        document.getElementById("on").checked =false;
				recordNumber = 0;
				initVal = 0;
				pageNumber = 1; 
				length = initClientValue;
		  }
		  else 	if(btnEvent == "authorizeDelete") {
		        document.getElementById("on").checked =false;
				recordNumber = 2;
				initVal = 0;
				pageNumber = 1; 
				length = initClientValue;
		  }
		  
		
	   }
	   // alert( "btnEvent:" +btnEvent +"init:" +initVal + "length" +length + " final Value:" + finalValue + "cat :"+category);
	    if (btnEvent == "search") {
	    	if(category != "search" )
	    		initVal = 0;
			category = "search";
	    	if(document.getElementById("on").checked == true)
	    		length = totalRecords;
			var searchField = document.getElementById("serachField").value;
			var searchValue = document.getElementById("searchValue").value;
			var url = gridURL + "?dispatch=ajaxRequestData" + "&recordStatus=" + recordNumber + "&initVal=" + initVal + "&length=" + length + "&searchField=" + searchField + "&searchValue=" + searchValue + "&category=" + category;
			searchField = "";
			searchValue = "";
		}
		else 
			var url = gridURL + "?dispatch=ajaxRequestData" + "&recordStatus=" + recordNumber + "&initVal=" + initVal + "&length=" + length;
			
		req = CreateXHR();
		req.onreadystatechange = processRequest;
		req.open("GET", url, true);
	   // req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); 
		req.send(null);
	  }
	  catch(err){
		      txt="There was an error on this page.\n\n";
              txt+="Error description: " + err.description + "\n\n";
              txt+="Click OK to continue.\n\n";
              errorUrl=location.pathname +"|"+ err.name +"|"+ err.message+"\n";
              alert(txt);
     }
	  
}

function prepareRequestData(message) {
 try{
   	var myArray = message;
	if (btn == "search")
		finalValue = initClientValue;
	if (myArray.length <= initClientValue || document.getElementById("on").checked == true)
		finalValue = initVal + myArray.length;
	
	var mdiv = document.getElementById("range");
	mdiv.value = (initVal + 1) + " - " + finalValue;
	
	document.getElementById("pageId").value = pageNumber;
	document.getElementById("totalRecords").value = totalRecords;
    // Enable and disabled  after  process data required buttons
   
    if(!isNaN(btn)){
    	firstTime ='1';
    	if (btn == 1) {
			enablededAll();
			document.getElementById("first").disabled = true;
			document.getElementById("previous").disabled = true;
			document.getElementById("first").className = "first_d";
			document.getElementById("previous").className = "previous_d";
		} 
		else if (btn == numOfPage) {
			enablededAll();
			document.getElementById("last").disabled = true;
			document.getElementById("next").disabled = true;
			document.getElementById("next").className = "next_d";
			document.getElementById("last").className = "last_d";
		}
		else
			enablededAll();    
    }
	else if (btn == "btnNext") {
		enablededAll();
		if (myArray.length < initClientValue) {
			document.getElementById("last").disabled = true;
			document.getElementById("next").disabled = true;
			document.getElementById("next").className = "next_d";
			document.getElementById("last").className = "last_d";
		}
		if ((finalValue) / initClientValue >= numOfPage) {
			document.getElementById("last").disabled = true;
			document.getElementById("next").disabled = true;
			document.getElementById("next").className = "next_d";
			document.getElementById("last").className = "last_d";
		}
	} 
	else if (btn == "btnPrevious") {
			enablededAll();
			if (initVal == 0) {
				document.getElementById("first").disabled = true;
				document.getElementById("previous").disabled = true;
				document.getElementById("first").className = "first_d";
				document.getElementById("previous").className = "previous_d";
			}
		} 
		else if (btn == "btnLast") {
				enablededAll();
				document.getElementById("next").disabled = true;
				document.getElementById("last").disabled = true;
				document.getElementById("next").className = "next_d";
				document.getElementById("last").className = "last_d";
		} 
		else if (btn == "btnFirst") {
				enablededAll();
				document.getElementById("first").disabled = true;
				document.getElementById("previous").disabled = true;
				document.getElementById("first").className = "first_d";
				document.getElementById("previous").className = "previous_d";
		} 
		else if (btn == "on" ) {
				if (document.getElementById("on").checked == true  ) {
					document.getElementById("on").disabled = false;
				} 
				else{ 
					enablededAll();
					document.getElementById("first").disabled = true;
					document.getElementById("previous").disabled = true;
					document.getElementById("first").className = "first_d";
					document.getElementById("previous").className = "previous_d";
				}
		} 
		
		else if ((btn == "search" || (authorizeType == "create" || authorizeType == "delete" || authorizeType == "update")) && firstTime == '1' ) {
			//alert("again paging");
			if (totalRecords <= initClientValue) 
				disabledAll();
			else {
			  	enablededAll();
			    var pageSize;
				document.getElementById("first").disabled = true;
				document.getElementById("previous").disabled = true;
				document.getElementById("first").className = "first_d";
				document.getElementById("previous").className = "previous_d";
				calNumOfPages();
				removeAllOptions(document.getElementById("pageId"));
				for (var s = 0; s < numOfPage; s++) {
					 var optionObject = new Option(s+1,s+1);
				     document.getElementById("pageId").options[s]=optionObject;	
				}
				//alert("range"+initVal +" finalval"+initClientValue);
				document.getElementById("pageId").value = pageNumber;
				document.getElementById("range").value = (initVal + 1) + " - " + initClientValue;
				
				
			}
		    if(document.getElementById("on").checked == true){
		   		disabledAll();
		   		document.getElementById("range").value = 1 + " - " + totalRecords;
				document.getElementById("on").disabled = false;
		    }
		    // alert( "btnEvent:" +btnEvent +"init:" +initVal + "length" +length + " final Value:" + finalValue + "cat :"+category);
		    window.parent.hidGridloding();
	   } 
	   
	   else 
	   		enablededAll();
	   obj.setSelectorVisible(true);
	   obj.clearScrollModel();
	   obj.clearSelectedModel();
	   obj.clearSortModel();
	   obj.clearRowModel();
	   obj.setRowCount(myArray.length);
	   obj.setCellText(myArray);
	   obj.refresh();
	 }
	 catch(err){
		      txt="There was an error on this page.\n\n";
              txt+="Error description: " + err.description + "\n\n";
              txt+="Click OK to continue.\n\n";
              errorUrl=location.pathname +"|"+ err.name +"|"+ err.message+"\n";
              alert(txt);

    }
}

function removeAllOptions(selectbox){
    var i;
    for(i=selectbox.options.length-1;i>=0;i--){
        selectbox.remove(i);
    }
}
function processRequest() {
	try {
		if (req.readyState <= 4) {
			obj.clearScrollModel();
			obj.clearSelectedModel();
			obj.clearSortModel();
			obj.clearRowModel();
			obj.setRowCount(1);
			obj.setSelectorVisible(false); 
	      //obj.onRowClicked(false);
	      //obj.setCellTemplate(new AW.Templates.ImageText);
	      // obj.setCellImage("home",0,0);
	      // obj.setCellImage("loading", 0, 0);  
			obj.setCellText("Loading Data.....Pleace wait", 0, 0);
			obj.setCellText("", 1, 0);
	      //obj.setSelectionMode(false);
			obj.refresh();
		}
		if (req.readyState == 4) {
			if (req.status == 200) {
				var totalText = req.responseText;
				var arrayTotaltext = totalText.split("#@%");
				var totalRecordsText = eval("(" + arrayTotaltext[0] + ")");
				if (totalRecordsText["totalRecords"]) {
					totalRecords = totalRecordsText["totalRecords"];
				} else {
					totalRecords = 0;
				}
				myArray = eval("(" + arrayTotaltext[1] + ")");
				prepareRequestData(myArray);
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
}
var obj;
function setGrid(gridName, myData, myColumns, cellFormat) {
        //	create ActiveWidgets Grid javascript object
	try{
		obj = new AW.UI.Grid;
		obj.setId(gridName);
	
	        //	define data formats
		obj.setCellFormat(cellFormat);
	       
	        //	provide cells and headers text
		obj.setCellText(myData);
		obj.setHeaderText(myColumns);
	       
	        //	set number of rows/columns
		if (window.showModalDialog) {
			obj.setRowCount(myData.length - 1);
		} else {
			obj.setRowCount(myData.length);
		}
		obj.setColumnCount(myColumns.length);
	        //	enable row selectors
		obj.setSelectorVisible(true);
		obj.setSelectorText(function (i) {
			return this.getRowPosition(i) + 1;
		});
	
	        //	set headers width/height
		obj.setSelectorWidth(40);
		obj.setHeaderHeight(20);
	
	        //	set row selection
		obj.setSelectionMode("single-row");
	        
	        //obj.setVirtualMode(false);
	  }
	  catch(err){
		      txt="There was an error on this page.\n\n";
              txt+="Error description: " + err.description + "\n\n";
              txt+="Click OK to continue.\n\n";
              errorUrl=location.pathname +"|"+ err.name +"|"+ err.message+"\n";
              alert(txt);
      }
	return obj;
}
function searchFieldClear(){
	document.getElementById("serachField").selectedIndex = 0;
	document.getElementById("searchValue").value= "";	
}

function resetGrid(){
	document.getElementById("serachField").selectedIndex = 0;
	document.getElementById("searchValue").value= "";
	obj.setSelectedRows([]);
    obj.clearScrollModel(); 
    obj.clearSortModel(); 
    obj.refresh(); 
}
 
function ToExcel() {
	try {
		if (window.ActiveXObject) {
			var xlApp = new ActiveXObject("Excel.Application");
			var xlBook = xlApp.Workbooks.Add();
			xlBook.worksheets("Sheet1").activate;
			var XlSheet = xlBook.activeSheet;
			xlApp.visible = true;
			var xlRow = 1;
			var xlCol = 1;
			for (var C = 0; C < obj.getColumnCount(); C++) {  
     // alert(obj.getHeaderText(C))
        //XlSheet.cells(xlRow, xlCol).value = obj.getHeaderText(obj.getColumnIndices()[C]); 
				XlSheet.cells(xlRow, xlCol).value = obj.getHeaderText(C);
				XlSheet.cells(xlRow, xlCol).Font.Bold = true;
				XlSheet.cells(xlRow, xlCol).Font.Size = 11;
				xlCol++;
			}
			for (var R = 0; R < obj.getRowCount(); R++) {
				xlRow++;
				xlCol = 1;
				for (var C = 0; C < obj.getColumnCount(); C++) {
					XlSheet.cells(xlRow, xlCol).value = obj.getCellText(C, R);
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

