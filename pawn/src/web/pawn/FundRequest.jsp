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
			var aproodDate='';
			var url = 'fundRequestService.do';
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
							$('requestAmount').value = message[2];
							$('requestNo').value 	= message[4];
							$('recordId').value     = message[7];
							$('version').value      = message[8];
							$('requestBranchId').value = message[10];
						}
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request ');
				    }
				}
				mySearchRequest.update(data,'POST');
			}

			function getSummaryData(){
				data = "dispatch=getAjaxMasterData";
				var mySearchRequest = new ajaxObject(url);
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
					if (responseStatus==200) {
						var message =  eval('(' + responseText + ')');
						if(message['error']){
							alert(message['error']);
						}else{
							$('fundlimit').value    	 = message[0];
							$('fundavailable').value  	 = message[1];

						}
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request ');
				    }
				}
				mySearchRequest.update(data,'POST');
			}

			function getGridData(){
				data = 'dispatch=getAjaxData'
				var mySearchRequest = new ajaxObject(url);

				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {

					if (responseStatus==200) {
						var message =  eval('(' + responseText + ')');
						if(message['error']){
							alert(message['error']);
						}else{

							setGridData(grid,message);
							getSummaryData();
						}
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request ');
				    }
				}
				mySearchRequest.update(data,'POST');
			}

			function getNotApprovedData(){
				data = "dispatch=getAjaxData&conditions=approvedBy&value="+ 0;
				var mySearchRequest = new ajaxObject(url);

				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {

					if (responseStatus==200) {
						var message =  eval('(' + responseText + ')');
						if(message['error']){
							alert(message['error']);
						}else{
							setGridData(grid,message);
							getSummaryData();
						}
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request ');
				    }
				}
				mySearchRequest.update(data,'POST');

			}

			function getSerchData(rurl,gridobj){
				data = 'dispatch=getAjaxData';

				var mySearchRequest = new ajaxObject(rurl);
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
					if (responseStatus==200) {
						var message =  eval('(' + responseText + ')');
						setGridData(gridobj,message);
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request 4');
				    }
				}
				mySearchRequest.update(data,'POST');
			}

			function clearAll(){
				$('requestDate').value		= '';
				$('requestAmount').value	= '';
				$('requestNo').value		= '';
				$('recordId').value    		= '';
				$('version').value     		= '';
				$('fundlimit').value     	= '';
				$('fundavailable').value    = '';
				$('requestBranchId').value	= '';

				//$('branchCode').innerHTML	= '';
				$('divDate').innerHTML 		= '';
				$('divAmount').innerHTML 	= '';
				$('divRequestNo').innerHTML = '';
				//setGridData(grid,[]);

				/*if(window.parent.frames['footer'].getCheckedValue(window.parent.frames['footer'].document.forms[0].elements['toolBtn'])== 5){
					getNotApprovedData();
				}else{
					getGridData();
				}*/

			}

			function getApproveGridData(){
				getNotApprovedData();
			}

			function clearFilled(){
				$('requestDate').value	= '';
				$('requestAmount').value = '0';
				$('requestNo').value	= '';
				$('recordId').value     = '';
				$('version').value      = '';
				$('requestBranchId').value 	= '';
				//$('approvedBy').value	= '';

				$('fundlimit').value     	= '';
				$('fundavailable').value    = '';
				$('divDate').innerHTML 	 = '';
				$('divAmount').innerHTML = '';
				$('divRequestNo').innerHTML = '';
				grid.setSelectedRows([]);
			}

			function clearOtherData(){
				$('requestDate').value	= '';
				$('requestAmount').value = '';
				$('requestNo').value	= '';
				$('recordId').value     = '';
				$('version').value      = '';
				$('fundlimit').value     	= '';
				$('fundavailable').value    = '';
				$('requestBranchId').value	= '';
				$('divDate').innerHTML 	 = '';
				$('divAmount').innerHTML = '';
				$('divRequestNo').innerHTML = '';
				//setGridData(grid,[]);
			}


			function getCreateData(){
				var requestDate   = $('requestDate').value;
				var requestAmount = unformatNumber($('requestAmount').value);
				var requestNo   = $('requestNo').value;
				//var pawnerId	= $('pawnerId').value;
				return "&requestDate=" + requestDate + "&requestAmount=" + requestAmount + "&requestNo=" +
				requestNo ;
			}
			function getApproveData(){
				return getChangedData();
			}

			function getChangedData(){

				var recordId 		= $('recordId').value;
				var version  		= $('version').value;
				var requestBranchId	= $('requestBranchId').value;

				var str = getCreateData() + "&recordId=" + recordId + "&version=" + version + "&requestBranchId=" + requestBranchId;
				return str;
			}

			function showValidationErrors(message){
				for( var i =0; i < message.length ; i++){
	       		    if( message[i]['requestDate'] )
	                    $('divDate').innerHTML = message[i]['requestDate'];
	                else if( message[i]['requestAmount'] )
	                    $('divAmount').innerHTML = message[i]['requestAmount'];
	            }
	        }
		</script>

		<style>
			#firstGrid {height: 250px;width:720px;}
			#firstGrid .aw-row-selector {text-align: center}
			#firstGrid .aw-column-0 {width: 100px;}
			#firstGrid .aw-column-1 {width: 150px;}
			#firstGrid .aw-column-2 {width: 120px;text-align: right;}
			#firstGrid .aw-column-3 {width: 120px;}
			#firstGrid .aw-column-4 {width: 120px;}
			#firstGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#firstGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}

			#secondGrid {height: 210px;width:600px;}
			#secondGrid .aw-row-selector {text-align: center}
			#secondGrid .aw-column-0 {width: 100px;}
			#secondGrid .aw-column-1 {width: 455px;}
			#secondGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#secondGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}

		</style>
  	</head>

  	<body onload="loadToolBar()">
    	<div id="screenCont" class="enableAll" align="center" ></div>

    	<!-- Create record -->
    	<logic:equal name="fundRequest" property="action" value="create">
			<html:form action="fundRequestService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
				<input type="hidden" id="requestBranchId" name="requestBranchId"/>
				<br/>
				<table border="0">
					<tr>
						<td>
							<table class="InputTable">
								<tr>
									<td colspan="2" align="center">
										<script>
											var myColumns = ["<bean:message bundle="lable" key="screen.requestno"/>",
															 "<bean:message bundle="lable" key="screen.date"/>",
															 "<bean:message bundle="lable" key="screen.amount"/>",
															 "<bean:message bundle="lable" key="screen.approvedby"/>",
															 "<bean:message bundle="lable" key="screen.approveddate"/>"];
				            				var str = new AW.Formats.String;

				            				var cellFormat = [str,str,str,str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){
			                                	if(row!='') {
			                                		$('recordId').value     	= grid.getCellText(7,row);
			                                		$('version').value      	= grid.getCellText(8,row);
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
										<bean:message bundle="lable" key="screen.requestno"/>&nbsp;
									</td>
									<td>
										<html:text property="requestNo" styleId="requestNo" size="15" maxlength="12" disabled="true" styleClass="READONLYINPUT" readonly="true"
											onfocus="clearDivision('divRequestNo')"/>
										<font color="red">*</font><br/>
										<div id="divRequestNo" class="validate"/>
									</td>
								</tr>

								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.date"/>&nbsp;
									</td>
									<td>
										<html:text property="requestDate" size="15" maxlength="12" styleId="requestDate" onkeypress="DateFormat(this,event);" onfocus="clearDivision('divDate')" onkeyup="DateFormat(this,event);" /><img src="images/none.gif" width="5px" ><input type="button" value="..." onclick="return showCalendar('requestDate');" />
										<font color="red">*</font><br/>
										<div id="divDate" class="validate"/>
									</td>
								</tr>

								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.amount"/>&nbsp;
									</td>
									<td>
										<html:text property="requestAmount" styleId="requestAmount" size="15" maxlength="10"
											onfocus="clearDivision('divAmount')"
											onkeyup="this.value=formatNumber(unformatNumber(this.value))"/>
											<font color="red">*</font><br/>
											<div id="divAmount" class="validate"/>
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
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.fundavailable"/>&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td>

													<input property="fundavailable" Id="fundavailable"
														value="0.00" size="15" style="text-align: right"
														readonly="readonly" class="READONLYINPUT" />
												</td>
											</tr>
										</table>
									</td>

									<td>
										<table cellpadding="0" cellspacing="0">
											<tr>
												<td style="width: 255px" align="right">
													<bean:message bundle="lable" key="screen.fundlimit" />&nbsp;
												</td>
												<td>
													<input property="fundlimit" Id="fundlimit"
														value="0.00" size="15" style="text-align: right"
														readonly="readonly" class="READONLYINPUT" />
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

    	<!-- update record  -->
    	<logic:equal name="fundRequest" property="action" value="update">
    		<br/>
			<html:form action="fundRequestService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
				<input type="hidden" id="approvedBy" name="approvedBy"/>
				<input type="hidden" id="requestBranchId" name="requestBranchId"/>

				<br/>
				<table border="0">
					<tr>
						<td>
							<table class="InputTable">
								<tr>
									<td colspan="2" align="center">
										<script>
											var myColumns = ["<bean:message bundle="lable" key="screen.requestno"/>",
															 "<bean:message bundle="lable" key="screen.date"/>",
															 "<bean:message bundle="lable" key="screen.amount"/>",
															 "<bean:message bundle="lable" key="screen.approvedby"/>",
															 "<bean:message bundle="lable" key="screen.approveddate"/>"];
				            				var str = new AW.Formats.String;
				            				var cellFormat = [str,str,str,str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){
			                                	if(row!='') {
			                                		if (grid.getCellText(9,row) != '0'){
														alert("Request Already Approved!");
														$('requestNo').readOnly = true;
														$('requestAmount').readOnly = true;
														$('requestDate').readOnly 	= true;
														$('requestAmount').value	= "";
														$('requestDate').value		= "";
														$('requestNo').value		= "";
														$('requestBranchId').value	= "";
													}else{
														$('requestNo').readOnly 	= false;
														$('requestAmount').readOnly = false;
														$('requestDate').readOnly 	= false;
				                                		//$('approvedBy').value  		= grid.getCellText(9,row);
				                                		$('requestAmount').value	= grid.getCellText(2,row);
				                                		$('requestDate').value  	= grid.getCellText(1,row);
				                                		$('requestNo').value  		= grid.getCellText(0,row);
				                                		$('recordId').value     	= grid.getCellText(7,row);
				                                		$('version').value      	= grid.getCellText(8,row);
				                                		$('requestBranchId').value	= grid.getCellText(10,row);
			                                		}
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
										<bean:message bundle="lable" key="screen.requestno"/>&nbsp;
									</td>
									<td>
										<html:text property="requestNo" styleId="requestNo" size="15" maxlength="12" disabled="true" styleClass="READONLYINPUT" readonly="true"
											onfocus="clearDivision('divRequestNo')"/>
										<font color="red">*</font><br/>
										<div id="divRequestNo" class="validate"/>
									</td>
								</tr>

								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.date"/>&nbsp;
									</td>
									<td>
										<html:text property="requestDate" size="15" maxlength="12" styleId="requestDate" onkeypress="DateFormat(this,event);" onfocus="clearDivision('divDate')" onkeyup="DateFormat(this,event);" /><img src="images/none.gif" width="5px" ><input type="button" value="..." onclick="return showCalendar('requestDate');" />
										<font color="red">*</font><br/>
										<div id="divDate" class="validate"/>
									</td>
								</tr>

								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.amount"/>&nbsp;
									</td>
									<td>
										<html:text property="requestAmount" styleId="requestAmount" size="15" maxlength="10"
											onfocus="clearDivision('divAmount')"
											onkeyup="this.value=formatNumber(unformatNumber(this.value))"/>
											<font color="red">*</font><br/>
											<div id="divAmount" class="validate"/>
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
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.fundavailable"/>&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td>

													<input property="fundavailable" Id="fundavailable"
														value="0.00" size="15" style="text-align: right"
														readonly="readonly" class="READONLYINPUT" />
												</td>
											</tr>
										</table>
									</td>

									<td>
										<table cellpadding="0" cellspacing="0">
											<tr>
												<td style="width: 255px" align="right">
													<bean:message bundle="lable" key="screen.fundlimit" />&nbsp;
												</td>
												<td>
													<input property="fundlimit" Id="fundlimit"
														value="0.00" size="15" style="text-align: right"
														readonly="readonly" class="READONLYINPUT" />
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<!--
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.fundavailable"/>&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td>

													<input property="fundavailable" Id="fundavailable"
														value="0.00" size="15" style="text-align: right"
														readonly="readonly" class="READONLYINPUT" />
												</td>
											</tr>
										</table>
									</td>
								</tr>
								 -->
								<tr height="5px"></tr>
							</table>
						</td>
					</tr>

				</table>
			</html:form>
    	</logic:equal>
     	<!-- Approve record -->

    	<logic:equal name="fundRequest" property="action" value="approve">
    		<br/>
			<html:form action="fundRequestService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
				<input type="hidden" id="approvedBy" name="approvedBy"/>
				<input type="hidden" id="requestBranchId" name="requestBranchId"/>

				<br/>
				<table border="0">
					<tr>
						<td>
							<table class="InputTable">
								<tr>
									<td colspan="2" align="center">
										<script>
											var myColumns = ["<bean:message bundle="lable" key="screen.requestno"/>",
															 "<bean:message bundle="lable" key="screen.date"/>",
															 "<bean:message bundle="lable" key="screen.amount"/>",
															 "<bean:message bundle="lable" key="screen.approvedby"/>",
															 "<bean:message bundle="lable" key="screen.approveddate"/>"];
				            				var str = new AW.Formats.String;
				            				var cellFormat = [str,str,str,str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){
			                                	if(row!='') {
			                                			$('requestNo').readOnly 	= false;
														$('requestAmount').readOnly = false;
														$('requestDate').readOnly 	= false;
				                                		$('approvedBy').value  		= grid.getCellText(9,row);
				                                		$('requestAmount').value	= grid.getCellText(2,row);
				                                		$('requestDate').value  	= grid.getCellText(1,row);
				                                		$('requestNo').value  		= grid.getCellText(0,row);
				                                		$('recordId').value     	= grid.getCellText(7,row);
				                                		$('version').value      	= grid.getCellText(8,row);
														$('requestBranchId').value	= grid.getCellText(10,row);
			                                	}
			                                };
			                                getNotApprovedData();
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
										<bean:message bundle="lable" key="screen.requestno"/>&nbsp;
									</td>
									<td>
										<html:text property="requestNo" styleId="requestNo" size="15" maxlength="12" disabled="true" styleClass="READONLYINPUT" readonly="true"
											onfocus="clearDivision('divRequestNo')"/>
										<font color="red">*</font><br/>
										<div id="divRequestNo" class="validate"/>
									</td>
								</tr>

								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.date"/>&nbsp;
									</td>
									<td>
										<html:text property="requestDate" size="15" maxlength="12" styleId="requestDate" styleClass="READONLYINPUT" disabled="true" readonly="true" onkeypress="DateFormat(this,event);" onfocus="clearDivision('divDate')" onkeyup="DateFormat(this,event);" /><img src="images/none.gif" width="5px" ><input type="button" value="..." disabled="true" onclick="return showCalendar('requestDate');" />
										<font color="red">*</font><br/>
										<div id="divDate" class="validate"/>
									</td>
								</tr>

								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.amount"/>&nbsp;
									</td>
									<td>
										<html:text property="requestAmount" styleId="requestAmount" size="15" maxlength="10" disabled="true" styleClass="READONLYINPUT" readonly="true"
											onfocus="clearDivision('divAmount')"
											onkeyup="this.value=formatNumber(unformatNumber(this.value))"/>
											<font color="red">*</font><br/>
											<div id="divAmount" class="validate"/>
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
								<tr height="5px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.fundavailable"/>&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td>

													<input property="fundavailable" Id="fundavailable"
														value="0.00" size="15" style="text-align: right"
														readonly="readonly" class="READONLYINPUT" />
												</td>
											</tr>
										</table>
									</td>

									<td>
										<table cellpadding="0" cellspacing="0">
											<tr>
												<td style="width: 255px" align="right">
													<bean:message bundle="lable" key="screen.fundlimit" />&nbsp;
												</td>
												<td>
													<input property="fundlimit" Id="fundlimit"
														value="0.00" size="15" style="text-align: right"
														readonly="readonly" class="READONLYINPUT" />
												</td>
											</tr>
										</table>
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
    	<logic:equal name="fundRequest" property="action" value="view">
    		<br/>
			<html:form action="fundRequestService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
			</html:form>
    	</logic:equal>

    	<jsp:include flush="true" page="OptionClientBrowser.jsp"></jsp:include>

  	</body>
</html:html>
