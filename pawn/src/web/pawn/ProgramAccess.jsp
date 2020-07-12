<%@ page language="java" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
  	<head>
    	<title>OfficerAccess.jsp</title>
		<link rel="StyleSheet" type="text/css" href="<html:rewrite forward="commonCSS"/>"></link>
		<script type="text/javascript" src="<html:rewrite forward="commonJS"/>"></script>

		<script type="text/javascript">
			var treeArray = new Array();
			var holderArray = new Array();
			var isDualAuthorize = '<c:out value='${sessionScope["LOGIN_KEY"].authorizeMode}'/>';

			function getIsDualAuthorize(){
				return isDualAuthorize;
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

			function setGridData(Data){
				grid.setRowCount(Data.length);
				grid.setCellText(Data);
				grid.setSelectedRows([]);
				grid.refresh();
			}

			function getGridData(){
				data = "dispatch=getAjaxData"
				var mySearchRequest = new ajaxObject("userGroupService.do");
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
					if (responseStatus==200) {
						var message =  eval('(' + responseText + ')');
						holderArray = message;
						setGridData(message);
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request');
				    }
				}
				mySearchRequest.update(data,'POST');
			}

			function getTableData(){
				var userGroupId = $('userGroupId').value;

				if(userGroupId!='' && userGroupId>0){
					data ="dispatch=getTreeData&userGroupId=" + userGroupId;
					url = "programAccessService.do";

					$('screenCont').className = 'disableAll';

					var mySearchRequest = new ajaxObject(url);
					mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
						if (responseStatus==200) {
							var message = eval('(' + responseText + ')');
							treeArray = message;
			       			createTree(message);
						}else {
				    	    alert(responseStatus + ' -- Error Processing Request');
					    }
					    $('screenCont').className = 'enableAll';
					}
					mySearchRequest.update(data,'POST');
				}else{
					o = new dTree('o');
					o.clearCookie();
					$('treeDiv').innerHTML = o;
				}
			}

			function createTree(message){
				o = new dTree('o');
				o.clearCookie();
				var str='<center><br /><br /><br /><br /><img src="images/icefaces.gif" /></center>';
				document.getElementById('treeDiv').innerHTML = str;
				var test = '';
				if(message!='' && message!=null){
					for(var i=0;i<message.length;i++){
						o.add(message[i][0],message[i][1],message[i][2],message[i][3],message[i][4],message[i][5],message[i][6]);
					}

					$('treeDiv').innerHTML = o;
					for (var n=0; n<o.aNodes.length; n++) {
						try{
							eNew = $("so" + o.aNodes[n].id );
							eNew.className = "node";
						}catch(err){}
					}
					o.closeAll();
					$('treeDiv').innerHTML = o;
				}
			}

			function markUp(obj){
				var parentId = obj.title.substr(1);
				for(var j=0;j<treeArray.length;j++){
					if(treeArray[j][1]!=-1 && parentId==treeArray[j][1]){
						$(''+parentId).value = '1';
					}
				}
			}

			function unMark(obj){
				var parentId = obj.title.substr(1);
				for(var j=0;j<treeArray.length;j++){
					if(treeArray[j][1]!=-1 && parentId==treeArray[j][1]){
						if($(''+treeArray[j][0]).value!=''){
							$(parentId).value = '1';
							break;
						}else{
							$(parentId).value = '0';
						}
					}
				}
			}

			function setValue(obj){
				var elementId = obj.id.substr(1);
				var str = ($('C'+elementId).checked?$('C'+elementId).value:'') +
						  ($('U'+elementId).checked?$('U'+elementId).value:'') +
						  ($('D'+elementId).checked?$('D'+elementId).value:'') +
						  ($('A'+elementId).checked?$('A'+elementId).value:'') +
						  ($('S'+elementId).checked?$('S'+elementId).value:'') +
						  ($('P'+elementId).checked?$('P'+elementId).value:'') ;

				$(elementId).value = str.substr(0,str.length-1)
				if(obj.checked)
					markUp(obj);
				else
					unMark(obj);
			}

			function loadToolBar(){
			    url = 'programAccessService.do';
			  	if(CurrentPage(window.parent.frames['footer'].location.pathname)!="programAccessService.do"){
			   		open("programAccessService.do?dispatch=toolbar&evn=<c:out value="${param.evn}" />","footer");
			    }
			}

			function getCreateData(){
				var str = '';  //{str format = programId,ProgramAccessId,access,version}
				for(var j=0;j<treeArray.length;j++){
					if(treeArray[j][1]!=-1){
						var obj = $(''+treeArray[j][0]);
						str += obj.id + '<@>' + obj.name.split(':')[0] + '<@>' + (obj.value!=''?obj.value:'0') + '<@>' + obj.name.split(':')[1] + '<row>' ;
					}
				}

				return str;
			}

			function createOrUpdate(){
				data ="dispatch=createOrUpdate&data=" + getCreateData() + "&userGroupId=" + $('userGroupId').value + "&code=" + $('code').value;
				url = "programAccessService.do";

				var mySearchRequest = new ajaxObject(url);
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
					if (responseStatus==200) {
						var message = eval('(' + responseText + ')');
						if(message['error']){
			       			alert(message['error']);
			       		}else if(message['success']){
			       			alert(message['success']);
			       			$('screenCont').className = 'enableAll';
			       			getTableData();
			       		}else{
			       			showValidationErrors(message);
			       		}
					}else {
			    	    alert(responseStatus + ' -- Error Processing Request');
				    }
				}
				mySearchRequest.update(data,'POST');
			}

			function showValidationErrors(message){
	       		for( var i =0; i < message.length ; i++){
	            	if ( message[i]['code'] )
	                	$('divCode').innerHTML = message[i]['code'];
	            }
	        }

	        function clearAll(){
	        	$('userGroupId').value 	= '';
	        	$('code').value 		= '';
	        	$('description').value 	= '';
	        	$('divCode').innerHTML  = '';
	        	$('treeDiv').innerHTML 	= '';
	        	treeArray = new Array();
	        }
		</script>

		<style>
			.on {
				background-color: #c0d2ec;
			}
			.out{
				background-color: #FFFFFF;
			}
			.dtreeLocal {
				font-family: Verdana, Geneva, Arial, Helvetica, sans-serif;
				font-size: 11px;
				color: #666;
				white-space: nowrap;
				overflow: auto;
				height: 100%;
				width:100%;
			}

			#firstGrid {height: 210px;width:600px;}
			#firstGrid .aw-row-selector {text-align: center}
			#firstGrid .aw-column-0 {width: 100px;}
			#firstGrid .aw-column-1 {width: 455px;}
			#firstGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#firstGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
		</style>
  	</head>

  	<body onload="loadToolBar();">
		<logic:equal name="programaccess" property="action" value="create">
			<script type="text/javascript" src="<html:rewrite forward="accessDTreeJS"/>"></script>
			<script type="text/javascript" src="<html:rewrite forward="ajaxJS"/>"></script>
			<script type="text/javascript" src="<html:rewrite forward="awJS"/>"></script>
			<script type="text/javascript" src="js/com-all.js"></script>

			<link rel="StyleSheet" type="text/css" href="<html:rewrite forward="dtreeCSS"/>"></link>
			<link rel="StyleSheet" type="text/css" href="<html:rewrite forward="awCSS"/>"></link>
			<link rel="StyleSheet" type="text/css" href="css/com-all.css"></link>

			<div id="serchDiv" class="x-hidden">
	        	<div class="x-window-header">
	            	Search Officer
	            </div>
	        	<div id="serch-tab">
	            	<div class="x-tab" title="Search">
						<table style="width: 600px">
							<tr>
								<td>
									<script>
										var myColumns = ["<bean:message bundle="lable" key="screen.code"/>","<bean:message bundle="lable" key="screen.description"/>"];
			            				var str = new AW.Formats.String;
			            				var cellFormat = [str,str,str,str];
		                    			var grid= createBrowser(myColumns,'firstGrid',cellFormat);
		                    			grid.setHeaderHeight(25);
		                                document.write(grid);
		                                grid.onRowDoubleClicked = function(event, row){
											try{
												$('code').value        = this.getCellText(0,row);
												$('description').value = this.getCellText(1,row);
												$('userGroupId').value = this.getCellText(2,row);
												$('hidDiv').className='hideSearchPopup';
					                        	win.hide();
					                        	getTableData();
											}catch(error){}
										};
										grid.onSelectedRowsChanged=function(row){
											try{
												$('code').value        = this.getCellText(0,row);
												$('description').value = this.getCellText(1,row);
												$('userGroupId').value = this.getCellText(2,row);
											}catch(error){}
										}
		                                getGridData();
									</script>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div id="hidDiv" class="hideSearchPopup">
			</div>

			<html:form action="programAccessService.do">
				<div id="screenCont" class="enableAll" align="center" ></div>
				<table border="0">
					<tr>
						<td>
							<table class="InputTable">
								<tr>
									<td colspan="2">
										<table class="InputTable">
											<tr height="2px"></tr>
											<tr>
												<td width="20%" align="right" >
													<bean:message bundle="lable" key="screen.usergroup"/>
												</td>
												<td>
													<input type="hidden" id="userGroupId" name="userGroupId" value="1" />
													<html:text property="code" styleId="code" size="6" maxlength="4"
														onfocus="clearDivision('divCode')"
														onblur="upperCase('code');commonSearch('programAccessService.do','userGroupId','code','description','getUserGroup','divCode',function(){getTableData();},'',function(){getTableData();})"/>
													<input id="ButtonSerch" type="button" value="..." />
													<input type="text" size="60" maxlength="100" id="description" style="background-color: #deecfd; border-right: #8bb8f3 1px solid; border-top: #8bb8f3 1px solid; border-left: #8bb8f3 1px solid; border-bottom: #8bb8f3 1px solid;" readonly="true"/>
													<font color="red">*</font><br/>
													<div id="divCode" class="validate"/>
												</td>
											</tr>
											<tr height="5px"></tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>

					<tr height="2px"></tr>
					<tr>
						<td>
							<table class="InputTable">
								<tr>
									<td colspan="2">
										<table width="100%" class="InputTable">
											<tbody id="tree" >
												<tr onmouseout="">
													<td style="width:100%;background-color: white;" >
														<div class="dtree" style="height:470px; width:100%; overflow:auto; margin-left: 20px" id="treeDiv" >

														</div>
													</td>
												</tr>
											</tbody>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</html:form>

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
				                   		getTableData();
				                   		//$('hidDiv').className='hideSearchPopup';
				                        win.hide();
				                   	}
				                }]
				            });
				        }
				        win.show(this);
				        //$('hidDiv').className='showSearchPopup';
				    });
				});
			</script>
		</logic:equal>

		<logic:equal name="programaccess" property="action" value="toolbar">
			<script>
				var ref = window.parent.frames['mainbody'];

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

			    function submitForm(){
			    	ref.$('screenCont').className = 'disableAll';
			    	if(confirmCommonSubmit('',1)){
				    	prosessForm();
				    	ref.createOrUpdate();
				    	resetProcessBtn();
				    }else{
				    	ref.$('screenCont').className = 'enableAll';
				    }
			    }
			</script>

			<table class="toolTable">
				<tr>
					<td width="50%" align="right">
						<input type="button" value="<bean:message bundle="button" key="button.submit"/>" id="Check" class="buttonNormal" onclick='submitForm()'/>
						<input type="button" value="<bean:message bundle="button" key="button.clear"/>" id="Clear" class="buttonNormal" onclick="ref.clearAll();"/>
					</td>
				</tr>

			</table>
		</logic:equal>
  	</body>
</html:html>
