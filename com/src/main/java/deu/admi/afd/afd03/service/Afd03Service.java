package deu.admi.afd.afd03.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nexacro.xapi.data.DataSet;

public interface Afd03Service {


    /*#########################################################################################################################################*/
    /* 기부금영수증발급(admi/afd/afd03/afd03010.xfdl)                                                                                                  */
    /*#########################################################################################################################################*/


	/**
	 * 기부자목록을 조회한다.
	 */
	public abstract List<?> retrieveRcpter(Map map) throws Exception;

	/**
	 * 입금내역을 조회한다.
	 */
	public abstract List<?> retrieveRcpmny(Map map) throws Exception;

	/**
	 * 기부금 영수증정보를 저장한다.
	 * @return 
	 */
	public List<?> saveRciptissu(DataSet ds) throws Exception;
	
	public HashMap createRciptissu(DataSet ds) throws Exception;


	public abstract List<?> test(Map map) throws Exception;

	public List<?> selectReceipNo(Map map) throws Exception;
}
