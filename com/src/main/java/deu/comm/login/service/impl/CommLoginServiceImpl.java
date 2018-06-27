package deu.comm.login.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nexacro.xapi.data.DataSet;

import deu.comm.login.service.CommLoginService;
import deu.comm.unione.BusinessException;
import deu.comm.unione.service.impl.CommonDAO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("commLoginService")
public class CommLoginServiceImpl extends EgovAbstractServiceImpl implements CommLoginService{
	
	//private static final Logger LOGGER = LoggerFactory.getLogger(ComCsiService.class);

	@Resource(name = "commLoginDAO")
	private CommLoginDAO commLoginDAO;
	
	@Resource(name = "commonDAO")
	private CommonDAO commonDAO;
	
	@Override
	public List<?> doGetLogin(Map map, String sessId, String sessIp) throws Exception{
		//Ip 주소를 map에 추가한다.
		map.put("sessIp", sessIp);

		List list = commLoginDAO.doGetLogin(map);
		
		if(list.size() == 0){
			int cnt = commLoginDAO.doGetUserCount(map);
			if(cnt > 0){
				commLoginDAO.doUpdateTemplate(map);
			}
			
			if("ID".equals(map.get("strLoginGb"))){
				throw new BusinessException("아이디 또는 비밀번호를 확인하십시요.");
			}else{
				throw new BusinessException("인증에 실패하였습니다.");
			}
		}
		
		Map<String, Object> datamap = (Map<String, Object>) list.get(0);

		if(Integer.parseInt(datamap.get("loginFailCnt").toString()) > 30){
			//실패가 5회 이상이면 로그인을 차단한다.
			throw new BusinessException("해당 아이디는 30회 이상 로그인에 실패하였습니다. 관리자에게 문의 바랍니다.");
		}else{
			commLoginDAO.doInitLoginFailCnt(map);
			commLoginDAO.doInitOnetimePwd(map);
			
			map.put("sessId", sessId);	//session Id
			map.put("sessIp", datamap.get("ipAddr"));	//접속 IpAddress
			
			//Login history table insert
			commLoginDAO.doInsertLog(map);
			
			return list;
		}
		
	}
	
	@Override
	public List<?> doGetLoginPop(Map map, String sessId, String sessIp) throws Exception{
		//Ip 주소를 map에 추가한다.
		map.put("sessIp", sessIp);

		List list = commLoginDAO.doGetLoginPop(map);
		
		if(list.size() == 0){
			int cnt = commLoginDAO.doGetUserCount(map);
			if(cnt > 0){
				commLoginDAO.doUpdateTemplate(map);
			}
			
			if("ID".equals(map.get("strLoginGb"))){
				throw new BusinessException("아이디 또는 비밀번호를 확인하십시요.");
			}else{
				throw new BusinessException("인증에 실패하였습니다.");
			}
		}
		
		Map<String, Object> datamap = (Map<String, Object>) list.get(0);

		if(Integer.parseInt(datamap.get("loginFailCnt").toString()) > 30){
			//실패가 5회 이상이면 로그인을 차단한다.
			throw new BusinessException("해당 아이디는 30회 이상 로그인에 실패하였습니다. 관리자에게 문의 바랍니다.");
		}else{
			commLoginDAO.doInitLoginFailCnt(map);
			commLoginDAO.doInitOnetimePwd(map);
			
			map.put("sessId", sessId);	//session Id
			map.put("sessIp", datamap.get("ipAddr"));	//접속 IpAddress
			
			//Login history table insert
			commLoginDAO.doInsertLog(map);
			
			return list;
		}
		
	}
	
	@Override
	public void doLogout(String sessId) throws Exception{
		Map map = new HashMap();
		map.put("sessId", sessId);
		commLoginDAO.doLogout(map);
	}
	
}
