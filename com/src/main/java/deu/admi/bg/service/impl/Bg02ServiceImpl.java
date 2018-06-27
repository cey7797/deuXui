package deu.admi.bg.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import deu.admi.bg.service.Bg02Service;
import deu.comm.unione.BusinessException;
import deu.comm.unione.ConvertUtil;
import deu.comm.unione.service.impl.CommonDAO;

import com.nexacro.xapi.data.DataSet;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("bg02Service")
public class Bg02ServiceImpl extends EgovAbstractServiceImpl implements Bg02Service{
	 
	@Resource(name = "commonDAO")
	private CommonDAO commonDAO;

}
