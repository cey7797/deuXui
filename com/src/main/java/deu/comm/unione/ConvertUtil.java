package deu.comm.unione;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nexacro.xapi.data.DataSet;
import com.nexacro.xapi.data.DataSetList;
import com.nexacro.xapi.data.PlatformData;
import com.nexacro.xapi.data.Variable;
import com.nexacro.xapi.data.VariableList;
import com.nexacro.xapi.data.datatype.PlatformDataType;
import com.nexacro.xapi.tx.HttpPlatformRequest;
import com.nexacro.xapi.tx.HttpPlatformResponse;
import com.nexacro.xapi.tx.PlatformException;
import com.nexacro.xapi.tx.PlatformType;

/**
 * 
 * @author Administrator
 *
 */
public class ConvertUtil {
	
	public ConvertUtil(){
		super();
	}
	
	/**
	 * @throws PlatformException 
	 * 
	 */
	public static Map getVariable(HttpServletRequest request) throws Exception{
		//PlatformData 생성
		PlatformData mav1 = new PlatformData();
		//DataSetList 생성
		DataSetList dsList = new DataSetList();
		//DataSet 생성
		DataSet ds = new DataSet("resultList");
		//VariableList 생성
		VariableList varList = mav1.getVariableList();
		
		//서비스의 요청을 플래폼데이터로 생성
		HttpPlatformRequest req = new HttpPlatformRequest(request);
		PlatformData mavReq = new PlatformData();
		//request에 데이터를 받아온다.
		req.receiveData();

		//PlatformData에 사용자 요청을 받는다.
		mavReq = req.getData();
		
		//VariableList를 생성
		VariableList varInList = new VariableList();
		
		varInList = mavReq.getVariableList();
		List list = varInList.valueList();
		HashMap map = new HashMap();
		
		for(int i=0; i<varInList.size(); i++){
			Variable vName = varInList.get(i);
			map.put(vName.getName(), varInList.getString(i));
		}
		
		return map;
	}
	
	/**
	 * getDataSetList 
	 * 
	 */
	public static List getDataSetList(HttpServletRequest request, String dsName) throws PlatformException{
		//화면에서 전송받을 platform request 생성
		HttpPlatformRequest req = new HttpPlatformRequest(request);
		// 통신할 platformData를 생성
		PlatformData mavReq = new PlatformData();
		//request에 데이터를 받아온다.
		req.receiveData();
		//PlatformData에 request된 데이터를 받는다.
		mavReq = req.getData();
		
		//DataSetList를 생성
		DataSetList dsList = new DataSetList();
		dsList = mavReq.getDataSetList();

		//데이터셋의 수만큼 dataSet을 생성 후 세팅해준다.
		DataSet ds = dsList.get(dsName);//나중에 resultList는 variable 처리해야됨
		
		//데이터셋을 변환할 List를 생성한다.
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		String header = "";
		String value = "";

		//데이터셋의 Row수만큼 List에 넣는다.
		for(int i=0; i<ds.getRowCount(); i++){
			Map<String, Object> map = new HashMap<String, Object>();
			for(int j=0; j<ds.getColumnCount(); j++){
				header = ds.getColumn(j).getName();
				value = ds.getString(i, j);
				map.put(header, value);
			}
			list.add(map);
		}

		return list;
	}
	
	/**
	 * sendDataSet
	 */
	public static void sendDataSet(HttpServletResponse response, List list, String dsName) throws PlatformException{
		//서비스단으로 전송할 HttpPlatformResponse를 생성한다.
		HttpPlatformResponse pRes = new HttpPlatformResponse(response, PlatformType.CONTENT_TYPE_XML, "utf-8");
		
		//PlatformData를 생성한다.
		PlatformData mav1 = new PlatformData();
		
		//DataSetList를 생성한다.
		DataSetList dsList = new DataSetList();
		
		//DataSet을 생성한다.
		DataSet ds = new DataSet(dsName);
		
		//variableList를 생성한다.
		VariableList varList = mav1.getVariableList();
		
		int colCnt = ((Map) list.get(0)).keySet().size();
		int rowCnt = list.size(); 
		
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
				ds.addColumn(columnName, PlatformDataType.STRING, 0);
			} else {
				if (target.getClass().getSimpleName().equals(String.class.getSimpleName())) {
					ds.addColumn(columnName, PlatformDataType.STRING, 0);
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
					ds.addColumn(columnName, PlatformDataType.STRING, 0);		
				}
			}
		}
		
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
		dsList.add(ds);
		
		varList.add("ErrorCode", "0");
		varList.add("ErrorMsg", "");
		
		mav1.setDataSetList(dsList);
		mav1.setVariableList(varList);
		
		pRes.setData(mav1);
		pRes.sendData();
	}
	
	/**
	 * getDataSetList 
	 * 
	 */
	public static HashMap<String, Object> getDStoMap(DataSet ds, int rowNo) throws PlatformException{

		String header = "";
		String value = "";
		HashMap<String, Object> map = new HashMap<String, Object>();
			
		for(int j=0; j<ds.getColumnCount(); j++){
			header = ds.getColumn(j).getName();
			value = ds.getString(rowNo, j);
			map.put(header, value);
		}
		
		return map;
	}
	
	/**
	 * map을 dataset으로 변환한다.
	 * 
	 */
	public static DataSet getMapToDS(Map map) throws PlatformException{

		DataSet ds = new DataSet("requestDs");
		
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
		
		return ds;
	}
	
	/**
	 * list을 dataset으로 변환한다.
	 * 
	 */
	public static DataSet getListToDS(List list) throws PlatformException{

		DataSet ds = new DataSet("responseDs");
			
		int colCnt = ((Map) list.get(0)).keySet().size();
		int rowCnt = list.size();
		
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
		
		return ds;
	}
}
