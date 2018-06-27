package deu.comm.unione.service;

import deu.comm.unione.vo.DataMap;

public interface AuthCheckInterceptorService {
	
	
	@SuppressWarnings("unchecked")
	public int doCntCntMenuAuth(DataMap dataMap);

	/**
	 * 교원지원자 용 
	 * @param dataMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int doCntCntMenuAuthRecruit(DataMap dataMap);

}