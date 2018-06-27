package deu.admi.afd.afd06.service;

import java.util.List;
import java.util.Map;

import com.nexacro.xapi.data.DataSet;

public interface Afd06Service {

	public abstract List<?> selectAfdAcntNoList(Map map) throws Exception;

	public abstract void saveAfd06010MainList(DataSet ds) throws Exception;
	
	public abstract List<?> selectAFD06020(Map map) throws Exception;
	
	public abstract void saveAFD06020(DataSet ds) throws Exception;
	
	public abstract List<?> selectAFD06030(Map map) throws Exception;
	
	public abstract void saveAFD06030(DataSet ds) throws Exception;
	
	public abstract List<?> selectAFD06040(Map map) throws Exception;
	
	public abstract void saveAFD06040(DataSet ds) throws Exception;
	
	public abstract List<?> selectAFD06050(Map map) throws Exception;
	
	public abstract void saveAFD06050(DataSet ds) throws Exception;
	
	public abstract List<?> selectAFD06060(Map map) throws Exception;
	
	public abstract void saveAFD06060(DataSet ds) throws Exception;
}
