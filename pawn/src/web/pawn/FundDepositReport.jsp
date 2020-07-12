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
		
			window.parent.document.getElementById('footer').style.display="none";			
			window.parent.document.getElementById("mainbody").height="600px";
			window.parent.document.getElementById("footer").height="0px";
									
			var action = 1;
			var aproodDate='';
			var url = 'fundDepositReportService.do';
			
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
			
			
			
			function getSummaryData(){
				data = "dispatch=getAjaxMasterData";
				var mySearchRequest = new ajaxObject(url);
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
					if (responseStatus==200) {						
						var message =  eval('(' + responseText + ')');
						if(message['error']){
							alert(message['error']);
						}else{
							$('totaltickts').value		 = message[0];
							$('totalticktsamt').value 	 = message[1];							
							$('totalreceipts').value     = message[2];
							$('totalreceiptsamt').value  = message[3];
							$('fundlimit').value    	 = message[4];
							$('fundavailable').value  	 = message[5];	
							$('depositdate').value  	 = message[6];	
							$('depositamount').value  	 = message[7];										
						}	
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request '); 
				    }
				}
				mySearchRequest.update(data,'POST');
			}
			function getGridData(){
				data = 'dispatch=getAjaxData&conditions=approvedBy&value='+ 0;
				var mySearchRequest = new ajaxObject(url);
				
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
					
					if (responseStatus==200) {		
						var message =  eval('(' + responseText + ')');
						if(message['error']){
							alert(message['error']);
						}else{	
							
							setGridData(grid,message);
							getSummaryData();			
						}	
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request '); 
				    }
				}
				mySearchRequest.update(data,'POST');
			}
				
			
							
			function getSerchData(rurl,gridobj){
				data = 'dispatch=getAjaxData';
				
				var mySearchRequest = new ajaxObject(rurl);
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
					if (responseStatus==200) {						
						var message =  eval('(' + responseText + ')');						
						setGridData(gridobj,message);						
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request 4'); 
				    }
				}
				mySearchRequest.update(data,'POST');
			}
		
			function printDocument(){
			
								
			    	url = "fundDepositReportService.do?dispatch=print";
					theHeight=500;
					theWidth=800;
					var theTop=(screen.height/2)-(theHeight/2);
					var theLeft=(screen.width/2)-(theWidth/2);
					var features='height='+theHeight+',width='+theWidth+',top='+theTop+',left='+theLeft+",status=yes,scrollbars=no,resizable=yes";
					window.open(url,"PrintPreview",features);
		    }
			
			
		</script>
		
		<style>
			#firstGrid {height: 250px;width:720px;}
			#firstGrid .aw-row-selector {text-align: center}
			#firstGrid .aw-column-0 {width: 100px;}
			#firstGrid .aw-column-1 {width: 150px;}	
			#firstGrid .aw-column-2 {width: 120px;text-align: right;}
			#firstGrid .aw-column-3 {width: 120px;}	
			#firstGrid .aw-column-4 {width: 120px;}
			#firstGrid .aw-column-5 {width: 120px;text-align: center;}
			#firstGrid .aw-column-6 {width: 120px;text-align: right;}
			#firstGrid .aw-column-7 {width: 120px;text-align: center;}
			#firstGrid .aw-column-8 {width: 120px;text-align: right;}
			#firstGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#firstGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
			
			#secondGrid {height: 210px;width:600px;}
			#secondGrid .aw-row-selector {text-align: center}
			#secondGrid .aw-column-0 {width: 100px;}
			#secondGrid .aw-column-1 {width: 455px;}	
			#secondGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#secondGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
			
		</style>
  	</head>
  
  	<body>
    	<div id="screenCont" class="enableAll" align="center" ></div>
    	
    	<!-- Create record -->
    	<logic:equal name="fundDepositReport" property="action" value="create">
			<html:form action="fundDepositReportService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
				<br/>
				<table border="0">
					<tr>
						<td>							
							<table class="InputTable">																	
								<tr>									
									<td colspan="2" align="center">
										<script>
											var myColumns = ["<bean:message bundle="lable" key="screen.depositno"/>",
															 "<bean:message bundle="lable" key="screen.depositdate"/>",
															 "<bean:message bundle="lable" key="screen.depositamount"/>",
															 "<bean:message bundle="lable" key="screen.approvedby"/>",
															 "<bean:message bundle="lable" key="screen.approveddate"/>",
															 "<bean:message bundle="lable" key="screen.totaltickts"/>",
															 "<bean:message bundle="lable" key="screen.totalticktsamt"/>",
															 "<bean:message bundle="lable" key="screen.totalreceipts"/>",
															 "<bean:message bundle="lable" key="screen.totalreceiptsamt"/>"];
				            				var str = new AW.Formats.String; 
				            				
				            				var cellFormat = [str,str,str,str,str,str,str,str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);			                    						                    						                    			
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){	
			                                	if(row!='') { 			                                	
			                                		$('recordId').value     	= grid.getCellText(11,row);
			                                		$('version').value      	= grid.getCellText(12,row);
			                                	}																						
			                                };
			                                getGridData();
			                            </script>
			                        </td>
			                    </tr>            
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
										<bean:message bundle="lable" key="screen.totaltickts"/>&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>	
												<td>
													
													<input property="totaltickts" Id="totaltickts"
														value="0" size="15" style="text-align: right"
														readonly="readonly" class="READONLYINPUT" />									
												</td>
											</tr>
										</table>
									</td>
									
									<td>
										<table cellpadding="0" cellspacing="0">
											<tr>
												<td style="width: 255px" align="right">
													<bean:message bundle="lable" key="screen.totalticktsamt" />&nbsp;
												</td>
												<td>
													<input property="totalticktsamt" Id="totalticktsamt"
														value="0.00" size="15" style="text-align: right"
														readonly="readonly" class="READONLYINPUT" />
												</td>
											</tr>
										</table>
									</td>
								</tr>
							   
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.totalreceipts"/>&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>	
												<td>
												
													<input property="totalreceipts" Id="totalreceipts"
														value="0" size="15" style="text-align: right"
														readonly="readonly" class="READONLYINPUT" />	
												</td>
											</tr>
										</table>
									</td>
									
									<td>
										<table cellpadding="0" cellspacing="0">
											<tr>
												<td style="width: 255px" align="right">
													<bean:message bundle="lable" key="screen.totalreceiptsamt" />&nbsp;
												</td>
												<td>
													<input property="totalreceiptsamt" Id="totalreceiptsamt"
														value="0.00" size="15" style="text-align: right"
														readonly="readonly" class="READONLYINPUT" />
												</td>
											</tr>
										</table>
									</td>
								</tr>
															
								<tr height="5px"></tr> 								 
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.fundavailable"/>&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>	
												<td>
												
													<input property="fundavailable" Id="fundavailable"
														value="0.00" size="15" style="text-align: right"
														readonly="readonly" class="READONLYINPUT" />
												</td>
											</tr>
										</table>
									</td>
									
									<td>
										<table cellpadding="0" cellspacing="0">
											<tr>
												<td style="width: 255px" align="right">
													<bean:message bundle="lable" key="screen.fundlimit" />&nbsp;
												</td>
												<td>
													<input property="fundlimit" Id="fundlimit"
														value="0.00" size="15" style="text-align: right"
														readonly="readonly" class="READONLYINPUT" />
												</td>
											</tr>
										</table>
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
							<!--  
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.depositno"/>&nbsp;
									</td>
									<td>
										<html:text property="depositno" styleId="depositno" size="15" maxlength="12" disabled="true" styleClass="READONLYINPUT" readonly="true"
											onfocus="clearDivision('divDepositNo')"/>										
										<font color="red">*</font><br/>
										<div id="divDepositNo" class="validate"/>
									</td>
								</tr>
							    -->
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.depositdate"/>&nbsp;
									</td>
									<td>
										<!-- <html:text property="depositdate" size="15" maxlength="12" styleId="depositdate" onkeypress="DateFormat(this,event);" onfocus="clearDivision('divDate')" onkeyup="DateFormat(this,event);" /><img src="images/none.gif" width="5px" ><input type="button" value="..." onclick="return showCalendar('depositdate');" />
										-->
										<input property="depositdate" Id="depositdate"
														value="" size="15" style="text-align: right"
														readonly="readonly" class="READONLYINPUT" />
										<div id="divDate" class="validate"/>
									</td>
								</tr>	
															
								<tr height="5px"></tr> 								 
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.depositamount"/>&nbsp;
									</td>
									<td>
										<html:text property="depositamount" styleId="depositamount" size="15" maxlength="10"  disabled="true" styleClass="READONLYINPUT" readonly="true"
											onfocus="clearDivision('divAmount')" style="text-align: right"
											onkeyup="this.value=formatNumber(unformatNumber(this.value))"/>										
											<div id="divAmount" class="validate"/>
									</td>
								</tr>	
								
								<tr height="5px"></tr>															
							</table>
						</td>
					</tr>
				</table>
				
				<table class="toolTable" id="toolbar">
				<tr>
					<td align="center">
						<input type='button' value="<bean:message bundle="button" key="button.print"/> " class="buttonNormal" id="Print" onclick="printDocument()"/> 					
					</td>
				</tr>
			</table>
				
			</html:form>					
    	</logic:equal>    	
    	
    </body>
</html:html>
