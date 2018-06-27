package deu.admi.ac.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import deu.admi.ac.service.Ac01Service;
import deu.comm.unione.BusinessException;
import deu.comm.unione.ConvertUtil;
import deu.comm.unione.service.impl.CommonDAO;

import com.nexacro.xapi.data.DataSet;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("ac01Service")
public class Ac01ServiceImpl extends EgovAbstractServiceImpl implements Ac01Service{
	 
	@Resource(name = "commonDAO")
	private CommonDAO commonDAO;

	
    /*#########################################################################################################################################*/
    /* 계좌등록(admi/ac/ac01/ac01010.xfdl)                                                                                                     */
    /*#########################################################################################################################################*/

    
    /**
     * 계좌등록을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    public List<?> selectAc01010MainList(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac01DAO.selectAc01010MainList", map);
        return list;
    }
    
    /**
     * 계좌등록을(를) 저장한다.
     * @throws Exception 
     */
	@Override
    public void saveAc01010MainList(DataSet ds) throws Exception{
        String insertQueryId="ac01DAO.insertAc01010MainList";
        String updateQueryId="ac01DAO.updateAc01010MainList";
        String deleteQueryId="ac01DAO.deleteAc01010MainList";
        commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
    }
     
    public List<?> selectCardAcntNoCombo(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac01DAO.selectCardAcntNoCombo", map);
        return list;
    }
	
     /**
      * 법인카드 목록을(를) 조회한다
      */
     public List<?> selectAcCorpCardList(Map map) throws Exception{
         List<?> list = commonDAO.selectData("ac01DAO.selectAcCorpCardList", map);
         return list;
     }
     
     /**
      * 법인카드 목록을(를) 등록,저장,삭제한다.
      */
     @Override
     public void saveAcCorpCardList(DataSet ds) throws Exception{
         String rowType = "";    // ROW 상태
     
         for (int i = 0; i < ds.getRowCount(); i++) {

             rowType = ds.getString(i, "rowStatus");
             HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
             
             if("insert".equals(rowType)){
                 commonDAO.insert("ac01DAO.insertAcCorpCardList", hm);
             }else if("update".equals(rowType)){
                 commonDAO.update("ac01DAO.updateAcCorpCardList", hm);
             }else if("delete".equals(rowType)){
                 commonDAO.delete("ac01DAO.deleteAcCorpCardList", hm);
             }
         }
     }
     
     /**
      * 법인카드 상세목록을(를) 조회한다
      */
     public List<?> selectAcCorpCardDetList(Map map) throws Exception{
         List<?> list = commonDAO.selectData("ac01DAO.selectAcCorpCardDetList", map);
         return list;
     }
     
     /**
      * 법인카드 상세목록을(를) 등록,저장,삭제한다.
      */
     @Override
     public void saveAcCorpCardDetList(DataSet ds) throws Exception{
         String rowType = "";    // ROW 상태
     
         for (int i = 0; i < ds.getRowCount(); i++) {

             rowType = ds.getString(i, "rowStatus");
             HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
             
             if("insert".equals(rowType)){
                 commonDAO.insert("ac01DAO.insertAcCorpCardDetList", hm);
             }else if("update".equals(rowType)){
                 commonDAO.update("ac01DAO.updateAcCorpCardDetList", hm);
             }else if("delete".equals(rowType)){
                 commonDAO.delete("ac01DAO.deleteAcCorpCardDetList", hm);
             }
         }
     }
     
     
     /**
      * 법인카드개인입금 목록을(를) 조회한다
      */
     public List<?> selectAcCorpCardUseList(Map map) throws Exception{
         List<?> list = commonDAO.selectData("ac01DAO.selectAcCorpCardUseList", map);
         return list;
     }
     
     /**
      * 법인카드개인입금 목록을(를) 등록,저장,삭제한다.
      */
     @Override
     public void saveAcCorpCardUseList(DataSet ds) throws Exception{
         String rowType = "";    // ROW 상태
     
         for (int i = 0; i < ds.getRowCount(); i++) {

             rowType = ds.getString(i, "rowStatus");
             HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
             
             if("insert".equals(rowType)){
                 commonDAO.insert("ac01DAO.insertAcCorpCardUseList", hm);
             }else if("update".equals(rowType)){
                 commonDAO.update("ac01DAO.updateAcCorpCardUseList", hm);
             }else if("delete".equals(rowType)){
                 commonDAO.delete("ac01DAO.deleteAcCorpCardUseList", hm);
             }
         }
     }
     
     @Override
     public void deleteAcCorpCardUseList(DataSet ds) throws Exception{
         String rowType = "";    // ROW 상태
     
         for (int i = 0; i < ds.getRowCount(); i++) {

             rowType = ds.getString(i, "rowStatus");
             HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
             
             if("delete".equals(rowType)){
                 commonDAO.delete("ac01DAO.deleteAcCardApvlList", hm);
             }
         }
     }
     
     /**
      * 미정산 입금내역을(를) 조회한다
      */
     public List<?> selectNoIncAcntNoCnt(Map map) throws Exception{
         List<?> list = commonDAO.selectData("ac01DAO.selectNoIncAcntNoCnt", map);
         return list;
     }
     
     /**
      * 미정산 입금내역을(를) 조회한다
      */
     public List<?> selectNoIncAcntNoList(Map map) throws Exception{
         List<?> list = commonDAO.selectData("ac01DAO.selectNoIncAcntNoList", map);
         return list;
     }
     public List<?> selectNoBillCardList(Map map) throws Exception{
         List<?> list = commonDAO.selectData("ac01DAO.selectNoBillCardList", map);
         return list;
     }
     
     /**
      * 입금내역 목록을(를) 조회한다
      */
     public List<?> selectIncAcntNoList(Map map) throws Exception{
         List<?> list = commonDAO.selectData("ac01DAO.selectIncAcntNoList", map);
         return list;
     }
     
     /**
      * 입금내역 목록을(를) 저장한다
      */
     public void saveIncAcntNoList(DataSet ds) throws Exception{
         for (int i = 0; i < ds.getRowCount(); i++) {
             HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
             commonDAO.update("ac01DAO.updateIncAcntNoList", hm);
         }
     }
     
     public List<?> selectAc01100MainList(Map map) throws Exception{
         List<?> list = commonDAO.selectData("ac01DAO.selectAc01100MainList", map);
         return list;
     }
     
 	@Override
     public void saveAc01100MainList(DataSet ds) throws Exception{
         String insertQueryId="ac01DAO.insertAc01100MainList";
         String updateQueryId="ac01DAO.updateAc01100MainList";
         String deleteQueryId="ac01DAO.deleteAc01100MainList";
         commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
     }
}
