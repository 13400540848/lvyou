<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String basePath = request.getContextPath();
if(basePath.endsWith("/")){
	basePath = basePath.substring(0, basePath.length()-1);
}
String path = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+basePath;
%>
<!DOCTYPE html>
<html>
 <head>
  <meta charset="utf-8" />
  <meta name="keywords" content="文学交流、社评、教育文化" />
  <meta name="description" content=" YouMe文化传播社是文字爱好者交流平台，致力于在线文字发布、学习、评比信息化服务，提供文学爱好者横向切磋平台。" />
  <meta name="renderer" content="webkit" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <title> YouMe文化传播社</title>
  <link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
  <link rel="stylesheet" href="/statics/layui/css/layui.css?t=1513292531245" />
  <link rel="stylesheet" href="/statics/global.css?t=1513292531245" charset="utf-8" />
  <script src="/statics/layui/layui.js?t=1513292531245"></script>
  <script>layui.cache.host='/statics/layui/';layui.cache.page ='';layui.cache.user ={username:"<c:out value='${user.userName}'/>" ,uid:"<c:out value='${user.id}'/>" ,avatar:"<c:out value='${user.headImage}'/>" ,experience:0 ,sum:0 ,vip:0 ,sex:''};</script>
  <sitemesh:write property='head'/>
  <style>
   li a.background-66CC66{
     color:#66CC66;
   }
  </style>
 </head>
 <body>
  <div class="fly-header fly-bg-bar">
   <div class="layui-container">
    <a class="fly-logo" href="/"> <img src="/statics/images/public/logo5.png" alt="YouMe文化传播社" /> </a>
    <ul class="layui-nav fly-nav layui-hide-xs">
    <li class="layui-nav-item"><a href="/home"  <c:if test="${channel=='home'}">style="color:#66CC66"</c:if>><i class="layui-icon" style="font-size: 26px;">&#xe68e;</i>首页</a></li>
     <li class="layui-nav-item"><a href="/articles" <c:if test="${channel=='articles' || channel=='article'}">style="color:#66CC66"</c:if>  ><i class="iconfont icon-jiaoliu" style="font-size: 26px;"></i>YouMe写作</a></li>
     <li class="layui-nav-item"><a href="/library" <c:if test="${channel=='library'}">style="color:#66CC66"</c:if> ><i class="layui-icon" style="font-size: 26px;">&#xe705;</i>书库</a></li>
     <li class="layui-nav-item"><a href="/open-class" <c:if test="${channel=='open-class'}">style="color:#66CC66"</c:if> ><i class="layui-icon" style="font-size: 26px;">&#xe6ed;</i>公开课</a></li>
    </ul>
     <c:if test="${user!=null && user.userName!=null}">
     <ul class="layui-nav fly-nav-user">
      <li class="layui-nav-item"> <a class="fly-nav-avatar" href="/account"> <cite class="layui-hide-xs">${user.nickName}</cite> <img src="${user.headImage}" /> </a>
      <dl class="layui-nav-child">
       <dd> <a href="/account"><i class="layui-icon" style="margin-left: 2px; font-size: 22px;"></i>我的主页</a> </dd>
       <dd>  <a href="/usercenter/#/setting"><i class="layui-icon"></i>基本设置</a> </dd>
       <hr />
       <!--
       <dd> <a href="/usercenter#/"><i class="layui-icon"></i>用户中心</a>  </dd>
       <dd>  <a href="/usercenter/#/setting"><i class="layui-icon"></i>基本设置</a> </dd>
       <dd> <a href="/usercenter/#/message"><i class="iconfont icon-tongzhi" style="top: 4px;"></i>我的消息</a> </dd>
       <hr style="margin: 5px 0;" /> -->
       <dd> <a href="/v0.1/user/logout" style="text-align: center;">退出</a> </dd>
      </dl> </li>
    </ul>
    </c:if>
     <c:if test="${user==null ||  user.userName==null}">
        <ul class="layui-nav fly-nav-user"> <li class="layui-nav-item">
         <a href="javascript:void(0);" onclick="layui.fly.go('/login')"><i class="iconfont icon-touxiang" style="margin-right:10px;"></i>登入</a> </li><span class="layui-nav-bar" style="width: 0px; left: 0px; opacity: 0; top: 55px;"></span></ul>
     </c:if>
   </div>
  </div>
  <div class="fly-body">
     <sitemesh:write property='body'/>
  </div>
  <script src="/statics/index.js?t=1513292531245"></script>
  <sitemesh:write property='customscript'/>
  </body>
</html>