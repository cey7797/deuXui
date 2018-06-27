package deu.comm.file.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import deu.comm.file.service.CommFileService;
import deu.comm.unione.service.impl.CommonDAO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("commFileService")
public class CommFileServiceImpl extends EgovAbstractServiceImpl implements CommFileService{
	
	//private static final Logger LOGGER = LoggerFactory.getLogger(ComCsiService.class);

	@Resource(name = "commonDAO")
	private CommonDAO commonDAO;
	
	@Override
	public List<?> selectFileInfo(Map map) throws Exception{
		List<?> list = commonDAO.selectData("comDAO.selectFileInfo", map);
		return list;
	}
	
}
