package deu.admi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nexacro.xapi.data.DataSet;

public interface AdmiService {

    /*****************                   admiCommon                   ******************/
    
	/*
	 * 회계연도 조회한다.
	 */
    public abstract List<?> selectAcntYy(Map map) throws Exception;
	
	/*
	 * 회계단위 콤보를 조회한다
	 */
    public abstract List<?> selectAcntUntCmb(Map map) throws Exception;
    
    /**
     * 회계단위등록(상세정보탭) 을(를) 조회한다.
     * @throws Exception 
     */
    public abstract List<?> selectAcntUntList(Map map) throws Exception;
    
    /**
     * 회계단위등록(상세정보탭) 을(를) 저장한다.
     * @throws Exception 
     */
    public abstract void saveAcntUntList(DataSet ds) throws Exception;
    
    /**
     * 회계단위등록(권한탭) 을(를) 조회한다.
     * @throws Exception 
     */
    public abstract List<?> selectAcntPermList(Map map) throws Exception;
    
    /**
     * 회계단위등록(권한탭) 을(를) 저장한다.
     * @throws Exception 
     */
    public abstract void saveAcntPermList(DataSet ds) throws Exception;
    
    /**
     * 회계단위등록(합산회계탭) 을(를) 조회한다.
     * @throws Exception 
     */
    public abstract List<?> selectAcntAddList(Map map) throws Exception;
    
    /**
     * 회계단위등록(합산회계탭) 을(를) 저장한다.
     * @throws Exception 
     */
    public abstract void saveAcntAddList(DataSet ds) throws Exception;
    

    /*#########################################################################################################################################*/
    /* 계정코드등록(bg01020)                                                                                                                   */
    /*#########################################################################################################################################*/
        
    
    /**
     * 계정내역을(를) 조회한다.
     * @param acntUnt 회계단위
     * @param acntYy 회계년도
     */
    public abstract List<?> selectAcntList(Map map) throws Exception;
    
    /**
     * 세세목내역을(를) 조회한다.
     * @param acntUnt 회계단위
     * @param acntYy 회계년도
     * @param acntCd 계정과목코드
     */
    public abstract List<?> selectItmSeqList(Map map) throws Exception;
    
	/**
     * 계정내역을(를) 저장한다.
     * @param resultList 계정 목록정보
	 */
	public abstract void saveAcntList(DataSet ds01, DataSet ds02) throws Exception;
	
	/**
     * 전년도 예산을 복사한다
     * @param resultList 계정 목록정보
	 */
	public abstract void saveAcntCopyList(DataSet ds) throws Exception;


	/*************************************************************
	 * 재원등록(bg01040)
	 *************************************************************/
	/*
	 * 재원등록내역을 조회한다.
	 */
    public abstract List<?> selectBgFinc(Map map) throws Exception;
    
    /*
	 * 재원등록내역을 저장한다.
	 */
    public abstract void saveBgFinc(DataSet ds) throws Exception;

	/*************************************************************
	 * 예산일정등록(bg02010)
	 *************************************************************/
    
    /*
	 * 예산일정을 조회한다.
	 */
    public abstract List<?> selectBgSched(Map map) throws Exception;
    
    /*
	 * 예산일정을 저장한다.
	 */
    public abstract void saveBgSched(DataSet ds) throws Exception;

    /*
	 * 사업등록내역을 조회한다
	 */
    public abstract List<?> selectBgBiz(Map map) throws Exception;

    /*
	 * 사업등록내역을 저장한다.
	 */
    public abstract void saveBgBiz(DataSet ds) throws Exception;
    
    /*
   	 * 예산편성관련통계 내역을 조회한다
   	 */
    public abstract List<?> selectbgFrmn(Map map) throws Exception;
    
    
    
    
    
    
    /***********************************************************
     * 관리부서등록 (bg01050)
     ************************************************************/
    
    public abstract List<?> selectBgOrgPermMain(Map map) throws Exception;

    /*
	 *관리부서 상세내역을 조회한다
	 */
    public abstract List<?> selectBgOrgPerm(Map map) throws Exception;
    
    /*
     * 관리부서 메인내역,상세내역을 저장한다
     */
    public abstract void saveBgOrgPerm(DataSet ds) throws Exception;
    
    
    
    
    
    
    
    
    
    public abstract List<?> selectBgOrgBizSta(Map map) throws Exception;
    
    
    /***********************************************************
     * 부서별사업등록(bg02020)
     ************************************************************/
    /*
     * 부서별사업등록 조회
     */
    public abstract List<?> selectBgOrgBiz(Map map) throws Exception;
    
    /*
     * 부서별사업등록 저장
     */
    public abstract void saveBgOrgBiz(DataSet ds) throws Exception;
    
