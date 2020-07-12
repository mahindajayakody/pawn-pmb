<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
	<head>
		<link rel="StyleSheet" type="text/css"	href="<html:rewrite forward="commonCSS"/>"></link>
		<link rel="StyleSheet" type="text/css"	href="<html:rewrite forward="awCSS"/>"></link>
		<link rel="StyleSheet" type="text/css" href="css/com-all.css"></link>
		<script type="text/javascript" src="<html:rewrite forward="commonJS"/>"></script>
		<script type="text/javascript" src="<html:rewrite forward="ajaxJS"/>"></script>
		<script type="text/javascript" src="<html:rewrite forward="awJS"/>"></script>
		<script type="text/javascript" src="js/com-all.js"></script>

		<script type="text/javascript">								
			var action = 1;
			
			window.parent.document.getElementById('footer').style.display="none";			
			window.parent.document.getElementById("mainbody").height="600px";
			window.parent.document.getElementById("footer").height="0px";
			
			var url = 'reminderRePrintService.do';
		
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
			function createBrowserWithCheckBox(myColumns,gridName,dataFormat){
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
				obj.setCellTemplate(new AW.Templates.Checkbox, 0); 	
				
				obj.setSelectorVisible(true);
			
			    obj.getCheckedValue=function() {
			    	for(var i=0;i<obj.getRowCount();i++) { 
			            if(obj.getCellValue(0,i)) { 
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
			function getGridData(){
				data = "dispatch=getAjaxData&conditions=reminderParaId<next>isPrinted&value=" + $('recordId').value + "<next>" + 1;
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
				
			function clearAll(){								
				$('clientId').value 		 	= '0';
				$('clientcode').value 			= '';
				$('clientName').value 			= '';	
				$('clientAddress').value		= '';			
				$('ticketId').value 			= '0';
				$('ticketNumber').value 		= '';
				$('receiptNo').value 			= '';
				$('receiptamt').value 			= '0.00';
				$('TotDueAmt').value 			= '0.00';
				$('remark').value 				= '';
				$('receiptNo').value			= '';
				$('divTicketno').innerHTML 		= '';
				$('divreceiptamt').innerHTML	= '';
				$('Check').disabled  = false;
				setGridData(grid,[]);
			}
			
			function selectReminders(){
				for(var i=0;i<grid.getRowCount();i++) {
	            	grid.setCellValue($('selectAll').checked,0,i); 
			    }
			}
			
			function print(){	
				var str = '';
				for(var i=0;i<grid.getRowCount();i++) { 
		            if(grid.getCellValue(0,i)) { 
	                	str += '<@>' + grid.getCellValue(5,i);
		            } 
		        } 	
		        if(str == '')
					alert("Please Select a reminder to Print");
				else{	
					url = 'reminderPrintService.do?dispatch=print&reminders=' + str.substring(3);
					theHeight=500;
					theWidth=800;
					var theTop=(screen.height/2)-(theHeight/2);
					var theLeft=(screen.width/2)-(theWidth/2);
					var features='height='+theHeight+',width='+theWidth+',top='+theTop+',left='+theLeft+",status=yes,scrollbars=no,resizable=yes";
					window.open(url,"PrintPreview",features);
				}
				$('screenCont').className = 'enableAll';	
			}
			function getReminderParaSerchData(){
					data = "dispatch=getAjaxData" 
					var mySearchRequest = new ajaxObject("reminderParaService.do");
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
			
			function showValidationErrors(message){
	       		for( var i =0; i < message.length ; i++){	            	
	                 if( message[i]['ticketNumber'] )
	                    $('divTicketno').innerHTML = message[i]['ticketNumber'];
	                 else if( message[i]['receiptamt'] )
	                    $('divreceiptamt').innerHTML = message[i]['receiptamt'];
	              //  else if( message[i]['interestCode'] )
	              //      $('divinterestCode').innerHTML = message[i]['interestCode'];
	            }    
	        }
		</script>
	
		<style>
			#reminder {height: 200px;width:720px;}
			#reminder .aw-row-selector {text-align: center}
			#reminder .aw-column-0 {width: 40px;}	
			#reminder .aw-column-1 {width: 150px;text-align: right;}	
			#reminder .aw-column-2 {width: 150px;text-align: right;}	
			#reminder .aw-column-3 {width: 130px;text-align: right;}	
			#reminder .aw-column-4 {width: 130px;text-align: right;}	
			#reminder .aw-column-5 {width: 130px;text-align: right;}				
			#reminder .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#reminder .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
			
			#secondGrid {height: 210px;width:600px;}
			#secondGrid .aw-row-selector {text-align: center}
			#secondGrid .aw-column-0 {width: 100px;}
			#secondGrid .aw-column-1 {width: 455px;}	
			#secondGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#secondGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
			
			
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
		<div id="screenCont" class="enableAll" align="center"></div>
		<div id="hidDiv" class="hideSearchPopup"></div>
		
		<div id="serchDiv" class="x-hidden">
        <div class="x-window-header">
            	Search Reminder Parameter
        </div>
        	<div id="serch-tab">
            	<div class="x-tab" title="Search">
					<table style="width: 600px">							
						<tr>
							<td>
								<script>
									var myColumnsSerch = ["<bean:message bundle="lable" key="screen.code"/>","<bean:message bundle="lable" key="screen.description"/>"];
		            				var str = new AW.Formats.String; 
		            				var cellFormatSerch = [str,str];
	                    			var gridSerch = createBrowser(myColumnsSerch,'secondGrid',cellFormatSerch);							                    			 			                    			
	                    			gridSerch.setHeaderHeight(25);
	                                document.write(gridSerch);
	                                gridSerch.onRowDoubleClicked = function(event, row){
										try{
											$('recordId').value = this.getCellText(11,row);
											$('code').value   = this.getCellText(0,row);
											$('description').value   = this.getCellText(1,row);
											$('hidDiv').className='hideSearchPopup';
				                        	win.hide();	
				                        	getGridData();			                        	
										}catch(error){}
									};
									gridSerch.onSelectedRowsChanged=function(row){
										try{
											$('recordId').value = this.getCellText(11,row);
											$('code').value   = this.getCellText(0,row);
											$('description').value   = this.getCellText(1,row);
										}catch(error){}
									}
	                                getReminderParaSerchData();
								</script>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		

		<!-- Create record -->
		<logic:equal name="reminderreprint" property="action" value="create">
			<html:form action="reminderRePrintService.do">
				<input type="hidden" id="recordId" name="recordId" />
				<input type="hidden" id="version" name="version" />

				<table border="0">
					<tr height="5px"></tr>
					<tr>
						<td>							
							<table class="InputTable">    
								<tr height="2px"></tr>     
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.code"/>&nbsp;
									</td>
									<td>
										<html:hidden property="recordId" styleId="recordId" />
										<html:text property="code" styleId="code" size="5" maxlength="4" 
											onfocus="clearDivision('divCode')"/>
										<input id="ButtonSerch" type="button" value="..." />
										<input type="text" size="40" id="description" readonly="readonly" class="READONLYINPUT"/>
										<font color="red">*</font>
										<input type="checkBox" id="selectAll"  align="middle" onClick="selectReminders();"/><bean:message bundle="lable" key="screen.selectall" /><br/>
										<div id="divCode" class="validate"/>
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
								<tr>
									<td colspan="5" align="center">
										<script>
											var myColumns = ["<bean:message bundle="lable" key="screen.select"/>",
															 "<bean:message bundle="lable" key="screen.ticketnumber"/>",
															 "<bean:message bundle="lable" key="screen.name"/>",
															 "<bean:message bundle="lable" key="screen.dategenarated"/>",
															 "<bean:message bundle="lable" key="screen.balancetopaid"/>"];
									 		var str = new AW.Formats.String; 
									 		var cellFormat = [str,str,str,str,str,str];
								     		var grid = createBrowserWithCheckBox(myColumns,'reminder',cellFormat);			                    						                    						                    			
								            document.write(grid);
								          </script>
									</td>
								</tr>
							</table>							
						</td>
					</tr>	
					<tr height="5px"></tr>
				</table>
			
				<table class="toolTable" id="toolbar">
					<tr>
						<td align="right">
							<input type="button" value="<bean:message bundle="button" key="button.print"/>" id="Clear" class="buttonNormal" onclick="print();"/>
							<input type="button" value="<bean:message bundle="button" key="button.clear"/>" id="Clear" class="buttonNormal" onclick="clearAll();"/>
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
			                   		getGridData();
			                   		$('hidDiv').className='hideSearchPopup';
			                        win.hide();
			                   	}
			                }]
			            });
			        }
			        win.show(this);
			        $('hidDiv').className='showSearchPopup';
			    });
			});
		</script>
			
	</body>
</html:html>
