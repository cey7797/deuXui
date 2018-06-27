package deu.admi.ac.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import deu.admi.ac.service.Ac02Service;
import deu.comm.unione.BusinessException;
import deu.comm.unione.ConvertUtil;
import deu.comm.unione.service.impl.CommonDAO;
import deu.comm.unione.service.impl.MsDAO;

import com.nexacro.xapi.data.DataSet;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("ac02Service")
public class Ac02ServiceImpl extends EgovAbstractServiceImpl implements Ac02Service{
	 
	@Resource(name = "commonDAO")
	private CommonDAO commonDAO;
	
	@Resource(name = "msDAO")
	private MsDAO msDAO;
	
	
	/*#########################################################################################################################################*/
    /* 결의서등록(admi/ac/ac02/ac02010.xfdl)                                                                                                     */
    /*#########################################################################################################################################*/
    
	/**
	 *  
	 */
	public List<?> selectAc02010_01(Map map) throws Exception{
		Map map1 = new HashMap();
		HashMap nMap = null;
		nMap = commonDAO.saveDataSP("ac02DAO.saveSpDeuOraMssqlApproChk", map1);
		
		
		
        Map map2 = new HashMap();
        map2.put("IN_DEPT_CD", map.get("vouOrgId"));
        HashMap nMap1 = null;
        
		
		
		nMap1 = commonDAO.saveDataSP("admiDAO.saveSpAcPurcBudgDept_recv", map2);
		System.out.println(map);
		List<?> list = commonDAO.selectData("ac02DAO.selectAc02010_01", map);
        return list;
	}
	
	public List<?> selectAc02010_excelMember(Map map) throws Exception{
		List<?> list = commonDAO.selectData("ac02DAO.selectAc02010_excelMember", map);
        return list;
	}
	
	
    @Override
    public HashMap saveAc02010_01_Search_Approval(DataSet ds) throws Exception{
        HashMap nMap = null;
        for (int i = 0; i < ds.getRowCount(); i++) {

           // rowType = ds.getString(i, "rowStatus");
            HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
            
                Map map = new HashMap();
                
                // TODO ENTRY_NO, ENTRY_IP 넣기
                nMap = commonDAO.saveDataSP("ac02DAO.saveSpDeuOraMssqlApproChk", map);
        }
        
        return nMap;
    }
	
	/*#########################################################################################################################################*/
    /* 결의서등록 (admi/ac/ac02/ac02010.xfdl)  전자결재                                                                                          */
    /*#########################################################################################################################################*/
    
