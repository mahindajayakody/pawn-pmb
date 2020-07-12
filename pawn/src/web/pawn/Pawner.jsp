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

			var url = 'pawnerService.do';
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

			function createPawnerTypeBrowser(myColumns,gridName,dataFormat){
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

				obj.setCellTemplate(new AW.Templates.Checkbox, 2);

			    //set initial value for column 2
			    obj.setCellValue(false, 2);
			    //obj.setCellValue(function(col,row){return this.getCellText(3, row)}, 2);
			    obj.setSelectorVisible(true);

			    obj.getCheckedValue=function() {
			        for(var i=0;i<obj.getRowCount();i++) {
			            if(obj.getCellValue(2,i)) {
			                alert(i);
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

			function getRecordData(){
				data = "dispatch=getAjaxData&recordId=" + $('pawnerId').value;
				var mySearchRequest = new ajaxObject(url);
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
					if (responseStatus==200) {
						var message =  eval('(' + responseText + ')');
						if(message['error']){
							alert(message['error']);
						}else{
							$('pawnerCode').value		= message[0];
							$('idOrBrNo').value        	= message[2];
							$('comOrIndiv').value		= message[3];
							$('title').value           	= message[4];
							$('initials').value   		= message[5];
							$('surName').value 			= message[6];
							$('initialsInFull').value 	= message[7];
							$('maritalStatus').value   	= message[8];
							$('sex').value 				= message[9];
							$('addressLine1').value 	= message[10];
							$('addressLine2').value 	= message[11];
							$('addressLine3').value 	= message[12];
							$('addressLine4').value 	= message[13];
							$('pasportNo').value 		= message[14];
							$('homeTelephoneNo').value 	= message[16];
							$('mobileNo').value 		= message[17];
							$('officeTelephoneNo').value= message[18];
							$('faxNo').value			= message[19];
							$('emailAddress').value 	= message[20];
							$('clientStatus').value 	= message[21];
							$('pawnerId').value    		= message[22];
							$('version').value     		= message[23];
							$('companyName').value 		= message[6];
							$('drivingLicenseNumber').value = message[15];

					        for(i=$('clientTypes').options.length-1;i>=0;i--){
						        $('clientTypes').remove(i);
						    }
					        for(var i=0;i<message[24].length;i+=2) {
				                $('clientTypes').options[$('clientTypes').options.length] = new Option(message[24][i+1],message[24][i]);
					        }
					        /*
					        for(var i=0;i<gridSerchPawnerType.getRowCount();i++) {
					            if(gridSerchPawnerType.getCellValue(2,i)) {
				                	$('clientTypes').options[$('clientTypes').options.length] = new Option(gridSerchPawnerType.getCellValue(1,i),gridSerchPawnerType.getCellValue(3,i));
					            }
					        }*/
						}
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request');
				    }
				}
				mySearchRequest.update(data,'POST');
			}

			function getPawnerData(){
				getRecordData();
			}

			function getGridData(){
				data = "dispatch=getAjaxData&conditions=recordStatus&value=1";
				var mySearchRequest = new ajaxObject('pawnerTypeService.do');
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
					if (responseStatus==200) {
						var message =  eval('(' + responseText + ')');
						if(message['error']){
							alert(message['error']);
						}else{
							setGridData(gridSerchPawnerType,message);
						}
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request');
				    }
				}
				mySearchRequest.update(data,'POST');
			}

			function clearOtherData(){
				$('comOrIndiv').value		= 'I';
				$('idOrBrNo').value        	= '';
				$('title').value           	= 'Mr';
				$('initials').value   		= '';
				$('surName').value 			= '';
				$('initialsInFull').value 	= '';
				$('maritalStatus').value   	= '';
				$('sex').value 				= '1';
				$('addressLine1').value 	= '';
				$('addressLine2').value 	= '';
				$('addressLine3').value 	= '';
				$('addressLine4').value 	= '';
				$('pasportNo').value 		= '';
				$('homeTelephoneNo').value 	= '';
				$('officeTelephoneNo').value= '';
				$('mobileNo').value 		= '';
				$('faxNo').value			= '';
				$('emailAddress').value 	= '';
				$('clientStatus').value 	= '1';
				$('pawnerId').value    		= '';
				$('version').value     		= '';
				$('companyName').value 		= '';
				$('drivingLicenseNumber').value = '';

				clearDiv();
				changeMode($('comOrIndiv'));
			}

			function clearDiv(){
				$('divIdOrBrNo').innerHTML 		  	= '';
				$('divInitials').innerHTML 			= '';
				$('divSurName').innerHTML 			= '';
				$('divInitialsInFull').innerHTML	= '';
				$('divAddressLine1').innerHTML 		= '';
				$('divAddressLine2').innerHTML 		= '';
				$('divAddressLine3').innerHTML 		= '';
				$('divAddressLine4').innerHTML		= '';
				$('divPasportNo').innerHTML 		= '';
				$('divHomeTelephoneNo').innerHTML 	= '';
				$('divOfficeTelephoneNo').innerHTML	= '';
				$('divMobileNo').innerHTML 			= '';
				$('divFaxNo').innerHTML 			= '';
				$('divEmailAddress').innerHTML		= '';
				$('divCompanyName').innerHTML		= '';
				$('divDrivingLicenseNumber').innerHTML = '';
			}

			function clearAll(){
				$('comOrIndiv').value		= 'I';
				$('idOrBrNo').value        	= '';
				$('title').value           	= 'Mr';
				$('initials').value   		= '';
				$('surName').value 			= '';
				$('initialsInFull').value 	= '';
				$('maritalStatus').value   	= '';
				$('sex').value 				= '1';
				$('addressLine1').value 	= '';
				$('addressLine2').value 	= '';
				$('addressLine3').value 	= '';
				$('addressLine4').value 	= '';
				$('pasportNo').value 		= '';
				$('homeTelephoneNo').value 	= '';
				$('officeTelephoneNo').value= '';
				$('mobileNo').value 		= '';
				$('faxNo').value			= '';
				$('emailAddress').value 	= '';
				$('clientStatus').value 	= '1';
				$('pawnerId').value    		= '';
				$('version').value     		= '';
				$('companyName').value 		= '';
				$('pawnerCode').value		= '';
				$('drivingLicenseNumber').value = '';

				$('divPawnerCode').innerHTML = '';
				changeMode($('comOrIndiv'));

				for(i=$('clientTypes').options.length-1;i>=0;i--){
			        $('clientTypes').remove(i);
			    }
			}

			function changeMode(obj){
				if(obj.value=='I'){
					$('trTitle').style.display 			= '';
					$('trInitials').style.display 		= '';
					$('trSurName').style.display 		= '';
					$('trCompanyName').style.display	= 'none';
					$('trInitialsInFull').style.display = '';
					$('trMaritalStatus').style.display 	= '';
					//$('trSex').style.display 			= '';
					$('trPassAndDrivNo').style.display 	= '';
					$('trHomeAndMobile').style.display 	= '';
					$('trOffTelAndFax').style.display 	= 'none';

					$('spTitle').style.display 			= '';
					$('spInitials').style.display 		= '';
					$('spInitialsInFull').style.display = '';
					$('spMaritalStatus').style.display 	= '';
					//$('spSex').style.display 			= '';
					$('spPassAndDrivNo').style.display 	= ''
					$('nicbrno').innerHTML 				= '<bean:message bundle="lable" key="screen.nicnumber"/>&nbsp;';
				}else{
					$('trTitle').style.display 			= 'none';
					$('trInitials').style.display 		= 'none';
					$('trSurName').style.display 		= 'none';
					$('trCompanyName').style.display	= '';
					$('trInitialsInFull').style.display = 'none';
					$('trMaritalStatus').style.display 	= 'none';
					//$('trSex').style.display 			= 'none';
					$('trPassAndDrivNo').style.display 	= 'none';
					$('trHomeAndMobile').style.display 	= 'none';
					$('trOffTelAndFax').style.display 	= '';

					$('spTitle').style.display 			= 'none';
					$('spInitials').style.display 		= 'none';
					$('spInitialsInFull').style.display = 'none';
					$('spMaritalStatus').style.display 	= 'none';
					//$('spSex').style.display 			= 'none';
					$('spPassAndDrivNo').style.display 	= 'none';
					$('nicbrno').innerHTML 				= '<bean:message bundle="lable" key="screen.brnumber"/>&nbsp;';
				}
			}

			function getCreateData(){
				var comOrIndiv		= $('comOrIndiv').value;
				var idOrBrNo 		= $('idOrBrNo').value;
				var title 			= $('title').value;
				var initials   		= $('initials').value;
				var companyName 	= $('companyName').value;
				var surName 		= comOrIndiv=='I'?$('surName').value:companyName;
				var initialsInFull  = $('initialsInFull').value;
				var maritalStatus   = $('maritalStatus').value;
				var sex 			= $('sex').value;
				var addressLine1 	= $('addressLine1').value;
				var addressLine2   	= $('addressLine2').value;
				var addressLine3 	= $('addressLine3').value;
				var addressLine4   	= $('addressLine4').value;
				var pasportNo       = $('pasportNo').value;
				var homeTelephoneNo = $('homeTelephoneNo').value;
				var mobileNo   		= $('mobileNo').value;
				var faxNo 			= $('faxNo').value;
				var emailAddress   	= $('emailAddress').value;
				var clientStatus    = $('clientStatus').value;
				var officeTelephoneNo	 = $('officeTelephoneNo').value;
				var drivingLicenseNumber = $('drivingLicenseNumber').value;

				if($('clientTypes').options.length>0){
					var clientTypes = $('clientTypes').options[0].value;
					for(i=1;i<$('clientTypes').options.length;i++){
				        clientTypes += '<@>' + $('clientTypes').options[i].value;
				    }
				}

				return "&comOrIndiv=" + comOrIndiv + "&idOrBrNo=" + idOrBrNo +
					   "&title=" + title + "&initials=" + initials +
					   "&surName=" + surName + "&surName=" + surName +
					   "&initialsInFull=" + initialsInFull + "&maritalStatus=" + maritalStatus +
					   "&sex=" + sex + "&addressLine1=" + addressLine1 +
					   "&addressLine2=" + addressLine2 + "&addressLine3=" + addressLine3 +
					   "&addressLine4=" + addressLine4 + "&pasportNo=" + pasportNo +
					   "&homeTelephoneNo=" + homeTelephoneNo + "&mobileNo=" + mobileNo +
					   "&faxNo=" + faxNo + "&emailAddress=" + emailAddress +
					   "&clientStatus=" + clientStatus + "&companyName=" + companyName +
					   "&officeTelephoneNo=" + officeTelephoneNo + "&drivingLicenseNumber=" + drivingLicenseNumber +
					   "&clientTypes=" + clientTypes;
			}

			function getChangedData(){
				var recordId   = $('pawnerId').value;
				var version    = $('version').value;
				var pawnerCode = $('pawnerCode').value;

				var str = getCreateData() + "&recordId=" + recordId + "&version=" + version + "&pawnerCode=" + pawnerCode;
				return str;
			}

			function showValidationErrors(message){
	       		for( var i =0; i < message.length ; i++){
	                if( message[i]['idOrBrNo'] )
	                    $('divIdOrBrNo').innerHTML = ($('comOrIndiv').value=='I'?'<bean:message bundle="lable" key="screen.nicnumber"/>&nbsp;':'<bean:message bundle="lable" key="screen.brnumber"/>&nbsp;')+message[i]['idOrBrNo'];
	                else if( message[i]['initials'] )
	                    $('divInitials').innerHTML = message[i]['initials'];
	               	else if( message[i]['surName'] )
	                    $('divSurName').innerHTML = message[i]['surName'];
	               	else if( message[i]['initialsInFull'] )
	                    $('divInitialsInFull').innerHTML = message[i]['initialsInFull'];
	                else if( message[i]['addressLine1'] )
	                    $('divAddressLine1').innerHTML = message[i]['addressLine1'];
	               	else if( message[i]['addressLine2'] )
	                    $('divAddressLine2').innerHTML = message[i]['addressLine2'];
	               	else if( message[i]['addressLine3'] )
	                    $('divAddressLine3').innerHTML = message[i]['addressLine3'];
	                else if( message[i]['addressLine4'] )
	                    $('divAddressLine4').innerHTML = message[i]['addressLine4'];
	                else if( message[i]['description'] )
	                    $('divCompanyName').innerHTML = message[i]['description'];
	               	else if( message[i]['pasportNo'] )
	                    $('divPasportNo').innerHTML = message[i]['pasportNo'];
	               	else if( message[i]['homeTelephoneNo'] )
	                    $('divHomeTelephoneNo').innerHTML = message[i]['homeTelephoneNo'];
	                else if( message[i]['officeTelephoneNo'] )
	                    $('divOfficeTelephoneNo').innerHTML = message[i]['officeTelephoneNo'];
	                else if( message[i]['mobileNo'] )
	                    $('divMobileNo').innerHTML = message[i]['mobileNo'];
	               	else if( message[i]['faxNo'] )
	                    $('divFaxNo').innerHTML = message[i]['faxNo'];
	               	else if( message[i]['emailAddress'] )
	                    $('divEmailAddress').innerHTML = message[i]['emailAddress'];
	                else if( message[i]['artModelCode'] )
	                    $('divDrivingLicenseNumber').innerHTML = message[i]['artModelCode'];
	            }

	        }
		</script>

		<style>
			#firstGrid {height: 210px;width:600px;}
			#firstGrid .aw-row-selector {text-align: center}
			#firstGrid .aw-column-0 {width: 150px;}
			#firstGrid .aw-column-1 {width: 300px;}
			#firstGrid .aw-column-2 {width: 100px;}
			#firstGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#firstGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}

			#secondGrid {height: 210px;width:600px;}
			#secondGrid .aw-row-selector {text-align: center}
			#secondGrid .aw-column-0 {width: 75px;}
			#secondGrid .aw-column-1 {width: 350px;}
			#secondGrid .aw-column-2 {width: 750px;text-shadow: none;}
			#secondGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#secondGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
		</style>
  	</head>

  	<body onload="loadToolBar()">
    	<div id="screenCont" class="enableAll" align="center" ></div>

 		<div id="pawnerType-serchDiv" class="x-hidden">
        	<div class="x-window-header">
            	Search Pawner Type
            </div>
        	<div id="serch-tab1">
            	<div class="x-tab" title="Search">
					<table style="width: 600px">
						<tr>
							<td>
								<script>
									var myColumnsSerchPawnerType  = ["<bean:message bundle="lable" key="screen.code"/>","<bean:message bundle="lable" key="screen.description"/>","<bean:message bundle="lable" key="screen.add"/>"];
		            				var strSerchPawnerType        = new AW.Formats.String;
		            				var cellFormatSerchPawnerType = [strSerchPawnerType,strSerchPawnerType,strSerchPawnerType];
	                    			var gridSerchPawnerType       = createPawnerTypeBrowser(myColumnsSerchPawnerType,'firstGrid',cellFormatSerchPawnerType);
	                    			gridSerchPawnerType.setHeaderHeight(25);
	                                document.write(gridSerchPawnerType);
	                                gridSerchPawnerType.onRowDoubleClicked = function(event, row){
										try{

										}catch(error){}
									};
									gridSerchPawnerType.onSelectedRowsChanged=function(row){
										try{
											if(row!=''){

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
    	<logic:equal name="pawner" property="action" value="create">
			<html:form action="pawnerService.do">
				<input type="hidden" id="pawnerId" name="pawnerId"/>
				<input type="hidden" id="version" name="version"/>
				<input type="hidden" id="pawnerCode" name="pawnerCode"/>
				<input type="hidden" id="divPawnerCode" name="divPawnerCode"/>
				<table border="0">
		          	<tr>
						<td>
							<table class="InputTable">
								<tr height="3px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.comorindiv"/>&nbsp;
									</td>
									<td>
										<select id="comOrIndiv" name="comOrIndiv"  style="width: 100px" onchange="changeMode(this)">
											<option value="I"><bean:message bundle="lable" key="screen.individual"/></option>
											<option value="C"><bean:message bundle="lable" key="screen.company"/></option>
										</select>
									</td>
								</tr>

								<tr height="2px"></tr>
								<tr>
									<td id="nicbrno" width="20%" align="right">
										<bean:message bundle="lable" key="screen.nicnumber"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="idOrBrNoDesc" name="idOrBrNoDesc"/>
										<input type="hidden" id="dividOrBrNoDesc" name="dividOrBrNoDesc"/>
										<html:text property="idOrBrNo" styleId="idOrBrNo" size="20" maxlength="20"
											onfocus="clearDivision('divIdOrBrNo')"
											onblur="upperCase('idOrBrNo');commonSearch('pawnerService.do','pawnerId','idOrBrNo','idOrBrNoDesc','getPawnerByNICOrBR','dividOrBrNoDesc',function(){getRecordData();},'',function(){})"/>
										<font color="red">*</font><br/>
										<div id="divIdOrBrNo" class="validate"/>
									</td>
								</tr>

								<tr id="spTitle" height="2px"></tr>
								<tr id="trTitle">
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.title"/>&nbsp;
									</td>
									<td>
										<select id="title" name="title" style="width: 90px">
											<option value="Mr">Mr</option>
											<option value="Mrs">Mrs</option>
											<option value="Rev">Rev</option>
											<option value="Ms">Ms</option>
										</select>
									</td>
								</tr>

								<tr id="spInitials" height="2px"></tr>
								<tr id="trInitials">
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.initial"/>&nbsp;
									</td>
									<td>
										<html:text property="initials" styleId="initials" size="20" maxlength="15"
											onfocus="clearDivision('divInitials')" onblur="upperCase('initials')"/>
										<font color="red">*</font><br/>
										<div id="divInitials" class="validate"/>
									</td>
								</tr>

								<tr height="2px"></tr>
								<tr id="trSurName">
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.surname"/>&nbsp;
									</td>
									<td>
										<html:text property="surName" styleId="surName" size="70" maxlength="100"
											onfocus="clearDivision('divSurName')"
											onblur="upperCase('surName')"/>
										<font color="red">*</font><br/>
										<div id="divSurName" class="validate"/>
									</td>
								</tr>

								<tr id="trCompanyName" style="display:none">
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.companyname"/>&nbsp;
									</td>
									<td>
										<html:text property="companyName" styleId="companyName" size="70" maxlength="100"
											onfocus="clearDivision('divCompanyName')"
											onblur="upperCase('companyName')"/>
										<font color="red">*</font><br/>
										<div id="divCompanyName" class="validate"/>
									</td>
								</tr>

								<tr id="spInitialsInFull" height="2px"></tr>
								<tr id="trInitialsInFull">
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.initialsinfull"/>&nbsp;
									</td>
									<td>
										<html:text property="initialsInFull" styleId="initialsInFull" size="70" maxlength="100"
											onfocus="clearDivision('divInitialsInFull')"
											onblur="upperCase('initialsInFull')"/>
										<font color="red">*</font><br/>
										<div id="divInitialsInFull" class="validate"/>
									</td>
								</tr>

								<tr id="spMaritalStatus" height="2px"></tr>
								<tr id="trMaritalStatus">
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.maritalstatus"/>&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td width="20%">
													<select id="maritalStatus" name="maritalStatus" style="width: 90px">
														<option value="1"><bean:message bundle="lable" key="screen.single"/></option>
														<option value="2"><bean:message bundle="lable" key="screen.married"/></option>
														<option value="3"><bean:message bundle="lable" key="screen.widow"/></option>
													</select>
												</td>
												<td>
													<table cellpadding="0" cellspacing="0">
														<tr>
															<td style="width: 109px" align="right">
																<bean:message bundle="lable" key="screen.sex"/>&nbsp;
															</td>
															<td>
																<select id="sex" name="sex" style="width: 90px">
																	<option value="1"><bean:message bundle="lable" key="screen.male"/></option>
																	<option value="2"><bean:message bundle="lable" key="screen.female"/></option>
																</select>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>

								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.addressline1"/>&nbsp;
									</td>
									<td>
										<html:text property="addressLine1" styleId="addressLine1" size="60" maxlength="60"
											onfocus="clearDivision('divAddressLine1')"
											onblur="upperCase('addressLine1')"/>
										<font color="red">*</font><br/>
										<div id="divAddressLine1" class="validate"/>
									</td>
								</tr>

								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.addressline2"/>&nbsp;
									</td>
									<td>
										<html:text property="addressLine2" styleId="addressLine2" size="60" maxlength="60"
											onfocus="clearDivision('divAddressLine2')"
											onblur="upperCase('addressLine2')"/>
										<div id="divAddressLine2" class="validate"/>
									</td>
								</tr>

								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.addressline3"/>&nbsp;
									</td>
									<td>
										<html:text property="addressLine3" styleId="addressLine3" size="60" maxlength="60"
											onfocus="clearDivision('divAddressLine3')"
											onblur="upperCase('addressLine3')"/>
										<br/>
										<div id="divAddressLine3" class="validate"/>
									</td>
								</tr>

								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.town"/>&nbsp;
									</td>
									<td>
										<html:text property="addressLine4" styleId="addressLine4" size="60" maxlength="60"
											onfocus="clearDivision('divAddressLine4')"
											onblur="upperCase('addressLine4')"/>
										<font color="red">*</font><br/>
										<div id="divAddressLine4" class="validate"/>
									</td>
								</tr>

								<tr id="spPassAndDrivNo" height="2px"></tr>
								<tr id="trPassAndDrivNo">
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.passportno"/>&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td width="20%">
													<html:text property="pasportNo" styleId="pasportNo" size="15" maxlength="10"
														onfocus="clearDivision('divPasportNo')"/>
													<br/><div id="divPasportNo" class="validate"/>
												</td>
												<td>
													<table cellpadding="0" cellspacing="0">
														<tr>
															<td style="width: 310px" align="right">
																<bean:message bundle="lable" key="screen.drivingno"/>&nbsp;
															</td>
															<td>
																<html:text property="drivingLicenseNumber" styleId="drivingLicenseNumber" size="15" maxlength="10"
																	onfocus="clearDivision('divDrivingLicenseNumber')"/>
																<br/><div id="divDrivingLicenseNumber" class="validate"/>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>

								<tr height="2px"></tr>
								<tr id="trHomeAndMobile">
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.hometelno"/>&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td width="20%">
													<html:text property="homeTelephoneNo" styleId="homeTelephoneNo" size="15" maxlength="10"
														onfocus="clearDivision('divHomeTelephoneNo')"/>
													<br/><div id="divHomeTelephoneNo" class="validate"/>
												</td>
												<td>
													<table cellpadding="0" cellspacing="0">
														<tr>
															<td style="width: 310px" align="right">
																<bean:message bundle="lable" key="screen.mobileno"/>&nbsp;
															</td>
															<td>
																<html:text property="mobileNo" styleId="mobileNo" size="15" maxlength="10"
																	onfocus="clearDivision('divMobileNo')"/>
																<br/><div id="divMobileNo" class="validate"/>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>

								<tr id="trOffTelAndFax" style="display: none;">
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.officetelno"/>&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td width="20%">
													<html:text property="officeTelephoneNo" styleId="officeTelephoneNo" size="15" maxlength="10"
														onfocus="clearDivision('divOfficeTelephoneNo')"/>
													<br/><div id="divOfficeTelephoneNo" class="validate"/>
												</td>
												<td>
													<table cellpadding="0" cellspacing="0">
														<tr>
															<td style="width: 310px" align="right">
																<bean:message bundle="lable" key="screen.faxno"/>&nbsp;
															</td>
															<td>
																<html:text property="faxNo" styleId="faxNo" size="15" maxlength="10"
																	onfocus="clearDivision('divFaxNo')"/>
																<br/><div id="divFaxNo" class="validate"/>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>

								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.email"/>&nbsp;
									</td>
									<td>
										<html:text property="emailAddress" styleId="emailAddress" size="60" maxlength="60"
											onfocus="clearDivision('divEmailAddress')"/>
										<br/><div id="divEmailAddress" class="validate"/>
									</td>
								</tr>

								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.clientstatus"/>&nbsp;
									</td>
									<td>
										<select id="clientStatus" name="clientStatus" style="width: 90px">
											<option value="1"><bean:message bundle="lable" key="screen.active"/></option>
											<option value="2"><bean:message bundle="lable" key="screen.inactive"/></option>
											<option value="3"><bean:message bundle="lable" key="screen.blacklist"/></option>
										</select>
									</td>
								</tr>

								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right"  style="vertical-align: top;">
										<bean:message bundle="lable" key="screen.pawnertype"/>&nbsp;
									</td>
									<td>
										<select id="clientTypes" style="width:150px; height:60px" name="clientTypes" multiple="multiple" size="4">
										</select>
										<input type="button" id="pawnerTypeBtn" value="..." style="vertical-align: top;"/>
									</td>
								</tr>
								<tr height="3px"></tr>
							</table>
						</td>
					</tr>
				</table>
			</html:form>
    	</logic:equal>


    	<!-- update record -->
    	<logic:equal name="pawner" property="action" value="update">
			<html:form action="pawnerService.do">
				<input type="hidden" id="pawnerId" name="pawnerId"/>
				<input type="hidden" id="version" name="version"/>
				<input type="hidden" id="userType" name="userType" value=""/>

				<table border="0">
					<tr>
						<td>
							<table class="InputTable">
								<tr height="3px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.code"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="pawnerName" name="pawnerName">
										<input id="pawnerCode" name="pawnerCode" style="width: 90px" maxlength="8" readonly="readonly" class="READONLYINPUT"
											onfocus="clearDivision('divPawnerCode')"
											onblur="commonSearch('pawnerService.do','pawnerId','pawnerCode','pawnerName','getPawner','divPawnerCode',function(){getRecordData();},'',function(){clearOtherData();})"/>
										<input id="ButtonPawnerSerch" type="button" value="..." />
										<font color="red">*</font><br/>
										<div id="divPawnerCode" class="validate"/>
									</td>
								</tr>

								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.comorindiv"/>&nbsp;
									</td>
									<td>
										<select id="comOrIndiv" name="comOrIndiv"  style="width: 90px" onchange="changeMode(this)" disabled="disabled" class="READONLYINPUT">
											<option value="I"><bean:message bundle="lable" key="screen.individual"/></option>
											<option value="C"><bean:message bundle="lable" key="screen.company"/></option>
										</select>
									</td>
								</tr>

								<tr height="2px"></tr>
								<tr>
									<td id="nicbrno" width="20%" align="right">
										<bean:message bundle="lable" key="screen.nicnumber"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="idOrBrNoDesc" name="idOrBrNoDesc"/>
										<input type="hidden" id="dividOrBrNoDesc" name="dividOrBrNoDesc"/>
										<html:text property="idOrBrNo" styleId="idOrBrNo" size="12" maxlength="10" readonly="readonly" styleClass="READONLYINPUT"
											onfocus="clearDivision('divIdOrBrNo')"
											onblur="upperCase('idOrBrNo');commonSearch('pawnerService.do','pawnerId','idOrBrNo','idOrBrNoDesc','getPawnerByNICOrBR','dividOrBrNoDesc',function(){getRecordData();},'',function(){})"/>
										<font color="red">*</font><br/>
										<div id="divIdOrBrNo" class="validate"/>
									</td>
								</tr>

								<tr id="spTitle" height="2px"></tr>
								<tr id="trTitle">
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.title"/>&nbsp;
									</td>
									<td>
										<select id="title" name="title" style="width: 90px">
											<option value="Mr">Mr</option>
											<option value="Mrs">Mrs</option>
											<option value="Rev">Rev</option>
											<option value="Ms">Ms</option>
										</select>
									</td>
								</tr>

								<tr id="spInitials" height="2px"></tr>
								<tr id="trInitials">
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.initial"/>&nbsp;
									</td>
									<td>
										<html:text property="initials" styleId="initials" size="20" maxlength="15"
											onfocus="clearDivision('divInitials')" onblur="upperCase('initials')"/>
										<font color="red">*</font><br/>
										<div id="divInitials" class="validate"/>
									</td>
								</tr>

								<tr height="2px"></tr>
								<tr id="trSurName">
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.surname"/>&nbsp;
									</td>
									<td>
										<html:text property="surName" styleId="surName" size="70" maxlength="100"
											onfocus="clearDivision('divSurName')"
											onblur="upperCase('surName')"/>
										<font color="red">*</font><br/>
										<div id="divSurName" class="validate"/>
									</td>
								</tr>

								<tr id="trCompanyName" style="display:none">
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.companyname"/>&nbsp;
									</td>
									<td>
										<html:text property="companyName" styleId="companyName" size="70" maxlength="100"
											onfocus="clearDivision('divCompanyName')"
											onblur="upperCase('companyName')"/>
										<font color="red">*</font><br/>
										<div id="divCompanyName" class="validate"/>
									</td>
								</tr>

								<tr id="spInitialsInFull" height="2px"></tr>
								<tr id="trInitialsInFull">
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.initialsinfull"/>&nbsp;
									</td>
									<td>
										<html:text property="initialsInFull" styleId="initialsInFull" size="70" maxlength="100"
											onfocus="clearDivision('divInitialsInFull')"
											onblur="upperCase('initialsInFull')"/>
										<font color="red">*</font><br/>
										<div id="divInitialsInFull" class="validate"/>
									</td>
								</tr>

								<tr id="spMaritalStatus" height="2px"></tr>
								<tr id="trMaritalStatus">
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.maritalstatus"/>&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td width="20%">
													<select id="maritalStatus" name="maritalStatus" style="width: 90px">
														<option value="1"><bean:message bundle="lable" key="screen.single"/></option>
														<option value="2"><bean:message bundle="lable" key="screen.married"/></option>
														<option value="3"><bean:message bundle="lable" key="screen.widow"/></option>
													</select>
												</td>
												<td>
													<table cellpadding="0" cellspacing="0">
														<tr>
															<td style="width: 109px" align="right">
																<bean:message bundle="lable" key="screen.sex"/>&nbsp;
															</td>
															<td>
																<select id="sex" name="sex" style="width: 90px">
																	<option value="1"><bean:message bundle="lable" key="screen.male"/></option>
																	<option value="2"><bean:message bundle="lable" key="screen.female"/></option>
																</select>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>

								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.addressline1"/>&nbsp;
									</td>
									<td>
										<html:text property="addressLine1" styleId="addressLine1" size="60" maxlength="60"
											onfocus="clearDivision('divAddressLine1')"
											onblur="upperCase('addressLine1')"/>
										<font color="red">*</font><br/>
										<div id="divAddressLine1" class="validate"/>
									</td>
								</tr>

								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.addressline2"/>&nbsp;
									</td>
									<td>
										<html:text property="addressLine2" styleId="addressLine2" size="60" maxlength="60"
											onfocus="clearDivision('divAddressLine2')"
											onblur="upperCase('addressLine2')"/>
										<br/>
										<div id="divAddressLine2" class="validate"/>
									</td>
								</tr>

								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.addressline3"/>&nbsp;
									</td>
									<td>
										<html:text property="addressLine3" styleId="addressLine3" size="60" maxlength="60"
											onfocus="clearDivision('divAddressLine3')"
											onblur="upperCase('addressLine3')"/>
										<br/>
										<div id="divAddressLine3" class="validate"/>
									</td>
								</tr>

								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.town"/>&nbsp;
									</td>
									<td>
										<html:text property="addressLine4" styleId="addressLine4" size="60" maxlength="60"
											onfocus="clearDivision('divAddressLine4')"
											onblur="upperCase('addressLine4')"/>
										<font color="red">*</font><br/>
										<div id="divAddressLine4" class="validate"/>
									</td>
								</tr>

								<tr id="spPassAndDrivNo" height="2px"></tr>
								<tr id="trPassAndDrivNo">
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.passportno"/>&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td width="20%">
													<html:text property="pasportNo" styleId="pasportNo" size="15" maxlength="10"
														onfocus="clearDivision('divPasportNo')"/>
													<br/><div id="divPasportNo" class="validate"/>
												</td>
												<td>
													<table cellpadding="0" cellspacing="0">
														<tr>
															<td style="width: 310px" align="right">
																<bean:message bundle="lable" key="screen.drivingno"/>&nbsp;
															</td>
															<td>
																<html:text property="drivingLicenseNumber" styleId="drivingLicenseNumber" size="15" maxlength="10"
																	onfocus="clearDivision('divDrivingLicenseNumber')"/>
																<br/><div id="divDrivingLicenseNumber" class="validate"/>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>

								<tr height="2px"></tr>
								<tr id="trHomeAndMobile">
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.hometelno"/>&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td width="20%">
													<html:text property="homeTelephoneNo" styleId="homeTelephoneNo" size="15" maxlength="10"
														onfocus="clearDivision('divHomeTelephoneNo')"/>
													<br/><div id="divHomeTelephoneNo" class="validate"/>
												</td>
												<td>
													<table cellpadding="0" cellspacing="0">
														<tr>
															<td style="width: 310px" align="right">
																<bean:message bundle="lable" key="screen.mobileno"/>&nbsp;
															</td>
															<td>
																<html:text property="mobileNo" styleId="mobileNo" size="15" maxlength="10"
																	onfocus="clearDivision('divMobileNo')"/>
																<br/><div id="divMobileNo" class="validate"/>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>

								<tr id="trOffTelAndFax" style="display: none;">
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.officetelno"/>&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td width="20%">
													<html:text property="officeTelephoneNo" styleId="officeTelephoneNo" size="15" maxlength="10"
														onfocus="clearDivision('divOfficeTelephoneNo')"/>
													<br/><div id="divOfficeTelephoneNo" class="validate"/>
												</td>
												<td>
													<table cellpadding="0" cellspacing="0">
														<tr>
															<td style="width: 310px" align="right">
																<bean:message bundle="lable" key="screen.faxno"/>&nbsp;
															</td>
															<td>
																<html:text property="faxNo" styleId="faxNo" size="15" maxlength="10"
																	onfocus="clearDivision('divFaxNo')"/>
																<br/><div id="divFaxNo" class="validate"/>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>

								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.email"/>&nbsp;
									</td>
									<td>
										<html:text property="emailAddress" styleId="emailAddress" size="60" maxlength="60"
											onfocus="clearDivision('divEmailAddress')"/>
										<br/><div id="divEmailAddress" class="validate"/>
									</td>
								</tr>

								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.clientstatus"/>&nbsp;
									</td>
									<td>
										<select id="clientStatus" name="clientStatus" style="width: 90px">
											<option value="1"><bean:message bundle="lable" key="screen.active"/></option>
											<option value="2"><bean:message bundle="lable" key="screen.inactive"/></option>
											<option value="3"><bean:message bundle="lable" key="screen.blacklist"/></option>
										</select>
									</td>
								</tr>

								<tr height="2px"></tr>
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.pawnertype"/>&nbsp;
									</td>
									<td>
										<select id="clientTypes" style="width:150px; height:60px" name="clientTypes" multiple="multiple" size="4">
										</select>
										<input type="button" id="pawnerTypeBtn" value="..." style="vertical-align: top;"/>
									</td>
								</tr>
								<tr height="3px"></tr>
							</table>
						</td>
					</tr>
				</table>
			</html:form>
			<jsp:include flush="true" page="ClientBrowser.jsp"></jsp:include>
    	</logic:equal>

		<script>
			var winPawnerType;

			Ext.onReady(function(){
			    var button1 = Ext.get('pawnerTypeBtn');
			    button1.on('click', function(){
			        if(!winPawnerType){
			            winPawnerType = new Ext.Window({
			                el:'pawnerType-serchDiv',
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
			                        winPawnerType.hide();

							    	for(i=$('clientTypes').options.length-1;i>=0;i--){
								        $('clientTypes').remove(i);
								    }
							        for(var i=0;i<gridSerchPawnerType.getRowCount();i++) {
							            if(gridSerchPawnerType.getCellValue(2,i)) {
						                	$('clientTypes').options[$('clientTypes').options.length] = new Option(gridSerchPawnerType.getCellValue(1,i),gridSerchPawnerType.getCellValue(3,i));
							            }
							        }
			                   	}
			                }]
			            });
			        }
			        winPawnerType.show(this);
			        getGridData();
			    });
			 });
		</script>

    	<!-- View record -->
    	<logic:equal name="pawner" property="action" value="view">
    		<br/>
			<html:form action="pawnerService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>


			</html:form>
    	</logic:equal>


  	</body>
</html:html>
