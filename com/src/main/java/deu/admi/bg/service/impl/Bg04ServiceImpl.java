package deu.admi.bg.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import deu.admi.bg.service.Bg04Service;
import deu.comm.unione.BusinessException;
import deu.comm.unione.ConvertUtil;
import deu.comm.unione.service.impl.CommonDAO;

import com.nexacro.xapi.data.DataSet;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("bg04Service")
public class Bg04ServiceImpl extends EgovAbstractServiceImpl implements Bg04Service{
	 
	@Resource(name = "commonDAO")
	private CommonDAO commonDAO;
	
	
	
	/*#########################################################################################################################################*/
    /* 예산통제관리(admi/bg/bg04/bg04010.xfdl)                                                                                                     */
    /*#########################################################################################################################################*/
    
	/**
	 *  
	 */
	public List<?> selectBg04010_01(Map map) throws Exception{
        List<?> list = commonDAO.selectData("bg04DAO.selectBg04010_01", map);
        return list;
	}
	
	
	/**
	 *  
	 */
	public List<?> selectBg04010_02(Map map) throws Exception{
        List<?> list = commonDAO.selectData("bg04DAO.selectBg04010_02", map);
        return list;
	}
	
	
	/**
	 *  
	 */
	public List<?> selectBg04010_ctrler_cnt(Map map) throws Exception{
        List<?> list = commonDAO.selectData("bg04DAO.selectBg04010_ctrler_cnt", map);
        return list;
	}
	
	
	
	/*#########################################################################################################################################*/
    /* 예산통제 결재 처리 SP
    /*#########################################################################################################################################*/
    /**
	 *  
	 */
    
    
    
    @Override
    public HashMap P_AC_CTRLER_APPRO(DataSet ds) throws Exception{
		HashMap map = new HashMap();
		for(int i=0; i<ds.getRowCount(); i++){
			for(int j=0; j<ds.getColumnCount(); j++){
				map.put(ds.getColumn(j).getName(), ds.getString(i, j));
			}
		}
		
		HashMap nMap = new HashMap();
        nMap = commonDAO.saveDataSP("bg04DAO.P_AC_CTRLER_APPRO", map);
        return nMap;
    }
	
	
}
