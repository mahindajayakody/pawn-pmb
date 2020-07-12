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

			var url = 'ticketService.do';

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

			function getSerchData(gridObj,urlRef,rData){
				data = "dispatch=getAjaxData" + rData;
				var mySearchRequest = new ajaxObject(urlRef);
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
					if (responseStatus==200) {
						var message =  eval('(' + responseText + ')');
						if(message['error']){
							alert(message['error']);
						}else{
							setGridData(gridObj,message);
						}
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request');
				    }
				}
				mySearchRequest.update(data,'POST');
			}

			function clearAll(){
				$('pawnerId').value		   		= '';
				$('pawnerCode').value		  	= '';
				$('pawnerName').value		   	= '';
				$('productId').value		   	= '';
				$('productCode').value		   	= '';
				$('productDescription').value	= '';
				$('ticketNumber').value			= '';

				$('divPawnerCode').innerHTML     = '';
		        $('divProductCode').innerHTML    = '';
		        $('divSchemeCode').innerHTML     = '';
		        $('divTotPawnAdvance').innerHTML = '';
		        $('divRemark').innerHTML 		 = '';

				clearItemData();
				clearTotalValues();
				clearSchemeData();
				setGridData(grid,[]);
				itemArray = new Array();
				$('Check').disabled = false;
				$('AddTO').disabled = false;
			}

			function getRecordData(){};

			function clearProductData(){
				clearSchemeData();
				clearTotalValues();
				clearItemData();
				setGridData(grid,[]);
				itemArray = new Array();
			}

			function clearSchemeData(){
				$('schemeId').value		   		= '';
				$('schemeCode').value		   	= '';
				$('schemeDescription').value	= '';
				$('period').value		   		= '';
				$('interestRateId').value		= '0';
				$('interestRate').value		   	= '';
			}

			function clearTotalValues(){
				$('totNoOfItems').value		   	= '';
				$('totWeight').value		   	= '';
				$('totGoldValue').value		   	= '0.00';
				$('totMrtValue').value		   	= '0.00';
				$('totPawnAdvance').value		= '0.00';
				$('totActualDisb').value		= '0.00';
				$('remark').value				= '';
			}

			function clearItemData(){
				$('articleModId').value		   	= '0';
				$('articleModCode').value		= '';
				$('articleModDescription').value= '';
				$('articleId').value		   	= '0';
				$('articleCode').value		   	= '';
				$('articleDescription').value	= '';
				//$('cartageMasterId').value		= '';
				$('cartageId').value		   	= '0';
				$('cartageCode').value		   	= '';
				$('cartageDescription').value	= '';
				$('grossWeight').value		   	= '';
				$('nonWeight').value		   	= '';
				$('weight').value		   		= '';
				$('marketValue').value		   	= '0.00';
				$('goldValue').value		   	= '0.00';
				$('noOfItems').value		   	= '';
				$('defaultDisburs').value		= '0.00';
				$('disbursement').value		   	= '0.00';
			}

			function getSchemeReletedData(){
				if($('schemeId').value>0){
					data = "dispatch=getSchemeData&conditions=&schemeId=" + $('schemeId').value + "&isActive=1" ;
					var mySearchRequest = new ajaxObject(url);
					mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
						if (responseStatus==200) {
							var message =  eval('(' + responseText + ')');
							if(message['error']){
								alert(message['error']);
							}else{
								$('period').value		 	= message['period'];
								$('interestRate').value 	= message['intCode'];
								$('interestRateId').value 	= message['intId'];
								$('cartageMasterId').value  = message['masterId'];
							}
						}else {
				    	    alert(responseStatus + ' -- Error Processing Request');
					    }
					}
					mySearchRequest.update(data,'POST');
				}else{
					$('period').value		 	= '';
					$('interestRate').value 	= '';
					$('interestRateId').value 	= '0';
					clearItemData();
					clearTotalValues();
					setGridData(grid,[]);
					itemArray = new Array();
				}
			}

			function getCartageReletedData(){
				if($('cartageId').value>0){
					data = "dispatch=getCartageData&cartageId=" + $('cartageId').value;
					var mySearchRequest = new ajaxObject(url);
					mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
						if (responseStatus==200) {
							var message =  eval('(' + responseText + ')');
							if(message['error']){
								alert(message['error']);
							}else{
								$('cartageMktValue').value 	 = message['marcketValue'];
								$('hidDefaultDisburs').value = message['disburseValue'];//formatNumber(parseFloat(message['disburseValue']).toFixed(2));
								$('defaultDisburs').value 	 = '0.00';
								calculateByWeight();
								//formatNumber(parseFloat(message['disburseValue']).toFixed(2));
							}
						}else {
				    	    alert(responseStatus + ' -- Error Processing Request');
					    }
					}
					mySearchRequest.update(data,'POST');
				}else{
					$('cartageMktValue').value 	= '';
					$('defaultDisburs').value 	= '0.00';
					calculateByWeight();
				}

			}

			function addItemToGrid(){
				var articleDescription 	= $('articleDescription').value;
				var weight             	= $('weight').value;
				var disbursement	   	= $('disbursement').value;
				var noOfItems			= $('noOfItems').value;
				var articleId			= $('articleId').value;
				var articleModId		= $('articleModId').value;
				var marketValue			= $('marketValue').value;
				//var disbursement		= $('disbursement').value;
				//var weight				= $('weight').value;
				var grossWeight			= $('grossWeight').value;
				var goldValue			= $('goldValue').value;
				var cartageId			= $('cartageId').value;

				if(articleDescription!='' && weight!='' && disbursement!='0.00' && noOfItems>0 &&
				   articleId>0 && marketValue!='0.00' && goldValue!='0.00'){

					if(itemArray.length < 5){
						var tempArray = new Array();
						tempArray[0] = articleDescription;
						tempArray[1] = weight;
						tempArray[2] = disbursement;
						tempArray[3] = noOfItems;
						tempArray[4] = articleId;
						tempArray[5] = articleModId;
						tempArray[6] = marketValue;
						tempArray[7] = grossWeight;
						tempArray[8] = goldValue;
						tempArray[9] = cartageId;

						itemArray[itemArray.length] = tempArray;
						setGridData(grid,itemArray);

						setTotalValue();
						clearItemData();
					}else{
						alert('Exceeded maximum articles(5) per Ticket.');
					}
				}else{
					alert('Please fill the required fileds.');
				}
			}

			function calculateByWeight(){
				if(($('grossWeight').value*1 - $('nonWeight').value*1)>0){
					$('weight').value = parseFloat(formatNumber(parseFloat($('grossWeight').value*1 - $('nonWeight').value*1).toFixed(2)));
					if($('cartageId').value>0){
						$('goldValue').value 		= formatNumber(parseFloat($('cartageMktValue').value * ($('weight').value/8)).toFixed(2));
						$('defaultDisburs').value	= formatNumber(parseFloat($('hidDefaultDisburs').value * ($('weight').value/8)).toFixed(2));
						$('marketValue').value 		= $('goldValue').value;
					}else{
						$('goldValue').value    = '0.00';
						$('marketValue').value  = '0.00';
					}
				}else{
					$('nonWeight').value      = '';
					$('defaultDisburs').value = '0.00'
					$('goldValue').value      = '0.00';
					$('marketValue').value    = '0.00';
				}
			}

			function setTotalValue(){
				var totNoOfItems 	= 0;
				var totWeight		= 0;
				var totGoldValue	= 0;
				var totMrtValue		= 0;
				var totActualDisb	= 0;
				var grossTotWeight  = 0;

				//alert(itemArray.toSource());

				for(var i=0;i<itemArray.length;i++){
					totWeight 		+= itemArray[i][1]*1;
					totActualDisb	+= unformatNumber(itemArray[i][2])*1;
					totNoOfItems	+= itemArray[i][3]*1;
					totGoldValue	+= unformatNumber(itemArray[i][8])*1;
					totMrtValue		+= unformatNumber(itemArray[i][6])*1;
					grossTotWeight  += itemArray[i][7]*1;
				}

				if(itemArray.length>0){
					$('totNoOfItems').value		   	= totNoOfItems;
					$('totWeight').value		   	= totWeight;
					$('totGoldValue').value		   	= formatNumber(parseFloat(totGoldValue).toFixed(2));
					$('totMrtValue').value		   	= formatNumber(parseFloat(totMrtValue).toFixed(2));
					$('totActualDisb').value		= formatNumber(parseFloat(totActualDisb).toFixed(2));
					$('totPawnAdvance').value		= $('totActualDisb').value;
					$('grossTotWeight').value		= grossTotWeight;
				}else{
					$('totNoOfItems').value		   	= '';
					$('totWeight').value		   	= '';
					$('totGoldValue').value		   	= '0.00';
					$('totMrtValue').value		   	= '0.00';
					$('totActualDisb').value		= '0.00';
					$('totPawnAdvance').value		= '0.00';
					$('grossTotWeight').value		= 0;
				}
			}

			function getGridCreateData(){
				//str formate {articleId<@>articleModId<@>marketValue<@>disbursement<@>weight<@>grossWeight<@>ArticleDescription<@>noOfItem<@>cartageId}
				var str = '';

				if(itemArray.length>0){
					str = 	itemArray[0][4] + '<@>' + itemArray[0][5] + '<@>' + unformatNumber(itemArray[0][6]) + '<@>' +
							unformatNumber(itemArray[0][2]) + '<@>' + itemArray[0][1] + '<@>' + itemArray[0][7] + '<@>' +
							itemArray[0][0] + '<@>' + itemArray[0][3] + '<@>' +itemArray[0][9];

					for(var i=1;i<itemArray.length;i++){
						str += 	'<row>' + itemArray[i][4] + '<@>' + itemArray[i][5] + '<@>' + unformatNumber(itemArray[i][6]) + '<@>' +
								unformatNumber(itemArray[i][2]) + '<@>' + itemArray[i][1] + '<@>' + itemArray[i][7] + '<@>' +
								itemArray[i][0] + '<@>' + itemArray[i][3] + '<@>' + itemArray[i][9];
					}
				}

				return str;
			}

			function getCreateData(){
				var pawnerId   		= $('pawnerId').value	;
				var pawnerCode 		= $('pawnerCode').value ;
				var productId  		= $('productId').value ;
				var productCode 	= $('productCode').value ;
				var schemeId  		= $('schemeId').value	;
				var schemeCode 		= $('schemeCode').value ;
				var period			= $('period').value ;
				var interestRateId	= $('interestRateId').value ;
				//var articleModId	= $('articleModId').value	;
				//var articleModCode	= $('articleModCode').value ;
				//var articleId		= $('articleId').value ;
				//var articleCode		= $('articleCode').value ;
				//var cartageMasterId	= $('cartageMasterId').value ;
				//var cartageId		= $('cartageId').value ;
				//var cartageCode		= $('cartageCode').value ;
				var totNoOfItems	= $('totNoOfItems').value	;
				var totWeight		= $('totWeight').value ;
				var totGoldValue	= unformatNumber($('totGoldValue').value )*1;
				var totMrtValue		= unformatNumber($('totMrtValue').value )*1;
				var totPawnAdvance	= unformatNumber($('totPawnAdvance').value)*1 ;
				var totActualDisb	= unformatNumber($('totActualDisb').value)*1 ;
				var remark			= $('remark').value;
				var grossTotWeight  = $('grossTotWeight').value;

				return 	"&pawnerId=" + pawnerId + "&pawnerCode=" + pawnerCode +
					   	"&productId=" + productId + "&productCode=" + productCode +
					   	"&schemeId=" + schemeId + "&schemeCode=" + schemeCode +
					   	"&period=" + period + "&interestRateId=" + interestRateId +
					   	//"&articleModId=" + articleModId + "&articleModCode=" + articleModCode +
					   	//"&articleId=" + articleId + "&articleCode=" + articleCode +
					   	//"&cartageMasterId=" + cartageMasterId + "&cartageId=" + cartageId +
					   	//"&cartageCode=" + cartageCode +
					   	"&totNoOfItems=" + totNoOfItems +
					   	"&totWeight=" + totWeight + "&totGoldValue=" + totGoldValue +
					   	"&totMrtValue=" + totMrtValue + "&totPawnAdvance=" + totPawnAdvance +
					   	"&totActualDisb=" + totActualDisb + "&itemData=" + getGridCreateData() +
					   	"&remark=" + remark + "&grossTotWeight=" + grossTotWeight;
			}

			function submitData(){
				data = "dispatch=create" + getCreateData();

				var mySearchRequest = new ajaxObject(url);
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
					if (responseStatus==200) {
						var message =  eval('(' + responseText + ')');
						if(message['error']){
			       			alert(message['error']);
			       		}else if(message['success']){
			       			alert(message['success']);
			       			$('ticketNumber').value = message['ticketNumber'];
			       			$('Check').disabled = true;
			       			$('AddTO').disabled = true;
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
			}

	        function prosessForm(){
		    	var processButton = ''
		    	processButton = $('Check');
				processButton.disabled=true;
				processButton.id = 'Processing';
				processButton.value = '<bean:message bundle="button" key="button.processsing"/>';
			}

			function resetProcessBtn(){
				processButton = $('Processing');
		       	processButton.disabled=false;
		    	processButton.id = 'Check';
		    	processButton.value = '<bean:message bundle="button" key="button.submit"/>' ;
		    }

		    function confirms(){
		    	if(itemArray.length>0){
			    	$('screenCont').className = 'disableAll';
			    	if(confirmCommonSubmit('',1)){
				    	prosessForm();
				    	submitData();
				    	resetProcessBtn();
				    }
				}else{
					alert('No Article found! ');
				}
		    }

		    function showValidationErrors(message){
	       		for( var i =0; i < message.length ; i++){
	                if( message[i]['pawnerCode'] )
	                    $('divPawnerCode').innerHTML = message[i]['pawnerCode'];
	                else if( message[i]['productCode'] )
	                    $('divProductCode').innerHTML = message[i]['productCode'];
	                else if( message[i]['schemeCode'] )
	                    $('divSchemeCode').innerHTML = message[i]['schemeCode'];
	                else if( message[i]['totPawnAdvance'] )
	                    $('divTotPawnAdvance').innerHTML = message[i]['totPawnAdvance'];
	            }
	        }
		</script>

		<style>
			#firstGrid {height: 100px;width:720px;}
			#firstGrid .aw-row-selector {text-align: center}
			#firstGrid .aw-column-0 {width: 350px;}
			#firstGrid .aw-column-1 {width: 100px;}
			#firstGrid .aw-column-2 {width: 100px;text-align: right;}
			#firstGrid .aw-column-3 {width: 100px;text-align: right;}
			#firstGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#firstGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
			#firstGrid .aw-image-delete {background:url(images/delete.gif) 1px 1px no-repeat}

			#popupGrid {height: 210px;width:600px;}
			#popupGrid .aw-row-selector {text-align: center}
			#popupGrid .aw-column-0 {width: 100px;}
			#popupGrid .aw-column-1 {width: 455px;}
			#popupGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#popupGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}

			#popupGrid1 {height: 210px;width:600px;}
			#popupGrid1 .aw-row-selector {text-align: center}
			#popupGrid1 .aw-column-0 {width: 100px;}
			#popupGrid1 .aw-column-1 {width: 455px;}
			#popupGrid1 .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#popupGrid1 .aw-grid-row {border-bottom: 1px solid threedlightshadow;}

			#popupGrid2 {height: 210px;width:600px;}
			#popupGrid2 .aw-row-selector {text-align: center}
			#popupGrid2 .aw-column-0 {width: 100px;}
			#popupGrid2 .aw-column-1 {width: 455px;}
			#popupGrid2 .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#popupGrid2 .aw-grid-row {border-bottom: 1px solid threedlightshadow;}

			#popupGrid3 {height: 210px;width:600px;}
			#popupGrid3 .aw-row-selector {text-align: center}
			#popupGrid3 .aw-column-0 {width: 100px;}
			#popupGrid3 .aw-column-1 {width: 455px;}
			#popupGrid3 .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#popupGrid3 .aw-grid-row {border-bottom: 1px solid threedlightshadow;}

			#popupGrid4 {height: 210px;width:600px;}
			#popupGrid4 .aw-row-selector {text-align: center}
			#popupGrid4 .aw-column-0 {width: 100px;}
			#popupGrid4 .aw-column-1 {width: 455px;}
			#popupGrid4 .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#popupGrid4 .aw-grid-row {border-bottom: 1px solid threedlightshadow;}

			#popupGrid5 {height: 210px;width:600px;}
			#popupGrid5 .aw-row-selector {text-align: center}
			#popupGrid5 .aw-column-0 {width: 120px;}
			#popupGrid5 .aw-column-0 {width: 120px;}
			#popupGrid5 .aw-column-0 {width: 120px;}
			#popupGrid5 .aw-column-1 {width: 120px;}
			#popupGrid5 .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#popupGrid5 .aw-grid-row {border-bottom: 1px solid threedlightshadow;}

			#popupGrid6 {height: 210px;width:700;}
			#popupGrid6 .aw-row-selector {text-align: center}
			#popupGrid6 .aw-column-0 {width: 100px;}
			#popupGrid6 .aw-column-1 {width: 120px;}
			#popupGrid6 .aw-column-2 {width: 120px;}
			#popupGrid6 .aw-column-3 {width: 120px;}
			#popupGrid6 .aw-column-4 {width: 120px;}
			#popupGrid6 .aw-column-5 {width: 120px;}
			#popupGrid6 .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#popupGrid6 .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
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
    	<input type="hidden" id="userType" name="userType" value="PWN"/>
    	<!-- Create record -->
    	<logic:equal name="ticket" property="action" value="create">
			<html:form action="ticketService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>

				<table border="0">
		          	<tr>
						<td>
							<table class="InputTable" border="0">
								<tr height="2px"></tr>
								<tr border="0">
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.ticketnumber"/>&nbsp;
									</td>
									<td>
										<input id="ticketNumber" name="ticketNumber" size="17" class="READONLYINPUT" readonly="readonly"/>
									</td>
								</tr>

								<tr border="0">
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.clientname"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="pawnerId" name="pawnerId">
										<input id="pawnerCode" name="pawnerCode" style="width: 90px" maxlength="8"  tabindex="1"
											onfocus="clearDivision('divPawnerCode')"
											onblur="commonSearch('pawnerService.do','pawnerId','pawnerCode','pawnerName','getPawner','divPawnerCode')"/>
										<input id="ButtonPawnerSerch" type="button" value="..." />
										<input id="pawnerName" name="pawnerName" size="60" class="READONLYINPUT" readonly="readonly"/>
										<font color="red">*</font> <input id="clientExposureBtn" type="button" value="..." /><br/>
										<div id="divPawnerCode" class="validate"/>
									</td>
								</tr>

								<tr border="0">
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.product"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="productId" name="productId" value="0">
										<input id="productCode" name="productCode" style="width: 50px" maxlength="3" tabindex="2"
											onfocus="clearDivision('divProductCode')"
											onblur="upperCase('productCode');commonSearch('ticketService.do','productId','productCode','productDescription','getProduct','divProductCode',function(){},'',function(){clearProductData();})"/>
										<input id="ButtonProductSerch" type="button" value="..." />
										<input id="productDescription" name="schemeDescription" size="60" class="READONLYINPUT" readonly="readonly"/>
										<font color="red">*</font><br/>
										<div id="divProductCode" class="validate"/>
									</td>
								</tr>

								<tr border="0">
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.scheme"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="schemeId" name="schemeId" value="0">
										<input id="schemeCode" name="schemeCode" style="width: 50px" maxlength="3" tabindex="3"
											onfocus="clearDivision('divSchemeCode')"
											onblur="upperCase('schemeCode');commonSearch('ticketService.do','schemeId','schemeCode','schemeDescription','getScheme','divSchemeCode',function(){getSchemeReletedData();},'&productId='+$('productId').value,function(){getSchemeReletedData();})"/>
										<input id="ButtonSchemeSerch" type="button" value="..." />
										<input id="schemeDescription" name="schemeDescription" size="60" class="READONLYINPUT" readonly="readonly"/>
										<font color="red">*</font><br/>
										<div id="divSchemeCode" class="validate"/>
									</td>
								</tr>

								<tr border="0">
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.period"/>&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%" border="0">
											<tr border="0">
												<td width="20%">
													<input type="text" id="period" name="period" value="" size="10" maxlength="5" style="text-align: center;" class="READONLYINPUT" readonly="true" tabindex="-1"
														onfocus="clearDivision('divPeriod')"/></br>
													<div id="divPeriod" class="validate" />
												</td>
												<td>
													<table cellpadding="0" cellspacing="0">
														<tr>
															<td style="width: 295px" align="right">
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

					<tr height="2px"></tr>
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
			                    			grid.setSelectorImage("delete");
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){
			                                	if(row!='') {

			                                	}
			                                };
			                                grid.onSelectorClicked = function(event, index) {
												if(confirm("Confirm to delete the record ?")){
													var tempArr=new Array();
													for(var i=0;i<itemArray.length;i++){
														if(i!=index){
															tempArr[tempArr.length]=itemArray[i];
														}
													}
													itemArray = tempArr;
													setGridData(grid,itemArray);
													setTotalValue();
													//grid.refresh();
												}
											}
			                            </script>
			                        </td>
			                    </tr>
			                    <tr height="2px"></tr>

								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.articlemodel"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="articleModId" name="articleModId" value="0">
										<input id="articleModCode" name="articleModCode" style="width: 50px" maxlength="3" tabindex="4"
											onfocus="clearDivision('divArticleModCode')"
											onblur="upperCase('articleModCode');commonSearch('ticketService.do','articleModId','articleModCode','articleModDescription','getArticleModel','divArticleModCode',function(){},'&productId='+$('productId').value,function(){clearItemData();})"/>
										<input id="ButtonArticleModSerch" type="button" value="..." />
										<input id="articleModDescription" name="articleModDescription" size="60" class="READONLYINPUT" readonly="readonly"/>
										<font color="red">*</font><br/>
										<div id="divArticleModCode" class="validate"/>
									</td>
								</tr>

								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.article"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="articleId" name="articleId">
										<input id="articleCode" name="articleCode" style="width: 50px" maxlength="3" tabindex="5"
											onfocus="clearDivision('divPawnerCode')"
											onblur="upperCase('articleCode');commonSearch('ticketService.do','articleId','articleCode','articleDescription','getArticle','divArticleCode',function(){},'&articleModId='+$('articleModId').value,function(){})"/>
										<input id="ButtonArticleSerch" type="button" value="..." />
										<input id="articleDescription" name="articleDescription" size="60" class="READONLYINPUT" readonly="readonly"/>
										<font color="red">*</font><br/>
										<div id="divArticleCode" class="validate"/>
									</td>
								</tr>

								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.cartage"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="cartageMasterId" name="cartageMasterId" value="0">
										<input type="hidden" id="cartageId" name="cartageId">
										<html:text property="cartageCode" styleId="cartageCode" style="width: 50px" maxlength="3" tabindex="6"
											onfocus="clearDivision('divCartageCode')"
											onblur="commonSearch('ticketService.do','cartageId','cartageCode','cartageDescription','getCartage','divCartageCode',function(){getCartageReletedData();},'&cartageMasterId='+$('cartageMasterId').value,function(){getCartageReletedData();})"/>
										<input id="ButtonCartageSerch" type="button" value="..." />
										<input id="cartageDescription" name="cartageDescription" size="60" class="READONLYINPUT" readonly="readonly"/>
										<font color="red">*</font><br/>
										<div id="divCartageCode" class="validate"/>
									</td>
								</tr>

								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.weight"/>&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td width="10%">
													<input type="text" id="grossWeight" name="grossWeight" size="10" tabindex="7"
														onblur="if(this.value*1 > 0){$('weight').value=this.value;}calculateByWeight();"/>
												</td>
												<td width="5%" align="center">
													-
												</td>
												<td width="10%">
													<input type="text" id="nonWeight" name="nonWeight" size="10" tabindex="8"
														onblur="calculateByWeight()"/>
												</td>
												<td width="5%" align="center">
													=
												</td>
												<td>
													<input type="text" id="weight" name="weight" size="10" maxlength="7" tabindex="-1" readonly="readonly" class="READONLYINPUT"
														onfocus="clearDivision('divWeight')"/>
													<font color="red">*</font><br/>
													<div id="divWeight" class="validate" />
												</td>
											</tr>
										</table>
									</td>
								</tr>

								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.noofitems"/>&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td width="20%">
													<input type="text" id="noOfItems" name="noOfItems" size="10" maxlength="5" style="text-align: center" tabindex="9"
														onfocus="clearDivision('divNoOfItems')"/>
													<font color="red">*</font><br/>
													<div id="divNoOfItems" class="validate"/>
												</td>
												<td>
													<table cellpadding="0" cellspacing="0">
														<tr>
															<td style="width: 210px" align="right">
																<bean:message bundle="lable" key="screen.goldvalue"/>&nbsp;
															</td>
															<td>
																<input type="hidden" id="cartageMktValue" name="cartageMktValue">
																<input type="text" id="goldValue" name="goldValue" value="0.00" size="19" style="text-align: right;" class="READONLYINPUT" readonly="readonly"
																	onfocus="clearDivision('divGoldValue')"/><br/>
																<div id="divGoldValue" class="validate" />
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
										<bean:message bundle="lable" key="screen.articlemarketvalue"/>&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td width="24%">
													<input type="text" id="marketValue" name="marketValue" value="0.00" size="19" maxlength="15" style="text-align: right;" tabindex="10" class="READONLYINPUT" readonly="readonly"
														onfocus="clearDivision('divGoldValue');this.maxLength=15;"
														onkeyup="this.value=formatNumber(unformatNumber(this.value));"
														onblur="if(this.value.length=15){this.maxLength=18;};if(this.value.length<=1 && (this.value.substring(0,1)=='-' || this.value=='' || this.value=='.')){this.value='0.00'};this.value=formatNumber(parseFloat(unformatNumber(this.value)).toFixed(2))"/>
														<font color="red">*</font><br/>
													<div id="divMarketValue" class="validate" />
												</td>
												<td>
													<table cellpadding="0" cellspacing="0">
														<tr>
															<td style="width: 186px" align="right">
																<bean:message bundle="lable" key="screen.defaultdisbursement"/>&nbsp;
															</td>
															<td>
																<input type="hidden" name="hidDefaultDisburs" id="hidDefaultDisburs" value="0.00">
																<input type="text" id="defaultDisburs" name="defaultDisburs" value="0.00" size="19" style="text-align: right;" class="READONLYINPUT" readonly="readonly"
																	onfocus="clearDivision('divDefaultDisburs')"/><br/>
																<div id="divDefaultDisburs" class="validate"/>
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
										<bean:message bundle="lable" key="screen.disbursement"/>&nbsp;
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td width="25%">
													<html:text property="disbursement" styleId="disbursement" value="0.00" size="23" maxlength="15" style="text-align: right" tabindex="11"
														onfocus="clearDivision('divDisbursement');this.maxLength=15;"
														onkeyup="this.value=formatNumber(unformatNumber(this.value));"
														onblur="if(this.value.length=15){this.maxLength=18;};if(this.value.length<=1 && (this.value.substring(0,1)=='-' || this.value=='' || this.value=='.')){this.value='0.00'};this.value=formatNumber(parseFloat(unformatNumber(this.value)).toFixed(2));if(unformatNumber(this.value)*1>unformatNumber($('defaultDisburs').value)*1){this.value='0.00'}"/>
														<font color="red">*</font><br/>
													<div id="divDisbursement" class="validate"/>
												</td>
												<td>
													<table cellpadding="0" cellspacing="0">
														<tr>
															<td style="width: 465px" align="right">
																<input type="button" value="Add" id="AddTO" style="border:medium none;background-image: url('images/button/EN/grp_button.gif');color:#000000;font-size:11px;height:19px;vertical-align:middle;width:93px;" tabindex="12" onclick="addItemToGrid();"/>
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


					<tr>
						<td width="100%">
							<fieldset>
								<legend>Total Values</legend>

								<table style="border: 0;width: 740px;background-color: white;">
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
																	<input type="hidden" id="grossTotWeight" name="grossTotWeight" value="0">
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
														<input type="text" id="totGoldValue" name="totGoldValue" value="" size="15" maxlength="10" style="text-align: right;width: 92px" readonly="readonly" class="READONLYINPUT"
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
																	<input type="text" id="totMrtValue" name="totMrtValue" size="15" maxlength="10" style="text-align: right;width: 92px" readonly="readonly" class="READONLYINPUT"
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
														<html:text property="totPawnAdvance" styleId="totPawnAdvance" value="0.00" size="23" maxlength="15" style="text-align: right;width: 92px"
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
																	<input type="text" id="totActualDisb" name="totActualDisb" size="15" maxlength="10" style="text-align: right;width: 92px" readonly="readonly" class="READONLYINPUT"
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
											<input type="text" id="remark" name="remark" size="89" maxlength="100"
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
							<input type="button" value="<bean:message bundle="button" key="button.submit"/>" id="Check" class="buttonNormal" onclick='confirms();'/>
							<input type="button" value="<bean:message bundle="button" key="button.clear"/>" id="Clear" class="buttonNormal" onclick="clearAll();"/>
						</td>
					</tr>
				</table>
			</html:form>
			<jsp:include flush="true" page="ClientBrowser.jsp"></jsp:include>

			<!-- Beging of the popup div  -->

			<div id="product-serchDiv" class="x-hidden">
	        	<div class="x-window-header">
	            	Search Product
	            </div>
	        	<div id="serch-tab5">
	            	<div class="x-tab" title="Search">
						<table style="width: 600px">
							<tr>
								<td>
									<script>
										var myColumnsPrd = ["<bean:message bundle="lable" key="screen.code"/>","<bean:message bundle="lable" key="screen.description"/>"];
			            				var str = new AW.Formats.String;
			            				var cellFormatPrd = [str,str];
		                    			var gridSerchPrd = createBrowser(myColumnsPrd,'popupGrid4',cellFormatPrd);
		                    			gridSerchPrd.setHeaderHeight(25);
		                                document.write(gridSerchPrd);
		                                gridSerchPrd.onRowDoubleClicked = function(event, row){
											try{
												$('productId').value   = this.getCellText(3,row);
												$('productCode').value = this.getCellText(0,row);
												$('productDescription').value = this.getCellText(1,row);
					                        	winProduct.hide();
											}catch(error){}
										};
										gridSerchPrd.onSelectedRowsChanged=function(row){
											try{
												if(row!=''){
													$('productId').value   = this.getCellText(3,row);
													$('productCode').value = this.getCellText(0,row);
													$('productDescription').value = this.getCellText(1,row);
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
	            	Search Scehme
	            </div>
	        	<div id="serch-tab4">
	            	<div class="x-tab" title="Search">
						<table style="width: 600px">
							<tr>
								<td>
									<script>
										var myColumnsScheme = ["<bean:message bundle="lable" key="screen.code"/>","<bean:message bundle="lable" key="screen.description"/>"];
			            				var cellFormatScheme = [str,str];
		                    			var gridSerchScheme = createBrowser(myColumnsScheme,'popupGrid3',cellFormatScheme);
		                    			gridSerchScheme.setHeaderHeight(25);
		                                document.write(gridSerchScheme);
		                                gridSerchScheme.onRowDoubleClicked = function(event, row){
											try{
												$('schemeId').value   = this.getCellText(9,row);
												$('schemeCode').value = this.getCellText(0,row);
												$('schemeDescription').value = this.getCellText(1,row);
												winScheme.hide();
					                        	getSchemeReletedData();
											}catch(error){}
										};
										gridSerchScheme.onSelectedRowsChanged=function(row){
											try{
												if(row!=''){
													$('schemeId').value   = this.getCellText(9,row);
													$('schemeCode').value = this.getCellText(0,row);
													$('schemeDescription').value = this.getCellText(1,row);

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

			<div id="articleModel-serchDiv" class="x-hidden">
	        	<div class="x-window-header">
	            	Search ArticleModel
	            </div>
	        	<div id="serch-tab1">
	            	<div class="x-tab" title="Search">
						<table style="width: 600px">
							<tr>
								<td>
									<script>
										var myColumnsArtMod = ["<bean:message bundle="lable" key="screen.code"/>","<bean:message bundle="lable" key="screen.description"/>"];
			            				var cellFormatArtMod = [str,str];
		                    			var gridSerchArtMod = createBrowser(myColumnsArtMod,'popupGrid',cellFormatArtMod);
		                    			gridSerchArtMod.setHeaderHeight(25);
		                                document.write(gridSerchArtMod);
		                                gridSerchArtMod.onRowDoubleClicked = function(event, row){
											try{
												$('articleModId').value   = this.getCellText(5,row);
												$('articleModCode').value = this.getCellText(0,row);
												$('articleModDescription').value = this.getCellText(1,row);
					                        	winArticleModel.hide();
					                        	//getGridData();
											}catch(error){}
										};
										gridSerchArtMod.onSelectedRowsChanged=function(row){
											try{
												if(row!=''){
													$('articleModId').value   = this.getCellText(5,row);
													$('articleModCode').value = this.getCellText(0,row);
													$('articleModDescription').value = this.getCellText(1,row);
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

			<div id="article-serchDiv" class="x-hidden">
	        	<div class="x-window-header">
	            	Search Article
	            </div>
	        	<div id="serch-tab2">
	            	<div class="x-tab" title="Search">
						<table style="width: 600px">
							<tr>
								<td>
									<script>
										var myColumnsArt  = ["<bean:message bundle="lable" key="screen.code"/>","<bean:message bundle="lable" key="screen.description"/>"];
			            				var str1 = new AW.Formats.String;
			            				var cellFormatArt = [str1,str1];
		                    			var gridSerchArt  = createBrowser(myColumnsArt,'popupGrid1',cellFormatArt);
		                    			gridSerchArt.setHeaderHeight(25);
		                                document.write(gridSerchArt);
		                                gridSerchArt.onRowDoubleClicked = function(event, row){
											try{
												$('articleId').value   = this.getCellText(5,row);
												$('articleCode').value = this.getCellText(0,row);
												$('articleDescription').value = this.getCellText(1,row);
					                        	winArticle.hide();
					                        	//getGridData();
											}catch(error){}
										};
										gridSerchArt.onSelectedRowsChanged=function(row){
											try{
												if(row!=''){
													$('articleId').value   = this.getCellText(5,row);
													$('articleCode').value = this.getCellText(0,row);
													$('articleDescription').value = this.getCellText(1,row);
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

			<div id="cartage-serchDiv" class="x-hidden">
	        	<div class="x-window-header">
	            	Search Cartage
	            </div>
	        	<div id="serch-tab3">
	            	<div class="x-tab" title="Search">
						<table style="width: 600px">
							<tr>
								<td>
									<script>
										var myColumnsCart  = ["<bean:message bundle="lable" key="screen.code"/>","<bean:message bundle="lable" key="screen.description"/>"];
			            				var cellFormatCart = [str,str];
		                    			var gridSerchCart  = createBrowser(myColumnsCart,'popupGrid2',cellFormatCart);
		                    			gridSerchCart.setHeaderHeight(25);
		                                document.write(gridSerchCart);
		                                gridSerchCart.onRowDoubleClicked = function(event, row){
											try{
												$('cartageId').value   = this.getCellText(12,row);
												$('cartageCode').value = this.getCellText(0,row);
												$('cartageDescription').value = this.getCellText(1,row);
					                        	winCartage.hide();
					                        	getCartageReletedData();
											}catch(error){}
										};
										gridSerchCart.onSelectedRowsChanged=function(row){
											try{
												if(row!=''){
													$('cartageId').value   = this.getCellText(12,row);
													$('cartageCode').value = this.getCellText(0,row);
													$('cartageDescription').value = this.getCellText(1,row);
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

			<div id="clientExposureDiv" class="x-hidden">
	        	<div class="x-window-header">
	            	Client Exposure
	            </div>
	        	<div id="clientExposureTab">
	            	<div class="x-tab" title="Client Exposure">
						<table style="width: 800px">
							<tr>
								<td>
									<script>
										var clientExposureColumns = ["<bean:message bundle="lable" key="screen.branchname"/>",
															"<bean:message bundle="lable" key="screen.totaltickts"/>",
							  								"<bean:message bundle="lable" key="screen.totoaladvanced"/>",
							  								"<bean:message bundle="lable" key="screen.totaldued"/>",
							  								"<bean:message bundle="lable" key="screen.totalarras"/>",
							  								"<bean:message bundle="lable" key="screen.totoalsettled"/>"];
			            				var strForth = new AW.Formats.String;
			            				var secCellFormat = [strForth,strForth,strForth,strForth,strForth,strForth];
		                    			var clientExposureGrid = createBrowser(clientExposureColumns,'popupGrid6',secCellFormat);
		                                document.write(clientExposureGrid);
									</script>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<script>
				var winArticleModel;
				var winArticle;
				var winCartage;
				var winScheme;
				var winProduct;
				var winInterest;

				Ext.onReady(function(){

				    var button = Ext.get('ButtonArticleModSerch');
				    button.on('click', function(){
				        if(!winArticleModel){
				            winArticleModel = new Ext.Window({
				                el:'articleModel-serchDiv',
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
				                        winArticleModel.hide();
				                   	}
				                }]
				            });
				        }

				        winArticleModel.show(this);
				        var data = "&conditions=productId&value=" + $('productId').value;
				        getSerchData(gridSerchArtMod,'articleModelService.do',data);
				    });

				    var button1 = Ext.get('ButtonArticleSerch');
				    button1.on('click', function(){
				        if(!winArticle){
				            winArticle = new Ext.Window({
				                el:'article-serchDiv',
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
				                        winArticle.hide();
				                        //if($('articleModelId').value!='0' && $('articleModelId').value!='')getGridData();
				                   	}
				                }]
				            });
				        }
				        winArticle.show(this);
				        var data = "&conditions=articleModelId&value=" + $('articleModId').value;
						getSerchData(gridSerchArt,'articleService.do',data);
				    });


				 	var button2 = Ext.get('ButtonCartageSerch');
				    button2.on('click', function(){
				        if(!winCartage){
				            winCartage = new Ext.Window({
				                el:'cartage-serchDiv',
				                layout:'fit',
				                width:600,
				                height:300,
				                closable:false,
				                closeAction:'hide',
				                plain: true,

				                items: new Ext.TabPanel({
				                    el: 'serch-tab3',
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
				                        winCartage.hide();
				                        getCartageReletedData();
				                        //if($('articleModelId').value!='0' && $('articleModelId').value!='')getGridData();
				                   	}
				                }]
				            });
				        }
				        winCartage.show(this);
				        /* data = "&conditions=masterCartageId&value=" + $('cartageMasterId').value; */
				        data = "&conditions=productId<next>masterCartageId&value=" + $('productId').value + "<next>" + $('cartageMasterId').value;
				        getSerchData(gridSerchCart,'cartageService.do',data);
				    });

				    var button3 = Ext.get('ButtonSchemeSerch');
				    button3.on('click', function(){
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
				                        winScheme.hide();
				                        getSchemeReletedData();
				                        //if($('articleModelId').value!='0' && $('articleModelId').value!='')getGridData();
				                   	}
				                }]
				            });
				        }
				        winScheme.show(this);
				        data = "&conditions=productId<next>isActive&value=" + $('productId').value + "<next>1";
				        getSerchData(gridSerchScheme,'schemeService.do',data);
				    });

				    var button4 = Ext.get('ButtonProductSerch');
				    button4.on('click', function(){
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
				                    el: 'serch-tab5',
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
				                        //if($('articleModelId').value!='0' && $('articleModelId').value!='')getGridData();
				                   	}
				                }]
				            });
				        }
				        winProduct.show(this);
				        getSerchData(gridSerchPrd,'productService.do','');
				    });

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
						var mySearchRequest = new ajaxObject('interestRatesService.do');
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
						mySearchRequest.update(data,'POST');
				    });
				   	
				   	var clientExposureWin;
				   	var clientExposureButton = Ext.get('clientExposureBtn');
				    clientExposureButton.on('click', function(){
						if($('pawnerId').value == null || $('pawnerId').value.length ==0){
							alert("Please insert or search for a pawner before this action");
							return;
						}
				        if(!clientExposureWin){
				            clientExposureWin = new Ext.Window({
				                el:'clientExposureDiv',
				                layout:'fit',
				                width:700,
				                height:300,
				                closable:false,
				                closeAction:'hide',
				                plain: true,

				                items: new Ext.TabPanel({
				                    el: 'clientExposureTab',
				                    autoTabs:true,
				                    activeTab:0,
				                    deferredRender:false,
				                    border:false
				                }),

				                buttons: [{
				                    text: 'Ok',
				                    handler: function(){
				                        clientExposureWin.hide();
				                   	}
				                }]
				            });
				        }
				        clientExposureWin.show(this);
				        data = "dispatch=getClientExposure&pawnerId="+$('pawnerId').value;
						var mySearchRequest = new ajaxObject('ticketService.do');
						mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
							if (responseStatus==200) {
								var message =  eval('(' + responseText + ')');
								if(message['error']){
									alert(message['error']);
								}else{
									setGridData(clientExposureGrid,message);
								}
							}else {
					    	    alert(responseStatus + ' -- Error Processing Request');
						    }
						}
						mySearchRequest.update(data,'POST');
				    });
				});
			</script>
    	</logic:equal>
    </body>
</html:html>
