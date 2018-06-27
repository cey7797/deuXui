package deu.comm.unione;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.nexacro.xapi.data.DataSet;
import com.nexacro.xapi.data.DataSetList;
import com.nexacro.xapi.data.PlatformData;
import com.nexacro.xapi.data.VariableList;
import com.nexacro.xapi.data.datatype.PlatformDataType;
import com.nexacro.xapi.tx.HttpPlatformResponse;
import com.nexacro.xapi.tx.PlatformException;
import com.nexacro.xapi.tx.PlatformType;

public class uniResponse {
	
	private PlatformData pData = new PlatformData();
	
	public void sendPlatformData(HttpServletResponse response) throws PlatformException  {
		
		HttpPlatformResponse pRes = new HttpPlatformResponse(response, PlatformType.CONTENT_TYPE_XML, "utf-8");

		VariableList resultList = pData.getVariableList();
		if(resultList.get("ErrorCode") == null){
			if(resultList.get("OUT_RTN")!=null){
				resultList.add("ErrorCode", resultList.getInt("OUT_RTN"));
				resultList.add("ErrorMsg", resultList.getString("OUT_MSG"));
			}else{
				resultList.add("ErrorCode", "0");
				resultList.add("ErrorMsg", "");
			}
		}
		
		pData.setVariableList(resultList);
		
		pRes.setData(pData);
		
		pData = new PlatformData();
		
		pRes.sendData();
		
	}
	
	/**
	 * @throws PlatformException 
	 * 
	 */
	public void setVariables(HashMap map) throws PlatformException{
		
		VariableList varList = pData.getVariableList();

		Iterator key = map.keySet().iterator();
		Iterator value = map.values().iterator();
		
		for(int i=0; i<map.size(); i++) {
			if(value.hasNext()){
				varList.add(key.next().toString(), value.next());				
			}
		}
		
		pData.setVariableList(varList);
		
	}
	
	/**
	 * @throws PlatformException 
	 * 
	 */
	public void setVariable(String key, Object value) throws PlatformException{
		
		VariableList varList = pData.getVariableList();

		varList.add(key, value);				
		
		pData.setVariableList(varList);
		
	}
	
