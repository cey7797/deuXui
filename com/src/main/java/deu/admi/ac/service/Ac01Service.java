package deu.admi.ac.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nexacro.xapi.data.DataSet;

public interface Ac01Service {
	
	
    /*#########################################################################################################################################*/
    /* 계좌등록(admi/ac/ac01/ac01010.xfdl)                                                                                                       */
    /*#########################################################################################################################################*/

    
    /**
     * 계좌등록을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    public abstract List<?> selectAc01010MainList(Map map) throws Exception;
    
    /**
     * 계좌등록을(를) 저장한다.
     * @throws Exception 
     */
    public abstract void saveAc01010MainList(DataSet ds) throws Exception;
    
    
    public abstract List<?> selectCardAcntNoCombo(Map map) throws Exception;
    
    /**
     * 법인카드 목록을(를) 조회한다
     */
    public abstract List<?> selectAcCorpCardList(Map map) throws Exception;
    
    /**
     * 법인카드 목록 을(를) 저장한다.
     * @throws Exception 
     */
    public abstract void saveAcCorpCardList(DataSet ds) throws Exception;
    
    /**
     * 법인카드 상세목록을(를) 조회한다
     */
    public abstract List<?> selectAcCorpCardDetList(Map map) throws Exception;
    
    /**
     * 법인카드 상세목록 을(를) 저장한다.
     * @throws Exception 
     */
    public abstract void saveAcCorpCardDetList(DataSet ds) throws Exception;
    
    
    /**
     * 법인카드개인입금 목록을(를) 조회한다
     */
    public abstract List<?> selectAcCorpCardUseList(Map map) throws Exception;
    
    /**
     * 법인카드개인입금 목록 을(를) 저장한다.
     * @throws Exception 
     */
    public abstract void saveAcCorpCardUseList(DataSet ds) throws Exception;

    public abstract void deleteAcCorpCardUseList(DataSet ds) throws Exception;
    
    /**
     * 미정산 입금내역을(를) 조회한다
     */
    public abstract List<?> selectNoIncAcntNoCnt(Map map) throws Exception;
    
    /**
     * 미정산 입금내역을(를) 조회한다
     */
    public abstract List<?> selectNoIncAcntNoList(Map map) throws Exception;
    
    public abstract List<?> selectNoBillCardList(Map map) throws Exception;
    /**
     * 입금내역 목록을(를) 조회한다
     */
    public abstract List<?> selectIncAcntNoList(Map map) throws Exception;
    
    /**
     * 입금내역 목록을(를) 저장한다
     */
    public abstract void saveIncAcntNoList(DataSet ds) throws Exception;
    
    public abstract List<?> selectAc01100MainList(Map map) throws Exception;
    
    public abstract void saveAc01100MainList(DataSet ds) throws Exception;
}
