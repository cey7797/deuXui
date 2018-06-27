if ( nexacro._init_platform_runtime ) //Runtime
{
	Eco._getLines = function(nexacroFont, txt, wordwrap, maxWidth)
	{
		var face = nexacroFont.face,
			fontSize = nexacroFont.size,
			antialias = nexacroFont._antialias,
			bold = nexacroFont._bold,
			italic = nexacroFont._italic,
			underline = nexacroFont._underline,
			strikeout = nexacroFont._strikeout;
		var lines,
			txtsWidth = 0;
		if ( wordwrap )
		{
			var fsz = nexacroFont.size;
			var sizeForOneChar = ( sz >= 0 ? nexacro._convertPtToPx(fsz) : Math.abs(fsz) );
			if ( sizeForOneChar >= maxWidth )
			{
				return txt.split("");
			}
			var sz = nexacro.__getTextSize(txt, face, fontSize, antialias, bold, italic, underline, strikeout, false);
			if ( sz[0] > maxWidth)
			{
				lines = [];
				var tmpLines = txt.split("\n"),
					line, words, wordSize,
					lineSize, testLine = "",
					reallines = [];
				for ( var i = 0, len = tmpLines.length ; i < len ; i++ )
				{
					line = tmpLines[i];
					lineSize = nexacro.__getTextSize(line, face, fontSize, antialias, bold, italic, underline, strikeout, false);
					if ( lineSize[0] > maxWidth )
					{
						lines = lines.concat(this._fragmentText(face, fontSize, antialias, bold, italic, underline, strikeout, line, maxWidth));
					}
					else
					{
						lines.push(line);
					}
				}
			}
			else
			{
				lines = txt.split("\n");
			}
		}
		else
		{
			lines = txt.split("\n");
		}
		return lines;
	}

	Eco._fragmentText = function(face, fontSize, antialias, bold, italic, underline, strikeout, text, maxWidth)
	{
		var words = text.split(' '),
			lines = [],
			line = "";

		for ( var n = 0, len = words.length ; n < len ; n++ )
		{
			var tmpLine = line + words[n] + ' ';
			if ( n > 0 && (nexacro.__getTextSize(tmpLine, face, fontSize, antialias, bold, italic, underline, strikeout, false))[0] > maxWidth )
			{
				var tmpChars = line.split(""), tmpstr = "";
				for ( var c = 0, clen = tmpChars.length ; c < clen ; c++ )
				{
					var tmpLine2 = tmpstr + tmpChars[c];
					if ( c > 0  && (nexacro.__getTextSize(tmpLine2, face, fontSize, antialias, bold, italic, underline, strikeout, false))[0] > maxWidth )
					{
						lines.push(tmpstr);
						tmpstr = tmpChars[c];
					}
					else
					{
						tmpstr = tmpLine2;
					}
				}				
				line = tmpstr + words[n] + ' ';
			}
			else
			{
				line = tmpLine;
			}
		}
		if ( (nexacro.__getTextSize(line, face, fontSize, antialias, bold, italic, underline, strikeout, false))[0] > maxWidth )
		{
			var tmpChars = line.split(""), tmpstr = "";
			for ( var c = 0, clen = tmpChars.length ; c < clen ; c++ )
			{
				var tmpLine2 = tmpstr + tmpChars[c];
				if ( c > 0  && (nexacro.__getTextSize(tmpLine2, face, fontSize, antialias, bold, italic, underline, strikeout, false))[0] > maxWidth )
				{
					lines.push(tmpstr);
					tmpstr = tmpChars[c];
				}
				else
				{
					tmpstr = tmpLine2;
				}
			}
			if ( tmpstr.length )
			{
				lines.push(tmpstr);
			}
		}
		else if ( line.length )
		{
			lines.push(line);
		}
		return lines;
	}

	Eco._getTextSize = function(nexacroFont, lines, fontHeight)
	{
		var face = nexacroFont.face,
			fontSize = nexacroFont.size,
			antialias = nexacroFont._antialias,
			bold = nexacroFont._bold,
			italic = nexacroFont._italic,
			underline = nexacroFont._underline,
			strikeout = nexacroFont._strikeout;

		var w = 0,
			len = lines.length,
			h = fontHeight*len;
		for ( var i = 0 ; i < len ; i++ )
		{
			w = Math.max(w, (nexacro.__getTextSize(lines[i], face, fontSize, antialias, bold, italic, underline, strikeout, false))[0]);
		}
		return [w, h];
	}

	Eco._updateFontInCanvas = function(nexacroFont, sys_style)
	{
		if ( nexacroFont )
		{
			sys_style._font = nexacroFont;
		}
		else
		{
			sys_style._font = null;
		}
	}

	var pCanvasElement = nexacro.CanvasElement.prototype;

	// override
	pCanvasElement.fillText = function (text, x, y, maxwidth)
	{
		var _handle = this._handle;
		if (_handle)
		{
			nexacro.__fillCanvasElementHandleText(_handle, text, x, y, maxwidth);
		}
	};

	pCanvasElement._setFont = function(font)
	{
		this.setElementFont(font);
	}

	pCanvasElement._applystyles = function(style)
	{
		if ( !style ) return;
		var _handle = this._handle;
		if ( _handle )
		{
			var width = style._width,
				join = style._strokejoin,
				cap = style._strokecap,
				limit = style._miterlimit,
				fillColor = style._fillcolor,
				strokeColor = style._strokecolor,
				dashArray = style._dasharr,
				dashOffset = style._dashoffset,
				font = style._font;

			if (width != null)
			{
				nexacro.__setCanvasElementHandleLineWidth(_handle, width);
			}
			if (join)
			{
				nexacro.__setCanvasElementHandleLineJoin(_handle, join);
			}
			if (cap)
			{
				 nexacro.__setCanvasElementHandleLineCap(_handle, cap);
			}

			if (limit)
			{
				nexacro.__setCanvasElementHandleMiterLimit(_handle, limit);
			}

			if (font)
			{
				nexacro.__setCanvasElementHandleFont(_handle, font.face, font.size, font._bold, font._italic, font._underline, font._strikeout, font._antialias);
			}

			if (fillColor)
			{
				if ( fillColor.style )
				{
                    nexacro.__setCanvasFillGradation(_handle, fillColor.style, fillColor.startx, fillColor.starty, fillColor.startcolor,
													        fillColor.endx, fillColor.endy, fillColor.endcolor, fillColor.sysvalue);
				}
				else
				{
					nexacro.__setCanvasElementHandleFillColor(_handle, fillColor);
				}
			}
			if (strokeColor)
			{
				nexacro.__setCanvasElementHandleStrokeColor(_handle, strokeColor);
				// runtime는 line dash 기능이 존재하지 않아 제외함.
				/*
				if ( dashArray && dashArray.length)
				{
					if ('setLineDash' in ctx)
					{
						ctx.setLineDash(dashArray);
						ctx.lineDashOffset = dashOffset;
					}
					else if ('mozDash' in ctx)
					{
						ctx.mozDash = dashArray;
						ctx.mozDashOffset = dashOffset;
					}
				}
				*/
			}
		}
	}

	pCanvasElement._updateStrokePen = function(strokePenObj, sys_style)
	{
		if ( strokePenObj )
		{
			var canvasval = strokePenObj._syscanvasval;
			if ( !canvasval )
			{
				var substyle = strokePenObj.style;
				canvasval = {};
				if ( substyle == "none" )
				{
					canvasval._width = null
					canvasval._strokecolor = null
				}
				else if ( substyle == "solid" )
				{
					canvasval._width = strokePenObj.width;
					canvasval._strokecolor = (strokePenObj._syscolor||nexacro._getWebColorFromXreColor(strokePenObj.color));
					canvasval._dasharr = null;
					canvasval._dashoffset = null;
				}
				else
				{
					canvasval._width = strokePenObj.width;
					canvasval._strokecolor = (strokePenObj._syscolor||nexacro._getWebColorFromXreColor(strokePenObj.color));
					switch ( substyle )
					{
						case "dotted":
							canvasval._dasharr = [1,2];
							canvasval._dashoffset = 2;
							break;
						case "dashed":
							canvasval._dasharr = [4,2];
							canvasval._dashoffset = 2;
							break;
						default:
							canvasval._dasharr = null;
							canvasval._dashoffset = null;
							break;
					}
				}
				strokePenObj._syscanvasval = canvasval;
			}
			sys_style._width = canvasval._width;
			sys_style._strokecolor = canvasval._strokecolor;
			sys_style._dasharr = canvasval._dasharr;
			sys_style._dashoffset = canvasval._dashoffset;
		}
		else
		{
			sys_style._width = null
			sys_style._strokecolor = null
			sys_style._dasharr = null;
			sys_style._dashoffset = null;
		}
	}
	pCanvasElement._updateFill = function(fillBrushObj, sys_style, fillGradationObj, bounds)
	{
		if ( fillBrushObj )
		{
			var canvasval = fillBrushObj._syscanvasval;
			if ( !canvasval )
			{
				var substyle = fillBrushObj.style;
				if ( substyle == "none" )
				{
					canvasval = "";
					fillBrushObj._syscanvasval = canvasval;
				}
				else if ( substyle == "solid" )
				{
					canvasval = (fillBrushObj._syscolor||nexacro._getWebColorFromXreColor(fillBrushObj.color));
					fillBrushObj._syscanvasval = canvasval;
				}
				else if ( substyle == "gradation" )
				{
					if ( fillGradationObj )
					{
						var gradationsubstyle = fillGradationObj.style, startPt, endPt,
							_handle = this._handle,
							ctxGradient;
						canvasval = nexacro._getWebColorFromXreColor(fillGradationObj.start_color);
						// 현재 runtime는 gradation 처리 api가 존재하지 않은다. 하여 임시로 startcolor로 설정함.
						if (gradationsubstyle == "linear")
						{
							var val = {};
							val.style = gradationsubstyle;
							val.startx = fillGradationObj._start_x;
							val.starty = fillGradationObj._start_y;
							val.startcolor = nexacro._getWebColorFromXreColor(fillGradationObj.start_color);
							val.endx = fillGradationObj._end_x;
							val.endy = fillGradationObj._end_y;
							val.endcolor = nexacro._getWebColorFromXreColor(fillGradationObj.end_color);
							val.sysvalue = fillGradationObj._sysvalue;
							canvasval = val;
						}
						else if ( gradationsubstyle == "radial" )
						{
							var val = {};
							val.style = gradationsubstyle;
							val.startx = fillGradationObj._start_x;
							val.starty = fillGradationObj._start_y;
							val.endx = fillGradationObj._end_x;
							val.endy = fillGradationObj._end_y;
							val.startcolor = nexacro._getWebColorFromXreColor(fillGradationObj.start_color);
							val.endcolor = nexacro._getWebColorFromXreColor(fillGradationObj.end_color);

							// nexacro._makeGradationSysValue function  at the SystemBase_Runtime.js
							// parse routine not exist when gradation style value is radial.
							// that is the problem( bug ) with nexacro._makeGradationSysValue function. but in here I can process until bug fixed. 
							if (fillGradationObj.peglist.length) 
							{
								var peglist = [];
								var arr = fillGradationObj._parsePegList(fillGradationObj.peglist);
								var len = arr.length;
								var valarr;
								for (var i = 0; i < len; i++) 
								{
									valarr = arr[i];
									peglist.push(valarr[0]);
									peglist.push(nexacro._getWebColorFromXreColor(valarr[1]));
								}
								val.sysvalue = peglist;
							}
							else
							{
								val.sysvalue = fillGradationObj._sysvalue;
							}
							canvasval = val;
						}
						else
						{
							canvasval = (fillBrushObj._syscolor||nexacro._getWebColorFromXreColor(fillBrushObj.color));
						}
					}
					else
					{
						canvasval = (fillBrushObj._syscolor||nexacro._getWebColorFromXreColor(fillBrushObj.color));
					}
				}
			}
			sys_style._fillcolor = canvasval;
		}
		else
		{
			sys_style._fillcolor = null;
		}
	}
	pCanvasElement._updateStrokeJoin = function(strokejoinObj, sys_style)
	{
		if ( strokejoinObj )
		{
			sys_style._strokejoin = val._value||val.value;
		}
		else
		{
			sys_style._strokejoin = null;
		}

	}
	pCanvasElement._updateStrokeCap = function(strokecapObj, sys_style)
	{
		if ( strokecapObj )
		{
			var tmpval = strokecapObj._value||strokecapObj.value;
			if ( tmpval == "flat" )
			{
				sys_style._strokecap = "butt";
			}
			else
			{
				sys_style._strokecap = tmpval;
			}
		}
		else
		{
			sys_style._strokecap = null;
		}
	}
	pCanvasElement._updateMiterJoinLimit = function(miterjoinlimitObj, sys_style)
	{
		if ( miterjoinlimitObj )
		{
			var tmpval = miterjoinlimitObj._value||miterjoinlimitObj.value;
			if ( isNaN(tmpval) )
			{
				sys_style._miterlimit = null;
			}
			else
			{
				sys_style._miterlimit = parseInt(tmpval);
			}
		}
		else
		{
			sys_style._miterlimit = null;
		}
	}

	pCanvasElement._updateFont = function(fontObj, fontcolorObj, sys_style)
	{
		if ( fontcolorObj )
		{
			sys_style._fontcolor = (fontcolorObj._syscolor||nexacro._getWebColorFromXreColor(fontcolorObj.value));
		}
		else
		{
			sys_style._fontcolor = null;
		}

		if ( fontObj )
		{
			sys_style._font = fontObj;
		}
		else
		{
			sys_style._font = null;
		}
	}
}
else if (nexacro.Browser == "IE" && nexacro.BrowserVersion <= 8)
{ // IE7,8
	Eco._getLines = function(nexacroFont, txt, wordwrap, maxWidth)
	{
		var lines,
			txtsWidth = 0;
		if ( wordwrap )
		{
			var fsz = nexacroFont.size;
			var sizeForOneChar = ( sz >= 0 ? nexacro._convertPtToPx(fsz) : Math.abs(fsz) );
			if ( sizeForOneChar >= maxWidth )
			{
				return txt.split("");
			}
			var sz = nexacro._getTextSize(txt, nexacroFont);
			if ( sz[0] > maxWidth)
			{
				lines = [];
				var tmpLines = txt.split("\n"),
					line, words, wordSize,
					lineSize, testLine = "",
					reallines = [];
				for ( var i = 0, len = tmpLines.length ; i < len ; i++ )
				{
					line = tmpLines[i];
					lineSize = nexacro._getTextSize(line, nexacroFont);
					if ( lineSize[0] > maxWidth )
					{
						lines = lines.concat(this._fragmentText(nexacroFont, line, maxWidth));
					}
					else
					{
						lines.push(line);
					}
				}
			}
			else
			{
				lines = txt.split("\n");
			}
		}
		else
		{
			lines = txt.split("\n");
		}
		return lines;
	}

	Eco._fragmentText = function(nexacroFont, text, maxWidth)
	{
		var words = text.split(' '),
			lines = [],
			line = "";

		for ( var n = 0, len = words.length ; n < len ; n++ )
		{
			var tmpLine = line + words[n] + ' ';
			if ( n > 0 && (nexacro._getTextSize(tmpLine, nexacroFont))[0] > maxWidth )
			{
				var tmpChars = line.split(""), tmpstr = "";
				for ( var c = 0, clen = tmpChars.length ; c < clen ; c++ )
				{
					var tmpLine2 = tmpstr + tmpChars[c];
					if ( c > 0  && (nexacro._getTextSize(tmpLine2, nexacroFont))[0] > maxWidth )
					{
						lines.push(tmpstr);
						tmpstr = tmpChars[c];
					}
					else
					{
						tmpstr = tmpLine2;
					}
				}				
				line = tmpstr + words[n] + ' ';
			}
			else
			{
				line = tmpLine;
			}
		}
		if ( (nexacro._getTextSize(line, nexacroFont))[0] > maxWidth )
		{
			var tmpChars = line.split(""), tmpstr = "";
			for ( var c = 0, clen = tmpChars.length ; c < clen ; c++ )
			{
				var tmpLine2 = tmpstr + tmpChars[c];
				if ( c > 0  && (nexacro._getTextSize(tmpLine2, nexacroFont))[0] > maxWidth )
				{
					lines.push(tmpstr);
					tmpstr = tmpChars[c];
				}
				else
				{
					tmpstr = tmpLine2;
				}
			}
			if ( tmpstr.length )
			{
				lines.push(tmpstr);
			}
		}
		else if ( line.length )
		{
			lines.push(line);
		}
		return lines;
	}

	Eco._getTextSize = function(nexacroFont, lines, fontHeight)
	{
		var w = 0,
			len = lines.length,
			h = fontHeight*len;
		for ( var i = 0 ; i < len ; i++ )
		{
			w = Math.max(w, (nexacro._getTextSize(lines[i], nexacroFont))[0]);
		}
		return [w, h];
	}

	Eco._updateFontInCanvas = function(nexacroFont, sys_style)
	{
		if ( nexacroFont )
		{
			sys_style._font = nexacroFont._sysvalue;
		}
		else
		{
			sys_style._font = null;
		}
	}

	var pCanvasElement = nexacro.CanvasElement.prototype;

	pCanvasElement.create = function ()
	{
		var _owner_elem = this._parent_elem.getContainerElement(this.position_step);
		if (_owner_elem && _owner_elem._handle)
		{
			var _handle = this._handle;
			if (!_handle)
			{
				this._owner_elem = _owner_elem;
				var _doc = _owner_elem.getRootWindowHandle();
				_handle = _doc.createElement("div");
				_handle._linked_element = this;
				_handle._element_type = 7;          
				
				nexacro.__setDOMNodeSelectable(_handle, false);
				
				// append TO Parent Element
				this._handle = this._dest_handle = _handle;
				var _draw_node = _doc.createElement("div");
				_handle._draw_node = _draw_node;
				var node_style = _draw_node.style;

				node_style.border = "none";
				nexacro.__setDOMNodeStyleAbsoluteTransparent(node_style);
				nexacro.__setDOMNodeStylePos(node_style, this.left, this.top);
				nexacro.__setDOMNodeSize(node_style, this.width, this.height);

				nexacro.__appendDOMNode(_handle, _draw_node);                    
				nexacro.__appendDOMNode(_owner_elem._dest_handle, _handle);
				G_vmlCanvasManager.initElement(_draw_node, this.width, this.height);
				_handle._draw_ctx = _draw_node.getContext();
			}
		}
	};

	pCanvasElement.destroy = function ()
	{
		var _handle = this._handle;
		if (_handle)
		{
			_handle._linked_element = null;
			
			var _owner_handle = null;   // unlink 인 경우 _owner_elem이 null이다.
			if (this._owner_elem && this._owner_elem._dest_handle)
			_owner_handle = this._owner_elem._dest_handle;
	
			_handle._draw_node.getContext = null;
			_handle._draw_node.context_ = null;
			_handle._draw_ctx = null;

			if (!this._owner_elem || _owner_handle)
				nexacro.__removeDOMNode(_owner_handle, _handle);
			
			this._owner_elem = null;
			this._handle = this._dest_handle = null;
			
			_handle._linked_element = null;
			_handle._draw_node = null;
		}
		this.parent = null;
		this._parent_elem = null;
	};

	// override
	pCanvasElement.clearRect = function (x, y, dx, dy)
	{
		var _handle = this._handle;
		if (_handle)
		{
			var ctx = _handle._draw_ctx;
			ctx.clearRect();
		}
	};

	pCanvasElement.fillText = function (text, x, y, maxwidth)
	{
		var _handle = this._handle;
		if (_handle)
		{
			var ctx = _handle._draw_ctx;
			ctx.fillText(text, x, y, maxWidth);
		}
	};

	pCanvasElement.setElementGlobalAlpha = function (alpha)
	{
		var _handle = this._handle;
		if (_handle && alpha)
		{
			var ctx = _handle._draw_ctx;
			ctx.globalAlpha = alpha;
		}
	};

	pCanvasElement.setElementLineCap = function (linecap)
	{
		var _handle = this._handle;
		if (_handle)
		{
			var ctx = _handle._draw_ctx;
			ctx.lineCap = alpha;
		}
	};

	pCanvasElement.setElementLineJoin = function (linejoin)
	{
		var _handle = this._handle;
		if (_handle)
		{
			var ctx = _handle._draw_ctx;
			ctx.lineJoin = linejoin;
		}
	};

	pCanvasElement.setElementLineWidth = function (width)
	{
		var _handle = this._handle;
		if (_handle && isFinite(width)) 
		{
			var ctx = _handle._draw_ctx;
			ctx.lineWidth = width;
		}
	};

	pCanvasElement.setElementStrokeStyle = function (color)
	{
		var _handle = this._handle;
		if (_handle && color)
		{
			var ctx = _handle._draw_ctx;
			ctx.strokeStyle = nexacro._getWebColorFromXreColor(color.value);
		}
	};

	pCanvasElement.setElementMiterLimit = function (limit)
	{
		var _handle = this._handle;
		if (_handle && limit)
		{
			var ctx = _handle._draw_ctx;
			ctx.miterLimit = limit*10;
		}
	};

	pCanvasElement.setElementShadowBlur = function (blur)
	{
		var _handle = this._handle;
		if (_handle)
		{
			var ctx = _handle._draw_ctx;
			ctx.shadowBlur = blur;
		}
	};
	
	pCanvasElement.setElementShadowColor = function (color)
	{
		var _handle = this._handle;
		if (color && _handle) 
		{
			var ctx = _handle._draw_ctx;
			ctx.shadowColor = nexacro._getWebColorFromXreColor(color.value);
		}
	};
	
	pCanvasElement.setElementShadowOffsetX = function (offsetx)
	{
		var _handle = this._handle;
		if (_handle) 
		{
			var ctx = _handle._draw_ctx;
			ctx.shadowOffsetX = offsetx;
		}
	};
	
	pCanvasElement.setElementShadowOffsetY = function (offsety)
	{
		var _handle = this._handle;
		if (_handle)
		{
			var ctx = _handle._draw_ctx;
			ctx.shadowOffsetY = offsety;
		}
	};

	pCanvasElement.setElementTextAlign = function (align)
	{
		var _handle = this._handle;
		if (_handle)
		{
			var ctx = _handle._draw_ctx;
			ctx.textAlign = align;
		}
	};
  
	pCanvasElement.setElementTextBaseline = function (value)
	{
		var _handle = this._handle;
		if (_handle) 
		{
			var ctx = _handle._draw_ctx;
			ctx.textBaseline = value;
		}
	};
	
	pCanvasElement.arc = function (x, y, r, start_rad, end_rad, counterclockwise)
	{
		var _handle = this._handle;
		if (_handle) 
		{
			var ctx = _handle._draw_ctx;
			ctx.arc(x, y, r, start_rad, end_rad, counterclockwise);
		}
	};

	pCanvasElement.beginPath = function ()
	{
		var _handle = this._handle;
		if (_handle)
		{
			var ctx = _handle._draw_ctx;
			ctx.beginPath();
		}
	};
	
	pCanvasElement.bezierCurveTo = function (p1x, p1y, p2x, p2y, x, y)
	{
		var _handle = this._handle;
		if (_handle)
		{
			var ctx = _handle._draw_ctx;
			ctx.bezierCurveTo(p1x, p1y, p2x, p2y, x, y);
		}
	};

	pCanvasElement.closePath = function ()
	{
		var _handle = this._handle;
		if (_handle)
		{
			var ctx = _handle._draw_ctx;
			ctx.closePath();
		}
	};
	
	pCanvasElement.drawImage = function (objimage, x, y, imgWidth, imgHeight)
	{
		var _handle = this._handle;
		var img = objimage._handle;
		if (img && _handle)
		{
			var ctx = _handle._draw_ctx;
			ctx.drawImage(img, x, y, imgWidth, imgHeight);
		}
	};
	
	pCanvasElement.fill = function ()
	{
		var _handle = this._handle;
		if (_handle) 
		{
			var ctx = _handle._draw_ctx;
			ctx.fill();
		}
	};

	pCanvasElement.fillRect = function (x, y, dx, dy)
	{
		var _handle = this._handle;
		if (_handle) 
		{
			var ctx = _handle._draw_ctx;
			ctx.fillRect(x, y, dx, dy);
		}
	};

	pCanvasElement.fillText = function (text, x, y, maxWidth)
	{
		var _handle = this._handle;
		if (_handle) 
		{
			var ctx = _handle._draw_ctx;
			ctx.fillText(text, x, y, maxWidth);
		}
	};

	pCanvasElement.lineTo = function (x, y)
	{
		var _handle = this._handle;
		if (_handle)
		{
			var ctx = _handle._draw_ctx;
			ctx.lineTo(x, y);
		}
	};
	
	pCanvasElement.moveTo = function (x, y)
	{
		var _handle = this._handle;
		if (_handle)
		{
			var ctx = _handle._draw_ctx;
			ctx.moveTo(x, y);
		}
	};
	
	pCanvasElement.quadraticCurveTo = function (px, py, x, y)
	{
		var _handle = this._handle;
		if (_handle)
		{
			var ctx = _handle._draw_ctx;
			ctx.quadraticCurveTo(px, py, x, y);
		}
	};
	
	pCanvasElement.rect = function (x, y, w, h)
	{
		var _handle = this._handle;
		if (_handle)
		{
			var ctx = _handle._draw_ctx;
			ctx.rect(x, y, w, h);
		}
	};

	pCanvasElement.strokeRect = function (x, y, width, height)
	{
		var _handle = this._handle;
		if (_handle)
		{    
			var ctx = _handle._draw_ctx;
			ctx.strokeRect(x, y, width, height);
		}
	};
	
	pCanvasElement.strokeText = function (text, x, y, maxWidth)
	{
		var _handle = this._handle;
		if (_handle)
		{
			var ctx = _handle._draw_ctx;
			ctx.strokeText(text, x, y, maxWidth);
		}
	};

	pCanvasElement.stroke = function ()
	{
		var _handle = this._handle;
		if (_handle)
		{
			var ctx = _handle._draw_ctx;
			ctx.stroke();
		}
	};

	pCanvasElement.rotate = function (angle)
	{
		var _handle = this._handle;
		if (_handle)
		{    
			var ctx = _handle._draw_ctx;
			ctx.rotate(angle);
		}
	};
	
	pCanvasElement.scale = function (dx, dy)
	{
		var _handle = this._handle;
		if (_handle)
		{    
			var ctx = _handle._draw_ctx;
			ctx.scale(dx, dy);
		}
	};
	
	pCanvasElement.setTransform = function (a, b, c, d, e, f)
	{
		var _handle = this._handle;
		if (_handle)
		{    
			var ctx = _handle._draw_ctx;
			ctx.setTransform(a, b, c, d, e, f);
		}
	};
	
	pCanvasElement.transform = function (a, b, c, d, e, f)
	{
		var _handle = this._handle;
		if (_handle)
		{    
			var ctx = _handle._draw_ctx;
			ctx.transform(a, b, c, d, e, f);
		}
	};
	
	pCanvasElement.translate = function (x, y)
	{
		var _handle = this._handle;
		if (_handle)
		{    
			var ctx = _handle._draw_ctx;
			ctx.translate(x, y);
		}
	};

	pCanvasElement.save = function ()
	{
		var _handle = this._handle;
		if (_handle)
		{    
			var ctx = _handle._draw_ctx;
			ctx.save();
		}
	};
	
	
	pCanvasElement.restore = function ()
	{
		var _handle = this._handle;
		if (_handle)
		{    
			var ctx = _handle._draw_ctx;
			ctx.restore();
		}
	};

	pCanvasElement._setFont = function(font)
	{
		var _handle = this._handle;
		if ( _handle )
		{
			var ctx = _handle._draw_ctx;
			if (font)
				ctx.font = font;
		}
	}

	pCanvasElement._applystyles = function(style)
	{
		if ( !style ) return;
		var _handle = this._handle;
		if ( _handle )
		{
			var ctx = _handle._draw_ctx;

			var width = style._width,
				join = style._strokejoin,
				cap = style._strokecap,
				limit = style._miterlimit,
				fillColor = style._fillcolor,
				strokeColor = style._strokecolor,
				dashstyle = style._dashstyle,
				font = style._font;

			if (width != null)
				ctx.lineWidth = width;
			if (join)
				ctx.lineJoin = join;
			if (cap)
				ctx.lineCap = cap;
			if (limit)
				ctx.miterLimit = limit;
			if (font)
				ctx.font = font;

			if (fillColor)
			{
				ctx.fillStyle = fillColor;
			}
			if (strokeColor)
			{
				ctx.strokeStyle = strokeColor;
				ctx.dashStyle = dashstyle;
			}
		}
	}

	pCanvasElement._updateStrokePen = function(strokePenObj, sys_style)
	{
		if ( strokePenObj )
		{
			var canvasval = strokePenObj._syscanvasval;
			if ( !canvasval )
			{
				var substyle = strokePenObj.style;
				canvasval = {};
				if ( substyle == "none" )
				{
					canvasval._width = null
					canvasval._strokecolor = null
				}
				else if ( substyle == "solid" )
				{
					canvasval._width = strokePenObj.width;
					canvasval._strokecolor = (strokePenObj._syscolor||nexacro._getWebColorFromXreColor(strokePenObj.color));
					canvasval._dashstyle = "solid";
				}
				else
				{
					canvasval._width = strokePenObj.width;
					canvasval._strokecolor = (strokePenObj._syscolor||nexacro._getWebColorFromXreColor(strokePenObj.color));
					switch ( substyle )
					{
						case "dotted":
							canvasval._dashstyle = "dot";
							break;
						case "dashed":
							canvasval._dashstyle = "dash";
							break;
						default:
							canvasval._dashstyle = "solid";
							break;
					}
				}
				strokePenObj._syscanvasval = canvasval;
			}
			sys_style._width = canvasval._width;
			sys_style._strokecolor = canvasval._strokecolor;
			sys_style._dashstyle = canvasval._dashstyle;
		}
		else
		{
			sys_style._width = null
			sys_style._strokecolor = null
			sys_style._dashstyle = null;
		}
	}
	pCanvasElement._updateFill = function(fillBrushObj, sys_style, fillGradationObj, bounds)
	{
		if ( fillBrushObj )
		{
			var canvasval = fillBrushObj._syscanvasval;
			if ( !canvasval )
			{
				var substyle = fillBrushObj.style;
				if ( substyle == "none" )
				{
					canvasval = "";
					fillBrushObj._syscanvasval = canvasval;
				}
				else if ( substyle == "solid" )
				{
					canvasval = (fillBrushObj._syscolor||nexacro._getWebColorFromXreColor(fillBrushObj.color));
					fillBrushObj._syscanvasval = canvasval;
				}
				else if ( substyle == "gradation" )
				{
					if ( fillGradationObj )
					{
						var gradationsubstyle = fillGradationObj.style, startPt, endPt,
							_handle = this._handle,
							ctx = _handle._draw_ctx,
							ctxGradient;
						if (gradationsubstyle == "linear" )
						{
							startPt = new Eco.Point(bounds.width*fillGradationObj._start_x/100, bounds.height*fillGradationObj._start_y/100);
							endPt = new Eco.Point(bounds.width*fillGradationObj._end_x/100, bounds.height*fillGradationObj._end_y/100);
							startPt.x += bounds.x;
							startPt.y += bounds.y;
							endPt.x += bounds.x;
							endPt.y += bounds.y;
							//if ( matrix )
							//{
							//	matrix._transformPoint(startPt, startPt);
							//	matrix._transformPoint(endPt, endPt);
							//}
							ctxGradient = ctx.createLinearGradient(startPt.x, startPt.y,
									endPt.x, endPt.y);
							//console.log(ctxGradient, startPt.toString(), endPt.toString());

							ctxGradient.addColorStop(0, nexacro._getWebColorFromXreColor(fillGradationObj.start_color));
							// add paglist
							//console.log(0, nexacro._getWebColorFromXreColor(fillGradationObj.start_color));
							if (fillGradationObj.peglist.length) 
							{
								var arr = fillGradationObj._parsePegList(fillGradationObj.peglist);
								var len = arr.length;
								var valarr;
								for (var i = 0; i < len; i++) 
								{
									valarr = arr[i];
									ctxGradient.addColorStop(valarr[0]/100, nexacro._getWebColorFromXreColor(valarr[1]));
									//console.log(valarr[0]/100, nexacro._getWebColorFromXreColor(valarr[1]));
								}
							}
							ctxGradient.addColorStop(1, nexacro._getWebColorFromXreColor(fillGradationObj.end_color));
							//console.log(1, nexacro._getWebColorFromXreColor(fillGradationObj.end_color));
							canvasval = ctxGradient;
						}
						else if (gradationsubstyle == "radial" )
						{
							startPt = new Eco.Point(bounds.width*fillGradationObj._start_x/100, bounds.height*fillGradationObj._start_y/100);
							endPt = new Eco.Point(bounds.width*fillGradationObj._end_x/100, bounds.height*fillGradationObj._end_y/100);
							startPt.x += bounds.x;
							startPt.y += bounds.y;
							endPt.x += bounds.x;
							endPt.y += bounds.y;
							ctxGradient = ctx.createRadialGradient(startPt.x, startPt.y,
									1, startPt.x, startPt.y, Math.max(bounds.width, bounds.height)/2);
							//console.log("radial", startPt.toString(), endPt.toString(), Math.max(endPt.x, endPt.y)/2);
							ctxGradient.addColorStop(0, nexacro._getWebColorFromXreColor(fillGradationObj.start_color));
							// add paglist
							if (fillGradationObj.peglist.length) 
							{
								var arr = fillGradationObj._parsePegList(fillGradationObj.peglist);
								var len = arr.length;
								var valarr;
								for (var i = 0; i < len; i++) 
								{
									valarr = arr[i];
									ctxGradient.addColorStop(valarr[0]/100, nexacro._getWebColorFromXreColor(valarr[1]));
								}
							}
							ctxGradient.addColorStop(1, nexacro._getWebColorFromXreColor(fillGradationObj.end_color));
							canvasval = ctxGradient;
						}
						else
						{
							canvasval = (fillBrushObj._syscolor||nexacro._getWebColorFromXreColor(fillBrushObj.color));
						}
					}
					else
					{
						canvasval = (fillBrushObj._syscolor||nexacro._getWebColorFromXreColor(fillBrushObj.color));
					}
				}
			}
			sys_style._fillcolor = canvasval;
		}
		else
		{
			sys_style._fillcolor = null;
		}
	}
	pCanvasElement._updateStrokeJoin = function(strokejoinObj, sys_style)
	{
		if ( strokejoinObj )
		{
			sys_style._strokejoin = val._value||val.value;
		}
		else
		{
			sys_style._strokejoin = null;
		}

	}
	pCanvasElement._updateStrokeCap = function(strokecapObj, sys_style)
	{
		if ( strokecapObj )
		{
			var tmpval = strokecapObj._value||strokecapObj.value;
			if ( tmpval == "flat" )
			{
				sys_style._strokecap = "butt";
			}
			else
			{
				sys_style._strokecap = tmpval;
			}
		}
		else
		{
			sys_style._strokecap = null;
		}
	}
	pCanvasElement._updateMiterJoinLimit = function(miterjoinlimitObj, sys_style)
	{
		if ( miterjoinlimitObj )
		{
			var tmpval = miterjoinlimitObj._value||miterjoinlimitObj.value;
			if ( isNaN(tmpval) )
			{
				sys_style._miterlimit = null;
			}
			else
			{
				sys_style._miterlimit = parseInt(tmpval);
			}
		}
		else
		{
			sys_style._miterlimit = null;
		}
	}
	pCanvasElement._updateFont = function(fontObj, fontcolorObj, sys_style)
	{
		if ( fontcolorObj )
		{
			sys_style._fontcolor = (fontcolorObj._syscolor||nexacro._getWebColorFromXreColor(fontcolorObj.value));
		}
		else
		{
			sys_style._fontcolor = null;
		}

		if ( fontObj )
		{
			sys_style._font = fontObj._sysvalue;
		}
		else
		{
			sys_style._font = null;
		}
	}
}
else //HTML5 || IE9이상
{
	Eco._getDefaultCanvas = function()
	{
		var defaultCanvas = Eco._defaultCanvas;
		if ( !defaultCanvas )
		{
			var _doc = nexacro._managerFrameDoc;
			defaultCanvas = _doc.createElement("canvas");
			defaultCanvas.width = 1;
			defaultCanvas.height = 1;
			_doc.body.appendChild(defaultCanvas);
			Eco._defaultCanvas = defaultCanvas;
		}
		return defaultCanvas;
	};

	Eco._getLines = function(nexacroFont, txt, wordwrap, maxWidth)
	{
		var canvas = Eco._getDefaultCanvas(),
			ctx = canvas.getContext('2d');
		var lines,
			txtsWidth = 0;
		if ( wordwrap )
		{
			var sz = nexacroFont.size;
			var sizeForOneChar = ( sz >= 0 ? nexacro._convertPtToPx(sz) : Math.abs(sz) );
			if ( sizeForOneChar >= maxWidth )
			{
				return txt.split("");
			}
			ctx.font = nexacroFont._sysvalue;
			var sz = ctx.measureText(txt);
			if ( sz.width > maxWidth)
			{
				lines = [];
				var tmpLines = txt.split("\n"),
					line, words, wordSize,
					lineSize, testLine = "",
					reallines = [];
				for ( var i = 0, len = tmpLines.length ; i < len ; i++ )
				{
					line = tmpLines[i];
					lineSize = ctx.measureText(line);
					if ( lineSize.width > maxWidth )
					{
						lines = lines.concat(this._fragmentText(ctx, line, maxWidth));
					}
					else
					{
						lines.push(line);
					}
				}
			}
			else
			{
				lines = txt.split("\n");
			}
		}
		else
		{
			lines = txt.split("\n");
		}
		return lines;
	}

	Eco._fragmentText = function(ctx, text, maxWidth)
	{
		var words = text.split(' '),
			lines = [],
			line = "";

		for ( var n = 0, len = words.length ; n < len ; n++ )
		{
			var tmpLine = line + words[n] + ' ';
			if ( n > 0 && ctx.measureText(tmpLine).width > maxWidth )
			{
				var tmpChars = line.split(""), tmpstr = "";
				for ( var c = 0, clen = tmpChars.length ; c < clen ; c++ )
				{
					var tmpLine2 = tmpstr + tmpChars[c];
					if ( c > 0  && ctx.measureText(tmpLine2).width > maxWidth )
					{
						lines.push(tmpstr);
						tmpstr = tmpChars[c];
					}
					else
					{
						tmpstr = tmpLine2;
					}
				}				
				line = tmpstr + words[n] + ' ';
			}
			else
			{
				line = tmpLine;
			}
		}
		if ( ctx.measureText(line).width > maxWidth )
		{
			var tmpChars = line.split(""), tmpstr = "";
			for ( var c = 0, clen = tmpChars.length ; c < clen ; c++ )
			{
				var tmpLine2 = tmpstr + tmpChars[c];
				if ( c > 0  && ctx.measureText(tmpLine2).width > maxWidth )
				{
					lines.push(tmpstr);
					tmpstr = tmpChars[c];
				}
				else
				{
					tmpstr = tmpLine2;
				}
			}
			if ( tmpstr.length )
			{
				lines.push(tmpstr);
			}
		}
		else if ( line.length )
		{
			lines.push(line);
		}
		return lines;
	}

	Eco._getTextSize = function(nexacroFont, lines, fontHeight)
	{
		var canvas = Eco._getDefaultCanvas(),
			ctx = canvas.getContext('2d');

		ctx.font = nexacroFont._sysvalue;
		var w = 0,
			len = lines.length,
			h = fontHeight*len;
		for ( var i = 0 ; i < len ; i++ )
		{
			w = Math.max(w, ctx.measureText(lines[i]).width);
		}
		return [w, h];
	}

	Eco._updateFontInCanvas = function(nexacroFont, sys_style)
	{
		if ( nexacroFont )
		{
			sys_style._font = nexacroFont._sysvalue;
		}
		else
		{
			sys_style._font = null;
		}
	}

	var pCanvasElement = nexacro.CanvasElement.prototype;

	pCanvasElement._setFont = function(font)
	{
		var _handle = this._handle;
		if ( _handle )
		{
			var ctx = _handle._draw_ctx;
			if (font)
				ctx.font = font;
		}
	}

	pCanvasElement._applystyles = function(style)
	{
		if ( !style ) return;
		var _handle = this._handle;
		if ( _handle )
		{
			var ctx = _handle._draw_ctx;

			var width = style._width,
				join = style._strokejoin,
				cap = style._strokecap,
				limit = style._miterlimit,
				fillColor = style._fillcolor,
				strokeColor = style._strokecolor,
				dashArray = style._dasharr,
				dashOffset = style._dashoffset,
				font = style._font;

			if (width != null)
				ctx.lineWidth = width;
			if (join)
				ctx.lineJoin = join;
			if (cap)
				ctx.lineCap = cap;
			if (limit)
				ctx.miterLimit = limit;
			if (font)
				ctx.font = font;

			if (fillColor)
			{
				ctx.fillStyle = fillColor;
			}
			if (strokeColor)
			{
				ctx.strokeStyle = strokeColor;
				if ( dashArray && dashArray.length)
				{
					if ('setLineDash' in ctx)
					{
						ctx.setLineDash(dashArray);
						ctx.lineDashOffset = dashOffset;
					}
					else if ('mozDash' in ctx)
					{
						ctx.mozDash = dashArray;
						ctx.mozDashOffset = dashOffset;
					}
				}
			}
		}
	}

	pCanvasElement._updateStrokePen = function(strokePenObj, sys_style)
	{
		if ( strokePenObj )
		{
			var canvasval = strokePenObj._syscanvasval;
			if ( !canvasval )
			{
				var substyle = strokePenObj.style;
				canvasval = {};
				if ( substyle == "none" )
				{
					canvasval._width = null
					canvasval._strokecolor = null
				}
				else if ( substyle == "solid" )
				{
					canvasval._width = strokePenObj.width;
					canvasval._strokecolor = (strokePenObj._syscolor||nexacro._getWebColorFromXreColor(strokePenObj.color));
					canvasval._dasharr = null;
					canvasval._dashoffset = null;
				}
				else
				{
					canvasval._width = strokePenObj.width;
					canvasval._strokecolor = (strokePenObj._syscolor||nexacro._getWebColorFromXreColor(strokePenObj.color));
					switch ( substyle )
					{
						case "dotted":
							canvasval._dasharr = [1,2];
							canvasval._dashoffset = 2;
							break;
						case "dashed":
							canvasval._dasharr = [2,3];
							canvasval._dashoffset = 2;
							break;
						default:
							canvasval._dasharr = null;
							canvasval._dashoffset = null;
							break;
					}
				}
				strokePenObj._syscanvasval = canvasval;
			}
			sys_style._width = canvasval._width;
			sys_style._strokecolor = canvasval._strokecolor;
			sys_style._dasharr = canvasval._dasharr;
			sys_style._dashoffset = canvasval._dashoffset;
		}
		else
		{
			sys_style._width = null
			sys_style._strokecolor = null
			sys_style._dasharr = null;
			sys_style._dashoffset = null;
		}
	}
	pCanvasElement._updateFill = function(fillBrushObj, sys_style, fillGradationObj, bounds)
	{
		if ( fillBrushObj )
		{
			var canvasval = fillBrushObj._syscanvasval;
			if ( !canvasval )
			{
				var substyle = fillBrushObj.style;
				if ( substyle == "none" )
				{
					canvasval = "";
					fillBrushObj._syscanvasval = canvasval;
				}
				else if ( substyle == "solid" )
				{
					canvasval = (fillBrushObj._syscolor||nexacro._getWebColorFromXreColor(fillBrushObj.color));
					fillBrushObj._syscanvasval = canvasval;
				}
				else if ( substyle == "gradation" )
				{
					if ( fillGradationObj )
					{
						var gradationsubstyle = fillGradationObj.style, startPt, endPt,
							_handle = this._handle,
							ctx = _handle._draw_ctx,
							ctxGradient;
						if (gradationsubstyle == "linear" )
						{
							startPt = new Eco.Point(bounds.width*fillGradationObj._start_x/100, bounds.height*fillGradationObj._start_y/100);
							endPt = new Eco.Point(bounds.width*fillGradationObj._end_x/100, bounds.height*fillGradationObj._end_y/100);
							startPt.x += bounds.x;
							startPt.y += bounds.y;
							endPt.x += bounds.x;
							endPt.y += bounds.y;
							//if ( matrix )
							//{
							//	matrix._transformPoint(startPt, startPt);
							//	matrix._transformPoint(endPt, endPt);
							//}
							ctxGradient = ctx.createLinearGradient(startPt.x, startPt.y,
									endPt.x, endPt.y);
							//console.log(ctxGradient, startPt.toString(), endPt.toString());

							ctxGradient.addColorStop(0, nexacro._getWebColorFromXreColor(fillGradationObj.start_color));
							// add paglist
							//console.log(0, nexacro._getWebColorFromXreColor(fillGradationObj.start_color));
							if (fillGradationObj.peglist.length) 
							{
								var arr = fillGradationObj._parsePegList(fillGradationObj.peglist);
								var len = arr.length;
								var valarr;
								for (var i = 0; i < len; i++) 
								{
									valarr = arr[i];
									ctxGradient.addColorStop(valarr[0]/100, nexacro._getWebColorFromXreColor(valarr[1]));
									//console.log(valarr[0]/100, nexacro._getWebColorFromXreColor(valarr[1]));
								}
							}
							ctxGradient.addColorStop(1, nexacro._getWebColorFromXreColor(fillGradationObj.end_color));
							//console.log(1, nexacro._getWebColorFromXreColor(fillGradationObj.end_color));
							canvasval = ctxGradient;
						}
						else if (gradationsubstyle == "radial" )
						{
							startPt = new Eco.Point(bounds.width*fillGradationObj._start_x/100, bounds.height*fillGradationObj._start_y/100);
							endPt = new Eco.Point(bounds.width*fillGradationObj._end_x/100, bounds.height*fillGradationObj._end_y/100);
							startPt.x += bounds.x;
							startPt.y += bounds.y;
							endPt.x += bounds.x;
							endPt.y += bounds.y;
							ctxGradient = ctx.createRadialGradient(startPt.x, startPt.y,
									1, startPt.x, startPt.y, Math.max(bounds.width, bounds.height)/2);
							//console.log("radial", startPt.toString(), endPt.toString(), Math.max(endPt.x, endPt.y)/2);
							ctxGradient.addColorStop(0, nexacro._getWebColorFromXreColor(fillGradationObj.start_color));
							// add paglist
							if (fillGradationObj.peglist.length) 
							{
								var arr = fillGradationObj._parsePegList(fillGradationObj.peglist);
								var len = arr.length;
								var valarr;
								for (var i = 0; i < len; i++) 
								{
									valarr = arr[i];
									ctxGradient.addColorStop(valarr[0]/100, nexacro._getWebColorFromXreColor(valarr[1]));
								}
							}
							ctxGradient.addColorStop(1, nexacro._getWebColorFromXreColor(fillGradationObj.end_color));
							canvasval = ctxGradient;
						}
						else
						{
							canvasval = (fillBrushObj._syscolor||nexacro._getWebColorFromXreColor(fillBrushObj.color));
						}
					}
					else
					{
						canvasval = (fillBrushObj._syscolor||nexacro._getWebColorFromXreColor(fillBrushObj.color));
					}
				}
			}
			sys_style._fillcolor = canvasval;
		}
		else
		{
			sys_style._fillcolor = null;
		}
	}
	pCanvasElement._updateStrokeJoin = function(strokejoinObj, sys_style)
	{
		if ( strokejoinObj )
		{
			sys_style._strokejoin = val._value||val.value;
		}
		else
		{
			sys_style._strokejoin = null;
		}

	}
	pCanvasElement._updateStrokeCap = function(strokecapObj, sys_style)
	{
		if ( strokecapObj )
		{
			var tmpval = strokecapObj._value||strokecapObj.value;
			if ( tmpval == "flat" )
			{
				sys_style._strokecap = "butt";
			}
			else
			{
				sys_style._strokecap = tmpval;
			}
		}
		else
		{
			sys_style._strokecap = null;
		}
	}
	pCanvasElement._updateMiterJoinLimit = function(miterjoinlimitObj, sys_style)
	{
		if ( miterjoinlimitObj )
		{
			var tmpval = miterjoinlimitObj._value||miterjoinlimitObj.value;
			if ( isNaN(tmpval) )
			{
				sys_style._miterlimit = null;
			}
			else
			{
				sys_style._miterlimit = parseInt(tmpval);
			}
		}
		else
		{
			sys_style._miterlimit = null;
		}
	}
	pCanvasElement._updateFont = function(fontObj, fontcolorObj, sys_style)
	{
		if ( fontcolorObj )
		{
			sys_style._fontcolor = (fontcolorObj._syscolor||nexacro._getWebColorFromXreColor(fontcolorObj.value));
		}
		else
		{
			sys_style._fontcolor = null;
		}

		if ( fontObj )
		{
			sys_style._font = fontObj._sysvalue;
		}
		else
		{
			sys_style._font = null;
		}
	}
}

