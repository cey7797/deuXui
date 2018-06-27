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

import deu.admi.ac.service.Ac01Service;
import deu.comm.unione.uniRequest;
import deu.comm.unione.uniResponse;

import com.nexacro.xapi.data.DataSet;

@Controller
public class Ac01Controller {

	/** ac01Service */
	@Resource(name = "ac01Service")
	private Ac01Service ac01Service; 

    
    /*#########################################################################################################################################*/
    /* 계좌등록(admi/ac/ac01/ac01010.xfdl)                                                                                                       */
    /*#########################################################################################################################################*/

    
    /**
     * 계좌등록을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    @RequestMapping(value = "admi/ac01/selectAc01010MainList.do")
    public void  selectAc01010MainList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac01Service.selectAc01010MainList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    } 
    
    /**
     * 계좌등록을(를) 저장한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    @RequestMapping(value = "admi/ac01/saveAc01010MainList.do")
    public void  saveAc01010MainList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            ac01Service.saveAc01010MainList(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 법인카드 결제계좌 목록을(를) 조회한다.
     */
    @RequestMapping(value = "admi/ac01/selectCardAcntNoCombo.do")
    public void  selectCardAcntNoCombo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac01Service.selectCardAcntNoCombo(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    } 
    
    /**
     * 법인카드 목록을(를) 조회한다.
     */
    @RequestMapping(value = "admi/ac01/selectAcCorpCardList.do")
    public void  selectAcCorpCardList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac01Service.selectAcCorpCardList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /**
     * 법인카드 목록을(를) 추가, 저장, 삭제한다.
     */
    @RequestMapping(value = "admi/ac01/saveAcCorpCardList.do")
    public void  saveAcCorpCardList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);
            
            DataSet ds = uniReq.getDS("resultList");
    
            ac01Service.saveAcCorpCardList(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 법인카드 상세목록을(를) 조회한다.
     */
    @RequestMapping(value = "admi/ac01/selectAcCorpCardDetList.do")
    public void  selectAcCorpCardDetList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac01Service.selectAcCorpCardDetList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /**
     * 법인카드 상세목록을(를) 추가, 저장, 삭제한다.
     */
    @RequestMapping(value = "admi/ac01/saveAcCorpCardDetList.do")
    public void  saveAcCorpCardDetList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);
            
            DataSet ds = uniReq.getDS("resultList");
    
            ac01Service.saveAcCorpCardDetList(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 법인카드개인입금 목록을(를) 조회한다.
     */
    @RequestMapping(value = "admi/ac01/selectAcCorpCardUseList.do")
    public void  selectAcCorpCardUseList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac01Service.selectAcCorpCardUseList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /**
     * 법인카드개인입금 목록을(를) 추가, 저장, 삭제한다.
     */
    @RequestMapping(value = "admi/ac01/saveAcCorpCardUseList.do")
    public void  saveAcCorpCardUseList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);
            
            DataSet ds = uniReq.getDS("resultList");
    
            ac01Service.saveAcCorpCardUseList(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    @RequestMapping(value = "admi/ac01/deleteAcCorpCardUseList.do")
    public void  deleteAcCorpCardUseList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);
            
            DataSet ds = uniReq.getDS("resultList");
    
            ac01Service.deleteAcCorpCardUseList(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 미정산 입금내역 CNT을(를) 조회한다.
     */
    @RequestMapping(value = "admi/ac01/selectNoIncAcntNoCnt.do")
    public void  selectIncAcntNoCnt(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac01Service.selectNoIncAcntNoCnt(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /**
     * 미정산 입금내역 목록을(를) 조회한다.
     */
    @RequestMapping(value = "admi/ac01/selectNoIncAcntNoList.do")
    public void  selectNoIncAcntNoList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac01Service.selectNoIncAcntNoList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /**
     * 미정산 카드내역 목록을(를) 조회한다.
     */
    @RequestMapping(value = "admi/ac01/selectNoBillCardList.do")
    public void  selectNoBillCardList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac01Service.selectNoBillCardList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /**
     * 입금내역 목록을(를) 조회한다.
     */
    @RequestMapping(value = "admi/ac01/selectIncAcntNoList.do")
    public void  selectIncAcntNoList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac01Service.selectIncAcntNoList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /**
     * 입금내역 목록을(를) 저장한다.
     */
    @RequestMapping(value = "admi/ac01/saveIncAcntNoList.do")
    public void  saveIncAcntNoList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);
            
            DataSet ds = uniReq.getDS("resultList");
    
            ac01Service.saveIncAcntNoList(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    @RequestMapping(value = "admi/ac01/selectAc01100MainList.do")
    public void  selectAc01100MainList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = ac01Service.selectAc01100MainList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    } 
    
    @RequestMapping(value = "admi/ac01/saveAc01100MainList.do")
    public void  saveAc01100MainList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            ac01Service.saveAc01100MainList(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
}
