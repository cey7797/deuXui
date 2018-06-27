package deu.admi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import deu.admi.service.AdmiService;
import deu.comm.unione.BusinessException;
import deu.comm.unione.ConvertUtil;
import deu.comm.unione.service.impl.CommonDAO;
import deu.comm.unione.service.impl.MsDAO;

import com.nexacro.xapi.data.DataSet;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("admiService")
public class AdmiServiceImpl extends EgovAbstractServiceImpl implements AdmiService{
	 
	//private static final Logger LOGGER = LoggerFactory.getLogger(AcmCsiService.class);

	@Resource(name = "commonDAO")
	private CommonDAO commonDAO;
	
	@Resource(name = "msDAO")
	private MsDAO msDAO;

    /*****************                   admiCommon                   ******************/
	
	/*
	 * 회계연도 조회한다.
	 */
    @Override
    public List<?> selectAcntYy(Map map) throws Exception{
        List<?> list = commonDAO.selectData("admiDAO.selectAcntYy", map);
        return list;
    }
	
	/*
	 * 회계단위 콤보를 조회한다
	 */
    @Override
    public List<?> selectAcntUntCmb(Map map) throws Exception{
        List<?> list = commonDAO.selectData("admiDAO.selectAcntUntCmb", map);
        return list;
    }
    
    /**
     * 회계단위등록(상세정보탭) 을(를) 조회한다.
     * @throws Exception 
     */
	@Override
    public List<?> selectAcntUntList(Map map) throws Exception{
        List<?> list = commonDAO.selectData("admiDAO.selectAcntUntList", map);
        return list;
    }
	
	/**
     * 회계단위등록(상세정보탭) 을(를) 저장한다.
     * @throws Exception 
     */
	@Override
    public void saveAcntUntList(DataSet ds) throws Exception{
        String insertQueryId="admiDAO.insertAcntUntList";
        String updateQueryId="admiDAO.updateAcntUntList";
        String deleteQueryId="admiDAO.deleteAcntUntList";
        commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
    }
	
	/**
     * 회계단위등록(권한탭) 을(를) 조회한다.
     * @throws Exception 
     */
	@Override
    public List<?> selectAcntPermList(Map map) throws Exception{
        List<?> list = commonDAO.selectData("admiDAO.selectAcntPermList", map);
        return list;
    }
	
	/**
     * 회계단위등록(권한탭) 을(를) 저장한다.
     * @throws Exception 
     */
	@Override
    public void saveAcntPermList(DataSet ds) throws Exception{
        String insertQueryId="admiDAO.insertAcntPermList";
        String updateQueryId="admiDAO.updateAcntPermList";
        String deleteQueryId="admiDAO.deleteAcntPermList";
        commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
    }
	
	/**
     * 회계단위등록(합산회계탭) 을(를) 조회한다.
     * @throws Exception 
     */
	@Override
    public List<?> selectAcntAddList(Map map) throws Exception{
        List<?> list = commonDAO.selectData("admiDAO.selectAcntAddList", map);
        return list;
    }
	
	/**
     * 회계단위등록(합산회계탭) 을(를) 저장한다.
     * @throws Exception 
     */
	@Override
    public void saveAcntAddList(DataSet ds) throws Exception{
        String insertQueryId="admiDAO.insertAcntAddList";
        String updateQueryId="admiDAO.updateAcntAddList";
        String deleteQueryId="admiDAO.deleteAcntAddList";
        commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
    }
    

    /*#########################################################################################################################################*/
    /* 계정코드등록(bg01020)                                                                                                                       */
    /*#########################################################################################################################################*/
        
    
    /**
     * 계정내역을(를) 조회한다.
     * @param acntUnt 회계단위
     * @param acntYy 회계년도
     */
    public List<?> selectAcntList(Map map) throws Exception{
        List<?> list = commonDAO.selectData("admiDAO.selectAcntList", map);
        return list;
    }
    
    /**
     * 세세목내역을(를) 조회한다.
     * @param acntUnt 회계단위
     * @param acntYy 회계년도
     * @param acntCd 계정과목코드
     */
    public List<?> selectItmSeqList(Map map) throws Exception{
        List<?> list = commonDAO.selectData("admiDAO.selectItmSeqList", map);
        return list;
    }

