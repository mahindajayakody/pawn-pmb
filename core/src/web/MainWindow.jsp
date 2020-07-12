<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Officer Controls v 1.0</title>

    <link rel="StyleSheet" type="text/css" href="css/dtree.css"></link>
	<script type="text/javascript" src="js/dtree.js"></script>
	<script type="text/javascript" src="js/ajax.js"></script>
    <script type="text/javascript" src="js/common.js"></script>

    <style>
        .heading {
			BORDER-RIGHT: #aca899 1px solid; BORDER-TOP: #ffffff 1px solid; PADDING-LEFT: 3px; FONT: 11px Trebuchet MS, Tahoma,Arial,Verdana,San-serif; BORDER-LEFT: #ffffff 1px solid; WIDTH: 100%; COLOR: #000000; PADDING-TOP: 1px; BORDER-BOTTOM: #aca899 1px solid; HEIGHT: 20px; BACKGROUND-COLOR: #ece9d8
    	}
    	BODY {
	    	MARGIN: 0px; FONT-SIZE: 12px; FONT-FAMILY: Trebuchet MS ;background-image: url('images/back.jpg');
    	}

		.DefTab {font-family:Tahoma;font-size:12px;color:#000000;  background-color:#ece9d8;background-image:url(images/tabd-s.gif);background-repeat:no-repeat;width:99px;height:19px;TEXT-ALIGN: center;BORDER-BOTTOM: #91a7b4 1px solid;CURSOR: hand;BORDER-RIGHT-STYLE: none ;}
		.HitTab{font-family:Tahoma;font-size:12px;color:#000000; background-color:#ece9d8;background-image:url(images/tabc-s.gif);background-repeat:no-repeat;width:99px;height:19px;TEXT-ALIGN: left;BORDER-BOTTOM-STYLE: none ;cursor: hand;}
		.MouseoverTab{font-family:Tahoma;font-size:12px;color:#000000; background-color:#ece9d8;background-image:url(images/tabc-s.gif);background-repeat:no-repeat;width:99px;height:19px;TEXT-ALIGN: center;cursor: hand;BORDER-BOTTOM: #91a7b4 1px solid}
		.RemoveTab{background-color:#ece9d8;background-repeat:no-repeat;width:100px;height:19px;TEXT-ALIGN: center;BORDER-BOTTOM: #91a7b4 1px solid}
		.TreeTableF{height:648px}
		.TreeTableS{height:575px}

		.TreeTableIEF{height:620px}
		.TreeTableIES{height:560px}

		#container { width: 100%; position: relative; height: 428px; visibility: hidden; }
		.LTD1S { visibility: visible; overflow: auto; width:215px; height: 423px;position: absolute;top: 5px;left: 10px}
		.LTD1F { visibility: hidden; overflow: auto; width:215px; height: 630px;position: absolute;}

		.LTD1IES { visibility: hidden; overflow: auto; width:215px; height: 560px;position: absolute;}
		.LTD1IEF { visibility: hidden; overflow: auto; width:215px; height: 620px;position: absolute;}

		#LTD2 { visibility: hidden; overflow: auto; width:215px; height: 573px;position: absolute;}


		.on{
			background-color: #040893;
			color:#ffffff;
			font-family: Verdana, Geneva, Arial, Helvetica, sans-serif;
			font-size:8pt;
		}

		.list{
			font-size:8pt;
			font-family: Verdana, Geneva, Arial, Helvetica, sans-serif;
			width:100%;
		}

		.bar {
			background-color: #DEECFD;
			color: #000000;
			cursor: move;
			width:236px;
			font-weight: bold;
			padding: 2px 2px 2px 1em;
		}

		.hideTree{
			visibility: hidden;
			position: absolute;
		}
		.showTree{
			visibility: visible;
			position: inherit;
		}
	</style>

	<script>

		function onClose(){
			alert('hello');
		}

		function loadSubTree(strUrl){
        	document.getElementById("subTree").src=strUrl;
       	}

		function clearOtherdata(){
			document.getElementById('footer').style.display="";
			document.getElementById('footer').style.visibility="visible";
			document.getElementById("mainbody").height="560px";
			document.getElementById("footer").height="42px";
			document.getElementById('footer').src="body.htm";
		}

		function changeBranch(val){
			data = "dispatch=changeBranch&branchId=" + val;
			var mySearchRequest = new ajaxObject('loginProcess.do');
			mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
				if (responseStatus==200) {
					var message =  eval('(' + responseText + ')');
					document.getElementById('dateTD').innerHTML = message['logindate'];
					document.getElementById('mainbody').src = "body.jsp";
					document.getElementById('footer').src = "body.htm";
				}else {
		    	    alert(responseStatus + ' -- Error Processing Request');
			    }
			}
			mySearchRequest.update(data,'POST');
		}

		o = new dTree('o');
		o.clearCookie();

		<c:forEach items="${dataList}" var="data">
			var access = '<c:out value="${data.access}" />';

			var id   = <c:out value="${data.systemProgramId}" />;
			var pId  = <c:out value="${data.parentId}" />;
			var desc = '<c:out value="${data.description}" />';
			var url  = '<c:out value="${data.url}" />';


			if(pId==-1){
				o.add(id,pId,desc,'','','mainbody','clearOtherdata()');
			}else if(pId=='0' && access!='0'){
				o.add(id,pId,desc,'','','mainbody','clearOtherdata()');
			}else if(access!='' && access!='0'){
				o.add(id,pId,desc,(url+'?evn=' + access),'','mainbody','clearOtherdata()');
			}
		</c:forEach>
	</script>
</head>
<body onunload="window.opener.logOut();">
    <table height="100%" cellspacing="0" cellpadding="0" width="1024px" border="0" id="Table1" align="center">
        <form id="frm" action="TreeAction.do"><input id="p1" type="hidden" value="" name=""/><input id="p2" type="hidden" value="" name=""/>
        </form>
        <tr id="pawn_header_tr">
            <td class="pawn_heade" align="right" id="pawn_heade_td">
            	<table cellspacing="0" cellpadding="0" width="100%" border="0"  height="80px">
            		<tr>
            			<td align="left">
            				<table cellpadding="0" cellspacing="0">
            					<tr>
            						<td width="10px"></td>
            						<td width="210px">
            							<table cellpadding="0" cellspacing="0" width="100%">
			            					<tbody>
			            						<tr>
			            							<td align="right" width="100px">
			            								<b>Login Name :-</b>&nbsp;
			            							</td>
							            			<td align="left">
							            				<c:out value="${sessionScope.LOGIN_KEY.loginName}" ></c:out>
													</td>
												</tr>

								            	<tr>
								            		<td align="right" width="100px">
			            								<b>Login Branch :-</b>&nbsp;
			            							</td>
								            		<td  align="left">
														<select style="width:120px;height:15px;font-size: 9px" onchange="changeBranch(this.value)">
							            			      	<c:forEach var="branch" items="${sessionScope.LOGIN_KEY.accessBranchList}">
													    		<option value="${branch.branchId}" style="font-size: 9px">${branch.branchName}</option>
													      	</c:forEach>
														</select>
				            						</td>
				            					</tr>

				            					<tr>
				            						<td align="right" width="100px">
			            								<b>Login Date :-</b>&nbsp;
			            							</td>
								            		<td align="left" id="dateTD">
								            			<f:formatDate value="${sessionScope.LOGIN_KEY.loginDate}" pattern="dd/MM/yyyy" />
				            						</td>
				            					</tr>
				            				</tbody>
				            			</table>
            						</td>
            						<td style="background-image: url('images/banner.jpg');height: 75px" width="775px">&nbsp;</td>

            					</tr>
            				</table>

            			</td>
            			<td align="right" >

	            		</td>
            			<td width="5px"></td>
            		</tr>
            	</table>
            </td>
        </tr>

        <tr>
            <td valign="top">
                <table cellspacing="0" cellpadding="0" width="100%" id="Table3" border="0">
                    <tr>
                        <td class="showTree" width="250" rowspan="2" valign="top" id="treeTd">
                       	    <table height="100%" border="0" cellspacing="0" cellpadding="0" width="226" align="center" id="Table4">
                                <tr>
                                    <td style="height: 7px">
                                    </td>
                                </tr>
                                <tr>
                                    <td valign="top">
                                        <table cellspacing="0" cellpadding="0" width="100%" align="center" id="Table5">
                                            <tr>
                                                <td valign="top">
                                                    <table border="0" cellspacing="0" cellpadding="0" >
                                                        <!-- <tr id="tabTR">
							                                <td class="HitTab" id="TD1" onmouseover="tabOnMouseOver(this)" onmouseout="tabOnMouseOut(this)" onclick="tabOnClick(this)">&nbsp;&nbsp;&nbsp;Pawn Solution</td>
							                                <td class="HitTab" id="TD2" onmouseover="tabOnMouseOver(this)" onmouseout="tabOnMouseOut(this)" onclick="tabOnClick(this)">&nbsp;</td>
							                                <td style="background-repeat:no-repeat;width:20px;height:19px;TEXT-ALIGN: center;BORDER-BOTTOM: #91a7b4 1px solid">&nbsp;</td>
                                                        </tr> -->
                                                        <tr>
                                                            <td colspan="3" style="border: #91a7b4 1px solid;  width: 100%; height: 100%; " valign="top">
                                                                <table width="220" height="438" border="0" cellspacing="0" cellpadding="0" id="TreeTable">
                                                                    <tr>
                                                                        <td valign="top" style="background-color: #FFFFFF;">
                                                                            <table border="0" cellspacing="0" cellpadding="0">
                                                                                <tr>
                                                                                    <td colspan="2" style="height: 10px">
                                                                                    </td>
                                                                                </tr>

                                                                                <tr>
                                                                                    <td>
                                                                                        <DIV id="container">
									                                                        <DIV id="LTD1" class="LTD1S"><jsp:include flush="true" page="tree.jsp"/></DIV>
									                                                        <!-- <DIV id="LTD2" ><iframe width="220" height="560" frameborder="0" name="subTree" id="subTree" src="blank_tree.htm"></iframe></DIV> -->
								                                                        </DIV>
                                                                                    </td>
                                                                                </tr>
                                                                            </table>
                                                                        </td>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>

                                <tr>
                                     <td style="background-image: url('images/logo.jpg');height: 150px;border: #91a7b4 1px solid;" width="150px">
                                     </td>
                                 </tr>
                            </table>
                        </td>
                        <td id="bodyTd" valign="top" height="100%">
                        	<table cellpadding="0" cellspacing="0" width="100%">
                        		<tr height="7px"></tr>
                        		<tr>
                        			<td valign="top" height="100%">
			                           	<iframe width="100%" height="600px" src="body.jsp" frameborder="0" name="mainbody"  id="mainbody" style="overflow: scroll-y"> </iframe>
			                           	<iframe width="100%" height="0px" src="body.jsp"  scrolling="no" frameborder="0" name="footer" id="footer"> </iframe>
                        			</td>
                        		</tr>
                        	</table>
                        </td>
                    </tr>
              </table>
          </tr>
    </table>
</body>
</html>
