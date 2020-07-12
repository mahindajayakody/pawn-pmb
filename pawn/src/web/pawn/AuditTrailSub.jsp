<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html:form action="auditTrail.do">
	<input type="hidden" id="recordId" name="recordId"/>
	<input type="hidden" id="version" name="version"/>
	<input type="hidden" id="authorizeMode" name="authorizeMode"/>
	<table border="0">
		<tr>
			<td>
				<table width="100%" class="InputTable">
					<tr height="5px"></tr>
					<tr>
						<td width="10%" align="right">
							<bean:message bundle="lable" key="screen.officer"/>&nbsp;
						</td>
						<td>
							<input type="hidden" id="officerId" name="officerId" value="0"/>
							<html:text property="officerCode" styleId="officerCode" size="14" maxlength="14" disabled="true" styleClass=
											"READONLYINPUT" onblur="upperCase('officerCode')" onfocus="clearDivision('divOfficerCode')"/>
							<input id="ButtonOfficerCodeSearch" type="button" value="..." />
							<input type="text" size="50" id="officerName" readonly="readonly" class="READONLYINPUT"/>
							<font color="red">*</font><br/>
							<div id="divOfficerCode" class="validate"/>
						</td>						
					</tr>
					<tr height="1px"></tr>
					<tr>
						<td width="10%" align="right">
							<bean:message bundle="lable" key="screen.audit.date.from"/>&nbsp;
						</td>
						<td>
							<input type="text" id="fromDate" maxlength="12" size="14" name="fromDate" value="" styleId="fromDate" readonly="readonly" class="READONLYINPUT"
								onfocus="clearDivision('divToDate')" onkeyup="DateFormat(this,event);" onkeypress="DateFormat(this,event);"" 
								onkeyup="DateFormat(this,event);"/><img src="images/none.gif" width="0px">
							<input type="button" value="..." onClick="return showCalendar('fromDate');" />
							<img src="images/none.gif" width="5px">
							<bean:message bundle="lable" key="screen.audit.date.to"/>&nbsp;
							<input type="text" id="toDate" maxlength="12" size="14" name="toDate" value="" styleId="toDate" readonly="readonly" class="READONLYINPUT"
								onfocus="clearDivision('divToDate')" onkeyup="DateFormat(this,event);" onkeypress="DateFormat(this,event);"" 
								onkeyup="DateFormat(this,event);"/><img src="images/none.gif" width="2px">
							<input type="button" value="..."onClick="return showCalendar('toDate');" styleClass="READONLYINPUT" readonly="readonly" />
							<img src="images/none.gif" width="18px">
			 				<input type='button' value="<bean:message bundle="button" key="button.search"/> " class="buttonNormal" id="search" onclick="getGridData()"/>
						</td>
					</tr>
					<tr height="1px"></tr>
					<tr>
						<td width="10%" align="right"><bean:message bundle="lable" key="screen.audit.Type"/></td>
						<td>
							<select id="auditTrailTypes" name="auditTrailTypes">
								<option>All</option>
							    <c:forEach var="types" items="${requestScope.auditTrailTypes}">
							     <option>${types}</option>
							    </c:forEach>
							</select>
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
					<tr>
						<td colspan="2" align="center">
							<script>
								var myColumns = ["<bean:message bundle="lable" key="screen.audit.action"/>",
												 "<bean:message bundle="lable" key="screen.audit.Type"/>",
												 "<bean:message bundle="lable" key="screen.audit.date"/>",
												 "<bean:message bundle="lable" key="screen.audit.before"/>",
												 "<bean:message bundle="lable" key="screen.audit.after"/>"];
								var str = new AW.Formats.String;
								var num = new AW.Formats.Number;
								var cellFormat = [str, num, str];
								var auditTrailGrid = createBrowser(myColumns,'auditTrailGrid',cellFormat);
								document.write(auditTrailGrid);
								auditTrailGrid.onSelectedRowsChanged = function(row){
									if(row!='') {
										getRecordData(auditTrailGrid.getCellText(3,row), auditTrailGrid.getCellText(4,row));
									}
								};
								//getGridData();
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
					<tr>
						<td colspan="2" align="center" id="auditTrailDetailGridRow"></td>
					</tr>
				</table>
			</td>
		</tr>

		<tr height="5px"></tr>
		
	</table>
</html:form>