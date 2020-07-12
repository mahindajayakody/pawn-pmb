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
		<script type="text/javascript" src="<html:rewrite forward="commonJS"/>"></script>
		<script type="text/javascript" src="<html:rewrite forward="ajaxJS"/>"></script>
		<script type="text/javascript" src="<html:rewrite forward="awJS"/>"></script>	
		<script type="text/javascript" src="<html:rewrite forward="calendarJS"/>"></script>	
		
		<script type="text/javascript">								
			var action = 1;
			
			var url = 'companyService.do';
			function loadToolBar(){				    	    
			  	if(CurrentPage(window.parent.frames['footer'].location.pathname)!='toolbar.jsp'){
			   		//open(url + '?dispatch=toolbar&evn=1:2:3','footer');
			   		open('toolbar.jsp?evn=<c:out value="${param.evn}" />','footer');			   		
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
				obj.onSelectedRowsChanged= function(row){};				
				return obj;
			}
			
			function setGridData(gridObj,Data){
				gridObj.setRowCount(Data.length);
				gridObj.setCellText(Data);
				gridObj.setSelectedRows([]);
				gridObj.refresh();
			}
			
			function getRecordData(id){
				data = "dispatch=getAjaxData&recordId=" + id;
				var mySearchRequest = new ajaxObject(url);
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
					if (responseStatus==200) {						
						var message =  eval('(' + responseText + ')');
						if(message['error']){
							alert(message['error']);
						}else{						
							$('code').value        		= message[0];
                       		$('name').value       		= message[1];
                       		$('addLine1').value        	= message[2];
                       		$('addLine2').value        	= message[3];
                       		$('addLine3').value        	= message[4];
                       		$('addLine4').value        	= message[5];
                       		$('telephoneNo').value     	= message[6];
                       		$('faxNo').value        	= message[7];
                       		$('taxNo').value        	= message[8];
                       		$('dateInstalled').value    = message[9];
                       		$('finBeginDate').value     = message[10];
                       		$('finEndDate').value       = message[11];
                       		$('recordId').value    		= message[12];
                       		$('version').value     		= message[13];
						}	
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request'); 
				    }
				}
				mySearchRequest.update(data,'POST');
			}
			
			function getGridData(){
				data = "dispatch=getAjaxData";
				var mySearchRequest = new ajaxObject(url);
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
					if (responseStatus==200) {						
						var message =  eval('(' + responseText + ')');
						if(message['error']){
							alert(message['error']);
						}else{
							setGridData(grid,message);					
						}	
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request'); 
				    }
				}
				mySearchRequest.update(data,'POST');
			}
			
			function clearAll(){
				$('code').value        		= '';
          		$('name').value       		= '';
          		$('addLine1').value        	= '';
          		$('addLine2').value        	= '';
          		$('addLine3').value        	= '';
          		$('addLine4').value        	= '';
          		$('telephoneNo').value     	= '';
          		$('faxNo').value        	= '';
          		$('taxNo').value        	= '';
          		$('dateInstalled').value    = '';
          		$('finBeginDate').value     = '';
          		$('finEndDate').value       = '';
          		$('recordId').value    		= '';
          		$('version').value      	= '';
          		$('authmode').value			= '1';
          		
				$('divCode').innerHTML 			= '';
				$('divName').innerHTML 			= '';
				$('divAddLine1').innerHTML		= '';
				$('divAddLine2').innerHTML		= '';
				$('divAddLine3').innerHTML		= '';
				$('divAddLine4').innerHTML		= '';
				$('divTelephoneNo').innerHTML 	= '';
				$('divFaxNo').innerHTML 		= '';
				$('divTaxNo').innerHTML 		= '';
				$('divDateInstalled').innerHTML = '';
				$('divFinBeginDate').innerHTML 	= '';
				$('divFinEndDate').innerHTML 	= '';
				grid.setSelectedRows([]);
			}
			
			function getCreateData(){
				var code        = $('code').value;
				var name        = $('name').value;
				var addLine1    = $('addLine1').value; 
				var addLine2    = $('addLine2').value;
				var addLine3    = $('addLine3').value;
				var addLine4    = $('addLine4').value;
				var telephoneNo = $('telephoneNo').value;
				var faxNo 		= $('faxNo').value;
				var taxNo 		= $('taxNo').value;
				var dateInstalled = $('dateInstalled').value;				
				var finBeginDate  = $('finBeginDate').value;
				var finEndDate    = $('finEndDate').value;
				var authmode    = $('authmode').value;
				
				return 	"&code=" + code + "&name=" + name +
						"&addLine1=" + addLine1 + "&addLine2=" + addLine2 +
						"&addLine3=" + addLine3 + "&addLine4=" + addLine4 +
						"&telephoneNo=" + telephoneNo + "&faxNo=" + faxNo +
						"&taxNo=" + taxNo + "&dateInstalled=" + dateInstalled +
						"&finBeginDate=" + finBeginDate + "&finEndDate=" + finEndDate +
						"&authmode=" + authmode;
			}
			
			function getChangedData(){
				var recordId = $('recordId').value;
				var version  = $('version').value;
				
				var str = getCreateData() + "&recordId=" + recordId + "&version=" + version;				
				return str;
			}
		
			function showValidationErrors(message){
	       		for( var i =0; i < message.length ; i++){	            	
	                if( message[i]['code'] )
	                    $('divCode').innerHTML = message[i]['code'];
	                else if( message[i]['name'] )
	                    $('divName').innerHTML = message[i]['name'];
	                else if( message[i]['addLine1'] )
	                    $('divAddLine1').innerHTML = message[i]['addLine1'];
	                else if( message[i]['addLine2'] )
	                    $('divAddLine2').innerHTML = message[i]['addLine2'];
	                else if( message[i]['addLine3'] )
	                    $('divAddLine3').innerHTML = message[i]['addLine3'];
	                else if( message[i]['addLine4'] )
	                    $('divAddLine4').innerHTML = message[i]['addLine4'];
	                else if( message[i]['telephoneNo'] )
	                    $('divTelephoneNo').innerHTML = message[i]['telephoneNo'];
	                else if( message[i]['faxNo'] )
	                    $('divFaxNo').innerHTML = message[i]['faxNo'];
	                else if( message[i]['taxNo'] )
	                    $('divTaxNo').innerHTML = message[i]['taxNo'];
	                else if( message[i]['dateInstalled'] )
	                    $('divDateInstalled').innerHTML = message[i]['dateInstalled'];
	                else if( message[i]['finBeginDate'] )
	                    $('divFinBeginDate').innerHTML = message[i]['finBeginDate'];
	                else if( message[i]['finEndDate'] )
	                    $('divFinEndDate').innerHTML = message[i]['finEndDate'];                                        
	            }    
	        }
		</script>
		
		<style>
			#firstGrid {height: 210px;width:720px;}
			#firstGrid .aw-row-selector {text-align: center}
			#firstGrid .aw-column-0 {width: 150px;}
			#firstGrid .aw-column-1 {width: 545px;}	
			#firstGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#firstGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
		
			th, td {
				margin:0pt;
				padding:0pt;
			}
		</style>
  	</head>
  
  	<body onload="loadToolBar()">
    	<div id="screenCont" class="enableAll" align="center" ></div>
    	
    	<!-- Create record -->
    	<logic:equal name="company" property="action" value="create">
    		<br/>
			<html:form action="companyService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
				
				<table border="0">					
					<tr>
						<td>							
							<table class="InputTable">																	
								<tr>									
									<td colspan="2" align="center">
										<script>
											var myColumns = ["<bean:message bundle="lable" key="screen.code"/>",
							  								 "<bean:message bundle="lable" key="screen.companyname"/>"];
				            				var str = new AW.Formats.String; 
				            				var cellFormat = [str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);			                    						                    						                    			
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){	
			                                	if(row!='') { 
			                                		//getRecordData(grid.getCellText(2,row));
			                                		$('code').value        		= grid.getCellText(0,row);
			                                		$('name').value       		= grid.getCellText(1,row);
			                                		$('addLine1').value        	= grid.getCellText(2,row);
			                                		$('addLine2').value        	= grid.getCellText(3,row);
			                                		$('addLine3').value        	= grid.getCellText(4,row);
			                                		$('addLine4').value        	= grid.getCellText(5,row);
			                                		$('telephoneNo').value     	= grid.getCellText(6,row);
			                                		$('faxNo').value        	= grid.getCellText(7,row);
			                                		$('taxNo').value        	= grid.getCellText(8,row);
			                                		$('dateInstalled').value    = grid.getCellText(9,row);
			                                		$('finBeginDate').value     = grid.getCellText(10,row);
			                                		$('finEndDate').value       = grid.getCellText(11,row);
			                                		$('authmode').value			= grid.getCellText(12,row);
			                                		$('recordId').value    		= grid.getCellText(13,row);
			                                		$('version').value     		= grid.getCellText(14,row);			                                		
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
										<bean:message bundle="lable" key="screen.code"/>&nbsp;
									</td>
									<td>
										<html:text property="code" styleId="code" size="5" maxlength="3"
											onblur="upperCase('code')" onfocus="clearDivision('divCode')"/>										
											<font color="red">*</font><br/>
											<div id="divCode" class="validate"/>
									</td>
								</tr>	
															
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.companyname"/>&nbsp;
									</td>
									<td>
										<html:text property="name" styleId="name" size="70" maxlength="50" 
											onfocus="clearDivision('divName')"/>										
										<font color="red">*</font><br/>
										<div id="divName" class="validate"/>
									</td>
								</tr>	
								
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.addressline1"/>&nbsp;
									</td>
									<td>
										<html:text property="addLine1" styleId="addLine1" size="70" maxlength="40" 
											onfocus="clearDivision('divAddLine1')"/>										
										<font color="red">*</font><br/>
										<div id="divAddLine1" class="validate"/>
									</td>
								</tr>
								
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.addressline2"/>&nbsp;
									</td>
									<td>
										<html:text property="addLine2" styleId="addLine2" size="70" maxlength="40" 
											onfocus="clearDivision('divAddLine2')"/>										
										<font color="red">*</font><br/>
										<div id="divAddLine2" class="validate"/>
									</td>
								</tr>
								
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.addressline3"/>&nbsp;
									</td>
									<td>
										<html:text property="addLine3" styleId="addLine3" size="70" maxlength="40" 
											onfocus="clearDivision('divAddLine3')"/>										
										<br/><div id="divAddLine3" class="validate"/>
									</td>
								</tr>
								
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.addressline4"/>&nbsp;
									</td>
									<td>
										<html:text property="addLine4" styleId="addLine4" size="70" maxlength="40" 
											onfocus="clearDivision('divAddLine4')"/>										
										<br/><div id="divAddLine4" class="validate"/>
									</td>
								</tr>
								
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.telephoneno"/>&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td width="20%">
													<html:text property="telephoneNo" styleId="telephoneNo" size="15" maxlength="10" 
														onfocus="clearDivision('divTelephoneNo')"/>										
													<font color="red">*</font><br/>
													<div id="divTelephoneNo" class="validate"/>
												</td>
												<td>
													<table cellpadding="0" cellspacing="0">
														<tr>
															<td style="width: 158px" align="right">
																<bean:message bundle="lable" key="screen.faxno"/>&nbsp;
															</td>
															<td>
																<html:text property="faxNo" styleId="faxNo" size="15" maxlength="10" 
																	onfocus="clearDivision('divFaxNo')"/>										
																<font color="red">*</font><br/>
																<div id="divFaxNo" class="validate"/>
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
										<bean:message bundle="lable" key="screen.taxno"/>&nbsp;
									</td>
									<td>
										<html:text property="taxNo" styleId="taxNo" size="15" maxlength="10" 
											onfocus="clearDivision('divTaxNo')"/>										
										<font color="red">*</font><br/>
										<div id="divTaxNo" class="validate"/>
									</td>
								</tr>
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.dateinstalled"/>&nbsp;
									</td>
									<td>
										<html:text property="dateInstalled" size="15" maxlength="12" styleId="dateInstalled" onkeypress="DateFormat(this,event);" onfocus="clearDivision('divDateInstalled')" onkeyup="DateFormat(this,event);" /><img src="images/none.gif" width="5px" ><input type="button" value="..." onclick="return showCalendar('dateInstalled');" />
										<font color="red">*</font><br/>
										<div id="divDateInstalled" class="validate"/>
									</td>
								</tr>
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.financebegindate"/>&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td width="23%">
													<html:text property="finBeginDate" size="15" maxlength="12" styleId="finBeginDate" onkeypress="DateFormat(this,event);" onfocus="clearDivision('divFinBeginDate')" onkeyup="DateFormat(this,event);" /><img src="images/none.gif" width="5px" ><input type="button" value="..." onclick="return showCalendar('finBeginDate');" />
													<font color="red">*</font><br/>
													<div id="divFinBeginDate" class="validate"/>
												</td>
												<td>
													<table cellpadding="0" cellspacing="0">
														<tr>
															<td style="width: 140px" align="right">
																<bean:message bundle="lable" key="screen.financeenddate"/>&nbsp;
															</td>
															<td>
																<html:text property="finEndDate" size="15" maxlength="12" styleId="finEndDate" onkeypress="DateFormat(this,event);" onfocus="clearDivision('divFinEndDate')" onkeyup="DateFormat(this,event);" /><img src="images/none.gif" width="5px"><input type="button" value="..." onclick="return showCalendar('finEndDate');" />
																<font color="red">*</font><br/>
																<div id="divFinEndDate" class="validate"/>
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
										<bean:message bundle="lable" key="screen.authmode"/>&nbsp;
									</td>
									<td>
										<select id="authmode" name="authmode" style="width: 70px;font-size: 12px">
											<option value="1">Dual</option>
											<option value="0">Single</option>
										</select>
									</td>
								</tr>
															
							<tr height="5px"></tr>															
						</table>
					</td>
				</tr>
				</table>
			</html:form>
			<script>
				//loadToolBar();
			</script> 					
    	</logic:equal>
    	
    	
    	<!-- update record -->
    	<logic:equal name="company" property="action" value="update">
    		<br/>
			<html:form action="companyService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
				
				<table border="0">					
					<tr>
						<td>							
							<table class="InputTable">																	
								<tr>									
									<td colspan="2" align="center">
										<script>
											var myColumns = ["<bean:message bundle="lable" key="screen.code"/>",
							  								 "<bean:message bundle="lable" key="screen.companyname"/>"];
				            				var str = new AW.Formats.String; 
				            				var cellFormat = [str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);			                    						                    						                    			
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){	
			                                	if(row!='') { 
			                                		//getRecordData(grid.getCellText(2,row));
			                                		$('code').value        		= grid.getCellText(0,row);
			                                		$('name').value       		= grid.getCellText(1,row);
			                                		$('addLine1').value        	= grid.getCellText(2,row);
			                                		$('addLine2').value        	= grid.getCellText(3,row);
			                                		$('addLine3').value        	= grid.getCellText(4,row);
			                                		$('addLine4').value        	= grid.getCellText(5,row);
			                                		$('telephoneNo').value     	= grid.getCellText(6,row);
			                                		$('faxNo').value        	= grid.getCellText(7,row);
			                                		$('taxNo').value        	= grid.getCellText(8,row);
			                                		$('dateInstalled').value    = grid.getCellText(9,row);
			                                		$('finBeginDate').value     = grid.getCellText(10,row);
			                                		$('finEndDate').value       = grid.getCellText(11,row);
			                                		$('authmode').value			= grid.getCellText(12,row);
			                                		$('recordId').value    		= grid.getCellText(13,row);
			                                		$('version').value     		= grid.getCellText(14,row);	
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
										<bean:message bundle="lable" key="screen.code"/>&nbsp;
									</td>
									<td>
										<html:text property="code" styleId="code" size="5" maxlength="3" disabled="true" styleClass="READONLYINPUT"
											onblur="upperCase('code')" onfocus="clearDivision('divCode')"/>										
											<font color="red">*</font><br/>
											<div id="divCode" class="validate"/>
									</td>
								</tr>	
															
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.companyname"/>&nbsp;
									</td>
									<td>
										<html:text property="name" styleId="name" size="70" maxlength="50" 
											onfocus="clearDivision('divName')"/>										
										<font color="red">*</font><br/>
										<div id="divName" class="validate"/>
									</td>
								</tr>	
								
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.addressline1"/>&nbsp;
									</td>
									<td>
										<html:text property="addLine1" styleId="addLine1" size="70" maxlength="40" 
											onfocus="clearDivision('divAddLine1')"/>										
										<font color="red">*</font><br/>
										<div id="divAddLine1" class="validate"/>
									</td>
								</tr>
								
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.addressline2"/>&nbsp;
									</td>
									<td>
										<html:text property="addLine2" styleId="addLine2" size="70" maxlength="40" 
											onfocus="clearDivision('divAddLine2')"/>										
										<font color="red">*</font><br/>
										<div id="divAddLine2" class="validate"/>
									</td>
								</tr>
								
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.addressline3"/>&nbsp;
									</td>
									<td>
										<html:text property="addLine3" styleId="addLine3" size="70" maxlength="40" 
											onfocus="clearDivision('divAddLine3')"/>										
										<br/><div id="divAddLine3" class="validate"/>
									</td>
								</tr>
								
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.addressline4"/>&nbsp;
									</td>
									<td>
										<html:text property="addLine4" styleId="addLine4" size="70" maxlength="40" 
											onfocus="clearDivision('divAddLine4')"/>										
										<br/><div id="divAddLine4" class="validate"/>
									</td>
								</tr>
								
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.telephoneno"/>&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td width="20%">
													<html:text property="telephoneNo" styleId="telephoneNo" size="15" maxlength="10" 
														onfocus="clearDivision('divTelephoneNo')"/>										
													<font color="red">*</font><br/>
													<div id="divTelephoneNo" class="validate"/>
												</td>
												<td>
													<table cellpadding="0" cellspacing="0">
														<tr>
															<td style="width: 158px" align="right">
																<bean:message bundle="lable" key="screen.faxno"/>&nbsp;
															</td>
															<td>
																<html:text property="faxNo" styleId="faxNo" size="15" maxlength="10" 
																	onfocus="clearDivision('divFaxNo')"/>										
																<font color="red">*</font><br/>
																<div id="divFaxNo" class="validate"/>
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
										<bean:message bundle="lable" key="screen.taxno"/>&nbsp;
									</td>
									<td>
										<html:text property="taxNo" styleId="taxNo" size="15" maxlength="10" 
											onfocus="clearDivision('divTaxNo')"/>										
										<font color="red">*</font><br/>
										<div id="divTaxNo" class="validate"/>
									</td>
								</tr>
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.dateinstalled"/>&nbsp;
									</td>
									<td>
										<html:text property="dateInstalled" size="15" maxlength="12" styleId="dateInstalled" onkeypress="DateFormat(this,event);" onfocus="clearDivision('divDateInstalled')" onkeyup="DateFormat(this,event);" /><img src="images/none.gif" width="5px" ><input type="button" value="..." onclick="return showCalendar('dateInstalled');" />
										<font color="red">*</font><br/>
										<div id="divDateInstalled" class="validate"/>
									</td>
								</tr>
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.financebegindate"/>&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td width="23%">
													<html:text property="finBeginDate" size="15" maxlength="12" styleId="finBeginDate" onkeypress="DateFormat(this,event);" onfocus="clearDivision('divFinBeginDate')" onkeyup="DateFormat(this,event);" /><img src="images/none.gif" width="5px" ><input type="button" value="..." onclick="return showCalendar('finBeginDate');" />
													<font color="red">*</font><br/>
													<div id="divFinBeginDate" class="validate"/>
												</td>
												<td>
													<table cellpadding="0" cellspacing="0">
														<tr>
															<td style="width: 140px" align="right">
																<bean:message bundle="lable" key="screen.financeenddate"/>&nbsp;
															</td>
															<td>
																<html:text property="finEndDate" size="15" maxlength="12" styleId="finEndDate" onkeypress="DateFormat(this,event);" onfocus="clearDivision('divFinEndDate')" onkeyup="DateFormat(this,event);" /><img src="images/none.gif" width="5px" ><input type="button" value="..." onclick="return showCalendar('finEndDate');" />
																<font color="red">*</font><br/>
																<div id="divFinEndDate" class="validate"/>
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
										<bean:message bundle="lable" key="screen.authmode"/>&nbsp;
									</td>
									<td>
										<select id="authmode" name="authmode" style="width: 70px;font-size: 12px">
											<option value="1">Dual</option>
											<option value="0">Single</option>
										</select>
									</td>
								</tr>
															
							<tr height="5px"></tr>															
						</table>
					</td>
				</tr>
				</table>
			</html:form>
    	</logic:equal>
    	
    	<!-- Delete record -->
    	<logic:equal name="company" property="action" value="delete">
    		<br/>
			<html:form action="companyService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
				
								<table border="0">					
					<tr>
						<td>							
							<table class="InputTable">																	
								<tr>									
									<td colspan="2" align="center">
										<script>
											var myColumns = ["<bean:message bundle="lable" key="screen.code"/>",
							  								 "<bean:message bundle="lable" key="screen.companyname"/>"];
				            				var str = new AW.Formats.String; 
				            				var cellFormat = [str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);			                    						                    						                    			
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){	
			                                	if(row!='') { 
			                                		//getRecordData(grid.getCellText(2,row));
			                                		$('code').value        		= grid.getCellText(0,row);
			                                		$('name').value       		= grid.getCellText(1,row);
			                                		$('addLine1').value        	= grid.getCellText(2,row);
			                                		$('addLine2').value        	= grid.getCellText(3,row);
			                                		$('addLine3').value        	= grid.getCellText(4,row);
			                                		$('addLine4').value        	= grid.getCellText(5,row);
			                                		$('telephoneNo').value     	= grid.getCellText(6,row);
			                                		$('faxNo').value        	= grid.getCellText(7,row);
			                                		$('taxNo').value        	= grid.getCellText(8,row);
			                                		$('dateInstalled').value    = grid.getCellText(9,row);
			                                		$('finBeginDate').value     = grid.getCellText(10,row);
			                                		$('finEndDate').value       = grid.getCellText(11,row);
			                                		$('authmode').value			= grid.getCellText(12,row);
			                                		$('recordId').value    		= grid.getCellText(13,row);
			                                		$('version').value     		= grid.getCellText(14,row);	
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
										<bean:message bundle="lable" key="screen.code"/>&nbsp;
									</td>
									<td>
										<html:text property="code" styleId="code" size="5" maxlength="3" disabled="true" styleClass="READONLYINPUT"
											onblur="upperCase('code')" onfocus="clearDivision('divCode')"/>										
											<font color="red">*</font><br/>
											<div id="divCode" class="validate"/>
									</td>
								</tr>	
															
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.companyname"/>&nbsp;
									</td>
									<td>
										<html:text property="name" styleId="name" size="70" maxlength="50" disabled="true" styleClass="READONLYINPUT"
											onfocus="clearDivision('divName')"/>										
										<font color="red">*</font><br/>
										<div id="divName" class="validate"/>
									</td>
								</tr>	
								
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.addressline1"/>&nbsp;
									</td>
									<td>
										<html:text property="addLine1" styleId="addLine1" size="70" maxlength="40" disabled="true" styleClass="READONLYINPUT"
											onfocus="clearDivision('divAddLine1')"/>										
										<font color="red">*</font><br/>
										<div id="divAddLine1" class="validate"/>
									</td>
								</tr>
								
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.addressline2"/>&nbsp;
									</td>
									<td>
										<html:text property="addLine2" styleId="addLine2" size="70" maxlength="40" disabled="true" styleClass="READONLYINPUT"
											onfocus="clearDivision('divAddLine2')"/>										
										<font color="red">*</font><br/>
										<div id="divAddLine2" class="validate"/>
									</td>
								</tr>
								
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.addressline3"/>&nbsp;
									</td>
									<td>
										<html:text property="addLine3" styleId="addLine3" size="70" maxlength="40" disabled="true" styleClass="READONLYINPUT"
											onfocus="clearDivision('divAddLine3')"/>										
										<br/><div id="divAddLine3" class="validate"/>
									</td>
								</tr>
								
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.addressline4"/>&nbsp;
									</td>
									<td>
										<html:text property="addLine4" styleId="addLine4" size="70" maxlength="40" disabled="true" styleClass="READONLYINPUT"
											onfocus="clearDivision('divAddLine4')"/>										
										<br/><div id="divAddLine4" class="validate"/>
									</td>
								</tr>
								
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.telephoneno"/>&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td width="20%">
													<html:text property="telephoneNo" styleId="telephoneNo" size="15" maxlength="10" disabled="true" styleClass="READONLYINPUT"
														onfocus="clearDivision('divTelephoneNo')"/>										
													<font color="red">*</font><br/>
													<div id="divTelephoneNo" class="validate"/>
												</td>
												<td>
													<table cellpadding="0" cellspacing="0">
														<tr>
															<td style="width: 213px" align="right">
																<bean:message bundle="lable" key="screen.faxno"/>&nbsp;
															</td>
															<td>
																<html:text property="faxNo" styleId="faxNo" size="15" maxlength="10" disabled="true" styleClass="READONLYINPUT"
																	onfocus="clearDivision('divFaxNo')"/>										
																<font color="red">*</font><br/>
																<div id="divFaxNo" class="validate"/>
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
										<bean:message bundle="lable" key="screen.taxno"/>&nbsp;
									</td>
									<td>
										<html:text property="taxNo" styleId="taxNo" size="15" maxlength="10" disabled="true" styleClass="READONLYINPUT"
											onfocus="clearDivision('divTaxNo')"/>										
										<font color="red">*</font><br/>
										<div id="divTaxNo" class="validate"/>
									</td>
								</tr>
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.dateinstalled"/>&nbsp;
									</td>
									<td>
										<html:text property="dateInstalled" size="15" maxlength="12" styleId="dateInstalled" disabled="true" styleClass="READONLYINPUT" onkeypress="DateFormat(this,event);" onfocus="clearDivision('divDateInstalled')" onkeyup="DateFormat(this,event);" /><img src="images/none.gif" width="5px" ><input disabled="true" class="READONLYINPUT" type="button" value="..." onclick="return showCalendar('dateInstalled');" />
										<font color="red">*</font><br/>
										<div id="divDateInstalled" class="validate"/>
									</td>
								</tr>
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.financebegindate"/>&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td width="25%">
													<html:text property="finBeginDate" size="15" maxlength="12" styleId="finBeginDate" disabled="true" styleClass="READONLYINPUT" onkeypress="DateFormat(this,event);" onfocus="clearDivision('divFinBeginDate')" onkeyup="DateFormat(this,event);" /><img src="images/none.gif" width="5px" ><input disabled="true" class="READONLYINPUT" type="button" value="..." onclick="return showCalendar('finBeginDate');" />
													<font color="red">*</font><br/>
													<div id="divFinBeginDate" class="validate"/>
												</td>
												<td>
													<table cellpadding="0" cellspacing="0">
														<tr>
															<td style="width: 185px" align="right">
																<bean:message bundle="lable" key="screen.financeenddate"/>&nbsp;
															</td>
															<td>
																<html:text property="finEndDate" size="15" maxlength="12" styleId="finEndDate" disabled="true" styleClass="READONLYINPUT" onkeypress="DateFormat(this,event);" onfocus="clearDivision('divFinEndDate')" onkeyup="DateFormat(this,event);" /><img src="images/none.gif" width="5px" ><input disabled="true" class="READONLYINPUT" type="button" value="..." onclick="return showCalendar('finEndDate');" />
																<font color="red">*</font><br/>
																<div id="divFinEndDate" class="validate"/>
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
										<bean:message bundle="lable" key="screen.authmode"/>&nbsp;
									</td>
									<td>
										<select id="authmode" name="authmode" style="width: 70px;font-size: 12px" disabled="disabled" class="READONLYINPUT">
											<option value="1">Dual</option>
											<option value="0">Single</option>
										</select>
									</td>
								</tr>
															
							<tr height="5px"></tr>															
						</table>
					</td>
				</tr>
				</table>
			</html:form>
    	</logic:equal>
    	
    	
    	<!-- View record -->
    	<logic:equal name="company" property="action" value="view">
    		<br/>
			<html:form action="companyService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
				
				<table border="0">					
					<tr>
						<td>							
							<table class="InputTable">																	
								<tr>									
									<td colspan="2" align="center">
										<script>
											var myColumns = ["<bean:message bundle="lable" key="screen.code"/>",
							  								 "<bean:message bundle="lable" key="screen.description"/>"];
				            				var str = new AW.Formats.String; 
				            				var cellFormat = [str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);			                    						                    						                    			
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){	
			                                	if(row!='') { 
			                                		getRecordData(grid.getCellText(2,row));
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
										<bean:message bundle="lable" key="screen.code"/>
									</td>
									<td>
										<html:text property="code" styleId="code" size="6" maxlength="4"
											onfocus="clearDivision('divCode')"/>										
											<font color="red">*</font><br/>
											<div id="divCode" class="validate"/>
									</td>
								</tr>	
															
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.description"/>
									</td>
									<td>
										<html:text property="description" styleId="description" size="70" maxlength="50" 
											onfocus="clearDivision('divDescription')"/>										
										<font color="red">*</font><br/>
										<div id="divDescription" class="validate"/>
									</td>
								</tr>								
							<tr height="5px"></tr>															
						</table>
					</td>
				</tr>
				</table>
			</html:form>
    	</logic:equal>
    	
  	</body>
</html:html>
