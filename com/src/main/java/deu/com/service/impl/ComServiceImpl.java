package deu.com.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import deu.com.service.ComService;
import deu.com.vo.FileUpVO;
import deu.comm.unione.ConvertUtil;
import deu.comm.unione.service.impl.CommonDAO;
import deu.comm.unione.service.impl.MsSmsDAO;

import com.nexacro.xapi.data.DataSet;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("comService")
public class ComServiceImpl extends EgovAbstractServiceImpl implements ComService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ComService.class);

	@Resource(name = "commonDAO")
	private CommonDAO commonDAO;
	
	@Resource(name = "msSmsDAO")
	private MsSmsDAO msSmsDAO;
	
	@Resource(name="txManager")
    private PlatformTransactionManager txManager;
	
	@Override
	public List<?> selectComCd(Map map) throws Exception{
		List<?> list = commonDAO.selectData("comDAO.selectComCd", map);
		return list;
	}
	
	@Override
	public void saveComCd(DataSet ds) throws Exception{
		String insertQueryId="comDAO.insertComCd";
		String updateQueryId="comDAO.updateComCd";
		String deleteQueryId="comDAO.deleteComCd";
		commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
	}
	
	@Override
	public List<?> selectComCdSub(Map map) throws Exception{
		List<?> list = commonDAO.selectData("comDAO.selectComCdSub", map);
		return list;
	}
	
	@Override
	public void saveComCdSub(DataSet ds) throws Exception{
		String insertQueryId="comDAO.insertComCdSub";
		String updateQueryId="comDAO.updateComCdSub";
		String deleteQueryId="comDAO.deleteComCdSub";
		
		if(!ds.hasData()) {
			return;
		}
		
		for(int i=0; i<ds.getRowCount(); i++) {
			
			/*if("COM_CD_DIV01".equals(ds.getString(i, "comCdDiv"))) {

				LOGGER.error("변경할 수 없는 공통코드입니다.");
				
				throw new BusinessException("변경할 수 없는 공통코드입니다.[-200]");	
				
			} else {*/
				
				String rowType = ds.getString(i, "rowStatus");
				HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
				
				//commonDAO.insertData("comDAO.scheduleData", hm);
				
				if("insert".equals(rowType)){
					commonDAO.insert(insertQueryId, hm);
		        }else if("update".equals(rowType)){
		        	commonDAO.update(updateQueryId, hm);
		        }else if("delete".equals(rowType)){
		        	commonDAO.delete(deleteQueryId, hm);
		        }					
			//}
			
		}
		
	}
			
	@Override
	public List<?> selectComCdCombo(Map map) throws Exception{
		List<?> list = commonDAO.selectComCdCombo("comDAO.selectComCdCombo", map);
		return list;
	}
	
	@Override
	public List<?> selectComCdComboOrder(Map map) throws Exception{
		List<?> list = commonDAO.selectComCdCombo("comDAO.selectComCdComboOrder", map);
		return list;
	}

	@Override
	public List<?> selectZipPopUp(Map map) throws Exception{
		List<?> list = commonDAO.selectData("comDAO.selectZipPopUp", map);
		return list;
	}
	
	@Override
	public List<?> selectComCsi07(Map map) throws Exception{
		List<?> list = commonDAO.selectData("comDAO.selectComCsi07", map);
		return list;
	}
	
	@Override
	public void saveComCsi07(DataSet ds) throws Exception{
		for (int i = 0; i < ds.getRowCount(); i++) {
			 
			String rowType = ds.getString(i, "rowStatus");
			HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
			
			if("insert".equals(rowType)){
				commonDAO.insert("comDAO.insertComCsi07", hm);
	        }else if("update".equals(rowType)){
	        	commonDAO.update("comDAO.updateComCsi07", hm);
	        }else if("delete".equals(rowType)){
	        	commonDAO.delete("comDAO.deleteComCsi07", hm);
	        	commonDAO.delete("comDAO.deleteComCsi07Sub", hm);
	        }
	    }
	}
	
	@Override
	public List<?> selectComCsi07Sub(Map map) throws Exception{
		List<?> list = commonDAO.selectData("comDAO.selectComCsi07Sub", map);
		return list;
	}
	
	@Override
	public void saveComCsi07Sub(DataSet ds) throws Exception{
		for (int i = 0; i < ds.getRowCount(); i++) {
			 
			String rowType = ds.getString(i, "rowStatus");
			HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
			
			if("insert".equals(rowType)){
				commonDAO.insert("comDAO.insertComCsi07Sub", hm);
	        }else if("update".equals(rowType)){
	        	commonDAO.update("comDAO.updateComCsi07Sub", hm);
	        }else if("delete".equals(rowType)){
	        	commonDAO.delete("comDAO.deleteComCsi07Sub", hm);
	        }
	    }
	}
	
	@Override
	public List<?> selectComCsi08(Map map) throws Exception{
		List<?> list = commonDAO.selectData("comDAO.selectComCsi08", map);
		return list;
	}
	
	@Override
	public void saveComCsi08(DataSet ds) throws Exception{
		String insertQueryId="comDAO.insertComCsi08";
		String updateQueryId="comDAO.updateComCsi08";
		String deleteQueryId="comDAO.deleteComCsi08";
		commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
	}
	
	@Override
	public List<?> selectComCsi08Sub(Map map) throws Exception{
		List<?> list = commonDAO.selectData("comDAO.selectComCsi08Sub", map);
		return list;
	}
	
	@Override
	public void saveComCsi08Sub(DataSet ds) throws Exception{
		String insertQueryId="comDAO.insertComCsi08Sub";
		String updateQueryId="comDAO.updateComCsi08Sub";
		String deleteQueryId="comDAO.deleteComCsi08Sub";
		commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
	}
	
	@Override
	public List<?> selectRoleGroup(Map map) throws Exception{
		List<?> list = commonDAO.selectData("comDAO.selectRoleGroup", map);
		return list;
	}
	
	@Override
	public List<?> selectRoleGroupCmb(Map map) throws Exception{
		List<?> list = commonDAO.selectData("comDAO.selectRoleGroupCmb", map);
		return list;
	}
	
	@Override
	public void saveRoleGroup(DataSet ds) throws Exception{
		String insertQueryId="comDAO.insertRoleGroup";
		String updateQueryId="comDAO.updateRoleGroup";
		String deleteQueryId="comDAO.deleteRoleGroup";
		commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
	}
	
	@Override
	public List<?> selectRoleGroupUser(Map map) throws Exception{
		List<?> list = commonDAO.selectData("comDAO.selectRoleGroupUser", map);
		return list;
	}
	
	@Override
	public List<?> selectRoleGroupPgm(Map map) throws Exception{
		List<?> list = commonDAO.selectData("comDAO.selectRoleGroupPgm", map);
		return list;
	}
	
	@Override
	public List<?> selectRolePgm(Map map) throws Exception{
		List<?> list = commonDAO.selectData("comDAO.selectRolePgm", map);
		return list;
	}
	
	@Override
	public List<?> selectRolePgmTree(Map map) throws Exception{
		List<?> list = commonDAO.selectData("comDAO.selectRolePgmTree", map);
		return list;
	}
	
	@Override
	public void saveRolePgm(DataSet ds) throws Exception{
		String insertQueryId="comDAO.insertRolePgm";
		String updateQueryId="comDAO.updateRolePgm";
		String deleteQueryId="comDAO.deleteRolePgm";
		commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
	}
	
	@Override
	public void saveRolePgmNonRegi(DataSet ds) throws Exception{
		for (int i = 0; i < ds.getRowCount(); i++) {
			 
			String rowType = ds.getString(i, "rowStatus");
			HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
			
			//if("check".equals(rowType)){
				commonDAO.update("comDAO.updateRolePgmDel", hm);
	        //}
	    }
	}
	
	@Override
	public void saveRolePgmRegi(DataSet ds) throws Exception{
		for (int i = 0; i < ds.getRowCount(); i++) {
			 
			String rowType = ds.getString(i, "rowStatus");
			for(int j=ds.getRowCount(); j >= 0; j--){
				if(ds.getInt(j, "treeLevel") != 4){
					ds.removeRow(j);
				}
			}
			HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
			if("update".equals(rowType)){
				commonDAO.update("comDAO.updateRolePgmInst", hm);
	        }
	    }
	}
	
	@Override
	public List<?> selectRolePgmDet(Map map) throws Exception{
		List<?> list = commonDAO.selectData("comDAO.selectRolePgmDet", map);
		return list;
	}
	
	@Override
	public List<?> selectRolePgmDetTree(Map map) throws Exception{
		List<?> list = commonDAO.selectData("comDAO.selectRolePgmDetTree", map);
		return list;
	}
	
	@Override
	public List<?> selectUserPgm(Map map) throws Exception{
		List<?> list = commonDAO.selectData("comDAO.selectUserPgm", map);
		return list;
	}
	
	@Override
	public void saveUserPgmInst(DataSet ds) throws Exception{
		for(int i=0; i<ds.getRowCount(); i++){
    		HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
    		commonDAO.insert("comDAO.updateUserPgmInst", hm);
    	}
	}
	
	@Override
	public void saveUserPgmDel(DataSet ds) throws Exception{
		for(int i=0; i<ds.getRowCount(); i++){
    		HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
    		commonDAO.insert("comDAO.updateUserPgmDel", hm);
    	}
	}
	
	@Override
	public List<?> selectNonRegiPgm(Map map) throws Exception{
		List<?> list = commonDAO.selectData("comDAO.selectNonRegiPgm", map);
		return list;
	}
	
	@Override
	public List<?> selectRegiPgm(Map map) throws Exception{
		List<?> list = commonDAO.selectData("comDAO.selectRegiPgm", map);
		return list;
	}
	
	@Override
	public List<?> selectRankRegiPgm(Map map) throws Exception{
		List<?> list = commonDAO.selectData("comDAO.selectRankRegiPgm", map);
		return list;
	}
	
	@Override
	public List<?> selectUnrankRegiPgm(Map map) throws Exception{
		List<?> list = commonDAO.selectData("comDAO.selectUnrankRegiPgm", map);
		return list;
	}
	
	@Override
	public List<?> selectUserGb(Map map) throws Exception{
		List<?> list = commonDAO.selectData("comDAO.selectUserGb", map);
		return list;
	}
	
	@Override
	public void saveRankRegiPgm(DataSet ds) throws Exception{
		String insertQueryId="";
		String updateQueryId="comDAO.updateRankRegiPgm";
		String deleteQueryId="";
		commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
	}
	
	@Override
	public void saveUnrankRegiPgm(DataSet ds) throws Exception{
		String insertQueryId="";
		String updateQueryId="comDAO.updateUnrankRegiPgm";
		String deleteQueryId="";
		commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
	}
	
	@Override
	public void saveSmsSend(DataSet ds) throws Exception{
		String insertQueryId="comDAO.insertSmsSend";
		String updateQueryId="";
		String deleteQueryId="";
		commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
	}
	
	@Override
	public List<?> selectCurrentDate(Map map) throws Exception{
		List<?> list = commonDAO.selectData("comDAO.selectCurrentDate", map);
		return list;
	}
	
	@Override
	public List<?> selectUserGroup(Map map) throws Exception{
		List<?> list = commonDAO.selectData("comDAO.selectUserGroup", map);
		return list;
	}

	@Override
	public FileUpVO selectComFileMasterNo() throws Exception{
		return (FileUpVO) commonDAO.select("comDAO.selectComFileMasterNo");		
	}
	
	@Override
	public List<?> selectComFile(Map map) throws Exception{
		List<?> list = commonDAO.selectData("comDAO.selectComFile", map);
		return list;
	}
	
	@Override
	public void saveComFile(DataSet ds) throws Exception{
		String insertQueryId="comDAO.insertComFile";
		String updateQueryId="comDAO.updateComFile";
		String deleteQueryId="comDAO.deleteComFile";
		commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
	}
	
	@Override
	public List<?> selectComFileSub(Map map) throws Exception{
		List<?> list = commonDAO.selectData("comDAO.selectComFileSub", map);
		return list;
	}
	
	@Override
	public void saveComFileSub(DataSet ds) throws Exception{
		String insertQueryId="comDAO.insertComFileSub";
		String updateQueryId="comDAO.updateComFileSub";
		String deleteQueryId="comDAO.deleteComFileSub";
		commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
	}
	
	@Override
    public void sendSms(DataSet ds) throws Exception{
		for(int i=0; i<ds.getRowCount(); i++){
			HashMap map = new HashMap();
			for(int j=0; j<ds.getColumnCount(); j++){
				map.put(ds.getColumn(j).getName(), ds.getString(i, j));
			}
			msSmsDAO.insertData("msSmsDAO.insertSms", map);
		}		
    }
	
	@Override
    public void sendMessage(DataSet ds) throws Exception{
		for(int i=0; i<ds.getRowCount(); i++){
			HashMap map = new HashMap();
			for(int j=0; j<ds.getColumnCount(); j++){
				map.put(ds.getColumn(j).getName(), ds.getString(i, j));
			}
			//msSmsDAO.insertData("msSmsDAO.insertSms", map);
		}		
    }
}
