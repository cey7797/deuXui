if ( !JsNamespace.exist("Eco.GraphicViewPort") )
{
	/**
	 * @class Eco.Graphic.ViewPort
	 * @classdesc width, height property를 가지는 Size Class.
	*/
	JsNamespace.declareClass("Eco.GraphicViewPort", {
		"_typename": "EcoGraphicViewPort",
		initialize: function(g, w, h)
		{
			this._graphic = g;
			this.setSize(new Eco.Size(w, h));
			this._matrix = new Eco.Matrix();
			this._zoom = 1;
			this._perspectiveFactor = 1;
			this._drawflags = 1;
		},
		setSize: function()
		{
			if ( arguments.length == 0 )
			{
				return;
			}
			var sz;
			if ( arguments[0] instanceof Eco.Size )
			{
				sz = arguments[0];
			}
			else if ( Eco.isNumber(arguments[0]) && Eco.isNumber(arguments[1]) )
			{
				sz = new Eco.Size(arguments[0], arguments[1]);
			}
			else
			{
				Eco.Logger.error("arguments error! ==>" + Eco.Logger._getDumpString(arguments, true));
			}
			var oldSize = this._size;
			if ( oldSize )
			{
				if ( !sz.equals(oldSize) )
				{
					this._size = sz;
					this._bounds = null;
					this._drawflags |= 2;
				}
			}
			else
			{
				this._size = sz;
				this._bounds = null;
				this._drawflags |= 2;
			}
		},
		getBounds: function()
		{
			if (!this._bounds)
			{
				var sz = this._size;
				this._bounds = this._getInverse()._transformBounds(new Eco.Rectangle(0, 0, sz.width, sz.height));
			}
			return this._bounds;
		},
		getCenter: function()
		{
			return this.getBounds().getCenter();
		},
		setCenter: function(center)
		{
			if ( !(center instanceof Eco.Point) )
			{
				center = new Eco.Point(arguments[0], arguments[1]);
			}
			this.scrollBy(center.subtract(this.getCenter()));
		},
		getZoom: function()
		{
			return this._zoom;
		},
		setZoom: function(zoom)
		{
			var val = zoom / this._zoom;
			var pt = new Eco.Point(val, val);
			var m = new Eco.Matrix();
			m.scale(pt, this.getCenter());
			this._transform(m);
			//this._graphic._viewPortChanged(5); //poschanged, transformchanged
			this._zoom = zoom;
		},
		scrollBy: function(pt)
		{
			if ( !(pt instanceof Eco.Point) )
			{
				pt = new Eco.Point(arguments[0], arguments[1]);
			}
			var m = new Eco.Matrix();
			this._transform(m.translate(pt.negate()));
			//this._graphic._viewPortChanged(1); //poschanged
		},
		graphicToView: function(pt)
		{
			if ( !(pt instanceof Eco.Point) )
			{
				pt = new Eco.Point(arguments[0], arguments[1]);
			}
			return this._matrix._transformPoint(pt);
		},
		viewToGraphic: function()
		{
			if ( !(pt instanceof Eco.Point) )
			{
				pt = new Eco.Point(arguments[0], arguments[1]);
			}
			return this._getInverse()._transformPoint(pt);
		},
		_getInverse: function()
		{
			if (!this._inverse)
				this._inverse = this._matrix.inverted();
			return this._inverse;
		},
		_transform: function(m)
		{
			if ( m ) this._matrix.concatenate(m);
			this._drawflags |= 1;
			this._bounds = null;
			this._inverse = null;
			//this._changed = true;
			//this.draw();
		}
	}); //end of 'JsNamespace.declareClass("Eco.Graphic.ViewPort", {'
} //end of 'if ( !JsNamespace.exist("Eco.Graphic.ViewPort") )'
		