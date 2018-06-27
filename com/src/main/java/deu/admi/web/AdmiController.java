package deu.admi.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import deu.admi.service.AdmiService;
import deu.comm.login.web.SessionManager;
import deu.comm.unione.uniRequest;
import deu.comm.unione.uniResponse;

import com.nexacro.xapi.data.DataSet;

@Controller
public class AdmiController {

	/** AdmiService */
	@Resource(name = "admiService")
	private AdmiService admiService; 

	/*****************                   admiCommon                   ******************/
	
	/*
	 * 회계연도 조회한다.
	 */
    @RequestMapping(value = "admi/selectAcntYy.do")
    public void  selectAcntYy(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = admiService.selectAcntYy(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
	
	/*
	 * 회계단위 콤보를 조회한다
	 */
    @RequestMapping(value = "admi/selectAcntUntCmb.do")
    public void  selectAcntUntCmb(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = admiService.selectAcntUntCmb(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /**
     * 회계단위등록(상세정보탭) 을(를) 조회한다.
     */
    @RequestMapping(value = "admi/selectAcntUntList.do")
    public void  selectAcntUntList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);
            
            Map map = uniReq.getVariable();
            
            List<?> list = admiService.selectAcntUntList(map);
            uniRes.setDataSet(list, "resultList");
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 회계단위등록(상세정보탭) 을(를) 저장한다.
     */
    @RequestMapping(value = "admi/saveAcntUntList.do")
    public void  saveAcntUntList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);
            
            DataSet ds = uniReq.getDS("resultList");
    
            admiService.saveAcntUntList(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 회계단위등록(권한탭) 을(를) 조회한다.
     */
    @RequestMapping(value = "admi/selectAcntPermList.do")
    public void  selectAcntPermList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);
            
            Map map = uniReq.getVariable();
            
            List<?> list = admiService.selectAcntPermList(map);
            uniRes.setDataSet(list, "resultList");
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 회계단위등록(권한탭) 을(를) 저장한다.
     */
    @RequestMapping(value = "admi/saveAcntPermList.do")
    public void  saveAcntPermList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);
            
            DataSet ds = uniReq.getDS("resultList");
    
            admiService.saveAcntPermList(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 회계단위등록(합산회계탭) 을(를) 조회한다.
     */
    @RequestMapping(value = "admi/selectAcntAddList.do")
    public void  selectAcntAddList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);
            
            Map map = uniReq.getVariable();
            
            List<?> list = admiService.selectAcntAddList(map);
            uniRes.setDataSet(list, "resultList");
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 회계단위등록(합산회계탭) 을(를) 저장한다.
     */
    @RequestMapping(value = "admi/saveAcntAddList.do")
    public void  saveAcntAddList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);
            
            DataSet ds = uniReq.getDS("resultList");
    
            admiService.saveAcntAddList(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    

    /*#########################################################################################################################################*/
    /* 계정코드등록(bg01020)                                                                                                                       */
    /*#########################################################################################################################################*/
        
    
    /**
     * 계정내역을(를) 조회한다.
     * @param acntUnt 회계단위
     * @param acntYy 회계년도
     */
    @RequestMapping(value = "admi/selectAcntList.do")
    public void  selectAcntList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = admiService.selectAcntList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    } 
    
    /**
     * 세세목내역을(를) 조회한다.
     * @param acntUnt 회계단위
     * @param acntYy 회계년도
     * @param acntCd 계정과목코드
     */
    @RequestMapping(value = "admi/selectItmSeqList.do")
    public void  selectItmSeqList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = admiService.selectItmSeqList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
	
	/**
     * 계정내역을(를) 저장한다.
     * @param resultList 계정 목록정보
	 */
	@RequestMapping(value = "admi/saveAcntList.do")
	public void  saveAcntList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			DataSet ds01 = uniReq.getDS("in_acntList");
			DataSet ds02 = uniReq.getDS("in_acntSubList");

			admiService.saveAcntList(ds01, ds02);
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/**
     * 전년도 예산을 복사한다
     * @param resultList 계정 목록정보
	 */
	@RequestMapping(value = "admi/saveAcntCopyList.do")
	public void  saveAcntCopyList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			DataSet ds = uniReq.getDS("resultList");

			admiService.saveAcntCopyList(ds);
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
	/************************************************************
	 * 재원등록(admi040)
	 ************************************************************/
	
