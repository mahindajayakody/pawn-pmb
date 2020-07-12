<%@ page language="java" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><head>
		<link rel="StyleSheet" type="text/css"	href="<html:rewrite forward="commonCSS"/>"></link>
		
<html:html>
	<link rel="StyleSheet" type="text/css"	href="<html:rewrite forward="awCSS"/>"></link>
		<link rel="StyleSheet" type="text/css"	href="<html:rewrite forward="calendarCSS"/>"></link>
	
		<link rel="StyleSheet" type="text/css" href="css/com-all.css"></link>
		<script type="text/javascript" src="<html:rewrite forward="commonJS"/>"></script>
		<script type="text/javascript" src="<html:rewrite forward="ajaxJS"/>"></script>
		<script type="text/javascript" src="<html:rewrite forward="awJS"/>"></script>
		<script type="text/javascript" src="js/com-all.js"></script>
		<script type="text/javascript"	src="<html:rewrite forward="calendarJS"/>"></script>
		
		<script type="text/javascript">								
			function formatNumber(num,prefix){prefix = prefix || '';   num += '';   var splitStr = num.split('.');   var splitLeft = splitStr[0];   var splitRight = splitStr.length > 1 ? '.' + splitStr[1] : '';   var regx = /(\d+)(\d{3})/;   while (regx.test(splitLeft)) {      splitLeft = splitLeft.replace(regx, '$1' + ',' + '$2');   }   return prefix + splitLeft + splitRight;}function unformatNumber(num) {  return num.replace(/([^0-9\.\-])/g,'');}				
			var action = 1;
			var winSchemeDirtyFlag = false;
			var previusSchemaCode = '';
			var itemsSelected = false;
			var otherDue = 0.00;
			
			window.parent.document.getElementById('footer').style.display="none";			
			window.parent.document.getElementById("mainbody").height="600px";
			window.parent.document.getElementById("footer").height="0px";
						
			var url = 'renewService.do';			
			
			function createBrowser(myColumns,gridName,dataFormat){
				var obj = new AW.UI.Grid;
				obj.setId(gridName);
				obj.setCellText([]);
				obj.setCellFormat(dataFormat);
				obj.setHeaderText(myColumns);
				obj.setSelectorVisible(true);
				obj.setRowCount(0);
				obj.setColumnCount(myColumns.length);
				obj.setSelectorText(function(i){return this.getRowPosition(i)+ 1});
				obj.setSelectorWidth(20);
				obj.setHeaderHeight(20);
				obj.setSelectionMode("single-row");
				obj.onSelectedRowsChanged= function(row){};				
				return obj;
			}
			
			function createEditBrowser(myColumns,gridName,dataFormat){
				var obj = new AW.UI.Grid;
				obj.setId(gridName);				
				obj.setCellFormat(cellFormat);
				obj.setHeaderText(myColumns);
				obj.setSelectorVisible(true);
				obj.setRowCount(0);
				obj.setColumnCount(myColumns.length);
				obj.setSelectorText(function(i){return this.getRowPosition(i)+ 1});
				obj.setSelectorWidth(20);
				obj.setHeaderHeight(20);
				obj.setVirtualMode(false);	
				obj.setCellText(function(c,r){return myData[r][c]} ); 
				//obj.setSelectionMode("single-row");			
				
				//obj.setCellEditable(true, 2);
				obj.setCellEditable(true, 5);				
			 	obj.onCellEditEnded = function(text, col, row){				 		
			 		if(col==5 && obj.getCellValue(0,row)==true)
			 			if(!validateReleaseItems(true,col, row)){			 		
			 				obj.setCellValue(false,0,row); 
						}
				}
				
				obj.onCellTextChanging = function(text,column,row){				    			       
			        if(column == 5){				        			        	
				        if (text.match(/[^0-9.+-]/) || (text*1 > obj.getCellValue(4,row))){
				            return "error"; // prevent non-digits
				        }
				    }    				    
				}
				//obj.getCellTemplate(2, 0).setStyle("border", "1px solid #ff0000");
				
				obj.onCellDoubleClicked = function(event, col, row){}; 
				
				obj.setCellTemplate(new AW.Templates.Checkbox, 0); 

			    //set initial value for column 0 
			    obj.setCellValue(false, 0); 
			    //obj.setCellValue(function(col,row){return this.getCellText(3, row)}, 2); 
			    obj.setSelectorVisible(true);
			
			    obj.getCheckedValue=function() { 
			        for(var i=0;i<obj.getRowCount();i++) { 
			            if(obj.getCellValue(0,i)) { 
			                alert(i); 
			            } 
			        } 
			    };
						
				return obj;
			}
			
			function setGridData(gridObj,Data){
				gridObj.setRowCount(Data.length);
				gridObj.setCellText(Data);
				gridObj.setSelectedRows([]);
				gridObj.refresh();
			}
			
			
			// Renew item validation
			// Renwew any item if and only if all the capital & intrest is settled
			function validateReleaseItems(value,col, row){
				if(value==true){									
					var payToAmt = 0;
					for(i=0;i<gridTicket.getRowCount();i++){
						//Modofied By Mahinda on 16-Sept-2011						
						if(gridTicket.getCellValue(0,i)==false){	
							payToAmt = payToAmt + unformatNumber(gridTicket.getCellValue(3,i))*1;
							itemsSelected = true;
						}	
					}		
					alert(payToAmt);
					var receiptAmt  = unformatNumber($('totalreceipts').value)*1;
					var interestAmt = $('interestAmt').value*1;
					var totDue = otherDue * 1 + interestAmt + payToAmt * 1;
					
					if(totDue > receiptAmt ){
						alert('	Please Collect a Receipt of ' + totDue + ' Or More ' );
						gridTicket.setCellValue(false,0,row);
						itemsSelected = false;
						return false;
					}					
				}
				return true;
			}
			
			function getTicketData(){
				getInfoData();
			}
			
			function getInfoData(){
				$('screenCont').className = 'disableAll';
				data = "dispatch=getInfoData&ticketId=" + $('ticketId').value;	
				var mySearchRequest = new ajaxObject(url);
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
					if (responseStatus==200) {						
						var message =  eval('(' + responseText + ')');						
						
						$('clientcode').value 			= message['pawnerCode'];
						$('clientName').value   		= message['pawnerName'];
						$('clientAddress').value 		= message['address'];				
						$('pawnadvanace').value 		= message['pawnAdvance'];
						$('marketvalue').value 			= message['marketValue'];
						$('actualdisbursment').value 	= message['actualDisValue'];
						$('totalweight').value 			= message['totalNetWeight'];
						$('ticketdate').value 			= message['ticketDate'];
						$('authorizedate').value 		= message['ticketDate'];//message['authorizeDate'];
						$('expairydate').value 			= message['expiraryDate'];//message['expiraryDate'];
						$('printeddate').value 			= message['ticketDate'];//message['printedDate'];
						$('schemeId').value 			= message['schemeCode'];
						$('schemeCode').value 			= message['schemeCode'];
						$('schemeDesc').value 			= message['schemeDesc'];
						$('interestRate').value 		= message['interestCode'];
						$('interestRateId').value 		= message['interestId'];
						$('totalreceipts').value		= message['totalreceipts'];
						$('period').value		 		= message['period'];
						previusSchemaCode 				= message['schemeCode']
																		
						setGridData(gridDue , eval(message['dueFromList']));  
						setGridData(gridTicket , eval(message['ticketArticleList']));
						
						var dueTotal   = 0;
						var totPaid    = 0;
						var balanceamt = 0;
						
						dueFromList = eval(message['dueFromList'])
						for(i=0;i<dueFromList.length;i++){
							dueTotal   += unformatNumber(dueFromList[i][1])*1;
							totPaid    += unformatNumber(dueFromList[i][2])*1;
							balanceamt += unformatNumber(dueFromList[i][3])*1;
							if(dueFromList[i][4]=='INT')
								$('interestAmt').value = unformatNumber(dueFromList[i][3])*1; 
							
							if(dueFromList[i][4]=='DOC')
								otherDue = unformatNumber(dueFromList[i][3])*1;							
						}
						
						$('dueTotal').value 	  = formatNumber(parseFloat(dueTotal).toFixed(2));
						$('totPaid').value 		  = formatNumber(parseFloat(totPaid).toFixed(2));
						$('balanceamt').value 	  = formatNumber(parseFloat(balanceamt).toFixed(2));
						$('screenCont').className = 'enableAll';										
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request'); 
			    	    $('screenCont').className = 'enableAll';
				    }
				}
				mySearchRequest.update(data,'POST');				
			}      
			      
			function clearAll(){				
				$('ticketNumber').value 		= '';				
				$('divTicketno').innerHTML		= '';
				$('divSchemeCode').innerHTML	= '';
				clearOtherData();
			}	
			
			function clearOtherData(){
				$('clientcode').value 			= '';
				$('clientName').value   		= '';
				$('clientAddress').value 		= '';				
				$('pawnadvanace').value 		= '0.00';
				$('marketvalue').value 			= '0.00';
				$('actualdisbursment').value 	= '0.00';
				$('totalweight').value 			= '';				
				$('ticketdate').value 			= '';
				$('authorizedate').value 		= '';
				$('expairydate').value 			= '';
				$('printeddate').value 			= '';
				$('schemeId').value 			= '';
				$('schemeCode').value 			= '';
				$('schemeDesc').value 			= ''; 
				$('period').value 				= ''; 
				$('interestRate').value 		= '';
				$('ticketId').value 			= '';				
				$('dueTotal').value 			= '0.00';
				$('totPaid').value 				= '0.00';         
				$('balanceamt').value 			= '0.00';
				$('interestRateId').value 		= '0';
				$('totalreceipts').value		= '0.00';	
				$('period').value		 		= '';
				previusSchemaCode 				= '';
				
				setGridData(gridDue , []);  
				setGridData(gridTicket , []);
				setGridData(ForthGrid , []);
				
				winSchemeDirtyFlag = false;
				itemsSelected = false;
				
				for(i=0;i<gridTicket.getRowCount();i++){						
					gridTicket.getCellValue(0,i)=false;
				}
			}	

			function confirms(){
				$('screenCont').className = 'disableAll';
        		//Modofied By Mahinda on 16-Sept-2011				
				if(!validateReleaseItems(true,0, 0)){
					$('screenCont').className = 'enableAll';	
					return false ;
				}
				// buisness Validations
				// validate a new schema is selected other than the exisiting schema to renew
// 				if(previusSchemaCode == '' || previusSchemaCode == $('schemeCode').value){
// 					// TODO: read mesaage strings from properties
// 					alert('Existing schema is Invalid, Please select a new schema for Renew.');
// 					$('screenCont').className = 'enableAll';	
// 					return false ;
// 				}
				
				// validate existing schema has been expired : validate ticket date /con date with today
/*				if(compareWithCurrentDate($('ticketdate').value)){
					// TODO: read mesaage strings from properties
					alert(" 	Ticket has Not Expired for Renewal.");
					$('screenCont').className = 'enableAll';	
					return false ;
				}
*/
				// articles has been selected for renewal
				if(!itemsSelected){
					alert(" 	No Articles has been selected for Renewal.");
					$('screenCont').className = 'enableAll';	
					return false ;				
				}
				
				// TODO: read mesaage strings from properties
				var agree=confirm('Are you sure you want to Confirm to Renew the record ?'); 
				if (!agree){
					$('screenCont').className = 'enableAll';		           
		           	return false ;
		        }   
				
				// append renewArticles to a string to be send to backend
				var renewArticles = '';
				for(i=0;i<gridTicket.getRowCount();i++){						
					if(gridTicket.getCellValue(0,i)==true){		
						renewArticles =renewArticles + gridTicket.getCellValue(5,i) + "<row>";
					}
				}	
				
				// Reedem to close the ticket & Renew selected articles to a new ticket
				data = "dispatch=renewTicket&ticketId=" + $('ticketId').value + "&ticketNumber=" + $('ticketNumber').value + "&schemaId=" + $('schemeId').value + "&renewArticles=" + renewArticles + "&period=" + $('period').value	;
				var mySearchRequest = new ajaxObject(url);
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
					if (responseStatus==200) {												
						var message =  eval('(' + responseText + ')');
						if(message['error']){
			       			alert(message['error']);
			       		}else if(message['success']){			       			
			       			alert(message['success']);
			       			clearAll();				   			       			    						       									
			       		}else{
			       			showValidationErrors(message);
			       		}	
			       		$('screenCont').className = 'enableAll';
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request'); 
			    	    $('screenCont').className = 'enableAll';
				    }
				}
				mySearchRequest.update(data,'POST');

 			}
			
			function showValidationErrors(message){
	       		for( var i =0; i < message.length ; i++){	            	
	                if( message[i]['ticketNumber'] )
	                    $('divTicketno').innerHTML = message[i]['ticketNumber'];	                
	            }    
	        }
			
			// TODO: needs to refactor to have single implemetation on schema brow from renew & ticket master
			function getSchemeReletedData(){
				//alert("hit on getSchemeReletedData");
				if(winSchemeDirtyFlag){
					data = "dispatch=getSchemeData&isActive=1&schemeId=" + $('schemeId').value ;
					winSchemeDirtyFlag = false;
					//alert("data "+data);
					// TODO: refactor all the common urls to a constant js
					var mySearchRequest = new ajaxObject("ticketService.do");
					mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
						if (responseStatus==200) {
							var message =  eval('(' + responseText + ')');
							if(message['error']){
								alert(message['error']);
							}else{
								// Perform validation on prvious schema can not be moved forward to renew
								// Perform validation on prvious schema is changed to a new schema
								// This is done by period comparision
								$('period').value		 	= message['period'];
								$('interestRate').value 	= message['intCode'];
								$('interestRateId').value 	= message['intId'];
								//$('cartageMasterId').value  = message['masterId'];
							}
						}else {
				    	    alert(responseStatus + ' -- Error Processing Request');
					    }
					}
					mySearchRequest.update(data,'POST');
				}else{
					setGridData(gridSerchScheme,[]);
				}		
			}
	        
			
			// TODO: needs to refactor, God fogive us on duplicating functions from TicketMaster.jsp
			function getSearchData(gridObj,urlRef,rData){
				//alert("hit on getSearchData");
				data = "dispatch=getAjaxData" + rData;
				var mySearchRequest = new ajaxObject(urlRef);
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
					if (responseStatus==200) {
						var message =  eval('(' + responseText + ')');
						if(message['error']){
							alert(message['error']);
						}else{
							setGridData(gridObj,message);
						}
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request');
				    }
				}
				mySearchRequest.update(data,'POST');
			}


		</script>

		<style>	
			#forthGrid {height: 100px;width:720px;}
			#forthGrid .aw-row-selector {text-align: center}
			#forthGrid .aw-column-0 {width: 50px;text-align: center;}
			#forthGrid .aw-column-1 {width: 225px;}
			#forthGrid .aw-column-2 {width: 70px;text-align: center;}	
			#forthGrid .aw-column-3 {width: 130px;text-align: right;}	
			#forthGrid .aw-column-4 {width: 100px;text-align: center;}	
			#forthGrid .aw-column-5 {width: 120px;text-align: center;}
			#forthGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#forthGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
			
			#fifthGrid {height: 90px;width:720px;}
			#fifthGrid .aw-row-selector {text-align: center}
			#fifthGrid .aw-column-0 {width: 255px;}
			#fifthGrid .aw-column-1 {width: 140px;text-align: right;}	
			#fifthGrid .aw-column-2 {width: 140px;text-align: right;}	
			#fifthGrid .aw-column-3 {width: 140px;text-align: right;}	
			#fifthGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#fifthGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
						
			#popupGridInterest {height: 210px;width:600px;}
			#popupGridInterest .aw-row-selector {text-align: center}
			#popupGridInterest .aw-column-0 {width: 120px;}
			#popupGridInterest .aw-column-0 {width: 120px;}
			#popupGridInterest .aw-column-0 {width: 120px;}
			#popupGridInterest .aw-column-1 {width: 120px;}	
			#popupGridInterest .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#popupGridInterest .aw-grid-row {border-bottom: 1px solid threedlightshadow;}	
			
			#popupGridReceipt {height: 100px;width:580px;}
			#popupGridReceipt .aw-row-selector {text-align: center}
			#popupGridReceipt .aw-column-0 {width: 100px;}
			#popupGridReceipt .aw-column-1 {width: 80px;}
			#popupGridReceipt .aw-column-2 {width: 100px;text-align: right;}
			#popupGridReceipt .aw-column-3 {width: 120px;}	
			#popupGridReceipt .aw-column-4 {width: 150px;}	
			#popupGridReceipt .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#popupGridReceipt .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
			
			#popupGridDueReceipt {height: 100px;width:580px;}
			#popupGridDueReceipt .aw-row-selector {text-align: center}
			#popupGridDueReceipt .aw-column-0 {width: 300px;}
			#popupGridDueReceipt .aw-column-1 {width: 120px;}
			#popupGridDueReceipt .aw-column-2 {width: 120px;text-align: right;}
			#popupGridDueReceipt .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#popupGridDueReceipt .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
			
			#popupGrid3 {height: 210px;width:600px;}
			#popupGrid3 .aw-row-selector {text-align: center}
			#popupGrid3 .aw-column-0 {width: 100px;}
			#popupGrid3 .aw-column-1 {width: 455px;}
			#popupGrid3 .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#popupGrid3 .aw-grid-row {border-bottom: 1px solid threedlightshadow;}

			
			table.toolTable { 
			    border: #91a7b4 1px solid;
			    width: 740px;
			    height:30px;
			    background-color:#FFFFFF;
			    bottom: 0px;
			    position: absolute;
			}
		</style>
	</head>

	<body>
		<div id="screenCont" class="enableAll" align="center"></div>
		<input type="hidden" id="ticketStatus" name="ticketStatus" value="1"/>
	
		<div id="hidDiv" class="hideSearchPopup">
		</div>
	
		<!-- Create record -->
		<logic:equal name="renew" property="action" value="create">
			<html:form action="renewService.do">
				<input type="hidden" id="recordId" name="recordId" />
				<input type="hidden" id="version" name="version" />
	
				<table border="0">
					<tr>
						<td>
							<table class="InputTable">
								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.tiketno" />&nbsp;
									</td>
									<td>
										<input type="hidden" id="ticketId" name="ticketId" value="0" />
										<html:text property="ticketNumber" styleId="ticketNumber" size="20"	maxlength="13" 
											onfocus="clearDivision('divTicketno')"
											onblur="upperCase('ticketNumber');commonSearch('infoconsoleService.do','ticketId','ticketNumber','ticketId','getTicket','divTicketno',function(){getInfoData();},'',function(){clearOtherData();})"/>
										<input id="ButtonTicketSerch" type="button" value="..." />
										<br/><div id="divTicketno" class="validate"/>
									</td>
								</tr>
								<tr height="2px"></tr>
							</table>
						</td>
					</tr>
	
	
	
					<tr>
						<td>
							<table class="InputTable">
								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.clientcode" />&nbsp;
									</td>
									<td>
										<input type="hidden" id="clientId" name="clientId" value="0" />
										<input type="text" style="width: 92px" id="clientcode" readonly="readonly" class="READONLYINPUT"/ >
										<input type="text" size="75" id="clientName" readonly="readonly" class="READONLYINPUT" />
									</td>
								</tr>
	
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.address" />&nbsp;
									</td>
									<td>
										<input type="text" size="91" id="clientAddress" readonly="readonly" class="READONLYINPUT" />
									</td>
								</tr>
								<tr height="2px"></tr>
							</table>
						</td>
					</tr>
	
	
	
					<tr height="2px"></tr>
					<tr>
						<td>
							<table class="InputTable">
								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.pawnadvanace" />&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td width="20%">
													<input property="pawnadvanace" Id="pawnadvanace" value="0.00" size="17" style="text-align: right"
														readonly="readonly" class="READONLYINPUT" />
												</td>
												<td>
													<table cellpadding="0" cellspacing="0">
														<tr>
															<td style="width: 255px" align="right">
																<bean:message bundle="lable" key="screen.marketvalue" />&nbsp;
															</td>
															<td>
																<input property="marketvalue" Id="marketvalue"
																	value="0.00" size="15" style="text-align: right"
																	readonly="readonly" class="READONLYINPUT" />
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
	
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.actualdisbursment" />&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td width="20%">
	
													<input property="actualdisbursment" Id="actualdisbursment" value="0.00" size="17" style="text-align: right"
														readonly="readonly" class="READONLYINPUT" />
												</td>
												<td>
													<table cellpadding="0" cellspacing="0">
														<tr>
															<td style="width: 255px" align="right">
																<bean:message bundle="lable" key="screen.totalnetweight" />&nbsp;
															</td>
															<td>
																<input property="totalweight" Id="totalweight" value="" size="15" style="text-align: center;" readonly="readonly"
																	class="READONLYINPUT" />
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
	
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.ticketdate" />&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td width="20%">
													<input property="ticketdate" Id="ticketdate" value="" size="17" style="text-align: left" readonly="readonly"
														class="READONLYINPUT" />
												</td>
												<td>
													<table cellpadding="0" cellspacing="0">
														<tr>
															<td style="width: 255px" align="right">
																<bean:message bundle="lable" key="screen.authorizedate" />&nbsp;
															</td>
															<td>
																<input property="authorizedate" Id="authorizedate" value="" size="15" style="text-align: left"
																	readonly="readonly" class="READONLYINPUT" />
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
	
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.expairydate" />&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td width="20%">
													<input property="expairydate" Id="expairydate" size="17" readonly="readonly"
														class="READONLYINPUT" />
												</td>
												<td>
													<table cellpadding="0" cellspacing="0">
														<tr>
															<td style="width: 255px" align="right">
																<bean:message bundle="lable" key="screen.printeddate" /> &nbsp;
															</td>
															<td>
																<input property="printeddate" Id="printeddate" size="15" readonly="readonly"
																	class="READONLYINPUT" />
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
	
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.scheme" />&nbsp;
									</td>
									<td style="padding:0px">
									    <input type="hidden" id="schemeId" name="schemeId" value="0">
									    <input type="text" id="schemeCode" name="schemeCode" style="width: 50px" maxlength="3" tabindex="3"
											onfocus="clearDivision('divSchemeCode')"
											/>
									    <input type="text" id="schemeDesc" name="schemeDesc" size="50" class="READONLYINPUT"/>
										<input name="button" type="button" id="ButtonSchemaSearch"  value="..." />                                 
									    <font color="red">*</font><br/>
									    <div id="divSchemeCode" class="validate"/>
								    </td>
								</tr>
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.interestCode" />&nbsp;
									</td>
									<td>									
										<input type="hidden" id="interestRateId" name="interestRateId" value="0">
										<input type="text"  id="interestRate" name="interestRate" style="width: 50px" maxlength="3" class="READONLYINPUT" tabindex="-1"/>
										<input type="button" id="interestRateBtn" value="...">
										<input type="hidden" id="period" name="period" value="0">
									</td>
								</tr>
								<tr height="2px"></tr>
							</table>
						</td>
					</tr>
					<tr height="2px"></tr>
					<tr>
						<td>
							<table class="InputTable">
								<tr>
									<td colspan="4" align="center">
										<script>
											var myColumns = ["<bean:message bundle="lable" key="screen.renew"/>",
															 "<bean:message bundle="lable" key="screen.article"/>",
															 "<bean:message bundle="lable" key="screen.netweight"/>",
															 "<bean:message bundle="lable" key="screen.disbursevalue"/>",
															 "<bean:message bundle="lable" key="screen.noofitems"/>",
															 "<bean:message bundle="lable" key="screen.tiketarticleid"/>"];
															
									 		var str = new AW.Formats.String; 
									 		var cellFormat = [str,str,str,str,str,str];
								     		var gridTicket = createEditBrowser(myColumns,'forthGrid',cellFormat);			                    						                    						                    			
								            document.write(gridTicket);
								          </script>
									</td>
								</tr>
							</table>
						</td>
					</tr>
	
					<tr height="2px"></tr>
					<tr>
						<td>
							<table class="InputTable">
								<tr>
									<input type="hidden" name="interestAmt" id="interestAmt" value="0">
									<td colspan="4" align="center">
										<script>
											var myColumns = ["<bean:message bundle="lable" key="screen.duetype"/>",
															 "<bean:message bundle="lable" key="screen.totalamt"/>",
															 "<bean:message bundle="lable" key="screen.totpaid"/>",
															 "<bean:message bundle="lable" key="screen.outstand"/>"];
															
									 		var str = new AW.Formats.String; 
									 		var cellFormat = [str,str,str,str];
								     		var gridDue = createBrowser(myColumns,'fifthGrid',cellFormat);			                    						                    						                    			
								            document.write(gridDue);
								          </script>
									</td>
								</tr>
								<tr>
									<td width="38%" align="center">
										Grand Total
									</td>
									<td width="20%">
										<input property="dueTotal" Id="dueTotal" value="0.00" size="20"
											style="text-align: right" readonly="readonly"
											class="READONLYINPUT" />
									</td>
									<td>
										<input property="totPaid" Id="totPaid" value="0.00" size="20"
											style="text-align: right" readonly="readonly"
											class="READONLYINPUT" />
									</td>
									<td>
										<input property="balanceamt" Id="balanceamt" value="0.00"
											size="20" style="text-align: right" readonly="readonly"
											class="READONLYINPUT" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
	
	
	
	
	
					<tr height="2px"></tr>
					<tr>
						<td>
							<table class="InputTable">
	
	
								<tr height="1px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.totalreceipts" />
										&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td width="20%">
													<input property="totalreceipts" Id="totalreceipts" 
														value="0.00" size="17" style="text-align: right"
														readonly="readonly" class="READONLYINPUT" />
	
												</td>
	
												<td>
													<table cellpadding="0" cellspacing="3">
														<tr>
															<td style="width: 5px" align="left">
																<input id="ButtonReceipts" type="button" value="..." />
															</td>
	
														</tr>
													</table>
												</td>
	
	
												<td>
													<table cellpadding="0" cellspacing="0">
														<tr style="visibility: hidden">
															<td style="width: 220px" align="right">
																<bean:message bundle="lable" key="screen.voucherno" />
																&nbsp;
															</td>
															<td>
																<input property="voucherno" Id="voucherno" value=""
																	size="15" style="text-align: left" readonly="readonly"
																	class="READONLYINPUT" />
															</td>
	
															<td>
																<table cellpadding="0" cellspacing="3">
																	<tr>
																		<td style="width: 5px" align="left">
																			<input id="Buttonvoucher" type="button" value="..." />
																		</td>
	
																	</tr>
																</table>
															</td>
	
														</tr>
													</table>
												</td>
	
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr height="2px"></tr>
				</table>
	
				<table class="toolTable" id="toolbar">
					<tr>
						<td align="right">	
							<input type="button" value="<bean:message bundle="button" key="button.renew"/>" id="Check" class="buttonNormal" onclick='confirms();'/>					
							<input type="button" value="<bean:message bundle="button" key="button.clear"/>" id="Clear" class="buttonNormal" onclick="clearAll();" />
						</td>
					</tr>
				</table>
				
				<div id="interest-serchDiv" class="x-hidden">
		        	<div class="x-window-header">
		            	Interest Slabs
		            </div>
		        	<div id="serch-tab6">
		            	<div class="x-tab" title="Search">
							<table style="width: 600px">							
								<tr>
									<td>
										<script>
											var mySecColumns = ["<bean:message bundle="lable" key="screen.index"/>",
																"<bean:message bundle="lable" key="screen.noDaysFrom"/>",
								  								"<bean:message bundle="lable" key="screen.noDaysTo"/>",
								  								"<bean:message bundle="lable" key="screen.interestRate"/>"];
				            				var strForth = new AW.Formats.String; 
				            				var secCellFormat = [strForth,strForth,strForth,strForth];
			                    			var ForthGrid = createBrowser(mySecColumns,'popupGridInterest',secCellFormat);			                    						                    						                    			
			                                document.write(ForthGrid);			                                
										</script>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
				
				<div id="receipt-serchDiv" class="x-hidden">
		        	<div class="x-window-header">
		            	Receipt Detais
		            </div>
		        	<div id="serch-receipt">
		            	<div class="x-tab" title="Search">
							<table style="width: 600px">							
								<tr>
									<td>
										<script>
											var myRecColumns  = ["<bean:message bundle="lable" key="screen.receiptno"/>",
																"<bean:message bundle="lable" key="screen.receiptdate"/>",
								  								"<bean:message bundle="lable" key="screen.receiptamt"/>",
								  								"<bean:message bundle="lable" key="screen.username"/>",
								  								"<bean:message bundle="lable" key="screen.remark"/>"];
				            				var strRec 		  = new AW.Formats.String; 
				            				var RecCellFormat = [strRec,strRec,strRec,strRec,strRec];
			                    			var RecGrid = createBrowser(myRecColumns,'popupGridReceipt',RecCellFormat);			                    						                    						                    			
			                                document.write(RecGrid);
			                                RecGrid.onSelectedRowsChanged = function(row){	
			                                	if(row!='') {
			                                		setGridData(DueRecGrid,eval(RecGrid.getCellText(6,row)));	
			                                	}
			                                }	
										</script>
									</td>
								</tr>
								
								<tr height="5px"></tr>
								<tr>
									<td>
										<script>
											var myDueRecColumns  = ["<bean:message bundle="lable" key="screen.duetype"/>",
																	"<bean:message bundle="lable" key="screen.referenceno"/>",
								  									"<bean:message bundle="lable" key="screen.dueamount"/>"];				            				
				            				var DueRecCellFormat = [strRec,strRec,strRec];
			                    			var DueRecGrid = createBrowser(myDueRecColumns,'popupGridDueReceipt',DueRecCellFormat);			                    						                    						                    			
			                                document.write(DueRecGrid);
										</script>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
				
				<!-- schema div -->
				<div id="scheme-serchDiv" class="x-hidden">
					<div class="x-window-header">
						Search Scehme
					</div>
					<div id="search-tab4">
						<div class="x-tab" title="Search">
							<table style="width: 600px">
								<tr>
									<td>
										<script>
											var myColumnsScheme = ["<bean:message bundle="lable" key="screen.code"/>","<bean:message bundle="lable" key="screen.description"/>"];
											var cellFormatScheme = [str,str];
											var gridSerchScheme = createBrowser(myColumnsScheme,'popupGrid3',cellFormatScheme);
											gridSerchScheme.setHeaderHeight(25);
											document.write(gridSerchScheme);
											gridSerchScheme.onRowDoubleClicked = function(event, row){
												//alert("gridSerchScheme.onRowDoubleClicked");
												try{
													$('schemeId').value   = this.getCellText(9,row);
													$('schemeCode').value = this.getCellText(0,row);
													$('schemeDesc').value = this.getCellText(1,row);
													winSchemeDirtyFlag = true;
													winScheme.hide();
													getSchemeReletedData();
												}catch(error){}
											};
											gridSerchScheme.onSelectedRowsChanged=function(row){
												//alert("gridSerchScheme.onSelectedRowsChanged");
												try{
													if(row!=''){
														$('schemeId').value   = this.getCellText(9,row);
														$('schemeCode').value = this.getCellText(0,row);
														$('schemeDesc').value = this.getCellText(1,row);
														winSchemeDirtyFlag = true;
													}
												}catch(error){}
											}
										</script>
									</td>
								</tr>
							</table>
						</div>
					</div>
			</div>

	
			</html:form>
			<jsp:include flush="true" page="TicketBrowser.jsp"></jsp:include>
		</logic:equal>
		
		<script type="text/javascript">
			var winInterest;
			var winReceipt;
			var winScheme;
			
			Ext.onReady(function(){
				var buttonSlab = Ext.get('interestRateBtn');				
				
			    buttonSlab.on('click', function(){
			        if(!winInterest){
			            winInterest = new Ext.Window({
			                el:'interest-serchDiv',
			                layout:'fit',
			                width:600,
			                height:300,
			                closable:false, 
			                closeAction:'hide',
			                plain: true,
			
			                items: new Ext.TabPanel({
			                    el: 'serch-tab6',
			                    autoTabs:true,
			                    activeTab:0,
			                    deferredRender:false,

			                    border:false
			                }),
			
			                buttons: [{
			                    text: 'Ok',
			                    handler: function(){				                   		
			                        winInterest.hide();
			                   	}
			                }]
			            });
			        }
			        winInterest.show(this);
			        data = "dispatch=getSlabs&conditions=interestSlabCode&value=" + $('interestRateId').value; 
					var mySearchRequest = new ajaxObject('interestRatesService.do');
					mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
						if (responseStatus==200) {						
							var message =  eval('(' + responseText + ')');
							if(message['error']){
								alert(message['error']);
							}else{											
								setGridData(ForthGrid,message);					
							}	
						}else {
				    	    alert(responseStatus + ' -- Error Processing Request'); 
					    }
					}
					mySearchRequest.update(data,'POST');
			    });
			    
			    var buttonRec = Ext.get('ButtonReceipts');
			    
			    buttonRec.on('click', function(){
			        if(!winReceipt){
			            winReceipt = new Ext.Window({
			                el:'receipt-serchDiv',
			                layout:'fit',
			                width:600,
			                height:310,
			                closable:false, 
			                closeAction:'hide',
			                plain: true,
			
			                items: new Ext.TabPanel({
			                    el: 'serch-receipt',
			                    autoTabs:true,
			                    activeTab:0,
			                    deferredRender:false,
			                    border:false
			                }),
			
			                buttons: [{
			                    text: 'Ok',
			                    handler: function(){				                   		
			                        winReceipt.hide();
			                   	}
			                }]
			            });
			        }
			        winReceipt.show(this);
			        data = "dispatch=getReceiptDetails&ticketId=" + $('ticketId').value; 
					var mySearchRequest = new ajaxObject('infoconsoleService.do');
					mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
						if (responseStatus==200) {						
							var message =  eval('(' + responseText + ')');
							if(message['error']){
								alert(message['error']);
							}else{											
								setGridData(RecGrid,message);					
							}	
						}else {
				    	    alert(responseStatus + ' -- Error Processing Request'); 
					    }
					}
					mySearchRequest.update(data,'POST');
			     });
				 
				// TODO : God Help us on this! : duplicated codes all over the place now !!!!
				// need to come up with majour design to handle popus from single central code!!!!!!
				var buttonSchema = Ext.get('ButtonSchemaSearch');
				buttonSchema.on('click', function(){
					//alert("hit on buttonSchemaSearch");
					if(!winScheme){
						winScheme = new Ext.Window({
							el:'scheme-serchDiv',
							layout:'fit',
							width:600,
							height:300,
							closable:false,
							closeAction:'hide',
							plain: true,

							items: new Ext.TabPanel({
								el: 'search-tab4',
								autoTabs:true,
								activeTab:0,
								deferredRender:false,
								border:false
							}),

							buttons: [{
								text: 'Ok',
								handler: function(){
									winScheme.hide();
									alert("1");
									getSchemeReletedData();
									alert("3");
								}
							}]
						});
					}
					winScheme.show(this);
					
					//productId = 1 and active list of schemas as isActive = 1
					data = "&conditions=isActive&value=1";
					getSearchData(gridSerchScheme,'schemeService.do',data);
				});				 
			});				
		</script>
	</body>
</html:html>
