package deu.comm.menu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import deu.comm.unione.MetadataUtil;
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

@Repository("commMenuDAO")
public class CommMenuDAO extends EgovAbstractDAO {

	/**
	 * 대분류 메뉴를 조회한다.
	 */
	public List<?> selectSysMenu(Map map) throws Exception {
		String queryId = new String();
		String sqlId = new String();
		
		sqlId = "commMenuDAO.selectSysMenu";
		
		return list(sqlId, map);
	}
	
	/**
	 * 대분류 메뉴를 조회한다.
	 */
	public List<?> selectSysCombo(Map map) throws Exception {
		String queryId = new String();
		String sqlId = new String();
		
		sqlId = "commMenuDAO.selectSysCombo";
		
		return list("commMenuDAO.selectSysCombo", map);
	}
	
	/**
	 * 중분류 메뉴를 조회한다.
	 */
	public List<?> selectSubMenu(Map map) throws Exception {
		String queryId = new String();
		String sqlId = new String();
		
		sqlId = "commMenuDAO.selectSubMenu";
		
		return list("commMenuDAO.selectSubMenu", map);
	}
	
	/**
	 * 중분류 메뉴를 조회한다.
	 */
	public List<?> selectSubCombo(Map map) throws Exception {
		String queryId = new String();
		String sqlId = new String();
		
		sqlId = "commMenuDAO.selectSubCombo";
		
		return list("commMenuDAO.selectSubCombo", map);
	}
	
	/**
	 * 프로그램 메뉴를 조회한다.
	 */
	public List<?> selectLeftMenu(Map map) throws Exception {
		String queryId = new String();
		String sqlId = new String();
		
		sqlId = "commMenuDAO.selectLeftMenu";
		
		return list("commMenuDAO.selectLeftMenu", map);
	}
	
	/**
	 * 프로그램 트리 메뉴를 조회한다.
	 */
	public List<?> selectTreeMenu(Map map) throws Exception {
		String queryId = new String();
		String sqlId = new String();
		
		sqlId = "commMenuDAO.selectTreeMenu";

		return list("commMenuDAO.selectTreeMenu", map);
	}
	
	/**
	 * 마이메뉴 트리를 조회한다.
	 */
	public List<?> selectTreeFavo(Map map) throws Exception {
		String queryId = new String();
		String sqlId = new String();
		
		sqlId = "commMenuDAO.selectTreeFavo";
		
		return list("commMenuDAO.selectTreeFavo", map);
	}
	
	/**
	 * 프로그램 메뉴를 조회한다.
	 */
	public List<?> selectMenuCombo(Map map) throws Exception {
		String queryId = new String();
		String sqlId = new String();
		
		sqlId = "commMenuDAO.selectMenuCombo";
		
		return list("commMenuDAO.selectMenuCombo", map);
	}
	
	/**
	 * 프로그램 ID를 조회한다.
	 */
	public List<?> selectProgCombo(Map map) throws Exception {
		String queryId = new String();
		String sqlId = new String();
		
		sqlId = "commMenuDAO.selectProgCombo";
		
		return list("commMenuDAO.selectProgCombo", map);
	}
	
	/**
	 * 프로그램 캠퍼스(콤보)를 조회한다.
	 */
	public List<?> selectCampusCombo(Map map) throws Exception {
		String queryId = new String();
		String sqlId = new String();
		
		sqlId = "commMenuDAO.selectCampusCombo";
		
		return list("commMenuDAO.selectCampusCombo", map);
	}
	
	/**
	 * 프로그램 전공(콤보)를 조회한다.
	 */
	public List<?> selectMajorCdCombo(Map map) throws Exception {
		String queryId = new String();
		String sqlId = new String();
		
		sqlId = "commMenuDAO.selectMajorCdCombo";
		
		return list("commMenuDAO.selectMajorCdCombo", map);
	}
}
