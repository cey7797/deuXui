package deu.comm.unione.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import deu.comm.unione.vo.DataMap;
import deu.comm.unione.service.AuthCheckInterceptorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ibatis.common.logging.Log;
import com.ibatis.common.logging.LogFactory;

public class AuthCheckInterceptor extends HandlerInterceptorAdapter {
	
	//--------------------------------------------
	// SPRING ANNOTATION 사용 service,dao 자동 연결
	//--------------------------------------------
	@Autowired	 
	private AuthCheckInterceptorService authCheckInterceptorService;
	private Log logger = LogFactory.getLog(this.getClass());
	//--------------------------------------------
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle (HttpServletRequest request, 
							  HttpServletResponse response,
							  Object handler){
		 
		  
		String path = request.getContextPath(); 
		HttpSession session = request.getSession();		
		
		
		String rURI = request.getRequestURI();
		logger.debug("################################################");
		logger.debug("##### 			   AUTHCHECK INTECEPTOR ######");
		logger.debug("				rURI "+request.getScheme()+"://"+request.getServerName()+rURI);
		logger.debug("################################################");
		//-------------------------------------------------------
		//----------- 개발 끝 -------------------------------------
		Object obj = (Object)session.getAttribute("loginUser");
		 try{
			 // 전자결재 제공용으로 /deu/aby_0203e/doElecDownLoadFile.deu 추가 2013.11.27 이균희
			 // Ksign.do 추가 2013.12.22 김영빈
//			 if(obj==null && rURI.indexOf("/deu/sysUser/doView.do") < 0 && rURI.indexOf("/deu/req_0001r/doPTCall1.deu") < 0 && rURI.indexOf("/deu/sysUser/doSSOKsign.do") < 0 && rURI.indexOf("/deu/sysUserRecruit/doView.do") < 0 && rURI.indexOf("/deu/sysUserRecruit/doPersonalCh.do") < 0 && rURI.indexOf("/deu/sysUser/doSSo.do") < 0 && rURI.indexOf("/deu/sysUserEipRecruit/doView.do") < 0 && rURI.indexOf("/deu/sysUserAhmRecruit/doView.do") < 0  )
			if (obj == null
					&& rURI.indexOf("/deu/sysUser/doView.do") < 0
					&& rURI.indexOf("/deu/sysUser/doSSOKsign.do") < 0
					&& rURI.indexOf("/deu/sysUserRecruit/doView.do") < 0
					&& rURI.indexOf("/deu/sysUserRecruit/doPersonalCh.do") < 0
					&& rURI.indexOf("/deu/sysUser/doSSo.do") < 0
					&& rURI.indexOf("/deu/sysUserEipRecruit/doView.do") < 0
					&& rURI.indexOf("/deu/sysUserAhmRecruit/doView.do") < 0
					&& rURI.indexOf("/deu/sysUser/doCheckSessionDuplication.do") < 0
					&& rURI.indexOf("/deu/sysUser/doInsert.do") < 0)
			 {
					StringBuilder strBuiler = new StringBuilder(); 
					strBuiler.append("<SCRIPT type=text/javascript>");
					strBuiler.append("	top.location='/deu/sysUser/doView.do'    ");
					 
					strBuiler.append("</SCRIPT>");
					response.setContentType("text/html; charset=UTF-8");
					response.getWriter().print(strBuiler.toString());

					return false;
				 
			 }

			 else if (  obj != null 
				&& rURI.indexOf(".mjc")>0 
				&& rURI.indexOf("doView.mjc") < 0  //세션체크 사용안할때
				&& rURI.indexOf("sysWorkLog/doInsert.mjc") < 0  //로그기록
				&& rURI.indexOf("com_Cod") < 0   //공통 코드
				&& rURI.indexOf("com_Combo") < 0 //공통 콤보
				&& rURI.indexOf("com_Photo") < 0 //사진
				&& rURI.indexOf("help_zip") < 0  //우편번호
				&& rURI.indexOf("helpManual") < 0   //매뉴월
				&& rURI.indexOf("help_student") < 0   //학생 팝업 
				&& rURI.indexOf("sysPop") < 0   //학생 팝업
				&& rURI.indexOf("sysMenu") < 0
				&& rURI.indexOf("help_emp") < 0       //직원 팝업
				&& rURI.indexOf("help_dept") < 0      //조직 팝업 
				&& rURI.indexOf("help_dept_all") < 0      //조직 팝업 
				&& rURI.indexOf("sysUserPwd") < 0     //패스워드 sysUserPwd 
				&& rURI.indexOf("help_school") < 0     //help_school
				&& rURI.indexOf("com_0510") < 0     //help_school
				&& rURI.indexOf("help_jojik") < 0	//조직팝업
				//&& rURI.indexOf("help_user") < 0   //사용자팝업
				&& rURI.indexOf("ghj_0001p") < 0
				//&& rURI.indexOf("sysSessionExpired") < 0
			    ) {
				  
				//System.out.println(rURI);
				DataMap sessLoginUser = (DataMap)request.getSession().getAttribute("loginUser");
				//int strUrlIdx = uri.substring(1);
				//int strProgIdx = rURI.substring(strUrlIdx).indexOf("/");
				String progId = rURI.substring(1);
				    
			    int end = progId.indexOf("/")+1; 
			    progId = progId.substring(end);
			    end = progId.indexOf("/");
			 
			    progId = progId.substring(0,end);
			    //System.out.println("end "+progId); 
				    
				sessLoginUser.put("prog_id", progId);
				String usergb = sessLoginUser.get("user_gb").toString();
				int menuCnt = 0;
				//교원지원자와 일반 사용자 구분 500-> 교원지원자 
				if(usergb.equals("500"))
				{
					menuCnt = authCheckInterceptorService.doCntCntMenuAuthRecruit(sessLoginUser);
				}
				else
				{
					menuCnt = authCheckInterceptorService.doCntCntMenuAuth(sessLoginUser);
				}
				
				if( menuCnt <= 0 ){
					 
					StringBuilder strBuiler = new StringBuilder();
					strBuiler.append("<SCRIPT type=text/javascript>");
					//strBuiler.append("	alert('").append(message).append("');");
					if(path.equals("frame")){
						strBuiler.append("	top.location='/sysUser/doView.do'    ");			
					}else{
						//strBuiler.append("	top.location='/frame/sysUser/doView.do'    ");
						strBuiler.append("	top.location='/deu/main.do'    ");
					} 
					//strBuiler.append("	top.location='frame/main.do'    ");			
					 
					strBuiler.append("</SCRIPT>");
					response.setContentType("text/html; charset=UTF-8");
					response.getWriter().print(strBuiler.toString());
					return false;
				} 
				
			} 
			return true;
		 }catch(Exception e){
			 e.printStackTrace();
			 return true;
		 }
	}
	
	 
}