package deu.admi.ac.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nexacro.xapi.data.DataSet;

public interface Ac02Service {
	
	
	
	
	
	
	/*#########################################################################################################################################*/
    /* 결의서등록(admi/ac/ac02/ac02010.xfdl)                                                                                                     */
    /*#########################################################################################################################################*/
    
	/**
	 * 
	 */
    public abstract List<?> selectAc02010_01(Map map) throws Exception;
    
    public abstract List<?> selectAc02010_excelMember(Map map) throws Exception;
    
    public abstract HashMap saveAc02010_01_Search_Approval(DataSet ds) throws Exception;
    
    
    
    /*#########################################################################################################################################*/
    /* 결의서등록 (admi/ac/ac02/ac02010.xfdl)  전자결재                                                                                              */
    /*#########################################################################################################################################*/
    
    /*
	 * 전자결재 공문양식 결의서 결의종류가 자금/결산 대체 제외한 나머지일때
	 */
    public abstract HashMap saveAc02010_01_Approval(DataSet ds) throws Exception;
    
    public abstract HashMap chkAc02010_01_Approval(DataSet ds) throws Exception;
    
    /*
	 * 전자결재 공문양식 결의서 결의종류가 자금/결산대체 일때
	 */
    public abstract HashMap saveAc02010_01_Approval_vouKnd32(DataSet ds) throws Exception;
    
    public abstract HashMap saveAc02010_01_Approval_vouKnd33(DataSet ds) throws Exception;
    
    
    
    /*#########################################################################################################################################*/
    /* 수입/여입결의서등록(admi/ac/ac02/ac02010_p01.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    
    /**
	 * 
	 */
    public abstract List<?> selectAc02010p01_01(Map map) throws Exception;
    
    /**
	 * 
	 */
    public abstract List<?> selectAc02010p01_01_link(Map map) throws Exception;
    
    /**
	 * 
	 */
    public abstract List<?> selectAc02010p01_02(Map map) throws Exception;
    
    /**
	 * 
	 */
    public abstract List<?> selectAc02010p01_03(Map map) throws Exception;
    
