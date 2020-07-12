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
			var itemArray = new Array();

			window.parent.document.getElementById('footer').style.display="none";
			window.parent.document.getElementById("mainbody").height="600px";
			window.parent.document.getElementById("footer").height="0px";

			var url = 'authorizeTicketService.do';

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
				//obj.onSelectedRowsChanged= function(row){};
				return obj;
			}

			function setGridData(gridObj,Data){
				gridObj.setRowCount(Data.length);
				gridObj.setCellText(Data);
				gridObj.setSelectedRows([]);
				gridObj.refresh();
			}

			function getSerchData(rurl,gridobj,data){
				//data = "dispatch=getAjaxData";
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

			function getTicketData(){
				getAuthorizeData();
			}

			function getAuthorizeData(){
				data = "dispatch=getAuthorizeData&ticketId=" + $('ticketId').value;
				var mySearchRequest = new ajaxObject(url);
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
					if (responseStatus==200) {
						var message =  eval('(' + responseText + ')');

						$('pawnerCode').value		  	= message['pawnerCode'];
						$('pawnerName').value		   	= message['pawnerName'];
						$('productCode').value		   	= message['productCode'];
						$('productDescription').value	= message['productDescription'];
						$('totNoOfItems').value		   	= message['noOfArticle'];
						$('totWeight').value		   	= message['totalNetWeight'];
						$('totGoldValue').value		   	= message['goldValue'];
						$('totMrtValue').value		   	= message['marketValue'];
						$('totPawnAdvance').value		= message['pawnAdvance'];
						$('totActualDisb').value		= message['actualDisValue'];
						$('schemeCode').value		   	= message['schemCode'];
						$('schemeDescription').value	= message['schemDescription'];
						$('period').value		   		= message['period'];
						$('interestRateId').value		= message['interestId'];
						$('interestRate').value		   	= message['interestCode'];
						$('remark').value				= message['remark'];
						setGridData(grid,eval(message['ticketArticleList']));
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request');
				    }
				}
				mySearchRequest.update(data,'POST');
			}

			function getApproveData(){
				var pawnerCode 	= $('pawnerCode').value ;
				var productCode = $('productCode').value ;
				var schemeCode 	= $('schemeCode').value ;
				var totPawnAdvance = unformatNumber($('totPawnAdvance').value)*1 ;
				var ticketId   	= $('ticketId').value;
				var version    	= $('version').value;

				return "&ticketId=" + ticketId + "&version=" + version +
					   "&pawnerCode=" + pawnerCode + "&productCode=" + productCode +
					   "&schemeCode=" + schemeCode + "&totPawnAdvance=" + totPawnAdvance;
			}

			function clearAll(){
				$('ticketNumber').value			= '';
				$('totNoOfItems').value		   	= '';
				$('totWeight').value		   	= '';
				$('totGoldValue').value		   	= '0.00';
				$('totMrtValue').value		   	= '0.00';
				$('totPawnAdvance').value		= '0.00';
				$('totActualDisb').value		= '0.00';
				$('schemeId').value		   		= '';
				$('schemeCode').value		   	= '';
				$('schemeDescription').value	= '';
				$('period').value		   		= '';
				$('interestRateId').value		= '0';
				$('interestRate').value		   	= '';
				$('pawnerCode').value		  	= '';
				$('pawnerName').value		   	= '';
				$('productCode').value		   	= '';
				$('productDescription').value	= '';
				$('remark').value				= '';
				setGridData(grid,[]);
			}

			function confirms(val){
				$('screenCont').className = 'disableAll';

				var agree ;
				var booleanVal;
	       	    if( val.id == "Authorize"){
	           		agree=confirm('<bean:message bundle="message" key="msg.authorizeconfirm"/>');
	           		booleanVal = true;
	            }else {
	           		agree=confirm('<bean:message bundle="message" key="msg.rejectconfirm"/>');
	           		booleanVal = false;
	           	}

		    	if(agree){
			    	var data="dispatch=approve"	+ getApproveData() + "&booleanVal=" + booleanVal;
					var mySearchRequest = new ajaxObject(url);
					mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
						if (responseStatus==200) {
							var message =  eval('(' + responseText + ')');
							if(message['error']){
				       			alert(message['error']);
				       		}else if(message['authorizeSuccess']){
				       			alert(message['authorizeSuccess']);
				       			clearAll();
				       		}else{
				       			showValidationErrors(message);
				       		}
				       		$('screenCont').className = 'enableAll';
						}else {
				    	    alert(responseStatus + ' -- Error Processing Request');
				    	    $('screenCont').className = 'enableAll';
					    }
					}
					mySearchRequest.update(data,'POST');
			    }else{
			    	$('screenCont').className = 'enableAll';
			    }
			}

		    function showValidationErrors(message){
	       		for( var i =0; i < message.length ; i++){
	                if( message[i]['pawnerCode'] )
	                    $('divPawnerCode').innerHTML = message[i]['pawnerCode'];
	            }
	        }
		</script>

		<style>
			#firstGrid {height: 200px;width:720px;}
			#firstGrid .aw-row-selector {text-align: center}
			#firstGrid .aw-column-0 {width: 350px;}
			#firstGrid .aw-column-1 {width: 100px;}
			#firstGrid .aw-column-2 {width: 100px;text-align: right;}
			#firstGrid .aw-column-3 {width: 100px;text-align: right;}
			#firstGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#firstGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}

			#thirdGrid {height: 210px;width:600px;}
			#thirdGrid .aw-row-selector {text-align: center}
			#thirdGrid .aw-column-0 {width: 100px;}
			#thirdGrid .aw-column-1 {width: 455px;}
			#thirdGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#thirdGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}

			#popupGrid5 {height: 210px;width:600px;}
			#popupGrid5 .aw-row-selector {text-align: center}
			#popupGrid5 .aw-column-0 {width: 120px;}
			#popupGrid5 .aw-column-0 {width: 120px;}
			#popupGrid5 .aw-column-0 {width: 120px;}
			#popupGrid5 .aw-column-1 {width: 120px;}
			#popupGrid5 .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#popupGrid5 .aw-grid-row {border-bottom: 1px solid threedlightshadow;}

			table.toolTable {
			    border: #91a7b4 1px solid;
			    width: 740px;
			    height:30px;
			    background-color:#FFFFFF;
			    bottom: 0px;
			    position: absolute;
			}
		</style>
  	</head>

  	<body>
    	<div id="screenCont" class="enableAll" align="center" ></div>
    	<input type="hidden" id="ticketStatus" name="ticketStatus" value="2"/>
    	<!-- Create record -->
    	<logic:equal name="ticket" property="action" value="approve">
			<html:form action="ticketService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>

				<table border="0">
		          	<tr>
						<td>
							<table class="InputTable">
								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.ticketnumber"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="ticketId" name="ticketId" value="0" />
										<input id="ticketNumber" name="ticketNumber" size="20"	maxlength="13"
											onfocus="clearDivision('divTicketno')"
											onblur="$('ticketNumber').value.toUpperCase();commonSearch('infoconsoleService.do','ticketId','ticketNumber','ticketId','getTicket','divTicketno',function(){getAuthorizeData();},'',function(){clearAll();})"/>
										<input id="ButtonTicketSerch" type="button" value="..." />
										<br/><div id="divTicketno" class="validate"/>
									</td>
								</tr>

								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.clientname"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="pawnerId" name="pawnerId">
										<input id="pawnerCode" name="pawnerCode" style="width: 90px" maxlength="8"  tabindex="1" class="READONLYINPUT" readonly="readonly"/>
										<input id="pawnerName" name="pawnerName" size="54" maxlength="100" class="READONLYINPUT" readonly="readonly"/>
										<font color="red">*</font><br/>
										<div id="divPawnerCode" class="validate"/>
									</td>
								</tr>

								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.product"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="productId" name="productId" value="0">
										<input id="productCode" name="productCode" style="width: 50px" maxlength="3" tabindex="2" class="READONLYINPUT" readonly="readonly"/>
										<input id="productDescription" name="schemeDescription" size="60" class="READONLYINPUT" readonly="readonly"/>
										<font color="red">*</font><br/>
										<div id="divProductCode" class="validate"/>
									</td>
								</tr>

								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.scheme"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="schemeId" name="schemeId" value="0">
										<input id="schemeCode" name="schemeCode" style="width: 50px" maxlength="3" tabindex="3" class="READONLYINPUT" readonly="readonly"/>
										<input id="schemeDescription" name="schemeDescription" size="60" class="READONLYINPUT" readonly="readonly"/>
										<font color="red">*</font><br/>
										<div id="divSchemeCode" class="validate"/>
									</td>
								</tr>

								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.period"/>&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td width="20%">
													<input type="text" id="period" name="period" value="" maxlength="5" style="text-align: center;width: 50px;" class="READONLYINPUT" readonly="true" tabindex="-1"
														onfocus="clearDivision('divPeriod')"/></br>
													<div id="divPeriod" class="validate" />
												</td>
												<td>
													<table cellpadding="0" cellspacing="0">
														<tr>
															<td style="width: 267px" align="right">
																<bean:message bundle="lable" key="screen.interestCode"/>&nbsp;
															</td>
															<td>
																<input type="hidden" id="interestRateId" name="interestRateId" value="0">
																<input type="text"  id="interestRate" name="interestRate" size="5" maxlength="3" class="READONLYINPUT" readonly="true" tabindex="-1"
																	onfocus="clearDivision('divInterestRate')"/>
																<input type="button" id="interestRateBtn" value="..."><br>
																<div id="divInterestRate" class="validate"></div>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr height="2px"></tr>
							</table>
						</td>
					</tr>

					<tr height="5px"></tr>
					<tr>
						<td>
							<table class="InputTable">
								<tr height="2px"></tr>
								<tr>
									<td colspan="2" align="center">
										<script>
											var myColumns = ["<bean:message bundle="lable" key="screen.article"/>",
							  								 "<bean:message bundle="lable" key="screen.netweight"/>",
							  								 "<bean:message bundle="lable" key="screen.disbursement"/>",
							  								 "<bean:message bundle="lable" key="screen.noofitems"/>",];
				            				var str = new AW.Formats.String;
				            				var cellFormat = [str,str,str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){	}
			                            </script>
			                        </td>
			                    </tr>
			                    <tr height="2px"></tr>
							</table>
						</td>
					</tr>

					<tr height="5px"></tr>
					<tr>
						<td width="100%">
							<fieldset>
								<legend>Total Values</legend>

								<table style="border: 0;width: 740px;background-color: white;">
									<tr height="2px"></tr>
									<tr>
										<td width="20%" align="right">
											<bean:message bundle="lable" key="screen.noofitems"/>&nbsp;
										</td>
										<td>
											<table cellpadding="0" cellspacing="0" width="100%">
												<tr>
													<td width="20%">
														<input type="text" id="totNoOfItems" name="totNoOfItems" value=""  maxlength="10" style="text-align: center;width: 92px" readonly="readonly" class="READONLYINPUT"
															onfocus="clearDivision('divTotNoOfItems')"/><br/>
														<div id="divTotNoOfItems" class="validate" />
													</td>
													<td>
														<table cellpadding="0" cellspacing="0">
															<tr>
																<td style="width: 250px" align="right">
																	<bean:message bundle="lable" key="screen.weight"/>&nbsp;
																</td>
																<td>
																	<input type="text" id="totWeight" name="totWeight" size="15" maxlength="6" style="text-align: center;width: 92px" readonly="readonly" class="READONLYINPUT"
																		onfocus="clearDivision('divTotWeight')"/><br/>
																	<div id="divTotWeight" class="validate"/>
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</td>
									</tr>

									<tr>
										<td width="20%" align="right">
											<bean:message bundle="lable" key="screen.goldvalue"/>&nbsp;
										</td>
										<td>
											<table cellpadding="0" cellspacing="0" width="100%">
												<tr>
													<td width="20%">
														<input type="text" id="totGoldValue" name="totGoldValue" value="0.00" size="15" maxlength="10" style="text-align: right;width: 92px" readonly="readonly" class="READONLYINPUT"
															onfocus="clearDivision('divTotGoldValue')"/><br/>
														<div id="divTotGoldValue" class="validate"></div>
													</td>
													<td>
														<table cellpadding="0" cellspacing="0">
															<tr>
																<td style="width: 250px" align="right">
																	<bean:message bundle="lable" key="screen.marketvalue"/>&nbsp;
																</td>
																<td>
																	<input type="text" id="totMrtValue" name="totMrtValue" size="15" maxlength="10" value="0.00" style="text-align: right;width: 92px" readonly="readonly" class="READONLYINPUT"
																		onfocus="clearDivision('divTotMrtValue')"/>	<br/>
																	<div id="divTotMrtValue" class="validate"/>
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</td>
									</tr>

									<tr>
										<td width="20%" align="right">
											<bean:message bundle="lable" key="screen.pawnadvanace"/>&nbsp;
										</td>
										<td>
											<table cellpadding="0" cellspacing="0" width="100%">
												<tr>
													<td width="20%">
														<html:text property="totPawnAdvance" styleId="totPawnAdvance" value="0.00" size="23" maxlength="15" style="text-align: right;width: 92px"  readonly="readonly" styleClass="READONLYINPUT"
															onfocus="clearDivision('divTotPawnAdvance');this.maxLength=15;"
															onkeyup="this.value=formatNumber(unformatNumber(this.value));"
															onblur="if(this.value.length=15){this.maxLength=18;};if(this.value.length<=1 && (this.value.substring(0,1)=='-' || this.value=='' || this.value=='.')){this.value='0.00'};this.value=formatNumber(parseFloat(unformatNumber(this.value)).toFixed(2));if(unformatNumber(this.value)*1>unformatNumber($('totActualDisb').value)*1){this.value='0.00'}"/>
														<font color="red">*</font><br/>
														<div id="divTotPawnAdvance" class="validate" />
													</td>
													<td>
														<table cellpadding="0" cellspacing="0">
															<tr>
																<td style="width: 250px" align="right">
																	<bean:message bundle="lable" key="screen.actualdisbursment"/>&nbsp;
																</td>
																<td>
																	<input type="text" id="totActualDisb" name="totActualDisb" size="15" maxlength="10" value="0.00" style="text-align: right;width: 92px" readonly="readonly" class="READONLYINPUT"
																		onfocus="clearDivision('divTotActualDisb')"/>	<br/>
																	<div id="divTotActualDisb" class="validate"/>
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</td>
									</tr>

									<tr>
										<td width="20%" align="right">
											<bean:message bundle="lable" key="screen.remark"/>&nbsp;
										</td>
										<td colspan="2">
											<input type="text" id="remark" name="remark" size="89" maxlength="100" readonly="readonly" class="READONLYINPUT"
												onfocus="clearDivision('divRemark')"/>	<br/>
											<div id="divRemark" class="validate"/>
										</td>
									</tr>

									<tr height="2px"></tr>
								</table>
							</fieldset>
						</td>
					</tr>
				</table>
				<table class="toolTable" id="toolbar">
					<tr>
						<td align="right">
							<input type='button' value="<bean:message bundle="button" key="button.authorize"/> " class="buttonNormal" id="Authorize" onclick="confirms(this)"/>
							<input type='button' value='<bean:message bundle="button" key="button.reject"/> ' class='buttonNormal' id='Reject' onclick='confirms(this)'/>
							<input type="button" value="<bean:message bundle="button" key="button.clear"/>" id="Clear" class="buttonNormal" onclick="clearAll();"/>
						</td>
					</tr>
				</table>
			</html:form>
			<jsp:include flush="true" page="TicketBrowser.jsp"></jsp:include>

			<!-- Beging of the popup div  -->

			<div id="interest-serchDiv" class="x-hidden">
	        	<div class="x-window-header">
	            	Interest Slabs
	            </div>
	        	<div id="serch-tab6">
	            	<div class="x-tab" title="Search">
						<table style="width: 600px">
							<tr>
								<td>
									<script>
										var mySecColumns = ["<bean:message bundle="lable" key="screen.index"/>",
															"<bean:message bundle="lable" key="screen.noDaysFrom"/>",
							  								"<bean:message bundle="lable" key="screen.noDaysTo"/>",
							  								"<bean:message bundle="lable" key="screen.interestRate"/>"];
			            				var strForth = new AW.Formats.String;
			            				var secCellFormat = [strForth,strForth,strForth,strForth];
		                    			var ForthGrid = createBrowser(mySecColumns,'popupGrid5',secCellFormat);
		                                document.write(ForthGrid);
									</script>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>

			<script>
				var winTicket;
				var winInterest;

				Ext.onReady(function(){
				    var button5 = Ext.get('interestRateBtn');
				    button5.on('click', function(){
				        if(!winInterest){
				            winInterest = new Ext.Window({
				                el:'interest-serchDiv',
				                layout:'fit',
				                width:600,
				                height:300,
				                closable:false,
				                closeAction:'hide',
				                plain: true,

				                items: new Ext.TabPanel({
				                    el: 'serch-tab6',
				                    autoTabs:true,
				                    activeTab:0,
				                    deferredRender:false,
				                    border:false
				                }),

				                buttons: [{
				                    text: 'Ok',
				                    handler: function(){
				                        winInterest.hide();
				                   	}
				                }]
				            });
				        }
				        winInterest.show(this);
				        data = "dispatch=getSlabs&conditions=interestSlabCode&value=" + $('interestRateId').value;
				        getSerchData("interestRatesService.do",ForthGrid,data);

						/*var mySearchRequest = new ajaxObject('interestRatesService.do');
						mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
							if (responseStatus==200) {
								var message =  eval('(' + responseText + ')');
								if(message['error']){
									alert(message['error']);
								}else{
									setGridData(ForthGrid,message);
								}
							}else {
					    	    alert(responseStatus + ' -- Error Processing Request');
						    }
						}
						mySearchRequest.update(data,'POST');*/
				    });
				});
			</script>
    	</logic:equal>
    </body>
</html:html>
