/* 公共方法类 Utils*/
var Utils = {    
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
        return this.getCookie('accessToken') || null;
    },
    /** 获取用户最后一次选择的应用ID */
    "getUserLoginProject": function () {
        return this.getCookie('userLoginProject');
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
        // if (!this.isLogin()) {
        //     window.top.location.href = window.top.location.origin + "/web/login.html";
        //     return false;
        // }
        return true;
    },
    /** 设置Token */
    "setAccessToken": function (token) {
        this.setCookie('accessToken', token);
    },
    /** 获取用户最后一次选择的应用ID */
    "setUserLoginProject": function (projectId) {
        return this.setCookie('userLoginProject', projectId || '');
    },
    /** 设置登录信息 */
    "setLoginUserInfo": function (loginUserInfo) {
        this.setCookie('loginUserInfo', JSON.stringify(loginUserInfo));
    },
    /** 获取登录信息 */
    "getLoginUserInfo": function () {
        var loginUserInfo = this.getCookie('loginUserInfo');
        return loginUserInfo ? JSON.parse(loginUserInfo) : null;
    },
    /** 退出系统 */
    "exitSystem": function () {
        // this.setCookie('accessToken', '');
        // this.setCookie('userInfo', '');
    	if(window.top.location.href.indexOf("index.html") >=0){
    		window.top.location.href = window.top.location.href.replace("index.html", "login.html");
    	}else{
    		window.top.location.href = window.top.location.href + "login.html";
    	}
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
    "copyObject":function(obj){
        return $.extend(true, obj);
    },
    "copyJson":function(json){
        return JSON.parse(JSON.stringify(json))
    },
    "copyArray":function(arr){
        return arr.slice(0);
    },
    "log":function(msg){
        console.log(msg);
    },
    "clearNoNum":function(obj, max){
        if(!obj.value)
            return;
        obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符
        obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的
        obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
        obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数
        //去除整数位首位非个位数的0，首位为小数点时补0
        obj.value = obj.value.replace(/(^[0]+)([0-9])/,"$2").replace(/(^(\.)([0-9]*))/,"0$1");
        if(max){
            //整数位最大位数
            let regexp = new RegExp('(^[0-9]{' + max + '})([0-9]+)')
            obj.value = obj.value.replace(regexp,"$1");
        }
    },
    "clearNoNumInt":function(obj){
        obj.value = obj.value.replace(/[^\d]/g,"");  //清除“数字”以外的字符
        if(obj.value.indexOf(".")< 0 && obj.value !=""){//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额
            obj.value= parseFloat(obj.value);
        }
        return obj.value;
    }
};

