/**
 * @fileoverview Shapes(Rect, Ellipse, Text, Image, Connector) Class 정의.
 */
if ( !JsNamespace.exist("Eco.GraphicShape") )
{
	JsNamespace.declareClass("Eco.GraphicShape", {
		"extends": "Eco.GraphicElement",
		/**
		 * Graphic 생성자(constructor)
		 * @param {Graphic} g 생성되는 요소가 속해야 하는 Graphic 객체
		 * @param {EcoSys.Point} pt 요소가 위치하는 지점 좌표(Group이나, Layer를 0,0 기준으로 설정됨)
		 * @class Graphic
		 * @classdesc 다양한 shape를 그리고 operation 처리하는 기능이 있는 Class.
		 * @constructor Graphic
		*/
		initialize: function()
		{
			this.callParent(arguments);
			var read = this.__readArgs;
			var sz = arguments[read];
			if ( sz instanceof Eco.Size )
			{
				read++;
				this._size = sz.clone();
				this._drawflags |= 2;
			}
			else if ( Eco.isNumber(sz) && Eco.isNumber(arguments[read + 1]) )
			{
				this._size = new Eco.Size(sz, arguments[read + 1]);
				read += 2;
				this._drawflags |= 2;
			}
			this.__readArgs = read;
		},
		"isEmpty": function()
		{
			var sz = this._size;
			if ( sz )
			{
				return sz.width <= 0 || sz.height <= 0;
			}
			return true;
		},
		_getRect: function(bStrokeWidth, iscurrent)
		{
			var rect = this._rect;
			if ( iscurrent ) rect = null;
			if ( !rect )
			{
				//rect = new Eco.Rectangle(this._size).setCenter(0, 0);
				rect = new Eco.Rectangle(this._size);
				if ( !iscurrent ) this._rect = rect;
			}
			if ( bStrokeWidth )
			{
				rect = rect.expand(this.getStrokeWidth()/2);
			}
			return rect;
		},
		properties: {
			"width": {
				"get": function()
				{
					var sz = this._size;
					if ( sz )
					{
						return sz.width;
					}
					return 0;
				},
				"set": function(val)
				{
					var sz = this._size;
					if ( sz )
					{
						if ( sz.width != val )
						{
							sz.width = val;
							this._drawflags |= 2;
						}
					}
					else
					{
						this._size = new Eco.Size(val, 0);
						this._drawflags |= 2;
					}
				}
			},
			"height": {
				"get": function()
				{
					var sz = this._size;
					if ( sz )
					{
						return sz.height;
					}
					return 0;
				},
				"set": function(val)
				{
					var sz = this._size;
					if ( sz )
					{
						if ( sz.height != val )
						{
							sz.height = val;
							this._drawflags |= 2;
						}
					}
					else
					{
						this._size = new Eco.Size(0, val);
						this._drawflags |= 2;
					}
				}
			},
			"size": {
				memberName: "_size",
				"get": function(bRetArray)
				{
					var sz = this._size;
					if ( bRetArray )
					{
						return [sz.width, sz.height];
					}
					else
					{
						return sz;
					}
				},
				"set": function()
				{
					var argLen = arguments.length;
					if ( argLen == 0 ) return; 
					var sz;
					if ( arguments[0] instanceof Eco.Size )
					{
						sz = arguments[0];
					}
					else
					{
						sz = new Eco.Size(arguments[0], arguments[1]);
					}
					if ( !this._size )
					{
						this._size = sz.clone();
						this._drawflags |= 2; //sizechanged
					}
					else if ( !this._size.equals(sz) )
					{
						this._size.set(sz.width, sz.height);
						this._drawflags |= 2; //sizechanged
					}
				}
			}
		}
	});
}

