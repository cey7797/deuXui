package deu.comm.unione;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.nexacro.xapi.data.DataSet;
import com.nexacro.xapi.data.DataSetList;
import com.nexacro.xapi.data.PlatformData;
import com.nexacro.xapi.data.Variable;
import com.nexacro.xapi.data.VariableList;
import com.nexacro.xapi.data.datatype.PlatformDataType;
import com.nexacro.xapi.tx.HttpPlatformRequest;
import com.nexacro.xapi.tx.PlatformException;

public class uniRequest {
	
	public uniRequest(){
		super();
	}
	
	private PlatformData pData = new PlatformData();
	
	public void receivePlatformData(HttpServletRequest request) throws PlatformException  {
		
		HttpPlatformRequest req = new HttpPlatformRequest(request);
		
		//request에 데이터를 받아온다.
		req.receiveData();
		
		pData = new PlatformData();

		setpData(req.getData());
	}
	
	/**
	 * @throws PlatformException 
	 * 
	 */
	public Map getVariable() throws PlatformException{

		VariableList varList = pData.getVariableList();
		List list = varList.valueList();
		HashMap map = new HashMap();
		
		for(int i=0; i<varList.size(); i++){
			Variable vName = varList.get(i);
			map.put(vName.getName(), varList.getString(i));
		}
		return map;
	}
	
	/**
	 * @throws PlatformException 
	 * 
	 */
	public VariableList getVarList() throws PlatformException{

		VariableList varList = pData.getVariableList();

		return varList;
	}
	
	/**
	 * getDataSetList 
	 * 
	 */
	public List getDataSet(String dsName) throws PlatformException{

		DataSetList dsList = new DataSetList();
		dsList = pData.getDataSetList();

		DataSet ds = new DataSet();
		ds = dsList.get(dsName);
		ds.setSaveType(1);//SAVE_TYPE_ALL
		
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		String header = "";
		String value = "";
		
		int rowCnt = 0;
		
		rowCnt = ds.getRowCount()+ds.getRemovedRowCount();
		for(int i=0; i<rowCnt; i++){
			Map<String, Object> map = new HashMap<String, Object>();
			
			if(ds.getRowCount()>0){
				for(int j=0; j<ds.getColumnCount(); j++){
					header = ds.getColumn(j).getName();
					value = ds.getString(i, j);
					map.put(header, value);
				}
			}
			
			if(ds.getRemovedRowCount()>0){
				ds.setRowType(i, DataSet.ROW_TYPE_DELETED);
				for(int j=0; j<ds.getColumnCount(); j++){
					header = ds.getColumn(j).getName();
					value = ds.getRemovedStringData(i, j);
					map.put(header, value);
				}
			}
			
			if(ds.getRowType(i) == DataSet.ROW_TYPE_INSERTED) {
				map.put("rowStatus", "insert"); // 1
			} else if(ds.getRowType(i) == DataSet.ROW_TYPE_UPDATED) {
				map.put("rowStatus", "update"); // 2
			} else if (ds.getRowType(i) == DataSet.ROW_TYPE_DELETED) {
				map.put("rowStatus", "delete"); // 3
			}
			
			list.add(map);
		}
		
		return list;
	}
	
	/**
	 * getDataSetList 
	 * 
	 */
	public DataSet getDS(String dsName) throws PlatformException{
		DataSetList dsList = new DataSetList();
		VariableList varList = new VariableList();
		
		dsList = pData.getDataSetList();
		varList = pData.getVariableList();

		DataSet ds = dsList.get(dsName);
		ds.setSaveType(1);//SAVE_TYPE_ALL
		
		int rowCnt = 0;
		rowCnt = ds.getRowCount()+ds.getRemovedRowCount();
		
		ds.setChangeStructureWithData(true);
		ds.addColumn("gvLangFg", PlatformDataType.STRING);
		ds.setChangeStructureWithData(false);
		
		for(int i=0; i<rowCnt; i++){
			if(DataSet.ROW_TYPE_INSERTED == ds.getRowType(i)) {
				ds.set(i, "gvLangFg", varList.get("gv_langFg"));
				ds.set(i, "rowStatus","insert"); //1	
			} else if(DataSet.ROW_TYPE_UPDATED == ds.getRowType(i)) {
				ds.set(i, "gvLangFg", varList.get("gv_langFg"));
				if("8".equals(ds.getString(i, "rowStatus"))){
					ds.set(i, "gvLangFg", varList.get("gv_langFg"));
					ds.set(i, "rowStatus","delete"); // 3
				}else if("10".equals(ds.getString(i, "rowStatus"))){
					ds.set(i, "gvLangFg", varList.get("gv_langFg"));
					ds.set(i, "rowStatus","check"); // 10 (임의생성)
				}else{
					ds.set(i, "gvLangFg", varList.get("gv_langFg"));
					ds.set(i, "rowStatus","update"); // 2
				}
			} else {
				//not execute
				ds.set(i, "gvLangFg", varList.get("gv_langFg"));
				ds.set(i, "rowStatus", "normal");//normal
			}
		}
		
		return ds;
	}
	
	/**
	 * getDataSetList 
	 * 
	 */
	public DataSetList getDataSetList() throws PlatformException{
		DataSetList dsList = new DataSetList();
		DataSetList rtnDsList = new DataSetList();
		VariableList varList = new VariableList();
		
		dsList = pData.getDataSetList();
		varList = pData.getVariableList();

		for(int j=0; j<dsList.size(); j++) {
		
			DataSet ds = dsList.get(j);
			ds.setSaveType(1);//SAVE_TYPE_ALL
			
			int rowCnt = 0;
			rowCnt = ds.getRowCount()+ds.getRemovedRowCount();
			
			ds.setChangeStructureWithData(true);
			ds.addColumn("gvLangFg", PlatformDataType.STRING);
			ds.setChangeStructureWithData(false);
			
			for(int i=0; i<rowCnt; i++){
				if(DataSet.ROW_TYPE_INSERTED == ds.getRowType(i)) {
					ds.set(i, "gvLangFg", varList.get("gv_langFg"));
					ds.set(i, "rowStatus","insert"); //1	
				} else if(DataSet.ROW_TYPE_UPDATED == ds.getRowType(i)) {
					ds.set(i, "gvLangFg", varList.get("gv_langFg"));
					if("8".equals(ds.getString(i, "rowStatus"))){
						ds.set(i, "gvLangFg", varList.get("gv_langFg"));
						ds.set(i, "rowStatus","delete"); // 3
					}else if("10".equals(ds.getString(i, "rowStatus"))){
						ds.set(i, "gvLangFg", varList.get("gv_langFg"));
						ds.set(i, "rowStatus","check"); // 10 (임의생성)
					}else{
						ds.set(i, "gvLangFg", varList.get("gv_langFg"));
						ds.set(i, "rowStatus","update"); // 2
					}
				} else {
					//not execute
					ds.set(i, "gvLangFg", varList.get("gv_langFg"));
					ds.set(i, "rowStatus", "normal");//normal
				}
			}
		
			rtnDsList.add(ds);
			
		}
		
		return rtnDsList;
	}
	
	public PlatformData getpData() {
		return pData;
	}

	public void setpData(PlatformData reqData) {
		pData = reqData;
	}

}
