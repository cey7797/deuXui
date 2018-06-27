/**
 * @fileoverview Path(Path, CompoundPath) Class 정의.
 */

if ( !JsNamespace.exist("Eco.GraphicPath") )
{
	JsNamespace.declareClass("Eco.GraphicPath", {
		"extends": "Eco.GraphicElement",
		/**
		 * Graphic 생성자(constructor)
		 * @param {Graphic} g 생성되는 요소가 속해야 하는 Graphic 객체
		 * @param {Eco.Point} pt 요소가 위치하는 지점 좌표(Group이나, Layer를 0,0 기준으로 설정됨)
		 * @class Graphic
		 * @classdesc 다양한 shape를 그리고 operation 처리하는 기능이 있는 Class.
		 * @constructor Graphic
		*/
		initialize: function()
		{
			this._type = "Path";
			this.callParent(arguments);
			this._closed = false;
			this._segments = [];
		},
		properties: {
			"d": {
				"set": function(val)
				{
					if ( arguments.length == 0 || val == null )
					{
						return;
					}
					this.setPathData(val);
				},
				"get": function()
				{
					return this.getPathData();
				}
			}
		},
		_add: function(segs, index)
		{
			var segments = this._segments,
				len = segs.length,
				append = index == null,
				seg;

			index = append ? segments.length : index;

			if (append)
			{
				segments.push.apply(segments, segs);
			}
			else
			{
				segments.splice.apply(segments, [index, 0].concat(segs));
			}
			this._drawflags |= 4; //contentschanged
			return segs;
		},
		isEmpty: function()
		{
			return this._segments.length === 0;
		},
		isClosed: function()
		{
			if ( this._closed ) return true;
			//return this._closed;
			var first = this.getFirstSegment(),
				last = this.getLastSegment();
			if ( this._segments.length > 1 && first.point.equals(last.point))
			{
				return true;
			}
			return false;
		},
		setClosed: function(closed)
		{
			if (this._closed != (closed = !!closed))
			{
				this._closed = closed;
				this._drawflags |= 4; //contentschanged
			}
		},
		removeSegment: function(index)
		{
			return this.removeSegments(index, index + 1)[0] || null;
		},
		removeSegments: function(from, to)
		{
			from = (from || 0);
			to = (to == null ? this._segments.length : to);
			var segments = this._segments,
				len = segments.length, 
				removed = segments.splice(from, to - from),
				removedLen = removed.length;
			if (!removedLen)
			{
				return removed;
			}
			this._drawflags |= 4; //contentschanged
			return removed;
		},
		_getCurrentSegment: function()
		{
			var segments = this._segments;
			if (segments.length == 0)
			{
				Eco.Logger.error('Use a moveTo() command first');
			}
			return segments[segments.length - 1];
		},
		curveTo: function(pt1, pt2, radius)
		{
			var t = radius||0.5,
				t1 = 1 - t,
				current = this._getCurrentSegment().point,
				controlPt = pt1.subtract(current.multiply(t1 * t1)).subtract(to.multiply(t * t)).divide(2 * t * t1);
				//controlPt = pt1.subtract(current.multiply(t1 * t1)).subtract(to.multiply(t * t)).divide(2 * t * t1);
			if (controlPt.isNaN())
			{
				Eco.Logger.error(
					'Cannot put a curve through points with parameter = ' + t);
			}
			this.quadraticCurveTo(controlPt, to);
		},
		quadraticCurveTo: function(controlPt, endpt)
		{
			var current = this._getCurrentSegment().point;
			//this.cubicCurveTo(
			//	controlPt.add(current.subtract(controlPt).multiply(1 / 3)),
			//	controlPt.add(endpt.subtract(controlPt).multiply(1 / 3)),
			//	endpt
			//);
			this.cubicCurveTo(
				controlPt.add(controlPt.multiply(1 / 3)),
				controlPt.add(endpt.multiply(1 / 3)),
				endpt
			);
		},
		cubicCurveTo: function(control1Pt, control2Pt, endpt)
		{
			var read = 0;
			if ( control1Pt instanceof Eco.Point )
			{
				read += 1;
			}
			else
			{
				control1Pt = new Eco.Point(arguments[0], arguments[1]);
				read += 2;
			}
			if ( arguments[read] instanceof Eco.Point )
			{
				control2Pt = arguments[read];
				read += 1;
			}
			else
			{
				control2Pt = new Eco.Point(arguments[read], arguments[read + 1]);
				read += 2;
			}
			if ( arguments[read] instanceof Eco.Point )
			{
				endpt = arguments[read];
				read += 1;
			}
			else
			{
				endpt = new Eco.Point(arguments[read], arguments[read + 1]);
				read += 2;
			}

			var current = this._getCurrentSegment();
			//current.outVector = control1Pt.subtract(current.point);
			current.outVector = control1Pt;
			var seg = {
				point: endpt.clone(),
				//inVector: control2Pt.subtract(endpt),
				inVector: control2Pt.clone(),
				nextSeg: null
			};
			this._add([ seg ]);
		},
		arcTo: function(pt, radiusPt, rotation, clockwise, large)
		{
			var current = this._getCurrentSegment(),
				from = current.point,
				to, radius,
				center, extent, vector, matrix,
				read = 0;

			if ( pt instanceof Eco.Point )
			{
					to = pt;
					read += 1;
			}
			else if ( Eco.isNumber(arguments[0]) && Eco.isNumber(arguments[1]) )
			{
					to = new Eco.Point(arguments[0], arguments[1]);
					read += 2;
			}

			if ( arguments[read] instanceof Eco.Size )
			{
					radius = arguments[read];
					read += 1;
			}
			else if ( Eco.isNumber(arguments[read]) && Eco.isNumber(arguments[read + 1]) )
			{
					radius = new Eco.Size(arguments[read], arguments[read + 1]);
					read += 2;
			}

			if ( Eco.isNumber(arguments[read]) )
			{
				rotation = arguments[read];
				read += 1;
			}
			
			if ( Eco.isNumber(arguments[read]) )
			{
				clockwise = !!arguments[read];
				read += 1;
			}

			if ( Eco.isNumber(arguments[read]) )
			{
				large = !!arguments[read];
				read += 1;
			}

			if (radius.isZero())
			{
				return this.lineTo(to);
			}
			var middle = from.add(to).divide(2),
				pt = from.subtract(middle).rotate(-rotation),
				x = pt.x,
				y = pt.y,
				abs = Math.abs,
				EPSILON = 1e-11,
				rx = abs(radius.width),
				ry = abs(radius.height),
				rxSq = rx * rx,
				rySq = ry * ry,
				xSq =  x * x,
				ySq =  y * y;
			var factor = Math.sqrt(xSq / rxSq + ySq / rySq);
			if (factor > 1)
			{
				rx *= factor;
				ry *= factor;
				rxSq = rx * rx;
				rySq = ry * ry;
			}
			factor = (rxSq * rySq - rxSq * ySq - rySq * xSq) /
					(rxSq * ySq + rySq * xSq);
			if (abs(factor) < EPSILON)
			{
					factor = 0;
			}
			if (factor < 0)
			{
				Eco.Logger.error('Cannot create an arc with the given arguments');
			}
			center = new Eco.Point(rx * y / ry, -ry * x / rx);
			center = center.multiply( (large === clockwise ? -1 : 1)
						* Math.sqrt(factor));
			center = center.rotate(rotation);
			center = center.add(middle);
			
			matrix = new Eco.Matrix().translate(center).rotate(rotation)
					.scale(rx, ry);
			vector = matrix._inverseTransform(from);
			if ( !vector )
			{
				return;
			}
			extent = vector.getDirectedAngle(matrix._inverseTransform(to));
			if (!clockwise && extent > 0)
				extent -= 360;
			else if (clockwise && extent < 0)
				extent += 360;

			var ext = Math.abs(extent),
				count =  ext >= 360 ? 4 : Math.ceil(ext / 90),
				inc = extent / count,
				half = inc * Math.PI / 360,
				z = 4 / 3 * Math.sin(half) / (1 + Math.cos(half)),
				segs = [], seg;

			for (var i = 0; i <= count; i++)
			{
				var pt = to,
					out = null;
				if (i < count)
				{
					out = vector.rotate(90).multiply(z);
					if (matrix)
					{
						pt = matrix._transformPoint(vector);
						out = matrix._transformPoint(vector.add(out));
								//.subtract(pt);
					}
					else
					{
						pt = center.add(vector);
					}
				}
				if (i === 0)
				{
					current.outVector = out;
				}
				else
				{
					var _in = vector.rotate(-90).multiply(z);
					if (matrix)
					{
						_in = matrix._transformPoint(vector.add(_in));
								//.subtract(pt);
					}
					seg = {
						point: pt.clone(),
						//inVector: control2Pt.subtract(endpt),
						inVector: _in.clone(),
						outVector: out,
						nextSeg: null
					};
					segs.push(seg);
				}
				vector = vector.rotate(inc);
			}
			this._add(segs);
		},
		moveTo: function(pt)
		{
			var argsLen = arguments.length;
			if ( argsLen == 2 )
			{
				pt = new Eco.Point(arguments[0], arguments[1]);
			}
			
			if (this._segments.length === 1)
			{
				this.removeSegment(0);
			}
			if (!this._segments.length)
			{
				var seg = {
					point: pt.clone(),
					nextSeg: null
				};
				this._add([ seg ]);
			}
		},
		lineTo: function(pt)
		{
			var argsLen = arguments.length;
			if ( argsLen == 2 )
			{
				pt = new Eco.Point(arguments[0], arguments[1]);
			}
			var seg = {
				point: pt.clone(),
				nextSeg: null
			};
			this._add([ seg ]);
		},
		closePath: function()
		{
			var first = this.getFirstSegment(),
				last = this.getLastSegment();
			if (first.point.equals(last.point))
			{
				first.inVector = last.inVector;
				this.removeSegment(this._segments.length - 1);
			}
			this.setClosed(true);
		},
		getFirstSegment: function()
		{
			return this._segments[0];
		},
		getLastSegment: function()
		{
			return this._segments[this._segments.length - 1];
		},
		_adjustSegments: function()
		{
			if (this._pathDataChanged)
			{
				var length = this._countSegs(),
					segments = this._segments,
					seg;
				for (var i = 0; i < length; i++)
				{
					seg = segments[i];
					seg.nextSeg = segments[i + 1] || segments[0];
				}
				this._pathDataChanged;
			}
		},
		_countSegs: function()
		{
			var length = this._segments.length;
			return !this._closed && length > 0 ? length - 1 : length;
		},
		_drawShape: function(ctx)
		{
			var segs = this._segments,
				parts = [];

			if (segs.length === 0)
			{
				return;
			}
			var gap = 0;
			if ( this._isOddStrokeWidth )
			{
				gap = 0.5;
			}

			function drawSegment(seg1, seg2, skipLine) {
				var point1 = seg1.point,
					point2 = seg2.point,
					controlPt1 = seg1.outVector,
					controlPt2 = seg2.inVector, pt;
				if ( !(controlPt1 && controlPt2) || (controlPt1.isZero() && controlPt2.isZero()) )
				{
					if (!skipLine)
					{
						ctx.lineTo(point2.x + gap, point2.y + gap);
					}
				}
				else
				{
					ctx.bezierCurveTo(controlPt1.x + gap, controlPt1.y + gap, controlPt2.x + gap, controlPt2.y + gap, point2.x + gap, point2.y + gap);
				}
			}
			ctx.beginPath();
			var mpt = segs[0].point;
			ctx.moveTo(mpt.x + gap, mpt.y + gap);
			for (var i = 0, l = segs.length  - 1; i < l; i++)
			{
				//trace(i + "===>" + segments[i].toString() + "//" + segments[i + 1].toString());
				drawSegment(segs[i], segs[i + 1], false);
			}
			if (this._closed)
			{
				drawSegment(segs[segs.length - 1], segs[0], true);
				ctx.closePath();
			}
		},
		_getSegPathData: function(parts, seg1, seg2, skipLine)
		{
			var point1 = seg1.point,
				point2 = seg2.point,
				controlPt1 = seg1.outVector,
				controlPt2 = seg2.inVector, pt,
				fmtNumber = Eco.ClassUtils.fmtNumber;
			if ( !(controlPt1 && controlPt2) || (controlPt1.isZero() && controlPt2.isZero()) )
			{
				if (!skipLine)
				{
				
					//parts.push('L' + ClassUtils.fmtPoint(point2, " "));
					parts.push('L' + fmtNumber(point2.x) + ',' + fmtNumber(point2.y));
				}
			}
			else
			{
				//var end = point2.subtract(point1);
				/*
				parts.push('C' + ClassUtils.fmtPoint(controlPt1, " ")
				//		+ ' ' + ClassUtils.fmtPoint(end.add(controlPt2), " ")
				//		+ ' ' + ClassUtils.fmtPoint(end, " "));
						+ ' ' + ClassUtils.fmtPoint(controlPt2, " ")
						+ ' ' + ClassUtils.fmtPoint(point2, " "));
				*/
				parts.push('C' + fmtNumber(controlPt1.x) + ',' + fmtNumber(controlPt1.y)
				//		+ ' ' + ClassUtils.fmtPoint(end.add(controlPt2), " ")
				//		+ ' ' + ClassUtils.fmtPoint(end, " "));
						+ ' ' + fmtNumber(controlPt2.x) + ',' + fmtNumber(controlPt2.y)
						+ ' ' + fmtNumber(point2.x) + ',' + fmtNumber(point2.y));
			}
		},
		getPathData: function()
		{
			var segs = this._segments,
				parts = [];

			if (segs.length === 0)
			{
				return '';
			}
			var mpt = segs[0].point,
				fmtNumber = Eco.ClassUtils.fmtNumber;
			//parts.push('M' + ClassUtils.fmtPoint(segs[0].point, " "));
			parts.push('M' + fmtNumber(mpt.x) + ',' + fmtNumber(mpt.y));
			for (var i = 0, l = segs.length  - 1; i < l; i++)
			{
				//trace(i + "===>" + segments[i].toString() + "//" + segments[i + 1].toString());
				this._getSegPathData(parts, segs[i], segs[i + 1], false);
			}
			if (this._closed)
			{
				this._getSegPathData(parts, segs[segs.length - 1], segs[0], true);
				parts.push('Z');
			}
			return parts.join('');
		},
		setPathData: function(data)
		{
			var clsPoint = Eco.Point;
			var parts = data.match(/[mlhvcsqtaz][^mlhvcsqtaz]*/ig),
				coords,
				relative = false,
				control,
				current = new Eco.Point(); 

			function getCoord(index, coord, update) {
				var val = parseFloat(coords[index]);
				if (relative)
					val += current[coord];
				if (update)
					current[coord] = val;
				return val;
			}

			function getPoint(index, update) {
				return new clsPoint(
					getCoord(index, 'x', update),
					getCoord(index + 1, 'y', update)
				);
			}

			//if (this._type === 'path')
				this.removeSegments();
			//else
			///	this.removeChildren();

			for (var i = 0, l = parts.length; i < l; i++)
			{
				var part = parts[i],
					cmd = part[0],
					lower = cmd.toLowerCase();
				coords = part.match(/[+-]?(?:\d*\.\d+|\d+\.?)(?:[eE][+-]?\d+)?/g);
				relative = cmd === lower;
				var length = coords && coords.length;
				switch (lower) {
				case 'm':
				case 'l':
					for (var j = 0; j < length; j += 2)
						this[j === 0 && lower === 'm' ? 'moveTo' : 'lineTo'](
								getPoint(j, true));
					break;
				case 'h':
				case 'v':
					var coord = lower == 'h' ? 'x' : 'y';
					for (var j = 0; j < length; j++) {
						getCoord(j, coord, true);
						this.lineTo(current);
					}
					break;
				case 'c':
					for (var j = 0; j < length; j += 6) {
						this.cubicCurveTo(
								getPoint(j),
								control = getPoint(j + 2),
								getPoint(j + 4, true));
					}
					break;
				case 's':
					for (var j = 0; j < length; j += 4) {
						this.cubicCurveTo(
								current.multiply(2).subtract(control),
								control = getPoint(j),
								getPoint(j + 2, true));
					}
					break;
				case 'q':
					for (var j = 0; j < length; j += 4) {
						this.quadraticCurveTo(
								control = getPoint(j),
								getPoint(j + 2, true));
					}
					break;
				case 't':
					for (var j = 0; j < length; j += 2) {
						this.quadraticCurveTo(
								control = current.multiply(2).subtract(control),
								getPoint(j, true));
					}
					break;
				case 'a':
					//rx ry x-axis-rotation large-arc-flag sweep-flag x y
					for (var j = 0; j < length; j += 7) {
						this.arcTo(current = getPoint(j + 5),
						new Eco.Size(+coords[0], +coords[1]),
						+coords[2], +coords[4], +coords[3]);
					}
					break;
				case 'z':
					this.closePath();
					break;
				}
			}
		},
		_getRect: function(bStrokeWidth, iscurrent)
		{
			var rect = this._rect;
			if ( iscurrent ) rect = null;
			if ( !rect )
			{
				var segs = this._segments;

				if (segs.length === 0)
				{
					rect = new Eco.Rectangle();
					this._rect = rect;
					return rect;
				}
				var pt = segs[0].point,
					pt1, pt2, controlPt1, controlPt2,
					seg1, seg2, tmpX, tmpY;
				var curx = pt.x;
				var cury = pt.y;
				for (var i = 0, l = segs.length  - 1; i < l; i++)
				{
					seg1 = segs[i];
					seg2 = segs[i + 1];
					pt1 = seg1.point;
					pt2 = seg2.point;
					controlPt1 = seg1.outVector;
					controlPt2 = seg2.inVector;
					if ( !(controlPt1 && controlPt2) || (controlPt1.isZero() && controlPt2.isZero()) )
					{
						tmpX = pt2.x;
						tmpY = pt2.y;
						if ( !rect )
						{
							rect = new Eco.Rectangle(Math.min(curx, tmpX), Math.min(cury, tmpY), Math.max(curx, tmpX) - Math.min(curx, tmpX), Math.max(cury, tmpY) - Math.min(cury, tmpY));
						}
						else
						{
							this._unionRect(rect, Math.min(curx, tmpX), Math.min(cury, tmpY), Math.max(curx, tmpX), Math.max(cury, tmpY));
						}
						curx = tmpX;
						cury = tmpY;
					}
					else
					{
						rect = this._getCurveBounds(curx, cury, controlPt1.x, controlPt1.y, controlPt2.x, controlPt2.y, pt2.x, pt2.y, rect);
						curx = pt2.x;
						cury = pt2.y;
					}
				}
				if (this._closed)
				{
					seg1 = segs[segs.length - 1];
					seg2 = segs[0];
					pt1 = seg1.point;
					pt2 = seg2.point;
					controlPt1 = seg1.outVector;
					controlPt2 = seg2.inVector;
					if ( !(controlPt1 && controlPt2) || (controlPt1.isZero() && controlPt2.isZero()) )
					{
						tmpX = pt2.x;
						tmpY = pt2.y;
						if ( !rect )
						{
							rect = new Eco.Rectangle(Math.min(curx, tmpX), Math.min(cury, tmpY), Math.max(curx, tmpX) - Math.min(curx, tmpX), Math.max(cury, tmpY) - Math.min(cury, tmpY));
						}
						else
						{
							this._unionRect(rect, Math.min(curx, tmpX), Math.min(cury, tmpY), Math.max(curx, tmpX), Math.max(cury, tmpY));
						}
						curx = tmpX;
						cury = tmpY;
					}
					else
					{
						rect = this._getCurveBounds(curx, cury, controlPt1.x, controlPt1.y, controlPt2.x, controlPt2.y, pt2.x, pt2.y, rect);
						curx = pt2.x;
						cury = pt2.y;
					}
				}
				if ( !rect )
				{
					rect = new Eco.Rectangle();
					this._rect = rect;
					return rect;
				}
				if ( !iscurrent ) this._rect = rect;
			}
			return rect;
		},
		_unionRect: function(rect, left, top, right, bottom)
		{
			var l = Math.min(rect.x, left);
			var t = Math.min(rect.y, top);
			var r = Math.max(rect.getRight(), right);
			var b = Math.max(rect.getBottom(), bottom);
			rect.x = l;
			rect.y = t;
			rect.width = r - l;
			rect.height = b - t;
		},
		// 곡선일때만 사용한다.
		_strokePadding: 0,
		"_getCurveBounds": function(sx, sy, ctrl1X, ctrl1Y, ctrl2X, ctrl2Y, eX, eY, rect)
		{
			var coords = new Array(6),
				prevCoords = [sx, sy, 0, 0, ctrl1X, ctrl1Y],
				coords = [eX, eY, ctrl2X, ctrl2Y, 0, 0],
				min = [sx, sy],
				max = [sx, sy], 
				roots = new Array(2),
				strokePadding = this._strokePadding;

			for (var i = 0; i < 2; i++)
			{
				this._calcCurve(
					prevCoords[i], 
					prevCoords[i + 4], 
					coords[i + 2], 
					coords[i], 
					i, strokePadding, min, max, roots);
			}
			if ( !rect )
			{
				rect = new Eco.Rectangle(min[0], min[1], max[0] - min[0], max[1] - min[1]);
			}
			else
			{
				this._unionRect(rect, min[0], min[1], max[0], max[1]);
			}
			return rect;
		},
		_curveMin: 0.00001,
		_curveMax: 1 - 0.00001,
		_calcCurve: function(v0, v1, v2, v3, xyCoord, padding, min, max, roots)
		{
			var a = 3 * (v1 - v2) - v0 + v3,
				b = 2 * (v0 + v2) - 4 * v1,
				c = v1 - v0,
				count = MathUtil.solveQuadratic(a, b, c, roots),
				tMin = this._curveMin,
				tMax = this._curveMax,
				u, t;

			this._calcCurveMinMax(v3, 0, xyCoord, min, max);
			for (var i = 0; i < count; i++)
			{
				t = roots[i];
				u = 1 - t;
				if (tMin < t && t < tMax)
				{
					this._calcCurveMinMax(u * u * u * v0
						+ 3 * u * u * t * v1
						+ 3 * u * t * t * v2
						+ t * t * t * v3,
						padding, xyCoord, min, max);
				}
			}
		},
		"_calcCurveMinMax": function(value, padding, xyCoord, min, max)
		{
			var left = value - padding,
				right = value + padding;

			if (left < min[xyCoord])
			{
				min[xyCoord] = left;
			}
			if (right > max[xyCoord])
			{
				max[xyCoord] = right;
			}
		},
		hitTest: function(pt)
		{
			if ( this.isClosed() )
			{
				return this.contains(pt);
			}
			else
			{
				return this.getStrokeCross(2, pt);
			}
			return false;
		},
		contains: function(pt)
		{
			var x = pt.x,
				y = pt.y;
			var segments = this._segments,
				crossings = 0,
				roots = new Array(3),
				last, previous;

			if (segments.length < 2)
			{
				return false;
			}

			last = this._getValues(segments[segments.length - 1], segments[0]);

			previous = last;

			var vals, tmpx, tmpy;
			for (var i = 0, l = segments.length - 1; i < l; i++)
			{
				vals = this._getValues(segments[i], segments[i+1]);
				tmpx = vals[0];
				tmpy = vals[1];
				//trace(i + ";;;vals==>" + vals);
				if (!(tmpx === vals[2] && tmpy === vals[3] && tmpx === vals[4]
						&& tmpy === vals[5] && tmpx === vals[6] && tmpy === vals[7]))
				{
					crossings += this._getCrossings(vals, previous,
							x, y, roots);
					previous = vals;
				}
			}
			crossings += this._getCrossings(last, previous, x, y,
					roots);
			//trace("crossings::"+ crossings);
			return (crossings & 1) === 1;
		},
		getStrokeCross: function(tolerance, pt)
		{
			var segs = this._segments,
				rect, sz = tolerance/2;

			if (segs.length === 0)
			{
				return null;
			}
			var hitRect = new Eco.Rectangle(pt.x - sz, pt.y - sz, tolerance, tolerance);
			var firstPt = segs[0].point.clone(),
				args = [],
				retVals = [],
				pt1, pt2, controlPt1, controlPt2,
				seg1, seg2, tmpX, tmpY;

			var topLeftPt = hitRect.getTopLeft(),
				bottomRightPt = hitRect.getBottomRight(),
				ret;
			var i = 0;
			while( i < 4 )
			{
				args[i] = new Eco.Point();
				i++;
			}
			args[0].x = firstPt.x;
			args[0].y = firstPt.y;
			for (var i = 0, l = segs.length  - 1; i < l; i++)
			{
				seg1 = segs[i];
				seg2 = segs[i + 1];
				pt1 = seg1.point;
				pt2 = seg2.point;
				controlPt1 = seg1.outVector;
				controlPt2 = seg2.inVector;
				if ( !(controlPt1 && controlPt2) || (controlPt1.isZero() && controlPt2.isZero()) )
				{
					args[1].x = pt2.x;
					args[1].y = pt2.y;
					//this._correctPoint(args[0], args[1]);
					//this._correctPoint(args[1], args[0]);
					//var resPt = new Eco.Point();
					if ( ret = this._intersectLineRectangle(args[0], args[1], topLeftPt, bottomRightPt) )
					{
						//trace(ret);
						//retVals.push(resPt);
						return true;
					}
					args[0].x = pt2.x;
					args[0].y = pt2.y;
				}
				else
				{
					args[1].x = controlPt1.x;
					args[1].y = controlPt1.y;
					args[2].x = controlPt2.x;
					args[2].y = controlPt2.y;
					args[3].x = pt2.x;
					args[3].y = pt2.y;
					if ( ret = this._intersectBezier3Rectangle(args[0], args[1], args[2], args[3], topLeftPt, bottomRightPt) )
					{
						//trace(ret);
						//retVals.push(resPt);
						return true;
					}
					args[0].x = pt2.x;
					args[0].y = pt2.y;
				}
			}
			if (this._closed)
			{
				seg1 = segs[segs.length - 1];
				seg2 = segs[0];
				pt1 = seg1.point;
				pt2 = seg2.point;
				controlPt1 = seg1.outVector;
				controlPt2 = seg2.inVector;
				if ( !(controlPt1 && controlPt2) || (controlPt1.isZero() && controlPt2.isZero()) )
				{
					args[1].x = pt2.x;
					args[1].y = pt2.y;
					//this._correctPoint(args[0], args[1]);
					//this._correctPoint(args[1], args[0]);
					//var resPt = new Eco.Point();
					if ( ret = this._intersectLineRectangle(args[0], args[1], topLeftPt, bottomRightPt) )
					{
						//retVals.push(resPt);
						//trace(ret);
						return true;
					}
					args[0].x = pt2.x;
					args[0].y = pt2.y;
				}
				else
				{
					args[1].x = controlPt1.x;
					args[1].y = controlPt1.y;
					args[2].x = controlPt2.x;
					args[2].y = controlPt2.y;
					args[3].x = pt2.x;
					args[3].y = pt2.y;
					if ( ret = this._intersectBezier3Rectangle(args[0], args[1], args[2], args[3], topLeftPt, bottomRightPt) )
					{
						//trace(ret);
						//retVals.push(resPt);
						return true;
					}
					args[0].x = pt2.x;
					args[0].y = pt2.y;
				}
			}
			return false;
		},
		_intersectLineLine: function(line1StartPt, line1EndPt, line2StartPt, line2EndPt, resPt)
		{
			var line2DistX = line2EndPt.x - line2StartPt.x,
				line2DistY = line2EndPt.y - line2StartPt.y,
				line1DistX = line1EndPt.x - line1StartPt.x,
				line1DistY = line1EndPt.y - line1StartPt.y,
				ua_t = line2DistX * (line1StartPt.y - line2StartPt.y) - line2DistY * (line1StartPt.x - line2StartPt.x),
				ub_t = line1DistX * (line1StartPt.y - line2StartPt.y) - line1DistY * (line1StartPt.x - line2StartPt.x),
				u_b  = line2DistY * line1DistX - line2DistX * line1DistY;

			if ( !MathUtil.isZero(u_b) )
			{
				var ua = ua_t / u_b;
				var ub = ub_t / u_b;

				if ( 0 <= ua && ua <= 1 && 0 <= ub && ub <= 1 )
				{
					resPt.x = line1StartPt.x + ua * (line1EndPt.x - line1StartPt.x);
					resPt.y = line1StartPt.y + ua * (line1EndPt.y - line1StartPt.y);
					return true;
				}
			}
			return false;
		},
		_intersectLineRectangle: function(lineStartPt, lineEndPt, rectTopLeftPt, rectBottomRightPt)
		{
			var clsPoint = Eco.Point;
			var minPt = clsPoint.min(rectTopLeftPt, rectBottomRightPt);
			var maxPt = clsPoint.max(rectTopLeftPt, rectBottomRightPt);
			var topRightPt = new clsPoint(maxPt.x, minPt.y);
			var bottomLeftPt = new clsPoint(minPt.x, maxPt.y);
			var inter1 = new clsPoint(),
				inter2 = new clsPoint(),
				inter3 = new clsPoint(),
				inter4 = new clsPoint();
			var ret = this._intersectLineLine(minPt, topRightPt, lineStartPt, lineEndPt, inter1);
			var resPts = [];
			if ( ret )
			{
				resPts.push(inter1);
			}
			ret = this._intersectLineLine(topRightPt, maxPt, lineStartPt, lineEndPt, inter2);
			if ( ret )
			{
				resPts.push(inter2);
			}
			ret = this._intersectLineLine(maxPt, bottomLeftPt, lineStartPt, lineEndPt, inter3);
			if ( ret )
			{
				resPts.push(inter3);
			}
			ret = this._intersectLineLine(bottomLeftPt, minPt, lineStartPt, lineEndPt, inter4);
			if ( ret )
			{
				resPts.push(inter4);
			}
			if ( resPts.length )
			{
				return resPts;
			}
		},
		_intersectBezier3Line: function(startPt, control1Pt, control2Pt, endPt, lineStartPt, lineEndPt, resPts)
		{
			var clsPoint = Eco.Point;
			var a, b, c, d;       // temporary variables
			var c3, c2, c1, c0;   // coefficients of cubic
			var cl;               // c coefficient for normal form of line
			var n;                // normal for normal form of line
			var min = clsPoint.min(lineStartPt, lineEndPt); // used to determine if point is on line segment
			var max = clsPoint.max(lineStartPt, lineEndPt); // used to determine if point is on line segment
			
			// Start with Bezier using Bernstein polynomials for weighting functions:
			//     (1-t^3)P1 + 3t(1-t)^2P2 + 3t^2(1-t)P3 + t^3P4
			//
			// Expand and collect terms to form linear combinations of original Bezier
			// controls.  This ends up with a vector cubic in t:
			//     (-P1+3P2-3P3+P4)t^3 + (3P1-6P2+3P3)t^2 + (-3P1+3P2)t + P1
			//             /\                  /\                /\       /\
			//             ||                  ||                ||       ||
			//             c3                  c2                c1       c0
			
			// Calculate the coefficients
			a = startPt.multiply(-1);
			b = control1Pt.multiply(3);
			c = control2Pt.multiply(-3);
			d = a.add(b.add(c.add(endPt)));
			c3 = new clsPoint(d.x, d.y); //use Vector

			a = startPt.multiply(3);
			b = control1Pt.multiply(-6);
			c = control2Pt.multiply(3);
			d = a.add(b.add(c));
			c2 = new clsPoint(d.x, d.y);

			a = startPt.multiply(-3);
			b = control1Pt.multiply(3);
			c = a.add(b);
			c1 = new clsPoint(c.x, c.y);

			c0 = new clsPoint(startPt.x, startPt.y);
			
			// Convert line to normal form: ax + by + c = 0
			// Find normal to line: negative inverse of original line's slope
			n = new clsPoint(lineStartPt.y - lineEndPt.y, lineEndPt.x - lineStartPt.x);
			
			// Determine new c coefficient
			cl = lineStartPt.x*lineEndPt.y - lineEndPt.x*lineStartPt.y;

			// ?Rotate each cubic coefficient using line for new coordinate system?
			// Find roots of rotated cubic
			var roots = [];
			MathUtil.solveCubic(n.dot(c3), n.dot(c2), n.dot(c1), n.dot(c0) + cl, roots);

			// Any roots in closed interval [0,1] are intersections on Bezier, but
			// might not be on the line segment.
			// Find intersections and calculate point coordinates
			for ( var i = 0; i < roots.length; i++ )
			{
				var t = roots[i];

				if ( 0 <= t && t <= 1 )
				{
					// We're within the Bezier curve
					// Find point on Bezier
					var p5 = startPt.lerp(control1Pt, t);
					var p6 = control1Pt.lerp(control2Pt, t);
					var p7 = control2Pt.lerp(endPt, t);

					var p8 = p5.lerp(p6, t);
					var p9 = p6.lerp(p7, t);

					var p10 = p8.lerp(p9, t);

					// See if point is on line segment
					// Had to make special cases for vertical and horizontal lines due
					// to slight errors in calculation of p10
					if ( lineStartPt.x == lineEndPt.x )
					{
						if ( min.y <= p10.y && p10.y <= max.y )
						{
							resPts.push(p10);
						}
					}
					else if ( lineStartPt.y == lineEndPt.y )
					{
						if ( min.x <= p10.x && p10.x <= max.x )
						{
							resPts.push(p10);
						}
					}
					else if ( p10.gte(min) && p10.lte(max) )
					{
						resPts.push(p10);
					}
				}
			}
			if ( resPts.length ) return true;
			return false;
		},
		_intersectBezier3Rectangle: function(startPt, control1Pt, control2Pt, endPt, rectTopLeftPt, rectBottomRightPt)
		{
			var clsPoint = Eco.Point;
			var minPt = clsPoint.min(rectTopLeftPt, rectBottomRightPt);
			var maxPt = clsPoint.max(rectTopLeftPt, rectBottomRightPt);
			var topRightPt = new clsPoint(maxPt.x, minPt.y);
			var bottomLeftPt = new clsPoint(minPt.x, maxPt.y);
			var inter1 = [],
				inter2 = [],
				inter3 = [],
				inter4 = [];

			var ret = this._intersectBezier3Line(startPt, control1Pt, control2Pt, endPt, minPt, topRightPt, inter1);
			var resPts = [];
			if ( ret )
			{
				resPts = resPts.concat(inter1);
			}
			ret = this._intersectBezier3Line(startPt, control1Pt, control2Pt, endPt, topRightPt, maxPt, inter2);
			if ( ret )
			{
				resPts = resPts.concat(inter2);
			}
			ret = this._intersectBezier3Line(startPt, control1Pt, control2Pt, endPt, maxPt, bottomLeftPt, inter3);
			if ( ret )
			{
				resPts = resPts.concat(inter3);
			}
			ret = this._intersectBezier3Line(startPt, control1Pt, control2Pt, endPt, bottomLeftPt, minPt, inter4);
			if ( ret )
			{
				resPts = resPts.concat(inter4);
			}
			if ( resPts.length )
			{
				return resPts;
			}
		},
		_getValues: function(seg1, seg2)
		{
			var pt1 = seg1.point;
			var pt2 = seg2.point;
			var controlPt1 = seg1.outVector;
			var controlPt2 = seg2.inVector;

			if ( !(controlPt1 && controlPt2) || (controlPt1.isZero() && controlPt2.isZero()) )
			{
				return [
					pt1.x, pt1.y,
					pt1.x, pt1.y,
					pt2.x, pt2.y,
					pt2.x, pt2.y
				];
			}
			else
			{
				return [
					pt1.x, pt1.y,
					controlPt1.x, controlPt1.y,
					controlPt2.x, controlPt2.y,
					pt2.x, pt2.y
				];
			}
		},
		_evaluate: function(v, t, type)
		{
			var p1x = v[0], p1y = v[1],
				c1x = v[2], c1y = v[3],
				c2x = v[4], c2y = v[5],
				p2x = v[6], p2y = v[7],
				x, y;

			if (type === 0 && (t === 0 || t === 1))
			{
				x = t === 0 ? p1x : p2x;
				y = t === 0 ? p1y : p2y;
			}
			else
			{
				var cx = 3 * (c1x - p1x),
					bx = 3 * (c2x - c1x) - cx,
					ax = p2x - p1x - cx - bx,

					cy = 3 * (c1y - p1y),
					by = 3 * (c2y - c1y) - cy,
					ay = p2y - p1y - cy - by;
				if (type === 0)
				{
					x = ((ax * t + bx) * t + cx) * t + p1x;
					y = ((ay * t + by) * t + cy) * t + p1y;
				}
				else
				{
					var tMin = this._curveMin;
					if (t < tMin && c1x == p1x && c1y == p1y
							|| t > 1 - tMin && c2x == p2x && c2y == p2y)
					{
						x = c2x - c1x;
						y = c2y - c1y;
					}
					else
					{
						x = (3 * ax * t + 2 * bx) * t + cx;
						y = (3 * ay * t + 2 * by) * t + cy;
					}
					if (type === 3)
					{
						var x2 = 6 * ax * t + 2 * bx,
							y2 = 6 * ay * t + 2 * by;
						return (x * y2 - y * x2) / Math.pow(x * x + y * y, 3 / 2);
					}
				}
			}
			return type == 2 ? new Eco.Point(y, -x) : new Eco.Point(x, y);
		},
		_solveCubic: function(v, coord, val, roots)
		{
			var p1 = v[coord],
				c1 = v[coord + 2],
				c2 = v[coord + 4],
				p2 = v[coord + 6],
				c = 3 * (c1 - p1),
				b = 3 * (c2 - c1) - c,
				a = p2 - p1 - c - b;
			return MathUtil.solveCubic(a, b, c, p1 - val, roots);
		},
		_getParameterOf: function(v, x, y)
		{
			var tMin = this._curveMin;
			if (Math.abs(v[0] - x) < tMin
					&& Math.abs(v[1] - y) <tMin)
			{
				return 0;
			}
			if (Math.abs(v[6] - x) < tMin
					&& Math.abs(v[7] - y) < tMin)
			{
				return 1;
			}
			var txs = [],
				tys = [],
				sx = this._solveCubic(v, 0, x, txs),
				sy = this._solveCubic(v, 1, y, tys),
				tx, ty;
			for (var cx = 0;  sx == -1 || cx < sx;)
			{
				if (sx == -1 || (tx = txs[cx++]) >= 0 && tx <= 1)
				{
					for (var cy = 0; sy == -1 || cy < sy;)
					{
						if (sy == -1 || (ty = tys[cy++]) >= 0 && ty <= 1)
						{
							if (sx == -1) tx = ty;
							else if (sy == -1) ty = tx;
							if (Math.abs(tx - ty) < tMin)
							{
								return (tx + ty) * 0.5;
							}
						}
					}
					if (sx == -1)
					{
						break;
					}
				}
			}
			return null;
		},
		_changesOrientation: function(tangent, prev)
		{
			return this._evaluate(prev, 1, 1).y
					* tangent.y > 0;
		},
		_getCrossings: function(v, prev, x, y, roots)
		{
			var count = this._solveCubic(v, 1, y, roots),
				crossings = 0,
				tolerance = this._curveMin,
				abs = Math.abs;

			if (count === -1)
			{
				roots[0] = this._getParameterOf(v, x, y);
				count = roots[0] !== null ? 1 : 0;
			}

			for (var i = 0; i < count; i++)
			{
				var t = roots[i];
				if (t > -tolerance && t < 1 - tolerance)
				{
					var pt = this._evaluate(v, t, 0);
					if (x < pt.x + tolerance)
					{
						var tan = this._evaluate(v, t, 1);
						if (Math.abs(pt.x - x) < tolerance)
						{
							var angle = tan.getAngle();
							if (angle > -180 && angle < 0
								&& (t > tolerance || this._changesOrientation(tan, prev)))
							{
									continue;
							}
						}
						else
						{
							if (Math.abs(tan.y) < tolerance
								|| t < tolerance && !this._changesOrientation(tan, prev))
							{
									continue;
							}
						}
						crossings++;
					}
				}
			}
			return crossings;
		}
	});
}

