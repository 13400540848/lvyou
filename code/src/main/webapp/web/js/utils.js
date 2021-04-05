
/* 公共方法类 Utils*/
var Utils = {
    /** 获取搜索条件 */
    "getSearchQuery": function (id) {
        var query = {};
        $.each($("#" + id).find("input,select"), function (i, item) {
            var field = $(item).attr("field");
            if (field) {
                query[$(item).attr("field")] = $(item).val();
            }
        });
        return query;
    },
    /** 重置搜索条件 */
    "resetSearchQuery": function (id) {
        $.each($("#" + id).find("input, select"), function (i, item) {
            var field = $(item).attr("field");
            if (field) {
                $(item).val('');
            }
        });
    },
    /** 获取表单提交字段 */
    "getFormFieldValue": function (id, query) {
        query = query || {};
        $.each($("#" + id).find("input,select,textarea"), function (i, item) {
            var field = $(item).attr("field");
            if (field) {
                query[$(item).attr("field")] = $(item).val();
            }
        });
        return query;
    },
    /** 表单字段数据回填 */
    "setFormFieldValue": function (id, json) {
        $.each($("#" + id).find("input,select,textarea"), function (i, item) {
            var field = $(item).attr("field");
            if (field) {
                var f = $(item).attr("format");
                if (f && f == "date") {
                    $(item).val(FormatUtils.parseTimestampToDate(json[field] || ''));
                } else {
                    var defaultValue = $(item).attr("defaultValue");
                    try {
                        $(item).val(json[field] != undefined ? json[field] : (defaultValue != undefined ? defaultValue : ''));
                    } catch (e) { }
                }
            }
        });
    },
    /** 表单字段数据验证 */
    "validateFormFieldValue": function (id) {
        var result = true;
        var count = 0;
        $.each($("#" + id).find("input,select,textarea"), function (i, item) {
            var field = $(item).attr("field");
            var required = $(item).attr("required");
            var validate = $(item).attr("validate");
            if (field) {
                $(item).removeClass("validate-error");
                var value = $(item).val();
                if (required && value == "") {
                    $(item).addClass("validate-error");
                    $(item).focus();
                    count++;
                }
                if (validate) {

                }
            }
        });
        return count == 0;
    },
    /** 获取URL参数 */
    "getUrlParam": function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]); return null; //返回参数值
    },
    /** 解析系统参数 */
    "parseDicToJson": function (dic) {
        var result = [];
        if (!dic) return result;
        var items = dic.split(",");
        if (items) {
            for (var i = 0; i < items.length; i++) {
                var item = items[i].split("|");
                result.push({ text: item[0], id: (item.length > 1 ? item[1] : item[0]) });
            }
        }
        return result;
    },
    /** 获取服务端地址 */
    "getServerIp": function () {
        return Config.WebServerAPI;
    },
    /** 获取Token */
    "getAccessToken": function () {
        // return this.getCookie('accessToken') || null;
        return "";
    },
    /** 获取用户最后一次选择的应用ID */
    "getUserLoginProject": function () {
        // return this.getCookie('userLoginProject');
        return "";
    },
    /** 获取用户信息 */
    "getUserInfo": function (callback) {
        DataUtils.getUserInfo(callback);
    },
    /** 是否登录 */
    "isLogin": function () {
        return this.getAccessToken() != null;
    },
    /** 验证是否登录 */
    "checkLogin": function () {
        var token = this.getUrlParam('ticket');
        if (token) {
            this.setAccessToken(token);
        }
        if (!this.isLogin()) {
            window.top.location.href = window.top.location.origin + "/web/login.html";
            return false;
        }
        return true;
    },
    /** 设置Token */
    "setAccessToken": function (token) {
        // this.setCookie('accessToken', token);
    },
    /** 获取用户最后一次选择的应用ID */
    "setUserLoginProject": function (projectId) {
        // return this.setCookie('userLoginProject', projectId || '');
    },
    /** 设置登录信息 */
    "setLoginUserInfo": function (loginUserInfo) {
        // this.setCookie('loginUserInfo', JSON.stringify(loginUserInfo));
    },
    /** 获取登录信息 */
    "getLoginUserInfo": function () {
        return null;
        // var loginUserInfo = this.getCookie('loginUserInfo');
        // return loginUserInfo ? JSON.parse(loginUserInfo) : null;
    },
    /** 退出系统 */
    "exitSystem": function () {
        this.setCookie('accessToken', '');
        this.setCookie('userInfo', '');
        window.top.location.href = window.top.location.href.replace("index.html", "login.html");
    },
    /** 设置Cookie */
    "getCookie": function (key) {
        return $.cookie(key);
    },
    /** 获取Cookie */
    "setCookie": function (key, value) {
        $.cookie(key, value, { path: '/' });
    },
    "beautifyJson": function (str) {
        return str ? js_beautify(str, 2, ' ', 0) : '';
    },
    "copyObject": function (obj) {
        return $.extend(true, obj);
    },
    "copyJson": function (json) {
        return JSON.parse(JSON.stringify(json))
    },
    "copyArray": function (arr) {
        return arr.slice(0);
    },
    "log": function (msg) {
        console.log(msg);
    }
};

