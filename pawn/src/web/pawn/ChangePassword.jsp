<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% try { %>
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
			var action = 2;
			
			var url = 'changePasswordService.do';	
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
							$('pawnerCode').value		   	= message[6];
							$('userName').value     		= message[0];
							$('password').value 			= message[11];
							$('confirmPassword').value 	    	= message[11];				
							$('pawnerName').value 			= message[7];
							$('recordId').value 			= message[12];					
							$('version').value 				= message[13];
							if(message[14] ='1'){
								$('isactive').checked= ture;
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
				$('pawnerCode').value		   	= '';
				$('userName').value     		= '';
				$('password').value 			= '';
				$('confirmPassword').value 			= '';				
				
				$('divPawnerCode').innerHTML 	= '';
				$('divUserName').innerHTML 		= '';
				$('divPassword').innerHTML 		= '';
				$('divComPassword').innerHTML 	= '';
												
				grid.setSelectedRows([]);
			}
			
			function getPawnerData(){};
			
			function getGridData(){
				 getSerchData(grid,'officerService.do','');		                               
			}
			
			function getCreateData(){
				var pawnerId 	= $('pawnerId').value;
				var pawnerCode	= $('pawnerCode').value;				
				var userName	= $('userName').value;
				var password	= $('password').value;
				var conPassword	= $('confirmPassword').value;
				var isActive ='0';
				if($('isactive').checked){
					isActive = '1';
				}
				
				return 	"&pawnerId=" + pawnerId + "&userName=" + userName + "&password=" + password +
						"&confirmPassword=" + conPassword;
			}
			
			function getChangedData(){
				var recordId = $('recordId').value;
				var version  = $('version').value;
				
				var str = getCreateData() + "&recordId=" + recordId + "&version=" + version + "&change=Yes";				
				return str;
			}
		
			function showValidationErrors(message){
				alert(message);
	       		for( var i =0; i < message.length ; i++){	 
	       			alert(message[i]['password']);
					if( message[i]['password'] )
	                    $('divPassword').innerHTML = message[i]['password'];
	                else if( message[i]['confirmPassword'] )
	                    $('divComPassword').innerHTML = message[i]['confirmPassword'];                
	            }    
	        }          
	       
		</script>
		
		<style>
			#firstGrid {height: 200px;width:720px;}
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
    	
    	<!-- update record -->
    	<logic:equal name="passwordchange" property="action" value="update">
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
											var myColumns = ["<bean:message bundle="lable" key="screen.username"/>",
											                 "<bean:message bundle="lable" key="screen.branch"/>",
															 "<bean:message bundle="lable" key="screen.usergroup"/>"];
				            				var str = new AW.Formats.String; 
				            				var cellFormat = [str,str,str];
			                    			var grid = createBrowser(myColumns,'firstGrid',cellFormat);			                    						                    						                    			
			                                document.write(grid);
			                                grid.onSelectedRowsChanged = function(row){	
			                                	if(row!='') { 
			                                		$('userName').value     		= grid.getCellText(0,row);
			                                		$('pawnerCode').value		   	= grid.getCellText(6,row);
			                                		$('pawnerName').value 			= grid.getCellText(7,row);			                                		
			                                		$('pawnerId').value		   		= grid.getCellText(8,row);													
													$('password').value 			= grid.getCellText(11,row);
													$('confirmPassword').value 		= grid.getCellText(11,row);												
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
										<input id="pawnerCode" name="pawnerCode" style="width: 80px" maxlength="8"  tabindex="1" disabled="disabled"
											onfocus="clearDivision('divPawnerCode')"											
											onblur="commonSearch('pawnerService.do','pawnerId','pawnerCode','pawnerName','getPawner','divPawnerCode')"/>										
										<input id="ButtonPawnerSerch" type="button" value="..." disabled="disabled"/>	
										<input id="pawnerName" name="pawnerName" size="50" disabled="disabled"/>										
										<font color="red">*</font><br/>
										<div id="divPawnerCode" class="validate"/>
									</td>
								</tr>
								<tr height="2px"></tr> 
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.username"/>&nbsp;
									</td>
									<td> 										
										<html:text property="userName" styleId="userName" size="17" maxlength="16" onfocus="clearDivision('divUserName')" disabled="true"/>
										<font color="red">*</font>
										<input type="checkBox" id="isactive"  align="middle" checked="checked" disabled="disabled"/>
										<bean:message bundle="lable" key="screen.isactive" />										
										<div id="divUserName" class="validate"/>
									</td>
								</tr>
								<tr height="2px"></tr> 
								<tr>
									<td width="20%" align="right">
										<bean:message bundle="lable" key="screen.newpassword"/>&nbsp;
									</td>
									<td> 										
										<html:password property="password" styleId="password" size="20" maxlength="16"
											onfocus="clearDivision('divPassword')" />
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
											onfocus="clearDivision('divComPassword')" />
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
  	</body>
</html:html>
<% 
} catch(Exception e)  { 
	System.out.println(e.getMessage());
}
%>