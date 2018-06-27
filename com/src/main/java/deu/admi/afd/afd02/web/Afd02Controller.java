package deu.admi.afd.afd02.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import deu.admi.afd.afd02.service.Afd02Service;
import deu.comm.unione.uniRequest;
import deu.comm.unione.uniResponse;

import com.nexacro.xapi.data.DataSet;
import com.nexacro.xapi.util.StringUtils;

@Controller
public class Afd02Controller {
	
	private static final int RESERVE_POSSIBLE = 0;
	private static final int TIME_DUPLICATION = 300;
		
	@Resource(name="afd02Service")
	private Afd02Service afd02Service;

	@RequestMapping(value="admi/afd/afd02/retrieveCntrctMember.do")
	public void retrieveEndw(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd02Service.retrieveCntrctMember(map);
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	@RequestMapping(value="admi/afd/afd02/retrieveEnggMgnNo.do")
	public void retrieveEnggMgnNo(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
			List<?> list = afd02Service.retrieveEnggMgnNo(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	@RequestMapping(value="admi/afd/afd02/selectFixCdComboList.do")
	public void selectFixCdComboList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd02Service.selectFixCdComboList(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	@RequestMapping(value="admi/afd/afd02/retrieveAfdCntrct.do")
	public void retrieveAfdCntrct(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd02Service.retrieveAfdCntrct(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	@RequestMapping(value="admi/afd/afd02/saveAfdCntrct.do")
	public void  saveAfdCntrct(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try{
			uniReq.receivePlatformData(request);
			DataSet ds = uniReq.getDS("resultList");
			List<?> list = afd02Service.saveAfdCntrct(ds);
			
			uniRes.setDataSet(list, "resultList1");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	@RequestMapping(value="admi/afd/afd02/retrieveOnlnCntrct.do")
	public void retrieveOnlnCntrct(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd02Service.retrieveOnlnCntrct(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	
	@RequestMapping(value="admi/afd/afd02/saveOnlnCntrct.do")
	public void  saveOnlnCntrct(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try{
			uniReq.receivePlatformData(request);
			DataSet ds = uniReq.getDS("resultList");

			afd02Service.saveOnlnCntrct(ds);
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	@RequestMapping(value="admi/afd/afd02/retrieveAfdDmsndngMember.do")
	public void retrieveAfdDmsndngMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd02Service.retrieveAfdDmsndngMember(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	@RequestMapping(value="admi/afd/afd02/retrieveAfdDmsndng.do")
	public void retrieveAfdDmsndng(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd02Service.retrieveAfdDmsndng(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	@RequestMapping(value="admi/afd/afd02/saveAfdDmSndng.do")
	public void  saveAfdDmSndng(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try{
			uniReq.receivePlatformData(request);
			DataSet ds = uniReq.getDS("resultList");

			afd02Service.saveAfdDmSndng(ds);
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	@RequestMapping(value="admi/afd/afd02/retrieveRcpmny.do")
	public void retrieveRcpmny(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd02Service.retrieveRcpmny(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	@RequestMapping(value="admi/afd/afd02/saveRcpmny.do")
	public void  saveRcpmny(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try{
			uniReq.receivePlatformData(request);
			DataSet ds = uniReq.getDS("resultList");
			
			afd02Service.saveRcpmny(ds);
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	@RequestMapping(value="admi/afd/afd02/retrieveEventDtl.do")
	public void retrieveEventDtl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd02Service.retrieveEventDtl(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
		
	@RequestMapping(value="admi/afd/afd02/saveEventDtl.do")
	public void  saveEventDtl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try{
			uniReq.receivePlatformData(request);
			DataSet ds = uniReq.getDS("resultList");
			
			afd02Service.saveEventDtl(ds);
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	@RequestMapping(value="admi/afd/afd02/retrieveMembas.do")
	public void retrieveMembas(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd02Service.retrieveMembas(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	@RequestMapping(value="admi/afd/afd02/retrieveEventng.do")
	public void retrieveEventng(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd02Service.retrieveEventng(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	@RequestMapping(value="admi/afd/afd02/saveEventng.do")
	public void  saveEventng(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try{
			uniReq.receivePlatformData(request);
			DataSet ds = uniReq.getDS("resultList");
			
			afd02Service.saveEventng(ds);
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	@RequestMapping(value="admi/afd/afd02/retrieveEventStatus.do")
	public void retrieveEventStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd02Service.retrieveEventStatus(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}

    
    /*#########################################################################################################################################*/
    /* 약정회원정보팝업(admi/afd/afd02/afd02030_p01.xfdl)                                                                                                  */
    /*#########################################################################################################################################*/


	/**
	 * 약정회원정보팝업을 조회한다.
	 */
	@RequestMapping(value="admi/afd/afd02/selectAFD02030_P01.do")
	public void selectAFD02030_P01(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd02Service.selectAFD02030_P01(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}

    
    /*#########################################################################################################################################*/
    /* CMS입금관리(admi/afd/afd02/afd02031.xfdl)                                                                                                  */
    /*#########################################################################################################################################*/

	
	/**
	 * CMS입금관리를(을) 조회한다.
	 */
	@RequestMapping(value="admi/afd/afd02/selectFD02031_01.do")
	public void selectFD02031_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd02Service.selectFD02031_01(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}

	/**
	 * CMS입금관리를(을) 엑셀업로드 후 저장 전 체크
	 */
	@RequestMapping(value="admi/afd/afd02/selectFD02031_02.do") 
	public void selectFD02031_02(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd02Service.selectFD02031_02(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * CMS입금관리를(을) 엑셀업로드 후 저장한다.
	 */
	@RequestMapping(value="admi/afd/afd02/saveFD02031_01.do")
	public void  saveFD02031_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try{
			uniReq.receivePlatformData(request);
			Map param = uniReq.getVariable();
			DataSet ds = uniReq.getDS("in_resultList");
			
			HashMap map = afd02Service.saveFD02031_01(ds, param);

        	List<Map<String, ?>> list = new ArrayList();
        	list.add(map);

        	uniRes.setDataSet(list, "out_resultList");
        	uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}

    
    /*#########################################################################################################################################*/
    /* 급여공제관리(admi/afd/afd02/afd02032.xfdl)                                                                                                  */
    /*#########################################################################################################################################*/


	/**
	 * 급여공제관리를(을) 조회한다.
	 */
	@RequestMapping(value="admi/afd/afd02/selectFD02032_01.do")
	public void selectFD02032_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd02Service.selectFD02032_01(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 데이터 생성전 기존 데이터 조회한다.
	 */
	@RequestMapping(value="admi/afd/afd02/selectFD02032_02.do")
	public void selectFD02032_02(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd02Service.selectFD02032_02(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
		
	/**
	 * 데이터 생성한다.
	 */
	@RequestMapping(value = "admi/afd/afd02/createFD02032.do")
	public void  createFD02032(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try{
			uniReq.receivePlatformData(request);
			
			DataSet ds = uniReq.getDS("resultList");
	
			afd02Service.createFD02032(ds);
			
			uniRes.sendPlatformData(response);
		
		} catch (Exception e){
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 데이터 저장한다.
	 */
	@RequestMapping(value = "admi/afd/afd02/saveFD02032.do")
	public void  saveFD02032(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try{
			uniReq.receivePlatformData(request);
			
			DataSet ds = uniReq.getDS("resultList");
	
			afd02Service.saveFD02032(ds);
			
			uniRes.sendPlatformData(response);
		
		} catch (Exception e){
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 일괄적용 update
	 */
	@RequestMapping(value = "admi/afd/afd02/updateFD02032.do")
	public void  updateFD02032(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try{
			uniReq.receivePlatformData(request);
			
			DataSet ds = uniReq.getDS("resultList");
	
			afd02Service.updateFD02032(ds);
			
			uniRes.sendPlatformData(response);
		
		} catch (Exception e){
			uniRes.sendException(response, e);
		}
	}

    
    /*#########################################################################################################################################*/
    /* 동문회관리(admi/afd/afd02/afd02033.xfdl)                                                                                                */
    /*#########################################################################################################################################*/


	/**
	 * 동문회관리를(을) 조회한다.
	 */
	@RequestMapping(value="admi/afd/afd02/selectFD02033_01.do")
	public void selectFD02033_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd02Service.selectFD02033_01(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 동문회를(을) 엑셀업로드 후 저장한다.
	 */
	@RequestMapping(value="admi/afd/afd02/saveFD02033_01.do")
	public void  saveFD02033_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try{
			uniReq.receivePlatformData(request);
			Map param = uniReq.getVariable();
			DataSet ds = uniReq.getDS("in_resultList");
			
			HashMap map = afd02Service.saveFD02033_01(ds, param);

        	List<Map<String, ?>> list = new ArrayList();
        	list.add(map);

        	uniRes.setDataSet(list, "out_resultList");
        	uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 변동사항을 조회한다.
	 */
	@RequestMapping(value="admi/afd/afd02/selectChangeData.do")
	public void selectChangeData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd02Service.selectChangeData(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 변동사항 상세내역을 조회한다.
	 */
	@RequestMapping(value="admi/afd/afd02/selectChangeDataDetail.do")
	public void selectChangeDataDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd02Service.selectChangeDataDetail(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 경리팀에 제출할 엑셀을 조회한다.
	 */
	@RequestMapping(value="admi/afd/afd02/selectAccountancy.do")
	public void selectAccountancy(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd02Service.selectAccountancy(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 일반(지정)기탁| 조회한다.
	 */
	@RequestMapping(value="admi/afd/afd02/selectRcpmny01.do")
	public void selectRcpmny01(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd02Service.selectRcpmny01(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 일반기탁|| 조회한다.
	 */
	@RequestMapping(value="admi/afd/afd02/selectRcpmny11.do")
	public void selectRcpmny11(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd02Service.selectRcpmny11(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 지정기탁관리_main 조회
	 */
	@RequestMapping(value="admi/afd/afd02/selectAFD02070_main.do")
	public void selectAFD02070_main(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd02Service.selectAFD02070_main(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 지정기탁관리_detail01 조회
	 */
	@RequestMapping(value="admi/afd/afd02/selectAFD02070_detail01.do")
	public void selectAFD02070_detail01(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd02Service.selectAFD02070_detail01(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 지정기탁관리_detail02 조회
	 */
	@RequestMapping(value="admi/afd/afd02/selectAFD02070_detail02.do")
	public void selectAFD02070_detail02(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd02Service.selectAFD02070_detail02(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	@RequestMapping(value="admi/afd/afd02/saveAFD02070_main.do")
	public void  saveAFD02070_main(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try{
			uniReq.receivePlatformData(request);
			DataSet ds = uniReq.getDS("resultList");
			afd02Service.saveAFD02070_main(ds);
	
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
		
	}
	
	@RequestMapping(value="admi/afd/afd02/saveAFD02070_detail01.do")
	public void  saveAFD02070_detail01(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try{
			uniReq.receivePlatformData(request);
			DataSet ds = uniReq.getDS("resultList");
			afd02Service.saveAFD02070_detail01(ds);
	
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
		
	}
	
	@RequestMapping(value="admi/afd/afd02/saveAFD02070_detail02.do")
	public void  saveAFD02070_detail02(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try{
			uniReq.receivePlatformData(request);
			DataSet ds = uniReq.getDS("resultList");
			afd02Service.saveAFD02070_detail02(ds);
	
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
		
	}
	
	/**
	 * 지정기탁관리_excel 조회
	 */
	@RequestMapping(value="admi/afd/afd02/selectAFD02070_excel.do")
	public void selectAFD02070_excel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd02Service.selectAFD02070_excel(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
	 * 대외협력팀 계좌내역 조회
	 */
	@RequestMapping(value="admi/afd/afd02/selectAFD02080.do")
	public void selectAFD02080(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd02Service.selectAFD02080(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}

}

