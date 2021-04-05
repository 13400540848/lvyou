/*! sapar - v0.1.0 - 2015-01-11 */(function ($, undefined) {
    // prevent duplicate loading
    // this is only a problem because we proxy existing functions
    // and we don't want to double proxy them
    // 如果有值在进行赋值，否则赋值为空，确保不是undefined
    $.ued = $.ued || {};

    if (!window.JSON) {
        window.JSON = {};
    }
    (function () {
        "use strict";
        function f(n) {
            // Format integers to have at least two digits.
            return n < 10 ? '0' + n : n;
        }

        if (typeof Date.prototype.toJSON !== 'function') {

            Date.prototype.toJSON = function (key) {

                return isFinite(this.valueOf()) ?
                    this.getUTCFullYear() + '-' +
                    f(this.getUTCMonth() + 1) + '-' +
                    f(this.getUTCDate()) + 'T' +
                    f(this.getUTCHours()) + ':' +
                    f(this.getUTCMinutes()) + ':' +
                    f(this.getUTCSeconds()) + 'Z' : null;
            };

            String.prototype.toJSON =
                Number.prototype.toJSON =
                Boolean.prototype.toJSON = function (key) {
                    return this.valueOf();
                };
        }

        var cx = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
            escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
            gap,
            indent,
            meta = {    // table of character substitutions
                '\b': '\\b',
                '\t': '\\t',
                '\n': '\\n',
                '\f': '\\f',
                '\r': '\\r',
                '"': '\\"',
                '\\': '\\\\'
            },
            rep;


        function quote(string) {

            // If the string contains no control characters, no quote characters, and no
            // backslash characters, then we can safely slap some quotes around it.
            // Otherwise we must also replace the offending characters with safe escape
            // sequences.

            escapable.lastIndex = 0;
            return escapable.test(string) ? '"' + string.replace(escapable, function (a) {
                var c = meta[a];
                return typeof c === 'string' ? c :
                    '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
            }) + '"' : '"' + string + '"';
        }


        function str(key, holder) {

            // Produce a string from holder[key].

            var i,          // The loop counter.
                k,          // The member key.
                v,          // The member value.
                length,
                mind = gap,
                partial,
                value = holder[key];

            // If the value has a toJSON method, call it to obtain a replacement value.

            if (value && typeof value === 'object' &&
                typeof value.toJSON === 'function') {
                value = value.toJSON(key);
            }

            // If we were called with a replacer function, then call the replacer to
            // obtain a replacement value.

            if (typeof rep === 'function') {
                value = rep.call(holder, key, value);
            }

            // What happens next depends on the value's type.

            switch (typeof value) {
                case 'string':
                    return quote(value);

                case 'number':

                    // JSON numbers must be finite. Encode non-finite numbers as null.

                    return isFinite(value) ? String(value) : 'null';

                case 'boolean':
                case 'null':

                    // If the value is a boolean or null, convert it to a string. Note:
                    // typeof null does not produce 'null'. The case is included here in
                    // the remote chance that this gets fixed someday.

                    return String(value);

                // If the type is 'object', we might be dealing with an object or an array or
                // null.

                case 'object':

                    // Due to a specification blunder in ECMAScript, typeof null is 'object',
                    // so watch out for that case.

                    if (!value) {
                        return 'null';
                    }

                    // Make an array to hold the partial results of stringifying this object value.

                    gap += indent;
                    partial = [];

                    // Is the value an array?

                    if (Object.prototype.toString.apply(value) === '[object Array]') {

                        // The value is an array. Stringify every element. Use null as a placeholder
                        // for non-JSON values.

                        length = value.length;
                        for (i = 0; i < length; i += 1) {
                            partial[i] = str(i, value) || 'null';
                        }

                        // Join all of the elements together, separated with commas, and wrap them in
                        // brackets.

                        v = partial.length === 0 ? '[]' : gap ?
                            '[\n' + gap + partial.join(',\n' + gap) + '\n' + mind + ']' :
                            '[' + partial.join(',') + ']';
                        gap = mind;
                        return v;
                    }

                    // If the replacer is an array, use it to select the members to be stringified.

                    if (rep && typeof rep === 'object') {
                        length = rep.length;
                        for (i = 0; i < length; i += 1) {
                            if (typeof rep[i] === 'string') {
                                k = rep[i];
                                v = str(k, value);
                                if (v) {
                                    partial.push(quote(k) + (gap ? ': ' : ':') + v);
                                }
                            }
                        }
                    } else {

                        // Otherwise, iterate through all of the keys in the object.

                        for (k in value) {
                            if (Object.prototype.hasOwnProperty.call(value, k)) {
                                v = str(k, value);
                                if (v) {
                                    partial.push(quote(k) + (gap ? ': ' : ':') + v);
                                }
                            }
                        }
                    }

                    // Join all of the member texts together, separated with commas,
                    // and wrap them in braces.

                    v = partial.length === 0 ? '{}' : gap ?
                        '{\n' + gap + partial.join(',\n' + gap) + '\n' + mind + '}' :
                        '{' + partial.join(',') + '}';
                    gap = mind;
                    return v;
            }
        }

        // If the JSON object does not yet have a stringify method, give it one.

        if (typeof window.JSON.stringify !== 'function') {
            window.JSON.stringify = function (value, replacer, space) {

                // The stringify method takes a value and an optional replacer, and an optional
                // space parameter, and returns a JSON text. The replacer can be a function
                // that can replace values, or an array of strings that will select the keys.
                // A default replacer method can be provided. Use of the space parameter can
                // produce text that is more easily readable.

                var i;
                gap = '';
                indent = '';

                // If the space parameter is a number, make an indent string containing that
                // many spaces.

                if (typeof space === 'number') {
                    for (i = 0; i < space; i += 1) {
                        indent += ' ';
                    }

                    // If the space parameter is a string, it will be used as the indent string.

                } else if (typeof space === 'string') {
                    indent = space;
                }

                // If there is a replacer, it must be a function or an array.
                // Otherwise, throw an error.

                rep = replacer;
                if (replacer && typeof replacer !== 'function' &&
                    (typeof replacer !== 'object' ||
                        typeof replacer.length !== 'number')) {
                    throw new Error('JSON.stringify');
                }

                // Make a fake root object containing our value under the key of ''.
                // Return the result of stringifying the value.

                return str('', { '': value });
            };
        }


        // If the JSON object does not yet have a parse method, give it one.

        if (typeof window.JSON.parse !== 'function') {
            window.JSON.parse = function (text, reviver) {

                // The parse method takes a text and an optional reviver function, and returns
                // a JavaScript value if the text is a valid JSON text.

                var j;

                function walk(holder, key) {

                    // The walk method is used to recursively walk the resulting structure so
                    // that modifications can be made.

                    var k, v, value = holder[key];
                    if (value && typeof value === 'object') {
                        for (k in value) {
                            if (Object.prototype.hasOwnProperty.call(value, k)) {
                                v = walk(value, k);
                                if (v !== undefined) {
                                    value[k] = v;
                                } else {
                                    delete value[k];
                                }
                            }
                        }
                    }
                    return reviver.call(holder, key, value);
                }


                // Parsing happens in four stages. In the first stage, we replace certain
                // Unicode characters with escape sequences. JavaScript handles many characters
                // incorrectly, either silently deleting them, or treating them as line endings.

                text = String(text);
                cx.lastIndex = 0;
                if (cx.test(text)) {
                    text = text.replace(cx, function (a) {
                        return '\\u' +
                            ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
                    });
                }

                // In the second stage, we run the text against regular expressions that look
                // for non-JSON patterns. We are especially concerned with '()' and 'new'
                // because they can cause invocation, and '=' because it can cause mutation.
                // But just to be safe, we want to reject all unexpected forms.

                // We split the second stage into 4 regexp operations in order to work around
                // crippling inefficiencies in IE's and Safari's regexp engines. First we
                // replace the JSON backslash pairs with '@' (a non-JSON character). Second, we
                // replace all simple value tokens with ']' characters. Third, we delete all
                // open brackets that follow a colon or comma or that begin the text. Finally,
                // we look to see that the remaining characters are only whitespace or ']' or
                // ',' or ':' or '{' or '}'. If that is so, then the text is safe for eval.

                if (/^[\],:{}\s]*$/
                    .test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@')
                        .replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']')
                        .replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {

                    // In the third stage we use the eval function to compile the text into a
                    // JavaScript structure. The '{' operator is subject to a syntactic ambiguity
                    // in JavaScript: it can begin a block or an object literal. We wrap the text
                    // in parens to eliminate the ambiguity.

                    j = eval('(' + text + ')');

                    // In the optional fourth stage, we recursively walk the new structure, passing
                    // each name/value pair to a reviver function for possible transformation.

                    return typeof reviver === 'function' ?
                        walk({ '': j }, '') : j;
                }

                // If the text is not JSON parseable, then a SyntaxError is thrown.

                throw new SyntaxError('JSON.parse');
            };
        }
    }());

    if ($.ued.version) {
        return;
    }

    $.extend($.ued, {
        version: "2.0",
        keyCode: {
            TAB: 9,
            ENTER: 13,
            ESCAPE: 27,
            SPACE: 32,
            LEFT: 37,
            UP: 38,
            RIGHT: 39,
            DOWN: 40
        },
        lang: {
            // 获取属性的国际化字符串，如果组件的options中已经设置这个值就直接使用，否则从$.ued.lang[comp]中获取
            _get: function (options, comp, attr) {
                return options[attr] ? options[attr] : $.ued.lang[comp][attr];
            }
        }
    });
    // plugins
    $.fn.extend({
        propAttr: $.fn.prop || $.fn.attr,
        _oldFocus: $.fn.focus, //为避免与jQuery ui冲突导致死循环，这里不要取名为'_focus'
        //设置元素焦点（delay：延迟时间）
        focus: function (delay, fn) {
            return typeof delay === "number" ? this.each(function () {
                var elem = this;
                setTimeout(function () {
                    $(elem).focus();
                    if (fn) {
                        fn.call(elem);
                    }
                }, delay);
            }) : this._oldFocus.apply(this, arguments);
        },
        //获取设置滚动属性的 父元素
        scrollParent: function () {
            var scrollParent;
            if (($.browser.msie && (/(static|relative)/).test(this.css('position'))) || (/absolute/).test(this.css('position'))) {
                scrollParent = this.parents().filter(function () {
                    return (/(relative|absolute|fixed)/).test($.curCSS(this, 'position', 1)) && (/(auto|scroll)/).test($.curCSS(this, 'overflow', 1) + $.curCSS(this, 'overflow-y', 1) + $.curCSS(this, 'overflow-x', 1));
                }).eq(0);
            } else {
                scrollParent = this.parents().filter(function () {
                    return (/(auto|scroll)/).test($.curCSS(this, 'overflow', 1) + $.curCSS(this, 'overflow-y', 1) + $.curCSS(this, 'overflow-x', 1));
                }).eq(0);
            }
            return (/fixed/).test(this.css('position')) || !scrollParent.length ? $(document) : scrollParent;
        },
        //设置或获取元素的垂直坐标
        zIndex: function (zIndex) {
            if (zIndex !== undefined) {
                return this.css("zIndex", zIndex);
            }
            if (this.length) {
                var elem = $(this[0]), position, value;
                while (elem.length && elem[0] !== document) {
                    // Ignore z-index if position is set to a value where z-index is ignored by the browser
                    // This makes behavior of this function consistent across browsers
                    // WebKit always returns auto if the element is positioned
                    position = elem.css("position");
                    if (position === "absolute" || position === "relative" || position === "fixed") {
                        // IE returns 0 when zIndex is not specified
                        // other browsers return a string
                        // we ignore the case of nested elements with an explicit value of 0
                        // <div style="z-index: -10;"><div style="z-index: 0;"></div></div>
                        value = parseInt(elem.css("zIndex"), 10);
                        if (!isNaN(value) && value !== 0) {
                            return value;
                        }
                    }
                    elem = elem.parent();
                }
            }
            return 0;
        },
        //设置元素不支持被选择
        disableSelection: function () {
            return this.bind(($.support.selectstart ? "selectstart" : "mousedown") + ".om-disableSelection", function (event) {
                event.preventDefault();
            });
        },
        //设置元素支持被选择
        enableSelection: function () {
            return this.unbind(".om-disableSelection");
        }
    });
    $.fn.extend({
		/**
		  * add by ynshen
		  * 扩展show，支持参数visibility（本质是通过样式控制visibility）
		  */
        _oldShow: $.fn.show,
        show: function (type) {
            if (type != "visibility") {
                return this._oldShow.apply(this, arguments);
            } else {
                return this.css("visibility", "visible");
            }
        },
		/**
		  * add by ynshen
		  * 扩展hide，支持参数visibility（本质是通过样式控制visibility）
		  */
        _oldHide: $.fn.hide,
        hide: function (type) {
            if (type != "visibility") {
                return this._oldHide.apply(this, arguments);
            } else {
                return this.css("visibility", "hidden");
            }
        }
    });
    $.extend({
		/**
		  * add by xzjiang
		  * 判断字符串数（中文占俩位）
		  * @param {Object} message
		  */
        getStrLength: function (message) {
            return message.replace(/([\u4E00-\u9FA5\uf900-\ufa2d])/g, 'aa').length;
        }
    });

	/**
	  * add by ynshen
	  * 判断浏览器是否支持console对象
	  * 如果不支持（当为IE并且未打开调试窗口时，初始化console对象，并将console.log方法指向window.alert方法）
	  */
    window.property = {};
    if (!window.console) {
        window.console = {};
        window.console.log = function (data) {
            window.alert(data)
        }
        //add by rbai(增加time和timeEnd性能测试函数) 
        if (!window.console.time) {
            window.console.time = function (name) {
                window.property[name] = new Date().getTime();
            }
            window.console.timeEnd = function (name) {
                var tempTime = new Date().getTime()
                window.alert(name + '===' + (tempTime - window.property[name]) + 'ms');
            }
        }
    }

    //增加debug模式
    if (!window.DebugModel) {
        window.DebugModel = {}, window.deBugObj = {};
        window.DebugModel.start = function (name) {
            window.deBugObj[name] = +new Date();
        }
        window.DebugModel.end = function (name) {
            var tempTime = +new Date();
            var tempConsole = name + '完成花费了' + (tempTime - window.deBugObj[name]) + 'ms';
            DebugModel._printLog(tempConsole);
        }
        window.DebugModel._printLog = function (log) {
            var $debugConsole = $('.ued-debugConsole', $('body'));
            if (!$debugConsole.size()) {//如果存在debug控制台元素
                $('body').append('<div class="ued-debugConsole" style="position:absolute;top:10px;left:10px;background:#F9F9F9;border:1px solid #CCC;padding:5px 10px 20px 10px;z-index:99999;color:#666;box-shadow:1px 1px 1px white inset;text-shadow:1px 1px 1px #FFF;">' +
                    '<h6 style="border-bottom:1px dashed #999;margin-bottom:5px;">UED DEBUG 控制台</h6>' +
                    '<div class="ued-debugContent uew-scroll-webkit" style="max-height:300px;overflow:auto;"></div>' +
                    '<a href="javascript:;" class="ued-debugClose" style="position:absolute;bottom:2px;right:5px;">关闭</a>' +
                    '</di>');
                $debugContent = $('.ued-debugContent', $('body'));
                DebugModel._initCloseOrShow();
            }
            $('.ued-debugConsole', $('body')).show();
            $debugContent.append('<p>' + log + '</p>');
        }

        window.DebugModel._initCloseOrShow = function ($debugConsole) {
            $('.ued-debugClose', $('body')).click(function (e) {
                $debugContent.empty();
                $('.ued-debugConsole', $('body')).hide();
            });

        }
    }
	/**
	  * add by ynshen
	  * 对象深度复制方法
	  */
    window.DeepCopy = function (obj) {
        var copyObj = new Object();
        for (prop in obj) {
            if (obj[prop] != null && obj[prop].constructor == Object) {
                copyObj[prop] = window.DeepCopy(obj[prop]);
            }
            else {
                copyObj[prop] = obj[prop]
            }
        }
        return copyObj;
    }
	/**
	  * add by ynshen
	  * 扩展Array对象，增加删除数组中指定元素方法
	  */
    Array.prototype.remove = function (element) {
        var indexArr = [];
        var outArr = [];
        //找到出现该元素的index并存入数组indexArr
        for (var i = 0; i < this.length; i++) {
            if (this[i] == element) {
                indexArr.push(i);
            }
        }
        if (indexArr.length > 0) {
            for (var j = 0; j < indexArr.length; j++) {
                if (j == 0) {//切分头部
                    outArr = outArr.concat(this.slice(0, indexArr[j]));
                } else {//切分中段
                    outArr = outArr.concat(this.slice(indexArr[j - 1] + 1, indexArr[j]));
                }
            }
            //切分尾端
            outArr = outArr.concat(this.slice(indexArr[j - 1] + 1, this.length));
            return outArr;
        } else {
            throw new Error(element + "can't be found in " + this);
        }
    }

    // 扩展innerWidth、innerHeight、outerWidth和outerHeight方法，如果不传参则获取值，如果传参则设置计算后的宽高。
    $.each(["Width", "Height"], function (i, name) {
        var side = name === "Width" ? ["Left", "Right"] : ["Top", "Bottom"], type = name.toLowerCase(), orig = {
            innerWidth: $.fn.innerWidth,
            innerHeight: $.fn.innerHeight,
            outerWidth: $.fn.outerWidth,
            outerHeight: $.fn.outerHeight
        };

        function reduce(elem, size, border, margin) {
            $.each(side, function () {
                size -= parseFloat($.curCSS(elem, "padding" + this, true)) || 0;
                if (border) {
                    size -= parseFloat($.curCSS(elem, "border" + this + "Width", true)) || 0;
                }
                if (margin) {
                    size -= parseFloat($.curCSS(elem, "margin" + this, true)) || 0;
                }
            });
            return size;
        }

        $.fn["inner" + name] = function (size) {
            if (size === undefined) {
                // 返回innerWidth/innerHeight
                return orig["inner" + name].call(this);
            }
            return this.each(function () {
                // 设置宽度/高度 = (size - padding)
                $(this).css(type, reduce(this, size) + "px");
            });
        };

        $.fn["outer" + name] = function (size, margin) {
            if (typeof size !== "number") {
                // 返回outerWidth/outerHeight
                return orig["outer" + name].call(this, size);
            }
            return this.each(function () {
                // 设置宽度/高度 = (size - padding - border - margin)
                $(this).css(type, reduce(this, size, true, margin) + "px");
            });
        };
    });
    // selectors
    function focusable(element, isTabIndexNotNaN) {
        var nodeName = element.nodeName.toLowerCase();
        if ("area" === nodeName) {
            var map = element.parentNode, mapName = map.name, img;
            if (!element.href || !mapName || map.nodeName.toLowerCase() !== "map") {
                return false;
            }
            img = $("img[usemap=#" + mapName + "]")[0];
            return !!img && visible(img);
        }
        return (/input|select|textarea|button|object/.test(nodeName) ? !element.disabled : "a" == nodeName ? element.href || isTabIndexNotNaN : isTabIndexNotNaN)
            // the element and all of its ancestors must be visible
            && visible(element);
    }

    function visible(element) {
        return !$(element).parents().andSelf().filter(function () {
            return $.curCSS(this, "visibility") === "hidden" || $.expr.filters.hidden(this);
        }).length;
    }


    $.extend($.expr[":"], {
        data: function (elem, i, match) {
            return !!$.data(elem, match[3]);
        },
        focusable: function (element) {
            return focusable(element, !isNaN($.attr(element, "tabindex")));
        },
        tabbable: function (element) {
            var tabIndex = $.attr(element, "tabindex"), isTabIndexNaN = isNaN(tabIndex);
            return (isTabIndexNaN || tabIndex >= 0) && focusable(element, !isTabIndexNaN);
        }
    });
    // support
    $(function () {
        var body = document.body, div = body.appendChild(div = document.createElement("div"));
        $.extend(div.style, {
            minHeight: "100px",
            height: "auto",
            padding: 0,
            borderWidth: 0
        });
        // 判断当前浏览器环境是否支持minHeight属性
        $.support.minHeight = div.offsetHeight === 100;
        $.support.selectstart = "onselectstart" in div;
        // set display to none to avoid a layout bug in IE
        // http://dev.jquery.com/ticket/4014
        body.removeChild(div).style.display = "none";
    });

    // deprecated
    $.extend($.ued, {
        // $.ued.plugin is deprecated.  Use the proxy pattern instead.
        plugin: {
            add: function (module, option, set) {
                var proto = $.ued[module].prototype;
                for (var i in set) {
                    proto.plugins[i] = proto.plugins[i] || [];
                    proto.plugins[i].push([option, set[i]]);
                }
            },
            call: function (instance, name, args) {
                var set = instance.plugins[name];
                if (!set || !instance.element[0].parentNode) {
                    return;
                }
                for (var i = 0; i < set.length; i++) {
                    if (instance.options[set[i][0]]) {
                        set[i][1].apply(instance.element, args);
                    }
                }
            }
        }
    });

})(jQuery);

