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

import deu.admi.ac.service.Ac04Service;
import deu.comm.unione.uniRequest;
import deu.comm.unione.uniResponse;

import com.nexacro.xapi.data.DataSet;

@Controller
public class Ac04Controller {

	/** ac04Service */
	@Resource(name = "ac04Service")
	private Ac04Service ac04Service; 
	
	
    /*#########################################################################################################################################*/
    /* 계산서등록(admi/ac/ac04/ac04010.xfdl)                                                                                                   */
    /*#########################################################################################################################################*/

    /**
     * 사업자번호을(를) 조회한다.
     */
    @RequestMapping(value = "admi/ac04/selectAcBizNo.do")
    public void  selectAcBizNo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac04Service.selectAcBizNo(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    } 
	
	
    /**
     * 계산서등록을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    @RequestMapping(value = "admi/ac04/selectAc04010MainList.do")
    public void  selectAc04010MainList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac04Service.selectAc04010MainList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    } 
    
    @RequestMapping(value = "admi/ac04/saveAc04010MainList.do")
    public void  saveAc04010MainList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            ac04Service.saveAc04010MainList(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }

    /*#########################################################################################################################################*/
    /* 계산서합계표현황(admi/ac/ac04/ac04020.xfdl)                                                                                             */
    /*#########################################################################################################################################*/


    /**
     * 계산서합계표현황을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    @RequestMapping(value = "admi/ac04/selectAc04020MainList.do")
    public void  selectAc04020MainList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac04Service.selectAc04020MainList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    } 
    
    @RequestMapping(value = "admi/ac04/selectAc04020SubList.do")
    public void  selectAc04020SubList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac04Service.selectAc04020SubList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    } 
    
    @RequestMapping(value = "admi/ac04/saveAc04020MainList.do")
    public void  saveAc04020MainList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            Map map = uniReq.getVariable();
            
            ac04Service.saveAc04020MainList(map);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    

    /*#########################################################################################################################################*/
    /* 소득자료등록(admi/ac/ac04/ac04040.xfdl)                                                                                                 */
    /*#########################################################################################################################################*/


    /**
     * 소득자료등록을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    @RequestMapping(value = "admi/ac04/selectAc04040MainList.do")
    public void  selectAc04040MainList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac04Service.selectAc04040MainList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    } 
    
    @RequestMapping(value = "admi/ac04/saveAc04040MainList.do")
    public void  saveAc04040MainList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            ac04Service.saveAc04040MainList(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /*#########################################################################################################################################*/
    /* 소득신고파일생성및신고완료처리(admi/ac/ac04/ac04050.xfdl)                                                                               */
    /*#########################################################################################################################################*/


    /**
     * 소득신고파일생성 및 신고완료처리을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    @RequestMapping(value = "admi/ac04/selectAc04050MainList.do")
    public void  selectAc04050MainList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac04Service.selectAc04050MainList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    } 
    
    
    /*#########################################################################################################################################*/
    /* 원천징수영수증(admi/ac/ac04/ac04060.xfdl)                                                                                               */
    /*#########################################################################################################################################*/


    /**
     * 원천징수영수증을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    @RequestMapping(value = "admi/ac04/selectAc04060MainList.do")
    public void  selectAc04060MainList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac04Service.selectAc04060MainList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    } 
    
    @RequestMapping(value = "admi/ac04/selectAc04060SubList.do")
    public void  selectAc04060SubList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac04Service.selectAc04060SubList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    } 
}
