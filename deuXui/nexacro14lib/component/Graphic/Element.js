if ( !JsNamespace.exist("Eco.HashMap") )
{
	JsNamespace.declareClass("Eco.HashMap", {
		/**
		 * HashMap 생성자(constructor)
		 * @class HashMap
		 * @classdesc key와 value 를 묶어서 하나의 entry로 저장한다.
		 * hasing을 사용하기 때문에 많은 양의 데이터를 검색하는데 뛰어난 성능을 보인다.<br>
		 * key값은 중복되지 않고 value값은 중복허용.<br>
		 * @constructor HashMap
		*/
		initialize: function()
		{
			this._size = 0;
			this._map = {};
		},
		statics: {
			_hashkeypool: {},
			_simpleToString: function()
			{
				return this._hashKey;
			},
			getHashKey: function(prefix, obj)
			{
				var key;
				if ( obj._hashKey == null )
				{
					var nextVal = this._hashkeypool[prefix];
					if ( nextVal == null )
					{
						nextVal = 0;
						this._hashkeypool[prefix] = nextVal;
					}
					else
					{
						nextVal++;
						this._hashkeypool[prefix] = nextVal;
					}
					key = prefix + nextVal;
					obj._hashKey = key;
				}
				else
				{
					key = obj._hashKey;
				}
				return key;
			},
			/**
			 * 객체를 hashMap의 key로 구성하고자 할 때, 해당 객체의 toString메소드가 유일한 key 값으로 반환하여야 하는데<br>
			 * 그렇지 않으면 toString 함수가 유일하게 반환 할 수 있도록 처리하여 주는 함수이다. 
			 * @param {object} obj 처리할 객체
			 * @param {number} toStringFn 주어지는 함수로 toString메소드 구성(this._hashKey를 이용하여 unique 값을 구성하면서 추가로 로직을 더한다. 만약 이 argument를 구성하지 않으면 return this._hashKey; toString이 구성된다.
			 * @static
			 * @memberOf Eco.HashMap
			*/
			makeHashKey: function(obj, toStringFn)
			{
				obj._hashKey = Eco.getUniqueId();
				obj.toString = toStringFn||this._simpleToString;
			},
			/**
			 * 주어진 arr에 존재하는 요소인 객체를 hashMap의 key로 구성하고자 할 때, 해당 객체의 toString메소드가 유일한 key 값으로 반환하여야 하는데<br>
			 * 그렇지 않으면 toString 함수가 유일하게 반환 할 수 있도록 처리하여 주는 함수이다. 
			 * @param {array} arr arr에 구성된 객체를 대상으로 처리한다.
			 * @param {number} toStringFn 주어지는 함수로 toString메소드 구성(this._hashKey를 이용하여 unique 값을 구성하면서 추가로 로직을 더한다. 만약 이 argument를 구성하지 않으면 return this._hashKey; toString이 구성된다.
			 * @static
			 * @memberOf Eco.HashMap
			*/
			makeHashKeyForArray: function(arr, toStringFn)
			{
				var getUniFn = Eco.getUniqueId;
				toStringFn = toStringFn||this._simpleToString;
				for ( var i = 0, len = arr.length ; i < len ; i++ )
				{
					arr[i]._hashKey = getUniFn();
					arr[i].toString = toStringFn;
				}
			}
		},
		/**
		 * 주어진 key에 value값을 저장한다.
		 * @param {string | object} key key
		 * @param {object} value value
		 * @return {*} 해당 key의 저장된 value값
		 * @memberOf Eco.HashMap
		*/
		put: function(key, value)
		{
			if ( this.containsKey(key) ) return false;
			var map = this._map;
			this._size++;
			return map[key] = value;
		},
		/**
		 * 키에 해당하는 value 반환.
		 * @param {string | object} key key
		 * @return {*} 키에 해당하는 value
		 * @memberOf Eco.HashMap
		*/
		"get": function(key)
		{
			return this._map[key];
		},
		/**
		 * 키에 해당하는 value 제거.
		 * @param {string | object} key key
		 * @return {boolean} 처리 결과
		 * @memberOf Eco.HashMap
		*/
		remove: function(key)
		{
			if ( !this.containsKey(key) ) return false;
			var map = this._map;
			this._size--;
			delete map[key];
			return true;
		},
		/**
		 * 주어진 key 값이 존재하는지 확인하는 함수
		 * @param {string} key key
		 * @return {boolean} 존재 여부
		 * @memberOf Eco.HashMap
		*/
		containsKey: function(key)
		{
			return key in this._map;
		},
		/**
		 * Eco.HashMap size 반환.
		 * @return {number} 저장된 size
		 * @memberOf Eco.HashMap
		*/
		getSize: function()
		{
			return this._size;
		},
		/**
		 * Eco.HashMap clear.
		 * @memberOf Eco.HashMap
		*/
		clear: function()
		{
			this._size = 0;
			this._map = {};
		},
		/**
		 * Dataset로 부터 key로 구성할 column명과 value로 구성할 column명을 가지고 hashMap 데이터를 구성한다.
		 * @param {Dataset} ds Dataset 객체
		 * @param {string} keyColumn key로 사용할 필드명
		 * @param {string} valueColumn value로 사용할 필드명
		 * @param {boolean} bClear 기존 데이터 clear여부 default true
		 * @memberOf Eco.HashMap
		*/
		fromDataset: function(ds, keyColumn, valueColumn, bClear)
		{
			if ( bClear !== false )
			{
				this.clear();
			}
			if ( valueColumn )
			{
				for ( var i = 0, len = ds.getRowCount() ; i < len ; i++ )
				{
					this.put(ds.getColumn(i, keyColumn), ds.getColumn(i, valueColumn));
				}
			}
			else
			{
				for ( var i = 0, len = ds.getRowCount() ; i < len ; i++ )
				{
					this.put(ds.getColumn(i, keyColumn), i);
				}
			}
		},
		/**
		 * hashMap에 저장된 값을 주어진 flag에 따라 array에 담아서 반환한다.<br>
		 * flag 값에 의해 데이터 구성 종류<br>
		 * 0 → value<br>
		 * 1 → key<br>
		 * 2-> {key: key, value: value}
		 * @param {number} flag 0 → only value, 1 → only key, 2-> {key: key, value: value}
		 * @return {*} 저장된 값을 주어진 flag에 따라 처리된 array
		 * @memberOf Eco.HashMap
		*/
		toArray: function(flag)
		{
			var arr = [];
			if ( flag == null ) flag = 0;
			if ( flag == 0 ) // getValues
			{
				this.forEach(function(key) { arr.push(key); });
			}
			else if ( flag == 1 ) // getKeys
			{
				this.forEach(function(key, value) { arr.push(value); });
			}
			else
			{
				this.forEach(function(key, value) { arr.push({key: key, value: value}); });
			}
			return arr;
		},
		/**
		 * 이곳에 저장된 데이터만큼 반복하여 주어진 f(function)을 호출하는 함수이다.
		 * @param {function} f 
		 * @memberOf Eco.HashMap
		*/
		forEach: function(f)
		{
			var map = this._map;
			for (var key in map)
			{
				f.call(this, key, map[key]);
			}
		},
		/**
		 * Eco.HashMap 저장된 값들을 trace로 확인한다.
		 * @private
		 * @memberOf Eco.HashMap
		*/
		_debug: function(bConsole)
		{
			if ( bConsole )
			{
				this.forEach(function(key, value) {
					console.log("key: ", key, "value: ", value);
				});
			}
			else
			{
				this.forEach(function(key, value) {
					trace(key + "--->" + value);
				});
			}
		}
	}); // end of 'JsNamespace.declare("Eco.HashMap",'
} // end of 'if ( !JsNamespace.exist("Eco.HashMap") )