(function ($, undefined) {
    // jQuery 1.4+
    if ($.cleanData) {
        var _cleanData = $.cleanData;
        $.cleanData = function (elems) {
            for (var i = 0, elem; (elem = elems[i]) != null; i++) {
                try {
                    $(elem).triggerHandler("ued-remove");
                } catch (e) { }
            }
            _cleanData(elems);
        };
    }

    $.uedWidget = function (name, base, prototype) {
        var namespace = name.split(".")[0], fullName;
        name = name.split(".")[1];
        fullName = namespace + "-" + name;
        // 例如参数name='ued.tabs'，变成namespace='ued',name='tabs',fullName='ued-tabs'
        // base默认为Widget类，组件默认会继承base类的所有方法
        if (!prototype) {
            prototype = base;
            base = $.UEDWidget;
        }
        // create selector for plugin
        $.expr[":"][fullName] = function (elem) {
            return !!$.data(elem, name);
        };
        // 创建命名空间$.ued.tabs
        $[namespace] = $[namespace] || {};
        // 组件的构造函数
        $[namespace][name] = function (options, element) {
            // allow instantiation without initializing for simple inheritance
            if (arguments.length) {
                this._createWidget(options, element);
            }
        };
        // 初始化父类，一般调用了$.Widget
        var basePrototype = new base();
        // we need to make the options hash a property directly on the new instance
        // otherwise we'll modify the options hash on the prototype that we're
        // inheriting from
        //		$.each( basePrototype, function( key, val ) {
        //			if ( $.isPlainObject(val) ) {
        //				basePrototype[ key ] = $.extend( {}, val );
        //			}
        //		});
        basePrototype.options = $.extend(true, {}, basePrototype.options);
        // 给om.tabs继承父类的所有原型方法和参数
        $[namespace][name].prototype = $.extend(true, basePrototype, {
            namespace: namespace,
            widgetName: name,
            // 组件的事件名前缀，调用_trigger的时候会默认给trigger的事件加上前缀
            // 例如_trigger('create')实际会触发'tabscreate'事件
            widgetEventPrefix: $[namespace][name].prototype.widgetEventPrefix || name,
            widgetBaseClass: fullName
        }, prototype);
        // 把tabs方法挂到jquery对象上，也就是$('#tab1').tabs();
        var temp = $.uedWidget.bridge(name, $[namespace][name]);
    };

    $.uedWidget.bridge = function (name, object) {
        $.fn[name] = function (options) {
            // 如果tabs方法第一个参数是string类型，则认为是调用组件的方法，否则调用options方法
            var isMethodCall = typeof options === "string", args = Array.prototype.slice.call(arguments, 1), returnValue = this;
            // allow multiple hashes to be passed on init
            options = !isMethodCall && args.length ? $.extend.apply(null, [true, options].concat(args)) : options;
            // '_'开头的方法被认为是内部方法，不会被执行，如$('#tab1').tabs('_init')
            if (isMethodCall && options.charAt(0) === "_") {
                return returnValue;
            }
            if (isMethodCall) {
                this.each(function () {
                    // 执行组件方法
                    var instance = $.data(this, name);
                    if (options == 'options') {
                        returnValue = instance && instance.options;
                        return false;
                    } else {
                        var methodValue = instance && $.isFunction(instance[options]) ? instance[options].apply(instance, args) : instance;
                        if (methodValue !== instance && methodValue !== undefined) {
                            returnValue = methodValue;
                            return false;
                        }
                    }
                });
            } else {
                // 调用组件的options方法
                this.each(function () {
                    var instance = $.data(this, name);
                    if (instance) {
                        // 设置options后再次调用_init方法，第一次调用是在_createWidget方法里面。这个方法需要开发者去实现。
                        // 主要是当改变组件中某些参数后可能需要对组件进行重画
                        instance._setOptions(options || {});
                        $.extend(instance.options, options);
                        $(instance.beforeInitListeners).each(function () {
                            this.call(instance);
                        });
                        instance._init();
                        $(instance.initListeners).each(function () {
                            this.call(instance);
                        });
                    } else {
                        // 没有实例的话，在这里调用组件类的构造函数，并把构造后的示例保存在dom的data里面。注意这里的this是dom，object是模块类
                        $.data(this, name, new object(options, this));
                    }
                });
            }
            return returnValue;
        };
    };
    $.uedWidget.addCreateListener = function (name, fn) {
        var temp = name.split(".");
        $[temp[0]][temp[1]].prototype.createListeners.push(fn);
    };
    $.uedWidget.addInitListener = function (name, fn) {
        var temp = name.split(".");
        $[temp[0]][temp[1]].prototype.initListeners.push(fn);
    };
    $.uedWidget.addBeforeInitListener = function (name, fn) {
        var temp = name.split(".");
        $[temp[0]][temp[1]].prototype.beforeInitListeners.push(fn);
    };
    $.UEDWidget = function (options, element) {
        this.createListeners = [];
        this.initListeners = [];
        this.beforeInitListeners = [];
        // allow instantiation without initializing for simple inheritance
        if (arguments.length) {
            this._createWidget(options, element);
        }
    };
    /**
     * 格式化简单节点数据
     * add by ynshen
     */
    $.formatData = function (easyData, sourcePid) {
        var index;
        var root;
        //寻找根节点
        for (var i = 0; i < easyData.length; i++) {
            if (easyData[i].pid == sourcePid) {
                index = i;
                root = easyData[i];
                root.level = 0;
                break;
            }
        }
        if (root) {
            handleArray(easyData, root);
            return easyData[index];
        } else {
            return null;
        };
    }
    function handleArray(target_, pNode) {
        for (var i = 0; i < target_.length; i++) {
            if (target_[i].pid == pNode.id) {
                if (!pNode.children) {
                    pNode.children = new Array();
                }
                pNode.children.push(target_[i]);
                target_[i].level = pNode.level + 1;
                handleArray(target_, target_[i]);
            }
        }
    }

    /*=============================================*/
    $.UEDWidget.prototype = {
        widgetName: "widget",
        widgetEventPrefix: "",
        options: {
            disabled: false
        },
        _createWidget: function (options, element) {
            // $.widget.bridge stores the plugin instance, but we do it anyway
            // so that it's stored even before the _create function runs
            $.data(element, this.widgetName, this);
            this.element = $(element);
            this.options = $.extend(true, {}, this.options, this._getCreateOptions(), options);
            var self = this;
            //注意，不要少了前边的 "ued-"，不然会与jquery-ui冲突
            this.element.bind("ued-remove._" + this.widgetName, function () {
                self.destroy();
            });
            // 开发者实现
            this._create();
            $(this.createListeners).each(function () {
                this.call(self);
            });
            // 如果绑定了初始化的回调函数，会在这里触发。注意绑定的事件名是需要加上前缀的，如$('#tab1').bind('tabscreate',function(){});
            this._trigger("create");
            // 开发者实现
            $(this.beforeInitListeners).each(function () {
                this.call(self);
            });
            this._init();
            $(this.initListeners).each(function () {
                this.call(self);
            });
        },
        _getCreateOptions: function () {
            return $.metadata && $.metadata.get(this.element[0])[this.widgetName];
        },
        _create: function () {
        },
        _init: function () {
        },
        destroy: function () {
            this.element.unbind("." + this.widgetName).removeData(this.widgetName);
            this.widget().unbind("." + this.widgetName);
        },
        widget: function () {
            return this.element;
        },

        option: function (key, value) {
            var options = key;
            if (arguments.length === 0) {
                // don't return a reference to the internal hash
                return $.extend({}, this.options);
            }
            if (typeof key === "string") {
                if (value === undefined) {
                    return this.options[key];
                    // 获取值
                }
                options = {};
                options[key] = value;
            }
            this._setOptions(options);
            // 设置值
            return this;
        },
        _setOptions: function (options) {
            var self = this;
            $.each(options, function (key, value) {
                self._setOption(key, value);
            });
            return this;
        },
        _setOption: function (key, value) {
            this.options[key] = value;
            return this;
        },

        // $.widget中优化过的trigger方法。type是回调事件的名称，如"onRowClick"，event是触发回调的事件（通常没有这个事件的时候传null）
        // 这个方法只声明了两个参数，如有其他参数可以直接写在event参数后面
        _trigger: function (type, event) {
            // 获取初始化配置config中的回调方法
			/**
			  * add by ynshen
			  * 对于多层级的方法进行调用
			  */
            var callback;
            var callbackArr = type.split(".");

            if (callbackArr.length > 1) {
                var tempCallback = this.options[callbackArr[0]];
                for (var i = 1; i < callbackArr.length; i++) {
                    tempCallback = tempCallback[callbackArr[i]];
                }
                callback = tempCallback;
            } else {
                callback = this.options[type]
            }


            // 封装js标准event对象为jquery的Event对象
            event = $.Event(event);
            event.type = type;
            // copy original event properties over to the new event
            // this would happen if we could call $.event.fix instead of $.Event
            // but we don't have a way to force an event to be fixed multiple times
            if (event.originalEvent) {
                for (var i = $.event.props.length, prop; i;) {
                    prop = $.event.props[--i];
                    event[prop] = event.originalEvent[prop];
                }
            }
            // 构造传给回调函数的参数，event放置在最后
            var newArgs = [], argLength = arguments.length;
            for (var i = 2; i < argLength; i++) {
                newArgs[i - 2] = arguments[i];
            }
            if (argLength > 1) {
                newArgs[argLength - 2] = arguments[1];
            }
            return !($.isFunction(callback) && callback.apply(this.element, newArgs) === false || event.isDefaultPrevented());
        }
    };
})(jQuery);


