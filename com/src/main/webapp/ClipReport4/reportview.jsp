<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="./css/clipreport.css">
<script type='text/javascript' src='./js/clipreport.js'></script>
<script type='text/javascript' src='./js/UserConfig.js'></script>
<script type='text/javascript' src='./js/jquery-1.11.1.js'></script>
<script type="text/javascript" src="WebIF.js"></script>
<script type='text/javascript'>

/**********************************************************
 * 클립리포트를 최종적으로 호출하는화면 jsp
 * ********************************************************/

var xpCallBackObj;  //WebBrowser 로부터 결과를 전달받을 오브젝트 (XPLATFORM Form)
var xpCallBackFn;

function clipCall(divPath){
	
		var report = "";
		
		var urlPath = document.location.protocol + "//" + document.location.host;
		
		var callType   = document.getElementById("callType").value;  // 파라미터 던지는타입과 데이타셋 던지는 타입을 구별하기위한 변수값(사용안함)
	 	var crfName   = document.getElementById("crfName").value;    // 레포트 명칭
	 	var crfParams = document.getElementById("crfParams").value;  // 파라미터 
	 	var crfDbName = document.getElementById("crfDbName").value;  // 데이터셋명칭 oracle1 하드코딩되어있음 (사용안함) 
	 	var crfFormYn = document.getElementById("crfFormYn").value;  // 레포트 호출되는 위치 확인용 폼Y/팝업N 하드코딩
	 	var crfRole = document.getElementById("crfRole").value;      // 출력,다운로드 권한 추후 세션값생기면 변경필요
		 	//alert(callType+"<==>"+crfName+"<==>"+crfParams+"<==>"+crfDbName+"<==>"+crfFormYn+"<==>"+crfRole);
		 	
		 	// 현재 페이지가 jsp인 상태로  createJSPReport 함수이용해 레포트를 만든다
	 		report = createJSPReport(urlPath + "/com/ClipReport4/Clip.jsp", urlPath + "/com/ClipReport4/reportCreatePARAM.jsp",   "crfName=" + crfName + "&crfParams=" + encodeURIComponent(crfParams) +  "&crfDbName=" + crfDbName ,document.getElementById(divPath));	
		 	
		    // 레포트 크기 초기에 꽉차게 셋팅
		 	report.setDefaultRatio("PageWidth"); // 리포트화면 꽉차게
		    
		    //엑셀 다운로드 기능 초기셋팅
		 	report.setDefaultSaveExcelOption(3, true, false, true, false, 100, 100, 10, 0, 100); 
		    report.setSaveDirectExcelOption("report",3, true, false, true, false, 100, 100, 10, 0, 100);
			// 엑셀 다운로드 설정           ↑
			// 1 : 페이지마다 하나의 시트
			// 2 : 페이지를 연속저장
			// 3: 섹션을 연속하여 저장
			
		     
		    window.NEXACROHTML.Init();
		    
		    //레포트 뷰어 닫기버튼 누를때 레포트 팝업창도 같이 닫을수있게 셋팅
		    report.setCloseReportEvent(function (){
		    	window.NEXACROHTML.FireUserNotify("close");
		    	//document.title = "close";
		    });
		    //폼에서 호출될땐 레포트뷰어 닫기버튼 비활성화
		    if(crfFormYn == "Y"){
				report.setDisabled("close_button", true);//닫기버튼 비활성화	
			}
			
		    // 출력,다운로드 권한에 따라 버튼제어
			if(crfRole == "A"){ //다운로드권한이 없다
				report.setDisabled("save_button", true);//파일저장버튼
			    report.setDisabled("excel_button", true);//excel파일저장버튼 
			    report.setDisabled("hwp_button", true);//hwp파일저장버튼 
			    report.setDisabled("pdf_button", true);//pdf파일저장버튼 
			}else if(crfRole == "B"){ // 출력권한이 없다
		    	report.setDisabled("print_button", true);//파일저장버튼 
			}else{ // 권한이 둘다없다. 임시로 열어주자
				/*report.setDisabled("save_button", false);//파일저장버튼
			    report.setDisabled("excel_button", false);//excel파일저장버튼 
			    report.setDisabled("hwp_button", false);//hwp파일저장버튼 
			    report.setDisabled("pdf_button", false);//pdf파일저장버튼 
			    report.setDisabled("print_button", false);//파일저장버튼 */
			}
			//report.setStyle("close_button","visibility:hidden"); //visibility:hidden/visible
		   //	report.setDirectPrint(true,"targetDiv1");
			
			
		    //레포트뷰어에서 출력할때 어도비 다이얼로그 화면으로 바로 갈수있도록 수정
			report.setStartPrintButtonEvent(function(){ 
				report.printView();
				//!중요: 선언한 함수안에 return 값이 true | false 따라 뷰어의 기본동작이 활성화 됩니다.
				return false;
			});
			
		    //레포트 출력  사전작업은 report.view(); 전에 이루어져야한다
		    report.view();
	    
	
}

</script>
</head>
<body>
<input type="hidden"   id="callType"     value="callType" />
<input type="hidden"   id="crfName"     value="crfName" />
<input type="hidden"   id="crfParams"   value="crfParams" />
<input type="hidden"   id="crfDbName"   value="crfDbName" />
<input type="hidden"   id="crfFormYn"   value="crfFormYn" />
<input type="hidden"   id="crfRole"   value="crfRole" />
<input type="hidden" id="btnClick" onclick="clipCall('targetDiv1')">
<div id='targetDiv1' style='position:absolute;left:0px;top:0px;height:100%;width:100%' ></div>
</body>
</html>
