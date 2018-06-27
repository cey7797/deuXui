<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ page language="java"%>
<%@ page import="java.net.URLDecoder.*" %>
<%@ page import="java.net.URLEncoder.*" %>
<%@ page import="java.io.BufferedInputStream"%>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="java.io.File"%>
<%@ page import="java.io.IOException"%>
<%@ page import="javax.servlet.ServletException"%>
<%@ page import="javax.servlet.ServletOutputStream"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ page import="javax.servlet.http.HttpServletResponse"%>
<%@ page import="deu.comm.unione.uniAuth"%>
<%
uniAuth uniauth = new uniAuth();
String userGb = uniauth.checkAuth(request, response);
System.out.println("userGb : "+userGb);
if("SUCESS".equals(userGb)) {
	//String contextRealPath = request.getSession().getServletContext().getRealPath("/");
	String contextRealPath = "c:/";
	
	String userAgent = request.getHeader("User-Agent");
	String isRuntime = "N";

    if (userAgent.indexOf("nexacroplatform14") > -1) {
    	isRuntime = "Y";
    }

	String savePath = request.getParameter("path");
	String filename = request.getParameter("fileName");
	String saveFileName = request.getParameter("saveFileName");

	savePath = savePath.replaceAll("\n","");
	savePath = savePath.replaceAll("\r","");
	savePath = savePath.replaceAll("\\.", "");
	savePath = savePath.replaceAll("\\,", "");
	
	filename = filename.replaceAll("\n","");
	filename = filename.replaceAll("\r","");
	
	saveFileName = saveFileName.replaceAll("\n","");
	saveFileName = saveFileName.replaceAll("\r","");
	
	//filename = new String(filename.getBytes("iso8859-1"), "utf-8");
	//saveFileName = new String(saveFileName.getBytes("iso8859-1"), "utf-8");
	
	String filePath = contextRealPath + savePath + File.separator + saveFileName;

	byte[] buffer = new byte[1024];
	
	ServletOutputStream out_stream	= null;
	BufferedInputStream in_stream	= null;
	
	File fis = new File(filePath);
	
	if(fis.exists())
	{
		try
		{
			response.setContentType("application/octet;charset=utf-8");
			
			if(isRuntime != null && isRuntime.equals("N")) {
				
				if (userAgent.indexOf("MSIE") > -1) 
				{
					String encodeFileName = java.net.URLEncoder.encode(filename, "utf-8").replaceAll("\\+", "\\ ");
					response.setHeader("Content-Disposition", "attachment; filename = \"" + new String(encodeFileName.getBytes("utf-8"), "ISO8859_1") + "\"");
					response.setHeader("Content-Transper-Encoding", "binary");
				}
				else
				{
					response.setHeader("Content-Disposition", "attachment; filename = \"" + new String(filename.getBytes("utf-8"), "ISO8859_1") + "\"");
				}				

				response.setHeader("Content-Transper-Encoding", "binary");
				response.setHeader("Content-Length", "" + String.valueOf(fis.length()));
				response.setHeader("Pargma", "no-cache");
				response.setHeader("Expires", "-1");
				
			} else {
				response.setHeader("Content-Disposition", "attachment; filename = \"" + new String(filename.getBytes("utf-8"), "ISO8859_1") + "\"");
				response.setHeader("Content-Transper-Encoding", "binary");
				response.setHeader("Content-Length", "" + String.valueOf(fis.length()));				
				
			}

			out.clear();
			out = pageContext.pushBody();	
			
			out_stream = response.getOutputStream();
			in_stream = new BufferedInputStream(new FileInputStream(fis));
	
			int n = 0;
			while ((n = in_stream.read(buffer, 0, 1024)) != -1) 
			{
				out_stream.write(buffer, 0, n);
			}
		} 
		catch (Exception e) 
		{
			e.getMessage();
		} 
		finally 
		{
			if (out_stream != null) {
				try {
					out_stream.close();
				} catch (IOException e) {}
			}
			if (in_stream != null) {
				try {
					in_stream.close();
				} catch (IOException e) {}
			}
		}
	}
	else
	{
		response.sendRedirect("unknownfile");
	}
}else{
	response.sendRedirect("unknownfile");
}
%>
