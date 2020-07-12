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


		<script type="text/javascript"><!--
			var action = 1;

			var url = 'initiateAuctionService.do';
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

			function getGridData(){
			
				data = "dispatch=getAjaxData&conditions=branchId&value=" + $('branchId').value ;
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

			function getSerchData(rurl,gridobj){
				data = "dispatch=getAjaxData";

				//if(rurl=="productService.do")
				//	data += "&conditions=branchId&value=" + $('branchId').value;

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
				alert("getAuthorizeGridData "+data);
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

			function getAuthorizeData(){
				var recordId 		= $('recordId').value;
				var version  		= $('version').value;
				var authorizeMode	= $('authorizeMode').value;

				var str = "&recordId=" + recordId + "&version=" + version + "&authorizeMode=" + authorizeMode;
				return str;
			}

			function clearAll(){
			
			
				$('code').value		   	= '';
				$('description').value 	= '';
				//$('productCode').value 	= '';
				//$('productId').value   	= '0';
				//$('prdDescription').value = '';
				//$('branchId').value 	= '0';
				//$('branchCode').value   = '';
				//$('branchDescription').value = '';
				$('recordId').value    	= '';
				$('version').value     	= '';
				
				
				$('pawnerId2').value      = 0;
           		$('pawnerCode2').value    = '';
           		$('pawnerName2').value 	  = '';
           		
           		$('pawnerId').value       = 0;
           		$('pawnerCode').value     = '';
           		$('pawnerName').value 	  = '';
           		
           		$('locationId').value     = 0;
           		$('locationCode').value   = '';
           		$('locationName').value   = '';
				$('auctionDate').value   = '';
				//$('isActive').value      = '1';
				$('divCode').innerHTML 		  	= '';
				$('divDescription').innerHTML 	= '';
				$('divProductCode').innerHTML 	= '';
				$('divPawnerCode').innerHTML 	= '';
				$('divPawnerCode2').innerHTML 	= '';
				$('divLocationCode').innerHTML 	= '';
				$('divBranchCode').innerHTML	= '';
				$('divAuctionDate').innerHTML 	= '';
				//setGridData(grid,[]);
			}

			function clearBranchAll(){
				
				$('code').value		   	= '';
				$('description').value 	= '';
				$('productCode').value 	= '';
				$('productId').value   	= '0';
				$('prdDescription').value = '';
				//$('branchId').value 	= '0';
				//$('branchCode').value   = '';
				//$('branchDescription').value = '';
				$('recordId').value    	= '';
				$('version').value     	= '';
				
				//$('isActive').value   = '1';
				$('pawnerId2').value      = 0;
           		$('pawnerCode2').value    = '';
           		$('pawnerName2').value 	  = '';
           		
           		$('pawnerId').value       = 0;
           		$('pawnerCode').value     = '';
           		$('pawnerName').value 	  = '';
           		
           		$('locationId').value     = 0;
           		$('locationCode').value   = '';
           		$('locationName').value   = '';
				$('auctionDate').value   = '';
				
				$('divCode').innerHTML 		  	= '';
				$('divDescription').innerHTML 	= '';
				$('divProductCode').innerHTML 	= '';
				$('divPawnerCode').innerHTML 	= '';
				$('divPawnerCode2').innerHTML 	= '';
				$('divLocationCode').innerHTML 	= '';
				//$('divBranchCode').innerHTML	= '';
				$('divAuctionDate').innerHTML 	= '';
				setGridData(grid,[]);
			}



			function clearFilled(){
		
				$('code').value		   = '';
				$('description').value = '';
				$('recordId').value    = '';
				$('version').value     = '';
				
				$('pawnerId2').value      = 0;
           		$('pawnerCode2').value    = '';
           		$('pawnerName2').value 	  = '';
           		
           		$('pawnerId').value       = 0;
           		$('pawnerCode').value     = '';
           		$('pawnerName').value 	  = '';
           		
           		$('locationId').value     = 0;
           		$('locationCode').value   = '';
           		$('locationName').value   = '';
				$('auctionDate').value   = '';
				
				//$('isActive').value   = '1';
				
				$('divCode').innerHTML 		  = '';
				$('divDescription').innerHTML = '';
				$('divProductCode').innerHTML = '';
				$('divBranchCode').innerHTML= '';
				$('divPawnerCode').innerHTML 	= '';
				$('divPawnerCode2').innerHTML 	= '';
				$('divLocationCode').innerHTML 	= '';
				$('divAuctionDate').innerHTML 	= '';
				//grid.setSelectedRows([]);
			}

			function clearOtherData(){
				
				$('code').value		   = '';
				$('description').value = '';				
				$('recordId').value    = '';
				$('version').value     = '';

				$('pawnerId2').value      = 0;
           		$('pawnerCode2').value    = '';
           		$('pawnerName2').value 	  = '';
           		
           		$('pawnerId').value       = 0;
           		$('pawnerCode').value     = '';
           		$('pawnerName').value 	  = '';
           		
           		$('locationId').value     = 0;
           		$('locationCode').value   = '';
           		$('locationName').value   = '';
           		$('auctionDate').value   = '';
			//	$('isActive').value   = '1';
				$('divCode').innerHTML 		  = '';
				$('divDescription').innerHTML = '';
				$('divProductCode').innerHTML = '';
				$('divBranchCode').innerHTML= '';
				$('divPawnerCode').innerHTML 	= '';
				$('divPawnerCode2').innerHTML 	= '';
				$('divLocationCode').innerHTML 	= '';
				$('divAuctionDate').innerHTML 	= '';
				setGridData(grid,[]);
			}

			function getCreateData(){
				var branchId 	= $('branchId').value;
				var branchCode  = $('branchCode').value;
				var productId   = $('productId').value;
				var productCode = $('productCode').value;				
				var code        = $('code').value;
				var description = $('description').value;
				var actioneeId	= $('pawnerId').value;
				var actioneeCode= $('pawnerCode').value;  
				var officeId	= $('pawnerId2').value;
				var officeCode	= $('pawnerCode2').value;
				var auctionDate	= $('auctionDate').value;  
				var locationId	= $('locationId').value;
				var locationCode= $('locationCode').value;
				/*var isCommon    = $('auctionglobe').value;*/ 
				
				/*if(isCommon){
					return 	"&branchId=0" + "&branchCode=" + branchCode +
							"&productId=" + productId + "&productCode=" + productCode + 
							"&code=" + code + "&description=" + description +
							"&actioneeId=" + actioneeId + "&actioneeCode=" + actioneeCode +
							"&officeId=" + officeId + "&officeCode=" + officeCode +
							"&auctionDate=" + auctionDate + "&locationId=" + locationId + 
							"&locationCode=" + locationCode + "&isCommon=1" ;
				}else{*/
					return 	"&branchId=" + branchId + "&branchCode=" + branchCode +
							"&productId=" + productId + "&productCode=" + productCode + 
							"&code=" + code + "&description=" + description +
							"&actioneeId=" + actioneeId + "&actioneeCode=" + actioneeCode +
							"&officeId=" + officeId + "&officeCode=" + officeCode +
							"&auctionDate=" + auctionDate + "&locationId=" + locationId + 
							"&locationCode=" + locationCode ;
					
				//}						
			}

			function getChangedData(){
				var recordId = $('recordId').value;
				var version  = $('version').value;
				var isActive = $('isActive').value;
				
				var str = getCreateData() + "&recordId=" + recordId + "&version=" + version+"&isActive="+isActive;
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
	               	else if( message[i]['branchCode'] )
	                    $('divBranchCode').innerHTML = message[i]['branchCode'];
	               else if( message[i]['pawnerCode'] )
	                    $('divPawnerCode').innerHTML = message[i]['pawnerCode'];
	               else if( message[i]['pawnerCode2'] )
	                    $('divPawnerCode2').innerHTML = message[i]['pawnerCode2'];
	                else if( message[i]['locationCode'] )
	                    $('divLocationCode').innerHTML = message[i]['locationCode'];
	                else if( message[i]['auctionDate'] )
	                    $('divAuctionDate').innerHTML = message[i]['auctionDate'];
	            }
	        }
		--></script>

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
			#fourthGrid .aw-column-0 {width: 150px;}
			#fourthGrid .aw-column-1 {width: 445px;}
			#fourthGrid .aw-column-2 {width: 100px;}
			#fourthGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#fourthGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
			
		
			#fifthGrid {height: 210px;width:600px;}
			#fifthGrid .aw-row-selector {text-align: center}
			#fifthGrid .aw-column-0 {width: 100px;}
			#fifthGrid .aw-column-1 {width: 455px;}
			#fifthGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#fifthGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}

			#sixthGrid {height: 210px;width:600px;}
			#sixthGrid .aw-row-selector {text-align: center}
			#sixthGrid .aw-column-0 {width: 100px;}
			#sixthGrid .aw-column-1 {width: 455px;}
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
									var myColumnsSercha = ["<bean:message bundle="lable" key="screen.code"/>","<bean:message bundle="lable" key="screen.description"/>"];
		            				var stra = new AW.Formats.String;
		            				var cellFormatSercha = [stra,stra];
	                    			var gridSerchArt = createBrowser(myColumnsSercha,'thirdGrid',cellFormatSercha);
	                    			gridSerchArt.setHeaderHeight(25);
	                                document.write(gridSerchArt);
	                                gridSerchArt.onRowDoubleClicked = function(event, row){
										try{
											//clearAll();
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
												//clearAll();
												clearBranchAll();
												$('branchId').value  		= this.getCellText(18,row);
												$('branchCode').value    	= this.getCellText(0,row);
												$('branchDescription').value= this.getCellText(1,row);
											}
											//getGridData();
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
									var myColumnsSerchp = ["<bean:message bundle="lable" key="screen.code"/>","<bean:message bundle="lable" key="screen.product"/>"];
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
				                        	//setGridData(grid,[]);				                        	
				                        	getGridData();
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

		<!-- Auction Location Browse -->
		<div id="location-serchDiv" class="x-hidden">
        	<div class="x-window-header">
            	Search Auction Location
            </div>
        	<div id="serch-tab4">
            	<div class="x-tab" title="Search">
					<table style="width: 600px">
						<tr>
							<td>
								<script>
									var myColumnsSerchL = ["<bean:message bundle="lable" key="screen.code"/>","<bean:message bundle="lable" key="screen.description"/>"];
		            				var strL = new AW.Formats.String;
		            				var cellFormatSerchL = [strL,strL];
	                    			var gridSerchLocation = createBrowser(myColumnsSerchL,'sixthGrid',cellFormatSerchL);
	                    			gridSerchLocation.setHeaderHeight(25);
	                                document.write(gridSerchLocation);
	                                gridSerchLocation.onRowDoubleClicked = function(event, row){
										try{    
											$('locationId').value     = this.getCellText(2,row);
											$('locationCode').value   = this.getCellText(0,row);
											$('locationName').value   = this.getCellText(1,row);											
				                        	winLocation.hide();					                        				                        					                        	
										}catch(error){}
									};
									gridSerchLocation.onSelectedRowsChanged=function(row){
										try{
											if(row!=''){
												$('locationId').value     = this.getCellText(2,row);
												$('locationCode').value   = this.getCellText(0,row);
												$('locationName').value   = this.getCellText(1,row);
												//clearOtherData();
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
    	<logic:equal name="auction" property="action" value="create">
			<html:form action="initiateAuctionService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
				

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
											onblur="upperCase('branchCode');commonSearch('initiateAuctionService.do','branchId','branchCode','branchDescription','getBranch','divBranchCode',function(){clearBranchAll();},' ',function(){clearAll();})"/>									
										<input id="ButtonBranchSerch" type="button" value="..." />
										<input type="text" size="60" id="branchDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divBranchCode" class="validate"/>
									</td>
									<!-- onblur="upperCase('branchCode');commonSearch('initiateAuctionService.do','branchId','branchCode','branchDescription','getBranch','divBranchCode',function(){clearOtherData();},function(){clearAll();})"/>  -->		
											
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
											onblur="upperCase('productCode');commonSearch('initiateAuctionService.do','productId','productCode','prdDescription','getProduct','divProductCode',function(){getGridData();},' ', function(){clearOtherData();})"/>											
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
			                    			var grid = createBrowser(myColumns,'fourthGrid',cellFormat);
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){
			                                	if(row!='') {
			                                		$('code').value           = grid.getCellText(0,row);
			                                		$('description').value    = grid.getCellText(1,row);
			                                		$('auctionDate').value    = grid.getCellText(10,row);
			                                		$('recordId').value       = grid.getCellText(11,row);
			                                		$('version').value        = grid.getCellText(12,row);
			                                		
			                                		$('pawnerId2').value      = grid.getCellText(15,row);
			                                		$('pawnerCode2').value    = grid.getCellText(16,row);
			                                		$('pawnerName2').value 	  = grid.getCellText(17,row);
			                                		
			                                		$('pawnerId').value       = grid.getCellText(18,row);
			                                		$('pawnerCode').value     = grid.getCellText(19,row);
			                                		$('pawnerName').value 	  = grid.getCellText(20,row);
			                                		
			                                		$('locationId').value     = grid.getCellText(21,row);
			                                		$('locationCode').value   = grid.getCellText(22,row);
			                                		$('locationName').value   = grid.getCellText(23,row);
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
										<html:text property="code" styleId="code" size="5" maxlength="3"
											onblur="upperCase('code')" onfocus="clearDivision('divCode')"/>
										<font color="red">*</font><br/>
										<div id="divCode" class="validate"/>
									</td>
								</tr>
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.description"/>&nbsp;
									</td>
									<td>
										<html:text property="description" styleId="description" size="70" maxlength="50"
											onfocus="clearDivision('divDescription')"/>
										<font color="red">*</font><br/>
										<div id="divDescription" class="validate"/>
									</td>
								</tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.auctioneename"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="pawnerId" name="pawnerId">										
										<input id="pawnerCode" name="pawnerCode" style="width: 70px" maxlength="8"  tabindex="1" class="READONLYINPUT" readonly="readonly"
											onfocus="clearDivision('divPawnerCode')"
											onblur="commonSearch('pawnerService.do','pawnerId','pawnerCode','pawnerName','getPawner','divPawnerCode')"/>
										<input id="ButtonPawnerSerch" type="button" value="..." name="AUN"/>
										<input id="pawnerName" name="pawnerName" size="70" class="READONLYINPUT" readonly="readonly"/>										
										<font color="red">*</font><br/>
										<div id="divPawnerCode" class="validate"/>
									</td>
								</tr>
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.officername"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="pawnerId2" name="pawnerId2">										
										<input id="pawnerCode2" name="pawnerCode2" style="width: 70px" maxlength="8"  tabindex="1" class="READONLYINPUT" readonly="readonly"
											onfocus="clearDivision('divPawnerCode2')"
											onblur="commonSearch('pawnerService.do','pawnerId2','pawnerCode2','pawnerName2','getPawner','divPawnerCode2')"/>
										<input id="ButtonPawnerSerch2" type="button" value="..." name="OFF"/>
										<input id="pawnerName2" name="pawnerName2" size="70" class="READONLYINPUT" readonly="readonly"/>										
										<font color="red">*</font><br/>
										<div id="divPawnerCode2" class="validate"/>
									</td>
								</tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.auctiondate"/>&nbsp;
									</td>								
									<td>
										<html:text property="auctionDate" size="15" maxlength="12" styleId="auctionDate" onkeypress="DateFormat(this,event);" onfocus="clearDivision('divAuctionDate')" onkeyup="DateFormat(this,event);" /><img src="images/none.gif" width="5px" ><input type="button" value="..." onclick="return showCalendar('auctionDate');" />
										<font color="red">*</font><br/>
										<div id="divAuctionDate" class="validate"/>									
									</td>
								</tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.auctionlocation"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="locationId" name="locationId">										
										<input id="locationCode" name="locationCode" style="width: 70px" maxlength="8"  tabindex="1" class="READONLYINPUT" readonly="readonly"
											onfocus="clearDivision('divLocationCode')"
											onblur="commonSearch('auctionLocationService.do','locationId','locationCode','locationName','getLocation','divLocationCode')"/>
										<input id="ButtonLocationSerch" type="button" value="..." />
										<input id="locationName" name="locationName" size="70" class="READONLYINPUT" readonly="readonly"/>										
										<font color="red">*</font><br/>
										<div id="divLocationCode" class="validate"/>
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
    	<logic:equal name="auction" property="action" value="update">
			<html:form action="initiateAuctionService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
				

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
											onblur="upperCase('branchCode');commonSearch('initiateAuctionService.do','branchId','branchCode','branchDescription','getBranch','divBranchCode',function(){clearBranchAll();},' ',function(){clearAll();})"/>									
										<input id="ButtonBranchSerch" type="button" value="..." />
										<input type="text" size="60" id="branchDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divBranchCode" class="validate"/>
									</td>
									<!-- onblur="upperCase('branchCode');commonSearch('initiateAuctionService.do','branchId','branchCode','branchDescription','getBranch','divBranchCode',function(){clearOtherData();},function(){clearAll();})"/>  -->		
											
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
											onblur="upperCase('productCode');commonSearch('initiateAuctionService.do','productId','productCode','prdDescription','getProduct','divProductCode',function(){getGridData();},' ', function(){clearOtherData();})"/>											
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
			                    			var grid = createBrowser(myColumns,'fourthGrid',cellFormat);
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){
			                                	if(row!='') {
			                                		$('code').value           = grid.getCellText(0,row);
			                                		$('description').value    = grid.getCellText(1,row);
			                                		
			                                		$('auctionDate').value    = grid.getCellText(10,row);
			                                		$('recordId').value       = grid.getCellText(11,row);
			                                		$('version').value        = grid.getCellText(12,row);
			                                		
			                                		$('pawnerId2').value      = grid.getCellText(15,row);
			                                		$('pawnerCode2').value    = grid.getCellText(16,row);
			                                		$('pawnerName2').value 	  = grid.getCellText(17,row);
			                                		
			                                		$('pawnerId').value       = grid.getCellText(18,row);
			                                		$('pawnerCode').value     = grid.getCellText(19,row);
			                                		$('pawnerName').value 	  = grid.getCellText(20,row);
			                                		
			                                		$('locationId').value     = grid.getCellText(21,row);
			                                		$('locationCode').value   = grid.getCellText(22,row);
			                                		$('locationName').value   = grid.getCellText(23,row);
			                                		$('isActive').value   	  = grid.getCellText(24,row);
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
										<input id="code" name="code" maxlength="3" size="5" class="READONLYINPUT" readonly="readonly"/>
									<!--  <html:text property="code" styleId="code" size="5" maxlength="3"
											onblur="upperCase('code')" onfocus="clearDivision('divCode')"/>-->	
										<font color="red">*</font><br/>
										<div id="divCode" class="validate"/>
									</td>
								</tr>
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.description"/>&nbsp;
									</td>
									<td>
										<html:text property="description" styleId="description" size="70" maxlength="50"
											onfocus="clearDivision('divDescription')"/>
										<font color="red">*</font><br/>
										<div id="divDescription" class="validate"/>
									</td>
								</tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.auctioneename"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="pawnerId" name="pawnerId">										
										<input id="pawnerCode" name="pawnerCode" style="width: 70px" maxlength="8"  tabindex="1" class="READONLYINPUT" readonly="readonly"
											onfocus="clearDivision('divPawnerCode')"
											onblur="commonSearch('pawnerService.do','pawnerId','pawnerCode','pawnerName','getPawner','divPawnerCode')"/>
										<input id="ButtonPawnerSerch" type="button" value="..." name="AUN"/>
										<input id="pawnerName" name="pawnerName" size="70" class="READONLYINPUT" readonly="readonly"/>										
										<font color="red">*</font><br/>
										<div id="divPawnerCode" class="validate"/>
									</td>
								</tr>
								
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.officername"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="pawnerId2" name="pawnerId2">										
										<input id="pawnerCode2" name="pawnerCode2" style="width: 70px" maxlength="8"  tabindex="1" class="READONLYINPUT" readonly="readonly"
											onfocus="clearDivision('divPawnerCode2')"
											onblur="commonSearch('pawnerService.do','pawnerId2','pawnerCode2','pawnerName2','getPawner','divPawnerCode2')"/>
										<input id="ButtonPawnerSerch2" type="button" value="..." name="OFF"/>
										<input id="pawnerName2" name="pawnerName2" size="70" class="READONLYINPUT" readonly="readonly"/>										
										<font color="red">*</font><br/>
										<div id="divPawnerCode2" class="validate"/>
									</td>
								</tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.auctiondate"/>&nbsp;
									</td>								
									<td>
										<html:text property="auctionDate" size="15" maxlength="12" styleId="auctionDate" onkeypress="DateFormat(this,event);" onfocus="clearDivision('divAuctionDate')" onkeyup="DateFormat(this,event);" /><img src="images/none.gif" width="5px" ><input type="button" value="..." onclick="return showCalendar('auctionDate');" />
										<font color="red">*</font><br/>
										<div id="divAuctionDate" class="validate"/>									
									</td>
								</tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.auctionlocation"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="locationId" name="locationId">										
										<input id="locationCode" name="locationCode" style="width: 70px" maxlength="8"  tabindex="1" class="READONLYINPUT" readonly="readonly"
											onfocus="clearDivision('divLocationCode')"
											onblur="commonSearch('auctionLocationService.do','locationId','locationCode','locationName','getLocation','divLocationCode')"/>
										<input id="ButtonLocationSerch" type="button" value="..." />
										<input id="locationName" name="locationName" size="70" class="READONLYINPUT" readonly="readonly"/>										
										<font color="red">*</font><br/>
										<div id="divLocationCode" class="validate"/>
									</td>
								</tr>
								<tr>
									<td width="20%" align="right">
										Active&nbsp;
									</td>
									<td>
										<select id="isActive" name="isActive" style="width: 70px">
											<option value="1">Active</option>
											<option value="2">InActive</option>
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
    	<logic:equal name="auction" property="action" value="authorize">
    		<br/>
			<html:form action="initiateAuctionService.do">
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
    	
    	<jsp:include flush="true" page="OptionClientBrowser.jsp"></jsp:include>	

    	<script>
			var winProduct;
			var winBranch;
			var winPawner;
			var winOfficer;
			var winLocation;
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
			                        	alert();
			                        	<logic:equal name="auction" property="action" value="authorize">
			                        		alert(1);
			                        		getAuthorizeGridData();
			                        	</logic:equal>
			                        	<logic:notEqual name="auction" property="action" value="authorize">
			                        		alert(2);
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
			    			    
			    var button4 = Ext.get('ButtonLocationSerch');
			    button4.on('click', function(){
			        if(!winLocation){
			            winLocation = new Ext.Window({
			                el:'location-serchDiv',
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
			                        winLocation.hide();
			                        if($('locationId').value!='0' && $('locationId').value!=''){
			                        	alert();
			                        	<logic:equal name="auction" property="action" value="authorize">
			                        		alert(1);
			                        		getAuthorizeGridData();
			                        	</logic:equal>
			                        	<logic:notEqual name="auction" property="action" value="authorize">
			                        		alert(2);
			                        		getGridData();
			                        	</logic:notEqual>
			                        }
			                   	}
			                }]
			            });
			        }
			        winLocation.show(this);
			        getSerchData("auctionLocationService.do",gridSerchLocation);
			    });
			    
			});
			
		</script>
  	</body>
</html:html>
