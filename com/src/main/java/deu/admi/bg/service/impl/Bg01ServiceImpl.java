package deu.admi.bg.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import deu.admi.bg.service.Bg01Service;
import deu.comm.unione.BusinessException;
import deu.comm.unione.ConvertUtil;
import deu.comm.unione.service.impl.CommonDAO;

import com.nexacro.xapi.data.DataSet;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("bg01Service")
public class Bg01ServiceImpl extends EgovAbstractServiceImpl implements Bg01Service{
	 
	@Resource(name = "commonDAO")
	private CommonDAO commonDAO;
	
	
	
	
	
	
	/*#########################################################################################################################################*/
    /* 예산통제자등록 (admi/bg/bg01/bg01060.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 *  
	 */
    public List<?> selectBg01060_01(Map map) throws Exception{
        List<?> list = commonDAO.selectData("bg01DAO.selectBg01060_01", map);
        return list;
    }
    
    
        
    /**
	 *  
	 */
    @Override
    public void saveBg01060_01(DataSet ds) throws Exception{
        String insertQueryId="bg01DAO.insertBg01060_01";
        String updateQueryId="bg01DAO.updateBg01060_01";
        String deleteQueryId="bg01DAO.deleteBg01060_01";
        commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
    }
	
	
	
	
	
}
