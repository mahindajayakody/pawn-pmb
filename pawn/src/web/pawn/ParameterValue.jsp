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
			var action = 1;
			
			var url = 'parameterValueService.do';
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
							$('effDate').value     = message[0];
							$('paraValue').value   = message[1];
							$('recordId').value    = message[2];
							$('version').value     = message[3];					
						}	
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request'); 
				    }
				}
				mySearchRequest.update(data,'POST');
			}
			
			function getGridData(){				
				data = "dispatch=getAjaxData&conditions=parameterId&value=" + $('parameterId').value;
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
						
			function getSerchData(rurl,gridobj){
				data = "dispatch=getAjaxData";
				
				if(rurl=="parameterService.do")
					data += "&conditions=productId&value=" + $('productId').value; 
				 
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
			
			function clearAll(){
				$('effDate').value		   = '';
				$('paraValue').value = '';
				$('productCode').value = '';
				$('productId').value   = '0';
				$('prdDescription').value = '';
				$('parameterId').value = '0';
				$('parameterCode').value   = '';
				$('paraDescription').value = '';
				$('recordId').value    = '';
				$('version').value     = '';
				$('dataType').value     = '';
				
				$('divDataType').innerHTML= '';
				$('divEffDate').innerHTML 		  = '';
				$('divParaValue').innerHTML = '';
				$('divProductCode').innerHTML = '';
				$('divParameterCode').innerHTML= '';
				
				setGridData(grid,[]);
			}
			
			function clearFilled(){
				$('effDate').value		   = '';
				$('paraValue').value = '';				
				$('recordId').value    = '';
				$('version').value     = '';
				$('parameterId').value = '0';
				$('parameterCode').value   = '';
				$('paraDescription').value = '';
				$('dataType').value     = '';
				
				$('divDataType').innerHTML= '';
				$('divEffDate').innerHTML 		  = '';
				$('divParaValue').innerHTML = '';
				$('divProductCode').innerHTML = '';
				$('divParameterCode').innerHTML= '';
				grid.setSelectedRows([]);
			}
			
			function clearOtherData(){
				$('effDate').value		   = '';
				$('paraValue').value = '';
				$('parameterId').value = '0';
				$('parameterCode').value   = '';
				$('paraDescription').value = '';
				$('recordId').value    = '';
				$('version').value     = '';
				$('dataType').value     = '';
				
				$('divDataType').innerHTML= '';
				$('divEffDate').innerHTML 		  = '';
				$('divParaValue').innerHTML = '';
				$('divProductCode').innerHTML = '';
				$('divParameterCode').innerHTML= '';
				setGridData(grid,[]);
			}
			
			function getCreateData(){
				var effDate        	= $('effDate').value;
				var paraValue		= $('paraValue').value;
				var productCode 	= $('productCode').value;
				var productId   	= $('productId').value;
				var parameterId 	= $('parameterId').value;
				var parameterCode   = $('parameterCode').value;				
			
				
				return "&effDate=" + effDate + "&paraValue=" + paraValue +
					   "&productCode=" + productCode + "&productId=" + productId +
					   "&parameterId=" + parameterId + "&parameterCode=" + parameterCode;
			}
			
			function getChangedData(){
				var recordId = $('recordId').value;
				var version  = $('version').value;
				
				var str = getCreateData() + "&recordId=" + recordId + "&version=" + version;				
				return str;
			}
		
			function showValidationErrors(message){
	       		for( var i =0; i < message.length ; i++){	            	
	                if( message[i]['effDate'] )
	                    $('divEffDate').innerHTML = message[i]['effDate'];
	                else if( message[i]['paraValue'] )
	                    $('divParaValue').innerHTML = message[i]['paraValue'];
	               	else if( message[i]['productCode'] )
	                    $('divProductCode').innerHTML = message[i]['productCode'];
	               	else if( message[i]['parameterCode'] )
	                    $('divParameterCode').innerHTML = message[i]['parameterCode'];          
	            }    
	        }
		</script>
		
		<style>
			#firstGrid {height: 250px;width:720px;}
			#firstGrid .aw-row-selector {text-align: center}
			#firstGrid .aw-column-0 {width: 150px;}
			#firstGrid .aw-column-1 {width: 545px;}	
			#firstGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#firstGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
			
			#secondGrid {height: 210px;width:600px;}
			#secondGrid .aw-row-selector {text-align: center}
			#secondGrid .aw-column-0 {width: 100px;}
			#secondGrid .aw-column-1 {width: 455px;}	
			#secondGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#secondGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
			
			#thirdGrid {height: 210px;width:600px;}
			#thirdGrid .aw-row-selector {text-align: center}
			#thirdGrid .aw-column-0 {width: 100px;}
			#thirdGrid .aw-column-1 {width: 455px;}	
			#thirdGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#thirdGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
		</style>
  	</head>
  
  	<body onload="loadToolBar()">
    	<div id="screenCont" class="enableAll" align="center" ></div>
    	
    	<div id="product-serchDiv" class="x-hidden">
        	<div class="x-window-header">
            	Search Product
            </div>
        	<div id="serch-tab">
            	<div class="x-tab" title="Search">
					<table style="width: 600px">							
						<tr>
							<td>
								<script>
									var myColumnsSerchp = ["<bean:message bundle="lable" key="screen.code"/>","<bean:message bundle="lable" key="screen.product"/>"];
		            				var strp = new AW.Formats.String; 
		            				var cellFormatSerchp = [strp,strp];
	                    			var gridSerchPrd = createBrowser(myColumnsSerchp,'secondGrid',cellFormatSerchp);							                    			 			                    			
	                    			gridSerchPrd.setHeaderHeight(25);
	                                document.write(gridSerchPrd);
	                                gridSerchPrd.onRowDoubleClicked = function(event, row){
	                                	
										try{
											$('productId').value     = this.getCellText(3,row);
											$('productCode').value   = this.getCellText(0,row);
											$('prdDescription').value= this.getCellText(1,row);
											$('hidDiv').className='hideSearchPopup';
											winProduct.hide();
				                        	clearOtherData();			                        	
										}catch(error){}
									};
									gridSerchPrd.onSelectedRowsChanged=function(row){
										try{											
											if(row!=''){
												$('productId').value     = this.getCellText(3,row);
												$('productCode').value   = this.getCellText(0,row);
												$('prdDescription').value= this.getCellText(1,row);
												clearOtherData();
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
		
		<div id="parameter-serchDiv" class="x-hidden">
        	<div class="x-window-header">
            	Search ArticleModel
            </div>
        	<div id="serch-tab1">
            	<div class="x-tab" title="Search">
					<table style="width: 600px">							
						<tr>
							<td>
								<script>
									var myColumnsSercha = ["<bean:message bundle="lable" key="screen.code"/>","<bean:message bundle="lable" key="screen.paracode"/>"];
		            				var stra = new AW.Formats.String; 
		            				var cellFormatSercha = [stra,stra];
	                    			var gridSerchArt = createBrowser(myColumnsSercha,'thirdGrid',cellFormatSercha);							                    			 			                    			
	                    			gridSerchArt.setHeaderHeight(25);
	                                document.write(gridSerchArt);
	                                gridSerchArt.onRowDoubleClicked = function(event, row){
										try{
											$('parameterId').value  = this.getCellText(4,row);
											$('parameterCode').value    = this.getCellText(0,row);
											$('paraDescription').value  = this.getCellText(1,row);
											$('hidDiv').className='hideSearchPopup';
				                        	winParameter.hide();
				                        	getGridData();
				                        	setGridData(grid,[]);
										}catch(error){}
									};
									gridSerchArt.onSelectedRowsChanged=function(row){
										try{
											if(row!=''){
												$('parameterId').value  	= this.getCellText(4,row);
												$('parameterCode').value    = this.getCellText(0,row);
												$('paraDescription').value  = this.getCellText(1,row);
												//$('dataType').value  		= this.getCellText(2,row);
											}	
											getGridData();
										}catch(error){}
									}
	                                
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
    	<logic:equal name="parameterValue" property="action" value="create">
			<html:form action="parameterValueService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
				<table border="0">
					<tr>
						<td>							
							<table class="InputTable">    
								<tr height="5px"></tr> 
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.product"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="productId" name="productId" value="0"/>
										<html:text property="productCode" styleId="productCode" size="5" maxlength="3" 
											onfocus="clearDivision('divProductCode')"
											onblur="upperCase('productCode');commonSearch('parameterValueService.do','productId','productCode','prdDescription','getProduct','divProductCode',function(){clearOtherData();},'',function(){clearAll();})"/>
										<input id="ButtonProductSerch" type="button" value="..." />
										<input type="text" size="60" id="prdDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divProductCode" class="validate"/>
									</td>
								</tr>
								
								<tr height="5px"></tr>  
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.paracode"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="parameterId" name="parameterId" value="0"/>
										<html:text property="parameterCode" styleId="parameterCode" size="5" maxlength="3" 
											onfocus="clearDivision('divParameterCode')"
											onblur="upperCase('parameterCode');commonSearch('parameterValueService.do','parameterId','parameterCode','paraDescription','getParameter','divParameterCode',function(){getGridData();},'&productId='+$('productId').value,function(){setGridData(grid,[]);})"/>
										<input id="ButtonParameterSerch" type="button" value="..." />
										<input type="text" size="60" id="paraDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divParameterCode" class="validate"/>
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
									<td colspan="2" align="center">
										<script>
											var myColumns = ["<bean:message bundle="lable" key="screen.effdate"/>",
							  								 "<bean:message bundle="lable" key="screen.paravalue"/>"];
				            				var str = new AW.Formats.String; 
				            				var cellFormat = [str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);			                    						                    						                    			
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){	
			                                	if(row!='') { 			                                		
			                                		$('effDate').value        = grid.getCellText(0,row);
			                                		$('paraValue').value      = grid.getCellText(1,row);
			                                		$('dataType').value       = grid.getCellText(4,row);
			                                		$('recordId').value       = grid.getCellText(5,row);
			                                		$('version').value        = grid.getCellText(6,row);
			                                	}																						
			                                };
			                                //getGridData();
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
										<bean:message bundle="lable" key="screen.effdate"/>&nbsp;
									</td>
									<td>
										<html:text property="effDate" size="15" maxlength="12" styleId="effDate" onkeypress="DateFormat(this,event);" onfocus="clearDivision('divEffDate')" onkeyup="DateFormat(this,event);" /><img src="images/none.gif" width="5px" ><input type="button" value="..." onclick="return showCalendar('effDate');" />
										<font color="red">*</font><br/>
										<div id="divEffDate" class="validate"/>
									</td>
								</tr>	
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.datatype"/>&nbsp;
									</td>
									<td>
										<html:text property="dataType" styleId="dataType" style="width:91px" maxlength="10" 
											onfocus="clearDivision('divDataType')" styleClass="READONLYINPUT" readonly="true"/>										
										<div id="divDataType" class="validate"/>
									</td>
								</tr>
															
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.paravalue"/>&nbsp;
									</td>
									<td>
										<html:text property="paraValue" styleId="paraValue" size="15" maxlength="10" 
											onfocus="clearDivision('divParaValue')"/>										
										<font color="red">*</font><br/>
										<div id="divParaValue" class="validate"/>
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
    	<logic:equal name="parameterValue" property="action" value="update">
			<html:form action="parameterValueService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
				
				<table border="0">
					<tr>
						<td>							
							<table class="InputTable">    
								<tr height="5px"></tr> 
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.product"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="productId" name="productId" value="0"/>
										<html:text property="productCode" styleId="productCode" size="5" maxlength="3" 
											onfocus="clearDivision('divProductCode')"
											onblur="upperCase('productCode');commonSearch('articleService.do','productId','productCode','prdDescription','getProduct','divProductCode',function(){clearOtherData();},'',function(){clearAll();})"/>
										<input id="ButtonProductSerch" type="button" value="..." />
										<input type="text" size="60" id="prdDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divProductCode" class="validate"/>
									</td>
								</tr>
								
								<tr height="5px"></tr>  
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.paracode"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="parameterId" name="parameterId" value="0"/>
										<html:text property="parameterCode" styleId="parameterCode" size="5" maxlength="3" 
											onfocus="clearDivision('divParameterCode')"
											onblur="upperCase('parameterCode');commonSearch('parameterValueService.do','parameterId','parameterCode','paraDescription','getParameter','divParameterCode',function(){getGridData();},'&productId='+$('productId').value,function(){setGridData(grid,[]);})"/>
										<input id="ButtonParameterSerch" type="button" value="..." />
										<input type="text" size="60" id="paraDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divParameterCode" class="validate"/>
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
									<td colspan="2" align="center">
										<script>
											var myColumns = ["<bean:message bundle="lable" key="screen.effdate"/>",
							  								 "<bean:message bundle="lable" key="screen.paravalue"/>"];
				            				var str = new AW.Formats.String; 
				            				var cellFormat = [str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);			                    						                    						                    			
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){	
			                                	if(row!='') { 			                                		
			                                		$('effDate').value        = grid.getCellText(0,row);
			                                		$('paraValue').value      = grid.getCellText(1,row);
			                                		$('dataType').value       = grid.getCellText(4,row);
			                                		$('recordId').value       = grid.getCellText(5,row);
			                                		$('version').value        = grid.getCellText(6,row);
			                                	}																						
			                                };
			                               // getGridData();
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
										<bean:message bundle="lable" key="screen.effdate"/>&nbsp;
									</td>
									<td>
										<html:text property="effDate" size="15" maxlength="12" styleId="effDate" onkeypress="DateFormat(this,event);" onfocus="clearDivision('divEffDate')" onkeyup="DateFormat(this,event);" /><img src="images/none.gif" width="5px" ><input type="button" value="..." onclick="return showCalendar('effDate');" />
										<font color="red">*</font><br/>
										<div id="divEffDate" class="validate"/>
									</td>
								</tr>	
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.datatype"/>&nbsp;
									</td>
									<td>
										<html:text property="dataType" styleId="dataType" style="width:91px" maxlength="10" 
											onfocus="clearDivision('divDataType')" styleClass="READONLYINPUT" readonly="true"/>										
										<div id="divDataType" class="validate"/>
									</td>
								</tr>
															
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.paravalue"/>&nbsp;
									</td>
									<td>
										<html:text property="paraValue" styleId="paraValue" size="15" maxlength="10" 
											onfocus="clearDivision('divParaValue')"/>										
										<font color="red">*</font><br/>
										<div id="divParaValue" class="validate"/>
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
    	<logic:equal name="parameterValue" property="action" value="delete">
			<html:form action="parameterValueService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
				
								<table border="0">
					<tr>
						<td>							
							<table class="InputTable">    
								<tr height="5px"></tr> 
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.product"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="productId" name="productId" value="0"/>
										<html:text property="productCode" styleId="productCode" size="5" maxlength="3" 
											onfocus="clearDivision('divProductCode')"
											onblur="upperCase('productCode');commonSearch('articleService.do','productId','productCode','prdDescription','getProduct','divProductCode',function(){clearOtherData();},'',function(){clearAll();})"/>
										<input id="ButtonProductSerch" type="button" value="..." />
										<input type="text" size="60" id="prdDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divProductCode" class="validate"/>
									</td>
								</tr>
								
								<tr height="5px"></tr>  
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.paracode"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="parameterId" name="parameterId" value="0"/>
										<html:text property="parameterCode" styleId="parameterCode" size="5" maxlength="3" 
											onfocus="clearDivision('divParameterCode')"
											onblur="upperCase('parameterCode');commonSearch('parameterValueService.do','parameterId','parameterCode','paraDescription','getParameter','divParameterCode',function(){getGridData();},'&productId='+$('productId').value,function(){setGridData(grid,[]);})"/>
										<input id="ButtonParameterSerch" type="button" value="..." />
										<input type="text" size="60" id="paraDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divParameterCode" class="validate"/>
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
									<td colspan="2" align="center">
										<script>
											var myColumns = ["<bean:message bundle="lable" key="screen.effdate"/>",
							  								 "<bean:message bundle="lable" key="screen.paravalue"/>"];
				            				var str = new AW.Formats.String; 
				            				var cellFormat = [str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);			                    						                    						                    			
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){	
			                                	if(row!='') { 			                                		
			                                		$('effDate').value        = grid.getCellText(0,row);
			                                		$('paraValue').value      = grid.getCellText(1,row);
			                                		$('dataType').value       = grid.getCellText(4,row);
			                                		$('recordId').value       = grid.getCellText(5,row);
			                                		$('version').value        = grid.getCellText(6,row);
			                                	}																						
			                                };
			                                //getGridData();
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
										<bean:message bundle="lable" key="screen.effdate"/>&nbsp;
									</td>
									<td>
										<html:text property="effDate" size="15" maxlength="12" styleId="effDate" onkeypress="DateFormat(this,event);" onfocus="clearDivision('divEffDate')" onkeyup="DateFormat(this,event);" /><img src="images/none.gif" width="5px" ><input type="button" value="..." onclick="return showCalendar('effDate');" />
										<font color="red">*</font><br/>
										<div id="divEffDate" class="validate"/>
									</td>
								</tr>	
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.datatype"/>&nbsp;
									</td>
									<td>
										<html:text property="dataType" styleId="dataType" style="width:91px" maxlength="10" 
											onfocus="clearDivision('divDataType')" styleClass="READONLYINPUT" readonly="true"/>										
										<div id="divDataType" class="validate"/>
									</td>
								</tr>
															
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.paravalue"/>&nbsp;
									</td>
									<td>
										<html:text property="paraValue" styleId="paraValue" size="15" maxlength="10" 
											onfocus="clearDivision('divParaValue')"/>										
										<font color="red">*</font><br/>
										<div id="divParaValue" class="validate"/>
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
    	<logic:equal name="parameterValue" property="action" value="view">
			<html:form action="parameterValueService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
			</html:form>
    	</logic:equal>
    	
    	<script>
			var winProduct;
			var winParameter;
			Ext.onReady(function(){
			    
			    var button = Ext.get('ButtonProductSerch');
			    button.on('click', function(){
			        if(!winProduct){
			            winProduct = new Ext.Window({
			                el:'product-serchDiv',
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
			                   		//getTableData();
			                   		//$('hidDiv').className='hideSearchPopup';
			                        winProduct.hide();
			                   	}
			                }]
			            });
			        }
			        winProduct.show(this);
			        getSerchData("productService.do",gridSerchPrd);			        
			        //$('hidDiv').className='showSearchPopup';
			    });
			    
			    var button1 = Ext.get('ButtonParameterSerch');
			    button1.on('click', function(){
			        if(!winParameter){
			            winParameter = new Ext.Window({
			                el:'parameter-serchDiv',
			                layout:'fit',
			                width:600,
			                height:300,
			                closable:false, 
			                closeAction:'hide',
			                plain: true,
			
			                items: new Ext.TabPanel({
			                    el: 'serch-tab1',
			                    autoTabs:true,
			                    activeTab:0,
			                    deferredRender:false,
			                    border:false
			                }),
			
			                buttons: [{
			                    text: 'Ok',
			                    handler: function(){				                   		
			                   		getGridData();
			                   		//$('hidDiv').className='hideSearchPopup';
			                        winParameter.hide();
			                        if($('parameterId').value!='0' && $('parameterId').value!='')getGridData();
			                   	}
			                }]
			            });
			        }
			        winParameter.show(this);
			        getSerchData("parameterService.do",gridSerchArt);
			        //$('hidDiv').className='showSearchPopup';
			    });
			});
		</script>
  	</body>
</html:html>
