<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.util.List"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.net.URL"%>
<%@page import="java.net.URLConnection"%>
<%@page import="java.io.OutputStreamWriter"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.File"%>
<%
out.clear();
out=pageContext.pushBody();

//크로스 도메인 설정
//response.setHeader("Access-Control-Allow-Origin", "*");

String ReportURL = "http://rexpert.clipsoft.kr/ClipReport/Clip.jsp";

StringBuffer params = new StringBuffer();

Map map = request.getParameterMap();

for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
	Map.Entry pairs = (Map.Entry) it.next();
	String key = (String) pairs.getKey();
	String[] value = (String[])map.get(key);
	if("oof".equalsIgnoreCase(key) || "data".equalsIgnoreCase(key)){
		params.append(key).append("=");
		params.append(URLEncoder.encode(value[0], "UTF-8")).append("&");
	}
	else{
			params.append(key).append("=").append(value[0]).append("&");
	}
}

ServletOutputStream outputStream = response.getOutputStream();
OutputStreamWriter wr = null;
InputStream inputStream = null;
try{
	URL url = new URL(ReportURL);
	HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	conn.setDoInput(true);
	conn.setDoOutput(true);
	conn.setUseCaches(false);
	conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
	if(null != session.getAttribute("ClipRSend")){
		conn.setRequestProperty("Cookie","JSESSIONID=" + session.getAttribute("ClipRSend"));
	}

	wr = new OutputStreamWriter(conn.getOutputStream());
	wr.write(params.toString());
	wr.flush();
	
	response.setContentType(conn.getContentType());
	String contentDisposition = conn.getHeaderField("Content-Disposition");
	
	if(null != contentDisposition){
		response.setHeader("Content-Disposition", contentDisposition);
	}
	
	Map httpResponseHeaders = conn.getHeaderFields();
	Iterator ite = httpResponseHeaders.keySet().iterator();
	while(ite.hasNext()) {
		String headerFieldKey = (String) ite.next();
		 if ("Set-Cookie".equalsIgnoreCase(headerFieldKey)) {
			  List keyList = (List) httpResponseHeaders.get(headerFieldKey);
	          Iterator listIter = keyList.iterator();
	          while (listIter.hasNext()) {
	        	 String listValue = (String) listIter.next();
                 String[] fields = listValue.split(";\\s*");
                 String cookieValue = fields[0];
                 if(-1 != cookieValue.indexOf("JSESSIONID")){
                	 String[] cookieValues = cookieValue.split("=");
                	 //Cookie cookie = new Cookie(cookieValues[0], cookieValues[1]);
                	 //String path = request.getParameter("path");
					 //cookie.setPath(path);
	             	 //response.addCookie(cookie);
	             	if(null == session.getAttribute("ClipRSend")){
	             		session.setAttribute("ClipRSend", cookieValues[1]);
	             	}
                 }
                 else{
                	 String[] cookieValues = cookieValue.split("=");
                	 session.setAttribute("ClipRSend", cookieValues[1]);
                 }
	          }
		
			 /*
			 for(int ic=0; ic<value.length; ic++){
				 String headerValue = value[ic];
                 String[] fields = headerValue.split(";\\s*");
                 String cookieValue = fields[0];
                 if(-1 == cookieValue.indexOf("JSESSIONID")){
                	 String[] cookieValues = cookieValue.split("=");
                	 Cookie cookie = new Cookie(cookieValues[0], cookieValues[1]);
					 String path = request.getParameter("path");
	             	 cookie.setPath(path);
	             	 response.addCookie(cookie);
                 }
                 else{
                	 String[] cookieValues = cookieValue.split("=");
                	 session.setAttribute("ClipRSend", cookieValues[1]);
                 }
             }
			 */
		 }
	}
	inputStream = conn.getInputStream();
	
	byte[] buffer = new byte[8 * 1024];
       int bytesRead = -1;
        
       while ((bytesRead = inputStream.read(buffer)) != -1) {
       	outputStream.write(buffer, 0, bytesRead);
       }
       wr.close();
	inputStream.close();
	outputStream.close();
}catch(Exception e){
	e.printStackTrace();
	wr = null;
	inputStream = null;
	outputStream = null;
}
	// Report
%>