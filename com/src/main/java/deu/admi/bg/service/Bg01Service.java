package deu.admi.bg.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nexacro.xapi.data.DataSet;

public interface Bg01Service {

	
	/*#########################################################################################################################################*/
    /* 예산통제자등록 (admi/bg/bg01/bg01060.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    
    /**
	 * 
	 */
    public abstract List<?> selectBg01060_01(Map map) throws Exception;
    
       
    
	/**
	 *  
	 */
    public abstract void saveBg01060_01(DataSet ds) throws Exception;
	
	
	
	
}
