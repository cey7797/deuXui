package deu.admi.afd.afd02.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nexacro.xapi.data.DataSet;
import com.nexacro.xapi.util.StringUtils;

import deu.admi.afd.afd02.service.Afd02Service;
import deu.comm.unione.BusinessException;
import deu.comm.unione.ConvertUtil;
import deu.comm.unione.service.impl.CommonDAO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("afd02Service")
public class Afd02ServiceImpl extends EgovAbstractServiceImpl implements Afd02Service{
	@Resource(name = "commonDAO")
	private CommonDAO commonDAO;
	
	
	
	@Override
	public List<?> retrieveCntrctMember(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd02SQL.retrieveCntrctMember", map);
		return resultList;
	}
	
	@Override
	public List<?> retrieveEnggMgnNo(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd02SQL.retrieveEnggMgnNo", map);
		return resultList;
	}
	
	@Override
	public List<?> saveAfdCntrct(DataSet ds) throws Exception {
		
		String rowType = ds.getString(0, "rowStatus");
		HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, 0);
		
		if("insert".equals(rowType)){			
			hm.put("enggMgnNo", commonDAO.selectString("Afd02SQL.createEnggMgnNo", hm));			
			commonDAO.insertData("Afd02SQL.insertAfdCntrct", hm);
        }else if("update".equals(rowType)){
        	commonDAO.updateData("Afd02SQL.updateAfdCntrct", hm);
        }else if("delete".equals(rowType)){
        	//[1]삭제 전 입금내역  COUNT조회
        	int rcpmnyCnt = commonDAO.selectCnt("Afd02SQL.retrieveRcpmnyMngNoCount", hm);
        	
        	//[2]입금내역이 존재하면 EXCEPTION 처리
        	if(rcpmnyCnt > 0) {
        		throw new BusinessException("err.saveAfdCntrct.child[300]");
        	}
        	
        	//[3]입금내역이 존재하지않으면 삭제처리
        	commonDAO.deleteData("Afd02SQL.deleteAfdCntrct", hm);
        }		
		
