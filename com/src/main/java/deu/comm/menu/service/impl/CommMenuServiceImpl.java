package deu.comm.menu.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nexacro.xapi.data.DataSet;

import deu.comm.menu.service.CommMenuService;
import deu.comm.unione.service.impl.CommonDAO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("commMenuService")
public class CommMenuServiceImpl extends EgovAbstractServiceImpl implements CommMenuService{
	
	//private static final Logger LOGGER = LoggerFactory.getLogger(ComCsiService.class);

	@Resource(name = "commonDAO")
	private CommonDAO commonDAO;
	
	@Override
	public List<?> selectSysMenu(Map map) throws Exception{
		List<?> list = commonDAO.selectData("commMenuDAO.selectSysMenu", map);
		return list;
	}
	
	@Override
	public List<?> selectSysCombo(Map map) throws Exception{
		List<?> list = commonDAO.selectData("commMenuDAO.selectSysCombo", map);
		return list;
	}
	
	@Override
	public List<?> selectSubMenu(Map map) throws Exception{
		List<?> list = commonDAO.selectData("commMenuDAO.selectSubMenu", map);
		return list;
	}
	
	@Override
	public List<?> selectSubCombo(Map map) throws Exception{
		List<?> list = commonDAO.selectData("commMenuDAO.selectSubCombo", map);
		return list;
	}
	
	@Override
	public List<?> selectLeftMenu(Map map) throws Exception{
		List<?> list = commonDAO.selectData("commMenuDAO.selectLeftMenu", map);
		return list;
	}
	
	@Override
	public List<?> selectTreeMenu(Map map) throws Exception{
		List<?> list = commonDAO.selectData("commMenuDAO.selectTreeMenu", map);
		return list;
	}
	
	@Override
	public List<?> selectTreeMenuPop(Map map) throws Exception{
		List<?> list = commonDAO.selectData("commMenuDAO.selectTreeMenuPop", map);
		return list;
	}
	
	@Override
	public List<?> selectTreeFavo(Map map) throws Exception{
		List<?> list = commonDAO.selectData("commMenuDAO.selectTreeFavo", map);
		return list;
	}
	
	@Override
	public List<?> selectTreeFavoPop(Map map) throws Exception{
		List<?> list = commonDAO.selectData("commMenuDAO.selectTreeFavoPop", map);
		return list;
	}
	
	@Override
	public void saveTreeFavo(DataSet ds) throws Exception{
		String insertQueryId="commMenuDAO.insertTreeFavo";
		String updateQueryId="";
		String deleteQueryId="commMenuDAO.deleteTreeFavo";
		commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
	}
	
	@Override
	public List<?> selectMenuCombo(Map map) throws Exception{
		List<?> list = commonDAO.selectData("commMenuDAO.selectMenuCombo", map);
		return list;
	}
	
	@Override
	public List<?> selectProgCombo(Map map) throws Exception{
		List<?> list = commonDAO.selectData("commMenuDAO.selectProgCombo", map);
		return list;
	}
	
	@Override
	public List<?> selectDeptCombo(Map map) throws Exception{
		List<?> list = commonDAO.selectData("commMenuDAO.selectDeptCombo", map);
		return list;
	}
	
	@Override
	public List<?> selectMenuPopInfo(Map map) throws Exception{
		List<?> list = commonDAO.selectData("commMenuDAO.selectMenuPopInfo", map);
		return list;
	}
	
}