//UI
var UIUtils = {
    /** 显示加载 */
    "loading": function (msg, panel) {
        panel = panel || $("body");
        msg = msg || "正在查询请稍候……";
        $('<div class="layui-layer-shade" style="z-index: 19891014; background-color: rgb(0, 0, 0); opacity: 0.2;"></div>').appendTo(panel);
        $('<div class="layui-layer layui-layer-page" style="background-color: white;z-index: 9999999999;"><div class="layui-layer-content" style="padding: 10px 20px;">' + msg + '</div></div>').appendTo(panel);
        var l = (panel.width() - panel.find(".layui-layer-page").width())/2;
        var t = (panel.height() - panel.find(".layui-layer-page").height())/2;
        panel.find(".layui-layer-page").css("top", t + "px").css("left", l + "px");
    },
    /** 隐藏加载 */
    "loaded": function (panel) {
        panel = panel || $("body");
        panel.find(".layui-layer-shade").remove();
        panel.find(".layui-layer").remove();
    },
    /** 显示tip */
    "tip": function (msg) {
        $.messager.show({
            // title: '提示',
            msg: '<div style="text-align:center;margin-top:0px;">' + msg + '</div>',
            height: 70,
            showType: 'fade',
            timeout: 500,
            modal: false,
            close: false,
            style: {
                right: '',
                // top:document.body.scrollTop+document.documentElement.scrollTop,
                bottom: ''
            }
        });
    },
    "alert": function (msg, callback) {
        $("#dialog-alert").remove();
        $('<div id="dialog-alert" style="display: none;">' +
            '<div class="dialog-content" style="width:100%; height:130px;">' +
            '<div style="font-size: 18px; padding: 20px 0px; height: 40px; text-align:center;">' + msg + '</div>' +
            '<div style="text-align: center;"><a style="padding: 8px 20px; text-align: center; border-radius: 2px; font-size: 14px; height: 31px; line-height: 31px; border: 1px solid #6AB96E; text-decoration: none; color: #FFF; background: none repeat scroll 0% 0% #6AB96E;" href="javascript:;" id="btn-alert-ok" >确定</a></div>' +
            '</div>' +
            '</div>').appendTo($("body"));
        $("#dialog-alert").Dialog({
            title: '提示',
            autoOpen: false,
            isDrag: true,
            fixed: true,
            width: 350,
            height: 130
        });
        $('#dialog-alert').Dialog('open');
        $("#btn-alert-ok").bind('click', function () {
            $("#dialog-alert").Dialog('close');
            if (callback) {
                callback();
            }
        });
    },
    "confirm": function (msg, callback) {
        $("#dialog-alert").remove();
        $('<div id="dialog-alert" style="display: none;">' +
            '<div class="dialog-content" style="width:100%; height:130px;">' +
            '<div style="font-size: 18px; padding: 20px 0px; height: 40px; text-align:center;">' + msg + '</div>' +
            '<div style="text-align: center;">' +
            '<a style="padding: 8px 20px; text-align: center; border-radius: 2px; font-size: 14px; height: 31px; line-height: 31px; border: 1px solid #6AB96E; text-decoration: none; color: #FFF; background: none repeat scroll 0% 0% #6AB96E;" href="javascript:;" id="btn-alert-ok">确定</a>' +
            '<a style="margin-left:20px; padding: 8px 20px; text-align: center; border-radius: 2px; font-size: 14px; height: 31px; line-height: 31px; border: 1px solid #cccccc; text-decoration: none; color: #FFF; background: none repeat scroll 0% 0% #cccccc;" href="javascript:;" id="btn-alert-cancel">取消</a>' +
            '</div>' +
            '</div>' +
            '</div>').appendTo($("body"));
        $("#dialog-alert").Dialog(
            {
                title: '提示',
                autoOpen: false,
                isDrag: true,
                fixed: true,
                width: 350,
                height: 130
            });
        $('#dialog-alert').Dialog('open');
        $("#btn-alert-ok").bind('click', function () {
            $("#dialog-alert").Dialog('close');
            if (callback) {
                callback();
            }
        });
        $("#btn-alert-cancel").bind('click', function () {
            $("#dialog-alert").Dialog('close');
        });
    },
    "error": function (msg, callback) {
        $("#dialog-error").remove();
        $('<div id="dialog-error" style="display: none;">' +
            '<div class="dialog-content" style="width:100%; height:130px;">' +
            '<div style="font-size: 18px; padding: 20px 0px; height: 40px; text-align:center; color:red;">' + msg + '</div>' +
            '<div style="text-align: center;"><a style="padding: 8px 20px; text-align: center; border-radius: 2px; font-size: 14px; height: 31px; line-height: 31px; border: 1px solid #6AB96E; text-decoration: none; color: #FFF; background: none repeat scroll 0% 0% #6AB96E;" href="javascript:;" id="btn-error-ok" >确定</a></div>' +
            '</div>' +
            '</div>').appendTo($("body"));
        $("#dialog-error").Dialog({
            title: '出错了',
            autoOpen: false,
            isDrag: true,
            fixed: true,
            width: 350,
            height: 130
        });
        $('#dialog-error').Dialog('open');
        $("#btn-error-ok").bind('click', function () {
            $("#dialog-error").Dialog('close');
            if (callback) {
                callback();
            }
        });
    },
    "tooltip": function (obj, msg, position) {
        //position:'left','right','top','bottom'.
        obj.tooltip({
            position: position || 'bottom',
            content: '<span style="color:#fff">' + msg + '</span>',
            onShow: function () {
                $(this).tooltip('tip').css({
                    backgroundColor: '#666',
                    borderColor: '#666'
                });
            }
        });
    },
    "openWindow": function (title, url, width, height) {
        layer.open({
            type: 2, title: title,
            offset: 'auto', //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            area: [width + 'px', height + 'px'],
            id: 'layerDemo', //防止重复弹出
            content: url,
            shade: 0.2 //不显示遮罩
            // , yes: function () {
            //   layer.closeAll();
            // }
        });
    },
    "closeWindow": function () {
        $(".open-dialog").Dialog('close');
    }
};

