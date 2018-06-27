package deu.admi.afd.afd01.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nexacro.xapi.data.DataSet;

import deu.admi.afd.afd01.service.Afd01Service;
import deu.comm.unione.BusinessException;
import deu.comm.unione.ConvertUtil;
import deu.comm.unione.service.impl.CommonDAO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("afd01Service")
public class Afd01ServiceImpl extends EgovAbstractServiceImpl implements
		Afd01Service {
	@Resource(name = "commonDAO")
	private CommonDAO commonDAO;

	/*
	 * ##########################################################################
	 * ###############################################################
	 */
	/* 기금코드정보관리(admi/afd/afd01/afd01010.xfdl) */
	/*
	 * ##########################################################################
	 * ###############################################################
	 */

	@Override
	public List<?> retrieveEndwDivCd(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd01SQL.retrieveEndwDivCd",
				map);
		return resultList;
	}

	@Override
	public List<?> retrieveEndw(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd01SQL.retrieveEndw", map);
		return resultList;
	}

	@Override
	public void saveEndw(DataSet ds) throws Exception {
		String insertSqlId = "Afd01SQL.insertEndw";
		String updateSqlId = "Afd01SQL.updateEndw";
		String deleteSqlId = "Afd01SQL.deleteEndw";

		String rowType = ds.getString(0, "rowStatus");
		if ("delete".equals(rowType)) {
			for (int i = 0; i < ds.getRowCount(); i++) {
				HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
				int cnt = commonDAO.selectCnt("Afd01SQL.retrieveUpperEndwCnt",
						hm);
				if (cnt > 0) {
					throw new BusinessException("err.delete.endw.child[300]");
				}
			}
		}

		commonDAO.saveData(insertSqlId, updateSqlId, deleteSqlId, ds);

	}

	@Override
	public List<?> retrieveMembas(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd01SQL.retrieveMembas",
				map);
		return resultList;
	}

	@Override
	public List<?> retrieveMembasCd(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd01SQL.retrieveMembasCd",
				map);
		return resultList;
	}

	@Override
	public void saveMembas(DataSet ds) throws Exception {
		String insertSqlId = "Afd01SQL.insertMembas";
		String updateSqlId = "Afd01SQL.updateMembas";
		String deleteSqlId = "Afd01SQL.deleteMembas";

		commonDAO.saveData(insertSqlId, updateSqlId, deleteSqlId, ds);
	}

	@Override
	public List<?> retrieveHonortreat(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData(
				"Afd01SQL.retrieveHonortreat", map);
		return resultList;
	}

	@Override
	public void saveHonortreat(DataSet ds) throws Exception {
		String insertSqlId = "Afd01SQL.insertHonortreat";
		String updateSqlId = "Afd01SQL.updateHonortreat";
		String deleteSqlId = "Afd01SQL.deleteHonortreat";

		commonDAO.saveData(insertSqlId, updateSqlId, deleteSqlId, ds);

	}


    /*#########################################################################################################################################*/
    /* 회원정보관리(admi/afd/afd01/afd01050.xfdl)                                                                                                  */
    /*#########################################################################################################################################*/


	/**
	 * 회원정보를 조회한다.
	 */
	@Override
	public List<?> selectAFD01050_01(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd01SQL.selectAFD01050_01", map);
		return resultList;
	}

	/**
	 * 회원정보를 저장한다.
	 */
	@Override
	public List<?> saveAFD01050_01(DataSet ds) throws Exception {
		HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, 0);
		
		if ("insert".equals(ds.getString(0, "rowStatus"))) {
			//[1] 회원번호 max값 조회 후 셋팅
			//System.out.println(hm.get("memberGb"));
			
			 System.out.println(hm.get("memberGb"));
			 
			//if(hm.get("memberGb") == "13") {   
			  if(hm.get("memberGb").equals("13")) {
	            String memberNo = hm.get("jigbeon").toString();
	           
	            System.out.println("memberNo  >>>>> " + memberNo);
	            
	            hm.put("memberNo", memberNo); 
	       
	         }
	         
	         else {
	            String memberNo = commonDAO.selectString("Afd01SQL.retrieveAfdMemberNo", hm);
	            
	            System.out.println("memberNo  >>>>> " + memberNo);
	         
	            hm.put("memberNo", memberNo);
	           
	         }

			//[2] 회원정보 생성
			commonDAO.insertData("Afd01SQL.insertAfdMaster", hm);
		} else if ("update".equals(ds.getString(0, "rowStatus"))) {
			//[1] 회원정보 수정
			commonDAO.updateData("Afd01SQL.updateAfdMaster", hm);
		} else if ("delete".equals(ds.getString(0, "rowStatus"))) {
			//[1] 회원정보 삭제
			commonDAO.deleteData("Afd01SQL.deleteAfdMaster", hm);
		}

		return selectAFD01050_01(hm);
	}

	@Override
	public List<?> retrieveWsJuminNo(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd01SQL.retrieveWsJuminNo", map);
		return resultList;
	}

	@Override
	public List<?> retrieveAfdMemberNo(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd01SQL.retrieveAfdMemberNo", map);
		return resultList;
	}
	
	@Override
	public List<?> retrieveAfdCntrct(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd01SQL.retrieveAfdCntrct", map);
		return resultList;
	}

	@Override
	public List<?> retrieveAfdDmsndng(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd01SQL.retrieveAfdDmsndng", map);
		return resultList;
	}

	@Override
	public List<?> selectComboMembas(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd01SQL.selectComboMembas", map);
		return resultList;
	}
	
	/*
	 * ##########################################################################
	 * ###############################################################
	 */
	/* 예우코드정보관리(admi/afd/afd01/afd01015.xfdl) */
	/*
	 * ##########################################################################
	 * ###############################################################
	 */

	/**
	 * 예우코드정보를 조회한다.
	 */
	@Override
	public List<?> selectAfdEvent(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd01SQL.selectAfdEvent", map);
		return resultList;
	}
	
	/**
	 * 예우코드정보를 저장한다.
	 */
	@Override
	public void saveAfdEvent(DataSet ds) throws Exception{
		String insertQueryId="Afd01SQL.insertAfdEvent";
		String updateQueryId="comDAO.updateComCdSub";
		String deleteQueryId="comDAO.deleteComCdSub";
		
		if(!ds.hasData()) {
			return;
		}
		
		for(int i=0; i<ds.getRowCount(); i++) {
							
				String rowType = ds.getString(i, "rowStatus");
				HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
				
				
				if("insert".equals(rowType)){
					commonDAO.insert(insertQueryId, hm);
		        }else if("update".equals(rowType)){
		        	commonDAO.update(updateQueryId, hm);
		        }else if("delete".equals(rowType)){
		        	commonDAO.delete(deleteQueryId, hm);
		        }	
			
		}
		
	}
}
