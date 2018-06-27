package deu.admi.adcom.web;

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

import deu.admi.adcom.service.AdcomService;
import deu.comm.unione.uniRequest;
import deu.comm.unione.uniResponse;

import com.nexacro.xapi.data.DataSet;

@Controller
public class AdcomController {

	/** adcomService */
	@Resource(name = "adcomService")
	private AdcomService adcomService; 


    /*#########################################################################################################################################*/
    /* 권한조회팝업(admi01010_p01.xfdl)                                                                                                             */
    /*#########################################################################################################################################*/

	
    /**
     * 권한조회 팝업을(를) 조회한다.
     * @param roleNm 권한ID/명
     */
    @RequestMapping(value = "admi/adcom/selectComRolePopList.do")
    public void  selectComRolePopList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = adcomService.selectComRolePopList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }


    /*#########################################################################################################################################*/
    /* 계정코드조회팝업(admi01010_p02.xfdl)                                                                                                         */
    /*#########################################################################################################################################*/

	
    /**
     * 계정코드조회 팝업을(를) 조회한다.
     * @param acntUnt 회계단위
     * @param acntYy 회계년도
     * @param bizNm 사업명
     * @param bizLvlCd 사업레벨
     * @param uprBizCd 상위사업레벨
     * @param befBizCd 이전사업레벨
     */
    @RequestMapping(value = "admi/adcom/selectBgAcntCdPopList.do")
    public void  selectBgAcntCdPopList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = adcomService.selectBgAcntCdPopList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }


    /*#########################################################################################################################################*/
    /* 사업조회팝업(admi01010_p03.xfdl)                                                                                                             */
    /*#########################################################################################################################################*/

	
    /**
     * 사업팝업을(를) 조회한다.
     * @param acntUnt 회계단위
     * @param acntYy 회계년도
     * @param bizNm 사업명
     * @param bizLvlCd 사업레벨
     * @param uprBizCd 상위사업레벨
     * @param befBizCd 이전사업레벨
     */
    @RequestMapping(value = "admi/adcom/selectBgBizPopList.do")
    public void  selectBgBizPopList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = adcomService.selectBgBizPopList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }	


    /*#########################################################################################################################################*/
    /* 재원조회팝업(admi01010_p04.xfdl)                                                                                                             */
    /*#########################################################################################################################################*/

	
    /**
     * 재원팝업을(를) 조회한다.
     * @param acntUnt 회계단위
     * @param fincNm 재원명
     */
    @RequestMapping(value = "admi/adcom/selectBgFincPopList.do")
    public void  selectBgFincPopList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = adcomService.selectBgFincPopList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }	

    
    /*#########################################################################################################################################*/
    /* 예산조회팝업(admi01010_p05.xfdl)                                                                                                             */
    /*#########################################################################################################################################*/
    
    
    /*#########################################################################################################################################*/
    /* 우편번호조회팝업(admi01010_p06.xfdl)                                                                                                         */
    /*#########################################################################################################################################*/

    
    /**
     * 우편번호조회팝업을(를) 조회한다.
     * @param sidoGb 시도구분
     * @param doroZipNm 지번도로명
     */
    @RequestMapping(value = "admi/adcom/selectZipPopList.do")
    public void  selectZipPopList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = adcomService.selectZipPopList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    
    /*#########################################################################################################################################*/
    /* 조직도조회팝업(admi01010_p07.xfdl)                                                                                                           */
    /*#########################################################################################################################################*/

	
    /**
     * 조직도팝업을(를) 조회한다.
     * @param jojikGb 조직구분
     * @param jojikCdNm 조직코드/명
     */
    @RequestMapping(value = "admi/adcom/selectDeptPopList.do")
    public void  selectDeptPopList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = adcomService.selectDeptPopList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }

    
    /*#########################################################################################################################################*/
    /* 계좌조회팝업(admi01010_p08.xfdl)                                                                                                        */
    /*#########################################################################################################################################*/

    
    /**
     * 계좌조회팝업을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    @RequestMapping(value = "admi/adcom/selectAcntNoPopList.do")
    public void  selectAcntNoPopList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = adcomService.selectAcntNoPopList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }

    @RequestMapping(value = "admi/adcom/selectAfdAcntNoPopList.do")
    public void  selectAfdAcntNoPopList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = adcomService.selectAfdAcntNoPopList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    @RequestMapping(value = "admi/adcom/selectAcntNoPopList30.do")
    public void  selectAcntNoPopList30(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = adcomService.selectAcntNoPopList30(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    
    /*#########################################################################################################################################*/
    /* 거래처팝업조회(admi01010_p09.xfdl)                                                                                                      */
    /*#########################################################################################################################################*/

	
    /**
     * 거래처 팝업을(를) 조회한다.
     * @param acntUnt 회계단위
     * @param acntYy 회계년도
     * @param bizNm 사업명
     * @param bizLvlCd 사업레벨
     * @param uprBizCd 상위사업레벨
     * @param befBizCd 이전사업레벨
     */
    @RequestMapping(value = "admi/adcom/selectVendPopList.do")
    public void  selectVendPopList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = adcomService.selectVendPopList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    
    /*#########################################################################################################################################*/
    /* 입금내역팝업조회(admi01010_p10.xfdl)                                                                                                             */
    /*#########################################################################################################################################*/

    
    /*#########################################################################################################################################*/
    /* 세금계산서팝업조회(admi01010_p11.xfdl)                                                                                                             */
    /*#########################################################################################################################################*/
    
    
    /*#########################################################################################################################################*/
    /* 연계결의서팝업조회(admi01010_p12.xfdl)                                                                                                             */
    /*#########################################################################################################################################*/
    
    /*#########################################################################################################################################*/
    /* 은행조회팝업(admi01010_p13.xfdl)                                                                                                        */
    /*#########################################################################################################################################*/

    
    /**
     * 계좌등록팝업을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    @RequestMapping(value = "admi/adcom/selectBankPopList.do")
    public void  selectBankPopList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = adcomService.selectBankPopList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
	
    /*#########################################################################################################################################*/
    /* 교직원조회팝업(admi01010_p14.xfdl)                                                                                                        */
    /*#########################################################################################################################################*/

    
    /**
     * 교직원팝업을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    @RequestMapping(value = "admi/adcom/selectComUserPopList.do")
    public void  selectComUserPopList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = adcomService.selectComUserPopList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /*#########################################################################################################################################*/
    /* 교직원조회팝업(admi01010_p14.xfdl)                                                                                                        */
    /*#########################################################################################################################################*/

    
    /**
     * 
     * 
     * 
     */
    @RequestMapping(value = "admi/adcom/selectAcntOrgIdCombo.do")
    public void  selectAcntOrgIdCombo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = adcomService.selectAcntOrgIdCombo(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 부서별예산조회팝업(admi01010_p15.xfdl)                                                                                                       */
    /*#########################################################################################################################################*/

    
    /**
     * 
     * 
     * 
     */
    @RequestMapping(value = "admi/adcom/admi01010_p15_01.do")
    public void  admi01010_p15_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = adcomService.admi01010_p15_01(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    @RequestMapping(value = "admi/adcom/admi01010_p15_01_bg.do")
    public void  admi01010_p15_01_bg(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = adcomService.admi01010_p15_01_bg(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    
    @RequestMapping(value = "admi/adcom/admi01010_p15_01_ac02010_p01.do")
    public void  admi01010_p15_01_ac02010_p01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = adcomService.admi01010_p15_01_ac02010_p01(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 기타소득자조회팝업(admi01010_p16.xfdl)                                                                                                       */
    /*#########################################################################################################################################*/

    
    /**
     * 
     * 
     * 
     */
    @RequestMapping(value = "admi/adcom/admi01010_p16_01.do")
    public void  admi01010_p16_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = adcomService.admi01010_p16_01(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    
    
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 국가조회팝업(admi01010_p17.xfdl)                                                                                                       */
    /*#########################################################################################################################################*/

    
    /**
     * 
     * 
     * 
     */
    @RequestMapping(value = "admi/adcom/admi01010_p17_01.do")
    public void  admi01010_p17_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = adcomService.admi01010_p17_01(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    
    
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 거래처계좌조회팝업(admi01010_p19.xfdl)                                                                                                    */
    /*#########################################################################################################################################*/

    
    /**
     * 
     * 
     * 
     */
    @RequestMapping(value = "admi/adcom/admi01010_p19_01.do")
    public void  admi01010_p19_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = adcomService.admi01010_p19_01(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    
    
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 교직원계좌조회팝업(admi01010_p20.xfdl)                                                                                                    */
    /*#########################################################################################################################################*/

    
    /**
     * 
     * 
     * 
     */
    @RequestMapping(value = "admi/adcom/admi01010_p20_01.do")
    public void  admi01010_p20_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = adcomService.admi01010_p20_01(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 기타계좌조회팝업(admi01010_p21.xfdl)                                                                                                    */
    /*#########################################################################################################################################*/

    
    /**
     * 
     * 
     * 
     */
    @RequestMapping(value = "admi/adcom/admi01010_p21_01.do")
    public void  admi01010_p21_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = adcomService.admi01010_p21_01(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    
    
    
    
    
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* (원)결의서조회팝업(admi01010_p12.xfdl)                                                                                                    */
    /*#########################################################################################################################################*/

    
    /**
     * 
     * 
     * 
     */
    @RequestMapping(value = "admi/adcom/admi01010_p12_01.do")
    public void  admi01010_p12_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = adcomService.admi01010_p12_01(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 계정과목조회팝업(결산대체)(admi01010_p22.xfdl)                                                                                                    */
    /*#########################################################################################################################################*/

    
    /**
     * 
     * 
     * 
     */
    @RequestMapping(value = "admi/adcom/admi01010_p22_01.do")
    public void  admi01010_p22_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = adcomService.admi01010_p22_01(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }


    /*#########################################################################################################################################*/
    /* 입금내역연계팝업(admi01010_p23.xfdl)                                                                                                         */
    /*#########################################################################################################################################*/

	
    /**
     * 입금내역연계 팝업을(를) 조회한다.
     * @param tdt 입금일자
     * @param acno 계좌번호
     * @param depsAmtFrom 입금액from
     * @param depsAmtTo 입금액to
     * @param userMemo 입금메시지
     */
    @RequestMapping(value = "admi/adcom/selectAdmi01010_p23.do")
    public void  selectAdmi01010_p23(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = adcomService.selectAdmi01010_p23(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }


    /*#########################################################################################################################################*/
    /* 법인카드승인내역팝업(admi01010_p24.xfdl)                                                                                                */
    /*#########################################################################################################################################*/

	
    /**
     * 법인카드승인내역팝업 팝업을(를) 조회한다.
     * @param tdt 입금일자
     * @param acno 계좌번호
     * @param depsAmtFrom 입금액from
     * @param depsAmtTo 입금액to
     * @param userMemo 입금메시지
     */
    @RequestMapping(value = "admi/adcom/selectAdmi01010_p24.do")
    public void  selectAdmi01010_p24(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = adcomService.selectAdmi01010_p24(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    @RequestMapping(value = "admi/adcom/selectAc01040.do")
    public void  selectAc01040(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = adcomService.selectAc01040(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
}