if ( !JsNamespace.exist("Eco.GraphicPaths") )
{
	JsNamespace.declareClass("Eco.GraphicPaths", {
		"extends": "Eco.GraphicElement",
		/**
		 * Graphic 생성자(constructor)
		 * @param {Graphic} g 생성되는 요소가 속해야 하는 Graphic 객체
		 * @param {Eco.Point} pt 요소가 위치하는 지점 좌표(Group이나, Layer를 0,0 기준으로 설정됨)
		 * @class Graphic
		 * @classdesc 다양한 shape를 그리고 operation 처리하는 기능이 있는 Class.
		 * @constructor Graphic
		*/
		initialize: function()
		{
			this._type = "Paths";
			this.callParent(arguments);
			var childs = arguments[this.__readArgs];
			this._childchanged = 0;
			if ( Eco.isArray(childs) )
			{
				this.addChildren(childs);
			}
		},
		properties: {
			"strokepen": {
				"set": function(val)
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
					var oldobj = this._currentStrokepen;
					if ( oldobj != newobj )
					{
						this._currentStrokepen = newobj;
						var node = this._firstChild;
						for ( ; node ; node = node._nextSibling )
						{
							node.setStrokepen(newobj);
						}
						if ( newobj )
						{
							var lineW = newobj.width||0;
							if ( this._strokeWidth != lineW )
							{
								this._strokeWidth = lineW;
								this._bounds = null;
								this._globalBounds = null;
							}
						}
						else
						{
							if ( this._strokeWidth != 0 )
							{
								this._strokeWidth = 0;
							}
						}
					}
				},
				"get": function()
				{
					var val = this._currentStrokepen;
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
					var oldobj = this._currentFillbrush;
					if ( oldobj != newobj )
					{
						this._currentFillbrush = newobj;
						var node = this._firstChild;
						for ( ; node ; node = node._nextSibling )
						{
							node.setFillbrush(val);
						}
					}
				},
				"get": function()
				{
					var val = this._currentFillbrush;
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
					var oldobj = this._currentFillgradation;
					if ( oldobj != newobj )
					{
						this._currentFillgradation = newobj;
						var node = this._firstChild;
						for ( ; node ; node = node._nextSibling )
						{
							node.setFillgradation(val);
						}
					}
				},
				"get": function()
				{
					var val = this._currentFillgradation;
					if ( val )
					{
						return val._value;
					}
					return null;
				}
			},
			"d": {
				"set": function(val)
				{
					if ( arguments.length == 0 || val == null )
					{
						return;
					}
					this.setPathData(val);
				},
				"get": function()
				{
					return this.getPathData();
				}
			}
		},
		_updateStyles: function(ctx, sizeChanged)
		{
		},
		_insertBefore: function()
		{
			var child = this.callParent(arguments);
			if ( child )
			{
				if ( this._currentStrokepen )
				{
					child.setStrokepen(this._currentStrokepen);
				}
				if ( this._currentFillbrush )
				{
					child.setFillbrush(this._currentFillbrush);
				}
				if ( this._currentFillgradation )
				{
					child.setFillgradation(this._currentFillgradation);
				}
			}
		},
		_getCurrentPath: function()
		{
			var len = this.getLength();
			if (!len )
			{
				Eco.Logger.error("Use a moveTo() command first");
			}
			return this.getChildByIndex(len - 1);
		},
		getFirstSegment: function()
		{
			var first = this.getFirstChild();
			return first && first.getFirstSegment();
		},
		getLastSegment: function()
		{
			var last = this.getLastChild();
			return last && last.getLastSegment();
		},
		moveTo: function()
		{
			var path = new Eco.GraphicPath();
			this.addChild(path);
			path.moveTo.apply(path, arguments);
		},
		moveBy: function(x, y)
		{
			var pt;
			if ( x instanceof Eco.Point )
			{
				pt = x;
			}
			else if ( Eco.isNumber(x) && Eco.isNumber(y) )
			{
				pt = new Eco.Point(x, y);
			}
			else
			{
				Eco.Logger.error("invalid arguments! =>" + Eco.Logger._getDumpString(arguments, true));
			}
			this.moveTo(this._getCurrentPath().getLastSegment().point.add(pt));
		},
		lineTo: function()
		{
			var path = this._getCurrentPath();
			path.lineTo.apply(path, arguments);
		},
		curveTo: function()
		{
			var path = this._getCurrentPath();
			path.curveTo.apply(path, arguments);
		},
		quadraticCurveTo: function()
		{
			var path = this._getCurrentPath();
			path.quadraticCurveTo.apply(path, arguments);
		},
		cubicCurveTo: function()
		{
			var path = this._getCurrentPath();
			path.cubicCurveTo.apply(path, arguments);
		},
		arcTo: function()
		{
			var path = this._getCurrentPath();
			path.arcTo.apply(path, arguments);
		},
		closePath: function()
		{
			this._getCurrentPath().closePath();
		},
		getPathData: function()
		{
			var node = this._firstChild,
				paths = [];
			for ( ; node ; node = node._nextSibling )
			{
				paths.push(node.getPathData());
			}
			return paths.join(' ');
		},
		setPathData: function(data)
		{
			var clsPoint = Eco.Point;
			var parts = data.match(/[mlhvcsqtaz][^mlhvcsqtaz]*/ig),
				coords,
				relative = false,
				control,
				current = new Eco.Point(); 

			function getCoord(index, coord, update) {
				var val = parseFloat(coords[index]);
				if (relative)
					val += current[coord];
				if (update)
					current[coord] = val;
				return val;
			}

			function getPoint(index, update) {
				return new clsPoint(
					getCoord(index, 'x', update),
					getCoord(index + 1, 'y', update)
				);
			}

			this.removeChildren();

			for (var i = 0, l = parts.length; i < l; i++)
			{
				var part = parts[i],
					cmd = part[0],
					lower = cmd.toLowerCase();
				coords = part.match(/[+-]?(?:\d*\.\d+|\d+\.?)(?:[eE][+-]?\d+)?/g);
				relative = cmd === lower;
				var length = coords && coords.length;
				switch (lower) {
				case 'm':
				case 'l':
					for (var j = 0; j < length; j += 2)
						this[j === 0 && lower === 'm' ? 'moveTo' : 'lineTo'](
								getPoint(j, true));
					break;
				case 'h':
				case 'v':
					var coord = lower == 'h' ? 'x' : 'y';
					for (var j = 0; j < length; j++) {
						getCoord(j, coord, true);
						this.lineTo(current);
					}
					break;
				case 'c':
					for (var j = 0; j < length; j += 6) {
						this.cubicCurveTo(
								getPoint(j),
								control = getPoint(j + 2),
								getPoint(j + 4, true));
					}
					break;
				case 's':
					for (var j = 0; j < length; j += 4) {
						this.cubicCurveTo(
								current.multiply(2).subtract(control),
								control = getPoint(j),
								getPoint(j + 2, true));
					}
					break;
				case 'q':
					for (var j = 0; j < length; j += 4) {
						this.quadraticCurveTo(
								control = getPoint(j),
								getPoint(j + 2, true));
					}
					break;
				case 't':
					for (var j = 0; j < length; j += 2) {
						this.quadraticCurveTo(
								control = current.multiply(2).subtract(control),
								getPoint(j, true));
					}
					break;
				case 'a':
					//rx ry x-axis-rotation large-arc-flag sweep-flag x y
					for (var j = 0; j < length; j += 7) {
						this.arcTo(current = getPoint(j + 5),
						new Eco.Size(+coords[0], +coords[1]),
						+coords[2], +coords[4], +coords[3]);
					}
					break;
				case 'z':
					this.closePath();
					break;
				}
			}
		},
		_render: function(ctx)
		{
			this._painted = 1;
		}
	});
}

