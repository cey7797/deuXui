<%@page import="com.clipsoft.clipreport.export.option.JPGOption"%>
<%@page import="com.clipsoft.clipreport.oof.OOFFile"%>
<%@page import="com.clipsoft.clipreport.oof.OOFDocument"%>
<%@page import="com.clipsoft.clipreport.server.service.ClipReportExport"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileOutputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../Property.jsp"%>
<%

String is_svg = request.getParameter("is_svg");
if(null == is_svg && !"true".equals(is_svg)){
	is_svg = "jpg";
}
out.clear(); // where out is a JspWriter
out = pageContext.pushBody();


OOFDocument oof = OOFDocument.newOOF();
OOFFile file = oof.addFile("crf.root", "%root%/crf/CLIP.crf");

//서버에 파일로 저장 할 때
//File localFileSave = new File("c:\\test.jpg");
//OutputStream fileStream = new FileOutputStream(localFileSave);

//클라이언트로 파일을 내릴 때
//String fileName = "report.jpg";
//response.setContentType("application/octet-stream; charset=UTF-8");
//response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");
//OutputStream fileStream = response.getOutputStream();

//클라이언트 브라우져에서 바로 보는 방법(헤더 변경)
if(is_svg.equals("jpg")){
	response.setContentType("image/jpg");
}
else{
	response.setContentType("image/svg+xml");
}
OutputStream fileStream = response.getOutputStream();

JPGOption jpgOption = new JPGOption();
//X DPI 설정
jpgOption.setDpiX(96);
//Y DPI 설정
jpgOption.setDpiY(96);
//품질(1~100)
jpgOption.setQuality(100);
//회전
jpgOption.setRotate90(false);

int pageNumber = 1;

int statusType = ClipReportExport.createExportForPageImage(request, fileStream, propertyPath, oof, jpgOption, pageNumber, is_svg);
//statusType == 0 정상적인 출력
//statusType == 1 인스톨 오류
//statusType == 2 oof 문서 오류
//statusType == 3 리포트 엔진 오류
//statusType == 4 img 출력 오류
%>