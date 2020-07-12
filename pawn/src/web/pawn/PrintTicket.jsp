<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
	<head>
		<title>'PrintTicket.jsp'</title>
		<link rel="StyleSheet" type="text/css" href="<html:rewrite forward="commonCSS"/>"></link>
		<link rel="StyleSheet" type="text/css" href="<html:rewrite forward="awCSS"/>"></link>
		<link rel="StyleSheet" type="text/css" href="css/com-all.css"></link>
		<script type="text/javascript" src="<html:rewrite forward="commonJS"/>"></script>
		<script type="text/javascript" src="<html:rewrite forward="ajaxJS"/>"></script>
		<script type="text/javascript" src="<html:rewrite forward="awJS"/>"></script>
		<script type="text/javascript" src="js/com-all.js"></script>

		<script type="text/javascript">
			window.parent.document.getElementById('footer').style.display="none";			
			window.parent.document.getElementById("mainbody").height="600px";
			window.parent.document.getElementById("footer").height="0px";
			
			function printDocument(){
				var ticketId     = document.getElementById('ticketId').value;
				var ticketNumber = document.getElementById('ticketNumber').value;
				var productId = ticketNumber.substring(3,5);
				if(ticketId>0){		
					if(productId == "PW"){
						url = "printTicketService.do?dispatch=print&reportName=/pawn/reports/Ticket&headless=false&jpIntTicketId=" + ticketId;
					}else{
						url = "printTicketService.do?dispatch=print&reportName=/pawn/reports/Ticket_gl&headless=false&jpIntTicketId=" + ticketId;
					}
					theHeight=500;
					theWidth=800;
					var theTop=(screen.height/2)-(theHeight/2);
					var theLeft=(screen.width/2)-(theWidth/2);
					var features='height='+theHeight+',width='+theWidth+',top='+theTop+',left='+theLeft+",status=yes,scrollbars=no,resizable=yes";
					window.open(url,"PrintPreview",features);
				}else{
					alert('Please select a Ticket to print.');
				}	
		    }
		    
		    
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
				//obj.onSelectedRowsChanged= function(row){};				
				return obj;
			}
			
			function setGridData(gridObj,Data){
				gridObj.setRowCount(Data.length);
				gridObj.setCellText(Data);
				gridObj.setSelectedRows([]);
				gridObj.refresh();
			}
			
			function getTicketData(){
				
			}
			
			function getSerchData(rurl,gridobj){
				data = "dispatch=getAjaxData";	
				var mySearchRequest = new ajaxObject(rurl);
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
					if (responseStatus==200) {						
						var message =  eval('(' + responseText + ')');						
						setGridData(gridobj,message);						
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request'); 
				    }
				}
				mySearchRequest.update(data,'POST');
			}
		</script>

		<style>
			table.toolTable { 
			    border: #91a7b4 1px solid;
			    width: 740px;
			    height:30px;
			    background-color:#FFFFFF;
			    bottom: 0px;
			    position: absolute;
			}	
			
			#thirdGrid {height: 310px;width:720px;}
			#thirdGrid .aw-row-selector {text-align: center}
			#thirdGrid .aw-column-0 {width: 120px;}
			#thirdGrid .aw-column-1 {width: 555px;}	
			#thirdGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#thirdGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
		</style>
	</head>

	<body>
		<html:form action="printTicketService.do">		
			<input type="hidden" name="ticketId" id="ticketId">
			
			<table border="0">								                          	
				<tr height="5px"></tr>					
					<tr>
						<td>							
							<table class="InputTable">    
								<tr height="2px"></tr>
											                     
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
														onblur="upperCase('ticketNumber');commonSearch('infoconsoleService.do','ticketId','ticketNumber','productId','getTicket','divTicketno',function(){getInfoData();},'',function(){clearOtherData();})"/>
													<input id="ButtonTicketSerch" type="button" value="..." />
													<br/><div id="divTicketno" class="validate"/>
												</td>
											</tr>
											<tr height="2px"></tr>
										</table>
									</td>
								</tr>
								
			                    <tr height="2px"></tr>														
							</table>
						</td>
					</tr>
			</table>
		</html:form>	
		
		<jsp:include flush="true" page="TicketBrowser.jsp"></jsp:include>
				
		<table class="toolTable" id="toolbar">
			<tr>
				<td align="center">
					<input type='button' value="<bean:message bundle="button" key="button.print"/> " class="buttonNormal" id="Print" onclick="printDocument()"/> 					
				</td>
			</tr>
		</table>
	</body>	
</html:html>
