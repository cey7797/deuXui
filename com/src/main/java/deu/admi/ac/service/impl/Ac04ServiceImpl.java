package deu.admi.ac.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import deu.admi.ac.service.Ac04Service;
import deu.comm.unione.BusinessException;
import deu.comm.unione.ConvertUtil;
import deu.comm.unione.service.impl.CommonDAO;

import com.nexacro.xapi.data.DataSet;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("ac04Service")
public class Ac04ServiceImpl extends EgovAbstractServiceImpl implements Ac04Service{
	 
	@Resource(name = "commonDAO")
	private CommonDAO commonDAO;


    /*#########################################################################################################################################*/
    /* 계산서등록(admi/ac/ac04/ac04010.xfdl)                                                                                                   */
    /*#########################################################################################################################################*/

    public List<?> selectAcBizNo(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac04DAO.selectAcBizNo", map);
        return list;
    }
	
    
    /**
     * 계산서등록을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    public List<?> selectAc04010MainList(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac04DAO.selectAc04010MainList", map);
        return list;
    }
    
	@Override
    public void saveAc04010MainList(DataSet ds) throws Exception{
        String insertQueryId="ac04DAO.insertAc04010MainList";
        String updateQueryId="ac04DAO.updateAc04010MainList";
        String deleteQueryId="ac04DAO.deleteAc04010MainList";
        commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
    }

    /*#########################################################################################################################################*/
    /* 계산서합계표현황(admi/ac/ac04/ac04020.xfdl)                                                                                             */
    /*#########################################################################################################################################*/

    
    /**
     * 계산서합계표현황을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    public List<?> selectAc04020MainList(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac04DAO.selectAc04020MainList", map);
        return list;
    }
    
    public List<?> selectAc04020SubList(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac04DAO.selectAc04020SubList", map);
        return list;
    }    
    
	@Override
    public void saveAc04020MainList(Map map) throws Exception{
		commonDAO.updateData("ac04DAO.updateAc04020MainList", map);
    }
    
    /*#########################################################################################################################################*/
    /* 소득자료등록(admi/ac/ac04/ac04040.xfdl)                                                                                                 */
    /*#########################################################################################################################################*/

    
    /**
     * 소득자료등록을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    public List<?> selectAc04040MainList(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac04DAO.selectAc04040MainList", map);
        return list;
    }
    
	@Override
    public void saveAc04040MainList(DataSet ds) throws Exception{
        String insertQueryId="ac04DAO.insertAc04040MainList";
        String updateQueryId="ac04DAO.updateAc04040MainList";
        String deleteQueryId="ac04DAO.deleteAc04040MainList";
        commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
    }
    
    /*#########################################################################################################################################*/
    /* 소득신고파일생성및신고완료처리(admi/ac/ac04/ac04050.xfdl)                                                                               */
    /*#########################################################################################################################################*/

    
    /**
     * 소득신고파일생성 및 신고완료처리을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    public List<?> selectAc04050MainList(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac04DAO.selectAc04050MainList", map);
        return list;
    }
    
    
    /*#########################################################################################################################################*/
    /* 원천징수영수증(admi/ac/ac04/ac04060.xfdl)                                                                                               */
    /*#########################################################################################################################################*/

    
    /**
     * 원천징수영수증을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    public List<?> selectAc04060MainList(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac04DAO.selectAc04060MainList", map);
        return list;
    }
    
    public List<?> selectAc04060SubList(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac04DAO.selectAc04060SubList", map);
        return list;
    }
}