	/*
	 * 재원등록내역을 조회한다.
	 */
    @RequestMapping(value = "admi/selectBgFinc.do")
    public void  selectBgFinc(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = admiService.selectBgFinc(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /*
     * 재원등록내역 저장
     */
    
    @RequestMapping(value = "admi/saveBgFinc.do")
    public void  saveBgFinc(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);
            
            DataSet ds = uniReq.getDS("resultList");
    
            admiService.saveBgFinc(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    
    /************************************************************
	 * 예산일정등록(bg02010)
	 ************************************************************/
    
    /*
	 * 예산일정을 조회한다.
	 */
    @RequestMapping(value = "admi/selectBgSched.do")
    public void  selectBgSched(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = admiService.selectBgSched(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /*
     * 예산일정을 저장한다.
     */
    @RequestMapping(value = "admi/saveBgSched.do")
    public void  saveBgSched(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);
            
            DataSet ds = uniReq.getDS("resultList");
    
            admiService.saveBgSched(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }


    
    /*
	 * 사업등록내역을 조회한다
	 */
    @RequestMapping(value = "admi/selectBgBiz.do")
    public void  selectBgBiz(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try {
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			
			List<?> list = admiService.selectBgBiz(map);
			
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    
    /*
	 * 사업등록내역을 저장한다.
	 */
    @RequestMapping(value = "admi/saveBgBiz.do")
    public void  saveBgBiz(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);
            
            DataSet ds = uniReq.getDS("resultList");
    
            admiService.saveBgBiz(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    
    /*
   	 * 예산편성관련통계 내역을 조회한다
   	 */
    @RequestMapping(value = "admi/selectbgFrmn.do")
    public void  selectbgFrmn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = admiService.selectbgFrmn(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    /*
    *관리부서 내역을 조회한다
  	 */
      @RequestMapping(value = "admi/selectBgOrgPermMain.do")
      public void  selectBgOrgPermMain(HttpServletRequest request, HttpServletResponse response) throws Exception {
          uniRequest uniReq = new uniRequest();
          uniResponse uniRes = new uniResponse();
      
          uniReq.receivePlatformData(request);
          
          Map map = uniReq.getVariable();
          
          List<?> list = admiService.selectBgOrgPermMain(map);
          uniRes.setDataSet(list, "resultList");
          
          uniRes.sendPlatformData(response);
      }
      

      
    /*
	 *관리부서 상세내역을 조회한다
	 */
    @RequestMapping(value = "admi/selectBgOrgPerm.do")
    public void  selectBgOrgPerm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = admiService.selectBgOrgPerm(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    /*
  	 * 관리부서 상세내역을 저장한다.
  	 */
      @RequestMapping(value = "admi/saveBgOrgPerm.do")
      public void  saveBgOrgPerm(HttpServletRequest request, HttpServletResponse response) throws Exception {
          uniRequest uniReq = new uniRequest();
          uniResponse uniRes = new uniResponse();

          try {
              uniReq.receivePlatformData(request);
              DataSet ds = uniReq.getDS("resultList");
  			      
              admiService.saveBgOrgPerm(ds);
              
              uniRes.sendPlatformData(response);
          } catch (Exception e) {
              uniRes.sendException(response, e);
          }
      }
    
      /************************************************************
       * 예산편성신청현황(bg02060)
       ************************************************************/
      @RequestMapping(value = "admi/selectBgOrgBizSta.do")
      public void  selectBgOrgBizSta(HttpServletRequest request, HttpServletResponse response) throws Exception {
          uniRequest uniReq = new uniRequest();
          uniResponse uniRes = new uniResponse();
      
          uniReq.receivePlatformData(request);
          
          Map map = uniReq.getVariable();
          
          List<?> list = admiService.selectBgOrgBizSta(map);
          uniRes.setDataSet(list, "resultList");
          
          uniRes.sendPlatformData(response);
      }
      
    /************************************************************
     * 부서별사업등록(bg02020)
     ************************************************************/
    /*
	 *부서별사업등록 상세내역을 조회한다
	 */
    @RequestMapping(value = "admi/selectBgOrgBiz.do")
    public void  selectBgOrgBiz(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = admiService.selectBgOrgBiz(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /*
     * 부서별사업등록 저장
     */
    
    @RequestMapping(value = "admi/saveBgOrgBiz.do")
    public void  saveBgOrgBiz(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);
            
            DataSet ds = uniReq.getDS("resultList");
    
            admiService.saveBgOrgBiz(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /************************************************************
     * 예산편성신청등록(bg02030)
     ************************************************************/
    /*
	 *예산편성신청등록 조회
	 */
    @RequestMapping(value = "admi/selectBgFrmnApplyList.do")
    public void  selectBgFrmnApplyList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try {
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectBgFrmnApplyList(map);
	        uniRes.setDataSet(list, "resultList");
	        
	        uniRes.sendPlatformData(response);
        } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    /*
	 *예산편성관리등록 조회
	 */
    @RequestMapping(value = "admi/selectBgFrmnApplyFixedList.do")
    public void  selectBgFrmnApplyFixedList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try {
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectBgFrmnApplyFixedList(map);
	        uniRes.setDataSet(list, "resultList");
	        
//	        list = admiService.selectBgFrmnFixedExcelTotal(map);
//	        uniRes.setDataSet(list, "resultList1");
//	        
//	        list = admiService.selectBgFrmnFixedExcelAcntUnt(map);
//	        uniRes.setDataSet(list, "resultList2");
//	        
//	        list = admiService.selectBgFrmnFixedExcelAcntCd(map);
//	        uniRes.setDataSet(list, "resultList3");
//	        
//	        list = admiService.selectBgFrmnFixedExcelBizCd(map);
//	        uniRes.setDataSet(list, "resultList4");
//	        
//	        list = admiService.selectBgFrmnFixedExcelBudgOrgId(map);
//	        uniRes.setDataSet(list, "resultList5");
	        
	        uniRes.sendPlatformData(response);
        } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    /*
	 *예산편성신청등록 조회 엑셀용
	 */
    @RequestMapping(value = "admi/selectBgFrmnApplyExcelList.do")
    public void  selectBgFrmnApplyExcelList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try {
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectBgFrmnApplyExcelList(map);
	        uniRes.setDataSet(list, "resultList");
	        
	        uniRes.sendPlatformData(response);
        } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    /*
	 *예산일정에 포함된 오늘기준 회계연도,회계단위,예산구분을 조회한다.
	 */
    @RequestMapping(value = "admi/selectBgFrmnSched.do")
    public void  selectBgFrmnSched(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try {
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectBgFrmnSched(map);
	        uniRes.setDataSet(list, "resultList");
	        
	        uniRes.sendPlatformData(response);
        } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    /*
	 *예산일정에 포함된 오늘기준 회계연도,회계단위,예산구분을 조회한다. 관리자용
	 */
    @RequestMapping(value = "admi/selectBgFrmnSchedFixed.do")
    public void  selectBgFrmnSchedFixed(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try {
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectBgFrmnSchedFixed(map);
	        uniRes.setDataSet(list, "resultList");
	        
	        uniRes.sendPlatformData(response);
        } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    /*
	 *선택된 예산회계,연도,구분이 예산일정에 포함되어있는지 조회한다.
	 */
    @RequestMapping(value = "admi/selectBgFrmnSchedCheck.do")
    public void  selectBgFrmnSchedCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try{
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectBgFrmnSchedCheck(map);
	        uniRes.setDataSet(list, "resultList");
	        
	        uniRes.sendPlatformData(response);
        
	    } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    /**
	 *편성예산조회한다.
	 */
    @RequestMapping(value = "admi/selectBgFrmnlist.do")
    public void  selectBgFrmnlist(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try{
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectBgFrmnlist(map);
	        uniRes.setDataSet(list, "resultList");
	        
//	        list = admiService.selectBgFrmnDetlist(map);
//	        uniRes.setDataSet(list, "resultList2");
//	        
//	        list = admiService.selectBgFrmnSchedList(map);
//	        uniRes.setDataSet(list, "resultList3");
	        
	        uniRes.sendPlatformData(response);
	    } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    /**
	 *변경예산(관리자용)조회한다.
	 */
    @RequestMapping(value = "admi/selectBgChgAdminList.do")
    public void  selectBgChgAdminList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try{
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectBgChgAdminList(map);
	        uniRes.setDataSet(list, "resultList");

	        uniRes.sendPlatformData(response);
	    } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    /**
	 *변경예산조회한다.
	 */
    @RequestMapping(value = "admi/selectBgChgList.do")
    public void  selectBgChgList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try{
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectBgChgList(map);
	        uniRes.setDataSet(list, "resultList");

	        uniRes.sendPlatformData(response);
	    } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    /**
     * 편성예산 등록,수정,삭제한다.
     */
    @RequestMapping(value = "admi/saveBgFrmnlist.do")
    public void  saveBgFrmnlist(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds1 = uniReq.getDS("resultList1");
            
            admiService.saveBgFrmnlist(ds1);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 변경예산 등록,수정,삭제한다.
     */
    @RequestMapping(value = "admi/saveBgChgList.do")
    public void  saveBgChgList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds1 = uniReq.getDS("resultList1");
            DataSet ds2 = uniReq.getDS("resultList2");
            DataSet ds3 = uniReq.getDS("resultList3");
            
            admiService.saveBgChgList(ds1, ds2, ds3);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 변경예산 등록,수정,삭제한다.
     */
    @RequestMapping(value = "admi/saveBgChgNewList.do")
    public void  saveBgChgNewList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds1 = uniReq.getDS("resultList1");
            
            admiService.saveBgChgNewList(ds1);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /*
	 *편성예산상세조회한다.
	 */
    @RequestMapping(value = "admi/selectBgFrmnDetlist.do")
    public void  selectBgFrmnDetlist(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try{
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectBgFrmnDetlist(map);
	        uniRes.setDataSet(list, "resultList");
	        
	        uniRes.sendPlatformData(response);
	    } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    /*
	 *변경예산상세조회한다.
	 */
    @RequestMapping(value = "admi/selectBgChgDetList.do")
    public void  selectBgChgDetList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try{
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectBgChgDetList(map);
	        uniRes.setDataSet(list, "resultList");
	        
	        uniRes.sendPlatformData(response);
	    } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    /**
     * 편성예산상세 등록,수정,삭제한다.
     */
    @RequestMapping(value = "admi/saveBgFrmnDetlist.do")
    public void  saveBgFrmnDetlist(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds1 = uniReq.getDS("resultList1");
            
            admiService.saveBgFrmnDetlist(ds1);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 변경예산상세 등록,수정,삭제한다.
     */
    @RequestMapping(value = "admi/saveBgChgDetList.do")
    public void  saveBgChgDetList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds1 = uniReq.getDS("resultList1");
            DataSet ds2 = uniReq.getDS("resultList2");
            DataSet ds3 = uniReq.getDS("resultList3");
            DataSet ds4 = uniReq.getDS("resultList4");
            
            admiService.saveBgChgDetList(ds1,ds2,ds3,ds4);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /*
	 *편성예산계획조회한다.
	 */
    @RequestMapping(value = "admi/selectBgFrmnSchedList.do")
    public void  selectBgFrmnSchedList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try{
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectBgFrmnSchedList(map);
	        uniRes.setDataSet(list, "resultList");
	        
	        uniRes.sendPlatformData(response);
	    } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    /*
	 *변경예산계획조회한다.
	 */
    @RequestMapping(value = "admi/selectBgChgSchedList.do")
    public void  selectBgChgSchedList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try{
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectBgChgSchedList(map);
	        uniRes.setDataSet(list, "resultList");
	        
	        uniRes.sendPlatformData(response);
	    } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    /**
     * 편성예산계획 등록,수정,삭제한다.
     */
    @RequestMapping(value = "admi/saveBgFrmnSchedlist.do")
    public void  saveBgFrmnSchedlist(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            admiService.saveBgFrmnSchedlist(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 변경예산계획 등록,수정,삭제한다.
     */
    @RequestMapping(value = "admi/saveBgChgSchedList.do")
    public void  saveBgChgSchedList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            admiService.saveBgChgSchedList(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 편성예산 계정과목코드 일괄 업데이트
     */
    @RequestMapping(value = "admi/saveBgFrmnChglist.do")
    public void  saveBgFrmnChglist(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            admiService.saveBgFrmnChglist(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 변경예산 계정과목코드 일괄 업데이트
     */
    @RequestMapping(value = "admi/saveBgChgChgList.do")
    public void  saveBgChgChgList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            admiService.saveBgChgChgList(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 편성예산 일괄 삭제
     */
    @RequestMapping(value = "admi/saveBgFrmnDelList.do")
    public void  saveBgFrmnDelList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds1 = uniReq.getDS("resultList");
            
            admiService.saveBgFrmnDelList(ds1);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 변경예산 일괄 삭제
     */
    @RequestMapping(value = "admi/saveBgChgDelList.do")
    public void  saveBgChgDelList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds1 = uniReq.getDS("resultList");
            
            admiService.saveBgChgDelList(ds1);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 변경예산 일괄 삭제 신규
     */
    @RequestMapping(value = "admi/saveBgChgDelNewList.do")
    public void  saveBgChgDelNewList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds1 = uniReq.getDS("resultList");
            
            admiService.saveBgChgDelNewList(ds1);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 편성예산 계정과목코드 일괄 삭제
     */
    @RequestMapping(value = "admi/saveBgFrmnDetDelList.do")
    public void  saveBgFrmnDetDelList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds1 = uniReq.getDS("resultList1");
            DataSet ds2 = uniReq.getDS("resultList2");
            DataSet ds3 = uniReq.getDS("resultList3");
            
            admiService.saveBgFrmnDetDelList(ds1, ds2, ds3);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /*
	 * 세세목콤보조회호출
	 */
    @RequestMapping(value = "admi/selectBgItmSeqCombo.do")
    public void  selectBgItmSeqCombo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try{
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectBgItmSeqCombo(map);
	        uniRes.setDataSet(list, "resultList");
	        
	        uniRes.sendPlatformData(response);
	    } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    /*
	 * 예산신청내역 계정기본,상세,월별 신청액의 합계여부확인
	 */
    @RequestMapping(value = "admi/selectBgApplyChk.do")
    public void  selectBgApplyChk(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try{
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectBgApplyChk(map);
	        uniRes.setDataSet(list, "resultList");
	        
	        uniRes.sendPlatformData(response);
	    } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    /*
	 * 예산변경신청내역 계정기본,상세,월별 신청액의 합계여부확인
	 */
    @RequestMapping(value = "admi/selectBgChgApplyChk.do")
    public void  selectBgChgApplyChk(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try{
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectBgChgApplyChk(map);
	        uniRes.setDataSet(list, "resultList");
	        
	        uniRes.sendPlatformData(response);
	    } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    /**
     * 예산계정편성신청 프로그램
     * 신청버튼 클릭시 예산편성기본+예산편성상세+예산편성계획 3개테이블의 예산액이 맞는지 체크,
     * 예산계정기본 테이블에 진행상태를 신청으로 변경하여 저장한다
     */
    @RequestMapping(value = "admi/saveBgStaCd.do")
    public void  saveBgStaCd(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            admiService.saveBgStaCd(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 예산계정변경신청 프로그램
     * 예산변경신청 테이블에 진행상태를 신청으로 변경하여 저장한다
     */
    @RequestMapping(value = "admi/saveBgChgStaCd.do")
    public void  saveBgChgStaCd(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            admiService.saveBgChgStaCd(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 월별편성계획 초기화(delete 후 insert)
     */
    @RequestMapping(value = "admi/saveBgFrmnSchedRemove.do")
    public void  saveBgFrmnSchedRemove(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds1 = uniReq.getDS("resultList1");
            DataSet ds2 = uniReq.getDS("resultList2");
            
            admiService.saveBgFrmnSchedRemove(ds1,ds2);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 월별변경계획 초기화(delete 후 insert)
     */
    @RequestMapping(value = "admi/saveBgChgSchedRemove.do")
    public void  saveBgChgSchedRemove(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds1 = uniReq.getDS("resultList1");
            DataSet ds2 = uniReq.getDS("resultList2");
            
            admiService.saveBgChgSchedRemove(ds1,ds2);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 예산계정편성관리 프로그램
     * 예산계정기본,상세,계획을(를)전차수복사(조정차수0,1,2,3) 증가시켜 저장한다.
     */
    @RequestMapping(value = "admi/saveBgAdjOrdCopy.do")
    public void  saveBgAdjOrdCopy(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);
            
            Map map = uniReq.getVariable();
    
            admiService.saveBgAdjOrdCopy(map);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 변경예산계정편성관리 프로그램
     * 변경예산계정기본(를)전차수복사(조정차수101,201,202,203) 증가시켜 저장한다.
     */
    @RequestMapping(value = "admi/saveBgChgAdjOrdCopy.do")
    public void  saveBgChgAdjOrdCopy(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);
            
            Map map = uniReq.getVariable();
    
            admiService.saveBgChgAdjOrdCopy(map);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 예산계정편성확정 프로그램
     * 신청버튼 클릭시 예산계정기본 테이블에 진행상태를 확정으로 변경하여 저장한다
     */
    @RequestMapping(value = "admi/saveBgStaCdFixed.do")
    public void  saveBgStaCdFixed(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            admiService.saveBgStaCdFixed(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 예산계정변경확정 프로그램
     * 확정버튼 클릭시 예산변경기본 테이블에 진행상태를 확정으로 변경하여 저장한다
     */
    @RequestMapping(value = "admi/saveBgChgStaCdFixed.do")
    public void  saveBgChgStaCdFixed(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            admiService.saveBgChgStaCdFixed(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 계정내역을 변경한다(회계년도,부서코드,사업코드,계정코드)
     * @throws Exception 
     */
    @RequestMapping(value = "admi/saveBgAcntChg.do")
    public void  saveBgAcntChg(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
    
            HashMap nMap = admiService.saveBgAcntChg(ds);

            uniRes.setVariables(nMap);
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /*
	 * 접속자의 소속부서및 관리부서 콤보호출
	 */
    @RequestMapping(value = "admi/selectBudgOrgIdCombo.do")
    public void  selectBudgOrgIdCombo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try{
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectBudgOrgIdCombo(map);
	        uniRes.setDataSet(list, "resultList");
	        
	        uniRes.sendPlatformData(response);
	    } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    /*
	 * 접속자의 사업 콤보호출
	 */
    @RequestMapping(value = "admi/selectBudgBizCombo.do")
    public void  selectBudgBizCombo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try{
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectBudgBizCombo(map);
	        uniRes.setDataSet(list, "resultList");
	        
	        uniRes.sendPlatformData(response);
	    } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    /*
	 * 예산편성신청 - 작성중인 사업(예산입력했지만 신청전) 확인
	 */
    @RequestMapping(value = "admi/selectBgApplyBizChk.do")
    public void  selectBgApplyBizChk(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try{
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectBgApplyBizChk(map);
	        uniRes.setDataSet(list, "resultList");
	        
	        uniRes.sendPlatformData(response);
	    } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    /*
	 * 예산신청중 입력되지않은 계정과목 확인
	 */
    @RequestMapping(value = "admi/selectBgApplyAcntChk.do")
    public void  selectBgApplyAcntChk(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try{
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectBgApplyAcntChk(map);
	        uniRes.setDataSet(list, "resultList");
	        
	        uniRes.sendPlatformData(response);
	    } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    /*
	 * 예산변경신청중 입력되지않은 계정과목 확인
	 */
    @RequestMapping(value = "admi/selectBgChgApplyAcntChk.do")
    public void  selectBgChgApplyAcntChk(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try{
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectBgChgApplyAcntChk(map);
	        uniRes.setDataSet(list, "resultList");
	        
	        uniRes.sendPlatformData(response);
	    } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    /**
     * 예산편성관리 사업반려저장
     */
    @RequestMapping(value = "admi/saveBgStaCdReca.do")
    public void  saveBgStaCdReca(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            admiService.saveBgStaCdReca(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 예산변경관리 사업반려저장
     */
    @RequestMapping(value = "admi/saveBgChgStaCdReca.do")
    public void  saveBgChgStaCdReca(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            admiService.saveBgChgStaCdReca(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /*************************************************************************
	 * 엑셀다운로드 예산편성관리 - 예산데이터를 전체를 엑셀로 내려받기위한 조회
	 *************************************************************************/
    @RequestMapping(value = "admi/selectBgFrmnFixedExcelTotal.do")
    public void  selectBgFrmnFixedExcelTotal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try{
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectBgFrmnFixedExcelTotal(map);
	        uniRes.setDataSet(list, "resultList");
	        
	        uniRes.sendPlatformData(response);
	    } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    /*************************************************************************
  	 * 엑셀다운로드 예산편성관리 - 예산데이터를 회계단위별로 엑셀로 내려받기위한 조회
  	 *************************************************************************/
    @RequestMapping(value = "admi/selectBgFrmnFixedExcelAcntUnt.do")
    public void  selectBgFrmnFixedExcelAcntUnt(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try{
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectBgFrmnFixedExcelAcntUnt(map);
	        uniRes.setDataSet(list, "resultList");
	        
	        uniRes.sendPlatformData(response);
	    } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    /*************************************************************************
     * 엑셀다운로드 예산편성관리 - 예산데이터를 계정과목별로 엑셀로 내려받기위한 조회
     *************************************************************************/
    @RequestMapping(value = "admi/selectBgFrmnFixedExcelAcntCd.do")
    public void  selectBgFrmnFixedExcelAcntCd(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try{
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectBgFrmnFixedExcelAcntCd(map);
	        uniRes.setDataSet(list, "resultList");
	        
	        uniRes.sendPlatformData(response);
	    } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    /*************************************************************************
     * 엑셀다운로드 예산편성관리 - 예산데이터를 사업별로 엑셀로 내려받기위한 조회
     *************************************************************************/
    @RequestMapping(value = "admi/selectBgFrmnFixedExcelBizCd.do")
    public void  selectBgFrmnFixedExcelBizCd(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try{
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectBgFrmnFixedExcelBizCd(map);
	        uniRes.setDataSet(list, "resultList");
	        
	        uniRes.sendPlatformData(response);
	    } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    /*************************************************************************
     * 엑셀다운로드 예산편성관리 - 예산데이터를 부서별로 엑셀로 내려받기위한 조회
     *************************************************************************/
    @RequestMapping(value = "admi/selectBgFrmnFixedExcelBudgOrgId.do")
    public void  selectBgFrmnFixedExcelBudgOrgId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try{
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectBgFrmnFixedExcelBudgOrgId(map);
	        uniRes.setDataSet(list, "resultList");
	        
	        uniRes.sendPlatformData(response);
	    } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    /*************************************************************************
     * 엑셀다운로드 예산편성관리 - 예산데이터를 추경사유를 엑셀로 내려받기위한 조회
     *************************************************************************/
    @RequestMapping(value = "admi/selectBgFrmnFixedExcelBudgCd.do")
    public void  selectBgFrmnFixedExcelBudgCd(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try{
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectBgFrmnFixedExcelBudgCd(map);
	        uniRes.setDataSet(list, "resultList");
	        
	        uniRes.sendPlatformData(response);
	    } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    /*
	 *변경예산계획조회한다.
	 */
    @RequestMapping(value = "admi/selectBgChgAplyList.do")
    public void  selectBgChgAplyList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = admiService.selectBgChgAplyList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /*
	 *변경예산상세계획조회한다.
	 */
    @RequestMapping(value = "admi/selectBgChgAplyDetList.do")
    public void  selectBgChgAplyDetList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = admiService.selectBgChgAplyDetList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /*
	 *변경예산계획확정조회한다.
	 */
    @RequestMapping(value = "admi/selectBgChgAplyFixedList.do")
    public void  selectBgChgAplyFixedList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = admiService.selectBgChgAplyFixedList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    @RequestMapping(value = "admi/selectBgChgAplyFixedListManager.do")
    public void  selectBgChgAplyFixedListManager(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = admiService.selectBgChgAplyFixedListManager(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /*
	 *변경예산상세계획확정조회한다.
	 */
    @RequestMapping(value = "admi/selectBgChgAplyDetFixedList.do")
    public void  selectBgChgAplyDetFixedList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = admiService.selectBgChgAplyDetFixedList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /*
     * 변경예산계획 등록,수정,삭제한다.
     */
    
    @RequestMapping(value = "admi/saveBgChgAplyList.do")
    public void  saveBgChgAplyList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);
            
            DataSet ds = uniReq.getDS("resultList");
    
            admiService.saveBgChgAplyList(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    @RequestMapping(value = "admi/copyBgChgAplyList.do")
    public void  copyBgChgAplyList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);
            
            DataSet ds = uniReq.getDS("resultList");
    
            admiService.copyBgChgAplyList(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /*
	 * 로그아웃
	 */
    @RequestMapping(value = "admi/selectDoGetLogout.do")
    public void  selectDoGetLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HttpSession session = request.getSession();
		SessionManager ssmanager = SessionManager.getInstance();
    	
    	uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        try{
			
			Object obj = request.getSession().getAttribute("loginUser");
			if(obj != null) {
				if(session != null){
					session.removeAttribute("loginUser");
				}
        
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectDoGetLogout(map);
	        uniRes.setDataSet(list, "resultList");
	        
	        uniRes.sendPlatformData(response);
			}
		} catch (Exception e){
			uniRes.sendException(response, e);
		}
      
  }
    /** 전자결재 예산변경(전용) **/
    @RequestMapping(value = "admi/saveBgChgDiv10Approval.do")
    public void saveBgChgDiv10Approval(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");

        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
    
            HashMap nMap = admiService.saveBgChgDiv10Approval(ds);
            
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

            uniRes.setDataSet(tMap, "ds_elec");
            
            uniRes.sendPlatformData(response);
            
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }

    }  
    
    /** 전자결재 예산변경(추가) **/
    @RequestMapping(value = "admi/saveBgChgDiv20Approval.do")
    public void saveBgChgDiv20Approval(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");

        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
    
            HashMap nMap = admiService.saveBgChgDiv20Approval(ds);
            
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

            uniRes.setDataSet(tMap, "ds_elec");
            
            uniRes.sendPlatformData(response);
            
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }

    }
    
    /** 전자결재 예산변경 취소 **/
    @RequestMapping(value = "admi/saveBgChgApprovalCancel.do")
    public void saveBgChgApprovalCancel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");

        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
    
            admiService.saveBgChgApprovalCancel(ds);
           
            uniRes.sendPlatformData(response);
            
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }

    }  
    
    /**
     * 구매예산통제 프로시져 호출 
     * @throws Exception 
     */
    @RequestMapping(value = "admi/saveSpAcPurcBudg.do")
    public void  saveSpAcPurcBudg(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            //DataSet ds = uniReq.getDS("resultList");
    
            //HashMap nMap = admiService.saveSpAcPurcBudg(ds);
            HashMap nMap = admiService.saveSpAcPurcBudg();
            
            uniRes.setVariables(nMap);
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 구매예산통제 프로시져 호출 
     * @throws Exception 
     */
    @RequestMapping(value = "admi/saveSpAcPurcVouCreate.do")
    public void  saveSpAcPurcVouCreate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            //DataSet ds = uniReq.getDS("resultList");
    
            HashMap nMap1  = new HashMap();
            nMap1.put("id", request.getParameter("id"));
            nMap1.put("workDiv", request.getParameter("workDiv"));
            nMap1.put("orderYn", request.getParameter("orderYn"));
            nMap1.put("userId", request.getParameter("userId"));
            nMap1.put("ip", request.getParameter("ip"));
            
            System.out.println("========================2========================");
            System.out.println(request.getParameter("id"));
            System.out.println(request.getParameter("workDiv"));
            System.out.println(request.getParameter("orderYn"));
            System.out.println(request.getParameter("userId"));
            System.out.println(request.getParameter("ip"));
            System.out.println("========================2========================");
            
			//HashMap nMap = admiService.saveSpAcPurcBudg(ds);
            HashMap nMap = admiService.saveSpAcPurcVouCreate(nMap1);
            
            uniRes.setVariables(nMap);
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 구매예산통제 프로시져 호출 
     * @throws Exception 
     */
    @RequestMapping(value = "admi/saveSpAcPurcVouCancel.do")
    public void  saveSpAcPurcVouCancel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            //DataSet ds = uniReq.getDS("resultList");
    
            HashMap nMap1  = new HashMap();
            nMap1.put("linkId", request.getParameter("linkId"));
            nMap1.put("workDiv", request.getParameter("workDiv"));
            
            System.out.println("========================2========================");
            System.out.println(request.getParameter("linkId"));
            System.out.println(request.getParameter("workDiv"));
            System.out.println("========================2========================");
            
			//HashMap nMap = admiService.saveSpAcPurcBudg(ds);
            HashMap nMap = admiService.saveSpAcPurcVouCancel(nMap1);
            
            uniRes.setVariables(nMap);
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /*
	 * 예산변경관리(관리자용) 대량의 학과 및 학과를 조회
	 */
    @RequestMapping(value = "admi/selectBgChgDeptAdddList.do")
    public void  selectBgChgDeptAdddList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try{
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectBgChgDeptAdddList(map);
	        uniRes.setDataSet(list, "resultList");
	        
	        uniRes.sendPlatformData(response);
	    } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    /*
	 * 기안등록 수신처 부서 콤보 조회
	 */
    @RequestMapping(value = "admi/selectAddrDeptCombo.do")
    public void  selectAddrDeptCombo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try{
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectAddrDeptCombo(map);
	        uniRes.setDataSet(list, "resultList");
	        
	        uniRes.sendPlatformData(response);
	    } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    @RequestMapping(value = "admi/selectPrivateAddrDeptCombo.do")
    public void  selectPrivateAddrDeptCombo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try{
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectPrivateAddrDeptCombo(map);
	        uniRes.setDataSet(list, "resultList");
	        
	        uniRes.sendPlatformData(response);
	    } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    @RequestMapping(value = "admi/selectDcDept.do")
    public void  selectDcDeptCombo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        try{
	        uniReq.receivePlatformData(request);
	        
	        Map map = uniReq.getVariable();
	        
	        List<?> list = admiService.selectDcDept(map);
	        uniRes.setDataSet(list, "resultList");
	        
	        uniRes.sendPlatformData(response);
	    } catch (Exception e) {
			uniRes.sendException(response, e);
		}
    }
    
    /**
     * 구매예산통제 프로시져 호출 
     * @throws Exception 
     */
    @RequestMapping(value = "admi/saveSpAcPurcBudgDept.do")
    public void  saveSpAcPurcBudgDept(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            //DataSet ds = uniReq.getDS("resultList");
    
            HashMap nMap1  = new HashMap();
            nMap1.put("deptCd", request.getParameter("deptCd"));
            
            System.out.println("========================2========================");
            System.out.println(request.getParameter("deptCd"));
            System.out.println("========================2========================");
            
			//HashMap nMap = admiService.saveSpAcPurcBudg(ds);
            HashMap nMap = admiService.saveSpAcPurcBudgDept(nMap1);
            
            uniRes.setVariables(nMap);
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 구매예산통제 프로시져 호출 
     * @throws Exception 
     */
    @RequestMapping(value = "admi/saveSpAcPurcBudgDept_recv.do")
    public void  saveSpAcPurcBudgDept_recv(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            //DataSet ds = uniReq.getDS("resultList");
    
            HashMap nMap1  = new HashMap();
            nMap1.put("deptCd", request.getParameter("deptCd"));
            
            System.out.println("========================2========================");
            System.out.println(request.getParameter("deptCd"));
            System.out.println("========================2========================");
            
			//HashMap nMap = admiService.saveSpAcPurcBudg(ds);
            HashMap nMap = admiService.saveSpAcPurcBudgDept_recv(nMap1);
            
            uniRes.setVariables(nMap);
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /**
     * 여비예산통제 프로시져 호출 
     * @throws Exception 
     */
    @RequestMapping(value = "admi/saveSpAcTripVouCreate.do")
    public void  saveSpAcTripVouCreate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            //DataSet ds = uniReq.getDS("resultList");
    
            HashMap nMap1  = new HashMap();
            nMap1.put("id", request.getParameter("id"));
            nMap1.put("userId", request.getParameter("userId"));
            nMap1.put("ip", request.getParameter("ip"));
            
            System.out.println("========================2========================");
            System.out.println(request.getParameter("id"));
            System.out.println(request.getParameter("userId"));
            System.out.println(request.getParameter("ip"));
            System.out.println("========================2========================");
            
			//HashMap nMap = admiService.saveSpAcPurcBudg(ds);
            HashMap nMap = admiService.saveSpAcTripVouCreate(nMap1);
            
            uniRes.setVariables(nMap);
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    @RequestMapping(value = "admi/selectBg03050.do")
    public void  selectBg03050(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = admiService.selectBg03050(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    @RequestMapping(value = "admi/saveBg03050.do")
    public void  saveBg3050(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            admiService.saveBg3050(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    @RequestMapping(value = "admi/selectBg04050.do")
    public void  selectBg04050(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = admiService.selectBg04050(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    @RequestMapping(value = "admi/selectBg04050Tab0.do")
    public void  selectBg04050Tab0(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = admiService.selectBg04050Tab0(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    @RequestMapping(value = "admi/selectBg04050Tab1.do")
    public void  selectBg04050Tab1(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = admiService.selectBg04050Tab1(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    @RequestMapping(value = "admi/selectBg04050Tab2.do")
    public void  selectBg04050Tab2(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = admiService.selectBg04050Tab2(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    @RequestMapping(value = "admi/selectBg04030.do")
    public void  selectBg04030(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = admiService.selectBg04030(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    @RequestMapping(value = "admi/selectBg04030Tab0.do")
    public void  selectBg04030Tab0(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = admiService.selectBg04030Tab0(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    @RequestMapping(value = "admi/selectBg04030Tab1.do")
    public void  selectBg04030Tab1(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = admiService.selectBg04030Tab1(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    @RequestMapping(value = "admi/selectBg04040.do")
    public void  selectBg04040(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = admiService.selectBg04040(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    @RequestMapping(value = "admi/selectBg04020.do")
    public void  selectBg04020(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = admiService.selectBg04020(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    @RequestMapping(value = "admi/selectBg04020Tab0.do")
    public void  selectBg04020Tab0(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = admiService.selectBg04020Tab0(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    @RequestMapping(value = "admi/selectBg04020Tab1.do")
    public void  selectBg04020Tab1(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = admiService.selectBg04020Tab1(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    @RequestMapping(value = "admi/selectBg04020Tab2.do")
    public void  selectBg04020Tab2(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = admiService.selectBg04020Tab2(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /*
	 * 부서별예산관리부서등록 조회한다.
	 */
    @RequestMapping(value = "admi/selectBgAcntDeptList.do")
    public void  selectBgAcntDeptList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = admiService.selectBgAcntDeptList(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    /*
     * 부서별예산관리부서등록 저장
     */
    
    @RequestMapping(value = "admi/saveBgAcntDeptList.do")
    public void  saveBgAcntDeptList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);
            
            DataSet ds = uniReq.getDS("resultList");
    
            admiService.saveBgAcntDeptList(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /*
     * 편성예산관리 선택된 예산구분을 다음 예산변경으로 복사해준다.
     */
    
    @RequestMapping(value = "admi/saveBgBudgCdCopy.do")
    public void  saveBgBudgCdCopy(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);
            
            Map map = uniReq.getVariable();
    
            admiService.saveBgBudgCdCopy(map);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
    /*
     * 본예산 기초자료 생성.
     */
    
    @RequestMapping(value = "admi/saveBgBudgCreate.do")
    public void  saveBgBudgCreate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        
        try {
        	uniReq.receivePlatformData(request);

        	Map rMap = uniReq.getVariable();
        	
        	HashMap map = admiService.saveBgBudgCreate(rMap);
        	
        	List<Map<String, ?>> list = new ArrayList();
        	list.add(map);
        	System.out.println(map);
        	uniRes.setDataSet(list, "resultList");
        	uniRes.sendPlatformData(response);
			
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
        
    }
    
    /**
     * 예산변경신청 CNT을(를) 조회한다.
     */
    @RequestMapping(value = "admi/selectBudgChgAplyCnt.do")
    public void  selectBudgChgAplyCnt(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = admiService.selectBudgChgAplyCnt(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
}