if ( !JsNamespace.exist("Eco.GraphicElement") )
{
	/**
	 * @class Eco.Graphic.Element
	 * @classdesc width, height property를 가지는 Size Class.
	*/
	JsNamespace.declareClass("Eco.GraphicElement", {
		"_typename": "EcoGraphicElement",
		initialize: function(x, y)
		{
			Eco.HashMap.getHashKey("ge", this);
			this._matrix = new Eco.Matrix();
			this._drawflags = 0; // 4 + 2 + 8 + 1 + 16 + 64
			this._painted = 0;
			this._style = {};
			var read = 0,
				args = arguments,
				argslen = args.length;
			if ( argslen > 0 )
			{
				if ( x instanceof Eco.Point )
				{
					read++;
					this._matrix.translate(x);
					this._x = x.x;
					this._y = x.y;
					this._drawflags |= 1;
				}
				else if ( argslen >= 2 )
				{
					this._x = x;
					this._y = y;
					pt = new Eco.Point(x, y);
					this._matrix.translate(pt);
					this._drawflags |= 1;
					read += 2;
				}
			}
			else
			{
				this._x = 0;
				this._y = 0;
			}
			this.__readArgs = read;
		},
		properties: {
			"id": {
				memberName : "_id"
			},
			"class": {
				memberName : "_class"
			},
			"strokepen": {
				"set": function()
				{
					var argsLen = arguments.length, val, newobj;
					if ( argsLen == 0 ) return;
					if ( argsLen == 1 )
					{
						val = arguments[0];
						if ( Eco.isString(val) )
						{
							newobj = nexacro._getCachedStrokepenObj(val);
						}
						else if ( val instanceof nexacro.Style_strokepen )
						{
							newobj = nexacro._getCachedStrokepenObj(val._value);
						}
						else if ( Eco.isObject(val) )
						{
							val = Eco.Graphic.Style.getKeyForStrokepen(val);
							newobj = nexacro._getCachedStrokepenObj(val);
						}
						else if ( Eco.isArray(val) )
						{
							val = Eco.Graphic.Style.getKeyForStrokepen(val[0], val[1], val[2]);
							newobj = nexacro._getCachedStrokepenObj(val);
						}
						else
						{
							newobj = null;
						}
					}
					else
					{
						val = Eco.Graphic.Style.getKeyForStrokepen(arguments[0], arguments[1], arguments[2]);
						newobj = nexacro._getCachedStrokepenObj(val);
					}
					var s = this._style,
						oldobj = s.strokepen;
					if ( oldobj != newobj )
					{
						s.strokepen = newobj;
						this._drawflags |= 8; //stylechanged
						if ( newobj )
						{
							var lineW = newobj.width||0;
							if ( this._strokeWidth != lineW )
							{
								//this._setOldGlobalBounds();
								this._strokeWidth = lineW;
								if ( lineW%2 ) this._isOddStrokeWidth = true;
								else this._isOddStrokeWidth = false;
								this._bounds = null;
								this._globalBounds = null;
							}
						}
						else
						{
							if ( this._strokeWidth != 0 )
							{
								//this._setOldGlobalBounds();
								this._strokeWidth = 0;
								this._isOddStrokeWidth = false;
							}
						}
						this._changedStrokepen = true;
					}
				},
				"get": function(bRetArray)
				{
					var val = this._style.strokepen;
					if ( bRetArray )
					{
						if ( val )
						{
							return [val.width, val.style, val.color];
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
			"fillbrush": {
				"set": function()
				{					
					var argsLen = arguments.length, val, newobj;
					if ( argsLen == 0 ) return;
					if ( argsLen == 1 )
					{
						val = arguments[0];
						if ( Eco.isString(val) )
						{
							newobj = nexacro._getCachedFillbrushObj(val);
						}
						else if ( val instanceof nexacro.Style_fillbrush )
						{
							newobj = nexacro._getCachedFillbrushObj(val._value);
						}
						else if ( Eco.isObject(val) )
						{
							val = Eco.Graphic.Style.getKeyForFillbrush(val);
							newobj = nexacro._getCachedFillbrushObj(val);
						}
						else if ( Eco.isArray(val) )
						{
							val = Eco.Graphic.Style.getKeyForFillbrush(val[0], val[1]);
							newobj = nexacro._getCachedFillbrushObj(val);
						}
						else
						{
							newobj = null;
						}
					}
					else
					{
						val = Eco.Graphic.Style.getKeyForFillbrush(arguments[0], arguments[1]);
						newobj = nexacro._getCachedFillbrushObj(val);
					}
					var s = this._style,
						oldobj = s.fillbrush;
					if ( oldobj != newobj )
					{
						s.fillbrush = newobj;
						this._drawflags |= 8; //stylechanged
						this._changedFillbrush = true;
						this._isGradation = newobj.style == "gradation" && s.fillgradation;
					}
				},
				"get": function(bRetArray)
				{
					var val = this._style.fillbrush;
					if ( bRetArray )
					{
						if ( val )
						{
							return [val.style, val.color];
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
			"fillgradation": {
				"set": function()
				{					
					var argsLen = arguments.length, val, newobj;
					if ( argsLen == 0 ) return;
					if ( argsLen == 1 )
					{
						val = arguments[0];
						if ( Eco.isString(val) )
						{
							newobj = nexacro._getCachedGradationObj(val);
						}
						else if ( val instanceof nexacro.Style_gradation )
						{
							newobj = nexacro._getCachedGradationObj(val._value);
						}
						else if ( Eco.isObject(val) )
						{
							val = Eco.Graphic.Style.getKeyForFillgradation(val);
							newobj = nexacro._getCachedGradationObj(val);
						}
						else if ( Eco.isArray(val) )
						{
							val = Eco.Graphic.Style.getKeyForFillgradation(val[0], val[1], val[2], val[3], val[4], val[5], val[6], val[7]);
							newobj = nexacro._getCachedGradationObj(val);
						}
						else
						{
							newobj = null;
						}
					}
					else
					{
						val = Eco.Graphic.Style.getKeyForFillgradation(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4], arguments[5], arguments[6], arguments[7]);
						newobj = nexacro._getCachedGradationObj(val);
					}
					var s = this._style,
						oldobj = s.fillgradation;
					if ( oldobj != newobj )
					{
						s.fillgradation = newobj;
						this._isGradation = newobj && s.fillbrush && (s.fillbrush.style == "gradation");
						if ( this._isGradation )
						{
							this._drawflags |= 8; //stylechanged
							this._changedFillbrush = true;
						}
					}
				},
				"get": function(bRetArray)
				{
					var val = this._style.fillgradation;
					if ( bRetArray )
					{
						if ( val )
						{
							return [val.style, val.start_point, val.start_color, val.end_point, val.end_color, val.peglist];
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
			x: {
				"get": function()
				{
					return this._x;
				},
				"set": function(val)
				{
					if ( !Eco.isNumber(val) ) return;
					if ( this._x != val )
					{
						this.translate(val - this._x, 0);
						this._x = val;
					}
				}
			},
			y: {
				"get": function()
				{
					return this._y;
				},
				"set": function(val)
				{
					if ( !Eco.isNumber(val) ) return;
					if ( this._y != val )
					{
						this.translate(0, val - this._y);
						this._y = val;
					}
				}
			},
			point: {
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
			},
			cx: {
				"get": function()
				{
					var pos = this._center
							|| (this._center = this.getBounds().getCenter());
					return pos.x;
				},
				"set": function(val)
				{
					var pos = this.getCenter();
					if ( pos.x != val )
					{
						this.translate(val - pos.x, 0);
					}
				}
			},
			cy: {
				"get": function()
				{
					var pos = this._center
							|| (this._center = this.getBounds().getCenter());
					return pos.y;
				},
				"set": function(val)
				{
					var pos = this.getCenter();
					if ( pos.y != val )
					{
						this.translate(0, val - pos.y);
					}
				}
			},
			center: {
				"get": function(bRetArray)
				{
					var pos = this._center
							|| (this._center = this.getBounds().getCenter());
					if ( bRetArray )
					{
						return [pos.x, pos.y];
					}
					else
					{
						return pos;
					}
				},
				"set": function(pt)
				{
					var argLen = arguments.length;
					if ( argLen == 0 ) return;
					if ( pt == null ) //default
					{
						pt = new Eco.Point();
					}
					else if ( !(pt instanceof Eco.Point) )
					{
						pt = new Eco.Point(arguments[0], arguments[1]);
					}
					this.translate(pt.subtract(this.getCenter()));
				}
			},
			transform: {
				"set": function(val)
				{
					this._toTransform(val);
				},
				"get": function()
				{
					return this._getTransform();
				}
			},
			matrix: {
				"set": function(m)
				{
					if ( m == null ) //default
					{
						m = new Eco.Matrix();
					}
					this._matrix.initialize(m);
					this._drawflags |= 1; //matrixchanged
				},
				"get": function()
				{
					return this._matrix;
				}
			},
			nextSibling:
			{
				memberName : "_nextSibling"
			},
			previousSibling:
			{
				memberName : "_previousSibling",
				"get" : function()
				{
					// if we are the firstChild, previousSibling actually refers to our
					// parent's lastChild, but we hide that
					return this._isFirstChild ? null : this._previousSibling;
				}
			},
			firstChild:
			{
				memberName : "_firstChild"
			},
			lastChild:
			{
				memberName : "_lastChild",
				"get": function()
				{
					// last child is stored as the previous sibling of first child
					var child = this._firstChild;
					return child ? child._previousSibling : null;
				},
				"set": function(val)
				{
					var child = this._firstChild;
					if ( child )
					{
						child._previousSibling = val;
					}
				}
			},
			owner:
			{
				memberName : "_owner",
				"set": function(owner)
				{
					if ( owner != this._owner )
					{
						var child = this._firstChild;
						for ( ; child != null ; child = child._nextSibling)
						{
							child.setOwner(owner);
						}
						this._owner = owner;
					}
				}
			},
			visible: {
				memberName : "_visible",
				value: true,
				"set": function(val)
				{
					var argsLen = arguments.length;
					if ( argsLen == 0 ) return;
					if ( val == null ) //default
					{
						val = true;
					}
					if ( !!val != this._visible )
					{
						/**/
						var p = this.Parent;
						if ( p )
						{
							p._drawflags |= 4;
						}
						
						this._visible = !!val;
						//this._drawflags |= 2;
					}
				}
			},
			opacity: {
				memberName : "_opacity",
				value: 1,
				"set": function(val)
				{
					var argsLen = arguments.length;
					if ( argsLen == 0 ) return;
					if ( val != this._opacity )
					{
						this._opacity = val||1;
						//this._drawflags |= 8;
					}
				}
			},
			cursor: {
				"set": function()
				{
					var argsLen = arguments.length, val, newobj;
					if ( argsLen == 0 ) return;
					if ( argsLen )
					{
						val = arguments[0];
						if ( Eco.isString(val) )
						{
							newobj = nexacro._getCachedValueObj(val);
						}
						else if ( val instanceof nexacro.Style_value )
						{
							newobj = nexacro._getCachedValueObj(val._value);
						}
						else
						{
							newobj = null;
						}
					}
					var oldobj = this._cursor;
					if ( oldobj != newobj )
					{
						this._cursor = newobj;
					}
				},
				"get": function()
				{
					var val = this._cursor;
					if ( val )
					{
						return val._value;
					}
					return null;
				}
			},
			clipPath: {
				"set": function() //일단 Eco.Rectangle만 처리
				{
					var argsLen = arguments.length, val, newobj;
					if ( argsLen == 0 ) return;
					if ( argsLen )
					{
						val = arguments[0];
						if ( !(val instanceof Eco.Rectangle) )
						{
							Eco.Logger.error("현재는 argument로 Eco.Rectangle만 지원한다!!");
						}
					}
					var clipItems = this._clipItems;
					if ( !clipItems )
					{
						clipItems = [];
						this._clipItems = clipItems;
					}
					clipItems.push(val);
				},
				"get": function()
				{
					return this._clipItems;
				}
			}
		},
		events: {
			onmousemove: {},
			onlbuttondown: {},
			onlbuttonup: {},
			onrbuttondown: {},
			onrbuttonup: {},
			onmouseenter: {},
			onmouseleave: {},
			onclick: {},
			ondblclick: {},
			ondrag: {},
			ondragstart: {},
			ondragend: {}
		},
		toString: function()
		{
			return this.getClassName() + "#" + (this.name ? this.name + "(" + this._hashKey + ")" : this._hashKey);
		},
		clone: function(deep)
		{
			var newElem = new this.constructor();
			//속성들 복사 처리필요.
			if ( deep )
			{
				var child = this._firstChild;
				for ( ; child != null ; child = child._nextSibling)
				{
					newElem.appendChild(child.clone(true));
				}
			}
			return newElem;
		},
		_getTransform: function()
		{
			var m = this._matrix.clone(),
				xyM = new Eco.Matrix();
 
			xyM.translate(-this._x - (this._dx||0), -this._y - (this._dy||0));

			m.preConcatenate(xyM);

			if (m.isIdentity())
				return "";

			var fmtNubmer = Eco.ClassUtils.fmtNumber;
			var decomposed = m.decompose();
			if (decomposed && !decomposed.shearing)
			{
				var parts = [],
					angle = decomposed.rotation,
					trans = decomposed.translation,
					scale = decomposed.scaling;

				if ( !trans.isZero())
					parts.push('translate(' + fmtNubmer(trans.x) + "," + fmtNubmer(trans.y) + ')');
				if (!MathUtil.isZero(scale.x - 1) || !MathUtil.isZero(scale.y - 1))
					parts.push('scale(' + fmtNubmer(scale.x) + "," + fmtNubmer(scale.y) +')');
				if (angle)
					parts.push('rotate(' + fmtNubmer(angle) + ')');
				return parts.join(' ');
			}
			else
			{
				return 'matrix(' + m.getValues().join(',') + ')';
			}
		},
		_toTransform: function(str)
		{
			var attrs ={};
			if ( str )
			{
				for (var i in str = str.match(/(\w+\((\-?\d+\.?\d*,?)+\))+/g))
				{
					if ( str[i].match )
					{
						var c = str[i].match(/[\w\.\-]+/g);
						attrs[c.shift()] = c;
					}
				}
			}
			var m = new Eco.Matrix(), val, shearX, shearY;
			for ( var cmd in attrs )
			{
				val = attrs[cmd];
				switch ( cmd )
				{
					case "translate":
						if ( val.length == 1 )
						{
							m.translate(parseFloat(val[0]), 0);
						}
						else
						{
							m.translate(parseFloat(val[0]), parseFloat(val[1]));
						}
						break;
					case "rotate":
						if ( val.length == 1 )
						{
							m.rotate(parseFloat(val[0]));
						}
						else
						{
							m.rotate(parseFloat(val[0]), parseFloat(val[1]), parseFloat(val[2]));
						}
						break;
					case "scale":
						if ( val.length == 1 )
						{
							m.scale(parseFloat(val[0]), parseFloat(val[0]));
						}
						else
						{
							m.scale(parseFloat(val[0]), parseFloat(val[1]));
						}
						break;
					case "matrix":
						m.set(parseFloat(val[0]), parseFloat(val[2]), parseFloat(val[1]), parseFloat(val[3]), parseFloat(val[4]), parseFloat(val[5]));
						break;
					case "skewX":
						shearX = Math.tan(parseFloat(val[0])* Eco.Matrix.DEGTORAD);
						break;
					case "skewY":
						shearY = Math.tan(parseFloat(val[0])* Eco.Matrix.DEGTORAD);
						break;
				}
			}
			if ( shearX != null || shearY != null )
			{
				m.shear(shearX||0, shearY||0);
			}
			if ( this._type )
			{
				m.translate(this._x + (this._dx||0), this._y + (this._dy||0));
				this.setMatrix(m);
			}
			return m;
		},
		_setOldGlobalBounds: function()
		{
			if ( (this._painted & (1|2|4)) && !this._oldGlobalBounds )
			{
				this._oldGlobalBounds = this.getGlobalBounds(true);
			}
		},
		_getOwnerLayer: function()
		{
			var ownerLayer = this._ownerLayer;
			if ( !ownerLayer )
			{
				var p = this.Parent,
					pLayerCls = Eco.GraphicLayer;
				while ( p )
				{
					if ( p instanceof pLayerCls )
					{
						ownerLayer = p;
						break;
					}
					p = p.Parent;
				}
				this._ownerLayer = ownerLayer;
			}
			return ownerLayer;
		},
		getStrokeWidth: function()
		{
			return this._strokeWidth||0;
		},
		_updateStyles: function(ctx, sizeChanged)
		{
			var sysstyle = this._renderstyle;
			if ( !sysstyle )
			{
				sysstyle = {};
				this._renderstyle = sysstyle;
			}
			var style = this._style;
			if ( this._changedStrokepen )
			{
				ctx._updateStrokePen(style.strokepen, sysstyle);
				this._changedStrokepen = false;
			}
			if ( this._changedFillbrush )
			{
				ctx._updateFill(style.fillbrush, sysstyle, style.fillgradation, this._getRect());
				this._changedFillbrush = false;
			}
			else if ( this._isGradation && sizeChanged )
			{
				ctx._updateFill(style.fillbrush, sysstyle, style.fillgradation, this._getRect());
			}
			ctx.setElementGlobalAlpha(this._globalAlpha);
			ctx._updateStrokeJoin(style.strokejoin, sysstyle);
			ctx._updateStrokeCap(style.strokecap, sysstyle);
			ctx._updateMiterJoinLimit(style.miterjoinlimit, sysstyle);
		},
		//tree process
		hasChildren: function()
		{
			return this._firstChild != null;
		},
		getChildren: function()
		{
			if ( this._firstChild )
			{
				var children = this._children;
				if ( !children || (children._changed != this._childchanged) )
				{
					children = [];
					var child = this._firstChild, seq = 0;
					for ( ; child != null ; child = child._nextSibling )
					{
						children[seq] = child;
						seq++;
					}
					this._children = children;
					children._changed = this._childchanged;
				}
				return children;
			}
		},
		getLength: function()
		{
			if ( this._firstChild )
			{
				var children = this._children;
				if ( children && (children._changed == this._childchanged) )
				{
					return children.length;
				}
				return this._getChildrenLength();
			}
			return 0;
		},
		getChildByIndex: function(index)
		{
			if ( this._firstChild )
			{
				var children = this._children;
				if ( children && (children._changed == this._childchanged) )
				{
					return children[index];
				}
				return this._getChildByIndex(index);
			}
		},
		insertBefore: function(newChild, refChild)
		{
			return this._insertBefore(newChild, refChild);
		},
		addChildren: function(arr)
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
			var owner = this._owner;
			for ( var i = 0, len = arr.length ; i < len ; i++ )
			{
				arr[i].Parent = this;
				if ( owner )
				{
					arr[i].setOwner(owner);
				}
			}
			this._childchanged++; //childchanged
			this._drawflags |= 4; //boundchanged
			var layer = this._getOwnerLayer();
			if ( layer )
			{
				layer._onTreeChanged();
			}
			var childListCache = this._childListCache;
			if (childListCache)
			{
				if (childListCache.flength != -1)
				{
					childListCache.flength += arr.length;
				}
				if (childListCache.fchildindex != -1)
				{
					childListCache.fchildindex = -1;
				}
			}
		},
		_insertBefore: function(newChild, refChild)
		{
			if ( refChild && refChild.Parent != this )
			{
				Eco.Logger.error("refChild' Parent가 호출하는 this와 동일해야 한다!");
			}
			if (newChild == refChild)
			{
				refChild = refChild.getNextSibling();
				this.removeChild(newChild);
				this.insertBefore(newChild, refChild);
				return newChild;
			}
			var oldparent = newChild.Parent;
			if (oldparent)
			{
				oldparent.removeChild(newChild);
			}

			newChild.Parent = this;
			//console.log("insertbefore", newChild, this);
			if ( this._owner )
			{
				newChild.setOwner(this._owner);
			}

			if ( !this._firstChild ) 
			{
				this._firstChild = newChild;
				newChild._isFirstChild = true;
				newChild._previousSibling = newChild;
			}
			else 
			{
				if (!refChild) //add
				{
					var lastChild = this._firstChild._previousSibling;
					lastChild._nextSibling = newChild;
					newChild._previousSibling = lastChild;
					this._firstChild._previousSibling = newChild;
				}
				else //insert
				{
					var firstChild = this._firstChild;
					if (refChild == firstChild)
					{
						firstChild._isFirstChild = false;
						newChild._nextSibling = firstChild;
						newChild._previousSibling = firstChild._previousSibling;
						firstChild._previousSibling = newChild;
						this._firstChild = newChild;
						newChild._isFirstChild = true;
					}
					else
					{
						var prev = refChild._previousSibling;
						newChild._nextSibling = refChild;
						prev._nextSibling = newChild;
						refChild._previousSibling = newChild;
						newChild._previousSibling = prev;
					}
				}
			}
			this._childchanged++; //childchanged
			this._drawflags |= 4; //boundchanged
			var layer = this._getOwnerLayer();
			if ( layer )
			{
				layer._onTreeChanged();
			}
			var childListCache = this._childListCache;
			if (childListCache)
			{
				if (childListCache.flength != -1)
				{
					childListCache.flength++;
				}
				if (childListCache.fchildindex != -1)
				{
					if (refChild && childListCache.fchild == refChild)
					{
						childListCache.fchild = newChild;
					}
					else
					{
						childListCache.fchildindex = -1;
						//childListCache.fchild = null;
					}
				}
			}
			return newChild;
		},
		removeChild: function(oldChild)
		{
			return this._removeChild(oldChild);
		},
		_removeChild: function(oldChild)
		{
			if ( oldChild && oldChild.Parent != this )
			{
				Eco.Logger.error("oldChild' Parent가 호출하는 this와 동일해야 한다!");
			}

			var childListCache = this._childListCache;
			if ( childListCache )
			{
				if (childListCache.flength != -1)
				{
					childListCache.flength--;
				}
				if (childListCache.fchildindex != -1)
				{
					if (childListCache.fchild == oldChild)
					{
						childListCache.fchildindex--;
						childListCache.fchild = oldChild.getPreviousSibling();
					}
					else
					{
						childListCache.fchildindex = -1;
						//childListCache.fchild = null;
					}
				}
			}
			if (oldChild == this._firstChild)
			{
				oldChild._isFirstChild = false;
				this._firstChild = oldChild._nextSibling;
				var firstChild = this._firstChild;
				if (firstChild)
				{
					firstChild._isFirstChild = true;
					firstChild._previousSibling = oldChild._previousSibling;
				}
			}
			else
			{
				var prev = oldChild._previousSibling;
				var next = oldChild._nextSibling;
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
			oldChild._remove(true);
			oldChild.Parent = null;
			oldChild._nextSibling = null;
			oldChild._previousSibling = null;
			this._childchanged++;
			this._drawflags |= 4; //boundchanged
			var layer = this._getOwnerLayer();
			if ( layer )
			{
				layer._onTreeChanged();
			}
			return oldChild;
		},
		_remove: function(deep, notDirty)
		{
			delete this._owner;

			if ( !notDirty && this._painted & (1|2|4) )
			{
				var layer = this._getOwnerLayer();
				if ( layer )
				{
					layer._addDirty(this);
					//trace(this.toString(), this.Parent.toString(), this.Parent._quadtree);
					//if ( this.Parent && this.Parent._quadtree ) this.Parent._quadtree.remove(this);
					this.Parent._quadtree.remove(this);
					//this.Parent._dr
					//console.log(this.toString(), "removed==>", (this._oldGlobalBounds ? this._oldGlobalBounds.toString() : ""), this.Parent._quadtree.contains(this));

				}
			}
			if ( deep )
			{
				var node = this._firstChild;
				for ( ; node ; node = node._nextSibling )
				{
					node._remove(true, true);
				}
			}
		},
		removeChildren: function()
		{
			var node = this._firstChild,
				delected = false, removed = [];
			for ( ; node ; node = node._nextSibling )
			{
				removed[removed.length] = node;
			}
			for ( var i = 0, len = removed.length; i < len ; i++ )
			{
				node = removed[i];
				node._remove(true);
				node.Parent = null;
				node._nextSibling = null;
				node._previousSibling = null;
				delected = true;
			}

			if ( delected )
			{
				this._firstChild._isFirstChild = false;
				this._firstChild = null;
				this._childchanged++;
				this._drawflags |= 4; //boundchanged
				var layer = this._getOwnerLayer();
				if ( layer )
				{
					layer._onTreeChanged();
				}
				var childListCache = this._childListCache;
				if ( childListCache )
				{
					childListCache.fchild = null;
					childListCache.fchildindex = -1;
					childListCache.flength = -1;
				}
			}
		},
		addChild: function(child)
		{
			return this.insertBefore(child, null);
		},
		replaceChild: function(newChild, oldChild)
		{
			this._insertBeforeChild(newChild, oldChild);
			if ( newChild != oldChild )
			{
				this._removeChild(oldChild);
			}
			return oldChild;
		},
		_getChildrenLength: function()
		{
			var childListCache = this._childListCache;
			if ( !childListCache )
			{
				var firstChild = this._firstChild
				if ( !firstChild )
				{
					return 0;
				}
				if ( firstChild == this.getLastChild() )
				{
					return 1;
				}
				childListCache = {
					flength: -1,
					fchildindex: -1,
					fchild: null,
					fowner: this
				};
				this._childListCache = childListCache;
			}
			if ( childListCache.flength == -1 )
			{
				var l, n;
				if ( childListCache.fchildindex != -1 && 
					childListCache.fchild != null )
				{
					l = childListCache.fchildindex;
					n = childListCache.fchild;
				}
				else
				{
					n = this._firstChild;
					l = 0;
				}
				while ( n )
				{
					l++;
					n = n._nextSibling;
				}
				childListCache.flength = l;
			}
			return childListCache.flength;
		},
		_getChildByIndex: function(index)
		{
			var childListCache = this._childListCache;
			if ( !childListCache )
			{
				if ( this._firstChild == this.getLastChild() )
				{
					return index == 0 ? this._firstChild : null;
				}
				childListCache = {
					flength: -1,
					fchildindex: -1,
					fchild: null,
					fowner: this
				};
				this._childListCache = childListCache;
			}
			var i = childListCache.fchildindex;
			var n = childListCache.fchild;
			var firstAccess = true;
			if ( i != -1 && n != null )
			{
				firstAccess = false;
				if ( i < index )
				{
					while(i < index && n )
					{
						i++;
						n = n._nextSibling;
					}
				}
				else if ( i > index )
				{
					while(i > index && n )
					{
						i--;
						n = n.getPreviousSibling();
					}
				}
			}
			else
			{
				if ( index < 0 )
				{
					return null;
				}
				n = this._firstChild;
				for ( i = 0 ; i < index && n ; i++ )
				{
					n = n._nextSibling;
				}
			}
			if ( !firstAccess && ( n == this._firstChild || n == this.getLastChild() ) )
			{
				childListCache.fchildindex = -1;
				childListCache.fchild = null;
			}
			else
			{
				childListCache.fchildindex = i;
				childListCache.fchild = n;
			}
			return n;
		},
		getElementById: function(id)
		{
			if ( !id ) return null;
			return this._getItemById(id);
		},
		getElementsByTagName: function(tag)
		{
			if ( !tag ) return [];
			return this._getItemsByFilter("_type", tag)||[];
		},
		getElementsByClassName: function(clsName)
		{
			if ( !clsName ) return [];
			return this._getItemsByFilter("_class", clsName)||[];
		},
		_getItemById: function(val)
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
		},
		_getItemsByFilter: function(propNm, val)
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
				var elem = this._firstChild, elems = cached.elements,
					res = elems.slice(0), retvals = [];
				seq = 0;
				//for ( var i = 0, len = elems.length ; i < len ; i++ )
				for ( ; elem ; elem = elem._nextSibling )
				{
					//elem = elems[i];
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
		},
		//bounds process
		isEmpty: function()
		{
			return !this.hasChildren();
		},
		_getRect: function(bStrokeWidth, iscurrent)
		{
			var child = this._firstChild;
			if (!child )
			{
				return new Eco.Rectangle();
			}
			var rect = this._rect;
			if ( iscurrent )
			{
				rect = null;
			}
			if ( !rect )
			{
				var x1 = Infinity,
					x2 = -x1,
					y1 = x1,
					y2 = x2;

				for ( ; child != null ; child = child._nextSibling )
				{
					if (child._visible && !child.isEmpty())
					{
						var rect = child.getBounds(false, iscurrent);
						x1 = Math.min(rect.x, x1);
						y1 = Math.min(rect.y, y1);
						x2 = Math.max(rect.x + rect.width, x2);
						y2 = Math.max(rect.y + rect.height, y2);
					}
				}
				rect = isFinite(x1)
						? new Eco.Rectangle(x1, y1, x2 - x1, y2 - y1)
						: new Eco.Rectangle();
				if ( !iscurrent ) this._rect = rect;
			}
			if ( bStrokeWidth )
			{
				rect = rect.expand(this.getStrokeWidth()/2);
			}
			return rect;
		},
		getBounds: function(bCopy, iscurrent)
		{
			var bounds = this._bounds;
			if ( iscurrent )
			{
				bounds = null;
			}
			if ( bounds ) return bounds;
			var rect = this._getRect(true, iscurrent);
			if ( rect )
			{
				bounds = this._matrix._transformBounds(rect);
				if ( !iscurrent ) this._bounds = bounds;
			}
			if ( bCopy )
			{
				return bounds.clone();
			}
			else
			{
				return bounds;
			}
		},
		getGlobalBounds: function(bCopy, iscurrent)
		{
			var bounds = this._globalBounds;
			if ( iscurrent )
			{
				bounds = null;
			}
			if ( bounds ) return bounds;
			var rect = this._getRect(true, iscurrent);
			if ( rect )
			{
				if ( iscurrent )
				{
					var elems = [this], p = this.Parent,
						pLayerCls = Eco.GraphicLayer, m;
					while ( p )
					{
						elems.unshift(p);
						if ( p instanceof pLayerCls )
						{
							break;
						}
						p = p.Parent;
					}
					for ( var i = 0, len = elems.length ; i < len ; i++ )
					{
						if ( i == 0 )
						{
							if ( elems[i] instanceof pLayerCls )
							{
								m = (elems[i]._matrix ? (elems[i].Parent._viewport._matrix.clone()).concatenate(elems[i]._matrix) : elems[i].Parent._viewport._matrix.clone());
							}
							else
							{
								m = elems[i]._matrix.clone();
							}
						}
						else
						{
							m = (m.clone()).concatenate(elems[i]._matrix);
						}
					}
					bounds = m._transformBounds(rect);
				}
				else
				{
					bounds = (this._globalMatrix||this._matrix)._transformBounds(rect);
					this._globalBounds = bounds;
				}
			}
			if ( bCopy )
			{
				return bounds.clone();
			}
			else
			{
				return bounds;
			}
		},
		//transform process
		"scale": function(x, y , center)
		{
			if (arguments.length < 2 || (y instanceof Eco.Point))
			{
				center = y;
				y = x;
			}
			var m = new Eco.Matrix();
			this.transform(m.scale(x, y, center || this.getCenter()));
		},
		"translate": function(pt)
		{
			var m = new Eco.Matrix();
			if ( !(pt instanceof Eco.Point) )
			{
				pt = new Eco.Point(arguments[0], arguments[1]);
			}
			m.translate(pt);
			return this.transform(m);
		},
		"rotate": function(angle, center)
		{
			if ( arguments.length == 3 )
			{
				center = new Eco.Point(arguments[1], arguments[2]);
			}
			var m = new Eco.Matrix();
			this.transform(m.rotate(angle, center|| this.getCenter()));
		},
		"shear": function(x, y, center)
		{
			if (arguments.length < 2 || (y instanceof Eco.Point))
			{
				center = y;
				y = x;
			}
			var m = new Eco.Matrix();
			this.transform(m.shear(x, y, center || this.getCenter()));
		},
		"transform": function(m)
		{
			var bounds = this._bounds,
				pos = this._center;

			this._matrix.preConcatenate(m);

			this._drawflags |= 1; //matrixchanged
			if (bounds && m.getRotation() % 90 === 0)
			{
				m._transformBounds(bounds, bounds);
				this._center = bounds.getCenter();
			}
			else if (pos)
			{
				this._center = m._transformPoint(pos, pos);
			}
		},
		//draw process
		_setGlobalTransforms: function(pMatrix)
		{
			this._globalMatrix = ( pMatrix ?
				(pMatrix.clone()).concatenate(this._matrix) : this._matrix.clone() );
		},
		_render: function(ctx)
		{
			var style =	this._renderstyle,
				m = this._globalMatrix;
			if ( m && style )
			{
				this._painted = 1;
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
				this._drawShape(ctx);
				var fillColor = style._fillcolor,
					strokeColor = style._strokecolor;
				if ( fillColor )
				{
					ctx.fill();
					this._painted |= 4;
				}
				if ( strokeColor )
				{
					ctx.stroke();
					this._painted |= 2;
				}
				ctx.restore();
			}
		},
		_drawShape: function(ctx)
		{
			var rect = this._getRect(),
				w = rect.width,
				h = rect.height,
				x = rect.x,
				y = rect.y;

			//var xyOffset = this._getSharperOffset(x, y);
			var gap = 0, exval = 0;
			if ( this._isOddStrokeWidth )
			{
				gap = 0.5;
				//exval = 1;
			}
			ctx.beginPath();
			ctx.rect(x + gap, y + gap, w , h );
			ctx.closePath();
		},
		// offset needed to make lines look sharper
		_getSharperOffset: function(x, y)
		{
			var ret = [];
			if ( this._isOddStrokeWidth )
			{
				if ( (~~(x)) == x )
				{
					ret[0] = 0;
				}
				else
				{
					ret[0] = 0.5;
				}
				if ( (~~(y)) == y )
				{
					ret[1] = 0;
				}
				else
				{
					ret[1] = 0.5;
				}
			}
			else
			{
				if ( (~~(x)) == x )
				{
					ret[0] = 0.5;
				}
				else
				{
					ret[0] = 0;
				}
				if ( (~~(y)) == y )
				{
					ret[1] = 0.5;
				}
				else
				{
					ret[1] = 0;
				}
			}
			return ret;
		},
		//hittest process
		hitTest: function(pt)
		{
			var rect = this._getRect();
			if ( rect )
			{
				return rect._containsPoint(pt);
			}
			return false;
		},
		getAttribute: function(name)
		{
			var fn = this.getGetter(name, true);
			if ( fn )
			{
				if ( arguments.length > 1 )
				{
					return fn.call(this, arguments[1]);
				}
				else
				{
					return fn.call(this);
				}
			}
		},
		setAttribute: function(name, value)
		{
			var fn = this.getSetter(name, true);
			if ( fn )
			{
				if ( Eco.isArray(value) )
				{
					fn.apply(this, value);
				}
				else
				{
					fn.call(this, value);
				}
			}
		},
		removeAttribute: function(name)
		{
			var fn = this.getSetter(name, true);
			if ( fn )
			{
				fn.call(this, null);
			}
		},
		getStyleProperty: function(name)
		{
			if ( name.indexOf(".") > -1 )
			{
				var fn = this._styleHelper[name];
				if ( fn )
				{
					if ( arguments.length > 1 )
					{
						var val = fn.call(this);
						if ( arguments[1] )
						{
							return this._styleHelper[name + ".toArray"].call(this, val);
						}
					}
					else
					{
						return fn.call(this);
					}
				}
			}
			else
			{
				var fn = this.getGetter(name, true);
				if ( fn )
				{
					if ( arguments.length > 1 )
					{
						if ( arguments[1] )
						{
							return fn.call(this, true);
						}
						return fn.call(this);
					}
					else
					{
						return fn.call(this);
					}
				}
			}
		},
		setStyleProperty: function(name, value)
		{
			if ( name.indexOf(".") > -1 )
			{
				var fn = this._styleHelper[name];
				if ( fn )
				{
					if ( Eco.isArray(value) )
					{
						var val = this._styleHelper[name + ".toString"].call(this, value);
						fn.call(this, val);
					}
					else
					{
						fn.call(this, value);
					}
				}
			}
			else
			{
				var fn = this.getSetter(name, true);
				if ( fn )
				{
					return fn.call(this, value);
				}
			}
		},
		removeStyleProperty: function(name)
		{
			if ( name.indexOf(".") )
			{
				var fn = this._styleHelper[name];
				if ( fn )
				{
					fn.call(this, null);
				}
			}
			else
			{
				var fn = this.getSetter(name, true);
				if ( fn )
				{
					return fn.call(this, null);
				}
			}
		}
	}); //end of 'JsNamespace.declareClass("Eco.GraphicElement", {'
	
	var pGraphicElement = Eco.GraphicElement.prototype;
	pGraphicElement._styleHelper = {
		"fillbrush.style": function()
		 {
			if ( arguments.length )
			{
				var s = this._style,
					oldobj = s.fillbrush,
					newobj = oldobj ? oldobj.clone() : new nexacro.Style_fillbrush("solid", "transparent");

				newobj.set_style(arguments[0]||"none");
				newobj = nexacro._registerCachedFillbrushObj(newobj);
				if ( newobj != oldobj )
				{
					s.fillbrush = newobj;
					this._drawflags |= 8; //stylechanged
					this._changedFillbrush = true;
					this._isGradation = newobj.style == "gradation" && s.fillgradation;
				}
			}
			else
			{
				var val = this._style.fillbrush;
				if ( val )
				{
					return val.style;
				}
				return null;
			}
		 },
		"fillbrush.color": function()
		 {
			if ( arguments.length )
			{
				var s = this._style,
					oldobj = s.fillbrush,
					newobj = oldobj ? oldobj.clone() : new nexacro.Style_fillbrush("solid", "transparent");

				newobj.set_color(arguments[0]||"transparent");
				newobj = nexacro._registerCachedFillbrushObj(newobj);
				if ( newobj != oldobj )
				{
					s.fillbrush = newobj;
					this._drawflags |= 8; //stylechanged
					this._changedFillbrush = true;
					this._isGradation = newobj.style == "gradation" && s.fillgradation;
				}
			}
			else
			{
				var val = this._style.fillbrush;
				if ( val )
				{
					return val.color;
				}
				return null;
			}
		 },
		"strokepen.style": function()
		 {
			if ( arguments.length )
			{
				var s = this._style,
					oldobj = s.strokepen,
					newobj = oldobj ? oldobj.clone() : new nexacro.Style_strokepen(1, "solid", "black");

				newobj.set_style(arguments[0]||"none");
				newobj = nexacro._registerCachedStrokepenObj(newobj);
				if ( newobj != oldobj )
				{
					s.strokepen = newobj;
					this._drawflags |= 8; //stylechanged
					this._changedStrokepen = true;
				}
			}
			else
			{
				var val = this._style.strokepen;
				if ( val )
				{
					return val.style;
				}
				return null;
			}
		 },
		"strokepen.color": function()
		 {
			if ( arguments.length )
			{
				var s = this._style,
					oldobj = s.strokepen,
					newobj = oldobj ? oldobj.clone() : new nexacro.Style_strokepen(1, "solid", "black");

				newobj.set_color(arguments[0]||"transparent");
				newobj = nexacro._registerCachedStrokepenObj(newobj);
				if ( newobj != oldobj )
				{
					s.strokepen = newobj;
					this._drawflags |= 8; //stylechanged
					this._changedStrokepen = true;
				}
			}
			else
			{
				var val = this._style.strokepen;
				if ( val )
				{
					return val.color;
				}
				return null;
			}
		 },
		"strokepen.width": function()
		 {
			if ( arguments.length )
			{
				var s = this._style,
					oldobj = s.strokepen,
					newobj = oldobj ? oldobj.clone() : new nexacro.Style_strokepen(1, "solid", "black");

				newobj.set_width(arguments[0]||1);
				newobj = nexacro._registerCachedStrokepenObj(newobj);
				if ( newobj != oldobj )
				{
					s.strokepen = newobj;
					this._drawflags |= 8; //stylechanged
					if ( newobj )
					{
						var lineW = newobj.width||0;
						if ( this._strokeWidth != lineW )
						{
							//this._setOldGlobalBounds();
							this._strokeWidth = lineW;
							if ( lineW%2 ) this._isOddStrokeWidth = true;
							else this._isOddStrokeWidth = false;
							this._bounds = null;
							this._globalBounds = null;
						}
					}
					else
					{
						if ( this._strokeWidth != 0 )
						{
							//this._setOldGlobalBounds();
							this._strokeWidth = 0;
							this._isOddStrokeWidth = false;
						}
					}
					this._changedStrokepen = true;
				}
			}
			else
			{
				var val = this._style.strokepen;
				if ( val )
				{
					return val.width;
				}
				return null;
			}
		 },
		"fillgradation.style": function()
		 {
			if ( arguments.length )
			{
				var s = this._style,
					oldobj = s.fillgradation,
					newobj = oldobj ? oldobj.clone() : new nexacro.Style_gradation();

				newobj.set_style(arguments[0]||"none");
				newobj = nexacro._registerCachedGradationObj(newobj);
				if ( newobj != oldobj )
				{
					s.fillgradation = newobj;
					this._isGradation = newobj && s.fillbrush && (s.fillbrush.style == "gradation");
					if ( this._isGradation )
					{
						this._drawflags |= 8; //stylechanged
						this._changedFillbrush = true;
					}
				}
			}
			else
			{
				var val = this._style.fillgradation;
				if ( val )
				{
					return val.style;
				}
				return null;
			}
		 },
		"fillgradation.peglist.toString": function(arr)
		{
			var val = "";
			for ( var i = 0, len = arr.length ; i < len ; i += 2 )
			{
				val += "[" + arr[i] + " " + arr[i + 1] + "]";
			}
			return val;
		},
		"fillgradation.peglist.toArray": function(str)
		{
			return nexacro.Style_gradation.prototype._parsePegList.call(this, str);
		},
		"fillgradation.peglist": function()
		 {
			if ( arguments.length )
			{
				var s = this._style,
					oldobj = s.fillgradation,
					newobj = oldobj ? oldobj.clone() : new nexacro.Style_gradation();

				newobj.set_peglist(arguments[0]||"");
				newobj = nexacro._registerCachedGradationObj(newobj);
				if ( newobj != oldobj )
				{
					s.fillgradation = newobj;
					this._isGradation = newobj && s.fillbrush && (s.fillbrush.style == "gradation");
					if ( this._isGradation )
					{
						this._drawflags |= 8; //stylechanged
						this._changedFillbrush = true;
					}
				}
			}
			else
			{
				var val = this._style.fillgradation;
				if ( val )
				{
					return val.peglist;
				}
				return null;
			}
		 },
		"fillgradation.start.toString": function(arr)
		{
			return arr[0] + "," + arr[1] + " " + arr[2];
		},
		"fillgradation.start.toArray": function(str)
		{
			var parts = str.split(/\s+/);
			var ptarr = parts[0].split(',');
			return [(parseInt(ptarr[0]) | 0), (parseInt(ptarr[1]) | 0), parts[1]];
		},
		"fillgradation.start": function()
		 {
			if ( arguments.length )
			{
				var s = this._style,
					oldobj = s.fillgradation,
					newobj = oldobj ? oldobj.clone() : new nexacro.Style_gradation();

				newobj.set_start(arguments[0]||"0,0 transparent");
				newobj = nexacro._registerCachedGradationObj(newobj);
				if ( newobj != oldobj )
				{
					s.fillgradation = newobj;
					this._isGradation = newobj && s.fillbrush && (s.fillbrush.style == "gradation");
					if ( this._isGradation )
					{
						this._drawflags |= 8; //stylechanged
						this._changedFillbrush = true;
					}
				}
			}
			else
			{
				var val = this._style.fillgradation;
				if ( val )
				{
					return val.start_point + " " + val.start_color;
				}
				return null;
			}
		 },
		"fillgradation.start_point.toString": function(arr)
		{
			return arr[0] + "," + arr[1];
		},
		"fillgradation.start_point.toArray": function(str)
		{
			var ptarr = str.split(',');
			return [(parseInt(ptarr[0]) | 0), (parseInt(ptarr[1]) | 0)];
		},
		"fillgradation.start_point": function()
		 {
			if ( arguments.length )
			{
				var s = this._style,
					oldobj = s.fillgradation,
					newobj = oldobj ? oldobj.clone() : new nexacro.Style_gradation();

				newobj.set_start_point(arguments[0]||"0,0");
				newobj = nexacro._registerCachedGradationObj(newobj);
				if ( newobj != oldobj )
				{
					s.fillgradation = newobj;
					this._isGradation = newobj && s.fillbrush && (s.fillbrush.style == "gradation");
					if ( this._isGradation )
					{
						this._drawflags |= 8; //stylechanged
						this._changedFillbrush = true;
					}
				}
			}
			else
			{
				var val = this._style.fillgradation;
				if ( val )
				{
					return val.start_point;
				}
				return null;
			}
		 },
		"fillgradation.start_color": function()
		 {
			if ( arguments.length )
			{
				var s = this._style,
					oldobj = s.fillgradation,
					newobj = oldobj ? oldobj.clone() : new nexacro.Style_gradation();

				newobj.set_start_color(arguments[0]||"transparent");
				newobj = nexacro._registerCachedGradationObj(newobj);
				if ( newobj != oldobj )
				{
					s.fillgradation = newobj;
					this._isGradation = newobj && s.fillbrush && (s.fillbrush.style == "gradation");
					if ( this._isGradation )
					{
						this._drawflags |= 8; //stylechanged
						this._changedFillbrush = true;
					}
				}
			}
			else
			{
				var val = this._style.fillgradation;
				if ( val )
				{
					return val.start_color;
				}
				return null;
			}
		 },
		"fillgradation.end.toString": function(arr)
		{
			return arr[0] + "," + arr[1] + " " + arr[2];
		},
		"fillgradation.end.toArray": function(str)
		{
			var parts = str.split(/\s+/);
			var ptarr = parts[0].split(',');
			return [(parseInt(ptarr[0]) | 0), (parseInt(ptarr[1]) | 0), parts[1]];
		},
		"fillgradation.end": function()
		 {
			if ( arguments.length )
			{
				var s = this._style,
					oldobj = s.fillgradation,
					newobj = oldobj ? oldobj.clone() : new nexacro.Style_gradation();

				newobj.set_end(arguments[0]||"100,100 transparent");
				newobj = nexacro._registerCachedGradationObj(newobj);
				if ( newobj != oldobj )
				{
					s.fillgradation = newobj;
					this._isGradation = newobj && s.fillbrush && (s.fillbrush.style == "gradation");
					if ( this._isGradation )
					{
						this._drawflags |= 8; //stylechanged
						this._changedFillbrush = true;
					}
				}
			}
			else
			{
				var val = this._style.fillgradation;
				if ( val )
				{
					return val.end_point + " " + val.end_color;
				}
				return null;
			}
		 },
		"fillgradation.end_point.toString": function(arr)
		{
			return arr[0] + "," + arr[1];
		},
		"fillgradation.end_point.toArray": function(str)
		{
			var ptarr = str.split(',');
			return [(parseInt(ptarr[0]) | 0), (parseInt(ptarr[1]) | 0)];
		},
		"fillgradation.end_point": function()
		 {
			if ( arguments.length )
			{
				var s = this._style,
					oldobj = s.fillgradation,
					newobj = oldobj ? oldobj.clone() : new nexacro.Style_gradation();

				newobj.set_end_point(arguments[0]||"100,100");
				newobj = nexacro._registerCachedGradationObj(newobj);
				if ( newobj != oldobj )
				{
					s.fillgradation = newobj;
					this._isGradation = newobj && s.fillbrush && (s.fillbrush.style == "gradation");
					if ( this._isGradation )
					{
						this._drawflags |= 8; //stylechanged
						this._changedFillbrush = true;
					}
				}
			}
			else
			{
				var val = this._style.fillgradation;
				if ( val )
				{
					return val.end_point;
				}
				return null;
			}
		 },
		"fillgradation.end_color": function()
		 {
			if ( arguments.length )
			{
				var s = this._style,
					oldobj = s.fillgradation,
					newobj = oldobj ? oldobj.clone() : new nexacro.Style_gradation();

				newobj.set_end_color(arguments[0]||"transparent");
				newobj = nexacro._registerCachedGradationObj(newobj);
				if ( newobj != oldobj )
				{
					s.fillgradation = newobj;
					this._isGradation = newobj && s.fillbrush && (s.fillbrush.style == "gradation");
					if ( this._isGradation )
					{
						this._drawflags |= 8; //stylechanged
						this._changedFillbrush = true;
					}
				}
			}
			else
			{
				var val = this._style.fillgradation;
				if ( val )
				{
					return val.end_color;
				}
				return null;
			}
		 },
		"font.face": function()
		 {
			if ( arguments.length )
			{
				var s = this._style,
					oldobj = s.font,
					newobj = oldobj ? oldobj.clone() : new nexacro.Style_font();

				newobj.set_face(arguments[0]||"");
				newobj = nexacro._registerCachedFontObj(newobj);
				if ( newobj != oldobj )
				{
					s.font = newobj;
					this._drawflags |= (2|8); //sizechanged, stylechanged
					this._changedFont = true;
				}
			}
			else
			{
				var val = this._style.font;
				if ( val )
				{
					return val.face;
				}
				return null;
			}
		 },
		"font.size": function()
		 {
			if ( arguments.length )
			{
				var s = this._style,
					oldobj = s.font,
					newobj = oldobj ? oldobj.clone() : new nexacro.Style_font();

				newobj.set_size(arguments[0]||"");
				newobj = nexacro._registerCachedFontObj(newobj);
				if ( newobj != oldobj )
				{
					s.font = newobj;
					this._drawflags |= (2|8); //sizechanged, stylechanged
					this._changedFont = true;
				}
			}
			else
			{
				var val = this._style.font;
				if ( val )
				{
					return val.size;
				}
				return null;
			}
		 },
		"font.type": function()
		 {
			if ( arguments.length )
			{
				var s = this._style,
					oldobj = s.font,
					newobj = oldobj ? oldobj.clone() : new nexacro.Style_font();

				newobj.set_type(arguments[0]||"");
				newobj = nexacro._registerCachedFontObj(newobj);
				if ( newobj != oldobj )
				{
					s.font = newobj;
					this._drawflags |= (2|8); //sizechanged, stylechanged
					this._changedFont = true;
				}
			}
			else
			{
				var val = this._style.font;
				if ( val )
				{
					var arr_type = [];
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
					return arr_type.join(" ");
				}
				return null;
			}
		 }

	};
} //end of 'if ( !JsNamespace.exist("Eco.GraphicElement") )'
		