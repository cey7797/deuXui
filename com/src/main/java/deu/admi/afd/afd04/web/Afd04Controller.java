package deu.admi.afd.afd04.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import deu.admi.afd.afd04.service.Afd04Service;
import deu.comm.unione.uniRequest;
import deu.comm.unione.uniResponse;

import com.nexacro.xapi.data.DataSet;
import com.nexacro.xapi.util.StringUtils;

@Controller
public class Afd04Controller {
	
	private static final int RESERVE_POSSIBLE = 0;
	private static final int TIME_DUPLICATION = 300;
		
	@Resource(name="afd04Service")
	private Afd04Service afd04Service;

    
    /*#########################################################################################################################################*/
    /* 온라인약정(admi/afd/afd04/afd04010.xfdl)                                                                                                  */
    /*#########################################################################################################################################*/


	/**
	 * 온라인 약정을 저장한다.
	 */
	@RequestMapping(value="admi/afd/afd04/saveOnlieCntrct.do")
	public void  saveOnlieCntrct(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try{
			uniReq.receivePlatformData(request);
			DataSet ds = uniReq.getDS("resultList");

			afd04Service.saveOnlieCntrct(ds);
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}

    
    /*#########################################################################################################################################*/
    /* 나의기부현황(admi/afd/afd04/afd04020.xfdl)                                                                                                  */
    /*#########################################################################################################################################*/


	/**
	 * 나의 기부현황을 조회한다.
	 */
	@RequestMapping(value="admi/afd/afd04/retrieveDonationStatus.do")
	public void retrieveDonationStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd04Service.retrieveDonationStatus(map);
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}

	/**
	 *기금코드/명을 조회한다.
	 */
	@RequestMapping(value="admi/afd/afd04/retrieveEndw.do")
	public void retrieveEndw(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd04Service.retrieveEndw(map);
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 로그인 인증
	 */
	@RequestMapping(value="admi/afd/afd04/selectAFDMemberYn.do")
	public void selectAFDMemberYn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd04Service.selectAFDMemberYn(map);
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
}

