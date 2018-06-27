/**********************************************************
 * 웹브라우져에 올라가있는 jsp/html 화면에서 리턴받기위한 js
 * WebBrowser00_onusernotify 이벤트에 함께 사용
 * 호출순서
 * 1. window.NEXACROHTML.Init 
 * 2. window.NEXACROHTML.FireUserNotify
 * jsp/html title에 데이터를 담아서 리턴해줌(최대256byte)
 * ex)window.NEXACROHTML.FireUserNotify("값"); ====> 받는화면 e.userdata
 * ********************************************************/

if (! window.NEXACROHTML) {
	  window.NEXACROHTML = {};
}

var parent = null;
var bInit = false;
window.NEXACROHTML.Init = function() {


	  var loopCnt = 0;
	  var interval = setInterval(fn = function() {
	      parent = window.NEXACROWEBBROWSER;
        if(loopCnt >= 10 || parent) {
            bInit = true;
    	      clearInterval(interval);
//console.log("Parent:" + loopCnt + ":" + parent);
	      }

		    loopCnt++;
  	}, 50);
}

window.NEXACROHTML.FireUserNotify = function(userdata) {
//console.log("Fire:" + bInit + ":" + parent);
    if(bInit == false) {
        var loopCnt = 0;
        var interval = setInterval(fn = function() {		
		        if (loopCnt > 10 || bInit) {
			          clearInterval(interval);
		        }
  		      loopCnt++;
	      }, 50);
  	}
	  if (parent) {
	      parent.on_fire_onusernotify(parent, userdata);
  	} else {
	  	  window.document.title = userdata;
	  }			
}