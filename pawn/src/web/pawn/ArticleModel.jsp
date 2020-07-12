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

			var url = 'articleModelService.do';
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
				if($('productId').value>0){
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
				}else{
					setGridData(grid,[]);
					$('code').value		   = '';
					$('description').value = '';
					$('productCode').value = '';
					$('productId').value   = '';
					$('prdDescription').value = '';
					$('recordId').value    = '';
					$('version').value     = '';
				}
			}

			function getProductSerchData(){
				data = "dispatch=getAjaxData"
				var mySearchRequest = new ajaxObject("productService.do");
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
					if (responseStatus==200) {
						var message =  eval('(' + responseText + ')');
						setGridData(gridSerch,message);
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request');
				    }
				}
				mySearchRequest.update(data,'POST');
			}

			function getAuthorizeGridData(){
				data = "dispatch=getAuthorizeData&conditions=productId&value=" + $('productId').value;
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

			function clearOtherData(){
				$('code').value		   = '';
				$('description').value = '';
				$('recordId').value    = '';
				$('version').value     = '';
				$('divCode').innerHTML 		  = '';
				$('divDescription').innerHTML = '';
				$('divProductCode').innerHTML = '';
				setGridData(grid,[]);
			}

			function clearAll(){
				$('productCode').value = '';
				$('productId').value   = '';
				$('prdDescription').value = '';
				clearOtherData();
			}

			function getCreateData(){
				var code        = $('code').value;
				var description = $('description').value;
				var productCode = $('productCode').value;
				var productId   = $('productId').value;

				return "&code=" + code + "&description=" + description +
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
	               	else if( message[i]['productCode'] )
	                    $('divProductCode').innerHTML = message[i]['productCode'];
	            }
	        }
		</script>

		<style>
			#firstGrid {height: 380px;width:720px;}
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

			#authorizeGrid {height: 380px;width:720px;}
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

    	<div id="serchDiv" class="x-hidden">
        	<div class="x-window-header">
            	Search Product
            </div>
        	<div id="serch-tab">
            	<div class="x-tab" title="Search">
					<table style="width: 600px">
						<tr>
							<td>
								<script>
									var myColumnsSerch = ["<bean:message bundle="lable" key="screen.code"/>","<bean:message bundle="lable" key="screen.companyname"/>"];
		            				var str = new AW.Formats.String;
		            				var cellFormatSerch = [str,str];
	                    			var gridSerch = createBrowser(myColumnsSerch,'secondGrid',cellFormatSerch);
	                    			gridSerch.setHeaderHeight(25);
	                                document.write(gridSerch);
	                                gridSerch.onRowDoubleClicked = function(event, row){
										try{
											$('productId').value     = this.getCellText(3,row);
											$('productCode').value   = this.getCellText(0,row);
											$('prdDescription').value= this.getCellText(1,row);
											$('hidDiv').className='hideSearchPopup';
				                        	win.hide();
				                        	clearOtherData();
				                        	<logic:equal name="articalemodel" property="action" value="authorize">
				                        		getAuthorizeGridData();
				                        	</logic:equal>
				                        	<logic:notEqual name="articalemodel" property="action" value="authorize">
				                        		getGridData();
				                        	</logic:notEqual>
										}catch(error){}
									};
									gridSerch.onSelectedRowsChanged=function(row){
										try{
											$('productId').value     = this.getCellText(3,row);
											$('productCode').value   = this.getCellText(0,row);
											$('prdDescription').value= this.getCellText(1,row);
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
    	<logic:equal name="articalemodel" property="action" value="create">
			<html:form action="articleModelService.do">
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
										<input type="hidden" id="productId" name="productId" />
										<html:text property="productCode" styleId="productCode" size="5" maxlength="3"
											onfocus="clearDivision('divProductCode')"
											onblur="upperCase('productCode');commonSearch('articleModelService.do','productId','productCode','prdDescription','getProduct','divProductCode',function(){getGridData();},'',function(){getGridData();})"/>
										<input id="ButtonSerch" type="button" value="..." />
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
							  								 "<bean:message bundle="lable" key="screen.description"/>"];
				            				var str = new AW.Formats.String;
				            				var cellFormat = [str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){
			                                	if(row!='') {
			                                		//getRecordData(grid.getCellText(2,row));
			                                		$('code').value           = grid.getCellText(0,row);
			                                		$('description').value    = grid.getCellText(1,row);
			                                		$('productId').value      = grid.getCellText(2,row);
			                                		$('productCode').value    = grid.getCellText(3,row);
			                                		$('prdDescription').value = grid.getCellText(4,row);
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
										<bean:message bundle="lable" key="screen.code"/>&nbsp;
									</td>
									<td>
										<html:text property="code" styleId="code" size="5" maxlength="3"
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
										<html:text property="description" styleId="description" size="70" maxlength="50"
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


    	<!-- update record -->
    	<logic:equal name="articalemodel" property="action" value="update">
			<html:form action="articleModelService.do">
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
										<input type="hidden" id="productId" name="productId" />
										<html:text property="productCode" styleId="productCode" size="5" maxlength="3"
											onfocus="clearDivision('divProductCode')"
											onblur="upperCase('productCode');commonSearch('articleModelService.do','productId','productCode','prdDescription','getProduct','divProductCode',function(){getGridData();},'',function(){getGridData();})"/>
										<input id="ButtonSerch" type="button" value="..." />
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
							  								 "<bean:message bundle="lable" key="screen.description"/>"];
				            				var str = new AW.Formats.String;
				            				var cellFormat = [str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){
			                                	if(row!='') {
			                                		//getRecordData(grid.getCellText(2,row));
			                                		$('code').value           = grid.getCellText(0,row);
			                                		$('description').value    = grid.getCellText(1,row);
			                                		$('productId').value      = grid.getCellText(2,row);
			                                		$('productCode').value    = grid.getCellText(3,row);
			                                		$('prdDescription').value = grid.getCellText(4,row);
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
										<bean:message bundle="lable" key="screen.code"/>&nbsp;
									</td>
									<td>
										<html:text property="code" styleId="code" size="4" maxlength="3" disabled="true" styleClass="READONLYINPUT"
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
										<html:text property="description" styleId="description" size="70" maxlength="50"
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

    	<!-- Delete record -->
    	<logic:equal name="articalemodel" property="action" value="delete">
			<html:form action="articleModelService.do">
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
										<input type="hidden" id="productId" name="productId" />
										<html:text property="productCode" styleId="productCode" size="5" maxlength="3"
											onfocus="clearDivision('divProductCode')"
											onblur="upperCase('productCode');commonSearch('articleModelService.do','productId','productCode','prdDescription','getProduct','divProductCode',function(){getGridData();},'',function(){getGridData();})"/>
										<input id="ButtonSerch" type="button" value="..." />
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
							  								 "<bean:message bundle="lable" key="screen.description"/>"];
				            				var str = new AW.Formats.String;
				            				var cellFormat = [str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){
			                                	if(row!='') {
			                                		//getRecordData(grid.getCellText(2,row));
			                                		$('code').value           = grid.getCellText(0,row);
			                                		$('description').value    = grid.getCellText(1,row);
			                                		$('productId').value      = grid.getCellText(2,row);
			                                		$('productCode').value    = grid.getCellText(3,row);
			                                		$('prdDescription').value = grid.getCellText(4,row);
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
										<bean:message bundle="lable" key="screen.code"/>&nbsp;
									</td>
									<td>
										<html:text property="code" styleId="code" size="5"  disabled="true" styleClass="READONLYINPUT"
											onfocus="clearDivision('divCode')"/>
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
										<html:text property="description" styleId="description" disabled="true" size="60" styleClass="READONLYINPUT"
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


    	<!-- View record -->
    	<logic:equal name="articalemodel" property="action" value="authorize">
			<html:form action="articleModelService.do">
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
										<input type="hidden" id="productId" name="productId" />
										<html:text property="productCode" styleId="productCode" size="5" maxlength="3"
											onfocus="clearDivision('divProductCode')"
											onblur="upperCase('productCode');commonSearch('articleModelService.do','productId','productCode','prdDescription','getProduct','divProductCode',function(){getAuthorizeGridData();},'',function(){clearOtherData();})"/>
										<input id="ButtonSerch" type="button" value="..." />
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
			                                		$('code').value          = grid.getCellText(0,row);
			                                		$('description').value 	 = grid.getCellText(1,row);
			                                		$('authorizeMode').value = grid.getCellText(2,row);
			                                		$('recordId').value      = grid.getCellText(3,row);
			                                		$('version').value       = grid.getCellText(4,row);
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
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.code"/>&nbsp;
									</td>
									<td>
										<html:text property="code" styleId="code" size="5"  disabled="true" styleClass="READONLYINPUT"
											onfocus="clearDivision('divCode')"/>
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
										<html:text property="description" styleId="description" disabled="true" size="60" styleClass="READONLYINPUT"
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
			var win;
			Ext.onReady(function(){
			    var button = Ext.get('ButtonSerch');
			    button.on('click', function(){
			        if(!win){
			            win = new Ext.Window({
			                el:'serchDiv',
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
			                   		$('hidDiv').className='hideSearchPopup';
			                        win.hide();
			                        clearOtherData();
			                        <logic:equal name="articalemodel" property="action" value="authorize">
		                        		getAuthorizeGridData();
		                        	</logic:equal>
		                        	<logic:notEqual name="articalemodel" property="action" value="authorize">
		                        		getGridData();
		                        	</logic:notEqual>
			                   	}
			                }]
			            });
			        }
			        getProductSerchData();
			        win.show(this);
			        $('hidDiv').className='showSearchPopup';
			    });
			});
		</script>
  	</body>
</html:html>
