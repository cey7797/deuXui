<%@page import="com.clipsoft.clipreport.oof.OOFFile"%>
<%@page import="com.clipsoft.clipreport.oof.OOFDocument"%>
<%@page import="java.io.File"%>
<%@page import="com.clipsoft.clipreport.server.service.ReportUtil"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
OOFDocument oof = OOFDocument.newOOF();
OOFFile file = oof.addFile("crfe.root", "%root%/crf/CLIP.crfe");
%><%@include file="Property.jsp"%><%
//세션을 활용하여 리포트키들을 관리하지 않는 옵션
//request.getSession().setAttribute("ClipReport-SessionList-Allow", false);
String resultKey =  ReportUtil.createEForm(request, oof, "false", "false", request.getRemoteAddr(), propertyPath);
//oof 문서가 xml 일 경우 (oof.toString())
//String resultKey =  ReportUtil.createEForm(request, oof.toString(), "false", "false", "localhost", propertyPath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>EForm</title>
<meta name="viewport" content="width=800, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" type="text/css" href="./css/clipreport.css">
<link rel="stylesheet" type="text/css" href="./css/eform.css">
<link rel="stylesheet" type="text/css" href="./css/UserConfig.css">
<link rel="stylesheet" type="text/css" href="./css/font.css">
<script type='text/javascript' src='./js/jquery-1.11.1.js'></script>
<script type='text/javascript'
	src='./js/clipreport.js?ver=1.0'></script>
<script type='text/javascript' src='./js/UserConfig.js'></script>
<script type='text/javascript'>
var urlPath = document.location.protocol + "//" + document.location.host;
	
function html2xml(divPath){	
    var eformkey = "<%=resultKey%>";
	var eform = createImportJSPEForm(urlPath + "/ClipReport4/Clip.jsp", eformkey, document.getElementById(divPath));
	
	/*
	eform.setSignOffset(100, 100);
	eform.setSignStaticPosition("50px","50%");
	eform.setNecessaryEnabled(true);
	eform.setEndSaveButtonEvent(function (){
		alert(JSON.stringify(eform.getEFormData()));
	});
	*/
	
	eform.setEndReportEvent(function(){ 
		userEFormEvent(eform);
	});
	
	eform.view();
}

function userEFormEvent(eform){
	var inputGroup = eform.findGroup("Inputbox1");
	var checkGroup = eform.findGroup("inputcheck1");
	var radioGroup = eform.findGroup("inputradio1");
	var signGroup = eform.findGroup("clipsign1");
	
	var checkControl = checkGroup.getControlList()[0];
	var inputControl = inputGroup.getControlList()[0];
	
	checkControl.onCheckedEvent(function(){
		inputControl.setValue("checked");
	});
	checkControl.onUnCheckedEvent(function(){
		inputControl.setValue("unChecked");
	});		
}
</script>
</head>
<body onload="html2xml('targetDiv1')">
<div id='targetDiv1' style='position:absolute;top:5px;left:5px;right:5px;bottom:5px;'></div>
</body>
</html>
