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

import deu.admi.ac.service.Ac03Service;
import deu.comm.unione.uniRequest;
import deu.comm.unione.uniResponse;

import com.nexacro.xapi.data.DataSet;

@Controller
public class Ac03Controller {

	/** ac03Service */
	@Resource(name = "ac03Service")
	private Ac03Service ac03Service; 
	
	
	/*#########################################################################################################################################*/
    /* 일계표(admi/ac/ac03/ac03010.xfdl)                                                                                                       */
    /*#########################################################################################################################################*/

    
    /**
     * 일계표을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    @RequestMapping(value = "admi/ac03/selectAc03010MainList.do")
    public void  selectAc03010MainList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac03Service.selectAc03010MainList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    } 
    

    /*#########################################################################################################################################*/
    /* 월계표(admi/ac/ac03/ac03020.xfdl)                                                                                                       */
    /*#########################################################################################################################################*/


    /**
     * 월계표을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    @RequestMapping(value = "admi/ac03/selectAc03020MainList.do")
    public void  selectAc03020MainList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac03Service.selectAc03020MainList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    } 
    

    /*#########################################################################################################################################*/
    /* 현금출납부(admi/ac/ac03/ac03030.xfdl)                                                                                                   */
    /*#########################################################################################################################################*/


    /**
     * 현금출납부을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    @RequestMapping(value = "admi/ac03/selectAc03030MainList.do")
    public void  selectAc03030MainList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac03Service.selectAc03030MainList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    } 
    

    /*#########################################################################################################################################*/
    /* 일일수지현황(admi/ac/ac03/ac03040.xfdl)                                                                                                   */
    /*#########################################################################################################################################*/


    /**
     * 총계정원장을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    @RequestMapping(value = "admi/ac03/selectAc03040MainList.do")
    public void  selectAc03040MainList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac03Service.selectAc03040MainList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    } 
    

    /*#########################################################################################################################################*/
    /* 예산대비집행현황(admi/ac/ac03/ac03050.xfdl)                                                                                             */
    /*#########################################################################################################################################*/


    /**
     * 예산대비집행현황을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    @RequestMapping(value = "admi/ac03/selectAc03050MainList.do")
    public void  selectAc03050MainList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac03Service.selectAc03050MainList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    } 
    

    /*#########################################################################################################################################*/
    /* 계좌현황(admi/ac/ac03/ac03060.xfdl)                                                                                                     */
    /*#########################################################################################################################################*/


    /**
     * 계좌현황을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    @RequestMapping(value = "admi/ac03/selectAc03060MainList.do")
    public void  selectAc03060MainList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac03Service.selectAc03060MainList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    } 

    
    /*#########################################################################################################################################*/
    /* 회계마감등록(admi/ac/ac03/ac03070.xfdl)                                                                                                 */
    /*#########################################################################################################################################*/


    /**
     * 회계마감등록을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    @RequestMapping(value = "admi/ac03/selectAc03070MainList.do")
    public void  selectAc03070MainList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac03Service.selectAc03070MainList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    } 

    
    /*#########################################################################################################################################*/
    /* 결산재무제표통계(admi/ac/ac03/ac03080.xfdl)                                                                                             */
    /*#########################################################################################################################################*/


    /**
     * 결산재무제표통계을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    @RequestMapping(value = "admi/ac03/selectAc03080_10List.do")
    public void  selectAc03080_10List(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac03Service.selectAc03080_10List(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    } 

    @RequestMapping(value = "admi/ac03/selectAc03080_20List.do")
    public void  selectAc03080_20List(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac03Service.selectAc03080_20List(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    } 
    
    @RequestMapping(value = "admi/ac03/selectAc03080_30List.do")
    public void  selectAc03080_30List(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac03Service.selectAc03080_30List(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    } 
    
    /*#########################################################################################################################################*/
    /* 결산부속명세서통계(admi/ac/ac03/ac03090.xfdl)                                                                                           */
    /*#########################################################################################################################################*/


    /**
     * 결산부속명세서통계을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    @RequestMapping(value = "admi/ac03/selectAc03090MainList.do")
    public void  selectAc03090MainList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac03Service.selectAc03090MainList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    } 
    
    /*#########################################################################################################################################*/
    /* 현금및예금명세(admi/ac/ac03/ac03100.xfdl)                                                                                            */
    /*#########################################################################################################################################*/

    /**
     * 결산부속명세서통계을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    @RequestMapping(value = "admi/ac03/selectAc03100MainList.do")
    public void  selectAc03100MainList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac03Service.selectAc03100MainList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    } 
    
    /*#########################################################################################################################################*/
    /* 자금원장(admi/ac/ac03/ac03110.xfdl)                                                                                            */
    /*#########################################################################################################################################*/

    /**
     * 자금원장을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    @RequestMapping(value = "admi/ac03/selectAc03110MainList.do")
    public void  selectAc03110MainList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac03Service.selectAc03110MainList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /*#########################################################################################################################################*/
    /* 운영원장(admi/ac/ac03/ac03120.xfdl)                                                                                            */
    /*#########################################################################################################################################*/

    /**
     * 운영원장을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    @RequestMapping(value = "admi/ac03/selectAc03120MainList.do")
    public void  selectAc03120MainList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac03Service.selectAc03120MainList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    @RequestMapping(value = "admi/ac03/selectAc03120MainListMok.do")
    public void  selectAc03120MainListMok(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac03Service.selectAc03120MainListMok(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    @RequestMapping(value = "admi/ac03/selectAc03120MainListAfd.do")
    public void  selectAc03120MainListAfd(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac03Service.selectAc03120MainListAfd(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /*#########################################################################################################################################*/
    /* 미결원장(admi/ac/ac03/ac03130.xfdl)                                                                                            */
    /*#########################################################################################################################################*/

    /**
     * 미결원장을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    @RequestMapping(value = "admi/ac03/selectAc03130MainList.do")
    public void  selectAc03130MainList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac03Service.selectAc03130MainList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
}
