package deu.admi.afd.afd05.service;

import java.util.List;
import java.util.Map;

import com.nexacro.xapi.data.DataSet;

public interface Afd05Service {

	public abstract List<?> selectExcel(Map map) throws Exception;
	
	public abstract List<?> selectExcel_02(Map map) throws Exception;
	
	public abstract List<?> selectExcel_03(Map map) throws Exception;
		
}