//Http请求
var HttpUtils = {
    /** ajax请求GET */
    "httpGet": function (url, params, callback, sync) {
        params = params || {};
        //url = url + (url.indexOf('?') > 0 ? "&" : "?") + "access_token=" + this.getAccessToken();
        $.ajax({
            url: url,
            type: 'GET',
            async: (sync ? false : true),
            //data: { param: JSON.stringify(params) },
            data: params,
            // contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                // request.setRequestHeader("access_token", Utils.getAccessToken());
            },
            success: function (result) {
                HttpUtils.httpReturn(result, callback);
            },
            error: function (xhr, type, errorThrown) {
                HttpUtils.httpError(xhr);
            }
        });
    },
    /** ajax请求POST */
    "httpPost": function (url, params, callback, sync) {
        params = params || {};
        $.ajax({
            withCredentials: true,
            url: url,
            type: 'POST',
            async: (sync ? false : true),
            data: JSON.stringify(params),
            // contentType: false,
            contentType: "application/json",
            beforeSend: function (request) {
                // request.setRequestHeader("access_token", Utils.getAccessToken());
                // request.setRequestHeader("company_id", Utils.getUserLoginProject() || '');
            },
            success: function (result) {
                HttpUtils.httpReturn(result, callback);
            },
            error: function (xhr, type, errorThrown) {
                HttpUtils.httpError(xhr);
            }
        });
    },
    /** ajax请求PUT */
    "httpPut": function (url, params, callback) {
        params = params || {};
        $.ajax({
            withCredentials: true,
            url: url,
            type: 'PUT',
            data: params,
            beforeSend: function (request) {
                request.setRequestHeader("access_token", Utils.getAccessToken());
            },
            success: function (result) {
                HttpUtils.httpReturn(result, callback);
            },
            error: function (xhr, type, errorThrown) {
                HttpUtils.httpError(xhr);
            }
        });
    },
    /** ajax请求DELETE */
    "httpDelete": function (url, params, callback) {
        params = params || {};
        $.ajax({
            withCredentials: true,
            url: url,
            type: 'DELETE',
            data: params,
            beforeSend: function (request) {
                request.setRequestHeader("access_token", Utils.getAccessToken());
            },
            success: function (result) {
                HttpUtils.httpReturn(result, callback);
            },
            error: function (xhr, type, errorThrown) {
                HttpUtils.httpError(xhr);
            }
        });
    },
    /** 代理请求GET */
    "httpGetProxy": function (url, callback) {
        url = url + (url.indexOf('?') > 0 ? "&" : "?") + "access_token=" + Utils.getAccessToken();
        $.ajax({
            withCredentials: true,
            url: Config.WEB_SERVER_API + "Proxy/Get?url=" + escape(url),
            type: 'GET',
            beforeSend: function (request) {
                request.setRequestHeader("access_token", Utils.getAccessToken());
            },
            success: function (result) {
                HttpUtils.httpReturn(result, callback);
            },
            error: function (xhr, type, errorThrown) {
                HttpUtils.httpError(xhr);
            }
        });
    },
    /** 代理请求POST */
    "httpPostProxy": function (url, params, callback) {
        params = params || {};
        $.ajax({
            withCredentials: true,
            url: Config.WEB_SERVER_API + "Proxy/Post?url=" + escape(url),
            type: 'POST',
            beforeSend: function (request) {
                request.setRequestHeader("access_token", Utils.getAccessToken());
                request.setRequestHeader("param", params);
            },
            success: function (result) {
                HttpUtils.httpReturn(result, callback);
            },
            error: function (xhr, type, errorThrown) {
                HttpUtils.httpError(xhr);
            }
        });
    },
    "httpReturn": function (result, callback) {
        Utils.log(result);
        if (result) {
            if (result.resultCode == -1000) {
                alert(result.resultMsg);
                Utils.exitSystem();
            } if (result.resultCode != 200) {
                // alert(result.resultMsg);
                if (callback) {
                    callback(result, false);
                }
            } else {
                if (callback) {
                    callback(result, true);
                }
            }
        } else {
            UIUtils.error("请求无返回内容！");
        }
    },
    "httpError": function (xhr) {
        console.log(xhr);
        if (xhr.status == 401) {//未授权
            alert(xhr.responseText);
            window.top.location.href = window.top.location.href.replace("index.html", "login.html");
        }
    }
};

