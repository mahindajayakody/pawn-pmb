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
		<script type="text/javascript" src="<html:rewrite forward="commonJS"/>"></script>
		<script type="text/javascript" src="<html:rewrite forward="ajaxJS"/>"></script>
		<script type="text/javascript" src="<html:rewrite forward="awJS"/>"></script>	
		
		
		<script type="text/javascript">								
			var action = 1;
			
			var url = 'dueTypeService.do';
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
							$('description').value 		= message[1];
							$('sequenceno').value  		= message[2];
							$('recordId').value    		= message[3];
							$('version').value     		= message[4];							
							$('receiptpayment').value 	= message[5];
							$('odinterest').value  		= message[6];
							$('interexter').value  		= message[7];
							$('accountno').value  		= message[8];
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
			
			function getAuthorizeGridData(){
				data = "dispatch=getAuthorizeData";
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
				$('code').value		   = '';
				$('description').value = '';
				$('recordId').value    = '';
				$('version').value     = '';
				$('sequenceno').value  = '0';
				$('receiptpayment').value = 'R';
				$('odinterest').value  = 'Y';
				$('interexter').value  = 'E';
				$('accountno').value   = '0';
				
				$('divCode').innerHTML = '';
				$('divDescription').innerHTML = '';
				$('divProductCode').innerHTML = ''
				grid.setSelectedRows([]);
			}
			
			function getCreateData(){
				var code           = $('code').value;
				var description    = $('description').value;
				var productId      = $('productId').value;
				var sequenceno     = $('sequenceno').value;
				var receiptpayment = $('receiptpayment').value;
				var odinterest     = $('odinterest').value;
				var interexter     = $('interexter').value;
				var productcode    = $('productcode').value;
				var accountno	   = $('accountno').value  
				
				return 	"&code=" + code + "&description=" + description +
						"&productId=" + productId + "&sequenceno=" + sequenceno +
						"&receiptpayment=" + receiptpayment + "&odinterest=" + odinterest +
						"&interexter=" + interexter + "&productcode=" + productcode + 
						"&accountno=" + accountno;
			}
			
			function getChangedData(){
				var recordId = $('recordId').value;
				var version  = $('version').value;
				
				var str = getCreateData() + "&recordId=" + recordId + "&version=" + version;				
				return str;
			}
			
			function getAuthorizeData(){
				var recordId 		= $('recordId').value;
				var version  		= $('version').value;
				var authorizeMode	= $('authorizeMode').value;
				
				var str = "&recordId=" + recordId + "&version=" + version + "&authorizeMode=" + authorizeMode;				
				return str;
			}
		
			function showValidationErrors(message){
	       		for( var i =0; i < message.length ; i++){	            	
	                if( message[i]['code'] )
	                    $('divCode').innerHTML = message[i]['code'];
	                else if( message[i]['description'] )
	                    $('divDescription').innerHTML = message[i]['description'];
	                else if( message[i]['productcode'] )
	                    $('divProductCode').innerHTML = message[i]['productcode']; 
	                else if( message[i]['accountno'] )
	                    $('divAccountno').innerHTML = message[i]['accountno'];    
	                       
	            }    
	        }
		</script>
		
		<style>
			#firstGrid {height: 265px;width:720px;}
			#firstGrid .aw-row-selector {text-align: center}
			#firstGrid .aw-column-0 {width: 150px;}
			#firstGrid .aw-column-1 {width: 445px;}	
			#firstGrid .aw-column-2 {width: 100px;text-align: center;}
			#firstGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#firstGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
			
			#authorizeGrid {height: 265px;width:720px;}
			#authorizeGrid .aw-row-selector {text-align: center}
			#authorizeGrid .aw-column-0 {width: 100px;}
			#authorizeGrid .aw-column-1 {width: 400px;}	
			#authorizeGrid .aw-column-2 {width: 100px;text-align: center;}
			#authorizeGrid .aw-column-3 {width: 90px;text-align: center;}
			#authorizeGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#authorizeGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
		
		</style>
  	</head>
  
  	<body onload="loadToolBar()">
    	<div id="screenCont" class="enableAll" align="center" ></div>
    	
    	<!-- Create record -->
    	<logic:equal name="duetype" property="action" value="create">
			<html:form action="dueTypeService.do">
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
							  								 "<bean:message bundle="lable" key="screen.description"/>",
							  								 "<bean:message bundle="lable" key="screen.sequenceno"/>"];
				            				var str = new AW.Formats.String; 
				            				var cellFormat = [str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);			                    						                    						                    			
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){	
			                                	if(row!='') { 
			                                		//getRecordData(grid.getCellText(2,row));
			                                		$('code').value        		= grid.getCellText(0,row);
			                                		$('description').value 		= grid.getCellText(1,row);
			                                		$('sequenceno').value  		= grid.getCellText(2,row);
			                                		$('recordId').value    		= grid.getCellText(3,row);
			                                		$('version').value     		= grid.getCellText(4,row);			                                		
													$('receiptpayment').value 	= grid.getCellText(5,row);
													$('odinterest').value  		= grid.getCellText(6,row);
													$('interexter').value  		= grid.getCellText(7,row);
													$('accountno').value  		= grid.getCellText(8,row);
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
							<table class="InputTable" >
								<tr height="3px"></tr>     
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.productcode"/>
									</td>
									<td>
										<input type="hidden" id="productId" name="productId">
										<html:text property="productcode" styleId="productcode" value="PW" size="4" readonly="true" maxlength="2" styleClass="READONLYINPUT"/>	
										<input type="text" size="20" id="productdesc" name="productdesc" value="Pawing" readonly="true" class="READONLYINPUT"/>																				
										<font color="red">*</font><br/>
										<div id="divProductCode" class="validate"/>
									</td>
								</tr>
								    
								<tr height="2px"></tr>     
								<tr>
									<td width="30%" align="right">
										<bean:message bundle="lable" key="screen.code"/>
									</td>
									<td width="20%">
										<html:text property="code" styleId="code" size="5" maxlength="3"
											onblur="upperCase('code')" onfocus="clearDivision('divCode')"/>										
											<font color="red">*</font><br/>
											<div id="divCode" class="validate"/>
									</td>
								</tr>	
															
								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.description"/>
									</td>
									<td>
										<html:text property="description" styleId="description" size="70" maxlength="50" 
											onfocus="clearDivision('divDescription')"/>										
										<font color="red">*</font>
										<div id="divDescription" class="validate"/>
									</td>
								</tr>	
								
								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.sequenceno"/>
									</td>
									<td>
										<html:text property="sequenceno" styleId="sequenceno" size="5" maxlength="2" 
											onfocus="clearDivision('divSequenceno')"/>										
										<font color="red">*</font><br/>
										<div id="divSequenceno" class="validate"/>
									</td>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.receiptpayment"/>
									</td>
									<td>
										<select id="receiptpayment" name="receiptpayment" style="width: 41px">
											<option value="R">R</option>
											<option value="P">P</option>
										</select>
									</td>									
								</tr>				
								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.odinterestchng"/>
									</td>
									<td>
										<select id="odinterest" name="odinterest" style="width: 41px">
											<option value="Y">Y</option>
											<option value="N">N</option>
										</select>
									</td>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.internalexternal"/>
									</td>
									<td>
										<select id="interexter" name="interexter" style="width: 41px">
											<option value="E">E</option>
											<option value="I">I</option>
										</select>
									</td>									
								</tr>
							<tr height="3px"></tr>
							<tr>
								<td width="20%" align="right">
									<bean:message bundle="lable" key="screen.account"/> 
								</td> 
								<td width="20%" align="left">
									<html:text property="accountno" styleId="accountno" size="20" maxlength="15" 
										onfocus="clearDivision('divAccountno')"/>										
									<font color="red">*</font><br/>
									<div id="divAccountno" class="validate"/>
								</td>
							</tr>															
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
    	<logic:equal name="duetype" property="action" value="update">
			<html:form action="dueTypeService.do">
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
							  								 "<bean:message bundle="lable" key="screen.description"/>",
							  								 "<bean:message bundle="lable" key="screen.sequenceno"/>"];
				            				var str = new AW.Formats.String; 
				            				var cellFormat = [str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);			                    						                    						                    			
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){	
			                                	if(row!='') { 
			                                		//getRecordData(grid.getCellText(2,row));
			                                		$('code').value        		= grid.getCellText(0,row);
			                                		$('description').value 		= grid.getCellText(1,row);
			                                		$('sequenceno').value  		= grid.getCellText(2,row);
			                                		$('recordId').value    		= grid.getCellText(3,row);
			                                		$('version').value     		= grid.getCellText(4,row);			                                		
													$('receiptpayment').value 	= grid.getCellText(5,row);
													$('odinterest').value  		= grid.getCellText(6,row);
													$('interexter').value  		= grid.getCellText(7,row);	
													$('accountno').value  		= grid.getCellText(8,row);		                                		
			                                	}																						
			                                };
			                                getGridData();
			                            </script>
			                        </td>
			                    </tr>            
			                </table>
						</td>
					</tr>
			          
					<tr height="3px"></tr>   			                
		          	<tr>
						<td>							
							<table class="InputTable">    
								<tr height="5px"></tr>  
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.productcode"/>
									</td>
									<td>
										<input type="hidden" id="productId" name="productId">
										<html:text property="productcode" styleId="productcode" value="PW" size="4" readonly="true" maxlength="2" styleClass="READONLYINPUT"/>	
										<input type="text" size="51" id="productdesc" name="productdesc" value="Pawing" readonly="true" class="READONLYINPUT"/>																				
										<font color="red">*</font><br/>
										<div id="divProductCode" class="validate"/>
									</td>
								</tr>
								
								<tr height="2px"></tr>   
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.code"/>
									</td>
									<td>
										<html:text property="code" styleId="code" size="4" maxlength="3" disabled="true" styleClass="READONLYINPUT"
											onblur="upperCase('code')" onfocus="clearDivision('divCode')"/>										
											<font color="red">*</font><br/>
											<div id="divCode" class="validate"/>
									</td>
								</tr>	
															
								<tr height="2px"></tr>
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
								
								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.sequenceno"/>
									</td>
									<td>
										<html:text property="sequenceno" styleId="sequenceno" size="5" maxlength="2" 
											onfocus="clearDivision('divSequenceno')"/>										
										<font color="red">*</font><br/>
										<div id="divSequenceno" class="validate"/>
									</td>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.receiptpayment"/>
									</td>
									<td>
										<select id="receiptpayment" name="receiptpayment" style="width: 41px">
											<option value="R">R</option>
											<option value="P">P</option>
										</select>
									</td>									
								</tr>				
								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.odinterestchng"/>
									</td>
									<td>
										<select id="odinterest" name="odinterest" style="width: 41px">
											<option value="Y">Y</option>
											<option value="N">N</option>
										</select>
									</td>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.internalexternal"/>
									</td>
									<td>
										<select id="interexter" name="interexter" style="width: 41px">
											<option value="E">E</option>
											<option value="I">I</option>
										</select>
									</td>									
								</tr>
							<tr height="3px"></tr>
							<tr>
								<td width="20%" align="right">
									<bean:message bundle="lable" key="screen.account"/> 
								</td> 
								<td width="20%" align="left">
									<html:text property="accountno" styleId="accountno" size="20" maxlength="15" 
										onfocus="clearDivision('divAccountno')"/>										
									<font color="red">*</font><br/>
									<div id="divAccountno" class="validate"/>
								</td>
							</tr>															
						</table>
					</td>
				</tr>
				</table>
			</html:form>
    	</logic:equal>
    	
    	<!-- Delete record -->
    	<logic:equal name="duetype" property="action" value="delete">
			<html:form action="dueTypeService.do">
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
							  								 "<bean:message bundle="lable" key="screen.description"/>",
							  								 "<bean:message bundle="lable" key="screen.sequenceno"/>"];
				            				var str = new AW.Formats.String; 
				            				var cellFormat = [str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);			                    						                    						                    			
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){	
			                                	if(row!='') { 
			                                		//getRecordData(grid.getCellText(2,row));
			                                		$('code').value        		= grid.getCellText(0,row);
			                                		$('description').value 		= grid.getCellText(1,row);
			                                		$('sequenceno').value  		= grid.getCellText(2,row);
			                                		$('recordId').value    		= grid.getCellText(3,row);
			                                		$('version').value     		= grid.getCellText(4,row);			                                		
													$('receiptpayment').value 	= grid.getCellText(5,row);
													$('odinterest').value  		= grid.getCellText(6,row);
													$('interexter').value  		= grid.getCellText(7,row);	
													$('accountno').value  		= grid.getCellText(8,row);																									
			                                	}																						
			                                };
			                                getGridData();
			                            </script>
			                        </td>
			                    </tr>            
			                </table>
						</td>
					</tr>
			          
					<tr height="3px"></tr>   			                
		          	<tr>
						<td>							
							<table class="InputTable">    
								<tr height="5px"></tr>  
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.productcode"/>
									</td>
									<td>
										<input type="hidden" id="productId" name="productId">
										<html:text property="productcode" styleId="productcode" value="PW" size="4" readonly="true" maxlength="2" styleClass="READONLYINPUT"/>	
										<input type="text" size="51" id="productdesc" name="productdesc" value="Pawing" readonly="true" class="READONLYINPUT"/>																				
										<font color="red">*</font><br/>
										<div id="divProductCode" class="validate"/>
									</td>
								</tr>
								
								<tr height="2px"></tr>   
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.code"/>
									</td>
									<td>
										<html:text property="code" styleId="code" size="4" maxlength="3" disabled="true" styleClass="READONLYINPUT"
											onblur="upperCase('code')" onfocus="clearDivision('divCode')"/>										
											<font color="red">*</font><br/>
											<div id="divCode" class="validate"/>
									</td>
								</tr>	
															
								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.description"/>
									</td>
									<td>
										<html:text property="description" styleId="description" size="58" maxlength="50" 
											onfocus="clearDivision('divDescription')" disabled="true" styleClass="READONLYINPUT"/>										
										<font color="red">*</font><br/>
										<div id="divDescription" class="validate"/>
									</td>
								</tr>
								
								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.sequenceno"/>
									</td>
									<td>
										<html:text property="sequenceno" styleId="sequenceno" size="5" maxlength="2" 
											onfocus="clearDivision('divSequenceno')"/>										
										<font color="red">*</font><br/>
										<div id="divSequenceno" class="validate"/>
									</td>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.receiptpayment"/>
									</td>
									<td>
										<select id="receiptpayment" name="receiptpayment" style="width: 41px">
											<option value="R">R</option>
											<option value="P">P</option>
										</select>
									</td>									
								</tr>				
								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.odinterestchng"/>
									</td>
									<td>
										<select id="odinterest" name="odinterest" style="width: 41px">
											<option value="Y">Y</option>
											<option value="N">N</option>
										</select>
									</td>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.internalexternal"/>
									</td>
									<td>
										<select id="interexter" name="interexter" style="width: 41px">
											<option value="E">E</option>
											<option value="I">I</option>
										</select>
									</td>									
								</tr>
							<tr height="3px"></tr>
							<tr>
								<td width="20%" align="right">
									<bean:message bundle="lable" key="screen.account"/> 
								</td> 
								<td width="20%" align="left">
									<html:text property="accountno" styleId="accountno" size="20" maxlength="15" 
										onfocus="clearDivision('divAccountno')"/>										
									<font color="red">*</font><br/>
									<div id="divAccountno" class="validate"/>
								</td>
							</tr>															
						</table>
					</td>
				</tr>
				</table>
			</html:form>
    	</logic:equal>
    	
    	
    	<!-- Authorize record -->
    	<logic:equal name="duetype" property="action" value="authorize">
			<html:form action="dueTypeService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
				<input type="hidden" id="authorizeMode" name="authorizeMode"/>
				
				<table border="0">					
					<tr>
						<td>							
							<table class="InputTable">																	
								<tr>									
									<td colspan="2" align="center">
										<script>
											var myColumns = ["<bean:message bundle="lable" key="screen.code"/>",
							  								 "<bean:message bundle="lable" key="screen.description"/>",
							  								 "<bean:message bundle="lable" key="screen.sequenceno"/>",
							  								 "<bean:message bundle="lable" key="screen.status"/>"];
				            				var str = new AW.Formats.String; 
				            				var cellFormat = [str,str];
			                    			var grid = createBrowser(myColumns,'authorizeGrid',cellFormat);			                    						                    						                    			
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){	
			                                	if(row!='') { 
			                                		//getRecordData(grid.getCellText(2,row));
			                                		$('code').value        		= grid.getCellText(0,row);
			                                		$('description').value 		= grid.getCellText(1,row);
			                                		$('sequenceno').value  		= grid.getCellText(2,row);
			                                		$('authorizeMode').value 	= grid.getCellText(3,row);
			                                		$('recordId').value    		= grid.getCellText(4,row);
			                                		$('version').value     		= grid.getCellText(5,row);			                                		
													$('receiptpayment').value 	= grid.getCellText(6,row);
													$('odinterest').value  		= grid.getCellText(7,row);
													$('interexter').value  		= grid.getCellText(8,row);	
													$('accountno').value  		= grid.getCellText(9,row);																									
			                                	}																						
			                                };
			                                getAuthorizeGridData();
			                            </script>
			                        </td>
			                    </tr>            
			                </table>
						</td>
					</tr>
			          
					<tr height="3px"></tr>   			                
		          	<tr>
						<td>							
							<table class="InputTable">    
								<tr height="5px"></tr>  
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.productcode"/>
									</td>
									<td>
										<input type="hidden" id="productId" name="productId">
										<html:text property="productcode" styleId="productcode" value="PW" size="4" readonly="true" maxlength="2" styleClass="READONLYINPUT"/>	
										<input type="text" size="51" id="productdesc" name="productdesc" value="Pawing" readonly="true" class="READONLYINPUT"/>																				
										<font color="red">*</font><br/>
										<div id="divProductCode" class="validate"/>
									</td>
								</tr>
								
								<tr height="2px"></tr>   
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.code"/>
									</td>
									<td>
										<html:text property="code" styleId="code" size="4" maxlength="3" disabled="true" styleClass="READONLYINPUT"
											onblur="upperCase('code')" onfocus="clearDivision('divCode')"/>										
											<font color="red">*</font><br/>
											<div id="divCode" class="validate"/>
									</td>
								</tr>	
															
								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.description"/>
									</td>
									<td>
										<html:text property="description" styleId="description" size="58" maxlength="50" 
											onfocus="clearDivision('divDescription')" disabled="true" styleClass="READONLYINPUT"/>										
										<font color="red">*</font><br/>
										<div id="divDescription" class="validate"/>
									</td>
								</tr>
								
								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.sequenceno"/>
									</td>
									<td>
										<html:text property="sequenceno" styleId="sequenceno" size="4" maxlength="2" 
											onfocus="clearDivision('divSequenceno')" disabled="true" styleClass="READONLYINPUT"/>										
										<font color="red">*</font><br/>
										<div id="divSequenceno" class="validate"/>
									</td>
								</tr>
								
								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.receiptpayment"/>
									</td>
									<td>
										<select id="receiptpayment" name="receiptpayment" disabled="disabled" style="width: 41px;" class="READONLYINPUT">
											<option value="R">R</option>
											<option value="P">P</option>
										</select>
									</td>
								</tr>
								
								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.odinterestchng"/>
									</td>
									<td>
										<select id="odinterest" name="odinterest" disabled="disabled" style="width: 41px;" class="READONLYINPUT">
											<option value="Y">Y</option>
											<option value="N">N</option>
										</select>
									</td>
								</tr>
								
								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.internalexternal"/>
									</td>
									<td>
										<select id="interexter" name="interexter" disabled="disabled" style="width: 41px;" class="READONLYINPUT">
											<option value="E">E</option>
											<option value="I">I</option>
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
    	 	
  	</body>
</html:html>
