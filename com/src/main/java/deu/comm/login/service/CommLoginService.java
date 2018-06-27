package deu.comm.login.service;

import java.util.List;
import java.util.Map;

import com.nexacro.xapi.data.DataSet;

public interface CommLoginService {

	/**
	 * 로그인 한다.
	 * @throws Exception 
	 */
	public abstract List<?> doGetLogin(Map map, String sessId, String sessIp) throws Exception;
	
	public abstract List<?> doGetLoginPop(Map map, String sessId, String sessIp) throws Exception;
	
	public abstract void doLogout(String sessId) throws Exception;
	
}
