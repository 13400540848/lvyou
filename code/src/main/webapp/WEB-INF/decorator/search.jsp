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
    <link href="/statics/hq2013/css/topLogin/topLogin.css" rel="stylesheet" type="text/css">
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
</body>
</html>