(function ($) {
	/**
	 * select 下拉选择框渲染组件
	 * author: yswang
	 * version: 1.0
	 */
    $.fn.iSelect = function () {
        return this.each(function () {
            var _iSel = new $.iSelect(this);
            _iSel = null;
        });
    };

    $.iSelect = function (select) {
        if (!select || select.multiple) {
            return false;
        }

        var isel_id = select.id;
        if (!isel_id || isel_id === "" || typeof isel_id == "undefined") {
            isel_id = "iSel-" + Math.round(Math.random() * 10000);
            select.id = isel_id;
        }
        if (document.getElementById("_iSelWrap_" + isel_id)) {
            return false;
        }

        var $select = $(select), sel_w = select.offsetWidth;
        $select.addClass("iselect");
        var _onchange = select.onchange;

        if (sel_w >= 500) {
            sel_w = 500;
        }

        var isel_wrap = '<div id="_iSelWrap_' + isel_id + '" class="iselect-wrapper"></div>';
        var isel_wrapin = '<span id="_iSelVal_' + isel_id + '" class="iselwrap-val"></span>';

        // 包裹select
        $select.wrap(isel_wrap);
        $("#_iSelWrap_" + isel_id).append(isel_wrapin);

        var iSel_val = $("#_iSelVal_" + isel_id);

        select.onchange = null;
        $select.change(function () {
            var _val = this.options[this.selectedIndex].text;
            iSel_val.text(_val);

            // 执行可能存在的原始onchange事件
            if (_onchange && typeof _onchange != "undefined") {
                _onchange.apply(this);
            }
        });

        // 初始下显示select默认值
        var sel_option = $select.find("option:selected").first();
        if (sel_option.length <= 0) {
            sel_option = $select.find("option:first");
        }

        iSel_val.text(sel_option.text());
    };

})(jQuery);

