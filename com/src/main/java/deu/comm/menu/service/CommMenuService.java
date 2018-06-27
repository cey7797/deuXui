package deu.comm.menu.service;

import java.util.List;
import java.util.Map;

import com.nexacro.xapi.data.DataSet;

public interface CommMenuService {

	/**
	 * 대분류 메뉴코드를 조회한다.
	 * @throws Exception 
	 */
	public abstract List<?> selectSysMenu(Map map) throws Exception;
	
	/**
	 * 대분류 메뉴코드를 조회한다.
	 * @throws Exception 
	 */
	public abstract List<?> selectSysCombo(Map map) throws Exception;
	
	/**
	 * 중분류 메뉴코드를 조회한다.
	 * @throws Exception 
	 */
	public abstract List<?> selectSubMenu(Map map) throws Exception;
	
	/**
	 * 중분류 메뉴코드(콤보)를 조회한다.
	 * @throws Exception 
	 */
	public abstract List<?> selectSubCombo(Map map) throws Exception;
	
	/**
	 * 프로그램 메뉴를 조회한다.
	 * @throws Exception 
	 */
	public abstract List<?> selectLeftMenu(Map map) throws Exception;
	
	/**
	 * 프로그램 트리 메뉴를 조회한다.
	 * @throws Exception 
	 */
	public abstract List<?> selectTreeMenu(Map map) throws Exception;
	
	/**
	 * 프로그램 트리 메뉴를 조회한다.
	 * @throws Exception 
	 */
	public abstract List<?> selectTreeMenuPop(Map map) throws Exception;
	
	/**
	 * 마이메뉴 트리를 조회한다.
	 * @throws Exception 
	 */
	public abstract List<?> selectTreeFavo(Map map) throws Exception;
	
	/**
	 * 마이메뉴 트리를 조회한다.
	 * @throws Exception 
	 */
	public abstract List<?> selectTreeFavoPop(Map map) throws Exception;
	
	/**
	 * 마이메뉴 을(를) 저장한다.
	 * @throws Exception 
	 */
	public abstract void saveTreeFavo(DataSet ds) throws Exception;
	
	/**
	 * 프로그램 메뉴(콤보)를 조회한다.
	 * @throws Exception 
	 */
	public abstract List<?> selectMenuCombo(Map map) throws Exception;
	
	/**
	 * 프로그램 프로그램(콤보)를 조회한다.
	 * @throws Exception 
	 */
	public abstract List<?> selectProgCombo(Map map) throws Exception;
		
	/**
	 * 프로그램 부서(콤보)를 조회한다.
	 * @throws Exception 
	 */
	public abstract List<?> selectDeptCombo(Map map) throws Exception;
	
	/**
	 * 프로그램 메뉴를 조회한다.
	 * @throws Exception 
	 */
	public abstract List<?> selectMenuPopInfo(Map map) throws Exception;
	
}
