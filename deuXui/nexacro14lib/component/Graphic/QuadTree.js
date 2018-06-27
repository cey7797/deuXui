if ( !JsNamespace.exist("Eco.GraphicQuadTree") )
{
	/**
	 * @class Eco.Graphic.Element
	 * @classdesc width, height property를 가지는 Size Class.
	*/
	JsNamespace.declareClass("Eco.QuadTree", {
		"_typename": "EcoQuadTree",
		/**
		 * Diagram.Graph 생성자(constructor)
		 * @param {Div} div Graph 모양을 그리는 container Div
		 * @param {...*} 속성값들
		 * @class Diagram.Graph
		 * @classdesc 다양한 shape를 그리고 operation 처리하는 기능이 있는 Class.
		 * @constructor Diagram.Graph
		*/
		initialize: function(x, y, w, h)
		{
			this._dict = new Eco.HashMap();
			w = Math.max(w, h);
			this._root = new Eco.QuadTreeNode(new Eco.Rectangle(x, y, w, w));
			//this._root = new Eco.Graphic.QuadTreeNode(new Eco.Rectangle(x, y, w, h));
			//console.log("root create:", this._root._bounds.toString());
			return this; //initialize는 반드시 이 코드 라인을 작성한다.
		},
		setDebug: function(topForm)
		{
			this._root._topForm = topForm;
			if ( topForm ) this._root._debug = true;
		},
		query: function(areaRect, results)
		{
			if ( Eco.isArray(results) )
			{
				this._root.query(areaRect, results);
			}
			else
			{
				return this._root.query(areaRect);
			}
		},
		getAll: function()
		{
			return this._dict.getValues();
		},
		getCount: function()
		{
			return this._dict.getSize();
		},
		move: function(item)
		{
			if ( this.contains(item) )
			{
				while (!this._checkRegion(item));
				this._root.move(item);
				return true;
			}
			else
			{
				return false;
			}
		},
		add: function(item)
		{
			while (!this._checkRegion(item));
			var root = this._root,
				map = this._dict;
			if(Eco.isArray(item))
			{
				var len = item.length;

				for(var i = 0; i < len; i++)
				{
					root.insert(item[i]);
					map.put(item[i], item[i]);
				}
			}
			else
			{
				root.insert(item);
				map.put(item, item);
			}
		},
		/**
		* Clears all nodes and children from the QuadTree
		* @method clear
		**/
		clear: function()
		{
			//this.root.clear();
			this._dict.clear();
			this._root.clear();
		},
		contains: function(item)
		{
			return this._dict.containsKey(item);
		},
		remove: function(item)
		{
			if ( this.contains(item) )
			{
				this._root.remove(item, true);
				this._dict.remove(item);
				return true;
			}
			else
			{
				return false;
			}
		},
		_checkRegion: function(item)
		{
			var root = this._root,
				bounds = root._bounds,
				itemBounds = item.getBounds();
			if ( !bounds || bounds.width == 0 || bounds.height == 0 )
			{
					Eco.Logger.error("QuadTree 의 bounds(width, height) 값이 0 이면 Rectangle정보를 구성할 수 없습니다.");
			}
			if ( !bounds._containsRectangle(itemBounds) )
			{
				var p;
				// If object is left of this node
				if(itemBounds.x < bounds.x)
				{
					// If object is to the top of this node
					if(itemBounds.y < bounds.y)
					{
						// Grow towards top left
						p = this._grow(bounds.width, bounds.height);
					}
					else
					{
						// Grow towards bottom left
						p = this._grow(bounds.width, 0);
					}
					// If object is right of this node
				}
				else if((itemBounds.x + itemBounds.width) > (bounds.x + bounds.width))
				{
					// If object is to the top of this node
					if(itemBounds.y < bounds.y)
					{
						// Grow towards top right
						p = this._grow(0, bounds.height);
					}
					else
					{
						// Grow towards bottom right
						p = this._grow(0, 0);
					} 
				// If object is within x-axis but top of node
				}
				else if(itemBounds.y < bounds.y)
				{
					// Grow towards top right (top left is just as valid though)
					p = this._grow(0, bounds.height);
				// If object is within x-axis but bottom of node
				}
				else if(itemBounds.y + itemBounds.height > bounds.y + bounds.height)
				{
					// Grow towards bottom right (bottom left is just as valid though)
					p = this._grow(0, 0);
				}
				if ( p )
				{
					this._root = p;
					//console.log("root change:", p._bounds.toString());
				}
				return false;
			}
			return true;
		},
		_grow: function(xoffset, yoffset)
		{
			var root = this._root,
				bounds = root._bounds;
			var x = bounds.x - xoffset,
				y = bounds.y - yoffset,
				cls = Eco.QuadTreeNode,
				rectCls = Eco.Rectangle,
				w = bounds.width,
				h = bounds.height;

			//console.log("grow Root Bounds", x, y, bounds.width * 2, bounds.height * 2);
			
			var p = new cls(x, y, bounds.width * 2, bounds.height * 2);
			var nodes = p._childNodes;
			if(xoffset)
			{
				if(yoffset)
				{
					nodes[3] = root; //br
					root.Parent = p;
				}
				else
				{
					nodes[2] = root; //tr
					root.Parent = p;
				}
			}
			else if(yoffset)
			{
				nodes[1] = root; //bl
				root.Parent = p;
			}
			else
			{
				nodes[0] = root; //tl
				root.Parent = p;
			}
 			nodes[0] = nodes[0] || new cls(p, new rectCls(x, y, w, h)); //tl
			nodes[1] = nodes[1] || new cls(p, new rectCls(x, y + h, w, h)); //bl
			nodes[2] = nodes[2] || new cls(p, new rectCls(x + w, y, w, h)); //tr
			nodes[3] = nodes[3] || new cls(p, new rectCls(x + w, y + h, w, h)); //br
			return p;
		}
	}); //end of 'JsNamespace.declareClass("Eco.Graphic.QuadTree", {'
} //end of 'if ( !JsNamespace.exist("Eco.Graphic.QuadTree") )'

