package deu.com.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Properties;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import deu.com.service.ComService;
import deu.comm.login.web.SessionManager;
import deu.comm.unione.uniRequest;
import deu.comm.unione.uniResponse;

import com.nexacro.xapi.data.DataSet;
import com.nexacro.xapi.data.DataSetList;

import org.codehaus.jackson.map.ObjectMapper;

@Controller
public class ComController {

	/** comcomService */
	@Resource(name = "comService")
	private ComService comService; 

	/**
	 * 공통코드 을(를) 조회한다.
	 */
	@RequestMapping(value = "/selectComCd.do")
	public void  selectComCd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		SessionManager ssmanager = SessionManager.getInstance();
		
		System.out.println("ssmanager.getLoginSessionList() : "+ssmanager.getLoginSessionList());
		
		try{
			
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = comService.selectComCd(map);
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
			
		} catch (Exception e)
		{
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 공통코드 을(를) 저장한다.
	 */
	@RequestMapping(value = "/saveComCd.do")
	public void  saveComCd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			DataSet ds = uniReq.getDS("resultList");
	
			comService.saveComCd(ds);
			
			uniRes.sendPlatformData(response);
		}catch(Exception e){
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 공통코드상세 을(를) 조회한다.
	 */
	@RequestMapping(value = "/selectComCdSub.do")
	public void  selectComCdSub(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = comService.selectComCdSub(map);

			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
		}catch(Exception e){
			uniRes.sendException(response, e);
		}
		
	}
	
