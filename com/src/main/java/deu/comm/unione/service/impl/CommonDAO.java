package deu.comm.unione.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import deu.com.service.ComService;
import deu.comm.unione.ConvertUtil;
import deu.comm.unione.MetadataUtil;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.nexacro.xapi.data.DataSet;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

/**
 * @Class Name : SampleDAO.java
 * @Description : Sample DAO Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2009.03.16           최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */
@SuppressWarnings("unchecked")
@Repository("commonDAO")
public class CommonDAO extends EgovAbstractDAO {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ComService.class);
	
	@Resource(name="sqlMapClient")
	public void initDAO(SqlMapClient sqlMapClient){
		setSqlMapClient(sqlMapClient);
	}
	
	@SuppressWarnings("deprecation")
	public SqlMapClientTemplate getSqlMapClientTemplateDAO(){
		return this.getSqlMapClientTemplate();
	}
	
	/**
	 * 데이터를 조회한다.
	 */
	public List<?> selectData(String sqlId, Map map) throws Exception {
		String queryId = new String();

			queryId = sqlId;	
		
		List<?> list =   list(queryId, map);
		
		if(list.size() == 0)
			list = MetadataUtil.getListMetadata(getSqlMapClientTemplate(), queryId, map);

		LOGGER.info("############################################################# sqlId : " + queryId);

		return list;
	}
	
	/**
	 * 데이터를 조회한다.
	 */
	public Integer selectCnt(String sqlId, Map map) throws Exception {
		String queryId = new String();

			queryId = sqlId;	
		
		Integer dataCnt =   (Integer) select(queryId, map);
		
		LOGGER.info("############################################################# sqlId : " + queryId);
		
		return dataCnt;
	}
	
	/**
	 * 데이터를 조회한다.
	 */
	public String selectString(String sqlId, Map map) throws Exception {
		String queryId = new String();

			queryId = sqlId;	
		
		String dataString =   (String) select(queryId, map);
		
		LOGGER.info("############################################################# sqlId : " + queryId);
		LOGGER.info("############################################################# dataString : " + dataString);
		
		return dataString;
	}
	
	/**
	 * 데이터를 조회한다.
	 */
	public List<?> selectJson(String sqlId, Map map) throws Exception {
		String queryId = new String();

			queryId = sqlId;	
		
		List<?> list =   list(queryId, map);
		
		LOGGER.info("############################################################# sqlId : " + queryId);
		
		return list;
	}
	
	/**
	 * 데이터를 조회한다.
	 */
	public List<?> selectDataSP(String sqlId, Map map) throws Exception {
		String queryId = new String();
		
			queryId = sqlId;	
		
		List<?> list =   list(queryId, map);
		
		if(list.size() == 0)
			list = MetadataUtil.getListMetadata(getSqlMapClientTemplate(), queryId, map);
		
		LOGGER.info("############################################################# sqlId : " + queryId);

		return (List<?>) map.get("OUT_CUR");
	}
	
	/**
	 * 프로시져를 호출한다.
	 */
	public HashMap saveDataSP(String sqlId, Map map) throws Exception {
		String queryId = new String();
		
			queryId = sqlId;	
		
		List<?> list =   list(queryId, insertDomainCol(map));

		LOGGER.info("############################################################# sqlId : " + queryId);
		
		return (HashMap) map;
	}
	
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
	
	/**
	 * 데이터를 추가한다.
	 */
	public Object insertData(String sqlId, Map map) throws Exception {
		String queryId = new String();
		
			queryId = sqlId;	
				
		Object cnt = insert(queryId, insertDomainCol(map));
		
		LOGGER.info("############################################################# sqlId : " + queryId);
		
		return cnt;
	}
	
	/**
	 * 데이터를 수정한다.
	 */
	public Object updateData(String sqlId, Map map) throws Exception {
		String queryId = new String();
		
			queryId = sqlId;	
		
		Object cnt = update(queryId, insertDomainCol(map));
		
		LOGGER.info("############################################################# sqlId : " + queryId);
		
		return cnt;
	}
	
	/**
	 * 데이터를 삭제한다.
	 */
	public Object deleteData(String sqlId, Map map) throws Exception {
		String queryId = new String();
		
			queryId = sqlId;	
		
		Object cnt = delete(queryId, insertDomainCol(map));
		
		LOGGER.info("############################################################# sqlId : " + queryId);
		
		return cnt;
	}
	
	/**
	 * 데이터를 조회한다.
	 */
	public List<?> selectComCdCombo(String sqlId, Map map) throws Exception {
		String queryId = new String();
		
			queryId = sqlId;	
		
		String strGrpCd = (String) map.get("strGrpCd");
		String strType = (String) map.get("strType");
		
		String dispStr = "";
		
		if("T".equals(strType)){
			dispStr = "전체";
		} else if("S".equals(strType)){
			dispStr = "선택";
		} else {
			dispStr = "";
		}
		
		map.put("dispStr", dispStr);
		
		List listResult = null;	// 조회 결과 반영
		
		// Multi-Group Code의 조회 조건 반영
		List listGrpCd = new ArrayList();	// 다중 그룹코드 조회 조건 반영
		StringTokenizer stGrpCd = new StringTokenizer(strGrpCd, "&");
		
		while(stGrpCd.hasMoreTokens()){
			listGrpCd.add(stGrpCd.nextToken().trim());
		}
		
		map.put("listGrpCd", listGrpCd);
		
		String strUseYn = (String) map.get("strUseYn") == null ? "" : (String) map.get("strUseYn");
		
		if(strUseYn.equals("T")) {
			strUseYn = "";
			map.put("strUseYn", strUseYn);
		}
		
		List<?> list =   list(queryId, map);
		
		if(list.size() == 0)
			list = MetadataUtil.getListMetadata(getSqlMapClientTemplate(), queryId, map);
		
		LOGGER.info("############################################################# sqlId : " + queryId);
		
		return list;
	}
	
	/**
	 * domain column정보 추가
	 */
	public Map insertDomainCol(Map map) throws Exception {
		
		if(map.containsKey("entryNo")){
			map.remove("entryNo");
		}
		
		if(map.containsKey("entryIp")){
			map.remove("entryIp");
		}
		
		if(map.containsKey("createNo")){
			map.remove("createNo");
		}
		
		if(map.containsKey("createIp")){
			map.remove("createIp");
		}
		
		map.put("entryNo", map.get("gv_memberNo"));
		map.put("entryIp", map.get("gv_ipAddr"));
		map.put("createNo", map.get("gv_memberNo"));
		map.put("createIp", map.get("gv_ipAddr"));
				
		return map;
	}
	
}
