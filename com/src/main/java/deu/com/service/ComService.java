package deu.com.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import deu.com.vo.FileUpVO;

import com.nexacro.xapi.data.DataSet;
import com.nexacro.xapi.data.DataSetList;

public interface ComService {

	/**
	 * 공통코드 을(를) 조회한다.
	 * @throws Exception 
	 */
	public abstract List<?> selectComCd(Map map) throws Exception;
	
	/**
	 * 공통코드 을(를) 저장한다.
	 * @throws Exception 
	 */
	public abstract void saveComCd(DataSet ds) throws Exception;
	
	/**
	 * 공통코드 을(를) 조회한다.
	 * @throws Exception 
	 */
	public abstract List<?> selectComCdSub(Map map) throws Exception;
	
	/**
	 * 공통코드 을(를) 저장한다.
	 * @throws Exception 
	 */
	public abstract void saveComCdSub(DataSet ds) throws Exception;
			
	/**
	 * 공통코드 을(를) 조회한다.
	 * @throws Exception 
	 */
	public abstract List<?> selectComCdCombo(Map map) throws Exception;
		
	/**
	 * 공통코드 을(를) 조회한다.
	 * @throws Exception 
	 */
	public abstract List<?> selectComCdComboOrder(Map map) throws Exception;
	
	/**
	 * 주소 을(를) 조회한다.
	 * @throws Exception 
	 */
	public abstract List<?> selectZipPopUp(Map map) throws Exception;
	
	/**
	 * 시스템 을(를) 조회한다.
	 * ds_main
	 * @throws Exception 
	 */
	public abstract List<?> selectComCsi07(Map map) throws Exception;
	
	/**
	 * 시스템 을(를) 저장한다.
	 * @throws Exception 
	 */
	public abstract void saveComCsi07(DataSet ds) throws Exception;
	
	/**
	 * 서브시스템 을(를) 조회한다.
	 * ds_sub
	 * @throws Exception 
	 */
	public abstract List<?> selectComCsi07Sub(Map map) throws Exception;
	
	/**
	 * 서브시스템 을(를) 저장한다.
	 * @throws Exception 
	 */
	public abstract void saveComCsi07Sub(DataSet ds) throws Exception;
	
	/**
	 * 메뉴 을(를) 조회한다.
	 * ds_main
	 * @throws Exception 
	 */
	public abstract List<?> selectComCsi08(Map map) throws Exception;
	
	/**
	 * 메뉴 을(를) 저장한다.
	 * @throws Exception 
	 */
	public abstract void saveComCsi08(DataSet ds) throws Exception;
	
	/**
	 * 프로그램 을(를) 조회한다.
	 * ds_main
	 * @throws Exception 
	 */
	public abstract List<?> selectComCsi08Sub(Map map) throws Exception;
	
	/**
	 * 프로그램 을(를) 저장한다.
	 * @throws Exception 
	 */
	public abstract void saveComCsi08Sub(DataSet ds) throws Exception;
	
	/**
	 * 권한등록 을(를) 조회한다.
	 * ds_roleGroup
	 * @throws Exception 
	 */
	public abstract List<?> selectRoleGroup(Map map) throws Exception;
	
	/**
	 * 권한콤보 을(를) 조회한다.
	 * ds_roleGroup
	 * @throws Exception 
	 */
	public abstract List<?> selectRoleGroupCmb(Map map) throws Exception;
	
	/**
	 * 권한등록 을(를) 저장한다.
	 * @throws Exception 
	 */
	public abstract void saveRoleGroup(DataSet ds) throws Exception;
	
	/**
	 * 권한등록(유저) 을(를) 조회한다.
	 * ds_roleGroupUser
	 * @throws Exception 
	 */
	public abstract List<?> selectRoleGroupUser(Map map) throws Exception;
	
	/**
	 * 권한등록(프로그램) 을(를) 조회한다.
	 * ds_roleGroupPgm
	 * @throws Exception 
	 */
	public abstract List<?> selectRoleGroupPgm(Map map) throws Exception;

	/**
	 * 권한별 프로그램 을(를) 조회한다.
	 * ds_main
	 * @throws Exception 
	 */
	public abstract List<?> selectRolePgm(Map map) throws Exception;
	
	/**
	 * 권한별 프로그램 트리 을(를) 조회한다.
	 * ds_main
	 * @throws Exception 
	 */
	public abstract List<?> selectRolePgmTree(Map map) throws Exception;
	
	/**
	 * 권한별 프로그램 을(를) 저장한다.
	 * @throws Exception 
	 */
	public abstract void saveRolePgm(DataSet ds) throws Exception;
	
	/**
	 * 권한별 프로그램 을(를) 저장한다. 등록
	 * @throws Exception 
	 */
	public abstract void saveRolePgmRegi(DataSet ds) throws Exception;
	