function setSelectVal(selectId, val) {
    $("#_iSelVal_" + selectId).text(val);
}

+function ($) {
    'use strict';

    // TOOLTIP PUBLIC CLASS DEFINITION
    // ===============================

    var Tooltip = function (element, options) {
        this.type =
            this.options =
            this.enabled =
            this.timeout =
            this.hoverState =
            this.$element = null

        this.init('tooltip', element, options)
    }

    Tooltip.VERSION = '3.3.1'

    Tooltip.TRANSITION_DURATION = 150

    Tooltip.DEFAULTS = {
        animation: true,
        placement: 'top',
        selector: false,
        template: '<div class="tooltip" role="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>',
        trigger: 'hover focus',
        title: '',
        delay: 0,
        html: false,
        container: false,
        viewport: {
            selector: 'body',
            padding: 0
        }
    }

    Tooltip.prototype.init = function (type, element, options) {
        this.enabled = true
        this.type = type
        this.$element = $(element)
        this.options = this.getOptions(options)
        this.$viewport = this.options.viewport && $(this.options.viewport.selector || this.options.viewport)

        var triggers = this.options.trigger.split(' ')

        for (var i = triggers.length; i--;) {
            var trigger = triggers[i]

            if (trigger == 'click') {
                this.$element.on('click.' + this.type, this.options.selector, $.proxy(this.toggle, this))
            } else if (trigger != 'manual') {
                var eventIn = trigger == 'hover' ? 'mouseenter' : 'focusin'
                var eventOut = trigger == 'hover' ? 'mouseleave' : 'focusout'

                this.$element.on(eventIn + '.' + this.type, this.options.selector, $.proxy(this.enter, this))
                this.$element.on(eventOut + '.' + this.type, this.options.selector, $.proxy(this.leave, this))
            }
        }

        this.options.selector ?
            (this._options = $.extend({}, this.options, { trigger: 'manual', selector: '' })) :
            this.fixTitle()
    }

    Tooltip.prototype.getDefaults = function () {
        return Tooltip.DEFAULTS
    }

    Tooltip.prototype.getOptions = function (options) {
        options = $.extend({}, this.getDefaults(), this.$element.data(), options)

        if (options.delay && typeof options.delay == 'number') {
            options.delay = {
                show: options.delay,
                hide: options.delay
            }
        }

        return options
    }

    Tooltip.prototype.getDelegateOptions = function () {
        var options = {}
        var defaults = this.getDefaults()

        this._options && $.each(this._options, function (key, value) {
            if (defaults[key] != value) options[key] = value
        })

        return options
    }

    Tooltip.prototype.enter = function (obj) {
        var self = obj instanceof this.constructor ?
            obj : $(obj.currentTarget).data('bs.' + this.type)

        if (self && self.$tip && self.$tip.is(':visible')) {
            self.hoverState = 'in'
            return
        }

        if (!self) {
            self = new this.constructor(obj.currentTarget, this.getDelegateOptions())
            $(obj.currentTarget).data('bs.' + this.type, self)
        }

        clearTimeout(self.timeout)

        self.hoverState = 'in'

        if (!self.options.delay || !self.options.delay.show) return self.show()

        self.timeout = setTimeout(function () {
            if (self.hoverState == 'in') self.show()
        }, self.options.delay.show)
    }

    Tooltip.prototype.leave = function (obj) {
        var self = obj instanceof this.constructor ?
            obj : $(obj.currentTarget).data('bs.' + this.type)

        if (!self) {
            self = new this.constructor(obj.currentTarget, this.getDelegateOptions())
            $(obj.currentTarget).data('bs.' + this.type, self)
        }

        clearTimeout(self.timeout)

        self.hoverState = 'out'

        if (!self.options.delay || !self.options.delay.hide) return self.hide()

        self.timeout = setTimeout(function () {
            if (self.hoverState == 'out') self.hide()
        }, self.options.delay.hide)
    }

    Tooltip.prototype.show = function () {
        var e = $.Event('show.bs.' + this.type)

        if (this.hasContent() && this.enabled) {
            this.$element.trigger(e)

            var inDom = $.contains(this.$element[0].ownerDocument.documentElement, this.$element[0])
            if (e.isDefaultPrevented() || !inDom) return
            var that = this

            var $tip = this.tip()

            var tipId = this.getUID(this.type)

            this.setContent()
            $tip.attr('id', tipId)
            this.$element.attr('aria-describedby', tipId)

            if (this.options.animation) $tip.addClass('fade')

            var placement = typeof this.options.placement == 'function' ?
                this.options.placement.call(this, $tip[0], this.$element[0]) :
                this.options.placement

            var autoToken = /\s?auto?\s?/i
            var autoPlace = autoToken.test(placement)
            if (autoPlace) placement = placement.replace(autoToken, '') || 'top'

            $tip
                .detach()
                .css({ top: 0, left: 0, display: 'block' })
                .addClass(placement)
                .data('bs.' + this.type, this)

            this.options.container ? $tip.appendTo(this.options.container) : $tip.insertAfter(this.$element)

            var pos = this.getPosition()
            var actualWidth = $tip[0].offsetWidth
            var actualHeight = $tip[0].offsetHeight

            if (autoPlace) {
                var orgPlacement = placement
                var $container = this.options.container ? $(this.options.container) : this.$element.parent()
                var containerDim = this.getPosition($container)

                placement = placement == 'bottom' && pos.bottom + actualHeight > containerDim.bottom ? 'top' :
                    placement == 'top' && pos.top - actualHeight < containerDim.top ? 'bottom' :
                        placement == 'right' && pos.right + actualWidth > containerDim.width ? 'left' :
                            placement == 'left' && pos.left - actualWidth < containerDim.left ? 'right' :
                                placement

                $tip
                    .removeClass(orgPlacement)
                    .addClass(placement)
            }

            var calculatedOffset = this.getCalculatedOffset(placement, pos, actualWidth, actualHeight)

            this.applyPlacement(calculatedOffset, placement)

            var complete = function () {
                var prevHoverState = that.hoverState
                that.$element.trigger('shown.bs.' + that.type)
                that.hoverState = null

                if (prevHoverState == 'out') that.leave(that)
            }

            $.support.transition && this.$tip.hasClass('fade') ?
                $tip
                    .one('bsTransitionEnd', complete)
                    .emulateTransitionEnd(Tooltip.TRANSITION_DURATION) :
                complete()
        }
    }

    Tooltip.prototype.applyPlacement = function (offset, placement) {
        var $tip = this.tip()
        var width = $tip[0].offsetWidth
        var height = $tip[0].offsetHeight

        // manually read margins because getBoundingClientRect includes difference
        var marginTop = parseInt($tip.css('margin-top'), 10)
        var marginLeft = parseInt($tip.css('margin-left'), 10)

        // we must check for NaN for ie 8/9
        if (isNaN(marginTop)) marginTop = 0
        if (isNaN(marginLeft)) marginLeft = 0

        offset.top = offset.top + marginTop
        offset.left = offset.left + marginLeft

        // $.fn.offset doesn't round pixel values
        // so we use setOffset directly with our own function B-0
        $.offset.setOffset($tip[0], $.extend({
            using: function (props) {
                $tip.css({
                    top: Math.round(props.top),
                    left: Math.round(props.left)
                })
            }
        }, offset), 0)

        $tip.addClass('in')

        // check to see if placing tip in new offset caused the tip to resize itself
        var actualWidth = $tip[0].offsetWidth
        var actualHeight = $tip[0].offsetHeight

        if (placement == 'top' && actualHeight != height) {
            offset.top = offset.top + height - actualHeight
        }

        var delta = this.getViewportAdjustedDelta(placement, offset, actualWidth, actualHeight)

        if (delta.left) offset.left += delta.left
        else offset.top += delta.top

        var isVertical = /top|bottom/.test(placement)
        var arrowDelta = isVertical ? delta.left * 2 - width + actualWidth : delta.top * 2 - height + actualHeight
        var arrowOffsetPosition = isVertical ? 'offsetWidth' : 'offsetHeight'

        $tip.offset(offset)
        this.replaceArrow(arrowDelta, $tip[0][arrowOffsetPosition], isVertical)
    }

    Tooltip.prototype.replaceArrow = function (delta, dimension, isHorizontal) {
        this.arrow()
            .css(isHorizontal ? 'left' : 'top', 50 * (1 - delta / dimension) + '%')
            .css(isHorizontal ? 'top' : 'left', '')
    }

    Tooltip.prototype.setContent = function () {
        var $tip = this.tip()
        var title = this.getTitle()

        $tip.find('.tooltip-inner')[this.options.html ? 'html' : 'text'](title)
        $tip.removeClass('fade in top bottom left right')
    }

    Tooltip.prototype.hide = function (callback) {
        var that = this
        var $tip = this.tip()
        var e = $.Event('hide.bs.' + this.type)

        function complete() {
            if (that.hoverState != 'in') $tip.detach()
            that.$element
                .removeAttr('aria-describedby')
                .trigger('hidden.bs.' + that.type)
            callback && callback()
        }

        this.$element.trigger(e)

        if (e.isDefaultPrevented()) return

        $tip.removeClass('in')

        $.support.transition && this.$tip.hasClass('fade') ?
            $tip
                .one('bsTransitionEnd', complete)
                .emulateTransitionEnd(Tooltip.TRANSITION_DURATION) :
            complete()

        this.hoverState = null

        return this
    }

    Tooltip.prototype.fixTitle = function () {
        var $e = this.$element
        if ($e.attr('title') || typeof ($e.attr('data-original-title')) != 'string') {
            $e.attr('data-original-title', $e.attr('title') || '').attr('title', '')
        }
    }

    Tooltip.prototype.hasContent = function () {
        return this.getTitle()
    }

    Tooltip.prototype.getPosition = function ($element) {
        $element = $element || this.$element

        var el = $element[0]
        var isBody = el.tagName == 'BODY'

        var elRect = el.getBoundingClientRect()
        if (elRect.width == null) {
            // width and height are missing in IE8, so compute them manually; see https://github.com/twbs/bootstrap/issues/14093
            elRect = $.extend({}, elRect, { width: elRect.right - elRect.left, height: elRect.bottom - elRect.top })
        }
        var elOffset = isBody ? { top: 0, left: 0 } : $element.offset()
        var scroll = { scroll: isBody ? document.documentElement.scrollTop || document.body.scrollTop : $element.scrollTop() }
        var outerDims = isBody ? { width: $(window).width(), height: $(window).height() } : null

        return $.extend({}, elRect, scroll, outerDims, elOffset)
    }

    Tooltip.prototype.getCalculatedOffset = function (placement, pos, actualWidth, actualHeight) {
        return placement == 'bottom' ? { top: pos.top + pos.height, left: pos.left + pos.width / 2 - actualWidth / 2 } :
            placement == 'top' ? { top: pos.top - actualHeight, left: pos.left + pos.width / 2 - actualWidth / 2 } :
                placement == 'left' ? { top: pos.top + pos.height / 2 - actualHeight / 2, left: pos.left - actualWidth } :
        /* placement == 'right' */ { top: pos.top + pos.height / 2 - actualHeight / 2, left: pos.left + pos.width }

    }

    Tooltip.prototype.getViewportAdjustedDelta = function (placement, pos, actualWidth, actualHeight) {
        var delta = { top: 0, left: 0 }
        if (!this.$viewport) return delta

        var viewportPadding = this.options.viewport && this.options.viewport.padding || 0
        var viewportDimensions = this.getPosition(this.$viewport)

        if (/right|left/.test(placement)) {
            var topEdgeOffset = pos.top - viewportPadding - viewportDimensions.scroll
            var bottomEdgeOffset = pos.top + viewportPadding - viewportDimensions.scroll + actualHeight
            if (topEdgeOffset < viewportDimensions.top) { // top overflow
                delta.top = viewportDimensions.top - topEdgeOffset
            } else if (bottomEdgeOffset > viewportDimensions.top + viewportDimensions.height) { // bottom overflow
                delta.top = viewportDimensions.top + viewportDimensions.height - bottomEdgeOffset
            }
        } else {
            var leftEdgeOffset = pos.left - viewportPadding
            var rightEdgeOffset = pos.left + viewportPadding + actualWidth
            if (leftEdgeOffset < viewportDimensions.left) { // left overflow
                delta.left = viewportDimensions.left - leftEdgeOffset
            } else if (rightEdgeOffset > viewportDimensions.width) { // right overflow
                delta.left = viewportDimensions.left + viewportDimensions.width - rightEdgeOffset
            }
        }

        return delta
    }

    Tooltip.prototype.getTitle = function () {
        var title
        var $e = this.$element
        var o = this.options

        title = $e.attr('data-original-title')
            || (typeof o.title == 'function' ? o.title.call($e[0]) : o.title)

        return title
    }

    Tooltip.prototype.getUID = function (prefix) {
        do prefix += ~~(Math.random() * 1000000)
        while (document.getElementById(prefix))
        return prefix
    }

    Tooltip.prototype.tip = function () {
        return (this.$tip = this.$tip || $(this.options.template))
    }

    Tooltip.prototype.arrow = function () {
        return (this.$arrow = this.$arrow || this.tip().find('.tooltip-arrow'))
    }

    Tooltip.prototype.enable = function () {
        this.enabled = true
    }

    Tooltip.prototype.disable = function () {
        this.enabled = false
    }

    Tooltip.prototype.toggleEnabled = function () {
        this.enabled = !this.enabled
    }

    Tooltip.prototype.toggle = function (e) {
        var self = this
        if (e) {
            self = $(e.currentTarget).data('bs.' + this.type)
            if (!self) {
                self = new this.constructor(e.currentTarget, this.getDelegateOptions())
                $(e.currentTarget).data('bs.' + this.type, self)
            }
        }

        self.tip().hasClass('in') ? self.leave(self) : self.enter(self)
    }

    Tooltip.prototype.destroy = function () {
        var that = this
        clearTimeout(this.timeout)
        this.hide(function () {
            that.$element.off('.' + that.type).removeData('bs.' + that.type)
        })
    }


    // TOOLTIP PLUGIN DEFINITION
    // =========================

    function Plugin(option) {
        return this.each(function () {
            var $this = $(this)
            var data = $this.data('bs.tooltip')
            var options = typeof option == 'object' && option
            var selector = options && options.selector

            if (!data && option == 'destroy') return
            if (selector) {
                if (!data) $this.data('bs.tooltip', (data = {}))
                if (!data[selector]) data[selector] = new Tooltip(this, options)
            } else {
                if (!data) $this.data('bs.tooltip', (data = new Tooltip(this, options)))
            }
            if (typeof option == 'string') data[option]()
        })
    }

    var old = $.fn.tooltip

    $.fn.tooltip = Plugin
    $.fn.tooltip.Constructor = Tooltip


    // TOOLTIP NO CONFLICT
    // ===================

    $.fn.tooltip.noConflict = function () {
        $.fn.tooltip = old
        return this
    }

}(jQuery);

