package deu.admi.ac.web;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import deu.admi.ac.service.Ac02Service;
import deu.comm.unione.uniRequest;
import deu.comm.unione.uniResponse;

import com.nexacro.xapi.data.DataSet;

@Controller
public class Ac02Controller {

	/** ac02Service */
	@Resource(name = "ac02Service")
	private Ac02Service ac02Service; 
	
	
	
	
	
	
	/*#########################################################################################################################################*/
    /* 결의서등록(admi/ac/ac02/ac02010.xfdl)                                                                                                      */
    /*#########################################################################################################################################*/

	/**
	 * 결의서 조회 
	 */
    @RequestMapping(value = "admi/ac02/selectAc02010_01.do")
    public void  selectAc02010_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02010_01(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    @RequestMapping(value = "admi/ac02/saveAc02010_01_Search_Approval.do")
    public void  saveAc02010_01_Search_Approval(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
    
            HashMap nMap = ac02Service.saveAc02010_01_Search_Approval(ds);

            uniRes.setVariables(nMap);
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    @RequestMapping(value = "admi/ac02/selectAc02010_excelMember.do")
    public void  selectAc02010_excelMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02010_excelMember(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /*#########################################################################################################################################*/
    /* 결의서등록 (admi/ac/ac02/ac02010.xfdl)  전자결재                                                                                              */
    /*#########################################################################################################################################*/
    /** 전자결재 공문양식 결의서 결의종류가 자금/결산 대체 제외한 나머지일때 **/
    @RequestMapping(value = "admi/ac02/saveAc02010_01_Approval.do")
    public void saveAc02010_01_Approval(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");

        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            HashMap nMap = ac02Service.saveAc02010_01_Approval(ds);
            System.out.println("=============================================");
            System.out.println(nMap);
            System.out.println("=============================================");
            HashMap<String, Object> tMap = new HashMap<String, Object>();
            
            tMap.put("id", nMap.get("id"));
            tMap.put("formId", nMap.get("formId"));
            tMap.put("title", nMap.get("title"));
            tMap.put("subjId", nMap.get("subjId"));
            tMap.put("subTitle", nMap.get("subTitle"));
            tMap.put("body", nMap.get("body"));
            tMap.put("approvalDiv", nMap.get("approvalDiv"));
            tMap.put("approvalDt", nMap.get("approvalDt"));
            tMap.put("approvalDocid", nMap.get("approvalDocid"));
            tMap.put("userId", nMap.get("userId"));
            tMap.put("updDt", nMap.get("updDt"));
            tMap.put("OUT_CNT", nMap.get("OUT_CNT"));
            tMap.put("OUT_MSG", nMap.get("OUT_MSG"));

            uniRes.setDataSet(tMap, "ds_elec");
            
            uniRes.sendPlatformData(response);
            
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }

    }
    
    @RequestMapping(value = "admi/ac02/chkAc02010_01_Approval.do")
    public void chkAc02010_01_Approval(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");

        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            HashMap nMap = ac02Service.chkAc02010_01_Approval(ds);
            HashMap<String, Object> tMap = new HashMap<String, Object>();
            tMap.put("OUT_CNT", nMap.get("OUT_CNT"));
            tMap.put("OUT_MSG", nMap.get("OUT_MSG"));

            uniRes.setDataSet(tMap, "ds_elec");
            
            uniRes.sendPlatformData(response);
            
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }

    }
    
    /** 전자결재 공문양식 결의서 결의종류가 자금대체 일때 **/
    @RequestMapping(value = "admi/ac02/saveAc02010_01_Approval_vouKnd32.do")
    public void saveAc02010_01_Approval_vouKnd32(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");

        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            HashMap nMap = ac02Service.saveAc02010_01_Approval_vouKnd32(ds);
            System.out.println("=============================================");
            System.out.println(nMap);
            System.out.println("=============================================");
            HashMap<String, Object> tMap = new HashMap<String, Object>();
            
            tMap.put("id", nMap.get("id"));
            tMap.put("formId", nMap.get("formId"));
            tMap.put("title", nMap.get("title"));
            tMap.put("subjId", nMap.get("subjId"));
            tMap.put("subTitle", nMap.get("subTitle"));
            tMap.put("body", nMap.get("body"));
            tMap.put("approvalDiv", nMap.get("approvalDiv"));
            tMap.put("approvalDt", nMap.get("approvalDt"));
            tMap.put("approvalDocid", nMap.get("approvalDocid"));
            tMap.put("userId", nMap.get("userId"));
            tMap.put("updDt", nMap.get("updDt"));
            tMap.put("OUT_CNT", nMap.get("OUT_CNT"));
            tMap.put("OUT_MSG", nMap.get("OUT_MSG"));

            uniRes.setDataSet(tMap, "ds_elec");
            
            uniRes.sendPlatformData(response);
            
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }

    }
    
