package deu.admi.afd.afd05.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import deu.admi.afd.afd05.service.Afd05Service;
import deu.comm.unione.uniRequest;
import deu.comm.unione.uniResponse;

import com.nexacro.xapi.data.DataSet;
import com.nexacro.xapi.util.StringUtils;

@Controller
public class Afd05Controller {
	
	private static final int RESERVE_POSSIBLE = 0;
	private static final int TIME_DUPLICATION = 300;
		
	@Resource(name="afd05Service")
	private Afd05Service afd05Service;

	/**
	 * 연말정산기부금 확정총괄 조회
	 */
	@RequestMapping(value="admi/afd/afd05/selectExcel.do")
	public void retrieveDonationStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd05Service.selectExcel(map);
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 추천인 목록 조회
	 */
	@RequestMapping(value="admi/afd/afd05/selectExcel_02.do")
	public void selectExcel_02(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd05Service.selectExcel_02(map);
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 지정/일반 총괄표
	 */
	@RequestMapping(value="admi/afd/afd05/selectExcel_03.do")
	public void selectExcel_03(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd05Service.selectExcel_03(map);
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
}

