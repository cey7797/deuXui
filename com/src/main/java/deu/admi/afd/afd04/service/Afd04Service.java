package deu.admi.afd.afd04.service;

import java.util.List;
import java.util.Map;

import com.nexacro.xapi.data.DataSet;

public interface Afd04Service {

    
    /*#########################################################################################################################################*/
    /* 온라인약정(admi/afd/afd04/afd04010.xfdl)                                                                                                  */
    /*#########################################################################################################################################*/


	/**
	 * 온라인 약정을 저장한다.
	 */
	public void saveOnlieCntrct(DataSet ds) throws Exception;

    
    /*#########################################################################################################################################*/
    /* 나의기부현황(admi/afd/afd04/afd04020.xfdl)                                                                                                  */
    /*#########################################################################################################################################*/

	
	/**
	 * 나의 기부현황을 조회한다.
	 */
	public abstract List<?> retrieveDonationStatus(Map map) throws Exception;
	
	/**
	 *기금코드/명을 조회한다.
	 */
	public abstract List<?> retrieveEndw(Map map) throws Exception;
	

	/**
	 * 로그인 인증
	 */
	public abstract List<?> selectAFDMemberYn(Map map) throws Exception;	
}
