<%
/*
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/ 
%>
 
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@ page import="org.apache.commons.fileupload.FileItemFactory"%>
<%@ page import="org.apache.commons.fileupload.FileUploadException"%>
<%@ page import="org.apache.commons.fileupload.FileItem"%>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@ page import="org.apache.commons.io.FilenameUtils"%>
<%@ page import="org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException"%>
<%@ page import="org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException"%>

<%@ page import="javax.servlet.http.HttpServlet"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ page import="javax.servlet.http.HttpServletResponse"%>
<%@ page import="javax.servlet.ServletException"%>
<%@ page import="java.io.IOException"%>
<%@ page import="java.io.File"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.TimeZone"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>

<%@ page import="com.nexacro.xapi.data.ColumnHeader"%>
<%@ page import="com.nexacro.xapi.data.DataSet"%>
<%@ page import="com.nexacro.xapi.data.DataTypes"%>
<%@ page import="com.nexacro.xapi.data.PlatformData"%>
<%@ page import="com.nexacro.xapi.data.VariableList"%>
<%@ page import="com.nexacro.xapi.tx.HttpPlatformResponse"%>
<%@ page import="com.nexacro.xapi.tx.PlatformException"%>
<%@ page import="com.nexacro.xapi.tx.HttpPlatformRequest"%>
<%@ page import="deu.comm.unione.service.impl.CommonDAO"%>
<%@ page import="java.sql.*"%>
<%@ page import="deu.comm.unione.BusinessException"%>
<%@ page import="deu.comm.unione.uniAuth"%>
<%
uniAuth uniauth = new uniAuth();
String userGb = uniauth.checkAuth(request, response);
System.out.println("userGb : "+userGb);
if("SUCESS".equals(userGb)) {
	final CommonDAO commonDAO = new CommonDAO();
	PlatformData resData = new PlatformData();
	VariableList resVarList = resData.getVariableList();
	
	//Check that we have a file upload request
	boolean isMultipart = ServletFileUpload.isMultipartContent(request);
	
	if (isMultipart) 
	{
		//String contextRealPath = request.getSession().getServletContext().getRealPath("/");
		String contextRealPath = "c:/"; 
		//String contextRealPath = "";
		String path = request.getParameter("path");
		String savePath = contextRealPath + path.substring(0, path.indexOf("?"));	//경로를 따로 주고 싶을 때 savePath를 변경해야 한다.
		String maxSize = request.getParameter("maxSize");
		String maxTotalSize = request.getParameter("maxTotalSize");
		String fileMasterNo = path.substring(path.indexOf("?")+14);
		String empNo = request.getParameter("EmpNo");
		String ipAddr = request.getParameter("IpAddr");
		
		String driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url = "jdbc:sqlserver://113.198.254.48:1433;DatabaseName=ADMI";
		String username = "deubudacc";
		String password = "budacc@dPtks";
		
		if ( maxSize == null )
		{
			maxSize = "-1";
		}
		if ( maxTotalSize == null )
		{
			maxTotalSize = "-1";
		}
		
		Long lMaxSize = Long.parseLong(maxSize);		
		Long lMaxTotalSize = Long.parseLong(maxTotalSize);
		
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		// Sets the size threshold beyond which files are written directly to disk
		factory.setSizeThreshold(4096);
		
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
	
		// Specifies the character encoding to be used when reading the headers of individual part
		upload.setHeaderEncoding("utf-8");
		
		// Set overall request size constraint
		upload.setSizeMax(lMaxTotalSize);
		
		// Sets the maximum allowed size of a single uploaded file
		upload.setFileSizeMax(lMaxSize); 
		
		try 
		{
			File filePath = new File(savePath); 
			System.out.println("filePath >>>>>>>>>>>>>>>>>> : "+ filePath);
			if (!filePath.exists()) 
			{
				filePath.mkdirs();
			}
	
			// Sets the directory used to temporarily store files that are larger than the configured size threshold
			factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
	
			// Parse the request
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			
			DataSet ds = new DataSet("ds_output");
			ds.addColumn(new ColumnHeader("filename", DataTypes.STRING));
			ds.addColumn(new ColumnHeader("savefilename", DataTypes.STRING));
			ds.addColumn(new ColumnHeader("filesize", DataTypes.INT));
			ds.addColumn(new ColumnHeader("fileMasterNo", DataTypes.INT));
			ds.addColumn(new ColumnHeader("fileSeq", DataTypes.INT));
			
			// Parse the request
			while (iter.hasNext()) 
			{
				FileItem item = (FileItem) iter.next();

				if (item.isFormField()) 
				{
					String name = item.getFieldName();
					String encoding = upload.getHeaderEncoding();
                    String value = item.getString(encoding);

	    			//input dataset 확인
                    //String value = item.getString();
	    			
	    			if(name == "inputDatasets") {
	    				//TODO
	    				//문자열(xml or ssv)을 Dataset으로 변환 후 업무로직에 사용할 것.
	    				continue;
	    			}
    				
    				if (value == null || value.equals(""))
					{
						continue;
					}
					else
					{
						File f = new File(savePath + File.separator, value);
						
						if (f.exists()) 
						{
							f.delete();
						}
					}
				}
				else	// Process a file upload
				{
					// filename on the client
					String fieldName = item.getFieldName();
					String fileName = item.getName();
					
					long fileSize = item.getSize();
					int intFileSize = (int)fileSize;
					String strFileSize = Integer.toString(intFileSize);
					
					if (fileName == null || fileName.equals(""))
					{
						continue;
					}
					else
					{
						fileName = FilenameUtils.getName(fileName);
						
                        //============================= save file name =============================    
                        Date date = new Date();
                        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
                        DateFormat gmtFormat = new SimpleDateFormat("yyyyMMdd");
                        gmtFormat.setTimeZone(gmtTime);
                        
                        int seq = 0;
                        String rename       = FilenameUtils.getBaseName(fileName) + "_" + gmtFormat.format(date);
                        String ext          = FilenameUtils.getExtension(fileName);
                        
                        if("htm".equals(ext) || "html".equals(ext) || "jsp".equals(ext) || "php".equals(ext) || "js".equals(ext)){
    						throw new BusinessException("허용되지 않은 확장자입니다.");						
    					}else{
    						
	                        String saveFileName = rename + "_" + seq + "." + ext;
	                        //==========================================================================					
							
	                        //20160414 파일이 존재할 경우 기존파일 삭제 후 업데이트 한다.
					        File existFile = new File(savePath+"/"+saveFileName);
					        
					        if(existFile.exists()){
					        	existFile.delete();
					        }
					        //20160414 파일 삭제 종료
					        
							File uploadedFile = new File(filePath + File.separator, saveFileName);
	
	                        while (!uploadedFile.createNewFile()) 
	                        {
	                            seq++;
	                            saveFileName = rename + "_" + seq + "." + ext;
	                            uploadedFile = new File(uploadedFile.getParent(), saveFileName);
	                        }
							item.write(uploadedFile);
							item.delete();
	
							int row = ds.newRow();
							ds.set(row, "filename", fileName);
							ds.set(row, "savefilename", saveFileName);
							ds.set(row, "filesize", fileSize);
							
							Connection conn = null;
							PreparedStatement pstmt = null;
							ResultSet rs = null;
							
							String qry = "";
							String qry1 = "";
							String qry2 = "";
							//String empNo = "dev1"; // 추후 사용자 ID로 수정
							int max_seq = 0;
							int resultCount = 0;
							
							if("x".equals(fileMasterNo)){
								//파일마스터번호 없을경우
								qry = "SELECT ISNULL(MAX(FILE_MASTER_NO),0) + 1 AS MAX_SEQ FROM COM_FILE";
								
								try{
									Class.forName(driverClassName);		
									conn = DriverManager.getConnection(url, username, password);
									
									pstmt = conn.prepareStatement(qry);
									pstmt.clearParameters();
								
									rs = pstmt.executeQuery();
									if(rs.next()){
										max_seq = rs.getInt("MAX_SEQ");
									}
	
									/*마스터*/ qry1 = "INSERT INTO COM_FILE (FILE_MASTER_NO,SUB_ID,MENU_ID,PROG_ID,INSERT_ID,INSERT_DT,INSERT_IP,UPDATE_ID,UPDATE_DT,UPDATE_IP) VALUES (?, ?, ?, ?, ?, GETDATE(), ?, ?, GETDATE(), ?)";
									/*디테일*/ qry2 = "INSERT INTO COM_FILE_SUB (FILE_MASTER_NO,FILE_SEQ,ORG_FILE_NM,FILE_PATH,FILE_SIZE,INSERT_ID,INSERT_DT,INSERT_IP,UPDATE_ID,UPDATE_DT,UPDATE_IP) VALUES (?, (SELECT ISNULL(MAX(FILE_SEQ),0)+1 FROM COM_FILE_SUB WHERE FILE_MASTER_NO=?), ?, ?, ?, ?, GETDATE(), ?, ?, GETDATE(), ?)";
								}catch(Exception e){
									out.println(e.toString());
								}finally {
							        if ( rs != null ) { try { rs.close(); } catch ( SQLException ex ) { ex.getMessage(); } }
							        if ( pstmt != null ) { try { pstmt.close(); } catch ( SQLException ex ) { ex.getMessage(); } }
							        if ( conn != null ) { try { conn.close(); } catch ( Exception ex ) { ex.getMessage(); } }
								}
								try{
									conn = null;
									pstmt = null;
									rs = null;
									
									Class.forName(driverClassName);		
									conn = DriverManager.getConnection(url, username, password);
									
									pstmt = conn.prepareStatement(qry1);
									
									pstmt.clearParameters();
									
									pstmt.setInt(1, max_seq);
									pstmt.setString(2, "MNG");
									pstmt.setString(3, "LOG");
									pstmt.setString(4, "webEditor");
									pstmt.setString(5, empNo);
									pstmt.setString(6, ipAddr);
									pstmt.setString(7, empNo);
									pstmt.setString(8, ipAddr);
									
									resultCount = pstmt.executeUpdate();// insert,updarte,delete pstmt.executeQuery();//select
									
									if(resultCount > 0) { //마스터에 정상적으로 인서트된 경우
										pstmt = null;
										pstmt = conn.prepareStatement(qry2); //디테일 쿼리
										pstmt.clearParameters();
										
										pstmt.setInt(1, max_seq);
										pstmt.setInt(2, max_seq);
										pstmt.setString(3, fileName);
										pstmt.setString(4, savePath+"/"+saveFileName);
										pstmt.setString(5, strFileSize);
										pstmt.setString(6, empNo);
										pstmt.setString(7, ipAddr);
										pstmt.setString(8, empNo);
										pstmt.setString(9, ipAddr);
										pstmt.executeUpdate();
										ds.set(row, "fileMasterNo", max_seq);
										ds.set(row, "fileSeq", "1");
									}
								}
								catch(Exception e){
									out.println(e.toString());
								}finally {
							        if ( rs != null ) { try { rs.close(); } catch ( SQLException ex ) { ex.getMessage(); } }
							        if ( pstmt != null ) { try { pstmt.close(); } catch ( SQLException ex ) { ex.getMessage(); } }
							        if ( conn != null ) { try { conn.close(); } catch ( Exception ex ) { ex.getMessage(); } }
								}
							//파일마스터번호 없을경우
							}else{
								/*디테일*/ qry = "INSERT INTO COM_FILE_SUB (FILE_MASTER_NO,FILE_SEQ,ORG_FILE_NM,FILE_PATH,FILE_SIZE,INSERT_ID,INSERT_DT,INSERT_IP,UPDATE_ID,UPDATE_DT,UPDATE_IP) VALUES (?, (SELECT ISNULL(MAX(FILE_SEQ),0)+1 FROM COM_FILE_SUB WHERE FILE_MASTER_NO=?), ?, ?, ?, ?, GETDATE(), ?, ?, GETDATE(), ?)";
								try{
									Class.forName(driverClassName);		
									conn = DriverManager.getConnection(url, username, password);
									
									pstmt = conn.prepareStatement(qry);
									pstmt.clearParameters();
								
									//rs = pstmt.executeQuery();
									max_seq = Integer.parseInt(fileMasterNo);
									pstmt.setInt(1, max_seq);
									pstmt.setInt(2, max_seq);
									pstmt.setString(3, fileName);
									pstmt.setString(4, savePath+"\\"+saveFileName);
									pstmt.setString(5, strFileSize);
									pstmt.setString(6, empNo);
									pstmt.setString(7, ipAddr);
									pstmt.setString(8, empNo);
									pstmt.setString(9, ipAddr);
									pstmt.executeUpdate();
									ds.set(row, "fileMasterNo", max_seq);
									
									qry2 = "SELECT ISNULL(MAX(FILE_SEQ),0) AS MAX_SEQ FROM COM_FILE_SUB WHERE FILE_MASTER_NO=?";
									
									conn = null;
									pstmt = null;
									rs = null;
									
									Class.forName(driverClassName);		
									conn = DriverManager.getConnection(url, username, password);
									
									pstmt = conn.prepareStatement(qry2);
									
									pstmt.clearParameters();
									pstmt.setInt(1, Integer.parseInt(fileMasterNo));
									
									rs = pstmt.executeQuery();
									
									if(rs.next()){
										ds.set(row, "fileSeq", rs.getInt("MAX_SEQ"));
									}
									
										
								}catch(Exception e){
									out.println(e.toString());
								}finally{
							        if ( rs != null ) { try { rs.close(); } catch ( SQLException ex ) { ex.getMessage(); } }
							        if ( pstmt != null ) { try { pstmt.close(); } catch ( SQLException ex ) { ex.getMessage(); } }
							        if ( conn != null ) { try { conn.close(); } catch ( Exception ex ) { ex.getMessage(); } }
								}
							}
    					}
						
					//////
					}
				}
			}
			resData.addDataSet(ds);
			resVarList.add("ErrorCode", 0);
			resVarList.add("ErrorMsg", "SUCC");
		}
		catch (FileSizeLimitExceededException e)
		{
			resVarList.add("ErrorCode", 1);
			resVarList.add("ErrorMsg", "FileSizeLimitExceededException");
		}
		catch (SizeLimitExceededException e)
		{
			resVarList.add("ErrorCode", 1);
			resVarList.add("ErrorMsg", "SizeLimitExceeded");
		}
		catch (FileUploadException e) 
		{
			resVarList.add("ErrorCode", -1);
			resVarList.add("ErrorMsg", e);
		}
		catch (Exception e) 
		{
			resVarList.add("ErrorCode", -1);
			resVarList.add("ErrorMsg", e);
		}
	}
	else
	{
		resVarList.add("ErrorCode", -1);
		resVarList.add("ErrorMsg", "Invalid Request");
	}
	
	HttpPlatformResponse res = new HttpPlatformResponse(response);
	res.setData(resData);
	res.sendData();
	
}else{
	response.sendRedirect("unknownfile");
}
%>
