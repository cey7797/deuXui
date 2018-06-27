package deu.admi.afd.afd02.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nexacro.xapi.data.DataSet;

public interface Afd02Service {
	/**
	 * 기금레벨별 코드를 조회한다..
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public abstract List<?> retrieveCntrctMember (Map map) throws Exception;
	
	/**
	 * 약정번호를 조회한다.
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public abstract List<?> retrieveEnggMgnNo (Map map) throws Exception;
	
	/**
	 * 약정을 저장한다.
	 * @param ds
	 * @return
	 * @throws Exception
	 */
	public List<?> saveAfdCntrct (DataSet ds) throws Exception;
	
	/**
	 * 약정을 조회한다.
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public abstract List<?> selectFixCdComboList (Map map) throws Exception;
	
	/**
	 * 약정을 조회한다.
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public abstract List<?> retrieveAfdCntrct (Map map) throws Exception;
	
	
	/**
	 * 온라인 약정상태를 조회한다.
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public abstract List<?> retrieveOnlnCntrct (Map map) throws Exception;
	
	/**
	 * 온라인 약정상태를 저장한다.
	 * @param ds
	 * @throws Exception
	 */
	public void saveOnlnCntrct (DataSet ds) throws Exception;
	
	/**
	 * dm 발송 내역을 조회한다.
	 */
	public abstract List<?> retrieveAfdDmsndng (Map map) throws Exception;
	
	/**
	 * dm 발송 관리 회원을 조회한다.
	 */
	public abstract List<?> retrieveAfdDmsndngMember (Map map) throws Exception;
	
	/**
	 * DM 발송을 내역을 저장한다.
	 * @param ds
	 * @throws Exception
	 */
	public void saveAfdDmSndng (DataSet ds) throws Exception;
	
	/**
	 * 입금내역을 조회한다.
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public abstract List<?> retrieveRcpmny(Map map) throws Exception;
	
	
	/**
	 * 입급내역을 저장한다.
	 * @param ds
	 * @throws Exception
	 */
	public void saveRcpmny(DataSet ds) throws Exception;
	
	/**
	 * 행사/예우정보를 조회한다.
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public abstract List<?> retrieveEventDtl(Map map) throws Exception;
	
	/**
	 * 행사/예우정보를 저장한다.
	 * @param ds
	 * @throws Exception
	 */
	public void saveEventDtl(DataSet ds) throws Exception;
	
	/**
	 * 회원구분코드를 조회한다.
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public abstract List<?> retrieveMembas(Map map) throws Exception;
	
	/**
	 * 행사/예우대상를 조회한다.
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public abstract List<?> retrieveEventng(Map map) throws Exception;
	
	/**
	 * 행사/예우대상자를 저장한다.
	 * @param ds
	 * @throws Exception
	 */
	public void saveEventng(DataSet ds) throws Exception;
	
	/**
	 * 예우현황을 조회한다.
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public abstract List<?> retrieveEventStatus(Map map) throws Exception;

    
    /*#########################################################################################################################################*/
    /* 약정회원정보팝업(admi/afd/afd02/afd02030_p01.xfdl)                                                                                                  */
    /*#########################################################################################################################################*/

	
	/**
	 * 약정회원정보팝업을 조회한다.
	 */
	public abstract List<?> selectAFD02030_P01(Map map) throws Exception;

    
    /*#########################################################################################################################################*/
    /* CMS입금관리(admi/afd/afd02/afd02031.xfdl)                                                                                                  */
    /*#########################################################################################################################################*/

    
	/**
	 * CMS입금관리를(을) 조회한다.
	 */
	public abstract List<?> selectFD02031_01(Map map) throws Exception;

	/**
	 * CMS입금관리를(을) 엑셀업로드 후 저장 전 체크
	 */
	public abstract List<?> selectFD02031_02(Map map) throws Exception; 
	
