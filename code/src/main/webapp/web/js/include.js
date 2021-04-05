
$(document).ready(function () {
    
    var html = '<div class="layui-logo"><img src="images/logo.png" style="width:125px; height:50px;" alt="logo"></div>'+
        '<ul class="layui-nav layui-layout-left">'+
        '<li id="liIndex" class="layui-nav-item"><a href="index.html">首页</a></li>'+
        '<li id="liProject" class="layui-nav-item"><a href="project.html">项目推荐<span class="layui-new"></a></li>'+
        '<li id="liReward" class="layui-nav-item"><a href="javascript:void(0);">消费娱乐<span class="layui-hot"></span></a><dl class="layui-nav-child"><dd><a href="seven.html">幸运七个球</a></dd><dd><a href="three.html">三个胖骰子</a></dd></dl></li>'+
        '<li id="liIco" class="layui-nav-item"><a href="javascript:void(0);">币币交易<span class="layui-wait"></span></a></li>'+
        '<li id="liCharge" class="layui-nav-item"><a href="javascript:void(0);">期货杠杆<span class="layui-wait"></span></a></li>'+
        '<li id="liDGChange" class="layui-nav-item"><a href="javascript:void(0);">DG 兑换</a></li>'+
        '<li id="liInviteReward" class="layui-nav-item"><a href="invitereward.html">邀请返佣<span class="layui-new"></a></li>'+
        '</ul>'+
        '<ul id="ul_login" class="layui-nav layui-layout-right">'+
        '<li class="layui-nav-item"><a id="btn_login" href="login.html">登入</a></li>'+
        '<li class="layui-nav-item"><a id="btn_reg" href="reg.html">注册 </a></li>'+
        '</ul>'+
        '<ul id="ul_logined" class="layui-nav layui-layout-right">'+
        '<li id="liUserCenter" class="layui-nav-item">'+
        '<a href="javascript:;"><img src="" class="layui-nav-img"><span id="span_nick_name"></span></a>'+
        '<dl class="layui-nav-child"><dd><a href="usercenter.html">个人中心</a></dd></dl></li>'+
        '<li class="layui-nav-item"><a id="btn_out" href="javascript:void(0);">退出</a></li>'+
        '</ul>';
    $(".layui-header").append(html);

    $("#btn_login").fancybox({ 'width': 390, 'height': 410, 'autoSize': false, 'autoHeight': true, 'type': 'iframe', 'modal': false, 'hideOnOverlayClick': false, 'autoScale': false, 'autoDimensions': false });
    $("#btn_reg").fancybox({ 'width': 390, 'height': 410, 'autoSize': false, 'autoHeight': true, 'type': 'iframe', 'modal': false, 'hideOnOverlayClick': false, 'autoScale': false, 'autoDimensions': false });

    var hash = window.location.href;
    if(hash.indexOf('project.html')>0 || hash.indexOf('project1.html')>0){
        $("#liProject").addClass("layui-this");
    }else if(hash.indexOf('invitereward.html')>0 || hash.indexOf('invitereward1.html')>0){
        $("#liInviteReward").addClass("layui-this");
    }else if(hash.indexOf('index.html')>0 || hash.indexOf('index1.html')>0){
        $("#liIndex").addClass("layui-this");
    }else if(hash.indexOf('usercenter.html')>0){
        $("#liUserCenter").addClass("layui-this");        
    }else if(hash.indexOf('seven.html')>0 || hash.indexOf('three.html')>0){
        $("#liReward").addClass("layui-this");        
    }
    $("#liIco").on('click', function(){
        alert('敬请期待');
    });
    $("#liCharge").on('click', function(){
        alert('敬请期待');
    });

    DataUtils.getUserIsLogin(function(isLogin, user){
        if (!isLogin) { //未登陆
            $("#ul_login").show();
            $("#ul_logined").hide();
            $("#liDGChange").on('click', function(){
                $("#btn_login").click();
            });            
        } else {
            $("#ul_login").hide();
            $("#ul_logined").show();
            $("#liDGChange").on('click', function(){
                window.location.href = 'usercenter.html?menu=6';
            });
            $("#span_nick_name").text(user.nickName);
            var head = user.headImage || '/statics/images/head.jpg';
            $(".layui-nav-img").attr('src', head);
        }
    });

    DataUtils.getConfigList(function(configs){
        if (configs && configs.length > 0) {
            for(var i=0;i<configs.length;i++){
                if(configs[i].value == "0"){
                    if(configs[i].code == "YLXFHZ"){
                        $("#liReward").hide();
                    }else if(configs[i].code == "BQQHGG"){
                        $("#liIco").hide();
                    }else if(configs[i].code == "KTJL"){
                        $("#liInviteReward").hide();
                    }
                }
            }
        }
    });

    $("#btn_out").click(function(){
        $.ajax({
            url: Config.WEB_SERVER_API + Config.URI.LOGIN_OUT,
            type: 'POST',
            async: false,
            data: {},
            success: function (data) {
                // window.top.location.reload();
                window.top.location.href = "index.html";
            },
            error: function (xhr, type, errorThrown) {
                console.log(xhr);
            }
        });
    });
  
    html = '<div class="link-menu">'+
        '<div style="font-size: 20px; line-height: 30px; height: 30px; margin-bottom: 20px; margin-top: 30px;">友情链接</div>'+
        '<div id="link-menu-body" style="color:white;font-size: 16px;text-align: left;"></div>'+
    '</div>'+
    '<div class="bottom-menu">'+
      '<div style="height:120px; margin: 0 auto; border-bottom: 1px solid #272d40;">'+
        '<table style="width:100%; border:0px;">'+
          '<tr>'+
            '<td style="text-align:right; width:35%"><a href="index.html"><img src="images/logo.png" style="width:125px; height:50px;" alt="logo"></a></td>'+
            '<td style="text-align:right; width:12%">'+
              '<a href="#" target="_blank">平台愿景</a><br /><br />'+
              '<a href="#" target="_blank">联系我们</a><br /><br />'+
              '<a href="#" target="_blank">投诉建议</a>'+
            '</td>'+
            '<td style="text-align:center; width:20%">'+
              '<a href="#" target="_blank">实名认证</a><br /><br />'+
              '<a href="#" target="_blank">费用说明</a><br /><br />'+
              '<a href="invitereward.html" target="_blank">邀请奖励</a>'+
            '</td>'+
            '<td style="text-align:left; width:37%">'+
            '<a href="file/数区网法律声明.pdf" target="_blank">法律声明</a><br /><br />'+
            '<a href="file/DG兑换说明.pdf" target="_blank">DG兑换说明</a><br /><br />'+
            '<a href="file/充值提现说明.pdf" target="_blank">充值提现说明</a>'+
            '</td>'+
          '</tr>'+
        '</table>'+
      '</div>'+
    '</div>'+    
    '<div class="copy-right">'+
      '<div style="height:20px; margin: 0 auto; text-align: center;">CopyRight © 2018 All Rights Reserved. 版权所有 © www.dg-block.com</div>'+
    '</div>';
    $(".layui-bottom").append(html);

    var getLinkHref = function (advert) {
        var href = "";
        if (advert.linkType == 1) {
            href = "projectinfo.html?id=" + advert.linkProject;
        } else if (advert.linkType == 2) {
            href = advert.linkUrl;
        } else {
            href = "advertinfo.html?id=" + advert.id;
        }
        return href;
    };

    HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.ADVERT_LINK, {}, function (data) {
        $.each(data.rows, function (i, item) {
            var href = getLinkHref(item);
            var html = '<a href="' + href + '" target="_blank" title="'+item.title+'"><img title="'+item.title+'" src="' + item.image + '" /></a>';
            $("#link-menu-body").append(html);
        });
    });
    
    //JavaScript代码区域
    layui.use(['element'], function () {
        var element = layui.element;
    });
});
