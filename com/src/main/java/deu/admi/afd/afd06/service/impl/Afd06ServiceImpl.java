package deu.admi.afd.afd06.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import deu.admi.afd.afd06.service.Afd06Service;
import deu.comm.unione.ConvertUtil;
import deu.comm.unione.service.impl.CommonDAO;

import com.nexacro.xapi.data.DataSet;
import com.nexacro.xapi.util.StringUtils;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("afd06Service")
public class Afd06ServiceImpl extends EgovAbstractServiceImpl implements Afd06Service{
	@Resource(name = "commonDAO")
	private CommonDAO commonDAO;

	@Override
	public List<?> selectAfdAcntNoList(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd06SQL.selectAfdAcntNoList", map);
		return resultList;
	}
	
	@Override
    public void saveAfd06010MainList(DataSet ds) throws Exception{
        String insertQueryId="Afd06SQL.insertAfd06010MainList";
        String updateQueryId="Afd06SQL.updateAfd06010MainList";
        String deleteQueryId="Afd06SQL.deleteAfd06010MainList";
        commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
    }
	
	@Override
	public List<?> selectAFD06020(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd06SQL.selectAFD06020", map);
		return resultList;
	}
	
	@Override
    public void saveAFD06020(DataSet ds) throws Exception{
        String insertQueryId="Afd06SQL.updateAfd06020MainList";
        String updateQueryId="Afd06SQL.updateAfd06020MainList";
        String deleteQueryId="Afd06SQL.updateAfd06020MainList";
        commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
    }
	
	@Override
	public List<?> selectAFD06030(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd06SQL.selectAFD06030", map);
		return resultList;
	}
	
	@Override
    public void saveAFD06030(DataSet ds) throws Exception{
        String insertQueryId="Afd06SQL.insertAfd06030MainList";
        String updateQueryId="Afd06SQL.updateAfd06030MainList";
        String deleteQueryId="Afd06SQL.deleteAfd06030MainList";
        commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
    }
	
	@Override
	public List<?> selectAFD06040(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd06SQL.selectAFD06040", map);
		return resultList;
	}
	
	@Override
    public void saveAFD06040(DataSet ds) throws Exception{
        String insertQueryId="Afd06SQL.insertAfd06040MainList";
        String updateQueryId="Afd06SQL.updateAfd06040MainList";
        String deleteQueryId="Afd06SQL.deleteAfd06040MainList";
        commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
    }
	
	@Override
	public List<?> selectAFD06050(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd06SQL.selectAFD06050", map);
		return resultList;
	}
	
	@Override
    public void saveAFD06050(DataSet ds) throws Exception{
        String insertQueryId="Afd06SQL.insertAfd06050MainList";
        String updateQueryId="Afd06SQL.updateAfd06050MainList";
        String deleteQueryId="Afd06SQL.deleteAfd06050MainList";
        commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
    }
	
	@Override
	public List<?> selectAFD06060(Map map) throws Exception {
		List<?> resultList = commonDAO.selectData("Afd06SQL.selectAFD06060", map);
		return resultList;
	}
	
	@Override
    public void saveAFD06060(DataSet ds) throws Exception{
        String insertQueryId="Afd06SQL.insertAfd06060MainList";
        String updateQueryId="Afd06SQL.updateAfd06060MainList";
        String deleteQueryId="Afd06SQL.deleteAfd06060MainList";
        commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
    }
}
