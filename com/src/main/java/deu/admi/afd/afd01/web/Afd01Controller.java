package deu.admi.afd.afd01.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import deu.admi.afd.afd01.service.Afd01Service;
import deu.comm.unione.uniRequest;
import deu.comm.unione.uniResponse;

import com.nexacro.xapi.data.DataSet;

@Controller
public class Afd01Controller {
	
	private static final int RESERVE_POSSIBLE = 0;
	private static final int TIME_DUPLICATION = 300;
	
	
	@Resource(name="afd01Service")
	private Afd01Service afd01Service;

    
    /*#########################################################################################################################################*/
    /* 기금코드정보관리(admi/afd/afd01/afd01010.xfdl)                                                                                                  */
    /*#########################################################################################################################################*/


	/**
	 * 기금코드 대/중구분 를(을) 조회한다.
	 */
	@RequestMapping(value="admi/afd/afd01/retrieveEndwDivCd.do")
	public void retrieveEndwDivCd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			
			String disStr = (String) map.get("disStr");	
			if("T".equals(disStr)) {
				map.put("disStr","전체");
			}else if("S".equals(disStr)) {
				map.put("disStr","선택");
			}
			List<?> list = afd01Service.retrieveEndwDivCd(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}

	/**
	 * 기금코드내역를(을) 조회한다.
	 */
	@RequestMapping(value="admi/afd/afd01/retrieveEndw.do")
	public void retrieveEndw(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd01Service.retrieveEndw(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}

	/**
	 * 기금코드내역를(을) 저장한다.
	 */
	@RequestMapping(value="admi/afd/afd01/saveEndw.do")
	public void  saveEndw(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try{
			uniReq.receivePlatformData(request);
			DataSet ds = uniReq.getDS("resultList");
			
			afd01Service.saveEndw(ds);
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	                                               
	@RequestMapping(value="admi/afd/afd01/retrieveMembas.do")
	public void retrieveMembas(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd01Service.retrieveMembas(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	@RequestMapping(value="admi/afd/afd01/retrieveMembasCd.do")
	public void retrieveMembasCd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			String disStr = (String) map.get("disStr");	
			
			if("T".equals(disStr)) {
				map.put("disStr","전체");
			}else if("S".equals(disStr)) {
				map.put("disStr","선택");
			}			
			List<?> list = afd01Service.retrieveMembasCd(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	@RequestMapping(value="admi/afd/afd01/retrieveHonortreat.do")
	public void retrieveHonortreat(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd01Service.retrieveHonortreat(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	
	@RequestMapping(value="admi/afd/afd01/saveMembas.do")
	public void  saveMembas(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try{
			uniReq.receivePlatformData(request);
			DataSet ds = uniReq.getDS("resultList");
			
			afd01Service.saveMembas(ds);
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
		
	@RequestMapping(value="admi/afd/afd01/saveHonortreat.do")
	public void  saveHonortreat(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try{
			uniReq.receivePlatformData(request);
			DataSet ds = uniReq.getDS("resultList");
			
			afd01Service.saveHonortreat(ds);
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}

    
    /*#########################################################################################################################################*/
    /* 회원정보관리(admi/afd/afd01/afd01050.xfdl)                                                                                                  */
    /*#########################################################################################################################################*/


	/**
	 * 회원정보를 조회한다.
	 */
	@RequestMapping(value="admi/afd/afd01/selectAFD01050_01.do")
	public void selectAFD01050_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd01Service.selectAFD01050_01(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	} 

	/**
	 * 회원정보를 저장한다.
	 */
	@RequestMapping(value="admi/afd/afd01/saveAFD01050_01.do")
	public void  saveAfdMaster(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try{
			uniReq.receivePlatformData(request);
			DataSet ds = uniReq.getDS("resultList");
			List<?> list = afd01Service.saveAFD01050_01(ds);
			
			uniRes.setDataSet(list, "out_resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}	
	
	@RequestMapping(value="admi/afd/afd01/retrieveAfdMemberNo.do")
	public void retrieveAfdMemberNo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd01Service.retrieveAfdMemberNo(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	} 
	
	@RequestMapping(value="admi/afd/afd01/retrieveWsJuminNo.do")
	public void retrieveWsJuminNo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd01Service.retrieveWsJuminNo(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	} 
	
	@RequestMapping(value="admi/afd/afd01/retrieveAfdCntrct.do")
	public void retrieveAfdCntrct(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd01Service.retrieveAfdCntrct(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	@RequestMapping(value="admi/afd/afd01/retrieveAfdDmsndng.do")
	public void retrieveAfdDmsndng(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd01Service.retrieveAfdDmsndng(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	@RequestMapping(value="admi/afd/afd01/selectComboMembas.do")
	public void selectComboMembas(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd01Service.selectComboMembas(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	} 
	
	/*#########################################################################################################################################*/
    /* 예우코드정보관리(admi/afd/afd01/afd01015.xfdl)                                                                                                  */
    /*#########################################################################################################################################*/
	@RequestMapping(value="admi/afd/afd01/selectAfdEvent.do")
	public void selectAfdEvent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd01Service.selectAfdEvent(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 예우코드를 저장한다.
	 */
	@RequestMapping(value = "admi/afd/afd01/saveAfdEvent.do")
	public void  saveAfdEvent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try{
			uniReq.receivePlatformData(request);
			
			DataSet ds = uniReq.getDS("resultList");
	
			afd01Service.saveAfdEvent(ds);
			
			uniRes.sendPlatformData(response);
		
		} catch (Exception e){
			uniRes.sendException(response, e);
		}
	}
	
}