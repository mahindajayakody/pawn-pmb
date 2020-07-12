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
			
			var url = 'receiptRePrintService.do';
		
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
				$('receiptamt').value			= '0.00';				
				$('receiptDate').value			= '';
				$('divTicketno').innerHTML 		= '';
				$('Check').disabled  = false;
				setGridData(grid,[]);
			}
			
			function print(){								
				var receiptNo	= $('receiptNo').value;
				var ticketNo	= $('ticketNumber').value;
				var clientId	= $('clientId').value;
				var amount		= unformatNumber($('receiptamt').value);
				var	date = $('receiptDate').value;
				
				if(receiptNo=='')
					alert('Receipt No is required.');
				else{	
						var url1 = 'receiptRePrintService.do?dispatch=print&receiptNo='+receiptNo+'&ticketNo='+ticketNo+'&clientId='+clientId+'&amount='+amount+'&receiptDate='+date;
						theHeight=500;
						theWidth=800;
						var theTop=(screen.height/2)-(theHeight/2);
						var theLeft=(screen.width/2)-(theWidth/2);
						var features='height='+theHeight+',width='+theWidth+',top='+theTop+',left='+theLeft+",status=yes,scrollbars=no,resizable=yes";
						window.open(url1,"Re-PrintPreview",features);
				}
				$('screenCont').className = 'enableAll';	
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
		</script>
	
		<style>
			#firstGrid {height: 200px;width:720px;}
			#firstGrid .aw-row-selector {text-align: center}
			#firstGrid .aw-column-0 {width: 250px;}	
			#firstGrid .aw-column-1 {width: 150px;text-align: right;}	
			#firstGrid .aw-column-2 {width: 150px;text-align: right;}				
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
		<input type="hidden" id="ticketStatus" name="ticketStatus" value="1" />

		<!-- Create record -->
		<logic:equal name="receipt" property="action" value="create">
			<html:form action="receiptService.do">
				<input type="hidden" id="receiptNo" name="receiptNo" value=""/>
				<input type="hidden" id="receiptamt" name="receiptamt" value="0.00"/>
				<input type="hidden" id="receiptDate" name="receiptDate" value=""/>

				<table border="0">
					<tr>
						<td>
							<table class="InputTable">				
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
													$('receiptNo').value  = this.getCellText(0,row);
													$('receiptamt').value = this.getCellText(1,row);
													$('receiptDate').value = this.getCellText(2,row);
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
