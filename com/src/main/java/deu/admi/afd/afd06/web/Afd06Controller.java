package deu.admi.afd.afd06.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import deu.admi.afd.afd06.service.Afd06Service;
import deu.comm.unione.uniRequest;
import deu.comm.unione.uniResponse;

import com.nexacro.xapi.data.DataSet;
import com.nexacro.xapi.util.StringUtils;

@Controller
public class Afd06Controller {
	
	private static final int RESERVE_POSSIBLE = 0;
	private static final int TIME_DUPLICATION = 300;
		
	@Resource(name="afd06Service")
	private Afd06Service afd06Service;

	@RequestMapping(value="admi/afd/afd06/selectAfdAcntNoList.do")
	public void selectAfdAcntNoList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd06Service.selectAfdAcntNoList(map);
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}

    @RequestMapping(value = "admi/afd/afd06/saveAfd06010MainList.do")
    public void  saveAfd06010MainList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            afd06Service.saveAfd06010MainList(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
	
	@RequestMapping(value="admi/afd/afd06/selectAFD06020.do")
	public void selectAFD06020(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd06Service.selectAFD06020(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
    @RequestMapping(value = "admi/afd/afd06/saveAFD06020.do")
    public void  saveAFD06020(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            afd06Service.saveAFD06020(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
	@RequestMapping(value="admi/afd/afd06/selectAFD06030.do")
	public void selectAFD06030(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd06Service.selectAFD06030(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
    @RequestMapping(value = "admi/afd/afd06/saveAFD06030.do")
    public void  saveAFD06030(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            afd06Service.saveAFD06030(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
	@RequestMapping(value="admi/afd/afd06/selectAFD06040.do")
	public void selectAFD06040(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd06Service.selectAFD06040(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
    @RequestMapping(value = "admi/afd/afd06/saveAFD06040.do")
    public void  saveAFD06040(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            afd06Service.saveAFD06040(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
	@RequestMapping(value="admi/afd/afd06/selectAFD06050.do")
	public void selectAFD06050(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd06Service.selectAFD06050(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
    @RequestMapping(value = "admi/afd/afd06/saveAFD06050.do")
    public void  saveAFD06050(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            afd06Service.saveAFD06050(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
    
	@RequestMapping(value="admi/afd/afd06/selectAFD06060.do")
	public void selectAFD06060(HttpServletRequest request, HttpServletResponse response) throws Exception {
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		try {
			uniReq.receivePlatformData(request);
			Map map = uniReq.getVariable();
			List<?> list = afd06Service.selectAFD06060(map);
			
			uniRes.setDataSet(list, "resultList");
			uniRes.sendPlatformData(response);
		} catch (Exception e) {
			uniRes.sendException(response, e);
		}
	}
	
    @RequestMapping(value = "admi/afd/afd06/saveAFD06060.do")
    public void  saveAFD06060(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uniRequest uniReq = new uniRequest();
        uniResponse uniRes = new uniResponse();

        try {
            uniReq.receivePlatformData(request);

            DataSet ds = uniReq.getDS("resultList");
            
            afd06Service.saveAFD06060(ds);
            
            uniRes.sendPlatformData(response);
        } catch (Exception e) {
            uniRes.sendException(response, e);
        }
    }
}