+function ($) {
    'use strict';

    // POPOVER PUBLIC CLASS DEFINITION
    // ===============================

    var Popover = function (element, options) {
        this.init('popover', element, options)
    }

    if (!$.fn.tooltip) throw new Error('Popover requires tooltip.js')

    Popover.VERSION = '3.3.1'

    Popover.DEFAULTS = $.extend({}, $.fn.tooltip.Constructor.DEFAULTS, {
        placement: 'right',
        trigger: 'click',
        content: '',
        template: '<div class="popover" role="tooltip"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>'
    })


    // NOTE: POPOVER EXTENDS tooltip.js
    // ================================

    Popover.prototype = $.extend({}, $.fn.tooltip.Constructor.prototype)

    Popover.prototype.constructor = Popover

    Popover.prototype.getDefaults = function () {
        return Popover.DEFAULTS
    }

    Popover.prototype.setContent = function () {
        var $tip = this.tip()
        var title = this.getTitle()
        var content = this.getContent()

        $tip.find('.popover-title')[this.options.html ? 'html' : 'text'](title)
        $tip.find('.popover-content').children().detach().end()[ // we use append for html objects to maintain js events
            this.options.html ? (typeof content == 'string' ? 'html' : 'append') : 'text'
        ](content)

        $tip.removeClass('fade top bottom left right in')

        // IE8 doesn't accept hiding via the `:empty` pseudo selector, we have to do
        // this manually by checking the contents.
        if (!$tip.find('.popover-title').html()) $tip.find('.popover-title').hide()
    }

    Popover.prototype.hasContent = function () {
        return this.getTitle() || this.getContent()
    }

    Popover.prototype.getContent = function () {
        var $e = this.$element
        var o = this.options

        return $e.attr('data-content')
            || (typeof o.content == 'function' ?
                o.content.call($e[0]) :
                o.content)
    }

    Popover.prototype.arrow = function () {
        return (this.$arrow = this.$arrow || this.tip().find('.arrow'))
    }

    Popover.prototype.tip = function () {
        if (!this.$tip) this.$tip = $(this.options.template)
        return this.$tip
    }


    // POPOVER PLUGIN DEFINITION
    // =========================

    function Plugin(option) {
        return this.each(function () {
            var $this = $(this)
            var data = $this.data('bs.popover')
            var options = typeof option == 'object' && option
            var selector = options && options.selector

            if (!data && option == 'destroy') return
            if (selector) {
                if (!data) $this.data('bs.popover', (data = {}))
                if (!data[selector]) data[selector] = new Popover(this, options)
            } else {
                if (!data) $this.data('bs.popover', (data = new Popover(this, options)))
            }
            if (typeof option == 'string') data[option]()
        })
    }

    var old = $.fn.popover

    $.fn.popover = Plugin
    $.fn.popover.Constructor = Popover


    // POPOVER NO CONFLICT
    // ===================

    $.fn.popover.noConflict = function () {
        $.fn.popover = old
        return this
    }

}(jQuery);

