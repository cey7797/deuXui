package deu.admi.adcom.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nexacro.xapi.data.DataSet;

public interface AdcomService {


    /*#########################################################################################################################################*/
	/* 권한조회팝업(admi01010_p01.xfdl)                                                                                                             */
    /*#########################################################################################################################################*/

	
    /**
     * 권한조회 팝업을(를) 조회한다.
     * @param roleNm 권한ID/명
     */
    public abstract List<?> selectComRolePopList(Map map) throws Exception;


    /*#########################################################################################################################################*/
    /* 계정코드조회팝업(admi01010_p02.xfdl)                                                                                                         */
    /*#########################################################################################################################################*/

	
    /**
     * 사업내역팝업을(를) 조회한다.
     * @param acntUnt 회계단위
     * @param acntYy 회계년도
     * @param bizNm 사업명
     * @param bizLvlCd 사업레벨
     * @param uprBizCd 상위사업레벨
     * @param befBizCd 이전사업레벨
     */
    public abstract List<?> selectBgAcntCdPopList(Map map) throws Exception;


    /*#########################################################################################################################################*/
    /* 사업조회팝업(admi01010_p03.xfdl)                                                                                                                       */
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
    public abstract List<?> selectBgBizPopList(Map map) throws Exception;


    /*#########################################################################################################################################*/
    /* 재원조회팝업(admi01010_p04.xfdl)                                                                                                             */
    /*#########################################################################################################################################*/

	
    /**
     * 재원팝업을(를) 조회한다.
     * @param acntUnt 회계단위
     * @param fincNm 재원명
     */
    public abstract List<?> selectBgFincPopList(Map map) throws Exception;

    
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
    public abstract List<?> selectZipPopList(Map map) throws Exception;
    

    /*#########################################################################################################################################*/
    /* 조직도조회팝업(admi01010_p07.xfdl)                                                                                                           */
    /*#########################################################################################################################################*/

	
    /**
     * 조직도팝업을(를) 조회한다.
     * @param jojikGb 조직구분
     * @param jojikCdNm 조직코드/명
     */
    public abstract List<?> selectDeptPopList(Map map) throws Exception;
    

    /*#########################################################################################################################################*/
    /* 계좌조회팝업(admiac01010_p08.xfdl)                                                                                                           */
    /*#########################################################################################################################################*/

    
    /**
     * 계좌팝업을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    public abstract List<?> selectAcntNoPopList(Map map) throws Exception;

    public abstract List<?> selectAfdAcntNoPopList(Map map) throws Exception;
    
    public abstract List<?> selectAcntNoPopList30(Map map) throws Exception;

    /*#########################################################################################################################################*/
    /* 거래처팝업조회(admi01010_p09.xfdl)                                                                                                      */
    /*#########################################################################################################################################*/
    
    
    /**
     * 거래처팝업을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    public abstract List<?> selectVendPopList(Map map) throws Exception;
    
    
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
    /* 은행조회팝업(admiac01010_p13.xfdl)                                                                                                           */
    /*#########################################################################################################################################*/

       
    /**
     * 은행팝업을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    public abstract List<?> selectBankPopList(Map map) throws Exception;
    
    
    /*#########################################################################################################################################*/
    /* 교직원팝업조회(admi01010_p14.xfdl)                                                                                                      */
    /*#########################################################################################################################################*/
    
    
    /**
     * 거래처팝업을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    public abstract List<?> selectComUserPopList(Map map) throws Exception;
    
    /*#########################################################################################################################################*/
    /* (관리)부서검색콤보(cfn_AcntOrgIdCombo)                                                                                                       */
    /*#########################################################################################################################################*/
    
    
    /**
     * 부서콤보을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    public abstract List<?> selectAcntOrgIdCombo(Map map) throws Exception;
    
    
    
    
    /*#########################################################################################################################################*/
    /* 부서별예산조회팝업(admi01010_p15.xfdl)                                                                                                       */
    /*#########################################################################################################################################*/
    
    
    /**
     * 
     * 
     * 
     */
    public abstract List<?> admi01010_p15_01(Map map) throws Exception;
    
    public abstract List<?> admi01010_p15_01_bg(Map map) throws Exception;
    
    public abstract List<?> admi01010_p15_01_ac02010_p01(Map map) throws Exception;
    
    
    /*#########################################################################################################################################*/
    /* 기타소득자조회팝업(admi01010_p16.xfdl)                                                                                                       */
    /*#########################################################################################################################################*/
    
    
    /**
     * 
     * 
     * 
     */
    public abstract List<?> admi01010_p16_01(Map map) throws Exception;
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 국가조회팝업(admi01010_p17.xfdl)                                                                                                       */
    /*#########################################################################################################################################*/
    
    
    /**
     * 
     * 
     * 
     */
    public abstract List<?> admi01010_p17_01(Map map) throws Exception;
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 거래처계좌조회팝업(admi01010_p19.xfdl)                                                                                                    */
    /*#########################################################################################################################################*/
    
    
    /**
     * 
     * 
     * 
     */
    public abstract List<?> admi01010_p19_01(Map map) throws Exception;
    
    
    
    /*#########################################################################################################################################*/
    /* 교직원계좌조회팝업(admi01010_p20.xfdl)                                                                                                    */
    /*#########################################################################################################################################*/
    
    
    /**
     * 
     * 
     * 
     */
    public abstract List<?> admi01010_p20_01(Map map) throws Exception;
    
    
    
    
    /*#########################################################################################################################################*/
    /* 기타계좌조회팝업(admi01010_p21.xfdl)                                                                                                    */
    /*#########################################################################################################################################*/
    
    
    /**
     * 
     * 
     * 
     */
    public abstract List<?> admi01010_p21_01(Map map) throws Exception;
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* (원)결의서조회팝업(admi01010_p12.xfdl)                                                                                                    */
    /*#########################################################################################################################################*/
    
    
    /**
     * 
     * 
     * 
     */
    public abstract List<?> admi01010_p12_01(Map map) throws Exception;
    
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 계정과목조회팝업(결산대체)(admi01010_p22.xfdl)                                                                                                    */
    /*#########################################################################################################################################*/
    
    
    /**
     * 
     * 
     * 
     */
    public abstract List<?> admi01010_p22_01(Map map) throws Exception;


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
    public abstract List<?> selectAdmi01010_p23(Map map) throws Exception;


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
    public abstract List<?> selectAdmi01010_p24(Map map) throws Exception;
    
    public abstract List<?> selectAc01040(Map map) throws Exception;
}