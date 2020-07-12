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
			
			var url = 'interestRatesService.do';
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
			
			function createSlabBrowser(myColumns,gridName,dataFormat){
				var obj = new AW.UI.Grid;
				obj.setId(gridName);				
				obj.setCellFormat(cellFormat);
				obj.setHeaderText(myColumns);
				obj.setSelectorVisible(true);
				obj.setRowCount(0);
				obj.setColumnCount(myColumns.length);
				obj.setSelectorText(function(i){return this.getRowPosition(i)+ 1});
				obj.setSelectorWidth(20);
				obj.setHeaderHeight(20);
				obj.setVirtualMode(false);	
				obj.setCellText(function(c,r){return myData[r][c]} ); 
				//obj.setSelectionMode("single-row");			
				
				//obj.setCellEditable(true, 2);
				obj.setCellEditable(true, 3);				
			 	obj.onCellEditEnded = function(text, col, row){	
			 		if(col==2 && text<obj.getCellText(1,row)){
			 			var gridArray = new Array();
						for(var i=0;i<obj.getRowCount()-1;i++){							
							var tempArray = new Array();
							tempArray[0] = obj.getCellText(0,i);
							tempArray[1] = obj.getCellText(1,i);
							tempArray[2] = obj.getCellText(2,i);
							tempArray[3] = obj.getCellText(3,i);
							gridArray[i] = tempArray;
						}
						gridArray[gridArray.length] = [gridArray.length+1,obj.getCellText(1,row),999,0];
						setGridData(obj,gridArray);	
			 		}else if(col==2 && text!=999){
						var gridArray = new Array();
						for(var i=0;i<obj.getRowCount();i++){							
							var tempArray = new Array();
							tempArray[0] = obj.getCellText(0,i);
							tempArray[1] = obj.getCellText(1,i);
							tempArray[2] = obj.getCellText(2,i);
							tempArray[3] = obj.getCellText(3,i);
							gridArray[i] = tempArray;
						}
						gridArray[gridArray.length] = [gridArray.length+1,text*1+1,999,0];
						setGridData(obj,gridArray);						
					}
				}
				
				obj.onCellTextChanging = function(text,column,row){				    			       
			        if(column == 2 || column == 3){			        	
				        if (text.match(/[^0-9.+-]/) || text>999){
				            return "error"; // prevent non-digits
				        }
				    }    
				}
				//obj.getCellTemplate(2, 0).setStyle("border", "1px solid #ff0000");
				
				obj.onCellDoubleClicked = function(event, col, row){
					if((col==2 && obj.getCellText(2,row)==999) || col==3){
					   	obj.setCellEditable(true, 2, row);
					}else{
						obj.setCellEditable(false, 2, row);
					}					
				}; 
							
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
				data = "dispatch=getAjaxData&conditions=productId&value=" + $('productId').value ;
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
			
			function getSlabData(id){	
				data = "dispatch=getSlabs&conditions=productId<next>interestSlabCode&value=" + $('productId').value + '<next>' + id;
				var mySearchRequest = new ajaxObject(url);
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
					if (responseStatus==200) {						
						var message =  eval('(' + responseText + ')');
						if(message['error']){
							alert(message['error']);
						}else{											
							setGridData(secGrid,message);					
						}	
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request'); 
				    }
				}
				mySearchRequest.update(data,'POST');
			}
						
			function getSerchData(rurl,gridobj,rData){
				data = "dispatch=getAjaxData" + rData;				 				
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
			
			function getAuthorizeData(){
				var recordId 		= $('recordId').value;
				var version  		= $('version').value;
				var authorizeMode	= $('authorizeMode').value;
				
				var str = "&recordId=" + recordId + "&version=" + version + "&authorizeMode=" + authorizeMode;				
				return str;
			}
			
			function clearAll(){
				$('code').value		   = '';
				$('description').value = '';
				$('productCode').value = '';
				$('productId').value   = '0';
				$('prdDescription').value = '';
				$('recordId').value    = '';
				$('version').value     = '';
				$('isActive').value     = '1';
				
				$('divCode').innerHTML 		  = '';
				$('divDescription').innerHTML = '';
				$('divProductCode').innerHTML = '';
				setGridData(grid,[]);
				setGridData(secGrid,[[1,1,999,0]]);
			}
			
			function clearFilled(){
				$('code').value		   = '';
				$('description').value = '';				
				$('recordId').value    = '';
				$('version').value     = '';
				$('isActive').value     = '1';
				
				
				$('divCode').innerHTML 		  = '';
				$('divDescription').innerHTML = '';
				$('divProductCode').innerHTML = '';
				grid.setSelectedRows([]);
				setGridData(secGrid,[[1,1,999,0]]);
			}
			
			function clearOtherData(){
				$('code').value		   = '';
				$('description').value = '';
				$('recordId').value    = '';
				$('version').value     = '';
				
				$('divCode').innerHTML 		  = '';
				$('divDescription').innerHTML = '';
				$('divProductCode').innerHTML = '';
				setGridData(grid,[]);
				setGridData(secGrid,[[1,1,999,0]]);
			}
			
			function getCreateData(){
				var code        = $('code').value;
				var description = $('description').value;
				var productCode = $('productCode').value;
				var productId   = $('productId').value;
				var isActive    = $('isActive').value;
				
				var slabs = '';//format(slabNo<@>noOfDaysFrom<@>noOfDaysTo<@>rate)
				slabs = secGrid.getCellText(0,0) + '<@>' + secGrid.getCellText(1,0) + '<@>' +
						secGrid.getCellText(2,0) + '<@>' + secGrid.getCellText(3,0);		
				for(var i=1;i<secGrid.getRowCount();i++){	
					slabs += '<next>' + secGrid.getCellText(0,i) + '<@>' + secGrid.getCellText(1,i) + '<@>' +
								secGrid.getCellText(2,i) + '<@>' + secGrid.getCellText(3,i);		
				}
								
				return "&code=" + code + "&description=" + description +
					   "&productCode=" + productCode + "&productId=" + productId + 
					   "&isActive=" + isActive + "&slabs=" + slabs;
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
			
			#thirdGrid {height: 100px;width:720px;}
			#thirdGrid .aw-row-selector {text-align: center}
			#thirdGrid .aw-column-0 {width: 100px;}
			#thirdGrid .aw-column-1 {width: 200px;}
			#thirdGrid .aw-column-2 {width: 200px;}
			#thirdGrid .aw-column-3 {width: 100px;}
			#thirdGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#thirdGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
			
			#authorizeGrid {height: 250px;width:720px;}
			#authorizeGrid .aw-row-selector {text-align: center}
			#authorizeGrid .aw-column-0 {width: 130px;}
			#authorizeGrid .aw-column-1 {width: 430px;}
			#authorizeGrid .aw-column-2 {width: 120px;}	
			#authorizeGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#authorizeGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
			
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
											$('hidDiv').className='hideSearchPopup';
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
		
		<div id="hidDiv" class="hideSearchPopup">
		</div>
    	
    	<!-- Create record -->
    	<logic:equal name="interestrates" property="action" value="create">
			<html:form action="interestRatesService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>

				<table border="0">
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
											onblur="upperCase('productCode');commonSearch('interestRatesService.do','productId','productCode','prdDescription','getProduct','divProductCode',function(){getGridData();},'',function(){clearAll();})"/>
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
					<tr>
						<td>							
							<table class="InputTable">																	
								<tr>									
									<td colspan="4" align="center">
										<script>
											var mySecColumns = ["<bean:message bundle="lable" key="screen.index"/>",
																"<bean:message bundle="lable" key="screen.noDaysFrom"/>",
							  								    "<bean:message bundle="lable" key="screen.noDaysTo"/>",
							  								   	"<bean:message bundle="lable" key="screen.interestRate"/>"];
				            				var strSec = new AW.Formats.String; 
				            				var secCellFormat = [strSec,strSec,strSec,strSec];
			                    			var secGrid = createSlabBrowser(mySecColumns,'thirdGrid',secCellFormat);			                    						                    						                    			
			                                document.write(secGrid);
			                                setGridData(secGrid,[[1,1,999,0]]);
			                                secGrid.onSelectedRowsChanged = function(row){				                                	
			                                	if(row!='') { 
	
			                                	}																						
			                                }
			                            </script>
			                        </td>
			                    </tr>            
			                </table>
						</td>
					</tr>
				</table>
		
			</html:form>					
    	</logic:equal>
    	
    	
    	<!-- update record -->
    	<logic:equal name="interestrates" property="action" value="update">
			<html:form action="interestRatesService.do">
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
											onblur="upperCase('productCode');commonSearch('interestRatesService.do','productId','productCode','prdDescription','getProduct','divProductCode',function(){getGridData();},'',function(){clearAll();})"/>
										<input id="ButtonProductSerch" type="button" value="..." />
										<input type="text" size="60" id="prdDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divProductCode" class="validate"/>
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
			                                		$('recordId').value       = grid.getCellText(2,row);
			                               			$('version').value        = grid.getCellText(3,row);
			                               			$('isActive').value       = grid.getCellText(4,row);
			                               			getSlabData($('recordId').value);
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
											<div id="divCode" class="validate"/>
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
										<div id="divDescription" class="validate"/>
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
					<tr>
						<td>							
							<table class="InputTable">																	
								<tr>									
									<td colspan="2" align="center">
										<script>
											var mySecColumns = ["<bean:message bundle="lable" key="screen.index"/>",
																"<bean:message bundle="lable" key="screen.noDaysFrom"/>",
							  								    "<bean:message bundle="lable" key="screen.noDaysTo"/>",
							  								   	"<bean:message bundle="lable" key="screen.interestRate"/>"];
				            				var strSec = new AW.Formats.String; 
				            				var secCellFormat = [strSec,strSec,strSec,strSec];
			                    			var secGrid = createSlabBrowser(mySecColumns,'thirdGrid',secCellFormat);			                    						                    						                    			
			                                document.write(secGrid);
			                                setGridData(secGrid,[[1,1,999,0]]);
			                                setGridData(secGrid,[]);
			                                secGrid.onSelectedRowsChanged = function(row){				                                	
			                                	if(row!='') { 

			                                	}																						
			                                }		                             
			                            </script>
			                        </td>
			                    </tr>            
			                </table>
						</td>
					</tr>
				</table>
			</html:form>
    	</logic:equal>
    	
    	
    	<!-- Authorize record -->
    	<logic:equal name="interestrates" property="action" value="authorize">
			<html:form action="interestRatesService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
				<input type="hidden" id="authorizeMode" name="authorizeMode"/>
				
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
											onblur="upperCase('productCode');commonSearch('interestRatesService.do','productId','productCode','prdDescription','getProduct','divProductCode',function(){getAuthorizeGridData();},'',function(){clearAll();})"/>
										<input id="ButtonProductSerch" type="button" value="..." />
										<input type="text" size="60" id="prdDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divProductCode" class="validate"/>
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
							  								 "<bean:message bundle="lable" key="screen.description"/>",
							  								 "<bean:message bundle="lable" key="screen.status"/>"];
				            				var strthird = new AW.Formats.String; 
				            				var cellFormat = [strthird,strthird,strthird,strthird];
			                    			var grid = createBrowser(myColumns,'authorizeGrid',cellFormat);			                    						                    						                    			
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){	
				                               	if(row!='') { 			                                		
			                                		$('code').value           = grid.getCellText(0,row);
			                                		$('description').value    = grid.getCellText(1,row);
			                                		$('recordId').value       = grid.getCellText(2,row);
			                               			$('version').value        = grid.getCellText(3,row);
			                               			$('isActive').value       = grid.getCellText(4,row);
			                               			getSlabData($('recordId').value);
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
											<div id="divCode" class="validate"/>
									</td>
								</tr>	
															
								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.description"/>&nbsp;
									</td>
									<td>
										<html:text property="description" styleId="description" size="70" maxlength="50" styleClass="READONLYINPUT" readonly="true"
										onblur="upperCase('description')"	onfocus="clearDivision('divDescription')"/>										
										<font color="red">*</font><br/>
										<div id="divDescription" class="validate"/>
									</td>
								</tr>
								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.isactive"/>&nbsp;
									</td>
									<td>
										<select id="isActive" name="isActive" style="width: 50px;font-size: 12px" disabled="disabled" class="READONLYINPUT">
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
					<tr>
						<td>							
							<table class="InputTable">																	
								<tr>									
									<td colspan="2" align="center">
										<script>
											var mySecColumns = ["<bean:message bundle="lable" key="screen.index"/>",
																"<bean:message bundle="lable" key="screen.noDaysFrom"/>",
							  								    "<bean:message bundle="lable" key="screen.noDaysTo"/>",
							  								   	"<bean:message bundle="lable" key="screen.interestRate"/>"];
				            				var strSec = new AW.Formats.String; 
				            				var secCellFormat = [strSec,strSec,strSec,strSec];
			                    			var secGrid = createSlabBrowser(mySecColumns,'thirdGrid',secCellFormat);			                    						                    						                    			
			                                document.write(secGrid);
			                                setGridData(secGrid,[[1,1,999,0]]);
			                                setGridData(secGrid,[]);
			                                secGrid.setSelectionMode("single-row");			                                
			                                secGrid.onSelectedRowsChanged = function(row){				                                	
			                                	if(row!='') { 

			                                	}																						
			                                }		                             
			                            </script>
			                        </td>
			                    </tr>            
			                </table>
						</td>
					</tr>
				</table>
			</html:form>
    	</logic:equal>

    	
    	<script>
			var winProduct;
			var winArticleModel;
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
			        var data = "&conditions=recordStatus&value=1";
			        getSerchData("productService.do",gridSerchPrd,data);			        
			        //$('hidDiv').className='showSearchPopup';
			    });
			});
		</script>
  	</body>
</html:html>
