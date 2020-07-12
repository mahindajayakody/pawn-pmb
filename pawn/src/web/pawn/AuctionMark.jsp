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
			var action = 1;

			var url = 'auctionMarkService.do';
			function loadToolBar(){
			    if(CurrentPage(window.parent.frames['footer'].location.pathname)!='toolbar.jsp'){
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
			
			
		
			function createBrowserWithCheckBox(myColumns,gridName,dataFormat){
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
				obj.setCellTemplate(new AW.Templates.Checkbox, 0); 	
				
				obj.setSelectorVisible(true);
			    obj.getCheckedValue=function() {
			    	for(var i=0;i<obj.getRowCount();i++) { 
			            if(obj.getCellValue(0,i)) { 
			            } 
			        } 
			    }			    
			    obj.getCheckBoxValue=function(col,row) { 
			    	return obj.getCellValue(col,row);
			    }					
				return obj;
			}

			function setGridData(gridObj,Data){
				gridObj.setRowCount(Data.length);
				gridObj.setCellText(Data);
				gridObj.setSelectedRows([]);
				gridObj.refresh();
			}

			function getGridData(){
		
				if($('auctionId').value!=0){
					data = "dispatch=getAuctionTickets&branchId=" + $('branchId').value ;
					//data = "dispatch=getAuctionTickets";
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
				    	    alert(responseStatus + ' -- Error Processing Request 3');
					    }
					}
					mySearchRequest.update(data,'POST');
				}
			}



			function getItemData(){
		
				data = "dispatch=getArticle&ticketId=" + $('ticketId').value;	
				//data = "dispatch=getAuctionTickets";
				var mySearchRequest = new ajaxObject(url);
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
					
					if (responseStatus==200) {
						var message =  eval('(' + responseText + ')');
						if(message['error']){
							alert(message['error']);
						}else{
							setGridData(gridItem,message);
						}
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request 3');
				    }
				}
				mySearchRequest.update(data,'POST');
			}

			function getSerchData(rurl,gridobj){
			
				data = "dispatch=getAjaxData";
				
				if(rurl=="auctionMarkService.do")
				{
					data = "dispatch=getActiveAuction&conditions=branchId&value=" + $('branchId').value +
						"&conditions=status&value=1";
				}
						
				var mySearchRequest = new ajaxObject(rurl);
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
					if (responseStatus==200) {
						var message =  eval('(' + responseText + ')');
						setGridData(gridobj,message);
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request 1');
				    }
				}
				mySearchRequest.update(data,'POST');
			}

			function getAuthorizeGridData(){
				data = "dispatch=getAuthorizeData&conditions=branchId&value=" + $('branchId').value;
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
			    	    alert(responseStatus + ' -- Error Processing Request 2');
				    }
				}
				mySearchRequest.update(data,'POST');
			}

			

			function clearAll(){
			
				$('productCode').value 	= '';
				$('productId').value   	= '0';
				$('prdDescription').value = '';
				$('branchId').value 	= '0';
				$('branchCode').value   = '';
				$('branchDescription').value = '';
				$('recordId').value    	= '';
				$('version').value     	= '';
			
           		$('auctionId').value     = 0;
           		$('auctionCode').value   = '';
           		$('auctDescription').value   = '';
           		$('ticketId').value   = 0;
           		$('divauctionCode').innerHTML 	= '';
				$('divProductCode').innerHTML 	= '';
				setGridData(grid,[]);
				setGridData(gridItem,[]);
			}

			function clearBranchAll(){
	
				$('productCode').value 	= '';
				$('productId').value   	= '0';
				$('prdDescription').value = '';
				$('recordId').value    	= '';
				$('version').value     	= '';
				$('ticketId').value   = 0;
				$('auctionId').value     = 0;
           		$('auctionCode').value   = '';
           		$('auctDescription').value   = '';
           		
				$('divauctionCode').innerHTML 	= '';
				$('divProductCode').innerHTML 	= '';
				setGridData(grid,[]);
				setGridData(gridItem,[]);
			}



			function clearFilled(){
		
				$('recordId').value    = '';
				$('version').value     = '';
				$('ticketId').value   = 0;
				$('auctionId').value     = 0;
           		$('auctionCode').value   = '';
           		$('auctDescription').value   = '';
           		
				
				$('divauctionCode').innerHTML 	= '';
				$('divProductCode').innerHTML = '';
				$('divBranchCode').innerHTML= '';
				
				setGridData(grid,[]);
				setGridData(gridItem,[]);
			}

			function clearOtherData(){
				
				$('recordId').value    = '';
				$('version').value     = '';
				$('ticketId').value   = 0;
				
           		$('auctionId').value     = 0;
           		$('auctionCode').value   = '';
           		$('auctDescription').value   = '';
           		
           		$('divauctionCode').innerHTML 	= '';
				$('divProductCode').innerHTML = '';
				$('divBranchCode').innerHTML= '';
				setGridData(grid,[]);
				setGridData(gridItem,[]);
			}

			function getCreateData(){
				var branchId 	= $('branchId').value;
				var branchCode  = $('branchCode').value;
				var productId   = $('productId').value;
				var productCode = $('productCode').value;				
				var auctionId	= $('auctionId').value;	
				var upsetvalue  =unformatNumber( $('upsetvalue').value)*1;
				
				var tikets = '';
				for(var i=0;i<grid.getRowCount();i++) { 
		            if(grid.getCellValue(0,i)) { 
	                	tikets += '<@>' + grid.getCellValue(3,i);
		            } 
				}
				if(tikets==''){
					alert('Please select a Ticket ');
				}
				return 	"&branchId=" + branchId + "&branchCode=" + branchCode +
						"&productId=" + productId + "&productCode=" + productCode +"&auctionId="+auctionId+"&tikets="+ 
						tikets.substring(3) + "&upsetvalue=" +upsetvalue	;
			}

			function getChangedData(){
				var recordId = $('recordId').value;
				var version  = $('version').value;
				
				var str = getCreateData() + "&recordId=" + recordId + "&version=" + version;
				return str;
			}
			
			function selectTickets(){
				for(var i=0;i<grid.getRowCount();i++) {
	            	grid.setCellValue($('selectAll').checked,0,i); 
			    }
			}
			function showValidationErrors(message){
				for( var i =0; i < message.length ; i++){
	                if( message[i]['productCode'] )
	                    $('divProductCode').innerHTML = message[i]['productCode'];
	               	else if( message[i]['branchCode'] )
	                    $('divBranchCode').innerHTML = message[i]['branchCode'];
	            }
	        }
		</script>

		<style>
			#firstGrid {height: 200px;width:720px;}
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


			#fourthGrid {height: 200px;width:720px;}
			#fourthGrid .aw-row-selector {text-align: center}
			#fourthGrid .aw-column-0 {width: 100px;}
			#fourthGrid .aw-column-1 {width: 250px;}
			#fourthGrid .aw-column-2 {width: 400px;}
			#fourthGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#fourthGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
			
			
			
			#fifthGrid {height: 100px;width:720px;}
			#fifthGrid .aw-row-selector {text-align: center}
			#fifthGrid .aw-column-0 {width: 245px;}
			#fifthGrid .aw-column-1 {width: 150px;text-align: center;}	
			#fifthGrid .aw-column-2 {width: 150px;text-align: right;}	
			#fifthGrid .aw-column-3 {width: 150px;text-align: center;}	
			#fifthGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#fifthGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
			

			#sixthGrid {height: 210px;width:600px;}
			#sixthGrid .aw-row-selector {text-align: center}
			#sixthGrid .aw-column-0 {width: 100px;}
			#sixthGrid .aw-column-1 {width: 355px;}
			#sixthGrid .aw-column-2 {width: 100px;}
			#sixthGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#sixthGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}

			#authorizeGrid {height: 350px;width:720px;}
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
		<div id="branch-serchDiv" class="x-hidden">
        	<div class="x-window-header">
            	Search Branch
            </div>
        	<div id="serch-tab1">
            	<div class="x-tab" title="Search">
					<table style="width: 600px">
						<tr>
							<td>
								<script>
								
									var myColumnsSercha = ["<bean:message bundle="lable" key="screen.code"/>",
														   "<bean:message bundle="lable" key="screen.description"/>"];
		            				var stra = new AW.Formats.String;
		            				var cellFormatSercha = [stra,stra];
	                    			var gridSerchArt = createBrowser(myColumnsSercha,'thirdGrid',cellFormatSercha);
	                    			gridSerchArt.setHeaderHeight(25);
	                                document.write(gridSerchArt);
	                                gridSerchArt.onRowDoubleClicked = function(event, row){
										try{
											clearBranchAll();
											$('branchId').value  		= this.getCellText(18,row);
											$('branchCode').value    	= this.getCellText(0,row);
											$('branchDescription').value= this.getCellText(1,row);
											$('hidDiv').className		= 'hideSearchPopup';
				                        	winBranch.hide();				                        					                        	
										}catch(error){}
									};
									gridSerchArt.onSelectedRowsChanged=function(row){
										try{
											if(row!=''){
												clearBranchAll();
												$('branchId').value  		= this.getCellText(18,row);
												$('branchCode').value    	= this.getCellText(0,row);
												$('branchDescription').value= this.getCellText(1,row);
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
									var myColumnsSerchp = ["<bean:message bundle="lable" key="screen.code"/>",
														   "<bean:message bundle="lable" key="screen.product"/>"];
		            				var strp = new AW.Formats.String;
		            				var cellFormatSerchp = [strp,strp];
	                    			var gridSerchPrd = createBrowser(myColumnsSerchp,'secondGrid',cellFormatSerchp);
	                    			gridSerchPrd.setHeaderHeight(25);
	                                document.write(gridSerchPrd);
	                                gridSerchPrd.onRowDoubleClicked = function(event, row){
										try{
											clearOtherData();
											$('productId').value     = this.getCellText(3,row);
											$('productCode').value   = this.getCellText(0,row);
											$('prdDescription').value= this.getCellText(1,row);
											$('hidDiv').className='hideSearchPopup';
				                        	winProduct.hide();
										}catch(error){}
									};
									gridSerchPrd.onSelectedRowsChanged=function(row){
										try{
											if(row!=''){
												clearOtherData();
												$('productId').value     = this.getCellText(3,row);
												$('productCode').value   = this.getCellText(0,row);
												$('prdDescription').value= this.getCellText(1,row);												
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

		<!-- Auction  Browse -->
		<div id="auction-serchDiv" class="x-hidden">
        	<div class="x-window-header">
            	Search Auction 
            </div>
        	<div id="serch-tab4">
            	<div class="x-tab" title="Search">
					<table style="width: 600px">
						<tr>
							<td>
								<script>
									var myColumnsSerchL = ["<bean:message bundle="lable" key="screen.code"/>",
														"<bean:message bundle="lable" key="screen.description"/>",
														"<bean:message bundle="lable" key="screen.status"/>"];
		            				var strL = new AW.Formats.String;
		            				var cellFormatSerchL = [strL,strL,strL];
	                    			var gridSerchAuction = createBrowser(myColumnsSerchL,'sixthGrid',cellFormatSerchL);
	                    			gridSerchAuction.setHeaderHeight(25);
	                                document.write(gridSerchAuction);
	                                gridSerchAuction.onRowDoubleClicked = function(event, row){
										try{    
											$('auctionId').value     	 = this.getCellText(3,row);
											$('auctionCode').value   	 = this.getCellText(0,row);
											$('auctDescription').value   = this.getCellText(1,row);											
				                        	winAuction.hide();	
				                        	getGridData();				                        				                        					                        	
										}catch(error){}
									};
									gridSerchAuction.onSelectedRowsChanged=function(row){
										try{
											if(row!=''){
												$('auctionId').value     	 = this.getCellText(2,row);
												$('auctionCode').value  	 = this.getCellText(0,row);
												$('auctDescription').value   = this.getCellText(1,row);
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
		<!--  -->
		
		<div id="hidDiv" class="hideSearchPopup">
		</div>

    	<!-- Create record -->
    	<logic:equal name="auctionMark" property="action" value="create">
			<html:form action="auctionMarkService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
				<input type="hidden" id="ticketId" name=""ticketId""/>

				<table border="0">
					<tr>
						<td>
							<table class="InputTable">
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.branch"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="branchId" name="branchId" value="0"/>
										<html:text property="branchCode" styleId="branchCode" size="5" maxlength="3"
											onfocus="clearDivision('divBranchCode')"
											onblur="upperCase('branchCode');commonSearch('auctionMarkService.do','branchId','branchCode','branchDescription','getBranch','divBranchCode',function(){clearBranchAll();},' ',function(){clearAll();})"/>									
										<input id="ButtonBranchSerch" type="button" value="..." />
										<input type="text" size="60" id="branchDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divBranchCode" class="validate"/>
									</td>
							
								</tr>							
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.product"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="productId" name="productId" value="0"/>
										<html:text property="productCode" styleId="productCode" size="5" maxlength="3"
											onfocus="clearDivision('divProductCode')"
											onblur="upperCase('productCode');commonSearch('auctionMarkService.do','productId','productCode','prdDescription','getProduct','divProductCode',function(){getGridData();},' ', function(){clearOtherData();})"/>											
										<input id="ButtonProductSerch" type="button" value="..." />
										<input type="text" size="60" id="prdDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divProductCode" class="validate"/>
									</td>
								</tr>

							<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.auction"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="auctionId" name="auctionId" value="0"/>
										<input id="auctionCode" name="auctionCode" size="4" maxlength="3"  tabindex="1" class="READONLYINPUT" readonly="readonly"
											onfocus="clearDivision('divauctionCode')"
										    onblur="upperCase('auctionCode');commonSearch('auctionMarkService.do','auctionId','auctionCode','auctDescription','getAuction','divauctionCode',function(){getGridData();},' ', function(){clearOtherData();})"/>											
											
										<input id="ButtonAuctionSerch" type="button" value="..." />
										<input type="text" size="60" id="auctDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divauctionCode" class="validate"/>
									</td>
								<tr height="5px"></tr>
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
									<td>
										<input type="checkBox" id="selectAll"  align="bottom" onClick="selectTickets();"/><bean:message bundle="lable" key="screen.selectall" /><br/>
									</td>
								</tr>
								<tr>
									<td colspan="2" align="center">
										<script>
										
											var myColumns = ["<bean:message bundle="lable" key="screen.select"/>",
							  								 "<bean:message bundle="lable" key="screen.ticketnumber"/>",
							  								 "<bean:message bundle="lable" key="screen.name"/>"];
				            				var str = new AW.Formats.String;
				            				var cellFormat = [str,str,str];
			                    			var grid = createBrowserWithCheckBox(myColumns,'fourthGrid',cellFormat);
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){
			                                	if(row!='') {
			                                	    $('ticketId').value           = grid.getCellText(3,row);
			                                		getItemData();
			                                	}
			                                };
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
								<tr>
									<td colspan="2" align="center">
										<script>
										
											var myColumnsItem =["<bean:message bundle="lable" key="screen.article"/>",
															 "<bean:message bundle="lable" key="screen.netweight"/>",
															 "<bean:message bundle="lable" key="screen.disbursevalue"/>",
															 "<bean:message bundle="lable" key="screen.noofitems"/>"];
				            				var strItem = new AW.Formats.String;
				            				var cellFormatItem = [strItem,strItem,strItem,strItem];
			                    			var gridItem = createBrowser(myColumnsItem,'fifthGrid',cellFormatItem);
			                                document.write(gridItem);
			                                gridItem.onSelectedRowsChanged = function(row){
			                                	if(row!='') {
			                                		
			                                	}
			                                };
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
									<td width="40%" align="right">
										<bean:message bundle="lable" key="screen.upsetvalue" />&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td width="60%">
													<html:text property="upsetvalue" styleId="upsetvalue" value="0.00" size="23" maxlength="15" style="text-align: right;width: 92px" 
														onfocus="clearDivision('divUpsetvalue');this.maxLength=15;"
														onkeyup="this.value=formatNumber(unformatNumber(this.value));"/>														
													<font color="red">*</font><br/>
													<div id="divUpsetvalue" class="validate" />
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					
				</table>
			</html:form>
    	</logic:equal>
    	<!-- View record -->
    	<logic:equal name="auctionMark" property="action" value="authorize">
    		<br/>
			<html:form action="auctionMarkService.do">
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
										<bean:message bundle="lable" key="screen.branch"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="branchId" name="branchId" value="0"/>
										<html:text property="branchCode" styleId="branchCode" size="5" maxlength="3"
											onfocus="clearDivision('divBranchCode')"
											onblur="upperCase('branchCode');commonSearch('initiateAuctionService.do','branchId','branchCode','branchDescription','getBranch','divBranchCode',function(){getGridData();},'&productId='+$('productId').value,function(){setGridData(grid,[]);})"/>
										<input id="ButtonBranchSerch" type="button" value="..." />
										<input type="text" size="60" id="branchDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divBranchCode" class="validate"/>
									</td>
								</tr>							
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.product"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="productId" name="productId" value="0"/>
										<html:text property="productCode" styleId="productCode" size="5" maxlength="3"
											onfocus="clearDivision('divProductCode')"
											onblur="upperCase('productCode');commonSearch('initiateAuctionService.do','productId','productCode','prdDescription','getProduct','divProductCode',function(){clearOtherData();},'',function(){clearAll();})"/>
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
									<td colspan="2" align="center">
										<script>
											var myColumns = ["<bean:message bundle="lable" key="screen.code"/>",
							  								 "<bean:message bundle="lable" key="screen.description"/>",
														     "<bean:message bundle="lable" key="screen.status"/>"];
				            				var str = new AW.Formats.String;
				            				var cellFormat = [str,str,str];
			                    			var grid = createBrowser(myColumns,'authorizeGrid',cellFormat);
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){
			                                	if(row!='') {
			                                		$('code').value           = grid.getCellText(0,row);
			                                		$('description').value    = grid.getCellText(1,row);
			                                		$('authorizeMode').value  = grid.getCellText(2,row);
			                                		$('recordId').value       = grid.getCellText(3,row);
			                                		$('version').value        = grid.getCellText(4,row);
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

								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.description"/>&nbsp;
									</td>
									<td>
										<html:text property="description" styleId="description" size="70" maxlength="50" styleClass="READONLYINPUT" readonly="true"
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
    	
    	
    	

    	<script>
			var winProduct;
			var winBranch;
			var winPawner;
			var winOfficer;
			var winAuction;
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
			        //$('hidDiv').className='showSearchPopup';
			    });

			    var button1 = Ext.get('ButtonBranchSerch');
			    button1.on('click', function(){
			   
			        if(!winBranch){
			       
			            winBranch = new Ext.Window({
			                el:'branch-serchDiv',
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
			                        winBranch.hide();
			                        if($('branchId').value!='0' && $('branchId').value!=''){
			                        	<logic:equal name="auctionMark" property="action" value="authorize">
			                        		
			                        		getAuthorizeGridData();
			                        	</logic:equal>
			                        	<logic:notEqual name="auctionMark" property="action" value="authorize">
			                        		
			                        		getGridData();
			                        	</logic:notEqual>
			                        }
			                   	}
			                }]
			            });
			        }
			        winBranch.show(this);
			        getSerchData("branchService.do",gridSerchArt);
			        			        
			        //$('hidDiv').className='showSearchPopup';
			    });
			   
				//////// Begin Auction 
				
				var button5 = Ext.get('ButtonAuctionSerch');
			    button5.on('click', function(){
			   
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
			                    el: 'serch-tab4',
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
			        getSerchData("auctionMarkService.do",gridSerchAuction);
			        //$('hidDiv').className='showSearchPopup';
			    });
				
				////End Of Auction
			    
			    
			    
			});
			
		</script>
  	</body>
</html:html>