if ( !JsNamespace.exist("Eco.Graphic.Style") )
{
	/**
	 * @class Eco.GraphicStyle
	 * @classdesc width, height property를 가지는 Size Class.
	*/
	JsNamespace.declareClass("Eco.Graphic.Style", {
		"_type": "EcoGraphicStyle",
		initialize: function()
		{
			this._property_cnt = 0;
			if ( arguments.length > 0 )
			{
				return this._setValue(arguments);
			}
		},
		statics: {
			_cachesStyle: {},
			getCached: function(v)
			{
				var sCache = this._cachesStyle;
				var style = sCache[v];
				if ( !style )
				{
					style = new this(v);
					if ( style.toString() != v )
					{
						var cacheStyle = sCache[style.toString()];
						if ( cacheStyle )
						{
							sCache[v] = style;
							return cacheStyle;
						}
						sCache[v] = style;
						sCache[style.toString()] = style;
					}
					else
					{
						sCache[v] = style;
					}
				}
				return style;
			},
			registerCached: function(v)
			{
				var sCache = this._cachesStyle;
				var cacheStyle = sCache[v.toString()];
				if ( !cacheStyle )
				{
					sCache[v.toString()] = v;
				}
				return v;
			},
			getKeyForAccessibility: function(role, bflag, desclevel, lab, des, act)
			{
				var str = "";
				if (role) str += role + " ";
				if (bflag) str += "enable ";
				else str += "disable ";
				str += (desclevel||"") + " '" + (lab||"") + "' '" + (des||"") + "' '" + (act||"") + "'";
				return str;
			},
			getKeyForStrokepen: function(w, s, color)
			{
				var val = [];
				if ( arguments.length == 1 )
				{
					var tmpval = w.width;
					if ( tmpval ) val.push(tmpval);
					tmpval = w.style;
					if ( tmpval ) val.push(tmpval);
					tmpval = w.color;
					if ( tmpval ) val.push(tmpval);
				}
				else
				{
					if (w) val.push(w);
					if (s) val.push(s);
					if (color) val.push(color);
				}
				return val.join(" ");
			},
			getKeyForFillbrush: function(s, color)
			{
				var val = [];
				if ( arguments.length == 1 )
				{
					var tmpval = w.style;
					if ( tmpval ) val.push(tmpval);
					tmpval = w.color;
					if ( tmpval ) val.push(tmpval);
				}
				else
				{
					if (s) val.push(s);
					if (color) val.push(color);
					return val.join(" ");
				}
			},
			getKeyForFillgradation: function(style, sX, sY, sColor, eX, eY, eColor, peglist)
			{
				if ( arguments.length == 1 )
				{
					return style.style + " " + style.start_point + " " + style.start_color + " " + style.end_point + " " + style.end_color + " " + style.peglist;
				}
				else
				{
					return style + " " + (sX + "," + sY) + " " + sColor + " " + (eX + "," + eY) + " " + eColor + " " + (peglist||"");
				}
			},
			getKeyForShadow: function(type, color, offsetx, offsety, f)
			{
				var str = "";
				str += type||"";
				str += " ";
				str += offsetx + "," + offsety;
				str += " ";
				str += f||0;
				str += " ";
				str += color||"";
				return str;
			},
			getKeyForAlign: function(valign, halign)
			{
				var str;
				if (halign && valign) 
				{
					str = halign + " " + valign;
				}
				else 
				{
					str = halign + valign;
				}
				return str;
			},
			getKeyForFont: function(face, size, type)
			{
				if ( arguments.length == 1 )
				{
					var arr_type = [];
					if (face.bold)
					{
						arr_type.push("bold");
					}
					if (face.italic) 
					{
						arr_type.push("italic");
					}
					if (face.underline) 
					{
						arr_type.push("underline");
					}
					if (face.strikeout) 
					{
						arr_type.push("strikeout");
					}
					if (face.antialias) 
					{
						arr_type.push("antialias");
					}
					return (face.face||"") + "," + (face.size||0) + "," + arr_type.join(" ");
				}
				else
				{
					return (face||"") + "," + (size||0) + "," + (type||"");
				}
			},
			getKeyForPadding: function(t, r, b, l)
			{
				return (t||0) + " " + (r||0) + " " + (b||0) + " " + (l||0);
			}
		},
		toString: function()
		{
			this._updateValue();
			return this._value;
		},
		setOwner: function(o, setfunc)
		{
			this._owner = o;
			this._setter = setfunc;
			//this._callSetterForOwner();
		},
		_callSetterForOwner: function()
		{
			var o = this._owner;
			if ( o )
			{
				o[this._setter]();
			}
		},
		_getValueStr: function()
		{
			if ( this.isEmpty() )
			{
				this._strCache = {};
				return "";
			}
			var tmpArr = [], strCache = {};
			var propNms = this.getPropertiesNames();
			Eco.array.forEach(propNms, function(val, idx)
			{
				if (this[val] && !this[val]._is_empty)
				{
					strCache[val] = this[val]._value;
					tmpArr.push(val + ":" + this[val]._value);
				}
			}, this);
			this._strCache = strCache;
			return tmpArr.join("; ");
		},
		_parseInfo: function(val)
		{
			var linearr = val.split(";");
			var propname, propval,
				tmpstr, idx,
				arg = {};
			for (var i = 0, len = linearr.length; i < len; i++) 
			{
				tmpstr = linearr[i];
				idx = tmpstr.indexOf(":");
				propname = tmpstr.substring(0, idx).trim();
				propval = tmpstr.substring(idx + 1);

				if (propname) 
				{
					arg[propname] = propval;
				}
			}
			this.setProperties(arg);
		},
		_propertyChange: function(nm, newVal, oldVal)
		{
			this._changed = true;
			if ( newVal && !oldVal) this._property_cnt++;
			if ( !newVal && oldVal) this._property_cnt--;
			//this._callSetterForOwner();
		},
		_updateValue: function() //*
		{
			if ( this._changed )
			{
				var val = this._getValueStr();
				if (val != this._value) 
				{
					this._value = val;
				}
				this._changed = false;
			}
		},
		_getStrCache: function()
		{
			if ( this._changed )
			{
				var val = this._getValueStr();
				if (val != this._value) 
				{
					this._value = val;
				}
				this._changed = false;
			}
			return this._strCache;
		},
		_setValue: function(v)
		{
			if ( !v )
			{
				if ( !this._empty_cnt )
				{
					this._empty();
					//this._callSetterForOwner();
				}
				return;
			}
			var args;
			if ( Eco.isObject(v) && v.length != null ) //v is arguments.
			{
				args = v;
			}
			else
			{
				args = arguments;
			}
			var argLen = args.length;
			if ( argLen == 1 )
			{
				var val = args[0];
				if ( Eco.isString(val) ) 
				{
					val = val.trim();
					if ( val != this.toString() ) 
					{
						this._empty();
						if (val) 
						{
							this._parseInfo(val);
						}
					}
				}
				else if ( Eco.isObject(val) || Eco.isArray(val) )
				{
					this.setProperties(val);
				}
				else if ( val == null )
				{
					if ( !this._empty_cnt )
					{
						this._empty();
						//this._callSetterForOwner();
					}
				}
			}
			else if ( argLen > 2 )
			{
				this.setProperties.apply(this, args);
			}
		},
		_empty: function()
		{
			var propNms = this.getPropertiesNames(), changed = false;
			Eco.array.forEach(propNms, function(val, idx)
				{
					if ( this[val] )
					{
						this[val] = null;
						changed = true;
					}
				}, this);
			if ( changed )
			{
				this._changed = true;
			}
			this._property_cnt = 0;
		},
		isEmpty: function()
		{
			return !this._property_cnt;
		},
		equals: function(v)
		{
			if ( !v ) return false;
			if ( v == this ) return true;
			//if ( this._property_cnt != v._property_cnt ) return false; 
			var flag = true;
			Eco.array.Each(propNms, function(val, idx)
				{
					if ( this[val] != v[val] )
					{
						flag = false;
						return false;
					}
				}, this);
			return flag;
		},
		clone: function()
		{
			var tmpObj = new Eco.Graphic.Style();
			var props = this.getPropertiesValues();
			Eco.object.Each(props, function(propNm, val)
			{
				if ( val )
				{
					tmpObj[propNm] = val;
					tmpObj._property_cnt++;
					tmpObj._changed = true;
				}
			});
			return tmpObj;
		}
	}); //end of 'JsNamespace.declareClass("Eco.GraphicStyle", {'

	var properties = {
		"accessibility": nexacro.Style_accessibility,
		"strokepen": nexacro.Style_strokepen,
		"fillbrush": nexacro.Style_fillbrush,
		"fillgradation": nexacro.Style_gradation,
		"cursor": nexacro.Style_value,
		"opacity": nexacro.Style_value,
		"strokejoin": nexacro.Style_value, // bevel, miter, round
		"strokecap": nexacro.Style_value,//butt, round, square => flat, round, square
		"miterjoinlimit": nexacro.Style_value,
		"shadow": nexacro.Style_shadow,
		"align": nexacro.Style_align,
		"font": nexacro.Style_font,
		"color": nexacro.Style_color,
		"padding": nexacro.Style_padding
	};

	JsNamespace.addProperties(properties, Eco.Graphic.Style, function(prop, val) {
		var propsOfSubObj = (val ? val.prototype : null),
			typeNm = (propsOfSubObj ? propsOfSubObj._type_name.toLowerCase() : "value"),
			capitalize = Eco.string.capitalize,
			capitalStr = capitalize(typeNm),
			funcStr = "var argsLen = arguments.length, val, newobj;\n";

		funcStr += "if ( argsLen == 0 ) return;\n";
		funcStr += "if ( argsLen == 1 )\n";
		funcStr += "{\n";
		funcStr += "\tval = arguments[0];\n";
		funcStr += "\tif ( Eco.isString(val) )\n";
		funcStr += "\t{\n";
		funcStr += "\t\tnewobj = nexacro._getCached" + capitalStr + "Obj(val);\n";
		funcStr += "\t}\n";
		funcStr += "\telse if ( val instanceof nexacro.Style_" + typeNm + " )\n";
		funcStr += "\t{\n";
		funcStr += "\t\tnewobj = nexacro._getCached" + capitalStr + "Obj(val._value);\n";
		funcStr += "\t}\n";
		funcStr += "\telse\n";
		funcStr += "\t{\n";
		funcStr += "\t\tnewobj = nexacro._getCached" + capitalStr + "Obj(val + \"\");\n";
		funcStr += "\t}\n";
		funcStr += "}\n";
		funcStr += "else if ( argsLen > 1 )\n";
		funcStr += "{\n";
		funcStr += "\tvar pCls = Eco.Graphic.Style,\n";
		funcStr += "\t\tstrkey;\n";

		if ( typeNm == "accessibility" )
		{
			funcStr += "\tstrkey = pCls.getKeyForAccessibility(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4], arguments[5]);\n";
		}
		else if ( typeNm == "strokepen" )
		{
			funcStr += "\tstrkey = pCls.getKeyForStrokepen(arguments[0], arguments[1], arguments[2]);\n";
		}
		else if ( typeNm == "padding" )
		{
			funcStr += "\tstrkey = pCls.getKeyForPadding(arguments[0], arguments[1], arguments[2], arguments[3]);\n";
		}
		else if ( typeNm == "fillbrush" )
		{
			funcStr += "\tstrkey = pCls.getKeyForFillbrush(arguments[0], arguments[1]);\n";
		}
		else if ( typeNm == "gradation" )
		{
			funcStr += "\tstrkey = pCls.getKeyForFillgradation(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4], arguments[5], arguments[6], arguments[7]);\n";
		}
		else if ( typeNm == "value" )
		{
			funcStr += "\tstrkey = arguments[0] + \"\";\n";
		}
		else if ( typeNm == "shadow" )
		{
			funcStr += "\tstrkey = pCls.getKeyForShadow(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4]);\n";
		}
		else if ( typeNm == "align" )
		{
			funcStr += "\tstrkey = pCls.getKeyForAlign(arguments[0], arguments[1]);\n";
		}
		else if ( typeNm == "font" )
		{
			funcStr += "\tstrkey = pCls.getKeyForFont(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4], arguments[5], arguments[6]);\n";
		}
		else if ( typeNm == "color" )
		{
			funcStr += "\tstrkey = arguments[0] + \"\";\n";
		}

		funcStr += "\tnewobj = nexacro._getCached" + capitalStr + "Obj(strkey);\n";
		funcStr += "}\n";

		funcStr += "var oldobj = this." + prop + ";\n";
		funcStr += "if ( oldobj != newobj )\n";
		funcStr += "{\n";
		funcStr += "\tthis." + prop + " = newobj;\n";
		funcStr += "\tthis._propertyChange(\"" + prop + "\", newobj, oldobj);\n";
		funcStr += "}\n";

		return {
			"set": new Function(funcStr)
		};
	});
	
	JsNamespace.addMethods(properties, Eco.Graphic.Style, false, function(prop, val) {
		var propsOfSubObj = (val ? val.prototype : null),
			typeNm = (propsOfSubObj ? propsOfSubObj._type_name.toLowerCase() : "value"),
			capitalize = Eco.string.capitalize,
			res = [],
			mName,
			pCls = this,
			funcStr = "";
		if ( propsOfSubObj )
		{
			Eco.object.Each(propsOfSubObj, function(subProp, subVal) {
				if ( /^set_/.test(subProp) && Eco.isFunction(subVal) )
				{
					var propNm = subProp.substring(4);
					funcStr = "var styleobj = this." + prop + ";\n";
					funcStr += "var newobj = styleobj ? styleobj.clone() : (new nexacro.Style_" + typeNm + "());\n";
					funcStr += "newobj.set_" + propNm + "(v);\n";
					funcStr += "newobj = nexacro._registerCached" + capitalize(typeNm) + "Obj(newobj);\n";
					funcStr += "if ( !newobj.equals(styleobj) ) {\n";
					funcStr += "\tthis." + prop + " = newobj;\n";
					funcStr += "\tthis._propertyChange(\"" + prop + "\", newobj, styleobj);\n";
					funcStr += "}\n";
					mName = "set_" + prop + "_" + propNm;
					pCls[mName] = new Function("v", funcStr);
					res.push(mName);
				}
			});
		}
		return res; //이 코드 라인이 있어야 추가한 Method에 debug정보를 구성한다.
	});
} //end of 'if ( !JsNamespace.exist("Eco.GraphicStyle") )'

