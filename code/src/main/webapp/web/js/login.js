
$(document).ready(function() {
    var reg = Utils.getUrlParam("reg");
    if(reg){
        $("#btnReg").attr("href", "reg.html?reg=" + reg);
    }
    CreateVerificationCode();
    $(".login").click(function(){
        login();
    });
    function login(){
        showMsg('');
        var u = $("#username").val();
        if(!u){
            showMsg('请输入手机号码！');
            $("#username").focus();
            return;
        }
        if(!ValidateUtils.isPhoneNo(u)){
            showMsg('请输入正确的手机号码！');
            $("#username").focus();
            return;
        }
        var p = $("#password").val();
        if(!p){
            showMsg('请输入密码！');
            $("#password").focus();
            return;
        }
        if(!ValidateUtils.isPassword(p)){
            showMsg('密码必须6到12位！');
            $("#password").focus();
            return;
        }        
        var v = $("#validate").val();
        if(!v){
            showMsg('请输入验证码！');
            $("#validate").focus();
            return;
        }
        if(v.length!=4){
            showMsg('请输入图中4位验证码！');
            $("#validate").focus();
            return;
        }
        var param = {};
        param.username = u;
        param.password = p;
        param.validatecode = v;
        $.ajax({
            url: Config.WEB_SERVER_API + Config.URI.LOGIN,
            type: 'POST',
            async: false,
            data: param,
            success: function (data) {
                if (!data) {
                    showMsg("登录失败!请重试。");
                    return;
                }
                if (data.resultCode != "200") {
                    showMsg(data.resultMsg);
                    CreateVerificationCode();
                    $("#validate").val("");
                    if(data.resultMsg == "验证码错误"){                        
                        $("#validate").focus();
                    }else{
                        $("#password").val("");
                        $("#password").focus();
                    }
                } else {
                    window.top.location.reload();
                    // window.location = 'login.html';
                }
            },
            error: function (xhr, type, errorThrown) {
                console.log(xhr);
                showMsg("登录失败！")
            }
        });
    }
    function showMsg(msg){
        $(".login-error").text(msg);
    }

    //按键弹起
    $(document).keyup(function(event){ 
        //获取当前按键的键值 
        //jQuery的event对象上有一个which的属性可以获得键盘按键的键值 
        var keycode = event.which; 
        if(keycode==13){//回车
            login();
        }
    });
});

function CreateVerificationCode(){
    var rad = Math.floor(Math.random() * Math.pow(10, 8));
    //uuuy是随便写的一个参数名称，后端不会做处理，作用是避免浏览器读取缓存的链接
    $("#randCodeImage").attr("src", Config.WEB_SERVER_API + Config.URI.VERIFICATION_CODE + "?uuuy="+rad);
}