if ( !JsNamespace.exist("Eco.GraphicRect") )
{
	JsNamespace.declareClass("Eco.GraphicRect", {
		"extends": "Eco.GraphicShape",
		/**
		 * Graphic 생성자(constructor)
		 * @param {Graphic} g 생성되는 요소가 속해야 하는 Graphic 객체
		 * @param {EcoSys.Point} pt 요소가 위치하는 지점 좌표(Group이나, Layer를 0,0 기준으로 설정됨)
		 * @class Graphic
		 * @classdesc 다양한 shape를 그리고 operation 처리하는 기능이 있는 Class.
		 * @constructor Graphic
		*/
		initialize: function()
		{
			this._type = "Rect";
			this.callParent(arguments);
			var read = this.__readArgs;
			if ( arguments[read] > 0 )
			{
				this._radiusx = arguments[read];
			}
			else
			{
				this._radiusx = 0;
			}
			if ( arguments[read + 1] > 0 )
			{
				this._radiusy = arguments[read + 1];
			}
			else
			{
				this._radiusy = 0;
			}
			this._drawflags |= 8; //stylechanged
		},
		properties: {
			"radiusx": {
				memberName: "_radiusx",
				"set": function()
				{
					var argLen = arguments.length;
					if ( argLen == 0 ) return; 
					if ( arguments[0] >= 0 )
					{
						if ( this._radiusx != arguments[0] )
						{
							this._radiusx = arguments[0];
							this._drawflags |= 8; //stylechanged
						}
					}
				}
			},
			"radiusy": {
				memberName: "_radiusy",
				"set": function()
				{
					var argLen = arguments.length;
					if ( argLen == 0 ) return; 
					if ( arguments[0] >= 0 )
					{
						if ( this._radiusy != arguments[0] )
						{
							this._radiusy = arguments[0];
							this._drawflags |= 8; //stylechanged
						}
					}
				}
			}
		},
		_drawShape: function(ctx)
		{
			var sz = this._size,
				rx = this._radiusx || 0,
				ry = this._radiusy || 0,
				w = sz.width,
				h = sz.height,
				x = 0, y = 0,
				//x = -w / 2,
				//y = -h / 2,
				isRounded = rx !== 0 || ry !== 0;
			var gap = 0;
			if ( this._isOddStrokeWidth )
			{
				gap = 0.5;
			}

			if ( isRounded )
			{
				ctx.beginPath();
				ctx.moveTo(x + rx + gap, y + gap);
				ctx.lineTo(x + w - rx + gap, y + gap);
				ctx.quadraticCurveTo(x + w + gap, y + gap, x + w + gap, y + ry + gap, x + w + gap, y + ry + gap);

				ctx.lineTo(x + w + gap, y + h - ry + gap);
				ctx.quadraticCurveTo(x + w + gap, y + h + gap, x + w - rx + gap, y + h + gap, x + w - rx + gap, y + h + gap);

				ctx.lineTo(x + rx + gap, y + h + gap);
				ctx.quadraticCurveTo(x + gap, y + h + gap, x + gap, y + h - ry + gap, x + gap, y + h - ry + gap);

				ctx.lineTo(x + gap, y + ry + gap);
				ctx.quadraticCurveTo(x + gap, y + gap, x + rx + gap, y + gap, x + rx + gap, y + gap);

				ctx.closePath();
			}
			else
			{
				ctx.beginPath();
				ctx.rect(x + gap, y + gap, w, h);
				ctx.closePath();
			}
		}
	});
}


