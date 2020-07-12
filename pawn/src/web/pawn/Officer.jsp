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
			
			var url = 'officerService.do';	
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
			
			function createAccessBranchBrowser(myColumns,gridName,dataFormat){
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
			
			function getRecordData(id){
				data = "dispatch=getAjaxData&recordId=" + id;
				var mySearchRequest = new ajaxObject(url);
				mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
					if (responseStatus==200) {						
						var message =  eval('(' + responseText + ')');
						if(message['error']){
							alert(message['error']);
						}else{
							$('pawnerId').value		   		= message[8];
							$('branchId').value 			= message[4];
							$('userGroupId').value    		= message[9];
							$('pawnerCode').value		   	= message[6];
							$('branchCode').value 			= message[1];
							$('userGroupCode').value    	= message[10];
							$('userName').value     		= message[0];
							$('password').value 			= message[11];
							$('confirmPassword').value 		= message[11];				
							$('pawnerName').value 			= message[7];
							$('branchDescription').value 	= message[5];
							$('userGroupDescription').value = message[2];
							$('recordId').value 			= message[12];					
							$('version').value 				= message[13];
							if(message[14] ='1'){
								$('isactive').checked= ture;
							}
							
							$accBranchData = message[14];
							
							for(i=$('accessBranch').options.length-1;i>=0;i--){
						        $('accessBranch').remove(i);
						    }
							
							for (i=0;i<$accBranchData.length;i++){
								$data = $accBranchData[i];
								$('accessBranch').options[$('accessBranch').options.length] = new Option($data[1],$data[0]);
							}					
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
				$('branchId').value 			= '';
				$('userGroupId').value    		= '';
				$('pawnerCode').value		   	= '';
				$('branchCode').value 			= '';
				$('userGroupCode').value    	= '';
				$('userName').value     		= '';
				$('password').value 			= '';
				$('confirmPassword').value 		= '';				
				$('pawnerName').value 			= '';
				$('branchDescription').value 	= '';
				$('userGroupDescription').value = '';
				
				$('divPawnerCode').innerHTML 	= '';
				$('divBranchCode').innerHTML 	= '';
				$('divUserGroupCode').innerHTML = '';
				$('divUserName').innerHTML 		= '';
				$('divPassword').innerHTML 		= '';
				$('divComPassword').innerHTML 	= '';
				
				for(i=$('accessBranch').options.length-1;i>=0;i--){
			        $('accessBranch').remove(i);
			    }
			    								
				grid.setSelectedRows([]);
			}
			
			function getPawnerData(){};
			
			function getGridData(){
				 getSerchData(grid,'officerService.do','');		                               
			}
			
			function getCreateData(){
				var pawnerId 	= $('pawnerId').value;
				var branchId	= $('branchId').value;
				var userGroupId	= $('userGroupId').value;
				var pawnerCode	= $('pawnerCode').value;				
				var branchCode	= $('branchCode').value;
				var userGroupCode = $('userGroupCode').value;
				var userName	= $('userName').value;
				var password	= $('password').value;
				var confirmPassword	= $('confirmPassword').value;
				var isActive ='0';
				if($('isactive').checked){
					isActive = '1';
				}
				var accstr = '';
				for(i=0;i<$('accessBranch').options.length;i++){
					brid   = $('accessBranch').options[i].value;
					brName = $('accessBranch').options[i].text;
					
			        accstr += brid + '<@>' + brName;
			        
			        if((i+1)<$('accessBranch').options.length){
			        	accstr += '<#>';
			        }
			    }
				
				return 	"&pawnerId=" + pawnerId + "&branchId=" + branchId +
						"&userGroupId=" + userGroupId + "&pawnerCode=" + pawnerCode +
						"&branchCode=" + branchCode + "&userGroupCode=" + userGroupCode +
						"&userName=" + userName + "&password=" + password +
						"&confirmPassword=" + confirmPassword + "&accessBranch=" + accstr+"&isActive=" + isActive + "&change=No";
			}
			
			function getChangedData(){
				var recordId = $('recordId').value;
				var version  = $('version').value;
				
				var str = getCreateData() + "&recordId=" + recordId + "&version=" + version;				
				return str;
			}
		
			function showValidationErrors(message){
	       		for( var i =0; i < message.length ; i++){	            	
	                if( message[i]['pawnerCode'] )
	                    $('divPawnerCode').innerHTML = message[i]['pawnerCode'];
	                else if( message[i]['branchCode'] )
	                    $('divBranchCode').innerHTML = message[i]['branchCode'];
	                else if( message[i]['userGroupCode'] )
	                    $('divUserGroupCode').innerHTML = message[i]['userGroupCode'];
	                else if( message[i]['userName'] )
	                    $('divUserName').innerHTML = message[i]['userName'];
	                else if( message[i]['password'] )
	                    $('divPassword').innerHTML = message[i]['password'];
	                else if( message[i]['confirmPassword'] )
	                    $('divComPassword').innerHTML = message[i]['confirmPassword'];                
	            }    
	        }  
	        
	        function updateAccessBranch(){
	        	for(i=$('accessBranch').options.length-1;i>=0;i--){
			        $('accessBranch').remove(i);
			    }
			    
			    $('accessBranch').options[$('accessBranch').options.length] = new Option($('branchDescription').value,$('branchId').value);
			    
		        for(var i=0;i<gridSerchAccess.getRowCount();i++) { 
		            if(gridSerchAccess.getCellValue(2,i) && gridSerchAccess.getCellValue(3,i)!=$('branchId').value) { 
	                	$('accessBranch').options[$('accessBranch').options.length] = new Option(gridSerchAccess.getCellValue(1,i),gridSerchAccess.getCellValue(3,i));
		            } 
		        }
	        }
	        
	        function clearAccessBranch(){
	        	var oldVal = $('old').value;
	        	var newVal = $('branchCode').value;
	        	
	        	if(oldVal == newVal){
	        		return false;
	        	}
	        		
	        	for(i=$('accessBranch').options.length-1;i>=0;i--){
			        $('accessBranch').remove(i);
			    }
			    if($('branchCode').value != '')
			    	$('accessBranch').options[$('accessBranch').options.length] = new Option($('branchDescription').value,$('branchId').value);
	        } 
		</script>
		
		<style>
			#firstGrid {height: 250px;width:720px;}
			#firstGrid .aw-row-selector {text-align: center}
			#firstGrid .aw-column-0 {width: 150px;}
			#firstGrid .aw-column-1 {width: 250px;}	
			#firstGrid .aw-column-2 {width: 250px;}	
			#firstGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#firstGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
			
			#popupgrid1 {height: 250px;width:580px;}
			#popupgrid1 .aw-row-selector {text-align: center}
			#popupgrid1 .aw-column-0 {width: 100px;}
			#popupgrid1 .aw-column-1 {width: 400px;}	
			#popupgrid1 .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#popupgrid1 .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
			
			#popupgrid2 {height: 250px;width:580px;}
			#popupgrid2 .aw-row-selector {text-align: center}
			#popupgrid2 .aw-column-0 {width: 100px;}
			#popupgrid2 .aw-column-1 {width: 400px;}				
			#popupgrid2 .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#popupgrid2 .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
		
			#popupgrid3 {height: 210px;width:580px;}
			#popupgrid3 .aw-row-selector {text-align: center}
			#popupgrid3 .aw-column-0 {width: 100px;}
			#popupgrid3 .aw-column-1 {width: 300px;}	
			#popupgrid3 .aw-column-2 {width: 100px;}
			#popupgrid3 .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#popupgrid3 .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
		</style>
  	</head>
  
  	<body onload="loadToolBar()">
    	<div id="screenCont" class="enableAll" align="center" ></div>
    	
    	<!-- Create record -->
    	<logic:equal name="officer" property="action" value="create">
			<html:form action="officerService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
				<input type="hidden" id="userType" name="userType" value="OFF"/>
				<input type="hidden" id="old" name="old" value=""/>
				
				<table border="0">					
					<tr>
						<td>							
							<table class="InputTable" >																	
								<tr>									
									<td colspan="2" align="center">
										<script >
											var myColumns = ["<bean:message bundle="lable" key="screen.username"/>",
											                 "<bean:message bundle="lable" key="screen.branch"/>",
															 "<bean:message bundle="lable" key="screen.usergroup"/>"];
				            				var str = new AW.Formats.String; 
				            				var cellFormat = [str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);			                    						                    						                    			
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){	
			                                	if(row!='') { 
			                                		$('userName').value     		= grid.getCellText(0,row);
			                                		$('branchDescription').value 	= grid.getCellText(1,row);
			                                		$('userGroupDescription').value = grid.getCellText(2,row);
			                                		$('branchId').value 			= grid.getCellText(4,row);
			                                		$('branchCode').value 			= grid.getCellText(5,row);
			                                		$('pawnerCode').value		   	= grid.getCellText(6,row);
			                                		$('pawnerName').value 			= grid.getCellText(7,row);			                                		
			                                		$('pawnerId').value		   		= grid.getCellText(8,row);													
													$('userGroupId').value    		= grid.getCellText(9,row);
													$('userGroupCode').value    	= grid.getCellText(10,row);													
													$('password').value 			= grid.getCellText(11,row);
													$('confirmPassword').value 		= grid.getCellText(11,row);												
													$('recordId').value 			= grid.getCellText(12,row);
													$('version').value 				= grid.getCellText(13,row);
													$accBranchData = grid.getCellText(14,row);
													
													for(i=$('accessBranch').options.length-1;i>=0;i--){
												        $('accessBranch').remove(i);
												    }
													
													for (i=0;i<$accBranchData.length;i++){
														$data = $accBranchData[i];
														$('accessBranch').options[$('accessBranch').options.length] = new Option($data[1],$data[0]);
													}
			                                	}																						
			                                };	
			                                getSerchData(grid,'officerService.do','');
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
										<bean:message bundle="lable" key="screen.officer"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="pawnerId" name="pawnerId">										
										<input id="pawnerCode" name="pawnerCode" style="width: 80px" maxlength="8"  tabindex="1" 
											onfocus="clearDivision('divPawnerCode')"
											onblur="commonSearch('pawnerService.do','pawnerId','pawnerCode','pawnerName','getPawner','divPawnerCode')"/>
										<input id="ButtonPawnerSerch" type="button" value="..." />
										<input id="pawnerName" name="pawnerName" size="40" class="READONLYINPUT" readonly="readonly"/>										
										<font color="red">*</font><br/>
										<div id="divPawnerCode" class="validate"/>
									</td>
								</tr>
								
								<tr height="2px"></tr>     
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.branch"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="branchId" name="branchId" value="0">										
										<input id="branchCode" name="branchCode" style="width: 50px" maxlength="3" tabindex="4"
											onfocus="clearDivision('divBranchCode');$('old').value=this.value"
											onblur="upperCase('branchCode');commonSearch('officerService.do','branchId','branchCode','branchDescription','getBranch','divBranchCode',function(){clearAccessBranch();},'',function(){clearAccessBranch();})"/>
										<input id="ButtonBranchSerch" type="button" value="..." />
										<input id="branchDescription" name="branchDescription" size="60" class="READONLYINPUT" readonly="readonly"/>										
										<font color="red">*</font><br/>
										<div id="divBranchCode" class="validate"/>
									</td>
								</tr>
								
								<tr height="2px"></tr>	
								<tr>
									<td width="20%" align="right"  style="vertical-align: top;">
										<bean:message bundle="lable" key="screen.accessbranch"/>&nbsp;
									</td>
									<td>
										<select id="accessBranch" style="width:150px; height:80px" name="accessBranch" multiple="multiple" size="6">											
										</select>
										<input type="button" id="branchesBtn" value="..." style="vertical-align: top;"/>
									</td>
								</tr>
								
								<tr height="2px"></tr> 
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.usergroup"/>&nbsp;
									</td>
									<td> 										
										<input type="hidden" id="userGroupId" name="userGroupId">
										<html:text property="userGroupCode" styleId="userGroupCode" style="width: 50px" maxlength="4" tabindex="6"
											onfocus="clearDivision('divUserGroupCode')"
											onblur="upperCase('userGroupCode');commonSearch('officerService.do','userGroupId','userGroupCode','userGroupDescription','getUserGroup','divUserGroupCode')"/>
										<input id="ButtonUserGroupSerch" type="button" value="..." />
										<input id="userGroupDescription" name="userGroupDescription" size="60" class="READONLYINPUT" readonly="readonly"/>										
										<font color="red">*</font><br/>
										<div id="divUserGroupCode" class="validate"/>
									</td>
								</tr>
								
								<tr height="2px"></tr> 
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.username"/>&nbsp;
									</td>
									<td> 										
										<html:text property="userName" styleId="userName" size="20" maxlength="16" value=""
											onfocus="clearDivision('divUserName')"/>
										<font color="red">*</font>
										<input type="checkBox" id="isactive"  align="middle" checked="checked"/>
										<bean:message bundle="lable" key="screen.isactive" />										
										<div id="divUserName" class="validate"/>
									</td>
								</tr>
								
								<tr height="2px"></tr> 
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.password"/>&nbsp;
									</td>
									<td> 										
										<html:password property="password" styleId="password" size="20" maxlength="16" value=""
											onfocus="clearDivision('divPassword')"/>
										<font color="red">*</font><br/>
										<div id="divPassword" class="validate"/>
									</td>
								</tr>
								
								<tr height="2px"></tr> 
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.conpassword"/>&nbsp;
									</td>
									<td> 										
										<html:password property="confirmPassword" styleId="confirmPassword" size="20" maxlength="16"
											onfocus="clearDivision('divComPassword')"/>
										<font color="red">*</font><br/>
										<div id="divComPassword" class="validate"/>
									</td>
								</tr>								
							<tr height="5px"></tr>															
						</table>
					</td>
				</tr>
				</table>
			</html:form>
			<jsp:include flush="true" page="ClientBrowser.jsp"></jsp:include>				
    	</logic:equal>
    	
    	
    	<!-- update record -->
    	<logic:equal name="officer" property="action" value="update">
			<html:form action="officerService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
				<input type="hidden" id="old" name="old" value=""/>				
				
				<table border="0">					
					<tr>
						<td>							
							<table class="InputTable">																	
								<tr>									
									<td colspan="2" align="center">
										<script>
											var myColumns = ["<bean:message bundle="lable" key="screen.username"/>","<bean:message bundle="lable" key="screen.branch"/>",
															 "<bean:message bundle="lable" key="screen.usergroup"/>"];
				            				var str = new AW.Formats.String; 
				            				var cellFormat = [str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);			                    						                    						                    			
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){	
			                                	if(row!='') { 
			                                		$('userName').value     		= grid.getCellText(0,row);
			                                		$('branchDescription').value 	= grid.getCellText(1,row);
			                                		$('userGroupDescription').value = grid.getCellText(2,row);
			                                		$('branchId').value 			= grid.getCellText(4,row);
			                                		$('branchCode').value 			= grid.getCellText(5,row);
			                                		$('pawnerCode').value		   	= grid.getCellText(6,row);
			                                		$('pawnerName').value 			= grid.getCellText(7,row);			                                		
			                                		$('pawnerId').value		   		= grid.getCellText(8,row);													
													$('userGroupId').value    		= grid.getCellText(9,row);
													$('userGroupCode').value    	= grid.getCellText(10,row);													
													$('password').value 			= grid.getCellText(11,row);
													$('confirmPassword').value 		= grid.getCellText(11,row);												
													$('recordId').value 			= grid.getCellText(12,row);
													$('version').value 				= grid.getCellText(13,row);
													$accBranchData = grid.getCellText(14,row);
													
													for(i=$('accessBranch').options.length-1;i>=0;i--){
												        $('accessBranch').remove(i);
												    }
													
													for (i=0;i<$accBranchData.length;i++){
														$data = $accBranchData[i];
														$('accessBranch').options[$('accessBranch').options.length] = new Option($data[1],$data[0]);
													}
			                                	}																						
			                                };	
			                                getSerchData(grid,'officerService.do',''); 		                               
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
										<bean:message bundle="lable" key="screen.officer"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="pawnerId" name="pawnerId">										
										<input id="pawnerCode" name="pawnerCode" style="width: 80px" maxlength="8"  tabindex="1" class="READONLYINPUT" readonly="readonly"
											onfocus="clearDivision('divPawnerCode')"											
											onblur="commonSearch('pawnerService.do','pawnerId','pawnerCode','pawnerName','getPawner','divPawnerCode')"/>										
										<input id="ButtonPawnerSerch" type="button" value="..." class="READONLYINPUT"/>	
										<input id="pawnerName" name="pawnerName" size="50" class="READONLYINPUT" readonly="readonly"/>										
										<font color="red">*</font><br/>
										<div id="divPawnerCode" class="validate"/>
									</td>
								</tr>
								
								<tr height="2px"></tr>     
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.branch"/>&nbsp;
									</td>
									<td>
										<!-- 
										<input type="hidden" id="branchId" name="branchId" value="0">										
										<input id="branchCode" name="branchCode" style="width: 50px" maxlength="3" tabindex="4"
											onfocus="clearDivision('divBranchCode')"
											onblur="upperCase('branchCode');commonSearch('officerService.do','branchId','branchCode','branchDescription','getBranch','divBranchCode')"/>
										<input id="ButtonBranchSerch" type="button" value="..." />
										<input id="branchDescription" name="branchDescription" size="60" class="READONLYINPUT" readonly="readonly"/>										
										<font color="red">*</font><br/>
										<div id="divBranchCode" class="validate"/>
										 -->
										<input type="hidden" id="branchId" name="branchId" value="0">										
										<input id="branchCode" name="branchCode" style="width: 50px" maxlength="3" tabindex="4"
											onfocus="clearDivision('divBranchCode')"
											onblur="upperCase('branchCode');commonSearch('officerService.do','branchId','branchCode','branchDescription','getBranch','divBranchCode',function(){updateAccessBranch();},'',function(){updateAccessBranch();})"/>
										<input id="ButtonBranchSerch" type="button" value="..." />
										<input id="branchDescription" name="branchDescription" size="60" class="READONLYINPUT" readonly="readonly"/>										
										<font color="red">*</font><br/>
										<div id="divBranchCode" class="validate"/>
									</td>
								</tr>
								
								<tr height="2px"></tr>	
								<tr>
									<td width="20%" align="right"  style="vertical-align: top;">
										<bean:message bundle="lable" key="screen.accessbranch"/>&nbsp;
									</td>
									<td>
										<select id="accessBranch" style="width:150px; height:80px" name="accessBranch" multiple="multiple" size="6">											
										</select>
										<input type="button" id="branchesBtn" value="..." style="vertical-align: top;"/>
									</td>
								</tr>
								
								<tr height="2px"></tr> 
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.usergroup"/>&nbsp;
									</td>
									<td> 										
										<input type="hidden" id="userGroupId" name="userGroupId">
										<html:text property="userGroupCode" styleId="userGroupCode" style="width: 50px" maxlength="4" tabindex="6"
											onfocus="clearDivision('divUserGroupCode')"
											onblur="upperCase('userGroupCode');commonSearch('officerService.do','userGroupId','userGroupCode','userGroupDescription','getUserGroup','divUserGroupCode')"/>
										<input id="ButtonUserGroupSerch" type="button" value="..." />
										<input id="userGroupDescription" name="userGroupDescription" size="60" class="READONLYINPUT" readonly="readonly"/>										
										<font color="red">*</font><br/>
										<div id="divUserGroupCode" class="validate"/>
									</td>
								</tr>
								
								<tr height="2px"></tr> 
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.username"/>&nbsp;
									</td>
									<td> 										
										<html:text property="userName" styleId="userName" size="17" maxlength="16" styleClass="READONLYINPUT" readonly="readonly"
											onfocus="clearDivision('divUserName')"/>
										<font color="red">*</font>
										<input type="checkBox" id="isactive"  align="middle" checked="checked"/>
										<bean:message bundle="lable" key="screen.isactive" />										
										<div id="divUserName" class="validate"/>
									</td>
								</tr>
								
								<tr height="2px"></tr> 
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.password"/>&nbsp;
									</td>
									<td> 										
										<html:password property="password" styleId="password" size="20" maxlength="16"
											onfocus="clearDivision('divPassword')" readonly="readonly" styleClass="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divPassword" class="validate"/>
									</td>
								</tr>
								
								<tr height="2px"></tr> 
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.conpassword"/>&nbsp;
									</td>
									<td> 										
										<html:password property="confirmPassword" styleId="confirmPassword" size="20" maxlength="16"
											onfocus="clearDivision('divComPassword')" readonly="readonly" styleClass="READONLYINPUT"/>
										<font color="red">*</font><br/>
										<div id="divComPassword" class="validate"/>
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
    	<logic:equal name="officer" property="action" value="delete">
			<html:form action="officerService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
				<input type="hidden" id="userType" name="userType" value="OFF"/>
				
				<table border="0">					
					<tr>
						<td>							
							<table class="InputTable">																	
								<tr>									
									<td colspan="2" align="center">
										<script>
											var myColumns = ["<bean:message bundle="lable" key="screen.username"/>","<bean:message bundle="lable" key="screen.branch"/>",
															 "<bean:message bundle="lable" key="screen.usergroup"/>"];
				            				var str = new AW.Formats.String; 
				            				var cellFormat = [str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);			                    						                    						                    			
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){	
			                                	if(row!='') { 
			                                		$('pawnerId').value		   		= grid.getCellText(8,row);
													$('branchId').value 			= grid.getCellText(4,row);
													$('userGroupId').value    		= grid.getCellText(9,row);
													$('pawnerCode').value		   	= grid.getCellText(6,row);
													$('branchCode').value 			= grid.getCellText(1,row);
													$('userGroupCode').value    	= grid.getCellText(10,row);
													$('userName').value     		= grid.getCellText(0,row);
													$('password').value 			= grid.getCellText(11,row);
													$('confirmPassword').value 		= grid.getCellText(11,row);
													$('pawnerName').value 			= grid.getCellText(7,row);
													$('branchDescription').value 	= grid.getCellText(5,row);
													$('userGroupDescription').value = grid.getCellText(2,row);
													$('recordId').value 			= grid.getCellText(12,row);
													$('version').value 				= grid.getCellText(13,row);
			                                	}																						
			                                };	
			                                getSerchData(grid,'officerService.do',''); 		                               
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
										<bean:message bundle="lable" key="screen.officer"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="pawnerId" name="pawnerId">										
										<input id="pawnerCode" name="pawnerCode" style="width: 80px" maxlength="8"  tabindex="1" class="READONLYINPUT" readonly="readonly"
											onfocus="clearDivision('divPawnerCode')"											
											onblur="commonSearch('pawnerService.do','pawnerId','pawnerCode','pawnerName','getPawner','divPawnerCode')"/>										
										<input id="ButtonPawnerSerch" type="button" value="..." disabled="disabled" class="READONLYINPUT"/>
										<input id="pawnerName" name="pawnerName" size="70" class="READONLYINPUT" readonly="readonly"/>										
										<font color="red">*</font><br/>
										<div id="divPawnerCode" class="validate"/>
									</td>
								</tr>
								
								<tr height="2px"></tr>     
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.branch"/>&nbsp;
									</td>
									<td>
										<input type="hidden" id="branchId" name="branchId" value="0">										
										<input id="branchCode" name="branchCode" style="width: 50px" maxlength="3" tabindex="4" class="READONLYINPUT" readonly="readonly"
											onfocus="clearDivision('divBranchCode')"
											onblur="upperCase('branchCode');commonSearch('officerService.do','branchId','branchCode','branchDescription','getBranch','divBranchCode')"/>
										<input id="ButtonBranchSerch" type="button" value="..." disabled="disabled" class="READONLYINPUT"/>
										<input id="branchDescription" name="branchDescription" size="60" class="READONLYINPUT" readonly="readonly"/>										
										<font color="red">*</font><br/>
										<div id="divBranchCode" class="validate"/>
									</td>
								</tr>
								
								<tr height="2px"></tr> 
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.usergroup"/>&nbsp;
									</td>
									<td> 										
										<input type="hidden" id="userGroupId" name="userGroupId">
										<html:text property="userGroupCode" styleId="userGroupCode" style="width: 50px" maxlength="4" tabindex="6" styleClass="READONLYINPUT" readonly="readonly"
											onfocus="clearDivision('divUserGroupCode')"
											onblur="upperCase('userGroupCode');commonSearch('officerService.do','userGroupId','userGroupCode','userGroupDescription','getUserGroup','divUserGroupCode')"/>
										<input id="ButtonUserGroupSerch" type="button" value="..." disabled="disabled" class="READONLYINPUT"/>
										<input id="userGroupDescription" name="userGroupDescription" size="60" class="READONLYINPUT" readonly="readonly"/>										
										<font color="red">*</font><br/>
										<div id="divUserGroupCode" class="validate"/>
									</td>
								</tr>
								
								<tr height="2px"></tr> 
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.username"/>&nbsp;
									</td>
									<td> 										
										<html:text property="userName" styleId="userName" size="20" maxlength="16" styleClass="READONLYINPUT" readonly="readonly"
											onfocus="clearDivision('divUserName')"/>
										<font color="red">*</font><br/>
										<div id="divUserName" class="validate"/>
									</td>
								</tr>
								
								<tr height="2px"></tr> 
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.password"/>&nbsp;
									</td>
									<td> 										
										<html:password property="password" styleId="password" size="20" maxlength="16" styleClass="READONLYINPUT" readonly="readonly"
											onfocus="clearDivision('divPassword')"/>
										<font color="red">*</font><br/>
										<div id="divPassword" class="validate"/>
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
    	<logic:equal name="officer" property="action" value="view">
			<html:form action="officerService.do">
				<input type="hidden" id="recordId" name="recordId"/>
				<input type="hidden" id="version" name="version"/>
				<input type="hidden" id="userType" name="userType" value="OFF"/>
				
				<table border="0">					
					<tr>
						<td>							
							
						</td>
					</tr>
			          
					<tr height="5px"></tr>   			                
		          	<tr>
						<td>							
							
						</td>
					</tr>
				</table>
			</html:form>
    	</logic:equal>    	
    	
    	
    	<div id="branch-serchDiv" class="x-hidden">
        	<div class="x-window-header">
            	Search Branch
            </div>
        	<div id="serch-tab1">
            	<div class="x-tab" title="Search">
					<table style="width: 600px">							
						<tr>
							<td>
								<script>
									var myColumns1 = ["<bean:message bundle="lable" key="screen.code"/>","<bean:message bundle="lable" key="screen.description"/>"];
		            				var str = new AW.Formats.String; 
		            				var cellFormat1 = [str,str];
	                    			var gridSerchBranch = createBrowser(myColumns1,'popupgrid1',cellFormat1);							                    			 			                    			
	                    			gridSerchBranch.setHeaderHeight(25);
	                                document.write(gridSerchBranch);
	                                gridSerchBranch.onRowDoubleClicked = function(event, row){
										try{
											$('branchId').value   = this.getCellText(18,row);
											$('branchCode').value = this.getCellText(0,row);
											$('branchDescription').value = this.getCellText(1,row);																					
				                        	winBranch.hide();
				                        	clearAccessBranch();					                        	
										}catch(error){}
									};
									gridSerchBranch.onSelectedRowsChanged = function(row){
										try{
											if(row!=''){
												$('branchId').value   = this.getCellText(16,row);
												$('branchCode').value = this.getCellText(0,row);
												$('branchDescription').value = this.getCellText(1,row);
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
		
		<div id="userGroup-serchDiv" class="x-hidden">
        	<div class="x-window-header">
            	Search User Group
            </div>
        	<div id="serch-tab2">
            	<div class="x-tab" title="Search">
					<table style="width: 600px">							
						<tr>
							<td>
								<script>
									var myColumns2 = ["<bean:message bundle="lable" key="screen.code"/>","<bean:message bundle="lable" key="screen.description"/>"];
		            				var cellFormat2 = [str,str];
	                    			var gridSerchGroup = createBrowser(myColumns2,'popupgrid2',cellFormat2);							                    			 			                    			
	                    			gridSerchGroup.setHeaderHeight(25);
	                                document.write(gridSerchGroup);
	                                gridSerchGroup.onRowDoubleClicked = function(event, row){
										try{
											$('userGroupId').value   = this.getCellText(2,row);
											$('userGroupCode').value = this.getCellText(0,row);
											$('userGroupDescription').value = this.getCellText(1,row);												
				                        	winUserGroup.hide();					 				                        	                       	
										}catch(error){}
									};
									gridSerchGroup.onSelectedRowsChanged=function(row){
										try{
											if(row!=''){
												$('userGroupId').value   = this.getCellText(2,row);
												$('userGroupCode').value = this.getCellText(0,row);
												$('userGroupDescription').value = this.getCellText(1,row);
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
		
		<div id="access-serchDiv" class="x-hidden">
        	<div class="x-window-header">
            	Access Branch
            </div>
        	<div id="serch-tab3">
            	<div class="x-tab" title="Search">
					<table style="width: 600px">							
						<tr>
							<td>
								<script>
									var myColumnsSerchAccess  	= ["<bean:message bundle="lable" key="screen.code"/>",
																   "<bean:message bundle="lable" key="screen.description"/>",
																   "<bean:message bundle="lable" key="screen.add"/>"];
		            				var strSerchAccess	       	= new AW.Formats.String; 
		            				var cellFormatSerchAccess 	= [strSerchAccess,strSerchAccess,strSerchAccess];
	                    			var gridSerchAccess		    = createAccessBranchBrowser(myColumnsSerchAccess,'popupgrid3',cellFormatSerchAccess);							                    			 			                    			
	                    			gridSerchAccess.setHeaderHeight(25);
	                                document.write(gridSerchAccess);
	                                gridSerchAccess.onRowDoubleClicked = function(event, row){
										try{
													                        				                        	
										}catch(error){}
									};
									gridSerchAccess.onSelectedRowsChanged=function(row){
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
		
		<script type="text/javascript">
			var winBranch;
			var winUserGroup;
			var winAccessBranch;
			
			Ext.onReady(function(){
			    
			    var button1 = Ext.get('ButtonBranchSerch');
			    button1.on('click', function(){
			        if(!winBranch){
			            winBranch = new Ext.Window({
			                el:'branch-serchDiv',
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
			                   		clearAccessBranch();
			                        winBranch.hide();
			                   	}
			                }]
			            });
			        }
			        
			        winBranch.show(this);
			        //var data = "&conditions=recordStatus&value=1";
			        $('old').value=$('branchCode').value;
			        getSerchData(gridSerchBranch,'branchService.do','');
			    });
			    
			    var button2 = Ext.get('ButtonUserGroupSerch');  
			    button2.on('click', function(){
			        if(!winUserGroup){
			            winUserGroup = new Ext.Window({
			                el:'userGroup-serchDiv',
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
			                        winUserGroup.hide();
			                        //if($('articleModelId').value!='0' && $('articleModelId').value!='')getGridData();
			                   	}
			                }]
			            });
			        }
			        winUserGroup.show(this);
			        //var data = "&conditions=recordStatus&value=1";
					getSerchData(gridSerchGroup,'userGroupService.do','');
			    });
			    
			    var button3 = Ext.get('branchesBtn');
			    button3.on('click', function(){
			        if(!winAccessBranch){
			            winAccessBranch = new Ext.Window({
			                el:'access-serchDiv',
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
			                        winAccessBranch.hide();			                         
							    	
							    	for(i=$('accessBranch').options.length-1;i>=0;i--){
								        $('accessBranch').remove(i);
								    }
								    
								    $('accessBranch').options[$('accessBranch').options.length] = new Option($('branchDescription').value,$('branchId').value);
								    
							        for(var i=0;i<gridSerchAccess.getRowCount();i++) { 
							            if(gridSerchAccess.getCellValue(2,i) && gridSerchAccess.getCellValue(3,i)!=$('branchId').value) { 
						                	$('accessBranch').options[$('accessBranch').options.length] = new Option(gridSerchAccess.getCellValue(1,i),gridSerchAccess.getCellValue(3,i));
							            } 
							        }							       							    			                        
			                   	}
			                }]
			            });
			        }
			        winAccessBranch.show(this);
			        
			        data = "dispatch=getBranchSelectedData";
					var mySearchRequest = new ajaxObject('branchService.do');
					mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
						if (responseStatus==200) {						
							var message =  eval('(' + responseText + ')');
							if(message['error']){
								alert(message['error']);
							}else{																
								setGridData(gridSerchAccess,message);
								
								for(var i=0;i<gridSerchAccess.getRowCount();i++) {									
						            //if(gridSerchAccess.getCellValue(2,i) && gridSerchAccess.getCellValue(3,i)!=$('branchId').value) { 
					                	gridSerchAccess.setCellValue(false, 2,i);
						            //} 
						        }
						        
						        for(var i=0;i<$('accessBranch').options.length;i++) {
						        	brId = $('accessBranch').options[i].value;
						        	text = $('accessBranch').options[i].text;
						        	
						        	for(var i=0;i<gridSerchAccess.getRowCount();i++) {																            
							            if(gridSerchAccess.getCellValue(3,i)==brId) { 							            
						                	gridSerchAccess.setCellValue(true, 2, i);
							            } 
							        }
						        }				
							}	
						}else {
				    	    alert(responseStatus + ' -- Error Processing Request'); 
					    }
					}
					mySearchRequest.update(data,'POST');			        
			    });	
			 });   
		</script>	    
  	</body>
</html:html>
