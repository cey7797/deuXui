<%@ page contentType="text/html;charset=utf-8" %>
<%@ page language="java"%>
<%@ page import="com.nexacro.xapi.data.*" %>
<%@ page import="com.nexacro.xapi.tx.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.FileOutputStream" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.sql.*"%>
<% 
	PlatformData platformData = new PlatformData();
	HttpPlatformRequest platformRequest = new HttpPlatformRequest(request);
	
	platformRequest.receiveData();
	PlatformData pData = platformRequest.getData(); 
	
	DataSetList in_dl = new DataSetList();     //input dataset list
	in_dl = pData.getDataSetList();  // dataset list	
	
	DataSet in_ds = in_dl.get("input"); //Dataset
	
	String contextRealPath = request.getSession().getServletContext().getRealPath("/");
	String path = request.getParameter("path");
	String savePath = contextRealPath + path;	//경로를 따로 주고 싶을 때 savePath를 변경해야 한다.
	String fileMasterNo = savePath.substring(savePath.indexOf("?")+14);
	String fileSeq = request.getParameter("FileSeq");
	String returnString = "";
	boolean succ = true;
	
	System.out.println("delete path==>"+path);
	System.out.println("delete savePath==>"+savePath);
	System.out.println("getRowCount==>"+in_ds.getRowCount());
	System.out.println("fileMasterNo==>"+fileMasterNo);
	System.out.println("fileSeq==>"+fileSeq);
	
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
						
						String qry1 = "DELETE FROM UST_COM.COM_FILE_SUB WHERE FILE_MASTER_NO = ? AND FILE_SEQ = ?";
						String qry2 = "DELETE FROM UST_COM.COM_FILE WHERE (SELECT COUNT(1) AS CNT FROM UST_COM.COM_FILE A, UST_COM.COM_FILE_SUB B WHERE A.FILE_MASTER_NO = B.FILE_MASTER_NO AND A.FILE_MASTER_NO = ?) = 0 AND FILE_MASTER_NO = ?";
						
						try{
							Class.forName("net.sf.log4jdbc.DriverSpy");		
							conn = DriverManager.getConnection("jdbc:log4jdbc:oracle:thin:@172.16.6.211:1521:orcl1", "ust_hs", "ust_hs01");
							
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
%>
