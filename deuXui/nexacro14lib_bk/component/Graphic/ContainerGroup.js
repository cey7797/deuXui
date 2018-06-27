/**
 * @fileoverview Graphic Class 정의.
 */

if ( !JsNamespace.exist("Eco.GraphicGroup") )
{
	JsNamespace.declareClass("Eco.GraphicGroup", {
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
			//this.callParent(arguments);
			this._type = "Group";
			this._childchanged = 0;
			this.callParent(arguments);
		}
	});
}

if ( !JsNamespace.exist("Eco.GraphicLayer") )
{
	Eco._IE8ANDLOWER = (nexacro.Browser == "IE" && nexacro.BrowserVersion <= 8);

	JsNamespace.declareClass("Eco.GraphicLayer", {
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
			Eco.HashMap.getHashKey("ge", this);
			this._type = "Layer";
			this._renderHint = "all";
			this._treechange = 0;
			this._childchanged = 0;
			this._visible = true;
			this._dirtyRectsMgr = new Eco.RectsManager();
		},
		_getOwnerLayer: function()
		{
			return this;
		},
		_onTreeChanged: function()
		{
			this._treechange++;
		},
		getItemsInRect: function(rect)
		{
			var res = [];
			this._getItemsInRect(this, rect, res);
			return res;
		},
		_getItemsInRect: function(p, rect, results)
		{
			var quadtree = p._quadtree;
			if ( quadtree )
			{
				var m = p._globalMatrix||p._matrix,
					invertM = m.inverted()||m,
					localRect = invertM._transformBounds(rect);

				var items = quadtree.query(localRect),
					item;
				items.sort(function(a, b) {return b._index - a._index});
				for ( var i = 0, len = items.length ; i < len ; i++ )
				{
					item = items[i];
					results[results.length] = item;
					if ( item._firstChild )
					{
						this._getItemsInRect(item, rect, results);
					}
				}
			}
		},
		getGElements: function()
		{
			var totalItems = this._totalItems;
			if ( !totalItems || (this._treechange != totalItems._treechange))
			{
				totalItems = [];
				this._level = 0;
				var node = this._firstChild,
					idx = 0;
				for ( ; node ; node = node._nextSibling )
				{
					node._index = idx;
					this._getGElements(totalItems, node, 1);
					idx++;
				}
				this._totalItems = totalItems;
				totalItems._treechange = this._treechange;
			}
			return totalItems;
		},
		_getGElements: function(arr, node, level)
		{
			node._level = level;
			arr[arr.length] = node;
			if ( node = node._firstChild )
			{
				level++;
				var idx = 0;
				for ( ; node ; node = node._nextSibling )
				{
					node._index = idx;
					this._getGElements(arr, node, level);
					idx++;
				}
			}
		},
		setItemsInDirtyRect: function(rect)
		{
			//console.log("startRect:", rect.toString());
			this._setItemsInDirtyRect(this, rect);
		},
		_setItemsInDirtyRect: function(p, rect)
		{
			var quadtree = p._quadtree;
			if ( quadtree )
			{
				var m = p._globalMatrix||p._matrix,
					invertM = m.inverted()||m,
					localRect = invertM._transformBounds(rect);

				var items = quadtree.query(localRect),
					item, invertPt;
				for ( var i = 0, len = items.length ; i < len ; i++ )
				{
					item = items[i];
					//console.log("dirtyItem:", item);
					item._painting |= 4;
					if ( item._firstChild )
					{
						this._setItemsInDirtyRect(item, rect);
					}
				}
			}
		},
		_setGlobalTransforms: function(isDebug)
		{
			if ( isDebug ) Eco.Logger.startElapsed();
			var totalItems = this.getGElements(),
				p = this.Parent,
				viewport = p._viewport,
				drawflags = viewport._drawflags,
				m;

			if ( this._matrix )
			{
				drawflags |= this._drawflags;
				m = viewport._matrix;
				this._globalMatrix = ( m ?
					(m.clone()).concatenate(this._matrix) : this._matrix.clone() );
			}
			else
			{
				m = viewport._matrix;
				this._globalMatrix = m.clone();
			}
			m = this._globalMatrix;

			if ( isDebug ) Eco.Logger.info({message: "getElements create 시간 !!!", elapsed: true});
			if ( isDebug ) Eco.Logger.startElapsed();
			var transformStack = [m],
				transformChangedStack = [(drawflags & 1)],
				visibleStack = [true],
				opacityStack = [1],
				item, alpha,
				level, upLevel;
			for ( var i = 0, len = totalItems.length ; i < len ; i++ )
			{
				item = totalItems[i];
				level = item._level;
				upLevel = level - 1;
				alpha = item._opacity*opacityStack[upLevel];
				if ( !visibleStack[upLevel] || !item._visible || item.isEmpty() || !alpha )
				{
					visibleStack[level] = false;
					if ( item._painted & (1|2|4) )
					{
						item._painting = 2; //ease
					}
					else
					{
						item._painting = 0;
					}
				}
				else
				{
					item._jobDrawflags = item._drawflags;
					if ( item._globalAlpha != alpha )
					{
						item._globalAlpha = alpha;
						//item._drawflags |= 8; //stylechanged
						item._jobDrawflags |= 8; //stylechanged
					}
					item._painting = 1; //visible
					if ( item._painted == 0 ) item._newDraw = true; //visible, empty, alpha에 의해 신규 로 draw하는 것
					if ( transformChangedStack[upLevel] || (item._jobDrawflags & 1) || 
						 !item._globalMatrix )
					{
						//item._oldGlobalMatrix = item._globalMatrix;
						//item._setOldGlobalBounds();
						item._setGlobalTransforms(transformStack[upLevel]);
						transformChangedStack[level] = true;
						item._jobDrawflags |= 1;
						//if ( this._matrix )
						//console.log("item._drawflags 1", item._globalMatrix.toString(),":::", (item._oldGlobalMatrix ? item._oldGlobalMatrix.toString() : null));
					}
					else
					{
						transformChangedStack[level] = false;
					}
					//transformStack[level] = item._globalMatrix;
					visibleStack[level] = true;
					//console.log("globlalMatrix", item, item._painting, item._drawflags);
				}
				if ( item._firstChild )
				{
					transformStack[level] = item._globalMatrix;
					opacityStack[level] = item._globalAlpha*opacityStack[upLevel];
				}
			}
			if ( isDebug ) Eco.Logger.info({message: "_setGlobalTransforms create 시간 !!!", elapsed: true});
			if ( isDebug ) Eco.Logger.startElapsed();
			return totalItems;
		},
		_drawAll: function(totalItems)
		{
			var item, drawflags, sizeChanged;
			for ( var i = totalItems.length - 1 ; i > -1 ; i-- )
			{
				item = totalItems[i];
				if ( item._painting & 1 )
				{
					sizeChanged = false;
					drawflags = item._jobDrawflags;
					if ( drawflags & (1|2|4) ) //transformchanged, sizechanged, contentschanged
					{
						//console.log("drawflags", drawflags, item);
						if ( drawflags & 1 )
						{
							item._bounds = null;
							item._globalBounds = null;
						}
						if ( drawflags & (2|4) )
						{
							item._bounds = null;
							item._globalBounds = null;
							item._rect = null;
							sizeChanged = true;
						}
						var p = item.Parent;
						if ( p && !(p._jobDrawflags&4))
						{
							if ( p == this )
							{
								p._rect = null;
							}
							else
							{
								p._jobDrawflags |= 4;
								/*
								if ( p._rect )
								{
									if ( !p._rect._containsRectangle(item.getBounds()) )
									{
										p._jobDrawflags |= 4;
										//console.log("parent setting", p);
									}
								}
								else
								{
									p._jobDrawflags |= 4; //boundsChanged
								}
								*/
							}
						}
						item._sizeChanged = true;
					}
				}
			}
		},
		_renderAll: function(totalItems, bSetDrawflags)
		{
			var p = this.Parent,
				viewport = p._viewport,
				sz = viewport._size,
				drawflags = viewport._drawflags,
				ctx = this._canvas;

			if ( !ctx._handle )
			{
				ctx.create();
			}
			else if ( !(drawflags & 2) )
			{
				ctx.clearRect(0, 0, ctx.width, ctx.height);
			}

			ctx.save();
			if ( this._matrix )
			{
				var gbounds = this._getCanvasRect();
				//var offset = new Eco.Point(bounds.x - gbounds.x, bounds.y - gbounds.y);
				//ctx.translate(offset.x + 0.5, offset.y + 0.5);
				ctx.translate(-gbounds.x, -gbounds.y);
				//m = (this._globalMatrix.clone()).concatenate(m);
				//m.applyToContext(ctx);
				//this._offset = offset;
				//console.log("ctx.translate", gbounds.getTopLeft().toString(), this._globalMatrix.getTranslation().toString());
			}
			var item, drawflags, style;
			for ( var i = 0, len = totalItems.length  ; i < len ; i++ )
			{
				item = totalItems[i];
				if ( item._painting & 1 )
				{
					if ( item._jobDrawflags & 8 ) //stylechanged
					{
						item._updateStyles(ctx, item._sizeChanged);
						item._sizeChanged = false;
						//item._paintHints |= 4; // paint stylesChanged
					}
					else if ( item._isGradation && item._sizeChanged )
					{
						item._sizeChanged = false;
						style = item._style;
						ctx._updateFill(style.fillbrush, item._renderstyle, style.fillgradation, item._getRect());
					}
					//if ( this._matrix ) console.log(item);
					item._render(ctx);
					//item._oldGlobalBounds = null;
					//item._painting = 0;
					if ( bSetDrawflags )
					{
						item._drawflags = 0;
						item._newDraw = false;
					}
				}
				else
				{
					item._painted = 0;
					//item._oldGlobalBounds = null;
				}
			}
			ctx.restore();
			this._dirtyRectsMgr.clear();
		},
		_getCanvasRect: function()
		{
			var p = this.Parent,
				viewport = p._viewport,
				sz = viewport._size;
			return new Eco.Rectangle(0, 0, sz.width, sz.height);
		},
		_setQuadTree: function(totalItems)
		{
			var item,
				quadTreeCls = Eco.QuadTree,
				quadtree = this._quadtree,
				bRefresh = this._refreshQuadTree;

			if ( bRefresh || !quadtree )
			{
				var bounds = this._getCanvasRect();
				quadtree = new quadTreeCls(bounds.x, bounds.y, bounds.width, bounds.height);
				this._quadtree = quadtree;
			}
			var quadtreeStacks = [quadtree],
				level, upLevel, pQuadtree;
			for ( var i = 0, len = totalItems.length  ; i < len ; i++ )
			{
				item = totalItems[i];
				if ( item._painting & 1 )
				{
					level = item._level;
					upLevel = level - 1;
					if ( bRefresh || (item._jobDrawflags & (1|2|4)) || item._newDraw ) //transformchanged, sizechanged, contentschanged
					{
						pQuadtree = quadtreeStacks[upLevel];
						if ( bRefresh || !pQuadtree.move(item) )
						{
							pQuadtree.add(item);
						}
						item._newDraw = false;
						//console.log("".padLeft(level, "\t"), item.__data__, item.getBounds().toString());
						//console.log("qTree add", item.toString(), item._jobDrawflags, item.getGlobalBounds().toString());
					}
					if ( item._firstChild )
					{
						quadtree = item._quadtree;
						//console.log("quadtree".padLeft(level, "\t"), item.getBounds().toString());
						if ( bRefresh || !quadtree )
						{
							var rect = item._getRect();
							quadtree = new quadTreeCls(rect.x, rect.y, rect.width, rect.height);
							item._quadtree = quadtree;
						}
						//console.log("invertPt:", rect.x
						quadtreeStacks[level] = quadtree;
					}
					item._oldGlobalBounds = null;
					item._setOldGlobalBounds();
					item._drawflags = 0;
				}
				else if ( !bRefresh && item._painting & 2 )
				{
					level = item._level;
					upLevel = level - 1;
					pQuadtree = quadtreeStacks[upLevel];
					pQuadtree.remove(item);
					item._oldGlobalBounds = null;
					//console.log("remove".padLeft(level, "\t"), item.__data__, item.getBounds().toString());
					//console.log("qTree remove", item);
					//item._painting = 0;
					//console.log("unpainged", item, item._drawflags);
				}
				//item._painting = 0;
			}
			if ( totalItems.length > 0 && !nexacro._init_platform_runtime && !Eco._IE8ANDLOWER )
			{
				this._renderHint = "dirty";
			}
			if ( this._drawflags != null ) this._drawflags = 0;
		},
		_addDirty: function(elem)
		{
			var rect = elem._oldGlobalBounds;
			if ( rect )
			{
				var x0 = Math.floor(rect.x);
				var y0 = Math.floor(rect.y);
				var x1 = Math.ceil(rect.x + rect.width);
				var y1 = Math.ceil(rect.y + rect.height);
				//console.log("_addDirty==>", rect.toString(), elem);
				this._dirtyRectsMgr.add(new Eco.Rectangle(x0 - 1, y0 - 1, x1 - x0 + 3, y1 - y0 + 3));
			}
		},
		_drawDirty: function(totalItems)
		{
			var dirtyRectsMgr = this._dirtyRectsMgr;
			var item, drawflags, sizeChanged, style,
				rect, x0, y0, x1, y1, boundChanged, p,
				addDirty = function(rect)
				{
					x0 = Math.floor(rect.x);
					y0 = Math.floor(rect.y);
					x1 = Math.ceil(rect.x + rect.width);
					y1 = Math.ceil(rect.y + rect.height);
					//rect.x = x0 - 1;
					//rect.y = y0 - 1;
					//rect.width = x1 - x0 + 3;
					//rect.height = y1 - y0 + 3;
					dirtyRectsMgr.add(new Eco.Rectangle(x0 - 1, y0 - 1, x1 - x0 + 3, y1 - y0 + 3));
				};

			
			for ( var i = totalItems.length - 1 ; i > -1 ; i-- )
			{
				item = totalItems[i];
				p = item.Parent;
				if ( item._painting & 1 )
				{
					sizeChanged = false;
					boundChanged = false;
					drawflags = item._jobDrawflags;
					if ( drawflags & (1|2|4) ) //transformchanged, sizechanged, contentschanged
					{
						if ( item._painted )
						{
							//item._setOldGlobalBounds();
							//console.log(item.toString(), "_setOldGlobalBounds", item._oldGlobalBounds, item._jobDrawflags);
						}
						if ( drawflags & 1 )
						{
							item._bounds = null;
							item._globalBounds = null;
						}
						if ( drawflags & (2|4) )
						{
							item._bounds = null;
							item._globalBounds = null;
							item._rect = null;
							sizeChanged = true;
						}
						if ( p && !(p._jobDrawflags&4) )
						{
							if ( p == this )
							{
								p._rect = null;
							}
							else
							{
								p._jobDrawflags |= 4; //boundsChanged
								/*	
								if ( p._rect )
								{
									if ( !p._rect._containsRectangle(item.getBounds()) )
									{
										p._jobDrawflags |= 4; //boundsChanged
										//console.log("parent setting", p, (p._rect ? p._rect.toString() : "" ), item.getBounds().toString(), p._drawflags);
									}
								}
								else
								{
									p._jobDrawflags |= 4; //boundsChanged
									//console.log("rect null parent setting", p, (p._rect ? p._rect.toString() : "" ), item.getBounds().toString(), p._drawflags);
								}
								*/
							}
						}
						boundChanged = true;
						item._sizeChanged = sizeChanged;
						item._painting |= 4;
					}
					if ( boundChanged )
					{
						if ( p == this || !(p._jobDrawflags&4) )
						{
							var oldBounds = item._oldGlobalBounds,
								curBounds = item.getGlobalBounds();
							if ( oldBounds  )
							{
								addDirty(oldBounds);
								//if ( item._type == "Group" ) console.log("boundchanged dirty oldBounds", oldBounds.toString(), item.__data__, item._visible, item.toString(), item._class);
							}
							//console.log("boundchanged dirty curBounds", curBounds.toString(), item.__data__, item._visible, item.toString(), item._class);
							addDirty(curBounds);
						}
					}
					else if ( (drawflags & 8) || item._newDraw ) //stylechanged
					{
						item._painting |= 4;
						if ( p == this || !(p._jobDrawflags&4) )
						{
							var oldBounds = item._oldGlobalBounds, //changed strokewidth 
								curBounds = item.getGlobalBounds();
							if ( oldBounds && !oldBounds._containsRectangle(curBounds) )
							{
								//console.log("stylechanged dirty oldBounds", oldBounds.toString(), item.__data__, item._visible, item.toString(), item._class);
								addDirty(oldBounds);
							}
							else
							{
								//console.log("stylechanged dirty curBounds", curBounds.toString(), item.__data__, item._visible, item.toString(), item._class);
								addDirty(curBounds);
							}
						}
					}
				}
				else if ( item._painting & 2 )
				{
					//if ( p ) console.log("remove dirty", p.toString(), item._class, p._jobDrawflags, (p._rect ? p._rect.toString() : ""), (p._oldGlobalBounds ? p._oldGlobalBounds.toString() : ""));
					if ( p == this || !(p._jobDrawflags&4) )
					{
						if ( item._painted )
						{
							//item._setOldGlobalBounds();
							//console.log("remove dirty", item._oldGlobalBounds.toString(), item.__data__, item._visible, item.toString(), item._class);
							addDirty(item._oldGlobalBounds);
						}
							//addDirty(item.getGlobalBounds(true));
					}
				}
				//console.log("check", item, item._painting);
			}
		},
		_renderDirty: function(totalItems)
		{
			var dirtyRectsMgr = this._dirtyRectsMgr,
				_self = this,
				ctx = this._canvas;

			if ( !ctx._handle )
			{
				ctx.create();
			}

			dirtyRectsMgr.mergeRects(10000, 10); //overhead, lineOverhead

			ctx.save();
			ctx.beginPath();
			//console.log("dirty rect==> start");
			dirtyRectsMgr.Each(function(rect) {
				//console.log("dirty rect==>", rect.toString());
				ctx.clearRect(rect.x, rect.y, rect.width, rect.height);
				_self.setItemsInDirtyRect(rect);
				ctx.rect(rect.x, rect.y, rect.width, rect.height);
			});
			ctx.clip();

			var item;
			for ( var i = 0, len = totalItems.length  ; i < len ; i++ )
			{
				item = totalItems[i];
				
				if (item._painted > 0)
				{
					ctx.setElementGlobalAlpha(item._globalAlpha);
				}
				
				if ( item._jobDrawflags & 8 ) //stylechanged
				{
					item._updateStyles(ctx, item._sizeChanged);
					item._sizeChanged = false;
				}
				else if ( item._isGradation && item._sizeChanged )
				{
					style = item._style;
					ctx._updateFill(style.fillbrush, item._renderstyle, style.fillgradation, item._getRect());
					item._sizeChanged = false;
				}
				if ( item._painting & 4 )
				{
					if ( item._painting & 2 )
					{
						item._painted = 0;
						//item._oldGlobalBounds = null;
						//console.log("render=>", item._class, item);
						//item._painting = 0;
					}
					else
					{
						item._render(ctx);
						//item._oldGlobalBounds = null;
						//console.log("render item", item.__data__, item._visible, item.toString());
					}
				}
				else if ( item._painting & 2 )
				{
					item._painted = 0;
					//item._oldGlobalBounds = null;
				}
			}
			dirtyRectsMgr.clear();
			ctx.restore();
		},
		_beforePaint: function(isDebug)
		{
			var hints = this._renderHint||"all";
			var totalItems = this._setGlobalTransforms(isDebug);
			if ( hints == "all" )
			{
				if ( isDebug ) Eco.Logger.startElapsed();
				this._drawAll(totalItems);
				if ( isDebug ) Eco.Logger.info({message: "_drawAll create 시간 !!!", elapsed: true});
			}
			else if ( hints == "dirty" )
			{
				if ( isDebug ) Eco.Logger.startElapsed();
				this._drawDirty(totalItems);
				if ( isDebug ) Eco.Logger.info({message: "_drawDirty create 시간 !!!", elapsed: true});
			}
		},
		_paint: function(isDebug, noQuadTree)
		{
			var hints = this._renderHint||"all";

			var totalItems = this.getGElements();
			if ( hints == "all" )
			{
				if ( isDebug ) Eco.Logger.startElapsed();
				this._renderAll(totalItems, noQuadTree);
				if ( isDebug ) Eco.Logger.info({message: "_renderAll 시간 !!!", elapsed: true});
				if ( isDebug ) Eco.Logger.startElapsed();
				if ( !noQuadTree ) this._setQuadTree(totalItems);
			}
			else if ( hints == "dirty" )
			{
				if ( isDebug ) Eco.Logger.startElapsed();
				this._renderDirty(totalItems);
				if ( isDebug ) Eco.Logger.info({message: "_renderDirty 시간 !!!", elapsed: true});
				if ( isDebug ) Eco.Logger.startElapsed();
				if ( !noQuadTree ) this._setQuadTree(totalItems);
			}
		},
		"_remove": function()
		{
			var ctx = this._canvas;
			if ( ctx )
			{
				ctx.destroy();
				this._canvas = null;
			}
		},
		hitTest: function(pt)
		{
			var rect = new Eco.Rectangle(pt.x - 1, pt.y - 1, 3, 3);
			var item = this._hitTestItems(this, pt, rect);
			if ( this != item )
			{
				return item;
			}
			return null;
		},
		_hitTestItems: function(p, pt, rect)
		{
			var quadtree = p._quadtree,
				item;
			if ( quadtree )
			{
				var items = quadtree.query(rect),
					item, ret, invertPt;

				items.sort(function(a, b) {return b._index - a._index});
				for ( var i = 0, len = items.length ; i < len ; i++ )
				{
					item = items[i];
					invertPt = item._globalMatrix._inverseTransform(pt);
					if ( item.hitTest(invertPt) )
					{
						if ( item._firstChild )
						{
							rect.x = invertPt.x - 1;
							rect.y = invertPt.y - 1;
							ret = this._hitTestItems(item, pt, rect);
							if ( item._type == "Paths" )
							{
								if ( ret != item ) return item;
							}
							else
							{
								return ret;
							}
						}
						else
						{
							return item;
						}
					}
				}
			}
			return p;
		}
	});
}

