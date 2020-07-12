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
		
			function $(obj){ return document.getElementById(obj); }
			function formatNumber(num,prefix){prefix = prefix || '';   num += '';   var splitStr = num.split('.');   var splitLeft = splitStr[0];   var splitRight = splitStr.length > 1 ? '.' + splitStr[1] : '';   var regx = /(\d+)(\d{3})/;   while (regx.test(splitLeft)) {      splitLeft = splitLeft.replace(regx, '$1' + ',' + '$2');   }   return prefix + splitLeft + splitRight;}function unformatNumber(num) {  return num.replace(/([^0-9\.\-])/g,'');}
			var action = 1;

			var url = 'reminderParaService.do';
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

			function getSerchData(rurl,gridobj){
				data = "dispatch=getAjaxData";

				if(rurl=="schemeService.do")
					data += "&conditions=productId<next>isActive&value=" + $('productId').value + "<next>1";

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
				data = "dispatch=getAuthorizeData&conditions=productId<next>schemeId&value=" + $('productId').value + "<next>" + $('schemeId').value;
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
				$('noOfDays').value    = '';
				$('recordId').value    = '';
				$('version').value     = '';

				$('divCode').innerHTML 		  = '';
				$('divDescription').innerHTML = '';
				$('divProductCode').innerHTML = '';
				$('divSchemeCode').innerHTML= '';
				$('divIsNominee').innerHTML= '';
				$('divNoOfDays').innerHTML= '';
				setGridData(grid,[]);
			}

			function clearFilled(){
				$('code').value		   = '';
				$('description').value = '';
				$('recordId').value    = '';
				$('version').value     = '';

				$('divCode').innerHTML 		  = '';
				$('divDescription').innerHTML = '';
				$('divProductCode').innerHTML = '';
				$('divSchemeCode').innerHTML= '';
				grid.setSelectedRows([]);
			}

			function clearOtherData(){
				$('code').value		   = '';
				$('description').value = '';
				$('schemeId').value = '0';
				$('schemeCode').value   = '';
				$('schemeDescription').value = '';
				$('recordId').value    = '';
				$('version').value     = '';

				$('divCode').innerHTML 		  = '';
				$('divDescription').innerHTML = '';
				$('divProductCode').innerHTML = '';
				$('divSchemeCode').innerHTML= '';
				setGridData(grid,[]);
			}

			function getCreateData(){
				var code        = $('code').value;
				var description = $('description').value;
				var productCode = $('productCode').value;
				var productId   = $('productId').value;
				//var schemeId 	= $('schemeId').value;
				//var schemeCode  = $('schemeCode').value;
				var isNominee 	= $('isNominee').value;
				var noOfDays	= $('noOfDays').value;
				return "&code=" + code + "&description=" + description + "&productCode=" + productCode + "&productId=" + productId +
					   "&isNominee=" + isNominee + "&noOfDays=" + noOfDays;
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
	               	else if( message[i]['schemeCode'] )
	                    $('divSchemeCode').innerHTML = message[i]['schemeCode'];
	            }
	        }
		</script>

		<style>
			#firstGrid {height: 250px;width:720px;}
			#firstGrid .aw-row-selector {text-align: center}
			#firstGrid .aw-column-0 {width: 150px;}
			#firstGrid .aw-column-1 {width: 250px;}
			#firstGrid .aw-column-2 {width: 75px;}
			#firstGrid .aw-column-3 {width: 75px;}
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
				                        	getGridData();
				                        	/* clearOtherData(); */
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

		<div id="scheme-serchDiv" class="x-hidden">
        	<div class="x-window-header">
            	Search Scheme
            </div>
        	<div id="serch-tab1">
            	<div class="x-tab" title="Search">
					<table style="width: 600px">
						<tr>
							<td>
								<script>
									var myColumnsSercha = ["<bean:message bundle="lable" key="screen.code"/>","<bean:message bundle="lable" key="screen.scheme"/>"];
		            				var stra = new AW.Formats.String;
		            				var cellFormatSercha = [stra,stra];
	                    			var gridSerchArt = createBrowser(myColumnsSercha,'thirdGrid',cellFormatSercha);
	                    			gridSerchArt.setHeaderHeight(25);
	                                document.write(gridSerchArt);
	                                gridSerchArt.onRowDoubleClicked = function(event, row){
										try{
											$('schemeId').value  = this.getCellText(9,row);
											$('schemeCode').value    = this.getCellText(0,row);
											$('schemeDescription').value  = this.getCellText(1,row);
											$('hidDiv').className='hideSearchPopup';
				                        	winScheme.hide();
				                        	<logic:equal name="reminderParameter" property="action" value="authorize">
				                        		getAuthorizeGridData();
				                        	</logic:equal>
				                        	<logic:notEqual name="reminderParameter" property="action" value="authorize">
				                        		getGridData();
				                        	</logic:notEqual>
										}catch(error){}
									};
									gridSerchArt.onSelectedRowsChanged=function(row){
										try{
											if(row!=''){
												$('schemeId').value  = this.getCellText(9,row);
												$('schemeCode').value    = this.getCellText(0,row);
												$('schemeDescription').value  = this.getCellText(1,row);
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
		<div id="hidDiv" class="hideSearchPopup">
		</div>

    	<!-- Create record -->
    	<logic:equal name="reminderParameter" property="action" value="create">
			<html:form action="reminderParaService.do">
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
											onblur="upperCase('productCode');commonSearch('productService.do','productId','productCode','prdDescription','getProduct','divProductCode',function(){clearOtherData();},'',function(){clearAll();})"/>
										<input id="ButtonProductSerch" type="button" value="..." />
										<input type="text" size="60" id="prdDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divProductCode" class="validate"/>
									</td>
								</tr>

								<%-- <tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.scheme"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="schemeId" name="schemeId" value="0"/>
										<html:text property="schemeCode" styleId="schemeCode" size="5" maxlength="3"
											onfocus="clearDivision('divSchemeCode')"
											onblur="upperCase('schemeCode');commonSearch('schemeService.do','schemeId','schemeCode','schemeDescription','getScheme','divSchemeCode',function(){getGridData();},'&productId='+$('productId').value,function(){setGridData(grid,[]);})"/>
										<input id="ButtonSchemeSerch" type="button" value="..." />
										<input type="text" size="60" id="schemeDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divSchemeCode" class="validate"/>
									</td>
								</tr> --%>
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
							  								 "<bean:message bundle="lable" key="screen.issendnominee"/>",
							  								 "<bean:message bundle="lable" key="screen.noofdays"/>"];
				            				var str = new AW.Formats.String;
				            				var cellFormat = [str,str,str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){
			                                	if(row!='') {
			                                		/*$('code').value          = grid.getCellText(0,row);
			                                		$('description').value   = grid.getCellText(1,row);
			                                		$('isNominee').value     = grid.getCellText(2,row);
			                                		$('noOfDays').value      = grid.getCellText(3,row);
			                                		$('recordId').value      = grid.getCellText(6,row);
			                                		$('version').value       = grid.getCellText(7,row);*/
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
							<table class="InputTable">
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.code"/>&nbsp;
									</td>
									<td>
										<html:text property="code" styleId="code" size="5" maxlength="4"
											onblur="upperCase('code')" onfocus="clearDivision('divCode')" tabindex="0"/>
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
										<html:text property="description" styleId="description" size="70" maxlength="50"
											onfocus="clearDivision('divDescription')" tabindex="1"/>
										<font color="red">*</font><br/>
										<div id="divDescription" class="validate"/>
									</td>
								</tr>
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.issendnominee"/>&nbsp;
									</td>
									<td>
										<select id="isNominee" name="isNominee" style="width: 50px" tabindex="2">
											<option value="Y">Yes</option>
											<option value="N">No</option>
										</select>
										<font color="red">*</font><br/>
										<div id="divIsNominee" class="validate"/>
									</td>
								</tr>
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.noofdays"/>&nbsp;
									</td>
									<td>
										<html:text property="noOfDays" styleId="noOfDays" size="5" maxlength="2"
											onfocus="clearDivision('divNoOfDays')" tabindex="3"/>
										<font color="red">*</font><br/>
										<div id="divNoOfDays" class="validate"/>
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
    	<logic:equal name="reminderParameter" property="action" value="update">
			<html:form action="reminderParaService.do">
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
											onblur="upperCase('productCode');commonSearch('productService.do','productId','productCode','prdDescription','getProduct','divProductCode',function(){clearOtherData();},'',function(){clearAll();})"/>
										<input id="ButtonProductSerch" type="button" value="..." />
										<input type="text" size="60" id="prdDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divProductCode" class="validate"/>
									</td>
								</tr>

								<tr height="5px"></tr>
								<%-- <tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.scheme"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="schemeId" name="schemeId" value="0"/>
										<html:text property="schemeCode" styleId="schemeCode" size="5" maxlength="3"
											onfocus="clearDivision('divSchemeCode')"
											onblur="upperCase('schemeCode');commonSearch('schemeService.do','schemeId','schemeCode','schemeDescription','getScheme','divSchemeCode',function(){getGridData();},'&productId='+$('productId').value,function(){setGridData(grid,[]);})"/>
										<input id="ButtonSchemeSerch" type="button" value="..." />
										<input type="text" size="60" id="schemeDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divSchemeCode" class="validate"/>
									</td>
								</tr> --%>
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
							  								 "<bean:message bundle="lable" key="screen.issendnominee"/>",
							  								 "<bean:message bundle="lable" key="screen.noofdays"/>"];
				            				var str = new AW.Formats.String;
				            				var cellFormat = [str,str,str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){
			                                	if(row!='') {
			                                		$('code').value          = grid.getCellText(0,row);
			                                		$('description').value   = grid.getCellText(1,row);
			                                		$('isNominee').value     = grid.getCellText(2,row);
			                                		$('noOfDays').value      = grid.getCellText(3,row);
			                                		$('recordId').value      = grid.getCellText(11,row);
			                                		$('version').value       = grid.getCellText(10,row);
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
										<html:text property="code" styleId="code" size="5" maxlength="4" styleClass="READONLYINPUT" readonly="true"
											onblur="upperCase('code')" onfocus="clearDivision('divCode')" />
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
										<html:text property="description" styleId="description" size="70" maxlength="50"
											onfocus="clearDivision('divDescription')" tabindex="0"/>
										<font color="red">*</font><br/>
										<div id="divDescription" class="validate"/>
									</td>
								</tr>
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.issendnominee"/>&nbsp;
									</td>
									<td>
										<select id="isNominee" name="isNominee" style="width: 50px" tabindex="1" >
											<option value="Y">Yes</option>
											<option value="N">No</option>
										</select>
										<font color="red">*</font><br/>
										<div id="divIsNominee" class="validate"/>
									</td>
								</tr>
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.noofdays"/>&nbsp;
									</td>
									<td>
										<html:text property="noOfDays" styleId="noOfDays" size="5" maxlength="2"
											onfocus="clearDivision('divNoOfDays')" tabindex="2" />
										<font color="red">*</font><br/>
										<div id="divNoOfDays" class="validate"/>
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
    	<logic:equal name="reminderParameter" property="action" value="delete">
			<html:form action="reminderParaService.do">
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
											onblur="upperCase('productCode');commonSearch('productService.do','productId','productCode','prdDescription','getProduct','divProductCode',function(){clearOtherData();},'',function(){clearAll();})"/>
										<input id="ButtonProductSerch" type="button" value="..." />
										<input type="text" size="60" id="prdDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divProductCode" class="validate"/>
									</td>
								</tr>

								<tr height="5px"></tr>
								<%-- <tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.scheme"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="schemeId" name="schemeId" value="0"/>
										<html:text property="schemeCode" styleId="schemeCode" size="5" maxlength="3"
											onfocus="clearDivision('divSchemeCode')"
											onblur="upperCase('schemeCode');commonSearch('schemeService.do','schemeId','schemeCode','schemeDescription','getScheme','divSchemeCode',function(){getGridData();},'&productId='+$('productId').value,function(){setGridData(grid,[]);})"/>
										<input id="ButtonSchemeSerch" type="button" value="..." />
										<input type="text" size="60" id="schemeDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divSchemeCode" class="validate"/>
									</td>
								</tr> --%>
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
							  								 "<bean:message bundle="lable" key="screen.issendnominee"/>",
							  								 "<bean:message bundle="lable" key="screen.noofdays"/>"];
				            				var str = new AW.Formats.String;
				            				var cellFormat = [str,str,str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){
			                                	if(row!='') {
			                                		$('code').value          = grid.getCellText(0,row);
			                                		$('description').value   = grid.getCellText(1,row);
			                                		$('isNominee').value     = grid.getCellText(2,row);
			                                		$('noOfDays').value      = grid.getCellText(3,row);
			                                		$('recordId').value      = grid.getCellText(11,row);
			                                		$('version').value       = grid.getCellText(10,row);
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
										<html:text property="code" styleId="code" size="5" maxlength="4" styleClass="READONLYINPUT" readonly="true"
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
										<div id="divDescription" class="validate"/>
									</td>
								</tr>
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.issendnominee"/>&nbsp;
									</td>
									<td>
										<select id="isNominee" name="isNominee" style="width: 50px" disabled="disabled" >
											<option value="Y">Yes</option>
											<option value="N">No</option>
										</select>
										<div id="divIsNominee" class="validate"/>
									</td>
								</tr>
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.noofdays"/>&nbsp;
									</td>
									<td>
										<html:text property="noOfDays" styleId="noOfDays" size="5" maxlength="2"
											onfocus="clearDivision('divNoOfDays')" styleClass="READONLYINPUT" readonly="true"/>
										<div id="divNoOfDays" class="validate"/>
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
    	<logic:equal name="reminderParameter" property="action" value="authorize">
    		<br/>
			<html:form action="reminderParaService.do">
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
											onblur="upperCase('productCode');commonSearch('productService.do','productId','productCode','prdDescription','getProduct','divProductCode',function(){clearOtherData();},'',function(){clearAll();})"/>
										<input id="ButtonProductSerch" type="button" value="..." />
										<input type="text" size="60" id="prdDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divProductCode" class="validate"/>
									</td>
								</tr>

								<tr height="5px"></tr>
								<%-- <tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.scheme"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="schemeId" name="schemeId" value="0"/>
										<html:text property="schemeCode" styleId="schemeCode" size="5" maxlength="3"
											onfocus="clearDivision('divSchemeCode')"
											onblur="upperCase('schemeCode');commonSearch('schemeService.do','schemeId','schemeCode','schemeDescription','getScheme','divSchemeCode',function(){getAuthorizeGridData();},'&productId='+$('productId').value,function(){setGridData(grid,[]);})"/>
										<input id="ButtonSchemeSerch" type="button" value="..." />
										<input type="text" size="60" id="schemeDescription" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divSchemeCode" class="validate"/>
									</td>
								</tr> --%>
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
			var winScheme;
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

			    var button1 = Ext.get('ButtonSchemeSerch');
			    button1.on('click', function(){
			        if(!winScheme){
			            winScheme = new Ext.Window({
			                el:'scheme-serchDiv',
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
			                        winScheme.hide();
			                        if($('schemeId').value!='0' && $('schemeId').value!=''){
			                        	<logic:equal name="reminderParameter" property="action" value="authorize">
			                        		getAuthorizeGridData();
			                        	</logic:equal>
			                        	<logic:notEqual name="reminderParameter" property="action" value="authorize">
			                        		getGridData();
			                        	</logic:notEqual>
			                        }
			                   	}
			                }]
			            });
			        }
			        winScheme.show(this);
			        getSerchData("schemeService.do",gridSerchArt);
			        //$('hidDiv').className='showSearchPopup';
			    });
			});
		</script>
  	</body>
</html:html>
