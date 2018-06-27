/*****************************
* 최초작성일 : 2018.06.20
* 작성자     : 성기동
*****************************/

[기금관리시스템]
	SVN관리.
		- https://106.247.234.162/svn/deu/trunk/deu
		- https://106.247.234.162/svn/deu/trunk/deu/com
		- https://106.247.234.162/svn/deu/trunk/servers
		
		=> ID : doul
		=> PW : doul0465

[Nexacro]

	1.Build설정.
		- 상단메뉴 Tools -> Build -> Generate Path 설정변경.
			=> C:\develop_deu\workspace\deu\com\src\main\webapp
	2.DB설정.
		context-datasource.xml		
			- C:\develop_deu\workspace\deu\com\src\main\resources\deu\spring\context-datasource.xml
			=> Nexacro DB 연결에 대한 설정
	3.IP 및 주소 지정
		default_typedef.xml
			- C:\develop_deu\workspace\deu\deuXui\default_typedef.xml
				=> 아래 url부분 수정.
				****************************************************************************************************************************************** 
				<Service prefixid="txComUrl" type="JSP" url="http://localhost:7080/" cachelevel="dynamic" version="0" communicationversion="0"/>
				<Service prefixid="txAdmUrl" type="JSP" url="http://localhost:7080/" cachelevel="dynamic" version="0" communicationversion="0"/>	
				******************************************************************************************************************************************
		globalvars.xml
			- C:\develop_deu\workspace\deu\deuXui\globalvars.xml
				=> 아래 url부분 수정. 	
				******************************************************************************************************************************************
				<Variable id="gv_svrUrl" initval="http://localhost:7080/com/"/>
				<Variable id="gv_initUrl" initval="http://localhost:7080/com/"/>	   
				******************************************************************************************************************************************	
			==> 수정후 반드시 build 수행해야함!!
	4. 디비연계.
		4-1. Nexacro에서의 호출
			=> 글로벌함수(gfn_transaction) 사용하여 java단 호출
				this.gfn_transaction("SAVE",				// fn_callBack의 ID값.
						  "txComUrl::com/saveComCd.do",		// 호출하고자하는 JAVA단.
						  "resultList=ds_main:u",		// Input값
						  "ds_sub=resultList",			// Output값
						  "",
						  "fn_callBack");			// JAVA호출후 수행할 함수.(Nexacro함수)
		4-2. Controller단
			=> Controller.java에서 Request받음.
			(ComController.java)
			/**
			 * 공통코드 을(를) 저장한다.
			 */
			@RequestMapping(value = "/saveComCd.do")
			public void  saveComCd(HttpServletRequest request, HttpServletResponse response) throws Exception {
				uniRequest uniReq = new uniRequest();
				uniResponse uniRes = new uniResponse();
				
				try {
					uniReq.receivePlatformData(request);
					
					DataSet ds = uniReq.getDS("resultList");
			
					comService.saveComCd(ds);
					
					uniRes.sendPlatformData(response);
				}catch(Exception e){
					uniRes.sendException(response, e);
				}
			}
		4-3. Service단
			=> Controller에서 Service 호출(ComService.java)
				/**
				 * 공통코드 을(를) 저장한다.
				 * @throws Exception 
				 */
				public abstract void saveComCd(DataSet ds) throws Exception;
		4-4. ServiceImpl단
			=> Service와 똑같은 이름을 생성.
			=> 아래부분은 dataset의 상태에 따라서 insert,update,delete가 이루어진다.
				@Override
				public void saveComCd(DataSet ds) throws Exception{
					String insertQueryId="comDAO.insertComCd";
					String updateQueryId="comDAO.updateComCd";
					String deleteQueryId="comDAO.deleteComCd";
					commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
				}
		4-5. CommonDAO단
			=> ServiceImpl에서 공통단 호출(CommonDAO.java)
				/**
				 * 데이터를 저장한다.
				 */
				public void saveData(String insertQueryId, String updateQueryId, String deleteQueryId, DataSet ds) throws Exception {
					String insertSqlId = new String();
					String updateSqlId = new String();
					String deleteSqlId = new String();
					
						insertSqlId = insertQueryId;
						updateSqlId = updateQueryId;
						deleteSqlId = deleteQueryId;
					
					for (int i = 0; i < ds.getRowCount(); i++) {
						 
						String rowType = ds.getString(i, "rowStatus");
						HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
						
						if("insert".equals(rowType)){
					    insert(insertSqlId, insertDomainCol(hm));
					}else if("update".equals(rowType)){
					    update(updateSqlId, insertDomainCol(hm));
					}else if("delete".equals(rowType)){
						delete(deleteSqlId, insertDomainCol(hm));
					}
				    }
					
					LOGGER.info("########################################## insertQueryId : " + insertSqlId + ", updateQueryId : " + updateSqlId + ", deleteQueryId : " + deleteSqlId);
				}
		4-6. SQL호출
			=> CommonDAO에서 SQL을 호출한다.(ComSQL.xml)
				<insert id="comDAO.insertComCd" parameterClass="java.util.HashMap">
					INSERT INTO COM_COD (
								COM_CD_DIV
							  , COM_CD
							  , COM_CD_NM
							  , USE_YN
							  ) VALUES (
							    #comCdDiv#
							  , '@'
							  , #comCdNm#
							  , #useYn#
							  )
				</insert>
	5. DB DAO설정관련
		5-1. CommonDAO.java
			- C:\develop_deu\workspace\deu\com\src\main\java\deu\comm\unione\service\impl\CommonDAO.java
			=>	@Resource(name="sqlMapClient")
				public void initDAO(SqlMapClient sqlMapClient){
					setSqlMapClient(sqlMapClient);
				}
			=> name으로 지정한 명칭 : "sqlMapClient"
		5-2. context-sqlMap.xml
			- C:\develop_deu\workspace\deu\com\src\main\resources\deu\spring\context-sqlMap.xml
			=> bean id="sqlMapClient" 를 찾아서 
				<property name="dataSource">
					<ref bean="dataSourceMssql" />
				</property> 의 ref bean 이름을 확인.
			   "dataSourceMssql"
		5-3. context-datasource.xml
			- C:\develop_deu\workspace\deu\com\src\main\resources\deu\spring\context-datasource.xml
			=> bean id="dataSourceMssql" 확인하여 디비접속정보 확인.
			
[ClipReport]

	DB설정.
		DataConnection.properties 
			- C:\develop_deu\workspace\deu\com\src\main\webapp\WEB-INF\clipreport4\DataConnection.properties
			=> ClipReport DB 연결에 대한 설정
