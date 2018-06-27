package deu.admi.afd.afd03.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import deu.admi.afd.afd03.service.Afd03Service;
import deu.comm.unione.uniRequest;
import deu.comm.unione.uniResponse;

import com.nexacro.xapi.data.DataSet;
import com.nexacro.xapi.util.StringUtils;

@Controller
public class Afd03Controller {
	
	private static final int RESERVE_POSSIBLE = 0;
	private static final int TIME_DUPLICATION = 300;
		
	@Resource(name="afd03Service")
	private Afd03Service afd03Service;


    /*#########################################################################################################################################*/
    /* 기부금영수증발급(admi/afd/afd03/afd03010.xfdl)                                                                                                  */
    /*#########################################################################################################################################*/


	/**
	 * 기부자목록을 조회한다.
	 */
	@RequestMapping(value="admi/afd/afd03/retrieveRcpter.do")
	public void retrieveRcpter(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			
			List<?> list = afd03Service.retrieveRcpter(map);
			uniRes.setDataSet(list, "resultList");			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}

	/**
	 * 입금내역을 조회한다.
	 */
	@RequestMapping(value="admi/afd/afd03/retrieveRcpmny.do")
	public void retrieveRcpmny(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd03Service.retrieveRcpmny(map);
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}

	/**
	 * 기부금 영수증정보를 저장한다.
	 */
	@RequestMapping(value="admi/afd/afd03/saveRciptissu.do")
	public void  saveRciptissu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try{
			uniReq.receivePlatformData(request);
			DataSet dsRcpter = uniReq.getDS("resultList");
			List<?> list = afd03Service.saveRciptissu(dsRcpter);
			
			uniRes.setDataSet(list, "out_resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 기부금 영수증 일괄발행
	 */
	@RequestMapping(value="admi/afd/afd03/createRciptissu.do")
	public void  createRciptissu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try{
			uniReq.receivePlatformData(request);
			DataSet dsRcpter = uniReq.getDS("resultList");
			HashMap nMap = afd03Service.createRciptissu(dsRcpter);
			
			List<Map<String, ?>> list = new ArrayList();
        	list.add(nMap);

        	uniRes.setDataSet(list, "resultList");
        	
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	

	@RequestMapping(value="admi/afd/afd03/test.do")
	public void test(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			
			List<?> list = afd03Service.test(map);
			uniRes.setDataSet(list, "resultList");			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
}

