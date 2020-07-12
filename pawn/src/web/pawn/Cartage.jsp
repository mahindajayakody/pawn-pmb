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
			var ref = window.parent.frames['footer'];

			var url = 'cartageService.do';
			function loadToolBar(){
			    if(CurrentPage(ref.location.pathname)!='toolbar.jsp'){
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
							$('code').value        			= message[0];
							$('description').value 			= message[1];
							$('disbursePercentage').value 	= message[2];
							$('disburseValue').value 		= message[3];
							$('displayValue').value 		= message[4];
							$('marcketValue').value 		= message[5];
							$('isActive').value 			= message[6];
							$('version').value     			= message[11];
							$('recordId').value    			= message[12];
							$('productId').value 			= message[13];
							$('masterCartageId').value 		= message[14];
						}
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request');
				    }
				}
				mySearchRequest.update(data,'POST');
			}

			function getGridData(){
				data = "dispatch=getAjaxData&conditions=masterCartageId<next>productId&value=" + $('masterCartageId').value + "<next>" + $('productId').value;
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

			function calculate(value1,value2){
	        	return (parseFloat(value2) * (parseFloat(value1) / parseFloat(100)));
	        }

			function getSerchData(rurl,gridobj){
				data = "dispatch=getAjaxData";

				//if(rurl=="cartageMarsterService.do")
				//	data += "&conditions=productId&value=" + $('productId').value;
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
			
				data = "dispatch=getAuthorizeData&conditions=masterCartageId<next>productId&value=" + $('masterCartageId').value + "<next>" + $('productId').value;
				//data = "dispatch=getAuthorizeData";
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
				$('code').value		   			= '';
				$('description').value 			= '';
				$('disburseValue').value 		= '0.00';
				$('disbursePercentage').value 	= '0';
				$('displayValue').value 		= '0.00';
				$('marcketValue').value 		= '0.00';
				$('isActive').value 			= '';
				//$('productCode').value 			= '';
				//$('productId').value   			= '0';
				//$('prdDescription').value 		= '';
				//$('masterCartageId').value 		= '0';
				//$('cartageMasterCode').value   	= '';
				//$('cartageMasterDescription').value = '';
				$('recordId').value    			= '';
				$('version').value     			= '';
				//setGridData(grid,[]);

				$('divCode').innerHTML 		  		= '';
				$('divDescription').innerHTML 		= '';
				$('divProductCode').innerHTML 		= '';
				$('divCartageMasterCode').innerHTML	= '';
				$('divDisbursevalue').innerHTML		= '';
				$('divDisplayValue').innerHTML		= '';
				$('divDisbursepercentage').innerHTML= '';
				$('divMarcketValue').innerHTML		= '';
				$('divIsActive').innerHTML			= '';				
				
				if($('productCode').value!='' && $('cartageMasterCode').value!=''){
					radioValue = ref.getRadioBtnValue();
					if(radioValue==1 || radioValue==2 || radioValue==3){
						getGridData();
					}else if(radioValue==4){
						getAuthorizeGridData();
					}
				}
			}

			function clearFilled(){
				$('code').value		   = '';
				$('description').value = '';
				$('disburseValue').value 		= '0.00';
				$('disbursePercentage').value 	= '0';
				$('displayValue').value 		= '';
				$('marcketValue').value 		= '0.00';
				$('recordId').value    = '';
				$('version').value     = '';

				$('divCode').innerHTML 		  = '';
				$('divDescription').innerHTML = '';
				$('divProductCode').innerHTML = '';
				$('divCartageMasterCode').innerHTML= '';
				$('divDisbursevalue').innerHTML		= '';
				$('divDisplayValue').innerHTML		= '';
				$('divDisbursepercentage').innerHTML	= '';
				$('divMarcketValue').innerHTML		= '';
				$('divIsActive').innerHTML			= '';
				grid.setSelectedRows([]);
			}

			function clearOtherData(){
				$('code').value		   = '';
				$('description').value = '';
				$('disburseValue').value 		= '0.00';
				$('disbursePercentage').value 	= '0.00';
				$('displayValue').value 		= '';
				$('marcketValue').value 		= '0.00';
				$('masterCartageId').value = '0';
				$('cartageMasterCode').value   = '';
				$('cartageMasterDescription').value = '';
				$('recordId').value    = '';
				$('version').value     = '';

				$('divCode').innerHTML 		  = '';
				$('divDescription').innerHTML = '';
				$('divProductCode').innerHTML = '';
				$('divCartageMasterCode').innerHTML = '';
				$('divDisbursevalue').innerHTML		= '';
				$('divDisbursepercentage').innerHTML	= '';
				$('divDisplayValue').innerHTML		= '';
				$('divMarcketValue').innerHTML		= '';
				$('divIsActive').innerHTML			= '';
				setGridData(grid,[]);
			}

			function getCreateData(){
				var code        = $('code').value;
				var description = $('description').value;
				var productCode = $('productCode').value;
				var productId   = $('productId').value;
				var disburseValue 		= unformatNumber($('disburseValue').value)*1;
				var disbursePercentage 	= unformatNumber($('disbursePercentage').value)*1;
				var displayValue  		= unformatNumber($('displayValue').value);				
				var masterCartageId 	= $('masterCartageId').value;
				var cartageMasterCode   = $('cartageMasterCode').value;
				var marcketValue 		= unformatNumber($('marcketValue').value)*1;
				var isActive	 		= $('isActive').value;

				return "&code=" + code + "&description=" + description + "&disburseValue=" + disburseValue + "&disbursePercentage=" + disbursePercentage +
					   "&displayValue=" + displayValue + "&productCode=" + productCode + "&productId=" + productId +
					   "&marcketValue=" + marcketValue + "&masterCartageId=" + masterCartageId + "&cartageMasterCode=" + cartageMasterCode +
					   "&isActive=" + isActive;
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
	               	else if( message[i]['cartageMasterCode'] )
	                    $('divCartageMasterCode').innerHTML = message[i]['cartageMasterCode'];
	                else if( message[i]['disburseValue'] )
	                    $('divDisbursevalue').innerHTML = message[i]['disburseValue'];
	                else if( message[i]['disbursePercentage'] )
	                    $('divDisbursepercentage').innerHTML = message[i]['disbursePercentage'];
	                else if( message[i]['displayValue'] )
	                    $('divDisplayValue').innerHTML = message[i]['displayValue'];
	                else if( message[i]['marcketValue'] )
	                    $('divMarcketValue').innerHTML = message[i]['marcketValue'];
	                else if( message[i]['isActive'] )
	                    $('divIsActive').innerHTML = message[i]['isActive'];
	            }
	        }
		</script>

		<style>
			#firstGrid {height: 200px;width:720px;}
			#firstGrid .aw-row-selector {text-align: center}
			#firstGrid .aw-column-0 {width: 70px;}
			#firstGrid .aw-column-1 {width: 200px;}
			#firstGrid .aw-column-2 {width: 100px;text-align: right;}
			#firstGrid .aw-column-3 {width: 110px;text-align: right;}
			#firstGrid .aw-column-4 {width: 100px;text-align: right;}
			#firstGrid .aw-column-5 {width: 100px;text-align: right;}
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
			
			#authorizeGrid {height: 200px;width:720px;}
			#authorizeGrid .aw-row-selector {text-align: center}
			#authorizeGrid .aw-column-0 {width: 70px;}
			#authorizeGrid .aw-column-1 {width: 160px;}
			#authorizeGrid .aw-column-2 {width: 90px;text-align: right;}
			#authorizeGrid .aw-column-3 {width: 100px;text-align: right;}
			#authorizeGrid .aw-column-4 {width: 90px;text-align: right;}
			#authorizeGrid .aw-column-5 {width: 90px;text-align: right;}
			#authorizeGrid .aw-column-5 {width: 70px;}
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
											$('productId').value      = this.getCellText(3,row);
											$('productCode').value    = this.getCellText(0,row);
											$('prdDescription').value = this.getCellText(1,row);
											$('hidDiv').className='hideSearchPopup';
				                        	winProduct.hide();
				                        	setGridData(grid,[]);
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

		<div id="cartageMaster-serchDiv" class="x-hidden">
        	<div class="x-window-header">
            	Search Master Cartage
            </div>
        	<div id="serch-tab1">
            	<div class="x-tab" title="Search">
					<table style="width: 600px">
						<tr>
							<td>
								<script>
									var myColumnsSercha = ["<bean:message bundle="lable" key="screen.code"/>","<bean:message bundle="lable" key="screen.cartagemaster"/>"];
		            				var stra = new AW.Formats.String;
		            				var cellFormatSercha = [stra,stra];
	                    			var gridSerchArt = createBrowser(myColumnsSercha,'thirdGrid',cellFormatSercha);
	                    			gridSerchArt.setHeaderHeight(25);
	                                document.write(gridSerchArt);
	                                gridSerchArt.onRowDoubleClicked = function(event, row){
										try{
											$('masterCartageId').value  	= this.getCellText(2,row);
											$('cartageMasterCode').value    = this.getCellText(0,row);
											$('cartageMasterDescription').value  = this.getCellText(1,row);
											$('hidDiv').className='hideSearchPopup';
											winCartageMaster.hide();
				                        	<logic:equal name="cartage" property="action" value="authorize">
				                        		getAuthorizeGridData();
				                        	</logic:equal>
				                        	<logic:notEqual name="cartage" property="action" value="authorize">
				                        		getGridData();
				                        	</logic:notEqual>
										}catch(error){}
									};
									gridSerchArt.onSelectedRowsChanged=function(row){
										try{
											if(row!=''){
												$('masterCartageId').value  = this.getCellText(5,row);
												$('cartageMasterCode').value    = this.getCellText(0,row);
												$('cartageMasterDescription').value  = this.getCellText(1,row);
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
    	<logic:equal name="cartage" property="action" value="create">
			<html:form action="cartageService.do">
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
										<html:text property="productCode" styleId="productCode" size="5" maxlength="3" tabindex="1"
											onfocus="clearDivision('divProductCode')"
											onblur="upperCase('productCode');commonSearch('cartageService.do','productId','productCode','prdDescription','getProduct','divProductCode',function(){clearOtherData();},'',function(){clearAll();})"/>
										<input id="ButtonProductSerch" type="button" value="..." />
										<input type="text" size="60" id="prdDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divProductCode" class="validate"/>
									</td>
								</tr>

								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.cartagemaster"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="masterCartageId" name="masterCartageId" value="0"/>
										<html:text property="cartageMasterCode" styleId="cartageMasterCode" size="5" maxlength="3" tabindex="2"
											onfocus="clearDivision('divCartageMasterCode')"
											onblur="upperCase('cartageMasterCode');commonSearch('cartageService.do','masterCartageId','cartageMasterCode','cartageMasterDescription','getCartageMaster','divCartageMasterCode',function(){getGridData();},'&productId='+$('productId').value,function(){setGridData(grid,[]);})"/>
										<input id="ButtonArticleModSerch" type="button" value="..." />
										<input type="text" size="60" id="cartageMasterDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divCartageMasterCode" class="validate"/>
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
							  								 "<bean:message bundle="lable" key="screen.disbursevalue"/>",
							  								 "<bean:message bundle="lable" key="screen.disbursepercentage"/>",
							  								 "<bean:message bundle="lable" key="screen.displayvalue"/>",
							  								 "<bean:message bundle="lable" key="screen.marcketvalue"/>"];
				            				var str = new AW.Formats.String;
				            				var cellFormat = [str,str,str,str,str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){
			                                	if(row!='') {
			                                		$('code').value           = grid.getCellText(0,row);
			                                		$('description').value    = grid.getCellText(1,row);
			                                		$('disbursePercentage').value    = grid.getCellText(3,row);
			                                		$('disburseValue').value    = grid.getCellText(2,row);
			                                		$('displayValue').value    = grid.getCellText(4,row);
			                                		$('marcketValue').value    = grid.getCellText(5,row);
			                                		$('isActive').value    = grid.getCellText(6,row);
			                                		$('version').value        = grid.getCellText(11,row);
			                                		$('recordId').value       = grid.getCellText(12,row);
			                                		$('productId').value      = grid.getCellText(13,row);
			                                		$('masterCartageId').value = grid.getCellText(14,row);
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
										<html:text property="code" styleId="code" size="5" maxlength="3" tabindex="3"
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
										<html:text property="description" styleId="description" size="70" maxlength="50" tabindex="4"
											onfocus="clearDivision('divDescription')"/>
										<font color="red">*</font><br/>
										<div id="divDescription" class="validate"/>
									</td>
								</tr>
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.marcketvalue"/>&nbsp;
									</td>
									<td>
										<html:text property="marcketValue" styleId="marcketValue"  value="0.00" size="23" maxlength="15" style="text-align: right;" tabindex="5"
											onfocus="clearDivision('divMarcketValue');this.maxLength=15;"
											onkeyup="this.value=formatNumber(unformatNumber(this.value));"		 
											onblur="if(this.value.length=15){this.maxLength=18;};if(this.value.length<=1 && (this.value.substring(0,1)=='-' || this.value=='' || this.value=='.')){this.value='0.00'};this.value=formatNumber(parseFloat(unformatNumber(this.value)).toFixed(2))"/>
										<font color="red">*</font><br/>
										<div id="divMarcketValue" class="validate"/>
									</td>
								</tr>
								<tr height="5px"></tr>

								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.disbursepercentage"/>&nbsp;
									</td>
									<td>
										<html:text property="disbursePercentage" styleId="disbursePercentage" size="7" maxlength="5" tabindex="6"
											onblur="disburseValue.value=formatNumber(calculate(unformatNumber(this.value),unformatNumber(marcketValue.value)))"
											onfocus="clearDivision('divDisbursepercentage')"
											onkeyup="this.value=formatNumber(unformatNumber(this.value))"/>
										<font color="red">*</font><br/>
										<div id="divDisbursepercentage" class="validate"/>
									</td>
								</tr>
								<tr height="5px"></tr>

								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.disbursevalue"/>&nbsp;
									</td>
									<td>
										<html:text property="disburseValue" styleId="disburseValue" value="0.00" size="23" maxlength="15" style="text-align: right;" tabindex="7"
											onfocus="clearDivision('divDisbursevalue');this.maxLength=15;"
											onkeyup="this.value=formatNumber(unformatNumber(this.value));"		 
											onblur="if(this.value.length=15){this.maxLength=18;};if(this.value.length<=1 && (this.value.substring(0,1)=='-' || this.value=='' || this.value=='.')){this.value='0.00'};this.value=formatNumber(parseFloat(unformatNumber(this.value)).toFixed(2))"/>
										<font color="red">*</font><br/>
										<div id="divDisbursevalue" class="validate"/>
									</td>
								</tr>
								<tr height="5px"></tr>

								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.displayvalue"/>&nbsp;
									</td>
									<td>
										<html:text property="displayValue" styleId="displayValue" value="0.00" size="23" maxlength="15" style="text-align: right;" tabindex="8"
											onfocus="clearDivision('divDisplayValue');this.maxLength=15;"
											onkeyup="this.value=formatNumber(unformatNumber(this.value));"		 
											onblur="if(this.value.length=15){this.maxLength=18;};if(this.value.length<=1 && (this.value.substring(0,1)=='-' || this.value=='' || this.value=='.')){this.value='0.00'};this.value=formatNumber(parseFloat(unformatNumber(this.value)).toFixed(2))"/>
										<font color="red">*</font><br/>
										<div id="divDisplayValue" class="validate"/>
									</td>
								</tr>
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.isactive"/>&nbsp;
									</td>
									<td>
										<select id="isActive" name="isActive" style="width: 70px" tabindex="9">
											<option value="A">Active</option>
											<option value="I">InActive</option>
										</select>
										<div id="divIsActive" class="validate"/>
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
    	<logic:equal name="cartage" property="action" value="update">
			<html:form action="cartageService.do">
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
										<html:text property="productCode" styleId="productCode" size="5" maxlength="3" tabindex="1"
											onfocus="clearDivision('divProductCode')"
											onblur="upperCase('productCode');commonSearch('cartageService.do','productId','productCode','prdDescription','getProduct','divProductCode',function(){clearOtherData();},'',function(){clearAll();})"/>
										<input id="ButtonProductSerch" type="button" value="..." />
										<input type="text" size="60" id="prdDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divProductCode" class="validate"/>
									</td>
								</tr>

								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.cartagemaster"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="masterCartageId" name="masterCartageId" value="0"/>
										<html:text property="cartageMasterCode" styleId="cartageMasterCode" size="5" maxlength="3" tabindex="2"
											onfocus="clearDivision('divCartageMasterCode')"
											onblur="upperCase('cartageMasterCode');commonSearch('cartageService.do','masterCartageId','cartageMasterCode','cartageMasterDescription','getCartageMaster','divCartageMasterCode',function(){getGridData();},'&productId='+$('productId').value,function(){setGridData(grid,[]);})"/>
										<input id="ButtonArticleModSerch" type="button" value="..." />
										<input type="text" size="60" id="cartageMasterDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divCartageMasterCode" class="validate"/>
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
							  								 "<bean:message bundle="lable" key="screen.disbursevalue"/>",
							  								 "<bean:message bundle="lable" key="screen.disbursepercentage"/>",
							  								 "<bean:message bundle="lable" key="screen.displayvalue"/>",
							  								 "<bean:message bundle="lable" key="screen.marcketvalue"/>"];
				            				var str = new AW.Formats.String;
				            				var cellFormat = [str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){
			                                	if(row!='') {
			                                		$('code').value           = grid.getCellText(0,row);
			                                		$('description').value    = grid.getCellText(1,row);
			                                		$('disbursePercentage').value    = grid.getCellText(3,row);
			                                		$('disburseValue').value    = grid.getCellText(2,row);
			                                		$('displayValue').value    = grid.getCellText(4,row);
			                                		$('marcketValue').value    = grid.getCellText(5,row);
			                                		$('isActive').value    = grid.getCellText(6,row);
			                                		$('version').value        = grid.getCellText(11,row);
			                                		$('recordId').value       = grid.getCellText(12,row);
			                                		$('productId').value      = grid.getCellText(13,row);
			                                		$('masterCartageId').value = grid.getCellText(14,row);
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
										<html:text property="code" styleId="code" size="5" maxlength="3" tabindex="3"
											onblur="upperCase('code')" onfocus="clearDivision('divCode')" styleClass="READONLYINPUT" readonly="true"/>
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
										<html:text property="description" styleId="description" size="70" maxlength="50" tabindex="4"
											onfocus="clearDivision('divDescription')" />
										<font color="red">*</font><br/>
										<div id="divDescription" class="validate"/>
									</td>
								</tr>
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.marcketvalue"/>&nbsp;
									</td>
									<td>
										<html:text property="marcketValue" styleId="marcketValue"  value="0.00" size="23" maxlength="15" style="text-align: right;" tabindex="5"
											onfocus="clearDivision('divMarcketValue');this.maxLength=15;"
											onkeyup="this.value=formatNumber(unformatNumber(this.value));"		 
											onblur="if(this.value.length=15){this.maxLength=18;};if(this.value.length<=1 && (this.value.substring(0,1)=='-' || this.value=='' || this.value=='.')){this.value='0.00'};this.value=formatNumber(parseFloat(unformatNumber(this.value)).toFixed(2))"/>
										<font color="red">*</font><br/>
										<div id="divMarcketValue" class="validate"/>
									</td>
								</tr>
								<tr height="5px"></tr>

								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.disbursepercentage"/>&nbsp;
									</td>
									<td>
										<html:text property="disbursePercentage" styleId="disbursePercentage" size="7" maxlength="5" tabindex="6"
											onblur="disburseValue.value=formatNumber(calculate(unformatNumber(this.value),unformatNumber(marcketValue.value)))"
											onfocus="clearDivision('divDisbursepercentage')"
											onkeyup="this.value=formatNumber(unformatNumber(this.value))"/>
										<font color="red">*</font><br/>
										<div id="divDisbursepercentage" class="validate"/>
									</td>
								</tr>
								<tr height="5px"></tr>

								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.disbursevalue"/>&nbsp;
									</td>
									<td>
										<html:text property="disburseValue" styleId="disburseValue" value="0.00" size="23" maxlength="15" style="text-align: right;" tabindex="7"
											onfocus="clearDivision('divDisbursevalue');this.maxLength=15;"
											onkeyup="this.value=formatNumber(unformatNumber(this.value));"		 
											onblur="if(this.value.length=15){this.maxLength=18;};if(this.value.length<=1 && (this.value.substring(0,1)=='-' || this.value=='' || this.value=='.')){this.value='0.00'};this.value=formatNumber(parseFloat(unformatNumber(this.value)).toFixed(2))"/>
										<font color="red">*</font><br/>
										<div id="divDisbursevalue" class="validate"/>
									</td>
								</tr>
								<tr height="5px"></tr>

								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.displayvalue"/>&nbsp;
									</td>
									<td>
										<html:text property="displayValue" styleId="displayValue" value="0.00" size="23" maxlength="15" style="text-align: right;" tabindex="8"
											onfocus="clearDivision('divDisplayValue');this.maxLength=15;"
											onkeyup="this.value=formatNumber(unformatNumber(this.value));"		 
											onblur="if(this.value.length=15){this.maxLength=18;};if(this.value.length<=1 && (this.value.substring(0,1)=='-' || this.value=='' || this.value=='.')){this.value='0.00'};this.value=formatNumber(parseFloat(unformatNumber(this.value)).toFixed(2))"/>
										<font color="red">*</font><br/>
										<div id="divDisplayValue" class="validate"/>
									</td>
								</tr>
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.isactive"/>&nbsp;
									</td>
									<td>
										<select id="isActive" name="isActive" style="width: 70px" tabindex="9">
											<option value="A">Active</option>
											<option value="I">InActive</option>
										</select>
										<div id="divIsActive" class="validate"/>
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
    	<logic:equal name="cartage" property="action" value="delete">
			<html:form action="cartageService.do">
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
										<html:text property="productCode" styleId="productCode" size="5" maxlength="3" tabindex="1"
											onfocus="clearDivision('divProductCode')"
											onblur="upperCase('productCode');commonSearch('cartageService.do','productId','productCode','prdDescription','getProduct','divProductCode',function(){clearOtherData();},'',function(){clearAll();})"/>
										<input id="ButtonProductSerch" type="button" value="..." />
										<input type="text" size="60" id="prdDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divProductCode" class="validate"/>
									</td>
								</tr>

								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.cartagemaster"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="masterCartageId" name="masterCartageId" value="0"/>
										<html:text property="cartageMasterCode" styleId="cartageMasterCode" size="5" maxlength="3" tabindex="2"
											onfocus="clearDivision('divCartageMasterCode')"
											onblur="upperCase('cartageMasterCode');commonSearch('cartageService.do','masterCartageId','cartageMasterCode','cartageMasterDescription','getCartageMaster','divCartageMasterCode',function(){getGridData();},'&productId='+$('productId').value,function(){setGridData(grid,[]);})"/>
										<input id="ButtonArticleModSerch" type="button" value="..." />
										<input type="text" size="60" id="cartageMasterDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divCartageMasterCode" class="validate"/>
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
							  								 "<bean:message bundle="lable" key="screen.disbursevalue"/>",
							  								 "<bean:message bundle="lable" key="screen.disbursepercentage"/>",
							  								 "<bean:message bundle="lable" key="screen.displayvalue"/>",
							  								 "<bean:message bundle="lable" key="screen.marcketvalue"/>"];
				            				var str = new AW.Formats.String;
				            				var cellFormat = [str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){
			                                	if(row!='') {
			                                		$('code').value           = grid.getCellText(0,row);
			                                		$('description').value    = grid.getCellText(1,row);
			                                		$('disbursePercentage').value    = grid.getCellText(3,row);
			                                		$('disburseValue').value    = grid.getCellText(2,row);
			                                		$('displayValue').value    = grid.getCellText(4,row);
			                                		$('marcketValue').value    = grid.getCellText(5,row);
			                                		$('isActive').value    = grid.getCellText(6,row);
			                                		$('version').value        = grid.getCellText(11,row);
			                                		$('recordId').value       = grid.getCellText(12,row);
			                                		$('productId').value      = grid.getCellText(13,row);
			                                		$('masterCartageId').value = grid.getCellText(14,row);
			                                		
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
											onblur="upperCase('code')" onfocus="clearDivision('divCode')" styleClass="READONLYINPUT" readonly="true"/>
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
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.marcketvalue"/>&nbsp;
									</td>
									<td>
										<html:text property="marcketValue" styleId="marcketValue"  value="0.00" size="23" maxlength="15" style="text-align: right;" tabindex="10" styleClass="READONLYINPUT" readonly="true"
											onfocus="clearDivision('divMarcketValue');this.maxLength=15;"
											onkeyup="this.value=formatNumber(unformatNumber(this.value));"		 
											onblur="if(this.value.length=15){this.maxLength=18;};if(this.value.length<=1 && (this.value.substring(0,1)=='-' || this.value=='' || this.value=='.')){this.value='0.00'};this.value=formatNumber(parseFloat(unformatNumber(this.value)).toFixed(2))"/>
										<font color="red">*</font><br/>
										<div id="divMarcketValue" class="validate"/>
									</td>
								</tr>
								<tr height="5px"></tr>

								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.disbursepercentage"/>&nbsp;
									</td>
									<td>
										<html:text property="disbursePercentage" styleId="disbursePercentage" size="7" maxlength="5" styleClass="READONLYINPUT" readonly="true"
											onblur="disburseValue.value=formatNumber(calculate(unformatNumber(this.value),unformatNumber(marcketValue.value)))"
											onfocus="clearDivision('divDisbursepercentage')"
											onkeyup="this.value=formatNumber(unformatNumber(this.value))"/>
										<font color="red">*</font><br/>
										<div id="divDisbursepercentage" class="validate"/>
									</td>
								</tr>
								<tr height="5px"></tr>

								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.disbursevalue"/>&nbsp;
									</td>
									<td>
										<html:text property="disburseValue" styleId="disburseValue" value="0.00" size="23" maxlength="15" style="text-align: right;" tabindex="10" styleClass="READONLYINPUT" readonly="true"
											onfocus="clearDivision('divDisbursevalue');this.maxLength=15;"
											onkeyup="this.value=formatNumber(unformatNumber(this.value));"		 
											onblur="if(this.value.length=15){this.maxLength=18;};if(this.value.length<=1 && (this.value.substring(0,1)=='-' || this.value=='' || this.value=='.')){this.value='0.00'};this.value=formatNumber(parseFloat(unformatNumber(this.value)).toFixed(2))"/>
										<font color="red">*</font><br/>
										<div id="divDisbursevalue" class="validate"/>
									</td>
								</tr>
								<tr height="5px"></tr>

								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.displayvalue"/>&nbsp;
									</td>
									<td>
										<html:text property="displayValue" styleId="displayValue" value="0.00" size="23" maxlength="15" style="text-align: right;" tabindex="10" styleClass="READONLYINPUT" readonly="true"
											onfocus="clearDivision('divDisplayValue');this.maxLength=15;"
											onkeyup="this.value=formatNumber(unformatNumber(this.value));"		 
											onblur="if(this.value.length=15){this.maxLength=18;};if(this.value.length<=1 && (this.value.substring(0,1)=='-' || this.value=='' || this.value=='.')){this.value='0.00'};this.value=formatNumber(parseFloat(unformatNumber(this.value)).toFixed(2))"/>
										<font color="red">*</font><br/>
										<div id="divDisplayValue" class="validate"/>
									</td>
								</tr>
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.isactive"/>&nbsp;
									</td>
									<td>
										<select id="isActive" name="isActive" style="width: 70px" class="READONLYINPUT" disabled="true">
											<option value="A">Active</option>
											<option value="I">InActive</option>
										</select>
										<div id="divIsActive" class="validate"/>
									</td>
								</tr>
								<tr height="5px"></tr>
							</table>
						</td>
					</tr>
				</table>
			</html:form>
    	</logic:equal>


    	<!-- Authorize record -->
    	<logic:equal name="cartage" property="action" value="authorize">
			<html:form action="cartageService.do">
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
										<html:text property="productCode" styleId="productCode" size="5" maxlength="3" tabindex="1"
											onfocus="clearDivision('divProductCode')"
											onblur="upperCase('productCode');commonSearch('cartageService.do','productId','productCode','prdDescription','getProduct','divProductCode',function(){clearOtherData();},'',function(){clearAll();})"/>
										<input id="ButtonProductSerch" type="button" value="..." tabindex="-1"/>
										<input type="text" size="60" id="prdDescription" readonly="readonly" class="READONLYINPUT" tabindex="-1"/>
										<font color="red">*</font><br/>
										<div id="divProductCode" class="validate"/>
									</td>
								</tr>

								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.cartagemaster"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="masterCartageId" name="masterCartageId" value="0"/>
										<html:text property="cartageMasterCode" styleId="cartageMasterCode" size="5" maxlength="3" tabindex="2"
											onfocus="clearDivision('divCartageMasterCode')"
											onblur="upperCase('cartageMasterCode');commonSearch('cartageService.do','masterCartageId','cartageMasterCode','cartageMasterDescription','getCartageMaster','divCartageMasterCode',function(){getAuthorizeGridData();},'&productId='+$('productId').value,function(){setGridData(grid,[]);})"/>
										<input id="ButtonArticleModSerch" type="button" value="..." tabindex="-1"/>
										<input type="text" size="60" id="cartageMasterDescription" readonly="readonly" class="READONLYINPUT" tabindex="-1"/>
										<font color="red">*</font><br/>
										<div id="divCartageMasterCode" class="validate"/>
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
							  								 "<bean:message bundle="lable" key="screen.disbursevalue"/>",
							  								 "<bean:message bundle="lable" key="screen.disbursepercentage"/>",
							  								 "<bean:message bundle="lable" key="screen.displayvalue"/>",
							  								 "<bean:message bundle="lable" key="screen.marcketvalue"/>",
							  								 "<bean:message bundle="lable" key="screen.status"/>"];
				            				var str = new AW.Formats.String;
				            				var cellFormat = [str,str];
			                    			var grid = createBrowser(myColumns,'authorizeGrid',cellFormat);
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){
			                                	if(row!='') {
			                                	
			                                		$('code').value           		= grid.getCellText(0,row);
			                                		$('description').value    		= grid.getCellText(1,row);
			                                		$('disbursePercentage').value   = grid.getCellText(3,row);
			                                		$('disburseValue').value    	= grid.getCellText(2,row);
			                                		$('displayValue').value    		= grid.getCellText(4,row);
			                                		$('marcketValue').value    		= grid.getCellText(5,row);
			                                		$('authorizeMode').value   		= grid.getCellText(6,row);
			                                		$('isActive').value    			= grid.getCellText(7,row);
			                                		$('version').value        		= grid.getCellText(12,row);
			                                		$('recordId').value       		= grid.getCellText(13,row);
			                                		$('productId').value      		= grid.getCellText(14,row);
			                                		$('masterCartageId').value 		= grid.getCellText(15,row);			                                		
			                                	}			                                	
			                                };
			                               // getAuthorizeGridData();
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
											onblur="upperCase('code')" onfocus="clearDivision('divCode')" styleClass="READONLYINPUT" readonly="true" tabindex="-1"/>
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
										<html:text property="description" styleId="description" size="70" maxlength="50" styleClass="READONLYINPUT" readonly="true" tabindex="-1"
											onfocus="clearDivision('divDescription')" />
										<font color="red">*</font><br/>
										<div id="divDescription" class="validate"/>
									</td>
								</tr>
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.marcketvalue"/>&nbsp;
									</td>
									<td>
										<html:text property="marcketValue" styleId="marcketValue"  value="0.00" size="23" maxlength="15" style="text-align: right;" tabindex="-1" styleClass="READONLYINPUT" readonly="true"
											onfocus="clearDivision('divMarcketValue');this.maxLength=15;"
											onkeyup="this.value=formatNumber(unformatNumber(this.value));"		 
											onblur="if(this.value.length=15){this.maxLength=18;};if(this.value.length<=1 && (this.value.substring(0,1)=='-' || this.value=='' || this.value=='.')){this.value='0.00'};this.value=formatNumber(parseFloat(unformatNumber(this.value)).toFixed(2))"/>
										<font color="red">*</font><br/>
										<div id="divMarcketValue" class="validate"/>
									</td>
								</tr>
								<tr height="5px"></tr>

								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.disbursepercentage"/>&nbsp;
									</td>
									<td>
										<html:text property="disbursePercentage" styleId="disbursePercentage" size="7" maxlength="5" styleClass="READONLYINPUT" readonly="true" tabindex="-1"
											onblur="disburseValue.value=formatNumber(calculate(unformatNumber(this.value),unformatNumber(marcketValue.value)))"
											onfocus="clearDivision('divDisbursepercentage')"
											onkeyup="this.value=formatNumber(unformatNumber(this.value))"/>
										<font color="red">*</font><br/>
										<div id="divDisbursepercentage" class="validate"/>
									</td>
								</tr>
								<tr height="5px"></tr>

								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.disbursevalue"/>&nbsp;
									</td>
									<td>
										<html:text property="disburseValue" styleId="disburseValue" value="0.00" size="23" maxlength="15" style="text-align: right;" tabindex="-1" styleClass="READONLYINPUT" readonly="true"
											onfocus="clearDivision('divDisbursevalue');this.maxLength=15;"
											onkeyup="this.value=formatNumber(unformatNumber(this.value));"		 
											onblur="if(this.value.length=15){this.maxLength=18;};if(this.value.length<=1 && (this.value.substring(0,1)=='-' || this.value=='' || this.value=='.')){this.value='0.00'};this.value=formatNumber(parseFloat(unformatNumber(this.value)).toFixed(2))"/>
										<font color="red">*</font><br/>
										<div id="divDisbursevalue" class="validate"/>
									</td>
								</tr>
								<tr height="5px"></tr>

								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.displayvalue"/>&nbsp;
									</td>
									<td>
										<html:text property="displayValue" styleId="displayValue" value="0.00" size="23" maxlength="15" style="text-align: right;" tabindex="-1" styleClass="READONLYINPUT" readonly="true"
											onfocus="clearDivision('divDisplayValue');this.maxLength=15;"
											onkeyup="this.value=formatNumber(unformatNumber(this.value));"		 
											onblur="if(this.value.length=15){this.maxLength=18;};if(this.value.length<=1 && (this.value.substring(0,1)=='-' || this.value=='' || this.value=='.')){this.value='0.00'};this.value=formatNumber(parseFloat(unformatNumber(this.value)).toFixed(2))"/>
										<font color="red">*</font><br/>
										<div id="divDisplayValue" class="validate"/>
									</td>
								</tr>
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.isactive"/>&nbsp;
									</td>
									<td>
										<select id="isActive" name="isActive" style="width: 70px" class="READONLYINPUT" disabled="true">
											<option value="A">Active</option>
											<option value="I">InActive</option>
										</select>
										<div id="divIsActive" class="validate"/>
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
			var winCartageMaster;
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

			    var button1 = Ext.get('ButtonArticleModSerch');
			    button1.on('click', function(){
			        if(!winCartageMaster){
			            winCartageMaster = new Ext.Window({
			                el:'cartageMaster-serchDiv',
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
			                        winCartageMaster.hide();
			                        if($('masterCartageId').value!='0' && $('masterCartageId').value!=''){
			                        	<logic:equal name="cartage" property="action" value="authorize">
			                        		getAuthorizeGridData();
			                        	</logic:equal>
			                        	<logic:notEqual name="cartage" property="action" value="authorize">
			                        		getGridData();
			                        	</logic:notEqual>
			                        }
			                   	}
			                }]
			            });
			        }
			        winCartageMaster.show(this);
			        getSerchData("cartageMarsterService.do",gridSerchArt);
			        //$('hidDiv').className='showSearchPopup';
			    });
			});
		</script>
  	</body>
</html:html>