$(document).ready(function (argument) {
    // body...

    $.each($('.tab-container'), function () {
        var index = $(this).find('.tab').find('.current').index();

        tabShowIndex($(this), index);

        var trigger = $(this).data('trigger'),
            $self = $(this);

        $(this).on(trigger, '.tab>a', function () {
            $(this).siblings().removeClass('current').end().addClass('current');

            tabShowIndex($self, $(this).index());
        });
    });


    function tabShowIndex($tab, index) {
        $tab.find('.tab-content-item').hide().eq(index).show();
    }

});

$(document).ready(function () {

    // 去除虚线框（会影响效率）
    $("a").on('focus', function () { $(this).blur(); });


    $('.file input[type=file]').change(function (e) {
        $(this).siblings('.text').text($(this).val());
    });

    $("tbody").find("tr:odd").css("backgroundColor", "#eff6fa");
});

function hideElement(currentElement, targetElement, fn) {
    if (!$.isArray(targetElement)) {
        targetElement = [targetElement];
    }
    $(document).on("click.hideElement", function (e) {
        var len = 0, $target = $(e.target);
        for (var i = 0, length = targetElement.length; i < length; i++) {
            $.each(targetElement[i], function (j, n) {
                if ($target.is($(n)) || $.contains($(n)[0], $target[0])) {
                    len++;
                }
            });
        }
        if ($.contains(currentElement[0], $target[0])) {
            len = 1;
        }
        if (len == 0) {
            currentElement.hide();
            fn && fn(currentElement, targetElement);
        }
    });
};