//UI
var UIUtils = {
    /** 显示加载 */
    "loading": function (msg, panel) {
        panel = panel || $("body");
        msg = msg || "正在查询请稍候";
        $('<div class="shade_mask"></div>').appendTo(panel);
        $('<div class="shade_layer"><div class="shade_msg"><span class="xubox_msgtype16"></span><span class="xubox_text">'+msg+'</span></div>').appendTo(panel);
    },
    /** 隐藏加载 */
    "loaded": function (panel) {
        panel = panel || $("body");
        panel.find(".shade_mask").remove();
        panel.find(".shade_layer").remove();
    },
    /** 显示tip */
    "tip": function (msg, callback) {
    	UIUtils.showTip(msg);
    	setTimeout(function(){
    		UIUtils.loaded();
    		if(callback){
    			callback();
    		}
		}, 1000);
    },
    "showTip": function (msg, panel) {
        panel = panel || $("body");
        msg = msg || "保存成功";
        $('<div class="shade_mask"></div>').appendTo(panel);
        $('<div class="shade_layer"><div class="shade_msg" style="background-color: #7E7E7E;color: #fff;"><span class="xubox_text" style="padding-left: 20px;">'+msg+'</span></div>').appendTo(panel);
    },
    "alert": function (msg, callback) {
        $("#dialog-alert").remove();
        $('<div id="dialog-alert" style="display: none;">' +
            '<div class="dialog-content" style="width:100%; height:130px;">'+
                '<div style="font-size: 18px; padding: 20px 0px; height: 40px; text-align:center;">' + msg + '</div>' +
                '<div style="text-align: center;"><a style="padding: 8px 20px; text-align: center; border-radius: 2px; font-size: 14px; height: 31px; line-height: 31px; border: 1px solid #6AB96E; text-decoration: none; color: #FFF; background: none repeat scroll 0% 0% #6AB96E;" href="javascript:;" id="btn-alert-ok" >确定</a></div>' +
            '</div>'+
        '</div>').appendTo($("body"));
        $("#dialog-alert").Dialog({
             title: '提示',
             autoOpen: false,
             isDrag:true,
             fixed:true,
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
            '<div class="dialog-content" style="width:100%; height:130px;">'+
                '<div style="font-size: 18px; padding: 20px 0px; height: 40px; text-align:center;">' + msg + '</div>' +
                '<div style="text-align: center;">' + 
                '<a style="padding: 8px 20px; text-align: center; border-radius: 2px; font-size: 14px; height: 31px; line-height: 31px; border: 1px solid #6AB96E; text-decoration: none; color: #FFF; background: none repeat scroll 0% 0% #6AB96E;" href="javascript:;" id="btn-alert-ok">确定</a>' + 
                '<a style="margin-left:20px; padding: 8px 20px; text-align: center; border-radius: 2px; font-size: 14px; height: 31px; line-height: 31px; border: 1px solid #cccccc; text-decoration: none; color: #FFF; background: none repeat scroll 0% 0% #cccccc;" href="javascript:;" id="btn-alert-cancel">取消</a>' +
                '</div>' +
            '</div>'+
        '</div>').appendTo($("body"));
        $("#dialog-alert").Dialog(
         {
             title: '提示',
             autoOpen: false,
             isDrag:true,
             fixed:true,
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
        $("#btn-alert-cancel").bind('click', function(){
            $("#dialog-alert").Dialog('close');
        });
    },
    "error":function(msg, callback){
        $("#dialog-error").remove();
        $('<div id="dialog-error" style="display: none;">' +
            '<div class="dialog-content" style="width:100%; height:130px;">'+
                '<div style="font-size: 18px; padding: 20px 0px; height: 40px; text-align:center; color:red;">' + msg + '</div>' +
                '<div style="text-align: center;"><a style="padding: 8px 20px; text-align: center; border-radius: 2px; font-size: 14px; height: 31px; line-height: 31px; border: 1px solid #6AB96E; text-decoration: none; color: #FFF; background: none repeat scroll 0% 0% #6AB96E;" href="javascript:;" id="btn-error-ok" >确定</a></div>' +
            '</div>'+
        '</div>').appendTo($("body"));
        $("#dialog-error").Dialog({
             title: '出错了',
             autoOpen: false,
             isDrag:true,
             fixed:true,
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
    "tooltip":function(obj, msg, position){
        //position:'left','right','top','bottom'.
        obj.tooltip({
            position: position || 'bottom',
            content: '<span style="color:#fff">'+msg+'</span>',
            onShow: function(){
                $(this).tooltip('tip').css({
                    backgroundColor: '#666',
                    borderColor: '#666'
                });
            }
        });
    },
    "openWindow":function(title, url, width, height){
        $(".open-dialog").remove();
        $('<div style="display: none;" class="open-dialog">'+
            '<div class="dialog-content">'+
                '<div class="dialog-iframe" style="height: '+height+'px;">'+
                    '<iframe id="iframe_dialog" width="100%" height="100%" frameborder="0" marginheight="0" marginwidth="0"></iframe>'+
                '</div>'+
            '</div>'+
        '</div>').appendTo($("body"));
        $('.open-dialog').Dialog({
			title: title,
			autoOpen: false,
			isDrag:true,
			fixed:false,
			width: width,
			height: height
        });
        $("#iframe_dialog").attr("src", url);
        $('.open-dialog').Dialog('open');
    },
    "closeWindow":function(){
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
//            contentType: "application/json; charset=utf-8",
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
    /** ajax请求DELETE */
    "httpDelete": function (url, params, callback) {
        params = params || {};
        $.ajax({
            withCredentials: true,
            url: url,
            type: 'DELETE',
            data: params,
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
    /** 代理请求GET */
    "httpGetProxy": function (url, callback) {
        url = url + (url.indexOf('?') > 0 ? "&" : "?") + "access_token=" + Utils.getAccessToken();
        $.ajax({
            withCredentials: true,
            url: Config.WEB_SERVER_API + "Proxy/Get?url=" + escape(url),
            type: 'GET',
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
    /** 代理请求POST */
    "httpPostProxy": function (url, params, callback) {
        params = params || {};
        $.ajax({
            withCredentials: true,
            url: Config.WEB_SERVER_API + "Proxy/Post?url=" + escape(url),
            type: 'POST',
            beforeSend: function (request) {
                // request.setRequestHeader("access_token", Utils.getAccessToken());
                // request.setRequestHeader("param", params);
            },
            success: function (result) {
                HttpUtils.httpReturn(result, callback);
            },
            error: function (xhr, type, errorThrown) {
                HttpUtils.httpError(xhr);
            }
        });
    },
    "httpReturn":function(result, callback){
        Utils.log(result);
        if(result){
            if(result.resultCode==-1000){
                alert(result.resultMsg);
                Utils.exitSystem();
            }if(result.resultCode!=0){
                UIUtils.error(result.resultMsg);
                if (callback) {
                    callback(result, false);
                }
            }else{
                if (callback) {
                    callback(result, true);
                }
            }
        }else{
            UIUtils.error("请求无返回内容！");
        }
    },
    "httpError":function(xhr){
        console.log(xhr);
        if(xhr.status==401){//未授权
//            alert(xhr.responseText);
            window.top.location.href = window.top.location.href.replace("index.html", "login.html");
        }
    }
};

//格式化显示
var FormatUtils = {
    /** 日期格式转换 */
    "formatterDate": function (date) {
        if(!date) return "";
        var dt = new Date(date);
        var y = dt.getFullYear();
        var m = dt.getMonth() + 1;
        var d = dt.getDate();
        return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
    },
    /** 日期时间格式转换 */
    "formatterDateTime": function (date) {
        if(!date) return "";
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
    "parseTimestampToDate":function(timestamp){
        if(!timestamp) return "";
        //var timestamp = timestampObj.time;获取时间戳的毫秒数
        var d = new Date(timestamp); //根据时间戳生成的时间对象
        var date = (d.getFullYear()) + "-" + 
        (d.getMonth() + 1) + "-" +
        (d.getDate());
        // (d.getDate()) + " " + 
        // (d.getHours()) + ":" + 
        // (d.getMinutes()) + ":" + 
        // (d.getSeconds());
        return date;
    },
    "parseTimestampToDateTime":function(timestamp){
        if(!timestamp) return "";
        //var timestamp = timestampObj.time;获取时间戳的毫秒数
        var d = new Date(timestamp); //根据时间戳生成的时间对象
        var date = (d.getFullYear()) + "-" + 
        (d.getMonth() + 1) + "-" +
        (d.getDate()) + " " + 
        (d.getHours()>9?d.getHours():'0' + d.getHours()) + ":" + 
        (d.getMinutes()>9?d.getMinutes():'0' + d.getMinutes()) + ":" + 
        (d.getSeconds()>9?d.getSeconds():'0' + d.getSeconds());
        return date;
    },
    /** 隐藏列 */
    "formatHideColumn": function (value, row, index) {
        return 'background-color:#cccccc;';
    },
    /** 按钮列 */
    "formatLinkButtonColumn": function (val, row, index) {
        return '<a href="javascript:void(0)">'+val+'</a>';
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
    "formatDuration": function(val, row) {
        var ts = parseInt(val);
        if(ts > 0){
            if(ts < 1000){
                return ts + "毫秒";
            }else if(ts < 60*1000){
                return ts/1000 + "秒" + ts%1000 + "毫秒";
            }else if(ts < 60*60*1000){
                return ts/(60*1000) + "分钟" + ts%(60*1000) + "秒";
            }
            else
                return ts/(60*60*1000) + "小时" + ts/(60*60*1000) + "分钟";
        }else{
            return ts;
        }
    },
    "formatMoneyAll":function(projectMoneys){
        var result = "";
        if(projectMoneys!=null && projectMoneys.length>0){
            for(var i=0;i<projectMoneys.length;i++){
                result += projectMoneys[i].allMoney + "" + projectMoneys[i].typeName+"、";
            }
            result = result.substring(0, result.length-1);
        }
        else{
            result = "<span style='color:red;'>未设置</span>";
        }
        return result;
    },
    "formatProjectMoneyAll":function(project){
        var result = 0;
        if(project.projectMoneys!=null && project.projectMoneys.length>0){
            for(var i=0;i<project.projectMoneys.length;i++){
                result += project.projectMoneys[i].allMoney * project.projectMoneys[i].moneyScale;
            }
            result += " " + project.moneyTypeName;
        }
        else{
            result = "<span style='color:red;'>未设置</span>";
        }
        return result;
    },
    "formatProjectMoneyUser":function(project){
        var result = 0;
        if(project.projectUsers!=null && project.projectUsers.length>0){
            for(var i=0;i<project.projectUsers.length;i++){
                result += project.projectUsers[i].projectMoney;
            }
            result += " " + project.moneyTypeName;
        }
        else{
            result = "<span style='color:red;'>0</span>";
        }
        return result;
    },
    "formatMoneyLimit":function(projectMoneys){
        var result = "";
        if(projectMoneys!=null && projectMoneys.length>0){
            for(var i=0;i<projectMoneys.length;i++){
                result += projectMoneys[i].min + "-" + projectMoneys[i].max+"、";
            }
            result = result.substring(0, result.length-1);
        }
        return result;
    },
    "formatFloat2":function(s){
        var num=parseFloat(s);
        return Math.round(num*100)/100;
        // return s.toFixed(2);
    },
    "formatPercent":function(s){
        return s+" %";
    },
    "formatDG":function(s){
        return s ? s+" DG" : "";
    },
    /** 格式化字典项 */
    "formatDictItem": function (val, row, index) {
    	var col = $(this)[0];
    	if(col.dictKey && col.dictItems){
    		var list = col.dictItems[col.dictKey];
    		for(var i=0;i<list.length;i++){
    			if(list[i].value == val){
    				return list[i].name;
    			}
    		}
    	}
        return "-";
    },
};

//通用控件
var ControlUtils = {
    "Upload": { //上传
        "Data":[],
        "create": function (id, control) {
            var dom = $('<form id="uploadForm_'+id+'" method="post" enctype="multipart/form-data" style="width:100%;height:22px;"><input class="easyui-filebox" id="uploadFile_'+id+'" style="width:100%" /><input id="uploadFileValue_'+id+'" type="hidden" /></form>').appendTo(control);
            dom.find("input").filebox({
                prompt:'请选择文件...',
                // accept: 'image/*',
                onChange : function(e) {
                    // file对象
                    var file = $(this).next().find('input[class="textbox-text"]');
                    // 上传的文件大小
                    var fileSzie = file.size;
                }
            });
            this.Data[id] = control;
        },
        "upload": function (id, okCallback, errCallback) {
            var file = $("#uploadForm_" + id).find("input[type=file]")[0].files[0];
            if(file){
                var formData = new FormData();
                formData.append("action", "UploadVMKImagePath");
                formData.append("file", file);
                var url = Config.WEB_SERVER_API + Config.URI.COMMON_UPLOAD;
                $.ajax({
                    url: url,
                    type: 'POST',
                    data: formData,
                    async: false,
                    cache: false,
                    contentType: false,
                    processData: false,
                    beforeSend: function (request) {
                        request.setRequestHeader("access_token", Utils.getAccessToken());
                        request.setRequestHeader("company_id", Utils.getUserLoginProject() || '');
                    },
                    success: function (data) {
                        if(data && data.resultCode > 0){
                            if(okCallback){
                                okCallback(data.resultMsg);
                            }
                        }else{
                            if(errCallback){
                                errCallback(data.resultMsg);
                            }
                        }
                    },
                    error: function (data) {
                        if(errCallback){
                            errCallback(data);
                        }
                    }
                });
            }else{
                if(okCallback){
                    okCallback(this.getValue());
                }
            }            
        },
        "getValue": function (id) {
            return $("#uploadFileValue_" + id).val();
        },
        "setValue": function (id, value) {
            $("#uploadFileValue_" + id).val(value);
        }
    },
    "DownloadFile":{ //下载
        "download":function(url, fileName){
            var url = Config.URI.DOWNLOAD_FILE + "?url="+(url||'')+"&fileName=" + fileName;
            window.open(url);
        }
    }
};

//数据请求

//数据
var DataUtils = {
    /** 获取当前用户信息 */
    "getUserInfo": function (callback) {
        var url = Config.WEB_SERVER_API + Config.URI.LOGIN_INFO;
        HttpUtils.httpGet(url, {}, function (data, success) {
            if(success){
                if (callback)
                callback(data ? data.rows : {});
            }   
        });
    },
    /** 获取当前用户菜单列表 */
    "getUserMenuList": function (callback) {
        var url = Config.WEB_SERVER_API + Config.URI.USER_MENU;
        HttpUtils.httpGet(url, {}, function (data, success) {
            if(success){
                if (callback)
                callback(data ? data.rows : {});
            }   
        });
    },
    /** 获取门户列表 */
    "getNavList": function (callback) {
        var url = Config.WEB_SERVER_API + Config.URI.MENU_NAVS;
        HttpUtils.httpGet(url, {}, function (data, success) {
            if(success){
                if (callback)
                callback(data ? data.rows : {});
            }   
        });
    },    
    /** 获取币种列表 */
    "getMenuList": function (typeMode, callback) {
        var url = Config.WEB_SERVER_API + Config.URI.MONEY_TYPE_SEARCH;
        HttpUtils.httpGet(url, {}, function (result) {
            var data = result.rows;
            if(typeMode!=""){
				var newData = [];
				for(var i=0;i<data.length;i++){
					if(data[i].typeMode == parseInt(typeMode)){
						newData.push(data[i]);
					}
				}
				data = newData;
			}
            if (callback)
                callback(data);
        });
    },
    /** 获取币种列表 */
    "getMoneyTypeList": function (typeMode, callback) {
        var url = Config.WEB_SERVER_API + Config.URI.MONEY_TYPE_SEARCH;
        HttpUtils.httpGet(url, {}, function (result) {
            var data = result.rows;
            if(typeMode!=""){
				var newData = [];
				for(var i=0;i<data.length;i++){
					if(data[i].typeMode == parseInt(typeMode)){
						newData.push(data[i]);
					}
				}
				data = newData;
			}
            if (callback)
                callback(data);
        });
    },
    /** 获取所有项目 */
    "getAllProjectList":function(callback){
        var query = {};
		query.offset = 1;
		query.limit = 100000;
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.PROJECT_SEARCH, query, function(data){
			if(callback){
				callback(data.rows);
			}
		});
    },
    /** 获取所有广告位 */
    "getAdvertLocations":function(callback){
        HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.ADVERT_LOCATION_SEARCH, {}, function(data){
			if(callback){
				callback(data.rows);
			}
		});        
    },
    /** 获取所有广告状态 */
    "getAdvertStatus":function(callback){
        HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.ADVERT_STATUS_SEARCH, {}, function(data){
			if(callback){
				callback(data.rows);
			}
		});        
    },    
    /** 获取所有广告连接方式 */
    "getAdvertLinkTypes":function(callback){
        HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.ADVERT_LINK_TYPE_SEARCH, {}, function(data){
			if(callback){
				callback(data.rows);
			}
		});        
    },    
    /** 获取查询数据 */
    "getQueryList": function (code, query, ids, areaIds, callback) {
        var params = {};
        query = query || [];
        query.push({ key: 'SEARCHCODE', value: code });
        query.push({ key: 'IS_PAGING', value: "不分页" });
        query.push({ key: 'PAGE_NUM', value: null });
        query.push({ key: 'ROWS_NUM', value: null });
        
        params.query = JSON.stringify(query);
        params.ids = ids;
        params.areaIds = areaIds;
        var url = Config.WEB_SERVER_API + Config.URI.WMCP_INVOKE;
        HttpUtils.httpPost(url, params, function (data) {
            if (callback) {
                callback(data);
            }
        });
    },
   
    /** 字典项列表 */
    "getDictItems": function (keys, callback) {
        var url = Config.WEB_SERVER_API + Config.URI.DICT_ITEMS;
        HttpUtils.httpPost(url, keys, function (data) {
            if (callback)
                callback(data.rows);
        });
    }
};
//数据表格

//表格
var DataTableUtils = {
	 /** 获取搜索条件 */
    "getSearchQuery": function (id) {
        var query = {};
        $.each($("#"+id).find("input,select"), function(i,item){
            var field = $(item).attr("field");
            var value = $(item).val();
            if(field && value){
                query[$(item).attr("field")] = value;
            }
        });
        return query;
    },
    /** 重置搜索条件 */
    "resetSearchQuery": function (id) {
        $.each($("#"+id).find("input, select"), function(i,item){
            var field = $(item).attr("field");
            if(field){
                $(item).val('');         
            }
        });
    },
    /** 设置搜索条件-字典 */
    "setSearchQueryDict": function (id, items) {
    	if(id && items){
    		$.each($("#"+id).find("input, select"), function(index, item){
                var key = $(item).attr("dict");             
                if(key){
            	   var emptyText = $(item).attr("emptyText");
                   var emptyValue = $(item).attr("emptyValue");
                   if(emptyText){
                	   $(item).append("<option value='"+(emptyValue?emptyValue:"")+"' selected='selected'>"+emptyText+"</option>");
                   }
                	var list = items[key];
                	if(list && list.length>0){	
            			for(var i=0;i<list.length;i++){
            				$(item).append("<option value='"+list[i].value+"'>"+list[i].name+"</option>");
            			}
                	}      
                }
            });
    	}
    },
    /** 获取查询分页数据 */
    "getQueryPageList": function (uri, query, pageIndex, pageSize, callback) {
    	query = query || {};
		query.pageIndex = pageIndex+1;
		query.pageSize = pageSize;
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpPost(Config.WEB_SERVER_API + uri, query, function(data){
			UIUtils.loaded();
			if(callback){
				callback(data.rows);
			}
		});
    },
    /** 导出Excel（分页的数据） */
    "exportExcelPageList": function (code, query, ids, areaIds, page, rows, fileName, heads, callback) {
        var params = {};
        query = query || [];
        query.push({ key: 'SEARCHCODE', value: code });
        query.push({ key: 'IS_PAGING', value: "分页" });
        query.push({ key: 'PAGE_NUM', value: page <= 0 ? 1 : page });
        query.push({ key: 'ROWS_NUM', value: rows });

        params.query = JSON.stringify(query);
        params.ids = ids;
        params.areaIds = areaIds;
        var cols = [];
        if (heads && heads.length > 0) {
            $.each(heads, function (i, item) {
                if (!item.formatter) {
                    cols.push(item);
                }
            });
        }
        params.heads = JSON.stringify(cols);
        //params.page = page;
        //params.rows = rows;
        var url = Config.WEB_SERVER_API + Config.URI.WMCP_EXPORT_EXCEL;
        HttpUtils.httpPost(url, params, function (data) {            
            if (data && data.resultCode > 0) {
                var url = Config.WEB_SERVER_API + Config.URI.WMCP_DOWNLOAD_FILE + "?file=" + data.resultMsg + "&fileName=" + fileName;
                window.top.open(url);
            }
            if (callback) {
                callback(data);
            }
        });
    },
}

//表单
var FormUtils = {
		/** 设置表单字段-字典 */
	    "setFormFieldDict": function (id, items) {
	    	if(id && items){
	    		$.each($("#"+id).find("input, select"), function(index, item){
	                var key = $(item).attr("dict");	                
	                if(key){
	                	var emptyText = $(item).attr("emptyText");
	                    var emptyValue = $(item).attr("emptyValue");
		                if(emptyText){
		                	$(item).append("<option value='"+(emptyValue?emptyValue:"")+"' selected='selected'>"+emptyText+"</option>");
		                }
	                	var list = items[key];
	                	if(list && list.length>0){	
	            			for(var i=0;i<list.length;i++){
	            				$(item).append("<option value='"+list[i].value+"'>"+list[i].name+"</option>");
	            			}
	                	}      
	                }
	            });
	    	}
	    },
		/** 获取表单提交字段 */
	    "getFormFieldValue": function (id, query) {
	        query = query || {};
	        $.each($("#"+id).find("input,select,textarea"), function(i,item){
	            var field = $(item).attr("field");
	            if(field){
	                query[$(item).attr("field")] = $(item).val();
	            }
	        });
	        return query;
	    },
	    /** 表单字段数据回填 */
	    "setFormFieldValue": function (id, json) {
	        $.each($("#"+id).find("input,select,textarea"), function(i,item){
	            var field = $(item).attr("field");
	            if(field){
	                var f = $(item).attr("format");
	                if(f && f=="date"){
	                    $(item).val(FormatUtils.parseTimestampToDate(json[field] || ''));
	                }else{
	                    var defaultValue = $(item).attr("defaultValue");
	                    try{
	                        $(item).val(json[field]!=undefined ? json[field] : (defaultValue!=undefined ? defaultValue : ''));
	                    }catch(e){}
	                }
	            }
	        });
	    },
	    /** 表单字段数据验证 */
	    "validateFormFieldValue": function (id) {
	        var result = true;
	        var count = 0;
	        $.each($("#"+id).find("input,select,textarea"), function(i,item){
	            var field = $(item).attr("field");
	            var required = $(item).attr("required");
	            var validate = $(item).attr("validate");
	            if(field){
	                $(item).removeClass("validate-error");
	                var value = $(item).val();
	                if(required && value==""){
	                    $(item).addClass("validate-error");
	                    $(item).focus();
	                    count++;
	                }
	                if(validate){
	                    
	                }
	            }
	        });
	        return count == 0;
	    },
    "isMobilePhone":function (mobile){
        if(mobile.length > 0 && mobile.length!=11) {
            return false;
        }
        var myreg = /^(((13[0-9]{1})|159|153)+\d{8})$/;
        if(!myreg.test(mobile)){
            return false;
        }
        return true;
    },
    "isEmail":function (value){
        var myreg = /^(.+)@(.+)$/;
        if(!myreg.test(value)){
            return false;
        }
        return true;
    }
}