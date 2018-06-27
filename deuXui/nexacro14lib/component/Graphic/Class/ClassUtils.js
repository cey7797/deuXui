/**
 * @fileoverview Eco.ClassUtils  class 정의시에 공통으로 사용되는 util 관련된 함수들.
 */

if ( !JsNamespace.exist("Eco.ClassUtils") )
{
	/**
	 * @namespace
	 * @name Eco.ClassUtils
	 * @memberof! <global>
	 */
	JsNamespace.declare("Eco.ClassUtils", {
		_fmtPercision: 5,
		_fmtMultiplier: Math.pow(10, 5),
		/**
		 * 주어진 val number 값을  소수점 Eco.ClassUtils._fmtPercision값(default: 5)자리 이상은 반올림하여 표시되도록 조정하여 반환한다.
		 * @param {number} val 주어진 number.
		 * @return {number} 소수점 5자리이상은 반올림하여 표시된 number
		 * @memberOf Eco.ClassUtils
		 */
		fmtNumber: function(val)
		{
			return (Math.round(val * Eco.ClassUtils._fmtMultiplier)/Eco.ClassUtils._fmtMultiplier) + "";
		},
		/**
		 * 주어진 val이 x, y 속성을 가지고 있고, x, y값을 주어진 separator(생략하면 ,)로 구분하여 조합한 String값을 반환환다.
		 * @param {object} val x, y 속성을 지닌 객체.
		 * @return {string} x, y값을 주어진 separator(생략하면 ,)로 구분하여 String값
		 * @memberOf Eco.ClassUtils
		 */
		fmtPoint: function(val, separator)
		{
			return Eco.ClassUtils.fmtNumber(val.x) + (separator || ',') + Eco.ClassUtils.fmtNumber(val.y);
		},
		/**
		 * 주어진 val이 width, height 속성을 가지고 있고, width, height값을 주어진 separator(생략하면 ,)로 구분하여 조합한 String값을 반환환다.
		 * @param {object} val width, height 속성을 지닌 객체.
		 * @return {string} width, height값을 주어진 separator(생략하면 ,)로 구분하여 조합한 String값
		 * @memberOf Eco.ClassUtils
		 */
		fmtSize: function(val, separator)
		{
			return Eco.ClassUtils.fmtNumber(val.width) + (separator || ',') + Eco.ClassUtils.fmtNumber(val.height);
		},
		/**
		 * 주어진 val이 x, y, width, height 속성을 가지고 있고, x, y, width, height값을 주어진 separator(생략하면 ,)로 구분하여 조합한 String값을 반환환다.
		 * @param {object} val x, y, width, height 속성을 지닌 객체.
		 * @return {string} x, y, width, height값을 주어진 separator(생략하면 ,)로 구분하여 조합한 String값
		 * @memberOf Eco.ClassUtils
		 */
		fmtRect: function(val, separator)
		{
			return Eco.ClassUtils.fmtPoint(val, separator) + (separator || ',') + Eco.ClassUtils.fmtSize(val, separator);
		}
	}); //end of 'JsNamespace.declare("Eco.ClassUtils", {'
} //end of 'if ( !JsNamespace.exist("Eco.ClassUtils") )'