    /***********************************************************
     * 예산편성신청등록(bg02030)
     ************************************************************/
    /*
     * 예산편성신청등록 조회
     */
    public abstract List<?> selectBgFrmnApplyList(Map map) throws Exception;
    
    /*
     * 예산편성관리등록 조회
     */
    public abstract List<?> selectBgFrmnApplyFixedList(Map map) throws Exception;
    
    /*
     * 예산편성신청등록 조회 엑셀용
     */
    public abstract List<?> selectBgFrmnApplyExcelList(Map map) throws Exception;
    
    /*
     * 예산일정에 포함된 오늘기준 회계연도,회계단위,예산구분을 조회한다.
     */
    public abstract List<?> selectBgFrmnSched(Map map) throws Exception;
    
    /*
     * 예산일정에 포함된 오늘기준 회계연도,회계단위,예산구분을 조회한다. 관리자용
     */
    public abstract List<?> selectBgFrmnSchedFixed(Map map) throws Exception;
    
    /*
     * 선택된 예산회계,연도,구분이 예산일정에 포함되어있는지 조회한다.
     */
    public abstract List<?> selectBgFrmnSchedCheck(Map map) throws Exception;
    
    /*
     * 편성예산조회한다.
     */
    public abstract List<?> selectBgFrmnlist(Map map) throws Exception;
    
    /*
     * 변경예산(관리자용)조회한다.
     */
    public abstract List<?> selectBgChgAdminList(Map map) throws Exception;
    
    /*
     * 변경예산조회한다.
     */
    public abstract List<?> selectBgChgList(Map map) throws Exception;
    
    /*
     * 편성예산 등록,수정,삭제한다.
     */
    public abstract void saveBgFrmnlist(DataSet ds1) throws Exception;
    
    /*
     * 변경예산 등록,수정,삭제한다.
     */
    public abstract void saveBgChgList(DataSet ds1,DataSet ds2,DataSet ds3) throws Exception;
    
    /*
     * 변경예산 등록,수정,삭제한다.
     */
    public abstract void saveBgChgNewList(DataSet ds1) throws Exception;
    
    /*
     * 편성예산상세조회한다.
     */
    public abstract List<?> selectBgFrmnDetlist(Map map) throws Exception;
    
    /*
     * 변경예산상세조회한다.
     */
    public abstract List<?> selectBgChgDetList(Map map) throws Exception;
    
    /*
     * 편성예산상세 등록,수정,삭제한다.
     */
    public abstract void saveBgFrmnDetlist(DataSet ds1) throws Exception;
    
    /*
     * 변경예산상세 등록,수정,삭제한다.
     */
    public abstract void saveBgChgDetList(DataSet ds1,DataSet ds2,DataSet ds3,DataSet ds4) throws Exception;
    
    /*
     * 편성예산계획조회한다.
     */
    public abstract List<?> selectBgFrmnSchedList(Map map) throws Exception;
    
    /*
     * 변경예산계획조회한다.
     */
    public abstract List<?> selectBgChgSchedList(Map map) throws Exception;
    
    /*
     * 편성예산계획 등록,수정,삭제한다.
     */
    public abstract void saveBgFrmnSchedlist(DataSet ds) throws Exception;
    
    /*
     * 변경예산계획 등록,수정,삭제한다.
     */
    public abstract void saveBgChgSchedList(DataSet ds) throws Exception;
    
    /*
     * 편성예산 계정과목코드 일괄 업데이트
     */
    public abstract void saveBgFrmnChglist(DataSet ds) throws Exception;
    
    /*
     * 변경예산 계정과목코드 일괄 업데이트
     */
    public abstract void saveBgChgChgList(DataSet ds) throws Exception;
    
    /*
     * 편성예산 일괄 삭제
     */
    public abstract void saveBgFrmnDelList(DataSet ds1) throws Exception;
    
    /*
     * 변경예산 일괄 삭제
     */
    public abstract void saveBgChgDelList(DataSet ds1) throws Exception;
    
    /*
     * 변경예산 일괄 삭제 신규
     */
    public abstract void saveBgChgDelNewList(DataSet ds1) throws Exception;
    
    /*
     * 편성예산 계정과목상세 일괄 삭제
     */
    public abstract void saveBgFrmnDetDelList(DataSet ds1,DataSet ds2,DataSet ds3) throws Exception;
    
    /*
     * 세세목콤보조회호출
     */
    public abstract List<?> selectBgItmSeqCombo(Map map) throws Exception;
    
    /*
     * 예산신청내역 계정기본,상세,월별 신청액의 합계여부확인
     */
    public abstract List<?> selectBgApplyChk(Map map) throws Exception;
    
    /*
     * 예산변경신청내역 계정기본,상세,월별 신청액의 합계여부확인
     */
    public abstract List<?> selectBgChgApplyChk(Map map) throws Exception;
    