if ( !JsNamespace.exist("Eco.QuadTreeNode") )
{
	JsNamespace.declareClass("Eco.QuadTreeNode", {
		/**
		 * Diagram.Graph 생성자(constructor)
		 * @param {Div} div Graph 모양을 그리는 container Div
		 * @param {...*} 속성값들
		 * @class Diagram.Graph
		 * @classdesc 다양한 shape를 그리고 operation 처리하는 기능이 있는 Class.
		 * @constructor Diagram.Graph
		*/
		initialize: function()
		{
			if ( arguments.length == 4 )
			{
				this._bounds = new Eco.Rectangle(arguments[0], arguments[1],
					arguments[2], arguments[3]);
			}
			else if ( arguments.length == 2 )
			{
				this.Parent = arguments[0];
				this._bounds = arguments[1];
			}
			else if ( arguments.length == 1 )
			{
				this._bounds = arguments[0];
			}
			this._items = []; //이 node에 속한 요소들
			this._childNodes = []; //4node로 쪼개어 진다.
			return this; //initialize는 반드시 이 코드 라인을 작성한다.
		},
		maxItemsPerNode: 2,
		isEmptyLeaf: function()
		{
			return this.getCount() == 0 && this._childNodes.length == 0 ;
		},
		getCount: function()
		{
			var cnt = 0;

			cnt += this._items.length;

			var nodes = this._childNodes, node;
			for ( var i = 0, len = nodes.length ; i < len ; i++ )
			{
				node = nodes[i];
				cnt += node.getCount();
			}
			return cnt;
		},
		add: function(item)
		{
			item._qOwner = this;
			var items = this._items;
			items.push(item);
		},
		_remove: function(item)
		{
			var items = this._items;
			var idx = Eco.array.indexOf(items, item);
			if ( idx >= 0 )
			{
				items[idx] = items[items.length - 1];
				Eco.array.removeAt(items, items.length - 1);
			}
		},
		relocate: function(item)
		{
			if ( this._bounds._containsRectangle(item.getBounds()) )
			{
				var nodes = this._childNodes;
				if ( nodes.length )
				{
					var dest = this.getDestTree(item);
					if ( item._qOwner != dest )
					{
						var formerOwner = item._qOwner;
						this.remove(item, false);
						dest.insert(item);
						if ( formerOwner )
						{
							formerOwner.cleanUpwards();
						}
					}
				}
			}
			else
			{
				if ( this.Parent != null )
				{
					//console.log(this.Parent);
					this.Parent.relocate(item);
				}
			}
		},
		cleanUpwards: function()
		{
			var nodes = this._childNodes;
			if ( nodes.length )
			{
				if ( nodes[0].isEmptyLeaf() &&
					nodes[1].isEmptyLeaf() &&
					nodes[2].isEmptyLeaf() &&
					nodes[3].isEmptyLeaf() )
				{
					this._childNodes = [];
					if ( this.Parent != null && this.getCount() == 0 )
					{
						this.Parent.cleanUpwards();
					}
				}
			}
			else
			{
				if ( this.Parent != null && this.getCount() == 0 )
				{
					this.Parent.cleanUpwards();
				}
			}
		},
		clear: function()
		{
			var nodes = this._childNodes;
			if ( nodes.length )
			{
				nodes[0].clear();
				nodes[1].clear();
				nodes[2].clear();
				nodes[3].clear();
			}
			this._childNodes = [];
			var items = this._items;
			for ( var i = 0, len = items.length ; i < len ; i++ )
			{
				items[i]._qOwner = null;
			}
			this._items = [];
		},
		remove: function(item, bClean)
		{
			if ( item._qOwner != null )
			{
				if ( item._qOwner == this )
				{
					this._remove(item);
					if ( bClean )
					{
						this.cleanUpwards();
					}
				}
				else
				{
					item._qOwner.remove(item, bClean);
				}
			}
		},
		getAll: function(results)
		{
			results.push.apply(results, this._items);
			var nodes = this._childNodes;
			for ( var i = 0, len = nodes.length ; i < len ; i++ )
			{
				nodes[i].getAll(results);
			}
		},
		query: function(areaRect, results)
		{
			if ( results != null )
			{
				var bounds = this._bounds;
				if ( areaRect._containsRectangle(bounds) )
				{
					this.getAll(results);
				}
				else if ( areaRect.intersects(bounds) )
				{
					//trace("==>" + bounds.toString());
					var nodes = this._childNodes,
						items = this._items;
					for ( var i = 0, len = items.length ; i < len ; i++ )
					{
						if ( areaRect.intersects(items[i].getBounds()) )
						{
							results.push(items[i]);
							//trace("push===>" + items[i]._id);
						}
					}
					for ( var i = 0, len = nodes.length ; i < len ; i++ )
					{
						nodes[i].query(areaRect, results);
					}
				}
			}
			else
			{
				var res = [];
				this.query(areaRect, res);
				return res;
			}
		},
		insert: function(item)
		{
			var bounds = this._bounds,
				itemBounds = item.getBounds();

			if ( !bounds._containsRectangle(itemBounds) )
			{
				return;
			}
			var nodes = this._childNodes,
				items = this._items;
			
			if ( items.length == 0 ||
				 ( nodes.length == 0 && items.length + 1 <= this.maxItemsPerNode) )
			{
				this.add(item);
			}
			else
			{
				if ( nodes.length == 0 )
				{
					this._createSubNodes();
				}
				var dest = this.getDestTree(item);
				if ( dest == this )
				{
					this.add(item);
				}
				else
				{
					dest.insert(item);
				}
			}
		},
		_createSubNodes: function()
		{
			var bounds = this._bounds;
			//if ( this._depth >= this._maxDepth || (bounds.width * bounds.height) <= 10 )
			if ( (bounds.width * bounds.height) <= 10 )
			{
				return;
			}
			var halfWidth = bounds.width/2;
			var halfHeight = bounds.height/2;
			var left = bounds.x;
			var top = bounds.y;
			var nodes = this._childNodes,
				cls = Eco.QuadTreeNode,
				rectCls = Eco.Rectangle;
			nodes[0] = new cls(this, new rectCls(left, top, halfWidth, halfHeight)); //tl
			nodes[1] = new cls(this, new rectCls(left, top + halfHeight, halfWidth, halfHeight)); //bl
			nodes[2] = new cls(this, new rectCls(left + halfWidth, top, halfWidth, halfHeight)); //tr
			nodes[3] = new cls(this, new rectCls(left + halfWidth, top + halfHeight, halfWidth, halfHeight)); //br

			var items = this._items, dest;
			for ( var i = 0 ; i < items.length ; i++ )
			{
				dest = this.getDestTree(items[i]);
				if ( dest != this )
				{
					dest.insert(items[i]);
					this._remove(items[i]);
					i--;
				}
			}
			if ( this._getDebug() )
			{
				this._drawNode(left, top, halfWidth, halfHeight);
				this._drawNode(left, top + halfHeight, halfWidth, halfHeight);
				this._drawNode(left + halfWidth, top, halfWidth, halfHeight);
				this._drawNode(left + halfWidth, top + halfHeight, halfWidth, halfHeight);
			}
		},
		getDestTree: function(item)
		{
			var destTree = this;
			var nodes = this._childNodes, node, nodeBounds;
			for ( var i = 0, len = nodes.length ; i < len ; i++ )
			{
				node = nodes[i];
				nodeBounds = node._bounds;
				if ( nodeBounds._containsRectangle(item.getBounds()) )
				{
					destTree = node;
					return destTree;
				}
			}
			return destTree;
		},
		move: function(item)
		{
			if ( item._qOwner != null )
			{
				item._qOwner.relocate(item);
			}
			else
			{
				this.relocate(item);
			}
		},
		_getDebug: function()
		{
			if ( this._debug == null )
			{
				var p = this;
				while(p.Parent)
				{
					p = p.Parent;
				}
				if ( p._debug ) this._debug = true;
				else this._debug = false;
			}
			return this._debug;
		},
		_getTopForm: function()
		{
			if ( this._topForm == null )
			{
				var p = this;
				while(p.Parent)
				{
					p = p.Parent;
				}
				this._topForm = p._topForm;
			}
			return this._topForm;
		},
		_drawNode: function(x, y, w, h)
		{
			return;
			var topForm = this._getTopForm();
			if ( topForm )
			{
				var c = XpCompFactory.getXpComp(topForm, "Shape");
				XpCompFactory.setProperties(c, "type", "rectangle", "style", "background: transparent; fillbrush: solid transparent; strokepen: 1 solid red;",
					"transparenthittest", true);
				var b = new EcoSys.Rectangle(x, y, w, h);
				XpCompFactory.setRect(c, b);
				trace(b.toString());
			}
		}
	});
}
		