if ( !JsNamespace.exist("Eco.GraphicFloatingLayer") )
{
	JsNamespace.declareClass("Eco.GraphicFloatingLayer", {
		"extends": "Eco.GraphicLayer",
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
			Eco.GraphicElement.prototype.initialize.apply(this, arguments);
			this._type = "FloatingLayer";
			var read = this.__readArgs;
			if ( arguments[read] && (arguments[read] instanceof Eco.Size) )
			{
				this._size = arguments[read].clone();
				this._drawflags |= 2; //sizechanged
			}
			else if ( Eco.isNumber(arguments[read]) && Eco.isNumber(arguments[read + 1]) )
			{
				this._size = new Eco.Size(arguments[read], arguments[read + 1]);
				this._drawflags |= 2; //sizechanged
			}
			this._dirtyRectsMgr = new Eco.RectsManager();
			this._renderHint = "all";
			this._treechange = 0;
			this._childchanged = 0;
		},
		properties: {
			"size": {
				memberName: "_size",
				"set": function()
				{
					var argLen = arugments.length;
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
		},
		hitTest: function(pt)
		{
			var inPt = this._globalMatrix._inverseTransform(pt);
			var rect = new Eco.Rectangle(inPt.x - 1, inPt.y - 1, 3, 3);
			var item = this._hitTestItems(this, pt, rect);
			if ( this != item )
			{
				return item;
			}
			return null;
		},
		_getRect: function(bStrokeWidth)
		{
			var rect = this._rect;
			if ( !rect )
			{
				var sz = this._size;
				if ( sz )
				{
					rect = new Eco.Rectangle(sz);
					this._rect = rect;
				}
				else
				{
					var child = this._firstChild;
					if ( !child )
					{
						rect = new Eco.Rectangle();
						this._rect = rect;
					}
					else
					{
						var x1 = Infinity,
							x2 = -x1,
							y1 = x1,
							y2 = x2;
						for ( ; child != null ; child = child._nextSibling )
						{
							if (child._visible && !child.isEmpty())
							{
								var rect = child.getBounds();
								x1 = Math.min(rect.x, x1);
								y1 = Math.min(rect.y, y1);
								x2 = Math.max(rect.x + rect.width, x2);
								y2 = Math.max(rect.y + rect.height, y2);
							}
						}
						rect = isFinite(x1)
								? new Eco.Rectangle(x1, y1, x2 - x1, y2 - y1)
								: new Eco.Rectangle();
						this._rect = rect;
					}
				}
			}
			if ( bStrokeWidth )
			{
				rect = rect.expand(this.getStrokeWidth()/2);
			}
			return rect;
		}
	});
}