	/**
	 * getDataSetList 
	 * 
	 */
	public void setDataSet(HashMap map, String dsName) throws PlatformException{
		DataSetList dsList = pData.getDataSetList();
		DataSet ds = new DataSet(dsName);
		
		int colCnt = map.keySet().size();
		int rowCnt = 1;
		
		Set<String> headerSet = map.keySet();
		Iterator<String> header = headerSet.iterator();
		
		Collection headerRowSet = map.values();
		Iterator headerRowData = headerRowSet.iterator();
		//column header add
		for(int i=0; i<colCnt; i++){
			String columnName = header.next();
			// getType으로 switch문 사용으로 값 return method구현필요
			Object target = headerRowData.next();
			
			if(target == null) {
				ds.addColumn(columnName, PlatformDataType.STRING);
			} else {
				if (target.getClass().getSimpleName().equals(String.class.getSimpleName())) {
					ds.addColumn(columnName, PlatformDataType.STRING);
				} else if (target.getClass().getSimpleName().equals(Integer.class.getSimpleName())) {
					ds.addColumn(columnName, PlatformDataType.INT);
				} else if (target.getClass().getSimpleName().equals(Boolean.class.getSimpleName())) {
					ds.addColumn(columnName, PlatformDataType.BOOLEAN);
				} else if (target.getClass().getSimpleName().equals(BigDecimal.class.getSimpleName())) {
					ds.addColumn(columnName, PlatformDataType.BIG_DECIMAL);
				} else if (target.getClass().getSimpleName().equals(Long.class.getSimpleName())) {
					ds.addColumn(columnName, PlatformDataType.LONG);
				} else if (target.getClass().getSimpleName().equals(Double.class.getSimpleName())) {
					ds.addColumn(columnName, PlatformDataType.DOUBLE);
				} else if (target.getClass().getSimpleName().equals(Date.class.getSimpleName())) {
					ds.addColumn(columnName, PlatformDataType.DATE);
				} else if (target.getClass().getSimpleName().equals(Timestamp.class.getSimpleName())) {
					ds.addColumn(columnName, PlatformDataType.DATE_TIME);
				} else if (target.getClass().getSimpleName().equals(Byte[].class.getSimpleName())) {
					ds.addColumn(columnName, PlatformDataType.BLOB);
				} else {
					ds.addColumn(columnName, PlatformDataType.STRING);		
				}
			}
		}
		
		int dummyPos = ds.indexOfColumn("ONLY_METADATA_RETURN");
		
		if(dummyPos < 0) {
			//row data add
			for(int i=0; i<rowCnt; i++) {
				Collection rowSet = map.values();
	
				Iterator rowData = rowSet.iterator();
				
				int rowNum = ds.newRow();
				
				for(int j=0; j<colCnt; j++) {
					Object rowdata = rowData.next();
					if(rowdata == null) {
							ds.set(rowNum, j, "" );
					} else {
							ds.set(rowNum, j, rowdata);	
					}
				}
			}
		} else {
			ds.removeColumn("ONLY_METADATA_RETURN");	
		}
		
		dsList.add(ds);
		
		pData.setDataSetList(dsList);
		
	}
	
	
	
	
	/**
	 * getDataSetList 
	 * 
	 */
	public void setDataSet(List list, String dsName) throws PlatformException{
		DataSetList dsList = pData.getDataSetList();
		DataSet ds = new DataSet(dsName);
		
		int colCnt = 0;
		int rowCnt = 0;
		if(list != null) {
			 colCnt = ((Map) list.get(0)).keySet().size();
			 rowCnt =list.size();
		}
		//int colCnt = 0;// ((Map) list.get(0)).keySet().size();
		//int rowCnt =0;// list.size();
		
		Set<String> headerSet = ((Map) list.get(0)).keySet();
		Iterator<String> header = headerSet.iterator();
		
		Collection headerRowSet = ((Map) list.get(0)).values();
		Iterator headerRowData = headerRowSet.iterator();
		//column header add
		for(int i=0; i<colCnt; i++){
			String columnName = header.next();
			// getType으로 switch문 사용으로 값 return method구현필요
			Object target = headerRowData.next();
			
			if(target == null) {
				ds.addColumn(columnName, PlatformDataType.STRING);
			} else {
				if (target.getClass().getSimpleName().equals(String.class.getSimpleName())) {
					ds.addColumn(columnName, PlatformDataType.STRING);
				} else 
				if (target.getClass().getSimpleName().equals(Integer.class.getSimpleName())) {
					ds.addColumn(columnName, PlatformDataType.INT);
				} else if (target.getClass().getSimpleName().equals(Boolean.class.getSimpleName())) {
					ds.addColumn(columnName, PlatformDataType.BOOLEAN);
				} else if (target.getClass().getSimpleName().equals(BigDecimal.class.getSimpleName())) {
					ds.addColumn(columnName, PlatformDataType.BIG_DECIMAL);
				} else if (target.getClass().getSimpleName().equals(Long.class.getSimpleName())) {
					ds.addColumn(columnName, PlatformDataType.LONG);
				} else if (target.getClass().getSimpleName().equals(Double.class.getSimpleName())) {
					ds.addColumn(columnName, PlatformDataType.DOUBLE);
				} else if (target.getClass().getSimpleName().equals(Date.class.getSimpleName())) {
					ds.addColumn(columnName, PlatformDataType.DATE);
				} else if (target.getClass().getSimpleName().equals(Timestamp.class.getSimpleName())) {
					ds.addColumn(columnName, PlatformDataType.DATE_TIME);
				} else if (target.getClass().getSimpleName().equals(Byte[].class.getSimpleName())) {
					ds.addColumn(columnName, PlatformDataType.BLOB);
				} else {
					ds.addColumn(columnName, PlatformDataType.STRING);		
				}
			}
		}
		
		int dummyPos = ds.indexOfColumn("ONLY_METADATA_RETURN");
		
		if(dummyPos < 0) {
			//row data add
			for(int i=0; i<rowCnt; i++) {
				Collection rowSet = ((Map) list.get(i)).values();
	
				Iterator rowData = rowSet.iterator();
				
				int rowNum = ds.newRow();
				
				for(int j=0; j<colCnt; j++) {
					Object rowdata = rowData.next();
					if(rowdata == null) {
							ds.set(rowNum, j, "" );
					} else {
							ds.set(rowNum, j, rowdata);	
					}
				}
			}
		} else {
			ds.removeColumn("ONLY_METADATA_RETURN");	
		}
		
		dsList.add(ds);
		
		pData.setDataSetList(dsList);
		
	}
	
	/**
	 * getDataSetList 
	 * 
	 */
	public void setDataSet(DataSet dsName) throws PlatformException{
		DataSetList dsList = pData.getDataSetList();
		
		dsList.add(dsName);
	}
	
	public void sendException(HttpServletResponse response, Exception e) throws PlatformException  {
		
		HttpPlatformResponse pRes = new HttpPlatformResponse(response, PlatformType.CONTENT_TYPE_XML, "utf-8");
		String errMsg = new String();
		VariableList resultList = pData.getVariableList();
		
		e.printStackTrace();
		
		errMsg = e.getMessage();
		
		if("deu.comm.unione.BusinessException".equals(e.getClass().getName())){
			if(errMsg.indexOf("[")> 0){
				resultList.add("ErrorCode", errMsg.substring(errMsg.indexOf("[")).replace("[", "").replace("]", ""));
				resultList.add("ErrorMsg", errMsg.substring(0, errMsg.indexOf("[")-1));	
			}else{
				resultList.add("ErrorCode", "-200");
				resultList.add("ErrorMsg", e.toString().replace("deu.comm.unione.BusinessException:", ""));	
			}
		}else{
			resultList.add("ErrorCode", "-1");
			resultList.add("ErrorMsg", e.toString());
		}
		
		pData.setVariableList(resultList);

		pRes.setData(pData);
		
		pData = new PlatformData();

		pRes.sendData();
		
	}
	
	public PlatformData getpData() {
		return pData;
	}

	public void setpData(PlatformData resData) {
		pData = resData;
	}
}