    /**
	 * 
	 */
    public abstract List<?> selectAc02010p01_04(Map map) throws Exception;
    
    
	/**
	 *  
	 */
    public abstract void saveAc02010p01_01(DataSet ds) throws Exception;
    
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 수입/여입결의서등록(admi/ac/ac02/ac02010_p01.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    
    /**
	 * 
	 */
        
    public abstract HashMap saveVouNoSp(DataSet ds) throws Exception;
    
    
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 수입/여입 TAB 계정내역 (admi/ac/ac02/ac02010_p01_t01.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 * 
	 */
    public abstract List<?> selectAc02010p01_t01_01(Map map) throws Exception;
    
    /**
	 * 
	 */
    public abstract List<?> selectAc02010p01_t01_cnt(Map map) throws Exception;
    
	/**
	 *  
	 */
    public abstract void saveAc02010p01_t01_01(DataSet ds) throws Exception;
    
    public abstract void saveAc02020p01_t01_01(DataSet ds) throws Exception;
    
    
    /*#########################################################################################################################################*/
    /* 수입/여입 TAB 증빙내역 (admi/ac/ac02/ac02010_p01_t01.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 * 
	 */
    public abstract List<?> selectAc02010p01_t01_02(Map map) throws Exception;
    
	/**
	 *  
	 */
    public abstract void saveAc02010p01_t01_02(DataSet ds) throws Exception;
    
    public abstract void saveAc02010p01_t01_02_purc(DataSet ds) throws Exception;
    
    
    /**
	 *  
	 */
    public abstract void saveAc02010p01_t01_02_file(DataSet ds) throws Exception;
    
    /**
	 *  
	 */
    public abstract void saveAc02010p01_t02_02_file(DataSet ds) throws Exception;
    
    
    /*#########################################################################################################################################*/
    /* 수입/여입 TAB 원천세 (admi/ac/ac02/ac02010_p01_t01.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 * 
	 */
    public abstract List<?> selectAc02010p01_t01_03(Map map) throws Exception;
    
	/**
	 *  
	 */
    public abstract void saveAc02010p01_t01_03(DataSet ds) throws Exception;
    
    
    /**
	 *  
	 */
    public abstract void saveAc02010p01_t01_03_purc(DataSet ds) throws Exception;
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 수입/여입 TAB 계좌내역(입금) (admi/ac/ac02/ac02010_p01_t01.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 * 
	 */
    public abstract List<?> selectAc02010p01_t01_04(Map map) throws Exception;
    
	/**
	 *  
	 */
    public abstract void saveAc02010p01_t01_04(DataSet ds) throws Exception;
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 수입/여입 TAB 계좌내역(지금) (admi/ac/ac02/ac02010_p01_t01.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 * 
	 */
    public abstract List<?> selectAc02010p01_t01_05(Map map) throws Exception;
    
	/**
	 *  
	 */
    public abstract void saveAc02010p01_t01_05(DataSet ds) throws Exception;
    
    
    
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 진행상태 처리 SP                                                                                           */
    /*#########################################################################################################################################*/
    
    /**
	 * 
	 */
        
    public abstract HashMap P_AC_STA_CD_PROC(DataSet ds) throws Exception;
    
    public abstract HashMap P_AC_STA_CD_PROC_PUCH(DataSet ds) throws Exception;
    
    
    
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 결의서접수 (admi/ac/ac02/ac02020.xfdl)                                                                                              */
    /*#########################################################################################################################################*/
    /**
	 * 
	 */
    public abstract List<?> selectAc02020_01(Map map) throws Exception;
    
    public abstract void saveAc02020_purc(DataSet ds) throws Exception;
    
    public abstract void saveAc02020_recv_chg(DataSet ds) throws Exception;
    
    public abstract List<?> selectAc02020_purc_p01_1(Map map) throws Exception;
    
    public abstract HashMap SP_SLIP_CREATE(DataSet ds) throws Exception;
    
    public abstract void saveAc02020_purc_p01_1(DataSet ds) throws Exception;
    
    public abstract List<?> selectAc02020_purc_p01_2(Map map) throws Exception;
    
    public abstract List<?> selectAc02020_purc_p01_3(Map map) throws Exception;
    
    public abstract HashMap saveAc02020_01_Search_Approval(DataSet ds) throws Exception;
    
    /**
	 * 
	 */
    public abstract List<?> selectAc02020_t01_01(Map map) throws Exception;
    
    
    /**
	 * 
	 */
    public abstract List<?> selectAc02020_t02_01(Map map) throws Exception;
    
    
    /**
	 * 
	 */
    public abstract List<?> selectAc02020_t03_01(Map map) throws Exception;
    
    
    /**
	 * 
	 */
    public abstract List<?> selectAc02020_t04_01(Map map) throws Exception;
    
    
    /**
	 * 
	 */
    public abstract List<?> selectAc02020_t04_02(Map map) throws Exception;
    
    /**
	 * 
	 */
    public abstract void saveAc02020_t04_02(DataSet ds) throws Exception;
    
    
    /*#########################################################################################################################################*/
    /* 결의서접수 (admi/ac/ac02/ac02010.xfdl)  전자결재                                                                                              */
    /*#########################################################################################################################################*/
    
    /*
	 * 전자결재 결의서접수(경리팀)
	 */
    public abstract HashMap saveAc02020_01_Approval(DataSet ds) throws Exception;
    
    /*
	 * 전자결재 결의서접수(구매팀)
	 */
    public abstract HashMap saveAc02020_01_Approval_puch(DataSet ds) throws Exception;
    
    public abstract void updateAc02020_01_VendorDesc(DataSet ds) throws Exception;
    
    /*#########################################################################################################################################*/
    /* 전표확정등록 (admi/ac/ac02/ac02030.xfdl)                                                                                              */
    /*#########################################################################################################################################*/
    /**
	 * 
	 */
    public abstract List<?> selectAc02030_01(Map map) throws Exception;
    
    
    /**
	 * 
	 */
    public abstract List<?> selectAc02030_t01_01(Map map) throws Exception;
    
    /*
	 * 분개내역(차변) 저장
	 */
    public abstract void saveAc02030_t01_01(DataSet ds) throws Exception;
    
    /**
	 * 
	 */
    public abstract List<?> selectAc02030_t01_02(Map map) throws Exception;
    
    /*
	 * 분개내역(대변) 저장
	 */
    public abstract void saveAc02030_t01_02(DataSet ds) throws Exception;
    
    /**
	 * 
	 */
    public abstract List<?> selectAc02030_t02_01(Map map) throws Exception;
    
    
    /**
	 * 
	 */
    public abstract List<?> selectAc02030_t03_01(Map map) throws Exception;
    
    
    /**
	 * 
	 */
    public abstract List<?> selectAc02030_t04_01(Map map) throws Exception;
    
    
    /**
	 * 
	 */
    public abstract List<?> selectAc02030_t04_02(Map map) throws Exception;
    
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 계정대체 계정내역 (admi/ac/ac02/ac02010_p01_t03.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 * 
	 */
    public abstract List<?> selectAc02010p01_t03_01(Map map) throws Exception;
    
    
    
    
    /*#########################################################################################################################################*/
    /* 자금대체 계정내역 (admi/ac/ac02/ac02010_p01_t04.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 * 
	 */
    public abstract List<?> selectAc02010p01_t04_01(Map map) throws Exception;
    
    /**
	 * 
	 */
    public abstract void saveAc02010p01_t04_01(DataSet ds) throws Exception;
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 결산대체 계정내역 (admi/ac/ac02/ac02010_p01_t05.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 * 
	 */
    public abstract List<?> selectAc02010p01_t05_01(Map map) throws Exception;
    
    /**
	 * 
	 */
    public abstract void saveAc02010p01_t05_01(DataSet ds) throws Exception;
    
    
    public abstract void saveSlipAccEmpId(DataSet ds) throws Exception;
    
    
    /*#########################################################################################################################################*/
    /* 은행이관(admi/ac/ac02/ac02040.xfdl)                                                                                                      */
    /*#########################################################################################################################################*/
    /**
	 * 은행목록 조회
	 */
    public abstract List<?> selectAc02040_01(Map map) throws Exception;
    
    /**
	 * 지급목록 조회
	 */
    public abstract List<?> selectAc02040_02(Map map) throws Exception;
    
    /**
	 * 이관/이관취소 처리 ( mssql생성전용 )
	 */        
    public abstract HashMap P_AC_BANK_PROC(DataSet ds, Map param) throws Exception;
    
    /**
	 * 이관/이관취소 처리 ( oracle생성전용 )
	 */        
    public abstract HashMap P_AC_BANK_PROC_POST(DataSet ds, Map param) throws Exception;
    
    
    
    
    
    
    
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 대체결의서접수 (admi/ac/ac02/ac02050_01.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 * 
	 */
    public abstract List<?> selectAc02050_01(Map map) throws Exception;
    
    /**
	 * 계정대체 상세
	 */
    public abstract List<?> selectAc02050_02_01(Map map) throws Exception;
    
    /**
	 * 자금대체 상세
	 */
    public abstract List<?> selectAc02050_02_02(Map map) throws Exception;
    
    /**
	 * 결산대체 상세 
	 */
    public abstract List<?> selectAc02050_02_03(Map map) throws Exception;
    
    
    public abstract List<?> selectAc02060(Map map) throws Exception;
    
    public abstract void saveAc02060(DataSet ds) throws Exception;
    
    public HashMap saveCfmAc02020(DataSet ds) throws Exception;
}