	/**
     * 계정내역을(를) 저장한다.
     * @param resultList 계정 목록정보
	 */
	@Override
	public void saveAcntList(DataSet ds01, DataSet ds02) throws Exception{
		if(ds01.getRowCount() > 0) {
			String insertQueryId="admiDAO.insertAcntList";
			String updateQueryId="admiDAO.updateAcntList";
			String deleteQueryId="admiDAO.deleteAcntList";
			commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds01);
		}		
		if(ds02.getRowCount() > 0) {
			String subInsertQueryId="admiDAO.insertItmSeqList";
			String subUpdateQueryId="admiDAO.updateItmSeqList";
			String subDeleteQueryId="admiDAO.deleteItmSeqList";
			commonDAO.saveData(subInsertQueryId, subUpdateQueryId, subDeleteQueryId, ds02);
		}
	}
	
	/*
     * 전년도 예산을 복사한다
     */
    @Override
    public void saveAcntCopyList(DataSet ds1) throws Exception{
  	  String rowType = "";
  	    //변경예산 등록,수정,삭제한다.
  	    for(int i = 0; i < ds1.getRowCount(); i++) {
  		  
  		    rowType = ds1.getString(i, "rowStatus");
  		    HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds1, i);
  		    
  		    if("insert".equals(rowType)){
  		    	commonDAO.insert("admiDAO.insertBgAcntCopyList", hm);
  		    }
  	    }
  	    
    }

	/************************************************************************
	 * 재원등록(bg01040
	 ***********************************************************************/

	/*
	 * 재원등록내역을 조회한다.
	 */
    @Override
    public List<?> selectBgFinc(Map map) throws Exception{
        List<?> list = commonDAO.selectData("admiDAO.selectBgFinc", map);
        return list;
    }
    
    /*
     * 재원내역 저장
     */
     
     @Override
     public void saveBgFinc(DataSet ds) throws Exception{
         String rowType = "";    // ROW 상태
     
         for (int i = 0; i < ds.getRowCount(); i++) {

             rowType = ds.getString(i, "rowStatus");
             HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
             
             if("insert".equals(rowType)){
                 commonDAO.insert("admiDAO.insertBgFinc", hm);
             }else if("update".equals(rowType)){
                 commonDAO.update("admiDAO.updateBgFinc", hm);
             }else if("delete".equals(rowType)){
                 commonDAO.delete("admiDAO.deleteBgFinc", hm);
             }
         }
     }
     
     /*********************************************************
      * 예산일정등록(bg02010)
      *********************************************************/
     /*
 	 *예산일정을 조회한다
 	 */
     @Override
     public List<?> selectBgSched(Map map) throws Exception{
         List<?> list = commonDAO.selectData("admiDAO.selectBgSched", map);
         return list;
     }

     /*
      * 예산일정저장
      */
      
      @Override
      public void saveBgSched(DataSet ds) throws Exception{
    	  String rowType = "";
    	  
    	  for(int i = 0; i < ds.getRowCount(); i++) {
    		  
    		  rowType = ds.getString(i, "rowStatus");
    		  HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
    		  
    		  if("insert".equals(rowType)){
    			  commonDAO.insert("admiDAO.insertBgSched", hm);
    		  }else if("update".equals(rowType)){
    			  commonDAO.update("admiDAO.updateBgSched", hm);
    		  }else if("delete".equals(rowType)){
    			  commonDAO.delete("admiDAO.deleteBgSched", hm);
    		  }
    	  }
      }

      /*
  	 * 사업등록내역을 조회한다
  	 */
      @Override
      public List<?> selectBgBiz(Map map) throws Exception{
          List<?> list = commonDAO.selectData("admiDAO.selectBgBiz", map);
          return list;
      }
      
      
     /*
  	 * 사업등록내역을 수정,추가,삭제한다
  	 */
      @Override
      public void saveBgBiz(DataSet ds) throws Exception{
          String rowType = "";    // ROW 상태
          int count = 0;          // insert 존재여부
          for (int i = 0; i < ds.getRowCount(); i++) {

              rowType = ds.getString(i, "rowStatus");
              HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
            
              if("insert".equals(rowType)){
            	  commonDAO.insert("admiDAO.insertBgBiz", hm);
              }else if("update".equals(rowType)){
            	  commonDAO.update("admiDAO.updateBgBiz", hm);
              }else if("delete".equals(rowType)){
            	  commonDAO.delete("admiDAO.deleteBgBiz", hm);
              }
          }
      }
      
      /*
  	 * 예산편성관련통계 내역을 조회한다
  	 */
      @Override
      public List<?> selectbgFrmn(Map map) throws Exception{
          List<?> list = commonDAO.selectData("admiDAO.selectbgFrmn", map);
          return list;
      }
      

      /*
    	 *관리부서 내역을 조회한다
    	 */
        @Override
        public List<?> selectBgOrgPermMain(Map map) throws Exception{
            List<?> list = commonDAO.selectData("admiDAO.selectBgOrgPermMain", map);
            return list;
        }
       
      /*
  	 *관리부서 상세내역을 조회한다
  	 */
      @Override
      public List<?> selectBgOrgPerm(Map map) throws Exception{
          List<?> list = commonDAO.selectData("admiDAO.selectBgOrgPerm", map);
          return list;
      }
     

    
      /*
    	 * 관리부서 을 수정,추가,삭제한다
    	 */
    /*
        @Override
    	public void saveBgOrgPerm(DataSet ds01, DataSet ds02) throws Exception{
    		if(ds01.getRowCount() > 0) {
    			String insertQueryId="admiDAO.insertBgOrgPermMain";
    			String updateQueryId="admiDAO.updateBgOrgPermMain";
    			String deleteQueryId="admiDAO.deleteBgOrgPermMain";
    			commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds01);
    		}		
    		if(ds02.getRowCount() > 0) {
    			String subInsertQueryId="admiDAO.insertBgOrgPerm";
    			String subUpdateQueryId="admiDAO.updateBgOrgPerm";
    			String subDeleteQueryId="admiDAO.deleteBgOrgPerm";
    			commonDAO.saveData(subInsertQueryId, subUpdateQueryId, subDeleteQueryId, ds02);
    		}
    	}
    	*/
      
      @Override
      public void saveBgOrgPerm(DataSet ds) throws Exception{
          String rowType = "";    // ROW 상태
      
          for (int i = 0; i < ds.getRowCount(); i++) {

              rowType = ds.getString(i, "rowStatus");
              HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
              
              if("insert".equals(rowType)){
                  commonDAO.insert("admiDAO.insertBgOrgPerm", hm);
              }else if("update".equals(rowType)){
                  commonDAO.update("admiDAO.updateBgOrgPerm", hm);
              }else if("delete".equals(rowType)){
                  commonDAO.delete("admiDAO.deleteBgOrgPerm", hm);
              }
          }
      }
      
      
      
      @Override
      public List<?> selectBgOrgBizSta(Map map) throws Exception{
          List<?> list = commonDAO.selectData("admiDAO.selectBgOrgBizSta", map);
          return list;
      }
      
      
      /**************************************************************
       * 부서별사업등록(bg02020)
       **************************************************************/
      
      
        
        /*
    	 * 부서별사업등록 조회
    	 */
        @Override
        public List<?> selectBgOrgBiz(Map map) throws Exception{
            List<?> list = commonDAO.selectData("admiDAO.selectBgOrgBiz", map);
            return list;
        }
        
        /*
         * 부서별사업등록 저장
         */
        @Override
        public void saveBgOrgBiz(DataSet ds) throws Exception{
      	  String rowType = "";
      	  
      	  for(int i = 0; i < ds.getRowCount(); i++) {
      		  
      		  rowType = ds.getString(i, "rowStatus");
      		  HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
      		  
      		  if("insert".equals(rowType)){
      			  commonDAO.insert("admiDAO.insertBgOrgBiz", hm);
      		  }else if("update".equals(rowType)){
      			  commonDAO.update("admiDAO.updateBgOrgBiz", hm);
      		  }else if("delete".equals(rowType)){
      			  commonDAO.delete("admiDAO.deleteBgOrgBiz", hm);
      		  }
      	  }
        }
        
        /**************************************************************
         * 예산편성신청등록(bg02030)
         **************************************************************/
       	/*
       	* 예산편성신청등록 조회
       	*/
       	@Override
       	public List<?> selectBgFrmnApplyList(Map map) throws Exception{
       	    List<?> list = commonDAO.selectData("admiDAO.selectBgFrmnApplyList", map);
       	    return list;
       	}
       	
       	/*
       	* 예산편성관리등록 조회
       	*/
       	@Override
       	public List<?> selectBgFrmnApplyFixedList(Map map) throws Exception{
       	    List<?> list = commonDAO.selectData("admiDAO.selectBgFrmnApplyFixedList", map);
       	    return list;
       	}
       	
       	/*
       	* 예산편성신청등록 조회 엑셀용
       	*/
       	@Override
       	public List<?> selectBgFrmnApplyExcelList(Map map) throws Exception{
       	    List<?> list = commonDAO.selectData("admiDAO.selectBgFrmnApplyExcelList", map);
       	    return list;
       	}
       	
       	/*
       	* 예산일정에 포함된 오늘기준 회계연도,회계단위,예산구분을 조회한다.
       	*/
       	@Override
       	public List<?> selectBgFrmnSched(Map map) throws Exception{
       	    List<?> list = commonDAO.selectData("admiDAO.selectBgFrmnSched", map);
       	    return list;
       	}
       	
       	/*
       	* 예산일정에 포함된 오늘기준 회계연도,회계단위,예산구분을 조회한다. 관리자용 
       	*/
       	@Override
       	public List<?> selectBgFrmnSchedFixed(Map map) throws Exception{
       	    List<?> list = commonDAO.selectData("admiDAO.selectBgFrmnSchedFixed", map);
       	    return list;
       	}
       	
       	/*
       	* 선택된 예산회계,연도,구분이 예산일정에 포함되어있는지 조회한다.
       	*/
       	@Override
       	public List<?> selectBgFrmnSchedCheck(Map map) throws Exception{
       	    List<?> list = commonDAO.selectData("admiDAO.selectBgFrmnSchedCheck", map);
       	    return list;
       	}
       	
       	/*
       	* 편성예산조회한다.
       	*/
       	@Override
       	public List<?> selectBgFrmnlist(Map map) throws Exception{
       	    List<?> list = commonDAO.selectData("admiDAO.selectBgFrmnlist", map);
       	    return list;
       	}
       	
       	/*
       	* 변경예산(관리자용)조회한다.
       	*/
       	@Override
       	public List<?> selectBgChgAdminList(Map map) throws Exception{
       	    List<?> list = commonDAO.selectData("admiDAO.selectBgChgAdminList", map);
       	    return list;
       	}
       	
       	/*
       	* 변경예산조회한다.
       	*/
       	@Override
       	public List<?> selectBgChgList(Map map) throws Exception{
       	    List<?> list = commonDAO.selectData("admiDAO.selectBgChgList", map);
       	    return list;
       	}
       	
       	/*
         * 편성예산 등록,수정,삭제한다.
         */
        @Override
        public void saveBgFrmnlist(DataSet ds1) throws Exception{
      	  String rowType = "";
      	    //편성예산 등록,수정,삭제한다.
      	    for(int i = 0; i < ds1.getRowCount(); i++) {
      		  
      		    rowType = ds1.getString(i, "rowStatus");
      		    HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds1, i);
      		  
      		    if("insert".equals(rowType)){
      			    commonDAO.insert("admiDAO.insertBgFrmn", hm);
      		    }else if("update".equals(rowType)){
      			    commonDAO.update("admiDAO.updateBgFrmn", hm);
      		    }else if("delete".equals(rowType)){
      			    commonDAO.delete("admiDAO.deleteBgFrmn", hm);
      		    }
      	    }
      	    
        }
        
        /*
         * 변경예산 등록,수정,삭제한다.
         */
        @Override
        public void saveBgChgList(DataSet ds1,DataSet ds2,DataSet ds3) throws Exception{
      	  String rowType = "";
      	    //변경예산 등록,수정,삭제한다.
      	    for(int i = 0; i < ds1.getRowCount(); i++) {
      		  
      		    rowType = ds1.getString(i, "rowStatus");
      		    HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds1, i);
      		  
      		    if("insert".equals(rowType)){
      			    commonDAO.insert("admiDAO.insertBgChg", hm);
      		    }else if("update".equals(rowType)){
      			    commonDAO.update("admiDAO.updateBgChg", hm);
      		    }else if("delete".equals(rowType)){
      			    commonDAO.delete("admiDAO.deleteBgChg", hm);
      		    }
      	    }
      	    //변경예산상세 등록,수정,삭제한다.
      	    for(int i = 0; i < ds2.getRowCount(); i++) {
      		  
    		    rowType = ds2.getString(i, "rowStatus");
    		    HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds2, i);
    		  
    		    if("insert".equals(rowType)){
    			    commonDAO.insert("admiDAO.insertBgChgDet", hm);
    		    }else if("update".equals(rowType)){
    			    commonDAO.update("admiDAO.updateBgChgDet", hm);
    		    }else if("delete".equals(rowType)){
    			    commonDAO.delete("admiDAO.deleteBgChgDet", hm);
    		    }
    	    }
      	    //변경예산계획 등록,수정,삭제한다.
      	    for(int i = 0; i < ds3.getRowCount(); i++) {
    		  
	  		    rowType = ds3.getString(i, "rowStatus");
	  		    HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds3, i);
	  		  
	  		    if("insert".equals(rowType)){
	  			    commonDAO.insert("admiDAO.insertBgChgSched", hm);
	  		    }else if("update".equals(rowType)){
	  			    commonDAO.update("admiDAO.updateBgChgSched", hm);
	  		    }else if("delete".equals(rowType)){
	  			    commonDAO.delete("admiDAO.deleteBgChgSched", hm);
	  		    }
      	    }
      	    
        }
        
        /*
         * 변경예산 등록,수정,삭제한다.
         */
        @Override
        public void saveBgChgNewList(DataSet ds1) throws Exception{
      	  String rowType = "";
      	    //변경예산 등록,수정,삭제한다.
      	    for(int i = 0; i < ds1.getRowCount(); i++) {
      		  
      		    rowType = ds1.getString(i, "rowStatus");
      		    HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds1, i);
      		    
      		    if("insert".equals(rowType)){
      		    	commonDAO.insert("admiDAO.insertBgChg", hm);
      		    }else if("update".equals(rowType)){
      			    commonDAO.update("admiDAO.updateBgChg", hm);
      		    }else if("delete".equals(rowType)){
      			    commonDAO.delete("admiDAO.deleteBgChg", hm);
      		    }
      	    }
      	    
        }
       	
       	/*
       	* 편성예산상세조회한다.
       	*/
       	@Override
       	public List<?> selectBgFrmnDetlist(Map map) throws Exception{
       	    List<?> list = commonDAO.selectData("admiDAO.selectBgFrmnDetlist", map);
       	    return list;
       	}
       	
       	/*
       	* 변경예산상세조회한다.
       	*/
       	@Override
       	public List<?> selectBgChgDetList(Map map) throws Exception{
       	    List<?> list = commonDAO.selectData("admiDAO.selectBgChgDetList", map);
       	    return list;
       	}
       	
       	/*
         * 편성예산상세 등록,수정,삭제한다.
         */
         
         @Override
         public void saveBgFrmnDetlist(DataSet ds1) throws Exception{
             String rowType = "";    // ROW 상태
             
             for (int i = 0; i < ds1.getRowCount(); i++) {

                 rowType = ds1.getString(i, "rowStatus");
                 HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds1, i);
                 HashMap<String, Object> hmSched = ConvertUtil.getDStoMap(ds1, i);
                 
                 if("insert".equals(rowType)){
                	 int intSeq = commonDAO.selectCnt("admiDAO.selectFrmnDetSeq", hm);
                	 
                	 hm.put("seq", intSeq);
                	 hmSched.put("seq", intSeq);
                     commonDAO.insert("admiDAO.insertBgFrmnDet", hm);
                     
                     int intAcntYy = Integer.parseInt((String) hm.get("acntYy"));
                     
                     hmSched.put("budgAmt", "0");
                     for(int j=3; j <= 14; j++) {
                    	 hmSched.put("budgYymm", String.valueOf(intAcntYy+1).concat("0").concat(String.valueOf(j-12)) );
                    	 if ( j > 12) {
                    		 
                    	 } else {
                    		 if ( j < 10) {
                    			 hmSched.put("budgYymm", String.valueOf(intAcntYy).concat("0").concat(String.valueOf(j)) );
                    		 } else {
                    			 hmSched.put("budgYymm", String.valueOf(intAcntYy).concat(String.valueOf(j)) );
                    		 }
                    	 }
                    	 
                    	 commonDAO.update("admiDAO.insertBgFrmnSched", hmSched);
                     }
                     
                     
                 }else if("update".equals(rowType)){
                     commonDAO.update("admiDAO.updateBgFrmnDet", hm);
                 }else if("delete".equals(rowType)){
                	 //commonDAO.delete("admiDAO.deleteBgFrmnSched", hm);
                	 commonDAO.delete("admiDAO.deleteBgFrmnDet", hm);
                 }
             }
         }
         
         /*
          * 변경예산상세 등록,수정,삭제한다.
          */
          
          @Override
          public void saveBgChgDetList(DataSet ds1, DataSet ds2, DataSet ds3, DataSet ds4) throws Exception{
              String rowType = "";    // ROW 상태
              
              for (int i = 0; i < ds1.getRowCount(); i++) {

                  rowType = ds1.getString(i, "rowStatus");
                  HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds1, i);
                  
                  if("insert".equals(rowType)){
                      commonDAO.insert("admiDAO.insertBgChgDet", hm);
                  }else if("update".equals(rowType)){
                      commonDAO.update("admiDAO.updateBgChgDet", hm);
                  }else if("delete".equals(rowType)){
                 	 commonDAO.delete("admiDAO.deleteBgChgSched", hm);
                 	 commonDAO.delete("admiDAO.deleteBgChgDet", hm);
                  }
              }
              
              for (int i = 0; i < ds2.getRowCount(); i++) {

                  rowType = ds2.getString(i, "rowStatus");
                  HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds2, i);
                  if("insert".equals(rowType)){
                 	 commonDAO.update("admiDAO.updateBgChg", hm);
                  }else if("update".equals(rowType)){
                 	 commonDAO.update("admiDAO.updateBgChg", hm);
                  }else if("delete".equals(rowType)){
                 	 commonDAO.update("admiDAO.updateBgChg", hm);
                  }
                  
              }
              
              for (int i = 0; i < ds4.getRowCount(); i++) {

                  rowType = ds4.getString(i, "rowStatus");
                  HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds4, i);
                  if("insert".equals(rowType)){
                 	 commonDAO.update("admiDAO.insertBgChgSched", hm);
                  }
                  
              }
              
          }
       	
       	/*
       	* 편성예산계획조회한다.
       	*/
       	@Override
       	public List<?> selectBgFrmnSchedList(Map map) throws Exception{
       	    List<?> list = commonDAO.selectData("admiDAO.selectBgFrmnSchedList", map);
       	    return list;
       	}
       	
       	/*
       	* 변경예산계획조회한다.
       	*/
       	@Override
       	public List<?> selectBgChgSchedList(Map map) throws Exception{
       	    List<?> list = commonDAO.selectData("admiDAO.selectBgChgSchedList", map);
       	    return list;
       	}
       	
       	/*
         * 편성예산계획 등록,수정,삭제한다.
         */
         
         @Override
         public void saveBgFrmnSchedlist(DataSet ds) throws Exception{
             String rowType = "";    // ROW 상태
         
             for (int i = 0; i < ds.getRowCount(); i++) {

                 rowType = ds.getString(i, "rowStatus");
                 HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
                 
                 if("insert".equals(rowType)){
                     commonDAO.insert("admiDAO.insertBgFrmnSched", hm);
                 }else if("update".equals(rowType)){
                     commonDAO.update("admiDAO.updateBgFrmnSched", hm);
                 }else if("delete".equals(rowType)){
                     commonDAO.delete("admiDAO.deleteBgFrmnSched", hm);
                 }
             }
         }
         
         /*
          * 변경예산계획 등록,수정,삭제한다.
          */
          
          @Override
          public void saveBgChgSchedList(DataSet ds) throws Exception{
              String rowType = "";    // ROW 상태
          
              for (int i = 0; i < ds.getRowCount(); i++) {

                  rowType = ds.getString(i, "rowStatus");
                  HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
                  
                  if("insert".equals(rowType)){
                      commonDAO.insert("admiDAO.insertBgChgSched", hm);
                  }else if("update".equals(rowType)){
                      commonDAO.update("admiDAO.updateBgChgSched", hm);
                  }else if("delete".equals(rowType)){
                      commonDAO.delete("admiDAO.deleteBgChgSched", hm);
                  }
              }
          }
         
         /*
          * 편성예산 계정과목코드 일괄 업데이트
          */
          
          @Override
          public void saveBgFrmnChglist(DataSet ds) throws Exception{
              String rowType = "";    // ROW 상태
          
              for (int i = 0; i < ds.getRowCount(); i++) {

                  rowType = ds.getString(i, "rowStatus");
                  HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
                  commonDAO.insert("admiDAO.insertBgFrmnChg", hm);
                  commonDAO.insert("admiDAO.insertBgFrmnDetChg", hm);
                  commonDAO.insert("admiDAO.insertBgFrmnSchedChg", hm);
                  commonDAO.delete("admiDAO.deleteBgFrmnSchedChg", hm);
                  commonDAO.delete("admiDAO.deleteBgFrmnDetChg", hm);
                  commonDAO.delete("admiDAO.deleteBgFrmnChg", hm);

              }
          }
          
          /*
           * 변경예산 계정과목코드 일괄 업데이트
           */
           
           @Override
           public void saveBgChgChgList(DataSet ds) throws Exception{
               String rowType = "";    // ROW 상태
           
               for (int i = 0; i < ds.getRowCount(); i++) {

                   rowType = ds.getString(i, "rowStatus");
                   HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
                   commonDAO.insert("admiDAO.insertBgChgChg", hm);
                   commonDAO.insert("admiDAO.insertBgChgDetChg", hm);
                   commonDAO.insert("admiDAO.insertBgChgSchedChg", hm);
                   commonDAO.delete("admiDAO.deleteBgChgSchedChg", hm);
                   commonDAO.delete("admiDAO.deleteBgChgDetChg", hm);
                   commonDAO.delete("admiDAO.deleteBgChgChg", hm);

               }
           }
          
          /*
           * 편성예산 일괄 삭제
           */
          @Override
          public void saveBgFrmnDelList(DataSet ds1) throws Exception{
        	  String rowType = "";
        	    for(int i = 0; i < ds1.getRowCount(); i++) {
	      		    rowType = ds1.getString(i, "rowStatus");
	      		    HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds1, i);
      		    	//commonDAO.delete("admiDAO.deleteBgFrmnSchedDel", hm);
      		    	//commonDAO.delete("admiDAO.deleteBgFrmnDetDel", hm);
      		    	//commonDAO.delete("admiDAO.deleteBgFrmn", hm);
	      		    commonDAO.delete("admiDAO.deleteBgFrmnAll", hm);
	      	    }
        	    
          }
          
          /*
           * 변경예산 일괄 삭제
           */
          @Override
          public void saveBgChgDelList(DataSet ds1) throws Exception{
        	  String rowType = "";
        	    for(int i = 0; i < ds1.getRowCount(); i++) {
	      		  
	      		    rowType = ds1.getString(i, "rowStatus");
	      		    HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds1, i);
      		    	commonDAO.delete("admiDAO.deleteBgChgSchedDel", hm);
      		    	commonDAO.delete("admiDAO.deleteBgChgDetDel", hm);
      		    	commonDAO.delete("admiDAO.deleteBgChg", hm);
	      		    	
	      	    }
        	    
          }
          
          /*
           * 변경예산 일괄 삭제 신규
           */
          @Override
          public void saveBgChgDelNewList(DataSet ds1) throws Exception{
        	  String rowType = "";
        	    for(int i = 0; i < ds1.getRowCount(); i++) {
	      		  
	      		    rowType = ds1.getString(i, "rowStatus");
	      		    HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds1, i);
      		    	commonDAO.delete("admiDAO.deleteBgChg", hm);
	      		    	
	      	    }
        	    
          }
          
	/*
	 * 편성예산 계정과목상세 단건 삭제
	 */
	@Override
	public void saveBgFrmnDetDelList(DataSet ds1,DataSet ds2,DataSet ds3) throws Exception{
		String rowType = "";
		//편성예산 등록,수정,삭제한다.
		for(int i = 0; i < ds1.getRowCount(); i++) {
	 
			rowType = ds1.getString(i, "rowStatus");
			HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds1, i);
			
	
			if("insert".equals(rowType)){
				commonDAO.insert("admiDAO.insertBgFrmn", hm);
			}else if("update".equals(rowType)){
				commonDAO.update("admiDAO.updateBgFrmn", hm);
			}else if("delete".equals(rowType)){
				commonDAO.delete("admiDAO.deleteBgFrmn", hm);
			}
		}
		//편성예산상세 등록,수정,삭제한다.
		for(int i = 0; i < ds2.getRowCount(); i++) {
	  
			rowType = ds2.getString(i, "rowStatus");
			HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds2, i);
	  
			if("insert".equals(rowType)){
				commonDAO.insert("admiDAO.insertBgFrmnDet", hm);
			}else if("update".equals(rowType)){
				commonDAO.update("admiDAO.updateBgFrmnDet", hm);
			}else if("delete".equals(rowType)){
				commonDAO.delete("admiDAO.deleteBgFrmnDet", hm);
			}
		}
		//편성예산계획 등록,수정,삭제한다.
		for(int i = 0; i < ds3.getRowCount(); i++) {
	
			rowType = ds3.getString(i, "rowStatus");
			HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds3, i);
	 
			if("insert".equals(rowType)){
				commonDAO.insert("admiDAO.insertBgFrmnSched", hm);
			}else if("update".equals(rowType)){
				commonDAO.update("admiDAO.updateBgFrmnSched", hm);
			}else if("delete".equals(rowType)){
				commonDAO.delete("admiDAO.deleteBgFrmnSched", hm);
			}
		}
	        	    
	}
	
	/*
   	* 세세목콤보조회호출
   	*/
   	@Override
   	public List<?> selectBgItmSeqCombo(Map map) throws Exception{
   	    List<?> list = commonDAO.selectData("admiDAO.selectBgItmSeqCombo", map);
   	    return list;
   	}
   	
   	/*
   	* 예산신청내역 계정기본,상세,월별 신청액의 합계여부확인
   	*/
   	@Override
   	public List<?> selectBgApplyChk(Map map) throws Exception{
   	    List<?> list = commonDAO.selectData("admiDAO.selectBgApplyChk", map);
   	    return list;
   	}
   	
   	/*
   	* 예산변경신청내역 계정기본,상세,월별 신청액의 합계여부확인
   	*/
   	@Override
   	public List<?> selectBgChgApplyChk(Map map) throws Exception{
   	    List<?> list = commonDAO.selectData("admiDAO.selectBgChgApplyChk", map);
   	    return list;
   	}
   	
   	
   	/**
     * 예산계정편성신청 프로그램
     * 신청버튼 클릭시 예산편성기본+예산편성상세+예산편성계획 3개테이블의 예산액이 맞는지 체크,
     * 예산계정기본 테이블에 진행상태를 신청으로 변경하여 저장한다
     */
	@Override
    public void saveBgStaCd(DataSet ds) throws Exception{
		String rowType = "";    // ROW 상태
	     
        for (int i = 0; i < ds.getRowCount(); i++) {

            rowType = ds.getString(i, "rowStatus");
            HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
            commonDAO.update("admiDAO.updateBgStaCd", hm);

        }
    }
	
	/**
     * 예산계정변경신청 프로그램
     * 예산변경신청 테이블에 진행상태를 신청으로 변경하여 저장한다
     */
	@Override
    public void saveBgChgStaCd(DataSet ds) throws Exception{
		String rowType = "";    // ROW 상태
	     
        for (int i = 0; i < ds.getRowCount(); i++) {

            rowType = ds.getString(i, "rowStatus");
            HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
            commonDAO.update("admiDAO.updateBgChgStaCd", hm);

        }
    }
	
	/*
   	* 접속자의 소속부서및 관리부서 콤보호출
   	*/
   	@Override
   	public List<?> selectBudgOrgIdCombo(Map map) throws Exception{
   	    List<?> list = commonDAO.selectData("admiDAO.selectBudgOrgIdCombo", map);
   	    return list;
   	}
   	
   	@Override
   	public List<?> selectBudgBizCombo(Map map) throws Exception{
   	    List<?> list = commonDAO.selectData("admiDAO.selectBudgBizCombo", map);
   	    return list;
   	}
   	
   	/*
	 * 월별편성계획 초기화(delete 후 insert)
	 */
	@Override
	public void saveBgFrmnSchedRemove(DataSet ds1,DataSet ds2) throws Exception{
		String rowType = "";
		
		//편성예산계획 삭제한다.
		for(int i = 0; i < ds1.getRowCount(); i++) {
	
			rowType = ds1.getString(i, "rowStatus");
			HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds1, i);
	 
			commonDAO.delete("admiDAO.deleteBgFrmnSchedRemove", hm);
		}
		//편성예산계획 등록한다.
		for(int i = 0; i < ds2.getRowCount(); i++) {
	
			rowType = ds2.getString(i, "rowStatus");
			HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds2, i);
	 
			commonDAO.insert("admiDAO.insertBgFrmnSchedRemove", hm);
		}
	}
	
	/*
	 * 월별변경계획 초기화(delete 후 insert)
	 */
	@Override
	public void saveBgChgSchedRemove(DataSet ds1,DataSet ds2) throws Exception{
		String rowType = "";
		
		//편성예산계획 삭제한다.
		for(int i = 0; i < ds1.getRowCount(); i++) {
	
			rowType = ds1.getString(i, "rowStatus");
			HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds1, i);
	 
			commonDAO.delete("admiDAO.deleteBgChgSchedRemove", hm);
		}
		//편성예산계획 등록한다.
		for(int i = 0; i < ds2.getRowCount(); i++) {
	
			rowType = ds2.getString(i, "rowStatus");
			HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds2, i);
	 
			commonDAO.insert("admiDAO.insertBgChgSchedRemove", hm);
		}
	}
	
	/**
     * 예산계정편성관리 프로그램
     * 예산계정기본,상세,계획을(를)전차수복사(조정차수0,1,2,3) 증가시켜 저장한다.
     */
	@Override
    public void saveBgAdjOrdCopy(Map map) throws Exception{
        commonDAO.delete("admiDAO.deleteBgFrmnSchedAdjOrdCopy", map);
        commonDAO.delete("admiDAO.deleteBgFrmnDetAdjOrdCopy", map);
        commonDAO.delete("admiDAO.deleteBgFrmnAdjOrdCopy", map);
        commonDAO.insert("admiDAO.insertBgFrmnAdjOrdCopy", map);
        commonDAO.insert("admiDAO.insertBgFrmnDetAdjOrdCopy", map);
        commonDAO.insert("admiDAO.insertBgFrmnSchedAdjOrdCopy", map);
        commonDAO.update("admiDAO.updateBgStaCdAdjCopy", map);
    }
	
	/**
     * 변경예산계정편성관리 프로그램
     * 변경예산계정기본(를)전차수복사(조정차수101,201,202,203) 증가시켜 저장한다.
     */
	@Override
    public void saveBgChgAdjOrdCopy(Map map) throws Exception{
        commonDAO.delete("admiDAO.deleteBgChgAdjOrdCopy", map);
        commonDAO.insert("admiDAO.insertBgChgAdjOrdCopy", map);
        commonDAO.update("admiDAO.updateBgChgStaCdAdjCopy", map);
    }
	
	/**
     * 예산계정편성확정 프로그램
     * 신청버튼 클릭시 예산계정기본 테이블에 진행상태를 신청으로 변경하여 저장한다
     */
	@Override
    public void saveBgStaCdFixed(DataSet ds) throws Exception{
		String staCd = "";    // 작성상태
		HashMap<String, Object> hm1 = ConvertUtil.getDStoMap(ds, 0); 
		for (int i = 0; i < ds.getRowCount(); i++) {

        	staCd = ds.getString(i, "staCd");
            HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
            
            if("50".equals(staCd)){ //확정
            	hm.put("cfmOrdCd", hm.get("adjOrdCd"));
            	commonDAO.insert("admiDAO.insertBgFrmnAsgnFixed", hm);
            	commonDAO.update("admiDAO.updateBgStaCdFixed", hm);
            }else  if("40".equals(staCd)){ //확정취소
            	hm.put("cfmOrdCd", "");
            	commonDAO.update("admiDAO.updateBgStaCdFixed", hm);
            	commonDAO.delete("admiDAO.deleteBgFrmnAsgnCancel", hm);
            }
            

        }
        //commonDAO.saveDataSP("admiDAO.saveSpDeuOraMssqlAsgnMig", hm1);
        //commonDAO.insert("admiDAO.saveSpDeuOraMssqlAsgnMig");
    }
	
	/**
     * 예산계정변경확정 프로그램
     * 확정버튼 클릭시 예산변경기본 테이블에 진행상태를 확정으로 변경하여 저장한다
     */
	@Override
    public void saveBgChgStaCdFixed(DataSet ds) throws Exception{
		String staCd = "";    // 작성상태
	     
        for (int i = 0; i < ds.getRowCount(); i++) {

        	staCd = ds.getString(i, "staCd");
            HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
            
            if("60".equals(staCd)){ //확정
            	commonDAO.insert("admiDAO.insertBgChgAsgnFixed", hm);
            	commonDAO.update("admiDAO.updateBgChgStaCdFixed", hm);
            }else  if("50".equals(staCd) || "10".equals(staCd)){ //확정취소(관리자용 프로그램에서는 작성(10)으로 돌림)
            	commonDAO.update("admiDAO.updateBgChgStaCdFixed", hm);
            	commonDAO.delete("admiDAO.deleteBgChgAsgnCancel", hm);
            }
            

        }
    }
	
	//saveBgAcntChg
	@Override
    public HashMap saveBgAcntChg(DataSet ds) throws Exception{
    //    String rowType = "";    // ROW 상태
        HashMap nMap = null;
        for (int i = 0; i < ds.getRowCount(); i++) {

           // rowType = ds.getString(i, "rowStatus");
            HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
            
                Map map = new HashMap();
                map.put("I_BF_ACNT_UNT", hm.get("bfAcntUnt"));        
                map.put("I_BF_ORG_ID", hm.get("bfBudgOrgId"));       
                map.put("I_BF_BIZ_CD", hm.get("bfBizCd"));        
                map.put("I_BF_ACNT_CD", hm.get("bfAcntCd"));       
                map.put("I_AF_ACNT_UNT", hm.get("aftAcntUnt"));    
                map.put("I_AF_ORG_ID", hm.get("aftBudgOrgId"));      
                map.put("I_AF_BIZ_CD", hm.get("aftBizCd"));      
                map.put("I_AF_ACNT_CD", hm.get("aftAcntCd"));       
                map.put("I_ACNT_YY", hm.get("acntYy"));       
                map.put("I_BAL_DIV", hm.get("balDiv"));        
                map.put("I_BUDG_CD", hm.get("budgCd"));        
                map.put("I_ADJ_ORD_CD", hm.get("adjOrdCd"));  
                map.put("I_ENTRY_NO", hm.get("insertId"));        
                map.put("I_ENTRY_IP", hm.get("insertIp"));  
                
                

                // TODO ENTRY_NO, ENTRY_IP 넣기
                nMap = commonDAO.saveDataSP("admiDAO.saveBgAcntChgSP", map);
        }
        
        return nMap;
    }
	
	/*
   	* 예산편성신청 - 작성중 사업 확인
   	*/
   	@Override
   	public List<?> selectBgApplyBizChk(Map map) throws Exception{
   	    List<?> list = commonDAO.selectData("admiDAO.selectBgApplyBizChk", map);
   	    return list;
   	}
	
	/*
   	* 예산신청중 입력되지않은 계정과목 확인
   	*/
   	@Override
   	public List<?> selectBgApplyAcntChk(Map map) throws Exception{
   	    List<?> list = commonDAO.selectData("admiDAO.selectBgApplyAcntChk", map);
   	    return list;
   	}
   	
   	/*
   	* 예산변경신청중 입력되지않은 계정과목 확인
   	*/
   	@Override
   	public List<?> selectBgChgApplyAcntChk(Map map) throws Exception{
   	    List<?> list = commonDAO.selectData("admiDAO.selectBgChgApplyAcntChk", map);
   	    return list;
   	}
   	
   	/**
     * 예산편성관리 사업반려저장
     */
	@Override
    public void saveBgStaCdReca(DataSet ds) throws Exception{
		String rowType = "";    // ROW 상태
	     
        for (int i = 0; i < ds.getRowCount(); i++) {

            rowType = ds.getString(i, "rowStatus");
            HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
            commonDAO.update("admiDAO.updateBgStaCdReca", hm);

        }
    }
	
	/**
     * 예산변경관리 사업반려저장
     */
	@Override
    public void saveBgChgStaCdReca(DataSet ds) throws Exception{
		String rowType = "";    // ROW 상태
	     
        for (int i = 0; i < ds.getRowCount(); i++) {

            rowType = ds.getString(i, "rowStatus");
            HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
            commonDAO.update("admiDAO.updateBgChgStaCdReca", hm);

        }
    }
	
	/*************************************************************************
	 * 엑셀다운로드 예산편성관리 - 예산데이터를 전체를 엑셀로 내려받기위한 조회
	 *************************************************************************/
      @Override
      public List<?> selectBgFrmnFixedExcelTotal(Map map) throws Exception{
          List<?> list = commonDAO.selectData("admiDAO.selectBgFrmnFixedExcelTotal", map);
          return list;
      }
    /*************************************************************************
  	 * 엑셀다운로드 예산편성관리 - 예산데이터를 회계단위별로 엑셀로 내려받기위한 조회
  	 *************************************************************************/
    @Override
    public List<?> selectBgFrmnFixedExcelAcntUnt(Map map) throws Exception{
        List<?> list = commonDAO.selectData("admiDAO.selectBgFrmnFixedExcelAcntUnt", map);
        return list;
    }
    /*************************************************************************
     * 엑셀다운로드 예산편성관리 - 예산데이터를 계정과목별로 엑셀로 내려받기위한 조회
     *************************************************************************/
    @Override
    public List<?> selectBgFrmnFixedExcelAcntCd(Map map) throws Exception{
        List<?> list = commonDAO.selectData("admiDAO.selectBgFrmnFixedExcelAcntCd", map);
        return list;
    }
    /*************************************************************************
     * 엑셀다운로드 예산편성관리 - 예산데이터를 사업별로 엑셀로 내려받기위한 조회
     *************************************************************************/
    @Override
    public List<?> selectBgFrmnFixedExcelBizCd(Map map) throws Exception{
        List<?> list = commonDAO.selectData("admiDAO.selectBgFrmnFixedExcelBizCd", map);
        return list;
    }
    /*************************************************************************
     * 엑셀다운로드 예산편성관리 - 예산데이터를 부서별로 엑셀로 내려받기위한 조회
     *************************************************************************/
    @Override
    public List<?> selectBgFrmnFixedExcelBudgOrgId(Map map) throws Exception{
        List<?> list = commonDAO.selectData("admiDAO.selectBgFrmnFixedExcelBudgOrgId", map);
        return list;
    }
    
    @Override
    public List<?> selectBgFrmnFixedExcelBudgCd(Map map) throws Exception{
        List<?> list = commonDAO.selectData("admiDAO.selectBgFrmnFixedExcelBudgCd", map);
        return list;
    }
    
    /*
   	* 변경예산계획조회한다.
   	*/
   	@Override
   	public List<?> selectBgChgAplyList(Map map) throws Exception{
   		Map map1 = new HashMap();
		HashMap nMap = null;		
		nMap = commonDAO.saveDataSP("admiDAO.saveSpDeuOraMssqlChgChk", map1);
   		List<?> list = commonDAO.selectData("admiDAO.selectBgChgAplyList", map);
   	    return list;
   	}
   	
   	/*
   	* 변경예산상세계획조회한다.
   	*/
   	@Override
   	public List<?> selectBgChgAplyDetList(Map map) throws Exception{
   	    List<?> list = commonDAO.selectData("admiDAO.selectBgChgAplyDetList", map);
   	    return list;
   	}
   	
   	/*
   	* 변경예산계획확정조회한다.
   	*/
   	@Override
   	public List<?> selectBgChgAplyFixedList(Map map) throws Exception{
   		Map map1 = new HashMap();
		HashMap nMap = null;		
		nMap = commonDAO.saveDataSP("admiDAO.saveSpDeuOraMssqlChgChk", map1);
   		List<?> list = commonDAO.selectData("admiDAO.selectBgChgAplyFixedList", map);
   	    return list;
   	}
   	
   	@Override
   	public List<?> selectBgChgAplyFixedListManager(Map map) throws Exception{
   		Map map1 = new HashMap();
		HashMap nMap = null;		
		nMap = commonDAO.saveDataSP("admiDAO.saveSpDeuOraMssqlChgChk", map1);
   		List<?> list = commonDAO.selectData("admiDAO.selectBgChgAplyFixedListManager", map);
   	    return list;
   	}
   	
   	/*
   	* 변경예산상세계획확정조회한다.
   	*/
   	@Override
   	public List<?> selectBgChgAplyDetFixedList(Map map) throws Exception{
   	    List<?> list = commonDAO.selectData("admiDAO.selectBgChgAplyDetFixedList", map);
   	    return list;
   	}
   	
   	/*
     * 변경예산계획 등록,수정,삭제한다.
     */
     
     @Override
     public void saveBgChgAplyList(DataSet ds) throws Exception{
         String rowType = "";    // ROW 상태
     
         for (int i = 0; i < ds.getRowCount(); i++) {

             rowType = ds.getString(i, "rowStatus");
             HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
             
             if("insert".equals(rowType)){
                 commonDAO.insert("admiDAO.insertBgChgAply", hm);
             }else if("update".equals(rowType)){
                 commonDAO.update("admiDAO.updateBgChgAply", hm);
             }else if("delete".equals(rowType)){
                 commonDAO.delete("admiDAO.deleteBgChgAply", hm);
             }
         }
     }
     
     @Override
     public void copyBgChgAplyList(DataSet ds) throws Exception{
         String rowType = "";    // ROW 상태
     
         for (int i = 0; i < ds.getRowCount(); i++) {

             rowType = ds.getString(i, "rowStatus");
             HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
             
             commonDAO.update("admiDAO.copyBgChgAply", hm);
         }
     }
     
   /*
	* 로그아웃
	*/
	@Override
	public List<?> selectDoGetLogout(Map map) throws Exception{
	    List<?> list = commonDAO.selectData("admiDAO.selectDoGetLogout", map);
	    return list;
	}
	
	/*
	* 전자결재 예산변경(전용)
	*/
	@Override
    public HashMap saveBgChgDiv10Approval(DataSet ds) throws Exception {
		String strId 			= "";
        String strFormId 		= "";
        String strTitle 		= "";
        String strSubjId 		= "";
        String strSubTitle 		= "";
        String strBody 			= "";
        String strApprovalDiv 	= "";
        String strApprovalDt 	= "";
        String strApprovalDocid = "";
        String strUserId 		= "";
        String strUpdDt 		= "";
        String strCnt = "";
        String balDivNm = "";
        String bizNm = "";
        String acntNm = "";
        String acntCd = "";
        String budgOrgId = "";
        String budgOrgNm = "";
        String budgAmt1 = "";
        String budgAmt2 = "";
        String budgAmt3 = "";
        String remark = "";
        String budgAmt1Tot = "";
        String budgAmt2Tot = "";
        String budgAmt3Tot = "";
        String elecKey = "";
        String elecKey1 = "";
        String elecKey2 = "";
        String acntUntNm = "";
        HashMap nMap  = new HashMap();    // 리턴 시킬 map
        HashMap nMap1 = new HashMap();   // 화면에서 넘어온 파라미터를 받을 map 
        HashMap nMap2 = new HashMap();   // 변경신청내역(본문내용) 받을 map
        HashMap nMap3 = new HashMap();   //mssql+oracle 전자결재연동테이블에 입력된 데이타를 예산변경테이블에 넣기위한 map
        HashMap nMap4 = new HashMap();	//전자결재ID담아올 맵
      //  String serverElecUrl = commonDAO.getProValue("Globals.serverElecUrl");  //전자결재 생성 서버url

        for (int i = 0; i < ds.getRowCount(); i++) {
            HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);//화면에서 넘어온 정보
            Map map = new HashMap(); 
            
            nMap1.put("acntUnt", hm.get("acntUnt"));
            nMap1.put("acntYy", hm.get("acntYy"));
            nMap1.put("chgSeq", hm.get("chgSeq"));
            nMap1.put("aplyOrgId", hm.get("aplyOrgId"));
            nMap1.put("chgDiv", hm.get("chgDiv"));
            
            nMap4.put("strElecId", hm.get("strElecId"));
            nMap4.put("strAcntYy", hm.get("strAcntYy"));
            
            //화면에서 넘어온 정보셋팅
            //map.put("id", hm.get("id")); // 전자결재ID
            map.put("title", hm.get("title")); // 타이틀
            map.put("subtitle", hm.get("subtitle")); // 서브타이틀
            map.put("formId", hm.get("formId")); // 양식ID
            map.put("userId", hm.get("userId")); // 기안자ID
            
            String strHtml = "<html>";
            strHtml += "<head>";
            strHtml += "<style type=\"text/css\">";
            strHtml += " p.HStyle {    margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;   } "; 
            strHtml += " p.HStyle0 {    margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;   } ";                
            strHtml += "  p.HStyleLeft {    margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: left;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;   }   ";               
            strHtml += " p.HStyleRight {    margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;   }  ";                
            strHtml += " td {    vertical-align: middle;    border: 0.4pt solid #000000;   }  ";         
            strHtml += " </style>";
            strHtml += " </head>";
            strHtml += " <body style=\"width: 650px\">";
            strHtml += " <p style=\"font-size:12pt\"></p>";
            strHtml += " <br><br>";
            strHtml += "	 <table border=\"1\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse:collapse;border:none;\">";
            strHtml += "			 <tr>";
            strHtml += "				<td width=\"50\" height=\"30\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">수지구분</p></td>";
            strHtml += "				<td width=\"80\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">부서명</p></td>";
            strHtml += "				<td width=\"150\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">사업명</p></td>";
            strHtml += "				<td width=\"90\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">계정과목명</p></td>";
            strHtml += "				<td width=\"70\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">회계단위</p></td>";
            strHtml += "				<td width=\"70\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">예산액</p></td>";
            strHtml += "				<td width=\"70\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">전용액</p></td>";
            strHtml += "				<td width=\"70\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">전용후잔액</p></td>";
            strHtml += "			</tr>";

           
            // 본문내용중 변경신청내역 얻기
            List<?> list = commonDAO.selectData("admiDAO.selectBgChgApproDiv10List", nMap1);

        	DataSet rtnDs = ConvertUtil.getListToDS(list);

            for(int j=0; j < list.size(); j++ ) {
				
            	balDivNm 	= rtnDs.getString(j,"balDivNm");
  	            bizNm 	    = rtnDs.getString(j,"bizNm");
  	            acntNm   	= rtnDs.getString(j,"acntNm");
  	            acntCd   	= rtnDs.getString(j,"acntCd");
  	            budgAmt1 	= rtnDs.getString(j,"budgAmt1");
  	            budgAmt2 	= rtnDs.getString(j,"budgAmt2");
  	            budgAmt3 	= rtnDs.getString(j,"budgAmt3");
  	            remark   	= rtnDs.getString(j,"remark");
  	            budgOrgNm   = rtnDs.getString(j,"budgOrgNm");
  	            acntUntNm   = rtnDs.getString(j,"acntUntNm");
  	            	            
	            strHtml += "			<tr>";
	            strHtml += "				<td height=\"30\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;\">"+balDivNm+"</p></td>";
	            strHtml += "				<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;\">"+budgOrgNm+"</p></td>";
	            strHtml += "				<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;\">"+bizNm+"</p></td>";
	            strHtml += "				<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;\">"+acntNm+"</p></td>";
	            strHtml += "				<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;\">"+acntUntNm+"</p></td>";
	            strHtml += "				<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+budgAmt1+"&nbsp;</p></td>";
	            strHtml += "				<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+budgAmt2+"&nbsp;</p></td>";
	            strHtml += "				<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+budgAmt3+"&nbsp;</p></td>";
	            strHtml += "			</tr>";
            }
            strHtml += "			<tr>";
            strHtml += "				<td colspan=\"5\" height=\"30\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">합&nbsp; 계</p></td>";
            strHtml += "				<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">&nbsp;</p></td>";
            strHtml += "				<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">0&nbsp;</p></td>";
            strHtml += "				<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">&nbsp;</p></td>";
            strHtml += "			</tr>";
            strHtml += "	</table>";
            strHtml += " 	</body>";
            strHtml += "</html>";
            
            List<?> list1 = commonDAO.selectData("admiDAO.selectGwApprovalMstElecId", nMap4);
            
        	DataSet rtnDs1 = ConvertUtil.getListToDS(list1);
        	
            nMap.put("id", rtnDs1.getString(0,"elecId"));
            nMap.put("formId",   map.get("formId"));
            nMap.put("title",    map.get("title"));
            nMap.put("subTitle", map.get("subTitle"));
            nMap.put("subjId",   map.get("subjId"));
            nMap.put("body", strHtml);
            nMap.put("approvalDiv", strApprovalDiv);
            nMap.put("approvalDt", strApprovalDt);
            nMap.put("approvalDocid", strApprovalDocid);
            nMap.put("userId", map.get("userId"));
            
            System.out.println(nMap);
            System.out.println("_________________________");
            
            nMap3.put("id", rtnDs1.getString(0,"elecId"));
            nMap3.put("formId",   map.get("formId"));
            
            nMap3.put("acntUnt", nMap1.get("acntUnt"));
            nMap3.put("acntYy", nMap1.get("acntYy"));
            nMap3.put("chgSeq", nMap1.get("chgSeq"));
            nMap3.put("aplyOrgId", nMap1.get("aplyOrgId"));
            nMap3.put("userId", map.get("userId"));
            
            //mssql 전자결재연동테이블에 인서트한다.
            msDAO.insert("admiDAO.insertGwApprovalMst", nMap);
            
            //oracle 전자결재연동테이블에 인서트한다.
          //  commonDAO.insert("admiDAO.insertGwApprovalMstOra", nMap);
            
            //oracle 전자결재연동테이블에 업데이트한다.
            commonDAO.update("admiDAO.updateBgChgAplyGwApprovalMstOra", nMap3);
        } 
        return nMap;
    }
	
	
	/*
	* 전자결재 예산변경(추가)
	*/
	@Override
    public HashMap saveBgChgDiv20Approval(DataSet ds) throws Exception {
		String strId 			= "";
        String strFormId 		= "";
        String strTitle 		= "";
        String strSubjId 		= "";
        String strSubTitle 		= "";
        String strBody 			= "";
        String strApprovalDiv 	= "";
        String strApprovalDt 	= "";
        String strApprovalDocid = "";
        String strUserId 		= "";
        String strUpdDt 		= "";
        String strCnt = "";
        String balDivNm = "";
        String bizNm = "";
        String acntNm = "";
        String acntCd = "";
        String budgOrgId = "";
        String budgOrgNm = "";
        String budgAmt = "";
        String budgAmt1 = "";
        String budgAmtSum = "";
        String budgAmt2 = "";
        String budgAmt3 = "";
        String remark = "";
        String budgAmt1Tot = "";
        String budgAmt2Tot = "";
        String budgAmt3Tot = "";
        String acntUntNm = "";
        HashMap nMap  = new HashMap();    // 리턴 시킬 map
        HashMap nMap1 = new HashMap();   // 화면에서 넘어온 파라미터를 받을 map 
        HashMap nMap2 = new HashMap();   // 변경신청내역(본문내용) 받을 map
        HashMap nMap3 = new HashMap();   //mssql+oracle 전자결재연동테이블에 입력된 데이타를 예산변경테이블에 넣기위한 map
        HashMap nMap4 = new HashMap();	//전자결재ID담아올 맵
      //  String serverElecUrl = commonDAO.getProValue("Globals.serverElecUrl");  //전자결재 생성 서버url

        for (int i = 0; i < ds.getRowCount(); i++) {
            HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);//화면에서 넘어온 정보
            Map map = new HashMap(); 
            
            nMap1.put("acntUnt", hm.get("acntUnt"));
            nMap1.put("acntYy", hm.get("acntYy"));
            nMap1.put("chgSeq", hm.get("chgSeq"));
            nMap1.put("aplyOrgId", hm.get("aplyOrgId"));
            nMap1.put("chgDiv", hm.get("chgDiv"));
            
            nMap4.put("strElecId", hm.get("strElecId"));
            nMap4.put("strAcntYy", hm.get("strAcntYy"));
            
    
            //화면에서 넘어온 정보셋팅
            //map.put("id", hm.get("id")); // 전자결재ID
            map.put("title", hm.get("title")); // 타이틀
            map.put("subtitle", hm.get("subtitle")); // 서브타이틀
            map.put("formId", hm.get("formId")); // 양식ID
            map.put("userId", hm.get("userId")); // 기안자ID
            
            String strHtml = "<html>";
            strHtml += "<head>";
            strHtml += "<style type=\"text/css\">";
            strHtml += " p.HStyle {    margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;   } "; 
            strHtml += " p.HStyle0 {    margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;   } ";                
            strHtml += "  p.HStyleLeft {    margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: left;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;   }   ";               
            strHtml += " p.HStyleRight {    margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;   }  ";                
            strHtml += " td {    vertical-align: middle;    border: 0.4pt solid #000000;   }  ";         
            strHtml += " </style>";
            strHtml += " </head>";
            strHtml += " <body style=\"width: 650px\">";
            strHtml += " <p style=\"font-size:12pt\"></p>";
            strHtml += " <br><br>";
            strHtml += "	 <table border=\"1\"  cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse:collapse;border:none;\">";
            strHtml += "			 <tr>";
            strHtml += "				<td width=\"50\" height=\"30\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">수지구분</p></td>"; 
            strHtml += "				<td width=\"80\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">부서명</p></td>";
            strHtml += "				<td width=\"150\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">사업명</p></td>";
            strHtml += "				<td width=\"90\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">계정과목명</p></td>";
            strHtml += "				<td width=\"70\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">회계단위</p></td>";
            strHtml += "				<td width=\"70\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">예산액</p></td>";
            strHtml += "				<td width=\"70\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">추가액</p></td>";
            strHtml += "				<td width=\"70\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">추가후잔액</p></td>";
            strHtml += "			</tr>";

           
            // 본문내용중 변경신청내역 얻기
            List<?> list = commonDAO.selectData("admiDAO.selectBgChgApproDiv20List", nMap1);

        	DataSet rtnDs = ConvertUtil.getListToDS(list);
        	
            for(int j=0; j < list.size(); j++ ) {
				
            	balDivNm 	= rtnDs.getString(j,"balDivNm");
  	            bizNm 	    = rtnDs.getString(j,"bizNm");
  	            acntNm   	= rtnDs.getString(j,"acntNm");
  	            acntCd   	= rtnDs.getString(j,"acntCd");
  	            budgAmt 	= rtnDs.getString(j,"budgAmt"); //예산액
  	            budgAmt1 	= rtnDs.getString(j,"budgAmt1"); //신청액
  	            budgAmtSum 	= rtnDs.getString(j,"budgAmtSum"); //예산액
  	            budgAmt2 	= rtnDs.getString(j,"budgAmt2"); //수입신청액합
  	            budgAmt3 	= rtnDs.getString(j,"budgAmt3"); //지출신청액합
  	            remark   	= rtnDs.getString(j,"remark");
  	            budgOrgNm   = rtnDs.getString(j,"budgOrgNm");
  	            acntUntNm   = rtnDs.getString(j,"acntUntNm");
  	            	            
	            strHtml += "			<tr>";
	            strHtml += "				<td height=\"30\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;\">"+balDivNm+"</p></td>";
	            strHtml += "				<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: left;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">&nbsp;"+budgOrgNm+"</p></td>";
	            strHtml += "				<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;\">"+bizNm+"</p></td>";
	            strHtml += "				<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;\">"+acntNm+"</p></td>";
	            strHtml += "				<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;\">"+acntUntNm+"</p></td>";
	            strHtml += "				<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;\">"+budgAmt+"</p></td>";
	            strHtml += "				<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+budgAmt1+"&nbsp;</p></td>";
	            strHtml += "				<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+budgAmtSum+"&nbsp;</p></td>";
	            strHtml += "			</tr>";
            }
            strHtml += "			<tr>";
            strHtml += "				<td colspan=\"4\" height=\"30\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;\">합&nbsp; 계</p></td>";
            strHtml += "				<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;\">수입신청합계</p></td>";
            strHtml += "				<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+budgAmt2+"&nbsp;</p></td>";
            strHtml += "				<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;\">지출신청합계</p></td>";
            strHtml += "				<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+budgAmt3+"&nbsp;</p></td>";
            strHtml += "			</tr>";
            strHtml += "	</table>";
            strHtml += " 	</body>";
            strHtml += "</html>";
            
            List<?> list1 = commonDAO.selectData("admiDAO.selectGwApprovalMstElecId", nMap4);

        	DataSet rtnDs1 = ConvertUtil.getListToDS(list1);

            nMap.put("id", rtnDs1.getString(0,"elecId"));
            nMap.put("formId",   map.get("formId"));
            nMap.put("title",    map.get("title"));
            nMap.put("subTitle", map.get("subTitle"));
            nMap.put("subjId",   map.get("subjId"));
            nMap.put("body", strHtml);
            nMap.put("approvalDiv", strApprovalDiv);
            nMap.put("approvalDt", strApprovalDt);
            nMap.put("approvalDocid", strApprovalDocid);
            nMap.put("userId", map.get("userId"));
            
            System.out.println(nMap);
            System.out.println("_________________________");
            
            nMap3.put("id", rtnDs1.getString(0,"elecId"));
            nMap3.put("formId",   map.get("formId"));
            
            nMap3.put("acntUnt", nMap1.get("acntUnt"));
            nMap3.put("acntYy", nMap1.get("acntYy"));
            nMap3.put("chgSeq", nMap1.get("chgSeq"));
            nMap3.put("aplyOrgId", nMap1.get("aplyOrgId"));
            
            //mssql 전자결재연동테이블에 인서트한다.
            msDAO.insert("admiDAO.insertGwApprovalMst", nMap);
            
            //oracle 전자결재연동테이블에 업데이트한다.
            commonDAO.update("admiDAO.updateBgChgAplyGwApprovalMstOra", nMap3);
        } 
        return nMap;
    }
	
	/*
	* 전자결재 예산변경 취소
	*/
	@Override
    public void saveBgChgApprovalCancel(DataSet ds) throws Exception {
        String rowType = "";    // ROW 상태
        
        for (int i = 0; i < ds.getRowCount(); i++) {
            HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
            
            String strDiv = commonDAO.selectString("admiDAO.selectGwApprovalMstApprovalDiv", hm);
            
            if ( strDiv.equals("1")) {
            	throw new BusinessException("이미 최종결재되었으므로 결재회수할 수 없습니다.");
            } else if ( strDiv.equals("2")) {
            	throw new BusinessException("이미 반려되었으므로 결재회수할 수 없습니다.");
            }
            
            commonDAO.update("admiDAO.updateBgChgAplyApprovalCancel", hm);
        }
	}
	
	//saveBgAcntChg
	@Override
    public HashMap saveSpAcPurcBudg() throws Exception{
    //    String rowType = "";    // ROW 상태
        HashMap nMap = null;
        //for (int i = 0; i < ds.getRowCount(); i++) {

           // rowType = ds.getString(i, "rowStatus");
            //HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
            
                Map map = new HashMap();

                //map.put("I_BF_ACNT_UNT", hm.get("bfAcntUnt"));  
                // TODO ENTRY_NO, ENTRY_IP 넣기
                nMap = commonDAO.saveDataSP("admiDAO.saveSpAcPurcBudg", map);
        //}
        
        return nMap;
    }
	
	@Override
    public HashMap saveSpAcPurcVouCreate(Map map) throws Exception{
    //    String rowType = "";    // ROW 상태
        HashMap nMap = null;
        //for (int i = 0; i < ds.getRowCount(); i++) {

            //rowType = ds.getString(i, "rowStatus");
            //HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
            
                Map map1 = new HashMap();

                map1.put("IN_ID", map.get("id"));
                map1.put("IN_WORK_DIV", map.get("workDiv"));
                map1.put("IN_ORDER_YN", map.get("orderYn"));
                map1.put("IN_USER_ID", map.get("userId"));
                map1.put("IN_IP", map.get("ip"));
                
                System.out.println("========================1========================");
                System.out.println(map1);
                System.out.println("========================1========================");
                
                // TODO ENTRY_NO, ENTRY_IP 넣기
                nMap = commonDAO.saveDataSP("admiDAO.saveSpAcPurcVouCreate", map1);
        //}
        
        return nMap;
    }
	
	@Override
    public HashMap saveSpAcPurcVouCancel(Map map) throws Exception{
    //    String rowType = "";    // ROW 상태
        HashMap nMap = null;
        //for (int i = 0; i < ds.getRowCount(); i++) {

            //rowType = ds.getString(i, "rowStatus");
            //HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
            
                Map map1 = new HashMap();

                map1.put("IN_LINK_ID", map.get("linkId"));
                map1.put("IN_WORK_DIV", map.get("workDiv"));
                
                System.out.println("========================1========================");
                System.out.println(map1);
                System.out.println("========================1========================");
                
                // TODO ENTRY_NO, ENTRY_IP 넣기
                nMap = commonDAO.saveDataSP("admiDAO.saveSpAcPurcVouCancel", map1);
        //}
        
        return nMap;
    }
	
	/*
   	* 예산변경관리(관리자용) 대량의 학과 및 학과를 조회
   	*/
   	@Override
   	public List<?> selectBgChgDeptAdddList(Map map) throws Exception{
   	    List<?> list = commonDAO.selectData("admiDAO.selectBgChgDeptAdddList", map);
   	    return list;
   	}
   	
   	/*
   	* 기안등록 수신처 부서 콤보 조회
   	*/
   	@Override
   	public List<?> selectAddrDeptCombo(Map map) throws Exception{
   	    List<?> list = commonDAO.selectData("admiDAO.selectAddrDeptCombo", map);
   	    return list;
   	}
   	
   	@Override
   	public List<?> selectPrivateAddrDeptCombo(Map map) throws Exception{
   	    List<?> list = commonDAO.selectData("admiDAO.selectPrivateAddrDeptCombo", map);
   	    return list;
   	}
   	
   	@Override
   	public List<?> selectDcDept(Map map) throws Exception{
   	    List<?> list = commonDAO.selectData("admiDAO.selectDcDept", map);
   	    return list;
   	}
   	
   	
   	@Override
    public HashMap saveSpAcPurcBudgDept(Map map) throws Exception{
    //    String rowType = "";    // ROW 상태
        HashMap nMap = null;
        //for (int i = 0; i < ds.getRowCount(); i++) {

            //rowType = ds.getString(i, "rowStatus");
            //HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
            
                Map map1 = new HashMap();

                map1.put("IN_DEPT_CD", map.get("deptCd"));
                
                System.out.println("========================1========================");
                System.out.println(map1);
                System.out.println("========================1========================");
                
                // TODO ENTRY_NO, ENTRY_IP 넣기
                nMap = commonDAO.saveDataSP("admiDAO.saveSpAcPurcBudgDept", map1);
        //}
        
        return nMap;
    }
   	
   	@Override
    public HashMap saveSpAcPurcBudgDept_recv(Map map) throws Exception{
    //    String rowType = "";    // ROW 상태
        HashMap nMap = null;
        //for (int i = 0; i < ds.getRowCount(); i++) {

            //rowType = ds.getString(i, "rowStatus");
            //HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
            
                Map map1 = new HashMap();

                map1.put("IN_DEPT_CD", map.get("vouOrgId"));
                
                System.out.println("========================1========================");
                System.out.println(map1);
                System.out.println("========================1========================");
                
                // TODO ENTRY_NO, ENTRY_IP 넣기
                nMap = commonDAO.saveDataSP("admiDAO.saveSpAcPurcBudgDept_recv", map1);
        //}
        
        return nMap;
    }
   	
   	@Override
    public HashMap saveSpAcTripVouCreate(Map map) throws Exception{
    //    String rowType = "";    // ROW 상태
        HashMap nMap = null;
        //for (int i = 0; i < ds.getRowCount(); i++) {

            //rowType = ds.getString(i, "rowStatus");
            //HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
            
                Map map1 = new HashMap();

                map1.put("IN_ID", map.get("id"));
                map1.put("IN_USER_ID", map.get("userId"));
                map1.put("IN_IP", map.get("ip"));
                
                System.out.println("========================1========================");
                System.out.println(map1);
                System.out.println("========================1========================");
                
                // TODO ENTRY_NO, ENTRY_IP 넣기
                nMap = commonDAO.saveDataSP("admiDAO.saveSpAcTripVouCreate", map1);
        //}
        
        return nMap;
    }
   	
   	public List<?> selectBg03050(Map map) throws Exception{
    	List<?> list = commonDAO.selectData("admiDAO.selectBg03050", map);
        return list;
    }
   	
    @Override
    public void saveBg3050(DataSet ds) throws Exception{
  	  String rowType = "";
  	    //편성예산 등록,수정,삭제한다.
  	    for(int i = 0; i < ds.getRowCount(); i++) {
  		    rowType = ds.getString(i, "rowStatus");
  		    HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
  		  
  			commonDAO.update("admiDAO.updateBg3050", hm);
  	    }  	    
    }
   	
   	public List<?> selectBg04020(Map map) throws Exception{
    	List<?> list = commonDAO.selectData("admiDAO.selectBg04020", map);
        return list;
    }
   	
   	public List<?> selectBg04020Tab0(Map map) throws Exception{
    	List<?> list = commonDAO.selectData("admiDAO.selectBg04020Tab0", map);
        return list;
    }
   	
   	public List<?> selectBg04020Tab1(Map map) throws Exception{
    	List<?> list = commonDAO.selectData("admiDAO.selectBg04020Tab1", map);
        return list;
    }
   	
   	public List<?> selectBg04020Tab2(Map map) throws Exception{
    	List<?> list = commonDAO.selectData("admiDAO.selectBg04020Tab2", map);
        return list;
    }
   	
   	public List<?> selectBg04030(Map map) throws Exception{
   		Map map1 = new HashMap();
   		commonDAO.saveDataSP("admiDAO.saveSpAcPurcBudg", map1);
   		
    	List<?> list = commonDAO.selectData("admiDAO.selectBg04030", map);
        return list;
    }
   	
   	public List<?> selectBg04030Tab0(Map map) throws Exception{
    	List<?> list = commonDAO.selectData("admiDAO.selectBg04030Tab0", map);
        return list;
    }
   	
   	public List<?> selectBg04030Tab1(Map map) throws Exception{
    	List<?> list = commonDAO.selectData("admiDAO.selectBg04030Tab1", map);
        return list;
    }
   	
   	public List<?> selectBg04040(Map map) throws Exception{
    	List<?> list = commonDAO.selectData("admiDAO.selectBg04040", map);
        return list;
    }
   	
   	public List<?> selectBg04050(Map map) throws Exception{
    	List<?> list = commonDAO.selectData("admiDAO.selectBg04050", map);
        return list;
    }
   	
   	public List<?> selectBg04050Tab0(Map map) throws Exception{
    	List<?> list = commonDAO.selectData("admiDAO.selectBg04050Tab0", map);
        return list;
    }
   	
   	public List<?> selectBg04050Tab1(Map map) throws Exception{
    	List<?> list = commonDAO.selectData("admiDAO.selectBg04050Tab1", map);
        return list;
    }
   	
   	public List<?> selectBg04050Tab2(Map map) throws Exception{
    	List<?> list = commonDAO.selectData("admiDAO.selectBg04050Tab2", map);
        return list;
    }
   	
   	/*
	 * 부서별예산관리부서등록 조회
	 */
    @Override
    public List<?> selectBgAcntDeptList(Map map) throws Exception{
        List<?> list = commonDAO.selectData("admiDAO.selectBgAcntDeptList", map);
        return list;
    }
    
    /*
     * 부서별예산관리부서등록 저장
     */
     
     @Override
     public void saveBgAcntDeptList(DataSet ds) throws Exception{
         String rowType = "";    // ROW 상태
     
         for (int i = 0; i < ds.getRowCount(); i++) {

             rowType = ds.getString(i, "rowStatus");
             HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
             
             if("insert".equals(rowType)){
                 commonDAO.insert("admiDAO.insertBgAcntDeptList", hm);
             }else if("update".equals(rowType)){
                 commonDAO.update("admiDAO.updateBgBgAcntDeptList", hm);
             }else if("delete".equals(rowType)){
                 commonDAO.delete("admiDAO.deleteBgAcntDeptList", hm);
             }
         }
     }
     
     /*
      * 편성예산관리 선택된 예산구분을 다음 예산변경으로 복사해준다.
      */
      
      @Override
      public void saveBgBudgCdCopy(Map map) throws Exception{
    	  commonDAO.insert("admiDAO.insertBgBudgCdCopy", map);
      }
               
      @Override
      public HashMap saveBgBudgCreate(Map map) throws Exception{  		
  		HashMap nMap = new HashMap();
          nMap = commonDAO.saveDataSP("admiDAO.SP_BG_CREATE", map);
          return nMap;
      }
      
      @Override
      public List<?> selectBudgChgAplyCnt(Map map) throws Exception{
          List<?> list = commonDAO.selectData("admiDAO.selectBudgChgAplyCnt", map);
          return list;
      }
      
}
