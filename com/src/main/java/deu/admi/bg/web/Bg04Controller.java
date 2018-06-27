package deu.admi.bg.web;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import deu.admi.bg.service.Bg04Service;
import deu.comm.unione.uniRequest;
import deu.comm.unione.uniResponse;

import com.nexacro.xapi.data.DataSet;

@Controller
public class Bg04Controller {

	/** bg04Service */
	@Resource(name = "bg04Service")
	private Bg04Service bg04Service; 
	
	
	
	/*#########################################################################################################################################*/
    /* 예산통제관리(admi/bg/bg04/bg04010.xfdl)
    /*#########################################################################################################################################*/

	/**
	 *  
	 */
    @RequestMapping(value = "admi/bg04/selectBg04010_01.do")
    public void  selectBg04010_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = bg04Service.selectBg04010_01(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
	
    
    /**
	 *  
	 */
    @RequestMapping(value = "admi/bg04/selectBg04010_02.do")
    public void  selectBg04010_02(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = bg04Service.selectBg04010_02(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    
	
    /**
	 *  
	 */
    @RequestMapping(value = "admi/bg04/selectBg04010_ctrler_cnt.do")
    public void  selectBg04010_ctrler_cnt(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = bg04Service.selectBg04010_ctrler_cnt(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
    
    
    
    
    /*#########################################################################################################################################*/
    /* 예산통제 결재 처리 SP                                                                                           */
    /*#########################################################################################################################################*/
    /**
	 * 
	 */
    
    
    @RequestMapping(value = "admi/bg04/P_AC_CTRLER_APPRO.do")
    public void P_AC_CTRLER_APPRO(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
        
        try {
        	uniReq.receivePlatformData(request);

        	DataSet ds = uniReq.getDS("resultList");
        	
        	HashMap map = bg04Service.P_AC_CTRLER_APPRO(ds);
        	
        	List<Map<String, ?>> list = new ArrayList();
        	list.add(map);

        	uniRes.setDataSet(list, "resultList");
        	uniRes.sendPlatformData(response);
			
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
        
    }
}