/*
 *  用来给不支持HTML5 placeholder属性的浏览器增加此功能。
 *  @param element {String or Object} 需要添加placeholder提示的输入框选择器或者输入框jquery对象。
 *  @param defualtCss {String} 提示默认的样式class。
 */
function showRemind(element, defualtCss) {
    if (-[1,]) {
        return false;
    }

    $(element).each(function (el, i) {
        var placeholder = $(this).attr('placeholder');
        if (placeholder) {
            $(this).addClass(defualtCss).val(placeholder);
            $(this).focus(function (e) {
                if ($(this).attr('placeholder') === $(this).val()) {
                    $(this).val('').removeClass(defualtCss);
                }
            });

            $(this).blur(function (e) {
                if ($(this).val() === "") {
                    $(this).addClass(defualtCss).val($(this).attr('placeholder'));
                }
            });
        }
    });
}

function resize(resizeHandle) {
    var d = document.documentElement;
    var timer;//避免resize触发多次,影响性能
    var width = d.clientWidth, height = d.clientHeight;
    $(top.window).on('resize', function (e) {
        if ((width != d.clientWidth || height != d.clientHeight)) {
            width = d.clientWidth, height = d.clientHeight;
            if (timer) { clearTimeout(timer); }
            timer = setTimeout(function () {
                resizeHandle();
            }, 10);
        }
    });
}

