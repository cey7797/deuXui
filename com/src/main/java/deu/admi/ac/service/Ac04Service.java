package deu.admi.ac.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nexacro.xapi.data.DataSet;

public interface Ac04Service {

    
    /*#########################################################################################################################################*/
    /* 계산서등록(admi/ac/ac04/ac04010.xfdl)                                                                                                   */
    /*#########################################################################################################################################*/

    
    public abstract List<?> selectAcBizNo(Map map) throws Exception;
	
    /**
     * 계산서등록을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    public abstract List<?> selectAc04010MainList(Map map) throws Exception;
    

    public abstract void saveAc04010MainList(DataSet ds) throws Exception;
    
    /*#########################################################################################################################################*/
    /* 계산서합계표현황(admi/ac/ac04/ac04020.xfdl)                                                                                             */
    /*#########################################################################################################################################*/

    
    /**
     * 계산서합계표현황을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    public abstract List<?> selectAc04020MainList(Map map) throws Exception;
    
    public abstract List<?> selectAc04020SubList(Map map) throws Exception;
    
    public abstract void saveAc04020MainList(Map map) throws Exception;
    
    /*#########################################################################################################################################*/
    /* 소득자료등록(admi/ac/ac04/ac04040.xfdl)                                                                                                 */
    /*#########################################################################################################################################*/

    
    /**
     * 소득자료등록을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    public abstract List<?> selectAc04040MainList(Map map) throws Exception;
    
    public abstract void saveAc04040MainList(DataSet ds) throws Exception;
    
    
    /*#########################################################################################################################################*/
    /* 소득신고파일생성및신고완료처리(admi/ac/ac04/ac04050.xfdl)                                                                               */
    /*#########################################################################################################################################*/

    
    /**
     * 소득신고파일생성 및 신고완료처리을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    public abstract List<?> selectAc04050MainList(Map map) throws Exception;
    
    
    /*#########################################################################################################################################*/
    /* 원천징수영수증(admi/ac/ac04/ac04060.xfdl)                                                                                               */
    /*#########################################################################################################################################*/

    
    /**
     * 원천징수영수증을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    public abstract List<?> selectAc04060MainList(Map map) throws Exception;
    
    public abstract List<?> selectAc04060SubList(Map map) throws Exception;
}