		return retrieveAfdCntrct(hm);
	}
	
	@Override
	public List<?> selectFixCdComboList(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd02SQL.selectFixCdComboList", map);
		return resultList;
	}
	
	@Override
	public List<?> retrieveAfdCntrct(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd02SQL.retrieveAfdCntrct", map);
		return resultList;
	}
	
	@Override
	public List<?> retrieveOnlnCntrct(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd02SQL.retrieveOnlnCntrct", map);
		return resultList;
	}
	
	@Override
	public void saveOnlnCntrct(DataSet ds) throws Exception {
		String saveSqlId = "Afd02SQL.saveOnlnCntrct";
		for (int i = 0; i < ds.getRowCount(); i++) {
			
			HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
			String procGb = (String)hm.get("procGb");
			if("1".equals(procGb)) {
				String inspyMgnNo = commonDAO.selectString("Afd02SQL.createEnggMgnNo", hm);
				hm.put("inspyMgnNo", inspyMgnNo);
			}		
			commonDAO.updateData(saveSqlId, hm);
	    }
	}
	
	@Override
	public List<?> retrieveAfdDmsndngMember(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd02SQL.retrieveAfdDmsndngMember", map);
		return resultList;
	}
	
	@Override
	public List<?> retrieveAfdDmsndng(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd02SQL.retrieveAfdDmsndng", map);
		return resultList;
	}
	
	@Override
	public void saveAfdDmSndng(DataSet ds) throws Exception {
		String saveSqlId = "Afd02SQL.saveAfdDmSndng";
		for (int i = 0; i < ds.getRowCount(); i++) {
			
			HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);	
			commonDAO.updateData(saveSqlId, hm);
			System.out.println("DM발송!!!!!!!!");
	    }
	}
	
	@Override
	public List<?> retrieveRcpmny(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd02SQL.retrieveRcpmny", map);
		return resultList;
	}
	
	@Override
	public void saveRcpmny(DataSet ds) throws Exception {
		String insertSqlId = "Afd02SQL.insertRcpmny";
		String updateSqlId = "Afd02SQL.updateRcpmny";
		String deleteSqlId = "Afd02SQL.deleteRcpmny";
		
		commonDAO.saveData(insertSqlId, updateSqlId, deleteSqlId, ds);
	}
	
	@Override
	public List<?> retrieveEventDtl(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd02SQL.retrieveEventDtl", map);
		return resultList;
	}
	
	@Override
	public void saveEventDtl(DataSet ds) throws Exception {
		String insertSqlId = "Afd02SQL.insertEventDtl";
		String updateSqlId = "Afd02SQL.updateEventDtl";
		String deleteSqlId ="Afd02SQL.deleteEventDtl";
		
		commonDAO.saveData(insertSqlId, updateSqlId, deleteSqlId, ds);
	}
	
	@Override
	public List<?> retrieveMembas(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd02SQL.retrieveMembas", map);
		return resultList;
	}
	
	@Override
	public List<?> retrieveEventng(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd02SQL.retrieveEventng", map);
		return resultList;
	}
	
	@Override
	public void saveEventng(DataSet ds) throws Exception {
		String insertSqlId = "Afd02SQL.insertEventng";
		String updateSqlId = "Afd02SQL.updateEventng";
		String deleteSqlId ="Afd02SQL.deleteEventng";
		
		commonDAO.saveData(insertSqlId, updateSqlId, deleteSqlId, ds);
		
	}
	
	@Override
	public List<?> retrieveEventStatus(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd02SQL.retrieveEventStatus", map);
		return resultList;
	}

    
    /*#########################################################################################################################################*/
    /* 약정회원정보팝업(admi/afd/afd02/afd02030_p01.xfdl)                                                                                                  */
    /*#########################################################################################################################################*/
	

	/**
	 * 약정회원정보팝업을 조회한다.
	 */
	@Override
	public List<?> selectAFD02030_P01(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd02SQL.selectAFD02030_P01", map);
		return resultList;
	}

    
    /*#########################################################################################################################################*/
    /* CMS입금관리(admi/afd/afd02/afd02031.xfdl)                                                                                                  */
    /*#########################################################################################################################################*/


	/**
	 * CMS입금관리를(을) 조회한다.
	 */	
	@Override
	public List<?> selectFD02031_01(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd02SQL.selectFD02031_01", map);
		return resultList;
	}

	/**
	 * CMS입금관리를(을) 엑셀업로드 후 저장 전 체크 
	 */	
	@Override
	public List<?> selectFD02031_02(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd02SQL.selectFD02031_02", map); 
		return resultList;
	}
	
	/**
	 * CMS입금관리를(을) 엑셀업로드 후 저장한다.
	 */
	@Override
	public HashMap saveFD02031_01(DataSet ds, Map map) throws Exception {

		/* [1] 엑셀업로드처리 전 ADMI.AFD_CMS테이블을 DELETE한다. */
		commonDAO.delete("Afd02SQL.delete_afdCms_FD02031_01", map);
		
		/* [1] 엑셀업로드처리 전 ADMI.AFD_CMS_EXCEL테이블을 DELETE한다. */
    	commonDAO.delete("Afd02SQL.delete_afdCmsExcel_FD02031_01");
    	
    	/* [2] 엑셀업로드처리 데이터를 ADMI.AFD_CMS_EXCEL테이블에 INSERT한다. */
        commonDAO.saveData("Afd02SQL.insertFD02031_01Excel", "", "", ds);
        
        /* [3] 엑셀업로드처리 프로시저를 호출한다. */
        HashMap rtnMap = commonDAO.saveDataSP("Afd02SQL.callP_FD_CMS_EXCEL", map);
        
        return rtnMap;
	}

    
    /*#########################################################################################################################################*/
    /* 급여공제관리(admi/afd/afd02/afd02032.xfdl)                                                                                                  */
    /*#########################################################################################################################################*/


	/**
	 * 급여공제관리를(을) 조회한다.
	 */	
	@Override
	public List<?> selectFD02032_01(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd02SQL.selectFD02032_01", map);
		return resultList;
	}
	
	/**
	 * 급여공제관리를(을) 조회한다.
	 */	
	@Override
	public List<?> selectFD02032_02(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd02SQL.selectFD02032_02", map);
		return resultList;
	}
		
	/**
	 * 급여공제관리 데이터를 생성한다.
	 */	
	@Override
	public void createFD02032(DataSet ds) throws Exception{
		String insertQueryId="Afd02SQL.createFD02032";
		String deleteQueryId="Afd02SQL.deleteFD02032";
		
		if(!ds.hasData()) {
			return;
		}
		
		for(int i=0; i<ds.getRowCount(); i++) {
							
			HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
			commonDAO.delete(deleteQueryId, hm);
			commonDAO.insert(insertQueryId, hm);
							
		}
		
	}
	
	/**
	 * 급여공제관리 데이터를 저장한다.
	 */	
	@Override
	public void saveFD02032(DataSet ds) throws Exception{
		String deleteQueryId="Afd02SQL.deleteFD02032";
		
		if(!ds.hasData()) {
			return;
		}
		
		for(int i=0; i<ds.getRowCount(); i++) {
							
				HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
				commonDAO.delete(deleteQueryId, hm);
							
		}
		
	}
	
	/**
	 * 지출일 일괄적용
	 */	
	@Override
	public void updateFD02032(DataSet ds) throws Exception{
		String updateQueryId="Afd02SQL.updateFD02032";
		
		if(!ds.hasData()) {
			return;
		}
		
		for(int i=0; i<ds.getRowCount(); i++) {
							
			HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
			commonDAO.update(updateQueryId, hm);
							
		}
		
	}

    
    /*#########################################################################################################################################*/
	/* 동문회관리(admi/afd/afd02/afd02033.xfdl)                                                                                                */
    /*#########################################################################################################################################*/


	/**
	 * 동문회관리를(을) 조회한다.
	 */	
	@Override
	public List<?> selectFD02033_01(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd02SQL.selectFD02033_01", map);
		return resultList;
	}

	/**
	 * 동문회를(을) 엑셀업로드 후 저장한다.
	 */
	@Override
	public HashMap saveFD02033_01(DataSet ds, Map map) throws Exception {

		/* [1] 엑셀업로드처리 전 ADMI.AFD_RCPMNY_EXCEL테이블을 DELETE한다. */
    	commonDAO.delete("Afd02SQL.delete_afdRcpmnyExcel_FD02033_01");
    	
    	/* [2] 엑셀업로드처리 데이터를 ADMI.AFD_RCPMNY_EXCEL테이블에 INSERT한다. */
        commonDAO.saveData("Afd02SQL.insertFD02033_01Excel", "", "", ds);
        
        /* [3] 엑셀업로드처리 프로시저를 호출한다. */
        HashMap rtnMap = commonDAO.saveDataSP("Afd02SQL.callP_FD_RCPMNY_EXCEL", map);
        
        //HashMap rtnMap = new HashMap();
        
        return rtnMap;
	}
	
	/**
	 * 변동사항을 조회한다.
	 * @throws Exception 
	 */
	public List<?> selectChangeData(Map map) throws Exception{
		
		List<?> resultList = commonDAO.selectData("Afd02SQL.selectChangeData", map);
		return resultList;
	}
	
	/**
	 * 변동사항 상세내역을 조회한다.
	 * @throws Exception 
	 */
	public List<?> selectChangeDataDetail(Map map) throws Exception{
		
		List<?> resultList = commonDAO.selectData("Afd02SQL.selectChangeDataDetail", map);
		return resultList;
	}
	
	/**
	 * 경리팀에 제출할 엑셀을 조회한다.
	 */
	public List<?> selectAccountancy(Map map) throws Exception{
		
		List<?> resultList = commonDAO.selectData("Afd02SQL.selectAccountancy", map);
		return resultList;
	}

	/**
	 * 일반(지정)기탁| 엑셀을 조회한다.
	 * @throws Exception 
	 */
	@Override
	public List<?> selectRcpmny01(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd02SQL.selectRcpmny01", map);
		return resultList;
	}

	/**
	 * 일반기탁|| 엑셀을 조회한다.
	 * @throws Exception 
	 */
	@Override
	public List<?> selectRcpmny11(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd02SQL.selectRcpmny11", map);
		return resultList;
	}

	/**
	 * 지정기탁관리_main 조회
	 * @throws Exception 
	 */
	@Override
	public List<?> selectAFD02070_main(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd02SQL.selectAFD02070_main", map);
		return resultList;
	}
	
	/**
	 * 지정기탁관리_detail01 조회
	 * @throws Exception 
	 */
	@Override
	public List<?> selectAFD02070_detail01(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd02SQL.selectAFD02070_detail01", map);
		return resultList;
	}
	/**
	 * 지정기탁관리_detail02 조회
	 * @throws Exception 
	 */
	@Override
	public List<?> selectAFD02070_detail02(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd02SQL.selectAFD02070_detail02", map);
		return resultList;
	}
	
	/**
	 * 지정기탁관리_main 저장
	 * @throws Exception 
	 */
	@Override
	public void saveAFD02070_main(DataSet ds) throws Exception {
		String insertSqlId = "Afd02SQL.insertAFD02070";
		String updateSqlId = "Afd02SQL.updateAFD02070";
		String deleteSqlId ="Afd02SQL.deleteAFD02070";
		
		commonDAO.saveData(insertSqlId, updateSqlId, deleteSqlId, ds);
	}
	
	/**
	 * 지정기탁관리detail01 저장
	 * @throws Exception 
	 */
	@Override
	public void saveAFD02070_detail01(DataSet ds) throws Exception {
		String insertSqlId = "Afd02SQL.insertAFD02070_01";
		String updateSqlId = "Afd02SQL.updateAFD02070_01";
		String deleteSqlId ="Afd02SQL.deleteAFD02070_01";
		
		commonDAO.saveData(insertSqlId, updateSqlId, deleteSqlId, ds);
	}
	
	/**
	 * 지정기탁관리_detail02 저장
	 * @throws Exception 
	 */
	@Override
	public void saveAFD02070_detail02(DataSet ds) throws Exception {
		String insertSqlId = "Afd02SQL.insertAFD02070_02";
		String updateSqlId = "Afd02SQL.updateAFD02070_02";
		String deleteSqlId ="Afd02SQL.deleteAFD02070_02";
		
		commonDAO.saveData(insertSqlId, updateSqlId, deleteSqlId, ds);
	}
	
	/**
	 * 지정기탁관리_excel 조회
	 * @throws Exception 
	 */
	@Override
	public List<?> selectAFD02070_excel(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd02SQL.selectAFD02070_excel", map);
		return resultList;
	}
	
	/**
	 * 대외협력팀 계좌내역 조회
	 */
	@Override
	public List<?> selectAFD02080(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd02SQL.selectAFD02080", map);
		return resultList;
	}
	
}
