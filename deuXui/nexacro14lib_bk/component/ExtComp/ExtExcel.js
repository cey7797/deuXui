/**
 * @fileoverview excel export/import 처리용.
 * poi 사용. 
 */
if(!nexacro.ExtExcelExport) {
	    //========================================================================
	    // nexacro.ExtExcelExportEventInfo
	    //========================================================================
	    nexacro.ExtExcelExportEventInfo = function (obj, id, url, errorobj)
	    {
	        this.id = this.eventid = id || "onsuccess";
	        this.fromobject = obj;
	        this.fromreferenceobject = errorobj;

	        this.url = url;
	    };
	    
	    var _pExtExcelExportEventInfo = nexacro._createPrototype(nexacro.Event, nexacro.ExtExcelExportEventInfo);
	    nexacro.ExtExcelExportEventInfo.prototype = _pExtExcelExportEventInfo;
	    _pExtExcelExportEventInfo._type_name = "ExcelExportEventInfo";

	    delete _pExtExcelExportEventInfo;	
		
		
		nexacro.ExtExcelExport = function(name, scope) {
			if(Eco.isEmpty(name)) {
				alert("[ExtExcelExport] name is empty.");
				return;
			}
			
			if(typeof(name)!= 'string') {
				alert("[ExtExcelExport] name argument is not a string.");
				return;
			}
			
			if(Eco.isEmpty(scope)) {
				alert("[ExtExcelExport] scope is empty.");
				return;
			}
			
			
			this.id = name;
			this.scope = scope;
			this.exportUrl = "";
			this.arg = "";
			this.sendDataType = 0; //0:XML, 1:Binary(RUNTIME Only), 2:SSV
			
			
			this.debug = false;
			this.workbook = "";
			this.workbookDatasetName = "_workbookDs";
			this.exportIFrameName = "_exportIFrame";
			this.exportFormName = "exportForm";

			this.inputDatasets = [];
			
			
			//event
			this._onsuccessCallback = "";
			this._onerrorCallback = "";
			this._onprogressCallback = "";
			
			//그리드 cell,band에 정의되어 있지 않을 경우 사용
			this.defaultHorizontalAlign = "left"; 
			this.defaultVerticalAlign   = "middle";
			this.defaultColor = "white";
			this.defaultCellLineColor = "#808080";
			this.defaultBorderStyle = "solid";
			this.defaultGridBackground = "white";
			this.transparentReplacedColor = "white";
			
			this.exportCellPropertyData = [];  //cell 속성값의 재사용을 목적으로 관리되는 객체배열
			this.exportCellPropertyIndex = {};  //exportCellPropertyData의 중복을 막기위해 key를 관리하는 객체
			
			this.cellSeqId = 0;
			this.headSeqId = 0;
			this.bodySeqId = 0;
			this.summSeqId = 0;
			
			
			//Expr이 적용되어 표시되는 text를 전달하기위한 dataset을 
			// pool로 관리하기 위한 form변수
			if(!scope["_exprDatasetPool"]) {
				scope["_exprDatasetPool"] = [];
			}
			
			this.exprDatasetPool = scope["_exprDatasetPool"]; 

			//Expr이 적용되는 각 cell의 속성값을 전달하기위한 dataset을 
			// pool로 관리하기 위한 form변수
			if(!scope["_exprPropertyPool"]) {
				scope["_exprPropertyPool"] = [];
			}			
			
			this.exprPropertyPool = scope["_exprPropertyPool"]; 

			//필터링된 데이터만 서버로 전송하기 위한 dataset을 
			// pool로 관리하기 위한 form변수
			if(!scope["_filterDatasetPool"]) {
				scope["_filterDatasetPool"] = [];
			}				
			
			this.filterDatasetPool = scope["_filterDatasetPool"];
			
	        this._event_list = { "onsuccess": 1};        

		};
		
		
		
		var _pExtExcelExport = nexacro.ExtExcelExport.prototype;
			
		//debuggin용 data 추가
		_pExtExcelExport.addDebuggingData = function (str) {
			this.debuggingData.push(str);
			
		};
		
		//export용 Iframe 내부 script
		_pExtExcelExport.iframeSource =   ""
			+ "<html>\n"
			+ "<head>\n"
			+ "<meta http-equiv='content-Type' content='text/html; charset=utf-8' />\n"
			+ "</head>\n"
			+ "<body bgcolor='white' leftmargin='0' topmargin='0' >\n"
			+ "     <form id='exportForm' action='\' method='post' >\n"
			+ "     <input type='text'  id='workbook' name='workbook'></input><br>\n"
			+ "     <input type='text'  id='datasetList' name='datasetList'></input><br>\n"
			+ "     <input type='text'  id='xmlData' name='xmlData'></input><br>\n"
			+ "     </form>\n"
			+ "</body>\n"
			+ "</html>";		

			
		
		
		
		if (nexacro.Browser == "Runtime") {
			//export용 WebBrowser 반환
			_pExtExcelExport.getExportIFrame = function () {
				var scope = this.scope;
				var frameName = this.exportIFrameName;
				var webBrowser = scope[frameName];
				
				if(!webBrowser) {
					webBrowser = this._createFrame(frameName);
					
				} else {
					//network 단절을 대비하기 위해 frame을 항상 새로 만들어 줘야한다.
					//this._removeFrame(frameName);
					//webBrowser = this._createFrame(frameName);		
					webBrowser.setEventHandler("onerror", this._makeWebBrowser_onError(this));
					
					trace("\n\n\n \t\t removeEventHandler=" + scope.removeEventHandler("ontimer", scope._prevTimerHandler, scope));
					
					scope._prevTimerHandler = "";
					var timerHandler = this._makeTimerHandler(this);
					scope._prevTimerHandler = timerHandler;
					trace("신규 addEventHandler=" + scope.addEventHandler("ontimer", timerHandler, scope));



					
					this.setIFrameContents(webBrowser, this.iframeSource)
				}
			
				//WebBrowser 동적생성 후 로딩되는 여유를 두기위한 timer
				scope.setTimer(110, 300);
				
				return webBrowser;				

			};
			
			
			//export용 WebBrowser 제거
			_pExtExcelExport._removeFrame = function (frameName) {
				var webBrowser = this.scope.removeChild(frameName); 
			 	webBrowser.clearEventHandler("onerror");
			 	//webBrowser.clearEventHandler("onloadcompleted");				
				
				// Destroy Object  
				webBrowser.destroy();
			};
			
			//export용 WebBrowser 생성
			_pExtExcelExport._createFrame = function (frameName) {
				var webBrowser = new WebBrowser(frameName, 'absolute', 200, 200,400, 400);
				var scope = this.scope;
			 	scope.addChild(frameName, webBrowser); 
			 	
				if(this.debug) {
					webBrowser.set_visible(true);
				} else {
					webBrowser.set_visible(false);
				}
				
			 	webBrowser.addEventHandler("onerror", this._makeWebBrowser_onError(this));
			 	//webBrowser.addEventHandler("onloadcompleted", this._makeWebBrowser_onloadcompleted(this));
				
				webBrowser.show();
				
				trace(" ### _createFrame");
				
				var timerHandler = this._makeTimerHandler(this);
				scope["_prevTimerHandler"] = timerHandler;
				scope.addEventHandler("ontimer", timerHandler, scope);
				
				
		        return webBrowser;
			};
			
			//export용 Iframe Contents 설정
			_pExtExcelExport.setIFrameContents = function (webBrowser, contents) {
				var doc = webBrowser.getProperty("document");
				var body = webBrowser.getProperty("document").getProperty("body");
				
				body.setProperty( "innerHTML", contents );
			};				
			
		} else {
			//HTML5용 
			//export용 Iframe 반환
			_pExtExcelExport.getExportIFrame = function () {
				var frameName = this.exportIFrameName;
				var iframe = document.getElementById(frameName);
				if(!iframe){
					this._createFrame(frameName);
				} else {
					//network 단절을 대비하기 위해 frame을 항상 새로 만들어 줘야한다.
					this._removeFrame(frameName);
					this._createFrame(frameName);
				} 
				
				var frameWindow = window.frames[frameName];
				return frameWindow;
			};
			
			
			//export용 Iframe 제거
			_pExtExcelExport._removeFrame = function (frameName) {
				 var frame = document.getElementById(frameName);
				 frame.parentNode.removeChild(frame);		
			};
			
			//export용 Iframe 생성
			_pExtExcelExport._createFrame = function (frameName) {
		        var frame = document.createElement("iframe");
		        frame.name = frameName;
		        frame.id = frameName;

		        document.body.appendChild(frame);
		        
		        this.setIFrameContents(frameName, this.iframeSource);
		        

		        
		        if(this.debug) {
		        	frame.style.left = "200px";
		        	frame.style.top = "200px";
		        	frame.style.width = "400px";
		        	frame.style.height = "100px";
		        	frame.style.frameborder = "1";
		        	frame.style.border = "solid red 2px";
		        	frame.style.position = 'absolute';
		        	
		        } else {
		        	frame.style.visibility = 'hidden';	
		        	frame.style.width = "0px";
		        	frame.style.height = "0px";
		        	//frame.style.border = "0px";
		        	frame.style.frameborder = "0";
		        	frame.style.position = 'absolute';        	
		        }
			};	
			
			//export용 Iframe Contents 설정
			_pExtExcelExport.setIFrameContents = function (frameName, contents) {
				var frameWindow = window.frames[frameName];
				var frameWinDoc = frameWindow.document;
				
				frameWinDoc.open();
				frameWinDoc.write(contents);
				frameWinDoc.close();		
			};			
		};
		
		
		//webBrowser onerror event for excel export.
		_pExtExcelExport._makeWebBrowser_onError = function (exportObj) {
			return function(obj, e) {
				alert("_makeWebBrowser_onError: url=" + e.url );		
			};		
		};
		
		
		
		if (nexacro.Browser != "Runtime") 
		{
			//ifram error 체크용 
			// onload는 에러시에도 발생함
			// 에러 발생시에 FF와 safari에서는 호출이 되지 않는다.
			_pExtExcelExport._makeTimerHandler = function (exportObj) {
				
				return function(scope, event) {
					var timerid = event.timerid;
					scope.killTimer(timerid);
					
					var frameObj = document.getElementById(exportObj.exportIFrameName);
					var frameDocBody = frameObj.contentWindow.document.body;
					
					//FireFox에서는 innnerHTML만 존재
					var responseMessage = (frameDocBody.innerText)?(frameDocBody.innerText):frameDocBody.innerHTML;
					
					
					//정상일 경우.
					//chrome, safari, opera에서는 responseMessage가 없다.
					//IE8,9 : <FORM id=exportForm ~, IE10+, FF : <form id="exportForm" ~
					if(!Eco.isEmpty(responseMessage) && responseMessage.length > 10) {
					 var str = responseMessage.substr(0, 50);
					 
					 if(str.indexOf(exportObj.exportFormName) < 0) {
						 //trace("Error:\n>" + responseMessage +"<");
						 var callback = exportObj._onerrorCallback;
						 if(callback) {
							 callback(exportObj, responseMessage);
						 }
					 }
					}
				};
			};			
		
		}
		else if (nexacro.Browser == "Runtime") 
		{
			//ifram error 체크용 
			// onload는 에러시에도 발생함
			// 에러 발생시에 FF와 safari에서는 호출이 되지 않는다.
			_pExtExcelExport._makeTimerHandler = function (exportObj) {
				
				return function(scope, event) {
					var timerid = event.timerid;
					
					trace(" @@@@ _makeTimerHandler timerid="+timerid);
					
					if(timerid == 55) {					
						scope.killTimer(timerid);

						var webBrowser = scope[exportObj.exportIFrameName];
						var body = webBrowser.getProperty("document").getProperty("body");
						var innerText = body.getProperty("innerText");
						
						var responseMessage = (innerText)?innerText:body.getProperty("innerHTML");
						
						//정상일 경우.
						//chrome, safari, opera에서는 responseMessage가 없다.
						//런타임, IE8,9 : <FORM id=exportForm ~, IE10+, FF : <form id="exportForm" ~
						if(!Eco.isEmpty(responseMessage) && responseMessage.length > 10) {
							 var str = responseMessage.substr(0, 50);
							 
							 if(str.indexOf(exportObj.exportFormName) < 0) {
								 trace("\t\t\t======================================================");
								 trace("\t\t\t\t[ ExtExcelExport._makeTimerHandler() >>> Error ]");
								 trace("\t\t\t======================================================");
								 trace(responseMessage);
								 
								 var callback = exportObj._onerrorCallback;
								 if(callback) {
									 callback(exportObj, responseMessage);
								 }
							 }
						} else if(Eco.isEmpty(responseMessage)) {
							 trace("\t\t\t======================================================");
							 trace("\t\t\t\t[ ExtExcelExport._makeTimerHandler() >>> Error ]");
							 trace("\t\t\t======================================================");
							 trace("Export Error!!");	
							 
							 var callback = exportObj._onerrorCallback;
							 if(callback) {
								 callback(exportObj, "Export Error!!");
							 }						
						}
						
						
					} else if(timerid == 110) {
					
						scope.killTimer(timerid);
						exportObj.setIFrameContents(scope[exportObj.exportIFrameName], exportObj.iframeSource);
						exportObj._exportDataRuntime();
						return;
					}	
				};
			};	
		
		}

		
		
	    _pExtExcelExport.set_onerrorCallback = function (callFn) {
			if(typeof callFn != 'function') {
				Eco.Logger.error({"message":"callBack function is not function!", "stack":true});
				return;
			}

			this._onerrorCallback = callFn;
	    };    
	   
		
	    //excel export url
		_pExtExcelExport.set_exportUrl = function(url) {
			if(!Eco.isEmpty(url)) {
				this.exportUrl = url;
				
				
			} else {
				Eco.Logger.error({"message":"url is empty!", "stack":true});			
			}
		};
		
	    //datatype data 전송방식. 0:XML, 1:Binary(Runtime only), 2:SSV
		_pExtExcelExport.set_sendDataType = function(dataType) {
			if(!Eco.isEmpty(dataType)) {
				if(dataType > 2 || dataType < 0){
					Eco.Logger.error({"message":"dataType-range allow 0 ~ 2.", "stack":true});	
				}
			
				this.sendDataType = dataType;
				
				
			} else {
				Eco.Logger.error({"message":"dataType is empty!", "stack":true});			
			}
		};		
		
		
		_pExtExcelExport.get_exportUrl = function(url) {
			return this.exportUrl;
		};	
		
		_pExtExcelExport.set_arg = function(arg) {
			this.arg = arg;
				
		};	
		
		_pExtExcelExport.set_debug = function(value) {
			if(Eco.isEmpty(value)) return;
			
			if(!Eco.isBoolean(value)) return;
			
			this.debug = value;
		};
		
		_pExtExcelExport.set_name = function(name) {
			if(!Eco.isEmpty(name)) {
				if(!Eco.isString(name)) {
					Eco.Logger.error({"message":"name is not string!", "stack":true});
				}
				
				this.id = name;
				
			} else {
				Eco.Logger.error({"message":"name is empty!", "stack":true});			
			}
		};	
		
		/**
		 * nexacro.LoadManager > loadMainModule()에서 호출함수 overriding.
		 */
		_pExtExcelExport.getParentContext = function() {
			return this.scope;
		};
		
		
		
		/**
		 * export.jsp에 엑셀변환용 데이터 전송
		 * @param {activeX} ax_msie webbrowser
		 * @param {object} wb  workbook object
		 * @param {string} datasetList  데이터셋 목록
		 * @param {string} xmlData  데이터셋의 내용을 XML 형식으로 저장된 문자열
		 */
		_pExtExcelExport.exportData = function() {
			if (nexacro.Browser != "Runtime"){
				this._exportDataHtml();
				
			} else {
				var webBrowser = this.getExportIFrame(); //_exportDataRuntime() 호출은 timer에서

			}
		};	
		
		    
		/**
		 * export.jsp에 엑셀변환용 데이터 전송
		 * @param {activeX} ax_msie webbrowser
		 * @param {object} wb  workbook object
		 * @param {string} datasetList  데이터셋 목록
		 * @param {string} xmlData  데이터셋의 내용을 XML 형식으로 저장된 문자열
		 */
		_pExtExcelExport._exportDataRuntime = function() {
			var id = this.id;
			var scope = this.scope;
			var arg = this.arg;
			var url = this.exportUrl;
			
		
			if(Eco.isEmpty(id)) {
				Eco.Logger.error({"message":"id is empty!", "stack":true});
				return;
			}
			
			if(Eco.isEmpty(scope)) {
				Eco.Logger.error({"message":"scope is empty!", "stack":true});
				return;
			}

			if(Eco.isEmpty(url)) {
				Eco.Logger.error({"message":"url is empty!", "stack":true});
				return;
			}		

			
			//workbook 정보를 json으로 변환
			var workbook = this.workbook;
			//var inputDataset = this.convertExprDatasetToXML(); 
			
			//X-API에 loadSSV() 필요.
			var inputDataset;
			if(this.sendDataType == 0) {
				workbook.dataType = "xml";
				inputDataset = this.convertExprDatasetToXML();
				
			} else {
				workbook.dataType = "ssv";
				inputDataset = this.convertExprDatasetToSSV();
			}
			
			var datasetList = inputDataset.datasetList;
			var xmlData = inputDataset.xmlData;
			
			this._debug(workbook, datasetList, xmlData);
			
			//return;
			var jsonWb = this.encodeToJson(workbook);
			var inputDs = "";
			 
	        //데이터 셋과 arg를  param으로 만들기.... 0:XML, 1:BINARY, 2:SSV
	        var loadItem = new nexacro.TransactionItem(url, scope, id, inputDs, "", arg, 0, true);
	        
	        var sendData = loadItem._sendData;
	        
	        var webBrowser = this.scope[this.exportIFrameName];
	        if(!webBrowser){
	        	trace("[Error] ExtExcel > exportData : WebBrowser not exist!");
	        	return;
	        }
	        
	        
	        //trace("======= 1 =============");
			//trace("document="+webBrowser.getProperty("document"));
			
			var exportForm = webBrowser.getProperty("document").callMethod("getElementById", this.exportFormName);
			
			exportForm.setProperty("action", url);
			
			
	        var target,hiddenWorkbook,hiddenDatasetList, hiddenXmlData;
			hiddenWorkbook = webBrowser.getProperty("document").callMethod("getElementById", "workbook");
			hiddenWorkbook.setProperty("value", jsonWb);
			
			hiddenDatasetList = webBrowser.getProperty("document").callMethod("getElementById", "datasetList");
			hiddenDatasetList.setProperty("value", datasetList);
			
			
			hiddenXmlData = webBrowser.getProperty("document").callMethod("getElementById", "xmlData");
			hiddenXmlData.setProperty("value", xmlData);
			
			
	        try {
				exportForm.callMethod("submit", "");
				
				scope.setTimer(55, 1500);
	        	
	        } catch(e) {
	        	
	    		trace("\n\n******* [ExtExcelExport] Fail to send export data: " + e);
	    		
	        } finally {
				this.clearWorkbook();
	        }			
			
			//2014.11.12.01 버전에서 적용되지 않는다.
//	        if(this.debug == true) {
//				hiddenWorkbook.setProperty("type", "text");
//				hiddenDatasetList.setProperty("type", "text");
//				hiddenXmlData.setProperty("type", "text");
//	        }        
	        

		};			
		
		
		/**
		 * export.jsp에 엑셀변환용 데이터 전송
		 * @param {activeX} ax_msie webbrowser
		 * @param {object} wb  workbook object
		 * @param {string} datasetList  데이터셋 목록
		 * @param {string} xmlData  데이터셋의 내용을 XML 형식으로 저장된 문자열
		 */
		_pExtExcelExport._exportDataHtml = function() {
			var id = this.id;
			var scope = this.scope;
			var arg = this.arg;
			var url = this.exportUrl;
			
			if(Eco.isEmpty(id)) {
				Eco.Logger.error({"message":"id is empty!", "stack":true});
				return;
			}
			
			if(Eco.isEmpty(scope)) {
				Eco.Logger.error({"message":"scope is empty!", "stack":true});
				return;
			}

			if(Eco.isEmpty(url)) {
				Eco.Logger.error({"message":"url is empty!", "stack":true});
				return;
			}		
			
			
			//workbook 정보를 json으로 변환
			var workbook = this.workbook;
			var inputDataset = this.convertExprDatasetToXML();
			
			var datasetList = inputDataset.datasetList;
			var xmlData = inputDataset.xmlData;
			
			this._debug(workbook, datasetList, xmlData);
			
			var jsonWb = this.encodeToJson(workbook);
			var inputDs = "";
			 
	        //데이터 셋과 arg를  param으로 만들기.... 0:XML, 1:BINARY, 2:SSV
	        var loadItem = new nexacro.TransactionItem(url, scope, id, inputDs, "ds_receiveData=ds_receiveData", arg, 0, true);
	        var sendData = loadItem._sendData;
	        
	        
	        //IFrame 적용
	        
	        var iframe = this.getExportIFrame();
	        if(!iframe){
	        	trace("[Error] ExtExcel > exportData : iframe not exist!");
	        	return;
	        }
	        
			//export error 체크용 timer timerid = 55
	        scope.addEventHandler("ontimer", this._makeTimerHandler(this), scope);
	        
	        //전송용 데이터 설정
	        var iframeDoc = iframe.document;
	        var exportForm = iframeDoc.getElementById(this.exportFormName);
	        exportForm.action = url;

	        var target,hiddenWorkbook,hiddenDatasetList, hiddenXmlData;
	        
	        hiddenWorkbook = iframeDoc.getElementById("workbook");
	        hiddenWorkbook.value = jsonWb;
	        
	        hiddenDatasetList = iframeDoc.getElementById("datasetList");
	        hiddenDatasetList.value = datasetList;
	        
	        hiddenXmlData = iframeDoc.getElementById("xmlData");
	        hiddenXmlData.value = xmlData;
	        
			
	        if(this.debug == true) {
	        	//IE8에서는 type 속성을 지원하지 않는다.
	        	if (nexacro.Browser == "IE" && nexacro.BrowserVersion < 9) {
	        		
	        	} else {
	            	hiddenWorkbook.type = "text";
	            	hiddenDatasetList.type = "text";
	            	hiddenXmlData.type = "text";        		
	        	}
	        }        
	        
	        try {
	        	exportForm.submit(); //form 전송
	        	scope.setTimer(55, 1500);
	        	
	        } catch(e) {
	        	
	    		console.error("\n\n******* [ExtExcelExport] Fail to send export data: " + e);
	    		
	        } finally {

	        }
		};			
				
		
		_pExtExcelExport._debug = function (workbook, datasetList, xmlData) {
			if(this.debug == false) return;
			
			//return;
			
			trace("**************************************");
			trace("*********** [Debugging] **************");
			trace("**************************************\n");

			
			trace("▶▶ [ Workbook ] \n");
			
			
			var cellPropertyData = workbook.cellPropertyData;
			var cellPropLength =  cellPropertyData.length;
			
			var str = "fileName= " + workbook.fileName;
			str += " ,exportExpr= " + workbook.exportExpr + "\n";
			
			trace(str);
			
			str = "";
			
			for(var i=0; i<cellPropLength; i++) {
				var temp = cellPropertyData[i];
				str += " key=" + temp.key;
				str += ",displayType=" + temp.displayType;
				
				var tempFont = temp.font;
				str += ",font face=" + tempFont.face +", size=" + tempFont.size +", type=" + tempFont.type;
				str += ",color=" + temp.color;
				str += ",background=" + temp.background;
				str += "\n\t\t,hAlign=" + temp.hAlign;
				str += ",vAlign=" + temp.vAlign;
				str += ",mask=" + temp.mask;
				str += ",line=" + temp.line;
				
				str += "\n";
				
			}
			
			trace("=== cellPropertyData === \n" + str);
			
			
			var sheets = workbook.sheets;
			var sheetsLength =  sheets.length;
			
			var str = "";
			for(var i=0; i<sheetsLength; i++) {
				var temp = sheets[i];
				str += "sheetName= " + temp.sheetName + "\n";
				
				var rows = temp.rows;
				var rowLen = rows.length;
				//trace("rowLen="+rowLen);
				
				for(var j=0; j<rowLen; j++ ) {
					var tempRow = rows[j];
					var columnBox = tempRow.columnBoxes;
					
					if(!columnBox) continue;
					
					var cbLen = columnBox.length;
					
					//trace("cbLen="+cbLen);
					for(var k=0; k<cbLen; k++) {
						var tmpBox = columnBox[k].boxes;
						var boxLen = tmpBox.length;
						
						//trace("boxLen="+boxLen);
						for(var l=0; l<boxLen; l++) {
							str += "\ntype= " + tmpBox[l].type;
							str += "\n\t" + j +" Row, " + k + " columnBox, " + l + " box.";
							
							
							if(tmpBox[l].type == "label"){
								var contents = tmpBox[l].contents;
								str += "\n\t\t[contents] > title=" + contents[0]
										+ ", face=" + contents[1].face
										+ ", size=" + contents[1].size
										+ ", type=" + contents[1].type
										+ ", fontColor=" + contents[2]
										+ ", background=" + contents[3]
										+ ", hAlign=" + contents[4]
										+ ", vAlign=" + contents[5];
								
							} else if(tmpBox[l].type == "grid"){
								var contents = tmpBox[l].contents;
								var contetsLen = contents.length;
								for(var m=0; m<contetsLen; m++) {
									var tmpContents = contents[m];	
									var tmpContents = Eco.Json.decode(tmpContents);
									
									str += "\n\t\t[contents] > gridName=" + tmpContents.gridName
									+ ", formatId=" + tmpContents.formatId
									+ ", dataUid=" + tmpContents.dataUid
									+ ", exportExpr=" + tmpContents.exportExpr
									+ ", exportSvcArg=" + tmpContents.exportSvcArg
									+ ", propertyDatasetId=" + tmpContents.propertyDatasetId
									+ ", suppressInfo=" + tmpContents.suppressInfo
									+ ", suppresslevel=" + tmpContents.suppresslevel;
									
									var columns = tmpContents.columns;
									var colLen = columns.length;
									str += "\n\t\t\t";
									for(var n=0; n<colLen; n++) {
										str += ", columns["+ n +"] " + columns[n].band + ", size=" + columns[n].size;
										str += "\n\t\t\t";
									}
									
									var rows = tmpContents.rows;
									var rowsLen = rows.length;
									str += "\n\t\t\t";
									for(var n=0; n<rowsLen; n++) {
										str += ", rows["+ n +"] " + rows[n].band + ", size=" + rows[n].size;
										str += "\n\t\t\t";
									}
									
									var headBand = tmpContents.headBand;
									var headBandLen = headBand.length;
									str += "\n\t\t\t";
									for(var n=0; n<headBandLen; n++) {
										str += ", headBand["+ n +"] " + Eco.Json.encode(headBand[n]);
										str += "\n\t\t\t";
									}									
									
									var bodyBand = tmpContents.bodyBand;
									var bodyBandLen = bodyBand.length;
									str += "\n\t\t\t";
									for(var n=0; n<bodyBandLen; n++) {
										str += ", bodyBand["+ n +"] " + Eco.Json.encode(bodyBand[n]);
										str += "\n\t\t\t";
									}										

									var summBand = tmpContents.summBand;
									var summBandLen = summBand.length;
									str += "\n\t\t\t";
									for(var n=0; n<summBandLen; n++) {
										str += ", summBand["+ n +"] " + Eco.Json.encode(summBand[n]);
										str += "\n\t\t\t";
									}										
									
								}
								
							}
							
						}
					}
					
					
				}
				str += "\n";
				
			}
			
			trace("=== sheets 정보 === \n" + str);	
			
		 	trace("\n▶▶[ datasetList ] \n" + datasetList);
		 	trace("\n\n▶▶[ xmlData ] \n" + xmlData);
		 	
		};
		

		
		
		/**
		 * workbook 관련정보 해제.
		 */	
		_pExtExcelExport.clearWorkbook = function (svcid, errstatus, message) {
			trace("\n★ clearWorkbook 호출");
	
			this.releaseExprDataset("text");
			this.releaseExprDataset("property");
			this.releaseFilterDataset();
			this.inputDatasets = [];
		};	
		

		
		
		/**
		 * cell에 display되는 text를 담은 
		 * Dataset 목록과 xml문자열 반환
		 * xml 분리자는 <SeperatoR>로 한다.
		 * @param {array.<dataset>} datasets 그리드에 바인딩된 
		 *             데이터셋 객체들의 배열. ex) [Dataset01,Dataset02]
		 * 전달받은 Dataset이 필터처리된 경우 
		 * 필터된 데이터만 복사한 데이터셋을 생성 후 서버로 전달한다.
		 * @return {object} Dataset 목록과 xml문자열 반환
		 */	
		_pExtExcelExport.convertExprDatasetToXML = function() {
			var size, ds;
			var tempDs, isUsed;
			var inputDs = "";
			var datasetPool;
			var xmlData = "";	
			
			var exprDatasetPool = this.exprDatasetPool;
			var exprPropertyPool = this.exprPropertyPool;
			var datasets = this.inputDatasets;
			
			//trace("convertExprDatasetToXML > datasets="+datasets);
			
			for(var k=0; k<2; k++)
			{
				if(k==0)
				{
					datasetPool = exprDatasetPool;
				}
				else 
				{
					datasetPool = exprPropertyPool;
				}
				
				size = datasetPool.length;
				
				for(var i=0; i<size; i++)
				{
					tempDs = datasetPool[i];
					isUsed = tempDs["_used"];
					//trace("convertExprDatasetToXML > tempDs="+tempDs.name + ", used="+ isUsed);
					//datasetPool에서 실제 사용된 Dataset만 추출한다.
					if(isUsed)
					{
						if(inputDs.length==0)
						{
							inputDs += tempDs.name;
							xmlData += tempDs.saveXML();
						}
						else
						{
							inputDs += "<SeperatoR>" + tempDs.name;
							xmlData += "<SeperatoR>" + tempDs.saveXML(tempDs.name, "A");
						}
						
						
					}
				}	
			}
			
			
			
			if(!Eco.isEmpty(datasets))
			{
				size = datasets.length;
				
				for(var i=0; i<size; i++)
				{
					tempDs = datasets[i];
					
					//필터링된 데이터만 신규 데이터셋에 복사 후 반환. 
					if(!Eco.isEmpty(tempDs.filterstr))
					{
					    //필터링된 데이터만 신규 데이터셋에 복사
						tempDs = this.getFilterDataset(tempDs);
					}
					
					
					if(inputDs.length==0)
					{
						inputDs += tempDs.name;
						xmlData += tempDs.saveXML();
					}
					else
					{
						inputDs += "<SeperatoR>" + tempDs.name;
						xmlData += "<SeperatoR>" + tempDs.saveXML(tempDs.name, "A");
					}			
				}
			}
			
			//trace("convertExprDatasetToXML > datasetList="+ inputDs);
			return {datasetList:inputDs, xmlData:xmlData};
		};
		
		
		
		/**
		 * cell에 display되는 text를 담은 
		 * Dataset 목록과 xml문자열 반환
		 * xml 분리자는 <SeperatoR>로 한다.
		 * @param {array.<dataset>} datasets 그리드에 바인딩된 
		 *             데이터셋 객체들의 배열. ex) [Dataset01,Dataset02]
		 * 전달받은 Dataset이 필터처리된 경우 
		 * 필터된 데이터만 복사한 데이터셋을 생성 후 서버로 전달한다.
		 * @return {object} Dataset 목록과 xml문자열 반환
		 */	
		_pExtExcelExport.convertExprDatasetToSSV = function() {
			var size, ds;
			var tempDs, isUsed;
			var inputDs = "";
			var datasetPool;
			var xmlData = "";	
			
			var exprDatasetPool = this.exprDatasetPool;
			var exprPropertyPool = this.exprPropertyPool;
			var datasets = this.inputDatasets;
			
			//trace("convertExprDatasetToXML > datasets="+datasets);
			
			for(var k=0; k<2; k++)
			{
				if(k==0)
				{
					datasetPool = exprDatasetPool;
				}
				else 
				{
					datasetPool = exprPropertyPool;
				}
				
				size = datasetPool.length;
				
				for(var i=0; i<size; i++)
				{
					tempDs = datasetPool[i];
					isUsed = tempDs["_used"];
					//trace("convertExprDatasetToXML > tempDs="+tempDs.name + ", used="+ isUsed);
					//datasetPool에서 실제 사용된 Dataset만 추출한다.
					if(isUsed)
					{
						if(inputDs.length==0)
						{
							inputDs += tempDs.name;
							xmlData += tempDs.saveSSV();
						}
						else
						{
							inputDs += "<SeperatoR>" + tempDs.name;
							xmlData += "<SeperatoR>" + tempDs.saveSSV(tempDs.name, "A");
						}
						
						
					}
				}	
			}
			
			
			
			if(!Eco.isEmpty(datasets))
			{
				size = datasets.length;
				
				for(var i=0; i<size; i++)
				{
					tempDs = datasets[i];
					
					//필터링된 데이터만 신규 데이터셋에 복사 후 반환. 
					if(!Eco.isEmpty(tempDs.filterstr))
					{
					    //필터링된 데이터만 신규 데이터셋에 복사
						tempDs = this.getFilterDataset(tempDs);
					}
					
					
					if(inputDs.length==0)
					{
						inputDs += tempDs.name;
						xmlData += tempDs.saveSSV();
					}
					else
					{
						inputDs += "<SeperatoR>" + tempDs.name;
						xmlData += "<SeperatoR>" + tempDs.saveSSV(tempDs.name, "A");
					}			
				}
			}
			
			//trace("convertExprDatasetToXML > datasetList="+ inputDs);
			return {datasetList:inputDs, xmlData:xmlData};
		};		
		
		
		
		/**
		 * 필터링된 데이터만 신규 데이터셋에 복사 후 반환.
		 * @param {dataset} dataset 필터링된 데이터셋
		 * @return {dataset} 필터링된 데이터가 복사된 dataset.
		 */	
		_pExtExcelExport.getFilterDataset = function(dataset) {
			var ds = this.getReusedDataset("filter");
			ds.copyData(dataset, true);

			//2015.05.14 update
			ds.set_name(dataset.name);
			return ds;		
		};
		
		
		
		/**
		 * workbook을 JSON으로 변환
		 *(오버플로우를 방지하기 위해 parent를 제거한다).
		 * @param {object} workbook  workbook object
		 * @return {object} JSON으로 변환된 문자열.
		 */	
		_pExtExcelExport.encodeToJson = function(workbook) {
			var sheets = workbook.sheets;
			var count = sheets.length;
			
			for(var i = 0; i < count; i++)
			{
				var rows = sheets[i]["rows"];
				var count2 = rows.length;
				for(var j = 0; j < count2; j++)
				{
					var columnBoxes = rows[j]["columnBoxes"];
					var count3 = columnBoxes.length;
					for(var k = 0; k < count3; k++)
					{
						delete columnBoxes[k]["parent"];
					}
					
					delete rows[j]["parent"];
				}
				
				delete sheets[i]["parent"];
			}
			
			return Eco.Json.encode(workbook);		
		};
		
		
		
		/**
		 * workbook에 grid에 display 되는 Data를 추가한다.
		 * @param {object} workbook object
		 * @param {grid} grid Grid Component
		 */	
		_pExtExcelExport.addDisplayData = function(workbook, grid) {
			this.setDisplayData(grid, "body");
			
			var dataUid = grid["_exprDs"].name;
			
			if(Eco.isEmpty(dataUid))
			{
				var msg = "dataUid가 존재하지 않습니다. 그리드명: " + grid.name;
				    msg += ". formatToJson() 그리드와 일치여부를 확인하시기 바랍니다.";
				
				Eco.Logger.error({message:msg , stack:true});
			}		
		};
		
		
		
		/**
		 * expr이 처리된 정보를 얻기위한 처리
		 * 그리드의 cell에 Display되는 Text값을 추출한다.
		 * 컬럼 index별 row의 text를 []에 담는다.
		 * @param {grid} grid Grid Component
		 * @param {string} band 얻고자 하는 Cell의 영역 입니다.
		 *                 'Head'/'Body'/'Summ'('Summary')를 지정합니다.
		 */	
		_pExtExcelExport.setDisplayData = function(grid, band) {
			var cellCount = grid.getCellCount(band);
			
			//expr 처리를 위해 임시로 추가된 컬럼은 제외
			if(grid._exportExprColumn) cellCount--;
			
			var rowCount  = grid.rowcount;
			
			if(cellCount == 0 || rowCount == 0) return 0;

		    var col;
			var exprColIndex = -1;
			
			if(grid._exportExprColumn) exprColIndex = grid._exportExprColumn;
			
			var exprDs = grid["_exprDs"];
			
			for (var i = 0; i < rowCount; i++) 
			{
				var rowPosition;
				var cellIndex = 0;
				for (var j = 0; j < cellCount; j++) 
				{
					col = grid.getCellProperty(band, j, "col");
					//expr 처리를 위해 임시로 추가된 컬럼은 제외
					if(exprColIndex == col) continue;
					
					if(i==0) exprDs.addColumn(cellIndex, "string");
					
					if(j==0) rowPosition = exprDs.addRow();

					text = grid.getCellText(i, j);
					exprDs.setColumn(rowPosition, cellIndex, text);
					cellIndex++;
				}
			}		
		};
		

		/**
		 * Expr이 적용된 각 cell의 속성값을 보관할 Dataset 반환(재사용 처리)
		 * @param {grid} grid Grid Component
		 * @param {string} type text: cell text보관용, property: cell 속성보관용
		 *                      , filter: 필터링된 데이터 보관용
		 * @return {dataset} Dataset Object
		 */	
		_pExtExcelExport.getExprDataset = function(grid, type) {
			var datasetPool;
			var uidPrefix;
			var gridLinkName;
			
			if(type=="text")
			{
				gridLinkName = "_exprDs";
			}
			else if(type=="property")
			{
				gridLinkName = "_exprPropertyDs";
			}
			else
			{
				Eco.Logger.error({"message":"getExprDataset(): type=[" 
				                       + type +"] argument값이 올바르지 않습니다.", "stack":true});
			}
			
		    ds = this.getReusedDataset(type);
			ds["_grid"] = grid;
			
		    //exprDs 설정
		    grid[gridLinkName] = ds;
		    
			return ds;		
		};
		
		
		
		/**
		 * 재사용 처리되는 Dataset 반환
		 * @param {string} type text: cell text보관용, property: cell 속성보관용
		 *                      , filter: 필터링된 데이터 보관용
		 * @return {dataset} Dataset Object
		 */	
		_pExtExcelExport.getReusedDataset = function(type) {
			var datasetPool;
			var uidPrefix;
			var gridLinkName;
			
			if(type=="text")
			{
				datasetPool = this.exprDatasetPool;
				uidPrefix = "_exprDs_";
			}
			else if(type=="property")
			{
				datasetPool = this.exprPropertyPool;
				uidPrefix = "_exprPropertyDs_";
			}
			else if(type=="filter")
			{
				datasetPool = this.filterDatasetPool;
				uidPrefix = "_filterDs_";
			}	
			else
			{
				Eco.Logger.error({"message":"getExprDataset(): type=[" + type +"] argument값이 올바르지 않습니다.", "stack":true});
			}
			
			var size = datasetPool.length;
			var ds;
			var tempDs;
			var isUsed;
			for(var i=0; i<size; i++)
			{
				tempDs = datasetPool[i];
				isUsed = tempDs["_used"];
				//trace("\n\t\t\t ■■■■ 체크  getReusedDataset > tempDs.name="+tempDs.name + ", _used=true")
				if(!isUsed)
				{
					ds = tempDs;
					ds["_used"] = true;
					//trace("\n\t\t\t ■ 재사용 getReusedDataset > tempDs.name="+tempDs.name + ", _used=true");
		            break;
				}
			}
			
			if(!ds)
			{

				var uid = Eco.getSequenceId(this.scope, uidPrefix);
				ds = new Dataset(uid);
				
				ds["_used"] = true;
				datasetPool.push(ds);
				
				//trace("\n\t\t\t  ■ 생성 getReusedDataset > ds.name="+ds.name + ", _used=true");
			}

			return ds;		
		};
		
		
		
		/**
		 * Expr 처리용 Dataset 초기화 및 관련정보 해제
		 * @param {string} type text: cell text보관용, property: cell 속성보관용
		 */	
		_pExtExcelExport.releaseExprDataset = function(type) {
			var datasetPool;
			var uidPrefix;
			var gridLinkName;
			//var scope = this.scope;
			
			if(type=="text")
			{
				datasetPool = this.exprDatasetPool;
				uidPrefix = "_exprDs_";
				gridLinkName = "_exprDs";
			}
			else if(type=="property")
			{
				datasetPool = this.exprPropertyPool;
				uidPrefix = "_exprPropertyDs_";
				gridLinkName = "_exprPropertyDs";
			}
			
			
			//trace("  >>>> releaseExprDataset   type="+type + ", datasetPool=" + datasetPool);
			var size = datasetPool.length;
			var ds;
			var tempDs, tempGrid;
			var isUsed;
			for(var i=0; i<size; i++)
			{
				tempDs = datasetPool[i];
				
				tempDs.clear();
				tempDs["_used"] = false;
				tempGrid = tempDs["_grid"];
				
				
				//trace("releaseExprDataset tempDs="+tempDs.name+ ", tempGrid="+tempGrid.name);
				//trace("tempDs="+tempDs);
				
				if(tempGrid){
					if(tempGrid["_exportExprColumn"]) tempGrid["_exportExprColumn"] = undefined;
					tempGrid[gridLinkName] = undefined;
				}
				
				tempDs["_grid"] = undefined;
			}		
		};
		
		
		
		/**
		 * 필터링 데이터복사용 Dataset 초기화 및 관련정보 해제
		 */	
		_pExtExcelExport.releaseFilterDataset = function() {
			var datasetPool = this.filterDatasetPool;
			var size = datasetPool.length;
			var ds;
			var tempDs;
			
			for(var i=0; i<size; i++)
			{
				tempDs = datasetPool[i];
				tempDs.clear();
				tempDs["_used"] = false;
			}		
		};
		
		
		/**
		 * columnBox에 contents를 포함한 box 추가
		 * @param {object} columnBox columnBox object
		 * @param {number} offsetRow 상위 box와의 row 간격(columnBox별 최초 box 간격은 절대, 2번째부터 상대)
		 * @param {string} type "text", "image", "grid"
		 * @param {... {string | object }} value string(fontColor, background...),object(font, exportItem...)
		 *                 type이 text일 경우: 텍스트, font, fontColor, background, halign, valign
		  *                        grid일 경우: 그리드,exportOption[, title, font, background, halign, valign ]
		 * @return {object} cell object
		 */	
		_pExtExcelExport.addBox = function(columnBox, offsetRow, type) {
			var contents = [];
			
			//그리드 소제목 존재여부 체크
			//Sheet > textRow 배열에 먼저 등록시킨다.
			if(type == "grid" && arguments.length > 5)
			{
				//타이틀 높이를 계산하기 위한 처리(줄바꿈에 따른 높이계산 용도)
				//var text  = arguments[5];
				var line = arguments[5].split("\n");
				var newFontInfo = {};
				//exportOption이 객체여서 값변경시 공유가 되므로 신규로 생성해서 사용한다.
				Eco.object.copyProperties(newFontInfo, arguments[6]);			
				
				newFontInfo["lineCount"] =  line.length;
				arguments[6] = newFontInfo;
				
				//trace("\n\n  ▨▨*** text="+arguments[5]+", lineCount="+line.length + ", last="+newFontInfo["lineCount"]);
				//type 이후의 arguments만 추출
				var label = [columnBox, offsetRow, "label"];
				for(var j = 5, len = arguments.length; j < len; j++)
				{
					label.push(arguments[j]);
				}	
			
				//label객체 생성 후  변경된 내용 재귀호출
				this.addBox.apply(this, label);
				offsetRow = 0;
			}

			if(type == "grid")
			{
				var grid = arguments[3];
				var exportOption = arguments[4];
				var exportExpr = exportOption["exportExpr"];
				
				if(exportExpr == false) {
					//binddataset 정보 추출
					this.getBindDataset(grid);
				}
				
				
				
				var wb, encodeFormat;
				wb = this.getWorkbook(columnBox);
				
				if(Eco.isEmpty(exportExpr) || exportExpr === false)
				{
					trace("▨ " + grid.name + " workbook에 exportExpr 미사용="+exportExpr);
				}
				else
				{
					//workbook에 설정된 값을 읽어 온다.
					exportExpr = wb["exportExpr"];
					trace("▨ " + grid.name + " exportOption에 exportExpr 사용="+exportExpr);
				}
				
				//expr 사용시 더미컬럼추가!!! 순서주의
				if(exportExpr == true) 
				{
					grid.set_enableevent(false);
					grid.set_enableredraw(false);

					this.addTempColumn(grid);

				}
				
				encodeFormat = this.formatToJson(wb, grid, exportExpr, exportOption);
				
				if(exportExpr == true) this.addDisplayData(wb, grid);
				
				//expr 사용시 더미컬럼제거
				if(exportExpr == true) 
				{
		            //deleteContentsCol의 3번째인자에 false를 해줘야 열고정이 있어도 삭제된다.
					grid.deleteContentsCol(grid._exportExprBand, grid._exportExprColumn, false);
					grid._exportExprColumn = undefined;
					grid._exportExprBand = undefined;	
					grid._bodyOriginCellCount = undefined;					
					
					grid.set_enableevent(true);
					grid.set_enableredraw(true);			
				}
			
				arguments[3] = encodeFormat;
			
			}
			else if(type == "textRow")
			{
				//타이틀 높이를 계산하기 위한 처리(줄바꿈에 따른 높이계산 용도)
				//var text  = arguments[3];
				var line = arguments[3].split("\n");
				var newFontInfo = {};
				//exportOption이 객체여서 값변경시 공유가 되므로 신규로 생성해서 사용한다.
				Eco.object.copyProperties(newFontInfo, arguments[4]);				
				
				newFontInfo["lineCount"] =  line.length;
				arguments[4] = newFontInfo;
			}
			
			
			
			//type 이후의 arguments만 추출
			var len = arguments.length;
			
			// label에 사용된 정보가 grid contents에 추가되어
			// JSON으로 변환되는 것을 방지.
			// export option은 formatToJson에서 처리되므로 제거.
			if(type == "grid" && len > 5) len = 4; 
			
			var argValue;
			for(var i = 3; i < len; i++)
			{
				argValue = arguments[i];
				if(i > 4 && i < 7)
				{
					//5: font color, 6: background color
					if(!Eco.isEmpty(argValue))
					{
						argValue = this.getNamedColorToHexColor(argValue);
						argValue = this._getHEXtoRGB(argValue);
					}
					else 
					{
						if(i==5){
							//5: font color
							argValue = "#000000";
							argValue = this._getHEXtoRGB(argValue);
						} else if(i==6){
							//6: background color
							argValue = "#FFFFFF";
							argValue = this._getHEXtoRGB(argValue);
						}
						
					}
				}
				
				//trace("argValue = " + argValue);
				contents.push(argValue);
			}
			
		    //contents는   type이 text일 경우: 텍스트, font[, fontColor, background, halign, valign]
		    //                    grid일 경우: 그리드 컴포넌트[, title, font, background, halign, valign ]
			
			
			var box = {name: "box", offsetRow: offsetRow, type: type, contents: contents};
			columnBox["boxes"].push(box);
			return box;		
		};
		
		
		/**
		 * grid에 bind된 dataset 정보를 보관한다.
		 * @param {grid} grid Grid Object.
		 */	
		_pExtExcelExport.getBindDataset = function(grid) {
			var bindDsName = grid.binddataset;
			var ds = Eco.XComp.lookup(this.scope, bindDsName);
			
			if(!Eco.isEmpty(ds)) {
				var inputDatasets = this.inputDatasets;
				var len = inputDatasets.length;
				
				//inputDatasets 중복 체크
				if(len == 0){
					inputDatasets.push(ds);
				} else {
					var isExist = false;
					
					for(var i=0; i<len; i++) {
						if(inputDatasets[i].name == bindDsName){
							isExist = true;
							break;
						}
					}
					
					if(!isExist) {
						inputDatasets.push(ds);
					}
				}
				
			} else {
				Eco.Logger.error({"message":grid.binddataset + "을(를) 찾을 수 없습니다.", "stack":false});
			}
		};
		
		
		/**
		 * expr 처리용 임시 컬럼을 그리드에 추가한다.
		 * @param {grid} grid Grid component.
		 */	
		_pExtExcelExport.addTempColumn = function(grid) {
			var bodyOriginCellCount = grid.getCellCount("body");
			var count = grid.getFormatColCount();
			var band = grid.getFormatColProperty(count -1, "band");
			var returnIdx = grid.appendContentsCol(band);
			grid._exportExprColumn = returnIdx;
			grid._exportExprBand = band;	
			grid._bodyOriginCellCount = bodyOriginCellCount;
		};
		
		
		/**
		 * workbook을 반환한다.
		 * @param {object} source columnBox or row or sheet.
		 * @return {object} workbook.
		 */	
		_pExtExcelExport.getWorkbook = function(source) {
			var p = source.parent;

			while (p && p.name != "workbook")
			{
				p = p.parent;
			}
			
			return p;		
		};
		
		
		

		/**
		 * grid의 format정보를 json정보로 변환해서 반환한다.
		 * @param {object} workbook object
		 * @param {grid} grid grid component.
		 * @param {boolean} isExportExpr cell속성의 expression 반영여부.
		 * @param {object} exportOption ExportItemType별로 추가할 Export 정보를 가지는 ExportItem Object
		 * @return {string} JSON 형태로 변환된 문자열.
		 */
		 
		_pExtExcelExport.formatToJson = function(workbook, grid, isExportExpr, exportOption) {
			var columns, rows, headBand, bodyBand, summBand;
			var cellProperty = "";
			var gridName, formatId, dataUid, dataset;
			
			gridName = grid.name;
			formatId = grid.formatid || "default";

		 	if(isExportExpr)
		 	{	
				dataUid = this.getExprDataset(grid, "text").name;
				grid["_dataUid"] = dataUid;
			} 
			else
			{
			    //※workbook내에 동일한 데이터셋 이름이 존재하지 않토록 한다.
				dataUid = grid.binddataset;
				grid["_dataUid"] = dataUid;
			}
			
			
			columns  = this.getColumnsProperty(grid);
			rows     = this.getRowsProperty(grid);
			
			headBand = this.getBandProperty(grid, "head", isExportExpr);
			
			//suppress처리용
			var suppressInfo = grid["_suppressInfo"] = [];	
			
		 	bodyBand = this.getBandProperty(grid, "body", isExportExpr);
		 	summBand = this.getBandProperty(grid, "summ", isExportExpr);
		 	
		 	var suppresslevel = grid.suppresslevel;
		 	suppressInfo = suppressInfo.sort(this.sortAsc);
		 	

		 	//expr을 export에 반영시 각 cell의 속성값을 포함한다.
		 	var propertyDatasetId = "";
		 	
		 	if(isExportExpr)
		 	{
				//property 동일값은 재사용처리한다.
				this.getBodyExprCellProperty(grid, "body", isExportExpr);
				propertyDatasetId = grid["_exprPropertyDs"].name;
		 	}
		 	
		 	//format 정보
		 	var format = {gridName: gridName, formatId: formatId
		 	              , dataUid: dataUid
		 	              , exportExpr: isExportExpr
		 	              , exportSvcID: ""
		 	              , exportSvcArg: []
		 	              , propertyDatasetId: propertyDatasetId
		 	              , suppressInfo: suppressInfo
		 	              , suppresslevel: suppresslevel
		 	              , columns: columns, rows: rows
		 	              , headBand: headBand, bodyBand: bodyBand
		 	              , summBand: summBand
		 	             };

			var exportSvcID = exportOption["exportSvcID"];
			if(!Eco.isEmpty(exportSvcID)) format["exportSvcID"] = exportSvcID;
			
			var exportSvcArg = exportOption["exportSvcArg"];
			if(!Eco.isEmpty(exportSvcArg)) format["exportSvcArg"] = exportSvcArg;

			var encodeFormat = Eco.Json.encode(format);

		 	return encodeFormat;
		};
		

		
		/**
		 * sort 내림차순 정렬함수
		 */
		_pExtExcelExport.sortAsc = function(l, r) {
		  if ( l.suppress < r.suppress ) return -1;
		  if ( l.suppress > r.suppress ) return 1;
		  return 0;
		};
		

		/**
		 * workbook 생성
		 * @param {string} fileName workbook 명. 
		 * @param {boolean} isExportExpr cell속성의 expression export 여부
		 *                  (default: false). workbook 전체에 반영된다.
		 * @return {object} workbook을 관리하는 객체.
		 */
		_pExtExcelExport.createWorkbook= function(fileName, isExportExpr)
		{
			if(Eco.isEmpty(isExportExpr))
			{
				isExportExpr = false;
			}

			this.cellSeqId = 0;
			this.headSeqId = 0;
			this.bodySeqId = 0;
			this.summSeqId = 0;
			
			this.exportCellPropertyIndex = {};
			this.exportCellPropertyData  = [];
			
			this.workbook = {name: "workbook"
							, dataType: "xml"
							, fileName: fileName
							, cellPropertyData: this.exportCellPropertyData	         
							, sheets: [], exportExpr: isExportExpr}; 
			 
			         
			return this.workbook;
		};


		/**
		 * sheet 생성
		 * @param {object} workbook object
		 * @param {string} sheetName  sheet 명
		 * @return {object} sheet를 관리하는 객체
		 */
		_pExtExcelExport.createSheet = function (workbook, sheetName)
		{
		    //rows: sheet에 포함된 row들.
			var sheet = {name: "sheet", parent: workbook, sheetName: sheetName, rows: []};
			workbook["sheets"].push(sheet);
			return sheet;
		};


		/**
		 * sheet에 row 추가
		 * @param {object} sheet sheet object
		 * @param {number} offsetRow 상위 row와의 간격
		 * @return {object} row object
		 */
		_pExtExcelExport.addRow = function(sheet, offsetRow)
		{
		    //columnBoxes: row에 포함될 columnBox들.
			var row = {name: "row", parent: sheet, offsetRow: offsetRow, columnBoxes: []};
			sheet["rows"].push(row);
			return row;
		};

		/**
		 * row에 ColumnBox 추가
		 * @param {object} row row object
		 * @param {number} offsetColumn 왼쪽 컬럼과의 간격(row별 최초 columnBox 간격은 절대, 2번째부터 상대)
		 * @return {object} ColumnBox object
		 */
		_pExtExcelExport.addColumnBox = function(row, offsetColumn)
		{
		    //boxes: columnBox에 포함될 Box들.
			var columnBox = {name: "columnBox", parent: row, offsetColumn: offsetColumn, boxes: []};
			row["columnBoxes"].push(columnBox);
			return columnBox;
		};



		/**
		 * 그리드 현재 format의 Column size 및 틀고정(고정영역) 정보반환
		 * @param {grid} grid grid component
		 * @return {array} Column {size, band}
		 */
		_pExtExcelExport.getColumnsProperty = function(grid)
		{
			var propertyInfo = [];
			var count = grid.getFormatColCount();
			
			//expr 처리를 위해 임시로 추가된 컬럼은 제외
			if(grid._exportExprColumn) count--;	
			
			var size, band;
			
//			trace("== Columns 정보 ==");
			for(var i = 0; i < count; i++)
			{
				band = grid.getFormatColProperty(i, "band");
				
				//2013.6.25.1 버전.
				//getFormatColProperty의 size속성값은 string으로 나오고, getFormatRowProperty의 size속성값은 object로 넘어옴.
				//size = toNumber(grid.getFormatColProperty(i, "size"));
				//size2 = toNumber(grid.getRealColSize(i, false)); //left 고정이 존재하는 그리드에서 비정상작동!!
				size = nexacro.toNumber(grid.getRealColSize(i)); //bBandIndex값에 true, false 값을 주지 말것!!!
				
				propertyInfo.push({size: size, band: band});
			}
			
			return propertyInfo;
		};


		/**
		 * 그리드의 현재 format의 Row size 정보반환
		 * @param {grid} grid grid component
		 * @return {array} Row {size, band}
		 */
		_pExtExcelExport.getRowsProperty = function(grid)
		{
			var propertyInfo = [];
			var count = grid.getFormatRowCount();
			var size, band;
			
			trace("== Rows 정보 ==");

			for(var i = 0; i < count; i++)
			{
				//2013.6.25.1 버전.
				//getFormatColProperty의 size속성값은 string으로 나오고, getFormatRowProperty의 size속성값은 object로 넘어옴.
				//또, getFormatRowProperty의 band 속성은 string이 아닌 object로 넘어옴.
				size = nexacro.toNumber( grid.getFormatRowProperty(i, "size") );
				
				//엑셀의 단위는 px이 아님. 단위환산처리
				//RowHeight는 포인트(픽셀과 다름)단위
				//ColumnWidth는 문자 너비 단위
		        //참고 URL : http://office.microsoft.com/ko-kr/help/HP001151724.aspx?redir=0
		        //           http://www.translatorscafe.com/cafe/units-converter/typography/7-12/ 
				band = grid.getFormatRowProperty(i, "band").toString();
				
				
				propertyInfo.push({size: size, band: band});
			}
			
			return propertyInfo;
		};



		/**
		 * 그리드의 현재 format의 band 정보반환
		 *
		 * ※특이사항
		 * @gradation end color로 단색처리
		 * @transparent 처리
		 * [font] face: object, size: object, type: object 으로 XPLATFORM에서 반환.
		 *
		 * @param {grid} grid grid component
		 * @param {string} band 얻고자 하는 Cell의 영역 입니다.
		 *                 'Head'/'Body'/'Summ'('Summary')를 지정합니다.
		 * @param {boolean} isExportExpr cell속성의 expression 반영여부.
		 * @return {object} band {row, rowspan, col, colspan, text, displayType, style}
		 */
		_pExtExcelExport.getBandProperty = function(grid, band, isExportExpr)
		{
			trace("=== getBandProperty ===");
			var propertyInfo = [];
			var count = grid.getCellCount(band);
			var row, rowspan, col, colspan, suppress, suppressAlign;
			var text, tempText;
			var textRow = 0;
			var cellProperty, bindColumn = "";
			
			var stylePackage = this.getGridStylePackage(grid, band);
			
			band = band.toLowerCase();
			textRow = this.getBandToRow(band);
			
			//var baseCopy = Eco.object.copyProperties;
			var ecoObj = Eco.object;
			var exprColIndex = -1;
			
			if(grid._exportExprColumn) exprColIndex = grid._exportExprColumn;
			
			trace("■ " + band + " Band 정보 == count(expr컬럼포함)="+count + ", exprColIndex="+exprColIndex);
			var cellIndex = 0;
			
			for(var i = 0; i < count; i++)
			{
				col         = grid.getCellProperty(band, i, "col");
				//expr 처리를 위해 임시로 추가된 컬럼은 제외
				if(exprColIndex == col) continue;
				
				row         = grid.getCellProperty(band, i, "row");
				rowspan     = grid.getCellProperty(band, i, "rowspan");
				colspan     = grid.getCellProperty(band, i, "colspan");
				suppress    = nexacro.toNumber(grid.getCellProperty(band, i, "suppress")) || 0 ;
				suppressAlign = grid.getCellProperty(band, i, "suppressalign") || "first" ;
				
				text = grid.getCellText(textRow, i);
				
				if(band == "body")
				{
					bindColumn = "";
					tempText = grid.getCellProperty(band, i, "text");
					if(tempText && (tempText.indexOf("bind:") > -1) ) 
					{
						bindColumn = tempText.replace("bind:","");
					}
				}
				
				var info = { cellIndex: cellIndex, bindColumn: bindColumn
				             , row: row, rowspan: rowspan, col: col, colspan: colspan
				             , suppress: suppress, suppressAlign: suppressAlign
				           ,text: text};
				           
				cellIndex++;
				             
				cellProperty = this.getBandCellProperty(grid, band, i, isExportExpr, stylePackage);
				
				ecoObj.copyProperties(info, cellProperty); 
				propertyInfo.push(info);
				
				if(band == "body" && suppress > 0) 
				{
					if(!isExportExpr) 
					{
						grid["_suppressInfo"].push({suppress: suppress, bindColumn: bindColumn});
					}
					else 
					{ 
					    //expr 사용시 생성되는 Dataset의 컬럼은 컬럼index로 생성된다.
						grid["_suppressInfo"].push({suppress: suppress, bindColumn: i+""});
					}
					
					
				}		

			}
			
			return propertyInfo;
		};
		

		/**
		 * Body밴드 cell의 expr 정보를 
		 * 서버에서 변환시 사용하도록 각 cell의 속성값을 Dataset 설정 후 추가한다.
		 * @param {grid} grid grid component
		 * @param {string} band 얻고자 하는 Cell의 영역 입니다.
		 *                 'Head'/'Body'/'Summ'('Summary')를 지정합니다.
		 * @param {boolean} isExportExpr cell속성의 expression 반영여부.
		 */
		_pExtExcelExport.getBodyExprCellProperty = function(grid, band, isExportExpr)
		{
			var count = grid.getCellCount(band);
			var rowCount  = grid.rowcount;
			
			band = band.toLowerCase();
			
			var propertyData = [];
			var propertyName, result;
			var mask, color, background, align, hAlign, vAlign;
			
			var exprColIndex = grid._exportExprColumn;
			//var cellIndex = 0;

		    //cell 속성값 전달용 Dataset 생성
			var exprDs = this.getExprDataset(grid, "property");
			exprDs.set_enableevent(false);
			
			var originCount = grid._bodyOriginCellCount;
			//trace("\n ===== >>> originCount="+originCount);
			
			for(var i=0; i<originCount; i++) 
			{
				exprDs.addColumn(i, "string");
			}
			
			for(var i=0; i<rowCount; i++) 
			{
				exprDs.addRow();
			}	
			
			var columnIndex = 0;
			var stylePackage = this.getGridStylePackage(grid, band);
			trace("\n ====  start  =====  , exprColIndex= "+exprColIndex);
			//nCellIndex의 값은 (행 라인 * 열 개수 + Col 값)
			for(var j = 0; j < count; j++)
			{
				var col = grid.getCellProperty(band, j, "col");
				var cellIndex = grid.getCellProperty(band, j, "cellidx");
				//var rowspan = grid.getCellProperty(band, j, "rowspan");
				//trace("col=" + col + ", cellIndex="+cellIndex); // + ", rowspan="+rowspan); // + ", input cellIndex="+cellIndex);
				
				//expr 처리를 위해 임시로 추가된 컬럼은 제외	
				if(exprColIndex == col) continue;
				
			
				this.getPropertyValue(grid, band, cellIndex, columnIndex, exprDs, stylePackage);
				
				columnIndex++;
				
			}
			
			
			exprDs.set_enableevent(true);
		};


		/**
		 * expr을 export에 반영시 각 cell의 속성값을 얻어서
		 * 재사용하기 위한 처리를 한다.
		 * dataset에는 속성값의 key정보만 등록하고
		 * 서버에서 해당 key에 속성값을 찾아 cell에 반영한다.
		 * @param {grid} grid grid component
		 * @param {string} band 얻고자 하는 Cell의 영역 입니다.
		 *                 'Head'/'Body'/'Summ'('Summary')를 지정합니다.
		 * @param {number} cellIndex 얻고자 하는 Cell의 Index 입니다.
		 * @param {number} columnIndex 속성전달용 dataset setColumn시 사용할 Index 입니다.
		 * @param {dataset} exprDs cell 속성전달용 dataset.
		 * @param {object} stylePackage 그리드 currentsytle 속성값들.
		 */
		_pExtExcelExport.getPropertyValue = function(grid, band, cellIndex, columnIndex, exprDs, stylePackage)
		{
			var propertyList = ["mask", "font", "color", "background", "align", "displaytype", "line"];
			var propSize = propertyList.length;
			var rowCount  = grid.rowcount;
			var textRow = this.getBandToRow(band);
			
			var bandFont = stylePackage.bandFont;
			var bandColor = stylePackage.bandColor.value;
			var bandCellLine = stylePackage.bandCellLine;
			var bandBackground = stylePackage.bandBackground;
			var bandCellbackground  = stylePackage.bandCellbackground.color;
			var bandCellbackground2  = stylePackage.bandCellbackground2.color;
			var bandAlign = stylePackage.bandAlign;
			var bandBorder = stylePackage.bandBorder;
			var gridBackground = stylePackage.gridBackground;		
			
			var propertyName, propValue;
			var value, fontFace, fontSize, fontType;
			var displayType, font, color, background
			var line, align, hAlign, vAlign, mask;	
			var dataset, text, bindColumn, columnInfo;
			    			
			var tempColumnIndex = grid._exportExprColumn;
			

			
			for (var i = 0; i < rowCount; i++) 
			{	
				var valueObj = {};
				
				for(var k = 0; k < propSize;  k++)
				{	
					propertyName = propertyList[k];
					propValue = grid.getCellProperty(band, cellIndex, propertyName);
					
					if(this.isExpr(propValue))
					{
						if(propValue.indexOf("EXPR(") > -1 ) 
						{
							propValue = propValue.replace("EXPR(", "");
							propValue = propValue.substring(0, propValue.length -1);
							propValue = "expr:" + propValue;
						}			
						
						//expr 결과값을 얻기위해 "expr"에 설정 후 값을 얻는다.
						grid.setCellProperty(band, tempColumnIndex, "expr", propValue);
		                value = grid.getCellText(i, tempColumnIndex);
		                
					}
					else
					{
						value = propValue;
					}

					if(k == 0) 
					{
						valueObj["cellIndex"] = cellIndex;
					}
					
		
					switch(propertyName)
					{
						case "font":
							if(Eco.isEmpty(value)) {
								value = stylePackage["bandFont"];
							}
							
							var fontType = typeof(value);
							var face, size, type;
							if(fontType=="string")
							{

								value = value.split(" ");
								
								var count = value.length;
								var fontObj = {face: "", size: "", type: ""};
								
								if(count == 2) 
								{
									fontObj["face"] = value[1];
									fontObj["size"] = value[0];
									
								} else if(count == 3) {
									fontObj["face"] = value[2];
									fontObj["size"] = value[1];
									fontObj["type"] = value[0];
								}								
								
								value = fontObj;
							}
							
							if(Eco.isEmpty(value.face))
							{
								face = value._default_face;
								size = value._default_size * 1;
								type = value._default_type;
								//if(bandFont.type != "")	type = bandFont.type.toString();
							}
							else
							{
								face = value.face.toString();
								size = value.size * 1;
								if(Eco.isEmpty(value.type)){
									type = "";
									if(value._defalut_type != "")	type = value._defalut_type;								
								} else {
									type = value.type;
								}

							}
							
							valueObj["font"] = {face: face, size: size, type: type};					
							break;
										
						case "displaytype":
							//if(!value)
							if(value == "normal")
							{
								//Dataset bind 여부체크.
								text = grid.getCellProperty(band, cellIndex, "text");
								
								//trace("\t input cellIndex ???= " + cellIndex +", text= " + text);
								
								//bind가 되어 있으면 해당컬럼의 type 사용.
								if(text && (text.indexOf("bind:") > -1) ) 
								{
									dataset = Eco.XComp.lookup(this.scope, grid.binddataset);
									bindColumn  = text.replace("bind:","");
									columnInfo  = dataset.getColumnInfo(bindColumn);
									value = columnInfo.type;
								}
								else
								{
									//bind가 되지 않았으면 "STRING" 사용.
									value = "STRING";
								}
							
							}
							
							valueObj[propertyName] = value;
							
							break;
										
						case "color":
						
							//trace("[1]getPropertyValue color=" + value +", typeof="+ (typeof value) + ", bandColor="+bandColor);
							
							value = (!Eco.isEmpty(value))? value : bandColor; 
							value = value.toString();
							value = this.getNamedColorToHexColor(value);
							
							value = this._getHEXtoRGB(value);

							
							valueObj[propertyName] = value;
							break;
							
						case "background":
							if(i%2)
							{
								bandBackground = bandCellbackground2;
							}
							else
							{
								bandBackground = bandCellbackground;
							}
							
							//trace("band="+band + ",value="+value + ", bandBackground="+bandBackground);
							value = (!Eco.isEmpty(value))? value : bandBackground; 
							value = value.toString();

							if(value == "transparent" || value == "@gradation")
							{
								value = this.getGradationEndColor(value, gridBackground, grid, band, cellIndex);
							}
							
							value = this.getNamedColorToHexColor(value);
							value = this._getHEXtoRGB(value);
							
							valueObj[propertyName] = value;
							break;
							
						case "align":
							value = (!Eco.isEmpty(value))? value :  bandAlign["halign"] + " " + bandAlign["valign"];
							value = value.toString().split(" ");
							hAlign = value[0] || bandAlign["halign"];
							vAlign = value[1] || bandAlign["valign"];						
							valueObj["hAlign"] = hAlign;
							valueObj["vAlign"] = vAlign;
							
							break;
							
						case "line":
							if(Eco.isEmpty(value)) {
								value = bandCellLine;
							}
							
							value = this.getBorderToString(value, false);
							
							if(value == "")
							{
								value = this.getBorderToString(bandCellLine, true, bandBorder);
							}
							
							valueObj[propertyName] = value;
							
							break;					
							
						case "mask":
							if(!Eco.isEmpty(value)) {
								value = (typeof value == 'string')? value : value._value;

							} else {
								value = "";
							}
							
							valueObj[propertyName] = value;
							
							break;						
							
						default: 
							valueObj[propertyName] = value;
							break;
					}
					
					grid.setCellProperty(band, tempColumnIndex, "expr", "");
					
				} //inner loop.
				
				displayType = valueObj["displaytype"];
				font        = valueObj["font"];
				fontFace    = font.face;
				fontSize    = font.size;
				fontType    = font.type;
				color       = valueObj["color"];
				background  = valueObj["background"];
				hAlign      = valueObj["hAlign"];
				vAlign      = valueObj["vAlign"];
				mask        = valueObj["mask"];
				line        = valueObj["line"];
				
				
				
				var key = displayType + font.face + font.size + font.type 
				          + color + background + hAlign + vAlign + mask + line;
				          
				var seq = this.exportCellPropertyIndex[key];
				
				
				if(seq === undefined)
				{
					//seq = Eco.getSequenceId(this.scope, "cell_");
					seq = "cell_" + this.cellSeqId++;
					
					this.exportCellPropertyIndex[key] = seq;
					var info = { key: seq, displayType: displayType
					            ,font: font
								,color: color, background: background
								,hAlign: hAlign, vAlign: vAlign, mask: mask
								,line: line
							   };		
					
					this.exportCellPropertyData.push(info);
				}
				
				//trace("\t [getPropertyValue]  seq="+seq + "   , key="+key );
						  
				exprDs.setColumn(i, columnIndex, seq);		          
				
			} //last loop.

		};


		/**
		 * exportCellPropertyData에 
		 * band cell의 속성값들에 해당하는 key 값을 반환한다.
		 * @param {grid} grid grid component
		 * @param {string} band 얻고자 하는 Cell의 영역 입니다.
		 *                 'Head'/'Body'/'Summ'('Summary')를 지정합니다.
		 * @param {number} cellIndex 얻고자 하는 Cell의 Index 입니다.
		 * @param {boolean} isExportExpr cell속성의 expression 반영여부.
		 * @param {object} stylePackage 그리드 currentsytle 속성값들.
		 * @return {object} {key: seq} band cell의 속성key.
		 */
		_pExtExcelExport.getBandCellProperty = function(grid, band, cellIndex, isExportExpr, stylePackage)
		{
			//TODO cellbackground, cellbackground2 처리.
			var textRow = this.getBandToRow(band);
			var displayType, font, color, background
			var line = "", align, hAlign, vAlign, mask;	
			var existExpr, propertyName;
			
			band = band.toLowerCase();

			var bandName = "headcells";	
			if(band == "head") {
				bandName = "_headcells";
				
			} else if(band == "body") {
				bandName = "_bodycells";
				
			} else if(band == "summ") {
				bandName = "_summcells";
				
			} else {
				var msg = "band 명은 'head','body','summ'만 가능합니다.";
			
			    Eco.Logger.error({message:msg , stack:true});				
			}			
			
			//2014.11.07
			var cellInfo = grid._curFormat[bandName][cellIndex];
			
			
		    //expression 미적용시
			if(!isExportExpr){
				var bandFont = stylePackage.bandFont;
				var bandColor = stylePackage.bandColor;
				var bandCellLine = stylePackage.bandCellLine;
				var bandCellbackground  = stylePackage.bandCellbackground;
				var bandAlign = stylePackage.bandAlign;
				var bandBorder = stylePackage.bandBorder;
				var gridBackground = stylePackage.gridBackground;
					
				mask = grid.getCellProperty(band, cellIndex, "mask");
				mask = mask._value;
				if(Eco.isEmpty(mask)) mask = "";
				
				displayType = this.getDisplayType(grid, band, cellIndex);
				
				font = this.getFont(grid, band, cellIndex, bandFont);
				
				color = grid.getCellProperty(band, cellIndex, "color");
				
//				if(typeof(color) == 'string') 
//				{
//					Eco.Logger.error({"message":"expr 미사용 설정에서 다음과 같은 color 정보가 존재합니다. " + color, "stack":false});
//				}		
				
				color = (!Eco.isEmpty(color))? color : bandColor.value; 
				color = color.toString();
				color = this.getNamedColorToHexColor(color);
				color = this._getHEXtoRGB(color);
				
				background = grid.getCellProperty(band, cellIndex, "background");
				
//				if(typeof(background) == 'string') 
//				{
//					Eco.Logger.error({"message":"expr 미사용 설정에서 다음과 같은 background 정보가 존재합니다. " + background, "stack":false});
//				}
				
				
				if(Eco.isEmpty(background)){
					background = cellInfo._query_pseudo_background(cellIndex, false, false, "normal");
				}
				
				
				background = (background != "")? background : bandCellbackground; 
				
				
				if(typeof(background) == 'object'){
					background = background._value;
				} else {
					background = background.toString();
				}
				
				
				if((background.indexOf("transparent") > -1) || (background.indexOf("@gradation") > -1))
				{
					background = this.getGradationEndColor(background, gridBackground, grid, band, cellIndex);
				}
				
				background = this.getNamedColorToHexColor(background);
				background = this._getHEXtoRGB(background);
				
				align = grid.getCellProperty(band, cellIndex, "align");
				
				if(Eco.isEmpty(align)){
					align = cellInfo._query_pseudo_align(cellIndex, displayType, "normal");
				}				
				
				align = (!Eco.isEmpty(align))? align :  bandAlign["halign"] + " " + bandAlign["valign"];
				align = align.toString().split(" ");
				hAlign = align[0] || bandAlign["halign"];
				vAlign = align[1] || bandAlign["valign"];						
			
				
				//trace("hAlign="+hAlign + ",vAlign="+vAlign);
					
				line = grid.getCellProperty(band, cellIndex, "line");
				
				if(Eco.isEmpty(line)){
					line = cellInfo._query_pseudo_border(cellIndex, false, "normal", 0);
				}			
				
				
				line = this.getBorderToString(line.toString(), false);
				
				
			} 
			else if(isExportExpr) //expression 적용시
			{
		 		var bandFont = stylePackage.bandFont;
		 		var bandColor = stylePackage.bandColor;
		 		var bandCellLine = stylePackage.bandCellLine;
		 		var bandBackground = stylePackage.bandBackground;
		 		var bandAlign = stylePackage.bandAlign;
		 		var bandBorder = stylePackage.bandBorder;
		 		var gridBackground = stylePackage.gridBackground;
			
				propertyName = "mask";
				mask = grid.getCellProperty(band, cellIndex, propertyName);
				mask = mask._value;
				mask = this.getCellExprValue(grid, band, cellIndex, propertyName, mask);
				
				if(Eco.isEmpty(mask)) mask = "";
				
				//displayType = this.getDisplayType(grid, band, cellIndex, isExportExpr);
				displayType = cellInfo.displaytype._value;

				font = this.getFont(grid, band, cellIndex, bandFont, isExportExpr);
				
				propertyName = "color";
				color = grid.getCellProperty(band, cellIndex, propertyName);
				
				//if(Eco.isEmpty(color))		trace("\n\n 1 getBandCellProperty band="+band + ", color="+color);
				//trace("\n\n 1 getBandCellProperty band="+band + ", color="+color);
				
				if(Eco.isEmpty(color)){
					color = cellInfo._query_pseudo_color(cellIndex, false, false, "normal");
				}
				
				color = this.getCellExprValue(grid, band, cellIndex, propertyName, color);
				color = (color != "")? color : bandColor; 
				color = color.toString();
				color = this.getNamedColorToHexColor(color);
				color = this._getHEXtoRGB(color);
				
				propertyName = "background";
				background = grid.getCellProperty(band, cellIndex, propertyName);
				
				//trace("\n\n ## band 1="+band + ",background="+background);
				
				if(Eco.isEmpty(background)){
					background = cellInfo._query_pseudo_background(cellIndex, false, false, "normal");
				}
				
				//trace("## band 2="+band + ",background="+background);
				background = this.getCellExprValue(grid, band, cellIndex, propertyName, background);

				//trace("## band 3="+band + ",background="+background);
				background = (background != "")? background : bandBackground;
				
				//trace("## band 4="+band + ",background="+ background  + ",typeof(background)="+ typeof(background));
				if(typeof background == 'object') {
					background = background._value;
					//trace("## band 4-1="+band + ",background._value="+background);
				} else {
					background = background.toString();
				}			
				 
				 
				//trace("## band 5="+band + ",background="+background); 
				
				//background = background.toString();
				//2014.11.12 RUNTIME버전에서 background._value가 "transparent left top" 반환되는 버그 대응.
				if(background.indexOf("transparent") > -1) background = "transparent";
				
				if(background == "transparent" || background == "@gradation")
				{
					background = this.getGradationEndColor(background, gridBackground, grid, band, cellIndex);
				}
				
				//trace("## band 6="+band + ",background="+background);
				
				background = this.getNamedColorToHexColor(background);
				background = this._getHEXtoRGB(background);
				
				//trace("## band 7="+band + ",background="+background);
				
				propertyName = "align";
				align = grid.getCellProperty(band, cellIndex, propertyName);
				
				if(Eco.isEmpty(align)){
					align = cellInfo._query_pseudo_align(cellIndex, displayType, "normal");
				}			
				
				align = this.getCellExprValue(grid, band, cellIndex, propertyName, align);
				align = (align != "")? align :  bandAlign["halign"] + " " + bandAlign["valign"];
				
				if(typeof align == 'string'){
					align = align.split(" ");
					hAlign = align[0] || bandAlign["halign"];
					vAlign = align[1] || bandAlign["valign"];				
				} else {
					hAlign = align.halign;
					vAlign = align.valign;				
				}
				
				propertyName = "line";
				line = grid.getCellProperty(band, cellIndex, propertyName);
				
				if(Eco.isEmpty(line)){
					line = cellInfo._query_pseudo_border(cellIndex, false, "normal", 0);
				}			
				
				line = this.getCellExprValue(grid, band, cellIndex, propertyName, line);
				
				line = this.getBorderToString(line, false);

				
			}
			
			var key = displayType + font.face + font.size + font.type + color + background + hAlign + vAlign + mask + line;
			var seq = this.exportCellPropertyIndex[key];
			
			//trace("\t <@ getBandCellProperty band="+band+", key="+key);
			
			if(seq === undefined)
			{
				//seq = Eco.getSequenceId(this.scope, band + "_");
				var bandSeq = this[band + "SeqId"]++;
				seq = (band + "_") + bandSeq;
				
				trace("******** bandSeq="+bandSeq + ", seq=" + seq);
				
				this.exportCellPropertyIndex[key] = seq;
				var info = { key: seq, displayType: displayType, font: font
						    ,color: color, background: background
						    ,hAlign: hAlign, vAlign: vAlign, mask: mask 
						    ,line: line
					       };		
				
				this.exportCellPropertyData.push(info);
			}
			
			return {key: seq};
		};
		

		/**
		 * gradation의 end color 반환.
		 * @param {string} value 현재값
		 * @param {string} defaultValue 현재값이 빈값일때 대체값
		 * @param {grid} grid grid component
		 * @param {string} band 얻고자 하는 Cell의 영역 입니다.
		 *                 'Head'/'Body'/'Summ'('Summary')를 지정합니다.
		 * @param {number} cellIndex 얻고자 하는 Cell의 Index 입니다.
		 * @return {string} gradtion end color.
		 */
		_pExtExcelExport.getGradationEndColor = function(value, defaultValue, grid, band, cellIndex)
		{
			var tmpBand = grid["_" + band + "Band"];
			
			//"transparent left top"와 같은 데이터 처리용.
			if(value.indexOf("transparent") > -1) {
				
				value = tmpBand.on_find_CurrentStyle_background("normal");
				
				//trace("\n\n getGradationEndColor value="+ value + ", typeof=" + typeof(value));
				
				//RGB to Hex
				if(typeof value == 'object'){
					//trace("    getGradationEndColor value._value="+ value._value + ",typeof(value)=" + typeof(value));
					//2014.11.12 RUNTIME버전에서 background._value가 "transparent left top" 반환되는 버그 대응.
					if( (value._value.indexOf("@gradation") > -1) || value._value == "@gradation") {
				
					//if(value._value == "@gradation") {
						value = tmpBand.on_find_CurrentStyle_gradation("normal");
						
						//trace(" @@@   getGradationEndColor() value="+value);;
						var start_color = value.start_color;
						var end_color = value.end_color;
						var sRGB = Eco.Color.hexToRgb(start_color);
						var eRGB = Eco.Color.hexToRgb(end_color);
						
						//대략적인 중간값 계산
						var newR = (sRGB[0] + eRGB[0]) / 2;
						var newG = (sRGB[1] + eRGB[1]) / 2;
						var newB = (sRGB[2] + eRGB[2]) / 2;					
						
						newR = Math.ceil(newR), newG = Math.ceil(newG), newB = Math.ceil(newB);
						value = Eco.Color.rgbToHex(newR, newG, newB);
					}				
				}

				
			} else if(value.indexOf("@gradation") > -1) {
				
				var bandName = "headcells";	
				if(band == "head") {
					bandName = "_headcells";
					
				} else if(band == "body") {
					bandName = "_bodycells";
					
				} else if(band == "summ") {
					bandName = "_summcells";
					
				} else {
					var msg = "band 명은 'head','body','summ'만 가능합니다.";
				
				    Eco.Logger.error({message:msg , stack:true});				
				}
				
				var cellInfo = grid._curFormat[bandName][cellIndex];
		
				var strPropID = "gradation";
	            var prop = cellInfo[strPropID];
	            if (prop == undefined)  prop = cellInfo["_" + strPropID];
	            
	            if (prop == undefined)  prop = cellInfo.style[strPropID];			
				
				
	            if (prop != undefined)
	            {
	                var type = typeof (prop);

	                if(type == 'object') {
	        			var start_color = prop.start_color;
	        			var end_color = prop.end_color;
	        			var sRGB = Eco.Color.hexToRgb(start_color);
	        			var eRGB = Eco.Color.hexToRgb(end_color);
	        			
	        			//대략적인 중간값 계산
	        			var newR = (sRGB[0] + eRGB[0]) / 2;
	        			var newG = (sRGB[1] + eRGB[1]) / 2;
	        			var newB = (sRGB[2] + eRGB[2]) / 2;
	        			
	        			newR = Math.ceil(newR), newG = Math.ceil(newG), newB = Math.ceil(newB);
	        			value = Eco.Color.rgbToHex(newR, newG, newB);        			
	                }
	            }   
	            
		     }
			
			return value;    
		
		};	
	                



		/**
		 * cell의 속성값이 expr이 적용되었을 경우,
		 * expr이 적용된 cell의 속성값을 반환한다.
		 * @param {grid} grid grid component
		 * @param {string} band 얻고자 하는 Cell의 영역 입니다.
		 *                 'Head'/'Body'/'Summ'('Summary')를 지정합니다.
		 * @param {number} cellIndex 얻고자 하는 Cell의 Index 입니다.
		 * @param {string} propertyName cell 속성명.
		 * @param {string} exprString expression string.
		 * @return {object} expr이 적용된 결과값.
		 */
		_pExtExcelExport.getCellExprValue = function(grid, band, cellIndex, propertyName, value)
		{
			var textRow = this.getBandToRow(band);
			
			if(this.isExpr(value))
			{
				var exprValue = value;
				if(value.indexOf("EXPR(") > -1 ) {
					value = value.replace("EXPR(", "");
					value = value.substring(0, value.length -1);
					value = "expr:" + value;
				}
				
				var tempColumnIndex = grid._exportExprColumn;
				
				//expr 결과값을 얻기위해 "expr"에 설정 후 값을 얻는다.
				grid.setCellProperty(band, tempColumnIndex, "expr", value);
				
				value = grid.getCellText(textRow, tempColumnIndex);

			}		
			
			return value;
		};


		/**
		 * Band 정보를 Row정보(셀의 행 인덱스)로 반환한다.
		 * @param {string} band 'Head'/'Body'/'Summ'('Summary')를 지정합니다.
		 * @return {number} band에 해당하는 number
		 */
		_pExtExcelExport.getBandToRow = function(band)
		{
			return (band == "body")? 0 : ((band=="head") ? -1 : -2);
		};
		
		

		/**
		 * Display Type 반환.
		 *     'normal' 은 Display 되는 Data에 따라 다르게 표현됩니다.
		 * @param {grid} grid grid component
		 * @param {string} band 얻고자 하는 Cell의 영역 입니다.
		 *                 'Head'/'Body'/'Summ'('Summary')를 지정합니다.
		 * @param {number} cellIndex 얻고자 하는 Cell의 Index 입니다.
		 * @param {boolean} isExportExpr cell속성의 expression 반영여부.
		 * @return {string} displaytype
		 */
		_pExtExcelExport.getDisplayType = function(grid, band, cellIndex, isExportExpr)	
		{
			//binddataset 가져오기
			//cell의 bindColumn 가져오기
			//displayType이 normal이면 undefined 반환됨.
			var dataset, displayType, text, bindColumn
			    ,columnInfo;
			
			dataset = Eco.XComp.lookup(this, grid.binddataset);
			displayType = grid.getCellProperty(band, cellIndex, "displaytype");
			if(isExportExpr) displayType = this.getCellExprValue(grid, band, cellIndex, "displaytype", displayType);
			
			if(displayType === undefined)
			{
				//Dataset bind 여부체크.
				text = grid.getCellProperty(band, cellIndex, "text");
				
				//bind가 되어 있으면 해당컬럼의 type 사용.
				if(text && (text.indexOf("bind:") > -1) ) 
				{
					bindColumn  = text.replace("bind:","");
					columnInfo  = dataset.getColumnInfo(bindColumn);
					displayType = columnInfo.type;
				}
				else
				{
					//bind가 되지 않았으면 "STRING" 사용.
					displayType = "STRING";
				}
			
			}
			
			return displayType;
		};




		/**
		 * font 정보반환
		 * font확인 절차....cell -> band.
		 * Grid.getBandProperty와 getCellProperty에서는 font,fontcolor등의 style정보 획득안됨.
		 * getBandCurrentStyle() 사용
		 *
		 * ※특이사항
		 * [font] face: object, size: object, type: object 으로 XPLATFORM에서 반환.
		 *
		 * @param {grid} grid grid component
		 * @param {string} band 얻고자 하는 Cell의 영역 입니다.
		 *                 'Head'/'Body'/'Summ'('Summary')를 지정합니다.
		 * @param {number} cellIndex 얻고자 하는 Cell의 Index 입니다.
		 * @param {object} bandFont band영역 font object(cell영역 font가 없을때 사용).
		 * @param {boolean} isExportExpr cell속성의 expression 반영여부.
		 * @return {object} font font object
		 */
		_pExtExcelExport.getFont = function(grid, band, cellIndex, bandFont, isExportExpr)	
		{
			band = band.toLowerCase();
			var face, size, type;
			var newFont;
			var tempFont;
			
			tempFont = grid.getCellProperty(band, cellIndex, "font");
			
			//2014.11.07 해당 cell에 직접정의된 값이 없을 경우 
			if(Eco.isEmpty(tempFont))
			{
				//그리드의 현재 format에서 normal pseudo 값으로 사용한다.
				var formatid = grid.formatid;
				var bandName = "headcells";
				var tempBand = band.toLowerCase();
				if(tempBand == "head") {
					bandName = "_headcells";
					
				} else if(tempBand == "body") {
					bandName = "_bodycells";
					
				} else if(tempBand == "summ") {
					bandName = "_summcells";
					
				} else {
					var msg = "band 명은 'head','body','summ'만 가능합니다.";
				
				    Eco.Logger.error({message:msg , stack:true});				
				}			
				
				var cellInfo = grid._curFormat[bandName][cellIndex];
				tempFont = cellInfo._query_pseudo_font(cellIndex, false, "normal");
			}
			
			if(isExportExpr) 
			{
				var fontValue = this.getCellExprValue(grid, band, cellIndex, "font", tempFont);
				var fontType = typeof(fontValue);

				if(fontType=="object") 
				{
					tempFont = fontValue;
				}
				else if(fontType=="string")
				{
					fontValue = fontValue.split(" ");
					var count = fontValue.length;
					var fontObj = {face: "", size: "", type: ""};

					//nexacro 리턴값 "bold 9 Verdana"
					if(count == 2) {
						fontObj["face"] = fontValue[1];
						fontObj["size"] = fontValue[0];
						
					} else if(count == 3) {
						fontObj["face"] = fontValue[2];
						fontObj["size"] = fontValue[1];
						fontObj["type"] = fontValue[0];
					}
					
					tempFont = fontObj;
					
				} 		
			}
			else if(!isExportExpr && typeof(tempFont) == 'string') 
			{
				//Eco.Logger.error({"message":"expr 미사용 설정에서 다음과 같은 font정보가 존재합니다. " + tempFont, "stack":false});
				fontValue = tempFont.split(" ");
				var count = fontValue.length;
				var fontObj = {face: "", size: "", type: ""};

				//nexacro 리턴값 "bold 9 Verdana"
				if(count == 2) {
					fontObj["face"] = fontValue[1];
					fontObj["size"] = fontValue[0];
					
				} else if(count == 3) {
					fontObj["face"] = fontValue[2];
					fontObj["size"] = fontValue[1];
					fontObj["type"] = fontValue[0];
				}
				
				tempFont = fontObj;			
			}

			if(tempFont.face == "")
			{
				face = bandFont.face.toString();
				size = bandFont.size * 1;
				type = "";
				if(bandFont.type != "")	type = bandFont.type.toString();
			}
			else
			{
				face = tempFont.face.toString();
				size = tempFont.size * 1;
				type = "";
				if(tempFont.type != "")	type = tempFont.type.toString();
			}
			
			newFont = {face: face, size: size, type: type};

			return newFont;
		};




		/**
		 * 그리드 currentsytle 속성값들의 재사용을 목적으로
		 * 속성값들을 객체로 반환한다.
		 *
		 * @param {grid} grid grid component
		 * @param {string} band 얻고자 하는 Cell의 영역 입니다.
		 *                 'Head'/'Body'/'Summ'('Summary')를 지정합니다.
		 * @return {object}  currentsytle 속성값들.
		 */
		_pExtExcelExport.getGridStylePackage = function(grid, band)	
		{
			trace("==getGridStylePackage==");
			var info;
			var bandFont = this.getBandCurrentStyle(grid, band, "cellfont");
			var bandColor = this.getBandCurrentStyle(grid, band, "cellcolor");
			var bandCellLine = this.getBandCurrentStyle(grid, band, "cellline");
			
			var bandBackground = this.getBandCurrentStyle(grid, band, "background");
			var bandCellbackground  = this.getBandCurrentStyle(grid, band, "cellbackground");
			
			var bandCellbackground2  = this.getBandCurrentStyle(grid, band, "cellbackground2");
			var bandAlign = this.getBandCurrentStyle(grid, band, "cellalign");
			
			if(Eco.isEmpty(bandAlign)) bandAlign = {halign: this.defaultHorizontalAlign, valign: this.defaultVerticalAlign};
			
			var bandBorder = this.getBandCurrentStyle(grid, band, "border");	
			
			if(Eco.isEmpty(bandCellbackground))
			{
				bandCellbackground = bandBackground;
			}
			else if(Eco.isEmpty(bandCellbackground2))
			{
				bandCellbackground2 = bandCellbackground;
			}		
				
			var currentStyle = grid.currentstyle;
			var gridBackground = currentStyle["background"];
			
			gridBackground = gridBackground.toString();
			
			gridBackground = (gridBackground != "")? gridBackground : this.defaultGridBackground;
			
			////bandColor = this._getHEXtoRGB(bandColor);
			////
			////bandBackground = this._getHEXtoRGB(bandBackground);
			////
			////bandCellbackground = this._getHEXtoRGB(bandCellbackground);
			////
			////bandCellbackground2 = this._getHEXtoRGB(bandCellbackground2);
			////
			////gridBackground = this._getHEXtoRGB(gridBackground);
			
			info = {bandFont: bandFont
			        , bandColor: bandColor
			        , bandCellLine: bandCellLine
			        , bandBackground: bandBackground
			        , bandCellbackground: bandCellbackground
			        , bandCellbackground2: bandCellbackground2
			        , bandAlign: bandAlign
			        , bandBorder: bandBorder
			        , gridBackground: gridBackground
			       };
			       
			return info;
		};


		/**
		 * Band currentsytle에서 해당 속성값 반환
		 * Grid.getBandProperty와 getCellProperty에서는 font,fontcolor등의 style정보 획득이 안됨.
		 *
		 * @param {grid} grid grid component
		 * @param {string} band 얻고자 하는 Cell의 영역 입니다.
		 *                 'Head'/'Body'/'Summ'('Summary')를 지정합니다.
		 * @param {string} propName 얻고자 하는 Band의 Property 명 입니다.
		 * @return {*}  Band의 Property 값 입니다.
		 */
		_pExtExcelExport.getBandCurrentStyle = function(grid, band, propName)	
		{
//			trace("getBandCurrentStyle grid=" + grid.name);
//			trace(grid[band]["currentstyle"] + ", band="+band + ", propName=" + propName);
			
			var bandObj = grid[band];

			//head나 summ이 없을 경우
			if(Eco.isEmpty(bandObj)) { 
				return "";
			}
			
			var value = bandObj["style"][propName];
			
			if(Eco.isEmpty(value)) {
				var ref_finder = bandObj.bandctrl._ref_css_finder;
				
				if(!ref_finder["normal"]){
		            return null;
					
				} else if(ref_finder["normal"][propName]){
					return ref_finder["normal"][propName];
					
				} else {
					return null;
					
				}			
	 			
			}
				
			return value;		
		};


		/**
		 * 속성값에 expr 사용여부 반환
		 * @param {*} value 확인할 대상
		 * @return {boolean} expr 사용유무.
		 */
		_pExtExcelExport.isExpr = function(value)
		{
			if(typeof value === "string") 
			{
				var checkString = value.substring(0,5);
				if(checkString == "expr:" || checkString == "EXPR(") 
				{
					return true;
				}
			}
			
			return false;
		};

		if (nexacro.Browser != "Runtime")
		{
			/**
			 * named color일 경우 hex color로 변환 후 반환.
			 * @param {string} value color 문자열.
			 * @return {string} hex color.
			 */
			_pExtExcelExport.getNamedColorToHexColor = function(value)
			 {
				//transparent는 object
				
				
				
				if(typeof(value) == 'object') value = "white";
					
			
				if(!Eco.string.startsWith(value, "#") )
				{
					value = nexacro._getWebColorFromXreColor(value);
				} 
				
				//trace("\n getNamedColorToHexColor value=" + value);
				
				return value;
			 };
		}
		else if (nexacro.Browser == "Runtime")
		{
			/**
			 * named color일 경우 hex color로 변환 후 반환.
			 * @param {string} value color 문자열.
			 * @return {string} hex color.
			 */
			_pExtExcelExport.getNamedColorToHexColor = function(value)
			 {
				//transparent는 object
				
				if(typeof(value) == 'object') value = "white";
					
			
				if(!Eco.string.startsWith(value, "#") )
				{
					//2014.11.12.1 런타임버전에서는 _getWebColorFromXreColor에서 리턴값이 number. (color=#FF0000FF, 변환=4278190335)
					value = nexacro._xreNamedColorList[value];
					value = (!Eco.isEmpty(value))? value: "white";
				} 
				
				//trace("\n getNamedColorToHexColor value=" + value);
				
				return value;
			 };		
		}
		
		
		
		_pExtExcelExport._getHEXtoRGB = function (color)
		{
			var rgb;
			if (typeof color == "object")
			{
				////trace(">>>> color <<<< " + color.toString());
				////2014.11.12 RUNTIME버전에서 color가 object type 일때 null이 올 경우 존재.
				//if(Eco.isEmpty(color))
				//{
				//	//표시를 위해 "black"로 설정.
				//	rgb = this._getHexColor("red");
				//}
				//else
				//{
				//	rgb = this._getHexColor(color._value.split(" ")[0]);
				//}
				
				rgb = this._getHexColor(color._value.split(" ")[0]);
				
			}
			else
			{
				////////2014.11.12 RUNTIME버전에서 color가 object type이 아닐때 "#ffffffff left top" 반환되는 버그 대응.
				////////[원본] rgb = this._getHexColor(color);
				////////trace("color="+color);
				//if(Eco.isEmpty(color))
				//{
				//	color = subColor || this.defaultColor;
				//}
					
				rgb = this._getHexColor(color.split(" ")[0]);
				
			}
			
			var style_a = [];

			if (rgb === "")
			{
				rgb = color._value;
			}
			else
			{
				if (rgb.indexOf("#") > -1)
				{
					style_a.push(parseInt(rgb.substring(1, 3), 16));
					style_a.push(parseInt(rgb.substring(3, 5), 16));
					style_a.push(parseInt(rgb.substring(5, 7), 16));
					rgb = style_a.join(",");
				}
				else
				{
					var start = rgb.indexOf("(");
					var end = rgb.indexOf(")");
					style_a = rgb.substring(start + 1, end - 1).split(",");
					style_a.pop();
					rgb = style_a.join(",");
				}
			}
			return rgb;
		};		
		
		
		
		_pExtExcelExport._getHexColor = function(color)
		{
			var v = nexacro._xreNamedColorList[color];
			if (v) 
			{
				return v;
			}

			len = color.length;
			if(color.substring(0,1) == '#')
			{
				if (len == 7) 
				{
					return color;
				}
				if (len == 9)
				{
					return color.substr(0, 7);
				}
			}
			if(color.substring(0,2) == "0x")
			{
				if (len == 8) 
				{
					return "#" + color.substring(2);
				}
				if (len == 10)
				{
					return "#" + color.substring(2, 8);
				}
			}
			return "";
		};		
		

		/**
		 * border 객체값을 string 형태로 반환.
		 * 공백을 "_(underscore)"로 치환한다.
		 * [주의]
		 * css에 적용된 band의 기본 색상값은 읽어오지 못한다????
		 * @param {object} value border object.
		 * @param {boolean} applyDefaultValue default value 적용여부.
		 * @param {object} bandCellLine Band.style.cellline.
		 * @param {object} bandBorderStyle Band.style.border.
		 * @return {string} string 형태의 border값.
		 *                  예)  1_solid_#808080ff,1_solid_#808080ff,1_solid_#808080ff,1_solid_#808080ff
		 */
		 _pExtExcelExport.getBorderToString = function(value, applyDefaultValue, bandBorder)
		{
			if(Eco.isEmpty(applyDefaultValue)) applyDefaultValue = false;
			
			if(typeof(value) == 'string')
			{
				var strArray = value.split(",");
				var len = strArray.length;
				var temp = [];
				var replacedColor = this.transparentReplacedColor;
				
				for(var i=0; i < len; i++){
					var eachArray = strArray[i];

					//rtrim
					var res = eachArray.trim().replace(/~+$/,"");
					//ltrim
					res = eachArray.trim().replace(/^\s+/,"");
					
					//grid left의 값에 type이 없는 경우 처리.0px transparent.
					res = res.replace(/transparent/g, replacedColor);
					             
					temp.push(res);
				}
				
				value = temp.join(",");
				
				value = value.trim().replace(/\s/g,'_');
				
				
				//String으로 아래와 같이 입력시 처리로직.
				//  "1px solid #808077ff,1px solid #808080ff 0px solid #808080ff,1px solid #808080ff"
				//1. ,로 분리 후 space로 분리
				// 2. [width type hexacode]
				if(value.indexOf("," > -1)) {
					var arrValue = value.split(",");
					var size = arrValue.length;
					var copyValue = "";
					var currValue = "";
					var borderWidth,borderStyle,borderColor;
				
					for(var j=0; j<size; j++) {
						var temp = arrValue[j].split("_");
						borderWidth = temp[0];
						borderStyle = temp[1];
						borderColor = temp[2];
					
						//border가 0일때 color 값은 white로 처리.
						if(Eco.isEmpty(borderColor)) borderColor = "#ffffff";
						
						borderColor = this._getHEXtoRGB(borderColor);
						
						currValue = borderWidth + "_" + borderStyle + "_" + borderColor;

						
						if(!copyValue)
						{
							copyValue += currValue;
						}
						else
						{
							copyValue += "-" + currValue;
						}						
					}
					
					value = copyValue;
					value = value.trim();
				
				} else {
					value = this._getHEXtoRGB(value);
					
				}

				
			}
			else if(typeof(value) == 'object')
			{
				
				var tempName = ["top","right", "bottom", "left"];
				var copyValue = "";
				var currValue = "";
				var borderWidth,borderStyle,borderColor;
				
				for(var m=0; m<4; m++) {
					var prefix = tempName[m];
					//_left_width가 0일때 left_width로 접근하면 ""이 반환되서 
					// width 관련부분만 "_"접근자 사용. 2014.11.10
					borderWidth = value["_" + prefix + "_width"];
					borderStyle = value[prefix + "_style"];
					borderColor = value[prefix + "_color"];

					//border가 0일때 color 값은 white로 처리.
					if(Eco.isEmpty(borderColor)) borderColor = "#ffffff";
					
					borderColor = this._getHEXtoRGB(borderColor);
					
					currValue = borderWidth + "_" + borderStyle + "_" + borderColor;

					
					if(!copyValue)
					{
						copyValue += currValue;
					}
					else
					{
						copyValue += "-" + currValue;
					}
				}
				
				value = copyValue;
				value = value.trim();			

			}
			
			return value;
		};
		
		
		delete _pExtExcelExport;		
		
	
};





