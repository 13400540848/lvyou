<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String basePath = request.getContextPath();
if(basePath.endsWith("/")){
	basePath = basePath.substring(0, basePath.length()-1);
}
String path = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+basePath;
%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=7"/>
    <meta name="filetype" content="1">
    <meta name="publishedtype" content="1">
    <meta name="pagetype" content="2">
    <meta name="catalogs" content="n_275">
    <meta name="baidu-site-verification" content="IrZAsR5ux2Kobuqy"/>
    <title>胡萝卜文学社</title>
    <meta name="keywords" content="小学,初中,高中,作文,诗歌,散文,社评">
    <meta name="description"
          content="是以写作为主题的垂直文学网站。">
    <link href="/statics/images/favicon1.ico" rel="shortcut icon"/>
    <link href="/statics/hq2013/css/common/common.css" rel="stylesheet" type="text/css"/>
    <link href="/statics/hq2013/css/news/skinNews.css" rel="stylesheet" type="text/css"/>
    <link href="/statics/hq2013/css/news/layoutOpinion.css" rel="stylesheet" type="text/css"/>
    <link href="/statics/hq2015/css/pubFooter/pubFooter150429.css" rel="stylesheet"
          type="text/css">
    <link href="/statics/hq2013/css/topLogin/topLogin.css" rel="stylesheet" type="text/css">
    <script src="/statics/js/jquery.js" type="text/javascript"></script>
    <script type="text/javascript" src="/statics/js/adapterCategory.js"
            charset="utf-8"></script>
    <style type="text/css">
        /***** 图片引用区域 *****/
        .channelHead {background: url(/statics/hq2013/images/news/worldMainNav.png) repeat-x;}
        .logoNews {}
        .cur span { background: url(/statics/hq2013/images/news/news2015.png) no-repeat;}
        .navSearch { background:url(/statics/hq2013/images/news/bgSear.png) no-repeat;}
        /***** 通用导航样式 *****/
        .navNews { height:70px;}
        .navNewsCon { width:970px;height:80px;margin:0px auto;position:relative;}
        .navNewsCon h1 { position:absolute;top:-100px;}
        .logoNews { width:155px;height:95px;position:absolute;top:-40px;left:1px;z-index:9999;}
        .logoNews
        span.info{width:208px;font-family:Arial,Helvetica,"宋体";height:40px;line-height:40px;position:absolute;top:0px;left:112px;color:#cacaca;}
        /*导航列表样式*/
        .navNewsMain { width:600px;float:left;height:32px;margin:21px 0 0 175px;font-size:16px;font-weight:bold;}
        .navNewsMain ul { height:54px;overflow:hidden;}
        .navNewsMain li { float:left; margin:0 7px;}
        .navNewsMain li span { width:55px; text-align:center;height:49px;line-height:28px;}
        .navNewsMain li span a { font-family:Microsoft
        YaHei,SimHei;color:#2b2b2b;padding:0;margin:0px;font-weight:normal;line-height:30px;font-size:16px;}
        .navNewsMain li span a:hover { color:#1a6f99; text-decoration:none;}
        .navNewsMain li i { color:#c8c8c8;font-family:Arial,Helvetica,"宋体";}
        .navNewsMain .cur span { background-position:0 -105px; display:block; height:49px;}
        .navNewsMain .cur span a { color:#fff;display:block;height:32px;}
        .navNewsMain .cur span a:hover { color:#fff;}
        .navNewsMain .cur i,.navNewsMain .cur_pre i { display:none;}
        .navNewsMain .curBig span ,.navNewsMain .curMid span ,.navNewsMain .curMid2 span{ display:inline-block;
        width:105px;}
        .navNewsMain .curBig span { background-position:-65px -105px;}
        .navNewsMain .curMid span { background-position:-200px -105px; width:70px;}
        .navNewsMain .curMid2 span { width:75px;}
        .navNewsMain .curBig span a ,.navNewsMain .curMid span a,.navNewsMain .curMid2 span a{ color:#fff;}
        /*导航搜索样式*/
        .navSearch ,.searchSmall ,.searchBig {float:right;width:160px;height:32px;padding:0;margin-top:20px;}
        .navSearch input ,.searchSmall input ,.searchBig input
        {float:left;border:0px;background:none;outline-style:none;padding:0;}
        .navSearch input.searchBox ,.searchSmall input.searchBox ,.searchBig input.searchBox
        {width:130px;height:30px;line-height:30px;color:#2c2624;font-family:"微软雅黑",Arial,Helvetica,"宋体";font-size:14px;
        padding-left:5px;}
        .navSearch input.searchBtn ,.searchSmall input.searchBtn ,.searchBig input.searchBtn
        {width:25px;height:30px;cursor:pointer;}
        /**/
        .searchSmall{width:100px;}
        .searchSmall input.searchBox{width:70px;}
        /**/
        .searchBig{width:210px;}
        .searchBig input.searchBox{width:180px;}
        .leftList dt h3 { width: 475px;text-overflow: ellipsis;white-space: nowrap;}
    </style>
    <sitemesh:write property='head'/>
</head>
<body>

<!-- 公共头部分 begin -->
<div class="topIndex">
    <div class="hdTop">
        <div class="hdTopCon navTopSec">
            <div id="hdBefore" class="hdBefore ui-loginBefore" style=""><a href="http://i.huanqiu.com/register

" target="_blank" class="hdtReg">注册</a><span class="hdtLogin ui-topLogin"><div class="hdtLogBoxWrap"><a href="http://i.huanqiu.com/

" class="btnLogin" style="color:#fff;" target="_self">登录</a></div></span></div>
            <div id="hdLogged" class="hdLogged ui-loginAfter" style="display: none;"><a href="http://i.huanqiu.com/logout

" target="_self" class="hdtExit">退出</a><a id="hdtName" class="hdtName ui-username"></a><span class="hdtWel">欢迎你</span></div>
        </div>
    </div>
</div>

<!-- 公共头部分 end -->

<!-- 频道头 begin-->
<div class="channelHead">
    <div class="navNews">
        <div class="navNewsCon">
            <!-- logo begin-->
            <a target="_self" href="http://opinion.huanqiu.com/">
                <div class="logoNews">
                    <h1></h1>
                </div>
            </a>
            <!-- logo end-->
            <!-- 导航 begin-->
            <div class="navNewsMain">
                <ul>
                    <li <c:if test="${module=='home'}">class="cur"</c:if>><span><a target="_self" href="/home">首页</a><em></em></span></li>
                    <li <c:if test="${module=='articles'}">class="cur curBig"</c:if>><span><a target="_self" href="/articles">学生作文</a></span></li>
                    <li <c:if test="${module=='article'}">class="cur curBig"</c:if>><span><a target="_self" href="http://mil.huanqiu.com/">诗歌散文</a></span></li>
                    <li <c:if test="${module=='article'}">class="cur curBig"</c:if>><span><a target="_self" href="http://mil.huanqiu.com/">文学赏析</a></span></li>
                    <li <c:if test="${module=='article'}">class="cur"</c:if>><span><a target="_self" href="http://mil.huanqiu.com/">社论</a></span></li>
                </ul>
            </div>
            <!-- 导航 end-->

            <!-- 搜索 begin-->
            <div class="navSearch">
                <form action="http://s.huanqiu.com/s/" method="get" id="ui-topSearch">
                    <input name="q" type="text" id="ui-topSearchKeyWord" class="searchBox"/>
                    <input id="ui-topSearchBtn" name="" type="submit" class="searchBtn" value=" "/>
                </form>
            </div>
            <!-- 搜索 end-->
        </div>
    </div>
</div>
<!-- 频道头 end-->

<div class="wrap">
    <sitemesh:write property='body'/>
</div>
<!--
<div id="floatingLayer" style="z-index:2147483647;">
    <ul>
        <li><a href="http://opinion.huanqiu.com/hqpl/">环球评论集</a></li>
        <li><a href="http://opinion.huanqiu.com/editorial/">社评</a>·<a href="http://opinion.huanqiu.com/shanrenping/">单仁平</a></li>
        <li><a href="http://opinion.huanqiu.com/dialogue/">访谈·对话</a></li>
        <li><a href="http://opinion.huanqiu.com/column/">名家·智库</a></li>
        <li><a href="http://opinion.huanqiu.com/roll.html">文章·热门</a></li>
        <li class="lastLine"><a href="http://opinion.huanqiu.com/debate1/">调查·第一话题</a></li>
    </ul>
</div>
-->

<!-- 尾巴 begin -->
<div class="footOutNew m_hide">
    <!-- 其他 begin -->
    <div class="fotherNew">
        <ul>
            <li class="fir">
                <h3>环球无线</h3>

                <div class="iconList">
                    <a href="http://mobile.huanqiu.com/#paperBox" target="_blank">环球时报客户端</a>
                    <a href="http://mobile.huanqiu.com/#mobileBox" target="_blank">手机环球网</a>
                </div>
            </li>
            <li class="sec">
                <h3>推荐服务</h3>

                <div class="iconList">
                    <a href="http://www.huanqiu.com/weibo.html" target="_blank">环球网-官方微博</a>
                    <a href="http://rss.huanqiu.com/" target="_blank">RSS订阅</a>
                    <a href="http://dl.baofeng.com/BFVCenter/kb-huanqiu-0125.exe" target="_blank">环球网-桌面版</a>
                    <a href="http://rank.huanqiu.com/" target="_blank">资讯排行</a>

                </div>
            </li>
            <li class="last">
                <h3>环球时报系产品</h3>

                <div class="iconList">
                    <a href="http://data.huanqiu.com/" target="_blank">环球时报版权数据库</a>
                    <a href="http://www.lifetimes.cn/" class="footTxtLife" target="_blank">生命时报</a>
                    <a href="http://hd.globaltimes.cn/" target="_blank">品牌活动</a>
                    <a href="http://www.globaltraveler.com.cn/" target="_blank">环球旅游周刊</a>
                    <a href="http://www.gtfoundation.cn/" target="_blank">环球时报公益基金会</a>
                    <a href="http://humor.huanqiu.com/" class="footTxtLife" target="_blank">讽刺与幽默</a>
                    <a href="http://www.globalsurvey.com.cn/" target="_blank">舆情中心</a>
                    <a href="http://www.globaltimes.cn/" target="_blank">GlobalTimes</a>

                </div>
            </li>
        </ul>
    </div>
    <!-- 其他 end -->

</div>
<script type="text/javascript" src="/statics/js/performance.js"></script>
<div class="fLinkNew m_hide">
    <div class="flinkMainNew marWidMain">
        <a href="http://corp.huanqiu.com/about/" target="_blank">环球网简介</a><em>|</em>
        <a href="http://corp.huanqiu.com/aboutus/" target="_blank">About huanqiu.com</a><em>|</em>
        <a href="http://www.huanqiu.com/sitemap.html" target="_blank">网站地图</a><em>|</em>
        <a href="http://www.huanqiu.com/weibo.html" target="_blank">官方微博</a><em>|</em>
        <a href="http://corp.huanqiu.com/jobs/" target="_blank">诚聘英才</a><em>|</em>
        <a href="http://corp.huanqiu.com/advertising_services/" target="_blank">广告服务</a><em>|</em>
        <a href="http://corp.huanqiu.com/contactus/" target="_blank">联系方式</a><em>|</em>
        <a href="http://corp.huanqiu.com/privacy_policy/" target="_blank">隐私政策</a><em>|</em>
        <a href="http://corp.huanqiu.com/terms/" target="_blank">服务条款</a><em>|</em>
        <a href="mailto:jubao@huanqiu.com" target="_blank">意见反馈</a>
    </div>
</div>
<!-- 尾巴 end -->
<script>
    $(".leftList>ul>li").each(function(){
        if($(this).index() == 1){
            $(this).addClass("splitLine");
        }
    });

    $(".leftList>ul>div>li").each(function(){
        if($(this).index() == 0){
            $(this).addClass("splitLine");
        }
    });



</script>
</body>
</html>