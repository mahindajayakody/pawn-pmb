<%@ taglib uri="http://struts.apache.org/tags-bean"  prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>

<html:html>
	<head>
		<title>'Pawn Article Sumary.jsp'</title>
		<link rel="StyleSheet" type="text/css" href="<html:rewrite forward="commonCSS"/>"></link>
		<link rel="StyleSheet" type="text/css" href="<html:rewrite forward="awCSS"/>"></link>
		<link rel="StyleSheet" type="text/css" href="<html:rewrite forward="calendarCSS"/>"></link>
		<script type="text/javascript" src="<html:rewrite forward="commonJS"/>"></script>
		<script type="text/javascript" src="<html:rewrite forward="ajaxJS"/>"></script>
		<script type="text/javascript" src="<html:rewrite forward="awJS"/>"></script>
		<script type="text/javascript" src="<html:rewrite forward="calendarJS"/>"></script>
		

		<script type="text/javascript">
			
			function printDocument(){
				var userName = "${sessionScope.LOGIN_KEY.loginName}";
		    	url = "pawnArticleSummary.do?dispatch=print&reportName=/pawn/reports/pawnArticleSummary&headless=false" + "&jpUserName=" + userName;
				theHeight=500;
				theWidth=800;
				var theTop=(screen.height/2)-(theHeight/2);
				var theLeft=(screen.width/2)-(theWidth/2);
				var features='height='+theHeight+',width='+theWidth+',top='+theTop+',left='+theLeft+",status=yes,scrollbars=no,resizable=yes";
				window.open(url,"PrintPreview",features);
		    }
			
		</script>
		<style>
			table.toolTable { 
			    border: #91a7b4 1px solid;
			    width: 740px;
			    height:30px;
			    background-color:#FFFFFF;
			    bottom: 0px;
			    position: absolute;
			}	
			
			#firstGrid {height: 310px;width:720px;}
			#firstGrid .aw-row-selector {text-align: center}
			#firstGrid .aw-column-0 {width: 120px;}
			#firstGrid .aw-column-1 {width: 555px;}	
			#firstGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
			#firstGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}
		</style>

	</head>
	
	<body>
		<h1 align="center">Pawn Article Summary</h1>
		<table class="toolTable" id="toolbar">
			<tr>
				<td align="center">
					<input type='button' value="<bean:message bundle="button" key="button.print"/> " class="buttonNormal" id="Print" onclick="printDocument()"/> 					
				</td>
			</tr>
		</table>
	</body>
</html:html>
