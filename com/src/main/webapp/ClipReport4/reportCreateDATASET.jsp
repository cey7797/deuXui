<%@page import="java.io.File"%>
<%@page import="com.clipsoft.clipreport.server.service.ReportUtil"%>
<%@page import="com.clipsoft.clipreport.oof.connection.*"%>
<%@page import="com.clipsoft.clipreport.oof.*"%>
<%@page import="com.clipsoft.org.apache.commons.lang.StringEscapeUtils"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");

	String crfName   = (request.getParameter("crfName")   == "" || request.getParameter("crfName")   == null ? "CLIP" : request.getParameter("crfName") );
	String crfParams = (request.getParameter("crfParams") == "" || request.getParameter("crfParams") == null ? "" : request.getParameter("crfParams") );
	String crfData = (request.getParameter("crfData") == ""   || request.getParameter("crfData")   == null ? "" : request.getParameter("crfData") );
	
//System.out.println("crfParams:" + crfParams);	
	String[] crfParamsArr = crfParams.split("\\|");
	
	String report ="%root%/crf/" + crfName + ".crf";
	
	//OOFDocument oof = OOFDocument.newOOF();
	//OOFFile crfFile1 = oof.addFile("reb.root", report);
	//crfFile1.addConnectionData("*", crfDbName);
	
	OOFDocument oof      = OOFDocument.newOOF();
	OOFFile crfFile1     = oof.addFile("reb.root", report);
	OOFConnectionMemo mc = oof.addConnectionMemo("*", crfData);
	mc.addContentParamXML("*", "euc-kr", "{%dataset.xml.root%}");

	
	//crf params
	for(String param : crfParamsArr){
		String[] temp = param.split(":");
		String name  = ""; 
		String value = "";
		
		if( temp.length == 0 ){
			name  = "";
			value = "";
		} else if( temp.length == 1 ){
			name  = temp[0];
			value = "";
		} else if( temp.length == 2 ){
			name  = temp[0];
			value = temp[1];
		}

		crfFile1.addField(name, value);
	}
	
	//System.out.println("oof:" + oof.toString());

%><%@include file="./Property.jsp"%><%
String resultKey =  ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
%>
<%=resultKey%>