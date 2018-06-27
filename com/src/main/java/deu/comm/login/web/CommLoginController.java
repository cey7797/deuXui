package deu.comm.login.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hsqldb.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nexacro.xapi.data.DataSet;
import com.nexacro.xapi.data.PlatformData;
import com.nexacro.xapi.data.datatype.PlatformDataType;

import deu.comm.login.service.CommLoginService;
import deu.comm.unione.BusinessException;
import deu.comm.unione.ConvertUtil;
import deu.comm.unione.uniRequest;
import deu.comm.unione.uniResponse;
import deu.comm.login.web.SessionManager;

@Controller
public class CommLoginController {

	/** comCsiService */
	@Resource(name = "commLoginService")
	private CommLoginService commLoginService; 
	
	/**
	 * 로그인한다.
	 */
	@RequestMapping(value = "/comm/doGetLogin.do")
	public void  doGetLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		SessionManager ssmanager = SessionManager.getInstance();
		
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();

		try{
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			List<?> list = commLoginService.doGetLogin(map, session.getId(), request.getRemoteAddr());

			uniRes.setDataSet(list, "resultList");
			uniRes.getpData().getDataSet("resultList").setChangeStructureWithData(true);
			uniRes.getpData().getDataSet("resultList").addColumn("sessId", PlatformDataType.STRING);
			uniRes.getpData().getDataSet("resultList").setChangeStructureWithData(false);
			uniRes.getpData().getDataSet("resultList").set(0, "sessId", session.getId());
			
			session.setAttribute("loginUser", uniRes.getpData().getDataSet("resultList"));
			
			uniRes.sendPlatformData(response);
			
		} catch (Exception e){
			uniRes.sendException(response, e);
		}
		
	}
	
	/**
	 * 로그인한다.
	 */
	@RequestMapping(value = "/comm/doGetLoginPop.do")
	public void  doGetLoginPop(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		SessionManager ssmanager = SessionManager.getInstance();
		
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();

		try{
			uniReq.receivePlatformData(request);
			
			Map map = uniReq.getVariable();
			List<?> list = commLoginService.doGetLoginPop(map, session.getId(), request.getRemoteAddr());

			uniRes.setDataSet(list, "resultList");
			uniRes.getpData().getDataSet("resultList").setChangeStructureWithData(true);
			uniRes.getpData().getDataSet("resultList").addColumn("sessId", PlatformDataType.STRING);
			uniRes.getpData().getDataSet("resultList").setChangeStructureWithData(false);
			uniRes.getpData().getDataSet("resultList").set(0, "sessId", session.getId());
			
			session.setAttribute("loginUser", uniRes.getpData().getDataSet("resultList"));
			
			uniRes.sendPlatformData(response);
			
		} catch (Exception e){
			uniRes.sendException(response, e);
		}
		
	}
	
	/**
	 * 세션을 체크하고 redirection한다.
	 */
	@RequestMapping(value = "/comm/doInit.do")
	public void  doInit(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		
		uniRequest uniReq = new uniRequest();
		uniResponse uniRes = new uniResponse();

		try{
			
			Object obj = session.getAttribute("loginUser");
			
			if(obj != null) {
				
				uniRes.setDataSet((DataSet)obj);
				
				uniRes.sendPlatformData(response);
			
			}
			
		} catch (Exception e){
			uniRes.sendException(response, e);
		}
		
	}
	
	/**
	 * 메인페이지로 이동을 한다.
	 */
	@RequestMapping(value = "/comm/doMove.do")
	public void  doMove(HttpServletRequest request, HttpServletResponse response) throws Exception {

			String url = "http://afa.deu.ac.kr:8080/com/deuXui/index.html";
			response.sendRedirect(url);
			
	}
	
	
	/**
	 * 로그아웃을 한다.
	 */
	@RequestMapping(value = "/comm/doLogout.do")
	public void  doLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		SessionManager ssmanager = SessionManager.getInstance();
		
		uniResponse uniRes = new uniResponse();

		try{
			
			Object obj = request.getSession().getAttribute("loginUser");
			if(obj != null) {
				if(session != null){
					commLoginService.doLogout(session.getId());
					session.removeAttribute("loginUser");
				}
				
				uniRes.setVariable("ErrorCode", "-9000");
				uniRes.setVariable("ErrorMsg", "");
				
				uniRes.sendPlatformData(response);
			
			} 
			
		} catch (Exception e){
			uniRes.sendException(response, e);
		}
		
	}
	
}
