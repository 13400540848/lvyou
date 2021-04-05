$(function () {
    LoginManage.init();
});
var LoginManage = {
    "init": function () {
        $("#btn_login").bind('click', function () {
            LoginManage.login();
        });
        $('.formcon .sr').each(function () {
            var thisVal = $(this).val();
            //判断文本框的值是否为空，有值的情况就隐藏提示语，没有值就显示
            if (thisVal != '') {
                $(this).siblings('span.s1').hide();
            } else {
                $(this).siblings('span.s1').show();
            }
            //聚焦型输入框验证 
            $(this).focus(function () {
                $(this).siblings('span').hide();
                $(this).closest('li').addClass('on');
            }).blur(function () {
                $(this).closest('li').removeClass('on');
                var val = $(this).val();
                if (val != '') {
                    $(this).siblings('span.s1').hide();
                } else {
                    $(this).siblings('span.s1').show();
                }
            });
        });
        $('span.s1').bind('click', function () {
            //refeshVerifyCode();
            $(this).next('.sr').focus();
        });
        var loginUserInfo = Utils.getLoginUserInfo();
        if (loginUserInfo) {
            $("#userID").val(loginUserInfo.username);
            if (loginUserInfo.remember == 1) {
                $("#remember").prop("checked", true);
                $("#pwd").val(loginUserInfo.password);
            } else {
                $('#remember').removeAttr("checked");
            }
        }
    },
    "login": function () {
        var username = $("#userID").val();
        if (username == "") {
            this.setMsg("请输入账号！")
            document.getElementById("userID").focus();
            return;
        }
        var password = $("#pwd").val();
        if (password == "") {
            this.setMsg("请输入密码！")
            document.getElementById("pwd").focus();
            return;
        }
        var json = JSON.stringify({account: username, password: password});
        $.ajax({
            url: Config.WEB_SERVER_API + Config.URI.LOGIN,
            type: 'POST',
            async: false,
            data: json,
            contentType: 'application/json',
            success: function (data) {
                if (!data) {
                    LoginManage.setMsg("登录失败!请重试。");
                    return;
                }
                if (data.resultCode != "0") {
                    LoginManage.setMsg(data.resultMsg);
                } else {
                    var loginUserInfo = {};
                    loginUserInfo.username = username;
                    if ($("#remember").is(":checked")) {
                        loginUserInfo.password = password;
                        loginUserInfo.remember = 1;
                    } else {
                        loginUserInfo.password = "";
                        loginUserInfo.remember = 0;
                    }
                    Utils.setLoginUserInfo(loginUserInfo);
                    window.top.location = 'index.html?t=' + new Date().getTime();
                }
            },
            error: function (xhr, type, errorThrown) {
                console.log(xhr);
                LoginManage.setMsg("登录失败！")
            }
        });

        // Utils.setAccessToken("xxxxxxx");
        // var loginUserInfo = {};
        // loginUserInfo.username = username;
        // if ($("#remember").is(":checked")) {
        //     loginUserInfo.password = password;
        //     loginUserInfo.remember = 1;
        // } else {
        //     loginUserInfo.password = "";
        //     loginUserInfo.remember = 0;
        // }
        // Utils.setLoginUserInfo(loginUserInfo);
        // window.location = 'index.html';
    },
    "setMsg": function (msg) {
        $(".errorwrap").show();
        $("#loginMsg").html(msg);
    }
};

$(document).keyup(function (event) {
    //获取当前按键的键值 
    //jQuery的event对象上有一个which的属性可以获得键盘按键的键值 
    var keycode = event.which;
    //处理回车的情况 
    if (keycode == 13) {
        LoginManage.login();
    }
});
