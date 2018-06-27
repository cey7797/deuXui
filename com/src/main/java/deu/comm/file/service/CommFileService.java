package deu.comm.file.service;

import java.util.List;
import java.util.Map;

public interface CommFileService {

	/**
	 * 파일 상세정보를 조회한다.
	 * @throws Exception 
	 */
	public abstract List<?> selectFileInfo(Map map) throws Exception;
	
}