//格式化显示
var FormatUtils = {
    /** 日期格式转换 */
    "formatterDate": function (date) {
        var dt = new Date(date);
        var y = dt.getFullYear();
        var m = dt.getMonth() + 1;
        var d = dt.getDate();
        return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
    },
    /** 日期时间格式转换 */
    "formatterDateTime": function (date) {
        var dt = new Date(date);
        return dt.toLocaleString();
        // return dt.format("yyyy-mm-dd HH:MM");
    },
    /** 日期控件格式转换 */
    "parserDate": function (s) {
        if (!s) return new Date();
        var ss = (s.split('-'));
        var y = parseInt(ss[0], 10);
        var m = parseInt(ss[1], 10);
        var d = parseInt(ss[2], 10);
        if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
            return new Date(y, m - 1, d);
        } else {
            return new Date();
        }
    },
    "parseTimestampToDate": function (timestamp) {
        //var timestamp = timestampObj.time;获取时间戳的毫秒数
        var d = new Date(timestamp); //根据时间戳生成的时间对象
        var month = d.getMonth() + 1;
        var date = (d.getFullYear()) + "-" +
            (month > 9 ? month : '0' + month) + "-" +
            (d.getDate() > 9 ? d.getDate() : '0' + d.getDate());
        // (d.getDate()) + " " + 
        // (d.getHours()) + ":" + 
        // (d.getMinutes()) + ":" + 
        // (d.getSeconds());
        return date;
    },
    "parseTimestampToDateTime": function (timestamp) {
        if (!timestamp) return "";
        //var timestamp = timestampObj.time;获取时间戳的毫秒数
        var d = new Date(timestamp); //根据时间戳生成的时间对象
        var month = d.getMonth() + 1;
        var date = (d.getFullYear()) + "-" +
            (month > 9 ? month : '0' + month) + "-" +
            (d.getDate() > 9 ? d.getDate() : '0' + d.getDate()) + " " +
            (d.getHours() > 9 ? d.getHours() : '0' + d.getHours()) + ":" +
            (d.getMinutes() > 9 ? d.getMinutes() : '0' + d.getMinutes()) + ":" +
            (d.getSeconds() > 9 ? d.getSeconds() : '0' + d.getSeconds());
        return date;
    },
    /** 隐藏列 */
    "formatHideColumn": function (value, row, index) {
        return 'background-color:#cccccc;';
    },
    /** 按钮列 */
    "formatLinkButtonColumn": function (val, row, index) {
        return '<a href="javascript:void(0)">' + val + '</a>';
        // return '<a href="#">'+val+'</a>';
    },
    /** 格式化YesNo */
    "formatYesNo": function (val, row) {
        var dic = Utils.parseDicToJson(Dic.Yes_No);
        for (var i = 0; i < dic.length; i++) {
            if (dic[i].id === parseInt(val).toString()) {
                return dic[i].text;
            }
        }
        return "否";
    },
    /** 格式化时长 */
    "formatDuration": function (val, row) {
        var ts = parseInt(val);
        if (ts > 0) {
            if (ts < 1000) {
                return ts + "毫秒";
            } else if (ts < 60 * 1000) {
                return ts / 1000 + "秒" + ts % 1000 + "毫秒";
            } else if (ts < 60 * 60 * 1000) {
                return ts / (60 * 1000) + "分钟" + ts % (60 * 1000) + "秒";
            }
            else
                return ts / (60 * 60 * 1000) + "小时" + ts / (60 * 60 * 1000) + "分钟";
        } else {
            return ts;
        }
    },
    "formatMoneyAll": function (projectMoneys) {
        var result = "";
        if (projectMoneys != null && projectMoneys.length > 0) {
            for (var i = 0; i < projectMoneys.length; i++) {
                result += projectMoneys[i].allMoney + "" + projectMoneys[i].typeName + "、";
            }
            result = result.substring(0, result.length - 1);
        }
        else {
            result = "<span style='color:red;'>未设置</span>";
        }
        return result;
    },
    "formatMoneyLimit": function (projectMoneys) {
        var result = "";
        if (projectMoneys != null && projectMoneys.length > 0) {
            for (var i = 0; i < projectMoneys.length; i++) {
                result += projectMoneys[i].min + "-" + projectMoneys[i].max + "、";
            }
            result = result.substring(0, result.length - 1);
        }
        return result;
    },
    "formatFloat2": function (s) {
        var num = parseFloat(s);
        return Math.round(num * 100) / 100;
        // return s.toFixed(2);
    },
    "formatFloat8": function (s) {
        // var num = new Number(s);
        // // 正则匹配小数科学记数法
        // if (/^(\d+(?:\.\d+)?)(e)([\-]?\d+)$/.test(num)) {
        //     // 正则匹配小数点最末尾的0
        //     var temp=/^(\d{1,}(?:,\d{3})*\.(?:0*[1-9]+)?)(0*)?$/.exec(num.toFixed(8));
        //     if(temp){
        //         return temp[1];
        //     }else{
        //         return num.toFixed(8);
        //     }
        // }else{
        //     return ""+num;
        // }
        var num = parseFloat(s);
        return Math.round(num * 1000000) / 1000000;
    },
    "formatDG": function (s) {
        return s!=null ? s + "DG" : "";
    }
};

