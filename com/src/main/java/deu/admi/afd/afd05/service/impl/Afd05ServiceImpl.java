package deu.admi.afd.afd05.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import deu.admi.afd.afd05.service.Afd05Service;
import deu.comm.unione.ConvertUtil;
import deu.comm.unione.service.impl.CommonDAO;

import com.nexacro.xapi.data.DataSet;
import com.nexacro.xapi.util.StringUtils;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("afd05Service")
public class Afd05ServiceImpl extends EgovAbstractServiceImpl implements Afd05Service{
	@Resource(name = "commonDAO")
	private CommonDAO commonDAO;

	/**
	 * 연말정산기부금 확정총괄 조회
	 */
	@Override
	public List<?> selectExcel(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd05SQL.selectExcel", map);
		return resultList;
	}
	
	/**
	 * 추천인목록 조회
	 */
	@Override
	public List<?> selectExcel_02(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd05SQL.selectExcel_02", map);
		return resultList;
	}
	
	/**
	 * 지정/일반 총괄표
	 */
	@Override
	public List<?> selectExcel_03(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd05SQL.selectExcel_03", map);
		return resultList;
	}

}
