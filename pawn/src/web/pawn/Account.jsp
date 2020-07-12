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

			var url = 'accountService.do';
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
							$('code').value 		= message[0];
							$('description').value 	= message[1];
							$('accountCode').value  = message[2];
							$('drcr').value 		= message[4];
							$('recordId').value     = message[3];
							$('version').value      = message[5];
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

			function getSerchData(rurl,gridobj){
				data = "dispatch=getAjaxData";

				if(rurl!="productService.do")
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
				$('code').value				= '';
				$('description').value		= '';
				$('accountCode').value		= '';
				$('drCr').value 			= '';
				$('productCode').value 		= '';
				$('productId').value   		= '0';
				$('prdDescription').value 	= '';
				$('recordId').value    		= '';
				$('version').value     		= '';

				$('divCode').innerHTML 		  = '';
				$('divDescription').innerHTML = '';
				$('divAcc').innerHTML 		  = '';
				$('divDrCr').innerHTML 		  = '';
				$('divProductCode').innerHTML = '';
				setGridData(grid,[]);
			}

			function clearFilled(){
				$('code').value			= '';
				$('description').value	= '';
				$('accountCode').value	= '';
				$('drCr').value 		= '';
				$('recordId').value     = '';
				$('version').value      = '';

				$('divCode').innerHTML 		  = '';
				$('divDescription').innerHTML = '';
				$('divProductCode').innerHTML = '';
				$('divAcc').innerHTML 		  = '';
				$('divDrCr').innerHTML 		  = '';
				grid.setSelectedRows([]);
			}

			function clearOtherData(){
				$('code').value			= '';
				$('description').value	= '';
				$('accountCode').value	= '';
				$('drCr').value 		= '';
				$('recordId').value     = '';
				$('version').value      = '';

				$('divCode').innerHTML 		  = '';
				$('divDescription').innerHTML = '';
				//$('divProductCode').innerHTML = '';
				$('divAcc').innerHTML 		  = '';
				$('divDrCr').innerHTML 		  = '';
				setGridData(grid,[]);
			}

			function getCreateData(){
				var acc         = $('accountCode').value;
				var drCr 		= $('drCr').value;
				var productCode = $('productCode').value;
				var productId   = $('productId').value;
				var code		= $('code').value;
				var description = $('description').value;

				return "&accountCode=" + acc + "&drCr=" + drCr + "&code=" + code + "&description=" + description +
					   "&productCode=" + productCode + "&productId=" + productId;
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
	                if( message[i]['accountCode'] )
	                    $('divAcc').innerHTML = message[i]['accountCode'];
	                else if( message[i]['drCr'] )
	                    $('divDrCr').innerHTML = message[i]['drCr'];
	               	else if( message[i]['productCode'] )
	                    $('divProductCode').innerHTML = message[i]['productCode'];
	            }
	        }
		</script>

		<style>
			#firstGrid {height: 310px;width:720px;}
			#firstGrid .aw-row-selector {text-align: center}
			#firstGrid .aw-column-0 {width: 100px;}
			#firstGrid .aw-column-1 {width: 320px;}
			#firstGrid .aw-column-2 {width: 120px;}
			#firstGrid .aw-column-3 {width: 120px;}
			#firstGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#firstGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}

			#secondGrid {height: 210px;width:600px;}
			#secondGrid .aw-row-selector {text-align: center}
			#secondGrid .aw-column-0 {width: 100px;}
			#secondGrid .aw-column-1 {width: 455px;}
			#secondGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#secondGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}

			#authorizeGrid {height: 310px;width:720px;}
			#authorizeGrid .aw-row-selector {text-align: center}
			#authorizeGrid .aw-column-0 {width: 100px;}
			#authorizeGrid .aw-column-1 {width: 320px;}
			#authorizeGrid .aw-column-2 {width: 120px;}
			#authorizeGrid .aw-column-3 {width: 120px;}
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
				                        	clearOtherData();
				                        	<logic:equal name="account" property="action" value="authorize">
				                        		getAuthorizeGridData();
				                        	</logic:equal>
				                        	<logic:notEqual name="account" property="action" value="authorize">
				                        		getGridData();
				                        	</logic:notEqual>
										}catch(error){}
									};
									gridSerchPrd.onSelectedRowsChanged=function(row){
										try{
											if(row!=''){
												$('productCode').value    = this.getCellText(0,row);
												$('prdDescription').value = this.getCellText(1,row);
												$('productId').value      = this.getCellText(3,row);
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
		<div id="hidDiv" class="hideSearchPopup">
		</div>

    	<!-- Create record -->
    	<logic:equal name="account" property="action" value="create">
			<html:form action="accountService.do">
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
											onblur="upperCase('productCode');commonSearch('accountService.do','productId','productCode','prdDescription','getProduct','divProductCode',function(){getGridData();},'',function(){clearOtherData();})"/>
										<input id="ButtonProductSerch" type="button" value="..." tabindex="-1"/>
										<input type="text" size="60" id="prdDescription" readonly="readonly" class="READONLYINPUT" tabindex="-1"/>
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
															 "<bean:message bundle="lable" key="screen.account"/>",
							  								 "<bean:message bundle="lable" key="screen.drcr"/>"];
				            				var str = new AW.Formats.String;
				            				var cellFormat = [str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){
			                                	if(row!='') {
			                                		$('code').value  		= grid.getCellText(0,row);
			                                		$('description').value	= grid.getCellText(1,row);
			                                		$('accountCode').value  = grid.getCellText(2,row);
			                                		$('drCr').value			= grid.getCellText(3,row);
			                                		$('recordId').value     = grid.getCellText(8,row);
			                                		$('version').value      = grid.getCellText(9,row);
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
										<html:text property="code" styleId="code" size="5" maxlength="3" tabindex="2"
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
										<html:text property="description" styleId="description" size="70" maxlength="50" tabindex="3"
											onfocus="clearDivision('divDescription')"/>
										<font color="red">*</font><br/>
										<div id="divDescription" class="validate"/>
									</td>
								</tr>
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.account"/>&nbsp;
									</td>
									<td>
										<html:text property="accountCode" styleId="accountCode" size="15" maxlength="10" tabindex="4"
											onblur="upperCase('code')" onfocus="clearDivision('divAcc')"/>
										<font color="red">*</font><br/>
										<div id="divAcc" class="validate"/>
									</td>
								</tr>

								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.drcr"/>&nbsp;
									</td>
									<td>
										<select id="drCr" name="drCr" style="width: 50px" tabindex="5">
											<option value="DR">DR</option>
											<option value="CR">CR</option>
										</select>
										<font color="red">*</font><br/>
										<div id="divDrCr" class="validate"/>
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
    	<logic:equal name="account" property="action" value="update">
			<html:form action="accountService.do">
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
											onblur="upperCase('productCode');commonSearch('accountService.do','productId','productCode','prdDescription','getProduct','divProductCode',function(){getGridData();},'',function(){clearOtherData();})"/>
										<input id="ButtonProductSerch" type="button" value="..." tabindex="-1"/>
										<input type="text" size="60" id="prdDescription" readonly="readonly" class="READONLYINPUT" tabindex="-1"/>
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
															 "<bean:message bundle="lable" key="screen.account"/>",
							  								 "<bean:message bundle="lable" key="screen.drcr"/>"];
				            				var str = new AW.Formats.String;
				            				var cellFormat = [str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){
			                                	if(row!='') {
			                                		$('code').value  		= grid.getCellText(0,row);
			                                		$('description').value	= grid.getCellText(1,row);
			                                		$('accountCode').value  = grid.getCellText(2,row);
			                                		$('drCr').value			= grid.getCellText(3,row);
			                                		$('recordId').value     = grid.getCellText(8,row);
			                                		$('version').value      = grid.getCellText(9,row);
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
										<bean:message bundle="lable" key="screen.code" />&nbsp;
									</td>
									<td>
										<html:text property="code" styleId="code" size="5" maxlength="3" styleClass="READONLYINPUT" readonly="true" tabindex="-1"
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
										<html:text property="description" styleId="description" size="70" maxlength="50" tabindex="2"
											onfocus="clearDivision('divDescription')"/>
										<font color="red">*</font><br/>
										<div id="divDescription" class="validate"/>
									</td>
								</tr>
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.account"/>&nbsp;
									</td>
									<td>
										<html:text property="accountCode" styleId="accountCode" size="15" maxlength="10" tabindex="3"
											onblur="upperCase('code')" onfocus="clearDivision('divAcc')"/>
											<font color="red">*</font><br/>
											<div id="divAcc" class="validate"/>
									</td>
								</tr>

								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.drcr"/>&nbsp;
									</td>
									<td>
										<select id="drCr" name="drCr" style="width: 50px" tabindex="4">
											<option value="DR">DR</option>
											<option value="CR">CR</option>
										</select>
										<font color="red">*</font><br/>
										<div id="divDrCr" class="validate"/>
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
    	<logic:equal name="account" property="action" value="authorize">
			<html:form action="accountService.do">
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
											onblur="upperCase('productCode');commonSearch('accountService.do','productId','productCode','prdDescription','getProduct','divProductCode',function(){getAuthorizeGridData();},'',function(){clearOtherData();})"/>
										<input id="ButtonProductSerch" type="button" value="..." tabindex="-1"/>
										<input type="text" size="60" id="prdDescription" readonly="readonly" class="READONLYINPUT" tabindex="-1"/>
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
															 "<bean:message bundle="lable" key="screen.account"/>",
															 "<bean:message bundle="lable" key="screen.status"/>"]
				            				var str = new AW.Formats.String;
				            				var cellFormat = [str,str];
			                    			var grid = createBrowser(myColumns,'authorizeGrid',cellFormat);
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){
			                                	if(row!='') {
			                                		$('code').value  		= grid.getCellText(0,row);
			                                		$('description').value	= grid.getCellText(1,row);
			                                		$('accountCode').value  = grid.getCellText(2,row);
			                                		$('authorizeMode').value= grid.getCellText(3,row);
			                                		$('drCr').value			= grid.getCellText(4,row);
			                                		$('recordId').value     = grid.getCellText(5,row);
			                                		$('version').value      = grid.getCellText(6,row);
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
										<html:text property="code" styleId="code" size="5" maxlength="3" styleClass="READONLYINPUT" readonly="true" tabindex="-1"
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
										<html:text property="description" styleId="description" size="70" maxlength="50" styleClass="READONLYINPUT" readonly="true" tabindex="-1"
											onfocus="clearDivision('divDescription')"/>
										<font color="red">*</font><br/>
										<div id="divDescription" class="validate"/>
									</td>
								</tr>
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.account"/>&nbsp;
									</td>
									<td>
										<html:text property="accountCode" styleId="accountCode" size="15" maxlength="10" styleClass="READONLYINPUT" readonly="true" tabindex="-1"
											onblur="upperCase('code')" onfocus="clearDivision('divAcc')"/>
										<font color="red">*</font><br/>
										<div id="divAcc" class="validate"/>
									</td>
								</tr>

								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.drcr"/>&nbsp;
									</td>
									<td>
										<select id="drCr" name="drCr" style="width: 50px" class="READONLYINPUT" disabled="disabled" tabindex="-1">
											<option value="DR">DR</option>
											<option value="CR">CR</option>
										</select>
										<font color="red">*</font><br/>
										<div id="divDrCr" class="validate"/>
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
			                        if($('productId').value>0){
				                        <logic:equal name="account" property="action" value="authorize">
			                        		getAuthorizeGridData();
			                        	</logic:equal>
			                        	<logic:notEqual name="account" property="action" value="authorize">
			                        		getGridData();
			                        	</logic:notEqual>
			                        }
			                   	}
			                }]
			            });
			        }
			        winProduct.show(this);
			        getSerchData("productService.do",gridSerchPrd);
			        //$('hidDiv').className='showSearchPopup';
			    });
			});
		</script>
  	</body>
</html:html>