if ( !JsNamespace.exist("Eco.GraphicEllipse") )
{
	JsNamespace.declareClass("Eco.GraphicEllipse", {
		"extends": "Eco.GraphicShape",
		/**
		 * Graphic 생성자(constructor)
		 * @param {Graphic} g 생성되는 요소가 속해야 하는 Graphic 객체
		 * @param {EcoSys.Point} pt 요소가 위치하는 지점 좌표(Group이나, Layer를 0,0 기준으로 설정됨)
		 * @class Graphic
		 * @classdesc 다양한 shape를 그리고 operation 처리하는 기능이 있는 Class.
		 * @constructor Graphic
		*/
		initialize: function()
		{
			this._type = "Ellipse";
			this.callParent(arguments);
		},
		properties: {
			cx: {
				"get": function()
				{
					return this._x;
				},
				"set": function(val)
				{
					if ( this._x != val )
					{
						this.translate(val - this._x, 0);
						this._x = val;
					}
				}
			},
			cy: {
				"get": function()
				{
					return this._y;
				},
				"set": function(val)
				{
					if ( this._y != val )
					{
						this.translate(0, val - this._y);
						this._y = val;
					}
				}
			},
			center: {
				"get": function(bRetArray)
				{
					if ( bRetArray )
					{
						return [this._x, this._y];
					}
					else
					{
						return new Eco.Point(this._x, this._y);
					}
				},
				"set": function(val)
				{
					var argLen = arguments.length;
					if ( argLen == 0 ) return;
					if ( val == null ) //default
					{
						this.translate(-this._x, -this._y);
						this._x = 0;
						this._y = 0;
					}
					else if ( !(val instanceof Eco.Point) )
					{
						var y = arguments[1];
						if ( this._x != val || this._y != y )
						{
							this.translate(val - this._x, y - this._y);
							this._x = val;
							this._y = y;
						}
					}
					else
					{
						if ( this._x != val.x || this._y != val.y )
						{
							this.translate(val.x - this._x, val.y - this._y);
							this._x = val.x;
							this._y = val.y;
						}
					}
				}
			}
		},
		_drawShape: function(ctx)
		{
			var sz = this._size,
				w = sz.width,
				h = sz.height;
			if ( w == h )
			{
				var xm = w / 2;       // y-middle
				ctx.beginPath();
				//ctx.arc(-xm, -xm, xm, 0, 2*Math.PI, false);
				ctx.arc(0, 0, (w + h) / 4, 0, Math.PI * 2, true);
			}
			else
			{
				ctx.beginPath();
				var mx = w / 2,
					my = h / 2,
					kappa = Eco.MathUtil._KAPPA,
					cx = mx * kappa,
					cy = my * kappa;
				ctx.moveTo(-mx, 0);
				ctx.bezierCurveTo(-mx, -cy, -cx, -my, 0, -my);
				ctx.bezierCurveTo(cx, -my, mx, -cy, mx, 0);
				ctx.bezierCurveTo(mx, cy, cx, my, 0, my);
				ctx.bezierCurveTo(-cx, my, -mx, cy, -mx, 0);
				ctx.closePath();
			}
		},
		_getRect: function(bStrokeWidth, iscurrent)
		{
			var rect = this._rect;
			if ( iscurrent ) rect = null;
			if ( !rect )
			{
				rect = new Eco.Rectangle(this._size).setCenter(0, 0);
				//rect = new Eco.Rectangle(this._size);
				if ( !iscurrent ) this._rect = rect;
			}
			if ( bStrokeWidth )
			{
				rect = rect.expand(this.getStrokeWidth()/2);
			}
			return rect;
		},
		hitTest: function(pt)
		{
			var sz = this._size;
			if ( sz )
			{
				/* This is a more general form of the circle equation
				*
				* X^2/a^2 + Y^2/b^2 <= 1
				*/
				return pt.divide(sz).getLength() <= 0.5;
			}
			return false;
		}
	});
}