	/**
	 * CMS입금관리를(을) 엑셀업로드 후 저장한다.
	 */
	public abstract HashMap saveFD02031_01(DataSet ds, Map map) throws Exception;
    
	
    /*#########################################################################################################################################*/
    /* 급여공제관리(admi/afd/afd02/afd02032.xfdl)                                                                                                  */
    /*#########################################################################################################################################*/


	/**
	 * 급여공제관리를(을) 조회한다.
	 */
	public abstract List<?> selectFD02032_01(Map map) throws Exception;
	
	/**
	 * 급여공제관리 기존 데이터 조회한다.
	 */
	public abstract List<?> selectFD02032_02(Map map) throws Exception;
	
	/**
	 * 급여공제관리 데이터 생성한다.
	 * @throws Exception 
	 */
	public abstract void createFD02032(DataSet ds) throws Exception;
	
	/**
	 * 예우코드정보 저장한다.
	 * @throws Exception 
	 */
	public abstract void saveFD02032(DataSet ds) throws Exception;
	
	/**
	 * 지출일 일괄적용
	 * @throws Exception 
	 */
	public abstract void updateFD02032(DataSet ds) throws Exception;
	

    /*#########################################################################################################################################*/
	/* 동문회관리(admi/afd/afd02/afd02033.xfdl)                                                                                                */
    /*#########################################################################################################################################*/


	/**
	 * 동문회관리를(을) 조회한다.
	 */
	public abstract List<?> selectFD02033_01(Map map) throws Exception;
	
	
	/**
	 * 동문회를(을) 엑셀업로드 후 저장한다.
	 */
	public abstract HashMap saveFD02033_01(DataSet ds, Map map) throws Exception;
	
	/**
	 * 변동사항을 조회한다.
	 * @throws Exception 
	 */
	public abstract List<?> selectChangeData(Map map) throws Exception;
	
	/**
	 * 변동사항 상세내역을 조회한다.
	 * @throws Exception 
	 */
	public abstract List<?> selectChangeDataDetail(Map map) throws Exception;
	
	/**
	 * 경리팀에 제출할 엑셀을 조회한다.
	 */
	public abstract List<?> selectAccountancy(Map map) throws Exception;

	/**
	 * 일반(지정)기탁 엑셀을 조회한다.
	 * @throws Exception 
	 */
	public abstract List<?> selectRcpmny01(Map map) throws Exception;

	/**
	 * 일반기탁|| 엑셀을 조회한다.
	 * @throws Exception 
	 */
	public abstract List<?> selectRcpmny11(Map map) throws Exception;

	/**
	 * 지정기탁관리_main 조회
	 * @throws Exception 
	 */
	public abstract List<?> selectAFD02070_main(Map map) throws Exception;

	/**
	 * 지정기탁관리_detail01 조회
	 * @throws Exception 
	 */
	public abstract List<?> selectAFD02070_detail01(Map map) throws Exception;

	/**
	 * 지정기탁관리_detail02 조회
	 * @throws Exception 
	 */
	public abstract List<?> selectAFD02070_detail02(Map map) throws Exception;
	
	/**
	 * 지정기탁관리_main 저장
	 * @throws Exception 
	 */
	public abstract void saveAFD02070_main(DataSet ds)throws Exception;
	
	/**
	 * 지정기탁관리_detail01 저장
	 * @throws Exception 
	 */
	public abstract void saveAFD02070_detail01(DataSet ds)throws Exception;
	
	/**
	 * 지정기탁관리_detail02 저장
	 * @throws Exception 
	 */
	public abstract void saveAFD02070_detail02(DataSet ds) throws Exception;
	
	/**
	 * 지정기탁관리_excel 조회
	 * @throws Exception 
	 */
	public abstract List<?> selectAFD02070_excel(Map map) throws Exception;
	
	/**
	 * 대외협력팀 계좌내역 조회 
	 */
	public abstract List<?> selectAFD02080(Map map) throws Exception;
	
}
