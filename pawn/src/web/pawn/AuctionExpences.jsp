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
			
			var url = 'auctionExpencesService.do';
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
				data = "dispatch=getAjaxData&conditions=auctionId&value=" + $('auctionId').value;
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
				//if(rurl=="initiateAuctionService.do")
					//data += "&conditions=productId&value=" + $('productId').value;
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
				//$('auctionCode').value 			= '';
				//$('auctionId').value   			= '0';
				//$('auctionDescription').value 	= '';
				
				$('dueTypeId').value 			= '0';
				$('dueTypeCode').value   		= '';
				$('dueTypeDescription').value 	= '';
				$('amount').value 			    = '0';
				
				//$('divDescription').innerHTML 	= '';
				//$('divAuctionCode').innerHTML 	= '';
				$('divDueTypeCode').innerHTML 	= '';	
				setGridData(grid,[]);
			}
			
			function clearFilled(){
				$('code').value		   = '';
				$('description').value = '';				
				$('recordId').value    = '';
				$('version').value     = '';
				$('dueTypeId').value   = '0';
				$('dueTypeCode').value = '';
				$('dueTypeDescription').value = '';
				$('amount').value 	= '0';
				
				$('divDescription').innerHTML = '';
				$('divAuctionCode').innerHTML = '';
				$('divDueTypeCode').innerHTML = '';
				grid.setSelectedRows([]);
			}
			
			function clearOtherData(){
				$('code').value		   = '';
				$('description').value = '';
				$('recordId').value    = '';
				$('version').value     = '';
				
				$('divDescription').innerHTML = '';
				$('divAuctionCode').innerHTML = '';
				$('divDueTypeCode').innerHTML = '';
				setGridData(grid,[]);
			}
			
			function getCreateData(){
				var auctionCode			= $('auctionCode').value;
				var auctionId   		= $('auctionId').value;
				var dueTypeCode 		= $('dueTypeCode').value;
				var dueTypeId   		= $('dueTypeId').value;
				var amount   			= unformatNumber($('amount').value)*1;
				return "&auctionCode=" + auctionCode + "&auctionId=" + auctionId +
					   "&dueTypeCode=" + dueTypeCode + "&dueTypeId=" + dueTypeId +
					   "&amount=" + amount;
			}
			
			function getChangedData(){
				var recordId = $('recordId').value;
				var version  = $('version').value;
				
				var str = getCreateData() + "&recordId=" + recordId + "&version=" + version;	
				return str;
			}
		
			function showValidationErrors(message){
	       		for( var i =0; i < message.length ; i++){	            	
					if( message[i]['auctionCode'] )
	                    $('divAuctionCode').innerHTML = message[i]['auctionCode'];
	                else if( message[i]['dueTypeCode'] )
	                    $('divDueTypeCode').innerHTML = message[i]['dueTypeCode'];
	            }    
	        }
		</script>
		
		<style>
			#firstGrid {height: 250px;width:720px;}
			#firstGrid .aw-row-selector {text-align: center}
			#firstGrid .aw-column-0 {width: 150px;}
			#firstGrid .aw-column-1 {width: 300px;}	
			#firstGrid .aw-column-2 {width: 200px;}
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
    	
    	<div id="auction-serchDiv" class="x-hidden">
        	<div class="x-window-header">
            	Search Auction
            </div>
        	<div id="serch-tab">
            	<div class="x-tab" title="Search">
					<table style="width: 600px">							
						<tr>
							<td>
								<script>
									var myColumnsSerchp = ["<bean:message bundle="lable" key="screen.code"/>",
														   "<bean:message bundle="lable" key="screen.description"/>"];
		            				var strp = new AW.Formats.String; 
		            				var cellFormatSerchp = [strp,strp];
	                    			var gridSerchPrd = createBrowser(myColumnsSerchp,'secondGrid',cellFormatSerchp);							                    			 			                    			
	                    			gridSerchPrd.setHeaderHeight(25);
	                                document.write(gridSerchPrd);
	                                gridSerchPrd.onRowDoubleClicked = function(event, row){
										try{
											$('auctionCode').value   = this.getCellText(0,row);
											$('auctionDescription').value= this.getCellText(1,row);
											$('auctionId').value     = this.getCellText(11,row);
											//$('hidDiv').className='hideSearchPopup';
				                        	winAuction.hide();
				                        	getGridData();	
										}catch(error){}
									};
									gridSerchPrd.onSelectedRowsChanged=function(row){
										try{											
											if(row!=''){
												$('auctionCode').value   = this.getCellText(0,row);
												$('auctionDescription').value= this.getCellText(1,row);
												$('auctionId').value     = this.getCellText(11,row);												
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
		
		<div id="DueType-serchDiv" class="x-hidden">
        	<div class="x-window-header">
            	Search Due Type
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
	                    			var gridSerchDT = createBrowser(myColumnsSerchI,'forthGrid',cellFormatSerchI);							                    			 			                    			
	                    			gridSerchDT.setHeaderHeight(25);
	                                document.write(gridSerchDT);
	                                gridSerchDT.onRowDoubleClicked = function(event, row){
										try{
											$('dueTypeCode').value   		= this.getCellText(0,row);
											$('dueTypeDescription').value	= this.getCellText(1,row);
											$('dueTypeId').value     		= this.getCellText(3,row);
											//$('hidDiv').className='hideSearchPopup';
				                        	winDueType.hide();
				                        	getGridData();	
				                        	
										}catch(error){}
									};
									gridSerchDT.onSelectedRowsChanged=function(row){
										try{											
											if(row!=''){
												$('dueTypeCode').value        = this.getCellText(0,row);
												$('dueTypeDescription').value = this.getCellText(1,row);
												$('dueTypeId').value          = this.getCellText(3,row);												
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
    	<logic:equal name="auctionexpences" property="action" value="create">
			<html:form action="auctionExpencesService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
 
				<table border="0"  >
					<tr>
						<td>							
							<table class="InputTable" >    
								<tr height="5px"></tr> 
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.auction"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="auctionId" name="auctionId" value="0"/>
										<html:text property="auctionCode" styleId="auctionCode" size="5" maxlength="3" 
											onfocus="clearDivision('divAuctionCode')"/>
										<input id="ButtonAuctionSerch" type="button" value="..." />
										<input type="text" size="60" id="auctionDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divAuctionCode" class="validate"></div>
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
											var myColumns = ["<bean:message bundle="lable" key="screen.duetype"/>",
							  								 "<bean:message bundle="lable" key="screen.description"/>",
							  								 "<bean:message bundle="lable" key="screen.dueamount"/>"];
				            				var str = new AW.Formats.String; 
				            				var cellFormat = [str,str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);			                    						                    						                    			
			                                document.write(grid);
			                               // grid.onSelectedRowsChanged = function(row){	
			                               // 	if(row!='') { 			                                		
			                               // 		$('dueTypeCode').value = grid.getCellText(0,row);
			                               // 		$('dueTypeDescription').value = grid.getCellText(1,row);
			                               //			$('recordId').value    = grid.getCellText(6,row);
			                               //			$('amount').value      = grid.getCellText(2,row);
			                               // 		$('version').value     = grid.getCellText(7,row);
			                               // 	}																						
			                               // };
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
										<bean:message bundle="lable" key="screen.duetype"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="dueTypeId" name="dueTypeId" value="0"/>
										<html:text property="dueTypeCode" styleId="dueTypeCode" size="5" maxlength="3" 
											onfocus="clearDivision('divDueTypeCode')"/>
										<input id="ButtonDueType" type="button" value="..." />
										<input type="text" size="60" id="dueTypeDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divDueTypeCode" class="validate"/>
									</td>
								</tr>
								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.dueamount"/>&nbsp;
									</td>
									<td>
										<html:text property="amount" styleId="amount" size="20" maxlength="15"
											onfocus="clearDivision('divAmount')"
											onkeyup="this.value=formatNumber(unformatNumber(this.value));"		 
											onblur="if(this.value.length=15){this.maxLength=18;};if(this.value.length<=1 && (this.value.substring(0,1)=='-' || this.value=='' || this.value=='.')){this.value='0.00'};this.value=formatNumber(parseFloat(unformatNumber(this.value)).toFixed(2))"/>
										<font color="red">*</font><br/>
										<div id="divAmount" class="validate"/>
									</td>
								</tr>
								<tr height="2px"></tr>								
							</table>
						</td>
					</tr>
					<tr height="5px"></tr>
				</table>
		
			</html:form>					
    	</logic:equal>
    	
    	
    	<!-- update record -->
    	<logic:equal name="auctionexpences" property="action" value="update">
			<html:form action="auctionExpencesService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
				
				<table border="0">
					<tr>
						<td>							
							<table class="InputTable">    
								<tr height="5px"></tr> 
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.auction"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="auctionId" name="auctionId" value="0"/>
										<html:text property="auctionCode" styleId="auctionCode" size="5" maxlength="3" 
											onfocus="clearDivision('divAuctionCode')"/>
										<input id="ButtonAuctionSerch" type="button" value="..." />
										<input type="text" size="60" id="auctionDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divAuctionCode" class="validate"></div>
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
											var myColumns = ["<bean:message bundle="lable" key="screen.duetype"/>",
							  								 "<bean:message bundle="lable" key="screen.description"/>",
							  								 "<bean:message bundle="lable" key="screen.dueamount"/>"];
				            				var str = new AW.Formats.String; 
				            				var cellFormat = [str,str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);			                    						                    						                    			
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){	
			                                	if(row!='') { 			                                		
			                                		$('dueTypeCode').value = grid.getCellText(0,row);
			                                		$('dueTypeDescription').value = grid.getCellText(1,row);
			                               			$('recordId').value    = grid.getCellText(6,row);
			                               			$('amount').value      = formatNumber(unformatNumber(grid.getCellText(2,row))) ;
			                                		$('version').value     = grid.getCellText(7,row);
			                                		$('dueTypeId').value   = grid.getCellText(3,row);	
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
										<bean:message bundle="lable" key="screen.duetype"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="dueTypeId" name="dueTypeId" value="0"/>
										<html:text property="dueTypeCode" styleId="dueTypeCode" size="5" 
											onfocus="clearDivision('divDueTypeCode')"/>
										<input id="ButtonDueType" type="button" value="..." readonly="readonly"/>
										<input type="text" size="60" id="dueTypeDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divDueTypeCode" class="validate"/>
									</td>
								</tr>
								<tr height="2px"></tr>
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.dueamount"/>&nbsp;
									</td>
									<td>
										<html:text property="amount" styleId="amount" size="20" maxlength="15"
											onfocus="clearDivision('divAmount')"
											onkeyup="this.value=formatNumber(unformatNumber(this.value));"		 
											onblur="if(this.value.length=15){this.maxLength=18;};if(this.value.length<=1 && (this.value.substring(0,1)=='-' || this.value=='' || this.value=='.')){this.value='0.00'};this.value=formatNumber(parseFloat(unformatNumber(this.value)).toFixed(2))"/>
										<font color="red">*</font><br/>
										<div id="divAmount" class="validate"/>
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

    	<!-- Remove record -->
    	<logic:equal name="auctionexpences" property="action" value="delete">
			<html:form action="auctionExpencesService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
				
				<table border="0">
					<tr>
						<td>							
							<table class="InputTable">    
								<tr height="5px"></tr> 
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.auction"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="auctionId" name="auctionId" value="0"/>
										<html:text property="auctionCode" styleId="auctionCode" size="5" maxlength="3"  
											onfocus="clearDivision('divAuctionCode')"/>
										<input id="ButtonAuctionSerch" type="button" value="..." />
										<input type="text" size="60" id="auctionDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divAuctionCode" class="validate"></div>
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
											var myColumns = ["<bean:message bundle="lable" key="screen.duetype"/>",
							  								 "<bean:message bundle="lable" key="screen.description"/>",
							  								 "<bean:message bundle="lable" key="screen.dueamount"/>"];
				            				var str = new AW.Formats.String; 
				            				var cellFormat = [str,str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);			                    						                    						                    			
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){	
			                                	if(row!='') { 			                                		
			                                		$('dueTypeCode').value = grid.getCellText(0,row);
			                                		$('dueTypeDescription').value = grid.getCellText(1,row);
			                               			$('recordId').value    = grid.getCellText(6,row);
			                               			$('amount').value      = grid.getCellText(2,row);
			                                		$('version').value     = grid.getCellText(7,row);
			                                		$('dueTypeId').value   = grid.getCellText(3,row);	
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
										<bean:message bundle="lable" key="screen.duetype"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="dueTypeId" name="dueTypeId" value="0"/>
										<html:text property="dueTypeCode" styleId="dueTypeCode" size="5" maxlength="3" 
											onfocus="clearDivision('divDueTypeCode')"/>
										<input id="ButtonDueType" type="button" value="..." />
										<input type="text" size="60" id="dueTypeDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divDueTypeCode" class="validate"/>
									</td>
								</tr>
								<tr height="2px"></tr>
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.dueamount"/>&nbsp;
									</td>
									<td>
										<html:text property="amount" styleId="amount" size="20" maxlength="15" 
											onfocus="clearDivision('divAmount')"/>
										<font color="red">*</font><br/>
										<div id="divAmount" class="validate"/>
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
    	<logic:equal name="auctionexpences" property="action" value="view">
    		<br/>
			<html:form action="auctionExpencesService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
				
				</html:form>
    	</logic:equal>
    	
    	<script>
			var winAuction;
			var winDueType;
			Ext.onReady(function(){
			    
			    var button = Ext.get('ButtonAuctionSerch');
			    button.on('click', function(){
			        if(!winAuction){
			            winAuction = new Ext.Window({
			                el:'auction-serchDiv',
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
			                        winAuction.hide();
			                        getGridData();
			                   	}
			                }]
			            });
			        }
			        winAuction.show(this);
			        getSerchData("initiateAuctionService.do",gridSerchPrd);			        
			    });
			    
			     var button2 = Ext.get('ButtonDueType');
			     button2.on('click', function(){
			        if(!winDueType){
			            winDueType = new Ext.Window({
			                el:'DueType-serchDiv',
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
			                        winDueType.hide();			                        
			                   	}
			                }]
			            });
			        }
			        winDueType.show(this);
			        getSerchData("dueTypeService.do",gridSerchDT);			        
			    });
			    
			    
			});
		</script>
  	</body>
</html:html>
