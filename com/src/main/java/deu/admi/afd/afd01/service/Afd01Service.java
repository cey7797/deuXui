package deu.admi.afd.afd01.service;

import java.util.List;
import java.util.Map;

import com.nexacro.xapi.data.DataSet;

public interface Afd01Service {

    
    /*#########################################################################################################################################*/
    /* 기금코드정보관리(admi/afd/afd01/afd01010.xfdl)                                                                                                  */
    /*#########################################################################################################################################*/


	/**
	 * 기금코드 대/중구분 를(을) 조회한다.
	 */
	public abstract List<?> retrieveEndwDivCd (Map map) throws Exception;
	
	/**
	 * 기금코드내역를(을) 조회한다.
	 */
	public abstract List<?> retrieveEndw (Map map) throws Exception;
	
	/**
	 * 기금코드내역를(을) 저장한다.
	 */
	public void saveEndw (DataSet ds) throws Exception;
	
	/**
	 * 회원구분정보를 조회한다.
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public abstract List<?> retrieveMembas (Map map) throws Exception;
	
	
	/**
	 * 회원구분코드정보를 조회한다.
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public abstract List<?> retrieveMembasCd (Map map) throws Exception;
	
	
	/**
	 * 회원구분정보를 저장한다.
	 * @param ds
	 * @throws Exception
	 */
	public void saveMembas (DataSet ds) throws Exception;
	
	/**
	 * 회원구분별 상세예우를 조회한다.
	 * @param map
	 * @return
	 * @throws Exception
	 */
	
	public abstract List<?> retrieveHonortreat (Map map) throws Exception;
	
	/**
	 * 회원구분별 상세예우를 저장한다.
	 * @param ds
	 * @throws Exception
	 */
	public void saveHonortreat (DataSet ds) throws Exception;


    /*#########################################################################################################################################*/
    /* 회원정보관리(admi/afd/afd01/afd01050.xfdl)                                                                                                  */
    /*#########################################################################################################################################*/

	
	/**
	 * 회원정보를 조회한다.
	 */
	public abstract List<?> selectAFD01050_01 (Map map) throws Exception;
	
	/**
	 * 회원정보를 저장한다.
	 */
	public  abstract List<?> saveAFD01050_01 (DataSet ds) throws Exception;
	
	/**
	 * 주민등록번호로 회원의 존재유무를 조회한다.
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public abstract List<?> retrieveWsJuminNo (Map map) throws Exception;
	
	public abstract List<?> retrieveAfdMemberNo (Map map) throws Exception;
	
	
	public abstract List<?> retrieveAfdCntrct (Map map) throws Exception;
	
	/**
	 * 회원별 DM 현황을 조회한다.
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public abstract List<?> retrieveAfdDmsndng (Map map) throws Exception;
	
	/**
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public abstract List<?> selectComboMembas (Map map) throws Exception;
	
	/*#########################################################################################################################################*/
    /* 예우코드정보관리(admi/afd/afd01/afd01015.xfdl)                                                                                                  */
    /*#########################################################################################################################################*/

	
	/**
	 *예우코드정보를 조회한다.
	 */
	public abstract List<?> selectAfdEvent (Map map) throws Exception;
	
	/**
	 * 예우코드정보 저장한다.
	 * @throws Exception 
	 */
	public abstract void saveAfdEvent(DataSet ds) throws Exception;
}