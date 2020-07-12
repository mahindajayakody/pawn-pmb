<%@ page language="java" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% try { %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><head>
		
<html:html>
		<link rel="StyleSheet" type="text/css" href="<html:rewrite forward="commonCSS"/>"></link>
	  	<link rel="StyleSheet" type="text/css" href="<html:rewrite forward="awCSS"/>"></link>
		<link rel="StyleSheet" type="text/css" href="<html:rewrite forward="calendarCSS"/>"></link>
		<link rel="StyleSheet" type="text/css" href="css/com-all.css"></link>
		<script type="text/javascript" src="<html:rewrite forward="commonJS"/>"></script>
		<script type="text/javascript" src="<html:rewrite forward="ajaxJS"/>"></script>
		<script type="text/javascript" src="<html:rewrite forward="awJS"/>"></script>
		<script type="text/javascript" src="<html:rewrite forward="calendarJS"/>"></script>	
		<script type="text/javascript" src="js/com-all.js"></script>


		<script type="text/javascript">
			var action = 1;

			var url = 'auditTrail.do';
			
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
			function getGridData(rurl,gridobj){

				if(rurl == null) {
					data = "dispatch=getAjaxData&userLogId=" + $('officerId').value + "&fromDate=" + $('fromDate').value + "&toDate=" + $('toDate').value + getAuditTrailTypes();
					//data = "dispatch=getAjaxData&userLogId=" + $('officerId').value;
					rurl = url;
					gridobj = auditTrailGrid;
				}

				else if(rurl=="officerService.do") {
					data = "dispatch=getAjaxData";
					getOfficerGridData(rurl, gridobj);
					return;
				}
				
				var mySearchRequest = new ajaxObject(rurl);
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
					if (responseStatus==200) {
						var message =  eval('(' + responseText + ')');
						setGridData(gridobj, message);
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request');
				    }
				}
				mySearchRequest.update(data,'POST');
			}

			function getOfficerGridData(rurl, gridobj){
				var mySearchRequest = new ajaxObject(rurl);
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
					if (responseStatus==200) {
						var message =  eval('(' + responseText + ')');
						if(message['error']){
							alert(responseStatus + ' -- Error Processing Request');
						}else{
							var swapedMessage = eval('(' + responseText + ')');
							for ( var i = 0; i < message.length; i++) {
								swapedMessage[i][0] = message[i][8]; //OfficerId
								swapedMessage[i][1] = message[i][0]; //OfficerCode
								swapedMessage[i][2] = message[i][6]; //OfficerName
								swapedMessage[i][3] = message[i][3]; //BranchCode
								swapedMessage[i][4] = message[i][4]; //BranchName
							}
							setGridData(gridobj,swapedMessage);
						}
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request');
				    }
				}
				mySearchRequest.update(data,'POST');
			}

			function getAuthorizeGridData(){
				
				data = "dispatch=getAuthorizeData&conditions=activityTypeId&value=" + $('activityTypeId').value ;
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

			function clearOtherData(){
				$('recordId').value     = '';
				$('version').value      = '';
				$('officerCode').value	= '';
				$('officerId').value	= '';
				$('officerName').value	= '';
				$('fromDate').value	= '';
				$('toDate').value	= '';

				$('divOfficerCode').innerHTML = '';

				setGridData(auditTrailGrid,[]);
				auditTrailGrid.setSelectedRows([]);
				document.getElementById('auditTrailDetailGridRow').innerHTML = '';
			}
			
			function getRecordData(before, after){
				var message =  [];
				message[0] = before.split("|");
				message[1] = after.split("|");	
				
				message[0].splice(0,1);
				message[1].splice(0,1);

				var auditTrailDetailGridRow = document.getElementById('auditTrailDetailGridRow');
				var str = new AW.Formats.String;
				var cellFormat = [];
				var myColumns = []
				for (var i = 0, l = message[0].length; i < l; ++i) {
					myColumns.push('');
					cellFormat.push(str);
				}				
				var auditTrailDetailGrid = createBrowser(myColumns,'auditTrailDetailGrid',cellFormat);
				setGridData(auditTrailDetailGrid, message);
				auditTrailDetailGridRow.innerHTML = (auditTrailDetailGrid);
			}

			function getAuditTrailTypes() {
			    var elt = document.getElementById("auditTrailTypes");

			    if (elt.selectedIndex == -1 || elt.selectedIndex == 0)
			        return;

			    return "&type=" + elt.options[elt.selectedIndex].text;
			}
			
			function loadToolBar(){
			    if(CurrentPage(window.parent.frames['footer'].location.pathname)!='toolbar.jsp'){
			    	open('toolbar.jsp?evn=4/>','footer');
			    }
			}

			function clearAll(){
				clearOtherData();
			}

			function getCreateData(){
				// not usd, ready only page
			}

			function getChangedData(){
				// not usd, ready only page 
			}

			function showValidationErrors(message){
				// not usd, ready only page 
	        }
		</script>

		<style>
			#userLogGrid {height: 150px;width:360px;}
			#userLogGrid .aw-row-selector {text-align: center}
			#userLogGrid .aw-column-0 {width: 50px;}
			#userLogGrid .aw-column-1 {width: 80px;}
			#userLogGrid .aw-column-2 {width: 80px;}
			#userLogGrid .aw-column-3 {width: 400px;}
			#userLogGrid .aw-column-4 {width: 400px;}
			#userLogGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#userLogGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}

			#eventLogGrid {height: 150px;width:360px;}
			#eventLogGrid .aw-row-selector {text-align: center}
			#eventLogGrid .aw-column-0 {width: 50px;}
			#eventLogGrid .aw-column-1 {width: 80px;}
			#eventLogGrid .aw-column-2 {width: 80px;}
			#eventLogGrid .aw-column-3 {width: 400px;}
			#eventLogGrid .aw-column-4 {width: 400px;}
			#eventLogGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#eventLogGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
						
			#auditTrailGrid {height: 150px;width:360px;}
			#auditTrailGrid .aw-row-selector {text-align: center}
			#auditTrailGrid .aw-column-0 {width: 50px;}
			#auditTrailGrid .aw-column-1 {width: 80px;}
			#auditTrailGrid .aw-column-2 {width: 80px;}
			#auditTrailGrid .aw-column-3 {width: 400px;}
			#auditTrailGrid .aw-column-4 {width: 400px;}
			#auditTrailGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#auditTrailGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}

			#auditTrailDetailGrid {height: 80px;width:720px;}
			#auditTrailDetailGrid .aw-row-selector {text-align: center}
			#auditTrailDetailGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#auditTrailDetailGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}

			#officerGrid {height: 210px;width:580px;}
			#officerGrid .aw-row-selector {text-align: center}
			#officerGrid .aw-column-0 {width: 50px;}
			#officerGrid .aw-column-1 {width: 80px;}
			#officerGrid .aw-column-2 {width: 200px;}
			#officerGrid .aw-column-3 {width: 50px;}
			#officerGrid .aw-column-3 {width: 100px;}
			#officerGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#officerGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
		</style>
  	</head>

  	<body onLoad="loadToolBar()">
    	<div id="screenCont" class="enableAll" align="center" ></div>

		<div id="officer-searchDiv" class="x-hidden">
        	<div class="x-window-header">Search Officer</div>
        	<div id="serch-tab1">
            	<div class="x-tab" title="Search">
					<table style="width: 600px">							
						<tr>
							<td>
								<script>
									var myColumns = ["<bean:message bundle="lable" key="screen.officer"/>",
									                 "<bean:message bundle="lable" key="screen.code"/>",
									                 "<bean:message bundle="lable" key="screen.officername"/>",
									                 "<bean:message bundle="lable" key="screen.branchcode"/>",
									                 "<bean:message bundle="lable" key="screen.branchname"/>"];
		            				var str = new AW.Formats.String; 
		            				var cellFormat = [str,str,str,str,str];
	                    			var gridSearchOfficer= createBrowser(myColumns,'officerGrid',cellFormat);							                    			 			                    			
	                    			gridSearchOfficer.setHeaderHeight(25);
	                                document.write(gridSearchOfficer);
	                                gridSearchOfficer.onRowDoubleClicked = function(event, row){
										try{												
											$('officerId').value 	= this.getCellText(0,row);
											$('officerCode').value	= this.getCellText(1,row);
											$('officerName').value 	= this.getCellText(2,row);
											$('hidDiv').className='hideSearchPopup';
											//getGridData();
											winOfficer.hide();
										}catch(error){}
									};
									gridSearchOfficer.onSelectedRowsChanged=function(row){
										try{
											$('officerId').value 	= this.getCellText(0,row);
											$('officerCode').value	= this.getCellText(1,row);
											$('officerName').value 	= this.getCellText(2,row);
										}catch(error){}
									}
								</script>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>

		<div id="hidDiv" class="hideSearchPopup"> </div>

    	<!-- Create record -->
    	<logic:equal name="auditTrail" property="action" value="create">
			<jsp:include page="AuditTrailSub.jsp">
				<jsp:param name="action" value="create" />	
			</jsp:include>
   	    </logic:equal>

    	<!-- View record -->
    	<logic:equal name="auditTrail" property="action" value="authorize">
  			<jsp:include page="AuditTrailSub.jsp">
				<jsp:param name="action" value="authorize" />	
			</jsp:include>
   		</logic:equal>

    	<script>
			var winOfficer;
			Ext.onReady(function(){			    
			    var button1 = Ext.get('ButtonOfficerCodeSearch');
			    button1.on('click', function(){
			        if(!winOfficer){
			            winOfficer = new Ext.Window({
			                el:'officer-searchDiv',
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
			                   		//$('hidDiv').className='hideSearchPopup';
			                    	winOfficer.hide();
			                   	}
			                }]
			            });
			        }
			        winOfficer.show(this);
			        clearOtherData();
			        getGridData("officerService.do", gridSearchOfficer);
			    });
			});
		</script>
  	</body>
</html:html>
<% }catch(Exception e) { System.out.println(e.getMessage()); } %>