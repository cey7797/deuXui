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

import deu.admi.bg.service.Bg01Service;
import deu.comm.unione.uniRequest;
import deu.comm.unione.uniResponse;

import com.nexacro.xapi.data.DataSet;

@Controller
public class Bg01Controller {

	/** bg01Service */
	@Resource(name = "bg01Service")
	private Bg01Service bg01Service; 
	
	
	
	
	/*#########################################################################################################################################*/
    /* 예산통제자등록 (admi/bg/bg01/bg01060.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    
    /**
	 * 
	 */
    @RequestMapping(value = "admi/bg01/selectBg01060_01.do")
    public void  selectBg01060_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();
    
        uniReq.receivePlatformData(request);
        
        Map map = uniReq.getVariable();
        
        List<?> list = bg01Service.selectBg01060_01(map);
        uniRes.setDataSet(list, "resultList");
        
        uniRes.sendPlatformData(response);
    }
        
    
    /**
	 * 
	 */
    @RequestMapping(value = "admi/bg01/saveBg01060_01.do")
    public void  saveBg01060_01(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            bg01Service.saveBg01060_01(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
	
	
	
	

}
