package deu.comm.menu.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nexacro.xapi.data.DataSet;

import deu.comm.menu.service.CommMenuService;
import deu.comm.unione.ConvertUtil;
import deu.comm.unione.uniRequest;
import deu.comm.unione.uniResponse;

@Controller
public class CommMenuController {

	/** comCsiService */
	@Resource(name = "commMenuService")
	private CommMenuService commMenuService; 
	
	/**
	 * 대분류 메뉴를 조회한다.
	 */
	@RequestMapping(value = "/comm/selectSysMenu.do")
	public void  selectSysMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			
			List<?> list = commMenuService.selectSysMenu(map);
	
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
			
		} catch (Exception e){
			uniRes.sendException(response, e);
		}
		
	}
	
	/**
	 * 대분류 메뉴(콤보)를 조회한다.
	 */
	@RequestMapping(value = "/comm/selectSysCombo.do")
	public void  selectSysCombo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = commMenuService.selectSysCombo(map);
	
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
			
		} catch (Exception e){
			uniRes.sendException(response, e);
		}
		
	}
	
	/**
	 * 중분류 메뉴를 조회한다.
	 */
	@RequestMapping(value = "/comm/selectSubMenu.do")
	public void  selectSubMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			
			List<?> list = commMenuService.selectSubMenu(map);
	
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
			
		} catch (Exception e){
			uniRes.sendException(response, e);
		}
		
	}
	
	/**
	 * 중분류 메뉴(콤보)를 조회한다.
	 */
	@RequestMapping(value = "/comm/selectSubCombo.do")
	public void  selectSubCombo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = commMenuService.selectSubCombo(map);
	
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
			
		} catch (Exception e){
			uniRes.sendException(response, e);
		}
		
	}
	
	/**
	 * 프로그램 메뉴를 조회한다.
	 */
	@RequestMapping(value = "/comm/selectLeftMenu.do")
	public void  selectLeftMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = commMenuService.selectLeftMenu(map);
	
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
			
		} catch (Exception e){
			uniRes.sendException(response, e);
		}
		
	}
	
	/**
	 * 프로그램 트리 메뉴를 조회한다.
	 */
	@RequestMapping(value = "/comm/selectTreeMenu.do")
	public void  selectTreeMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = commMenuService.selectTreeMenu(map);
	
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
			
		} catch (Exception e){
			uniRes.sendException(response, e);
		}
		
	}
	
	/**
	 * 프로그램 트리 메뉴를 조회한다.
	 */
	@RequestMapping(value = "/comm/selectTreeMenuPop.do")
	public void  selectTreeMenuPop(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = commMenuService.selectTreeMenuPop(map);
	
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
			
		} catch (Exception e){
			uniRes.sendException(response, e);
		}
		
	}
	
	/**
	 * 마이메뉴 트리를 조회한다.
	 */
	@RequestMapping(value = "/comm/selectTreeFavo.do")
	public void  selectTreeFavo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = commMenuService.selectTreeFavo(map);	
			uniRes.setDataSet(list, "resultList");
			
			list = commMenuService.selectTreeFavo(map);
			uniRes.setDataSet(list, "resultList2");
			
			uniRes.sendPlatformData(response);
			
		} catch (Exception e){
			uniRes.sendException(response, e);
		}
		
	}
	
	/**
	 * 마이메뉴 트리를 조회한다.
	 */
	@RequestMapping(value = "/comm/selectTreeFavoPop.do")
	public void  selectTreeFavoPop(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = commMenuService.selectTreeFavoPop(map);	
			uniRes.setDataSet(list, "resultList");
			
			list = commMenuService.selectTreeFavo(map);
			uniRes.setDataSet(list, "resultList2");
			
			uniRes.sendPlatformData(response);
			
		} catch (Exception e){
			uniRes.sendException(response, e);
		}
		
	}
	
	/**
	 * 마이메뉴을(를) 저장한다.
	 */
	@RequestMapping(value = "/comm/saveTreeFavo.do")
	public void  saveComCd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		uniReq.receivePlatformData(request);
		
		DataSet ds = uniReq.getDS("resultList");

		commMenuService.saveTreeFavo(ds);
		
		uniRes.sendPlatformData(response);
	}
	
	/**
	 * 프로그램 메뉴를 조회한다.
	 */
	@RequestMapping(value = "/comm/selectMenuCombo.do")
	public void  selectMenuCombo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = commMenuService.selectMenuCombo(map);
	
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
			
		} catch (Exception e){
			uniRes.sendException(response, e);
		}
		
	}
	
	/**
	 * 프로그램 프로그램ID를 조회한다.
	 */
	@RequestMapping(value = "/comm/selectProgCombo.do")
	public void  selectProgCombo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = commMenuService.selectProgCombo(map);
	
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
			
		} catch (Exception e){
			uniRes.sendException(response, e);
		}
		
	}
	
	/**
	 * 부서를 조회한다.
	 */
	@RequestMapping(value = "/comm/selectDeptCombo.do")
	public void  selectDeptCombo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = commMenuService.selectDeptCombo(map);
	
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
			
		} catch (Exception e){
			uniRes.sendException(response, e);
		}
		
	}
	
	/**
	 * 프로그램 메뉴 정보를 조회한다.
	 */
	@RequestMapping(value = "/comm/selectMenuPopInfo.do")
	public void  selectMenuPopInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = commMenuService.selectMenuPopInfo(map);
	
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
			
		} catch (Exception e){
			uniRes.sendException(response, e);
		}
	}
	
}
