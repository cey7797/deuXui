<%@page import="com.clipsoft.clipreport.server.service.ClipReportExport"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileOutputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%

out.clear(); // where out is a JspWriter
out = pageContext.pushBody();

String report_key = request.getParameter("report_key");


//서버에 파일로 저장 할 때
File localFileSave = new File("c:\\test.html");
OutputStream fileStream = new FileOutputStream(localFileSave);

//클라이언트로 파일을 내릴 때
//response.setContentType("text/html");
//OutputStream fileStream = response.getOutputStream();

ClipReportExport.createExportForPartHTML5(request, report_key, fileStream, "UTF-8");
%>