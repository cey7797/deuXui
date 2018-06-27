<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page language="java"%>
<%@ page import="java.io.BufferedInputStream"%>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="java.io.File"%>
<%@ page import="java.io.IOException"%>
<%@ page import="javax.servlet.ServletException"%>
<%@ page import="javax.servlet.ServletOutputStream"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ page import="javax.servlet.http.HttpServletResponse"%>
<%@ page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%

String contextRealPath = "ust_file\\dext5Files\\";
// String contextRealPath = request.getSession().getServletContext().getRealPath("/").replace("\\com", "\\") + "ust_file\\dext5Files\\" + ds.getString(0, "fileNm") + "." + ds.getString(0, "fileType");
// String savePath = contextRealPath + "file";
String savePath = contextRealPath;

String name = request.getParameter("file");
String saveName = request.getParameter("file");
// String name = ds.getString(0, "fileNm") + "." + ds.getString(0, "fileType");

String filename = new String(name.getBytes("iso8859-1"), "UTF-8");

	byte[] buffer = new byte[1024];
	ServletOutputStream out_stream = null;
	BufferedInputStream in_stream = null;
    File fis = new File(savePath + "/" + filename);
	// File.separator;
if(fis.exists()){
	try{
		response.setContentType("utf-8");
		response.setContentType("application/octet;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + filename);
		
		out.clear();
		out = pageContext.pushBody();
		
   		out_stream = response.getOutputStream();
		in_stream = new BufferedInputStream(new FileInputStream(fis));

		int n = 0;
		while ((n = in_stream.read(buffer, 0, 1024)) != -1) {
			out_stream.write(buffer, 0, n);
		}// while
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (in_stream != null) {
			try {
				in_stream.close();
			} catch (Exception e) {}
		}
		if (out_stream != null) {
			try {
				out_stream.close();
			} catch (Exception e) {}
		}
	}
}else{
		response.sendRedirect("unknownfile");
}
%>