if ( !JsNamespace.exist("Eco.Graphic.Css") )
{
	/**
	 * @class Eco.GraphicStyle
	 * @classdesc width, height property를 가지는 Size Class.
	*/
	JsNamespace.declareClass("Eco.Graphic.Css", {
		"_type": "EcoGraphicCss",
		initialize: function()
		{
			if ( arguments.length )
			{
				this._setValue(arguments);
			}
		},
		statics: {
			getDefaultCss: function(status)
			{
				if ( !this._defaultCss )
				{
					var defaultCss = {},
						pCls = Eco.Graphic.Style;
					defaultCss["normal"] = pCls.getCached("font: Consolas,9; strokepen: 1 solid black; fillbrush: solid transparent; color: #444444ff; align:center middle;");
					defaultCss["selected"] = null;
					defaultCss["mouseover"] = pCls.getCached("strokepen: 1 solid red;");
					this._defaultCss = defaultCss;
				}
				return this._defaultCss[status];
			},
			getStyleSubProps: function()
			{
				var subprops = this._stylesubprops;
				if ( !subprops )
				{
					if ( this._defaultCss && this._defaultCss.normal)
					{
						subprops = this._defaultCss.normal.getPropertiesNames();
					}
					else
					{
						var tmpStyle = new Eco.Graphic.Style();
						subprops = tmpStyle.getPropertiesNames();
					}
					this._stylesubprops = subprops;
				}
				return subprops;
			}
		},
		properties: {
			normal: {
				"set": function(v)
				{
					if ( arguments.length == 0 ) return;
					var newStyle;
					if ( arguments.length == 1 )
					{
						pCls = Eco.Graphic.Style;
						if ( v instanceof pCls )
						{
							//newStyle = v.clone();
							newStyle = pCls.registerCached(v);
						}
						else
						{
							newStyle = pCls.getCached(v);
						}
					}
					else
					{
						newStyle = new Eco.Graphic.Style();
						newStyle._setValue(arguments);
						newStyle = pCls.registerCached(newStyle);
					}
					var oldStyle = this.normal;
					if ( newStyle != oldStyle )
					{
						this.normal = newStyle.isEmpty() ? null : newStyle;
						this._propertyChange("normal", this.normal, oldStyle);
						/*
						if ( oldStyle )
						{
							oldStyle.setOwner(null, null);
						}
						if ( newStyle )
						{
							newStyle.setOwner(this, "_setNormalStyleValue");
						}
						*/
					}
				},
				"get": function(isReal)
				{
					if ( isReal )
					{
						var n = this._realnormal;
						if ( !n )
						{
							//n = {};
							var pCls = Eco.Graphic.Css,
								pStyleCls = Eco.Graphic.Style;
							//	subpropNames = pCls.getStyleSubProps();
							var style = this.normal,
								keyCache,
								tmpVal;
							if ( style )
							{
								keyCache = style._getStrCache();
								/*
								Eco.array.forEach(subpropNames, function(v)
								{
									tmpVal = style[v];
									if ( tmpVal )
									{
										n[v] = tmpVal;
									}
								});
								*/
							}
							var defaultNormal = pCls.getDefaultCss("normal"),
								props;
							if ( defaultNormal )
							{
								var keyCache1 = defaultNormal._getStrCache();
								if ( keyCache )
								{
									Eco.object.merge(keyCache, keyCache1);
								}
								else
								{
									keyCache = keyCache1;
								}
								/*
								props = {};
								Eco.array.forEach(subpropNames, function(v)
								{
									tmpVal = defaultNormal[v];
									if ( tmpVal )
									{
										props[v] = tmpVal;
									}
								});
								n = Eco.object.merge(n, props);
								*/
							}
							this._normalkeyCache = keyCache;
							var tmpArr = [];
							Eco.object.Each(keyCache, function(pNm, val)
							{
								tmpArr.push(pNm + ": " + val);
							});
							n = pStyleCls.getCached(tmpArr.join("; "));
							this._realnormal = n;
						}
						return n;
					}
					else
					{
						return this.normal;
					}
				}
			},
			selected: {
				"set": function(v)
				{
					if ( arguments.length == 0 ) return;
					var newStyle;
					if ( arguments.length == 1 )
					{
						if ( v instanceof Eco.Graphic.Style )
						{
							newStyle = v.clone();
						}
						else
						{
							newStyle = new Eco.Graphic.Style(v);
						}
					}
					else
					{
						newStyle = new Eco.Graphic.Style();
						newStyle._setValue(arguments);
					}
					var oldStyle = this.selected;
					if ( !newStyle.equals(oldStyle) )
					{
						this.selected = newStyle.isEmpty() ? null : newStyle;
						this._propertyChange("selected", this.selected, oldStyle);
						if ( oldStyle )
						{
							oldStyle.setOwner(null, null);
						}
						if ( newStyle )
						{
							newStyle.setOwner(this, "_setSelectedStyleValue");
						}
					}
				},
				"get": function(isReal)
				{
					if ( isReal )
					{
						var s = this._realselected;
						if ( !s )
						{
							var style = this.selected;
							if ( style )
							{
								s = style.getPropertiesValues();
							}
							else
							{
								s = {};
							}
							var defaultSelected = Eco.Graphic.Css.getDefaultCss("selected"),
								props;
							if ( defaultSelected )
							{
								props = defaultSelected.getPropertiesValues();
								s = Eco.object.merge(s, props);
							}
							var realnormal = this.getNormal(true);
							if ( realnormal )
							{
								s = Eco.object.merge(s, realnormal);
							}
							this._realselected = s;
						}
						return s;
					}
					else
					{
						return this.selected;
					}
				}
			},
			mouseover: {
				"set": function(v)
				{
					if ( arguments.length == 0 ) return;
					var newStyle;
					if ( arguments.length == 1 )
					{
						if ( v instanceof Eco.Graphic.Style )
						{
							newStyle = v.clone();
						}
						else
						{
							newStyle = new Eco.Graphic.Style(v);
						}
					}
					else
					{
						newStyle = new Eco.Graphic.Style();
						newStyle._setValue(arguments);
					}
					var oldStyle = this.mouseover;
					if ( !newStyle.equals(oldStyle) )
					{
						this.mouseover = newStyle.isEmpty() ? null : newStyle;
						this._propertyChange("mouseover", this.mouseover, oldStyle);
						if ( oldStyle )
						{
							oldStyle.setOwner(null, null);
						}
						if ( newStyle )
						{
							newStyle.setOwner(this, "_setMouseoverStyleValue");
						}
					}
				},
				"get": function(isReal)
				{
					if ( isReal )
					{
						var mo = this._realmouseover;
						if ( !mo )
						{
							var style = this.mouseover;
							if ( style )
							{
								mo = style.getPropertiesValues();
							}
							else
							{
								mo = {};
							}
							var defaultMouseover = Eco.Graphic.Css.getDefaultCss("mouseover"),
								props;
							if ( defaultMouseover )
							{
								props = defaultMouseover.getPropertiesValues();
								mo = Eco.object.merge(mo, props);
							}
							var realnormal = this.getNormal(true);
							if ( realnormal )
							{
								mo = Eco.object.merge(mo, realnormal);
							}
							this._realmouseover = mo;
						}
						return mo;
					}
					else
					{
						return this.mouseover;
					}
				}
			}
		},
		toString: function()
		{
			this._updateValue();
			return this._value;
		},
		setOwner: function(o, setfunc)
		{
			this._owner = o;
			this._setter = setfunc;
			//this._callSetterForOwner();
		},
		_callSetterForOwner: function(pseudo)
		{
			var o = this._owner;
			if ( o )
			{
				o[this._setter](pseudo);
			}
		},
		_getValueStr: function()
		{
			var tmpArr = [];
			var propNms = this.getPropertiesNames();
			Eco.array.forEach(propNms, function(val, idx)
			{
				var s = this[val];
				if ( s )
				{
					var value = s.toString();
					if ( value.length )
					{
						if ( val == "normal" )
						{
							tmpArr.push(value.trimRight());
						}
						else
						{
							tmpArr.push(":" + val + " {" + value.trimRight() + "}");
						}
					}
				}
			}, this);
			return tmpArr.join(" ");
		},
		_parseInfo: function(v)
		{
			this._empty();
			var blocks = v.split("}");
			var s = blocks[0].trim();

			blocks.pop();

			var i, len = blocks.length;
			var definition_block, pseudo, normal_style,
				args = {};

			definition_block = s.split("{");
			normal_style = definition_block[0].substring(0, definition_block[0].lastIndexOf(";") + 1).trim();

			if (normal_style.length == 0)
			{
				normal_style = definition_block[0].substring(0, definition_block[0].length).trim();
			}
			args["normal"] = normal_style;
			if (len > 0) 
			{
				var setFn, capitalize = Eco.string.capitalize;
				for (i = 0; i < len; i++) 
				{
					definition_block = blocks[i].split("{");
					pseudo = definition_block[0].substring(definition_block[0].lastIndexOf(":") + 1).trim();
					args[pseudo] = definition_block[1];
				}
			}
			this.setProperties(args);
		},
		_propertyChange: function(nm, newVal, oldVal)
		{
			this._changed = true;
			this["_real" + nm] = null;
			this._callSetterForOwner(nm);
		},
		_updateValue: function()
		{
			if ( this._changed )
			{
				var val = this._getValueStr();
				if (val != this._value) 
				{
					this._value = val;
					if ( this._owner )
					{
						this._owner._updateStyles(changedprops, subprops);
					}
				}
				this._changed = false;
			}
		},
		_setValue: function(v)
		{
			if ( !v )
			{
				this._empty();
				this._callSetterForOwner();
				return;
			}
			var args;
			if ( Eco.isObject(v) && v.length != null ) //v is arguments.
			{
				args = v;
			}
			else
			{
				args = arguments;
			}
			var argLen = args.length;

			if ( argLen == 1 )
			{
				var val = v[0];
				if ( Eco.isObject(val) || Eco.isArray(val) )
				{
					this.setProperties(val);
				}
				else if ( Eco.isString(val) ) 
				{
					var val = val.trim();
					if ( val != this.toString() ) 
					{
						this._empty();
						if (val) 
						{
							this._parseInfo(val);
						}
					}
				}
				else if ( val == null )
				{
					this._empty();
					this._callSetterForOwner();
				}
			}
			else if ( argLen > 2 )
			{
				this.setProperties.apply(this, v);
			}
		},
		_empty: function()
		{
			var propNms = this.getPropertiesNames(), changed = false;
			Eco.array.forEach(propNms, function(val, idx)
				{
					if ( this[val] )
					{
						this[val] = null;
						this["_real" + val] = null;
						changed = true;
					}
				}, this);
			if ( changed )
			{
				this._changed = true;
			}
		},
		equals: function(v)
		{
			if ( !v ) return false;
			if ( v == this ) return true;
			var flag = true;
			var propNms = this.getPropertiesNames();
			Eco.array.Each(propNms, function(val, idx)
				{
					if ( this[val] )
					{
						flag = this[val].equals(v[val]);
						if ( !flag ) return false;
					}
					else if ( v[val] )
					{
						flag = v[val].equals(this[val]);
						if ( !flag ) return false;
					}
				}, this);
			return flag;
		},
		clone: function()
		{
			var tmpObj = new Eco.Graphic.Css();
			var props = this.getPropertiesValues();
			Eco.object.Each(props, function(propNm, val)
				{
					if ( val )
					{
						tmpObj[val] = val.clone();
						tmpObj._changed = true;
					}
				}, this);
			return tmpObj;
		},
		_setNormalStyleValue: function()
		{
			this._propertyChange("normal");
		},
		_setSelectedStyleValue: function()
		{
			this._propertyChange("selected");
		},
		_setMouseoverStyleValue: function()
		{
			this._propertyChange("mouseover");
		}
	});
}
		