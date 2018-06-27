package deu.comm.login.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

@Repository("commLoginDAO")
public class CommLoginDAO extends EgovAbstractDAO {

	/**
	 * 로그인 한다.
	 */
	public List<?> doGetLogin(Map map) throws Exception {
		return list("commMenuDAO.doGetLogin", map);
	}
	
	/**
	 * 로그인(구매결의서용 팝업로긴) 한다.
	 */
	public List<?> doGetLoginPop(Map map) throws Exception {
		return list("commMenuDAO.doGetLoginPop", map);
	}
	
	/**
	 * 로그아웃 한다.
	 */
	public void doLogout(Map map) throws Exception {
		update("commMenuDAO.doLogout", map);
	}
	
	/**
	 * ID 존재여부를 체크한다.
	 */
	public int doGetUserCount(Map map) throws Exception {
		return (Integer) select("commMenuDAO.doGetUserCount", map);
	}
	
	/**
	 * 실패 카운트를 저장한다.
	 */
	public void doUpdateTemplate(Map map) {
		update("commMenuDAO.doIncrementLoginFailCnt", map);
	}
	
	/**
	 * 실패 카운트를 초기화한다.
	 */
	public void doInitLoginFailCnt(Map map) {
		update("commMenuDAO.doInitLoginFailCnt", map);
	}
	
	/**
	 * 임시인증번호를 초기화한다.
	 */
	public void doInitOnetimePwd(Map map) {
		update("commMenuDAO.doInitOnetimePwd", map);
	}
	
	/**
	 * 로그인 로그를 저장한다.
	 */
	public void doInsertLog(Map map) {		
		insert("commMenuDAO.doInsertLog", map);
	}
}
