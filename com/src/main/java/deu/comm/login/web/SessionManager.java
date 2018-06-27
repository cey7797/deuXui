package deu.comm.login.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.nexacro.xapi.data.DataSet;
import com.nexacro.xapi.data.datatype.PlatformDataType;

import deu.comm.unione.vo.DataMap;

public class SessionManager implements HttpSessionListener {

	public static SessionManager sessionManager = null;
	private static ConcurrentHashMap<String, HttpSession> sessionMonitor;


	public SessionManager() {
		if (sessionMonitor == null) sessionMonitor = new ConcurrentHashMap<String, HttpSession>();
		sessionManager = this;
	}
	
	public static synchronized SessionManager getInstance() {
		if (sessionManager == null) sessionManager = new SessionManager();
		return sessionManager;
	}
	
	/**
	 * 활성화 세션 개수를 반환
	 * @return
	 */
	public int getActiveSessionCount() {
		return sessionMonitor.size();
	}
	
	/**
	 * 로그인된 세션 개수를 반환
	 * @return
	 */
	public int getLoginSessionCount() {
		int cnt = 0;
		Enumeration<HttpSession> eNum = sessionMonitor.elements();
		while(eNum.hasMoreElements()) {
			HttpSession sh_session = null;
			try {
				sh_session = (HttpSession)eNum.nextElement();
			} catch(Exception e) {
				continue;
			}
		
			DataMap<String, Object> baseModel = new DataMap<String, Object>();
			
			baseModel.put(((DataSet)sh_session.getAttribute("loginUser")).getString(0, "sessId"), (DataSet)sh_session.getAttribute("loginUser"));

			if(baseModel != null) cnt++;
		}
		return cnt;
	}
	
	/**
	 * 현재 등록된 session의 id목록을 반환한다.
	 * @return
	 */
	public Enumeration<String> getIds() {
		return sessionMonitor.keys();
	}
	
	/**
	 * 활성화 세션 리스트를 반환
	 * @return
	 */
	public ConcurrentHashMap<String, HttpSession> getActiveSessionList() {
		return this.sessionMonitor;
	}
	
	/**
	 * 로그인된 세션 리스트를 반환
	 * @return
	 */
	public Hashtable<String, HttpSession> getLoginSessionList() {
		Hashtable<String, HttpSession> sessionList = new Hashtable<String, HttpSession>();
		Enumeration<HttpSession> eNum = sessionMonitor.elements();
		while(eNum.hasMoreElements()) {
			HttpSession sh_session = null;
			try {
				sh_session = (HttpSession)eNum.nextElement();
			} catch(Exception e) {
				continue;
			}
		
			DataSet baseModel = (DataSet)sh_session.getAttribute("loginUser");
		
			if(baseModel != null) {
				sessionList.put(sh_session.getId(), sh_session);
			}
		}
		return sessionList;
	}


	public boolean checkDuplicationLogin(String sessionId, String member_no) {
		boolean ret = false;

		if(!member_no.equals("dev1")) {
			Enumeration<HttpSession> eNum = sessionMonitor.elements();
	
//			System.out.println("session count : " + getActiveSessionCount());
			
			while(eNum.hasMoreElements()) {
				HttpSession sh_session = null;
				try {
					sh_session = (HttpSession)eNum.nextElement();
				} catch(Exception e) {
					continue;
				}
			
				DataSet baseModel = (DataSet)sh_session.getAttribute("loginUser");
			
				if(baseModel != null) {
			
					if(member_no != null && sessionId != null && member_no.equals(baseModel.getString(0, "memberNo")) && !sessionId.equals(sh_session.getId())) {
						// 전달 받은 사번과(member_no) 기존 세션값 중 사번이 중복 되면
						// 기존 세션을 소멸 시킨다.
						try {
							
							if(sh_session != null) {
								//sh_session.removeAttribute("SSOPRINCIPAL");
								sh_session.removeAttribute("loginUser");
								//sh_session.setAttribute("newLogin", "Y");
							}
							
							ret = true;

						} catch(IllegalStateException e) {
							continue;
						}
					}
				}
			}
		}
		
		return ret;
	}
	
	public DataSet isDuplication(String sessionId, String member_no) {
		DataSet ret = new DataSet();
		
		try {
			if(!member_no.equals("dev1")) {
		
				Enumeration<HttpSession> eNum = sessionMonitor.elements();
				
				while(eNum.hasMoreElements()) {
					HttpSession sh_session = null;
					try {
						sh_session = (HttpSession)eNum.nextElement();
					} catch(Exception e) {
						continue;
					}
					
					DataSet baseModel = (DataSet)sh_session.getAttribute("loginUser");
				
					if(baseModel != null) {
				
						if(member_no.equals(baseModel.getString(0, "memberNo")) && !sessionId.equals(sh_session.getId())) {
							// 전달 받은 사번과(member_no) 기존 세션값 중 사번이 중복 되면
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date dt = new Date();
							dt.setTime(sh_session.getCreationTime());
							
							ret.setChangeStructureWithData(true);
							
							ret.addColumn("isDupYn", PlatformDataType.STRING);
							ret.addColumn("isDupIp", PlatformDataType.STRING);
							ret.addColumn("isDupTime", PlatformDataType.STRING);
							
							ret.set(0, "isDupYn", "Y");
							ret.set(0, "isDupIp", baseModel.getString(0, "ip_addr"));
							ret.set(0, "isDupTime", sdf.format(dt));

							ret.setChangeStructureWithData(false);
							
							break;
						}
					}
				}
			}
		} catch(Exception e) {

		}
		return ret;
	}
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {

		HttpSession session = event.getSession();
		System.out.println("session create : "+session.getId());
		synchronized( sessionMonitor ) {
			sessionMonitor.put(session.getId(), session);
		}
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {

		HttpSession session = event.getSession();
		System.out.println("session destroy : "+session.getId());
		synchronized( sessionMonitor ) {
			sessionMonitor.remove(session.getId());
		}
	}

}