	/**
	 * 공통코드 을(를) 저장한다.
	 */
	@RequestMapping(value = "/saveComCdSub.do")
	public void  saveComCdSub(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try{
			uniReq.receivePlatformData(request);
			
			DataSet ds = uniReq.getDS("resultList");
	
			comService.saveComCdSub(ds);
			
			uniRes.sendPlatformData(response);
		
		} catch (Exception e){
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 공통코드 을(를) 조회한다.
	 */
	@RequestMapping(value = "/selectComCdCombo.do")
	public void  selectComCdCombo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			String strDataSet = (String) map.get("strDataSet");
			String strGrpCd   = (String) map.get("strGrpCd");
			String strUseYn   = (String) map.get("strUseYn");
			String strModeFg  = (String) map.get("strModeFg");
			String strWhere   = (String) map.get("strWhere");
			
			if(!"".equals(strDataSet)){
				// 화면에 넘겨준 dataSet 및 공통코드 문자열의 data parsing
				StringTokenizer stDataSet 		= new StringTokenizer(strDataSet, "|");
				StringTokenizer stGrpCd 		= new StringTokenizer(strGrpCd, "|");
				StringTokenizer stUseYn 		= new StringTokenizer(strUseYn, "|");
				StringTokenizer stModeFg 		= new StringTokenizer(strModeFg, "|");
				StringTokenizer stWhere 		= new StringTokenizer(strWhere, "|");
				
				while(stDataSet.hasMoreTokens()) {
					String tmpDataSet = stDataSet.nextToken();
					
					Map nMap = new HashMap();
					List listResult = new ArrayList();
					
					nMap.put("strType" , stModeFg.nextToken().trim());
					nMap.put("strGrpCd" , stGrpCd.nextToken().trim());
					nMap.put("strUseYn" , stUseYn.nextToken().trim());
					nMap.put("strWhere" , stWhere.nextToken().trim());
					
					listResult = comService.selectComCdCombo(nMap);
					
					uniRes.setDataSet(listResult, tmpDataSet);
					
				}
			}
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 공통코드 을(를) 조회한다.
	 */
	@RequestMapping(value = "/selectComCdComboOrder.do")
	public void  selectComCdComboOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			String strDataSet = (String) map.get("strDataSet");
			String strGrpCd   = (String) map.get("strGrpCd");
			String strUseYn   = (String) map.get("strUseYn");
			String strModeFg  = (String) map.get("strModeFg");
			String strWhere   = (String) map.get("strWhere");
			String strOrder   = (String) map.get("strOrder");
			
			if(!"".equals(strDataSet)){
				// 화면에 넘겨준 dataSet 및 공통코드 문자열의 data parsing
				StringTokenizer stDataSet 		= new StringTokenizer(strDataSet, "|");
				StringTokenizer stGrpCd 		= new StringTokenizer(strGrpCd, "|");
				StringTokenizer stUseYn 		= new StringTokenizer(strUseYn, "|");
				StringTokenizer stModeFg 		= new StringTokenizer(strModeFg, "|");
				StringTokenizer stWhere 		= new StringTokenizer(strWhere, "|");
				StringTokenizer stOrder 		= new StringTokenizer(strOrder, "|");
				
				while(stDataSet.hasMoreTokens()) {
					String tmpDataSet = stDataSet.nextToken();
					
					Map nMap = new HashMap();
					List listResult = new ArrayList();
					
					nMap.put("strType" , stModeFg.nextToken().trim());
					nMap.put("strGrpCd" , stGrpCd.nextToken().trim());
					nMap.put("strUseYn" , stUseYn.nextToken().trim());
					nMap.put("strWhere" , stWhere.nextToken().trim());
					nMap.put("strOrder" , stOrder.nextToken().trim());
					listResult = comService.selectComCdComboOrder(nMap);
					
					uniRes.setDataSet(listResult, tmpDataSet);
					
				}
			}
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 우편번호 을(를) 조회한다.
	 */
	@RequestMapping(value = "/selectZipPopUp.do")
	public void  selectZipPopUp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = comService.selectZipPopUp(map);
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
		
	}
		
	/**
	 * 시스템 을(를) 조회한다.
	 * ds_main
	 */
	@RequestMapping(value = "/selectComCsi07.do")
	public void  selectComCsi07(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = comService.selectComCsi07(map);
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 시스템 을(를) 저장한다.
	 */
	@RequestMapping(value = "/saveComCsi07.do")
	public void  saveComCsi07(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			DataSet ds = uniReq.getDS("resultList");
			
			comService.saveComCsi07(ds);
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 서브시스템 을(를) 조회한다.
	 * ds_sub
	 */
	@RequestMapping(value = "/selectComCsi07Sub.do")
	public void  selectComCsi07Sub(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = comService.selectComCsi07Sub(map);
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 시스템 을(를) 저장한다.
	 */
	@RequestMapping(value = "/saveComCsi07Sub.do")
	public void  saveComCsi07Sub(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			DataSet ds = uniReq.getDS("resultList");
			
			comService.saveComCsi07Sub(ds);
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 메뉴 을(를) 조회한다.
	 * ds_main
	 */
	@RequestMapping(value = "/selectComCsi08.do")
	public void  selectComCsi08(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = comService.selectComCsi08(map);
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 메뉴 을(를) 저장한다.
	 */
	@RequestMapping(value = "/saveComCsi08.do")
	public void  saveComCsi08(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			DataSet ds = uniReq.getDS("resultList");
			
			comService.saveComCsi08(ds);
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 프로그램 을(를) 조회한다.
	 * ds_main
	 */
	@RequestMapping(value = "/selectComCsi08Sub.do")
	public void  selectComCsi08Sub(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = comService.selectComCsi08Sub(map);
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 프로그램 을(를) 저장한다.
	 */
	@RequestMapping(value = "/saveComCsi08Sub.do")
	public void  saveComCsi08Sub(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			DataSet ds = uniReq.getDS("resultList");
			
			comService.saveComCsi08Sub(ds);
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 권한등록 을(를) 조회한다.
	 * ds_roleGroup
	 */
	@RequestMapping(value = "/selectRoleGroup.do")
	public void  selectRoleGroup(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = comService.selectRoleGroup(map);
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 권한콤보 을(를) 조회한다.
	 * ds_roleGroup
	 */
	@RequestMapping(value = "/selectRoleGroupCmb.do")
	public void  selectRoleGroupCmb(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = comService.selectRoleGroupCmb(map);
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 권한등록 을(를) 저장한다.
	 */
	@RequestMapping(value = "/saveRoleGroup.do")
	public void  saveRoleGroup(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			DataSet ds = uniReq.getDS("resultList");
			
			comService.saveRoleGroup(ds);
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 권한등록(유저) 을(를) 조회한다.
	 * ds_roleGroupUser
	 */
	@RequestMapping(value = "/selectRoleGroupUser.do")
	public void  selectRoleGroupUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();

		try {
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = comService.selectRoleGroupUser(map);
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
			
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 권한등록(프로그램) 을(를) 조회한다.
	 * ds_roleGroupPgm
	 */
	@RequestMapping(value = "/selectRoleGroupPgm.do")
	public void  selectRoleGroupPgm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = comService.selectRoleGroupPgm(map);
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 권한별 프로그램 을(를) 조회한다.
	 * ds_main
	 */
	@RequestMapping(value = "/selectRolePgm.do")
	public void  selectRolePgm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = comService.selectRolePgm(map);
			List<?> list2 = comService.selectRolePgmTree(map);
			uniRes.setDataSet(list, "resultList");
			uniRes.setDataSet(list2, "resultList2");
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 권한별 프로그램 을(를) 저장한다.
	 */
	@RequestMapping(value = "/saveRolePgm.do")
	public void  saveRolePgm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			DataSet ds = uniReq.getDS("resultList");
			
			comService.saveRolePgm(ds);
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 권한별 프로그램 을(를) 저장한다. 등록
	 */
	@RequestMapping(value = "/saveRolePgmRegi.do")
	public void  saveRolePgmRegi(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			DataSet ds = uniReq.getDS("resultList");
			comService.saveRolePgmRegi(ds);
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 권한별 프로그램 을(를) 저장한다. 등록해제
	 */
	@RequestMapping(value = "/saveRolePgmNonRegi.do")
	public void  saveRolePgmNonRegi(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			DataSet ds = uniReq.getDS("resultList");
			
			comService.saveRolePgmNonRegi(ds);
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 권한별 프로그램 상세 을(를) 조회한다.
	 * ds_detail
	 */
	@RequestMapping(value = "/selectRolePgmDet.do")
	public void  selectRolePgmDet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = comService.selectRolePgmDet(map);
			List<?> list2 = comService.selectRolePgmDetTree(map);
			uniRes.setDataSet(list, "resultList");
			uniRes.setDataSet(list2, "resultList2");
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 사용자별 권한관리 (유저) 을(를) 조회한다.
	 * ds_user
	 */
	@RequestMapping(value = "/selectUserPgm.do")
	public void  selectUserPgm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = comService.selectUserPgm(map);
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 사용자별 권한관리 을(를) 저장한다. 등록
	 */
	@RequestMapping(value = "/saveUserPgmInst.do")
	public void  saveUserPgmInst(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			DataSet ds = uniReq.getDS("resultList");
			
			comService.saveUserPgmInst(ds);
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 사용자별 권한관리 을(를) 저장한다. 등록해제
	 */
	@RequestMapping(value = "/saveUserPgmDel.do")
	public void  saveUserPgmDel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			DataSet ds = uniReq.getDS("resultList");
			
			comService.saveUserPgmDel(ds);
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 사용자별 권한관리 (미등록) 을(를) 조회한다.
	 * ds_nonRegi
	 */
	@RequestMapping(value = "/selectNonRegiPgm.do")
	public void  selectNonRegiPgm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = comService.selectNonRegiPgm(map);
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 사용자별 권한관리 (등록)) 을(를) 조회한다.
	 * ds_regi
	 */
	@RequestMapping(value = "/selectRegiPgm.do")
	public void  selectRegiPgm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = comService.selectRegiPgm(map);
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 신분별 권한관리 (등록)) 을(를) 조회한다.
	 * ds_regi
	 */
	@RequestMapping(value = "/selectRankRegiPgm.do")
	public void  selectRankRegiPgm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = comService.selectRankRegiPgm(map);
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 신분별 권한관리 (미등록)) 을(를) 조회한다.
	 * ds_unregi
	 */
	@RequestMapping(value = "/selectUnrankRegiPgm.do")
	public void  selectUnrankRegiPgm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = comService.selectUnrankRegiPgm(map);
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 사용자분류 재조회 을(를) 조회한다.
	 * ds_unregi
	 */
	@RequestMapping(value = "/selectUserGb.do")
	public void  selectUserGb(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = comService.selectUserGb(map);
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 신분별 권한관리 (등록) 을(를) 저장한다. 등록
	 */
	@RequestMapping(value = "/saveRankRegiPgm.do")
	public void  saveRankRegiPgm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			DataSet ds = uniReq.getDS("resultList");
			
			comService.saveRankRegiPgm(ds);
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 신분별 권한관리 (미등록) 을(를) 저장한다. 등록해제
	 */
	@RequestMapping(value = "/saveUnrankRegiPgm.do")
	public void  saveUnrankRegiPgm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			DataSet ds = uniReq.getDS("resultList");
			
			comService.saveUnrankRegiPgm(ds);
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * SMS 문자를 전송한다. (KT 크로샷? 서버에 인서트한다.)
	 */
	@RequestMapping(value = "/saveSmsSend.do")
	public void  saveSmsSend(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			DataSet ds = uniReq.getDS("resultList");
			
			comService.saveSmsSend(ds);
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * DB 서버 시간을(를) 조회한다.
	 * gds_currentDate
	 */
	@RequestMapping(value = "/selectCurrentDate.do")
	public void  selectCurrentDate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = comService.selectCurrentDate(map);
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 사용자그룹을(를) 조회한다.
	 * gds_currentDate
	 */
	@RequestMapping(value = "/selectUserGroup.do")
	public void  selectUserGroup(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = comService.selectUserGroup(map);
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 파일업로드 정보을(를) 조회한다.
	 * 
	 */
	@RequestMapping(value = "/selectComFile.do")
	public void  selectComFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = comService.selectComFile(map);
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 파일업로드 정보을(를) 저장한다. 
	 */
	@RequestMapping(value = "/saveComFile.do")
	public void  saveComFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			DataSet ds = uniReq.getDS("resultList");
			
			comService.saveComFile(ds);
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 파일업로드상세 정보을(를) 조회한다.
	 * 
	 */
	@RequestMapping(value = "/selectComFileSub.do")
	public void  selectComFileSub(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = comService.selectComFileSub(map);
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 파일업로드상세 정보을(를) 저장한다. 
	 */
	@RequestMapping(value = "/saveComFileSub.do")
	public void  saveComFileSub(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			DataSet ds = uniReq.getDS("resultList");
			
			comService.saveComFileSub(ds);
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * SMS를 전송한다.
	 * ds_sms
	 */
	@RequestMapping(value = "/sendSms.do")
    public void sendSms(HttpServletRequest request,	HttpServletResponse response) throws Exception {
    	
    	uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        
        try {
        	uniReq.receivePlatformData(request);

        	DataSet ds = uniReq.getDS("resultList");
        	
        	comService.sendSms(ds);
        	
        	uniRes.sendPlatformData(response);
			
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
}
