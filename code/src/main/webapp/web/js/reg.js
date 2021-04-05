
$(document).ready(function () {
    var lock = false, time = 120, t = null;

    //推荐的ID
    var id = Utils.getUrlParam("reg");
    if (id) {
        $("#referrerid").val(id);
        $("#referrerid").attr("disabled", "disabled");
        $("#btnLogin").attr("href", "login.html?reg=" + id);
    }
    // CreateVerificationCode();
    $("#btn_sms").on('click', function () {
        if (lock) return false;
        var mobile = $("#username").val();
        if (!mobile) {
            showMsg('请输入11位手机号码！');
            $("#username").focus();
            return false;
        }
        if (!ValidateUtils.isPhoneNo(mobile)) {
            showMsg('请输入正确的手机号码！');
            $("#username").focus();
            return false;
        }

        //开始倒计时
        // $("#btn_sms").addClass("layui-btn-disabled");
        lock = true;
        time = 120;
        t = setInterval(function () {
            time--;
            $("#btn_sms").text(time + '(s)');
            if (time <= 0) {
                // $("#btn_sms").removeClass("layui-btn-disabled");
                $("#btn_sms").text("获取");
                lock = false;
                clearInterval(t);
            }
        }, 1000);
        var json = { mobilePhone: mobile };
        var url = Config.WEB_SERVER_API + Config.URI.SMS_SEND;
        HttpUtils.httpPost(url, json, function (result, success) {
            if (success) {
                alert('短信验证码已发送到您的手机，请注意查收！');
            } else {
                showMsg(result.resultMsg);
                // $("#btn_sms").removeClass("layui-btn-disabled");
                $("#btn_sms").text("获取");
                lock = false;
                t = null;
            }
        });
        return false;
    });
    $(".reg").click(function () {
        reg();
    });
    function reg() {
        showMsg('');
        var u = $("#username").val();
        if (!u) {
            showMsg('请输入11位手机号码！');
            $("#username").focus();
            return;
        }
        if (!ValidateUtils.isPhoneNo(u)) {
            showMsg('请输入正确的手机号码！');
            $("#username").focus();
            return;
        }
        var v = $("#validate").val();
        if(!v){
            showMsg('请输入短信验证码！');
            $("#validate").focus();
            return;
        }
        if(!ValidateUtils.isSmsCode(v)){
            showMsg('请输入正确的短信验证码！');
            $("#validate").focus();
            return;
        }
        var p = $("#password").val();
        if (!p) {
            showMsg('请输入登入密码！');
            $("#password").focus();
            return;
        }
        if (!ValidateUtils.isPassword(p)) {
            showMsg('登入密码必须6到12位！');
            $("#password").focus();
            return;
        }
        var pn = $("#passwordnew").val();
        if (!pn) {
            showMsg('请输入确认密码！');
            $("#passwordnew").focus();
            return;
        }
        if (!ValidateUtils.isPassword(pn)) {
            showMsg('确认密码必须6到12位！');
            $("#password").focus();
            return;
        }
        if (p != pn) {
            showMsg('两次输入的密码不一致！');
            $("#password").focus();
            return;
        }
        var dp = $("#dealpassword").val();
        if (!dp) {
            showMsg('请输入交易密码！');
            $("#dealpassword").focus();
            return;
        }
        if (!ValidateUtils.isDealPassword(dp)) {
            showMsg('交易密码为6位数字！');
            $("#dealpassword").focus();
            return;
        }
        
        // var v = $("#validate").val();
        // if(!v){
        //     showMsg('请输入验证码！');
        //     $("#validate").focus();
        //     return;
        // }
        // if(v.length!=4){
        //     showMsg('请输入图中4位验证码！');
        //     $("#validate").focus();
        //     return;
        // }
        
        var param = {};
        param.username = u;
        param.password = p;
        param.dealpassword = dp;
        param.validatecode = v;      
        param.referrerid = $("#referrerid").val();
        $.ajax({
            url: Config.WEB_SERVER_API + Config.URI.REG,
            type: 'POST',
            async: false,
            data: param,
            success: function (data) {
                if (!data) {
                    showMsg("注册失败!请重试。");
                    return;
                }
                if (data.resultCode != "200") {
                    showMsg(data.resultMsg);
                    // CreateVerificationCode();
                } else {
                    alert("恭喜您，注册成功！");
                    //window.top.location.reload();
                    window.top.location = 'usercenter.html?t=' + new Date().getTime();
                }
            },
            error: function (xhr, type, errorThrown) {
                console.log(xhr);
                showMsg("注册失败！")
            }
        });
    }
    function showMsg(msg) {
        $(".reg-error").text(msg);
    }

    //按键弹起
    $(document).keyup(function (event) {
        //获取当前按键的键值 
        //jQuery的event对象上有一个which的属性可以获得键盘按键的键值 
        var keycode = event.which;
        if (keycode == 13) {//回车
            reg();
        }
    });
});

// function CreateVerificationCode(){
//     var rad = Math.floor(Math.random() * Math.pow(10, 8));
//     //uuuy是随便写的一个参数名称，后端不会做处理，作用是避免浏览器读取缓存的链接
//     $("#randCodeImage").attr("src", Config.WEB_SERVER_API + Config.URI.VERIFICATION_CODE + "?uuuy="+rad);
// }
