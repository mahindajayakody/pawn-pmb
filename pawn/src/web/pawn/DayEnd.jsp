<%@ taglib uri="http://struts.apache.org/tags-bean"  prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<html:html>
	<head>
		<title>DayEnd</title>
		<link rel="StyleSheet" type="text/css" href="<html:rewrite forward="commonCSS"/>"></link>
		<link rel="StyleSheet" type="text/css" href="<html:rewrite forward="awCSS"/>"></link>
		<link rel="StyleSheet" type="text/css" href="css/com-all.css"></link>
		<script type="text/javascript" src="<html:rewrite forward="commonJS"/>"></script>
		<script type="text/javascript" src="<html:rewrite forward="ajaxJS"/>"></script>
		<script type="text/javascript" src="<html:rewrite forward="awJS"/>"></script>
		<script type="text/javascript" src="js/com-all.js"></script>
		
		<script type="text/javascript">
			window.parent.document.getElementById('footer').style.display="none";			
			window.parent.document.getElementById("mainbody").height="600px";
			window.parent.document.getElementById("footer").height="0px";
		
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
			
			function createBranchGrid(myColumns,gridName,dataFormat){
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
				
				obj.setCellTemplate(new AW.Templates.Checkbox, 3); 

			    //set initial value for column 2 
			    //obj.setCellValue(false, 2); 
			    //obj.setCellValue(function(col,row){return this.getCellText(3, row)}, 2); 
			    obj.setSelectorVisible(true);
			
			    obj.getCheckedValue=function() { 
			        for(var i=0;i<obj.getRowCount();i++) { 
			            if(obj.getCellValue(3,i)) { 
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
			
			function getInitialData(){
				data = "dispatch=getInitialData";
				var mySearchRequest = new ajaxObject('dayEndService.do');
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
					if (responseStatus==200) {
							var gridData  = eval('(' + responseText.split("<@>")[0] + ')');						
							var otherData = eval('(' + responseText.split("<@>")[1] + ')');
							
							setGridData(branch,gridData);	
							$('loginBranchId').value 	= otherData['branchId'];				
							$('loginBranchCode').value 	= otherData['branchCode'];
							$('loginBranchName').value 	= otherData['branchName'];
							$('systemDate').value 		= otherData['systemDate'];
							$('startDate').value 		= otherData['systemDate'];
							$('processingDate').value 	= otherData['processingDate'];
							$('endDate').value 			= otherData['endDate'];							
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request'); 
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
		    	$('screenCont').className = 'enableAll';		    	
		    }
			
			function submitForm(){
				$('screenCont').className = 'disableAll';
				if(confirmCommonSubmit()){
					prosessForm();
					var str = '';
					var systemDate = $('systemDate').value;
					var okToProcess = 'Yes';

					for(var i=0;i<branch.getRowCount();i++) { 
						if(branch.getCellValue(3,i) && branch.getCellValue(2,i) != systemDate) { 
		                	okToProcess = 'No';
		                	break;
			            } 
			        } 
					if (okToProcess == 'Yes'){
						for(var i=0;i<branch.getRowCount();i++) { 
				            if(branch.getCellValue(3,i)) { 
			                	str += '<@>' + branch.getCellValue(5,i);
				            } 
				        } 
				        if(str!=''){
							data = "dispatch=doProcess&brances=" + str.substring(3);
							var mySearchRequest = new ajaxObject('dayEndService.do');
							mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
								if (responseStatus==200) {
									var message =  eval('(' + responseText + ')');
									if(message['error']){
						       			alert(message['error']);
						       		}else if(message['success']){			       			
						       			alert(message['success']);
						       			getInitialData();
						       			$('screenCont').className = 'enableAll';
						       			resetProcessBtn();
									}	
								}else {
						    	    alert(responseStatus + ' -- Error Processing Request');
						    	    resetProcessBtn(); 					    	    
							    }
							}
							mySearchRequest.update(data,'POST');
						}else{
							alert('<bean:message bundle="message" key="errors.errordayendcreate"/>');
							resetProcessBtn();
							$('screenCont').className = 'enableAll';
						}
					} else {
						alert('<bean:message bundle="message" key="errors.branchdatenottally"/>');
						resetProcessBtn();
						$('screenCont').className = 'enableAll';
					}										
				}					
			}
			
			function confirmCommonSubmit(){
			   	try{
			       var agree=confirm('Confirm to Process ?');                    
			       
			       if (agree)
			           return true ;
			       else
			           return false ;
			    }
			    catch(err){
				      txt="There was an error on this page.\n\n";
			             txt+="Error description: " + err.description + "\n\n";
			             txt+="Click OK to continue.\n\n";
			             errorUrl=location.pathname +"|"+ err.name +"|"+ err.message+"\n";
			             alert(txt);
			             $('screenCont').className = 'enableAll';
				}
			}
			function selectAll(){
				for(var i=0;i<branch.getRowCount();i++) {
					branch.setCellValue($('selectAll').checked,3,i); 
			    }
			}
			
		</script>
		<style>
			#branch {height: 190px;width:720px;}
			#branch .aw-row-selector {text-align: center}
			#branch .aw-column-0 {width: 80px;}
			#branch .aw-column-1 {width: 350px;}
			#branch .aw-column-2 {width: 100px;}
			#branch .aw-column-3 {width: 60px;text-align: center; }
			#branch .aw-column-4 {width: 80px;}
			#branch .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#branch .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
			
			#log {height: 170px;width:720px;}
			#log .aw-row-selector {text-align: center}
			#log .aw-column-0 {width: 80px;}
			#log .aw-column-1 {width: 80px;}
			#log .aw-column-2 {width: 100px;}
			#log .aw-column-3 {width: 80px;}
			#log .aw-column-4 {width: 240px;}
			#log .aw-column-5 {width: 100px;}
			#log .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#log .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
			
		</style>
	</head>
	<body onload="getInitialData();">
		<div id="screenCont" class="enableAll" align="center" ></div>
		
		<logic:equal value="create" property="action" name="dayend">
			<table border="0">					
				<tr>
					<td>							
						<table class="InputTable">	
							<tr height="2px">					
							<tr>
								<td width="20%" align="right">
									<bean:message bundle="lable" key="screen.branch"/>
								</td>
								<td>
									<input type="hidden" id="loginBranchId" name="loginBranchId"/>
									<input type="text" id="loginBranchCode" name="loginBranchCode" size="5" readonly="true" class="READONLYINPUT"/> 
									<input type="text" id="loginBranchName" name="loginBranchName" readonly="true" size="60" class="READONLYINPUT"/>
								</td>
							</tr>
							<tr>
								<td align="right">
									<bean:message bundle="lable" key="screen.systemdate"/>
								</td>
								<td>
									<input type="text" id="systemDate" name="systemDate" readonly="true" size="12" class="READONLYINPUT"/>
								</td>
							</tr>
							<tr>
								<td align="right">
									<bean:message bundle="lable" key="screen.startdate"/>
								</td>
								<td>
									<input type="text" id="startDate" name="startDate" readonly="true" size="12" class="READONLYINPUT"/>
								</td>
							</tr>
							<tr>
								<td align="right">
									<bean:message bundle="lable" key="screen.processingdate"/>
								</td>
								<td>
									<input type="text" id="processingDate" name="processingDate" readonly="true" size="12" class="READONLYINPUT"/>
								</td>
							</tr>
							<tr>
								<td align="right">
									<bean:message bundle="lable" key="screen.enddate" />
								</td>
								<td>
									<input type="text" id="endDate" name="endDate" readonly="true" size="12" class="READONLYINPUT"/>
								</td>
								<td>
									<input type="checkBox" id="selectAll"  align="middle" onClick="selectAll();"/>
									<bean:message bundle="lable" key="screen.selectall" />
								</td>
								
							</tr>
							<tr height="2px">
						</table>
					</td>						
				</tr>
				
				<tr height="2px">
				</tr>
				<tr>
					<td>							
						<table class="InputTable">
							<tr>
								<td colspan="2" align="center" width="100%">
									<script type="text/javascript">
										var branchColumns = [ '<bean:message bundle="lable" key="screen.branchcode"/>',
															  '<bean:message bundle="lable" key="screen.branchname"/>',
															  '<bean:message bundle="lable" key="screen.systemdate"/>',
															  '<bean:message bundle="lable" key="screen.select"/>',
															  '<bean:message bundle="lable" key="screen.status"/>'];
										var str = new AW.Formats.String;
										var branch = createBranchGrid(branchColumns,"branch",[str,str,str,str,str]);
										document.write(branch);
									</script>
								</td>
							</tr>
						</table>			
					</td>
				</tr>
				<tr height="2px">
				</tr>
				<tr>
					<td align="center" colspan="2">
						<table class="InputTable" width="100%">
							<tr height="3px" >
							</tr>
							<tr>
								<td align="center">
									<table cellpadding="0" cellspacing="0">
										<tr>
											<td width="100px" colspan="2">
												<input type="button" value="<bean:message bundle="lable" key="screen.process"/>" id="Check" class="buttonNormal" onclick='submitForm()'/>												
											</td>											
										</tr>
									</table>
								</td>
							</tr>
							<tr height="3px" >
							</tr>
						</table>
					</td>
				</tr>
				<tr height="2px">
				</tr>
				<tr>
					<td valign="middle" align="center" colspan="2">
						<fieldset style="padding: 1px;">
							<legend>Process Log</legend>
							<div style="width: 730px;height: 145px;position: absolute;z-index: 1000;"></div>
							<table width="100%" >
								<tr>
									<td colspan="2">
										<script type="text/javascript">
											var logColumns = [ 	'<bean:message bundle="lable" key="screen.date"/>',
																'<bean:message bundle="lable" key="screen.time"/>',
																'<bean:message bundle="lable" key="screen.username"/>',
																'<bean:message bundle="lable" key="screen.branchcode"/>',
																'<bean:message bundle="lable" key="screen.process"/>',
																'<bean:message bundle="lable" key="screen.status"/>'];
											var str=new AW.Formats.String;
											var log=createBrowser(logColumns,"log",[str,str,str,str,str,str]);
											document.write(log);
										</script>
									</td>
								</tr>
							</table>
						</fieldset>
					</td>
				</tr>
			</table>	
		</logic:equal>
	</body>
</html:html>
