package deu.comm.unione.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import deu.comm.unione.uniRequest;
import deu.comm.unione.uniResponse;

import com.nexacro.xapi.data.DataSet;
import com.nexacro.xapi.data.PlatformData;

public class SessionCheckInterceptor extends HandlerInterceptorAdapter {
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String path = request.getContextPath();
		HttpSession session = request.getSession();
		String rURI = request.getRequestURI();
		
		DataSet obj = (DataSet) session.getAttribute("loginUser");
	
		if (obj == null){
			
			if(
				 (rURI.indexOf("selectMultiLangMng.do") < 0) &&
				 (rURI.indexOf("doInit.do") < 0) &&
				 (rURI.indexOf("admi/saveSpAcPurcBudg.do") < 0) && //구매예산통제 프로시져 호출용
				 (rURI.indexOf("admi/saveSpAcPurcBudgDept.do") < 0) && //구매예산통제 프로시져 호출용
				 (rURI.indexOf("admi/saveSpAcPurcVouCreate.do") < 0) && //구매예산통제 프로시져 param 호출용
				 (rURI.indexOf("admi/saveSpAcPurcVouCancel.do") < 0) && //구매예산통제 프로시져 param 호출용
				 (rURI.indexOf("admi/saveSpAcTripVouCreate.do") < 0) && //여비예산통제 프로시져 param 호출용
				 
				 (rURI.indexOf("comm/selectMenuPopInfo.do") < 0) && //발전기금전용팝업
				 (rURI.indexOf("admi/afd/afd04/selectAFDMemberYn.do") < 0) && //발전기금로그인인증				 
				 (rURI.indexOf("selectComCdCombo.do") < 0) && //온라인약정
				 (rURI.indexOf("admi/afd/afd01/selectComboMembas.do") < 0) && //온라인약정
				 (rURI.indexOf("admi/afd/afd04/saveOnlieCntrct.do") < 0) && //온라인약정
				 (rURI.indexOf("admi/afd/afd01/retrieveEndw.do") < 0) && //온라인약정
				 (rURI.indexOf("admi/afd/afd04/retrieveEndw.do") < 0) && //나의기부현황
				 (rURI.indexOf("admi/afd/afd04/retrieveDonationStatus.do") < 0) && //나의기부현황
					 
				 (rURI.indexOf("doGetLoginPop.do") < 0) && //구매 결의서 로그인팝업 
				 (rURI.indexOf("selectSysMenu.do") < 0) && //기존시스템과 링크를 위해 추가
				 (rURI.indexOf("selectSubMenu.do") < 0) && //기존시스템과 링크를 위해 추가
				 (rURI.indexOf("selectLeftMenu.do") < 0) && //기존시스템과 링크를 위해 추가
				 (rURI.indexOf("admi/selectDoGetLogout.do") < 0) && //로그아웃
				 (rURI.indexOf("doGetLogin.do") < 0 &&
				  rURI.indexOf("selectRecruitInfo.do") < 0) //login처리 url, session검증처리가 불필요한 API호출등등 url을 등록
			 ){ 
				
				String message = "세션정보가 만료되어 로그인페이지로 이동합니다.";

				System.out.println("session expired");
				
				//.do direct호출시 
				/*if(rURI.indexOf(".do")>0){
					
					StringBuilder strBuiler = new StringBuilder();
					
					strBuiler.append("<SCRIPT type=text/javascript>");
					strBuiler.append("\talert('").append(message).append("');");
					strBuiler.append("\ttop.location='/com/deuXui/index.html'    "); //개발서버일 경우
					strBuiler.append("</SCRIPT>");
					response.setContentType("text/html; charset=UTF-8");
					response.getWriter().print(strBuiler.toString());
				} else {//naxacro mathod call시*/
					uniResponse uniRes = new uniResponse();
					
					uniRes.setVariable("ErrorCode", "-9009");
					uniRes.setVariable("ErrorMsg", message);
					
					uniRes.sendPlatformData(response);
				//}
	
				return false;
			
			}
		} else {
			
		}
			return true;
	}
}