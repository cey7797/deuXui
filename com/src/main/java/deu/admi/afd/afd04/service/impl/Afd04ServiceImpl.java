package deu.admi.afd.afd04.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import deu.admi.afd.afd04.service.Afd04Service;
import deu.comm.unione.ConvertUtil;
import deu.comm.unione.service.impl.CommonDAO;

import com.nexacro.xapi.data.DataSet;
import com.nexacro.xapi.util.StringUtils;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("afd04Service")
public class Afd04ServiceImpl extends EgovAbstractServiceImpl implements Afd04Service{
	@Resource(name = "commonDAO")
	private CommonDAO commonDAO;

    
    /*#########################################################################################################################################*/
    /* 온라인약정(admi/afd/afd04/afd04010.xfdl)                                                                                                  */
    /*#########################################################################################################################################*/


	/**
	 * 온라인 약정을 저장한다.
	 */
	@Override
	public void saveOnlieCntrct(DataSet ds) throws Exception {
		
		for (int i = 0; i < ds.getRowCount(); i++) {
			HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
			
			String memberNo = commonDAO.selectString("Afd04SQL.retrieveMemberNo", hm);
			if(StringUtils.isEmpty(memberNo)) {
				memberNo =  commonDAO.selectString("Afd04SQL.createAfdMemberNo", hm);
			}
			
			hm.put("memberNo", memberNo);
			commonDAO.insertData("Afd04SQL.saveOnlieCntrct", hm);
	    }
		
	}

    
    /*#########################################################################################################################################*/
    /* 나의기부현황(admi/afd/afd04/afd04020.xfdl)                                                                                                  */
    /*#########################################################################################################################################*/


	/**
	 * 나의 기부현황을 조회한다.
	 */
	@Override
	public List<?> retrieveDonationStatus(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd04SQL.retrieveDonationStatus", map);
		return resultList;
	}

	/**
	 *기금코드/명을 조회한다.
	 */
	@Override
	public List<?> retrieveEndw(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd04SQL.retrieveEndw", map);
		return resultList;
	}
	/**
	 * 로그인 인증
	 */
	@Override
	public List<?> selectAFDMemberYn(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd04SQL.selectAFDMemberYn", map);
		return resultList;
	}
}