	/*
	* 전자결재 결의서등록 결의종류가 자금/결산대체를 제외한 나머지 일때
	*/
	@Override
    public HashMap saveAc02010_01_Approval(DataSet ds) throws Exception {
		String strId 			= "";
        String strFormId 		= "";
        String strTitle 		= "";
        String strSubjId 		= "";
        String strSubTitle 		= "";
        String strBody 			= "";
        String strApprovalDiv 	= "";
        String strApprovalDt 	= "";
        String strApprovalDocid = "";
        String strUserId 		= "";
        String strUpdDt 		= "";
        String strCnt = "";
        String balDivNm = "";
        String bizNm = "";
        String acntNm1 = "";
        String acntNm2 = "";
        String acntNm3 = "";
        String acntCd = "";
        String aplyAmt = "";
        String budgAmt1 = "";
        String budgAmt2 = "";
        String budgAmt3 = "";
        String draftDesc = "";
        String remark = "";
        String budgAmt1Tot = "";
        String budgAmt2Tot = "";
        String budgAmt3Tot = "";
        String acntUntNm = "";
        String vouNo = "";
        String strHtml = "";
        HashMap nMap  = new HashMap();    // 리턴 시킬 map
        HashMap nMap1 = new HashMap();   // 화면에서 넘어온 파라미터를 받을 map 
        HashMap nMap2 = new HashMap();   // 변경신청내역(본문내용) 받을 map
        HashMap nMap3 = new HashMap();   //mssql+oracle 전자결재연동테이블에 입력된 데이타를 예산변경테이블에 넣기위한 map
        HashMap nMap4 = new HashMap();	//전자결재ID담아올 맵
        //  String serverElecUrl = commonDAO.getProValue("Globals.serverElecUrl");  //전자결재 생성 서버url

        for (int i = 0; i < ds.getRowCount(); i++) {
            HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);//화면에서 넘어온 정보
            Map map = new HashMap(); 
            nMap1.put("acntUnt", hm.get("acntUnt"));
            nMap1.put("acntYy", hm.get("acntYy"));
            nMap1.put("vouNo", hm.get("vouNo"));
            nMap1.put("aplyDt", hm.get("aplyDt"));
            nMap1.put("aplyUserId", hm.get("aplyUserId"));
            
            nMap1.put("msg", hm.get("msg"));
            nMap1.put("procCd", hm.get("procCd"));
            nMap1.put("userId", hm.get("userId"));
            nMap1.put("userIp", hm.get("userIp"));
            nMap1.put("userPgm", hm.get("userPgm"));
            nMap1.put("cfmDt", hm.get("cfmDt"));

            
            nMap4.put("strElecId", hm.get("strElecId"));
            nMap4.put("strAcntYy", hm.get("strAcntYy"));
            
    
            //화면에서 넘어온 정보셋팅
            //map.put("id", hm.get("id")); // 전자결재ID
            map.put("title", hm.get("title")); // 타이틀
            map.put("subtitle", hm.get("subtitle")); // 서브타이틀
            map.put("formId", hm.get("formId")); // 양식ID
            map.put("userId", hm.get("userId")); // 기안자ID
            map.put("subjId", hm.get("subjId")); // 기안자ID
            map.put("accDept", hm.get("accDept")); // 수신처
            
            // 본문내용중 변경신청내역 얻기
            List<?> list = commonDAO.selectData("ac02DAO.selectAc02010p01_t01_01(elec)", nMap1);

        	DataSet rtnDs = ConvertUtil.getListToDS(list);
        	
            if (rtnDs.getRowCount() == 0) {
            	list = commonDAO.selectData("ac02DAO.selectAc02010p01_t01_01(elec2)", nMap1);
            	rtnDs = ConvertUtil.getListToDS(list);
            	
            	draftDesc 	= rtnDs.getString(0,"draftDesc");
            	
                strHtml = "<html>";
                strHtml += "<head>";
                strHtml += "<style type=\"text/css\">";
                strHtml += " p.HStyle {    margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;   } "; 
                strHtml += " p.HStyle0 {    margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;   } ";                
                strHtml += " p.HStyleLeft {    margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: left;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;   } ";                 
                strHtml += " p.HStyleRight {    margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;   }    ";              
                strHtml += " </style>";
                strHtml += " </head>";
                strHtml += " <body style=\"width: 650px; margin: 0px;\">";
                strHtml += "	 			<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse:collapse;border:none;\">";
                vouNo = hm.get("vouNo").toString().substring(0, 8) + "-" + hm.get("vouNo").toString().substring(8, 12);
                
                strHtml += "					<tr>";
                strHtml += "						<td height=\"30\" width=\"650\" style=\"border-top:0px; border-left:0px; border-right:0px; border-bottom:0px;\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: left;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">( 기안번호 : "+ vouNo+" ).  끝</p></td>";
                strHtml += "					</tr>";
                strHtml += "				</table>";
                strHtml += "	</body>";
                strHtml += "</html>";

            	
            } else {
            	
            	draftDesc 	= rtnDs.getString(0,"draftDesc");
            	
                strHtml = "<html>";
                strHtml += "<head>";
                strHtml += "<style type=\"text/css\">";
                strHtml += " p.HStyle {    margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;   } "; 
                strHtml += " p.HStyle0 {    margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;   } ";                
                strHtml += " p.HStyleLeft {    margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: left;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;   } ";                 
                strHtml += " p.HStyleRight {    margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;   }    ";              
                strHtml += " </style>";
                strHtml += " </head>";
                strHtml += " <body style=\"width: 650px; margin: 0px;\">";
                strHtml += "	 			<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse:collapse;border:none;\">";
                strHtml += "			 		<tr>";
                strHtml += "						<td colspan = \"3\" align=\"center\" width=\"210\" height=\"30\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">과목</p></td>";
                strHtml += "						<td rowspan = \"2\" width=\"80\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">회계단위</p></td>";
                strHtml += "						<td rowspan = \"2\" width=\"80\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">예산총액</p></td>";
                strHtml += "						<td rowspan = \"2\" width=\"80\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">신청액</p></td>";
                strHtml += "						<td rowspan = \"2\" width=\"80\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">집행액</p></td>";
                strHtml += "						<td rowspan = \"2\" width=\"80\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">예산잔액</p></td>";
                strHtml += "						<td rowspan = \"2\" width=\"40\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">비고</p></td>";
                strHtml += "					</tr>";
                strHtml += "					<tr>";
                strHtml += "						<td width=\"70\" height=\"30\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">관</p></td>";
                strHtml += "						<td width=\"70\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">항</p></td>";
                strHtml += "						<td width=\"70\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">목</p></td>";
                strHtml += "					</tr>";
                
                // 위에서 얻은 본문내용 갯수만큼 셋팅해주기
                for(int j=0; j < list.size(); j++ ) {
    				
                	aplyAmt 	= rtnDs.getString(j,"aplyAmt");
                	acntNm1 	= rtnDs.getString(j,"acntNm1");
                	acntNm2     = rtnDs.getString(j,"acntNm2");
                	acntNm3   	= rtnDs.getString(j,"acntNm3");
      	            budgAmt1 	= rtnDs.getString(j,"budgAmt1");
      	            budgAmt2 	= rtnDs.getString(j,"budgAmt2");
      	            budgAmt3 	= rtnDs.getString(j,"budgAmt3");
      	            acntUntNm 	= rtnDs.getString(j,"acntUntNm");
      	            
    	            strHtml += "					<tr>";
    	            strHtml += "						<td height=\"30\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+acntNm1+"</p></td>";
    	            strHtml += "						<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+acntNm2+"</p></td>";
    	            strHtml += "						<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+acntNm3+"</p></td>";
    	            strHtml += "						<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+acntUntNm+"</p></td>";
    	            strHtml += "						<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+budgAmt1+"</p></td>";
    	            strHtml += "						<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+aplyAmt+"</p></td>";
    	            strHtml += "						<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+budgAmt2+"</p></td>";
    	            strHtml += "						<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+budgAmt3+"</p></td>";
    	            strHtml += "						<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: left;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">&nbsp;"+remark+"&nbsp;</p></td>";
    	            strHtml += "					</tr>";
                }
                
	            strHtml += "					<tr>";
	            strHtml += "						<td height=\"30\" colspan=\"5\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">신청금액계</p></td>";
	            strHtml += "						<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+ rtnDs.getString(0, "aplyAmtTot3") +"</p></td>";
	            strHtml += "						<td colspan=\"3\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\"></p></td>";
	            strHtml += "					</tr>";
                strHtml += "					<tr>";
                strHtml += "						<td height=\"30\" colspan=\"9\" style=\"border-left:0px; border-right:0px; border-bottom:0px;\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+ rtnDs.getString(0, "bizNmTot") +"</td>";
                strHtml += "					</tr>";
	            
                vouNo = hm.get("vouNo").toString().substring(0, 8) + "-" + hm.get("vouNo").toString().substring(8, 12);
                
                strHtml += "					<tr>";
                strHtml += "						<td height=\"30\" colspan=\"9\" style=\"border-left:0px; border-right:0px; border-bottom:0px;\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: left;    text-indent: 0.0pt;    line-height: 140%;    font-size: 12pt;    font-family: 굴림;\">( 기안번호 : "+ vouNo+" ). 끝.</p></td>";
                strHtml += "					</tr>";
                strHtml += "				</table>";
                strHtml += "	</body>";
                strHtml += "</html>";            	
            }
        	

            
            List<?> list1 = commonDAO.selectData("admiDAO.selectGwApprovalMstElecId", nMap4);

        	DataSet rtnDs1 = ConvertUtil.getListToDS(list1);
            
            nMap.put("id", rtnDs1.getString(0,"elecId"));
            nMap.put("formId",   map.get("formId"));
            nMap.put("title",    map.get("title"));
            nMap.put("subTitle", map.get("subTitle"));
            nMap.put("subjId",   map.get("subjId"));
            nMap.put("body", strHtml);
            nMap.put("approvalDiv", strApprovalDiv);
            nMap.put("approvalDt", strApprovalDt);
            nMap.put("approvalDocid", strApprovalDocid);
            nMap.put("userId", map.get("userId"));
            
            System.out.println(nMap);
            System.out.println("_________________________");
            
            nMap.put("id", rtnDs1.getString(0,"elecId"));
            nMap.put("formId",   map.get("formId"));
            nMap.put("approvalDiv", strApprovalDiv);
            nMap.put("approvalDt", strApprovalDt);
            nMap.put("approvalUserid", map.get("userId"));
            
            nMap.put("acntUnt", nMap1.get("acntUnt"));
            nMap.put("acntYy", nMap1.get("acntYy"));
            nMap.put("vouNo", nMap1.get("vouNo"));
            nMap.put("aplyDt", nMap1.get("aplyDt"));
            nMap.put("aplyUserId", nMap1.get("aplyUserId"));
            
            nMap.put("msg", nMap1.get("msg"));
            nMap.put("procCd", nMap1.get("procCd"));
            nMap.put("userId", nMap1.get("userId"));
            nMap.put("userIp", nMap1.get("userIp"));
            nMap.put("userPgm", nMap1.get("userPgm"));
            nMap.put("cfmDt", nMap1.get("cfmDt"));
            nMap.put("accDept", map.get("accDept"));
            
            //mssql 전자결재연동테이블에 인서트한다.
            msDAO.insert("admiDAO.insertGwApprovalMst", nMap);
            
            //oracle 전자결재연동테이블에 인서트한다.
           // commonDAO.insert("admiDAO.insertGwApprovalMstOra", nMap);
            
            //oracle 결의서등록테이블(ADMI.AC_VOU)에 업데이트한다.
            commonDAO.update("ac02DAO.updateAc02010p01_01AplyGwApprovalMstOra", nMap);
            
            commonDAO.saveDataSP("ac02DAO.P_AC_STA_CD_PROC", nMap);
        } 
        return nMap;
    }
	
	@Override
    public HashMap chkAc02010_01_Approval(DataSet ds) throws Exception {

        HashMap nMap  = new HashMap();    // 리턴 시킬 map

        for (int i = 0; i < ds.getRowCount(); i++) {
            HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);//화면에서 넘어온 정보
            Map map = new HashMap(); 
            
            //화면에서 넘어온 정보셋팅
            //map.put("id", hm.get("id")); // 전자결재ID           
            nMap.put("acntUnt", hm.get("acntUnt"));
            nMap.put("acntYy", hm.get("acntYy"));
            nMap.put("vouNo", hm.get("vouNo"));
            
            commonDAO.saveDataSP("ac02DAO.CHK_VOU_PYMT_PROC", nMap);
        } 
        return nMap;
    }
	
	/*
	* 전자결재 결의서등록 결의종류가 자금대체 일때
	*/
	@Override
    public HashMap saveAc02010_01_Approval_vouKnd32(DataSet ds) throws Exception {
		String strId 			= "";
        String strFormId 		= "";
        String strTitle 		= "";
        String strSubjId 		= "";
        String strSubTitle 		= "";
        String strBody 			= "";
        String strApprovalDiv 	= "";
        String strApprovalDt 	= "";
        String strApprovalDocid = "";
        String strUserId 		= "";
        String strUpdDt 		= "";
        String strCnt = "";
        String balDivNm = "";
        String bizNm = "";
        String aplyAmt = "";
        String drcrNm = "";
        String acntNo = "";
        String vouNo = "";
        HashMap nMap  = new HashMap();    // 리턴 시킬 map
        HashMap nMap1 = new HashMap();   // 화면에서 넘어온 파라미터를 받을 map 
        HashMap nMap2 = new HashMap();   // 변경신청내역(본문내용) 받을 map
        HashMap nMap3 = new HashMap();   //mssql+oracle 전자결재연동테이블에 입력된 데이타를 예산변경테이블에 넣기위한 map
        HashMap nMap4 = new HashMap();	//전자결재ID담아올 맵
        //  String serverElecUrl = commonDAO.getProValue("Globals.serverElecUrl");  //전자결재 생성 서버url

        for (int i = 0; i < ds.getRowCount(); i++) {
            HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);//화면에서 넘어온 정보
            Map map = new HashMap(); 
            nMap1.put("acntUnt", hm.get("acntUnt"));
            nMap1.put("acntYy", hm.get("acntYy"));
            nMap1.put("vouNo", hm.get("vouNo"));
            nMap1.put("aplyDt", hm.get("aplyDt"));
            nMap1.put("aplyUserId", hm.get("aplyUserId"));
            
            nMap1.put("msg", hm.get("msg"));
            nMap1.put("procCd", hm.get("procCd"));
            nMap1.put("userId", hm.get("userId"));
            nMap1.put("userIp", hm.get("userIp"));
            nMap1.put("userPgm", hm.get("userPgm"));
            nMap1.put("cfmDt", hm.get("cfmDt"));
            
            nMap4.put("strElecId", hm.get("strElecId"));
            nMap4.put("strAcntYy", hm.get("strAcntYy"));
    
            //화면에서 넘어온 정보셋팅
            //map.put("id", hm.get("id")); // 전자결재ID
            map.put("title", hm.get("title")); // 타이틀
            map.put("subtitle", hm.get("subtitle")); // 서브타이틀
            map.put("formId", hm.get("formId")); // 양식ID
            map.put("userId", hm.get("userId")); // 기안자ID
            map.put("subjId", hm.get("subjId")); // 기안자ID
            map.put("accDept", hm.get("accDept")); // 수신처
            
            // 본문내용중 변경신청내역 얻기
            List<?> list = commonDAO.selectData("ac02DAO.selectAc02010p01_t01_01(elec32)", nMap1);

        	DataSet rtnDs = ConvertUtil.getListToDS(list);
        	
            String strHtml = "<html>";
            strHtml += "<head>";
            strHtml += "<style type=\"text/css\">";
            strHtml += " p.HStyle {    margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;   } "; 
            strHtml += " p.HStyle0 {    margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;   } ";                
            strHtml += " p.HStyleLeft {    margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: left;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;   } ";                 
            strHtml += " p.HStyleRight {    margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;   }    ";              
            strHtml += " </style>";
            strHtml += " </head>";
            strHtml += " <body style=\"width: 650px; margin: 0px;\">";
            strHtml += "	 			<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse:collapse;border:none;\">";
            strHtml += "			 		<tr>";
            strHtml += "						<td width=\"150\" height=\"30\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">구분</p></td>";
            strHtml += "						<td width=\"300\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">계좌번호</p></td>";
            strHtml += "						<td width=\"200\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">금액</p></td>";
            strHtml += "					</tr>";
            
            // 위에서 얻은 본문내용 갯수만큼 셋팅해주기
            for(int j=0; j < list.size(); j++ ) {
				
            	aplyAmt    = rtnDs.getString(j,"aplyAmt");
            	acntNo 	   = rtnDs.getString(j,"acntNo");
            	drcrNm     = rtnDs.getString(j,"drcrCdNm");
            
	            strHtml += "					<tr>";
	            strHtml += "						<td height=\"30\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+drcrNm+"</p></td>";
	            strHtml += "						<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+acntNo+"</p></td>";
	            strHtml += "						<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+aplyAmt+"</p></td>";
	            strHtml += "					</tr>";
            }
            
            vouNo = hm.get("vouNo").toString().substring(0, 8) + "-" + hm.get("vouNo").toString().substring(8, 12);
            
            strHtml += "					<tr>";
            strHtml += "						<td height=\"30\" colspan=\"3\" style=\"border-left:0px; border-right:0px; border-bottom:0px;\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: left;    text-indent: 0.0pt;    line-height: 140%;    font-size: 12pt;    font-family: 굴림;\">( 기안번호 : "+ vouNo+" ). 끝.</p></td>";
            strHtml += "					</tr>";
            strHtml += "				</table>";
            strHtml += "	</body>";
            strHtml += "</html>";

            
            List<?> list1 = commonDAO.selectData("admiDAO.selectGwApprovalMstElecId", nMap4);

        	DataSet rtnDs1 = ConvertUtil.getListToDS(list1);
            
            nMap.put("id", rtnDs1.getString(0,"elecId"));
            nMap.put("formId",   map.get("formId"));
            nMap.put("title",    map.get("title"));
            nMap.put("subTitle", map.get("subTitle"));
            nMap.put("subjId",   map.get("subjId"));
            nMap.put("body", strHtml);
            nMap.put("approvalDiv", strApprovalDiv);
            nMap.put("approvalDt", strApprovalDt);
            nMap.put("approvalDocid", strApprovalDocid);
            nMap.put("userId", map.get("userId"));
            
            System.out.println(nMap);
            System.out.println("_________________________");
            
            nMap.put("id", rtnDs1.getString(0,"elecId"));
            nMap.put("formId",   map.get("formId"));
            nMap.put("approvalDiv", strApprovalDiv);
            nMap.put("approvalDt", strApprovalDt);
            nMap.put("approvalUserid", map.get("userId"));
            
            nMap.put("acntUnt", nMap1.get("acntUnt"));
            nMap.put("acntYy", nMap1.get("acntYy"));
            nMap.put("vouNo", nMap1.get("vouNo"));
            nMap.put("aplyDt", nMap1.get("aplyDt"));
            nMap.put("aplyUserId", nMap1.get("aplyUserId"));
            
            nMap.put("msg", nMap1.get("msg"));
            nMap.put("procCd", nMap1.get("procCd"));
            nMap.put("userId", nMap1.get("userId"));
            nMap.put("userIp", nMap1.get("userIp"));
            nMap.put("userPgm", nMap1.get("userPgm"));
            nMap.put("cfmDt", nMap1.get("cfmDt"));
            nMap.put("accDept", map.get("accDept"));
            
            //mssql 전자결재연동테이블에 인서트한다.
            msDAO.insert("admiDAO.insertGwApprovalMst", nMap);
            
            //oracle 전자결재연동테이블에 인서트한다.
           // commonDAO.insert("admiDAO.insertGwApprovalMstOra", nMap);
            
            //oracle 결의서등록테이블(ADMI.AC_VOU)에 업데이트한다.
            commonDAO.update("ac02DAO.updateAc02010p01_01AplyGwApprovalMstOra", nMap);
            
            commonDAO.saveDataSP("ac02DAO.P_AC_STA_CD_PROC", nMap);
        } 
        return nMap;
    }
	
	/*
	* 전자결재 결의서등록 결의종류가 결산대체 일때
	*/
	@Override
    public HashMap saveAc02010_01_Approval_vouKnd33(DataSet ds) throws Exception {
		String strId 			= "";
        String strFormId 		= "";
        String strTitle 		= "";
        String strSubjId 		= "";
        String strSubTitle 		= "";
        String strBody 			= "";
        String strApprovalDiv 	= "";
        String strApprovalDt 	= "";
        String strApprovalDocid = "";
        String strUserId 		= "";
        String strUpdDt 		= "";
        String strCnt = "";
        String balDivNm = "";
        String bizNm = "";
        String acntNm1 = "";
        String acntNm2 = "";
        String acntNm3 = "";
        String acntCd = "";
        String aplyAmt = "";
        String budgAmt1 = "";
        String budgAmt2 = "";
        String budgAmt3 = "";
        String drcrNm = "";
        String draftDesc = "";
        String remark = "";
        String budgAmt1Tot = "";
        String budgAmt2Tot = "";
        String budgAmt3Tot = "";
        String acntUntNm = "";
        String vouNo = "";
        String strHtml = "";
        HashMap nMap  = new HashMap();    // 리턴 시킬 map
        HashMap nMap1 = new HashMap();   // 화면에서 넘어온 파라미터를 받을 map 
        HashMap nMap2 = new HashMap();   // 변경신청내역(본문내용) 받을 map
        HashMap nMap3 = new HashMap();   //mssql+oracle 전자결재연동테이블에 입력된 데이타를 예산변경테이블에 넣기위한 map
        HashMap nMap4 = new HashMap();	//전자결재ID담아올 맵
        //  String serverElecUrl = commonDAO.getProValue("Globals.serverElecUrl");  //전자결재 생성 서버url

        for (int i = 0; i < ds.getRowCount(); i++) {
            HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);//화면에서 넘어온 정보
            Map map = new HashMap(); 
            nMap1.put("acntUnt", hm.get("acntUnt"));
            nMap1.put("acntYy", hm.get("acntYy"));
            nMap1.put("vouNo", hm.get("vouNo"));
            nMap1.put("aplyDt", hm.get("aplyDt"));
            nMap1.put("aplyUserId", hm.get("aplyUserId"));
            
            nMap1.put("msg", hm.get("msg"));
            nMap1.put("procCd", hm.get("procCd"));
            nMap1.put("userId", hm.get("userId"));
            nMap1.put("userIp", hm.get("userIp"));
            nMap1.put("userPgm", hm.get("userPgm"));
            nMap1.put("cfmDt", hm.get("cfmDt"));

            
            nMap4.put("strElecId", hm.get("strElecId"));
            nMap4.put("strAcntYy", hm.get("strAcntYy"));
            
    
            //화면에서 넘어온 정보셋팅
            //map.put("id", hm.get("id")); // 전자결재ID
            map.put("title", hm.get("title")); // 타이틀
            map.put("subtitle", hm.get("subtitle")); // 서브타이틀
            map.put("formId", hm.get("formId")); // 양식ID
            map.put("userId", hm.get("userId")); // 기안자ID
            map.put("subjId", hm.get("subjId")); // 기안자ID
            map.put("accDept", hm.get("accDept")); // 수신처
            
            // 본문내용중 변경신청내역 얻기
            List<?> list = commonDAO.selectData("ac02DAO.selectAc02010p01_t01_01(elec)", nMap1);

        	DataSet rtnDs = ConvertUtil.getListToDS(list);
        	
            if (rtnDs.getRowCount() == 0) {
            	list = commonDAO.selectData("ac02DAO.selectAc02010p01_t01_01(elec2)", nMap1);
            	rtnDs = ConvertUtil.getListToDS(list);
            	
            	draftDesc 	= rtnDs.getString(0,"draftDesc");
            	
                strHtml = "<html>";
                strHtml += "<head>";
                strHtml += "<style type=\"text/css\">";
                strHtml += " p.HStyle {    margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;   } "; 
                strHtml += " p.HStyle0 {    margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;   } ";                
                strHtml += " p.HStyleLeft {    margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: left;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;   } ";                 
                strHtml += " p.HStyleRight {    margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;   }    ";              
                strHtml += " </style>";
                strHtml += " </head>";
                strHtml += " <body style=\"width: 650px; margin: 0px;\">";
                strHtml += "	 			<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse:collapse;border:none;\">";

                vouNo = hm.get("vouNo").toString().substring(0, 8) + "-" + hm.get("vouNo").toString().substring(8, 12);
                
                strHtml += "					<tr>";
                strHtml += "						<td height=\"30\" width=\"650\" style=\"border-top:0px; border-left:0px; border-right:0px; border-bottom:0px;\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: left;    text-indent: 0.0pt;    line-height: 140%;    font-size: 12pt;    font-family: 굴림;\">( 기안번호 : "+ vouNo+" ). 끝.</p></td>";
                strHtml += "					</tr>";
                strHtml += "				</table>";
                strHtml += "	</body>";
                strHtml += "</html>";
            } else {
            	
            	draftDesc 	= rtnDs.getString(0,"draftDesc");
            	
                strHtml = "<html>";
                strHtml += "<head>";
                strHtml += "<style type=\"text/css\">";
                strHtml += " p.HStyle {    margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;   } "; 
                strHtml += " p.HStyle0 {    margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;   } ";                
                strHtml += " p.HStyleLeft {    margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: left;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;   } ";                 
                strHtml += " p.HStyleRight {    margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;   }    ";              
                strHtml += " </style>";
                strHtml += " </head>";
                strHtml += " <body style=\"width: 650px; margin: 0px;\">";
                strHtml += "	 			<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse:collapse;border:none;\">";
                strHtml += "			 		<tr>";
                strHtml += "						<td colspan = \"3\" align=\"center\" width=\"220\" height=\"30\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">과목</p></td>";
                strHtml += "						<td rowspan = \"2\" width=\"50\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">회계단위</p></td>";
                strHtml += "						<td rowspan = \"2\" width=\"30\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">차대<br>구분</p></td>";
                strHtml += "						<td rowspan = \"2\" width=\"82\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">예산총액</p></td>";
                strHtml += "						<td rowspan = \"2\" width=\"81\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">신청액</p></td>";
                strHtml += "						<td rowspan = \"2\" width=\"82\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">집행액</p></td>";
                strHtml += "						<td rowspan = \"2\" width=\"82\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">예산잔액</p></td>";
                strHtml += "						<td rowspan = \"2\" width=\"25\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">비고</p></td>";
                strHtml += "					</tr>";
                strHtml += "					<tr>";
                strHtml += "						<td width=\"65\" height=\"30\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">관</p></td>";
                strHtml += "						<td width=\"65\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">항</p></td>";
                strHtml += "						<td width=\"90\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">목</p></td>";
                strHtml += "					</tr>";
                
                // 위에서 얻은 본문내용 갯수만큼 셋팅해주기
                for(int j=0; j < list.size(); j++ ) {
    				
                	aplyAmt 	= rtnDs.getString(j,"aplyAmt");
                	acntNm1 	= rtnDs.getString(j,"acntNm1");
                	acntNm2     = rtnDs.getString(j,"acntNm2");
                	acntNm3   	= rtnDs.getString(j,"acntNm3");
      	            budgAmt1 	= rtnDs.getString(j,"budgAmt1");
      	            budgAmt2 	= rtnDs.getString(j,"budgAmt2");
      	            budgAmt3 	= rtnDs.getString(j,"budgAmt3");
      	            drcrNm 	    = rtnDs.getString(j,"drcrNm");
      	            acntUntNm   = rtnDs.getString(j,"acntUntNm");
                
    	            strHtml += "					<tr>";
    	            strHtml += "						<td height=\"30\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+acntNm1+"</p></td>";
    	            strHtml += "						<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+acntNm2+"</p></td>";
    	            strHtml += "						<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+acntNm3+"</p></td>";
    	            strHtml += "						<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+acntUntNm+"&nbsp;</p></td>";
    	            strHtml += "						<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+drcrNm+"&nbsp;</p></td>";
    	            strHtml += "						<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+budgAmt1+"&nbsp;</p></td>";
    	            strHtml += "						<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+aplyAmt+"&nbsp;</p></td>";
    	            strHtml += "						<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+budgAmt2+"&nbsp;</p></td>";
    	            strHtml += "						<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+budgAmt3+"&nbsp;</p></td>";
    	            strHtml += "						<td ><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: left;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">&nbsp;"+remark+"&nbsp;</p></td>";
    	            strHtml += "					</tr>";
                }
                
                strHtml += "					<tr>";
                strHtml += "						<td height=\"30\" colspan=\"10\" style=\"border-left:0px; border-right:0px; border-bottom:0px;\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+ rtnDs.getString(0, "bizNmTot") +"</td>";
                strHtml += "					</tr>";
                
                vouNo = hm.get("vouNo").toString().substring(0, 8) + "-" + hm.get("vouNo").toString().substring(8, 12);
                
                strHtml += "					<tr>";
                strHtml += "						<td height=\"30\" colspan=\"10\" style=\"border-left:0px; border-right:0px; border-bottom:0px;\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: left;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">( 기안번호 : "+ vouNo+" ). 끝.</p></td>";
                strHtml += "					</tr>";
                strHtml += "				</table>";
                strHtml += "	</body>";
                strHtml += "</html>";
            	
            }
        	            
            List<?> list1 = commonDAO.selectData("admiDAO.selectGwApprovalMstElecId", nMap4);

        	DataSet rtnDs1 = ConvertUtil.getListToDS(list1);
            
            nMap.put("id", rtnDs1.getString(0,"elecId"));
            nMap.put("formId",   map.get("formId"));
            nMap.put("title",    map.get("title"));
            nMap.put("subTitle", map.get("subTitle"));
            nMap.put("subjId",   map.get("subjId"));
            nMap.put("body", strHtml);
            nMap.put("approvalDiv", strApprovalDiv);
            nMap.put("approvalDt", strApprovalDt);
            nMap.put("approvalDocid", strApprovalDocid);
            nMap.put("userId", map.get("userId"));
            
            System.out.println(nMap);
            System.out.println("_________________________");
            
            nMap.put("id", rtnDs1.getString(0,"elecId"));
            nMap.put("formId",   map.get("formId"));
            nMap.put("approvalDiv", strApprovalDiv);
            nMap.put("approvalDt", strApprovalDt);
            nMap.put("approvalUserid", map.get("userId"));
            
            nMap.put("acntUnt", nMap1.get("acntUnt"));
            nMap.put("acntYy", nMap1.get("acntYy"));
            nMap.put("vouNo", nMap1.get("vouNo"));
            nMap.put("aplyDt", nMap1.get("aplyDt"));
            nMap.put("aplyUserId", nMap1.get("aplyUserId"));
            
            nMap.put("msg", nMap1.get("msg"));
            nMap.put("procCd", nMap1.get("procCd"));
            nMap.put("userId", nMap1.get("userId"));
            nMap.put("userIp", nMap1.get("userIp"));
            nMap.put("userPgm", nMap1.get("userPgm"));
            nMap.put("cfmDt", nMap1.get("cfmDt"));
            nMap.put("accDept", map.get("accDept"));
            
            //mssql 전자결재연동테이블에 인서트한다.
            msDAO.insert("admiDAO.insertGwApprovalMst", nMap);
            
            //oracle 전자결재연동테이블에 인서트한다.
           // commonDAO.insert("admiDAO.insertGwApprovalMstOra", nMap);
            
            //oracle 결의서등록테이블(ADMI.AC_VOU)에 업데이트한다.
            commonDAO.update("ac02DAO.updateAc02010p01_01AplyGwApprovalMstOra", nMap);
            
            commonDAO.saveDataSP("ac02DAO.P_AC_STA_CD_PROC", nMap);
        } 
        return nMap;
    }
	
    /*#########################################################################################################################################*/
    /* 수입/여입결의서등록(admi/ac/ac02/ac02010_p01.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 *  
	 */
    public List<?> selectAc02010p01_01(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02010p01_01", map);
        return list;
    }
    
    public List<?> selectAc02010p01_01_link(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02010p01_01_link", map);
        return list;
    }
    
    public List<?> selectAc02010p01_02(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02010p01_02", map);
        return list;
    }
    
    public List<?> selectAc02010p01_03(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02010p01_03", map);
        return list;
    }
    
    public List<?> selectAc02010p01_04(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02010p01_04", map);
        return list;
    }
    
    /**
	 *  
	 */
    @Override
    public void saveAc02010p01_01(DataSet ds) throws Exception{
        String insertQueryId="ac02DAO.insertAc02010p01_01";
        String updateQueryId="ac02DAO.updateAc02010p01_01";
        String deleteQueryId="ac02DAO.deleteAc02010p01_01";
        commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
    }
    
    
    
    
    
    
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 수입/여입결의서등록(admi/ac/ac02/ac02010_p01.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 *  
	 */
    
    /*
    @Override
    public HashMap saveVouNoSp(Map map) throws Exception{
        HashMap nMap = commonDAO.saveDataSP("ac02DAO.saveVouNoSp", map);
        return nMap;
    }
    */
    
    @Override
    public HashMap saveVouNoSp(DataSet ds) throws Exception{
		HashMap map = new HashMap();
		for(int i=0; i<ds.getRowCount(); i++){
			for(int j=0; j<ds.getColumnCount(); j++){
				map.put(ds.getColumn(j).getName(), ds.getString(i, j));
			}
		}
		
		HashMap nMap = new HashMap();
        nMap = commonDAO.saveDataSP("ac02DAO.saveVouNoSp", map);
        return nMap;
    }
    
    
    
    
    
    
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 수입/여입 TAB 계정내역 (admi/ac/ac02/ac02010_p01_t01.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 *  
	 */
    public List<?> selectAc02010p01_t01_01(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02010p01_t01_01", map);
        return list;
    }
    
    /**
	 *  
	 */
    public List<?> selectAc02010p01_t01_cnt(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02010p01_t01_cnt", map);
        return list;
    }
        
    /**
	 *  
	 */
    @Override
    public void saveAc02010p01_t01_01(DataSet ds) throws Exception{
        String insertQueryId="ac02DAO.insertAc02010p01_t01_01";
        String updateQueryId="ac02DAO.updateAc02010p01_t01_01";
        String deleteQueryId="ac02DAO.deleteAc02010p01_t01_01";
        commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
    }
    
    @Override
    public void saveAc02020p01_t01_01(DataSet ds) throws Exception{
        String insertQueryId="ac02DAO.insertAc02020p01_t01_01";
        String updateQueryId="ac02DAO.updateAc02020p01_t01_01";
        String deleteQueryId="ac02DAO.deleteAc02020p01_t01_01";
        commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
    }
    
    
    
    /*#########################################################################################################################################*/
    /* 수입/여입 TAB 증빙내역 (admi/ac/ac02/ac02010_p01_t01.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 *  
	 */
    public List<?> selectAc02010p01_t01_02(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02010p01_t01_02", map);
        return list;
    }
        
    /**
	 *  
	 */
    @Override
    public void saveAc02010p01_t01_02(DataSet ds) throws Exception{
        String insertQueryId="ac02DAO.insertAc02010p01_t01_02";
        String updateQueryId="ac02DAO.updateAc02010p01_t01_02";
        String deleteQueryId="ac02DAO.deleteAc02010p01_t01_02";
        commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
    }
    
    @Override
    public void saveAc02010p01_t01_02_purc(DataSet ds) throws Exception{
        String insertQueryId="ac02DAO.insertAc02010p01_t01_02_purc";
        String updateQueryId="ac02DAO.updateAc02010p01_t01_02_purc";
        String deleteQueryId="ac02DAO.deleteAc02010p01_t01_02_purc";
        commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
    }
    
    
    /*
     * 파일ID 저장
     */
    @Override
    public void saveAc02010p01_t01_02_file(DataSet ds) throws Exception{
  	  String rowType = "";
  	  
  	    for(int i = 0; i < ds.getRowCount(); i++) {
  		  
  		  rowType = ds.getString(i, "rowStatus");
  		  HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
  		  
  		  commonDAO.update("ac02DAO.updateAc02010p01_t01_02_file", hm);
  	    }
    }
    
    /*
     * 파일ID 저장
     */
    @Override
    public void saveAc02010p01_t02_02_file(DataSet ds) throws Exception{
  	  String rowType = "";
  	  
  	    for(int i = 0; i < ds.getRowCount(); i++) {
  		  
  		  rowType = ds.getString(i, "rowStatus");
  		  HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
  		  
  		  commonDAO.update("ac02DAO.updateAc02010p01_t02_02_file", hm);
  	    }
    }
    
    
    
    
    /*#########################################################################################################################################*/
    /* 수입/여입 TAB 원천세 (admi/ac/ac02/ac02010_p01_t01.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 *  
	 */
    public List<?> selectAc02010p01_t01_03(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02010p01_t01_03", map);
        return list;
    }
        
    /**
	 *  
	 */
    @Override
    public void saveAc02010p01_t01_03(DataSet ds) throws Exception{
        String insertQueryId="ac02DAO.insertAc02010p01_t01_03";
        String updateQueryId="ac02DAO.updateAc02010p01_t01_03";
        String deleteQueryId="ac02DAO.deleteAc02010p01_t01_03";
        commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
    }
    
    @Override
    public void saveAc02010p01_t01_03_purc(DataSet ds) throws Exception{
        String insertQueryId="ac02DAO.insertAc02010p01_t01_03_purc";
        String updateQueryId="ac02DAO.updateAc02010p01_t01_03_purc";
        String deleteQueryId="ac02DAO.deleteAc02010p01_t01_03_purc";
        commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
    }
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 수입/여입 TAB 계좌내역(입금) (admi/ac/ac02/ac02010_p01_t01.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 *  
	 */
    public List<?> selectAc02010p01_t01_04(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02010p01_t01_04", map);
        return list;
    }
        
    /**
	 *  
	 */
    @Override
    public void saveAc02010p01_t01_04(DataSet ds) throws Exception{
        String insertQueryId="ac02DAO.insertAc02010p01_t01_04";
        String updateQueryId="ac02DAO.updateAc02010p01_t01_04";
        String deleteQueryId="ac02DAO.deleteAc02010p01_t01_04";
        commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
    }
    
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 수입/여입 TAB 계좌내역(지금) (admi/ac/ac02/ac02010_p01_t01.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 *  
	 */
    public List<?> selectAc02010p01_t01_05(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02010p01_t01_05", map);
        return list;
    }
        
    /**
	 *  
	 */
    @Override
    public void saveAc02010p01_t01_05(DataSet ds) throws Exception{
        String insertQueryId="ac02DAO.insertAc02010p01_t01_05";
        String updateQueryId="ac02DAO.updateAc02010p01_t01_05";
        String deleteQueryId="ac02DAO.deleteAc02010p01_t01_05";
        commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
    }
    
    
    
    
    
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 진행상태 처리 SP
    /*#########################################################################################################################################*/
    /**
	 *  
	 */
    
    
    
    @Override
    public HashMap P_AC_STA_CD_PROC(DataSet ds) throws Exception{
		HashMap map = new HashMap();
		for(int i=0; i<ds.getRowCount(); i++){
			for(int j=0; j<ds.getColumnCount(); j++){
				map.put(ds.getColumn(j).getName(), ds.getString(i, j));
			}
		}
		
		HashMap nMap = new HashMap();
        nMap = commonDAO.saveDataSP("ac02DAO.P_AC_STA_CD_PROC", map);
        return nMap;
    }
    
    @Override
    public HashMap P_AC_STA_CD_PROC_PUCH(DataSet ds) throws Exception{
		HashMap map = new HashMap();
		for(int i=0; i<ds.getRowCount(); i++){
			for(int j=0; j<ds.getColumnCount(); j++){
				map.put(ds.getColumn(j).getName(), ds.getString(i, j));
			}
		}
		
		HashMap nMap = new HashMap();
        nMap = commonDAO.saveDataSP("ac02DAO.P_AC_STA_CD_PROC_PUCH", map);
        return nMap;
    }
    
    
    
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 결의서접수 (admi/ac/ac02/ac02020.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 *  
	 */
    public List<?> selectAc02020_01(Map map) throws Exception{
    	Map map1 = new HashMap();
		HashMap nMap = null;
		nMap = commonDAO.saveDataSP("ac02DAO.saveSpDeuOraMssqlApproChk", map1);
		nMap = commonDAO.saveDataSP("ac02DAO.saveSpDeuOraMssqlSlipChk", map1);
    	List<?> list = commonDAO.selectData("ac02DAO.selectAc02020_01", map);
        return list;
    }
    
    @Override
    public void saveAc02020_purc(DataSet ds) throws Exception{
		String rowType = "";    // ROW 상태
	     
        for (int i = 0; i < ds.getRowCount(); i++) {

            rowType = ds.getString(i, "rowStatus");
            HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
            
            if("insert".equals(rowType)){
                commonDAO.insert("ac02DAO.insertAc02020_purc", hm);
            }else if("update".equals(rowType)){
                commonDAO.update("ac02DAO.updateAc02020_purc", hm);
            }else if("delete".equals(rowType)){
                commonDAO.delete("ac02DAO.deleteAc02020_purc", hm);
            }

        }
    }
    
    @Override
    public void saveAc02020_recv_chg(DataSet ds) throws Exception{
		String rowType = "";    // ROW 상태
	     
        for (int i = 0; i < ds.getRowCount(); i++) {

            rowType = ds.getString(i, "rowStatus");
            HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
            commonDAO.update("ac02DAO.updateAc02020_recv_chg", hm);

        }
    }
    
    public List<?> selectAc02020_purc_p01_1(Map map) throws Exception{
    	Map map1 = new HashMap();
		HashMap nMap = null;		
		nMap = commonDAO.saveDataSP("ac02DAO.saveSpDeuOraMssqlApproChk", map1);
		nMap = commonDAO.saveDataSP("ac02DAO.saveSpDeuOraMssqlSlipChk", map1);
    	List<?> list = commonDAO.selectData("ac02DAO.selectAc02020_purc_p01_1", map);
        return list;
    }
    
    @Override
    public HashMap SP_SLIP_CREATE(DataSet ds) throws Exception{
		HashMap map = new HashMap();
		for(int i=0; i<ds.getRowCount(); i++){
			for(int j=0; j<ds.getColumnCount(); j++){
				map.put(ds.getColumn(j).getName(), ds.getString(i, j));
			}
		}
		
		HashMap nMap = new HashMap();
        nMap = commonDAO.saveDataSP("ac02DAO.SP_SLIP_CREATE", map);
        return nMap;
    }
    
    @Override
    public void saveAc02020_purc_p01_1(DataSet ds) throws Exception{
        String rowType = "";    // ROW 상태
    
        for (int i = 0; i < ds.getRowCount(); i++) {

            rowType = ds.getString(i, "rowStatus");
            HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
            
            if("insert".equals(rowType)){
                commonDAO.insert("ac02DAO.insertAc02020_purc_p01_1", hm);
            }else if("update".equals(rowType)){
                commonDAO.update("ac02DAO.updateAc02020_purc_p01_1", hm);
            }else if("delete".equals(rowType)){
                commonDAO.delete("ac02DAO.deleteAc02020_purc_p01_1", hm);
            }
        }
    }
    
    public List<?> selectAc02020_purc_p01_2(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02020_purc_p01_2", map);
        return list;
    }
    
    public List<?> selectAc02020_purc_p01_3(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02020_purc_p01_3", map);
        return list;
    }
    
    @Override
    public HashMap saveAc02020_01_Search_Approval(DataSet ds) throws Exception{
        HashMap nMap = null;
        for (int i = 0; i < ds.getRowCount(); i++) {

           // rowType = ds.getString(i, "rowStatus");
            HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
            
                Map map = new HashMap();
                
                // TODO ENTRY_NO, ENTRY_IP 넣기
                nMap = commonDAO.saveDataSP("ac02DAO.saveSpDeuOraMssqlSlipChk", map);
        }
        
        return nMap;
    }
    
    /**
	 *  
	 */
    public List<?> selectAc02020_t01_01(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02020_t01_01", map);
        return list;
    }
    
        
    /**
	 *  
	 */
    public List<?> selectAc02020_t02_01(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02020_t02_01", map);
        return list;
    }
    
    
    /**
	 *  
	 */
    public List<?> selectAc02020_t03_01(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02020_t03_01", map);
        return list;
    }
    
    
    /**
	 *  
	 */
    public List<?> selectAc02020_t04_01(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02020_t04_01", map);
        return list;
    }
    
    
    /**
	 *  
	 */
    public List<?> selectAc02020_t04_02(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02020_t04_02", map);
        return list;
    }
    
    @Override
    public void saveAc02020_t04_02(DataSet ds) throws Exception{
        String rowType = "";    // ROW 상태
    
        for (int i = 0; i < ds.getRowCount(); i++) {

            rowType = ds.getString(i, "rowStatus");
            HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
            
            if("insert".equals(rowType)){
                commonDAO.insert("ac02DAO.insertAc02020_t04_02", hm);
            }else if("update".equals(rowType)){
                commonDAO.update("ac02DAO.updateAc02020_t04_02", hm);
            }else if("delete".equals(rowType)){
                commonDAO.delete("ac02DAO.deleteAc02020_t04_02", hm);
            }
        }
    }
    
    /*#########################################################################################################################################*/
    /* 결의서접수 (admi/ac/ac02/ac02020.xfdl)   전자결재                                                                                         */
    /*#########################################################################################################################################*/
    
    /*
	* 전자결재 결의서접수 (경리팀)
	*/
	@Override
    public HashMap saveAc02020_01_Approval(DataSet ds) throws Exception {
		String strId 			= "";
        String strFormId 		= "";
        String strTitle 		= "";
        String strSubjId 		= "";
        String strSubTitle 		= "";
        String strBody 			= "";
        String strApprovalDiv 	= "";
        String strApprovalDt 	= "";
        String strApprovalDocid = "";
        String strUserId 		= "";
        String strUpdDt 		= "";
        String strCnt = "";
        String balDivNm = "";
        String bizNm = "";
        String acntNm1 = "";
        String acntNm2 = "";
        String acntNm3 = "";
        String acntCd = "";
        String budgAmt1 = "";
        String budgAmt2 = "";
        String budgAmt3 = "";
        String budgAmt4 = "";
        String budgAmt5 = "";
        String budgAmt6 = "";
        String budgAmt7 = "";
        String draftDesc = "";
        String remark = "";
        String dr_mok_nm = "";
        String dr_acnt_nm = "";
        String dr_amt = "";
        String dr_amt_tot = "";
        String cr_mok_nm = "";
        String cr_acnt_nm = "";
        String cr_amt = "";
        String cr_amt_tot = "";
        String budgAmt2Tot = "";
        String budgAmt3Tot = "";
        HashMap nMap  = new HashMap();    // 리턴 시킬 map
        HashMap nMap1 = new HashMap();   // 화면에서 넘어온 파라미터를 받을 map 
        HashMap nMap2 = new HashMap();   // 변경신청내역(본문내용) 받을 map
        HashMap nMap3 = new HashMap();   //mssql+oracle 전자결재연동테이블에 입력된 데이타를 예산변경테이블에 넣기위한 map
        HashMap nMap4 = new HashMap();	//전자결재ID담아올 맵
        //  String serverElecUrl = commonDAO.getProValue("Globals.serverElecUrl");  //전자결재 생성 서버url

        for (int i = 0; i < ds.getRowCount(); i++) {
            HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);//화면에서 넘어온 정보
            Map map = new HashMap(); 
            
            nMap1.put("acntUnt", hm.get("acntUnt"));
            nMap1.put("acntYy", hm.get("acntYy"));
            nMap1.put("vouNo", hm.get("vouNo"));
            nMap1.put("aplyDt", hm.get("aplyDt"));
            nMap1.put("aplyUserId", hm.get("aplyUserId"));
            nMap1.put("vouNoElec", hm.get("vouNoElec"));
            
            nMap1.put("procCd", hm.get("procCd"));
            nMap1.put("userId", hm.get("userId"));
            nMap1.put("userIp", hm.get("userIp"));
            nMap1.put("userPgm", hm.get("userPgm"));
            nMap1.put("cfmDt", hm.get("cfmDt"));
            nMap1.put("slipNoDisp", hm.get("slipNo"));
            
            nMap4.put("strElecId", hm.get("strElecId"));
            nMap4.put("strAcntYy", hm.get("strAcntYy"));
            
            //화면에서 넘어온 정보셋팅
            //map.put("id", hm.get("id")); // 전자결재ID
            map.put("title", hm.get("title")); // 타이틀
            map.put("subtitle", hm.get("subtitle")); // 서브타이틀
            map.put("formId", hm.get("formId")); // 양식ID
            map.put("userId", hm.get("userId")); // 기안자ID
            map.put("subjId", hm.get("subjId")); // 리스트상세내용
            
            
            // 본문내용중 변경신청내역 얻기
            List<?> list1 = commonDAO.selectData("ac02DAO.selectAc02010p01_t01_01(elec)", nMap1);

            DataSet rtnDs1 = ConvertUtil.getListToDS(list1);
            
            if (rtnDs1.getRowCount() == 0) {
            	list1 = commonDAO.selectData("ac02DAO.selectAc02010p01_t01_01(elec2)", nMap1);
            	rtnDs1 = ConvertUtil.getListToDS(list1);
            }
        	
            if ( !rtnDs1.getString(0,"crSum").equals(rtnDs1.getString(0,"drSum")) ) {
            	nMap.put("cdChk", "1");
            	nMap.put("chkMsg", "차대변 금액의 합계가 다릅니다.");
            	return nMap;
            }
            
        	// 위에서 얻은 본문내용 갯수만큼 셋팅해주기
        	// 본문내용중 변경신청내역 얻기
            List<?> list2 = commonDAO.selectData("ac02DAO.selectAc02030_t01_01(elec)", nMap1); //차변

        	DataSet rtnDs2 = ConvertUtil.getListToDS(list2);//차변
        	
        	List<?> list3 = commonDAO.selectData("ac02DAO.selectAc02030_t01_02(elec)", nMap1); //대변

        	DataSet rtnDs3 = ConvertUtil.getListToDS(list3);//대변
            
            String strHtml = "<html>";
        	strHtml += "<head>";
        	strHtml += "<style type=\"text/css\"> ";
        	strHtml += " table, td {   margin: 0 0 0 0; padding: 0 0 0 0; border-spacing: 0px 0px; border-collapse:collapse;}    ";
        	strHtml += " </style>";
        	strHtml += " </head>";
        	strHtml += " <body style=\"margin: 0 0 0 0;\" >";
        	strHtml += " <div>";
        	//strHtml += "<table width=\"671\" border=\"0\" style=\"margin:0 0 0 0; padding:0 0 0 0; border-collapse:collapse;\" cellspacing=\"0\" cellpadding=\"0\">";
        	//strHtml += "    <tr>";
        	//strHtml += "    	<td nowrap style=\"margin:0 0 0 0; \">";
        	strHtml += "	    	<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\" style=\"table-layout:fixed; margin:0 0 0 0; padding:0 0 0 0; border-collapse:collapse;\">";
        	strHtml += "				<tr>";
        	strHtml += "					<td align=\"center\" colspan=\"2\" width=\"70\" height=\"30\" style=\"text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">발의</td>";
			strHtml += "					<td align=\"center\" width=\"160\" align=\"center\" style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">"+rtnDs1.getString(0,"aplyDt1")+"</td>";
			strHtml += "					<td align=\"center\" width=\"70\" style=\" text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">구분</td>";
			strHtml += "					<td align=\"center\" width=\"153\" style=\"text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">"+rtnDs1.getString(0,"acntUntNm")+"</td>";
			strHtml += "					<td align=\"center\" colspan=\"2\" width=\"70\" style=\" text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">"+rtnDs1.getString(0,"dateGb")+"</td>";
			strHtml += "					<td align=\"center\" colspan=\"2\" width=\"155\" style=\" text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\"></td>";
        	strHtml += "				</tr>";
        	strHtml += "				<tr>";
        	strHtml += "					<td align=\"center\" colspan=\"2\" width=\"70\" height=\"30\" style=\"border-right: 0px none; margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">一金</td>";
        	strHtml += "					<td align=\"left\" colspan=\"4\" width=\"423\" style=\"border-left: 0px none; border-right: 0px none; margin-top: 0.0pt;    margin-bottom: 0.0pt;     text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+rtnDs1.getString(0,"aplyAmtTotHan2")+" 원정</td>";
        	strHtml += "				    <td align=\"right\" width=\"30\" style=\"border-left: 0px none; border-right: 0px none; margin-top: 0.0pt;    margin-bottom: 0.0pt;     text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림; font-weight:bold;\">(￦  </td>";
        	strHtml += "					<td align=\"left\" width=\"130\" style=\"border-left: 0px none; border-right: 0px none; margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: left;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\"> "+rtnDs1.getString(0,"aplyAmtTot2")+"</td>"; 
        	strHtml += "				    <td align=\"left\" width=\"25\" style=\"border-left: 0px none; margin-top: 0.0pt; margin-bottom: 0.0pt; text-indent: 0.0pt; line-height: 120%; font-size: 8pt; font-family: 굴림;\">)</td>";
        	strHtml += "				</tr>";
        	strHtml += "				<tr>";
        	strHtml += "					<td align=\"center\" width=\"30\" height=\"90\" style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">적<br><br>요</td>";
        	strHtml += "					<td valign=\"top\" nowrap width=\"463\" colspan=\"5\" style=\"overflow:hidden;\"><p style=\"margin-top: 4px; font-family: 굴림; \">"+rtnDs1.getString(0,"jukyo")+"</p></td>";
        	
        	if("수입처".equals(rtnDs1.getString(0,"subTitle1"))) {
        		strHtml += "					<td align=\"center\" width=\"30\" style=\"margin-top: 0.0pt; margin-bottom: 0.0pt; text-indent: 0.0pt; line-height: 120%; font-size: 8pt; font-family: 굴림; \">수<br><br>입<br><br>처</td>";
        	} else {
        		strHtml += "					<td align=\"center\" width=\"30\" style=\"margin-top: 0.0pt; margin-bottom: 0.0pt; text-indent: 0.0pt; line-height: 120%; font-size: 8pt; font-family: 굴림; \">영<br><br>수<br><br>란</td>";
        	}
        	
        	strHtml += "					<td valign=\"top\" nowrap width=\"155\" colspan=\"2\"><p style=\"margin-top: 4px;  font-family: 굴림; \">"+rtnDs1.getString(0,"sdVendorDesc")+"</p></td>";
        	strHtml += "				</tr>";
        	strHtml += "	  		</table>";
        	//strHtml += "		</td>";
        	//strHtml += "	</tr>";
        	strHtml += "	</div>";
        	strHtml += "	<div>";
        	//strHtml += "	<tr>";
        	//strHtml += "		<td nowrap>";
        	strHtml += " 			<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse:collapse;border:none; margin:0px;\">";
			strHtml += " 				<tr>";
			strHtml += " 					<td align=\"center\" width=\"110\" height=\"30\" style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">계 정 과 목</td>";
			strHtml += " 		  			<td align=\"center\" width=\"115\" style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">예 산 액</td>";
			strHtml += " 					<td align=\"center\" width=\"116\" style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">기 집 행 액</td>";
			strHtml += " 					<td align=\"center\" width=\"116\" style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">금회 신청액</td>";
			strHtml += " 					<td align=\"center\" width=\"115\" style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">누 계 액</td>";
			strHtml += " 					<td align=\"center\" width=\"106\" style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">예 산 잔 액</td>";
			strHtml += " 				</tr>";
			// 위에서 얻은 본문내용 갯수만큼 셋팅해주기
        	// 본문내용중 변경신청내역 얻기
        	for(int j=0; j < 5; j++ ) {
				
            	if(rtnDs1.getString(j,"acntCdMokNm") == "" || rtnDs1.getString(j,"acntCdMokNm") == null ){
            		acntNm1 	= " ";
            	}else {
            		acntNm1 	= rtnDs1.getString(j,"acntCdMokNm"); //목명
            	}
            	if(rtnDs1.getString(j,"acntCdNm") == "" || rtnDs1.getString(j,"acntCdNm") == null || rtnDs1.getString(j,"acntCdNm").equals(rtnDs1.getString(j,"acntCdMokNm"))){
            		acntNm2 	= " ";
            	}else {
            		acntNm2 	= rtnDs1.getString(j,"acntCdNm"); //세목명
            	}
            	if(rtnDs1.getString(j,"budgAmt1") == "" || rtnDs1.getString(j,"budgAmt1") == null ){
            		budgAmt1 	= " ";
            	}else {
            		budgAmt1 	= rtnDs1.getString(j,"budgAmt1"); //예산액
            	}
            	if(rtnDs1.getString(j,"budgAmt2") == "" || rtnDs1.getString(j,"budgAmt2") == null ){
            		budgAmt2 	= " ";
            	}else {
            		budgAmt2 	= rtnDs1.getString(j,"budgAmt2");//집행액
            	}
            	if(rtnDs1.getString(j,"budgAmt3") == "" || rtnDs1.getString(j,"budgAmt3") == null ){
            		budgAmt3 	= " ";
            	}else {
            		budgAmt3 	= rtnDs1.getString(j,"budgAmt3"); //예산잔액
            	}
            	if(rtnDs1.getString(j,"budgAmt4") == "" || rtnDs1.getString(j,"budgAmt4") == null ){
            		budgAmt4 	= " ";
            	}else {
            		budgAmt4 	= rtnDs1.getString(j,"budgAmt4"); //금회 신청액
            	}
            	if(rtnDs1.getString(j,"budgAmt5") == "" || rtnDs1.getString(j,"budgAmt5") == null ){
            		budgAmt5 	= " ";
            	}else {
            		budgAmt5 	= rtnDs1.getString(j,"budgAmt5"); //기집행액
            	}
            	if(rtnDs1.getString(j,"budgAmt6") == "" || rtnDs1.getString(j,"budgAmt6") == null ){
            		budgAmt6 	= " ";
            	}else {
            		budgAmt6 	= rtnDs1.getString(j,"budgAmt6"); //누계액
            	}
            	if(rtnDs1.getString(j,"budgAmt7") == "" || rtnDs1.getString(j,"budgAmt7") == null ){
            		budgAmt7 	= " ";
            	}else {
            		budgAmt7 	= rtnDs1.getString(j,"budgAmt7"); //누계액
            	}
	        	strHtml += "				<tr>";
	        	strHtml += "					<td height=\"30\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+acntNm1+"<br>"+acntNm2+"</p></td>";
	        	strHtml += "					<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+budgAmt1+"</p></td>";
	        	strHtml += "					<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+budgAmt5+"</p></td>";
	        	strHtml += "					<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+budgAmt4+"</p></td>";
	        	strHtml += "					<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+budgAmt6+"</p></td>";
	        	strHtml += "					<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+budgAmt7+"</p></td>";
	        	strHtml += "				</tr>";
            }
			strHtml += " 				<tr>";
			strHtml += " 					<td align=\"center\" height=\"30\" style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">계 약 일 자</td>";
			strHtml += " 					<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">" + rtnDs1.getString(0,"promiseDt") +"</p></td>";
			strHtml += " 					<td colspan=\"4\" style=\" border-bottom: 0px none; border-right: 0px none;\">";
			strHtml += " 				</tr>";
			strHtml += " 				<tr>";
			strHtml += " 					<td align=\"center\" height=\"30\" style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">검 수 일 자</td>";
			strHtml += " 					<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">" + rtnDs1.getString(0,"recvDt") + "</p></td>";
			strHtml += " 					<td colspan=\"4\" style=\"border-top: 0px none; border-bottom: 0px none; border-right: 0px none;\">";
			strHtml += " 				</tr>";
			strHtml += " 			</table>";
			//strHtml += "		</td>";
        	//strHtml += "	</tr>";
			strHtml += "	</div>";
			strHtml += "	<div>";
        	//strHtml += "	<tr>";
        	//strHtml += "		<td nowrap>";
        	strHtml += "			<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse:collapse;border:none; margin:0px;\">";
        	strHtml += "				 <tr>";
        	strHtml += "					<td width=\"110\" height=\"30\" style=\"border-left: 0px none; border-right: 0px none; border-top: 0px none; border-bottom: 0px none;\"></td>";
        	strHtml += "					<td  width=\"115\" style=\"border-left: 0px none; border-right: 0px none; border-top: 0px none; border-bottom: 0px none;\"></td>";
        	strHtml += "					<td  width=\"232\" style=\"border-left: 0px none; border-right: 0px none; border-top: 0px none; border-bottom: 0px none;\">";
        	strHtml += "					<p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 16pt;    font-family: 굴림;   font-weight:bold;\"><u>"+rtnDs1.getString(0,"subTitle2")+"</u></p></td>";
        	strHtml += "					<td  width=\"69\" style=\"border-left: 0px none; border-right: 0px none; border-top: 0px none; border-bottom: 0px none;\"></td>";
        	strHtml += "					<td  width=\"145\" style=\"border-left: 0px none; border-right: 0px none; border-top: 0px none; border-bottom: 0px none;\"></td>";
        	strHtml += "				</tr>";
        	strHtml += "				<tr>";
        	strHtml += "					<td width=\"110\" height=\"30\" style=\"border-left: 0px none; border-right: 0px none; border-top: 0px none; border-bottom: 0px none;\">";
        	strHtml += "					<p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: left;    text-indent: 0.0pt;    line-height: 140%;    font-size: 10pt;    font-family: 굴림;\">일자 : </p></td>";
        	strHtml += "					<td  width=\"115\" style=\"border-left: 0px none; border-right: 0px none; border-top: 0px none; border-bottom: 0px none;\">";
        	strHtml += "					<p style='margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: left;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;'></p></td>";
        	strHtml += "					<td style=\"padding-right:232px; border-left: 0px none; border-right: 0px none; border-top: 0px none; border-bottom: 0px none;\"> ";
        	strHtml += "					<td  colspan=\"2\" style=\"border-left: 0px none; border-right: 0px none; border-top: 0px none; border-bottom: 0px none;\">";
        	strHtml += "					<p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 10pt;    font-family: 굴림;\">결의서번호 : "+nMap1.get("slipNoDisp")+"</p></td>";
        	strHtml += "				</tr>";
        	strHtml += "			</table>";
        	//strHtml += "		</td>";
        	//strHtml += "	</tr>";
        	strHtml += "	</div>";
        	strHtml += "	<div>";
        	//strHtml += "	<tr>"; 
        	//strHtml += "		<td nowrap>";
        	strHtml += "			<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse:collapse;border:none; margin:0px;\">";
        	strHtml += "				<tr>";
        	strHtml += "					<td width=\"162\" height=\"30\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 10pt;    font-family: 굴림;\">차 변 과 목</p></td>";
        	strHtml += "					<td width=\"177\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 10pt;    font-family: 굴림;\">금       액</p></td>";
        	strHtml += "					<td width=\"162\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 10pt;    font-family: 굴림;\">대 변 과 목</p></td>";
        	strHtml += "					<td width=\"177\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 10pt;    font-family: 굴림;\">금       액</p></td>";
        	strHtml += "				</tr>";
        	// 위에서 얻은 본문내용 갯수만큼 셋팅해주기
        	// 본문내용중 변경신청내역 얻기
        	
        	for(int k=0; k < 5; k++ ) {
        		
        		if(rtnDs2.getString(k,"acntCdMokNm") == "" || rtnDs2.getString(k,"acntCdMokNm") == null ){
        			dr_mok_nm 	= " ";
            	}else {
            		dr_mok_nm 	= rtnDs2.getString(k,"acntCdMokNm");
            	}
            	if(rtnDs2.getString(k,"acntCdSemokNm") == "" || rtnDs2.getString(k,"acntCdSemokNm") == null || rtnDs2.getString(k,"acntCdSemokNm").equals(rtnDs2.getString(k,"acntCdMokNm"))){
            		dr_acnt_nm 	= " ";
            	}else {
            		dr_acnt_nm 	= rtnDs2.getString(k,"acntCdSemokNm"); 
            	}
            	if(rtnDs2.getString(k,"slipAmt") == "" || rtnDs2.getString(k,"slipAmt") == null ){
            		dr_amt 	= " ";
            	}else {
            		dr_amt 	= rtnDs2.getString(k,"slipAmt"); 
            	}
            	if(rtnDs2.getString(0,"slipAmtTot") == "" || rtnDs2.getString(0,"slipAmtTot") == null ){
            		dr_amt_tot 	= " ";
            	}else {
            		dr_amt_tot 	= rtnDs2.getString(0,"slipAmtTot"); 
            	}
            	
            	if(rtnDs3.getString(k,"acntCdMokNm") == "" || rtnDs3.getString(k,"acntCdMokNm") == null ){
            		cr_mok_nm 	= " ";
            	}else {
            		cr_mok_nm 	= rtnDs3.getString(k,"acntCdMokNm");
            	}
            	if(rtnDs3.getString(k,"acntCdSemokNm") == "" || rtnDs3.getString(k,"acntCdSemokNm") == null || rtnDs3.getString(k,"acntCdSemokNm").equals(rtnDs3.getString(k,"acntCdMokNm"))){
            		cr_acnt_nm 	= " ";
            	}else {
            		cr_acnt_nm 	= rtnDs3.getString(k,"acntCdSemokNm"); 
            	}
            	if(rtnDs3.getString(k,"slipAmt") == "" || rtnDs3.getString(k,"slipAmt") == null ){
            		cr_amt 	= " ";
            	}else {
            		cr_amt 	= rtnDs3.getString(k,"slipAmt"); 
            	}
            	if(rtnDs3.getString(0,"slipAmtTot") == "" || rtnDs3.getString(0,"slipAmtTot") == null ){
            		cr_amt_tot 	= " ";
            	}else {
            		cr_amt_tot 	= rtnDs3.getString(0,"slipAmtTot"); 
            	}
  	            
	        	strHtml += "			<tr>";
	        	strHtml += "				<td height=\"30\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+dr_mok_nm+"<br>"+dr_acnt_nm+"</p></td>";
	        	strHtml += "				<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+dr_amt+"</p></td>";
	        	strHtml += "				<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+cr_mok_nm+"<br>"+cr_acnt_nm+"</p></td>";
	        	strHtml += "				<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+cr_amt+"</p></td>";
	        	strHtml += "			</tr>";
        	}
        	strHtml += "				<tr>";
        	strHtml += "					<td height=\"30\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림; \">합    계</p></td>";
        	strHtml += "					<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+dr_amt_tot+"</p></td>";
        	strHtml += "					<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">합    계</p></td>";
        	strHtml += "					<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+cr_amt_tot+"</p></td>";
        	strHtml += "				</tr>";
        	strHtml += "				<tr>";
        	strHtml += "					<td height=\"30\" colspan=\"4\" style=\"border-right: 0px none; border-left: 0px none; border-bottom: 0px none;\"><p style=\"margin-top: 0.0pt; margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림; \">"+rtnDs1.getString(0,"approvalDocno2")+"</p></td>";
        	strHtml += "				</tr>";
        	strHtml += "			</table>";
        	//strHtml += "		</td>";
        	//strHtml += "	</tr>";
        	//strHtml += "	</table>";
        	strHtml += "	</body>";
        	strHtml += "</html>";

            List<?> list4 = commonDAO.selectData("admiDAO.selectGwApprovalMstElecId", nMap4);

        	DataSet rtnDs4 = ConvertUtil.getListToDS(list4);
            
            nMap.put("id", rtnDs4.getString(0,"elecId"));
            nMap.put("formId",   map.get("formId"));
            nMap.put("title",    map.get("title"));
            nMap.put("subTitle", map.get("subTitle"));
            nMap.put("subjId",   map.get("subjId"));
            
            nMap.put("vouNo",   nMap1.get("vouNo"));
            nMap.put("bizNm",   rtnDs1.getString(0,"bizCdNm"));
            nMap.put("vouNoElec",   nMap1.get("vouNoElec"));
            
            nMap.put("body", strHtml);
            nMap.put("approvalDiv", strApprovalDiv);
            nMap.put("approvalDt", strApprovalDt);
            nMap.put("approvalDocid", strApprovalDocid);
            nMap.put("userId", map.get("userId"));
            
            System.out.println(nMap);
            System.out.println("_________________________"); 
            
            nMap3.put("id", rtnDs4.getString(0,"elecId"));
            nMap3.put("formId",   map.get("formId"));
            nMap3.put("approvalDiv", strApprovalDiv);
            nMap3.put("approvalDt", strApprovalDt);
            nMap3.put("approvalUserid", map.get("userId"));
            
            nMap3.put("acntUnt", nMap1.get("acntUnt"));
            nMap3.put("acntYy", nMap1.get("acntYy"));
            nMap3.put("vouNo", nMap1.get("vouNo"));
            nMap3.put("aplyDt", nMap1.get("aplyDt"));
            nMap3.put("aplyUserId", nMap1.get("aplyUserId"));
            
            nMap3.put("procCd", nMap1.get("procCd"));
            nMap3.put("userId", nMap1.get("userId"));
            nMap3.put("userIp", nMap1.get("userIp"));
            nMap3.put("userPgm", nMap1.get("userPgm"));
            nMap3.put("cfmDt", nMap1.get("cfmDt"));
            
            //mssql 전자결재연동테이블에 인서트한다.
            msDAO.insert("admiDAO.insertGwApprovalMst", nMap);
            
            //oracle 결의서접수테이블(ADMI.AC_SLIP)에 업데이트한다.
            commonDAO.update("ac02DAO.updateAc02020p01_01AplyGwApprovalMstOra", nMap3);
            
            //commonDAO.saveDataSP("ac02DAO.P_AC_STA_CD_PROC", nMap3);
        } 
        return nMap;
    }
	
	/*
	* 전자결재 결의서접수 (구매팀)
	*/
	@Override
    public HashMap saveAc02020_01_Approval_puch(DataSet ds) throws Exception {
		String strId 			= "";
        String strFormId 		= "";
        String strTitle 		= "";
        String strSubjId 		= "";
        String strSubTitle 		= "";
        String strBody 			= "";
        String strApprovalDiv 	= "";
        String strApprovalDt 	= "";
        String strApprovalDocid = "";
        String strUserId 		= "";
        String strUpdDt 		= "";
        String strCnt = "";
        String balDivNm = "";
        String bizNm = "";
        String acntNm1 = "";
        String acntNm2 = "";
        String acntNm3 = "";
        String acntCd = "";
        String budgAmt1 = "";
        String budgAmt2 = "";
        String budgAmt3 = "";
        String budgAmt4 = "";
        String budgAmt5 = "";
        String budgAmt6 = "";
        String budgAmt7 = "";
        String draftDesc = "";
        String remark = "";
        String dr_mok_nm = "";
        String dr_acnt_nm = "";
        String dr_amt = "";
        String dr_amt_tot = "";
        String cr_mok_nm = "";
        String cr_acnt_nm = "";
        String cr_amt = "";
        String cr_amt_tot = "";
        String budgAmt2Tot = "";
        String budgAmt3Tot = "";
        HashMap nMap  = new HashMap();    // 리턴 시킬 map
        HashMap nMap1 = new HashMap();   // 화면에서 넘어온 파라미터를 받을 map 
        HashMap nMap2 = new HashMap();   // 변경신청내역(본문내용) 받을 map
        HashMap nMap3 = new HashMap();   //mssql+oracle 전자결재연동테이블에 입력된 데이타를 예산변경테이블에 넣기위한 map
        HashMap nMap4 = new HashMap();	//전자결재ID담아올 맵
        //  String serverElecUrl = commonDAO.getProValue("Globals.serverElecUrl");  //전자결재 생성 서버url

        for (int i = 0; i < ds.getRowCount(); i++) {
            HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);//화면에서 넘어온 정보
            Map map = new HashMap(); 
            
            nMap1.put("acntUnt", hm.get("acntUnt"));
            nMap1.put("acntYy", hm.get("acntYy"));
            nMap1.put("slipNo", hm.get("slipNo"));
            nMap1.put("vouNo", hm.get("vouNo"));
            nMap1.put("aplyDt", hm.get("aplyDt"));
            nMap1.put("aplyUserId", hm.get("aplyUserId"));
            nMap1.put("vouNoElec", hm.get("vouNoElec"));
            nMap1.put("slipNoDisp", hm.get("slipNoDisp"));
            
            nMap1.put("procCd", hm.get("procCd"));
            nMap1.put("userId", hm.get("userId"));
            nMap1.put("userIp", hm.get("userIp"));
            nMap1.put("userPgm", hm.get("userPgm"));
            nMap1.put("cfmDt", hm.get("cfmDt"));
            
            nMap4.put("strElecId", hm.get("strElecId"));
            nMap4.put("strAcntYy", hm.get("strAcntYy"));
            
            //화면에서 넘어온 정보셋팅
            //map.put("id", hm.get("id")); // 전자결재ID
            map.put("title", hm.get("title")); // 타이틀
            map.put("subtitle", hm.get("subtitle")); // 서브타이틀
            map.put("formId", hm.get("formId")); // 양식ID
            map.put("userId", hm.get("userId")); // 기안자ID
            map.put("subjId", hm.get("subjId")); // 리스트상세내용
            
            // 본문내용중 변경신청내역 얻기
            List<?> list1 = commonDAO.selectData("ac02DAO.selectAc02010p01_t01_01_puch(elec)", nMap1);
        	DataSet rtnDs1 = ConvertUtil.getListToDS(list1);
        	
            if ( !rtnDs1.getString(0,"crSum").equals(rtnDs1.getString(0,"drSum")) ) {
            	nMap.put("cdChk", "1");
            	nMap.put("chkMsg", "차대변 금액의 합계가 다릅니다.");
            	return nMap;
            }
        	
        	// 위에서 얻은 본문내용 갯수만큼 셋팅해주기
        	// 본문내용중 변경신청내역 얻기
            List<?> list2 = commonDAO.selectData("ac02DAO.selectAc02030_t01_01_puch_elec", nMap1); //차변

        	DataSet rtnDs2 = ConvertUtil.getListToDS(list2);//차변
        	
        	List<?> list3 = commonDAO.selectData("ac02DAO.selectAc02030_t01_02_puch_elec", nMap1); //대변

        	DataSet rtnDs3 = ConvertUtil.getListToDS(list3);//대변
            
            String strHtml = "<html>";
        	strHtml += "<head>";
        	strHtml += "<style type=\"text/css\"> ";
        	strHtml += " table, td {   margin: 0 0 0 0; padding: 0 0 0 0; border-spacing: 0px 0px; border-collapse:collapse;}    ";
        	strHtml += " </style>";
        	strHtml += " </head>";
        	strHtml += " <body style=\"margin: 0 0 0 0;\" >";
        	strHtml += " <div>";
        	//strHtml += "<table width=\"671\" border=\"0\" style=\"margin:0 0 0 0; padding:0 0 0 0; border-collapse:collapse;\" cellspacing=\"0\" cellpadding=\"0\">";
        	//strHtml += "    <tr>";
        	//strHtml += "    	<td nowrap style=\"margin:0 0 0 0; \">";
        	strHtml += "	    	<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\" style=\"margin:0 0 0 0; padding:0 0 0 0; border-collapse:collapse;\">";
        	strHtml += "				<tr>";
        	strHtml += "					<td align=\"center\" colspan=\"2\" width=\"70\" height=\"30\" style=\"text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">발의</td>";
			strHtml += "					<td align=\"center\" width=\"160\" align=\"center\" style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">"+rtnDs1.getString(0,"aplyDt1")+"</td>";
			strHtml += "					<td align=\"center\" width=\"70\" style=\" text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">구분</td>";
			strHtml += "					<td align=\"center\" width=\"153\" style=\"text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">"+rtnDs1.getString(0,"acntUntNm")+"</td>";
			strHtml += "					<td align=\"center\" colspan=\"2\" width=\"70\" style=\" text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">"+rtnDs1.getString(0,"dateGb")+"</td>";
			strHtml += "					<td align=\"center\" colspan=\"2\" width=\"155\" style=\" text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\"></td>";
        	strHtml += "				</tr>";
        	strHtml += "				<tr>";
        	strHtml += "					<td align=\"center\" colspan=\"2\" width=\"70\" height=\"30\" style=\"border-right: 0px none; margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">一金</td>";
        	strHtml += "					<td align=\"left\" colspan=\"4\" width=\"423\" style=\"border-left: 0px none; border-right: 0px none; margin-top: 0.0pt;    margin-bottom: 0.0pt;     text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+rtnDs1.getString(0,"aplyAmtTotHan2")+" 원정</td>";
        	strHtml += "				    <td align=\"right\" width=\"30\" style=\"border-left: 0px none; border-right: 0px none; margin-top: 0.0pt;    margin-bottom: 0.0pt;     text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림; font-weight:bold;\">(￦  </td>";
        	strHtml += "					<td align=\"left\" width=\"130\" style=\"border-left: 0px none; border-right: 0px none; margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: left;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\"> "+rtnDs1.getString(0,"aplyAmtTot2")+"</td>"; 
        	strHtml += "				    <td align=\"left\" width=\"25\" style=\"border-left: 0px none; margin-top: 0.0pt; margin-bottom: 0.0pt; text-indent: 0.0pt; line-height: 120%; font-size: 8pt; font-family: 굴림;\">)</td>";
        	strHtml += "				</tr>";
        	strHtml += "				<tr>";
        	strHtml += "					<td align=\"center\" width=\"30\" height=\"90\" style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">적<br><br>요</td>";
        	strHtml += "					<td valign=\"top\" width=\"463\" colspan=\"5\"><p style=\"margin-top: 4px; font-family: 굴림; \">"+rtnDs1.getString(0,"jukyo")+"</p></td>";
        	strHtml += "					<td align=\"center\" width=\"30\" style=\"margin-top: 0.0pt; margin-bottom: 0.0pt; text-indent: 0.0pt; line-height: 120%; font-size: 8pt; font-family: 굴림; \">영<br><br>수<br><br>란</td>";
        	strHtml += "					<td valign=\"top\" width=\"155\" colspan=\"2\"><p style=\"margin-top: 4px; font-family: 굴림; \">"+rtnDs1.getString(0,"sdVendorDesc")+"</p></td>";
        	strHtml += "				</tr>";
        	strHtml += "	  		</table>";
        	//strHtml += "		</td>";
        	//strHtml += "	</tr>";
        	strHtml += "	</div>";
        	strHtml += "	<div>";
        	//strHtml += "	<tr>";
        	//strHtml += "		<td nowrap>";
        	strHtml += " 			<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse:collapse;border:none; margin:0px;\">";
			strHtml += " 				<tr>";
			strHtml += " 					<td align=\"center\" width=\"110\" height=\"30\" style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">계 정 과 목</td>";
			strHtml += " 		  			<td align=\"center\" width=\"115\" style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">예 산 액</td>";
			strHtml += " 					<td align=\"center\" width=\"116\" style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">기 집 행 액</td>";
			strHtml += " 					<td align=\"center\" width=\"116\" style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">금회 신청액</td>";
			strHtml += " 					<td align=\"center\" width=\"115\" style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">누 계 액</td>";
			strHtml += " 					<td align=\"center\" width=\"106\" style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">예 산 잔 액</td>";
			strHtml += " 				</tr>";
			// 위에서 얻은 본문내용 갯수만큼 셋팅해주기
        	// 본문내용중 변경신청내역 얻기
			
        	for(int j=0; j < 5; j++ ) {
				
            	if(rtnDs1.getString(j,"acntCdMokNm") == "" || rtnDs1.getString(j,"acntCdMokNm") == null ){
            		acntNm1 	= " ";
            	}else {
            		acntNm1 	= rtnDs1.getString(j,"acntCdMokNm"); //목명
            	}
            	if(rtnDs1.getString(j,"acntCdNm") == "" || rtnDs1.getString(j,"acntCdNm") == null || rtnDs1.getString(j,"acntCdNm").equals(rtnDs1.getString(j,"acntCdMokNm"))){
            		acntNm2 	= " ";
            	}else {
            		acntNm2 	= rtnDs1.getString(j,"acntCdNm"); //세목명
            	}
            	if(rtnDs1.getString(j,"budgAmt1") == "" || rtnDs1.getString(j,"budgAmt1") == null ){
            		budgAmt1 	= " ";
            	}else {
            		budgAmt1 	= rtnDs1.getString(j,"budgAmt1"); //예산액
            	}
            	if(rtnDs1.getString(j,"budgAmt2") == "" || rtnDs1.getString(j,"budgAmt2") == null ){
            		budgAmt2 	= " ";
            	}else {
            		budgAmt2 	= rtnDs1.getString(j,"budgAmt2");//기집행액
            	}
            	if(rtnDs1.getString(j,"budgAmt3") == "" || rtnDs1.getString(j,"budgAmt3") == null ){
            		budgAmt3 	= " ";
            	}else {
            		budgAmt3 	= rtnDs1.getString(j,"budgAmt3"); //예산잔액
            	}
            	if(rtnDs1.getString(j,"budgAmt4") == "" || rtnDs1.getString(j,"budgAmt4") == null ){
            		budgAmt4 	= " ";
            	}else {
            		budgAmt4 	= rtnDs1.getString(j,"budgAmt4"); //누계액
            	}
            	if(rtnDs1.getString(j,"budgAmt5") == "" || rtnDs1.getString(j,"budgAmt5") == null ){
            		budgAmt5 	= " ";
            	}else {
            		budgAmt5 	= rtnDs1.getString(j,"budgAmt5"); //금회 신청액
            	}
            	if(rtnDs1.getString(j,"budgAmt6") == "" || rtnDs1.getString(j,"budgAmt6") == null ){
            		budgAmt6 	= " ";
            	}else {
            		budgAmt6 	= rtnDs1.getString(j,"budgAmt6"); //금회 신청액
            	}
            	if(rtnDs1.getString(j,"budgAmt7") == "" || rtnDs1.getString(j,"budgAmt7") == null ){
            		budgAmt7 	= " ";
            	}else {
            		budgAmt7 	= rtnDs1.getString(j,"budgAmt7"); //금회 신청액
            	}
	        	strHtml += "				<tr>";
	        	strHtml += "					<td height=\"30\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+acntNm1+"<br>"+acntNm2+"</p></td>";
	        	strHtml += "					<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+budgAmt1+"</p></td>";
	        	strHtml += "					<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+budgAmt5+"</p></td>";
	        	strHtml += "					<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+budgAmt4+"</p></td>";
	        	strHtml += "					<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+budgAmt6+"</p></td>";
	        	strHtml += "					<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+budgAmt7+"</p></td>";
	        	strHtml += "				</tr>";
            }
			strHtml += " 				<tr>";
			strHtml += " 					<td align=\"center\" height=\"30\" style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">계 약 일 자</td>";
			strHtml += " 					<td><p style=\"text-align: center; \">" + rtnDs1.getString(0,"promiseDt") +"</p></td>";
			strHtml += " 					<td colspan=\"4\" style=\" border-bottom: 0px none; border-right: 0px none;\">";
			strHtml += " 				</tr>";
			strHtml += " 				<tr>";
			strHtml += " 					<td align=\"center\" height=\"30\" style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 8pt;    font-family: 굴림;    font-weight:bold;\">검 수 일 자</td>";
			strHtml += " 					<td><p style=\"text-align: center; \">" + rtnDs1.getString(0,"elecAplyDt") + "</p></td>";
			strHtml += " 					<td colspan=\"4\" style=\"border-top: 0px none; border-bottom: 0px none; border-right: 0px none;\">";
			strHtml += " 				</tr>";
			strHtml += " 			</table>";
			//strHtml += "		</td>";
        	//strHtml += "	</tr>";
			strHtml += "	</div>";
			strHtml += "	<div>";
        	//strHtml += "	<tr>";
        	//strHtml += "		<td nowrap>";
        	strHtml += "			<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse:collapse;border:none; margin:0px;\">";
        	strHtml += "				 <tr>";
        	strHtml += "					<td width=\"110\" height=\"30\" style=\"border-left: 0px none; border-right: 0px none; border-top: 0px none; border-bottom: 0px none;\"></td>";
        	strHtml += "					<td  width=\"115\" style=\"border-left: 0px none; border-right: 0px none; border-top: 0px none; border-bottom: 0px none;\"></td>";
        	strHtml += "					<td  width=\"232\" style=\"border-left: 0px none; border-right: 0px none; border-top: 0px none; border-bottom: 0px none;\">";
        	strHtml += "					<p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 16pt;    font-family: 굴림;   font-weight:bold;\"><u>"+rtnDs1.getString(0,"subTitle2")+"</u></p></td>";
        	strHtml += "					<td  width=\"69\" style=\"border-left: 0px none; border-right: 0px none; border-top: 0px none; border-bottom: 0px none;\"></td>";
        	strHtml += "					<td  width=\"145\" style=\"border-left: 0px none; border-right: 0px none; border-top: 0px none; border-bottom: 0px none;\"></td>";
        	strHtml += "				</tr>";
        	strHtml += "				<tr>";
        	strHtml += "					<td width=\"110\" height=\"30\" style=\"border-left: 0px none; border-right: 0px none; border-top: 0px none; border-bottom: 0px none;\">";
        	strHtml += "					<p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: left;    text-indent: 0.0pt;    line-height: 140%;    font-size: 10pt;    font-family: 굴림;\">일자 : </p></td>";
        	strHtml += "					<td  width=\"115\" style=\"border-left: 0px none; border-right: 0px none; border-top: 0px none; border-bottom: 0px none;\">";
        	strHtml += "					<p style='margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: left;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;'></p></td>";
        	strHtml += "					<td style=\"padding-right:232px; border-left: 0px none; border-right: 0px none; border-top: 0px none; border-bottom: 0px none;\"> ";
        	strHtml += "					<td  colspan=\"2\" style=\"border-left: 0px none; border-right: 0px none; border-top: 0px none; border-bottom: 0px none;\">";
        	strHtml += "					<p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 10pt;    font-family: 굴림;\">결의서번호 : "+nMap1.get("slipNoDisp")+"</p></td>";
        	strHtml += "				</tr>";
        	strHtml += "			</table>";
        	//strHtml += "		</td>";
        	//strHtml += "	</tr>";
        	strHtml += "	</div>";
        	strHtml += "	<div>";
        	//strHtml += "	<tr>"; 
        	//strHtml += "		<td nowrap>";
        	strHtml += "			<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse:collapse;border:none; margin:0px;\">";
        	strHtml += "				<tr>";
        	strHtml += "					<td width=\"162\" height=\"30\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 10pt;    font-family: 굴림;\">차 변 과 목</p></td>";
        	strHtml += "					<td width=\"177\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 10pt;    font-family: 굴림;\">금       액</p></td>";
        	strHtml += "					<td width=\"162\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 10pt;    font-family: 굴림;\">대 변 과 목</p></td>";
        	strHtml += "					<td width=\"177\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 120%;    font-size: 10pt;    font-family: 굴림;\">금       액</p></td>";
        	strHtml += "				</tr>";
        	// 위에서 얻은 본문내용 갯수만큼 셋팅해주기
        	// 본문내용중 변경신청내역 얻기
        	
        	for(int k=0; k < 5; k++ ) {
        		
        		if(rtnDs2.getString(k,"acntCdMokNm") == "" || rtnDs2.getString(k,"acntCdMokNm") == null ){
        			dr_mok_nm 	= " ";
            	}else {
            		dr_mok_nm 	= rtnDs2.getString(k,"acntCdMokNm");
            	}
            	if(rtnDs2.getString(k,"acntCdSemokNm") == "" || rtnDs2.getString(k,"acntCdSemokNm") == null || rtnDs2.getString(k,"acntCdSemokNm").equals(rtnDs2.getString(k,"acntCdMokNm"))){
            		dr_acnt_nm 	= " ";
            	}else {
            		dr_acnt_nm 	= rtnDs2.getString(k,"acntCdSemokNm"); 
            	}
            	if(rtnDs2.getString(k,"slipAmt") == "" || rtnDs2.getString(k,"slipAmt") == null ){
            		dr_amt 	= " ";
            	}else {
            		dr_amt 	= rtnDs2.getString(k,"slipAmt"); 
            	}
            	if(rtnDs2.getString(0,"slipAmtTot") == "" || rtnDs2.getString(0,"slipAmtTot") == null ){
            		dr_amt_tot 	= " ";
            	}else {
            		dr_amt_tot 	= rtnDs2.getString(0,"slipAmtTot"); 
            	}
            	
            	if(rtnDs3.getString(k,"acntCdMokNm") == "" || rtnDs3.getString(k,"acntCdMokNm") == null ){
            		cr_mok_nm 	= " ";
            	}else {
            		cr_mok_nm 	= rtnDs3.getString(k,"acntCdMokNm");
            	}
            	if(rtnDs3.getString(k,"acntCdSemokNm") == "" || rtnDs3.getString(k,"acntCdSemokNm") == null || rtnDs3.getString(k,"acntCdSemokNm").equals(rtnDs3.getString(k,"acntCdMokNm"))){
            		cr_acnt_nm 	= " ";
            	}else {
            		cr_acnt_nm 	= rtnDs3.getString(k,"acntCdSemokNm"); 
            	}
            	if(rtnDs3.getString(k,"slipAmt") == "" || rtnDs3.getString(k,"slipAmt") == null ){
            		cr_amt 	= " ";
            	}else {
            		cr_amt 	= rtnDs3.getString(k,"slipAmt"); 
            	}
            	if(rtnDs3.getString(0,"slipAmtTot") == "" || rtnDs3.getString(0,"slipAmtTot") == null ){
            		cr_amt_tot 	= " ";
            	}else {
            		cr_amt_tot 	= rtnDs3.getString(0,"slipAmtTot"); 
            	}
  	            
	        	strHtml += "			<tr>";
	        	strHtml += "				<td height=\"30\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+dr_mok_nm+"<br>"+dr_acnt_nm+"</p></td>";
	        	strHtml += "				<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+dr_amt+"</p></td>";
	        	strHtml += "				<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+cr_mok_nm+"<br>"+cr_acnt_nm+"</p></td>";
	        	strHtml += "				<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+cr_amt+"</p></td>";
	        	strHtml += "			</tr>";
        	}
        	strHtml += "				<tr>";
        	strHtml += "					<td height=\"30\"><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림; \">합    계</p></td>";
        	strHtml += "					<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+dr_amt_tot+"</p></td>";
        	strHtml += "					<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: center;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">합    계</p></td>";
        	strHtml += "					<td><p style=\"margin-top: 0.0pt;    margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림;\">"+cr_amt_tot+"</p></td>";
        	strHtml += "				</tr>";
        	strHtml += "				<tr>";
        	strHtml += "					<td height=\"30\" colspan=\"4\" style=\"border-right: 0px none; border-left: 0px none; border-bottom: 0px none;\"><p style=\"margin-top: 0.0pt; margin-bottom: 0.0pt;    text-align: right;    text-indent: 0.0pt;    line-height: 140%;    font-size: 8pt;    font-family: 굴림; \">"+rtnDs1.getString(0,"approvalDocno2")+"</p></td>";
        	strHtml += "				</tr>";
        	strHtml += "			</table>";
        	//strHtml += "		</td>";
        	//strHtml += "	</tr>";
        	//strHtml += "	</table>";
        	strHtml += "	</body>";
        	strHtml += "</html>";

            List<?> list4 = commonDAO.selectData("admiDAO.selectGwApprovalMstElecId", nMap4);

        	DataSet rtnDs4 = ConvertUtil.getListToDS(list4);
            
            nMap.put("id", rtnDs4.getString(0,"elecId"));
            nMap.put("formId",   map.get("formId"));
            nMap.put("title",    map.get("title"));
            nMap.put("subTitle", map.get("subTitle"));
            nMap.put("subjId",   map.get("subjId"));
            
            nMap.put("slipNo",   nMap1.get("slipNo"));
            nMap.put("vouNo",   nMap1.get("vouNo"));
            nMap.put("bizNm",   rtnDs1.getString(0,"bizCdNm"));
            nMap.put("vouNoElec",   nMap1.get("vouNoElec"));
            
            nMap.put("body", strHtml);
            nMap.put("approvalDiv", strApprovalDiv);
            nMap.put("approvalDt", strApprovalDt);
            nMap.put("approvalDocid", strApprovalDocid);
            nMap.put("userId", map.get("userId"));
            
            System.out.println(nMap);
            System.out.println("_________________________"); 
            
            nMap3.put("id", rtnDs4.getString(0,"elecId"));
            nMap3.put("formId",   map.get("formId"));
            nMap3.put("approvalDiv", strApprovalDiv);
            nMap3.put("approvalDt", strApprovalDt);
            nMap3.put("approvalUserid", map.get("userId"));
            
            nMap3.put("acntUnt", nMap1.get("acntUnt"));
            nMap3.put("acntYy", nMap1.get("acntYy"));
            nMap3.put("slipNo", nMap1.get("slipNo"));
            nMap3.put("aplyDt", nMap1.get("aplyDt"));
            nMap3.put("aplyUserId", nMap1.get("aplyUserId"));
            
            nMap3.put("procCd", nMap1.get("procCd"));
            nMap3.put("userId", nMap1.get("userId"));
            nMap3.put("userIp", nMap1.get("userIp"));
            nMap3.put("userPgm", nMap1.get("userPgm"));
            nMap3.put("cfmDt", nMap1.get("cfmDt"));
            
            //mssql 전자결재연동테이블에 인서트한다.
            msDAO.insert("admiDAO.insertGwApprovalMst", nMap);
            
            //oracle 전자결재연동테이블에 인서트한다.
           // commonDAO.insert("admiDAO.insertGwApprovalMstOra", nMap);
            System.out.println("****************************************");
            System.out.println(nMap);
            System.out.println(nMap1);
            System.out.println(nMap2);
            System.out.println(nMap3);
            System.out.println(nMap4);
            System.out.println("****************************************");
            //oracle 결의서접수테이블(ADMI.AC_SLIP)에 업데이트한다.
            commonDAO.update("ac02DAO.updateAc02020p01_01AplyGwApprovalMstOra_puch", nMap3);
            
            //commonDAO.saveDataSP("ac02DAO.P_AC_STA_CD_PROC_PUCH", nMap3);
        } 
        return nMap;
    }
    
    
    @Override
    public void updateAc02020_01_VendorDesc(DataSet ds) throws Exception{
  	  String rowType = "";
  	  
  	  for(int i = 0; i < ds.getRowCount(); i++) {
  		  
  		  rowType = ds.getString(i, "rowStatus");
  		  HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
  		  
  		  if("update".equals(rowType)){
  			  commonDAO.update("ac02DAO.updateSdVendorDesc", hm);
  		  }
  	  }
    }
    
    
    /*#########################################################################################################################################*/
    /* 전표확정드옥 (admi/ac/ac02/ac02030.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 *  
	 */
    public List<?> selectAc02030_01(Map map) throws Exception{
    	commonDAO.saveDataSP("ac02DAO.saveSpDeuOraMssqlSlipChk", map);
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02030_01", map);
        return list;
    }    
    
    
    /**
	 * 분개내역(차변)
	 */
    public List<?> selectAc02030_t01_01(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02030_t01_01", map);
        return list;
    }
    
    /*
     * 분개내역(차변) 저장
     */
    @Override
    public void saveAc02030_t01_01(DataSet ds) throws Exception{
  	  String rowType = "";
  	  
  	  for(int i = 0; i < ds.getRowCount(); i++) {
  		  
  		  rowType = ds.getString(i, "rowStatus");
  		  HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
  		  
  		  if("insert".equals(rowType)){
  			  commonDAO.insert("ac02DAO.insertAc02030_t01_01", hm);
  		  }else if("update".equals(rowType)){
  			  commonDAO.update("ac02DAO.updateAc02030_t01_01", hm);
  		  }else if("delete".equals(rowType)){
  			  commonDAO.delete("ac02DAO.deleteAc02030_t01_01", hm);
  		  }
  	  }
    }
    
    
    /**
	 *  분개내역(대변)
	 */
    public List<?> selectAc02030_t01_02(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02030_t01_02", map);
        return list;
    }
    
    /*
     * 분개내역(차변) 저장
     */
    @Override
    public void saveAc02030_t01_02(DataSet ds) throws Exception{
  	  String rowType = "";
  	  
  	  for(int i = 0; i < ds.getRowCount(); i++) {
  		  
  		  rowType = ds.getString(i, "rowStatus");
  		  HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
  		  
  		  if("insert".equals(rowType)){
  			  commonDAO.insert("ac02DAO.insertAc02030_t01_02", hm);
  		  }else if("update".equals(rowType)){
  			  commonDAO.update("ac02DAO.updateAc02030_t01_02", hm);
  		  }else if("delete".equals(rowType)){
  			  commonDAO.delete("ac02DAO.deleteAc02030_t01_02", hm);
  		  }
  	  }
    }
    
        
    /**
	 *  
	 */
    public List<?> selectAc02030_t02_01(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02030_t02_01", map);
        return list;
    }
    
    
    /**
	 *  
	 */
    public List<?> selectAc02030_t03_01(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02030_t03_01", map);
        return list;
    }
    
    
    /**
	 *  
	 */
    public List<?> selectAc02030_t04_01(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02030_t04_01", map);
        return list;
    }
    
    
    /**
	 *  
	 */
    public List<?> selectAc02030_t04_02(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02030_t04_02", map);
        return list;
    }
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 계정대체 계정내역 (admi/ac/ac02/ac02010_p01_t03.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 *  
	 */
    public List<?> selectAc02010p01_t03_01(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02010p01_t03_01", map);
        return list;
    }
    
    
    
    
    /*#########################################################################################################################################*/
    /* 자금대체 계정내역 (admi/ac/ac02/ac02010_p01_t04.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 *  
	 */
    public List<?> selectAc02010p01_t04_01(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02010p01_t04_01", map);
        return list;
    }
    
    @Override
    public void saveAc02010p01_t04_01(DataSet ds) throws Exception{
        String insertQueryId="ac02DAO.insertAc02010p01_t04_01";
        String updateQueryId="ac02DAO.updateAc02010p01_t04_01";
        String deleteQueryId="ac02DAO.deleteAc02010p01_t04_01";
        commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
    }
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 결산대체 계정내역 (admi/ac/ac02/ac02010_p01_t05.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 *  
	 */
    public List<?> selectAc02010p01_t05_01(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02010p01_t05_01", map);
        return list;
    }
    
    @Override
    public void saveAc02010p01_t05_01(DataSet ds) throws Exception{
        String insertQueryId="ac02DAO.insertAc02010p01_t05_01";
        String updateQueryId="ac02DAO.updateAc02010p01_t05_01";
        String deleteQueryId="ac02DAO.deleteAc02010p01_t05_01";
        commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
    }
    
    @Override
    public void saveSlipAccEmpId(DataSet ds) throws Exception{
        String insertQueryId="ac02DAO.updateSlipAccEmpId";
        String updateQueryId="ac02DAO.updateSlipAccEmpId";
        String deleteQueryId="ac02DAO.updateSlipAccEmpId";
        commonDAO.saveData(insertQueryId, updateQueryId, deleteQueryId, ds);
    }
    
    
    /*#########################################################################################################################################*/
    /* 은행이관(admi/ac/ac02/ac02040.xfdl)                                                                                                      */
    /*#########################################################################################################################################*/
    /**
	 * 은행목록 조회
	 */
    public List<?> selectAc02040_01(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02040_01", map);
        return list;
    }

    /**
	 * 지급목록 조회
	 */
    public List<?> selectAc02040_02(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02040_02", map);
        return list;
    }
    
    /**
	 * 이관/이관취소 처리 ( mssql생성전용 )
	 */
    @Override
    public HashMap P_AC_BANK_PROC(DataSet ds, Map param) throws Exception{

		HashMap rtnMap = new HashMap();
		
		/*[1] 은행이관처리 전 dummy테이블을 delete한다.*/
    	commonDAO.delete("ac02DAO.oracle_delete_if_matrn_dummy");

    	/*[2] 은행이관처리 데이터를 dummy테이블에 insert한다.*/
        commonDAO.saveData("ac02DAO.oracle_pre_dummy_P_AC_BANK_PROC", "", "", ds);
        System.out.println(ds);
        
		/*[4] 은행이관 프로시저를 호출한다.*/
		rtnMap = commonDAO.saveDataSP("ac02DAO.P_AC_BANK_PROC", param);
			
        return rtnMap;
        
    }
    
    /**
	 * 이관/이관취소 처리 ( oracle생성전용 )
	 */
    @Override
    public HashMap P_AC_BANK_PROC_POST(DataSet ds, Map param) throws Exception{

		HashMap rtnMap = new HashMap();
		
		/*[1] 은행이관 프로시저를 호출한다.*/
		rtnMap = commonDAO.saveDataSP("ac02DAO.P_AC_BANK_PROC_POST", param);
    	
        return rtnMap;
        
    }
    
    
    
    
    
    
    
    
    
    /*#########################################################################################################################################*/
    /* 대체결의서접수 (admi/ac/ac02/ac02050.xfdl)                                                                                            */
    /*#########################################################################################################################################*/
    /**
	 *  
	 */
    public List<?> selectAc02050_01(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02050_01", map);
        return list;
    }
    
    /**
	 *  계정대체 상세
	 */
    public List<?> selectAc02050_02_01(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02050_02_01", map);
        return list;
    }
    
    /**
	 *  자금대체 상세
	 */
    public List<?> selectAc02050_02_02(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02050_02_02", map);
        return list;
    }
    
    /**
	 *  결산대체 상세
	 */
    public List<?> selectAc02050_02_03(Map map) throws Exception{
        List<?> list = commonDAO.selectData("ac02DAO.selectAc02050_02_03", map);
        return list;
    }
    
	public List<?> selectAc02060(Map map) throws Exception{
		List<?> list = commonDAO.selectData("ac02DAO.selectAc02060", map);
        return list;
	}
	
    @Override
    public void saveAc02060(DataSet ds) throws Exception{
    	
    	HashMap nMap = null;
        for (int i = 0; i < ds.getRowCount(); i++) {

           // rowType = ds.getString(i, "rowStatus");
            HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, i);
            
                Map map = new HashMap();
                
                // TODO ENTRY_NO, ENTRY_IP 넣기
                nMap = commonDAO.saveDataSP("ac02DAO.P_AC_PRE_EXP_CFM_PROC", hm);
        }
    }
    
	@Override
	public HashMap saveCfmAc02020(DataSet ds) throws Exception {
		
		HashMap<String, Object> hm = ConvertUtil.getDStoMap(ds, 0);
		
		hm = commonDAO.saveDataSP("ac02DAO.P_AC_CFM_VOU_PROC", hm);

		return hm;
	}
}