    /*
     * 예산계정편성신청 프로그램
     * 신청버튼 클릭시 예산편성기본+예산편성상세+예산편성계획 3개테이블의 예산액이 맞는지 체크,
     * 예산계정기본 테이블에 진행상태를 신청으로 변경하여 저장한다
     */
    public abstract void saveBgStaCd(DataSet ds) throws Exception;
    
    /*
     * 예산계정변경신청 프로그램
     * 예산변경신청 테이블에 진행상태를 신청으로 변경하여 저장한다
     */
    public abstract void saveBgChgStaCd(DataSet ds) throws Exception;
    
    /*
     * 월별편성계획 초기화(delete 후 insert)
     */
    public abstract void saveBgFrmnSchedRemove(DataSet ds1,DataSet ds2) throws Exception;
    
    /*
     * 월별변경계획 초기화(delete 후 insert)
     */
    public abstract void saveBgChgSchedRemove(DataSet ds1,DataSet ds2) throws Exception;
    
    /**
     * 예산계정편성관리 프로그램
     * 예산계정기본,상세,계획을(를)전차수복사(조정차수0,1,2,3) 증가시켜 저장한다.
     */
    public abstract void saveBgAdjOrdCopy(Map map) throws Exception;
    
    /**
     * 변경예산계정편성관리 프로그램
     * 변경예산계정기본(를)전차수복사(조정차수101,201,202,203) 증가시켜 저장한다.
     */
    public abstract void saveBgChgAdjOrdCopy(Map map) throws Exception;
    
    /*
     * 예산계정편성확정 프로그램
     * 신청버튼 클릭시 예산계정기본 테이블에 진행상태를 신청으로 변경하여 저장한다
     */
    public abstract void saveBgStaCdFixed(DataSet ds) throws Exception;
    
    /*
     * 예산계정변경확정 프로그램
     * 확정버튼 클릭시 예산변경기본 테이블에 진행상태를 확정으로 변경하여 저장한다
     */
    public abstract void saveBgChgStaCdFixed(DataSet ds) throws Exception;
    
    /**
     * 계정내역을 변경한다(회계년도,부서코드,사업코드,계정코드)
     * @throws Exception 
     */
    public abstract HashMap saveBgAcntChg(DataSet ds) throws Exception;

    
    public abstract List<?> selectBgApplyBizChk(Map map) throws Exception;
    
    /*
     * 예산신청중 입력되지않은 계정과목 확인
     */
    public abstract List<?> selectBgApplyAcntChk(Map map) throws Exception;
    
    /*
     * 예산변경신청중 입력되지않은 계정과목 확인
     */
    public abstract List<?> selectBgChgApplyAcntChk(Map map) throws Exception;
    
    /*
     * 접속자의 소속부서및 관리부서 콤보호출
     */
    public abstract List<?> selectBudgOrgIdCombo(Map map) throws Exception;
    
    public abstract List<?> selectBudgBizCombo(Map map) throws Exception;
    
    /*
     * 예산편성관리 사업반려저장
     */
    public abstract void saveBgStaCdReca(DataSet ds1) throws Exception;
    
    /*
     * 예산변경관리 사업반려저장
     */
    public abstract void saveBgChgStaCdReca(DataSet ds1) throws Exception;
    
    /*************************************************************************
	 * 엑셀다운로드 예산편성관리 - 예산데이터를 전체를 엑셀로 내려받기위한 조회
	 *************************************************************************/
    public abstract List<?> selectBgFrmnFixedExcelTotal(Map map) throws Exception;
    
    /*************************************************************************
  	 * 엑셀다운로드 예산편성관리 - 예산데이터를 회계단위별로 엑셀로 내려받기위한 조회
  	 *************************************************************************/
    public abstract List<?> selectBgFrmnFixedExcelAcntUnt(Map map) throws Exception;
    
    /*************************************************************************
     * 엑셀다운로드 예산편성관리 - 예산데이터를 계정과목별로 엑셀로 내려받기위한 조회
     *************************************************************************/
    public abstract List<?> selectBgFrmnFixedExcelAcntCd(Map map) throws Exception;
    
    /*************************************************************************
     * 엑셀다운로드 예산편성관리 - 예산데이터를 사업별로 엑셀로 내려받기위한 조회
     *************************************************************************/
    public abstract List<?> selectBgFrmnFixedExcelBizCd(Map map) throws Exception;
    
    /*************************************************************************
     * 엑셀다운로드 예산편성관리 - 예산데이터를 부서별로 엑셀로 내려받기위한 조회
     *************************************************************************/
    public abstract List<?> selectBgFrmnFixedExcelBudgOrgId(Map map) throws Exception;
    
    public abstract List<?> selectBgFrmnFixedExcelBudgCd(Map map) throws Exception;
    