//数据请求
var DataUtils = {
    "getUserIsLogin": function (callback) {
        $.ajax({
            url: Config.WEB_SERVER_API + Config.URI.USER_INFO,
            type: 'GET',
            async: false,
            data: {},
            success: function (data) {
                if (!data || data.resultCode != "200") {
                    callback(false);
                } else {
                    callback(true, data.rows);
                }
            },
            error: function (xhr, type, errorThrown) {
                console.log(xhr);
            }
        });
    },
    /** 获取当前用户信息 */
    "getUserInfo": function (callback) {
        var url = Config.WEB_SERVER_API + Config.URI.USER_INFO;
        HttpUtils.httpGet(url, {}, function (data, success) {
            if (success && callback) {
                callback(data ? data.rows : {});
            }
        });
    },
    /** 获取当前用户是否已认证通过 */
    "getUserIsCheck": function (callback) {
        var url = Config.WEB_SERVER_API + Config.URI.USER_INFO;
        HttpUtils.httpGet(url, {}, function (data, success) {
            if (success && callback) {
                callback(data && data.rows && data.rows.checkStatus == 1);
            }
        });
    },
    /** 获取币种列表 */
    "getMoneyTypeList": function (callback) {
        var url = Config.WEB_SERVER_API + Config.URI.MONEY_TYPE_SEARCH;
        HttpUtils.httpGet(url, {}, function (data) {
            if (callback)
                callback(data.rows);
        });
    },
    /** 获取系统币种列表 */
    "getMoneyTypeList2": function (callback) {
        this.getMoneyTypeListByMode(2, callback);
    },
    /** 获取代币币种列表 */
    "getMoneyTypeList1": function (callback) {
        this.getMoneyTypeListByMode(1, callback);
    },
    /** 获取投资币种列表 */
    "getMoneyTypeList0": function (callback) {
        this.getMoneyTypeListByMode(0, callback);
    },
    /** 获取币种列表 */
    "getMoneyTypeListByMode": function (typeMode, callback) {
        var url = Config.WEB_SERVER_API + Config.URI.MONEY_TYPE_SEARCH;
        HttpUtils.httpGet(url, {}, function (result) {
            var data = result.rows;
            var newData = [];
            for (var i = 0; i < data.length; i++) {
                if (data[i].typeMode == typeMode) {
                    newData.push(data[i]);
                }
            }
            data = newData;
            if (callback)
                callback(data);
        });
    },
    /** 获取所有项目 */
    "getAllProjectList": function (callback) {
        var query = {};
        query.offset = 1;
        query.limit = 100000;
        HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.PROJECT_SEARCH, query, function (data) {
            if (callback) {
                callback(data.rows);
            }
        });
    },
    /** 获取项目详情 */
    "getProjectInfo": function (id, callback) {
        HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.PROJECT_INFO + id, {}, function (data) {
            if (callback) {
                callback(data.rows);
            }
        });
    },
    /** 获取关于我们 */
    "getAboutUs": function (callback) {
        HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.ADVERT_ABOUT_US, {}, function (data) {
            if (callback) {
                callback(data.rows);
            }
        });
    },
    /** 获取当前用户的钱包列表 */
    "getUserMoneyList": function (callback) {
        var url = Config.WEB_SERVER_API + Config.URI.USER_MONEY_LIST;
        HttpUtils.httpGet(url, {}, function (data) {
            if (callback)
                callback(data.rows);
        });
    },
    /** 获取当前用户的DG钱包 */
    "getUserMoneyList2": function (callback) {
        var url = Config.WEB_SERVER_API + Config.URI.USER_MONEY_LIST;
        HttpUtils.httpGet(url, {}, function (data) {
            var item = null;
            if (data && data.rows) {
                for (var i = 0; i < data.rows.length; i++) {
                    if (data.rows[i].moneyType && data.rows[i].moneyType.typeMode == 2) {
                        item = data.rows[i];
                        break;
                    }
                }
            }
            if (callback) {
                callback(item);
            }
        });
    },
    /** 获取当前用户的钱包 */
    "getUserMoneyInfo": function (id, callback) {
        var url = Config.WEB_SERVER_API + Config.URI.USER_MONEY_INFO + id;
        HttpUtils.httpGet(url, {}, function (data) {
            if (callback)
                callback(data.rows);
        });
    },
    /** 取消卖单 */
    "cancelUserMoneySell": function (sell, callback) {
        var url = Config.WEB_SERVER_API + Config.URI.USER_MONEY_SELL_CANCEL;
        HttpUtils.httpPost(url, sell, function (data) {
            if (callback)
                callback(data.rows);
        });
    },
    /** 获取卖单详情 */
    "getUserMoneyCellInfo": function (id, callback) {
        var url = Config.WEB_SERVER_API + Config.URI.USER_MONEY_CELL_INFO + id;
        HttpUtils.httpGet(url, {}, function (data) {
            if (callback)
                callback(data.rows);
        });
    },
    /** 获取奖励 */
    "getUserIniviteReward": function (callback) {
        var url = Config.WEB_SERVER_API + Config.URI.USER_INIVITE_REWARD_LIST;
        HttpUtils.httpGet(url, {}, function (data) {
            if (callback)
                callback(data.rows);
        });
    },
    /** 投资项目 */
    "buyProject": function (param, callback) {
        var url = Config.WEB_SERVER_API + Config.URI.USER_MONEY_PROJECT_SAVE;
        HttpUtils.httpPost(url, param, function (data, success) {
            if (callback)
                callback(data, success);
        });
    },
    /** 取消购买项目 */
    "cancelBuyProject": function (param, callback) {
        var url = Config.WEB_SERVER_API + Config.URI.USER_MONEY_PROJECT_CANCEL;
        HttpUtils.httpPost(url, param, function (data) {
            if (callback)
                callback(data.rows);
        });
    },
    /** 获取配置 */
    "getConfigList": function (callback) {
        var url = Config.WEB_SERVER_API + Config.URI.CONFIG_LIST;
        HttpUtils.httpGet(url, {}, function (data) {
            if (callback)
                callback(data.rows);
        });
    },
    /** 获取配置 */
    "getConfig": function (code, callback) {
        var url = Config.WEB_SERVER_API + Config.URI.CONFIG_INFO + code;
        HttpUtils.httpGet(url, {}, function (data) {
            if (callback)
                callback(data.rows);
        });
    },
    /** 获取娱乐消费回赠 */
    "getYLXFHZ": function (callback) {
        var url = Config.WEB_SERVER_API + Config.URI.CONFIG_INFO + 'YLXFHZ';
        HttpUtils.httpGet(url, {}, function (data) {
            if (callback)
                callback(data.rows.value == 1);
        });
    },
    /** 字典项列表 */
    "getDictItems": function (keys, obj, callback) {
        var url = Config.WEB_SERVER_API + Config.URI.DICT_ITEMS;
        HttpUtils.httpPost(url, keys, function (data) {
        	obj = data;
            if (callback)
                callback(data.rows);
        });
    },
    
};

//验证
var ValidateUtils = {
    "isPhoneNo": function (phone) {
        // 验证手机号
        var pattern = /^1[34578]\d{9}$/;
        return pattern.test(phone);
    },
    "isCardNo": function (card) {
        // 验证身份证
        var pattern = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
        return pattern.test(card);
    },
    "isNumber": function (number) {
        // 验证数字
        var pattern = /^\d+\d+\d$/;
        return pattern.test(number);
    },
    "isFloat": function (number) {
        // 验证整数或者小数
        var pattern = /^[0-9]+([.]{1}[0-9]+){0,1}$/;
        return pattern.test(number);
    },
    "isPassword": function (password) {
        // 验证密码
        var pattern = /(.+){6,12}$/;
        return pattern.test(password);
    },
    "isDealPassword": function (password) {
        // 6位交易密码
        var pattern = /^\d{6}$/;
        return pattern.test(password);
    },
    "isSmsCode": function (code) {
        // 6位短信验证码
        var pattern = /^\d{6}$/;
        return pattern.test(code);
    }
};