    /** 전자결재 공문양식 결의서 결의종류가 결산대체 일때 **/
    @RequestMapping(value = "admi/ac02/saveAc02010_01_Approval_vouKnd33.do")
    public void saveAc02010_01_Approval_vouKnd33(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");

        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            HashMap nMap = ac02Service.saveAc02010_01_Approval_vouKnd33(ds);
            System.out.println("=============================================");
            System.out.println(nMap);
            System.out.println("=============================================");
            HashMap<String, Object> tMap = new HashMap<String, Object>();
            
            tMap.put("id", nMap.get("id"));
            tMap.put("formId", nMap.get("formId"));
            tMap.put("title", nMap.get("title"));
            tMap.put("subjId", nMap.get("subjId"));
            tMap.put("subTitle", nMap.get("subTitle"));
            tMap.put("body", nMap.get("body"));
            tMap.put("approvalDiv", nMap.get("approvalDiv"));
            tMap.put("approvalDt", nMap.get("approvalDt"));
            tMap.put("approvalDocid", nMap.get("approvalDocid"));
            tMap.put("userId", nMap.get("userId"));
            tMap.put("updDt", nMap.get("updDt"));
            tMap.put("OUT_CNT", nMap.get("OUT_CNT"));
            tMap.put("OUT_MSG", nMap.get("OUT_MSG"));

            uniRes.setDataSet(tMap, "ds_elec");
            
            uniRes.sendPlatformData(response);
            
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }

    }
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 수입/여입결의서등록(admi/ac/ac02/ac02010_p01.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/selectAc02010p01_01.do")
    public void  selectAc02010p01_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02010p01_01(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/selectAc02010p01_01_link.do")
    public void  selectAc02010p01_01_link(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02010p01_01_link(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/selectAc02010p01_02.do")
    public void  selectAc02010p01_02(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02010p01_02(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/selectAc02010p01_03.do")
    public void  selectAc02010p01_03(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02010p01_03(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/selectAc02010p01_04.do")
    public void  selectAc02010p01_04(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02010p01_04(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    
    
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/saveAc02010p01_01.do")
    public void  saveAc02010p01_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            ac02Service.saveAc02010p01_01(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 수입/여입결의서등록(admi/ac/ac02/ac02010_p01.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 * 
	 */
    
    
    @RequestMapping(value = "admi/ac02/saveVouNoSp.do")
    public void  saveVouNoSp(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        
        try {
        	uniReq.receivePlatformData(request);

        	DataSet ds = uniReq.getDS("resultList");
        	
        	HashMap map = ac02Service.saveVouNoSp(ds);
        	
        	List<Map<String, ?>> list = new ArrayList();
        	list.add(map);

        	uniRes.setDataSet(list, "resultList");
        	uniRes.sendPlatformData(response);
			
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
        
    }
    
    
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 수입/여입 TAB 계정내역 (admi/ac/ac02/ac02010_p01_t01.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/selectAc02010p01_t01_01.do")
    public void  selectAc02010p01_t01_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02010p01_t01_01(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/selectAc02010p01_t01_cnt.do")
    public void  selectAc02010p01_t01_cnt(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02010p01_t01_cnt(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
        
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/saveAc02010p01_t01_01.do")
    public void  saveAc02010p01_t01_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            ac02Service.saveAc02010p01_t01_01(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    @RequestMapping(value = "admi/ac02/saveAc02020p01_t01_01.do")
    public void  saveAc02020p01_t01_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            ac02Service.saveAc02020p01_t01_01(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 수입/여입 TAB 증빙내역 (admi/ac/ac02/ac02010_p01_t01.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/selectAc02010p01_t01_02.do")
    public void  selectAc02010p01_t01_02(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02010p01_t01_02(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
        
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/saveAc02010p01_t01_02.do")
    public void  saveAc02010p01_t01_02(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            ac02Service.saveAc02010p01_t01_02(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    @RequestMapping(value = "admi/ac02/saveAc02010p01_t01_02_purc.do")
    public void  saveAc02010p01_t01_02_purc(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            ac02Service.saveAc02010p01_t01_02_purc(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/saveAc02010p01_t01_02_file.do")
    public void  saveAc02010p01_t01_02_file(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            ac02Service.saveAc02010p01_t01_02_file(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/saveAc02010p01_t02_02_file.do")
    public void  saveAc02010p01_t02_02_file(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            ac02Service.saveAc02010p01_t02_02_file(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 수입/여입 TAB 원천세 (admi/ac/ac02/ac02010_p01_t01.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/selectAc02010p01_t01_03.do")
    public void  selectAc02010p01_t01_03(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02010p01_t01_03(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
        
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/saveAc02010p01_t01_03.do")
    public void  saveAc02010p01_t01_03(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            ac02Service.saveAc02010p01_t01_03(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/saveAc02010p01_t01_03_purc.do")
    public void  saveAc02010p01_t01_03_purc(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            ac02Service.saveAc02010p01_t01_03_purc(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 수입/여입 TAB 계좌내역(입금) (admi/ac/ac02/ac02010_p01_t01.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/selectAc02010p01_t01_04.do")
    public void  selectAc02010p01_t01_04(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02010p01_t01_04(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
        
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/saveAc02010p01_t01_04.do")
    public void  saveAc02010p01_t01_04(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            ac02Service.saveAc02010p01_t01_04(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 수입/여입 TAB 계좌내역(지금) (admi/ac/ac02/ac02010_p01_t01.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/selectAc02010p01_t01_05.do")
    public void  selectAc02010p01_t01_05(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02010p01_t01_05(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
        
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/saveAc02010p01_t01_05.do")
    public void  saveAc02010p01_t01_05(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            ac02Service.saveAc02010p01_t01_05(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 진행상태 처리 SP                                                                                           */
    /*#########################################################################################################################################*/
    /**
	 * 
	 */
    
    
    @RequestMapping(value = "admi/ac02/P_AC_STA_CD_PROC.do")
    public void  P_AC_STA_CD_PROC(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        
        try {
        	uniReq.receivePlatformData(request);

        	DataSet ds = uniReq.getDS("resultList");
        	
        	HashMap map = ac02Service.P_AC_STA_CD_PROC(ds);
        	
        	List<Map<String, ?>> list = new ArrayList();
        	list.add(map);
        	System.out.println(map);
        	uniRes.setDataSet(list, "resultList");
        	uniRes.sendPlatformData(response);
			
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
        
    }
    
    @RequestMapping(value = "admi/ac02/P_AC_STA_CD_PROC_PUCH.do")
    public void  P_AC_STA_CD_PROC_PUCH(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        
        try {
        	uniReq.receivePlatformData(request);

        	DataSet ds = uniReq.getDS("resultList");
        	
        	HashMap map = ac02Service.P_AC_STA_CD_PROC_PUCH(ds);
        	
        	List<Map<String, ?>> list = new ArrayList();
        	list.add(map);
        	System.out.println(map);
        	uniRes.setDataSet(list, "resultList");
        	uniRes.sendPlatformData(response);
			
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
        
    }
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 결의서접수 (admi/ac/ac02/ac02020.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/selectAc02020_01.do")
    public void  selectAc02020_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        
        List<?> list = ac02Service.selectAc02020_01(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    @RequestMapping(value = "admi/ac02/saveAc02020_purc.do")
    public void  saveAc02020_purc(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            ac02Service.saveAc02020_purc(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    @RequestMapping(value = "admi/ac02/saveAc02020_recv_chg.do")
    public void  saveAc02020_recv_chg(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            ac02Service.saveAc02020_recv_chg(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    @RequestMapping(value = "admi/ac02/selectAc02020_purc_p01_1.do")
    public void  selectAc02020_purc_p01_1(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02020_purc_p01_1(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    @RequestMapping(value = "admi/ac02/SP_SLIP_CREATE.do")
    public void  SP_SLIP_CREATE(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        
        try {
        	uniReq.receivePlatformData(request);

        	DataSet ds = uniReq.getDS("resultList");
        	
        	HashMap map = ac02Service.SP_SLIP_CREATE(ds);
        	
        	List<Map<String, ?>> list = new ArrayList();
        	list.add(map);
        	System.out.println(map);
        	uniRes.setDataSet(list, "resultList");
        	uniRes.sendPlatformData(response);
			
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
        
    }
    
    @RequestMapping(value = "admi/ac02/saveAc02020_purc_p01_1.do")
    public void  saveAc02020_purc_p01_1(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);
            
            DataSet ds = uniReq.getDS("resultList");
    
            ac02Service.saveAc02020_purc_p01_1(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    @RequestMapping(value = "admi/ac02/selectAc02020_purc_p01_2.do")
    public void  selectAc02020_purc_p01_2(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02020_purc_p01_2(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    @RequestMapping(value = "admi/ac02/selectAc02020_purc_p01_3.do")
    public void  selectAc02020_purc_p01_3(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02020_purc_p01_3(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    @RequestMapping(value = "admi/ac02/saveAc02020_01_Search_Approval.do")
    public void  saveAc02020_01_Search_Approval(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
    
            HashMap nMap = ac02Service.saveAc02020_01_Search_Approval(ds);

            uniRes.setVariables(nMap);
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/selectAc02020_t01_01.do")
    public void  selectAc02020_t01_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02020_t01_01(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/selectAc02020_t02_01.do")
    public void  selectAc02020_t02_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02020_t02_01(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/selectAc02020_t03_01.do")
    public void  selectAc02020_t03_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02020_t03_01(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/selectAc02020_t04_01.do")
    public void  selectAc02020_t04_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02020_t04_01(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/selectAc02020_t04_02.do")
    public void  selectAc02020_t04_02(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02020_t04_02(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    @RequestMapping(value = "admi/ac02/saveAc02020_t04_02.do")
    public void  saveAc02020_t04_02(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);
            
            DataSet ds = uniReq.getDS("resultList");
    
            ac02Service.saveAc02020_t04_02(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /*#########################################################################################################################################*/
    /* 결의서접수 (admi/ac/ac02/ac02010.xfdl)  전자결재                                                                                              */
    /*#########################################################################################################################################*/
    /** 전자결재 결의서접수 경리팀 **/
    @RequestMapping(value = "admi/ac02/saveAc02020_01_Approval.do")
    public void saveAc02020_01_Approval(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");

        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
    
            HashMap nMap = ac02Service.saveAc02020_01_Approval(ds);
            
            HashMap<String, Object> tMap = new HashMap<String, Object>();
            
            tMap.put("id", nMap.get("id"));
            tMap.put("formId", nMap.get("formId"));
            tMap.put("title", nMap.get("title"));
            tMap.put("subjId", nMap.get("subjId"));
            tMap.put("subTitle", nMap.get("subTitle"));
            tMap.put("body", nMap.get("body"));
            tMap.put("approvalDiv", nMap.get("approvalDiv"));
            tMap.put("approvalDt", nMap.get("approvalDt"));
            tMap.put("approvalDocid", nMap.get("approvalDocid"));
            tMap.put("userId", nMap.get("userId"));
            tMap.put("updDt", nMap.get("updDt"));
            tMap.put("cdChk", nMap.get("cdChk"));
            tMap.put("chkMsg", nMap.get("chkMsg"));

            uniRes.setDataSet(tMap, "ds_elec");
            
            uniRes.sendPlatformData(response);
            
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }

    }
    
    /** 전자결재 결의서접수 구매팀 **/
    @RequestMapping(value = "admi/ac02/saveAc02020_01_Approval_puch.do")
    public void saveAc02020_01_Approval_puch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");

        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
    
            HashMap nMap = ac02Service.saveAc02020_01_Approval_puch(ds);
            
            HashMap<String, Object> tMap = new HashMap<String, Object>();
            
            tMap.put("id", nMap.get("id"));
            tMap.put("formId", nMap.get("formId"));
            tMap.put("title", nMap.get("title"));
            tMap.put("subjId", nMap.get("subjId"));
            tMap.put("subTitle", nMap.get("subTitle"));
            tMap.put("body", nMap.get("body"));
            tMap.put("approvalDiv", nMap.get("approvalDiv"));
            tMap.put("approvalDt", nMap.get("approvalDt"));
            tMap.put("approvalDocid", nMap.get("approvalDocid"));
            tMap.put("userId", nMap.get("userId"));
            tMap.put("updDt", nMap.get("updDt"));
            tMap.put("cdChk", nMap.get("cdChk"));
            tMap.put("chkMsg", nMap.get("chkMsg"));

            uniRes.setDataSet(tMap, "ds_elec");
            
            uniRes.sendPlatformData(response);
            
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }

    }
    
    /** 전자결재 결의서접수 경리팀 **/
    @RequestMapping(value = "admi/ac02/updateAc02020_01_VendorDesc.do")
    public void updateAc02020_01_VendorDesc(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);
            
            DataSet ds = uniReq.getDS("resultList");
    
            ac02Service.updateAc02020_01_VendorDesc(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }

    }
    
    /*#########################################################################################################################################*/
    /* 전표확정등록 (admi/ac/ac02/ac02030.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/selectAc02030_01.do")
    public void  selectAc02030_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02030_01(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/selectAc02030_t01_01.do")
    public void  selectAc02030_t01_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        
        try 
        {
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = ac02Service.selectAc02030_t01_01(map);
	        List<?> list1 = ac02Service.selectAc02030_t01_02(map);
	        uniRes.setDataSet(list, "resultList");
	        uniRes.setDataSet(list1, "resultList1");
	        uniRes.sendPlatformData(response);
        } 
        catch (Exception e) 
        {
        	uniRes.sendPlatformData(response);
        }
    }
    
    /*
     * 분개내역(차변) 저장
     */
    @RequestMapping(value = "admi/saveAc02030_t01_01.do")
    public void  saveAc02030_t01_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);
            
            DataSet ds = uniReq.getDS("resultList");
    
            ac02Service.saveAc02030_t01_01(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    
    
    
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/selectAc02030_t01_02.do")
    public void  selectAc02030_t01_02(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02030_t01_02(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /*
     * 분개내역(대변) 저장
     */
    @RequestMapping(value = "admi/saveAc02030_t01_02.do")
    public void  saveAc02030_t01_02(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);
            
            DataSet ds = uniReq.getDS("resultList");
    
            ac02Service.saveAc02030_t01_02(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/selectAc02030_t02_01.do")
    public void  selectAc02030_t02_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02030_t02_01(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/selectAc02030_t03_01.do")
    public void  selectAc02030_t03_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02030_t03_01(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/selectAc02030_t04_01.do")
    public void  selectAc02030_t04_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02030_t04_01(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    
    
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/selectAc02030_t04_02.do")
    public void  selectAc02030_t04_02(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02030_t04_02(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 계정대체 계정내역 (admi/ac/ac02/ac02010_p01_t03.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/selectAc02010p01_t03_01.do")
    public void  selectAc02010p01_t03_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02010p01_t03_01(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    
    
    
    /*#########################################################################################################################################*/
    /* 자금대체 계정내역 (admi/ac/ac02/ac02010_p01_t04.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/selectAc02010p01_t04_01.do")
    public void  selectAc02010p01_t04_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02010p01_t04_01(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/saveAc02010p01_t04_01.do")
    public void  saveAc02010p01_t04_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            ac02Service.saveAc02010p01_t04_01(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 결산대체 계정내역 (admi/ac/ac02/ac02010_p01_t05.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/selectAc02010p01_t05_01.do")
    public void  selectAc02010p01_t05_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02010p01_t05_01(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/saveAc02010p01_t05_01.do")
    public void  saveAc02010p01_t05_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            ac02Service.saveAc02010p01_t05_01(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }

    
    /**
     * 전표접수자 변경
     */
    @RequestMapping(value = "admi/ac02/saveSlipAccEmpId.do")
    public void  saveSlipAccEmpId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            ac02Service.saveSlipAccEmpId(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }

    
    
    
    
    
    
    
	/*#########################################################################################################################################*/
    /* 은행이관(admi/ac/ac02/ac02040.xfdl)                                                                                                      */
    /*#########################################################################################################################################*/
    /**
	 * 은행목록 조회
	 */
    @RequestMapping(value = "admi/ac02/selectAc02040_01.do")
    public void  selectAc02040_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02040_01(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /**
	 * 지급목록 조회
	 */
    @RequestMapping(value = "admi/ac02/selectAc02040_02.do")
    public void  selectAc02040_02(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02040_02(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }

    
    /**
	 * 이관/이관취소 처리 ( mssql생성전용 )
	 */
    @RequestMapping(value = "admi/ac02/P_AC_BANK_PROC.do")
    public void  P_AC_BANK_PROC(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        
        try {
        	uniReq.receivePlatformData(request);
            Map param = uniReq.getVariable();
        	DataSet ds = uniReq.getDS("resultList");
        	
        	HashMap map = ac02Service.P_AC_BANK_PROC(ds, param);
        	
        	List<Map<String, ?>> list = new ArrayList();
        	list.add(map);

        	uniRes.setDataSet(list, "resultList");
        	uniRes.sendPlatformData(response);
			
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
        
    }

    
    /**
	 * 이관/이관취소 후 처리 ( oracle생성전용 )
	 */
    @RequestMapping(value = "admi/ac02/P_AC_BANK_PROC_POST.do")
    public void  P_AC_BANK_PROC_POST(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        
        try {
        	uniReq.receivePlatformData(request);
            Map param = uniReq.getVariable();
        	DataSet ds = uniReq.getDS("resultList");
        	
        	HashMap map = ac02Service.P_AC_BANK_PROC_POST(ds, param);
        	
        	List<Map<String, ?>> list = new ArrayList();
        	list.add(map);

        	uniRes.setDataSet(list, "resultList");
        	uniRes.sendPlatformData(response);
			
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
        
    }
    
    
    
    
    
    
    
    
    
    
    
        
    
    
    
    
    /*#########################################################################################################################################*/
    /* 대체결의서접수 (admi/ac/ac02/ac02050.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 * 
	 */
    @RequestMapping(value = "admi/ac02/selectAc02050_01.do")
    public void  selectAc02050_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02050_01(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /**
	 * 계정대체 상세
	 */
    @RequestMapping(value = "admi/ac02/selectAc02050_02_01.do")
    public void  selectAc02050_02_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02050_02_01(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /**
	 * 자금대체 상세
	 */
    @RequestMapping(value = "admi/ac02/selectAc02050_02_02.do")
    public void  selectAc02050_02_02(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02050_02_02(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /**
	 * 결산대체 상세
	 */
    @RequestMapping(value = "admi/ac02/selectAc02050_02_03.do")
    public void  selectAc02050_02_03(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02050_02_03(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    @RequestMapping(value = "admi/ac02/selectAc02060.do")
    public void  selectAc02060(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac02Service.selectAc02060(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    @RequestMapping(value = "admi/ac02/saveAc02060.do")
    public void  saveAc02060(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            ac02Service.saveAc02060(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
	@RequestMapping(value="admi/ac02/saveCfmAc02020.do")
	public void  saveCfmAc02020(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try{
			uniReq.receivePlatformData(request);
			DataSet dsRcpter = uniReq.getDS("resultList");
			HashMap nMap = ac02Service.saveCfmAc02020(dsRcpter);
			
			List<Map<String, ?>> list = new ArrayList();
        	list.add(nMap);

        	uniRes.setDataSet(list, "resultList");
        	
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
}
