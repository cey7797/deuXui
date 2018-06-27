package deu.admi.ac.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import deu.admi.ac.service.Ac03Service;
import deu.comm.unione.BusinessException;
import deu.comm.unione.ConvertUtil;
import deu.comm.unione.service.impl.CommonDAO;

import com.nexacro.xapi.data.DataSet;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("ac03Service")
public class Ac03ServiceImpl extends EgovAbstractServiceImpl implements Ac03Service{
	 
	@Resource(name = "commonDAO")
	private CommonDAO commonDAO;


    /*#########################################################################################################################################*/
    /* 일계표(admi/ac/ac03/ac03010.xfdl)                                                                                                       */
    /*#########################################################################################################################################*/

    
    /**
     * 일계표을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    public List<?> selectAc03010MainList(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac03DAO.selectAc03010MainList", map);
        return list;
    }
    

    /*#########################################################################################################################################*/
    /* 월계표(admi/ac/ac03/ac03020.xfdl)                                                                                                       */
    /*#########################################################################################################################################*/

    
    /**
     * 월계표을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    public List<?> selectAc03020MainList(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac03DAO.selectAc03020MainList", map);
        return list;
    }
    

    /*#########################################################################################################################################*/
    /* 현금출납부(admi/ac/ac03/ac03030.xfdl)                                                                                                   */
    /*#########################################################################################################################################*/

    
    /**
     * 현금출납부을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    public List<?> selectAc03030MainList(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac03DAO.selectAc03030MainList", map);
        return list;
    }
    

    /*#########################################################################################################################################*/
    /* 일일수지현황(admi/ac/ac03/ac03040.xfdl)                                                                                                   */
    /*#########################################################################################################################################*/

    
    /**
     * 총계정원장을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    public List<?> selectAc03040MainList(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac03DAO.selectAc03040MainList", map);
        return list;
    }
    

    /*#########################################################################################################################################*/
    /* 예산대비집행현황(admi/ac/ac03/ac03050.xfdl)                                                                                             */
    /*#########################################################################################################################################*/

    
    /**
     * 예산대비집행현황을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    public List<?> selectAc03050MainList(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac03DAO.selectAc03050MainList", map);
        return list;
    }
    

    /*#########################################################################################################################################*/
    /* 계좌현황(admi/ac/ac03/ac03060.xfdl)                                                                                                     */
    /*#########################################################################################################################################*/

    
    /**
     * 계좌현황을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    public List<?> selectAc03060MainList(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac03DAO.selectAc03060MainList", map);
        return list;
    }
    

    /*#########################################################################################################################################*/
    /* 회계마감등록(admi/ac/ac03/ac03070.xfdl)                                                                                                 */
    /*#########################################################################################################################################*/

    
    /**
     * 회계마감등록을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    public List<?> selectAc03070MainList(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac03DAO.selectAc03070MainList", map);
        return list;
    }
    

    /*#########################################################################################################################################*/
    /* 결산재무제표통계(admi/ac/ac03/ac03080.xfdl)                                                                                             */
    /*#########################################################################################################################################*/

    
    /**
     * 결산재무제표통계을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    public List<?> selectAc03080_10List(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac03DAO.selectAc03080_10List", map);
        return list;
    }
    public List<?> selectAc03080_20List(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac03DAO.selectAc03080_20List", map);
        return list;
    }
    public List<?> selectAc03080_30List(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac03DAO.selectAc03080_30List", map);
        return list;
    }
    

    /*#########################################################################################################################################*/
    /* 결산부속명세서통계(admi/ac/ac03/ac03090.xfdl)                                                                                           */
    /*#########################################################################################################################################*/

    
    /**
     * 결산부속명세서통계을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    public List<?> selectAc03090MainList(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac03DAO.selectAc03090MainList", map);
        return list;
    }
    
    /*#########################################################################################################################################*/
    /* 현금및예금명세(admi/ac/ac03/ac03100.xfdl)                                                                                           */
    /*#########################################################################################################################################*/

    /**
     * 현금및예금명세을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    public List<?> selectAc03100MainList(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac03DAO.selectAc03100MainList", map);
        return list;
    }
    
    
    /*#########################################################################################################################################*/
    /* 자금원장(admi/ac/ac03/ac03110.xfdl)                                                                                           */
    /*#########################################################################################################################################*/
    
    /**
     * 자금원장을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    public List<?> selectAc03110MainList(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac03DAO.selectAc03110MainList", map);
        return list;
    }
    
    /*#########################################################################################################################################*/
    /* 운영원장(admi/ac/ac03/ac03120.xfdl)                                                                                           */
    /*#########################################################################################################################################*/
    
    /**
     * 운영원장을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    public List<?> selectAc03120MainList(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac03DAO.selectAc03120MainList", map);
        return list;
    }
    
    public List<?> selectAc03120MainListMok(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac03DAO.selectAc03120MainListMok", map);
        return list;
    }
    public List<?> selectAc03120MainListAfd(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac03DAO.selectAc03120MainListAfd", map);
        return list;
    }
    /*#########################################################################################################################################*/
    /* 미결원장(admi/ac/ac03/ac03130.xfdl)                                                                                           */
    /*#########################################################################################################################################*/
    
    /**
     * 미결원장을(를) 조회한다.
     * @param strParam1 파라메타1
     * @param strParam2 파라메타2
     */
    public List<?> selectAc03130MainList(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac03DAO.selectAc03130MainList", map);
        return list;
    }
}