	/**
	 * 권한별 프로그램 을(를) 저장한다. 등록해제
	 * @throws Exception 
	 */
	public abstract void saveRolePgmNonRegi(DataSet ds) throws Exception;
	
	/**
	 * 권한별 프로그램 상세 을(를) 조회한다.
	 * ds_main
	 * @throws Exception 
	 */
	public abstract List<?> selectRolePgmDet(Map map) throws Exception;
	
	/**
	 * 권한별 프로그램 상세 트리을(를) 조회한다.
	 * ds_main
	 * @throws Exception 
	 */
	public abstract List<?> selectRolePgmDetTree(Map map) throws Exception;
	
	/**
	 * 사용자별 권한관리 (유저) 을(를) 조회한다.
	 * ds_user
	 * @throws Exception 
	 */
	public abstract List<?> selectUserPgm(Map map) throws Exception;
	
	/**
	 * 사용자별 권한관리 을(를) 저장한다. 등록
	 * @throws Exception 
	 */
	public abstract void saveUserPgmInst(DataSet ds) throws Exception;
	
	/**
	 * 사용자별 권한관리 을(를) 저장한다. 등록해제
	 * @throws Exception 
	 */
	public abstract void saveUserPgmDel(DataSet ds) throws Exception;
	
	/**
	 * 사용자별 권한관리 (미등록) 을(를) 조회한다.
	 * ds_nonRegi
	 * @throws Exception 
	 */
	public abstract List<?> selectNonRegiPgm(Map map) throws Exception;
	
	/**
	 * 사용자별 권한관리 (등록) 을(를) 조회한다.
	 * ds_regi
	 * @throws Exception 
	 */
	public abstract List<?> selectRegiPgm(Map map) throws Exception;
	
	/**
	 * 신분별 권한관리 (등록) 을(를) 조회한다.
	 * ds_regi
	 * @throws Exception 
	 */
	public abstract List<?> selectRankRegiPgm(Map map) throws Exception;
	
	/**
	 * 신분별 권한관리 (미등록) 을(를) 조회한다.
	 * ds_unRegi
	 * @throws Exception 
	 */
	public abstract List<?> selectUnrankRegiPgm(Map map) throws Exception;

	/**
	 * 사용자분류 재조회을(를) 조회한다.
	 * ds_unRegi
	 * @throws Exception 
	 */
	public abstract List<?> selectUserGb(Map map) throws Exception;
	
	/**
	 * 신분별 권한관리 (등록) 을(를) 저장한다. 등록
	 * @throws Exception 
	 */
	public abstract void saveRankRegiPgm(DataSet ds) throws Exception;
	
	/**
	 * 신분별 권한관리 (미등록) 을(를) 저장한다. 등록해제
	 * @throws Exception 
	 */
	public abstract void saveUnrankRegiPgm(DataSet ds) throws Exception;
	
	
	/**
	 * SMS 문자를 전송한다. (KT 크로샷? 서버에 인서트한다.)
	 * @throws Exception 
	 */
	public abstract void saveSmsSend(DataSet ds) throws Exception;
	
	/**
	 * DB 서버 시간을(를) 조회한다.
	 * gds_currentDate
	 * @throws Exception 
	 */
	public abstract List<?> selectCurrentDate(Map map) throws Exception;
	
	/**
	 * 사용자그룹을(를) 조회한다.
	 * ds_main
	 * @throws Exception 
	 */
	public abstract List<?> selectUserGroup(Map map) throws Exception;
	
	/**
	 * 파일마스터넘버(를) 조회한다.
	 * ds_main
	 * @throws Exception 
	 */
	public abstract FileUpVO selectComFileMasterNo() throws Exception;
	
	/**
	 * 파일업로드 정보을(를) 조회한다.
	 * ds_main
	 * @throws Exception 
	 */
	public abstract List<?> selectComFile(Map map) throws Exception;
	
	/**
	 * 파일업로드 정보을(를) 저장한다. 
	 * @throws Exception 
	 */
	public abstract void saveComFile(DataSet ds) throws Exception;
	
	/**
	 * 파일업로드상세 정보을(를) 조회한다.
	 * ds_main
	 * @throws Exception 
	 */
	public abstract List<?> selectComFileSub(Map map) throws Exception;
	
	/**
	 * 파일업로드상세 정보을(를) 저장한다. 
	 * @throws Exception 
	 */
	public abstract void saveComFileSub(DataSet ds) throws Exception;
    	
	/**
     * SMS를 전송한다.
     * @throws Exception 
     */
    public abstract void sendSms(DataSet ds) throws Exception;
    
	/**
     * SMS를 전송한다.
     * @throws Exception 
     */
    public abstract void sendMessage(DataSet ds) throws Exception;
}
