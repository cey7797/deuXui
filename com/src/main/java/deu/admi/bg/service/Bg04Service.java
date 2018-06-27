package deu.admi.bg.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nexacro.xapi.data.DataSet;

public interface Bg04Service {
	
	
	/*#########################################################################################################################################*/
    /* 예산통제관리(admi/bg/bg04/bg04010.xfdl)  
    /*#########################################################################################################################################*/
    
	/**
	 * 
	 */
    public abstract List<?> selectBg04010_01(Map map) throws Exception;
    
    
    
    /**
	 * 
	 */
    public abstract List<?> selectBg04010_02(Map map) throws Exception;
	
    
    
    /**
	 * 
	 */
    public abstract List<?> selectBg04010_ctrler_cnt(Map map) throws Exception;
    
    
    
    
    /*#########################################################################################################################################*/
    /* 예산통제 결재 처리 SP                                                                                           */
    /*#########################################################################################################################################*/
    
    /**
	 * 
	 */
        
    public abstract HashMap P_AC_CTRLER_APPRO(DataSet ds) throws Exception;
}