if ( !JsNamespace.exist("Eco.GraphicImage") )
{
	JsNamespace.declareClass("Eco.GraphicImage", {
		"extends": "Eco.GraphicShape",
		/**
		 * Graphic 생성자(constructor)
		 * @param {Graphic} g 생성되는 요소가 속해야 하는 Graphic 객체
		 * @param {EcoSys.Point} pt 요소가 위치하는 지점 좌표(Group이나, Layer를 0,0 기준으로 설정됨)
		 * @class Graphic
		 * @classdesc 다양한 shape를 그리고 operation 처리하는 기능이 있는 Class.
		 * @constructor Graphic
		*/
		initialize: function()
		{
			this._type = "Image";
			this.callParent(arguments);
			var read = this.__readArgs;
			if ( arguments[read] )
			{
				this._setProp("src", arguments[read]);
			}
			return this; //initialize는 반드시 이 코드 라인을 작성한다.
		},
		properties: {
			originalImageSize: {
				"get": function()
				{
					return [this._orgWidth, this._orgHeight];
				},
				"set": function()
				{
				}
			},
			src: {
				"get": function()
				{
					return this._src;
				},
				"set": function(val)
				{
					if ( this._src != val )
					{
						this._src = val;
						if ( val )
						{
							this._imageloaded = false;
							if (nexacro.Browser != "Runtime")
							{
								var rurl = this._getRealImageUrl(val);
								var cacheimages = Eco._cacheimages,
									imageobj;
								if ( !this._imgobj ) this._imgobj = {}; 
								if ( cacheimages && cacheimages[rurl] )
								{
									imageobj = cacheimages[rurl];
									this._imgobj._handle = imageobj;
									var w = imageobj.naturalWidth||imageobj.width,
										h = imageobj.naturalHeight||imageobj.height;
									this._doingSomethingAfterImageloaded(w, h);
								}
								else
								{
									imageobj = new Image();
									var that = this,
										bindfn = function() {
											nexacro._stopSysObserving(this, "load", "onload", bindfn);
											that._imageload_callback(rurl, imageobj);
										};
									nexacro._observeSysEvent(imageobj, "load", "onload", bindfn);
									imageobj.src = rurl;
								}
			               //nexacro._observeSysEvent(imgnode, "error", "onerror", nexacro._imgloadhandler_onerror_forward);
							}
							else
							{
								if ( !this._imgobj ) this._imgobj = {}; 
								this._imgobj.src = val;
								this._imgobj._handle = nexacro._getImageObject(val,  this._imageload_callback, this);
							}
						}
						else
						{
							this._imgobj = null;
							this._imageloaded = true;
						}
						this._drawflags |= 4; //contentschanged
					}
				}
			}
		},
		_getRealImageUrl: function(url)
		{
			if (url.substring(0, 4).toLowerCase() == "url(")
			{
			   url = url.substring(5, url.length - 2);
			}
			if (!url || !url.length)
				return null;

			return nexacro._getImageLocation(url);
		},
		_imageload_callback: function(url, w, h, imgnode)
		{
			if (nexacro.Browser != "Runtime")
			{
					var cacheimages = Eco._cacheimages,
						imgobj;
					if ( Eco._cacheimagescnt == null )
					{
						Eco._cacheimagescnt = 0;
						Eco._cacheimagesCapacity = 30;
					}
					if ( !cacheimages )
					{
						cacheimages = {};
						Eco._cacheimages = cacheimages;
					}
					
					var width, height;

					if ('naturalHeight' in w)
					{
						if (w.naturalHeight + w.naturalWidth === 0)
						{
							return;
						}
						width = w.naturalWidth;
						height = w.naturalHeight;
						
					}
					else if ( w.width + w.height == 0 )
					{
						return;
					}
					else
					{
						width = w.width;
						height = w.height;
					}
					this._imgobj._handle = w;
					cacheimages[url] = w;
					Eco._cacheimagescnt++;
					if ( Eco._cacheimagescnt > Eco._cacheimagesCapacity )
					{
						Eco.object.Each(cacheimages, function(prop, val, object) {
							delete object[prop];
							Eco._cacheimagescnt--;
							if ( Eco._cacheimagescnt < Eco._cacheimagesCapacity )
							{
								return false;
							}
						});
					}
					this._orgWidth = width;
					this._orgHeight = height;
					//TODO: have to fire callback event.  
					this._doingSomethingAfterImageloaded(width, height);
			}
			else
			{
				this._orgWidth = w;
				this._orgHeight = h;
				//TODO: have to fire callback event.  
				this._doingSomethingAfterImageloaded(w, h);
			}
		},
		_doingSomethingAfterImageloaded: function(w, h)
		{
			var sz = this._size;
			this._imageloaded = true;
			if ( sz == null )
			{
				this._size = new Eco.Size(w, h);
				this._drawflags |= 2;
				if ( this._needRedarw )
				{
					this._drawflags |= 4; //contentschanged
					var layer = this._getOwnerLayer(),
						graph = layer.Parent;
					graph.delayTask();
					this._needRedarw = false;
				}
				return;
			}
			if ( sz.width <= 0 )
			{
				sz.width = w;
				this._drawflags |= 2;
			}
			if ( sz.height <= 0 )
			{
				sz.height = h;
				this._drawflags |= 2;
			}
			if ( this._needRedarw )
			{
				this._drawflags |= 4; //contentschanged
				var layer = this._getOwnerLayer(),
					graph = layer.Parent;
				graph.delayTask();
				this._needRedarw = false;
			}
		},
		"isEmpty": function()
		{
			if ( this._imgobj )
			{
				var sz = this._size;
				if ( sz )
				{
					return sz.width <= 0 || sz.height <= 0;
				}
				if ( this._imageloaded ) return true;
				else return false;
			}
			return true;
		},
		_drawShape: function(ctx)
		{
			if ( !this.isEmpty() )
			{
				if ( this._imageloaded )
				{
					var sz = this._size,
						w = sz.width,
						h = sz.height;
					ctx.drawImage(this._imgobj, 0, 0, w, h);
				}
				else
				{
					this._needRedarw = true;
				}
			}
		}
	});
}

