if ( !JsNamespace.exist("Eco.RectsManager") )
{
	JsNamespace.declareClass("Eco.RectsManager", {
		initialize: function()
		{
			var argLens = arguments.length;
			if ( argLens >= 1 )
			{
				var arg0 = arguments[0];
				if ( Eco.isArray(arg0) )
				{
					if ( argLens == 3 )
					{
						// offset, size
						this._rects = arg0.slice(arguments[1], arguments[1] + arguments[2] - 1);
					}
					else
					{
						this._rects = arg0.slice(0);
					}
					this._rects.sort(this._compare);
				}
				else if ( arg0 instanceof Eco.Rectangle )
				{
					this._rects = [arg0];
				}
				else if ( arg0 instanceof Eco.RectsManager )
				{
					this._rects = arg0._rects.slice(0);		
					this._rects.sort(this._compare);
				}
			}
			else
			{
				this._rects = [];
			}
		},
		clear: function()
		{
			this._rects = [];
			this._bounds = null;
		},
		_compare: function(a, b)
		{
			return a.x - b.x;
		},
		_debug: function()
		{
			var rects = this._rects,
				size = rects.length;
			console.log("Eco.RectsManager: ", "size: ", size);
			console.log("bounds: ", this.getBounds());
			for ( var i = 0 ; i < size ; i++ )
			{
				console.log(rects[i].toString());
			}
		},
		getBounds: function()
		{
			var bounds = this._bounds;
			if ( !bounds )
			{
				var rects = this._rects,
					len = rects.length,
					r;
				//console.log("getBounds:" , rects);
				if ( len )
				{
					bounds = rects[0].clone();
					for ( var i = 1 ; i < len ; i++ )
					{
						r = rects[i];
						if ( r.x < bounds.x )
						{
							bounds.width = bounds.x+bounds.width-r.x;
							bounds.x = r.x;
						}
						if ( r.y < bounds.y )
						{
							bounds.height = bounds.y+bounds.height-r.y;
							bounds.y = r.y;
						}
						if ( r.x+r.width > bounds.x+bounds.width )
						{
							bounds.width = r.x+r.width-bounds.x;
						}
						if ( r.y+r.height > bounds.y+bounds.height )
						{
							bounds.height = r.y+r.height-bounds.y;
						}
					}
					this._bounds = bounds;
				}
			}
			return bounds;
		},
		add: function(rect)
		{
			this._add(rect, 0, this._rects.length - 1);
		},
		/**
		 * Ensures that this collection contains the specified element
		 * l is the lower bound index for insertion r is upper
		 * bound index for insertion.
		 * @param rect The rectangle to add
		 * @param l the lowest possible index for a rect with
		 *          greater 'x' coord.
		 * @param r the highest possible index for a rect with
		 *          greater 'x' coord.
		 */
		_add: function(rect, l, r)
		{
			var idx = l,
				rects = this._rects,
				size = rects.length;
			while (l <= r)
			{
				idx = (l + r) >>> 1; //(l+r)/2;
				while ((rects[idx] == null) && (idx <r)) idx++;
				if (rects[idx] == null)
				{
					// All 'null' from center to r so skip them
					r = (l + r) >>> 1; //(l+r)/2;
					idx = (l + r) >>> 1;//(l+r)/2;
					if (l>r)
					{
						idx=l;
					}
					while ((rects[idx] == null) && (idx > l)) idx--;
					if (rects[idx] == null)
					{
						rects[idx] = rect;
						return;
					}
				}
				if (rect.x == rects[idx].x) break;
				if (rect.x <  rects[idx].x)
				{
					if (idx == 0) break;
					if ((rects[idx-1] != null) &&
						(rect.x >= rects[idx-1].x)) break;
					r = idx-1;
				}
				else
				{
					if (idx == size-1)  {idx++; break; }
					if ((rects[idx+1] != null) &&
						(rect.x <= rects[idx+1].x)) { idx++; break;}
					l = idx+1;
				}
			}
			if ( idx < size )
			{
				rects.splice(idx, 0, rect);
			}
			else
			{
				rects[idx] = rect;
			}
			this._bounds = null;
		},
		/**
		 * Ensures that this collection contains the specified element
		 * l is the lower bound index for insertion r is upper
		 * bound index for insertion.
		 * @param rect The rectangle to add
		 * @param l the lowest possible index for a rect with
		 *          greater 'x' coord.
		 * @param r the highest possible index for a rect with
		 *          greater 'x' coord.
		 */
		_addOrSkipWhenInclude: function(rect, l, r)
		{
			var idx = l,
				rects = this._rects,
				size = rects.length;
			while (l <= r)
			{
				idx = (l + r) >>> 1; //(l+r)/2;
				while ((rects[idx] == null) && (idx <r)) idx++;
				if (rects[idx] == null)
				{
					// All 'null' from center to r so skip them
					r = (l + r) >>> 1; //(l+r)/2;
					idx = (l + r) >>> 1;//(l+r)/2;
					if (l>r)
					{
						idx=l;
					}
					while ((rects[idx] == null) && (idx > l)) idx--;
					if (rects[idx] == null)
					{
						rects[idx] = rect;
						return;
					}
				}
				if (rect.x == rects[idx].x) break;
				if (rect.x <  rects[idx].x)
				{
					if (idx == 0) break;
					if ((rects[idx-1] != null) &&
						(rect.x >= rects[idx-1].x)) break;
					r = idx-1;
				}
				else
				{
					if (idx == size-1)  {idx++; break; }
					if ((rects[idx+1] != null) &&
						(rect.x <= rects[idx+1].x)) { idx++; break;}
					l = idx+1;
				}
			}
			if ( idx < size )
			{
				rects.splice(idx, 0, rect);
			}
			else
			{
				rects[idx] = rect;
			}
			this._bounds = null;
		},
		contains: function(rect)
		{
			var l=0,
				rects = this._rects,
				size = rects.length,
				r = size - 1,
				idx=0;
			while (l <= r)
			{
				idx = (l+r) >>> 1;
				if (rect.x == rects[idx].x) break;
				if (rect.x <  rects[idx].x)
				{
					if (idx == 0) break;
					if (rect.x >= rects[idx-1].x) break;
					r = idx-1;
				}
				else
				{
					if (idx == size-1)  {idx++; break; }
					if (rect.x <= rects[idx+1].x) { idx++; break;}
					l = idx+1;
				}
			}
			// Didn't find any rect with the same x value.
			if (rects[idx].x != rect.x) return false;

			// Search towards 0 from idx for rect that matches
			for (var i=idx; i>=0; i--)
			{
				if (rects[idx].equals(rect)) return true;
				if (rects[idx].x != rect.x)  break;
			}

			// Search towards size from idx for rect that matches
			for (var i=idx+1; i<size; i++)
			{
				if (rects[idx].equals(rect)) return true;
				if (rects[idx].x != rect.x)  break;
			}
			// No match...
			return false;
		},
		/**
		 * Returns true if this collection contains all of the elements in
		 * the specified collection.
		 */
		containsAll: function(arr)
		{
			if (!(arr instanceof Eco.RectsManager))
			{
				arr = new Eco.RectsManager(arr);
			}
			return this._containsAll(arr);
		},
		_containsAll: function(rectMgr)
		{
			var x, xChange = 0,
				rects = this._rects,
				size = rects.length,
				argRects = rectMgr._rects,
				argSize = argRects.length;
			for (var j=0, i=0; j < argSize; j++)
			{
				i=xChange;
				while(rects[i].x < argRects[j].x)
				{
					i++;
					if (i == size) return false;
				}
				xChange = i;
				x = rects[i].x;
				while (!argRects[j].equals(rects[i]))
				{
					i++;
					if (i == size) return false; // out of rects
					if (x != rects[i].x)
						return false; // out of the zone.
				}
			}
			return true;
		},
		/**
		* Removes a single instance of the specified Rectangle from this
		* collection, if it is present.
		* @param rect Rectangle to remove an matching instance of.
		*/
		remove: function(rect)
		{
			var l=0,
				rects = this._rects,
				size = rects.length,
				r = size - 1,
				arrayCopy = Eco.array.arrayCopy,
				idx=0;
			
			while (l <= r)
			{
				idx = (l+r) >>> 1;
				if (rect.x == rects[idx].x) break;
				if (rect.x <  rects[idx].x)
				{
					if (idx == 0) break;
					if (rect.x >= rects[idx-1].x) break;
					r = idx-1;
				}
				else
				{
					if (idx == size-1)  {idx++; break; }
					if (rect.x <= rects[idx+1].x) { idx++; break;}
					l = idx+1;
				}
			}
			// Didn't find any rect with the same x value.
			if (rects[idx].x != rect.x) return false;

			// Search towards 0 from idx for rect that matches
			for (var i=idx; i>=0; i--)
			{
				if (rects[idx].equals(rect))
				{
					arrayCopy(rects, idx + 1, rects, idx, size-idx);
					rects.length = size - 1;
					this._bounds = null;
					return true;
				}
				if (rects[idx].x != rect.x)  break;
			}

			// Search towards size from idx for rect that matches
			for (var i=idx+1; i<size; i++)
			{
				if (rects[idx].equals(rect))
				{
					arrayCopy(rects, idx + 1, rects, idx, size-idx);
					rects.length = size - 1;
					this._bounds = null;
					return true;
				}
				if (rects[idx].x != rect.x)  break;
			}
			// No match...
			return false;
		},
		removeAll: function(arr)
		{
			if (!(arr instanceof Eco.RectsManager))
			{
				arr = new Eco.RectsManager(arr);
			}
			return this._removeAll(arr);
		},
		_removeAll: function(rectsMgr)
		{
			var x, xChange = 0,
				rects = this._rects,
				size = rects.length,
				argRects = rectsMgr,_rects,
				argSize = argRects.length;
			var ret = false;

			for (var j=0, i=0; j<argSize; j++)
			{
				i=xChange;
				while ((rects[i] == null) ||
					(rects[i].x < argRects[j].x))
				{
					i++;
					if (i == size) break;
				}

				if (i == size) break;

				xChange = i;
				x = rects[i].x;
				while (true)
				{
					if (rects[i] == null)
					{
						i++;
						if (i == size) break; // out of rects
						continue;
					}
					if (argRects[j].equals(rects[i]))
					{
						rects[i] = null;
						ret = true;
					}
					i++;
					if (i == size)       break; // out of rects
					if (x != rects[i].x) break; // out of the zone.
				}
			}

			// Now we will go through collapsing the nulled entries.
			if (ret)
			{
				var j=0, i=0;
				while (i<size)
				{
					if (rects[i] != null)
					{
						rects[j++] = rects[i];
					}
					i++;
				}
				rects.length = j;
				this._bounds = null;
			}
			return ret;
		},
		retainAll: function(arr)
		{
			if (!(arr instanceof Eco.RectsManager))
			{
				arr = new Eco.RectsManager(arr);
			}
			return this._retainAll(arr);
		},
		_retainAll: function(rectsMgr)
		{
			var x, xChange = 0,
				rects = this._rects,
				size = rects.length,
				argRects = rectsMgr._rects,
				argSize = argRects.length;
			var ret = false;

			for (var j=0, i=0; j<size; j++)
			{
				i=xChange;
				while (argRects[i].x < rects[j].x)
				{
					i++;
					if (i == argSize) break;
				}
				if (i == argSize)
				{
					ret = true;
					// No more rects will match anything from rlm
					// so remove them from this RLM.
					for (var k=j; k<size; k++)
					{
						rects[k] = null;
					}
					size = j;
					rects.length = j;
					break;
				}
				xChange = i;
				x = argRects[i].x;
				while (true)
				{
					if (rects[j].equals(argRects[i])) break;
					i++;
					if ((i == argSize) ||
						(x != argRects[i].x))
					{
						// Out of zone or rects
						rects[j] = null;
						ret = true;
						break;
					}
				}
			}

			// Now we will go through collapsing the nulled entries.
			if (ret)
			{
				var j=0, i=0;
				while (i<size)
				{
					if (rects[i] != null)
					{
						rects[j++] = rects[i];
					}
					i++;
				}
				rects.length = j;
				this._bounds = null;
			}
			return ret;
		},
		/**
		* Adds the contents of <code>rlm</code> to this RectListManager.  No
		* collapsing of rectangles is done here the contents are simply
		* added (you should generally call 'mergeRects' some time after
		* this operation before using the contents of this
		* RectListManager.
		* @param rlm The RectListManager to add the contents of.  */
		append: function(rectsMgr)
		{
			var argRects = rectsMgr._rects,
				argSize = argRects.length;
			if (argSize == 0)
			{
				return;
			}

			var rects = this._rects,
				dst = rects,
				size = rects.length,
				arrayCopy = Eco.array.arrayCopy;
			
			if (size == 0)
			{
				this._rects = argRects.slice(0);
				this._bounds = null;
				return;
			}

			if (rects.length < (size+argSize))
			{
				dst = new Array(size+argSize);
			}

			var src1 = argRects,
				src1Sz = argSize,
				src1I = src1Sz-1;

			var src2 = rects,
				src2Sz = size,
				src2I  = src2Sz-1;

			var dstI = size+argSize-1;
			var x1 = src1[src1I].x;
			var x2 = src2[src2I].x;

			while (dstI >= 0)
			{
				if (x1 <= x2)
				{
					dst[dstI] = src2[src2I];
					if (src2I == 0)
					{
						arrayCopy(src1, 0, dst, 0, src1I+1);
						break;
					}
					src2I--;
					x2 = src2[src2I].x;
				}
				else
				{
					dst[dstI] = src1[src1I];
					if (src1I == 0)
					{
						arrayCopy(src2, 0, dst, 0, src2I+1);
						break;
					}
					src1I--;
					x1 = src1[src1I].x;
				}
				dstI--;
			}
			this._rects = dst;
			this._bounds = null;
		},
		mergeRects: function(overhead, lineOverhead)
		{
			var rects = this._rects,
				size = rects.length;

			if (size == 0) return;
			
			var r, cr;
			var cost1, cost2, cost3;
			var splits = new Array(4);

			for (var j, i=0; i<size; i++)
			{
				r = rects[i];
				if (r == null) continue;
				cost1 = (overhead +
					(r.height*lineOverhead) +
					(r.height*r.width));
				do
				{
					var maxX = r.x+r.width+overhead/r.height;
					for (j=i+1; j<size; j++)
					{
						cr = rects[j];
						if ((cr == null) || (cr == r)) continue;
						if (cr.x >= maxX)
						{
							// No more merges can happen.
							j = size;
							break;
						}
						cost2 = (overhead +
							(cr.height*lineOverhead) +
							(cr.height*cr.width));
						var mr = r.unite(cr);
						cost3 = (overhead +
							(mr.height*lineOverhead) +
							(mr.height*mr.width));
						if (cost3 <= cost1+cost2)
						{
							r = rects[i] = mr;
							rects[j] = null;
							cost1 = cost3;
							j=-1;
							break;
						}

						if (!r.intersects(cr)) continue;

						this.splitRect(cr, r, splits);
						var splitCost=0;
						var l=0;
						for (var k=0; k<4; k++)
						{
							if (splits[k] != null)
							{
								var sr = splits[k];
								// Collapse null entries in first three
								// (That share common 'x').
								if (k<3) splits[l++] = sr;
								splitCost += (overhead +
									(sr.height*lineOverhead) +
									(sr.height*sr.width));
							}
						}
						if (splitCost >= cost2) continue;

						// Insert the splits.
						if (l == 0)
						{
							// only third split may be left (no common 'x').
							rects[j] = null;
							if (splits[3] != null)
							{
								this._add(splits[3], j, size-1);
								size = rects.length;
							}
							continue;
						}

						rects[j] = splits[0];
						if (l > 1)
						{
							this.insertRects(splits, 1, j+1, l-1);
							size = rects.length;
						}
						if (splits[3] != null)
						{
							this._add(splits[3], j, size-1);
							size = rects.length;
						}
					}
					// if we merged it with another rect then
					// we need to check all the rects up to i again,
					// against the merged rect.
				}
				while (j != size);
			}

			// Now we will go through collapsing the nulled entries.
			var j=0, i=0;
			var area=0;
			while (i<size)
			{
				if (rects[i] != null)
				{
					r = rects[i];
					rects[j++] = r;
					area += overhead + (r.height*lineOverhead) +
							(r.height*r.width);
				}
				i++;
			}
			rects.length = j;
			this._bounds = null;
			r = this.getBounds();
			if (r == null) return;
			if (overhead + (r.height*lineOverhead) + (r.height*r.width) < area)
			{
				rects[0] = r;
				rects.length=1;
			}
		},
		subtract: function(rectsMgr, overhead, lineOverhead)
		{
			var r, sr;
			var cost;
			var jMin=0;
			var splits = new Array(4);
			var rects = this._rects,
				size = rects.length,
				argRects = rectsMgr._rects,
				argSize = argRects.length;

			for(var i=0; i<size; i++)
			{
				r = rects[i]; // Canidate rect...
				cost = (overhead +
						(r.height*lineOverhead) +
						(r.height*r.width));
			
				for (var j=jMin; j<argSize; j++)
				{
					sr = argRects[j]; // subtraction rect.

					// Check if the canidate rect starts after
					// the end of this rect in 'x' if so
					// go to the next one.
					if (sr.x+sr.width < r.x)
					{
						// If this was jMin then increment jMin (no
						// future canidate rect will intersect this rect).
						if (j == jMin) jMin++;
						continue;
					}

					// Check if the rest of the rects from rlm are past
					// the end of the canidate rect.  If so we are
					// done with this canidate rect.
					if (sr.x > r.x+r.width)
						break;

					// If they don't insersect then go to next sub rect.
					if (!r.intersects(sr))
						continue;

					// Now we know they intersect one another lets
					// figure out how...

					this.splitRect(r, sr, splits);

					var splitCost=0;
					var tmpR;
					for (var k=0; k<4; k++)
					{
						tmpR = splits[k];
						if (tmpR != null)
						{
							splitCost += (overhead +
								(tmpR.height*lineOverhead) +
								(tmpR.height*tmpR.width));
						}
					}

					if (splitCost >= cost)
					// This isn't ideal as depending on the order
					// Stuff is done in we might later kill some of
					// these rectangles (hence lowering the cost).
					// For this reason it is probably best of the
					// subtract list has been merged as this will help
					// reduce the instances where this will happen.
						continue;

					// Collapse null entries in first three elements
					// split 0, 1, 2 (entries that share a common 'x').
					var l = 0;
					for (var k=0; k<3; k++)
					{
						if (splits[k] != null)
							splits[l++] = splits[k];
					}

					// Fully covered (or only split 3 survived which we
					// will visit later) this canidate rect goes away.
					if (l==0)
					{
						rects[i].width = 0;
						// Insert the third split (if any) at the
						// proper place in rects list.
						if (splits[3] != null)
						{
							this._add(splits[3], i, size-1);
							size = rects.length;
						}
						break;
					}

					// Otherwise replace the canidate with the top of
					// the split, since it only shrunk it didn't grow,
					// we know that the previous subtract rects don't
					// intersect it.
					r = splits[0];
					rects[i] = r;
					cost = (overhead +
						(r.height*lineOverhead) +
						(r.height*r.width));

					// Add the remainder of the rects that
					// share 'r.x' (if any).  Possible
					// are split 1, and split 2.
					if (l > 1)
					{
						this.insertRects(splits, 1, i+1, l-1);
						size = rects.length;
					}

					// Insert the third split (if any) at the
					// proper place in rects list.
					if (splits[3] != null)
					{
						this._add(splits[3], i+l, size-1);
						size = rects.length;
					}
				}
			}

			// Now we will go through collapsing the nulled entries.
			var j=0, i=0;
			while (i<size)
			{
				if (rects[i].width == 0)
					rects[i] = null;
				else
					rects[j++] = rects[i];
				i++;
			}
			rects.length = j;
			this._bounds = null;
		},
		splitRect: function(r, sr, splits)
		{
			// We split the canidate rectrect into four parts.  In
			// many cases one or more of these will be empty.
			//
			//    +-------------------------------------+ ry0
			//    |                                     |
			//    |                                     |
			//    |          Split 0                    |
			//    |                                     |
			//    |                                     |
			// ------------+-----------------+--------------- sry0
			//    |        |                 |          |
			//    | Split2 |   subtracted    | Split 3  |
			//    |        |   rect          |          |
			//    |        |                 |          |
			// ------------+-----------------+--------------- sry1
			//    |       srx0              srx1        |
			//    |                                     |
			//    |          Split 1                    |
			//    |                                     |
			//    +-------------------------------------+ ry1
			//   rx0                                   rx1

			var rx0 = r.x;
			var rx1 = rx0+r.width-1;
			var ry0 = r.y;
			var ry1 = ry0+r.height-1;

			var srx0 = sr.x;
			var srx1 = srx0+sr.width-1;
			var sry0 = sr.y;
			var sry1 = sry0+sr.height-1;

			if ((ry0 < sry0) && (ry1 >= sry0))
			{
				splits[0] = new Eco.Rectangle(rx0, ry0, r.width, sry0-ry0);
				ry0 = sry0;
			}
			else
			{
				splits[0] = null;
			}

			if ((ry0 <= sry1) && (ry1 > sry1))
			{
				splits[1] = new Eco.Rectangle(rx0, sry1+1, r.width, ry1-sry1);
				ry1 = sry1;
			}
			else
			{
				splits[1] = null;
			}

			if ((rx0 < srx0) && (rx1 >= srx0))
			{
				splits[2] = new Eco.Rectangle(rx0, ry0, srx0-rx0, ry1-ry0+1);
			}
			else
			{
				splits[2] = null;
			}

			if ((rx0 <= srx1) && (rx1 > srx1))
			{
				splits[3]= new Eco.Rectangle(srx1+1, ry0, rx1-srx1, ry1-ry0+1);
			}
			else
			{
				splits[3] = null;
			}
		},
		insertRects: function(rects, srcPos, dstPos, len)
		{
			if (len == 0) return;
			// Make sure we have room.
			var innerRects = this._rects,
				size = innerRects.length;
			innerRects.length = (size+len);

			// Move everything after pos up...
			for (var i=size-1; i>=dstPos; i--)
				innerRects[i+len] = innerRects[i];

			// Put the new rects in.
			Eco.array.arrayCopy( rects, srcPos, innerRects, dstPos, len );
			//size += len;
		},
		Each: function(fn, scope)
		{
			var rects = this._rects,
				size = rects.length;

			scope = scope||this;
			for ( var i = 0 ; i < size ; i++ )
			{
				fn.call(scope, rects[i]);
			}
		}
	});
}