if ( !JsNamespace.exist("Eco.GraphicLine") )
{
	JsNamespace.declareClass("Eco.GraphicLine", {
		"extends": "Eco.GraphicPath",
		/**
		 * Graphic 생성자(constructor)
		 * @param {Graphic} g 생성되는 요소가 속해야 하는 Graphic 객체
		 * @param {Eco.Point} pt 요소가 위치하는 지점 좌표(Group이나, Layer를 0,0 기준으로 설정됨)
		 * @class Graphic
		 * @classdesc 다양한 shape를 그리고 operation 처리하는 기능이 있는 Class.
		 * @constructor Graphic
		*/
		initialize: function()
		{
			this.callParent(arguments);
			this._type = "Line";
			var addarr = [];
			seg = {
				point: new Eco.Point(0, 0),
				nextSeg: null
			};
			addarr.push(seg);
			seg = {
				point: new Eco.Point(0, 0),
				nextSeg: null
			};
			addarr.push(seg);
			this._add(addarr);
		},
		properties: {
			"x1": {
				"set": function(val)
				{
					if ( arguments.length == 0 || val == null )
					{
						return;
					}
					var segs = this._segments;
					if ( segs[0].point.x != val )
					{
						segs[0].point.x = val;
						this._drawflags |= 4; //contentschanged
					}
				},
				"get": function()
				{
					var segs = this._segments,
						seg = segs[0];
					if ( seg )
					{
						return seg.point.x;
					}
					return 0;
				}
			},
			"y1": {
				"set": function(val)
				{
					if ( arguments.length == 0 || val == null )
					{
						return;
					}
					var segs = this._segments;
					if ( segs[0].point.y != val )
					{
						segs[0].point.y = val;
						this._drawflags |= 4; //contentschanged
					}
				},
				"get": function()
				{
					var segs = this._segments,
						seg = segs[0];
					if ( seg )
					{
						return seg.point.y;
					}
					return 0;
				}
			},
			"x2": {
				"set": function(val)
				{
					if ( arguments.length == 0 || val == null )
					{
						return;
					}
					var segs = this._segments;
					if ( segs[1].point.x != val )
					{
						segs[1].point.x = val;
						this._drawflags |= 4; //contentschanged
					}
				},
				"get": function()
				{
					var segs = this._segments,
						seg = segs[1];
					if ( seg )
					{
						return seg.point.x;
					}
					return 0;
				}
			},
			"y2": {
				"set": function(val)
				{
					if ( arguments.length == 0 || val == null )
					{
						return;
					}
					var segs = this._segments;
					if ( segs[1].point.y != val )
					{
						segs[1].point.y = val;
						this._drawflags |= 4; //contentschanged
					}
				},
				"get": function()
				{
					var segs = this._segments,
						seg = segs[1];
					if ( seg )
					{
						return seg.point.y;
					}
					return 0;
				}
			}
		}
	});
}