if ( !JsNamespace.exist("Eco.GraphicText") )
{
	JsNamespace.declareClass("Eco.GraphicText", {
		"extends": "Eco.GraphicElement",
		/**
		 * Graphic 생성자(constructor)
		 * @param {Graphic} g 생성되는 요소가 속해야 하는 Graphic 객체
		 * @param {EcoSys.Point} pt 요소가 위치하는 지점 좌표(Group이나, Layer를 0,0 기준으로 설정됨)
		 * @class Graphic
		 * @classdesc 다양한 shape를 그리고 operation 처리하는 기능이 있는 Class.
		 * @constructor Graphic
		*/
		initialize: function()
		{
			this._type = "Text";
			this.callParent(arguments);
			var read = this.__readArgs;
			if ( arguments[read] != null )
			{
				this.setText(arguments[read]);
			}
		},
		properties: {
			"font": {
				"set": function()
				{					
					var argsLen = arguments.length, val, newobj;
					if ( argsLen == 0 ) return;
					if ( argsLen == 1 )
					{
						val = arguments[0];
						if ( Eco.isString(val) )
						{
							newobj = nexacro._getCachedFontObj(val);
						}
						else if ( val instanceof nexacro.Style_font )
						{
							newobj = nexacro._getCachedFontObj(val._value);
						}
						else if ( Eco.isObject(val) )
						{
							val = Eco.Graphic.Style.getKeyForFont(val);
							newobj = nexacro._getCachedFontObj(val);
						}
						else if ( Eco.isArray(val) )
						{
							val = Eco.Graphic.Style.getKeyForFont(val[0], val[1], val[2]);
							newobj = nexacro._getCachedFontObj(val);
						}
						else
						{
							newobj = null;
						}
					}
					else
					{
						val = Eco.Graphic.Style.getKeyForFont(arguments[0], arguments[1], arguments[2]);
						newobj = nexacro._getCachedFontObj(val);
					}
					var s = this._style,
						oldobj = s.font;
					if ( oldobj != newobj )
					{
						s.font = newobj;
						this._drawflags |= (2|8); //sizechanged, stylechanged
						this._changedFont = true;
					}
				},
				"get": function(bRetArray)
				{
					var val = this._style.font;
					if ( bRetArray )
					{
						if ( val )
						{
							var arr = [val.face, val.size],
								arr_type = [];
							if (val._bold)
							{
								arr_type.push("bold");
							}
							if (val._italic)
							{
								arr_type.push("italic");
							}
							if (val._underline)
							{
								arr_type.push("underline");
							}
							if (val._strikeout)
							{
								arr_type.push("strikeout");
							}
							if (val._antialias)
							{
								arr_type.push("antialias");
							}
							arr.push(arr_type.join(" "));
							return arr;
						}
						return [];
					}
					if ( val )
					{
						return val._value;
					}
					return null;
				}
			},
			"halign": {
				value: "left",
				"set": function(val)
				{
					if (!/^(left|center|right)$/.test(val))
					{
						return;
					}
					if ( this.halign != val )
					{
						this.halign = val;
						this._drawflags |= (1|8); //stylechanged
						this._alignChanged = true;
					}
				},
			},
			"valign": {
				value: "bottom",
				"set": function(val)
				{
					if (!/^(top|middle|bottom)$/.test(val))
					{
						return;
					}
					if ( this.valign != val )
					{
						this.valign = val;
						this._drawflags |= (1|8); //stylechanged
						this._alignChanged = true;
					}
				},
			},
			"text": {
				"set": function(val)
				{
					if ( !Eco.isString(val) )
					{
						if ( !Eco.isEmpty(val) )
						{
							val = val.toString();
						}
						else
						{
							val = "";
						}
					}
					if ( this._txt != val )
					{
						this._txt = val;
						this._drawflags |= 4; //contentschanged
						this._textChanged = true;
					}
				},
				"get": function()
				{
					return this._txt;
				}
			},
			dx: {
				"get": function()
				{
					return this._dx;
				},
				"set": function(val)
				{
					if ( this._dx != val )
					{
						this.translate(val - (this._dx||0), 0);
						this._dx = val;
					}
				}
			},
			dy: {
				"get": function()
				{
					return this._dy;
				},
				"set": function(val)
				{
					if ( this._dy != val )
					{
						this.translate(0, val - (this._dy||0));
						this._dy = val;
					}
				}
			},
			"wordwrap": {
				"set": function(val)
				{
					if ( this._wordwrap !== val )
					{
						this._wordwrap = !!val;
						this._drawflags |= 4; //contentschanged
						this._wordwrapChanged = true;
					}
				},
				"get": function()
				{
					return this._wordwrap;
				}
			},
			"width": {
				"set": function(val)
				{
					if ( !Eco.isNumber(val) ) return;
					if ( this._width != val )
					{
						this._width = val;
						this._drawflags |= 4; //contentschanged
						this._widthChanged = true;
					}
				},
				"get": function()
				{
					return this._width;
				}
			}
		},
		_updateStyles: function(ctx, sizeChanged)
		{
			this._updateText();
			this.callParent([ctx, sizeChanged]);
		},
		_updateText: function()
		{
			var sysstyle = this._renderstyle,
				style = this._style;
			if ( !sysstyle )
			{
				sysstyle = {};
				this._renderstyle = sysstyle;
			}

			if ( this._textChanged || this._changedFont || this._wordwrapChanged || this._widthChanged )
			{
				var font = style.font;
				if ( this._changedFont )
				{
					Eco._updateFontInCanvas(font, sysstyle);
					if ( font )
					{
						var sz = Eco.XComp.PositionSize.getTextSize(font, "Wj", undefined, undefined, true);
						this._lineHeight = sz[1];
					}
					else
					{
						this._lineHeight = 0;
					}
					this._changedFont = false;
				}
				var txt = this._txt;
				if ( font && txt && txt.length && this._lineHeight )
				{
					this._lines = Eco._getLines(font, txt, this._wordwrap, this._width);
					this._txtSize = Eco._getTextSize(font, this._lines, this._lineHeight);
				}
				else
				{
					this._lines = null;
					this._txtSize = [0, 0];
				}
				this._textChanged = false;
				this._widthChanged = false;
				this._wordwrapChanged = false;
			}
		},
		"isEmpty": function()
		{
			var sz = this._getRect();
			return sz.width <= 0 || sz.height <= 0;
		},
		_getRect: function(bStrokeWidth, iscurrent)
		{
			var rect = this._rect;
			if ( iscurrent ) rect = null;
			if ( !rect || this._changedFont || this._textChanged || this._wordwrapChanged || this._widthChanged || this._alignChanged )
			{
				this._updateText();
				var txtSz = this._txtSize;
				//rect = new Eco.Rectangle(new Eco.Size(txtSz[0], txtSz[1])).setCenter(0, 0);
				rect = new Eco.Rectangle(new Eco.Size(txtSz[0], txtSz[1]));
				switch ( this.halign )
				{
					case "left" :
						break;
					case "center" :
						rect.x = -(txtSz[0]/2);
						break;
					case "right" :
						rect.x = -txtSz[0];
						break;
				}
				switch ( this.valign )
				{
					case "top" :
						break;
					case "middle" :
						rect.y = -(txtSz[1]/2);
						break;
					case "bottom" :
						rect.y = -txtSz[1];
						break;
				}
				if ( !iscurrent ) this._rect = rect;
				this._alignChanged = false;
			}
			return rect;
		},
		_render: function(ctx)
		{
			var style =	this._renderstyle,
				m = this._globalMatrix;
			if ( style && m )
			{
				this._painted |= 1;
				ctx.save();
				m.applyToContext(ctx);
				var clipItems = this._clipItems; //, isClip;
				if ( clipItems && clipItems.length )
				{
					ctx.beginPath();
					for ( var i = 0, len =  clipItems.length; i < len ; i++ )
					{
						var rect = clipItems[i];
						ctx.rect(rect.x, rect.y, rect.width, rect.height);
						//console.log(rect.toString(), this);
					}
					//isClip = true;
					ctx.closePath();
					ctx.clip();
					//if ( isClip ) ctx.clip();
				}
				ctx._applystyles(style);

				var fillColor = style._fillcolor,
					strokeColor = style._strokecolor;

				var lineHeight = this._lineHeight,
					txtSize = this._txtSize,
					lines = this._lines,
					x = 0;
				if ( lines )
				{
					switch ( this.halign )
					{
						case "left" :
							ctx.setElementTextAlign("start");
							break;
						case "center" :
							ctx.setElementTextAlign("center");
							//x = txtSize[0]/2; 
							break;
						case "right" :
							ctx.setElementTextAlign("end");
							//x = txtSize[0]; 
							break;
					}
					switch ( this.valign )
					{
						case "top" :
							//x = 0;
							//ctx.setElementTextBaseline("top");
							ctx.setElementTextBaseline("hanging");
							if ( fillColor )
							{
								for (var i = 0, len = lines.length; i < len; i++)
								{
									ctx.fillText(lines[i], 0, i*lineHeight);
								}
								this._painted |= 4;
							}
							break;
						case "middle" :
							ctx.setElementTextBaseline("middle");
							if ( fillColor )
							{
								var len = lines.length,
									yGap = (len - 1)*lineHeight/2;
								for (var i = 0 ; i < len; i++)
								{
									ctx.fillText(lines[i], 0, i*lineHeight - yGap);
								}
								this._painted |= 4;
							}
							break;
						case "bottom" :
							//ctx.setElementTextBaseline("bottom");
							ctx.setElementTextBaseline("alphabetic");
							if ( fillColor )
							{
								var len = lines.length,
									yGap = (len - 1)*lineHeight;
								for (var i = 0 ; i < len; i++)
								{
									ctx.fillText(lines[i], 0, i*lineHeight - yGap);
									//console.log(i*lineHeight - yGap, yGap, lineHeight);
								}
								this._painted |= 4;
							}
							break;
					}

				}
				ctx.restore();
			}
		}
	});
}
