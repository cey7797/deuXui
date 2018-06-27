//==============================================================================
//
//  TOBESOFT Co., Ltd.
//  Copyright 2014 TOBESOFT Co., Ltd.
//  All Rights Reserved.
//
//  NOTICE: TOBESOFT permits you to use, modify, and distribute this file 
//          in accordance with the terms of the license agreement accompanying it.
//
//  Readme URL: http://www.tobesoft.com/nexacro-public-license-readme-1.0.html	
//
//==============================================================================


//==============================================================================
// nexacro.Graphic && nexacro.Graphic_Style
//==============================================================================
if (!nexacro.Graphic) 
{
	//==============================================================================
	// nexacro.Graphic_Style
	//==============================================================================
	nexacro.Graphic_Style = function (target) 
	{
	    nexacro.Style.call(this);
		if (target) 
		{
			this._target = target;
		}
	};

	var _pGraphicStyle = nexacro._createPrototype(nexacro.Style, nexacro.Graphic_Style);
	nexacro.Graphic_Style.prototype = _pGraphicStyle;	

	//eval(nexacro._createValueAttributeEvalStr("_pButtonStyle", "image"));
	//eval(nexacro._createAlignAttributeEvalStr("_pButtonStyle", "imagealign"));

	// custom Part
	_pGraphicStyle.__custom_emptyObject = function () 
	{
		//this.image = null;
		//this.imagealign = null;
	};
	_pGraphicStyle.__get_custom_style_value = function () 
	{
		/*
		var val = "";
		if (this.image && !this.image._is_empty)
		{
		    val += "image:" + this.image._value + "; ";
		}
		if (this.imagealign && !this.imagealign._is_empty)
		{
		    val += "imagealign:" + this.imagealign._value + "; ";
		}
		return val;
		*/
		return "";
	};
	

	//---------------------------------------------------------------
	nexacro.Graphic_CurrentStyle = function () 
	{
		nexacro.CurrentStyle.call(this);
		//this.image = null;
		//this.imagealign = null;
	};

	var _pGraphicCurrentStyle = nexacro._createPrototype(nexacro.CurrentStyle, nexacro.Graphic_CurrentStyle);
	nexacro.Graphic_CurrentStyle.prototype = _pGraphicCurrentStyle;	

	_pGraphicCurrentStyle.__custom_emptyObject = _pGraphicStyle.__custom_emptyObject;
	_pGraphicCurrentStyle.__get_custom_style_value = _pGraphicStyle.__get_custom_style_value;
	
	// end of Graphic Style
	delete _pGraphicStyle;
	delete _pGraphicCurrentStyle;

	//==============================================================================
	// nexacro.Button
	//==============================================================================
	nexacro.Graphic = function (id, position, left, top, width, height, right, bottom, parent)
	{
		nexacro.Component.call(this, id, position, left, top, width, height, right, bottom, parent);
		this._layers = [];
		this._childchanged = 0;
		//Accessibility
		//this._accessibility_role = "button";
	};

	// TODO expr, escapebutton, firedblclicktype
	// TODO getSelectStatus, setSelectStatus, toggleSelectStatus

	var _pGraphic = nexacro._createPrototype(nexacro.Component, nexacro.Graphic);
	nexacro.Graphic.prototype = _pGraphic;
    	
	_pGraphic._type_name = "Graphic";

	//===============================================================
	// nexacro.Graphic : Style Part
	//---------------------------------------------------------------
	_pGraphic.on_create_custom_style = function () 
	{
		return new nexacro.Graphic_Style(this);
	};

	_pGraphic.on_create_custom_currentStyle = function () 
	{
		return new nexacro.Graphic_CurrentStyle();
	};

	//===============================================================
	// nexacro.Graphic : Graphic's Create & Update
	//---------------------------------------------------------------
	_pGraphic.on_create_contents = function () 
	{
	    var control_elem = this.getElement();
		if (control_elem) 
		{
			var layers = this._layers,
				layer;
			var viewport = this._viewport = new Eco.GraphicViewPort(this, this._client_width, this._client_height),
				ctx, bounds;

			len = layers.length;
			if ( len > 0 )
			{
				var isDebug = this._debug;
				for ( var i = 0 ; i < len ; i++ )
				{
					layer = layers[i];
					layer._beforePaint(isDebug);
					ctx = new nexacro.CanvasElement(control_elem);
					layer._canvas = ctx;
					bounds = layer._getCanvasRect();
					ctx.setElementPosition(bounds.x, bounds.y);
					ctx.setElementSize(bounds.width, bounds.height);
				}
				//this._activeLayer = layers[len - 1];
			}
			else
			{
				layer = new Eco.GraphicLayer();
				this.addChild(layer);
				//this._activeLayer = layer;
				ctx = new nexacro.CanvasElement(control_elem);
				layer._canvas = ctx;
				bounds = layer._getCanvasRect();
				ctx.setElementPosition(bounds.x, bounds.y);
				ctx.setElementSize(bounds.width, bounds.height);
			}
		}
	};
	
	_pGraphic.on_created_contents = function () 
	{
		var control_elem = this.getElement();
		if (control_elem)
		{
			var layers = this._layers,
				viewport = this._viewport,
				layer;
			for ( var i = 0, len = layers.length ; i < len ; i++ )
			{
				layer = layers[i];
				layer._canvas.create();
			}
			var isDebug = this._debug;
			if ( isDebug ) Eco.Logger.startElapsed(true);
			for ( var i = 0, len = layers.length ; i < len ; i++ )
			{
				layer = layers[i];
				layer._paint(isDebug);
			}
			viewport._drawflags = 0;
			if ( isDebug ) Eco.Logger.info({message: "paint 시간 !!!", elapsed: true, elapsedTotal: true});
		}
	};
	
	_pGraphic.on_destroy_contents = function () 
	{
		var control_elem = this.getElement();
		if (control_elem)
		{
			var layers = this._layers,
				layer;
			for ( var i = 0, len = layers.length ; i < len ; i++ )
			{
				layer = layers[i];
				layer._remove();
				delete layer._index;
			}
			this._layers = null;
		}
	};

	_pGraphic.on_change_containerRect = function (width, height) 
	{
		this._viewport.setSize(width, height);
		var control_elem = this.getElement();
		if ( control_elem && control_elem._handle )
		{
			var layers = this._layers,
				layer;
			for ( var i = 0, len = layers.length ; i < len ; i++ )
			{
				layer = layers[i];
				layer._renderHint = "all";
				layer._refreshQuadTree = true;
			}
			 this._paint();
		}
	};

	//==============================================================================
	// nexacro.Graphic : Event Handlers
	//==============================================================================
	_pGraphic.on_fire_user_onmousemove = function (button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY, from_comp, from_refer_comp)
	{
		if ( this._dragstart )
		{
			var ret = this._dragstart.moved(button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY);
			if ( ret == "dragstart" )
			{
				Eco.XComp.Event._lockMouseEvent(this);
				return;
			}
			else if ( ret == "drag" )
			{
				return;
			}
			else
			{
				this._dragstart = null;
			}
		}
		var target = this._getGraphicElement(clientX, clientY),
			cursorVal, evt, isPrevented;

		if ( target )
		{
			//console.log("target", target, "cursor", target._cursor);
			if ( target.hasEventHandler("onmousemove") )
			{
				evt = new nexacro.MouseEventInfo(target, "onmousemove", button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY, target, target);
				target.fireEvent("onmousemove", evt);
				isPrevented = evt._prevented;
			}
			if ( !isPrevented )
			{
				 isPrevented = this._bubbleFireMouseEvent("onmousemove", target, button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY);
			}
			if ( target._cursor )
			{
				cursorVal = target._cursor;
			}
			else
			{
				if ( !isPrevented )
				{
					var p = target.Parent,
						pLayerCls = Eco.GraphicLayer;
					while ( p )
					{
						if ( p instanceof pLayerCls ) break;
						cursorVal = p._cursor;
						if ( cursorVal ) break;
						p = p.Parent;
					}
				}
			}
		}
		if ( cursorVal  )
		{
			var realCursor = this.getElement().cursor;
			//console.log("cursorVal", cursorVal._value);
			if ( cursorVal._value != realCursor._value )
			{
				this.getElement().setElementCursor(cursorVal);
			}
		}
		else
		{
			var compCursor = this.currentstyle.cursor;	
			var realCursor = this.getElement().cursor;
			if ( compCursor._value != realCursor._value )
			{
				this.getElement().setElementCursor(compCursor);
			}
		}

		if ( target != this._overGElem )
		{
			if ( this._overGElem )
			{
				if ( !isPrevented )
				{
					if ( this._overGElem.hasEventHandler("onmouseleave") )
					{
						evt = new nexacro.MouseEventInfo(this._overGElem, "onmouseleave", button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY, target, target);
						this._overGElem.fireEvent("onmouseleave", evt);
						isPrevented = evt._prevented;
					}
				}
			}
			if ( target )
			{
				if ( !isPrevented )
				{
					if ( target.hasEventHandler("onmouseenter") )
					{
						evt = new nexacro.MouseEventInfo(target, "onmouseenter", button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY, this._overGElem, this._overGElem);
						target.fireEvent("onmouseenter", evt);
						isPrevented = evt._prevented;
					}
				}
			}
			this._overGElem = target;
		}
		if ( !isPrevented )
		{
			return nexacro.Component.prototype.on_fire_user_onmousemove.call(this, button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY, from_comp, from_refer_comp);
		}
		return false;
	};

	_pGraphic.on_fire_user_onmouseleave = function (button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY, from_comp, from_refer_comp)
	{
		var isPrevented;
		if ( this._overGElem )
		{
			var evt = new nexacro.MouseEventInfo(this._overGElem, "onmouseleave", button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY, null, null);
			this._overGElem.fireEvent("onmouseleave", evt);
			isPrevented = evt._prevented;
			this._overGElem = null;
		}
		if ( !isPrevented )
		{
			return nexacro.Component.prototype.on_fire_user_onmouseleave.call(this, button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY, from_comp, from_refer_comp);
		}
		return false;
	};

	_pGraphic._bubbleFireMouseEvent = function(eventNm, target, button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY)
	{
			var bubbleElem = target.Parent,
				isPrevented;
			while ( bubbleElem && bubbleElem != this )
			{
				if ( isPrevented ) break;
				if ( bubbleElem.hasEventHandler(eventNm) )
				{
					var evt = new nexacro.MouseEventInfo(bubbleElem, eventNm, button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY, target, target);
					bubbleElem.fireEvent(eventNm, evt);
					isPrevented = evt._prevented;
				}
				bubbleElem = bubbleElem.Parent;
			}
			return isPrevented;
	};

	_pGraphic._makeDragObject = function(target, x, y)
	{
		var origin = [x, y],
			prevXY = [x, y],
			dragged = 0,
			offset = [0, 0],
			distance = [0, 0];
		return {
			moved: function (button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY) {
				var ret;
				distance[0] = clientX - origin[0];
				distance[1] = clientY - origin[1];
				offset[0] =clientX - prevXY[0];
				offset[1] = clientY - prevXY[1];
				prevXY[0] = clientX;
				prevXY[1] = clientY;
				if ( !dragged && (dragged |= distance[0] | distance[1]) )
				{
					if ( target.hasEventHandler("ondragstart") )
					{
						var evt = new nexacro.MouseEventInfo(target, "ondragstart", button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY, target, target);
						evt.offsetX = offset[0];
						evt.offsetY = offset[1];
						evt.distanceX = distance[0];
						evt.distanceY = distance[1];
						ret = target.fireEvent("ondragstart",evt);
						if ( ret && !evt._prevented ) ret = "dragstart";
					}
				}
				else if ( dragged )
				{
					if ( target.hasEventHandler("ondrag") )
					{
						var evt = new nexacro.MouseEventInfo(target, "ondrag", button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY, target, target);
						evt.offsetX = offset[0];
						evt.offsetY = offset[1];
						evt.distanceX = distance[0];
						evt.distanceY = distance[1];
						target.fireEvent("ondrag",  evt);
					}
					ret = "drag";
				}
				return ret;
			},
			ended: function(button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY) {
				if ( target.hasEventHandler("ondragend") )
				{
					var evt = new nexacro.MouseEventInfo(target, "ondragend", button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY, target, target);
					evt.offsetX = offset[0];
					evt.offsetY = offset[1];
					evt.distanceX = distance[0];
					evt.distanceY = distance[1];
					target.fireEvent("ondragend",  evt);
				}
			}
		};
	};

	_pGraphic.on_fire_user_onlbuttondown = function (button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY, from_comp, from_refer_comp)
	{
		var target = this._getGraphicElement(clientX, clientY),
			dragstart, isPrevented, dragTarget;;
		if ( target )
		{
			if ( target.hasEventHandler("onlbuttondown") )
			{
				var evt = new nexacro.MouseEventInfo(target, "onlbuttondown", button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY, target, target);
				target.fireEvent("onlbuttondown", evt);
				isPrevented = evt._prevented;
			}
			if ( !isPrevented )
			{
				 isPrevented = this._bubbleFireMouseEvent("onlbuttondown", target, button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY);
			}
			
			if ( target.behavior == "drag" )
			{
				dragTarget = target;
				dragstart = true;
			}
			else
			{
				if ( !isPrevented )
				{
					var p = target.Parent,
						pLayerCls = Eco.GraphicLayer;
					while ( p )
					{
						if ( p instanceof pLayerCls ) break;
						if ( p.behavior == "drag" )
						{
							dragTarget = p;
							dragstart = true;
							break;
						}
						p = p.Parent;
					}
				}
			}
		}

		if ( dragstart )
		{
			this._dragstart = this._makeDragObject(dragTarget, clientX, clientY);
			this._curlbtnGElem = dragTarget;
		}
		else
		{
			this._dragstart = null;
			this._curlbtnGElem = target;
			if ( !isPrevented )
			{
				return nexacro.Component.prototype.on_fire_user_onlbuttondown.call(this, button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY, from_comp, from_refer_comp);
			}
		}
		return false;
	};

	_pGraphic.on_fire_user_onlbuttonup = function (button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY, from_comp, from_refer_comp, from_elem)
	{
		var isPrevented;	
		if ( this._dragstart )
		{
			this._dragstart.ended(button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY);
			this._dragstart = null;
			this._curlbtnGElem = null;
			return;
		}
		if ( this._curlbtnGElem )
		{
			if ( this._curlbtnGElem.hasEventHandler("onlbuttonup") )
			{
				var evt = new nexacro.MouseEventInfo(this._curlbtnGElem, "onlbuttonup", button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY, this._curlbtnGElem, this._curlbtnGElem);
				this._curlbtnGElem.fireEvent("onlbuttonup", evt);
				isPrevented = evt._prevented;
			}
			if ( !isPrevented )
			{
				 isPrevented = this._bubbleFireMouseEvent("onlbuttonup", this._curlbtnGElem, button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY);
			}
			this._curlbtnGElem = null;
		}
		if (!isPrevented)
		{
			return nexacro.Component.prototype.on_fire_user_onlbuttonup.call(this, button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY, from_comp, from_refer_comp, from_elem);
		}
		return false;
	};

	_pGraphic.on_fire_user_onrbuttondown = function (button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY, from_comp, from_refer_comp)
	{
		var target = this._getGraphicElement(clientX, clientY),
			isPrevented;
		if ( target )
		{
			if ( target.hasEventHandler("onrbuttondown") )
			{
				var evt = new nexacro.MouseEventInfo(target, "onrbuttondown", button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY, target, target);
				target.fireEvent("onrbuttondown", evt);
				isPrevented = evt._prevented;
			}
			if ( !isPrevented )
			{
				 isPrevented = this._bubbleFireMouseEvent("onrbuttondown", target, button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY);
			}
		}
		this._currbtnGElem = target;
		if ( !isPrevented )
		{
			return nexacro.Component.prototype.on_fire_user_onrbuttondown.call(this, button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY, from_comp, from_refer_comp);
		}
		return false;
	};

	_pGraphic.on_fire_user_onrbuttonup = function (button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY, from_comp, from_refer_comp, from_elem)
	{
		var isPrevented;
		if ( this._currbtnGElem )
		{
			if ( this._currbtnGElem.hasEventHandler("onrbuttonup") )
			{
				var evt = new nexacro.MouseEventInfo(this._currbtnGElem, "onrbuttonup", button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY, this._currbtnGElem, this._currbtnGElem);
				target.fireEvent("onrbuttonup", evt);
				isPrevented = evt._prevented;
			}
			if ( !isPrevented )
			{
				 isPrevented = this._bubbleFireMouseEvent("onrbuttonup", this._currbtnGElem, button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY);
			}
			this._currbtnGElem = null;
		}
		if ( !isPrevented )
		{
			return nexacro.Component.prototype.on_fire_user_onrbuttonup.call(this, button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY, from_comp, from_refer_comp, from_elem);
		}
		return false;
	};

	_pGraphic.on_fire_onclick = function (button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY, from_comp, from_refer_comp)
	{
		var isPrevented;
		var target = this._getGraphicElement(clientX, clientY);
		if ( target )
		{
			if ( target.hasEventHandler("onclick") )
			{
				var evt = new nexacro.MouseEventInfo(target, "onclick", button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY, target, target);
				target.fireEvent("onclick", evt);
				isPrevented = evt._prevented;
			}
			if ( !isPrevented )
			{
				 isPrevented = this._bubbleFireMouseEvent("onclick", target, button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY);
			}
		}
		if ( !isPrevented )
		{
			return nexacro.Component.prototype.on_fire_onclick.call(this, button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY, from_comp, from_refer_comp);
		}
		return false;
	};

	_pGraphic.on_fire_ondblclick = function (button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY, from_comp, from_refer_comp)
	{
		var isPrevented;
		var target = this._getGraphicElement(clientX, clientY);
		if ( target )
		{
			if ( target.hasEventHandler("ondblclick") )
			{
				var evt = new nexacro.MouseEventInfo(target, "ondblclick", button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY, target, target);
				target.fireEvent("ondblclick", evt);
				isPrevented = evt._prevented;
			}
			if ( !isPrevented )
			{
				 isPrevented = this._bubbleFireMouseEvent("ondblclick", target, button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY);
			}
		}
		if ( !isPrevented )
		{
			return nexacro.Component.prototype.on_fire_onclick.call(this, button, alt_key, ctrl_key, shift_key, screenX, screenY, canvasX, canvasY, clientX, clientY, from_comp, from_refer_comp);
		}
		return false;
	};

	//TODO: key, touch 관련 이벤트 처리

	//===============================================================
	// nexacro.Graphic : Graphic's Custom Part
    //---------------------------------------------------------------
	_pGraphic._getGraphicElement = function(x, y)
	{
		var layers = this._layers,
			cnt, elem;
		if ( layers && ( cnt = layers.length ) )
		{
			var pt = new Eco.Point(x, y);
			for ( var i = cnt - 1 ; i > -1 ; i-- )
			{
				elem = layers[i].hitTest(pt);
				if ( elem )
				{
					break;
				}
			}
		}
		return elem;
	};

	// layer children methods
	_pGraphic.hasChildren = function()
	{
		return this._layers.length != 0;
	};

	_pGraphic.getChildren = function()
	{
		return this._layers.slice(0);
	};

	_pGraphic.getLength = function()
	{
		return this._layers.length;
	};

	_pGraphic.getChildByIndex = function(index)
	{
		return this._layers[index];
	};

	_pGraphic.insertBefore = function(newLayer, refLayer)
	{
		if ( !(newLayer instanceof Eco.GraphicLayer) || !(refLayer instanceof Eco.GraphicLayer) )
		{
			Eco.Logger.error("newLayer, refLayer 는 Eco.GraphicLayer이어야 한다.");
		}
		if ( refLayer.Parent != this )
		{
			Eco.Logger.error("refLayer 기준으로 insertBefore하지 못합니다.");
		}

		var oldparent = newLayer.Parent;
		if (oldparent)
		{
			oldparent.removeChild(newChild, oldparent == this);
		}

		var targetIdx = refLayer._index;

		var firstChild = this._firstChild;
		if (refLayer == firstChild)
		{
			firstChild._isFirstChild = false;
			newLayer._nextSibling = firstChild;
			newLayer._previousSibling = firstChild._previousSibling;
			firstChild._previousSibling = newLayer;
			this._firstChild = newLayer;
			newLayer._isFirstChild = true;
		}
		else
		{
			var prev = refLayer._previousSibling;
			newLayer._nextSibling = refLayer;
			prev._nextSibling = newLayer;
			refLayer._previousSibling = newLayer;
			newLayer._previousSibling = prev;
		}
		layers.splice(targetIdx, 0, newLayer);
		newLayer.Parent = this;
		newLayer.setOwner(this);
		for ( var i = idx, len = layers.length; i < len ; i++ )
		{
			layers[i]._index = i;
		}
	};

	_pGraphic.addChildren = function(arr)
	{
		if ( !Eco.isArray(arr) || !arr.length )
		{
			return false;
		}
		if ( !this._firstChild ) 
		{
			this._firstChild = arr[0];
			arr[0]._isFirstChild = true;
			for ( var i = 0, len = arr.length - 1 ; i < len ; i++ )
			{
				arr[i]._nextSibling = arr[i + 1];
				arr[i + 1]._previousSibling = arr[i];
			}
			arr[0]._previousSibling = arr[arr.length - 1];
		}
		else
		{
			var node = arr[0];
			var lastChild = this._firstChild._previousSibling;
			lastChild._nextSibling = arr[0];
			arr[0]._previousSibling = lastChild;
			for ( var i = 0, len = arr.length - 1 ; i < len ; i++ )
			{
				arr[i]._nextSibling = arr[i + 1];
				arr[i + 1]._previousSibling = arr[i];
			}
			this._firstChild._previousSibling = arr[arr.length - 1];
		}

		var layers = this._layers;
		var idx = layers.length - 1;
		layers.push.apply(layers, arr);
		for ( var i = idx, len = layers.length; i < len ; i++ )
		{
			layers[i]._index = i;
			layers[i].Parent = this;
			layers[i].setOwner(this);
		}
	};
	
	_pGraphic.removeChild = function(layer, notDepose)
	{
		var idx = layer._index;
		if ( idx != null )
		{
			var layers = this._layers;
			layers.splice(idx, 1);
			if (layer == this._firstChild)
			{
				layer._isFirstChild = false;
				this._firstChild = layer._nextSibling;
				var firstChild = this._firstChild;
				if (firstChild)
				{
					firstChild._isFirstChild = true;
					firstChild._previousSibling = layer._previousSibling;
				}
			}
			else
			{
				var prev = layer._previousSibling;
				var next = layer._nextSibling;
				prev._nextSibling = next;
				if (!next)
				{
					this._firstChild._previousSibling = prev;
				}
				else
				{
					next._previousSibling = prev;
				}
			}
			for ( var i = idx, len = layers.length; i < len ; i++ )
			{
				layers[i]._index = i;
			}
			layer._previousSibling = null;
			layer._nextSibling = null;
			layer.Parent = null;
			layer.setOwner(null);
			if ( !notDepose )
			{
				var removedLayers = this._removedLayers;
				if ( !removedLayers )
				{
					removedLayers = [];
					this._removedLayers = removedLayers;
				}
			}
			delete layer._index;
			removedLayers.push(layer);
		}
		return layer;
	};

	_pGraphic.removeChildren = function()
	{
		var node = this._firstChild,
			delected = false, removed = [];
		for ( ; node ; node = node._nextSibling )
		{
			removed[removed.length] = node;
		}
		var removedLayers = this._removedLayers;
		if ( removed.length )
		{
			if ( !removedLayers )
			{
				removedLayers = [];
				this._removedLayers = removedLayers;
			}
		}

		for ( var i = 0, len = removed.length; i < len ; i++ )
		{
			node = removed[i];
			node._remove(true);
			node.Parent = null;
			node._nextSibling = null;
			node._previousSibling = null;
			delete node._index;
			removedLayers.push(node);
			delected = true;
		}

		if ( delected )
		{
			this._firstChild._isFirstChild = false;
			this._firstChild = null;
		}
	};

	_pGraphic.addChild = function(layer)
	{
		if ( layer instanceof Eco.GraphicLayer )
		{
			var layers = this._layers,
				last;

			if ( !this._firstChild ) 
			{
				this._firstChild = layer;
				layer._isFirstChild = true;
				layer._previousSibling = layer;
			}
			else 
			{
				var lastChild = this._firstChild._previousSibling;
				lastChild._nextSibling = layer;
				layer._previousSibling = lastChild;
				this._firstChild._previousSibling = layer;
			}
			this._layers[layers.length] =  layer;
			layer._index = layers.length - 1;
			layer.Parent = this;
			layer.setOwner(this);
		}
	};

	_pGraphic.replaceChild = function(newLayer, oldLayer)
	{
		if ( !(newLayer instanceof Eco.GraphicLayer) || !(oldLayer instanceof Eco.GraphicLayer) )
		{
			Eco.Logger.error("newLayer, oldLayer 는 Eco.GraphicLayer이어야 한다.");
		}
		if ( oldLayer._index == null )
		{
			Eco.Logger.error("oldLayer 는 등록된 상태가 아니므로 실행될 수 없다.");
		}

		this.insertBefore(newLayer, oldLayer);
		if ( newLayer != oldLayer )
		{
			this.removeChild(oldLayer);
		}
		return oldLayer;
	};

	_pGraphic.getElementById = function(id)
	{
		if ( !id ) return null;
		return this._getItemById(id);
	};

	_pGraphic.getElementsByTagName = function(tag)
	{
		if ( !tag ) return [];
		return this._getItemsByFilter("_type", tag)||[];
	};

	_pGraphic.getElementsByClassName = function(clsName)
	{
		if ( !clsName ) return [];
		return this._getItemsByFilter("_class", clsName)||[];
	};

	_pGraphic._getItemById = function(val)
	{
		if ( this._firstChild )
		{
			var idCache = this._idCache;
			if ( !idCache || idCache._childchanged != this._childchanged )
			{
				idCache = {};
				this._idCache = idCache;
				idCache._childchanged = this._childchanged;
				var elem = this._firstChild;
				for ( ; elem ; elem = elem._nextSibling )
				{
					if ( elem._id )
					{
						idCache[elem._id] = elem;
					}
				}
			}
			var res = idCache[val];
			if ( !res )
			{
				var elem = this._firstChild;
				for ( ; elem ; elem = elem._nextSibling )
				{
					if ( elem._firstChild )
					{
						res = elem._getItemById(val);
						if ( res )
						{
							break;
						}
					}
				}
				return res;
			}
			return res;
		}
	};

	_pGraphic._getItemsByFilter = function(propNm, val)
	{
		if ( this._firstChild )
		{
			var filterCache = this._filterCache;
			if ( !filterCache )
			{
				filterCache = {};
				this._filterCache = filterCache;
			}
			var selectorkey = propNm + "_" + val,
				cached = filterCache[selectorkey],
				elem, seq;
			if ( !cached )
			{
				cached = {
					pElement: this,
					propertyNm: propNm,
					value: val,
					elements: null
				};
				filterCache[selectorkey] = cached;
			}
			if ( cached._childchanged != this._childchanged )
			{
				var arr = [];
				seq = 0;
				elem = this._firstChild;
				if ( val == "*" )
				{
					for ( ; elem ; elem = elem._nextSibling )
					{
						arr[seq] = elem;
						seq++;
					}
				}
				else
				{
					for ( ; elem ; elem = elem._nextSibling )
					{
						if ( elem[propNm] == val )
						{
							arr[seq] = elem;
							seq++;
						}
					}
				}
				cached.elements = arr;
				cached._childchanged = this._childchanged;
			}
			var elems = cached.elements,
				res = elems.slice(0), retvals = [];
			seq = 0;
			for ( var i = 0, len = elems.length ; i < len ; i++ )
			{
				elem = elems[i];
				if ( elem._firstChild )
				{
					seq++;
					retvals = elem._getItemsByFilter(propNm, val);
					retvals.unshift(seq, 0);
					res.splice.apply(res, retvals);
					seq += retvals.length - 2;
				}
				else
				{
					seq++;
				}
			}
			return res;
		}
		else
		{
			return null;
		}
	};

	_pGraphic.invalidate = function()
	{
		this._paint();
		if ( this._repaint )
		{
			this._repaint = false;
			var win_handle = this._getReferenceContext()._getWindowHandle(),
				that = this;
			if ( this._timeId != null )
			{
				nexacro._clearSystemTimer(win_handle, this._timeId);
				this._timeId = null;
			}
			this._timeId = nexacro._setSystemTimer(win_handle, 
			function() {
					nexacro._clearSystemTimer(win_handle, that._timeId);
					that._timeId = null;
					console.log("graph painted!!!!");
					that._paint();
			}, 10);
		}
	};

	_pGraphic.delayTask = function()
	{
		if ( this._painting )
		{
			this._repaint = true;
			return;
		}
		else
		{
			this._paint();
		}
	};

	_pGraphic._paint = function()
	{
		var control_elem = this.getElement();
		if ( control_elem )
		{
			if ( this._painting ) return;
			this._painting = true;
			var isDebug = this._debug;
			if ( isDebug ) Eco.Logger.startElapsed(true);

			var layers = this._layers,
				removedLayers = this._removedLayers,
				len;
			if ( layers && (len = layers.length) )
			{
				for ( var i = 0 ; i < len ; i++ )
				{
					layers[i]._beforePaint(isDebug);
				}
			}

			if ( removedLayers && (len = removedLayers.length) )
			{
				for ( var i = 0 ; i < len ; i++ )
				{
					removedLayers[i]._remove();
				}
				this._removedLayers = null;
			}

			if ( layers && (len = layers.length) )
			{
				var layer,
					viewport = this._viewport;
				if ( control_elem._handle )
				{
					var ctx;
					for ( var i = 0 ; i < len ; i++ )
					{
						layer = layers[i];
						ctx = layer._canvas;
						if ( !ctx )
						{
							ctx = new nexacro.CanvasElement(control_elem);
							layer._canvas = ctx;
							var bounds = layer._getCanvasRect();
							ctx.setElementPosition(bounds.x, bounds.y);
							ctx.setElementSize(bounds.width, bounds.height);
						}
						else if ( viewport._drawflags & 2 )
						{
							var bounds = layer._getCanvasRect();
							ctx.setElementPosition(bounds.x, bounds.y);
							ctx.setElementSize(bounds.width, bounds.height);
						}
					}
					var animate = this._animate;
					if ( animate )
					{
						for ( var i = 0 ; i < len ; i++ )
						{
							layer = layers[i];
							//layer._renderHint = "all";
						}
						for ( var i = 0 ; i < len ; i++ )
						{
							layer = layers[i];
							//layer._paint(isDebug, true);
							layer._paint(isDebug, false);
						}
						this._animatePainted = true;
					}
					else
					{
						var prevPaintedStatus = this._animatePainted;
						if ( prevPaintedStatus )
						{
							for ( var i = 0 ; i < len ; i++ )
							{
								layer = layers[i];
								//layer._renderHint = "all";
								//layer._refreshQuadTree = true;
							}
						}
						for ( var i = 0 ; i < len ; i++ )
						{
							layer = layers[i];
							layer._paint(isDebug);
							layer._refreshQuadTree = false;
						}
						this._animatePainted = false;
					}
					var prevLayer;
					for ( var i = 1 ; i < len ; i++ )
					{
						prevLayer = layers[i - 1];
						layer = layers[i];
						nexacro.__setElementHandleMoveToNext(prevLayer._canvas._handle, layer._canvas._handle);
					}
				}
				else
				{
					var ctx;
					for ( var i = 0 ; i < len ; i++ )
					{
						layer = layers[i];
						ctx = layer._canvas;
						if ( !ctx )
						{
							ctx = new nexacro.CanvasElement(control_elem);
							layer._canvas = ctx;
							var bounds = layer._getCanvasRect();
							ctx.setElementPosition(bounds.x, bounds.y);
							ctx.setElementSize(bounds.width, bounds.height);
						}
						else if ( viewport._drawflags & 2 )
						{
							var bounds = layer._getCanvasRect();
							ctx.setElementPosition(bounds.x, bounds.y);
							ctx.setElementSize(bounds.width, bounds.height);
						}
					}
					var animate = this._animate;
					if ( animate )
					{
						for ( var i = 0 ; i < len ; i++ )
						{
							layer = layers[i];
							//layer._renderHint = "all";
						}
						for ( var i = 0 ; i < len ; i++ )
						{
							layer = layers[i];
							//layer._paint(isDebug, true);
							layer._paint(isDebug, false);
						}
						this._animatePainted = true;
					}
					else
					{
						var prevPaintedStatus = this._animatePainted;
						if ( prevPaintedStatus )
						{
							for ( var i = 0 ; i < len ; i++ )
							{
								layer = layers[i];
								//layer._renderHint = "all";
								//layer._refreshQuadTree = true;
							}
						}
						for ( var i = 0 ; i < len ; i++ )
						{
							layer = layers[i];
							layer._paint(isDebug);
							layer._refreshQuadTree = false;
						}
						this._animatePainted = false;
					}
				}
				viewport._drawflags = 0;
				this._painting = false;
				this._activeLayer = layers[layers.length - 1];
			}
			if ( isDebug ) Eco.Logger.info({message: "paint 시간 !!!", elapsed: true, elapsedTotal: true});
		}
	};

	//===============================================================
	// nexacro.GraphicCtrl : Graphic
	//===============================================================
	nexacro.GraphicCtrl = function (id, position, left, top, width, height, right, bottom, parent)
	{
		nexacro.Graphic.call(this, id, position, left, top, width, height, right, bottom, parent); // 상속받을 Graphic 컴포넌트 생성자 호출
		this._is_subcontrol = true; // 컴포넌트를 컨트롤화
	};
	
	var _pGraphicCtrl = nexacro.GraphicCtrl.prototype = nexacro._createPrototype(nexacro.Graphic, nexacro.GraphicCtrl); // Graphic 컴포넌트를 상속
	_pGraphicCtrl._type_name = "GraphicControl"; // css에서 사용할 type name

	nexacro._setForControlStyleFinder(_pGraphicCtrl); // 컨트롤 전용 style finder 메소드 연결
	delete _pGraphicCtrl;
}
