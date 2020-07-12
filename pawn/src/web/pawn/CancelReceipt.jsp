<%@ page language="java" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
  	<head>
		<link rel="StyleSheet" type="text/css" href="<html:rewrite forward="commonCSS"/>"></link>
		<link rel="StyleSheet" type="text/css" href="<html:rewrite forward="awCSS"/>"></link>
		<link rel="StyleSheet" type="text/css" href="<html:rewrite forward="calendarCSS"/>"></link>
		
		<link rel="StyleSheet" type="text/css" href="css/com-all.css"></link>
		<script type="text/javascript" src="<html:rewrite forward="commonJS"/>"></script>
		<script type="text/javascript" src="<html:rewrite forward="ajaxJS"/>"></script>
		<script type="text/javascript" src="<html:rewrite forward="awJS"/>"></script>	
		<script type="text/javascript" src="js/com-all.js"></script>
		<script type="text/javascript" src="<html:rewrite forward="calendarJS"/>"></script>
		<script type="text/javascript">								
			var action = 1;
			
			var url = 'cancelreceiptService.do';
			
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
			
			function getTicketData(){
				data = "dispatch=getAjaxData&ticketId=" + $('ticketId').value;
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
														
							setGridData(grid,message['receipts']);																				
						}	
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request'); 
				    }
				}
				mySearchRequest.update(data,'POST');				
			}
			
			function clearAll(){
				$('ticketId').value 	= '';
				$('ticketNumber').value	= '';
				$('recordId').value		= '';
				$('version').value		= '';  
				$('receiptno').value	= '';
				$('clientcode').value	= '';
				$('clientName').value	= '';
				$('clientAddress').value= '';

				$('divTicketno').innerHTML	= '';
				$('divReceiptNo').innerHTML	= '';

				setGridData(grid,[]);
			}
				
			function getCreateData(){
				var ticketId       	= $('ticketId').value;
				var ticketNumber 	= $('ticketNumber').value;
				var recordId 		= $('recordId').value;
				var version  		= $('version').value; 
				var receiptno		= $('receiptno').value;
				
				return "&ticketId=" + ticketId + "&ticketNumber=" + ticketNumber +
					   "&recordId=" + recordId + "&version=" + version + "&receiptno=" + receiptno;
			}
				
			function submitData(action){
				$('screenCont').className = 'disableAll';

				if(confirm('Confirm to cancel the receipt ?')){
					var data = "dispatch=cancelReceipt" + getCreateData();
								
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
				}else{
					$('screenCont').className = 'enableAll';
				}
			}
			
			function showValidationErrors(message){
	       		for( var i =0; i < message.length ; i++){	            	
	                if( message[i]['ticketNumber'] )
	                    $('divTicketno').innerHTML = message[i]['ticketNumber'];	
                   	else if( message[i]['receiptno'] )
	                    $('divReceiptNo').innerHTML = message[i]['receiptno'];                    
	            }    
	        }
		</script>
		
		<style>
			#firstGrid {height: 200px;width:720px;}
			#firstGrid .aw-row-selector {text-align: center}
			#firstGrid .aw-column-0 {width: 200px;}
			#firstGrid .aw-column-1 {width: 150px;text-align: right;}	
			#firstGrid .aw-column-2 {width: 150px;text-align: center;}	
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
    	<div id="screenCont" class="enableAll" align="center" ></div>
    	
		<div id="hidDiv" class="hideSearchPopup">
		</div>
    	
    	<!-- Create record -->
    	<logic:equal name="cancelreceipt" property="action" value="create">
			<html:form action="cancelreceiptService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>				
 
				<table border="0">
					<tr>
						<td>							
							<table class="InputTable" >    						
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
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.receiptno" />&nbsp;
									</td>
									<td>									
										<input type="text" id="receiptno" name="receiptno" size="15" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font>
										<br/><div id="divReceiptNo" class="validate"/>										
									</td>
								</tr>
																
								<tr height="5px"></tr>
							</table>
						</td>
					</tr>
					
					<tr height="5px"></tr>
					<tr>
						<td>							
							<table class="InputTable" >    
								<tr height="2px"></tr> 
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.clientcode"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="clientId" name="clientId" value="0"/>
										<input type="text" size="15" id="clientcode" readonly="readonly" class="READONLYINPUT"/ >
									</td>
								</tr>
								<tr height="2px"></tr>     
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.clientname"/>&nbsp;
									</td>
									<td>
										<input type="text" size="91" id="clientName" readonly="readonly" class="READONLYINPUT"/>
									</td>
								</tr>
								<tr height="2px"></tr>     
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.address"/>&nbsp;
									</td>
									<td>
										<input type="text" size="91" id="clientAddress" readonly="readonly" class="READONLYINPUT"/>
									</td>
								</tr>
								<tr height="2px"></tr>
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
											var myColumns = ["<bean:message bundle="lable" key="screen.receiptno"/>",
															 "<bean:message bundle="lable" key="screen.receiptamt"/>",															 
															 "<bean:message bundle="lable" key="screen.receiptdate"/>"];
									 		var str = new AW.Formats.String; 
									 		var cellFormat = [str,str,str];
								     		var grid = createBrowser(myColumns,'firstGrid',cellFormat);			                    						                    						                    			
								            document.write(grid);
								            grid.onSelectedRowsChanged=function(row){
												if(row!='') { 
													$('receiptno').value= this.getCellText(0,row);
													$('recordId').value = this.getCellText(3,row);
													$('version').value 	= this.getCellText(4,row);
			                                	}else{
			                                		$('receiptno').value= '';
			                                	}
											}
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
							<input type="button" value="<bean:message bundle="button" key="button.cancel"/>" id="Clear" class="buttonNormal" onclick="submitData('');"/>
							<input type="button" value="<bean:message bundle="button" key="button.clear"/>" id="Clear" class="buttonNormal" onclick="clearAll();"/>
						</td>
					</tr>
				</table>		
			</html:form>	
			
			<jsp:include flush="true" page="TicketBrowser.jsp"></jsp:include>				
    	</logic:equal>
  	</body>
</html:html>
