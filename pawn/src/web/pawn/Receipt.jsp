<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
	<head>
		<link rel="StyleSheet" type="text/css"	href="<html:rewrite forward="commonCSS"/>"></link>
		<link rel="StyleSheet" type="text/css"	href="<html:rewrite forward="awCSS"/>"></link>
		<link rel="StyleSheet" type="text/css" href="css/com-all.css"></link>
		<script type="text/javascript" src="<html:rewrite forward="commonJS"/>"></script>
		<script type="text/javascript" src="<html:rewrite forward="ajaxJS"/>"></script>
		<script type="text/javascript" src="<html:rewrite forward="awJS"/>"></script>
		<script type="text/javascript" src="js/com-all.js"></script>

		<script type="text/javascript">								
			var action = 1;
			
			window.parent.document.getElementById('footer').style.display="none";			
			window.parent.document.getElementById("mainbody").height="600px";
			window.parent.document.getElementById("footer").height="0px";
			
			var url = 'receiptService.do';
		
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
			
			function setGridData(gridObj,Data){
				gridObj.setRowCount(Data.length);
				gridObj.setCellText(Data);
				gridObj.setSelectedRows([]);
				gridObj.refresh();
			}
			
			function clearAll(){								
				$('clientId').value 		 	= '0';
				$('clientcode').value 			= '';
				$('clientName').value 			= '';	
				$('clientAddress').value		= '';			
				$('ticketId').value 			= '0';
				$('ticketNumber').value 		= '';
				$('receiptNo').value 			= '';
				$('receiptamt').value 			= '0.00';
				$('TotDueAmt').value 			= '0.00';
				$('remark').value 				= '';
				$('receiptNo').value			= '';
				$('divTicketno').innerHTML 		= '';
				$('divreceiptamt').innerHTML	= '';
				$('Check').disabled  = false;
				setGridData(grid,[]);
			}
			
			function print(){								
				var receiptNo	=$('receiptNo').value;
				var ticketNo	=$('ticketNumber').value;
				var clientId	=$('clientId').value;
				var amount		=unformatNumber($('receiptamt').value);				
				if(receiptNo=='')
					alert('Receipt No is required.');
				else{	
						var url1 = 'receiptService.do?dispatch=print&receiptNo='+receiptNo+'&ticketNo='+ticketNo+'&clientId='+clientId+'&amount='+amount;
						theHeight=500;
						theWidth=800;
						var theTop=(screen.height/2)-(theHeight/2);
						var theLeft=(screen.width/2)-(theWidth/2);
						var features='height='+theHeight+',width='+theWidth+',top='+theTop+',left='+theLeft+",status=yes,scrollbars=no,resizable=yes";
						window.open(url1,"PrintPreview",features);
				}
				$('screenCont').className = 'enableAll';	
			}
			
			function getTicketData(){
				data = "dispatch=getAjaxData&ticketId=" + $('ticketId').value + "&receiptType=1";
				var mySearchRequest = new ajaxObject(url);
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
					if (responseStatus==200) {						
						var message =  eval('(' + responseText + ')');
						if(message['error']){
							alert(message['error']);
						}else{
							$('clientId').value        	= message['clientId'];
							$('clientcode').value 		= message['clientCode'];
							$('clientName').value    	= message['clientName'];
							$('clientAddress').value    = message['address'];	
							$('TotDueAmt').value    	= message['totalDueAmount'];							
							setGridData(grid,message['dueFromList']);
							var totDueAmt = 0;
							for(i = 0;i<message['dueFromList'].length;i++){
								totDueAmt += unformatNumber(message['dueFromList'][i][3])*1;
							}
							
							$('TotDueAmt').value = formatNumber(parseFloat(totDueAmt).toFixed(2));
						}	
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request'); 
				    }
				}
				mySearchRequest.update(data,'POST');				
			}
			
			function getCreateData(){
				var clientId 	= $('clientId').value ;				
				var ticketId	= $('ticketId').value ;
				var ticketNumber= $('ticketNumber').value;				
				var receiptamt	= unformatNumber($('receiptamt').value)*1;
				var remark		= $('remark').value; 
				var receiptType = "1";									
				var str = "&clientId=" + clientId + "&ticketId=" + ticketId +
						  "&ticketNumber=" + ticketNumber + "&receiptamt=" + receiptamt +
						  "&remark=" + remark + "&receiptType=" + receiptType;
	  
				return str;		  
			}
				
			function submitData(){
				$('screenCont').className = 'disableAll';
				if(confirmCommonSubmit('',1)){
					data = "dispatch=createReceipt" + getCreateData();										
					var mySearchRequest = new ajaxObject(url);
					mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
						if (responseStatus==200) {												
							var message =  eval('(' + responseText + ')');
							if(message['error']){
				       			alert(message['error']);
				       		}else if(message['success']){			       			
				       			alert(message['success']);
				       			$('Check').disabled  = true;
								$('receiptNo').value = message['receiptNo'];
								getTicketData();		       									
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
				}else{
					$('screenCont').className = 'enableAll';
				}	
			}
			
			function validateAmt(){
				var receiptamt 	= unformatNumber($('receiptamt').value)*1;
				var TotDueAmt	= unformatNumber($('TotDueAmt').value)*1;
				
				if(receiptamt>TotDueAmt && TotDueAmt>0){
					$('divreceiptamt').innerHTML = 'Receipt amount connot be Grater than Total Due amount.';
					return false;
				}
				return true;
			}
			
			function showValidationErrors(message){
	       		for( var i =0; i < message.length ; i++){	            	
	                 if( message[i]['ticketNumber'] )
	                    $('divTicketno').innerHTML = message[i]['ticketNumber'];
	                 else if( message[i]['receiptamt'] )
	                    $('divreceiptamt').innerHTML = message[i]['receiptamt'];
	              //  else if( message[i]['interestCode'] )
	              //      $('divinterestCode').innerHTML = message[i]['interestCode'];
	            }    
	        }
		</script>
	
		<style>
			#firstGrid {height: 200px;width:720px;}
			#firstGrid .aw-row-selector {text-align: center}
			#firstGrid .aw-column-0 {width: 250px;}	
			#firstGrid .aw-column-1 {width: 140px;text-align: right;}	
			#firstGrid .aw-column-2 {width: 150px;text-align: right;}	
			#firstGrid .aw-column-3 {width: 130px;text-align: right;}				
			#firstGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#firstGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
			
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
		<input type="hidden" id="ticketStatus" name="ticketStatus" value="9" />

			<!--<div id="hidDiv" class="hideSearchPopup"></div>-->

		<!-- Create record -->
		<logic:equal name="receipt" property="action" value="create">
			<html:form action="receiptService.do">
				<input type="hidden" id="recordId" name="recordId" />
				<input type="hidden" id="version" name="version" />

				<table border="0">
					<tr>
						<td>
							<table class="InputTable">
								<tr height="5px"></tr>							
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.receiptno" />&nbsp;
									</td>
									<td>									
										<input type="text" id="receiptNo" name="receiptNo" size="15" readonly="readonly" class="READONLYINPUT"/>										
									</td>
								</tr>
								
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.tiketno" />&nbsp;
									</td>
									<td>
										<input type="hidden" id="ticketId" name="ticketId" value="0" />
										<html:text property="ticketNumber" styleId="ticketNumber" size="18"	maxlength="13" 
											onfocus="clearDivision('divTicketno')"
											onblur="$('ticketNumber').value.toUpperCase();commonSearch('infoconsoleService.do','ticketId','ticketNumber','ticketId','getTicket','divTicketno',function(){getTicketData();},'',function(){clearAll();})"/>
											
										<input id="ButtonTicketSerch" type="button" value="..." />
										<font color="red">*</font>
										<br/><div id="divTicketno" class="validate"/>
									</td>
								</tr>
								<tr height="5px"></tr>
							</table>
						</td>
					</tr>
	
					<tr height="5px"></tr>
					<tr>
						<td>
							<table class="InputTable">
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.clientcode" />
										&nbsp;
									</td>
									<td> 
										<input type="hidden" id="clientId" name="clientId" value="0" />
										<input type="text" size="15" id="clientcode" readonly="readonly" class="READONLYINPUT"/ >
									</td>
								</tr>
								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.clientname" />&nbsp;
									</td>
									<td>
										<input type="text" size="91" id="clientName" readonly="readonly" class="READONLYINPUT" />
									</td>
								</tr>
								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.address" />&nbsp;
									</td>
									<td>
										<input type="text" size="91" id="clientAddress" readonly="readonly" class="READONLYINPUT" />
									</td>
								</tr>
								
								<tr height="5px"></tr>
							</table>
						</td>
					</tr>
	
					<tr height="5px"></tr>
					<tr>
						<td>
							<table class="InputTable">
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.receiptamt" />&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td width="20%">
													<html:text property="receiptamt" styleId="receiptamt" value="0.00" size="23" maxlength="15" style="text-align: right;width: 92px" 
														onfocus="clearDivision('divreceiptamt');this.maxLength=15;"
														onkeyup="this.value=formatNumber(unformatNumber(this.value));"		 
														onblur="if(this.value.length=15){this.maxLength=18;};if(this.value.length<=1 && (this.value.substring(0,1)=='-' || this.value=='' || this.value=='.')){this.value='0.00'};this.value=formatNumber(parseFloat(unformatNumber(this.value)).toFixed(2));"/>														
													<font color="red">*</font><br/>
													<div id="divreceiptamt" class="validate" />
												</td>
												<td>
													<table cellpadding="0" cellspacing="0">
														<tr>
															<td style="width: 338px" align="right">
																<bean:message bundle="lable" key="screen.totaldue" />&nbsp;
															</td>
															<td>
																<input property="TotDueAmt" Id="TotDueAmt" value="0.00" size="15" style="text-align: right" readonly="readonly" class="READONLYINPUT" />
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
	
								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.remark" />&nbsp;
									</td>
									<td>
										<input property="remark" Id="remark" size="80" maxlength="200" />
									</td>
								</tr>
	
								<tr height="5px"></tr>
							</table>
						</td>
					</tr>
	
					<tr height="5px"></tr>
					<tr>
						<td>
							<table class="InputTable">
								<tr>
									<td colspan="5" align="center">
										<script>
											var myColumns = ["<bean:message bundle="lable" key="screen.description"/>",
															 "<bean:message bundle="lable" key="screen.dueamount"/>",
															 "<bean:message bundle="lable" key="screen.totpaid"/>",
															 "<bean:message bundle="lable" key="screen.balancetopaid"/>"];
									 		var str = new AW.Formats.String; 
									 		var cellFormat = [str,str,str,str];
								     		var grid = createBrowser(myColumns,'firstGrid',cellFormat);			                    						                    						                    			
								            document.write(grid);
								          </script>
									</td>
								</tr>
							</table>
						</td>
					</tr>
	
					<tr height="5px"></tr>
				</table>
			
				<table class="toolTable" id="toolbar">
					<tr>
						<td align="right">
							<input type="button" value="<bean:message bundle="button" key="button.submit"/>" id="Check" class="buttonNormal" onclick='submitData();'/>
							<input type="button" value="<bean:message bundle="button" key="button.print"/>" id="Clear" class="buttonNormal" onclick="print();"/>
							<input type="button" value="<bean:message bundle="button" key="button.clear"/>" id="Clear" class="buttonNormal" onclick="clearAll();"/>
						</td>
					</tr>
				</table>

			</html:form>
			
			<jsp:include flush="true" page="TicketBrowser.jsp"></jsp:include>
									
		</logic:equal>
		
		</body>
</html:html>
