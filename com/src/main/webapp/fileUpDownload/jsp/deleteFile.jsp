<%@ page contentType="text/html;charset=utf-8" %>
<%@ page language="java"%>
<%@ page import="com.nexacro.xapi.data.*" %>
<%@ page import="com.nexacro.xapi.tx.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.FileOutputStream" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.sql.*"%>
<%@ page import="deu.comm.unione.service.impl.CommonDAO"%>
<%@ page import="deu.comm.unione.uniAuth"%>
<%
uniAuth uniauth = new uniAuth();
String userGb = uniauth.checkAuth(request, response);
System.out.println("userGb : "+userGb);
if("SUCESS".equals(userGb)) {
	final CommonDAO commonDAO = new CommonDAO();

	PlatformData platformData = new PlatformData();
	HttpPlatformRequest platformRequest = new HttpPlatformRequest(request);
	
	platformRequest.receiveData();
	PlatformData pData = platformRequest.getData(); 
	
	DataSetList in_dl = new DataSetList();     //input dataset list
	in_dl = pData.getDataSetList();  // dataset list	
	
	DataSet in_ds = in_dl.get("input"); //Dataset
	
	String driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	String url = "jdbc:sqlserver://113.198.254.48:1433;DatabaseName=ADMI";
	String username = "deubudacc";
	String password = "budacc@dPtks";
	
	//String contextRealPath = request.getSession().getServletContext().getRealPath("/");
	String contextRealPath = "c:/";
	String path = request.getParameter("path");
	String savePath = contextRealPath + path;	//경로를 따로 주고 싶을 때 savePath를 변경해야 한다.
	String fileMasterNo = "";
	
	if(savePath.indexOf("?") >= 0){
		fileMasterNo = savePath.substring(savePath.indexOf("?")+14);
	}
	
	String fileSeq = request.getParameter("FileSeq");
	String returnString = "";
	boolean succ = true;
	
	for(int i=0 ; i<in_ds.getRowCount(); i++) 
	{
		String fileRealNm = in_ds.getString(i, "savefilename");
		
		if (!fileRealNm.equals("")) 
		{
			if ( returnString.length() > 0 ) 
			{
				returnString += "\r\n";
			}
			
			try 
			{
				String realSavePath = savePath.substring(0, savePath.indexOf("?"));
				File f = new File(realSavePath + File.separator, fileRealNm);
				if (f.exists()) 
				{
					if (f.delete()) 
					{
						returnString += "'" + fileRealNm + "' Delete Success";
						
						//file table 정보 삭제
						Connection conn = null;
						PreparedStatement pstmt = null;
						ResultSet rs = null;
						int resultCount = 0;
						
						String qry1 = "DELETE FROM COM_FILE_SUB WHERE FILE_MASTER_NO = ? AND FILE_SEQ = ?";
						String qry2 = "DELETE FROM COM_FILE WHERE (SELECT COUNT(1) AS CNT FROM COM_FILE A, COM_FILE_SUB B WHERE A.FILE_MASTER_NO = B.FILE_MASTER_NO AND A.FILE_MASTER_NO = ?) = 0 AND FILE_MASTER_NO = ?";
						
						try{
							Class.forName(driverClassName);		
							conn = DriverManager.getConnection(url, username, password);
							
							pstmt = conn.prepareStatement(qry1);
							
							pstmt.clearParameters();
							
							pstmt.setString(1, fileMasterNo);
							pstmt.setString(2, fileSeq);
							
							resultCount = pstmt.executeUpdate();
							
							if(resultCount > 0) { //마스터에 정상적으로 인서트된 경우
								pstmt = null;
								pstmt = conn.prepareStatement(qry2); //디테일 쿼리
								pstmt.clearParameters();
								
								pstmt.setString(1, fileMasterNo);
								pstmt.setString(2, fileSeq);
								pstmt.executeUpdate();
							}
						} catch (Exception e){
							out.println("Error." + e.toString());
						} finally {
							if(pstmt != null)try{pstmt.close();}catch(SQLException ex){}
							if(conn != null) try { conn.close(); } catch(Exception e) {}
						}
					} 
					else 
					{
						succ = false;
						returnString += "'" + fileRealNm + "' Delete failed";
					}
				} 
				else 
				{
					succ = false;
					returnString += "'" + fileRealNm + "' File not available";
				}
			} 
			catch(Exception e) 
			{
				succ = false;
				returnString += "'" + fileRealNm + "' " + e;
	    	}
		}
	}
	
	platformData.addDataSet(in_ds);
	VariableList varList = platformData.getVariableList();
	
	if (succ) 
	{
		varList.add("ErrorCode", "0");
		varList.add("ErrorMsg", "SUCC");
	} 
	else 
	{
		varList.add("ErrorCode", "-1");
		varList.add("ErrorMsg", returnString);
	}
	
	HttpPlatformResponse pRes = new HttpPlatformResponse(response, PlatformType.CONTENT_TYPE_XML, "UTF-8"); 
	pRes.setData(platformData);
	pRes.sendData();
}else{
	response.sendRedirect("unknownfile");
}
%>
