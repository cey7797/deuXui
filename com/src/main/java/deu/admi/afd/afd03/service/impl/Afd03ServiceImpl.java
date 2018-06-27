package deu.admi.afd.afd03.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import deu.admi.afd.afd03.service.Afd03Service;
import deu.comm.unione.ConvertUtil;
import deu.comm.unione.service.impl.CommonDAO;
import deu.comm.unione.service.impl.MsSmsDAO;

import com.nexacro.xapi.data.DataSet;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("afd03Service")
public class Afd03ServiceImpl extends EgovAbstractServiceImpl implements Afd03Service{
	@Resource(name = "commonDAO")
	private CommonDAO commonDAO;
	
	@Resource(name = "msSmsDAO")
	private MsSmsDAO msSmsDAO;


    /*#########################################################################################################################################*/
    /* 기부금영수증발급(admi/afd/afd03/afd03010.xfdl)                                                                                                  */
    /*#########################################################################################################################################*/


	/**
	 * 기부자목록을 조회한다.
	 */
	@Override
	public List<?> retrieveRcpter(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd03SQL.retrieveRcpter", map);
		return resultList;
	}

	/**
	 * 입금내역을 조회한다.
	 */
	@Override
	public List<?> retrieveRcpmny(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd03SQL.retrieveRcpmny", map);
		return resultList;
	}

	/**
	 * 기부금 영수증정보를 저장한다.
	 * @return 
	 */
	@Override
	public List<?> saveRciptissu(DataSet ds) throws Exception {
		
		HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, 0);
		
		String receiptNo = commonDAO.selectString("Afd03SQL.createReceiptNo", hm);
		hm.put("receiptNo", receiptNo);
		
		for (int i = 1; i < ds.getRowCount(); i++) {
			HashMap<String, Object> hm2 = ConvertUtil.getDStoMap(ds, i);
			hm2.put("receiptNo", receiptNo);
			
			commonDAO.updateData("Afd03SQL.updateNo1", hm2);//무통장
			commonDAO.updateData("Afd03SQL.updateNo2", hm2);//CMS
			commonDAO.updateData("Afd03SQL.updateNo3", hm2);//급여공제
		}
		
		commonDAO.insertData("Afd03SQL.saveRciptissu", hm);//영수증발급저장
		
		//commonDAO.saveData("Afd03SQL.saveRciptissu", "", "", ds);
		
		return selectReceipNo(hm);
	}
	
	@Override
	public HashMap createRciptissu(DataSet ds) throws Exception {
		
		HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, 0);
		
		hm = commonDAO.saveDataSP("Afd03SQL.P_AFD_CREATE_RCIPTISSU_PROC", hm);

		return hm;
	}
	
	@Override
	public List<?> selectReceipNo(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd03SQL.selectReceiptNo", map);
		return resultList;
	}

	@Override
	public List<?> test(Map map) throws Exception {
		List<?> resultList = msSmsDAO.selectData("Afd03SQL.test", map);
		return resultList;
	}
}
