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
		<link rel="StyleSheet" type="text/css" href="css/com-all.css"></link>
		<script type="text/javascript" src="<html:rewrite forward="commonJS"/>"></script>
		<script type="text/javascript" src="<html:rewrite forward="ajaxJS"/>"></script>
		<script type="text/javascript" src="<html:rewrite forward="awJS"/>"></script>	
		<script type="text/javascript" src="js/com-all.js"></script>
		
		
		<script type="text/javascript">								
			var action = 1;
			
			var url = 'schemeService.do';
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
							$('code').value        = message[0];
							$('description').value = message[1];
							//$('cartageMarsterCode').value    = message[2];
							//$('cartageMarsterId').value    = message[3];
							//$('isActive').value    = message[4];
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
				data = "dispatch=getAjaxData&conditions=productId&value=" + $('productId').value;
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
				
				if(rurl=="interestRatesService.do")
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
				$('code').value		   					= '';
				$('description').value 					= '';
				$('productCode').value 					= '';
				$('productId').value   					= '0';
				$('prdDescription').value 				= '';
				
				$('cartageMarsterId').value 			= '0';
				$('cartageMarsterCode').value   		= '';
				$('ctMarsterDescription').value 		= '';
				
				$('interestId').value 					= '0';
				$('interestCode').value   				= '';
				$('interestDescription').value 			= '';
				$('period').value						= '0';
				$('isActive').value 					= '1';
				$('recordId').value    					= '';
				$('version').value     					= '';
				
				$('divCode').innerHTML 		  			= '';
				$('divDescription').innerHTML 			= '';
				$('divProductCode').innerHTML 			= '';
				$('divcartageMarsterCode').innerHTML 	= '';				
				$('divinterestCode').innerHTML 			= '';
				$('divPeriod').innerHTML 				= '';
				setGridData(grid,[]);
			}
			
			function clearFilled(){
				$('code').value		   = '';
				$('description').value = '';				
				$('recordId').value    = '';
				$('version').value     = '';
				$('cartageMarsterId').value = '0';
				$('cartageMarsterCode').value   = '';
				$('ctMarsterDescription').value = '';
				$('period').value	   = '0';
				
				$('interestId').value = '0';
				$('interestCode').value   = '';
				$('interestDescription').value = '';
				
				$('isActive').value = '1';
				
				$('divCode').innerHTML 		  = '';
				$('divDescription').innerHTML = '';
				$('divProductCode').innerHTML = '';
				$('divcartageMarsterCode').innerHTML = '';
				$('divinterestCode').innerHTML = '';
				$('divPeriod').innerHTML = '';
				grid.setSelectedRows([]);
			}
			
			function clearOtherData(){
				$('code').value		   = '';
				$('description').value = '';
				$('recordId').value    = '';
				$('version').value     = '';
				
				$('divCode').innerHTML 		  = '';
				$('divDescription').innerHTML = '';
				$('divProductCode').innerHTML = '';
				$('divcartageMarsterCode').innerHTML = '';
				$('divinterestCode').innerHTML = '';
				
				setGridData(grid,[]);
			}
			
			function getCreateData(){
				var code        		= $('code').value;
				var description 		= $('description').value;
				var productCode			= $('productCode').value;
				var productId   		= $('productId').value;
				var cartageMarsterCode 	= $('cartageMarsterCode').value;
				var cartageMarsterId   	= $('cartageMarsterId').value;
				var interestCode 		= $('interestCode').value;
				var interestId   		= $('interestId').value;
				var isActive   			= $('isActive').value;
				var period				= $('period').value;
				
				return "&code=" + code + "&description=" + description +
					   "&productCode=" + productCode + "&productId=" + productId +
					   "&cartageMarsterCode=" + cartageMarsterCode + "&cartageMarsterId=" + cartageMarsterId +
					   "&interestCode=" + interestCode + "&interestId=" + interestId + "&isActive=" + isActive +
					   "&period=" + period;
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
	                else if( message[i]['description'] )
	                    $('divDescription').innerHTML = message[i]['description'];
	               	else if( message[i]['productCode'] )
	                    $('divProductCode').innerHTML = message[i]['productCode'];
	                else if( message[i]['cartageMarsterCode'] )
	                    $('divcartageMarsterCode').innerHTML = message[i]['cartageMarsterCode'];
	                else if( message[i]['interestCode'] )
	                    $('divinterestCode').innerHTML = message[i]['interestCode'];
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
			
			#forthGrid {height: 210px;width:600px;}
			#forthGrid .aw-row-selector {text-align: center}
			#forthGrid .aw-column-0 {width: 100px;}
			#forthGrid .aw-column-1 {width: 455px;}	
			#forthGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#forthGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
			
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
											$('productCode').value   = this.getCellText(0,row);
											$('prdDescription').value= this.getCellText(1,row);
											$('productId').value     = this.getCellText(3,row);
											//$('hidDiv').className='hideSearchPopup';
				                        	winProduct.hide();
				                        	getGridData();	
										}catch(error){}
									};
									gridSerchPrd.onSelectedRowsChanged=function(row){
										try{											
											if(row!=''){
												$('productCode').value   = this.getCellText(0,row);
												$('prdDescription').value= this.getCellText(1,row);
												$('productId').value     = this.getCellText(3,row);												
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
		
		<div id="CaratageMaster-serchDiv" class="x-hidden">
        	<div class="x-window-header">
            	Search Caratage Master
            </div>
        	<div id="serch-tab1">
            	<div class="x-tab" title="Search">
					<table style="width: 600px">							
						<tr>
							<td>
								<script>
									var myColumnsSerchC = ["<bean:message bundle="lable" key="screen.code"/>","<bean:message bundle="lable" key="screen.description"/>"];
		            				var strC = new AW.Formats.String; 
		            				var cellFormatSerchC = [strC,strC];
	                    			var gridSerchCTM = createBrowser(myColumnsSerchC,'thirdGrid',cellFormatSerchC);							                    			 			                    			
	                    			gridSerchCTM.setHeaderHeight(25);
	                                document.write(gridSerchCTM);
	                                gridSerchCTM.onRowDoubleClicked = function(event, row){
										try{
											$('cartageMarsterCode').value   = this.getCellText(0,row);
											$('ctMarsterDescription').value= this.getCellText(1,row);
											$('cartageMarsterId').value     = this.getCellText(2,row);
											$('hidDiv').className='hideSearchPopup';
				                        	winCaratage.hide();
				                        	getGridData();	
										}catch(error){}
									};
									gridSerchCTM.onSelectedRowsChanged=function(row){
										try{											
											if(row!=''){
												$('cartageMarsterCode').value   = this.getCellText(0,row);
												$('ctMarsterDescription').value= this.getCellText(1,row);
												$('cartageMarsterId').value     = this.getCellText(2,row);												
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
		
		<div id="InterestRate-serchDiv" class="x-hidden">
        	<div class="x-window-header">
            	Search Interest Rate
            </div>
        	<div id="serch-tab2">
            	<div class="x-tab" title="Search">
					<table style="width: 600px">							
						<tr>
							<td>
								<script>
									var myColumnsSerchI = ["<bean:message bundle="lable" key="screen.code"/>","<bean:message bundle="lable" key="screen.description"/>"];
		            				var strI = new AW.Formats.String; 
		            				var cellFormatSerchI = [strI,strI];
	                    			var gridSerchINT = createBrowser(myColumnsSerchI,'forthGrid',cellFormatSerchI);							                    			 			                    			
	                    			gridSerchINT.setHeaderHeight(25);
	                                document.write(gridSerchINT);
	                                gridSerchINT.onRowDoubleClicked = function(event, row){
										try{
											$('interestCode').value   		= this.getCellText(0,row);
											$('interestDescription').value	= this.getCellText(1,row);
											$('interestId').value     		= this.getCellText(2,row);
											//$('hidDiv').className='hideSearchPopup';
				                        	winInterest.hide();
				                        	getGridData();	
				                        	
										}catch(error){}
									};
									gridSerchINT.onSelectedRowsChanged=function(row){
										try{											
											if(row!=''){
												$('interestCode').value        = this.getCellText(0,row);
												$('interestDescription').value = this.getCellText(1,row);
												$('interestId').value          = this.getCellText(2,row);												
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
		
		<div id="hidDiv" class="hideSearchPopup">
		</div>
    	
    	<!-- Create record -->
    	<logic:equal name="schemes" property="action" value="create">
			<html:form action="schemeService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
 
				<table border="0"  >
					<tr>
						<td>							
							<table class="InputTable" >    
								<tr height="5px"></tr> 
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.product"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="productId" name="productId" value="0"/>
										<html:text property="productCode" styleId="productCode" size="5" maxlength="3" 
											onfocus="clearDivision('divProductCode')"
											onblur="upperCase('productCode');commonSearch('schemeService.do','productId','productCode','prdDescription','getProduct','divProductCode',function(){clearOtherData();getGridData();},'',function(){clearAll();})"/>
										<input id="ButtonProductSerch" type="button" value="..." />
										<input type="text" size="60" id="prdDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divProductCode" class="validate"></div>
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
											var myColumns = ["<bean:message bundle="lable" key="screen.code"/>",
							  								 "<bean:message bundle="lable" key="screen.description"/>"];
				            				var str = new AW.Formats.String; 
				            				var cellFormat = [str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);			                    						                    						                    			
			                                document.write(grid);
			                              //  grid.onSelectedRowsChanged = function(row){	
			                              //  	if(row!='') { 			                                		
			                              //  		$('code').value           = grid.getCellText(0,row);
			                              //  		$('description').value    = grid.getCellText(1,row);
			                              //  		$('recordId').value       = grid.getCellText(8,row);
			                              //  		$('version').value        = grid.getCellText(9,row);
			                              //  	}																						
			                              //  };
			                                //getGridData();
											
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
										<bean:message bundle="lable" key="screen.code"/>&nbsp;
									</td>
									<td>
										<html:text property="code" styleId="code" size="5" maxlength="3"
											onblur="upperCase('code')" onfocus="clearDivision('divCode')"/>										
											<font color="red">*</font><br/>
											<div id="divCode" class="validate"></div>
									</td>
								</tr>	
								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.description"/>&nbsp;
									</td>
									<td>
										<html:text property="description" styleId="description" size="70" maxlength="50" 
										 	onfocus="clearDivision('divDescription')"/>										
										<font color="red">*</font><br/>
										<div id="divDescription" class="validate"></div>
									</td>
								</tr>
								<tr height="2px"></tr>
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.interestCode"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="interestId" name="interestId" value="0"/>
										<html:text property="interestCode" styleId="interestCode" size="5" maxlength="3" 
											onfocus="clearDivision('divinterestCode')"
											onblur="upperCase('interestCode');commonSearch('schemeService.do','interestId','interestCode','interestDescription','getInterestRates','divinterestCode',function(){getGridData();},'&interestId='+$('interestId').value,function(){setGridData(grid,[]);})"/>
										<input id="ButtonInterest" type="button" value="..." />
										<input type="text" size="60" id="interestDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divinterestCode" class="validate"/>
									</td>
								</tr>
								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.cartagemaster"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="cartageMarsterId" name="cartageMarsterId" value="0"/>
										<html:text property="cartageMarsterCode" styleId="cartageMarsterCode" size="5" maxlength="3" 
											onfocus="clearDivision('divcartageMarsterCode')"
											onblur="upperCase('cartageMarsterCode');commonSearch('schemeService.do','cartageMarsterId','cartageMarsterCode','ctMarsterDescription','getCartageMarster','divcartageMarsterCode')"/>
										<input id="ButtonCaratage" type="button" value="..." />
										<input type="text" size="60" id="ctMarsterDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divcartageMarsterCode" class="validate"/>
									</td>
								</tr>
								<tr height="2px"></tr>
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.period"/>&nbsp;
									</td>
									<td>
										<html:text property="period" styleId="period" size="5" maxlength="2"
											onfocus="clearDivision('divPeriod')"/>
										<font color="red">*</font><br/>
										<div id="divPeriod" class="validate"/>
									</td>
								</tr>
								<tr height="2px"></tr>
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.isactive"/>&nbsp;
									</td>
									<td>
										<select id="isActive" name="isActive" style="width: 50px;font-size: 12px">
											<option value="1">Yes</option>
											<option value="0">No</option>
										</select>
									</td>
								</tr>								
								<tr height="5px"></tr>															
							</table>
						</td>
					</tr>
					<tr height="5px"></tr>
				</table>
		
			</html:form>					
    	</logic:equal>
    	
    	
    	<!-- update record -->
    	<logic:equal name="schemes" property="action" value="update">
			<html:form action="schemeService.do">
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
										<html:text property="productCode" styleId="productCode" size="5" maxlength="3" disabled="true" styleClass="READONLYINPUT"
											onfocus="clearDivision('divProductCode')"
											onblur="upperCase('productCode');commonSearch('schemeService.do','productId','productCode','prdDescription','getProduct','divProductCode',function(){clearOtherData();},'',function(){clearAll();})"/>
										<input id="ButtonProductSerch" type="button" value="..." />
										<input type="text" size="60" id="prdDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divProductCode" class="validate"></div>
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
									<td colspan="4" align="center">
										<script>
											var myColumns = ["<bean:message bundle="lable" key="screen.code"/>",
							  								 "<bean:message bundle="lable" key="screen.description"/>"];
				            				var strthird = new AW.Formats.String; 
				            				var cellFormat = [strthird,strthird,strthird,strthird];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);			                    						                    						                    			
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){	
			                               	if(row!='') { 			                                		
			                                		$('code').value           = grid.getCellText(0,row);
			                                		$('description').value    = grid.getCellText(1,row);
			                                		$('cartageMarsterId').value = grid.getCellText(5,row);
			                                		$('cartageMarsterCode').value = grid.getCellText(6,row);
			                                		$('ctMarsterDescription').value = grid.getCellText(7,row);
			                                		$('isActive').value		  = grid.getCellText(8,row);
			                                		$('recordId').value       = grid.getCellText(9,row);
			                               			$('version').value        = grid.getCellText(10,row);
			                               			$('interestId').value        = grid.getCellText(11,row);
			                               			$('interestCode').value        = grid.getCellText(12,row);
			                               			$('interestDescription').value = grid.getCellText(13,row);
			                               			$('period').value		  = grid.getCellText(14,row);
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
										<bean:message bundle="lable" key="screen.code"/>&nbsp;
									</td>
									<td>
										<html:text property="code" styleId="code" size="5" maxlength="3" styleClass="READONLYINPUT" readonly="true"
											onblur="upperCase('code')" onfocus="clearDivision('divCode')"/>										
											<font color="red">*</font><br/>
											<div id="divCode" class="validate"></div>
									</td>
								</tr>	
															
								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.description"/>&nbsp;
									</td>
									<td>
										<html:text property="description" styleId="description" size="70" maxlength="50" 
										onblur="upperCase('description')"	onfocus="clearDivision('divDescription')"/>										
										<font color="red">*</font><br/>
										<div id="divDescription" class="validate"></div>
									</td>
								</tr>
								<tr height="2px"></tr>
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.interestCode"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="interestId" name="interestId" value="0"/>
										<html:text property="interestCode" styleId="interestCode" size="5" maxlength="3" disabled="true" styleClass="READONLYINPUT"
											onfocus="clearDivision('divinterestCode')"
											onblur="upperCase('interestCode');commonSearch('schemeService.do','interestId','interestCode','interestDescription','getInterestRates','divinterestCode',function(){getGridData();},'&interestId='+$('interestId').value,function(){setGridData(grid,[]);})"/>
										<input id="ButtonInterest" type="button" value="..." disabled="true" Class="READONLYINPUT" />
										<input type="text" size="60" id="interestDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divinterestCode" class="validate"/>
									</td>
								</tr>
								<tr height="2px"></tr>
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.cartagemaster"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="cartageMarsterId" name="cartageMarsterId" value="0"/>
										<html:text property="cartageMarsterCode" styleId="cartageMarsterCode" size="5" maxlength="3" disabled="true" styleClass="READONLYINPUT"
											onfocus="clearDivision('divcartageMarsterCode')"
											onblur="upperCase('cartageMarsterCode');commonSearch('schemeService.do','cartageMarsterId','cartageMarsterCode','ctMarsterDescription','getCartageMarster','divcartageMarsterCode',function(){clearOtherData();},'',function(){clearAll();})"/>
										<input id="ButtonCaratage" type="button" value="..." disabled="true" Class="READONLYINPUT" />
										<input type="text" size="60" id="ctMarsterDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divcartageMarsterCode" class="validate"></div>
									</td>
								</tr>
								<tr height="2px"></tr>
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.period"/>&nbsp;
									</td>
									<td>
										<html:text property="period" styleId="period" size="5" maxlength="2"
											onfocus="clearDivision('divPeriod')"/>
										<font color="red">*</font><br/>
										<div id="divPeriod" class="validate"/>
									</td>
								</tr>
								<tr height="2px"></tr>
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.isactive"/>&nbsp;
									</td>
									<td>
										<select id="isActive" name="isActive" style="width: 50px;font-size: 12px">
											<option value="1">Yes</option>
											<option value="0">No</option>
										</select>
									</td>
								</tr>									
								<tr height="5px"></tr>															
							</table>
						</td>
					</tr>
					
					<tr height="5px"></tr>
					
				</table>
			</html:form>
    	</logic:equal>
    	
    	
    	
    	
    	<!-- View record -->
    	<logic:equal name="schemes" property="action" value="view">
    		<br/>
			<html:form action="schemeService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
				
				</html:form>
    	</logic:equal>
    	
    	<script>
			var winProduct;
			var winCaratage;
			var winInterest;
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
			                        getGridData();
			                   	}
			                }]
			            });
			        }
			        winProduct.show(this);
			        getSerchData("productService.do",gridSerchPrd);			        
			    });
			    
			    
			    var button1 = Ext.get('ButtonCaratage');
			    button1.on('click', function(){
			        if(!winCaratage){
			            winCaratage = new Ext.Window({
			                el:'CaratageMaster-serchDiv',
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
			                   		//getTableData();
			                   		//$('hidDiv').className='hideSearchPopup';
			                        winCaratage.hide();
			                        getGridData();
			                   	}
			                }]
			            });
			        }
			        winCaratage.show(this);
			        getSerchData("cartageMarsterService.do",gridSerchCTM);			        
			    });
			    
			    
			     var button2 = Ext.get('ButtonInterest');
			     button2.on('click', function(){
			        if(!winInterest){
			            winInterest = new Ext.Window({
			                el:'InterestRate-serchDiv',
			                layout:'fit',
			                width:600,
			                height:300,
			                closable:false, 
			                closeAction:'hide',
			                plain: true,
			
			                items: new Ext.TabPanel({
			                    el: 'serch-tab2',
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
			                        winInterest.hide();
			                        getGridData();
			                   	}
			                }]
			            });
			        }
			        winInterest.show(this);
			        getSerchData("interestRatesService.do",gridSerchINT);			        
			    });
			    
			    
			});
		</script>
  	</body>
</html:html>
