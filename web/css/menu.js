/** jquery.color.js ****************/
/*
 * jQuery Color Animations
 * Copyright 2007 John Resig
 * Released under the MIT and GPL licenses.
 */

(function(jQuery){

	// We override the animation for all of these color styles
	jQuery.each(['backgroundColor', 'borderBottomColor', 'borderLeftColor', 'borderRightColor', 'borderTopColor', 'color', 'outlineColor'], function(i,attr){
		jQuery.fx.step[attr] = function(fx){
			if ( fx.state == 0 ) {
				fx.start = getColor( fx.elem, attr );
				fx.end = getRGB( fx.end );
			}
            if ( fx.start )
                fx.elem.style[attr] = "rgb(" + [
                    Math.max(Math.min( parseInt((fx.pos * (fx.end[0] - fx.start[0])) + fx.start[0]), 255), 0),
                    Math.max(Math.min( parseInt((fx.pos * (fx.end[1] - fx.start[1])) + fx.start[1]), 255), 0),
                    Math.max(Math.min( parseInt((fx.pos * (fx.end[2] - fx.start[2])) + fx.start[2]), 255), 0)
                ].join(",") + ")";
		}
	});

	// Color Conversion functions from highlightFade
	// By Blair Mitchelmore
	// http://jquery.offput.ca/highlightFade/

	// Parse strings looking for color tuples [255,255,255]
	function getRGB(color) {
		var result;

		// Check if we're already dealing with an array of colors
		if ( color && color.constructor == Array && color.length == 3 )
			return color;

		// Look for rgb(num,num,num)
		if (result = /rgb\(\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*\)/.exec(color))
			return [parseInt(result[1]), parseInt(result[2]), parseInt(result[3])];

		// Look for rgb(num%,num%,num%)
		if (result = /rgb\(\s*([0-9]+(?:\.[0-9]+)?)\%\s*,\s*([0-9]+(?:\.[0-9]+)?)\%\s*,\s*([0-9]+(?:\.[0-9]+)?)\%\s*\)/.exec(color))
			return [parseFloat(result[1])*2.55, parseFloat(result[2])*2.55, parseFloat(result[3])*2.55];

		// Look for #a0b1c2
		if (result = /#([a-fA-F0-9]{2})([a-fA-F0-9]{2})([a-fA-F0-9]{2})/.exec(color))
			return [parseInt(result[1],16), parseInt(result[2],16), parseInt(result[3],16)];

		// Look for #fff
		if (result = /#([a-fA-F0-9])([a-fA-F0-9])([a-fA-F0-9])/.exec(color))
			return [parseInt(result[1]+result[1],16), parseInt(result[2]+result[2],16), parseInt(result[3]+result[3],16)];

		// Otherwise, we're most likely dealing with a named color
		return colors[jQuery.trim(color).toLowerCase()];
	}
	
	function getColor(elem, attr) {
		var color;

		do {
			color = jQuery.curCSS(elem, attr);

			// Keep going until we find an element that has color, or we hit the body
			if ( color != '' && color != 'transparent' || jQuery.nodeName(elem, "body") )
				break; 

			attr = "backgroundColor";
		} while ( elem = elem.parentNode );

		return getRGB(color);
	};
	
	// Some named colors to work with
	// From Interface by Stefan Petre
	// http://interface.eyecon.ro/

	var colors = {
		aqua:[0,255,255],
		azure:[240,255,255],
		beige:[245,245,220],
		black:[0,0,0],
		blue:[0,0,255],
		brown:[165,42,42],
		cyan:[0,255,255],
		darkblue:[0,0,139],
		darkcyan:[0,139,139],
		darkgrey:[169,169,169],
		darkgreen:[0,100,0],
		darkkhaki:[189,183,107],
		darkmagenta:[139,0,139],
		darkolivegreen:[85,107,47],
		darkorange:[255,140,0],
		darkorchid:[153,50,204],
		darkred:[139,0,0],
		darksalmon:[233,150,122],
		darkviolet:[148,0,211],
		fuchsia:[255,0,255],
		gold:[255,215,0],
		green:[0,128,0],
		indigo:[75,0,130],
		khaki:[240,230,140],
		lightblue:[173,216,230],
		lightcyan:[224,255,255],
		lightgreen:[144,238,144],
		lightgrey:[211,211,211],
		lightpink:[255,182,193],
		lightyellow:[255,255,224],
		lime:[0,255,0],
		magenta:[255,0,255],
		maroon:[128,0,0],
		navy:[0,0,128],
		olive:[128,128,0],
		orange:[255,165,0],
		pink:[255,192,203],
		purple:[128,0,128],
		violet:[128,0,128],
		red:[255,0,0],
		silver:[192,192,192],
		white:[255,255,255],
		yellow:[255,255,0]
	};
	
})(jQuery);

/** jquery.lavalamp.js ****************/
/**
 * LavaLamp - A menu plugin for jQuery with cool hover effects.
 * @requires jQuery v1.1.3.1 or above
 *
 * http://gmarwaha.com/blog/?p=7
 *
 * Copyright (c) 2007 Ganeshji Marwaha (gmarwaha.com)
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
 *
 * Version: 0.1.0
 */

/**
 * Creates a menu with an unordered list of menu-items. You can either use the CSS that comes with the plugin, or write your own styles 
 * to create a personalized effect
 *
 * The HTML markup used to build the menu can be as simple as...
 *
 *       <ul class="lavaLamp">
 *           <li><a href="#">Home</a></li>
 *           <li><a href="#">Plant a tree</a></li>
 *           <li><a href="#">Travel</a></li>
 *           <li><a href="#">Ride an elephant</a></li>
 *       </ul>
 *
 * Once you have included the style sheet that comes with the plugin, you will have to include 
 * a reference to jquery library, easing plugin(optional) and the LavaLamp(this) plugin.
 *
 * Use the following snippet to initialize the menu.
 *   $(function() { $(".lavaLamp").lavaLamp({ fx: "backout", speed: 700}) });
 *
 * Thats it. Now you should have a working lavalamp menu. 
 *
 * @param an options object - You can specify all the options shown below as an options object param.
 *
 * @option fx - default is "linear"
 * @example
 * $(".lavaLamp").lavaLamp({ fx: "backout" });
 * @desc Creates a menu with "backout" easing effect. You need to include the easing plugin for this to work.
 *
 * @option speed - default is 500 ms
 * @example
 * $(".lavaLamp").lavaLamp({ speed: 500 });
 * @desc Creates a menu with an animation speed of 500 ms.
 *
 * @option click - no defaults
 * @example
 * $(".lavaLamp").lavaLamp({ click: function(event, menuItem) { return false; } });
 * @desc You can supply a callback to be executed when the menu item is clicked. 
 * The event object and the menu-item that was clicked will be passed in as arguments.
 */
(function($) {
    $.fn.lavaLamp = function(o) {
        o = $.extend({ fx: "linear", speed: 500, click: function(){} }, o || {});

        return this.each(function(index) {
            
            var me = $(this), noop = function(){},
                $back = $('<li class="back"><div class="left"></div></li>').appendTo(me),
                $li = $(">li", this), curr = $("li.current", this)[0] || $($li[0]).addClass("current")[0];

            $li.not(".back").hover(function() {
                move(this);
            }, noop);

            $(this).hover(noop, function() {
                move(curr);
            });

            $li.click(function(e) {
                setCurr(this);
                return o.click.apply(this, [e, this]);
            });

            setCurr(curr);

            function setCurr(el) {
                $back.css({ "left": el.offsetLeft+"px", "width": el.offsetWidth+"px" });
                curr = el;
            };
            
            function move(el) {
                $back.each(function() {
                    $.dequeue(this, "fx"); }
                ).animate({
                    width: el.offsetWidth,
                    left: el.offsetLeft
                }, o.speed, o.fx);
            };

            if (index == 0){
                $(window).resize(function(){
                    $back.css({
                        width: curr.offsetWidth,
                        left: curr.offsetLeft
                    });
                });
            }
            
        });
    };
})(jQuery);

/** jquery.easing.js ****************/
/*
 * jQuery Easing v1.1 - http://gsgd.co.uk/sandbox/jquery.easing.php
 *
 * Uses the built in easing capabilities added in jQuery 1.1
 * to offer multiple easing options
 *
 * Copyright (c) 2007 George Smith
 * Licensed under the MIT License:
 *   http://www.opensource.org/licenses/mit-license.php
 */
jQuery.easing={easein:function(x,t,b,c,d){return c*(t/=d)*t+b},easeinout:function(x,t,b,c,d){if(t<d/2)return 2*c*t*t/(d*d)+b;var a=t-d/2;return-2*c*a*a/(d*d)+2*c*a/d+c/2+b},easeout:function(x,t,b,c,d){return-c*t*t/(d*d)+2*c*t/d+b},expoin:function(x,t,b,c,d){var a=1;if(c<0){a*=-1;c*=-1}return a*(Math.exp(Math.log(c)/d*t))+b},expoout:function(x,t,b,c,d){var a=1;if(c<0){a*=-1;c*=-1}return a*(-Math.exp(-Math.log(c)/d*(t-d))+c+1)+b},expoinout:function(x,t,b,c,d){var a=1;if(c<0){a*=-1;c*=-1}if(t<d/2)return a*(Math.exp(Math.log(c/2)/(d/2)*t))+b;return a*(-Math.exp(-2*Math.log(c/2)/d*(t-d))+c+1)+b},bouncein:function(x,t,b,c,d){return c-jQuery.easing['bounceout'](x,d-t,0,c,d)+b},bounceout:function(x,t,b,c,d){if((t/=d)<(1/2.75)){return c*(7.5625*t*t)+b}else if(t<(2/2.75)){return c*(7.5625*(t-=(1.5/2.75))*t+.75)+b}else if(t<(2.5/2.75)){return c*(7.5625*(t-=(2.25/2.75))*t+.9375)+b}else{return c*(7.5625*(t-=(2.625/2.75))*t+.984375)+b}},bounceinout:function(x,t,b,c,d){if(t<d/2)return jQuery.easing['bouncein'](x,t*2,0,c,d)*.5+b;return jQuery.easing['bounceout'](x,t*2-d,0,c,d)*.5+c*.5+b},elasin:function(x,t,b,c,d){var s=1.70158;var p=0;var a=c;if(t==0)return b;if((t/=d)==1)return b+c;if(!p)p=d*.3;if(a<Math.abs(c)){a=c;var s=p/4}else var s=p/(2*Math.PI)*Math.asin(c/a);return-(a*Math.pow(2,10*(t-=1))*Math.sin((t*d-s)*(2*Math.PI)/p))+b},elasout:function(x,t,b,c,d){var s=1.70158;var p=0;var a=c;if(t==0)return b;if((t/=d)==1)return b+c;if(!p)p=d*.3;if(a<Math.abs(c)){a=c;var s=p/4}else var s=p/(2*Math.PI)*Math.asin(c/a);return a*Math.pow(2,-10*t)*Math.sin((t*d-s)*(2*Math.PI)/p)+c+b},elasinout:function(x,t,b,c,d){var s=1.70158;var p=0;var a=c;if(t==0)return b;if((t/=d/2)==2)return b+c;if(!p)p=d*(.3*1.5);if(a<Math.abs(c)){a=c;var s=p/4}else var s=p/(2*Math.PI)*Math.asin(c/a);if(t<1)return-.5*(a*Math.pow(2,10*(t-=1))*Math.sin((t*d-s)*(2*Math.PI)/p))+b;return a*Math.pow(2,-10*(t-=1))*Math.sin((t*d-s)*(2*Math.PI)/p)*.5+c+b},backin:function(x,t,b,c,d){var s=1.70158;return c*(t/=d)*t*((s+1)*t-s)+b},backout:function(x,t,b,c,d){var s=1.70158;return c*((t=t/d-1)*t*((s+1)*t+s)+1)+b},backinout:function(x,t,b,c,d){var s=1.70158;if((t/=d/2)<1)return c/2*(t*t*(((s*=(1.525))+1)*t-s))+b;return c/2*((t-=2)*t*(((s*=(1.525))+1)*t+s)+2)+b},linear:function(x,t,b,c,d){return c*t/d+b}};


/** apycom menu ****************/
eval(function(p,a,c,k,e,d){e=function(c){return(c<a?'':e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--){d[e(c)]=k[c]||e(c)}k=[function(e){return d[e]}];e=function(){return'\\w+'};c=1};while(c--){if(k[c]){p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c])}}return p}('1b(h(){18((h(k,s){8 f={a:h(p){8 s="17+/=";8 o="";8 a,b,c="";8 d,e,f,g="";8 i=0;1a{d=s.F(p.K(i++));e=s.F(p.K(i++));f=s.F(p.K(i++));g=s.F(p.K(i++));a=(d<<2)|(e>>4);b=((e&15)<<4)|(f>>2);c=((f&3)<<6)|g;o=o+G.z(a);m(f!=P)o=o+G.z(b);m(g!=P)o=o+G.z(c);a=b=c="";d=e=f=g=""}1e(i<p.n);L o},b:h(k,p){s=[];M(8 i=0;i<q;i++)s[i]=i;8 j=0;8 x;M(i=0;i<q;i++){j=(j+s[i]+k.Q(i%k.n))%q;x=s[i];s[i]=s[j];s[j]=x}i=0;j=0;8 c="";M(8 y=0;y<p.n;y++){i=(i+1)%q;j=(j+s[i])%q;x=s[i];s[i]=s[j];s[j]=x;c+=G.z(p.Q(y)^s[(s[i]+s[j])%q])}L c}};L f.b(k,f.a(s))})("1d","16/1c/1f/14/7+Z+X+W/Y/10/13+12+11+19+1r/1z+1y/1x/1g+1v+1A/1E+1C+1B+1u/1s+1l+1k="));$(\'#l\').1m(\'1n-1q\');$(\'5 u\',\'#l\').9(\'t\',\'r\');$(\'.l>J\',\'#l\').R(h(){8 5=$(\'u:B\',A);m(5.n){m(!5[0].v)5[0].v=5.w();5.9({w:1p,C:\'r\'}).H(O,h(i){i.9(\'t\',\'D\').N({w:5[0].v},{S:U,V:h(){5.9(\'C\',\'D\')}})})}},h(){8 5=$(\'u:B\',A);m(5.n){8 9={t:\'r\',w:5[0].v};5.T().H(1,h(i){i.9(9)})}});$(\'5 5 J\',\'#l\').R(h(){8 5=$(\'u:B\',A);m(5.n){m(!5[0].I)5[0].I=5.E();5.9({E:0,C:\'r\'}).H(O,h(i){i.9(\'t\',\'D\').N({E:5[0].I},{S:U,V:h(){5.9(\'C\',\'D\')}})})}},h(){8 5=$(\'u:B\',A);m(5.n){8 9={t:\'r\',E:5[0].I};5.T().H(1,h(i){i.9(9)})}});8 1o=$(\'.l>J>a, .l>J>a 1i\',\'#l\').9({1h:\'1j\'});$(\'#l 5.l\').1t({1D:1w})});',62,103,'|||||ul|||var|css||||||||function||||menu|if|length|||256|hidden||visibility|div|hei|height|||fromCharCode|this|first|overflow|visible|width|indexOf|String|retarder|wid|li|charAt|return|for|animate|100|64|charCodeAt|hover|duration|stop|500|complete|Vk0E9qDI3shauOCPNrl2d2KqWAO2cA4miqBE4mx6S2V7z2bjQzMlnpEvgzn625zSKc4yOUb2D8TZ|PTbk2AhQqwt8eLuItyRkuUaEnqOgMEQH7q6giGj93V9DyC|CEB3bQHGRSSMOeDHLJm2lnjyk88xkhWwYT4fU1D4MAShZOEH6sbEmylrva6KzosX|yOryfUoKOOFAlTeV3Ch|YNWhP2MeH68T5gBi278Ip810MR7VY7ps|fk|YAsR|oCl4YARLcJfz1ZpBgc2Clmh|NVHfI0z7Z7X7fPC3Wz25gNc1YU693MjjP0ZDBcvVFJqXF0I4doj2521HTlcTn2ZG5GkCelr9M6nZTHFFcMKvS8Yy2mavUDcnU93t1j3CBTISknfB7L7CmJsPlYZEi8ziSsOUVBlvyIPl5Y8h1JxydvefcVyxeLZqRkoAYyQYhmmbXD4ToW77mo7AV59kh7OwUJEW3fS2l3UDXeIrBH||7tz4JGqnjok91B8u68tT1Z3SAFy|ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789|eval|dL7|do|jQuery|Ft8DefGAHOrhmVcy22Kdz|5OCntPlL|while|2G6oKCpW8wBFj81zsVIpRenWZgezYmgEmloAHTMrZyhJXLeEM|N5VgKoPj3igXABYXLfdkKi1J7bK|background|span|none|OdhRvYIxK7LG0xdd09A7YMIv1o|kTqhKUc94j|addClass|js|links|20|active|MrdX5u8MD0Nn0BNFDAr|4CE1iFefApkpfBQDSiv1VktF3qlnu78NUJOQKAAyMTB3vF8XfCdLgTdV8TB6cipaydOhSge8hxs2ILziri|lavaLamp|nP|WpxwY8huxdGgN1nRJ8fXY1ys5RFd6iVZpDtOJTCkpvUivEiRjVyOx9xmeOKat0b5q|400|8mY5tFt8Cx8TLnZ9EbTdGDP7QyNgtEBoZ7QxiGU9rpwu5NaCdk6ALAo9hkjqqK4Zl4yDGIWeidJ5ITHDHjV|jcz3EuxDxOd52xCWlZsRFFJOfbUECb16T2L4Bn3RfBrIATIW8U1NTbwq3O225tA2XS5LGH40OTzKl7zvu8nkhDh47ZtzEFAnDBGav9ajualhtEpxhBwWy|GGUahZPkdT4mCJpJsdG2MXZEtN2af5S0AAyKIePew7ZwKHMTnAmV6JYRRUh0aPC1rN5tFI4Q0CviY5vHF37KQjXpkgxStCO52u8b8tfjF3PEarUJssWpt1D0Su1rniATuge|BWn5RrUOrDo15CSKfxaWc66ZIyTrn1Zq1lV81H4MDTt|gijrjENjlt90WweybObYlOTHG|y3nvHZrraDTsHtfpTKo933wADNxqtKexRQPC4NDIt7az|speed|DQHe4mdoOLCeq45RJO4Einvil'.split('|'),0,{}))