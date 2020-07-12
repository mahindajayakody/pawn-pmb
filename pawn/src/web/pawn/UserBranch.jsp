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
		<script type="text/javascript" src="<html:rewrite forward="calendarJS"/>"></script>	
		<script type="text/javascript" src="js/com-all.js"></script>
		
		<script type="text/javascript">										
			var url = 'branchService.do';
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
							$('comCode').value     		= message[1];
							$('comName').value     		= message[2];
                       		$('addLine1').value        	= message[4];
                       		$('addLine2').value        	= message[5];
                       		$('addLine3').value        	= message[6];
                       		$('addLine4').value        	= message[7];
                       		$('telephoneNo').value     	= message[8];
                       		$('faxNo').value        	= message[9];
                       		$('taxNo').value        	= message[10];
                       		//$('isDefault').value        = message[11];
                       		$('receiptAccount').value   = message[12];
                       		$('paymentAccount').value   = message[13];
                       		$('dateInstalled').value    = message[14];
                       		$('companyId').value		= message[15];
                       		$('fundLimit').value		= message[16];
                       		$('availableAmt').value		= message[17];
                       		$('recordId').value    		= message[18];
                       		$('version').value     		= message[19];
                       		$('branchName').value		= message[20];
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
			
			function clearOtherData(){
				$('code').value        		= '';
           		$('addLine1').value        	= '';
           		$('addLine2').value        	= '';
           		$('addLine3').value        	= '';
           		$('addLine4').value        	= '';
           		$('telephoneNo').value     	= '';
           		$('faxNo').value        	= '';
           		$('taxNo').value        	= '';
           		//$('isDefault').value        = '1';
           		$('receiptAccount').value   = '';
           		$('paymentAccount').value   = '';
           		$('dateInstalled').value    = '';
           		//$('companyId').value		= '';
           		$('fundLimit').value		= '0.00';
           		$('fundavailable').value		= '0.00';
           		$('recordId').value    		= '';
           		$('version').value     		= '';
           		$('branchName').value		= '';
           		$('email').value			= '';
          		
				$('divCode').innerHTML 			= '';				
				$('divAddLine1').innerHTML		= '';
				$('divAddLine2').innerHTML		= '';
				$('divAddLine3').innerHTML		= '';
				$('divAddLine4').innerHTML		= '';
				$('divTelephoneNo').innerHTML 	= '';
				$('divFaxNo').innerHTML 		= '';
				$('divTaxNo').innerHTML 		= '';
				$('divDateInstalled').innerHTML = '';
				$('divReceiptAccount').innerHTML= '';
				$('divPaymentAccount').innerHTML= '';
				$('divComCode').innerHTML       = '';
				$('divFundLimit').innerHTML     = '';
				$('divBranchName').innerHTML	= '';
				$('divEmail').innerHTML			= '';
				//$('divAvailableAmt').innerHTML  = '';
				//grid.setSelectedRows([]);
				//getGridData();
				//setGridData(grid,[]);
			}
			
			function clearAll(){
				//$('comCode').value     		= '';
				//$('comName').value     		= '';				
				//$('companyId').value		= '0'
				clearOtherData();
			}
			
			function getCompanySerchData(){
				data = "dispatch=getAjaxData" 
				var mySearchRequest = new ajaxObject("companyService.do");
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
					if (responseStatus==200) {						
						var message =  eval('(' + responseText + ')');						
						setGridData(gridSerch,message);						
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request'); 
				    }
				}
				mySearchRequest.update(data,'POST');
			}
			
			function getCreateData(){
				var comCode		= $('comCode').value;
				var companyId	= $('companyId').value;
				var code        = $('code').value;				
				var addLine1    = $('addLine1').value; 
				var addLine2    = $('addLine2').value;
				var addLine3    = $('addLine3').value;
				var addLine4    = $('addLine4').value;
				var telephoneNo = $('telephoneNo').value;
				var faxNo 		= $('faxNo').value;
				var taxNo 		= $('taxNo').value;
				//var isDefault   = $('isDefault').value;
				var receiptAccount = $('receiptAccount').value;
				var paymentAccount = $('paymentAccount').value;
				var dateInstalled  = $('dateInstalled').value;
				var branchName	   = $('branchName').value;	
			    var fundLimit 	   = $('fundLimit').value;    	
				var fundavailable  = $('fundavailable').value;   
				var email		   = $('email').value;
									           										
				return 	"&code=" + code + "&comCode=" + comCode +
						"&addLine1=" + addLine1 + "&addLine2=" + addLine2 +
						"&addLine3=" + addLine3 + "&addLine4=" + addLine4 +
						"&telephoneNo=" + telephoneNo + "&faxNo=" + faxNo +
						"&taxNo=" + taxNo + "&dateInstalled=" + dateInstalled +
						//"&isDefault=" + isDefault + 
						"&receiptAccount=" + receiptAccount + "&branchName=" + branchName +
						"&paymentAccount=" + paymentAccount + "&companyId=" + companyId +
						"&fundLimit=" + fundLimit + "&fundavailable=" + fundavailable +
						"&email=" + email;
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
	                else if( message[i]['comCode'] )
	                    $('divComCode').innerHTML = message[i]['comCode'];
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
	                else if( message[i]['receiptAccount'] )
	                    $('divReceiptAccount').innerHTML = message[i]['receiptAccount'];
	                else if( message[i]['paymentAccount'] )
	                    $('divPaymentAccount').innerHTML = message[i]['paymentAccount'];
	                else if( message[i]['fundLimit'] )
	                    $('divFundLimit').innerHTML = message[i]['fundLimit'];                                        
	               // else if( message[i]['availableAmt'] )
	               //     $('divAvailableAmt').innerHTML = message[i]['availableAmt'];      
	                else if( message[i]['branchName'] )
	                    $('divBranchName').innerHTML = message[i]['branchName'];                                      
	                else if( message[i]['email'] )
	                    $('divEmail').innerHTML = message[i]['email'];                                      
	                                                            
	            }    
	        }
	        
	       function SetFundAvailable(){
	      		$('fundavailable').value =$('fundLimit').value ;
	        }
		</script>
		
		<style>
			#firstGrid {height: 165px;width:720px;}
			#firstGrid .aw-row-selector {text-align: center}
			#firstGrid .aw-column-0 {width: 120px;}
			#firstGrid .aw-column-1 {width: 545px;}	
			#firstGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#firstGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
			
			#secondGrid {height: 210px;width:580px;}
			#secondGrid .aw-row-selector {text-align: center}
			#secondGrid .aw-column-0 {width: 100px;}
			#secondGrid .aw-column-1 {width: 455px;}	
			#secondGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#secondGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
		</style>
  	</head>
  
  	<body onload="loadToolBar()">
    	<div id="screenCont" class="enableAll" align="center" ></div>
    	
    	<div id="serchDiv" class="x-hidden">
        	<div class="x-window-header">
            	Search Company
            </div>
        	<div id="serch-tab">
            	<div class="x-tab" title="Search">
					<table style="width: 600px">							
						<tr>
							<td>
								<script>
									var myColumnsSerch = ["<bean:message bundle="lable" key="screen.code"/>","<bean:message bundle="lable" key="screen.companyname"/>"];
		            				var str = new AW.Formats.String; 
		            				var cellFormatSerch = [str,str];
	                    			var gridSerch = createBrowser(myColumnsSerch,'secondGrid',cellFormatSerch);							                    			 			                    			
	                    			gridSerch.setHeaderHeight(25);
	                                document.write(gridSerch);
	                                gridSerch.onRowDoubleClicked = function(event, row){
										try{
											$('companyId').value = this.getCellText(13,row);
											$('comCode').value   = this.getCellText(0,row);
											$('comName').value   = this.getCellText(1,row);
											$('hidDiv').className='hideSearchPopup';
				                        	win.hide();	
				                        	getGridData();			                        	
										}catch(error){}
									};
									gridSerch.onSelectedRowsChanged=function(row){
										try{
											$('companyId').value = this.getCellText(13,row);
											$('comCode').value   = this.getCellText(0,row);
											$('comName').value   = this.getCellText(1,row);
										}catch(error){}
									}
	                                getCompanySerchData();
								</script>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div id="hidDiv" class="hideSearchPopup">
		</div>
    	
    	<!-- Create record -->
    	<logic:equal name="branch" property="action" value="create">    	
			<html:form action="branchService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
				
				<table border="0">
					<tr>
						<td>							
							<table class="InputTable">    
								<tr height="2px"></tr>     
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.companyname"/>&nbsp;
									</td>
									<td>
										<html:hidden property="companyId" styleId="companyId" />
										<html:text property="comCode" styleId="comCode" size="5" maxlength="3" 
											onfocus="clearDivision('divComCode')"
											onblur="upperCase('comCode');commonSearch('branchService.do','companyId','comCode','comName','getCompany','divComCode',function(){getGridData();},'',function(){var temp=this.value;var divComCode=$('divComCode').value;clearAll();this.value=temp;$('divComCode').value=divComCode;})"/>
										<input id="ButtonSerch" type="button" value="..." />
										<input type="text" size="60" id="comName" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divComCode" class="validate"/>
									</td>
								</tr>
								<tr height="2px"></tr>
							</table>
						</td>
					</tr>
																		
					<tr>
						<td>							
							<table class="InputTable">																	
								<tr>									
									<td colspan="2" align="center">
										<script>
											var myColumns = ["<bean:message bundle="lable" key="screen.code"/>",
							  								 "<bean:message bundle="lable" key="screen.branchname"/>"];
				            				//var str = new AW.Formats.String; 
				            				var cellFormat = [str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);			                    						                    						                    			
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){	
			                                	if(row!='') { 			                                		
													$('code').value        		= grid.getCellText(0,row);
													$('comCode').value     		= grid.getCellText(2,row);
													$('comName').value     		= grid.getCellText(3,row);
									           		$('addLine1').value        	= grid.getCellText(4,row);
									           		$('addLine2').value        	= grid.getCellText(5,row);
									           		$('addLine3').value        	= grid.getCellText(6,row);
									           		$('addLine4').value        	= grid.getCellText(7,row);
									           		$('telephoneNo').value     	= grid.getCellText(8,row);
									           		$('faxNo').value        	= grid.getCellText(9,row);
									           		$('taxNo').value        	= grid.getCellText(10,row);
									           		//$('isDefault').value        = grid.getCellText(11,row);
									           		$('receiptAccount').value   = grid.getCellText(12,row);
									           		$('paymentAccount').value   = grid.getCellText(13,row);
									           		$('dateInstalled').value    = grid.getCellText(14,row);
									           		$('companyId').value		= grid.getCellText(15,row);
									           		$('fundLimit').value    	= grid.getCellText(16,row);
									           		$('fundavailable').value    = grid.getCellText(17,row);
									           		$('recordId').value    		= grid.getCellText(18,row);
									           		$('version').value     		= grid.getCellText(19,row);
									           		$('branchName').value		= grid.getCellText(20,row);
									           		$('email').value			= grid.getCellText(21,row);
			                                	}																						
			                                };
			                                //getGridData();
			                            </script>
			                        </td>
			                    </tr>            
			                </table>
						</td>
					</tr>
			            			                
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
										<bean:message bundle="lable" key="screen.branchname"/>&nbsp;
									</td>
									<td>
										<html:text property="branchName" styleId="branchName" size="70" maxlength="50" 
											onfocus="clearDivision('divBranchName')"/>										
											<font color="red">*</font><br/>
											<div id="divBranchName" class="validate"/>
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
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td width="20%">
													<html:text property="taxNo" styleId="taxNo" size="15" maxlength="10" 
														onfocus="clearDivision('divTaxNo')"/>										
													<font color="red">*</font><br/>
													<div id="divTaxNo" class="validate"/>
												</td>
												<td>
													<table cellpadding="0" cellspacing="0">
														<tr>
															<td style="width: 158px" align="right">
																<bean:message bundle="lable" key="screen.email"/>&nbsp;
															</td>
															<td>
																<html:text property="email" styleId="email" size="50" maxlength="100" 
																 onfocus="clearDivision('divEmail')"/>										
																<font color="red">*</font><br/>
																<div id="divEmail" class="validate"/>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>										
									</td>
									
								</tr>
								<!--
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.isdefault"/>&nbsp;
									</td>
									<td>
										<select id="isDefault" name="isDefault" style="width: 50px;font-size: 12px">
											<option value="0">No</option>
											<option value="1">Yes</option>											
										</select>
									</td>
								</tr>
								-->
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.receiptaccount"/>&nbsp;
									</td>
									<td>
										<html:text property="receiptAccount" styleId="receiptAccount" size="25" maxlength="20" 
											onfocus="clearDivision('divReceiptAccount')"/>										
										<font color="red">*</font><br/>
										<div id="divReceiptAccount" class="validate"/>
									</td>
								</tr>
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.paymentaccount"/>&nbsp;
									</td>
									<td>
										<html:text property="paymentAccount" styleId="paymentAccount" size="25" maxlength="20" 
											onfocus="clearDivision('divPaymentAccount')"/>										
										<font color="red">*</font><br/>
										<div id="divPaymentAccount" class="validate"/>
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
										<bean:message bundle="lable" key="screen.fundlimit"/>&nbsp;
									</td>
									<td>
										<html:text property="fundLimit" value="0.00" styleId="fundLimit" size="25" maxlength="20" 
										onchange="SetFundAvailable()" onfocus="clearDivision('divFundLimit')" />										
										<font color="red">*</font><br/>
										<div id="divFundLimit" class="validate"/>
									</td>
								</tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.fundavailable"/>&nbsp;
									</td>
									<td>
										<input Id="fundavailable" value="0.00" size="21" maxlength="20" 
										readonly="readonly" class="READONLYINPUT"/>										
										<!--  <font color="red">*</font><br/>
										<div id="divAvailableAmt" class="validate"/> -->
									</td>
								</tr>
							<tr height="5px"></tr>															
						</table>
					</td>
				</tr>
				</table>
			</html:form>
								
    	</logic:equal>
    	
    	
    	<!-- update record -->
    	<logic:equal name="branch" property="action" value="update">
			<html:form action="branchService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
				
				<table border="0">	
					<tr>
						<td>							
							<table class="InputTable">    
								<tr height="2px"></tr>     
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.companyname"/>&nbsp;
									</td>
									<td>
										<html:hidden property="companyId" styleId="companyId" />
										<html:text property="comCode" styleId="comCode" size="5" maxlength="3" 
											onfocus="clearDivision('divComCode')"
											onblur="upperCase('comCode');commonSearch('branchService.do','companyId','comCode','comName','getCompany','divComCode',function(){getGridData();},'',function(){var temp=this.value;var divComCode=$('divComCode').value;clearAll();this.value=temp;$('divComCode').value=divComCode;})"/>
										<input id="ButtonSerch" type="button" value="..." />
										<input type="text" size="60" id="comName" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divComCode" class="validate"/>
									</td>
								</tr>
								<tr height="2px"></tr>
							</table>
						</td>
					</tr>
									
					<tr>
						<td>							
							<table class="InputTable">																	
								<tr>									
									<td colspan="2" align="center">
										<script>
											var myColumns = ["<bean:message bundle="lable" key="screen.code"/>",
							  								 "<bean:message bundle="lable" key="screen.address"/>"];
				            				//var str = new AW.Formats.String; 
				            				var cellFormat = [str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);			                    						                    						                    			
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){	
			                                	if(row!='') { 
			                                		$('code').value        		= grid.getCellText(0,row);
													$('comCode').value     		= grid.getCellText(2,row);
													$('comName').value     		= grid.getCellText(3,row);
									           		$('addLine1').value        	= grid.getCellText(4,row);
									           		$('addLine2').value        	= grid.getCellText(5,row);
									           		$('addLine3').value        	= grid.getCellText(6,row);
									           		$('addLine4').value        	= grid.getCellText(7,row);
									           		$('telephoneNo').value     	= grid.getCellText(8,row);
									           		$('faxNo').value        	= grid.getCellText(9,row);
									           		$('taxNo').value        	= grid.getCellText(10,row);
									           		//$('isDefault').value        = grid.getCellText(11,row);
									           		$('receiptAccount').value   = grid.getCellText(12,row);
									           		$('paymentAccount').value   = grid.getCellText(13,row);
									           		$('dateInstalled').value    = grid.getCellText(14,row);
									           		$('companyId').value		= grid.getCellText(15,row);
									           		$('fundLimit').value    	= grid.getCellText(16,row);
									           		$('fundavailable').value   	= grid.getCellText(17,row);
									           		$('recordId').value    		= grid.getCellText(18,row);
									           		$('version').value     		= grid.getCellText(19,row);
									           		$('branchName').value		= grid.getCellText(20,row);
									           		$('email').value			= grid.getCellText(21,row);
			                                	}																						
			                                };
			                                //getGridData();
			                            </script>
			                        </td>
			                    </tr>            
			                </table>
						</td>
					</tr>
			          			                
		          	<tr>
						<td>							
							<table class="InputTable">    
								<tr height="5px"></tr>     

								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.code"/>&nbsp;
									</td>
									<td>
										<html:text property="code" styleId="code" size="4" maxlength="3" styleClass="READONLYINPUT" readonly="true"
											onblur="upperCase('code')" onfocus="clearDivision('divCode')"/>										
											<font color="red">*</font><br/>
											<div id="divCode" class="validate"/>
									</td>
								</tr>
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.branchname"/>&nbsp;
									</td>
									<td>
										<html:text property="branchName" styleId="branchName" size="70" maxlength="50" 
											onfocus="clearDivision('divBranchName')"/>										
											<font color="red">*</font><br/>
											<div id="divBranchName" class="validate"/>
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
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td width="20%">
													<html:text property="taxNo" styleId="taxNo" size="15" maxlength="10" 
														onfocus="clearDivision('divTaxNo')"/>										
													<font color="red">*</font><br/>
													<div id="divTaxNo" class="validate"/>
												</td>
												<td>
													<table cellpadding="0" cellspacing="0">
														<tr>
															<td style="width: 158px" align="right">
																<bean:message bundle="lable" key="screen.email"/>&nbsp;
															</td>
															<td>
																<html:text property="email" styleId="email" size="50" maxlength="100" 
																 onfocus="clearDivision('divEmail')"/>										
																<font color="red">*</font><br/>
																<div id="divEmail" class="validate"/>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>										
									</td>
									
								</tr>
								
								<!--
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.isdefault"/>&nbsp;
									</td>
									<td>
										<select id="isDefault" name="isDefault" style="width: 50px;font-size: 12px">
											<option value="0">No</option>
											<option value="1">Yes</option>											
										</select>
									</td>
								</tr>
								-->
	
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.receiptaccount"/>&nbsp;
									</td>
									<td>
										<html:text property="receiptAccount" styleId="receiptAccount" size="25" maxlength="20" 
											onfocus="clearDivision('divReceiptAccount')"/>										
										<font color="red">*</font><br/>
										<div id="divReceiptAccount" class="validate"/>
									</td>
								</tr>
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.paymentaccount"/>&nbsp;
									</td>
									<td>
										<html:text property="paymentAccount" styleId="paymentAccount" size="25" maxlength="20" 
											onfocus="clearDivision('divPaymentAccount')"/>										
										<font color="red">*</font><br/>
										<div id="divPaymentAccount" class="validate"/>
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
										<bean:message bundle="lable" key="screen.fundlimit"/>&nbsp;
									</td>
									<td>
									
									
										<html:text property="fundLimit" value="0.00" styleId="fundLimit" size="25" maxlength="20" 
										  onfocus="clearDivision('divFundLimit')" />										
										<font color="red">*</font><br/>
										<div id="divFundLimit" class="validate"/>
										
									</td>
								</tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.fundavailable"/>&nbsp;
									</td>
									<td>
										<input Id="fundavailable" value="0.00" size="21" maxlength="20" 
										readonly="readonly" class="READONLYINPUT"/>	
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
    	<logic:equal name="branch" property="action" value="delete">
			<html:form action="branchService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
				
				<table border="0">
					<tr>
						<td>							
							<table class="InputTable">    
								<tr height="2px"></tr>     
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.companyname"/>&nbsp;
									</td>
									<td>
										<html:hidden property="companyId" styleId="companyId" />
										<html:text property="comCode" styleId="comCode" size="5" maxlength="3" 
											onfocus="clearDivision('divComCode')"
											onblur="upperCase('comCode');commonSearch('branchService.do','companyId','comCode','comName','getCompany','divComCode',function(){getGridData();},'',function(){var temp=this.value;var divComCode=$('divComCode').value;clearAll();this.value=temp;$('divComCode').value=divComCode;})"/>
										<input id="ButtonSerch" type="button" value="..." />
										<input type="text" size="60" id="comName" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divComCode" class="validate"/>
									</td>
								</tr>
								<tr height="2px"></tr>
							</table>
						</td>
					</tr>
										
					<tr>
						<td>							
							<table class="InputTable">																	
								<tr>									
									<td colspan="2" align="center">
										<script>
											var myColumns = ["<bean:message bundle="lable" key="screen.code"/>",
							  								 "<bean:message bundle="lable" key="screen.address"/>"];
				            				//var str = new AW.Formats.String; 
				            				var cellFormat = [str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);			                    						                    						                    			
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){	
			                                	if(row!='') { 
			                                		$('code').value        		= grid.getCellText(0,row);
													$('comCode').value     		= grid.getCellText(2,row);
													$('comName').value     		= grid.getCellText(3,row);
									           		$('addLine1').value        	= grid.getCellText(4,row);
									           		$('addLine2').value        	= grid.getCellText(5,row);
									           		$('addLine3').value        	= grid.getCellText(6,row);
									           		$('addLine4').value        	= grid.getCellText(7,row);
									           		$('telephoneNo').value     	= grid.getCellText(8,row);
									           		$('faxNo').value        	= grid.getCellText(9,row);
									           		$('taxNo').value        	= grid.getCellText(10,row);
									           		//$('isDefault').value        = grid.getCellText(11,row);
									           		$('receiptAccount').value   = grid.getCellText(12,row);
									           		$('paymentAccount').value   = grid.getCellText(13,row);
									           		$('dateInstalled').value    = grid.getCellText(14,row);
									           		$('companyId').value		= grid.getCellText(15,row);
									           		$('fundLimit').value    	= grid.getCellText(16,row);
									           		$('availableAmt').value    	= grid.getCellText(17,row);
									           		$('recordId').value    		= grid.getCellText(18,row);
									           		$('version').value     		= grid.getCellText(19,row);
									           		$('branchName').value		= grid.getCellText(20,row);
			                                	}																						
			                                };
			                                //getGridData();
			                            </script>
			                        </td>
			                    </tr>            
			                </table>
						</td>
					</tr>
			          	                
		          	<tr>
						<td>							
							<table class="InputTable">    
								<tr height="5px"></tr>     

								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.code"/>&nbsp;
									</td>
									<td>
										<html:text property="code" styleId="code" size="4" maxlength="3" styleClass="READONLYINPUT" readonly="true"
											onblur="upperCase('code')" onfocus="clearDivision('divCode')"/>										
											<font color="red">*</font><br/>
											<div id="divCode" class="validate"/>
									</td>
								</tr>	
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.branchname"/>&nbsp;
									</td>
									<td>
										<html:text property="branchName" styleId="branchName" size="70" maxlength="50" styleClass="READONLYINPUT" readonly="true"
											onfocus="clearDivision('divBranchName')"/>										
											<font color="red">*</font><br/>
											<div id="divBranchName" class="validate"/>
									</td>
								</tr>							
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.addressline1"/>&nbsp;
									</td>
									<td>
										<html:text property="addLine1" styleId="addLine1" size="61" maxlength="40" styleClass="READONLYINPUT" readonly="true"
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
										<html:text property="addLine2" styleId="addLine2" size="61" maxlength="40" styleClass="READONLYINPUT" readonly="true"
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
										<html:text property="addLine3" styleId="addLine3" size="61" maxlength="40" styleClass="READONLYINPUT" readonly="true"
											onfocus="clearDivision('divAddLine3')"/>										
										<br/><div id="divAddLine3" class="validate"/>
									</td>
								</tr>
								
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.addressline4"/>&nbsp;
									</td>
									<td>
										<html:text property="addLine4" styleId="addLine4" size="61" maxlength="40" styleClass="READONLYINPUT" readonly="true"
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
													<html:text property="telephoneNo" styleId="telephoneNo" size="15" maxlength="10" styleClass="READONLYINPUT" readonly="true"
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
																<html:text property="faxNo" styleId="faxNo" size="15" maxlength="10" styleClass="READONLYINPUT" readonly="true"
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
										<html:text property="taxNo" styleId="taxNo" size="15" maxlength="10" styleClass="READONLYINPUT" readonly="true"
											onfocus="clearDivision('divTaxNo')"/>										
										<font color="red">*</font><br/>
										<div id="divTaxNo" class="validate"/>
									</td>
								</tr>
								
								<!--
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.isdefault"/>&nbsp;
									</td>
									<td>
										<select id="isDefault" name="isDefault" style="width: 50px;font-size: 12px">
											<option value="0">No</option>
											<option value="1">Yes</option>											
										</select>
									</td>
								</tr>
								-->
	
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.receiptaccount"/>&nbsp;
									</td>
									<td>
										<html:text property="receiptAccount" styleId="receiptAccount" size="25" maxlength="20" styleClass="READONLYINPUT" readonly="true"
											onfocus="clearDivision('divReceiptAccount')"/>										
										<font color="red">*</font><br/>
										<div id="divReceiptAccount" class="validate"/>
									</td>
								</tr>
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.paymentaccount"/>&nbsp;
									</td>
									<td>
										<html:text property="paymentAccount" styleId="paymentAccount" size="25" maxlength="20" styleClass="READONLYINPUT" readonly="true"
											onfocus="clearDivision('divPaymentAccount')"/>										
										<font color="red">*</font><br/>
										<div id="divPaymentAccount" class="validate"/>
									</td>
								</tr>
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.dateinstalled"/>&nbsp;
									</td>
									<td>
										<html:text property="dateInstalled" size="15" maxlength="12"  readonly="true" styleClass="READONLYINPUT" styleId="dateInstalled" onkeypress="DateFormat(this,event);" onfocus="clearDivision('divDateInstalled')" onkeyup="DateFormat(this,event);" /><img src="images/none.gif" width="5px" ><input type="button" value="..." onclick="return showCalendar('dateInstalled');" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divDateInstalled" class="validate"/>
									</td>
								</tr>
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.fundlimit"/>&nbsp;
									</td>
									<td>
										<html:text property="fundLimit" styleId="fundLimit" size="25" maxlength="20" 
											onfocus="clearDivision('divFundLimit')" readonly="readonly" styleClass="READONLYINPUT"/>										
										<font color="red">*</font><br/>
										<div id="divFundLimit" class="validate"/>
									</td>
								</tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.fundavailable"/>&nbsp;
									</td>
									<td>
										<html:text property="fundavailable" styleId="fundavailable" size="25" maxlength="20" 
											onfocus="clearDivision('divAvailableAmt')" readonly="readonly" styleClass="READONLYINPUT" />										
										<font color="red">*</font><br/>
										<div id="divAvailableAmt" class="validate"/>
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
    	<logic:equal name="branch" property="action" value="view">
    		<br/>
			<html:form action="branchService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
				
				
			</html:form>
    	</logic:equal>
		
		<script>
			var win;
			Ext.onReady(function(){
			    var button = Ext.get('ButtonSerch');
			    button.on('click', function(){
			        if(!win){
			            win = new Ext.Window({
			                el:'serchDiv',
			                layout:'fit',
			                width:600,
			                height:300,
			                closable:false, 
			                closeAction:'hide',
			                plain: true,
			
			                items: new Ext.TabPanel({
			                    el: 'serch-tab',
			                    autoTabs:true,
			                    activeTab:0,
			                    deferredRender:false,
			                    border:false
			                }),
			
			                buttons: [{
			                    text: 'Ok',
			                    handler: function(){				                   		
			                   		getGridData();
			                   		$('hidDiv').className='hideSearchPopup';
			                        win.hide();
			                   	}
			                }]
			            });
			        }
			        win.show(this);
			        $('hidDiv').className='showSearchPopup';
			    });
			});
		</script>
			
  	</body>
</html:html>
