package deu.comm.file.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import deu.comm.file.service.CommFileService;
import deu.comm.unione.uniRequest;
import deu.comm.unione.uniResponse;

@Controller
public class CommFileController {

	/** comFileService */
	@Resource(name = "commFileService")
	private CommFileService commFileService; 
	
	/**
	 * 로그인한다.
	 */
	@RequestMapping(value = "/comm/selectFile.do")
	public void  selectFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();
		
		try{			
			uniReq.receivePlatformData(request);

			Map map = uniReq.getVariable();
			List<?> list = commFileService.selectFileInfo(map);
	
			uniRes.setDataSet(list, "resultList");
			
			uniRes.sendPlatformData(response);
			
		} catch (Exception e){
			uniRes.sendException(response, e);
		}
		
	}
	
}