    /*
     * 변경예산계획조회한다.
     */
    public abstract List<?> selectBgChgAplyList(Map map) throws Exception;
    
    /*
     * 변경예산상세계획조회한다.
     */
    public abstract List<?> selectBgChgAplyDetList(Map map) throws Exception;
    
    /*
     * 변경예산계획확정조회한다.
     */
    public abstract List<?> selectBgChgAplyFixedList(Map map) throws Exception;
    
    public abstract List<?> selectBgChgAplyFixedListManager(Map map) throws Exception;
    /*
     * 변경예산상세계획확정조회한다.
     */
    public abstract List<?> selectBgChgAplyDetFixedList(Map map) throws Exception;
    
    /*
     * 변경예산계획 등록,수정,삭제한다.
     */
    public abstract void saveBgChgAplyList(DataSet ds) throws Exception;
    
    public abstract void copyBgChgAplyList(DataSet ds) throws Exception;
    
    /*
	 * 로그아웃
	 */
    public abstract List<?> selectDoGetLogout(Map map) throws Exception;
    
    /*
	 * 전자결재 예산변경(전용)
	 */
    public abstract HashMap saveBgChgDiv10Approval(DataSet ds) throws Exception;
    
    /*
	 * 전자결재 예산변경(추가)
	 */
    public abstract HashMap saveBgChgDiv20Approval(DataSet ds) throws Exception;
    
    /*
	 * 전자결재 예산변경 취소
	 */
    public abstract void saveBgChgApprovalCancel(DataSet ds) throws Exception;
    
    /*
     * 구매예산통제 프로시져 호출 
     */
    public abstract HashMap saveSpAcPurcBudg() throws Exception;
    
    /*
     * 구매예산통제 프로시져 호출 
     */
    public abstract HashMap saveSpAcPurcVouCreate(Map map) throws Exception;
    
    /*
     * 구매예산통제 프로시져 호출 
     */
    public abstract HashMap saveSpAcPurcVouCancel(Map map) throws Exception;
    
    /*
     * 예산변경관리(관리자용) 대량의 학과 및 학과를 조회
     */
    public abstract List<?> selectBgChgDeptAdddList(Map map) throws Exception;
    
    /*
     * 기안등록 수신처 부서 콤보 조회
     */
    public abstract List<?> selectAddrDeptCombo(Map map) throws Exception;
    
    public abstract List<?> selectPrivateAddrDeptCombo(Map map) throws Exception;
    
    public abstract List<?> selectDcDept(Map map) throws Exception;
    
    /*
     * 구매예산통제 프로시져 호출 (부서추가)
     */
    public abstract HashMap saveSpAcPurcBudgDept(Map map) throws Exception;
    
    /*
     * 구매예산통제 프로시져 호출 (부서추가)
     */
    public abstract HashMap saveSpAcPurcBudgDept_recv(Map map) throws Exception;
    
    /*
     * 여비예산통제 프로시져 호출 
     */
    public abstract HashMap saveSpAcTripVouCreate(Map map) throws Exception;
    
    public abstract List<?> selectBg03050(Map map) throws Exception;
    
    public abstract void saveBg3050(DataSet ds) throws Exception;
    
    public abstract List<?> selectBg04020(Map map) throws Exception;
    
    public abstract List<?> selectBg04020Tab0(Map map) throws Exception;
    
    public abstract List<?> selectBg04020Tab1(Map map) throws Exception;
    
    public abstract List<?> selectBg04020Tab2(Map map) throws Exception;
    
    public abstract List<?> selectBg04030(Map map) throws Exception;
    
    public abstract List<?> selectBg04030Tab0(Map map) throws Exception;
    
    public abstract List<?> selectBg04030Tab1(Map map) throws Exception;
    
    public abstract List<?> selectBg04040(Map map) throws Exception;
    
    public abstract List<?> selectBg04050(Map map) throws Exception;
    
    public abstract List<?> selectBg04050Tab0(Map map) throws Exception;
    
    public abstract List<?> selectBg04050Tab1(Map map) throws Exception;
    
    public abstract List<?> selectBg04050Tab2(Map map) throws Exception;
    /*
	 * 부서별예산관리부서등록을 조회한다.
	 */
    public abstract List<?> selectBgAcntDeptList(Map map) throws Exception;
    
    /*
	 * 부서별예산관리부서등록을 저장한다.
	 */
    public abstract void saveBgAcntDeptList(DataSet ds) throws Exception;
    
    /*
	 * 편성예산관리 선택된 예산구분을 다음 예산변경으로 복사해준다.
	 */
    public abstract void saveBgBudgCdCopy(Map map) throws Exception;
    
    public abstract HashMap saveBgBudgCreate(Map map) throws Exception;
    
    public abstract List<?> selectBudgChgAplyCnt(Map map) throws Exception;
}
